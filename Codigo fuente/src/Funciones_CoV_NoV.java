
/**
 *
 * @author sergi
 */
public class Funciones_CoV_NoV {

    private String nombre; //Nombre de la funcion
    private String valor_CoV;
    private String tipo_CoV; //Cadena o variable
    private String valor_NoV;
    private String tipo_NoV; //Cadena o variable
    private int fila;
    private String columna;
    private String fila_columna;
    private String status;

    public Funciones_CoV_NoV(String nombre, String valor, String tipo_CoV, String valor_NoV, String tipo_NoV, int fila, String columna, String fila_columna) {
        this.nombre = nombre; //Nombre de la funcion
        this.valor_CoV = valor; //Nombre de la variable ingresada
        this.tipo_CoV = tipo_CoV;
        this.valor_NoV = valor_NoV;
        this.tipo_NoV = tipo_NoV;
        this.fila = fila;
        this.columna = columna;
        this.fila_columna = fila_columna;
    }

    public Funciones_CoV_NoV(String nombre, String valor, String tipo_CoV, String valor_NoV, String tipo_NoV, int fila, String columna, String fila_columna, String status) {
        this.nombre = nombre;
        this.valor_CoV = valor_CoV;
        this.tipo_CoV = tipo_CoV;
        this.valor_NoV = valor_NoV;
        this.tipo_NoV = tipo_NoV;
        this.fila = fila;
        this.columna = columna;
        this.fila_columna = fila_columna;
        this.status = status;
    }

    public Funciones_CoV_NoV(String nombre, String valor_CoV, String tipo_CoV, String valor_NoV, String tipo_NoV, String fila_columna) {
        this.nombre = nombre;
        this.valor_CoV = valor_CoV;
        this.tipo_CoV = tipo_CoV;
        this.valor_NoV = valor_NoV;
        this.tipo_NoV = tipo_NoV;
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

    public String valor_CoV() {
        return valor_CoV;
    }

    public String valor_NoV() {
        return valor_NoV;
    }

    public void setValor_NoV(String valor_NoV) {
        this.valor_NoV = valor_NoV;
    }

    public void setValor_CoV(String valor_CoV) {
        this.valor_CoV = valor_CoV;
    }

    public String tipo_CoV() {
        return tipo_CoV;
    }

    public void setTipo_CoV(String tipo) {
        this.tipo_CoV = tipo;
    }

    public String tipo_NoV() {
        return tipo_NoV;
    }

    public void setTipo_NoV(String tipo) {
        this.tipo_NoV = tipo;
    }

    public String fila_columna() {
        return fila_columna;
    }

    public void setFila_columna(String fila_columna) {
        this.fila_columna = fila_columna;
    }
}
