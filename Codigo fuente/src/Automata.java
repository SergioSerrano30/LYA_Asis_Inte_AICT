
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
        //this.pintarImagen(this.jLabel, "src/imagenes/automataas.png");
    }

    public Automata(ArrayList<String> tokens, ArrayList<String> nombres) {
        initComponents();
        //this.pintarImagen(this.jLabel, "src/imagenes/automataas.png");
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
            Cadena += Tokens.get(conta) + "  Q0: AVANZA HACIA EL P1 \n";
             P1(NombreToken, Tokens);
        } else {
           Icon icon;
           icon= new ImageIcon(getClass().getResource("/imagenes/automata p0.png"));
           jLabel.setIcon(icon);
            Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE Q0 HACIA Q1";
        }
    }

    public void P1(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (NombreToken.get(conta).equals("Identificador")) {
                Cadena += Tokens.get(conta) + "  Q1: AVANZA HACIA EL Q2 \n";
                P2(NombreToken, Tokens);
            } else {
                Icon icon;
           icon= new ImageIcon(getClass().getResource("/imagenes/automata p1.png"));
           jLabel.setIcon(icon);
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE P1 HACIA Q2";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

    public void P2(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (NombreToken.get(conta).equals("Llave_A")) {
                Cadena += Tokens.get(conta) + "  Q2: AVANZA HACIA EL Q3 \n";
                P3(NombreToken, Tokens);
                 System. out. println("Q2 "+"Contador: "+conta+" Condicion: "+(NombreToken.size() - 1));
            } else {
                Icon icon;
           icon= new ImageIcon(getClass().getResource("/imagenes/automata p2.png"));
           jLabel.setIcon(icon);
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE Q2 HACIA Q3";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

    public void P3(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (Tokens.get(conta).equals("PRINCIPAL")) {
                Cadena += Tokens.get(conta) + "  Q3: AVANZA HACIA EL Q4 \n";
                 System. out. println("Q3 "+"Contador: "+conta+" Condicion: "+(NombreToken.size() - 1));
                P4(NombreToken, Tokens);
            } else {
                Icon icon;
           icon= new ImageIcon(getClass().getResource("/imagenes/automata p3.png"));
           jLabel.setIcon(icon);
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE Q3 HACIA Q4";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

    public void P4(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (NombreToken.get(conta).equals("Parentesis_A")) {
                Cadena += Tokens.get(conta) + "  Q4: AVANZA HACIA EL Q5 \n";
                 System. out. println("Q4 "+"Contador: "+conta+" Condicion: "+(NombreToken.size() - 1));
                P5(NombreToken, Tokens);
            } else {
                Icon icon;
           icon= new ImageIcon(getClass().getResource("/imagenes/automata p4.png"));
           jLabel.setIcon(icon);
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE Q4 HACIA Q5";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

    public void P5(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (NombreToken.get(conta).equals("Parentesis_C")) {
                Cadena += Tokens.get(conta) + "  Q5: AVANZA HACIA EL Q6 \n";
                 System. out. println("Q5 "+"Contador: "+conta+" Condicion: "+(NombreToken.size() - 1));
                P6(NombreToken, Tokens);
            } else {
                Icon icon;
           icon= new ImageIcon(getClass().getResource("/imagenes/automata p5.png"));
           jLabel.setIcon(icon);
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE Q5 HACIA Q6";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

    public void P6(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (NombreToken.get(conta).equals("Llave_A")) {
                Cadena += Tokens.get(conta) + "  Q6: AVANZA HACIA EL Q7 \n";               
                P7(NombreToken, Tokens);
            } else {
                Icon icon;
           icon= new ImageIcon(getClass().getResource("/imagenes/automata p6.png"));
           jLabel.setIcon(icon);
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE Q6 HACIA Q7";
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
                   Cadena += Tokens.get(conta) + "  Q7: AVANZA HACIA EL Q8 \n";
                    System. out. println("P7  "+"Contador: "+conta+" Condicion: "+(NombreToken.size() - 1));
                   P8(NombreToken, Tokens);
                   break;
                }else{
                Icon icon;
                icon= new ImageIcon(getClass().getResource("/imagenes/automata p7.png"));
                jLabel.setIcon(icon);
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE Q6 HACIA Q7";
            
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
                Cadena += Tokens.get(conta) + "  Q8: AVANZA HACIA EL Q9 \n";
                    P9(NombreToken, Tokens);
            } else {
                Icon icon;
                icon= new ImageIcon(getClass().getResource("/imagenes/automata p8.png"));
                jLabel.setIcon(icon);
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE Q8 HACIA Q9";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
            Cadena += "YA NO HAY TOKENS POR ANALIZAR Y NO SE FINALIZO EL AUTOMATA";
        }
    }

       public void P9(ArrayList<String> NombreToken, ArrayList<String> Tokens) {
        conta += 1;
        if (conta <= NombreToken.size() - 1) {
            if (Tokens.get(conta).equals("FINAL")&& (NombreToken.size() - 1)==conta) {
                Icon icon;
                icon= new ImageIcon(getClass().getResource("/imagenes/automata.png"));
                jLabel.setIcon(icon);
                Cadena += Tokens.get(conta) + "  Q9: AVANZA HACIA EL Q10 \n  AUTOMATA CONCLUIDO";
                //    P7(NombreToken, Tokens);
               System. out. println("P8 1 "+"Contador: "+conta+" Condicion: "+(NombreToken.size() - 1));
            }
            
            else if (Tokens.get(conta).equals("FINAL")&& (NombreToken.size() - 1)!=conta) {
                Cadena += Tokens.get(conta) + "  Q9: AVANZA HACIA EL Q10 \n  AUTOMATA CONCLUIDO \n EL CODIGO QUE SE ESCUENTRA DESPUES DEL FINAL NO ES VALIDO "; 
                //    P7(NombreToken, Tokens);
               }
            else {
                Icon icon;
                icon= new ImageIcon(getClass().getResource("/imagenes/automata p9.png"));
                jLabel.setIcon(icon);
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE Q6 HACIA Q7";
            
                Cadena += "EL TOKEN [ " + Tokens.get(conta) + " ] ANALIZADO NO PERTENCE A LA TRANSICION DE Q9 HACIA Q10";
            }
        } else if (!Tokens.get(Tokens.size() - 1).equals("FINAL")) {
                Icon icon;
                icon= new ImageIcon(getClass().getResource("/imagenes/automata p9.png"));
                jLabel.setIcon(icon);
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

        pnlFondo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(550, 650));
        setMinimumSize(new java.awt.Dimension(550, 650));
        setResizable(false);
        getContentPane().setLayout(null);

        pnlFondo.setMaximumSize(new java.awt.Dimension(550, 650));
        pnlFondo.setMinimumSize(new java.awt.Dimension(550, 650));
        pnlFondo.setPreferredSize(new java.awt.Dimension(550, 650));
        pnlFondo.setLayout(null);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        pnlFondo.add(jScrollPane1);
        jScrollPane1.setBounds(20, 80, 500, 244);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("AUTOMATA");
        pnlFondo.add(jLabel2);
        jLabel2.setBounds(0, 20, 550, 47);

        jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/automata.png"))); // NOI18N
        pnlFondo.add(jLabel);
        jLabel.setBounds(50, 350, 450, 240);

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
    private javax.swing.JPanel pnlFondo;
    // End of variables declaration//GEN-END:variables
}
