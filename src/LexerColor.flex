import compilerTools.TextColor;
import java.awt.Color;

%%
%class LexerColor
%type TextColor
%char
%{
    private TextColor textColor(long start, int size, Color color){
        return new TextColor((int) start, size, color);
    }
%}
 /* Variables de comentarios y espacios */
    TerminadorDeLinea = \r|\n|\r\n
    EntradaDeCaracter = [^\r\n]
    EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
    ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
    FinDeLineaComentario = "//" {EntradaDeCaracter}* {TerminadorDeLinea}?
    ContenidoComentario = ( [^*] | \*+ [^/*] )*
    ComentarioDeDocumentacion = "/**" {ContenidoComentario} "*"+ "/"
    
/* Comentarios */
    Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

    /* Identificador */
    Letra = [A-Za-zÑñ_ÁÉÍÓÚáéíóú]
    Digito = [0-9]
    Identificador = {Letra}({Letra}|{Digito})*
    
    /* Numero */
    Numero = 0 | [1-9][0-9]*
    
    /* Cadena */
    Cadena = \".*\"
%%

/* Comentarios o espacios en blanco */
{Comentario} { return textColor(yychar, yylength(), new Color(146, 146, 146)); } //Gris
{EspacioEnBlanco} {/*Ignorar*/}
{Cadena} { return textColor(yychar, yylength(), new Color(51, 119, 255)); } //Azul

/* IDENTIFICADOR */
\${Identificador} { return textColor(yychar, yylength(), new Color(255, 0, 255)); }

/* ------------ PALABRAS RESERVADAS ------------ */
/* Opciones cita */
cita_agendar | cita_cancelar | turno_nuevo | turno_actual | turno_proximo | turno_tiempo | ilu_encender | ilu_apagar | temp_establecer | temp_subir | temp_bajar | puerta_abrir | puerta_cerrar | 
puerta_status | temp_actual | ilu_status { return textColor(yychar, yylength(), new Color(22, 196, 59)); } // VERDE
/* --------------------------------- */

/* INICIO Y FINAL*/

INICIO | FINAL { return textColor(yychar, yylength(), new Color(255, 121, 255)); } // morado 

/* PRINCIPAL*/
PRINCIPAL { return textColor(yychar, yylength(), new Color(75, 203, 204)); } // AZUL

/* Condicionales */

if |
while |
for |
return { return textColor(yychar, yylength(), new Color(138, 22, 196)); } // MORADO

/* Operadores relacionales */

">" |
"<" |
">=" |
"<=" |
"==" |
"!=" { return textColor(yychar, yylength(), new Color(196, 115, 22)); } // NARANJA


. {/* Ignorar */}