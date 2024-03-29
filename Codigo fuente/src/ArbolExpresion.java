import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Larios
 */

public class ArbolExpresion {
    private Nodo raiz;
    private String operadores;
    private Pila operandosPila;
    private Pila operadoresPila;
    private ArrayList<Cuadruplo> c;
    private int t = 0;
    private int contador = 0;
    private int operaciones = 0;
    private String asignacion = "";
    private String Ciclos="  ";
     private String CiclosOpti = "  ";

    public ArbolExpresion() {
        this.c = new ArrayList<>();
        this.operadores = ") == > < >= <= +-*/(";
        this.operadoresPila = new Pila();
        this.operandosPila = new Pila();
    }
    
    public void tAcero() {this.t = 0;}
    
    public String[] crearArbol(String expresion) {
        contador = 0;
        this.raiz = null;
        this.operandosPila.clear();
        this.operadoresPila.clear();
        if(expresion.equals("{")|expresion.equals("}")){
        }
        else if(expresion.contains("//")){
        Ciclos=Ciclos+"\n"+expresion;
        }
        else{
        String[] e = expresion.split("(\\(|=|==|>|<|!=|#)", 2);
        asignacion = e[0]; 
        System. out. println("###UNO###\n"+e[0]);
        System. out. println("###DOS####\n"+e[1]+"<------"); 
        
        if (e[0].contains("if")) {
        String[] uno=expresion.split("(\\()", 2);
        String[] dos=uno[1].split("(\\))", 2);
         Ciclos=Ciclos+"\n\n============================\n"
                      + expresion+"\n============================\n"+"\n"+"T1= "+dos[0]+"\n"+
                       "if_false T1 goto L1\n" ;
         CiclosOpti=CiclosOpti+"\n\n============================\n"
                      + expresion+"\n============================\n"+"\n"+"T1= "+dos[0]+"\n"+
                       "if_false T1 goto L1\n" ;
        }
        else if (e[0].contains("for")) {
        String[] uno=expresion.split("(\\()", 2);
        String[] dos=uno[1].split("(\\))", 2);
         Ciclos=Ciclos+"\n\n============================\n"
                      + expresion+"\n============================\n"+"\n"+"Label L1\n"+"T1= "+dos[0]+"-1\n"+
                     "T2= T1>=0\n"+  
                 "if_false T2 goto L2\n" ;
        CiclosOpti=CiclosOpti+"\n\n============================\n"
                      + expresion+"\n============================\n"+"\n"+"Label L1\n"+"T1= "+dos[0]+"-1\n"+
                     "T2= T1>=0\n"+  
                 "if_false T2 goto L2\n";
        }
        else if (e[0].contains("(cortadora_activar|ventilador_activar|iluminacion_activar|banda_activar|puerta_abrir|tv_encender|cajafuerte_desactivar|panel_encender|aspersor_activar|panel_girar)")) {
            Ciclos=Ciclos+"\n"+expresion;
            CiclosOpti=CiclosOpti+"\n"+expresion;
        }
        
     //#alarma|#aspPatio|#caja|#cortadora|#iluPrincipal|#iluRepecion|#iluSala1|#iluSala2|#panelPatio|#pRecepcion|#pSala1|#pSala2|#tvRecepcion|#vRecepcion   
    else{
            Ciclos=Ciclos+"\n"+expresion;
            CiclosOpti=CiclosOpti+"\n"+expresion;
            operaciones = contarOperacionesArit(e[1].trim());
            crearArbolAritmetico(e[1]);
        }
        }
        crearCuadruplos(raiz);
        String[] Arreglo={Ciclos,CiclosOpti};
        return Arreglo;
    }
    
    public void crearArbolBooleano(String expresion) {
        StringTokenizer token = new StringTokenizer(expresion.trim(), "()<=>,", true);
        
        if (token.countTokens() == 1) {
            asignar(expresion.trim());
            return;
        }
        
        while(token.hasMoreElements()) {
            String t = token.nextToken();
            if (t.equals("<") | t.equals(">") | t.equals("=")) {
                String temporal = token.nextToken();
                if (temporal.equals("=")) {
                    t = t + temporal;
                    arbolAnalisis(t);
                } else {
                    arbolAnalisis(t);
                    arbolAnalisis(temporal);
                }
            } else {
                t = existeFuncion(t, token);
                if (!t.isEmpty())
                    arbolAnalisis(t);
            }
        }
        
        agregarSubArbol();
    }
    
