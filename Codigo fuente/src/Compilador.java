
import com.formdev.flatlaf.FlatIntelliJLaf;
import compilerTools.Directory;
import compilerTools.ErrorLSSL;
import compilerTools.Functions;
import compilerTools.Grammar;
import compilerTools.Production;
import compilerTools.TextColor;
import compilerTools.Token;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import jflex.logging.Out;
import say.swing.JFontChooser;

/**
 *
 * @author sergi
 */
public class Compilador extends javax.swing.JFrame {

    private String title;
    private Directory directorio;
    private ArrayList<Token> tokens;
    private ArrayList<String> ArreToken, ArreCompleto, ArreNomToken, ArreFilaColumnaToken;
    private ArrayList<Variables> ArreVariables = new ArrayList<>();
    private ArrayList<Funciones_CadenaoVariable> ArreFunciones_CadenaoVariable = new ArrayList<>();
    private ArrayList<String> ArreFun_Puerta = new ArrayList<>();

    private ArrayList<String> ArreFun_aspPatio = new ArrayList<>();

    private ArrayList<String> ArreFun_iluPrincipal = new ArrayList<>();
    private ArrayList<String> ArreFun_iluRecepcion = new ArrayList<>();
    private ArrayList<String> ArreFun_iluSala1 = new ArrayList<>();
    private ArrayList<String> ArreFun_iluSala2 = new ArrayList<>();

    private ArrayList<String> ArreFun_pRecepcion = new ArrayList<>();
    private ArrayList<String> ArreFun_pSala1 = new ArrayList<>();
    private ArrayList<String> ArreFun_pSala2 = new ArrayList<>();

    private ArrayList<String> ArreFun_panelPatio = new ArrayList<>();

    private ArrayList<String> ArreFun_tvRecepcion = new ArrayList<>();

    private ArrayList<String> ArreFun_vRecepcion = new ArrayList<>();

    private ArrayList<String> ArreFun_Cortador = new ArrayList<>();
    private ArrayList<String> ArreFun_Aspersor = new ArrayList<>();
    private ArrayList<String> ArreFun_Ventilador = new ArrayList<>();
    private ArrayList<String> ArreFun_Iluminacion = new ArrayList<>();
    private ArrayList<String> ArreFun_Banda = new ArrayList<>();
    private ArrayList<String> ArreFun_TV = new ArrayList<>();
    private ArrayList<String> ArreFun_Alarma = new ArrayList<>();
    private ArrayList<String> ArreFun_Caja = new ArrayList<>();

    private ArrayList<Funciones_CoV_NoV> ArreFunciones_CoV_NoV = new ArrayList<>();
    private ArrayList<Integer> ArreFilaFnc = new ArrayList<>();
    private ArrayList<Integer> ArreFilaVar = new ArrayList<>();
    private ArrayList<ErrorLSSL> errors;
    private ArrayList<String> errores, gramaticas, gramaUsadas;
    String gramatica = "";
    private ArrayList<TextColor> textsColor;
    private Timer timerKeyReleased;
    private ArrayList<Production> identProd, CadErrores, ProCorrectas;
    private HashMap<String, String> identificadores;
    private boolean codeHasBeenCompiled = false;
    private GramaUsa err;
    private int pos;
    Automata auto;
    VentanaErrores venErrores;
    OpcionesGrama opcGrama;
    VentanaCuadruplos vIntermedio;
    private String fncCoV_NoVNombre = "";
    private String fncCoV_NoVValor_CoV = "";
    private String fncCoV_NoVTipo_CoV = "";

    //Colores
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";

