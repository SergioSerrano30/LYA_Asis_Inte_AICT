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
    Letra = [A-Za-zÑñ_ÁÉÍÓÚáéíúó]
    Digito = [0-9]
    Identifier = {Letra}(Letra)|(Digito)*
    
    /* Numero */
    Numero = 0 | [1-9][0-9]*

%%
{Comentario}|{EspacioEnBlanco} { /* Ignorar */ }

. { return teken(yytext(), "ERROR", yyline, yycolumn); }