    public String existeFuncion(String t, StringTokenizer token) {
        if (t.equals("dist")) {
            String temporal = token.nextToken();
            //System.out.println(temporal);
            if (temporal.equals("(")) {
                String arg1 = token.nextToken();
                token.nextToken();
                String arg2 = token.nextToken();
                c.add(new Cuadruplo(t, arg1, arg2, "T" + (++this.t)));
                token.nextToken();
                return "T" + this.t;
            } else {
                arbolAnalisis(t);
                arbolAnalisis(temporal);
                return "";
            }
        }
        return t;
    }
    
    private void agregarSubArbol() {
        this.raiz = this.operandosPila.get();
        while (!this.operadoresPila.isEmpty()) {
            if (this.operadoresPila.get().getValor().equals("(")) {
                this.operadoresPila.pop();
            } else {
                guardarSubArbol();
                this.raiz = this.operandosPila.get();
            }
        }
    }
    
    public void arbolAnalisis(String t) {
        if (!this.operadores.contains(t)) {
            this.operandosPila.add(new Nodo(t));
        } else {
            if (t.equals(")")) {
                while (!this.operadoresPila.isEmpty() && 
                        !this.operadoresPila.get().getValor().equals("(")) {
                    guardarSubArbol();
                }
                this.operadoresPila.pop();
            } else {
                if (!t.equals("(") && !this.operadoresPila.isEmpty()) {
                    Nodo op = this.operadoresPila.get();
                    while (!op.getValor().equals("(") && 
                            !this.operadoresPila.isEmpty() && 
                            this.operadores.indexOf(op.getValor()) >= this.operadores.indexOf(t)) {
                        guardarSubArbol();
                        if (!this.operadoresPila.isEmpty()) {
                            op = this.operadoresPila.get();
                        }
                    }
                }

                this.operadoresPila.add(new Nodo(t));
            }
        }
    }
    
    private void crearArbolAritmetico(String expresion) {
        StringTokenizer token = new StringTokenizer(expresion.trim(), "()+-*/,", true);
        
        if (token.countTokens() == 1) {
            asignar(expresion.trim());
            return;
        }
        
        while(token.hasMoreElements()) {
            String t = token.nextToken();
            
            t = existeFuncion(t, token);
            if (!t.isEmpty())
                arbolAnalisis(t);
        }
        
        agregarSubArbol();
    }
    
    private int contarOperacionesArit(String e) {
        int op = 0;
        for(int i = 0; i < e.length(); i++) {
            String t = String.valueOf(e.charAt(i));
            if ("+".equals(t) | "-".equals(t) | "*".equals(t) | "/".equals(t)) {
                op++;
            }
        }
        
        return op;
    }
    
    private int contarOperacionesBool(String e) {
        int op = 0;
        for(int i = 0; i < e.length(); i++) {
            String t = String.valueOf(e.charAt(i));
            if ("<".equals(t) | ">".equals(t) | "=".equals(t)) {
                op++;
                if (String.valueOf(e.charAt(i+1)).equals("=")) {
                    i++;
                } 
            }
        }
        
        return op;
    }
    
    private void guardarSubArbol() {
        Nodo op2 = this.operandosPila.pop();
        Nodo op1 = this.operandosPila.pop();
        this.operandosPila.add(new Nodo(op1, this.operadoresPila.pop().getValor(), op2));
    }
    
    public void mostrarCuadruplos() {
        c.forEach(System.out::println);
    }
    public void mostrarTablaCua() {
        c.get(0);
    }
    public ArrayList<Cuadruplo> getCuadruplos(){
        return c;
    }
    
    public void asignar(String valor) {
        c.add(new Cuadruplo("=", valor, "", this.asignacion));
    }
    
    public void crear(Nodo nodo) {
        contador++;
        String op = nodo.getValor();
        c.add(new Cuadruplo(op, nodo.getIzquierdo().getValor(), nodo.getDerecho().getValor(), "T" + (++t)));
        nodo.setValor("T" + t);
        
        if (contador == this.operaciones) {
            c.add(new Cuadruplo("=", "T" + t, "", this.asignacion));
        }
    }
    
    private void crearCuadruplos(Nodo nodo) {
        if (nodo == null) return;
        
        if (nodo.getIzquierdo() != null) {
            crearCuadruplos(nodo.getIzquierdo());
            crearCuadruplos(nodo.getDerecho());
            crear(nodo);
        }
    }
    
    public Nodo getRaiz() {
        return this.raiz;
    }
}