    public int numeroError = 0;
    public String errores_lexicos[] = {
        /* 0 */"",
        /* 1 */ "Error lexico [1]: Numero mal definido no debe iniciar con un 0 ",
        /* 2 */ "Error lexico [2]: Token invalido "
    };
    public String soluciones_lexico[] = {
        /* 0 */"",
        /* 1 */ "Solución: \n" + "Borre el 0 que está en la declaracion del numero \n",
        /* 2 */ "Solución: \n" + "Verifique los token señalado y sustituyalo por uno valido \n"
    };
    public String errores_sintacticos[] = {
        /* 0  */"",
        /* 1  */ "Error sintactico [1]: No hay tokens para analizar ",
        /* 2  */ "Error sintactico [2], se esperaba operador de asignacion en el identificador ",
        /* 3  */ "Error sintactico [3], se esperaba numero o cadena en el identificador ",
        /* 4  */ "Error sintactico [4], se esperaba punto y coma ';' en el identificador ",
        /* 5  */ "Error sintactico [5], se esperaba una variable de control en la funcion ",
        /* 6  */ "Error sintactico [6], se esperaba una número o un identificador en la funcion ",
        /* 7  */ "Error sintactico [7], se esperaba parentesis que abre '(' en el ciclo For ",
        /* 8  */ "Error sintactico [8], se esperaba numero o identificador en el ciclo For ",
        /* 9  */ "Error sintactico [9], se esperaba parentesis que cierra ')' en el ciclo For ",
        /* 10 */ "Error sintactico [10], se esperaba llave que abre '{' en el ciclo For ",
        /* 11 */ "Error sintactico [11], se esperaba llave que cierra '}' en el ciclo For ",
        /* 12 */ "Error sintactico [12], se esperaba 'INICIO' en el bloque inicial ",
        /* 13 */ "Error sintactico [13], se esperaba un identificador en el bloque inicial ",
        /* 14 */ "Error sintactico [14], se esperaba llave que abre '{' en el bloque inicial ",
        /* 15 */ "Error sintactico [15], se esperaba 'PRINCIPAL' en el bloque inicial ",
        /* 16 */ "Error sintactico [16], se esperaba parentesis que abre '(' en el bloque inicial ",
        /* 17 */ "Error sintactico [17], se esperaba parentesis que cierra ')' en el bloque inicial ",
        /* 18 */ "Error sintactico [18], se esperaba llave que cierra '}' en el bloque inicial ",
        /* 19 */ "Error sintactico [19], se esperaba 'FINAL' en el bloque inicial ",
        /* 20 */ "Error sintactico [20], se encontraron tokens despues de 'FINAL' eliminalos ",
        /* 21 */ "Error sintactico [21], se esperaba codigo para analizar en el bloque inicial ",
        /* 22 */ "Error sintactico [22], se esperaba parentesis que abre '(' en la funcion ",
        /* 23 */ "Error sintactico [23], se esperaba parentesis que cierra ')' en la funcion ",
        /* 24 */ "Error sintactico [24], se esperaba punto y coma ';' en la funcion ",
        /* 25 */ "Error sintactico [25], se esperaba una función ",
        /* 26 */ "Error sintactico [26], se esperaba coma ',' en la funcion ",
        /* 27 */ "Error sintactico [27], se esperaba parentesis que abre '(' en el if ",
        /* 28 */ "Error sintactico [28], se esperaba valor o identificador en el if ",
        /* 29 */ "Error sintactico [29], se esperaba operador relacional en el if ",
        /* 30 */ "Error sintactico [30], se esperaba parentesis que cierra ')' en el if ",
        /* 31 */ "Error sintactico [31], se esperaba llave que abre '{' en el if ",
        /* 32 */ "Error sintactico [32], se esperaba llave que cierra '}' en el if ",
        /* 33 */ "Error sintactico [33], se esperaba parentesis que abre '(' en el while ",
        /* 34 */ "Error sintactico [34], se esperaba valor o identificador en el while ",
        /* 35 */ "Error sintactico [35], se esperaba operador relacional en el while ",
        /* 36 */ "Error sintactico [36], se esperaba parentesis que cierra ')' en el while ",
        /* 37 */ "Error sintactico [37], se esperaba llave que abre '{' en el while ",
        /* 38 */ "Error sintactico [38], se esperaba llave que cierra '}' en el while ",
        /* 39 */ "Error sintactico [39], se esperaba llave que cierra '}' en el bloque PRINCIPAL ",
        /* 40 */ "Error sintactico [40], se esperaba parentesis que abre '(' en el bloque PRINCIPAL ",
        /* 41 */ "Error sintactico [41], se esperaba parentesis que cierra ')' en el bloque PRINCIPAL ",
        /* 42 */ "Error sintactico [42], se esperaba llave que abre '{' en el bloque PRINCIPAL ",
        /* 43 */ "Error sintactico [43],  se esperaba una variable de control despues del 'def' ",
        /* 44 */ "Error sintactico [44],  se esperaba punto y coma ';' despues de la variable de control "
    };
    public String[] soluciones_sintactico = {
        /* 0 */"",
        /* 1 */ "Solución: \n" + "Se espera escritura de código. \n" + "\n" + "Resultado esperado: \n" + "Agregue la estructura inicial para comenzar con el programa \n" + "Estructura inicial: \n" + "INICIO $ejemplo { \n" + "  PRINCIPAL(){ \n" + "     //Código \n" + "  } \n" + "} ",
        /* 2 */ "Solución: \n" + "Agregue un operador de asignación ‘=’ después del identificador \n" + "\n" + "Resultado esperado: \n" + "Identificador OP_Asignacion Valor Punto_Coma \n" + "Identificador = (Numero | Cadena); \n" + "$ejemplo = 12;",
        /* 3 */ "Solución: \n" + "Escriba un numero o cadena o OP_Retorno despues del signo ‘=’ \n" + "\n" + "Resultado esperado:  \n" + "Identificador OP_Asignacion Valor Punto_Coma \n" + "Identificador = (Numero | Cadena); \n" + "$ejemplo = 12;",
        /* 4 */ "Solución: \n" + "Escribe punto y coma ‘;’ después de la línea de código \n" + "\n" + "Resultado esperado:  \n" + "Identificador OP_Asignacion Valor Punto_Coma \n" + "Identificador = (Numero | Cadena); \n" + "$ejemplo = 12;",
        /* 5 */ "Solución: \n" + "Escriba una variable de control valida \n" + "\n" + "Resultado esperado:  \n" + "(cortadora_activar | cortadora_desactivar | aspersor_activar | aspersor_desactivar | ventilador_activar | ventilador_desactivar |  iluminacion_encender | iluminacion_apagar | puerta_abrir | puerta_cerrar | banda_activar | banda_desactivar | tv_encender | tv_apagar | cajafuerte_activar | cajafuerte_desactivar) Parentesis_A (VARIABLE DE CONTROL) Parentesis_C Punto_Coma \n" + "funcion(VARIABLE DE CONTROL); \n" + "\nEjemplo \n" + "tv_encender(#tvRecepcion);",
        /* 6 */ "Solución: \n" + "Escriba el número o identificador dentro de la función \n" + "\n" + "Resultado esperado:  \n" + "(aspersor_activar | cajafuerte_activar | panel_girar) Parentesis_A (Numero | Identificador) Parentesis_C Punto_Coma \n" + "funcion(Numero | Identificador); \n" + "\nEjemplo \n" + "panel_girar(45);",
        /* 7 */ "Solución: \n" + "Escriba un paréntesis ‘(’ después del for para empezar el uso de la sentencia \n" + "\n" + "Resultado esperado:  \n" + "For Parentesis_A (Numero | Identificador) Parentesis_C Llave_A CODIGO Llave_C \n" + "for(Numero | Identificador){ CODIGO } \n" + "\nEjemplo \n" + "for($ejemplo){ \n" + "CODIGO \n" + "}",
        /* 8 */ "Solución: \n" + "Escriba los parámetros para cumplir con la sentencia for ‘FOR(i=1,i<=10,i++)’ \n" + "\n" + "Resultado esperado:  \n" + "For Parentesis_A (Numero | Identificador) Parentesis_C Llave_A CODIGO Llave_C \n" + "for(Numero | Identificador){ CODIGO } \n" + "\nEjemplo \n" + "for($ejemplo){ \n" + "CODIGO \n" + "}",
        /* 9 */ "Solución: \n" + "Escriba un paréntesis ‘)’ al final de los parámetros del FOR \n" + "\n" + "Resultado esperado:  \n" + "For Parentesis_A (Numero | Identificador) Parentesis_C Llave_A CODIGO Llave_C \n" + "for(Numero | Identificador){ CODIGO } \n" + "\nEjemplo \n" + "for($ejemplo){ \n" + "CODIGO \n" + "}",
        /* 10 */ "Solución: \n" + "Después de los parámetros del FOR abra con una llave ‘{‘ el código que se ciclara en el FOR \n" + "\n" + "Resultado esperado:  \n" + "For Parentesis_A (Numero | Identificador) Parentesis_C Llave_A CODIGO Llave_C \n" + "for(Numero | Identificador){ CODIGO } \n" + "\nEjemplo \n" + "for($ejemplo){ \n" + "CODIGO \n" + "}",
        /* 11 */ "Solución: \n" + "Escriba una llave ‘}’ de cierre para ciclar el código escrito en el FOR \n" + "\n" + "Resultado esperado:  \n" + "For Parentesis_A (Numero | Identificador) Parentesis_C Llave_A CODIGO Llave_C \n" + "for(Numero | Identificador){ CODIGO } \n" + "\nEjemplo \n" + "for($ejemplo){ \n" + "CODIGO \n" + "}",
        /* 12 */ "Solución: \n" + "Escriba el identificador ‘INICIO’ en el comienzo de la hoja en blanco o del código \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",
        /* 13 */ "Solución: \n" + "Escriba un identificador para dar nombre al bloque de código \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",
        /* 14 */ "Solución: \n" + "Escriba la llave ‘{‘ que abre el bloque de inicio \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",
        /* 15 */ "Solución: \n" + "Escriba la palabra reservada ‘PRINCIPAL’ para asignar un main \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",
        /* 16 */ "Solución: \n" + "Escriba el paréntesis ‘(’ que abre el bloque inicial \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",
        /* 17 */ "Solución: \n" + "Escriba el paréntesis ‘)’ que cierra el bloque inicial \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",
        /* 18 */ "Solución: \n" + "Escriba la llave ‘}‘ que cierra el bloque de escritura de código del bloque inicial \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",
        /* 19 */ "Solución: \n" + "Escriba la palabra reservada ‘FINAL’ para cerrar el bloque inicial \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "} \n " + "FINAL ",
        /* 20 */ "Solución: \n" + "Elimine los tokens que se escribieron después de la palabra reservada ‘FINAL’ \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",
        /* 21 */ "Solución: \n" + "Escriba sentencias de código en el bloque ‘INICIAL’ \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",
        /* 22 */ "Solución: \n" + "Escriba el paréntesis ‘(‘ que abre el inicio de una función \n" + "\n" + "Resultado esperado:  \n" + "(cortadora_activar | cortadora_desactivar | aspersor_activar | aspersor_desactivar | ventilador_activar | ventilador_desactivar |  iluminacion_encender | iluminacion_apagar | puerta_abrir | puerta_cerrar | banda_activar | banda_desactivar | tv_encender | tv_apagar | alarma_activar | alarma_desactivar | cajafuerte_activar | cajafuerte_desactivar | panel_girar) Parentesis_A (Valor) Parentesis_C Punto_Coma \n" + "(cortadora_activar | cortadora_desactivar | aspersor_activar | aspersor_desactivar | ventilador_activar | ventilador_desactivar |  iluminacion_encender | iluminacion_apagar | puerta_abrir | puerta_cerrar | banda_activar | banda_desactivar | tv_encender | tv_apagar | alarma_activar | alarma_desactivar | cajafuerte_activar | cajafuerte_desactivar | panel_girar) (Valor); \n" + "\nEjemplo \n" + "panel_girar(45);",
        /* 23 */ "Solución: \n" + "Escriba el paréntesis ‘)‘ que cierra la función \n" + "\n" + "Resultado esperado:  \n" + "(cortadora_activar | cortadora_desactivar | aspersor_activar | aspersor_desactivar | ventilador_activar | ventilador_desactivar |  iluminacion_encender | iluminacion_apagar | puerta_abrir | puerta_cerrar | banda_activar | banda_desactivar | tv_encender | tv_apagar | alarma_activar | alarma_desactivar | cajafuerte_activar | cajafuerte_desactivar | panel_girar) Parentesis_A (Valor) Parentesis_C Punto_Coma \n" + "(cortadora_activar | cortadora_desactivar | aspersor_activar | aspersor_desactivar | ventilador_activar | ventilador_desactivar |  iluminacion_encender | iluminacion_apagar | puerta_abrir | puerta_cerrar | banda_activar | banda_desactivar | tv_encender | tv_apagar | alarma_activar | alarma_desactivar | cajafuerte_activar | cajafuerte_desactivar | panel_girar) (Valor); \n" + "\nEjemplo \n" + "panel_girar(45);",
        /* 24 */ "Solución: \n" + "Escriba punto y coma ‘;’ para cerrar la función escrita \n" + "\n" + "Resultado esperado:  \n" + "(cortadora_activar | cortadora_desactivar | aspersor_activar | aspersor_desactivar | ventilador_activar | ventilador_desactivar |  iluminacion_encender | iluminacion_apagar | puerta_abrir | puerta_cerrar | banda_activar | banda_desactivar | tv_encender | tv_apagar | alarma_activar | alarma_desactivar | cajafuerte_activar | cajafuerte_desactivar | panel_girar) Parentesis_A (Valor) Parentesis_C Punto_Coma \n" + "(cortadora_activar | cortadora_desactivar | aspersor_activar | aspersor_desactivar | ventilador_activar | ventilador_desactivar |  iluminacion_encender | iluminacion_apagar | puerta_abrir | puerta_cerrar | banda_activar | banda_desactivar | tv_encender | tv_apagar | alarma_activar | alarma_desactivar | cajafuerte_activar | cajafuerte_desactivar | panel_girar) (Valor); \n" + "\nEjemplo \n" + "panel_girar(45);",
        /* 26 */ "Solución: \n" + "Escriba el punto y coma que falta en la función \n" + "\n" + "Resultado esperado:  \n" + "(cortadora_activar | cortadora_desactivar | aspersor_activar | aspersor_desactivar | ventilador_activar | ventilador_desactivar |  iluminacion_encender | iluminacion_apagar | puerta_abrir | puerta_cerrar | banda_activar | banda_desactivar | tv_encender | tv_apagar | alarma_activar | alarma_desactivar | cajafuerte_activar | cajafuerte_desactivar | panel_girar) Parentesis_A (Valor) Parentesis_C Punto_Coma \n" + "(cortadora_activar | cortadora_desactivar | aspersor_activar | aspersor_desactivar | ventilador_activar | ventilador_desactivar |  iluminacion_encender | iluminacion_apagar | puerta_abrir | puerta_cerrar | banda_activar | banda_desactivar | tv_encender | tv_apagar | alarma_activar | alarma_desactivar | cajafuerte_activar | cajafuerte_desactivar | panel_girar) (Valor); \n" + "\nEjemplo \n" + "panel_girar(45);",
        /* 27 */ "Solución: \n" + "Escriba el paréntesis ‘(’ que abre los parámetros de la sentencia IF \n" + "\n" + "Resultado esperado:  \n" + "If Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "If ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + "}",
        /* 28 */ "Solución: \n" + "Escriba los parámetros que se usan dentro de la sentencia IF \n" + "\n" + "Resultado esperado:  \n" + "If Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "If ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + "}",
        /* 29 */ "Solución: \n" + "Escriba el operador relacional (<=, <, >=, >, ==, <>) que compara los parámetros en el IF \n" + "\n" + "Resultado esperado:  \n" + "If Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "If ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + "}",
        /* 30 */ "Solución: \n" + "Escriba el paréntesis ‘)’ de cierre que concluye los parámetros en el IF \n" + "\n" + "Resultado esperado:  \n" + "If Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "If ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + " ",
        /* 31 */ "Solución: \n" + "Escriba la llave ‘{’ que abre sentencias de código dentro del IF \n" + "\n" + "Resultado esperado:  \n" + "If Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "If ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + " ",
        /* 32 */ "Solución: \n" + "Escriba la llave ‘}’ que cierra las sentencias de código dentro del IF \n" + "\n" + "Resultado esperado:  \n" + "If Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "If ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + "}",
        /* 33 */ "Solución: \n" + "Escriba el paréntesis ‘(’ que abre los parámetros de condición en el WHILE \n" + "\n" + "Resultado esperado:  \n" + "While Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "While ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + "}",
        /* 34 */ "Solución: \n" + "Escriba los valores numéricos,cadenas o identificadores que se usarán de condición en el WHILE \n" + "\n" + "Resultado esperado:  \n" + "While Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "While ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + "}",
        /* 35 */ "Solución: \n" + "Escriba el operador relacional (<=, <, >=, >, ==, <>) que compara los parámetros en el WHILE \n" + "\n" + "Resultado esperado:  \n" + "While Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "While ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + "}",
        /* 36 */ "Solución: \n" + "Escriba el paréntesis ‘)’ que cierra los parámetros asignados en el WHILE \n" + "\n" + "Resultado esperado:  \n" + "While Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "While ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + "}",
        /* 37 */ "Solución: \n" + "Escriba la llave ‘{’ que abre sentencias de código dentro del WHILE \n" + "\n" + "Resultado esperado:  \n" + "While Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "While ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + "}",
        /* 38 */ "Solución: \n" + "Escriba la llave ‘}’ que cierra sentencias de código dentro del WHILE \n" + "\n" + "Resultado esperado:  \n" + "While Parentesis_A (Valor | Identificador) Op_Relacional (Valor | Identificador) Llave_A CODIGO Llave_C \n" + "\nEjemplo \n" + "While ($ejemplo >= “ejemplo”){ \n" + "CODIGO \n" + "}",
        /* 39 */ "Solución: \n" + "Escriba la llave ‘}‘ que cierra el bloque de escritura de código del bloque PRINCIPAL \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }  \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n",
        /* 40 */ "Solución: \n" + "Escriba el paréntesis ‘(’ que abre el bloque PRINCIPAL \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",
        /* 41 */ "Solución: \n" + "Escriba el paréntesis ‘)’ que cierra el bloque PRINCIPAL \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",
        /* 42 */ "Solución: \n" + "Escriba la llave ‘{‘ que abre el bloque de escritura de código \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL "

    };

    public String errores_semanticos[] = {
        /* 0 */"",
        /* 1 */ "Error semantico [1]: La variable ya se encuentra declarada ",
        /* 2 */ "Error semantico [2]: No se puede cambiar el tipo de dato de la variable \nporque ya se encuentra definido ",
        /* 3 */ "Error semantico [3]: La variable no se encuentra declarada ",
        /* 4 */ "Error semantico [4]: La variable asignada en la función es de tipo 'cadena', se esperaba una 'numero' ",
        /* 5 */ "Error semantico [5]: La puerta no se puede cerrar si esta cerrada ",
        /* 6 */ "Error semantico [6]: La puerta no se puede abrir si esta abierta ",
        /* 7 */ "Error semantico [7]: El aspersor no se puede desactivar si ya está desactivado ",
        /* 8 */ "Error semantico [8]: El aspersor no se puede activar si ya está activado ",
        /* 9 */ "Error semantico [9]: La cortadora no se puede desactivar si ya está desactivado ",
        /* 10 */ "Error semantico [10]: La cortadora no se puede activar si ya está activado  ",
        /* 11 */ "Error semantico [11]: El ventilador no se puede desactivar si ya está desactivado ",
        /* 12 */ "Error semantico [12]: El ventilador no se puede activar si ya está activado ",
        /* 13 */ "Error semantico [13]: La iluminacion no se puede apagar si ya está apagada ",
        /* 14 */ "Error semantico [14]: La iluminacion no se puede encender si ya está encendida ",
        /* 15 */ "Error semantico [15]: La banda no se puede desactivar si ya está desactivado ",
        /* 16 */ "Error semantico [16]: La banda no se puede activar si ya está activado ",
        /* 17 */ "Error semantico [17]: La TV no se puede apagar si ya está apagada ",
        /* 18 */ "Error semantico [18]: La TV no se puede encender si ya está encendida ",
        /* 19 */ "Error semantico [19]: La alarma no se puede desactivar si ya está desactivada ",
        /* 20 */ "Error semantico [20]: La alarma no se puede activar si ya está activada ",
        /* 21 */ "Error semantico [21]: La caja fuerte no se puede desactivar si ya está desactivada ",
        /* 22 */ "Error semantico [22]: La caja fuerte no se puede activar si ya está activada ",
        /* 23 */ "Error semantico [23]: La variable asignada no corresponde al tipo de funcion ",
        /* 24 */ "Error semantico [24]: El panel no se puede apagar si ya esta apagado ",
        /* 25 */ "Error semantico [25]: El panel no se puede encender si ya esta encendido ",
        /* 26 */ "Error semantico [26]: El panel no puede girar si no se encuentra encendido ",
        /* 27 */ "Error semantico [27]: El panel no puede girar a más de 180° ",
        /* 28 */ "Error semantico [28]: Los tipos de datos no coinciden ",
        /* 29 */ "Error semantico [29]: Operacion invalida ",};

    public String soluciones_semantico[] = {
        /* 0 */"",
        /* 1 */ "Solucion: \n" + "La variable que quiere declarar ya se encuentra anteriormente declarada \n" + "\n" + "Resultado esperado: \n" + "$ejemplo = 12",
        /* 2 */ "Solucion: \n" + "No se puede cambiar el tipo de dato de la variable porque ya se encuentra definido \n",
        /* 3 */ "Solucion: \n" + "La variable no se encuentra declarada para asignar en la función \n",
        /* 4 */ "Solucion: \n" + "La variable asignada en la función es de tipo 'cadena', se esperaba una 'numero' \n" + "\n" + "Resultado esperado:  \n" + "Identificador OP_Asignacion Valor Punto_Coma \n" + "$identificador = 'Cadena'; \n" + "$ejemplo = 'Ejemplo'",
        /* 5 */ "Solucion: \n" + "La puerta no se puede cerrar si esta cerrada \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "puerta_cerar(#varControl);",
        /* 6 */ "Solucion: \n" + "La puerta no se puede abrir si esta abierta \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "puerta_abrir(#varControl);",
        /* 7 */ "Solucion: \n" + "El aspersor no se puede desactivar si ya está desactivado \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "aspersor_activar(#varControl);",
        /* 8 */ "Solucion: \n" + "El aspersor no se puede activar si ya está activado \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "aspersor_desactivar(#varControl);",
        /* 9 */ "Solucion: \n" + "La cortadora no se puede desactivar si ya está desactivado \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "cortadora_activar(#varControl);",
        /* 10 */ "Solucion: \n" + "La cortadora no se puede activar si ya está activado \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "cortadora_desactivar(#varControl);",
        /* 11 */ "Solucion: \n" + "El ventilador no se puede desactivar si ya está desactivado \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "ventilador_activar(#varControl);",
        /* 12 */ "Solucion: \n" + "El ventilador no se puede activar si ya está activado \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "ventilador_desactivar(#varControl);",
        /* 13 */ "Solucion: \n" + "La iluminacion no se puede apagar si ya está apagada \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "iluminacion_encender(#varControl);",
        /* 14 */ "Solucion: \n" + "La iluminacion no se puede encender si ya está encendida  \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "iluminacion_apagar(#varControl);",
        /* 15 */ "Solucion: \n" + "La banda no se puede desactivar si ya está desactivado \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "banda_activar(#varControl);",
        /* 16 */ "Solucion: \n" + "La banda no se puede activar si ya está activado \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "banda_desactivar(#varControl);",
        /* 17 */ "Solucion: \n" + "La TV no se puede apagar si ya está apagada \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "tv_encender(#varControl);",
        /* 18 */ "Solucion: \n" + "La TV no se puede encender si ya está encendida \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "tv_apagar(#varControl);",
        /* 19 */ "Solucion: \n" + "La alarma no se puede desactivar si ya está desactivada \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "alarma_activar(#varControl);",
        /* 20 */ "Solucion: \n" + "La alarma no se puede activar si ya está activada \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "alarma_desactivar(#varControl);",
        /* 21 */ "Solucion: \n" + "La caja fuerte no se puede desactivar si ya está desactivada \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "cajafuerte_activar(#varControl);",
        /* 22 */ "Solucion: \n" + "La caja fuerte no se puede activar si ya está activada \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "cajafuerte_desactivar(#varControl);",
        /* 23 */ "Solucion: \n" + "La variable asignada no corresponde al tipo de funcion \n",
        /* 23 */ "Solucion: \n" + "El panel no se puede apagar si ya esta apagado \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "panel_encender(#varControl);",
        /* 25 */ "Solucion: \n" + "El panel no se puede encender si ya esta encendido \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "panel_apagar(#varControl);",
        /* 26 */ "Solucion: \n" + "El panel no puede girar si no se encuentra encendido \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL); \n" + "\n Ejemplo \n" + "panel_encender(#varControl);",
        /* 27 */ "Solucion: \n" + "El panel no puede girar a más de 180° \n" + "\n" + "Resultado esperado: \n" + "funcion(CONTROL,Numero o Identificador); \n" + "\n Ejemplo \n" + "panel_girar(#varCOntrol,180);"

    };

    public Compilador() {
        initComponents();
        init();
        //a_dOpciones();
    }

    private void init() {
        title = "AICT - Compilador";
        setLocationRelativeTo(null);
        setTitle(title);
        directorio = new Directory(this, txtCodigo, title, ".aict");
        addWindowListener(new WindowAdapter() {// Cuando presiona la "X" de la esquina superior derecha
            @Override
            public void windowClosing(WindowEvent e) {
                directorio.Exit();
                System.exit(0);
            }
        });

        Functions.setLineNumberOnJTextComponent(txtCodigo); //Pone los numeros de linea
        timerKeyReleased = new Timer((int) (1000 * 0.3), (ActionEvent e) -> {
            timerKeyReleased.stop();

            int posicion = txtCodigo.getCaretPosition();
            txtCodigo.setText(txtCodigo.getText().replaceAll("[\r]+", ""));
            txtCodigo.setCaretPosition(posicion);

            colorAnalysis();

        });
        Functions.insertAsteriskInName(this, txtCodigo, () -> {
            timerKeyReleased.restart();
        });
        tokens = new ArrayList<>();
        ArreNomToken = new ArrayList<>();
        ArreToken = new ArrayList<>();
        ArreFilaColumnaToken = new ArrayList<>();
        errors = new ArrayList<>();
        errores = new ArrayList<>();
        gramaticas = new ArrayList<>();
        gramaUsadas = new ArrayList<>();
        CadErrores = new ArrayList<>();
        textsColor = new ArrayList<>();
        ProCorrectas = new ArrayList<>();
        ArreCompleto = new ArrayList<>();
        identProd = new ArrayList<>(); //Identificadores de producción
        identificadores = new HashMap<>();

        Functions.setAutocompleterJTextComponent(new String[]{"INICIO $programa {\n    PRINCIPAL(){\n//Bloque de codigo\n       \n       \n       \n//Fin bloque de codigo\n    }//Fin PRINCIPAL\n}//Fin INICIO\nFINAL\n//NO AGREGAR CODIGO DESPUES DE LA PALABRA FINAL",
            "cortadora_activar(#cortadora);", "cortadora_desactivar(#cortadora);",
            "aspersor_activar(#aspPatio);", "aspersor_desactivar(#aspPatio);",
            "ventilador_activar(#vRecepcion);", "ventilador_desactivar(#vRecepcion);",
            "iluminacion_encender(#ilu);", "iluminacion_apagar(#ilu);",
            "puerta_abrir(#p);", "puerta_cerrar(#p);",
            "banda_activar(#banda);", "banda_desactivar(#banda);",
            "tv_encender(#tvRecepcion);", "tv_apagar(#tvRecepcion);",
            "alarma_activar(#alarma);", "alarma_desactivar(#alarma);",
            "cajafuerte_activar(#caja);", "cajafuerte_desactivar(#caja);",
            "panel_girar(#panelPatio,GRADOS);", "panel_encender(#panelPatio);", "panel_apagar(#panelPatio);",
            "#vRecepcion;", "#pRecepcion;",
            "#pSala1;", "#pSala2;",
            "#iluRecepcion;", "#iluPrincipal;",
            "#iluSala2;", "#iluSala1;",
            "#tvRecepcion;", "#panelPatio;",
            "#aspPatio;", "#alarma;",
            "#caja;", "#cortadora;"
        }, txtCodigo, () -> {
            timerKeyReleased.restart();
        });
    }

    private void colorAnalysis() {
        /* Limpiar el arreglo de colores */
        textsColor.clear();
        /* Extraer rangos de colores */
        LexerColor lexerColor;
        try {
            File codigo = new File("color.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = txtCodigo.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexerColor = new LexerColor(entrada);
            while (true) {
                TextColor textColor = lexerColor.yylex();
                if (textColor == null) {
                    break;
                }
                textsColor.add(textColor);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
        Functions.colorTextPane(textsColor, txtCodigo, new Color(40, 40, 40));
    }

    private void clearField() {
        //Functions.clearDataInTable(tblTokens);
        txtConsola.setText("");
        tokens.clear();
        errors.clear();
        gramaticas.clear();
        gramaUsadas.clear();
        ArreToken.clear();
        CadErrores.clear();
        ArreNomToken.clear();
        ProCorrectas.clear();
        ArreCompleto.clear();
        ArreVariables.clear();
        ArreFunciones_CadenaoVariable.clear();
        ArreFunciones_CoV_NoV.clear();

        ArreFun_aspPatio.clear();

        ArreFun_iluPrincipal.clear();
        ArreFun_iluRecepcion.clear();
        ArreFun_iluSala1.clear();
        ArreFun_iluSala2.clear();

        ArreFun_pRecepcion.clear();
        ArreFun_pSala1.clear();
        ArreFun_pSala2.clear();

        ArreFun_panelPatio.clear();

        ArreFun_tvRecepcion.clear();

        ArreFun_vRecepcion.clear();

        ArreFun_Puerta.clear();
        ArreFun_Cortador.clear();
        ArreFun_Aspersor.clear();
        ArreFun_Ventilador.clear();
        ArreFun_Iluminacion.clear();
        ArreFun_Banda.clear();
        ArreFun_TV.clear();
        ArreFun_Alarma.clear();
        ArreFun_Caja.clear();

        identProd.clear();
        identificadores.clear();
        codeHasBeenCompiled = false;
    }

    private void compile() {
        clearField();
        lexicalAnalysis();
        //fillTablaTokens();
        //syntacticAnalysis();
        sintactico();
        //semanticAnalysis();
        printConsole();
        codigoIntermedio();
        codeHasBeenCompiled = true;
        //a_dOpciones();

    }

    private void lexicalAnalysis() {
        Lexer lexer;
        try {
            File codigo = new File("code.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = txtCodigo.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexer = new Lexer(entrada);
            while (true) {
                Token token = lexer.yylex();
                if (token == null) {
                    break;
                }
                tokens.add(token);

            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }

    }

    private void fillTablaTokens() {
        vtn_TablaTokens vtnTabla = new vtn_TablaTokens();
        Functions.clearDataInTable(vtnTabla.tblTokens);
        tokens.forEach(token -> {
            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(), "[" + token.getLine() + ", " + token.getColumn() + "]"};
            Functions.addRowDataInTable(vtnTabla.tblTokens, data);
            System.out.println(token.getLexicalComp() + " " + token.getLexeme() + "[" + token.getLine() + ", " + token.getColumn() + "]");
        });

        System.out.println("Tokens listos");
        vtnTabla.setVisible(true);

    }

    private void fillTablaCuadruplos() {
        String cadena="";
        ArrayList<String> Arre = codigoIntermedio();
        System. out. println("=================\n"+Arre); 
        VentanaCuadruplos vtnIntermedio = new VentanaCuadruplos(cadena);
        Functions.clearDataInTable(vtnIntermedio.tblCuadru);
        for (int a = 0; a < Arre.size(); a++) {
            ArbolExpresion arbolExpresionArit = new ArbolExpresion();
            String cad = Arre.get(a);
            cadena=cadena+arbolExpresionArit.crearArbol(cad);
            
            ArrayList<Cuadruplo> cuadruplos = arbolExpresionArit.getCuadruplos();
            for (int i = 0; i < cuadruplos.size(); i++) {
                Cuadruplo cuadruplo = cuadruplos.get(i);
                String operador = cuadruplo.getOp();
                String argu1 = cuadruplo.getArg1();
                String argu2 = cuadruplo.getArg2();
                String resul = cuadruplo.getR();
                Object[] data = new Object[]{operador, argu1, argu2, resul};
                Functions.addRowDataInTable(vtnIntermedio.tblCuadru, data);
                System.out.println(operador + " " + argu1 + " " + argu2 + " " + resul);
            }
        }
        System.out.println("Tokens listos");
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&\n"+cadena);
        vtnIntermedio.setCadena(cadena);
        vtnIntermedio.setVisible(true);
    }

    private void semanticAnalysis() {
        sema_asignaFilas();
        if (ArreVariables.size() > 0) {
            if (sema_variables_tipoDato()) {
                System.out.println("Sin cambios de tipos de variables");
            }
        }
        if (ArreFunciones_CadenaoVariable.size() > 0) {
            if (!sema_fncCoV_varNoDeclarada()) { //true -> hubo variables no declaradas. false -> todas las variables están declaradas
                System.out.println("Todas las variables estan bien definidas para las funciones");
                if (!sema_fncCoV_tipoIncorrecto()) {
                    System.out.println("Todas las variables o datos recibidos en las funciones son correctos");
                }
            }
        }
        //System.out.println("Tamaño NoV: " + ArreFunciones_CoV_NoV.size());
        if (ArreFunciones_CoV_NoV.size() > 0) {
            if (!sema_fncCoV_NoV_varNoDeclaradas()) {
                System.out.println("Todas las variables estan bien definidas para las funciones CoV_NoV");
                if (!sema_fncCoV_NoV_tipoIncorrecto()) {
                    System.out.println("Todas las variables CoV_NoV o datos recibidos en las funciones son correctos");
                }
            }
        }

    }

    private boolean sema_variables_repetidas() {
        //EDITANDO

        for (int i = 0; i < ArreVariables.size(); i++) {
            for (int k = i + 1; k < ArreVariables.size(); k++) {
//                System.out.println("");
//                System.out.println("Nom i -> " + ArreVariables.get(i).nombre() + " Fila_Columna -> " + ArreVariables.get(i).fila_columna());
//                System.out.println("Nom k -> " + ArreVariables.get(k).nombre() + " Fila_Columna -> " + ArreVariables.get(k).fila_columna());
                if (ArreVariables.get(i).nombre().equals(ArreVariables.get(k).nombre())) {
                    System.out.println(ANSI_RED + errores_semanticos[1] + ArreVariables.get(i).fila_columna() + "\nVariable -> " + ArreVariables.get(k).nombre() + ArreVariables.get(k).fila_columna() + ANSI_RESET);
                    errores.add(errores_semanticos[1] + " " + ArreVariables.get(i).fila_columna() + "\nVariable -> " + ArreVariables.get(k).nombre() + " " + ArreVariables.get(k).fila_columna());

                    return true; //Si hay variables repetidas
                }
            }
        }
        return false; //No hay variables repetidas
    }

    private void printConsole() {
        int numErrores = errores.size();
        if (numErrores > 0) {
            String strErrores = errores.get(0);
            txtConsola.setText("Compilación terminada...\n" + strErrores + "\nLa compilación terminó con errores...");
            txtConsola.setForeground(Color.red);
        } else {
            txtConsola.setText("Compilación terminada...");
            txtConsola.setForeground(Color.green);
            String msg = "";

            for (int i = 0; i < ArreVariables.size(); i++) {
                Variables var = ArreVariables.get(i);
                msg += var.nombre() + " " + var.valor() + " " + var.tipo() + " " + var.fila_columna() + "\n";
            }
            //mensaje(msg);

        }
        txtConsola.setCaretPosition(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        pnlFondo = new javax.swing.JPanel();
        pnlBarraHerramientas = new javax.swing.JPanel();
        imgNuevo = new javax.swing.JLabel();
        imgAbrir = new javax.swing.JLabel();
        imgGuardar = new javax.swing.JLabel();
        imgGuardarComo = new javax.swing.JLabel();
        imgCompilar = new javax.swing.JLabel();
        imgEjecutar = new javax.swing.JLabel();
        pnlCodigo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCodigo = new javax.swing.JTextPane();
        jFontChooser1 = new say.swing.JFontChooser();
        pnlError = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtConsola = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        menu = new javax.swing.JMenuBar();
        archivo = new javax.swing.JMenu();
        opNuevo = new javax.swing.JMenuItem();
        opAbrir = new javax.swing.JMenuItem();
        opGuardar = new javax.swing.JMenuItem();
        opGuardarComo = new javax.swing.JMenuItem();
        editar = new javax.swing.JMenu();
        opCortar = new javax.swing.JMenuItem();
        opCopiar = new javax.swing.JMenuItem();
        opPegar = new javax.swing.JMenuItem();
        correr = new javax.swing.JMenu();
        opCompilar = new javax.swing.JMenuItem();
        opEjecutar = new javax.swing.JMenuItem();
        opciones = new javax.swing.JMenu();
        opFuente = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        opTokens = new javax.swing.JMenu();
        opTablaTokensD = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        opGramatica = new javax.swing.JMenu();
        opAutomata = new javax.swing.JMenuItem();
        opGramaticaG = new javax.swing.JMenuItem();
        opGramaticaU = new javax.swing.JMenuItem();
        opSemantico = new javax.swing.JMenu();
        opAspersor = new javax.swing.JMenu();
        opAspersorPatio = new javax.swing.JMenuItem();
        opIlu = new javax.swing.JMenu();
        opIluPrincipal = new javax.swing.JMenuItem();
        opIluRecepcion = new javax.swing.JMenuItem();
        opIluSala1 = new javax.swing.JMenuItem();
        opIluSala2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        opPuertaRecepcion = new javax.swing.JMenuItem();
        opPuertaSala1 = new javax.swing.JMenuItem();
        opPuertaSala2 = new javax.swing.JMenuItem();
        opPanel = new javax.swing.JMenu();
        opPanelPatio = new javax.swing.JMenuItem();
        opTV = new javax.swing.JMenu();
        opTVRecepcion = new javax.swing.JMenuItem();
        opVentilador = new javax.swing.JMenu();
        opVentiladorRecepcion = new javax.swing.JMenuItem();
        opAlarma = new javax.swing.JMenuItem();
        opBanda = new javax.swing.JMenuItem();
        opCaja = new javax.swing.JMenuItem();
        opCortadora = new javax.swing.JMenuItem();
        opCodIntermedio = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AICT Compilador");
        setMinimumSize(new java.awt.Dimension(830, 550));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlFondo.setBackground(new java.awt.Color(255, 255, 255));
        pnlFondo.setMaximumSize(new java.awt.Dimension(830, 530));
        pnlFondo.setMinimumSize(new java.awt.Dimension(830, 530));
        pnlFondo.setPreferredSize(new java.awt.Dimension(830, 530));
        pnlFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlBarraHerramientas.setBackground(new java.awt.Color(255, 255, 255));
        pnlBarraHerramientas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
        pnlBarraHerramientas.setMaximumSize(new java.awt.Dimension(830, 30));
        pnlBarraHerramientas.setMinimumSize(new java.awt.Dimension(830, 30));
        pnlBarraHerramientas.setPreferredSize(new java.awt.Dimension(830, 30));
        pnlBarraHerramientas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imgNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevoArchivo30x30.png"))); // NOI18N
        imgNuevo.setToolTipText("Nuevo archivo");
        imgNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgNuevoMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        imgAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abrirArchivo30x30.png"))); // NOI18N
        imgAbrir.setToolTipText("Abrir archivo");
        imgAbrir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgAbrir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgAbrirMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgAbrir, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, -1));

        imgGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardarArchivo30x30.png"))); // NOI18N
        imgGuardar.setToolTipText("Guardar archivo");
        imgGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgGuardarMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, -1, -1));

        imgGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardarComo30x30.png"))); // NOI18N
        imgGuardarComo.setToolTipText("Guardar como");
        imgGuardarComo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgGuardarComo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgGuardarComoMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgGuardarComo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, -1, -1));

        imgCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/compilar30x30.png"))); // NOI18N
        imgCompilar.setToolTipText("Compilar");
        imgCompilar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgCompilar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgCompilarMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgCompilar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, -1, -1));

        imgEjecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ejecutar30x30.png"))); // NOI18N
        imgEjecutar.setToolTipText("Ejecutar");
        imgEjecutar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgEjecutar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgEjecutarMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgEjecutar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, -1, -1));

        pnlFondo.add(pnlBarraHerramientas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlCodigo.setBackground(new java.awt.Color(204, 204, 204));
        pnlCodigo.setMinimumSize(new java.awt.Dimension(830, 320));
        pnlCodigo.setPreferredSize(new java.awt.Dimension(830, 320));
        pnlCodigo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        txtCodigo.setMaximumSize(new java.awt.Dimension(830, 320));
        txtCodigo.setMinimumSize(new java.awt.Dimension(830, 320));
        txtCodigo.setPreferredSize(new java.awt.Dimension(830, 320));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtCodigo);

        pnlCodigo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 810, 320));

        jFontChooser1.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jFontChooser1.setSelectedFontFamily("Roboto");
        jFontChooser1.setSelectedFontSize(16);
        pnlCodigo.add(jFontChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        pnlFondo.add(pnlCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, -1));

        pnlError.setBackground(new java.awt.Color(184, 184, 184));
        pnlError.setMaximumSize(new java.awt.Dimension(830, 130));
        pnlError.setMinimumSize(new java.awt.Dimension(830, 130));
        pnlError.setPreferredSize(new java.awt.Dimension(830, 130));
        pnlError.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtConsola.setEditable(false);
        txtConsola.setColumns(20);
        txtConsola.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        txtConsola.setRows(5);
        txtConsola.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtConsola.setMaximumSize(new java.awt.Dimension(830, 130));
        txtConsola.setMinimumSize(new java.awt.Dimension(830, 130));
        txtConsola.setPreferredSize(new java.awt.Dimension(830, 130));
        txtConsola.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtConsolaMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(txtConsola);

        pnlError.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 810, 130));

        pnlFondo.add(pnlError, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, -1, 170));

        getContentPane().add(pnlFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jButton1.setText("jButton1");
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        menu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        archivo.setText("Archivo");

        opNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        opNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevoArchivo20x20.png"))); // NOI18N
        opNuevo.setText("Nuevo...");
        opNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opNuevoActionPerformed(evt);
            }
        });
        archivo.add(opNuevo);

        opAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        opAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abrirArchivo20x20.png"))); // NOI18N
        opAbrir.setText("Abrir...");
        opAbrir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opAbrirActionPerformed(evt);
            }
        });
        archivo.add(opAbrir);

        opGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        opGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardarArchivo20x20.png"))); // NOI18N
        opGuardar.setText("Guardar...");
        opGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opGuardarActionPerformed(evt);
            }
        });
        archivo.add(opGuardar);

        opGuardarComo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        opGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardarComo20x20.png"))); // NOI18N
        opGuardarComo.setText("Guardar como...");
        opGuardarComo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opGuardarComoActionPerformed(evt);
            }
        });
        archivo.add(opGuardarComo);

        menu.add(archivo);

        editar.setText("Editar");

        opCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        opCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cortar20x20.png"))); // NOI18N
        opCortar.setText("Cortar");
        opCortar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editar.add(opCortar);

        opCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        opCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/copiar20x20.png"))); // NOI18N
        opCopiar.setText("Copiar");
        opCopiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editar.add(opCopiar);

        opPegar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        opPegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pegar20x20.png"))); // NOI18N
        opPegar.setText("Pegar");
        opPegar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editar.add(opPegar);

        menu.add(editar);

        correr.setText("Correr");

        opCompilar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        opCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/compilar20x20.png"))); // NOI18N
        opCompilar.setText("Compilar");
        opCompilar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opCompilarActionPerformed(evt);
            }
        });
        correr.add(opCompilar);

        opEjecutar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        opEjecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ejecutar20x20.png"))); // NOI18N
        opEjecutar.setText("Ejecutar");
        opEjecutar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opEjecutarActionPerformed(evt);
            }
        });
        correr.add(opEjecutar);

        menu.add(correr);

        opciones.setText("Opciones");

        opFuente.setText("Fuente");
        opFuente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opFuenteMouseClicked(evt);
            }
        });
        opFuente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opFuenteActionPerformed(evt);
            }
        });
        opciones.add(opFuente);
        opciones.add(jSeparator2);

        opTokens.setText("Tokens");

        opTablaTokensD.setText("Tabla de tokens");
        opTablaTokensD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opTablaTokensDActionPerformed(evt);
            }
        });
        opTokens.add(opTablaTokensD);

        opciones.add(opTokens);
        opciones.add(jSeparator1);

        opGramatica.setText("Gramática");

        opAutomata.setText("Autómata");
        opAutomata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opAutomataActionPerformed(evt);
            }
        });
        opGramatica.add(opAutomata);

        opGramaticaG.setText("Gramáticas");
        opGramaticaG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opGramaticaGActionPerformed(evt);
            }
        });
        opGramatica.add(opGramaticaG);

        opGramaticaU.setText("Gramáticas usadas");
        opGramaticaU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opGramaticaUActionPerformed(evt);
            }
        });
        opGramatica.add(opGramaticaU);

        opciones.add(opGramatica);

        opSemantico.setText("Semantico");

        opAspersor.setText("Aspersor");

        opAspersorPatio.setText("Patio");
        opAspersorPatio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opAspersorPatioActionPerformed(evt);
            }
        });
        opAspersor.add(opAspersorPatio);

        opSemantico.add(opAspersor);

        opIlu.setText("Iluminacion");

        opIluPrincipal.setText("Principal");
        opIluPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opIluPrincipalActionPerformed(evt);
            }
        });
        opIlu.add(opIluPrincipal);

        opIluRecepcion.setText("Recepcion");
        opIluRecepcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opIluRecepcionActionPerformed(evt);
            }
        });
        opIlu.add(opIluRecepcion);

        opIluSala1.setText("Sala1");
        opIluSala1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opIluSala1ActionPerformed(evt);
            }
        });
        opIlu.add(opIluSala1);

        opIluSala2.setText("Sala2");
        opIluSala2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opIluSala2ActionPerformed(evt);
            }
        });
        opIlu.add(opIluSala2);

        opSemantico.add(opIlu);

        jMenu2.setText("Puerta");

        opPuertaRecepcion.setText("Recepcion");
        opPuertaRecepcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opPuertaRecepcionActionPerformed(evt);
            }
        });
        jMenu2.add(opPuertaRecepcion);

        opPuertaSala1.setText("Sala1");
        opPuertaSala1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opPuertaSala1ActionPerformed(evt);
            }
        });
        jMenu2.add(opPuertaSala1);

        opPuertaSala2.setText("Sala2");
        opPuertaSala2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opPuertaSala2ActionPerformed(evt);
            }
        });
        jMenu2.add(opPuertaSala2);

        opSemantico.add(jMenu2);

        opPanel.setText("Panel");

        opPanelPatio.setText("Patio");
        opPanelPatio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opPanelPatioActionPerformed(evt);
            }
        });
        opPanel.add(opPanelPatio);

        opSemantico.add(opPanel);

        opTV.setText("TV");

        opTVRecepcion.setText("Recepcion");
        opTVRecepcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opTVRecepcionActionPerformed(evt);
            }
        });
        opTV.add(opTVRecepcion);

        opSemantico.add(opTV);

        opVentilador.setText("Ventilador");

        opVentiladorRecepcion.setText("Recepcion");
        opVentiladorRecepcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opVentiladorRecepcionActionPerformed(evt);
            }
        });
        opVentilador.add(opVentiladorRecepcion);

        opSemantico.add(opVentilador);

        opAlarma.setText("Alarma");
        opAlarma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opAlarmaActionPerformed(evt);
            }
        });
        opSemantico.add(opAlarma);

        opBanda.setText("Banda");
        opBanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opBandaActionPerformed(evt);
            }
        });
        opSemantico.add(opBanda);

        opCaja.setText("Caja");
        opCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opCajaActionPerformed(evt);
            }
        });
        opSemantico.add(opCaja);

        opCortadora.setText("Cortadora");
        opCortadora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opCortadoraActionPerformed(evt);
            }
        });
        opSemantico.add(opCortadora);

        opciones.add(opSemantico);

        opCodIntermedio.setText("Código intermedio");
        opCodIntermedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opCodIntermedioActionPerformed(evt);
            }
        });
        opciones.add(opCodIntermedio);

        jMenuItem2.setText("Código optimizado");
        opciones.add(jMenuItem2);

        menu.add(opciones);

        setJMenuBar(menu);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void opFuenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opFuenteMouseClicked
        //JFontChooser fc = new JFontChooser();
        //JOptionPane.showMessageDialog(null, fc,"Elegir fuente",JOptionPane.PLAIN_MESSAGE);
        //txtCodigo.setFont(fc.getSelectedFont());
    }//GEN-LAST:event_opFuenteMouseClicked

    private void imgEjecutarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEjecutarMousePressed
        opCompilar.doClick();
        if (codeHasBeenCompiled) {
            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Se encontró un error");
            } else {

            }
        }
    }//GEN-LAST:event_imgEjecutarMousePressed

    private void opFuenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opFuenteActionPerformed
        JFontChooser fc = new JFontChooser();
        JOptionPane.showMessageDialog(null, fc, "Elegir fuente", JOptionPane.PLAIN_MESSAGE);
        txtCodigo.setFont(fc.getSelectedFont());
    }//GEN-LAST:event_opFuenteActionPerformed

    private void imgNuevoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgNuevoMousePressed
        directorio.New();
        clearField();
    }//GEN-LAST:event_imgNuevoMousePressed

    private void imgAbrirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgAbrirMousePressed
        if (directorio.Open()) {
            colorAnalysis();
            clearField();
        }
    }//GEN-LAST:event_imgAbrirMousePressed

    private void imgGuardarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgGuardarMousePressed
        if (directorio.Save()) {
            clearField();
        }
    }//GEN-LAST:event_imgGuardarMousePressed

    private void imgGuardarComoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgGuardarComoMousePressed
        if (directorio.SaveAs()) {
            clearField();
        }
    }//GEN-LAST:event_imgGuardarComoMousePressed

    private void imgCompilarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgCompilarMousePressed
        if (getTitle().contains("*") || getTitle().equals(title)) {
            if (directorio.Save()) {
                compile();
            }
        } else {
            compile();
        }

    }//GEN-LAST:event_imgCompilarMousePressed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped

    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        if (evt.getKeyCode() == 9) {
            int pos = txtCodigo.getCaretPosition();
            txtCodigo.setText(txtCodigo.getText().replaceAll("\t", "    "));
            txtCodigo.setCaretPosition(pos + 4);

        }
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void opTablaTokensDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opTablaTokensDActionPerformed
        if (err != null) {
            err.dispose();
        }
        if (auto != null) {
            auto.dispose();
        }
        if (venErrores != null) {
            venErrores.dispose();
        }

        if (opcGrama != null) {
            opcGrama.dispose();
        }
        fillTablaTokens();
    }//GEN-LAST:event_opTablaTokensDActionPerformed

    private void opNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opNuevoActionPerformed
        directorio.New();
        clearField();
    }//GEN-LAST:event_opNuevoActionPerformed

    private void opAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opAbrirActionPerformed
        if (directorio.Open()) {
            colorAnalysis();
            clearField();
        }
    }//GEN-LAST:event_opAbrirActionPerformed

    private void opGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opGuardarActionPerformed
        if (directorio.Save()) {
            clearField();
        }
    }//GEN-LAST:event_opGuardarActionPerformed

    private void opGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opGuardarComoActionPerformed
        if (directorio.SaveAs()) {
            clearField();
        }
    }//GEN-LAST:event_opGuardarComoActionPerformed

    private void opCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opCompilarActionPerformed
        if (getTitle().contains("*") || getTitle().equals(title)) {
            if (directorio.Save()) {
                compile();
            }
        } else {
            compile();
        }
    }//GEN-LAST:event_opCompilarActionPerformed

    private void opEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opEjecutarActionPerformed
        //imgCompilar.doLayout();
        if (codeHasBeenCompiled) {
            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Se encontró un error");
            } else {

            }
        }
    }//GEN-LAST:event_opEjecutarActionPerformed


    private void txtConsolaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtConsolaMouseReleased
        if (txtConsola.getText().length() != 24) {

            String cadena = errores.get(0);
            String[] parts = null;
            parts = cadena.split("\\[");
            String solucion = "";
            String[] parts2 = parts[1].split("\\]");
            numeroError = Integer.parseInt(parts2[0]);
            if (parts[0].contains("lexico")) {
                solucion = soluciones_lexico[numeroError];
            } else if (parts[0].contains("sintactico")) {
                solucion = soluciones_sintactico[numeroError];
            } else if (parts[0].contains("semantico")) {
                solucion = soluciones_semantico[numeroError];
            }
            venErrores = new VentanaErrores();
            venErrores.setVisible(true);
            venErrores.txtError.setText(errores.get(0) + "\n\n" + solucion);
        }
    }//GEN-LAST:event_txtConsolaMouseReleased

    private void opAutomataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opAutomataActionPerformed
        tokens.clear();
        for (Token token : tokens) {
            ArreToken.add(token.getLexeme());
            ArreNomToken.add(token.getLexicalComp());
        }
        if (err != null) {
            err.dispose();
        }
        if (auto != null) {
            auto.dispose();
        }
        if (venErrores != null) {
            venErrores.dispose();
        }

        if (opcGrama != null) {
            opcGrama.dispose();
        }

        auto = new Automata(ArreToken, ArreNomToken);
        auto.setVisible(true);

    }//GEN-LAST:event_opAutomataActionPerformed

    private void opGramaticaGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opGramaticaGActionPerformed
        for (int i = 0; i < ProCorrectas.size(); i++) {
            for (int j = i + 1; j < ProCorrectas.size(); j++) {
                Production ProdCorrecta_i = ProCorrectas.get(i), ProdCorrecta_j = ProCorrectas.get(j);
                if (ProdCorrecta_i.getLine() > ProdCorrecta_j.getLine()) {
                    ProCorrectas.set(j, ProdCorrecta_i);
                    ProCorrectas.set(i, ProdCorrecta_j);
                }
            }
        }
        for (Production ProdCorrecta : ProCorrectas) {
            ArreCompleto.add("[" + ProdCorrecta.getLine() + ", " + ProdCorrecta.getColumn() + "]    " + ProdCorrecta.getName() + " ---> " + ProdCorrecta.lexicalCompRank(0, -1));
        }
        if (err != null) {
            err.dispose();
        }
        if (auto != null) {
            auto.dispose();
        }
        if (venErrores != null) {
            venErrores.dispose();
        }

        if (opcGrama != null) {
            opcGrama.dispose();
        }

        err = new GramaUsa(ArreCompleto);
        err.setVisible(true);
    }//GEN-LAST:event_opGramaticaGActionPerformed

    private void opGramaticaUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opGramaticaUActionPerformed
