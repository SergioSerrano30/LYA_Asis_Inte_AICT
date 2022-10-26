
/**
 *
 * @author sergi
 */
public class Funciones_CadenaoVariable {
    private String nombre; //Nombre de la funcion
    private String valor; //Nombre de la variable ingresada
    private String tipo; //Cadena o variable
    private int fila;
    private String columna;
    private String fila_columna;
    private String status;

    public Funciones_CadenaoVariable(String nombre, String valor, String tipo, int fila, String columna, String fila_columna) {
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.fila_columna = fila_columna;
    }

    public Funciones_CadenaoVariable(String nombre, String valor, String tipo, int fila, String columna, String fila_columna, String status) {
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.fila_columna = fila_columna;
        this.status = status;
    }

    public Funciones_CadenaoVariable(String nombre, String valor, String tipo, String fila_columna) {
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
        this.fila_columna = fila_columna;
    }
    
    

    public int fila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public String columna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    } 

    public String status() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String nombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String valor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String tipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String fila_columna() {
        return fila_columna;
    }

    public void setFila_columna(String fila_columna) {
        this.fila_columna = fila_columna;
    }
}
