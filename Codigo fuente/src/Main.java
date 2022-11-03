public class Main {
    public static void main(String[] omega) {
        ArbolExpresion arbolExpresionArit = new ArbolExpresion();
        arbolExpresionArit.crearArbol("$turno = 5+6 ");
        arbolExpresionArit.mostrarCuadruplos();
        ArbolExpresion arbolExpresionBool = new ArbolExpresion();
        arbolExpresionBool.crearArbol("x=VERDAD>FALSO==VERDAD");
        arbolExpresionBool.mostrarCuadruplos();
    }
}