//        for (int i = 0; i < ProCorrectas.size(); i++) {
//            for (int j = i + 1; j < ProCorrectas.size(); j++) {
//                Production ProdCorrecta_i = ProCorrectas.get(i), ProdCorrecta_j = ProCorrectas.get(j);
//                if (ProdCorrecta_i.getLine() > ProdCorrecta_j.getLine()) {
//                    ProCorrectas.set(j, ProdCorrecta_i);
//                    ProCorrectas.set(i, ProdCorrecta_j);
//                }
//            }
//        }
//        for (Production ProdCorrecta : ProCorrectas) {
//            ArreCompleto.add(ProdCorrecta.getName());
//        }

        if (err != null) {
            err.dispose();
        }
        if (auto != null) {
            auto.dispose();
        }
        if (venErrores != null) {
            venErrores.dispose();
        }

        if (opcGrama != null) {
            opcGrama.dispose();
        }
//        String cadena = "";
//        for (int i = 0; i <= gramaUsadas.size() - 1; i++) {
//            cadena += gramaUsadas.get(i) + "\n";
//        }

        opcGrama = new OpcionesGrama(gramaUsadas);
        opcGrama.setVisible(true);

    }//GEN-LAST:event_opGramaticaUActionPerformed

    private void opCodIntermedioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opCodIntermedioActionPerformed
        if (err != null) {
            err.dispose();
        }
        if (auto != null) {
            auto.dispose();
        }
        if (venErrores != null) {
            venErrores.dispose();
        }

        if (opcGrama != null) {
            opcGrama.dispose();
        }
        fillTablaCuadruplos();
    }//GEN-LAST:event_opCodIntermedioActionPerformed

    private void opPuertaRecepcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opPuertaRecepcionActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_pRecepcion);
        pila.setVisible(true);
        //this.dispose();
    }//GEN-LAST:event_opPuertaRecepcionActionPerformed

    private void opTVRecepcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opTVRecepcionActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_tvRecepcion);
        pila.setVisible(true);
    }//GEN-LAST:event_opTVRecepcionActionPerformed

    private void opCortadoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opCortadoraActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_Cortador);
        pila.setVisible(true);
    }//GEN-LAST:event_opCortadoraActionPerformed

    private void opAspersorPatioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opAspersorPatioActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_aspPatio);
        pila.setVisible(true);
    }//GEN-LAST:event_opAspersorPatioActionPerformed

    private void opVentiladorRecepcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opVentiladorRecepcionActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_vRecepcion);
        pila.setVisible(true);
    }//GEN-LAST:event_opVentiladorRecepcionActionPerformed

    private void opIluRecepcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opIluRecepcionActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_iluRecepcion);
        pila.setVisible(true);
    }//GEN-LAST:event_opIluRecepcionActionPerformed

    private void opBandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opBandaActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_Banda);
        pila.setVisible(true);
    }//GEN-LAST:event_opBandaActionPerformed

    private void opAlarmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opAlarmaActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_Alarma);
        pila.setVisible(true);
    }//GEN-LAST:event_opAlarmaActionPerformed

    private void opCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opCajaActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_Caja);
        pila.setVisible(true);
    }//GEN-LAST:event_opCajaActionPerformed

    private void opPuertaSala1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opPuertaSala1ActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_pSala1);
        pila.setVisible(true);
    }//GEN-LAST:event_opPuertaSala1ActionPerformed

    private void opIluPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opIluPrincipalActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_iluPrincipal);
        pila.setVisible(true);
    }//GEN-LAST:event_opIluPrincipalActionPerformed

    private void opIluSala1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opIluSala1ActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_iluSala1);
        pila.setVisible(true);
    }//GEN-LAST:event_opIluSala1ActionPerformed

    private void opIluSala2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opIluSala2ActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_iluSala2);
        pila.setVisible(true);
    }//GEN-LAST:event_opIluSala2ActionPerformed

    private void opPuertaSala2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opPuertaSala2ActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_pSala2);
        pila.setVisible(true);
    }//GEN-LAST:event_opPuertaSala2ActionPerformed

    private void opPanelPatioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opPanelPatioActionPerformed
        OperacionesPila pila = new OperacionesPila(ArreFun_panelPatio);
        pila.setVisible(true);
    }//GEN-LAST:event_opPanelPatioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compilador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                new Compilador().setVisible(true);

            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(Compilador.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu archivo;
    private javax.swing.JMenu correr;
    private javax.swing.JMenu editar;
    private javax.swing.JLabel imgAbrir;
    private javax.swing.JLabel imgCompilar;
    private javax.swing.JLabel imgEjecutar;
    private javax.swing.JLabel imgGuardar;
    private javax.swing.JLabel imgGuardarComo;
    private javax.swing.JLabel imgNuevo;
    private javax.swing.JButton jButton1;
    private say.swing.JFontChooser jFontChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenuItem opAbrir;
    private javax.swing.JMenuItem opAlarma;
    private javax.swing.JMenu opAspersor;
    private javax.swing.JMenuItem opAspersorPatio;
    private javax.swing.JMenuItem opAutomata;
    private javax.swing.JMenuItem opBanda;
    private javax.swing.JMenuItem opCaja;
    private javax.swing.JMenuItem opCodIntermedio;
    private javax.swing.JMenuItem opCompilar;
    private javax.swing.JMenuItem opCopiar;
    private javax.swing.JMenuItem opCortadora;
    private javax.swing.JMenuItem opCortar;
    private javax.swing.JMenuItem opEjecutar;
    private javax.swing.JMenuItem opFuente;
    private javax.swing.JMenu opGramatica;
    private javax.swing.JMenuItem opGramaticaG;
    private javax.swing.JMenuItem opGramaticaU;
    private javax.swing.JMenuItem opGuardar;
    private javax.swing.JMenuItem opGuardarComo;
    private javax.swing.JMenu opIlu;
    private javax.swing.JMenuItem opIluPrincipal;
    private javax.swing.JMenuItem opIluRecepcion;
    private javax.swing.JMenuItem opIluSala1;
    private javax.swing.JMenuItem opIluSala2;
    private javax.swing.JMenuItem opNuevo;
    private javax.swing.JMenu opPanel;
    private javax.swing.JMenuItem opPanelPatio;
    private javax.swing.JMenuItem opPegar;
    private javax.swing.JMenuItem opPuertaRecepcion;
    private javax.swing.JMenuItem opPuertaSala1;
    private javax.swing.JMenuItem opPuertaSala2;
    private javax.swing.JMenu opSemantico;
    private javax.swing.JMenu opTV;
    private javax.swing.JMenuItem opTVRecepcion;
    private javax.swing.JMenuItem opTablaTokensD;
    private javax.swing.JMenu opTokens;
    private javax.swing.JMenu opVentilador;
    private javax.swing.JMenuItem opVentiladorRecepcion;
    private javax.swing.JMenu opciones;
    private javax.swing.JPanel pnlBarraHerramientas;
    private javax.swing.JPanel pnlCodigo;
    private javax.swing.JPanel pnlError;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JTextPane txtCodigo;
    private javax.swing.JTextArea txtConsola;
    // End of variables declaration//GEN-END:variables

    private void sintactico() {

        pos = 0;
        ArreToken.clear();
        ArreNomToken.clear();
        ArreFilaColumnaToken.clear();
        errores.clear();
        gramaticas.clear();
        for (Token token : tokens) {
            ArreToken.add(token.getLexeme());
            ArreNomToken.add(token.getLexicalComp());
            ArreFilaColumnaToken.add("[" + token.getLine() + "," + token.getColumn() + "]");
            switch (token.getLexicalComp()) {
                case "Error_1":
                    agregarError("lexico", 1, "[" + token.getLine() + "," + token.getColumn() + "]", "Token ---> " + token.getLexeme());
                    break;
                case "Error_2":
                    agregarError("lexico", 2, "[" + token.getLine() + "," + token.getColumn() + "]", "Token ---> " + token.getLexeme());
                    break;
                default:
            }
        }
        if (err != null) {
            err.dispose();

        }
        if (auto != null) {
            auto.dispose();
        }
        if (venErrores != null) {
            venErrores.dispose();
        }
        if (opcGrama != null) {
            opcGrama.dispose();
        }

        if (txtCodigo.getText().isEmpty()) {
            errores.add("Error [1]: No hay tokens para analizar");
            return;
        }

        if (!inicioCorrecto()) {

            return;
        } else if (errores.size() > 0) {
            for (int i = 0; i <= errores.size() - 1; i++) {
                System.out.println("Error " + i + ": " + errores.get(i));
            }
            System.out.println("xSe encontro un error dentro del codigo");
            gramaticas.add("xEl Token: " + ArreToken.get(pos) + " no es un 'TERMINAL'");
            System.out.println("xEl Token:" + ArreToken.get(pos) + " no es un 'TERMINAL'");

            return;
        }

    }

    private void agregarVariable(String nombre, String valor, String tipo, String filaColumna) {
        System.out.println(ANSI_PURPLE + "Nombre de la varibale: " + nombre + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Valor de la variable: " + valor + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Tipo de la variable: " + tipo + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Fila_Columna de la variable: " + filaColumna + ANSI_RESET);
        ArreVariables.add(new Variables(nombre, valor, tipo, filaColumna));
    }

    private void agregarFuncion(String nombre, String valor, String tipo, String filaColumna) {
        System.out.println(ANSI_PURPLE + "Nombre: " + nombre + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Valor: " + valor + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Tipo: " + tipo + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Fila_Columna: " + filaColumna + ANSI_RESET);
        ArreFunciones_CadenaoVariable.add(new Funciones_CadenaoVariable(nombre, valor, tipo, filaColumna));
    }

    private void agregarFuncionDosParams(String nombre, String valor1, String tipo1, String valor2, String tipo2, String filaColumna) {
        System.out.println(ANSI_PURPLE + "Nombre: " + nombre + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Valor 1: " + valor1 + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Tipo 1: " + tipo1 + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Valor 2: " + valor2 + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Tipo 2: " + tipo2 + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Fila_Columna: " + filaColumna + ANSI_RESET);
    }

    private boolean identificadorCorrecto() {
        if (valorCorrecto(ArreNomToken.get(pos), "Op_Asignacion")) {
            pos++;
            if (hayOtro()) {
                switch (ArreNomToken.get(pos)) {
                    case "Numero":
                        agregarVariable(ArreToken.get(pos - 2), ArreToken.get(pos), ArreNomToken.get(pos), ArreFilaColumnaToken.get(pos - 1));
                        valorCorrecto(ArreNomToken.get(pos), "Numero");
                        pos++;
                        if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Punto_Coma")) {
                            pos++;
                            return true;
                        } else {
                            agregarError("sintactico", 4, ArreFilaColumnaToken.get(pos - 1), "");
                        }

                        break;
                    case "Cadena":
                        agregarVariable(ArreToken.get(pos - 2), ArreToken.get(pos), ArreNomToken.get(pos), ArreFilaColumnaToken.get(pos - 1));
                        valorCorrecto(ArreNomToken.get(pos), "Cadena");
                        pos++;
                        if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Punto_Coma")) {
                            pos++;
                            return true;
                        } else {
                            agregarError("sintactico", 4, ArreFilaColumnaToken.get(pos - 1), "");
                        }

                        break;
                    default:
                        agregarError("sintactico", 3, ArreFilaColumnaToken.get(pos - 1), "");
                }
            } else {
                agregarError("sintactico", 3, ArreFilaColumnaToken.get(pos - 1), "");
            }

        } else {
            agregarError("sintactico", 2, ArreFilaColumnaToken.get(pos - 1), "");
        }
        return false;
    }

    private boolean hayOtro() {
        if (ArreNomToken.size() > pos) {
            return true;
        } else {
            return false;
        }
    }

    private boolean forCorrecto() {
        if (valorCorrecto(ArreNomToken.get(pos), "Parentesis_A")) {
            pos++;
            if (hayOtro() && (valorCorrecto(ArreNomToken.get(pos), "Numero") || valorCorrecto(ArreNomToken.get(pos), "Identificador"))) {
                pos++;
                if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Parentesis_C")) {
                    pos++;
                    if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Llave_A")) {
                        pos++;
                        agregarGramaticaUsada();
                        boolean test;
                        do {
                            System.out.println("Analizando el codigo dentro de estructura for");
                            test = operacionCorrecta();
                            if (errores.size() > 0) {
                                System.out.println("Se encontro un error dentro del codigo en el for");
                                return false;
                            }
                        } while (test);
                        System.out.println("Saliendo de analizar el codigo dentro de la estructura de for");

                        if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Llave_C")) {
                            pos++;
                            return true;
                        } // }
                        else {
                            agregarError("sintactico", 11, ArreFilaColumnaToken.get(pos - 1), "");
                        } // }
                    } // {
                    else {
                        agregarError("sintactico", 10, ArreFilaColumnaToken.get(pos - 1), "");
                    } // {
                }// )
                else {
                    agregarError("sintactico", 9, ArreFilaColumnaToken.get(pos - 1), "");
                }// )
            }//Es valor o identificador
            else {
                agregarError("sintactico", 8, ArreFilaColumnaToken.get(pos - 1), "");
            }//No es valor o identificador
        } else {
            agregarError("sintactico", 7, ArreFilaColumnaToken.get(pos - 1), "");
        }
        return false;
    }

    private boolean operacionCorrecta() {
        if (hayOtro()) {
            switch (ArreNomToken.get(pos)) {
                /* Variables */
                case "Identificador":
                    valorCorrecto(ArreNomToken.get(pos), "Identificador");
                    pos++;
                    if (hayOtro()) {
                        if (identificadorCorrecto()) {
                            agregarGramaticaUsada();
                            return true;
                        }
                    }//Hay otro
                    else {
                        agregarError("sintactico", 2, ArreFilaColumnaToken.get(pos - 1), "");
                    }
                    break;
                /* Variables */
                case "def":
                    valorCorrecto(ArreNomToken.get(pos), "def");
                    pos++;
                    if (hayOtro()) {
                        if (controlCorrecto()) {
                            agregarGramaticaUsada();
                            return true;
                        }
                    }//Hay otro
                    else {
                        agregarError("sintactico", 43, ArreFilaColumnaToken.get(pos - 1), "");
                    }
                    break;
                case "For":
                    valorCorrecto(ArreNomToken.get(pos), "For");
                    pos++;
                    if (hayOtro()) {
                        if (forCorrecto()) {
                            agregarGramaticaUsada();
                            //gramaticas.add("For correcto \n");
                            return true;
                        }

                    } else {
                        agregarError("sintactico", 7, ArreFilaColumnaToken.get(pos - 1), "");
                    }
                    break;
                /* If */
                case "If":
                    valorCorrecto(ArreNomToken.get(pos), "If");
                    pos++;
                    if (hayOtro()) {
                        if (ifCorrecta()) {
                            agregarGramaticaUsada();
                            return true;
                        }

                    } else {
                        agregarError("sintactico", 27, ArreFilaColumnaToken.get(pos - 1), "");
                    }
                    break;
                default:

            }
            if (buscarFunciones()) {
                return true;
            }
        } else {
            agregarError("sintactico", 39, ArreFilaColumnaToken.get(pos - 1), "");
        }
        return false;
    }

    private boolean buscarFunciones() {

        String funciones[] = {"OP_Cortadora", "OP_Aspersor", "OP_Ventilador", "OP_Iluminacion", "OP_Puerta", "OP_Banda", "OP_TV", "OP_Alarma", "OP_Caja", "OP_Panel"};
        String erroresFun[] = {""};
        for (int i = 0; i <= funciones.length - 1; i++) {
            if (hayOtro() && (valorCorrecto(ArreNomToken.get(pos), funciones[i]))) {
                //Si encontró una funcion
                pos++;

                if (hayOtro()) {
                    if (funcionCorrecta()) {
                        agregarGramaticaUsada();
                        return true;
                    } else {
                        System.out.println("XXXXXXXXX");
                    }
                } else {
                    agregarError("sintactico", 22, ArreFilaColumnaToken.get(pos - 1), "");

                }
            }
            if (errores.size() > 0) {
                return false;
            }
        }

        return false;
    }

    private boolean valorCorrecto(String nomToken, String valor) {
        if (nomToken.equals(valor)) {
            System.out.println(valor + " encontrado");
            gramaticas.add(valor);
            return true;
        } else {
            return false;
        }

    }

    private void agregarError(String tipo, int numeroError, String filaColumna, String extra) {
        String error = "";
        switch (tipo) {
            case "lexico":
                error = errores_lexicos[numeroError] + filaColumna + "\n" + extra;
                errores.add(error);
                break;
            case "sintactico":
                error = errores_sintacticos[numeroError] + filaColumna + "\n" + extra;
                errores.add(error);
                break;
            case "semantico":
                error = errores_semanticos[numeroError] + filaColumna + "\n" + extra;
                errores.add(error);
                break;
            default:
                throw new AssertionError();
        }
    }

    private boolean inicioCorrecto() {
        if (valorCorrecto(ArreNomToken.get(pos), "INICIO")) {
            pos++;
            if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Identificador")) {
                pos++;
                if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Llave_A")) {
                    pos++;
                    if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "PRINCIPAL")) {
                        pos++;
                        if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Parentesis_A")) {
                            pos++;
                            if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Parentesis_C")) {
                                pos++;
                                if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Llave_A")) {
                                    pos++;
                                    agregarGramaticaUsada();
                                    if (hayOtro()) {
                                        boolean test;
                                        do {
                                            System.out.println(ANSI_YELLOW + "Analizando el codigo dentro de estructura inicial" + ANSI_RESET);
                                            test = operacionCorrecta();

                                            if (errores.size() > 0) {
                                                System.out.println("Se encontro un error dentro del codigo");
                                                return false;
                                            }
                                        } while (test);
                                        System.out.println("Saliendo de analizar el codigo dentro de la estructura de inicio");
                                        /* Continua con el bloque de inicio */
                                        if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Llave_C")) {
                                            pos++;
                                            if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Llave_C")) {
                                                pos++;
                                                if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "FINAL")) {
                                                    pos++;
                                                    if (ArreNomToken.size() > pos) {
                                                        agregarError("sintactico", 20, ArreFilaColumnaToken.get(pos - 1), "");
                                                    } else {
                                                        System.out.println("Todo el código es correcto");
                                                        agregarGramaticaUsada();
                                                        return true;
                                                    }
                                                } // FINAL
                                                else {
                                                    agregarError("sintactico", 19, ArreFilaColumnaToken.get(pos - 1), "");
                                                } // FINAL

                                            } // } FINAL
                                            else {
                                                agregarError("sintactico", 18, ArreFilaColumnaToken.get(pos - 1), "");
                                            } // } FINAL
                                        } // } PRINCIPAL
                                        else {
                                            agregarError("sintactico", 39, ArreFilaColumnaToken.get(pos - 1), "");
                                        } // } PRINCIPAL
                                    }//hay otro
                                    else {
                                        agregarError("sintactico", 21, ArreFilaColumnaToken.get(pos - 1), "");
                                    }
                                } // { PRINCIPAL
                                else {
                                    agregarError("sintactico", 42, ArreFilaColumnaToken.get(pos - 1), "");
                                } // { PRINCIPAL
                            } // )
                            else {
                                agregarError("sintactico", 41, ArreFilaColumnaToken.get(pos - 1), "");
                            } // )
                        } // (
                        else {
                            agregarError("sintactico", 40, ArreFilaColumnaToken.get(pos - 1), "");
                        } // (
                    } // PRINCIPAL
                    else {
                        agregarError("sintactico", 15, ArreFilaColumnaToken.get(pos - 1), "");
                    } // PRINCIPAL
                } // {
                else {
                    agregarError("sintactico", 14, ArreFilaColumnaToken.get(pos - 1), "");
                } // {
            }//Identificador
            else {
                agregarError("sintactico", 13, ArreFilaColumnaToken.get(pos - 1), "");
            }
        } // INICIO
        else {
            agregarError("sintactico", 12, ArreFilaColumnaToken.get(pos), "");
        } // INICIO
        System.out.println("Entrando al return false");
        return false;
    }

    private boolean funcionCorrecta() {
        if (valorCorrecto(ArreNomToken.get(pos), "Parentesis_A")) {
            pos++;
            /* Verifica parametros */

            if (hayOtro()) {
                if (ArreToken.get(pos - 2).equals("alarma_activar") || ArreToken.get(pos - 2).equals("alarma_desactivar")) {
                    System.out.println(ANSI_CYAN + "Evaluando: " + ArreToken.get(pos - 2) + ANSI_RESET);
                    fncCoV_NoVNombre = ArreToken.get(pos - 2);
                    if (agregaPila(ArreToken.get(pos - 2), ArreToken.get(pos), "")) {
                        //System.out.println(ANSI_RED+" Error semantico "+ANSI_RESET);
                        //JOptionPane.showMessageDialog(null, "Error");
                    } else {
                        //System.out.println(ANSI_GREEN+" Good semantico "+ANSI_RESET);
                        //JOptionPane.showMessageDialog(null, ArreToken.get(pos - 2)+" - "+ArreToken.get(pos));
                    }
                    if (finFuncion()) {
                        return true;
                    }
                } else if (ArreToken.get(pos - 2).equals("cortadora_activar") || ArreToken.get(pos - 2).equals("cortadora_desactivar")
                        || ArreToken.get(pos - 2).equals("ventilador_activar") || ArreToken.get(pos - 2).equals("ventilador_desactivar")
                        || ArreToken.get(pos - 2).equals("iluminacion_activar") || ArreToken.get(pos - 2).equals("iluminacion_desactivar")
                        || ArreToken.get(pos - 2).equals("banda_activar") || ArreToken.get(pos - 2).equals("banda_desactivar")
                        || ArreToken.get(pos - 2).equals("puerta_abrir") || ArreToken.get(pos - 2).equals("puerta_cerrar")
                        || ArreToken.get(pos - 2).equals("tv_encender") || ArreToken.get(pos - 2).equals("tv_apagar")
                        || ArreToken.get(pos - 2).equals("cajafuerte_desactivar") || ArreToken.get(pos - 2).equals("aspersor_desactivar")
                        || ArreToken.get(pos - 2).equals("panel_encender") || ArreToken.get(pos - 2).equals("panel_apagar")
                        || ArreToken.get(pos - 2).equals("aspersor_activar") || ArreToken.get(pos - 2).equals("cajafuerte_activar")) {
                    fncCoV_NoVNombre = ArreToken.get(pos - 2);
                    if (agregaPila(ArreToken.get(pos - 2), ArreToken.get(pos), "")) {
                        //System.out.println(ANSI_RED+" Error semantico "+ANSI_RESET);
                        //JOptionPane.showMessageDialog(null, "Error");
                    } else {
                        //System.out.println(ANSI_GREEN+" Good semantico "+ANSI_RESET);
                        //JOptionPane.showMessageDialog(null, ArreToken.get(pos - 2)+" - "+ArreToken.get(pos));
                    }
                    if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "CONTROL")) {
                        agregarFuncion(ArreToken.get(pos - 2), ArreToken.get(pos), ArreNomToken.get(pos), ArreFilaColumnaToken.get(pos - 1));
                        pos++;
                        if (finFuncion()) {
                            return true;
                        }
                    } else {
                        agregarError("sintactico", 5, ArreFilaColumnaToken.get(pos - 1), "Funcion ---> " + fncCoV_NoVNombre);
                    }

                }//If varias funciones
                else if (ArreToken.get(pos - 2).equals("panel_girar")) {
                    fncCoV_NoVNombre = ArreToken.get(pos - 2);
                    String var1 = "";
                    if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "CONTROL")) {
                        var1 = ArreToken.get(pos);
                        fncCoV_NoVValor_CoV = ArreToken.get(pos);
                        fncCoV_NoVTipo_CoV = "CONTROL";
                        pos++;
                        if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Coma")) {
                            pos++;

                            if (hayOtro()) {
                                switch (ArreNomToken.get(pos)) {
                                    case "Numero":
                                        if (fncCoV_NoVNombre.equals("panel_girar")) {
                                            int valor = Integer.parseInt(ArreToken.get(pos));

                                            if (valor < 0 || valor > 180) {
                                                agregarError("semantico", 27, ArreFilaColumnaToken.get(pos - 1), "Valor ---> " + valor);
//                                                return false;
                                            }
                                        }
                                        valorCorrecto(ArreNomToken.get(pos), "Numero");
                                        agregarFuncionDosParams(fncCoV_NoVNombre, fncCoV_NoVValor_CoV, fncCoV_NoVTipo_CoV, ArreToken.get(pos), ArreNomToken.get(pos), ArreFilaColumnaToken.get(pos - 1));
                                        break;
                                    case "Identificador":
                                        valorCorrecto(ArreNomToken.get(pos), "Identificador");
                                        agregarFuncionDosParams(fncCoV_NoVNombre, fncCoV_NoVValor_CoV, fncCoV_NoVTipo_CoV, ArreToken.get(pos), ArreNomToken.get(pos), ArreFilaColumnaToken.get(pos - 1));
                                        break;
                                    default:
                                        agregarError("sintactico", 6, ArreFilaColumnaToken.get(pos - 1), "Funcion ---> " + fncCoV_NoVNombre);
                                }
                                String var2 = ArreToken.get(pos);
                                if (agregaPila(fncCoV_NoVNombre, var1, var2)) {
                                    //System.out.println(ANSI_RED+" Error semantico "+ANSI_RESET);
                                    //JOptionPane.showMessageDialog(null, "Error");
                                } else {
                                    //System.out.println(ANSI_GREEN+" Good semantico "+ANSI_RESET);
                                    //JOptionPane.showMessageDialog(null, ArreToken.get(pos - 2)+" - "+ArreToken.get(pos));
                                }
                                pos++;
                                if (hayOtro()) {
                                    if (finFuncion()) {
                                        return true;
                                    }

                                }//Hay otro
                                else {
                                    agregarError("sintactico", 23, ArreFilaColumnaToken.get(pos - 1), "Funcion ---> " + ArreToken.get(pos - 1));
                                }
                            }//Hay otro
                            else {
                                agregarError("sintactico", 6, ArreFilaColumnaToken.get(pos - 1), "Funcion ---> " + ArreToken.get(pos - 1));
                            }

                        } // Coma
                        else {
                            agregarError("sintactico", 26, ArreFilaColumnaToken.get(pos - 1), "Funcion ---> " + ArreToken.get(pos - 1));
                        } // Coma
                    } else {
                        agregarError("sintactico", 5, ArreFilaColumnaToken.get(pos - 1), "Funcion ---> " + fncCoV_NoVNombre);
                    }

                }
