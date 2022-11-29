
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author 52311
 */
public class GramaUsa extends javax.swing.JFrame {

    ArrayList<String> lexema;
    String grama = "";
    int cont = 0;

    /**
     * Creates new form GramaUsa
     */
    public GramaUsa() {
        initComponents();

        String cadena = "";

        cadena += "Variables\n"
                + "Valor = Cadena|Numero|OP_RETORNO\n"
                + "IDENTIFICADOR $cadena\n"
                + "= (Operador de asignación)\n"
                + "VALOR (Cadena| Numero |OP_RETORNO)\n"
                + "; (Punto y coma) \n";

        cadena += "\n";

        cadena += "Funciones\n"
                + "Correctas\n"
                + "Parametros: Valor(Cadena| Numero |OP_RETORNO) | IDENTIFICADOR , Valor(Cadena| Numero |OP_RETORNO) IDENTIFICADOR\n"
                + "OP_Cita(cita_agendar | cita_cencelar) | OP_Turno(turno_nuevo)|OP_Ilumunacion( ilu_encender| ilu_apagar) | OP_Temperatura(temp_establecer| temp_subir| temp_bajar) | OP_Puerta(puerta_abrir| puerta_cerrar)\n"
                + "FUNCION (Función completa)\n"
                + "( (Parentesis_A)\n"
                + "PARAMETOS (Valor | IDENTIFICADOR)\n"
                + ") (Parentesis que cierra)         	\n"
                + "; (Punto y coma)         	\n"
                + "FUNCION_COMPLETA (Función completa PC)\n"
                + "; (Punto y coma) \n";

        cadena += "\n";

        cadena += "Ciclo FOR\n"
                + "Correctas\n"
                + "For (Ciclo)\n"
                + "( (Parentesis_A)\n"
                + "Valor(Cadena| Numero |OP_RETORNO) | IDENTIFICADOR\n"
                + ") (Parentesis_C) \n";

        cadena += "\n";

        cadena += "Condicionales\n"
                + "Correctas\n"
                + "IF (Condicional)\n"
                + "( (Parentesis_A)\n"
                + "EXP_LOGICA(> | < | >= | <= | == | !=) | IDENTIFICADOR\n"
                + ") (Parentesis_C)\n"
                + "{ (Llave que abre)\n"
                + "} (Llave que cierra) \n";

        cadena += "\n";

        cadena += "Ciclo While\n"
                + "Correctas\n"
                + "While (While)\n"
                + "( (Parentesis_A)\n"
                + "EXP_LOGICA(> | < | >= | <= | == | !=) | IDENTIFICADOR\n"
                + ") (Parentesis_C)\n"
                + "{ (Llave que abre)\n"
                + "} (Llave que cierra)";

        txtGramaticas.setText(cadena);
    }

    public GramaUsa(ArrayList<String> lexe) {
        this();
//        lexema = lexe;
//        txtGramaticas.setText(MostrarTokens());
//        lexe.clear();
    }

    private String MostrarTokens() {
        while (cont <= lexema.size() - 1) {
            grama += lexema.get(cont) + "\n";
            grama += "\n";
            cont++;
        }
        return grama;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGramaticas = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(550, 650));
        setMinimumSize(new java.awt.Dimension(550, 650));
        setPreferredSize(new java.awt.Dimension(550, 650));
        setResizable(false);
        getContentPane().setLayout(null);

        pnlFondo.setMaximumSize(new java.awt.Dimension(550, 650));
        pnlFondo.setMinimumSize(new java.awt.Dimension(550, 650));
        pnlFondo.setPreferredSize(new java.awt.Dimension(550, 650));
        pnlFondo.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GRAMATICAS");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnlFondo.add(jLabel1);
        jLabel1.setBounds(4, 6, 540, 62);

        txtGramaticas.setEditable(false);
        txtGramaticas.setColumns(20);
        txtGramaticas.setRows(5);
        jScrollPane1.setViewportView(txtGramaticas);

        pnlFondo.add(jScrollPane1);
        jScrollPane1.setBounds(30, 90, 480, 490);

        getContentPane().add(pnlFondo);
        pnlFondo.setBounds(0, 0, 550, 650);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GramaUsa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GramaUsa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GramaUsa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GramaUsa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GramaUsa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlFondo;
    public javax.swing.JTextArea txtGramaticas;
    // End of variables declaration//GEN-END:variables
}
