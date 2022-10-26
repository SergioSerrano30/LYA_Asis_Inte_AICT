
/**
 *
 * @author sergi
 */
public class Variables {
    private String nombre;
    private String valor;
    private String tipo; //Cadena o numero
    private int fila;
    private String columna;
    private String fila_columna;

    public Variables(String nombre, String valor, String tipo, int fila, String columna, String fila_columna) {
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.fila_columna = fila_columna;
    }

    public Variables(String nombre, String valor, String tipo, String fila_columna) {
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
    

    

    public String nombre() {
        return nombre;
    }

    public String valor() {
        return valor;
    }

    public String tipo() {
        return tipo;
    }

    public String fila_columna() {
        return fila_columna;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFila_columna(String fila_columna) {
        this.fila_columna = fila_columna;
    }

        
}
