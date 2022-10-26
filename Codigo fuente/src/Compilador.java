
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
    private ArrayList<Funciones_CoV_NoV> ArreFunciones_CoV_NoV = new ArrayList<>();
    private ArrayList<Integer> ArreFilaFnc = new ArrayList<>();
    private ArrayList<Integer> ArreFilaVar = new ArrayList<>();
    private ArrayList<ErrorLSSL> errors;
    private ArrayList<String> errores, gramaticas;
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
    private int esteXO;
    private String varNombre, fncCoVNombre, fncCoV_NoVNombre = "";
    private String varValor, fncCoVValor, fncCoV_NoVValor_CoV, fncCoV_NoVValor_NoV = "";
    private String varTipo, fncCoVTipo, fncCoV_NoVTipo_CoV, fncCoV_NoVTipo_NoV = "";
    private String varFila_columna, fncCoVFila_Columna, fncCoV_NoVFila_columna = "";
    private String fncCoVStatus, fncCoV_NoVStatus = "";

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
    public String[] errores_sintacticos = {};
    public String[] soluciones_sintactico = {
        /* 1 */"Solución: \n" + "Se espera escritura de código. \n" + "\n" + "Resultado esperado: \n" + "Agregue la estructura inicial para comenzar con el programa \n" + "Estructura inicial: \n" + "INICIO $ejemplo { \n" + "  PRINCIPAL(){ \n" + "     //Código \n" + "  } \n" + "} ",
        /* 2 */ "Solución: \n" + "Agregue un operador de asignación ‘=’ después del identificador \n" + "\n" + "Resultado esperado: \n" + "Identificador OP_Asignacion Valor Punto_Coma \n" + "Identificador = (Numero | Cadena | OP_Retorno); \n" + "$ejemplo = 12;",
        /* 3 */ "Solución: \n" + "Escriba un numero o cadena o OP_Retorno despues del signo ‘=’ \n" + "\n" + "Resultado esperado:  \n" + "Identificador OP_Asignacion Valor Punto_Coma \n" + "Identificador = (Numero | Cadena | OP_Retorno); \n" + "$ejemplo = 12;",
        /* 4 */ "Solución: \n" + "Escribe punto y coma ‘;’ después de la línea de código \n" + "\n" + "Resultado esperado:  \n" + "Identificador OP_Asignacion Valor Punto_Coma \n" + "Identificador = (Numero | Cadena | OP_Retorno); \n" + "$ejemplo = 12;",
        /* 5 */ "Solución: \n" + "Escriba un parentesis ‘(‘ para asignar la existencia de un operador \n" + "\n" + "Resultado esperado:  \n" + "OP_Retorno Parentesis_A Parentesis_C \n" + "OP_Retorno () \n" + "\nEjemplo \n" + "ilu_status()",
        /* 6 */ "Solución: \n" + "Escriba un paréntesis ‘)’ al final del operador para asignar el cierre de la operación \n" + "\n" + "Resultado esperado:  \n" + "OP_Retorno Parentesis_A Parentesis_C \n" + "OP_Retorno () \n" + "\nEjemplo \n" + "ilu_status()",
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
        /* 22 */ "Solución: \n" + "Escriba el paréntesis ‘(‘ que abre el inicio de una función u operación \n" + "\n" + "Resultado esperado:  \n" + "(OP_Cita | OP_Turno | OP_Iluminacion | OP_Temperatura | OP_Puerta) Parentesis_A (Valor) Parentesis_C Punto_Coma \n" + "(OP_Cita | OP_Turno | OP_Iluminacion | OP_Temperatura | OP_Puerta) (Valor); \n" + "\nEjemplo \n" + "Op_Cita(“ejemplo”);",
        /* 23 */ "Solución: \n" + "Escriba el paréntesis ‘)‘ que cierra la función u operación \n" + "\n" + "Resultado esperado:  \n" + "(OP_Cita | OP_Turno | OP_Iluminacion | OP_Temperatura | OP_Puerta) Parentesis_A (Valor) Parentesis_C Punto_Coma \n" + "(OP_Cita | OP_Turno | OP_Iluminacion | OP_Temperatura | OP_Puerta) (Valor); \n" + "\nEjemplo \n" + "Op_Cita(“ejemplo”);",
        /* 24 */ "Solución: \n" + "Escriba punto y coma ‘;’ para cerrar la función o sentencia escrita \n" + "\n" + "Resultado esperado:  \n" + "(OP_Cita | OP_Turno | OP_Iluminacion | OP_Temperatura | OP_Puerta) Parentesis_A (Valor) Parentesis_C Punto_Coma \n" + "(OP_Cita | OP_Turno | OP_Iluminacion | OP_Temperatura | OP_Puerta) (Valor); \n" + "\nEjemplo \n" + "Op_Cita(“ejemplo”);",
        /* 25 */ "Solución: \n" + "Escriba un valor numérico, cadena u operación dentro de la función \n" + "\n" + "Resultado esperado:  \n" + "(OP_Cita | OP_Turno | OP_Iluminacion | OP_Temperatura | OP_Puerta) Parentesis_A (Valor) Parentesis_C Punto_Coma \n" + "(OP_Cita | OP_Turno | OP_Iluminacion | OP_Temperatura | OP_Puerta) (Valor); \n" + "\nEjemplo \n" + "Op_Cita(“ejemplo”);",
        /* 26 */ "Solución: \n" + "Escriba el punto y coma que falta en la función \n" + "\n" + "Resultado esperado:  \n" + "(OP_Cita | OP_Turno | OP_Iluminacion | OP_Temperatura | OP_Puerta) Parentesis_A (Valor) Parentesis_C Punto_Coma \n" + "(OP_Cita | OP_Turno | OP_Iluminacion | OP_Temperatura | OP_Puerta) (Valor); \n" + "\nEjemplo \n" + "Op_Cita(“ejemplo”);",
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
        /* 42 */ "Solución: \n" + "Escriba la llave ‘{‘ que abre el bloque de escritura de código \n" + "\n" + "Resultado esperado:  \n" + "INICIO Identificador Llave_A PRINCIPAL Parentesis_A Parentesis_C Llave_A CODIGO Llave_C Llave_C FINAL \n" + "INICIO Identificador { PRINCIPAL() { CODIGO }}Final \n" + "\nEjemplo \n" + "INICIO $ejemplo { \n" + "   PRINCIPAL(){ \n" + "      CODIGO \n" + "   } \n" + "}  \n" + "FINAL ",};

    public String errores_semanticos[] = {
        /* 0 */"",
        /* 1 */ "Error semantico [1]: La variable ya se encuentra declarada en: ",
        /* 2 */ "Error semantico [2]: No se puede cambiar el tipo de dato de la variable \nporque ya se encuentra definido",
        /* 3 */ "Error semantico [3]: La variable no se encuentra declarada para asignar en la función",
        /* 4 */ "Error semantico [4]: La variable asignada en la función es de tipo 'numero', se esperaba una 'cadena'"
    };
    public String soluciones_semantico[] = {};

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
        CadErrores = new ArrayList<>();
        textsColor = new ArrayList<>();
        ProCorrectas = new ArrayList<>();
        ArreCompleto = new ArrayList<>();
        identProd = new ArrayList<>(); //Identificadores de producción
        identificadores = new HashMap<>();
        esteXO = this.getX();

        Functions.setAutocompleterJTextComponent(new String[]{"INICIO $programa {\n    PRINCIPAL(){\n//Bloque de codigo\n       \n       \n       \n//Fin bloque de codigo\n    }//Fin PRINCIPAL\n}//Fin INICIO\nFINAL\n//NO AGREGAR CODIGO DESPUES DE LA PALABRA FINAL",
            "cortadora_activar", "cortadora_desactivar",
            "aspersor_activar", "aspersor_desactivar",
            "ventilador_activar", "ventilador_desactivar",
            "iluminacion_encender", "iluminacion_apagar",
            "puerta_abrir", "puerta_cerrar",
            "banda_activar", "banda_desactivar",
            "tv_encender", "tv_apagar",
            "alarma_activar", "alarma_desactivar",
            "cajafuerte_activar", "cajafuerte_desactivar",
            "panel_girar"
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
        ArreToken.clear();
        CadErrores.clear();
        ArreNomToken.clear();
        ProCorrectas.clear();
        ArreCompleto.clear();
        ArreVariables.clear();
        ArreFunciones_CadenaoVariable.clear();
        ArreFunciones_CoV_NoV.clear();
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
        semanticAnalysis();
        printConsole();
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

    private void semanticAnalysis() {

//        errores.clear();
//        System.out.println("NUMERO DE FUNCIONES DEFINIDAS: " + ArreFunciones_CadenaoVariable.size());
//        ArreFunciones_CadenaoVariable.forEach(var
//                -> {
//            System.out.println("");
//            System.out.println("Nombre: " + var.nombre());
//            System.out.println("Valor: " + var.valor());
//            System.out.println("Tipo: " + var.tipo());
//            System.out.println("Fila_Columna: " + var.fila_columna());
//            System.out.println("Status: " + var.status());
//        }
//        );
//        if (!sema_variables_repetidas()){
//            System.out.println("Sin variables repetidas");
//        }
//        ArreFunciones_CadenaoVariable.forEach(fnc ->{
//            System.out.println("Fila de "+fnc.nombre()+ " desde propiedad -> "+fnc.fila());
//        });
//        ArreVariables.forEach(variables ->{
//            System.out.println("Fila de "+variables.nombre()+ " desde propiedad -> "+variables.fila());
//        });
//        ArreFilaFnc.forEach(ffnc -> {
//            System.out.println("Fila de la funcion: " + ffnc);
//        });
//        ArreFilaVar.forEach(fv -> {
//            System.out.println("Fila de la variable: " + fv);
//        });
        //SERGIO_EDITANDO 
        sema_asignaFilas();
//        if (ArreVariables.size() > 0) {
//            if (sema_variables_tipoDato()) {
//                System.out.println("Sin cambios de tipos de variables");
//            }
//        }
//        if (ArreFunciones_CadenaoVariable.size() > 0) {
//            if (!sema_fncCoV_varNoDeclarada()) { //true -> hubo variables no declaradas. false -> todas las variables están declaradas
//                System.out.println("Todas las variables estan bien definidas para las funciones");
//                if (!sema_fncCoV_tipoIncorrecto()) {
//                    System.out.println("Todas las variables o datos recibidos en las funciones son correctos");
//                }
//            }
//        }
        System.out.println("Tamaño NoV: " + ArreFunciones_CoV_NoV.size());
        if (ArreFunciones_CoV_NoV.size() > 0) {
            if (!sema_fncCoV_NoV_varNoDeclaradas()) {
                System.out.println("Todas las variables estan bien definidas para las funciones CoV_NoV");
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
        int sizeErrors = errors.size();
        int numErrores = errores.size();
        if (numErrores > 0) {
            String strErrores = errores.get(0);
//            for (int i = 0; i < numErrores; i++) {
//                strErrores = errores.get(i) + "\n";
//            }            
            txtConsola.setText("Compilación terminada...\n" + strErrores + "\nLa compilación terminó con errores...");
            txtConsola.setForeground(Color.red);
        } else {
            txtConsola.setText("Compilación terminada...");
            txtConsola.setForeground(Color.green);
        }
        txtConsola.setCaretPosition(0);
//        if (opcGrama != null) {
//            opcGrama.dispose();
//            opcGrama = new OpcionesGrama(gramaticas);
//            opcGrama.setVisible(true);
//            this.setLocation(esteXO-100, this.getY());
//            opcGrama.setLocation(opcGrama.getX()+300, opcGrama.getY());
//        }

//        if (sizeErrors > 0) {
//            Functions.sortErrorsByLineAndColumn(errors);
//            String strErrors = "\n";
//            for (ErrorLSSL error : errors) {
//                String strError = String.valueOf(error);
//                strErrors += strError + "\n";
//            }
//            txtConsola.setText("Compilación terminada...\n" + strErrors + "\nLa compilación terminó con errores...");
//            txtConsola.setForeground(Color.red);
//        } else {
//            txtConsola.setText("Compilación terminada...");
//            txtConsola.setForeground(Color.green);
//        }
//        txtConsola.setCaretPosition(0);
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
            System.out.println("Tamaño errores: " + errores.size());
            for (int i = 0; i < errores.size(); i++) {
                System.out.println("Error " + i + " " + errores.get(i));
            }
            System.out.println("");
            System.out.println("Error 0 " + errores.get(0));
            String cadena = errores.get(0);
            String[] parts = null;
            parts = cadena.split("\\[");
            System.out.println("Error split parte 1 " + parts[1]);
            String[] parts2 = parts[1].split("\\]");
            System.out.println("Error split parte 2 " + parts2[0]);
            numeroError = Integer.parseInt(parts2[0]);
            venErrores = new VentanaErrores();
            venErrores.setVisible(true);
            venErrores.txtError.setText(errores.get(0) + "\n\n" + soluciones_sintactico[numeroError - 1]);
        }
        /*
        int position = txtConsola.getCaretPosition();
        //txtConsola.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (position != -1) {
            String console = txtConsola.getText();
            int firstSaltLine = -1, ultimateSaltLine = -1;
            for (int i = position - 1; i >= 0; i--) {
                String character = console.substring(i, i + 1);
                if (character.equals("\n")) {
                    firstSaltLine = i;
                    break;
                }
            }
            for (int i = position; i < console.length(); i++) {
                String character = console.substring(i, i + 1);
                if (character.equals("\n")) {
                    ultimateSaltLine = i;
                    break;
                }
            }
            int contador = -2;
            for (int i = 0; i < console.length(); i++) {
                String character = console.substring(i, i + 1);
                if (character.equals("\n")) {
                    contador++;
                }
                if ((i >= firstSaltLine) && (i <= ultimateSaltLine) && (contador >= 0) && (contador < errors.size())) {
                    ErrorLSSL error = errors.get(contador);
                    boolean existAutomata = false;
                    for (Production er : CadErrores) {
                        if ((er.getLine() == error.getLine() || er.getFinalLine() == error.getLine())
                                && (er.getColumn() == error.getColumn() || er.getFinalColumn() == error.getColumn())) {
                            System.out.println(er.getName());
                            System.out.println(er.lexicalCompRank(0, -1));
                            System.out.println(er.lexemeRank(0, -1));
                            System.out.println(error);
                            existAutomata = true;

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
                                opcGrama.dispose opcGrama = new OpcionesGrama(gramaticas);        opcGrama.setVisible(true); ();
                            }

                            venErrores = new VentanaErrores(er.getName(), er.lexicalCompRank(0, -1), er.lexemeRank(0, -1));
                            venErrores.setVisible(true);
                            //pintar(firstSaltLine, ultimateSaltLine);
                            break;
                        }
                    }
                    if (!existAutomata) {
                        System.out.println("no existe el automata de esa expresión");
                    }
                    break;
                }
            }
        }
        //txtConsola.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         */
    }//GEN-LAST:event_txtConsolaMouseReleased

    private void opAutomataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opAutomataActionPerformed
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
        String cadena = "";
        for (int i = 0; i <= gramaticas.size() - 1; i++) {
            cadena += gramaticas.get(i) + "\n";
        }

        opcGrama = new OpcionesGrama(gramaticas);
        opcGrama.setVisible(true);

    }//GEN-LAST:event_opGramaticaUActionPerformed

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
    private say.swing.JFontChooser jFontChooser1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenuItem opAbrir;
    private javax.swing.JMenuItem opAutomata;
    private javax.swing.JMenuItem opCompilar;
    private javax.swing.JMenuItem opCopiar;
    private javax.swing.JMenuItem opCortar;
    private javax.swing.JMenuItem opEjecutar;
    private javax.swing.JMenuItem opFuente;
    private javax.swing.JMenu opGramatica;
    private javax.swing.JMenuItem opGramaticaG;
    private javax.swing.JMenuItem opGramaticaU;
    private javax.swing.JMenuItem opGuardar;
    private javax.swing.JMenuItem opGuardarComo;
    private javax.swing.JMenuItem opNuevo;
    private javax.swing.JMenuItem opPegar;
    private javax.swing.JMenuItem opTablaTokensD;
    private javax.swing.JMenu opTokens;
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

    private boolean identificadorCorrecto() {
        String error = "";
        if (ArreNomToken.get(pos).equals("Op_Asignacion")) {
            pos++;
            System.out.println("Operador de asignacion encontrado");
            gramaticas.add("Operador de asignacion");
            if (hayOtro()) {
                switch (ArreNomToken.get(pos)) {
                    case "Numero":
                        varValor = ArreToken.get(pos);
                        System.out.println(ANSI_PURPLE + "Valor de la variable: " + ArreToken.get(pos) + ANSI_RESET);
                        varTipo = "Numero";
                        System.out.println(ANSI_PURPLE + "Tipo de la variable: " + ArreNomToken.get(pos) + ANSI_RESET);
                        varFila_columna = ArreFilaColumnaToken.get(pos - 1);
                        System.out.println(ANSI_PURPLE + "Fila_Columna de la variable: " + ArreFilaColumnaToken.get(pos - 1) + ANSI_RESET);
                        ArreVariables.add(new Variables(varNombre, varValor, varTipo, varFila_columna));
                        pos++;
                        System.out.println("Numero encontrado");
                        gramaticas.add("Numero");
                        if (hayOtro()) {
                            if (ArreNomToken.get(pos).equals("Punto_Coma")) {
                                pos++;
                                System.out.println("Punto y Coma encontrado");
                                gramaticas.add("Punto y coma");
                                return true;
                            } else {
                                error = "Error [4], se esperaba punto y coma ';' en el identificador " + ArreFilaColumnaToken.get(pos - 1);
                                errores.add(error);
                            }
                        } else {
                            error = "Error [4], se esperaba punto y coma ';' en el identificador " + ArreFilaColumnaToken.get(pos - 1);
                            errores.add(error);
                        }

                        break;
                    case "Cadena":
                        varValor = ArreToken.get(pos);
                        System.out.println(ANSI_PURPLE + "Valor de la variable: " + ArreToken.get(pos) + ANSI_RESET);

                        varTipo = "Cadena";
                        System.out.println(ANSI_PURPLE + "Tipo de la variable: " + ArreNomToken.get(pos) + ANSI_RESET);

                        varFila_columna = ArreFilaColumnaToken.get(pos - 1);
                        System.out.println(ANSI_PURPLE + "Fila_Columna de la variable: " + ArreFilaColumnaToken.get(pos - 1) + ANSI_RESET);

                        ArreVariables.add(new Variables(varNombre, varValor, varTipo, varFila_columna));
                        pos++;
                        System.out.println("Cadena encontrada");
                        gramaticas.add("Cadena");
                        if (hayOtro()) {
                            if (ArreNomToken.get(pos).equals("Punto_Coma")) {
                                pos++;
                                System.out.println("Punto y Coma encontrado");
                                gramaticas.add("Punto y coma");
                                return true;
                            } else {
                                error = "Error [4], se esperaba punto y coma ';' en el identificador " + ArreFilaColumnaToken.get(pos - 1);
                                errores.add(error);
                            }
                        } else {
                            error = "Error [4], se esperaba punto y coma ';' en el identificador " + ArreFilaColumnaToken.get(pos - 1);
                            errores.add(error);
                        }

                        break;
//                    case "OP_Retorno":
//                        System.out.println("OP_Retorno encontrada");
//                        gramaticas.add("Op_Retorno");
//                        pos++;
//                        if (hayOtro()) {
//                            if (!op_RetornoCorrecto()) {
//                                System.out.println("OP_Retorno incorrecta");
//                            }//No está correcta
//                            else {
//                                if (hayOtro()) {
//                                    if (ArreNomToken.get(pos).equals("Punto_Coma")) {
//                                        pos++;
//                                        System.out.println("Punto y Coma encontrado");
//                                        gramaticas.add("Punto y coma");
//                                        return true;
//                                    } else {
//                                        error = "Error [4], se esperaba punto y coma ';' en el identificador " + ArreFilaColumnaToken.get(pos - 1);
//                                        errores.add(error);
//                                    }
//                                }//Hay otro
//                                else {
//                                    error = "Error [4], se esperaba punto y coma ';' en el identificador " + ArreFilaColumnaToken.get(pos - 1);
//                                    errores.add(error);
//                                }
//                                gramaticas.add("Op_Retorno Correcta \n");
//                            }//Si está correcta
//                        }//Hay otro
//                        else {
//                            errores.add("Error [5], se esperaba parentesis que abre '(' en la OP_Retorno " + ArreFilaColumnaToken.get(pos - 1));
//                        }
//
//                        break;
                    default:
                        error = "Error [3], se esperaba numero, cadena en el identificador " + ArreFilaColumnaToken.get(pos - 1);
                        errores.add(error);
                }
            } else {
                error = "Error [3], se esperaba numero, cadena u op_retorno en el identificador " + ArreFilaColumnaToken.get(pos - 1);
                errores.add(error);
            }

        } else {
            error = "Error [2], se esperaba operador de asignacion en el identificador " + ArreFilaColumnaToken.get(pos - 1);
            errores.add(error);
        }
        return false;
    }

    private boolean hayOtro() {
//        System.out.println("Cant NomToken: "+ArreNomToken.size());
//        System.out.println("Cant pos: "+pos);
        if (ArreNomToken.size() > pos) {
            return true;
        } else {
            System.out.println("Ya no hay otro");
            //errores.add("No hay más tokens para analizar");
            return false;
        }
    }

    private boolean op_RetornoCorrecto() {
        String error = "";
        if (ArreNomToken.get(pos).equals("Parentesis_A")) {
            pos++;
            System.out.println("Parentecis que abre encontrado");
            gramaticas.add("Parentecis que abre");
            if (hayOtro()) {
                if (ArreNomToken.get(pos).equals("Parentesis_C")) {
                    pos++;
                    System.out.println("Parentesis que cierra encontrado");
                    gramaticas.add("Parentecis que cierra");
                    return true;
                    //Es corrrecto
                } else {
                    error = "Error [6], se esperaba parentesis que cierra ')' en la OP_Retorno " + ArreFilaColumnaToken.get(pos - 1);
                    errores.add(error);
                }
            }//Hay otro
            else {
                error = "Error [6], se esperaba parentesis que cierra ')' en la OP_Retorno " + ArreFilaColumnaToken.get(pos - 1);
                errores.add(error);
            }

        } else {
            error = "Error [5], se esperaba parentesis que abre '(' en la OP_Retorno " + ArreFilaColumnaToken.get(pos - 1);
            errores.add(error);
        }
        return false;
    }

    private boolean forCorrecto() {
        String error = "";
        if (ArreNomToken.get(pos).equals("Parentesis_A")) {
            pos++;
            System.out.println("Parentecis que abre encontrado");
            gramaticas.add("Parentecis que abre");
            if (hayOtro()) {
                if (ArreNomToken.get(pos).equals("Numero") || ArreNomToken.get(pos).equals("Identificador")) {
                    pos++;
                    System.out.println("Numero o identificador encontrado");
                    gramaticas.add("Numero o identificador");
                    if (hayOtro()) {
                        if (ArreNomToken.get(pos).equals("Parentesis_C")) {
                            pos++;
                            System.out.println("Parentesis que cierra encontrado");
                            gramaticas.add("Parentecis que cierra");
                            if (hayOtro()) {
                                if (ArreNomToken.get(pos).equals("Llave_A")) {
                                    pos++;
                                    System.out.println("Llave que abre encontrada");
                                    gramaticas.add("Llave que abre \n");
                                    if (hayOtro()) {

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

                                        if (hayOtro()) {
                                            if (ArreNomToken.get(pos).equals("Llave_C")) {
                                                pos++;
                                                System.out.println("Llave que cierra encontrada");
                                                gramaticas.add("Llave que cierra");
                                                return true;
                                            } // }
                                            else {
                                                error = "Error [11], se esperaba llave que cierra '}' en el ciclo For " + ArreFilaColumnaToken.get(pos - 1);
                                                errores.add(error);
                                            } // }
                                        }//Hay otro
                                        else {
                                            error = "Error [11], se esperaba llave que cierra '}' en el ciclo For " + ArreFilaColumnaToken.get(pos - 1);
                                            errores.add(error);
                                        }
                                    }//Hay otro
                                    else {
                                        error = "Error [], se esperaba   " + ArreFilaColumnaToken.get(pos - 1);
                                        errores.add(error);
                                    }

                                } // {
                                else {
                                    error = "Error [10], se esperaba llave que abre '{' en el ciclo For " + ArreFilaColumnaToken.get(pos - 1);
                                    errores.add(error);
                                } // {
                            }//Hay otro
                            else {
                                error = "Error [10], se esperaba llave que abre '{' en el ciclo For " + ArreFilaColumnaToken.get(pos - 1);
                                errores.add(error);
                            }
                        }// )
                        else {
                            error = "Error [9], se esperaba parentesis que cierra ')' en el ciclo For " + ArreFilaColumnaToken.get(pos - 1);
                            errores.add(error);
                        }// )
                    }//Hay otro
                    else {
                        error = "Error [9], se esperaba parentesis que cierra ')' en el ciclo For " + ArreFilaColumnaToken.get(pos - 1);
                        errores.add(error);
                    }

                }//Es valor o identificador
                else {
                    error = "Error [8], se esperaba numero o identificador en el ciclo For " + ArreFilaColumnaToken.get(pos - 1);
                    errores.add(error);
                }//No es valor o identificador

            }//Hay otro
            else {
                error = "Error [8], se esperaba numero o identificador en el ciclo For " + ArreFilaColumnaToken.get(pos - 1);
                errores.add(error);
            }
        } else {
            error = "Error [7], se esperaba parentesis que abre '(' en el ciclo For " + ArreFilaColumnaToken.get(pos - 1);
            errores.add(error);
        }
        return false;
    }

    private boolean operacionCorrecta() {
        if (hayOtro()) {
            switch (ArreNomToken.get(pos)) {
                /* Variables */
                case "Identificador":
                    System.out.println("Identificador encontrado");
                    gramaticas.add("Identificador");
                    varNombre = ArreToken.get(pos);
                    System.out.println(ANSI_PURPLE + "Nombre de la variable: " + ArreToken.get(pos) + ANSI_RESET);
                    pos++;
                    if (hayOtro()) {
                        if (identificadorCorrecto()) {
                            gramaticas.add("Identificador correcto \n");
                            return true;
                        }
                    }//Hay otro
                    else {
                        errores.add("Error [2], se esperaba operador de asignacion en el identificador " + ArreFilaColumnaToken.get(pos - 1));
                    }

                    break;
                case "For":
                    System.out.println("For encontrado");
                    gramaticas.add("For");
                    pos++;
                    if (hayOtro()) {
                        if (forCorrecto()) {
                            gramaticas.add("For correcto \n");
                            return true;
                        }

                    } else {
                        errores.add("Error [7], se esperaba parentesis que abre '(' en el ciclo For " + ArreFilaColumnaToken.get(pos - 1));
                    }
                    break;
                /* If */
                case "If":
                    System.out.println("If encontrada");
                    gramaticas.add("If");
                    pos++;
                    if (hayOtro()) {
                        if (ifCorrecta()) {
                            gramaticas.add("If correcto \n");
                            return true;
                        }

                    } else {
                        errores.add("Error [27], se esperaba parentesis que abre '(' en el if " + ArreFilaColumnaToken.get(pos - 1));
                    }
                    break;

                /* While */
                case "While":
                    System.out.println("While encontrada");
                    gramaticas.add("While");
                    pos++;
                    if (hayOtro()) {
                        if (whileCorrecta()) {
                            gramaticas.add("While correcto \n");
                            return true;
                        }

                    } else {
                        errores.add("Error [33], se esperaba parentesis que abre '(' en el while " + ArreFilaColumnaToken.get(pos - 1));

                    }

                    break;

                default:
                    boolean resp = buscarFunciones();
                    if (resp) {
                        return true;
                    }

            }
        } else {
            errores.add("Error [39], se esperaba llave que cierra '}' en el bloque PRINCIPAL " + ArreFilaColumnaToken.get(pos - 1));
        }
        return false;
    }

    private boolean buscarFunciones() {

        String funciones[] = {"OP_Cortadora", "OP_Aspersor", "OP_Ventilador", "OP_Iluminacion", "OP_Puerta", "OP_Banda", "OP_TV", "OP_Alarma", "OP_Caja", "OP_Panel"};
        String erroresFun[] = {""};
        boolean resp = false;
        for (int i = 0; i <= funciones.length - 1; i++) {
            if (ArreNomToken.get(pos).equals(funciones[i])) {
                System.out.println("Se encontró: " + funciones[i]);
                pos++;
                if (hayOtro()) {
                    if (funcionCorrecta()) {
                        gramaticas.add("Funcion correcta \n");
                        resp = true;
                    }
                } else {
                    errores.add("Error [22], se esperaba parentesis que abre '(' en la funcion " + ArreFilaColumnaToken.get(pos - 1));
                }
                i = funciones.length - 1; //Para de buscar para ahorrar tiempo
            }
        }
        return resp;
    }

    private boolean inicioCorrecto() {
        String error = "";

        if (ArreNomToken.get(pos).equals("INICIO")) {
            pos++;
            System.out.println("INICIO encontrado");
            gramaticas.add("Inicio");
            if (hayOtro()) {
                if (ArreNomToken.get(pos).equals("Identificador")) {
                    pos++;
                    System.out.println("Identificador encontrado");
                    gramaticas.add("Identificador");
                    if (hayOtro()) {
                        if (ArreNomToken.get(pos).equals("Llave_A")) {
                            pos++;
                            System.out.println("Llave que abre encontrada");
                            gramaticas.add("Llave que abre");
                            if (hayOtro()) {
                                if (ArreNomToken.get(pos).equals("PRINCIPAL")) {
                                    pos++;
                                    System.out.println("PRINCIPAL encontrado");
                                    gramaticas.add("PRINCIPAL");
                                    if (hayOtro()) {
                                        if (ArreNomToken.get(pos).equals("Parentesis_A")) {
                                            pos++;
                                            System.out.println("Parentesis que abre encontrado");
                                            gramaticas.add("Parentesis que abre");
                                            if (hayOtro()) {
                                                if (ArreNomToken.get(pos).equals("Parentesis_C")) {
                                                    pos++;
                                                    System.out.println("Parentesis que cierra encontrado");
                                                    gramaticas.add("Parentesis que cierra");
                                                    if (hayOtro()) {
                                                        if (ArreNomToken.get(pos).equals("Llave_A")) {
                                                            pos++;
                                                            System.out.println("Llave que abre encontrada");
                                                            gramaticas.add("Llave que abre \n");
                                                            if (hayOtro()) {
                                                                boolean test;
                                                                do {
                                                                    System.out.println(ANSI_YELLOW + "Analizando el codigo dentro de estructura inicial" + ANSI_RESET);
                                                                    test = operacionCorrecta();
                                                                    if (hayOtro()) {
                                                                        if (errores.size() > 0) {
                                                                            System.out.println("Se encontro un error dentro del codigo");
                                                                            //gramaticas.add("El Token: " + ArreToken.get(pos) + " no es un 'TERMINAL'");
                                                                            //System.out.println("El Token:" + ArreToken.get(pos) + " no es un 'TERMINAL'");
                                                                            return false;
                                                                        }
                                                                    }//hay otro
                                                                    else {
                                                                        //error = "Error[XX], No hay tokens para analizar " + ArreFilaColumnaToken.get(pos - 1);
                                                                        //errores.add(error);
                                                                        System.out.println("Error[XX], No hay tokens para analizar" + ArreFilaColumnaToken.get(pos - 1));
                                                                    }

                                                                } while (test);
                                                                System.out.println("Saliendo de analizar el codigo dentro de la estructura de inicio");
                                                                /* Continua con el bloque de inicio */
                                                                if (hayOtro()) {
                                                                    if (ArreNomToken.get(pos).equals("Llave_C")) {
                                                                        pos++;
                                                                        System.out.println("Llave que cierra encontrada");
                                                                        gramaticas.add("Llave que cierra");
                                                                        if (hayOtro()) {
                                                                            if (ArreNomToken.get(pos).equals("Llave_C")) {
                                                                                pos++;
                                                                                System.out.println("Llave que cierra encontrada");
                                                                                gramaticas.add("Llave que cierra");
                                                                                if (hayOtro()) {
                                                                                    if (ArreNomToken.get(pos).equals("FINAL")) {
                                                                                        pos++;
                                                                                        System.out.println("FINAL encontrado");
                                                                                        gramaticas.add("FINAL");
                                                                                        if (ArreNomToken.size() > pos) {
                                                                                            error = "Error [20], se encontraron tokens despues de 'FINAL' eliminalos " + ArreFilaColumnaToken.get(pos - 1);
                                                                                            errores.add(error);
                                                                                        } else {
                                                                                            System.out.println("Todo el código es correcto");
                                                                                            gramaticas.add("INICIO Correcto \n");
                                                                                            return true;
                                                                                        }
                                                                                    } // FINAL
                                                                                    else {
                                                                                        error = "Error [19], se esperaba 'FINAL' en el bloque inicial " + ArreFilaColumnaToken.get(pos - 1);
                                                                                        errores.add(error);
                                                                                    } // FINAL
                                                                                }//Hay otro
                                                                                else {
                                                                                    error = "Error [19], se esperaba 'FINAL' en el bloque inicial " + ArreFilaColumnaToken.get(pos - 1);
                                                                                    errores.add(error);
                                                                                }
                                                                            } // } FINAL
                                                                            else {
                                                                                error = "Error [18], se esperaba llave que cierra '}' en el bloque inicial " + ArreFilaColumnaToken.get(pos - 1);
                                                                                errores.add(error);
                                                                            } // } FINAL
                                                                        }//Hay otro
                                                                        else {
                                                                            error = "Error [18], se esperaba llave que cierra '}' en el bloque inicial " + ArreFilaColumnaToken.get(pos - 1);
                                                                            errores.add(error);
                                                                        }
                                                                    } // } PRINCIPAL
                                                                    else {
                                                                        error = "Error [39], se esperaba llave que cierra '}' en el bloque PRINCIPAL " + ArreFilaColumnaToken.get(pos - 1);
                                                                        errores.add(error);
                                                                    } // } PRINCIPAL
                                                                }//Hay otro
                                                                else {
                                                                    error = "Error [39], se esperaba llave que cierra '}' en el bloque PRINCIPAL " + ArreFilaColumnaToken.get(pos - 1);
                                                                    errores.add(error);
                                                                }
                                                            }//hay otro
                                                            else {
                                                                error = "Error[21], se esperaba codigo para analizar en el bloque inicial " + ArreFilaColumnaToken.get(pos - 1);
                                                                errores.add(error);
                                                            }
                                                        } // { PRINCIPAL
                                                        else {
                                                            error = "Error [42], se esperaba llave que abre '{' en el bloque PRINCIPAL " + ArreFilaColumnaToken.get(pos - 1);
                                                            errores.add(error);
                                                        } // { PRINCIPAL
                                                    }//Hay otro
                                                    else {
                                                        error = "Error [42], se esperaba llave que abre '{' en el bloque PRINCIPAL " + ArreFilaColumnaToken.get(pos - 1);
                                                        errores.add(error);
                                                    }
                                                } // )
                                                else {
                                                    error = "Error [41], se esperaba parentesis que cierra ')' en el bloque PRINCIPAL " + ArreFilaColumnaToken.get(pos - 1);
                                                    errores.add(error);
                                                } // )
                                            }//Hay otro
                                            else {
                                                error = "Error [41], se esperaba parentesis que cierra ')' en el bloque PRINCIPAL " + ArreFilaColumnaToken.get(pos - 1);
                                                errores.add(error);
                                            }
                                        } // (
                                        else {
                                            error = "Error [40], se esperaba parentesis que abre '(' en el bloque PRINCIPAL " + ArreFilaColumnaToken.get(pos - 1);
                                            errores.add(error);
                                        } // (
                                    }//Hay otro
                                    else {
                                        error = "Error [40], se esperaba parentesis que abre '(' en el bloque PRINCIPAL " + ArreFilaColumnaToken.get(pos - 1);
                                        errores.add(error);
                                    }
                                } // PRINCIPAL
                                else {
                                    error = "Error [15], se esperaba 'PRINCIPAL' en el bloque inicial " + ArreFilaColumnaToken.get(pos - 1);
                                    errores.add(error);
                                } // PRINCIPAL
                            }//Hay otro
                            else {
                                error = "Error [15], se esperaba 'PRINCIPAL' en el bloque inicial " + ArreFilaColumnaToken.get(pos - 1);
                                errores.add(error);
                            }
                        } // {
                        else {
                            error = "Error [14], se esperaba llave que abre '{' en el bloque inicial " + ArreFilaColumnaToken.get(pos - 1);
                            errores.add(error);
                        } // {
                    }//Hay otro
                    else {
                        error = "Error [14], se esperaba llave que abre '{' en el bloque inicial " + ArreFilaColumnaToken.get(pos - 1);
                        errores.add(error);
                    }
                } // Identificador
                else {
                    error = "Error [13], se esperaba un identificador en el bloque inicial " + ArreFilaColumnaToken.get(pos - 1);
                    errores.add(error);
                } // Identificador
            }//Hay otro
            else {
                error = "Error [13], se esperaba un identificador en el bloque inicial " + ArreFilaColumnaToken.get(pos - 1);
                errores.add(error);
            }
        } // INICIO
        else {
            System.out.println("Entrando al else");
            error = "Error [12], se esperaba 'INICIO' en el bloque inicial " + ArreFilaColumnaToken.get(pos);
            errores.add(error);
            System.out.println("Saliendo del else");
        } // INICIO
        System.out.println("Entrando al return false");
        return false;
    }

    private boolean funcionCorrecta() {
        String error = "";
        gramaticas.add("Funcion");
        if (ArreNomToken.get(pos).equals("Parentesis_A")) {
            pos++;
            System.out.println("Parentecis que abre encontrado");
            gramaticas.add("Parentecis que abre");
            /* Verifica parametros */

            if (hayOtro()) {
                if (ArreToken.get(pos - 2).equals("alarma_activar") || ArreToken.get(pos - 2).equals("alarma_desactivar")) {
                    System.out.println(ANSI_CYAN + "Evaluando: " + ArreToken.get(pos - 2) + ANSI_RESET);

                    if (finFuncion()) {
                        return true;
                    }
                } //System.out.println(ANSI_PURPLE+"Palabra reservada: " + ArreToken.get(pos-2)+ANSI_RESET);
                else if (ArreToken.get(pos - 2).equals("cortadora_activar") || ArreToken.get(pos - 2).equals("cortadora_desactivar")
                        || ArreToken.get(pos - 2).equals("ventilador_activar") || ArreToken.get(pos - 2).equals("ventilador_desactivar")
                        || ArreToken.get(pos - 2).equals("iluminacion_activar") || ArreToken.get(pos - 2).equals("iluminacion_desactivar")
                        || ArreToken.get(pos - 2).equals("banda_activar") || ArreToken.get(pos - 2).equals("banda_desactivar")
                        || ArreToken.get(pos - 2).equals("puerta_abrir") || ArreToken.get(pos - 2).equals("puerta_cerrar")
                        || ArreToken.get(pos - 2).equals("tv_encender") || ArreToken.get(pos - 2).equals("tv_apagar")
                        || ArreToken.get(pos - 2).equals("aspersor_desactivar") || ArreToken.get(pos - 2).equals("aspersor_desactivar")
                        || ArreToken.get(pos - 2).equals("cajafuerte_desactivar")) {
                    System.out.println(ANSI_CYAN + "Evaluando: " + ArreToken.get(pos - 2) + ANSI_RESET);
                    fncCoVNombre = ArreToken.get(pos - 2);
                    System.out.println(ANSI_PURPLE + "Nombre de la funcion: " + fncCoVNombre + ANSI_RESET);
                    //fncCoVStatus = asignaStatus_fncCoV(fncCoVNombre);
                    switch (ArreNomToken.get(pos)) {
                        case "Cadena":
                            System.out.println("Cadena encontrada");
                            fncCoVValor = ArreToken.get(pos);

                            System.out.println(ANSI_PURPLE + "Valor: " + fncCoVValor + ANSI_RESET);
                            fncCoVTipo = "Cadena";
                            System.out.println(ANSI_PURPLE + "Tipo: " + fncCoVTipo + ANSI_RESET);
                            fncCoVFila_Columna = ArreFilaColumnaToken.get(pos - 1);
                            System.out.println(ANSI_PURPLE + "Fila_Columna: " + fncCoVFila_Columna + ANSI_RESET);
                            ArreFunciones_CadenaoVariable.add(new Funciones_CadenaoVariable(fncCoVNombre, fncCoVValor, fncCoVTipo, fncCoVFila_Columna));
                            break;
                        case "Identificador":
                            System.out.println("Identificador encontrado");
                            fncCoVValor = ArreToken.get(pos);
                            System.out.println(ANSI_PURPLE + "Valor: " + fncCoVValor + ANSI_RESET);
                            fncCoVTipo = "Identificador";
                            System.out.println(ANSI_PURPLE + "Tipo: " + fncCoVTipo + ANSI_RESET);
                            fncCoVFila_Columna = ArreFilaColumnaToken.get(pos - 1);
                            System.out.println(ANSI_PURPLE + "Fila_Columna: " + fncCoVFila_Columna + ANSI_RESET);
                            ArreFunciones_CadenaoVariable.add(new Funciones_CadenaoVariable(fncCoVNombre, fncCoVValor, fncCoVTipo, fncCoVFila_Columna));
                            break;
                        default:
                            error = "Error [25], se esperaba una cadena o un identificador en la funcion: " + ArreToken.get(pos - 2) + " " + ArreFilaColumnaToken.get(pos - 1);
                            errores.add(error);
                    }
                    pos++;
                    if (hayOtro()) {
                        if (finFuncion()) {
                            return true;
                        }

                    }//Hay otro
                    else {
                        error = "Error [23], se esperaba parentesis que cierra ')' en la funcion: " + ArreToken.get(pos - 3) + " " + ArreFilaColumnaToken.get(pos - 1);
                        errores.add(error);
                    }
                }//If varias funciones
                else if (ArreToken.get(pos - 2).equals("aspersor_activar") || ArreToken.get(pos - 2).equals("cajafuerte_activar")) {
                    System.out.println(ANSI_CYAN + "Evaluando: " + ArreToken.get(pos - 2) + ANSI_RESET);
                    fncCoV_NoVNombre = ArreToken.get(pos - 2);
                    System.out.println(ANSI_PURPLE + "Nombre de la funcion: " + fncCoV_NoVNombre + ANSI_RESET);
                    switch (ArreNomToken.get(pos)) {
                        case "Cadena":
                            System.out.println("Cadena encontrada");

                            fncCoV_NoVValor_CoV = ArreToken.get(pos);
                            System.out.println(ANSI_PURPLE + "Valor CoV: " + fncCoV_NoVValor_CoV + ANSI_RESET);

                            fncCoV_NoVTipo_CoV = "Cadena";
                            System.out.println(ANSI_PURPLE + "Tipo: " + fncCoV_NoVTipo_CoV + ANSI_RESET);
                            break;
                        case "Identificador":
                            System.out.println("Identificador encontrado");
                            fncCoV_NoVValor_CoV = ArreToken.get(pos);
                            System.out.println(ANSI_PURPLE + "Valor CoV: " + fncCoV_NoVValor_CoV + ANSI_RESET);

                            fncCoV_NoVTipo_CoV = "Identificador";
                            System.out.println(ANSI_PURPLE + "Tipo: " + fncCoV_NoVTipo_CoV + ANSI_RESET);
                            break;
                        default:
                            error = "Error [25], se esperaba una cadena o un identificador en la funcion: " + ArreToken.get(pos - 2) + " " + ArreFilaColumnaToken.get(pos - 1);
                            errores.add(error);
                    }
                    pos++;
                    if (hayOtro()) {
                        if (ArreNomToken.get(pos).equals("Coma")) {
                            pos++;
                            System.out.println("Coma encontrada");
                            if (hayOtro()) {
                                switch (ArreNomToken.get(pos)) {
                                    case "Numero":
                                        System.out.println("Numero encontrado");
                                        fncCoV_NoVValor_NoV = ArreToken.get(pos);
                                        System.out.println(ANSI_PURPLE + "Valor NoV: " + fncCoV_NoVValor_NoV + ANSI_RESET);

                                        fncCoV_NoVTipo_NoV = "Numero";
                                        System.out.println(ANSI_PURPLE + "Tipo: " + fncCoV_NoVTipo_NoV + ANSI_RESET);
                                        fncCoV_NoVFila_columna = ArreFilaColumnaToken.get(pos - 1);
                                        System.out.println(ANSI_PURPLE + "Fila_Columna: " + fncCoV_NoVFila_columna + ANSI_RESET);
                                        ArreFunciones_CoV_NoV.add(new Funciones_CoV_NoV(fncCoV_NoVNombre, fncCoV_NoVValor_CoV, fncCoV_NoVTipo_CoV, fncCoV_NoVValor_NoV, fncCoV_NoVTipo_NoV, fncCoV_NoVFila_columna));

                                        break;
                                    case "Identificador":
                                        System.out.println("Identificador encontrado");
                                        fncCoV_NoVValor_NoV = ArreToken.get(pos);
                                        System.out.println(ANSI_PURPLE + "Valor CoV: " + fncCoV_NoVValor_NoV + ANSI_RESET);

                                        fncCoV_NoVTipo_NoV = "Identificador";
                                        System.out.println(ANSI_PURPLE + "Tipo: " + fncCoV_NoVTipo_NoV + ANSI_RESET);

                                        fncCoV_NoVFila_columna = ArreFilaColumnaToken.get(pos - 1);
                                        System.out.println(ANSI_PURPLE + "Fila_Columna: " + fncCoV_NoVFila_columna + ANSI_RESET);

                                        ArreFunciones_CoV_NoV.add(new Funciones_CoV_NoV(fncCoV_NoVNombre, fncCoV_NoVValor_CoV, fncCoV_NoVTipo_CoV, fncCoV_NoVValor_NoV, fncCoV_NoVTipo_NoV, fncCoV_NoVFila_columna));
                                        break;
                                    default:
                                        error = "Error [44], se esperaba una número o un identificador en la funcion: " + ArreToken.get(pos - 4) + " " + ArreFilaColumnaToken.get(pos - 1);
                                        errores.add(error);
                                }
                                pos++;
                                if (hayOtro()) {
                                    if (finFuncion()) {
                                        return true;
                                    }

                                }//Hay otro
                                else {
                                    error = "Error [23], se esperaba parentesis que cierra ')' en la funcion: " + ArreToken.get(pos - 3) + " " + ArreFilaColumnaToken.get(pos - 1);
                                    errores.add(error);
                                }
                            }//Hay otro
                            else {
                                error = "Error [44], se esperaba una número o un identificador en la funcion: " + ArreToken.get(pos - 4) + " " + ArreFilaColumnaToken.get(pos - 1);
                                errores.add(error);
                            }

                        } // Coma
                        else {
                            error = "Error [43], se esperaba coma ',' en la función: " + ArreToken.get(pos - 2) + ArreFilaColumnaToken.get(pos - 1);
                            errores.add(error);
                        } // Coma
                    }//Hay otro
                    else {
                        error = "Error [43], se esperaba coma ',' en la función: " + ArreToken.get(pos - 2) + ArreFilaColumnaToken.get(pos - 1);
                        errores.add(error);
                    }
                } else if (ArreToken.get(pos - 2).equals("panel_girar")) {
                    System.out.println(ANSI_CYAN + "Evaluando: " + ArreToken.get(pos - 2) + ANSI_RESET);
                    switch (ArreNomToken.get(pos)) {
                        case "Numero":
                            System.out.println("Cadena encontrada");
                            break;
                        case "Identificador":
                            System.out.println("Identificador encontrado");
                            break;
                        default:
                            error = "Error [44], se esperaba una número o un identificador en la funcion: " + ArreToken.get(pos - 2) + " " + ArreFilaColumnaToken.get(pos - 1);
                            errores.add(error);
                    }
                    pos++;
                    if (hayOtro()) {
                        if (finFuncion()) {
                            return true;
                        }
                    } else {
                        error = "Error [23], se esperaba parentesis que cierra ')' en la funcion: " + ArreToken.get(pos - 3) + " " + ArreFilaColumnaToken.get(pos - 1);
                        errores.add(error);
                    }
                }

//                if (finFuncion()) {
//                    return true;
//                }//No se metió ningun valor y se encontró un parentesis que cierra
            } //No hay otro valor
            else {
                if (ArreToken.get(pos - 2).equals("alarma_activar") || ArreToken.get(pos - 2).equals("alarma_desactivar")) {
                    error = "Error [23], se esperaba parentesis que cierra ')' en la funcion: " + ArreToken.get(pos - 2) + " " + ArreFilaColumnaToken.get(pos - 1);
                    errores.add(error);
                } else if (ArreToken.get(pos - 2).equals("panel_girar")) {
                    error = "Error [44], se esperaba una número o un identificador en la funcion: " + ArreToken.get(pos - 2) + " " + ArreFilaColumnaToken.get(pos - 1);
                    errores.add(error);
                } else {
                    error = "Error [25], se esperaba una cadena o un identificador en la funcion: " + ArreToken.get(pos - 2) + " " + ArreFilaColumnaToken.get(pos - 1);
                    errores.add(error);
                }

            }

        } else {
            error = "Error [22], se esperaba parentesis que abre '(' en la funcion " + ArreFilaColumnaToken.get(pos - 1);
            errores.add(error);
        }
        return false;
    }

    private boolean valorCorrecto() {
        String error = "";
        switch (ArreNomToken.get(pos)) {
            case "Numero":
                pos++;
                System.out.println("Numero encontrado");
                gramaticas.add("Numero");

                return true;
            case "Cadena":
                pos++;
                System.out.println("Cadena encontrada");
                gramaticas.add("Cadena");

                return true;
//            case "OP_Retorno":
//                System.out.println("OP_Retorno encontrada");
//                gramaticas.add("OP_Retorno");
//                pos++;
//                if (hayOtro()) {
//                    if (!op_RetornoCorrecto()) {
//                        System.out.println("OP_Retorno incorrecta");
//                    }//No está correcta
//                    else {
//                        gramaticas.add("OP_Retorno Correcta");
//                        return true;
//                    }//Si está correcta
//                }//Hay otro
//                else {
//                    errores.add("Error [5], se esperaba parentesis que abre '(' en la OP_Retorno " + ArreFilaColumnaToken.get(pos - 1));
//                }

//                break;
            default:

        }//Switch

        return false;
    }

    private boolean finFuncion() {
        String error = "";
        if (ArreNomToken.get(pos).equals("Parentesis_C")) {
            pos++;
            System.out.println("Parentesis que cierra encontrado");
            gramaticas.add("Parentesis que cierra");
            if (hayOtro()) {
                if (ArreNomToken.get(pos).equals("Punto_Coma")) {
                    pos++;
                    System.out.println("Punto y Coma encontrado");
                    gramaticas.add("Punto y coma");
                    return true;
                    //Es correcto
                } else {
                    error = "Error [24], se esperaba punto y coma ';' en la funcion " + ArreFilaColumnaToken.get(pos - 1);
                    errores.add(error);
                }
            } else {
                error = "Error [24], se esperaba punto y coma ';' en la funcion " + ArreFilaColumnaToken.get(pos - 1);
                errores.add(error);
            }

        } else {
            error = "Error [23], se esperaba parentesis que cierra ')' en la funcion: " + ArreToken.get(pos - 2) + ArreFilaColumnaToken.get(pos - 1);
            errores.add(error);
        }
        return false;
    }

    private boolean ifCorrecta() {
        String error = "";
        if (ArreNomToken.get(pos).equals("Parentesis_A")) {
            pos++;
            System.out.println("Parentecis que abre encontrado");
            gramaticas.add("Parentecis que abre");
            if (hayOtro()) {
                if (valorCorrecto() || ArreNomToken.get(pos++).equals("Identificador")) {
                    System.out.println("Valor o identificador encontrado");
                    gramaticas.add("Valor o identificador");
                    if (hayOtro()) {
                        if (ArreNomToken.get(pos).equals("Op_Relacional")) {
                            pos++;
                            System.out.println("Op_Relacional encontrado");
                            gramaticas.add("Op_Relacional");
                            if (hayOtro()) {
                                if (valorCorrecto() || ArreNomToken.get(pos++).equals("Identificador")) {
                                    System.out.println("Valor o identificador encontrado");
                                    gramaticas.add("Valor o identificador");
                                    if (hayOtro()) {
                                        if (ArreNomToken.get(pos).equals("Parentesis_C")) {
                                            pos++;
                                            System.out.println("Parentesis que cierra encontrado");
                                            gramaticas.add("Parentesis que cierra");
                                            if (hayOtro()) {
                                                if (ArreNomToken.get(pos).equals("Llave_A")) {
                                                    pos++;
                                                    System.out.println("Llave que abre encontrada");
                                                    gramaticas.add("Llave que abre \n");
                                                    if (hayOtro()) {
                                                        boolean test;
                                                        do {
                                                            System.out.println("Analiznado el codigo dentro de estructura if");
                                                            test = operacionCorrecta();
                                                            if (errores.size() > 0) {
                                                                System.out.println("Se encontro un error dentro del codigo en el if");
                                                                return false;
                                                            }
                                                        } while (test);
                                                        System.out.println("Saliendo de analizar el codigo dentro de la estructura de if");
                                                        if (hayOtro()) {
                                                            if (ArreNomToken.get(pos).equals("Llave_C")) {
                                                                pos++;
                                                                System.out.println("Llave que cierra encontrada");
                                                                gramaticas.add("Llave que cierra");
                                                                return true;
                                                            } // }
                                                            else {
                                                                error = "Error [32], se esperaba llave que cierra '}' en el if " + ArreFilaColumnaToken.get(pos - 1);
                                                                errores.add(error);
                                                            } // }
                                                        }//Hay otro
                                                        else {
                                                            error = "Error [32], se esperaba llave que cierra '}' en el if " + ArreFilaColumnaToken.get(pos - 1);
                                                            errores.add(error);
                                                        }
                                                    }//Hay otro
                                                    else {
                                                        error = "Error [32], se esperaba llave que cierra '}' en el if " + ArreFilaColumnaToken.get(pos - 1);
                                                        errores.add(error);
                                                    }
                                                } // 
                                                else {
                                                    error = "Error [31], se esperaba llave que abre '{' en el if " + ArreFilaColumnaToken.get(pos - 1);
                                                    errores.add(error);
                                                } // 
                                            }//Hay otro
                                            else {
                                                error = "Error [31], se esperaba llave que abre '{' en el if " + ArreFilaColumnaToken.get(pos - 1);
                                                errores.add(error);
                                            }
                                        } // 
                                        else {
                                            error = "Error [30], se esperaba parentesis que cierra ')' en el if  " + ArreFilaColumnaToken.get(pos - 1);
                                            errores.add(error);
                                        } // 
                                    }//Hay otro
                                    else {
                                        error = "Error [30], se esperaba parentesis que cierra ')' en el if  " + ArreFilaColumnaToken.get(pos - 1);
                                        errores.add(error);
                                    }

                                } // valor o identificador
                                else {
                                    if (errores.size() > 0) {
                                        System.out.println("Se encontro un error analizando el if");
                                        return false;
                                    }
                                    error = "Error [28], se esperaba valor o identificador en el if " + ArreFilaColumnaToken.get(pos - 1);
                                    errores.add(error);
                                } // valor o identificador
                            }//Hay otro
                            else {
                                error = "Error [28], se esperaba valor o identificador en el if " + ArreFilaColumnaToken.get(pos - 1);
                                errores.add(error);
                            }
                        } // Operador relacional
                        else {
                            error = "Error [29], se esperaba operador relacional en el if " + ArreFilaColumnaToken.get(pos - 1);
                            errores.add(error);
                        } // Operador relacional
                    }//Hay otro
                    else {
                        error = "Error [29], se esperaba operador relacional en el if " + ArreFilaColumnaToken.get(pos - 1);
                        errores.add(error);
                    }

                } // valor o identificador correcto
                else {
                    if (errores.size() > 0) {
                        System.out.println("Se encontro un error analizando el if");
                        return false;
                    }
                    error = "Error [28], se esperaba valor o identificador en el if " + ArreFilaColumnaToken.get(pos - 1);
                    errores.add(error);
                } // valor o identificador correcto
            }//Hay otro
            else {
                error = "Error [28], se esperaba valor o identificador en el if " + ArreFilaColumnaToken.get(pos - 1);
                errores.add(error);
            }
        } else {
            error = "Error [27], se esperaba parentesis que abre '(' en el if " + ArreFilaColumnaToken.get(pos - 1);
            errores.add(error);
        }
        return false;
    }

    private boolean whileCorrecta() {
        String error = "";
        if (ArreNomToken.get(pos).equals("Parentesis_A")) {
            pos++;
            System.out.println("Parentecis que abre encontrado");
            gramaticas.add("Parentesis que abre");
            if (hayOtro()) {
                if (valorCorrecto() || ArreNomToken.get(pos++).equals("Identificador")) {
                    System.out.println("Valor o identificador encontrado");
                    gramaticas.add("Valor o identificador");
                    if (hayOtro()) {
                        if (ArreNomToken.get(pos).equals("Op_Relacional")) {
                            pos++;
                            System.out.println("Op_Relacional encontrado");
                            gramaticas.add("Op_Relacional");
                            if (hayOtro()) {
                                if (valorCorrecto() || ArreNomToken.get(pos++).equals("Identificador")) {
                                    System.out.println("Valor o identificador encontrado");
                                    gramaticas.add("Valor o identificador");
                                    if (hayOtro()) {
                                        if (ArreNomToken.get(pos).equals("Parentesis_C")) {
                                            pos++;
                                            System.out.println("Parentesis que cierra encontrado");
                                            gramaticas.add("Parentesis que cierra");
                                            if (hayOtro()) {
                                                if (ArreNomToken.get(pos).equals("Llave_A")) {
                                                    pos++;
                                                    System.out.println("Llave que abre encontrada");
                                                    gramaticas.add("Llave que abre \n");
                                                    if (hayOtro()) {
                                                        boolean test;
                                                        do {
                                                            System.out.println("Analizando el codigo dentro de estructura while");
                                                            test = operacionCorrecta();
                                                            if (errores.size() > 0) {
                                                                System.out.println("Se encontro un error dentro del codigo en el while");
                                                                return false;
                                                            }
                                                        } while (test);
                                                        System.out.println("Saliendo de analizar el codigo dentro de la estructura de while");
                                                        if (hayOtro()) {
                                                            if (ArreNomToken.get(pos).equals("Llave_C")) {
                                                                pos++;
                                                                System.out.println("Llave que cierra encontrada");
                                                                gramaticas.add("Llave que cierra");
                                                                return true;
                                                            } // }
                                                            else {
                                                                error = "Error [38], se esperaba llave que cierra '}' en el while  " + ArreFilaColumnaToken.get(pos - 1);
                                                                errores.add(error);
                                                            } // }
                                                        }//Hay otro
                                                        else {
                                                            error = "Error [38], se esperaba llave que cierra '}' en el while  " + ArreFilaColumnaToken.get(pos - 1);
                                                            errores.add(error);
                                                        }
                                                    }//Hay otro
                                                    else {
                                                        error = "Error [], se esperaba   " + ArreFilaColumnaToken.get(pos - 1);
                                                        errores.add(error);
                                                    }
                                                } // 
                                                else {
                                                    error = "Error [37], se esperaba llave que abre '{' en el while  " + ArreFilaColumnaToken.get(pos - 1);
                                                    errores.add(error);
                                                } // 
                                            }//Hay otro
                                            else {
                                                error = "Error [37], se esperaba llave que abre '{' en el while  " + ArreFilaColumnaToken.get(pos - 1);
                                                errores.add(error);
                                            }
                                        } // 
                                        else {
                                            error = "Error [36], se esperaba parentesis que cierra ')' en el while   " + ArreFilaColumnaToken.get(pos - 1);
                                            errores.add(error);
                                        } // 
                                    }//Hay otro
                                    else {
                                        error = "Error [36], se esperaba parentesis que cierra ')' en el while   " + ArreFilaColumnaToken.get(pos - 1);
                                        errores.add(error);
                                    }

                                } // valor o identificador
                                else {
                                    if (errores.size() > 0) {
                                        System.out.println("Se encontro un error analizando el while");
                                        return false;
                                    }
                                    error = "Error [34], se esperaba valor o identificador en el while  " + ArreFilaColumnaToken.get(pos - 1);
                                    errores.add(error);
                                } // valor o identificador
                            }//Hay otro
                            else {
                                error = "Error [34], se esperaba valor o identificador en el while  " + ArreFilaColumnaToken.get(pos - 1);
                                errores.add(error);
                            }
                        } // Operador relacional
                        else {
                            error = "Error [35], se esperaba operador relacional en el while  " + ArreFilaColumnaToken.get(pos - 1);
                            errores.add(error);
                        } // Operador relacional
                    }//Hay otro
                    else {
                        error = "Error [35], se esperaba operador relacional en el while  " + ArreFilaColumnaToken.get(pos - 1);
                        errores.add(error);
                    }

                } // valor o identificador correcto
                else {
                    if (errores.size() > 0) {
                        System.out.println("Se encontro un error analizando el if");
                        return false;
                    }
                    error = "Error [34], se esperaba valor o identificador en el while  " + ArreFilaColumnaToken.get(pos - 1);
                    errores.add(error);
                } // valor o identificador correcto
            }//Hay otro
            else {
                error = "Error [34], se esperaba valor o identificador en el while  " + ArreFilaColumnaToken.get(pos - 1);
                errores.add(error);
            }
        } else {
            error = "Error [33], se esperaba parentesis que abre '(' en el while " + ArreFilaColumnaToken.get(pos - 1);
            errores.add(error);
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

    private String asignaStatus_fncCoV(String nombreFuncion) {
        String status = "";
        switch (nombreFuncion) {
            case "puerta_abrir":
                status = "abierto";
                break;
            case "puerta_cerrar":
                status = "cerrado";
            default:

        }
        return status;
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
            System.out.println("vvfncCoV: " + vvfncCoV);
            System.out.println("vvfncNoV: " + vvfncNoV);
            System.out.println("No. variables: " + ArreVariables.size());
            if (ArreVariables.size() > 0) {

                for (int k = 0; k < ArreVariables.size(); k++) {
                    Variables variable = ArreVariables.get(k);
                    //Verifica si la variable de la funcion está declarada antes de llamarla
                    System.out.println("");
                    System.out.println("Nom fnc i -> " + funcion.nombre() + " Valor CoV fnc i -> " + funcion.valor_CoV() + " Valor NoV fnc i ->" + funcion.valor_NoV() + /*" Tipo -> "+ ArreVariables.get(i).tipo()+ */ " Fila_Columna -> " + funcion.fila_columna());
                    System.out.println("Nom variable k -> " + variable.nombre() +/* " Valor k -> " + ArreVariables.get(k).valor()+ " Tipo -> "+ ArreVariables.get(k).tipo()+ */ " Fila_Columna -> " + variable.fila_columna());

                    if (funcion.fila() > variable.fila()) {
                        nv = variable.nombre(); //Nombre de la variable
                        fcv = variable.fila_columna(); //Fila y columna donde está declarada la variable

                        if (funcion.tipo_CoV().equals("Identificador")) {
                            System.out.println("Si es un identificador Procediendo...");

                            if (!funcion.valor_CoV().equals(variable.nombre())) {
                                System.out.println(ANSI_RED + "Variables diferentes detectadas" + ANSI_RESET);
                                resp = true; //No está declarada la variable
                            } else {
                                //Ambas variables son igual
                                System.out.println(ANSI_GREEN + "Ambas variables son iguales CoV Procediendo..." + ANSI_RESET);
                                resp = false;
                                k = ArreVariables.size() - 1;
                            }
                        }//if identificador
                        else {
                            System.out.println("Es cadena Procediendo...");
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
                    System.out.println("");
                    System.out.println("Nom fnc i -> " + funcion.nombre() + " Valor CoV fnc i -> " + funcion.valor_CoV() + " Valor NoV fnc i ->" + funcion.valor_NoV() + /*" Tipo -> "+ ArreVariables.get(i).tipo()+ */ " Fila_Columna -> " + funcion.fila_columna());
                    System.out.println("Nom variable k -> " + variable.nombre() +/* " Valor k -> " + ArreVariables.get(k).valor()+ " Tipo -> "+ ArreVariables.get(k).tipo()+ */ " Fila_Columna -> " + variable.fila_columna());

                    if (funcion.fila() > variable.fila()) {
                        nv = variable.nombre(); //Nombre de la variable
                        fcv = variable.fila_columna(); //Fila y columna donde está declarada la variable

                        if (funcion.tipo_NoV().equals("Identificador")) {
                            System.out.println("Si es un identificador Procediendo...");

                            if (!funcion.valor_NoV().equals(variable.nombre())) {
                                System.out.println(ANSI_RED + "Variables diferentes detectadas" + ANSI_RESET);
                                r = true; //No está declarada la variable
                            } else {
                                //Ambas variables son igual
                                System.out.println(ANSI_GREEN + "Ambas variables son iguales NoV Procediendo..." + ANSI_RESET);
                                r = false;
                                k = ArreVariables.size() - 1;
                            }
                            
                        }//if identificador
                        else {
                            System.out.println("Es numero Procediendo...");
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

}