//                else {
//                    if (ArreToken.get(pos - 2).equals("alarma_activar") || ArreToken.get(pos - 2).equals("alarma_desactivar")) {
//                        agregarError("sintactico", 23, ArreFilaColumnaToken.get(pos - 2), "Funcion ---> " + ArreToken.get(pos - 2));
//                    } else if (ArreToken.get(pos - 2).equals("panel_girar")) {
//                        agregarError("sintactico", 6, ArreFilaColumnaToken.get(pos - 2), "Funcion ---> " + ArreToken.get(pos - 2));
//                    } else {
//                        agregarError("sintactico", 5, ArreFilaColumnaToken.get(pos - 2), "Funcion ---> " + ArreToken.get(pos - 2));
//                    }
//
//                }
            } //No hay otro valor
            else {
                agregarError("sintactico", 5, ArreFilaColumnaToken.get(pos - 1), "Funcion ---> " + ArreToken.get(pos - 1));
            }

        } else {
            agregarError("sintactico", 22, ArreFilaColumnaToken.get(pos - 1), "Funcion ---> " + ArreToken.get(pos - 1));
        }
        return false;
    }

    private boolean valorCorrecto() {
        switch (ArreNomToken.get(pos)) {
            case "Numero":
                valorCorrecto(ArreNomToken.get(pos), "Numero");
                pos++;

                return true;
            case "Cadena":
                valorCorrecto(ArreNomToken.get(pos), "Cadena");
                pos++;

                return true;
            default:

        }//Switch

        return false;
    }

    private boolean finFuncion() {
        if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Parentesis_C")) {
            pos++;
            if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Punto_Coma")) {
                pos++;
                return true;
                //Es correcto
            } else {
                agregarError("sintactico", 24, ArreFilaColumnaToken.get(pos - 1), "Funcion ---> " + fncCoV_NoVNombre);
            }
        } else {
            agregarError("sintactico", 23, ArreFilaColumnaToken.get(pos - 1), "Funcion ---> " + fncCoV_NoVNombre);
        }
        return false;
    }

    private boolean ifCorrecta() {
        boolean correcto = true;
        if (valorCorrecto(ArreNomToken.get(pos), "Parentesis_A")) {
            pos++;
            Variables var1 = new Variables("var1", "var1", "var1", "var1");
            Variables var2 = new Variables("var2", "var2", "var2", "var2");
            if (hayOtro() && (valorCorrecto() || valorCorrecto(ArreNomToken.get(pos++), "Identificador"))) {
                if (ArreNomToken.get(pos - 1).equals("Identificador")) {
                    var1 = identificadorDeclarado(ArreToken.get(pos - 1));
                    if (var1.nombre().equals("XXX")) {
                        agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + ArreToken.get(pos - 1));
                        return false;
                    }
                } else if (ArreNomToken.get(pos - 1).equals("Numero") || ArreNomToken.get(pos - 1).equals("Cadena")) {
                    var1.setTipo(ArreNomToken.get(pos - 1));
                    var1.setValor(ArreToken.get(pos - 1));
                }
                if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Op_Relacional")) {
                    String operador = ArreToken.get(pos);
                    pos++;
                    if (hayOtro() && (valorCorrecto() || valorCorrecto(ArreNomToken.get(pos++), "Identificador"))) {
                        if (ArreNomToken.get(pos - 1).equals("Identificador")) {
                            var2 = identificadorDeclarado(ArreToken.get(pos - 1));
                            if (var2.nombre().equals("XXX")) {
                                agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + ArreToken.get(pos - 1));
                                return false;
                            }
                        } else if (ArreNomToken.get(pos - 1).equals("Numero") || ArreNomToken.get(pos - 1).equals("Cadena")) {
                            var2.setTipo(ArreNomToken.get(pos - 1));
                            var2.setValor(ArreToken.get(pos - 1));
                        }
                        if (!tiposIguales(var1, var2)) {
                            agregarError("semantico", 28, ArreFilaColumnaToken.get(pos - 1), "Tipo 1 ---> " + var1.tipo() + "\nTipo 2 ---> " + var2.tipo());
                            return false;
                        } else { //SI SON IGUALES

                            switch (var1.tipo()) {
                                case "Cadena":
                                    if (operador.equals("==") || operador.equals("!=")) {
                                    } else {
                                        agregarError("semantico", 29, ArreFilaColumnaToken.get(pos - 1), "Operacion no permitida entre cadenas");
                                        return false;
                                    }
                                    break;
                                default:
                            }
                            switch (operador) {
                                case ">":
                                    if (Integer.parseInt(var1.valor()) > Integer.parseInt(var2.valor())) {
                                    } else {
                                        correcto = false;
                                    }
                                    break;
                                case "<":
                                    if (Integer.parseInt(var1.valor()) < Integer.parseInt(var2.valor())) {
                                    } else {
                                        correcto = false;
                                    }
                                    break;
                                case ">=":
                                    if (Integer.parseInt(var1.valor()) >= Integer.parseInt(var2.valor())) {
                                    } else {
                                        correcto = false;
                                    }
                                    break;
                                case "<=":
                                    if (Integer.parseInt(var1.valor()) <= Integer.parseInt(var2.valor())) {
                                    } else {
                                        correcto = false;
                                    }
                                    break;
                                case "==":

                                    if (var1.tipo().equals("Numero") && (Integer.parseInt(var1.valor()) == Integer.parseInt(var2.valor()))) {
                                    } else if (var1.tipo().equals("Cadena") && (var1.valor().equals(var2.valor()))) {
                                    } else {
                                        correcto = false;
                                    }
                                    break;
                                case "!=":
                                    if (var1.tipo().equals("Numero") && (Integer.parseInt(var1.valor()) != Integer.parseInt(var2.valor()))) {
                                    } else if (var1.tipo().equals("Cadena") && (!var1.valor().equals(var2.valor()))) {
                                    } else {
                                        correcto = false;
                                    }
                                    break;
                                default:
                            }
                        }

                        if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Parentesis_C")) {
                            pos++;

                            if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Llave_A")) {
                                pos++;
                                agregarGramaticaUsada();
                                if (correcto) {
                                    boolean test;
                                    do {
                                        if (!hayOtro()) {
                                            agregarError("sintactico", 32, ArreFilaColumnaToken.get(pos - 1), "");
                                            return false;
                                        }
                                        System.out.println("Analiznado el codigo dentro de estructura if");
                                        test = operacionCorrecta();
                                        if (errores.size() > 0) {
                                            System.out.println("Se encontro un error dentro del codigo en el if");
                                            return false;
                                        }
                                    } while (test);
                                    System.out.println("Saliendo de analizar el codigo dentro de la estructura de if");

                                } else {
                                    boolean nivelado = true;
                                    do {
                                        String par = "no";
                                        if (ArreNomToken.get(pos++).equals("Llave_A")) {

                                            do {
                                                if (ArreNomToken.get(pos).equals("Llave_C")) {
                                                    par = "si";
                                                } else {
                                                    pos++;
                                                }
                                            } while (par.equals("no"));
                                        } else if (ArreNomToken.get(pos).equals("Llave_C") && par.equals("no")) {
                                            nivelado = false;
                                        } else {
                                        }
                                    } while (nivelado);
                                }
                                if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Llave_C")) {
                                    pos++;
                                    return true;
                                } // }
                                else {
                                    agregarError("sintactico", 32, ArreFilaColumnaToken.get(pos - 1), "");
                                } // }

                            } // {
                            else {
                                agregarError("sintactico", 31, ArreFilaColumnaToken.get(pos - 1), "");
                            } // } 
                        } // )
                        else {
                            agregarError("sintactico", 30, ArreFilaColumnaToken.get(pos - 1), "");
                        } // 
                    } // valor o identificador
                    else {
                        agregarError("sintactico", 28, ArreFilaColumnaToken.get(pos - 1), "");
                    } // valor o identificador
                } // Operador relacional
                else {
                    agregarError("sintactico", 29, ArreFilaColumnaToken.get(pos - 1), "");
                } // Operador relacional

            } // valor o identificador correcto
            else {
                agregarError("sintactico", 28, ArreFilaColumnaToken.get(pos - 1), "");
            } // valor o identificador correcto
        } //(
        else {
            System.out.println("----------->");
            agregarError("sintactico", 27, ArreFilaColumnaToken.get(pos - 1), "");
        }
        return false;
    }

    private boolean sema_variables_tipoDato() {
        //EDITANDO
        for (int i = 0; i < ArreVariables.size(); i++) {
            for (int k = i + 1; k < ArreVariables.size(); k++) {
                //System.out.println("");
                //System.out.println("Nom i -> " + ArreVariables.get(i).nombre() +" Valor i -> " + ArreVariables.get(i).valor()+ " Tipo -> "+ ArreVariables.get(i).tipo()+ " Fila_Columna -> " + ArreVariables.get(i).fila_columna());
                //System.out.println("Nom k -> " + ArreVariables.get(k).nombre() +" Valor k -> " + ArreVariables.get(k).valor()+ " Tipo -> "+ ArreVariables.get(k).tipo()+ " Fila_Columna -> " + ArreVariables.get(k).fila_columna());
                if (ArreVariables.get(i).nombre().equals(ArreVariables.get(k).nombre())
                        && !ArreVariables.get(i).tipo().equals(ArreVariables.get(k).tipo())) {

                    System.out.println(ANSI_RED + errores_semanticos[2] + ArreVariables.get(i).fila_columna() + "\nVariable -> " + ArreVariables.get(k).nombre() + ArreVariables.get(k).fila_columna() + ANSI_RESET);
                    errores.add(errores_semanticos[2] + " " + ArreVariables.get(i).fila_columna() + "\nVariable -> " + ArreVariables.get(k).nombre() + " " + ArreVariables.get(k).fila_columna());

                    return true; //Si hay variables repetidas
                }
            }
        }
        return false; //Sin cambios de datos
    }

    private boolean sema_fncCoV_varNoDeclarada() {
        boolean resp = true;
        String nvfnc = "";
        String vvfnc = "";
        String fcvfnc = "";
        String nv = "";
        String fcv = "";

        for (int i = 0; i < ArreFunciones_CadenaoVariable.size(); i++) {
            System.out.println("No. Vars " + ArreVariables.size());
            nvfnc = ArreFunciones_CadenaoVariable.get(i).nombre(); //Nombre de la función
            vvfnc = ArreFunciones_CadenaoVariable.get(i).valor();  //Nombre de la variable escrita en la funcion
            fcvfnc = ArreFunciones_CadenaoVariable.get(i).fila_columna(); //Fila y columna de la funcion

            if (ArreVariables.size() > 0) {

                for (int k = 0; k < ArreVariables.size(); k++) {
                    if ((ArreFunciones_CadenaoVariable.get(i).fila() > ArreVariables.get(k).fila())) {
                        System.out.println("La fila de la fnc si es mayor Procediendo...");
                        System.out.println("");
                        System.out.println("Nom fnc i -> " + ArreFunciones_CadenaoVariable.get(i).nombre() + " Valor fnc i -> " + ArreFunciones_CadenaoVariable.get(i).valor() + /*" Tipo -> "+ ArreVariables.get(i).tipo()+ */ " Fila_Columna -> " + ArreFunciones_CadenaoVariable.get(i).fila_columna());
                        System.out.println("Nom variable k -> " + ArreVariables.get(k).nombre() +/* " Valor k -> " + ArreVariables.get(k).valor()+ " Tipo -> "+ ArreVariables.get(k).tipo()+ */ " Fila_Columna -> " + ArreVariables.get(k).fila_columna());
                        nv = ArreVariables.get(k).nombre(); //Nombre de la variable
                        fcv = ArreVariables.get(k).fila_columna(); //Fila y columna donde está declarada la variable
                        System.out.println("Fila fnc -> " + ArreFunciones_CadenaoVariable.get(i).fila());
                        System.out.println("Fila variable -> " + ArreVariables.get(k).fila());

                        if (ArreFunciones_CadenaoVariable.get(i).tipo().equals("Identificador")) {
                            System.out.println("Si es un identificador Procediendo...");
                            if ((!ArreFunciones_CadenaoVariable.get(i).valor().equals(ArreVariables.get(k).nombre()))) {
                                System.out.println(ANSI_RED + "Variables diferentes detectadas" + ANSI_RESET);
                                resp = true; //No está declarada la variable
//                                if ((ArreFunciones_CadenaoVariable.get(i).fila() < ArreVariables.get(k).fila())) {
//                                    System.out.println(ANSI_RED + "Se detectó que una función no tiene ninguna variable declarada \nMatando proceso" + ANSI_RESET);
//                                    System.out.println(ANSI_RED + errores_semanticos[3] + fcvfnc + "\nVariable fnc -> " + vvfnc + ANSI_RESET);
//                                    errores.add(errores_semanticos[3] + " " + fcvfnc + "\nVariable fnc -> " + vvfnc);
//                                    return true;
//                                }
                            } else {
                                //Ambas variables son igual
                                System.out.println("Ambas variables son iguales Procediendo...");
                                resp = false;
                                k = ArreVariables.size() - 1;
                            }

                        }//if identificador
                        else {
                            System.out.println("Es cadena Procediendo...");
                            resp = false;
                            k = ArreVariables.size() - 1;
                        }
                    } else {
                        //La fila de la funcion es menor a la de la variable a evaluar
                        System.out.println(ANSI_RED + "Se detectó que una función no tiene ninguna variable declarada \nMatando proceso" + ANSI_RESET);
                        System.out.println(ANSI_RED + errores_semanticos[3] + fcvfnc + "\nVariable fnc -> " + vvfnc + ANSI_RESET);
                        errores.add(errores_semanticos[3] + " " + fcvfnc + "\nVariable fnc -> " + vvfnc);
                        return true;
                    }

                } //For k
                if (resp) {
                    System.out.println("");
                    System.out.println(ANSI_RED + "Se detectó que una función no tiene ninguna variable declarada \nMatando proceso" + ANSI_RESET);
                    System.out.println(ANSI_RED + errores_semanticos[3] + fcvfnc + "\nVariable fnc -> " + vvfnc + ANSI_RESET);
                    errores.add(errores_semanticos[3] + " " + fcvfnc + "\nVariable fnc -> " + vvfnc);
                    return true;
                }

            } else if (ArreFunciones_CadenaoVariable.get(i).tipo().equals("Cadena")) {
                resp = false;
                System.out.println("Evaluando: " + ArreFunciones_CadenaoVariable.get(i).valor());
                //i = ArreFunciones_CadenaoVariable.size();
            } else {
                resp = true; //No hay variables definidas en las funciones pero si hay declaradas en el arreglo
                i = ArreFunciones_CadenaoVariable.size();
            }
        }

        if (resp) {
            System.out.println(ANSI_RED + errores_semanticos[3] + fcvfnc + "\nVariable fnc -> " + vvfnc + ANSI_RESET);
            errores.add(errores_semanticos[3] + " " + fcvfnc + "\nVariable fnc -> " + vvfnc);
        }
        return resp; //Todas las variables se encuentran declaradas
    }

    private void sema_asignaFilas() {
        for (int i = 0; i < ArreFunciones_CadenaoVariable.size(); i++) {
            String fcfnc_1[] = ArreFunciones_CadenaoVariable.get(i).fila_columna().split("\\[");
            String fcfnc_2[] = {};
            for (int j = 1; j < fcfnc_1.length; j = j + 2) {
                fcfnc_2 = fcfnc_1[j].split(",");
            }
            //System.out.println("Fila de la funcion agregada -> " + fcfnc_2[0]);
            ArreFunciones_CadenaoVariable.get(i).setFila(Integer.parseInt(fcfnc_2[0]));
            ArreFilaFnc.add(Integer.parseInt(fcfnc_2[0]));
        }
        for (int i = 0; i < ArreFunciones_CoV_NoV.size(); i++) {
            String fcvar_1[] = ArreFunciones_CoV_NoV.get(i).fila_columna().split("\\[");
            String fcvar_2[] = {};
            for (int j = 1; j < fcvar_1.length; j = j + 2) {
                fcvar_2 = fcvar_1[j].split(",");
            }
            //System.out.println("Fila de la Funcion CoV_NoV agregada -> " + fcvar_2[0]);
            ArreFunciones_CoV_NoV.get(i).setFila(Integer.parseInt(fcvar_2[0]));
            ArreFilaVar.add(Integer.parseInt(fcvar_2[0]));
        }
        for (int i = 0; i < ArreVariables.size(); i++) {
            String fcvar_1[] = ArreVariables.get(i).fila_columna().split("\\[");
            String fcvar_2[] = {};
            for (int j = 1; j < fcvar_1.length; j = j + 2) {
                fcvar_2 = fcvar_1[j].split(",");
            }
            //System.out.println("Fila de la variable agregada -> " + fcvar_2[0]);
            ArreVariables.get(i).setFila(Integer.parseInt(fcvar_2[0]));
            ArreFilaVar.add(Integer.parseInt(fcvar_2[0]));
        }

    }

    private boolean sema_fncCoV_tipoIncorrecto() {
        boolean resp = true; //Si hay tipos incorrectos
        String nvfnc = "";
        String vvfnc = "";
        String fcvfnc = "";
        String nv = "";
        String fcv = "";
        for (int i = 0; i < ArreFunciones_CadenaoVariable.size(); i++) {
            if (ArreVariables.size() > 0) {
                Funciones_CadenaoVariable funCoV = ArreFunciones_CadenaoVariable.get(i);
                nvfnc = funCoV.nombre();
                fcvfnc = funCoV.fila_columna();
                for (int j = 0; j < ArreVariables.size(); j++) {
                    Variables vars = ArreVariables.get(j);
                    nv = vars.nombre();
                    fcv = vars.fila_columna();
                    if (funCoV.valor().equals(vars.nombre())) {
                        if (vars.tipo().equals("Numero")) {
                            System.out.println("Variable declarada como numero, error detectado");
                            resp = true; //Si hay tipos incorrectos
                            i = ArreFunciones_CadenaoVariable.size();
                        } else {
                            resp = false;
                        }
                    }
                }
            } else {
                return false;
            }

        }
        if (resp) {
            System.out.println(ANSI_RED + errores_semanticos[4] + "\nFuncion -> " + nvfnc + " " + fcvfnc + "\nVariable -> " + nv + " " + fcv + ANSI_RESET);
            errores.add(errores_semanticos[4] + "\nFuncion -> " + nvfnc + " " + fcvfnc + "\nVariable -> " + nv + " " + fcv);

        }

        return resp;
    }

    private boolean sema_fncCoV_NoV_varNoDeclaradas() {
        boolean resp = true;
        boolean r = false;
        String nvfnc = "";
        String vvfncCoV = "";
        String vvfncNoV = "";
        String fcvfnc = "";
        String nv = "";
        String fcv = "";

        for (int i = 0; i < ArreFunciones_CoV_NoV.size(); i++) {
            Funciones_CoV_NoV funcion = ArreFunciones_CoV_NoV.get(i);
            nvfnc = funcion.nombre(); //Nombre de la función
            vvfncCoV = funcion.valor_CoV();  //Nombre de la variable escrita en la funcion
            vvfncNoV = funcion.valor_NoV();
            fcvfnc = funcion.fila_columna(); //Fila y columna de la funcion
//            System.out.println("vvfncCoV: " + vvfncCoV);
//            System.out.println("vvfncNoV: " + vvfncNoV);
//            System.out.println("No. variables: " + ArreVariables.size());
            if (ArreVariables.size() > 0) {

                for (int k = 0; k < ArreVariables.size(); k++) {
                    Variables variable = ArreVariables.get(k);
                    //Verifica si la variable de la funcion está declarada antes de llamarla
//                    System.out.println("");
//                    System.out.println("Nom fnc i -> " + funcion.nombre() + " Valor CoV fnc i -> " + funcion.valor_CoV() + " Valor NoV fnc i ->" + funcion.valor_NoV() + /*" Tipo -> "+ ArreVariables.get(i).tipo()+ */ " Fila_Columna -> " + funcion.fila_columna());
//                    System.out.println("Nom variable k -> " + variable.nombre() +/* " Valor k -> " + ArreVariables.get(k).valor()+ " Tipo -> "+ ArreVariables.get(k).tipo()+ */ " Fila_Columna -> " + variable.fila_columna());

                    if (funcion.fila() > variable.fila()) {
                        nv = variable.nombre(); //Nombre de la variable
                        fcv = variable.fila_columna(); //Fila y columna donde está declarada la variable

                        if (funcion.tipo_CoV().equals("Identificador")) {
                            //System.out.println("Si es un identificador Procediendo...");

                            if (!funcion.valor_CoV().equals(variable.nombre())) {
                                //System.out.println(ANSI_RED + "Variables diferentes detectadas" + ANSI_RESET);
                                resp = true; //No está declarada la variable
                            } else {
                                //Ambas variables son igual
                                //System.out.println(ANSI_GREEN + "Ambas variables son iguales CoV Procediendo..." + ANSI_RESET);
                                resp = false;
                                k = ArreVariables.size() - 1;
                            }
                        }//if identificador
                        else {
                            //System.out.println("Es cadena Procediendo...");
                            resp = false;
                            k = ArreVariables.size() - 1;
                        }
                    } else { //if funcion.fila() > variable.fila()
                        //La fila de la funcion es menor a la de la variable a evaluar
                        System.out.println(ANSI_RED + "Se detectó que ninguna de las variables definidas coincide \ncon la de la funcion \nMatando proceso" + ANSI_RESET);
                        System.out.println(ANSI_RED + errores_semanticos[3] + fcvfnc + "\nVariable fnc -> " + vvfncCoV + ANSI_RESET);
                        errores.add(errores_semanticos[3] + " " + fcvfnc + "\nVariable fnc -> " + vvfncCoV);
                        return true;
                    } //else funcion.fila() > variable.fila()
                } //For k CoV
                if (resp) {
                    System.out.println("");
                    System.out.println(ANSI_RED + "Se detectó que una función no tiene ninguna variable declarada \nMatando proceso" + ANSI_RESET);
                    System.out.println(ANSI_RED + errores_semanticos[3] + fcvfnc + "\nVariable fnc -> " + vvfncCoV + ANSI_RESET);
                    errores.add(errores_semanticos[3] + " " + fcvfnc + "\nVariable fnc -> " + vvfncCoV);
                    return true;
                }

                for (int k = 0; k < ArreVariables.size(); k++) {
                    Variables variable = ArreVariables.get(k);
                    //Verifica si la variable de la funcion está declarada antes de llamarla
//                    System.out.println("");
//                    System.out.println("Nom fnc i -> " + funcion.nombre() + " Valor CoV fnc i -> " + funcion.valor_CoV() + " Valor NoV fnc i ->" + funcion.valor_NoV() + /*" Tipo -> "+ ArreVariables.get(i).tipo()+ */ " Fila_Columna -> " + funcion.fila_columna());
//                    System.out.println("Nom variable k -> " + variable.nombre() +/* " Valor k -> " + ArreVariables.get(k).valor()+ " Tipo -> "+ ArreVariables.get(k).tipo()+ */ " Fila_Columna -> " + variable.fila_columna());

                    if (funcion.fila() > variable.fila()) {
                        nv = variable.nombre(); //Nombre de la variable
                        fcv = variable.fila_columna(); //Fila y columna donde está declarada la variable

                        if (funcion.tipo_NoV().equals("Identificador")) {
                            //System.out.println("Si es un identificador Procediendo...");

                            if (!funcion.valor_NoV().equals(variable.nombre())) {
                                //System.out.println(ANSI_RED + "Variables diferentes detectadas" + ANSI_RESET);
                                r = true; //No está declarada la variable
                            } else {
                                //Ambas variables son igual
                                //System.out.println(ANSI_GREEN + "Ambas variables son iguales NoV Procediendo..." + ANSI_RESET);
                                r = false;
                                k = ArreVariables.size() - 1;
                            }

                        }//if identificador
                        else {
                            //System.out.println("Es numero Procediendo...");
                            r = false;
                            k = ArreVariables.size() - 1;
                        }
                    } else { //if funcion.fila() > variable.fila()
                        //La fila de la funcion es menor a la de la variable a evaluar
                        System.out.println(ANSI_RED + "Se detectó que ninguna de las variables definidas coincide \ncon la de la funcion \nMatando proceso" + ANSI_RESET);
                        System.out.println(ANSI_RED + errores_semanticos[3] + fcvfnc + "\nVariable fnc -> " + vvfncNoV + ANSI_RESET);
                        errores.add(errores_semanticos[3] + " " + fcvfnc + "\nVariable fnc -> " + vvfncNoV);
                        return true;
                    } //else funcion.fila() > variable.fila()
                } //For k 2
                if (r) {
                    System.out.println("");
                    System.out.println(ANSI_RED + "Se detectó que una función no tiene ninguna variable declarada \nMatando proceso" + ANSI_RESET);
                    System.out.println(ANSI_RED + errores_semanticos[3] + fcvfnc + "\nVariablefnc -> " + vvfncNoV + ANSI_RESET);
                    errores.add(errores_semanticos[3] + " " + fcvfnc + "\nVariable fnc -> " + vvfncNoV);
                    return true;
                }
            }// If no.variables > 0
            else if (funcion.tipo_CoV().equals("Cadena")) {
                System.out.println("Evaluando: " + funcion.valor_CoV());
                resp = false;
                //i = ArreFunciones_CadenaoVariable.size()-1;
            } else {

                resp = true; //No hay variables definidas en las funciones pero si hay declaradas en el arreglo
                i = ArreFunciones_CadenaoVariable.size();
            }
        }//For i
        if (resp) {
            System.out.println("Entrando aqui");
            System.out.println(ANSI_RED + errores_semanticos[3] + fcvfnc + "\nVariable fnc -> " + vvfncCoV + ANSI_RESET);
            errores.add(errores_semanticos[3] + " " + fcvfnc + "\nVariable fnc -> " + vvfncCoV);
        }
        return resp;
    }

    private boolean sema_fncCoV_NoV_tipoIncorrecto() {
        boolean resp = true; //Si hay tipos incorrectos
        String nvfnc = "";
        String vvfncCoV = "";
        String vvfncNoV = "";
        String fcvfnc = "";
        String nv = "";
        String fcv = "";
        if (ArreVariables.size() > 0) {
            System.out.println("Num. Variables " + ArreVariables.size());
            for (int i = 0; i < ArreFunciones_CoV_NoV.size(); i++) {
                Funciones_CoV_NoV fun = ArreFunciones_CoV_NoV.get(i);
                nvfnc = fun.nombre();
                vvfncCoV = fun.valor_CoV();
                vvfncNoV = fun.valor_NoV();
                fcvfnc = fun.fila_columna();
                System.out.println("For j");
                for (int j = 0; j < ArreVariables.size(); j++) {
                    System.out.println("Entrando");
                    Variables vars = ArreVariables.get(j);
                    nv = vars.nombre();
                    fcv = vars.fila_columna();
                    System.out.println("Valor CoV fnc: " + fun.valor_CoV());
                    System.out.println("Valor var: " + vars.nombre());
                    if (fun.valor_CoV().equals(vars.nombre())) {
                        if (vars.tipo().equals("Numero")) {
                            System.out.println("Variable declarada como numero, error detectado");
                            resp = true; //Si hay tipos incorrectos
                            j = ArreFunciones_CoV_NoV.size();
                        } else {
                            resp = false;
                            System.out.println("Si es una cadena");
                            j = ArreVariables.size() - 1;
                        }
                    }
                } //For j
                if (resp) {
                    System.out.println(ANSI_RED + errores_semanticos[4] + "\nFuncion -> " + nvfnc + " " + fcvfnc + "\nVariable -> " + nv + " " + fcv + ANSI_RESET);
                    errores.add(errores_semanticos[4] + "\nFuncion -> " + nvfnc + " " + fcvfnc + "\nxVariable -> " + nv + " " + fcv);
                    return true;
                }
                for (int j = 0; j < ArreVariables.size(); j++) {
                    System.out.println("Entrando");
                    Variables vars = ArreVariables.get(j);
                    nv = vars.nombre();
                    fcv = vars.fila_columna();
                    System.out.println("Valor CoV fnc: " + fun.valor_CoV());
                    System.out.println("Valor var: " + vars.nombre());
                    if (fun.valor_NoV().equals(vars.nombre())) {
                        if (vars.tipo().equals("Cadena")) {
                            System.out.println("Variable declarada como cadena, error detectado");
                            resp = true; //Si hay tipos incorrectos
                            j = ArreFunciones_CoV_NoV.size();
                        } else {
                            resp = false;
                            System.out.println("Si es un numero");
                            j = ArreVariables.size() - 1;
                        }
                    }
                } //For j
                if (resp) {
                    System.out.println(ANSI_RED + errores_semanticos[5] + "\nFuncion -> " + nvfnc + " " + fcvfnc + "\nVariable -> " + nv + " " + fcv + ANSI_RESET);
                    errores.add(errores_semanticos[5] + "\nFuncion -> " + nvfnc + " " + fcvfnc + "\nxVariable -> " + nv + " " + fcv);
                    return true;
                }

            } // For i

        } else {
            System.out.println("No hay variables");
            return false;
        }

        return resp;
    }

    public ArrayList<String> matches(String text, String regex) {
        ArrayList<String> texts = new ArrayList();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println("Found text: " + matcher.group());
            System.out.println("Start index: " + matcher.start());
            System.out.println("End index: " + matcher.end());
            System.out.println("=============================");
            texts.add(matcher.group());
        }
        return texts;
    }

    public ArrayList<String> codigoIntermedio() {
        String codigo = txtCodigo.getText();
        codigo = codigo.replaceAll("[\r]+", "");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXx");
        System.out.println(codigo);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXx");
        String expregularnumero = "(for[\t\s]*\\([\t\s]*([0-9]+|\\$[A-Za-zÑñÁÉÍÓÚ]+)[\t\s]*\\))|(if[\t\s]*\\([\t\s]*([0-9]+|\\$[A-Za-zÑñÁÉÍÓÚ]+)[\t\s]*(>|<|>=|<=|==|!=)[\t\s]*([0-9]+|\\$[A-Za-zÑñÁÉÍÓÚ]+)[\t\s]*\\))|(\\$[A-Za-zÑñÁÉÍÓÚ]+[\t\s]*=[\t\s]*([0-9]+|\".*\"))";
        return matches(codigo, expregularnumero);

    }

    private void agregarGramaticaUsada() {
        gramaticas.forEach(gram -> {
            gramatica += gram + " ";
        });
        gramaUsadas.add(gramatica);
        gramatica = "";
        gramaticas.clear();
    }

    private void mensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private boolean agregaPila(String operacion, String variable, String variable2) {
        int tamaño = 0;
        String ultimo = "";
        switch (operacion) {
            case "puerta_abrir":
                switch (variable) {
                    case "#pRecepcion":
                        if (ArreFun_pRecepcion.size() > 0) {
                            tamaño = ArreFun_pRecepcion.size() - 1;
                            ultimo = ArreFun_pRecepcion.get(tamaño);
                            if (ultimo.contains("abrir")) {
                                agregarError("semantico", 6, ArreFilaColumnaToken.get(pos - 1), "Abriendo ---> " + variable);
                            } else {
                                ArreFun_pRecepcion.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }

                        break;
                    case "#pSala1":
                        if (ArreFun_pRecepcion.size() > 0) {
                            tamaño = ArreFun_pSala1.size() - 1;
                            ultimo = ArreFun_pSala1.get(tamaño);
                            if (ultimo.contains("abrir")) {
                                agregarError("semantico", 6, ArreFilaColumnaToken.get(pos - 1), "Abriendo ---> " + variable);
                            } else {
                                ArreFun_pSala1.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }

                        break;
                    case "#pSala2":
                        if (ArreFun_pRecepcion.size() > 0) {
                            tamaño = ArreFun_pSala2.size() - 1;
                            ultimo = ArreFun_pSala2.get(tamaño);
                            if (ultimo.contains("abrir")) {
                                agregarError("semantico", 6, ArreFilaColumnaToken.get(pos - 1), "Abriendo ---> " + variable);
                            } else {
                                ArreFun_pSala2.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }

                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;
            //break;
            case "puerta_cerrar":
                switch (variable) {
                    case "#pRecepcion":
                        if (ArreFun_pRecepcion.size() > 1) {
                            ArreFun_pRecepcion.remove(ArreFun_pRecepcion.size() - 1);
                        } else {
                            agregarError("semantico", 5, ArreFilaColumnaToken.get(pos - 1), "Cerrando ---> " + variable);
                        }
                        break;
                    case "#pSala1":
                        if (ArreFun_pSala1.size() > 1) {
                            ArreFun_pSala1.remove(ArreFun_pRecepcion.size() - 1);
                        } else {
                            agregarError("semantico", 5, ArreFilaColumnaToken.get(pos - 1), "Cerrando ---> " + variable);
                        }
                        break;
                    case "#pSala2":
                        if (ArreFun_pSala2.size() > 1) {
                            ArreFun_pSala2.remove(ArreFun_pRecepcion.size() - 1);
                        } else {
                            agregarError("semantico", 5, ArreFilaColumnaToken.get(pos - 1), "Cerrando ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;

            case "aspersor_activar":
                switch (variable) {
                    case "#aspPatio":
                        if (ArreFun_aspPatio.size() > 0) {
                            tamaño = ArreFun_aspPatio.size() - 1;
                            ultimo = ArreFun_aspPatio.get(tamaño);
                            if (ultimo.contains("activar")) {
                                agregarError("semantico", 8, ArreFilaColumnaToken.get(pos - 1), "Activando ---> " + variable);
                            } else {
                                ArreFun_aspPatio.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }

                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;
            //break;
            case "aspersor_desactivar":
                switch (variable) {
                    case "#aspPatio":
                        if (ArreFun_aspPatio.size() > 1) {
                            ArreFun_aspPatio.remove(ArreFun_aspPatio.size() - 1);
                        } else {
                            agregarError("semantico", 7, ArreFilaColumnaToken.get(pos - 1), "Desctivando ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;
            case "cortadora_activar":
                switch (variable) {
                    case "#cortadora":
                        if (ArreFun_Cortador.size() > 0) {
                            tamaño = ArreFun_Cortador.size() - 1;
                            ultimo = ArreFun_Cortador.get(tamaño);
                            if (ultimo.contains("activar")) {
                                agregarError("semantico", 10, ArreFilaColumnaToken.get(pos - 1), "Activando ---> " + variable);
                            } else {
                                ArreFun_Cortador.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }

                        break;

                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;
            //break;
            case "cortadora_desactivar":
                switch (variable) {
                    case "#coradora":
                        if (ArreFun_Cortador.size() > 1) {
                            ArreFun_Cortador.remove(ArreFun_Cortador.size() - 1);
                        } else {
                            agregarError("semantico", 9, ArreFilaColumnaToken.get(pos - 1), "Desctivando ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;
            case "ventilador_activar":
                switch (variable) {
                    case "#vRecepcion":
                        if (ArreFun_vRecepcion.size() > 0) {
                            tamaño = ArreFun_vRecepcion.size() - 1;
                            ultimo = ArreFun_vRecepcion.get(tamaño);
                            if (ultimo.contains("activar")) {
                                agregarError("semantico", 12, ArreFilaColumnaToken.get(pos - 1), "Activando ---> " + variable);
                            } else {
                                ArreFun_vRecepcion.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }

                break;
            case "ventilador_desactivar":
                switch (variable) {
                    case "#vRecepcion":
                        if (ArreFun_vRecepcion.size() > 1) {
                            ArreFun_vRecepcion.remove(ArreFun_vRecepcion.size() - 1);
                        } else {
                            agregarError("semantico", 11, ArreFilaColumnaToken.get(pos - 1), "Desctivando ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;
            case "iluminacion_encender":
                switch (variable) {
                    case "#iluRecepcion":
                        if (ArreFun_iluRecepcion.size() > 0) {
                            tamaño = ArreFun_iluRecepcion.size() - 1;
                            ultimo = ArreFun_iluRecepcion.get(tamaño);
                            if (ultimo.contains("encender")) {
                                agregarError("semantico", 14, ArreFilaColumnaToken.get(pos - 1), "Encendiendo ---> " + variable);
                            } else {
                                ArreFun_iluRecepcion.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }
                        break;
                    case "#iluPrincipal":
                        if (ArreFun_iluPrincipal.size() > 0) {
                            tamaño = ArreFun_iluPrincipal.size() - 1;
                            ultimo = ArreFun_iluPrincipal.get(tamaño);
                            if (ultimo.contains("encender")) {
                                agregarError("semantico", 14, ArreFilaColumnaToken.get(pos - 1), "Encendiendo ---> " + variable);
                            } else {
                                ArreFun_iluPrincipal.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }
                        break;
                    case "#iluSala1":
                        if (ArreFun_iluSala1.size() > 0) {
                            tamaño = ArreFun_iluSala1.size() - 1;
                            ultimo = ArreFun_iluSala1.get(tamaño);
                            if (ultimo.contains("encender")) {
                                agregarError("semantico", 14, ArreFilaColumnaToken.get(pos - 1), "Encendiendo ---> " + variable);
                            } else {
                                ArreFun_iluSala1.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }
                        break;
                    case "#iluSala2":
                        if (ArreFun_iluSala2.size() > 0) {
                            tamaño = ArreFun_iluSala2.size() - 1;
                            ultimo = ArreFun_iluSala2.get(tamaño);
                            if (ultimo.contains("encender")) {
                                agregarError("semantico", 14, ArreFilaColumnaToken.get(pos - 1), "Encendiendo ---> " + variable);
                            } else {
                                ArreFun_iluSala2.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;
            //break;
            case "iluminacion_apagar":
                switch (variable) {
                    case "#iluRecepcion":
                        if (ArreFun_iluRecepcion.size() > 1) {
                            ArreFun_iluRecepcion.remove(ArreFun_iluRecepcion.size() - 1);
                        } else {
                            agregarError("semantico", 13, ArreFilaColumnaToken.get(pos - 1), "Desctivando ---> " + variable);
                        }
                        break;
                    case "#iluPrincipal":
                        if (ArreFun_iluPrincipal.size() > 1) {
                            ArreFun_iluPrincipal.remove(ArreFun_iluPrincipal.size() - 1);
                        } else {
                            agregarError("semantico", 13, ArreFilaColumnaToken.get(pos - 1), "Desctivando ---> " + variable);
                        }
                        break;
                    case "#iluSala1":
                        if (ArreFun_iluSala1.size() > 1) {
                            ArreFun_iluSala1.remove(ArreFun_iluSala1.size() - 1);
                        } else {
                            agregarError("semantico", 13, ArreFilaColumnaToken.get(pos - 1), "Desctivando ---> " + variable);
                        }
                        break;
                    case "#iluSala2":
                        if (ArreFun_iluSala2.size() > 1) {
                            ArreFun_iluSala2.remove(ArreFun_iluSala2.size() - 1);
                        } else {
                            agregarError("semantico", 13, ArreFilaColumnaToken.get(pos - 1), "Desctivando ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;

            case "banda_activar":
                switch (variable) {
                    case "#banda":
                        if (ArreFun_Banda.size() > 0) {
                            tamaño = ArreFun_Banda.size() - 1;
                            ultimo = ArreFun_Banda.get(tamaño);
                            if (ultimo.contains("activar")) {
                                agregarError("semantico", 16, ArreFilaColumnaToken.get(pos - 1), "Activando ---> " + variable);
                            } else {
                                ArreFun_Banda.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }

                        break;

                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }

                break;
            //break;
            case "banda_desactivar":
                switch (variable) {
                    case "#banda":
                        if (ArreFun_Banda.size() > 1) {
                            ArreFun_Banda.remove(ArreFun_Banda.size() - 1);
                        } else {
                            agregarError("semantico", 15, ArreFilaColumnaToken.get(pos - 1), "Desctivando ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;

            case "tv_encender":
                switch (variable) {
                    case "#tvRecepcion":
                        if (ArreFun_tvRecepcion.size() > 0) {
                            tamaño = ArreFun_tvRecepcion.size() - 1;
                            ultimo = ArreFun_tvRecepcion.get(tamaño);
                            if (ultimo.contains("encender")) {
                                agregarError("semantico", 18, ArreFilaColumnaToken.get(pos - 1), "Encendiendo ---> " + variable);
                            } else {
                                ArreFun_tvRecepcion.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }
                        break;
                    default:
                }

                break;
            //break;
            case "tv_apagar":
                switch (variable) {
                    case "#tvRecepcion":
                        if (ArreFun_tvRecepcion.size() > 1) {
                            ArreFun_tvRecepcion.remove(ArreFun_tvRecepcion.size() - 1);
                        } else {
                            agregarError("semantico", 17, ArreFilaColumnaToken.get(pos - 1), "Apagando ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;

            case "alarma_activar":
                switch (variable) {
                    case "#alarma":
                        if (ArreFun_Alarma.size() > 0) {
                            tamaño = ArreFun_Alarma.size() - 1;
                            ultimo = ArreFun_Alarma.get(tamaño);
                            if (ultimo.contains("activar")) {
                                agregarError("semantico", 20, ArreFilaColumnaToken.get(pos - 1), "Activando ---> " + variable);
                            } else {
                                ArreFun_Alarma.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }
                        break;
                    default:
                }

                break;
            //break;
            case "alarma_desactivar":
                switch (variable) {
                    case "#alarma":
                        if (ArreFun_Alarma.size() > 1) {
                            ArreFun_Alarma.remove(ArreFun_Alarma.size() - 1);
                        } else {
                            agregarError("semantico", 19, ArreFilaColumnaToken.get(pos - 1), "Desactivando ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;

            case "cajafuerte_activar":
                switch (variable) {
                    case "#caja":
                        if (ArreFun_Caja.size() > 0) {
                            tamaño = ArreFun_Caja.size() - 1;
                            ultimo = ArreFun_Caja.get(tamaño);
                            if (ultimo.contains("activar")) {
                                agregarError("semantico", 22, ArreFilaColumnaToken.get(pos - 1), "Activando ---> " + variable);
                            } else {
                                ArreFun_Caja.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }
                        break;
                    default:
                }

                break;
            //break;
            case "cajafuerte_desactivar":
                switch (variable) {
                    case "#caja":
                        if (ArreFun_Caja.size() > 1) {
                            ArreFun_Caja.remove(ArreFun_Caja.size() - 1);
                        } else {
                            agregarError("semantico", 21, ArreFilaColumnaToken.get(pos - 1), "Desactivando ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;

            case "panel_encender":
                switch (variable) {
                    case "#panelPatio":
                        if (ArreFun_panelPatio.size() > 0) {
                            tamaño = ArreFun_panelPatio.size() - 1;
                            ultimo = ArreFun_panelPatio.get(tamaño);
                            if (ultimo.contains("encender") || ultimo.contains("girar")) {
                                agregarError("semantico", 25, ArreFilaColumnaToken.get(pos - 1), "Encendiendo ---> " + variable);
                            } else {
                                ArreFun_panelPatio.add(operacion + "( " + variable + " )");
                            }
                        } else {
                            agregarError("semantico", 3, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                        }
                        break;
                    default:
                }

                break;
            //break;
            case "panel_apagar":
                switch (variable) {
                    case "#panelPatio":
                        if (ArreFun_panelPatio.size() > 1) {
                            while (ArreFun_panelPatio.size() > 1) {
                                ArreFun_panelPatio.remove(ArreFun_panelPatio.size() - 1);
                            }
                        } else {
                            agregarError("semantico", 24, ArreFilaColumnaToken.get(pos - 1), "Apagando ---> " + variable);
                        }
                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }
                break;
            case "panel_girar":
                switch (variable) {
                    case "#panelPatio":
                        if (ArreFun_panelPatio.size() > 0) { //Si tiene la variable declarada
                            tamaño = ArreFun_panelPatio.size() - 1;
                            ultimo = ArreFun_panelPatio.get(tamaño);
                            if (ultimo.contains("encender") || ultimo.contains("girar")) {
                                ArreFun_panelPatio.add(operacion + "( " + variable + " , " + variable2 + " )");
                            } else {
                                agregarError("semantico", 26, ArreFilaColumnaToken.get(pos - 1), "Girando ---> " + variable);
                            }
                        } else {
                            agregarError("semantico", 26, ArreFilaColumnaToken.get(pos - 1), "Girando ---> " + variable);
                        }

                        break;
                    default:
                        agregarError("semantico", 23, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + variable);
                }

            default:
                //System.out.println("Nada encontrado");
                //JOptionPane.showMessageDialog(this, "Nada encontrado");
                return true;
        }
        return false;

    }

    private boolean controlCorrecto() {
        if (valorCorrecto(ArreNomToken.get(pos), "CONTROL")) {
            String control = ArreToken.get(pos);
            pos++;
            if (hayOtro() && valorCorrecto(ArreNomToken.get(pos), "Punto_Coma")) {
                pos++;
                switch (control) {
                    case "#aspPatio":
                        if (ArreFun_aspPatio.size() > 0) {
                            agregarError("semantico", 1, ArreFilaColumnaToken.get(pos - 1), "Variable ---> " + control);
                        } else {
                            ArreFun_aspPatio.add("def " + control);
                        }

                        break;
                    case "#iluRecepcion":
                        ArreFun_iluRecepcion.add("def " + control);
                        break;
                    case "#iluPrincipal":
                        ArreFun_iluPrincipal.add("def " + control);
                        break;
                    case "#iluSala1":
                        ArreFun_iluSala1.add("def " + control);
                        break;
                    case "#iluSala2":
                        ArreFun_iluSala2.add("def " + control);
                        break;
                    case "#pRecepcion":
                        ArreFun_pRecepcion.add("def " + control);
                        break;
                    case "#pSala1":
                        ArreFun_pSala1.add("def " + control);
                        break;
                    case "#pSala2":
                        ArreFun_pSala2.add("def " + control);
                        break;
                    case "#panelPatio":
                        ArreFun_panelPatio.add("def " + control);
                        break;
                    case "#tvRecepcion":
                        ArreFun_tvRecepcion.add("def " + control);
                        break;
                    case "#vRecepcion":
                        ArreFun_vRecepcion.add("def " + control);
                        break;
                    case "#alarma":
                        ArreFun_Alarma.add("def " + control);
                        break;
                    case "#caja":
                        ArreFun_Caja.add("def " + control);
                        break;
                    case "#cortadora":
                        ArreFun_Cortador.add("def " + control);
                        break;
                    default:
                }
                return true;
            } else {
                agregarError("sintactico", 44, ArreFilaColumnaToken.get(pos - 1), "");
            }

        } else {
            agregarError("sintactico", 43, ArreFilaColumnaToken.get(pos - 1), "");
        }
        return false;
    }

    private Variables identificadorDeclarado(String identificador) {
        Variables variable = new Variables("XXX", "XXX", "XXX", "XXX");
        for (int i = 0; i < ArreVariables.size(); i++) {
            Variables var = ArreVariables.get(i);
            if (var.nombre().equals(identificador)) {
                variable = var;
                return variable;
            }
        }
        return variable;
    }

    private boolean tiposIguales(Variables var1, Variables var2) {
        if (var1.tipo().equals(var2.tipo())) {
            return true;
        }
        return false;
    }
}
