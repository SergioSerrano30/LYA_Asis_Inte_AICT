
/**
 *
 * @author sergi
 */
public class Variables {
    private String nombre;
    private String valor;
    private String tipo;
    private String fila_columna;

    public Variables(String nombre, String valor, String tipo, String fila_columna) {
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
        this.fila_columna = fila_columna;
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

        
}
