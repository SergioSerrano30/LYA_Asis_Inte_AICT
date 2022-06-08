
import java.util.ArrayList;
import javax.swing.Icon;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author 52311
 */
public class Automata extends javax.swing.JFrame {
    private ImageIcon imagen;
    private Icon icono;

    ArrayList<String> NombreToken;
    ArrayList<String> Tokens;
    String Cadena = "";
    int conta = 0;

    /**
     * Creates new form Automata
     */
    public Automata() {
        initComponents();
        this.pintarImagen(this.jLabel, "src/imagenes/automataas.png");
    }

    public Automata(ArrayList<String> tokens, ArrayList<String> nombres) {
        initComponents();
        this.pintarImagen(this.jLabel, "src/imagenes/automataas.png");
        NombreToken = nombres;
        Tokens = tokens;
        Validar(NombreToken, Tokens);
        jTextArea1.setText(Cadena);
    }

    public void Validar(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        if (NombreToken.size() > 0) {
            P0(NombreToken, Tokens);
        } else {
            Cadena += "NO HAY CODIGO PARA ANALIZAR";
        }
    }

    public void P0(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        if (Tokens.get(conta).equals("INICIO")) {
            Cadena += Tokens.get(conta) + "  P0: AVANZA HACIA EL P1 \n";
            P1(NombreToken, Tokens);
        } else {
            Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE P0 HACIA P1";
        }
    }

    public void P1(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (NombreToken.get(conta).equals("Identificador")) {
                Cadena += Tokens.get(conta) + "  P1: AVANZA HACIA EL P2 \n";
                P2(NombreToken, Tokens);
            } else {
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE P1 HACIA P2";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

    public void P2(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (NombreToken.get(conta).equals("Llave_A")) {
                Cadena += Tokens.get(conta) + "  P2: AVANZA HACIA EL P3 \n";
                P3(NombreToken, Tokens);
            } else {
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE P2 HACIA P3";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

    public void P3(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (Tokens.get(conta).equals("PRINCIPAL")) {
                Cadena += Tokens.get(conta) + "  P3: AVANZA HACIA EL P4 \n";
                P4(NombreToken, Tokens);
            } else {
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE P3 HACIA P4";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

    public void P4(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (NombreToken.get(conta).equals("Parentesis_A")) {
                Cadena += Tokens.get(conta) + "  P4: AVANZA HACIA EL P5 \n";
                P5(NombreToken, Tokens);
            } else {
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE P4 HACIA P5";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

    public void P5(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (NombreToken.get(conta).equals("Parentesis_C")) {
                Cadena += Tokens.get(conta) + "  P5: AVANZA HACIA EL P6 \n";
                P6(NombreToken, Tokens);
            } else {
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE P5 HACIA P6";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

    public void P6(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (NombreToken.get(conta).equals("Llave_A")) {
                Cadena += Tokens.get(conta) + "  P6: AVANZA HACIA EL P7 \n";
                P7(NombreToken, Tokens);
            } else {
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE P6 HACIA P7";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }
    //LO MAS COMPICADO

    public void P7(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            int Llave_A = 0;
            while (conta <= NombreToken.size() - 1) {
                if (NombreToken.get(conta).equals("Llave_A")) {
                    Llave_A++;
                } else if (NombreToken.get(conta).equals("Llave_C")) {
                    Llave_A--;
                }
             
                if (Llave_A == -1) {
                   Cadena += Tokens.get(conta) + "  P7: AVANZA HACIA EL P8 \n";
                   P8(NombreToken, Tokens);
                   break;
                }
                 conta++;

            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

    public void P8(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (NombreToken.get(conta).equals("Llave_C")) {
                Cadena += Tokens.get(conta) + "  P8: AVANZA HACIA EL P9 \n";
                 P9(NombreToken, Tokens);
            } else {
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE P8 HACIA P9";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

       public void P9(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (Tokens.get(conta).equals("FINAL")&& (NombreToken.size() - 1)==conta) {
                Cadena += Tokens.get(conta) + "  P9: AVANZA HACIA EL P10 \n  AUTOMATA CONCLUIDO";
                //    P7(NombreToken, Tokens);
            }
            
            else if (Tokens.get(conta).equals("FINAL")&& (NombreToken.size() - 1)!=conta) {
                Cadena += Tokens.get(conta) + "  P9: AVANZA HACIA EL P10 \n  AUTOMATA CONCLUIDO \n EL CODIGO QUE SE ESCUENTRA DESPUES DEL FINAL NO ES VALIDO "; 
                //    P7(NombreToken, Tokens);
            } 
            else {
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE P9 HACIA P10";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }
       
       private void  pintarImagen(JLabel lbl,String ruta){
           this.imagen=new ImageIcon(ruta);
           this.icono=new ImageIcon(
                   this.imagen.getImage().getScaledInstance(
                   lbl.getWidth(), 
                   lbl.getHeight(),
                   Image.SCALE_DEFAULT
                   )
           );lbl.setIcon(this.icono);
           this.repaint();
                   
                   }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setText("AUTOMATA");

        jLabel.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 936, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 936, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(380, 380, 380)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
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
            java.util.logging.Logger.getLogger(Automata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Automata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Automata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Automata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Automata().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
