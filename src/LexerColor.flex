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
    TerminadorDeLinea = (\r|\n)|(\r\n)
    EntradaDeCaracter = [^\r\n]
    EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
    ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
    FinDeLineaComentario = "//" {EntradaDeCaracter}* {TerminadorDeLinea}?
    ContenidoComentario = ( [^*] | \*+ [^/*] )*
    ComentarioDeDocumentacion = "/**" {ContenidoComentario} "*"+ "/"
    
/* Comentarios */
    Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

    /* Identificador */
    Letra = [A-Za-z��_����������]
    Digito = [0-9]
    Identificador = {Letra}({Letra}|{Digito})*
    
    /* Numero */
    Numero = 0 | [1-9][0-9]*
%%

/* Comentarios o espacios en blanco */
{Comentario} { return textColor(yychar, yylength(), new Color(146, 146, 146)); }
{EspacioEnBlanco} {/*Ignorar*/}


/* ------------ PALABRAS RESERVADAS ------------ */
/* Opciones cita */
cita_agendar | cita_cancelar | turno_nuevo | turno_actual | turno_proximo | turno_tiempo | turno_fecha | ilu_encender | ilu_apagar | temp_establecer | temp_subir | temp_bajar | puerta_abrir | puerta_cerrar { return textColor(yychar, yylength(), new Color(22, 196, 59)); } // VERDE
/* --------------------------------- */

/* Condicionales */

if |
else |
while |
for |
break |
end |
class |
return { return textColor(yychar, yylength(), new Color(138, 22, 196)); } // MORADO

/* Operadores logicos */

"&&" |
"||" |
"=" |
"+" |
"-" |
"*" |
"/" { return textColor(yychar, yylength(), new Color(196, 115, 22)); } // NARANJA


. {/* Ignorar */}