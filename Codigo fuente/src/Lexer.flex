import compilerTools.Token;

%%
%class Lexer
%type Token
%line
%column
%{
    private Token token(String lexeme, String lexicalComp,int line, int column){
        return new Token(lexeme,lexicalComp, line+1, column+1);
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
    /* comments */
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
{Comentario}|{EspacioEnBlanco} { /* Ignorar */ }
{Cadena} { return token (yytext(), "Cadena", yyline, yycolumn); }
/* Identificador */
\${Identificador} { return token(yytext(), "Identificador", yyline, yycolumn); }


/* ------------ PALABRAS RESERVADAS ------------ */
/*INICIO*/
INICIO { return token(yytext(), "INICIO", yyline, yycolumn); }

/*FINAL*/
FINAL { return token(yytext(), "FINAL", yyline, yycolumn); }

/*PRINCIPAL*/
PRINCIPAL { return token(yytext(), "PRINCIPAL", yyline, yycolumn); }

/* Opciones cortadora */
cortadora_activar |
cortadora_apagar { return token(yytext(), "OP_Cortadora", yyline, yycolumn); }

/* Opciones aspersores */

aspersor_activar |
aspersor_apagar { return token(yytext(), "OP_Aspersor", yyline, yycolumn); }

/* Opciones ventiladores */
ventilador_activar |
ventilador_apagar { return token(yytext(), "OP_Ventilador", yyline, yycolumn); }

/* Opciones iluminación */
iluminacion_activar |
iluminacion_apagar { return token(yytext(), "OP_Iluminacion", yyline, yycolumn); }

/* Opciones puerta */
puerta_abrir |
puerta_cerrar { return token(yytext(), "OP_Puerta", yyline, yycolumn); }

/* Opciones banda transportadora */
banda_activar |
banda_apagar  { return token(yytext(), "OP_Banda", yyline, yycolumn); }

/* Opciones TV's */
tv_encender |
tv_apagar  { return token(yytext(), "OP_TV", yyline, yycolumn); }

/* Opciones alarmas de seguridad */
alarma_activar |
alarma_apagar  { return token(yytext(), "OP_Alarma", yyline, yycolumn); }

/* Opciones caja fuerte */
cajafuerte_activar |
cajafuerte_desactivar  { return token(yytext(), "OP_Caja", yyline, yycolumn); }

/* Opciones panel solar */
panel_activar |
panel_apagar |
panel_girar  { return token(yytext(), "OP_Panel", yyline, yycolumn); }

/* --------------------------------- */

/* Condicionales */

if { return token(yytext(), "If", yyline, yycolumn); }

/* Ciclos */

while { return token(yytext(), "While", yyline, yycolumn); }
for { return token(yytext(), "For", yyline, yycolumn); }

/* Return */

return { return token(yytext(), "Return", yyline, yycolumn); }

/* Operadores Relacional */

">" |
"<" |
">=" |
"<=" |
"==" |
"!=" { return token(yytext(), "Op_Relacional", yyline, yycolumn); }


/* Operador de asignacion */

"=" { return token(yytext(), "Op_Asignacion", yyline, yycolumn); }

/* --------------------------------- */
/* Operadores de agrupacion */

"(" { return token(yytext(), "Parentesis_A", yyline, yycolumn); }
")" { return token(yytext(), "Parentesis_C", yyline, yycolumn); }
"{" { return token(yytext(), "Llave_A", yyline, yycolumn); }
"}" { return token(yytext(), "Llave_C", yyline, yycolumn); }


/* Signos de puntuacion */

";" { return token(yytext(), "Punto_Coma", yyline, yycolumn); }
"," { return token(yytext(), "Coma", yyline, yycolumn); }

/* Numero */

{Numero} { return token(yytext(), "Numero", yyline, yycolumn); }

/* ------------ ERRORES ------------ */

    //Numero erroneo
    0{Numero} { return token(yytext(), "Error_1", yyline, yycolumn); }
    //Identificador erroneo
    {Identificador} { return token(yytext(), "Error_2", yyline, yycolumn); }
. { return token(yytext(), "Error", yyline, yycolumn); }