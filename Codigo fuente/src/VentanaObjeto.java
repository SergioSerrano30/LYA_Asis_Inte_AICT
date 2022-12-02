
import compilerTools.Directory;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author sergi
 */
public class VentanaObjeto extends javax.swing.JFrame {

    /**
     * Creates new form VentanaObjeto
     */
    private Directory directorio;
    private String title;
    private String cod;

    public VentanaObjeto() {
        initComponents();
        //directorio = new Directory(this, txtCodigo, "test", ".ino");
    }

    public VentanaObjeto(String titulo, String variables, String codigo) {
        initComponents();
        title = titulo;
        setTitle(title);
        cod = codigo;
        cod = generaIntermedio(variables, cod);
        txtCodigo.setText(cod);
        //directorio = new Directory(this, txtCodigo, title, ".ino");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCodigo = new javax.swing.JTextArea();
        btnAbrirArchivo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(700, 700));
        setMinimumSize(new java.awt.Dimension(700, 700));
        setPreferredSize(new java.awt.Dimension(700, 700));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Código objeto");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 10, 690, 60);

        txtCodigo.setEditable(false);
        txtCodigo.setColumns(20);
        txtCodigo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodigo.setRows(5);
        jScrollPane1.setViewportView(txtCodigo);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(90, 170, 520, 460);

        btnAbrirArchivo.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnAbrirArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/check-mar30x30.png"))); // NOI18N
        btnAbrirArchivo.setText("Abrir archivo");
        btnAbrirArchivo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAbrirArchivo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnAbrirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirArchivoActionPerformed(evt);
            }
        });
        getContentPane().add(btnAbrirArchivo);
        btnAbrirArchivo.setBounds(390, 100, 220, 60);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbrirArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirArchivoActionPerformed
        String ruta = System.getProperty("user.dir") + "\\CodigoObjeto\\" + title + "\\" + title + ".ino";
        crearCarpeta();
        crearArchivo(ruta);
        abrirarchivo(ruta);
    }//GEN-LAST:event_btnAbrirArchivoActionPerformed

    public void abrirarchivo(String archivo) {

        try {
            File objetofile = new File(archivo);
            Desktop.getDesktop().open(objetofile);
            JOptionPane.showMessageDialog(this, "Abriendo archivo...");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
        }

    }

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
            java.util.logging.Logger.getLogger(VentanaObjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaObjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaObjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaObjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaObjeto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirArchivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtCodigo;
    // End of variables declaration//GEN-END:variables

    private void crearCarpeta() {
        try {
            String r = System.getProperty("user.dir") + "\\CodigoObjeto\\" + title;
            File directorio = new File(r);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado");
                } else {
                    System.out.println("Error al crear directorio");
                }
            }
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    private void crearArchivo(String ruta) {
        try {
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(cod);
            JOptionPane.showMessageDialog(this, "Archivo guardado");

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private String codigoFuncion(String funcion) {
        String codigo = "";
        funcion = funcion.replace("(", "");
        funcion = funcion.replace(")", "");

        try {
            String info[] = funcion.split("#");
            //codigo = "Funcion ---> " + info[0] + " Lugar ---> " + info[1];
            String parte1 = "";
            switch (info[0]) {
                case "iluminacion_activar":
                    parte1 = complemento(info[1]);
                    codigo = info[0] + parte1;
                    break;
                case "iluminacion_desactivar":
                    parte1 = complemento(info[1]);
                    codigo = info[0] + parte1;
                    break;
                case "puerta_abrir":
                    parte1 = complemento(info[1]);
                    codigo = info[0]+parte1;
                    break;
                case "puerta_cerrar":
                    parte1 = complemento(info[1]);
                    codigo = info[0]+parte1;
                    break;
                case "ventilador_activar":
                    parte1 = complemento(info[1]);
                    codigo = info[0]+parte1;
                    break;
                case "ventilador_desactivar":
                    parte1 = complemento(info[1]);
                    codigo = info[0]+parte1;
                    break;
                case "panel_encender":
                    codigo = info[0]+"();\n";
                    break;
                case "panel_apagar":
                    codigo = info[0]+"();\n";
                    break;
                case "panel_girar":
                    parte1 = complemento(info[1]);
                    codigo = info[0]+parte1;
                    break;
                case "aspersor_activar":
                    parte1 = complemento(info[1]);
                    codigo = info[0]+parte1;
                    break;
                case "aspersor_desactivar":
                    parte1 = complemento(info[1]);
                    codigo = info[0]+parte1;
                    break;
                default:
                    throw new AssertionError();
            }

        } catch (Exception e) {
            mensaje("Error " + e);
        }
        return codigo;
    }

    private String generaIntermedio(String variables, String cod) {
        String codigoObjeto = "";
        String lineasCodigo[] = cod.split("\n");
        for (int i = 0; i < lineasCodigo.length; i++) {
            String linea = lineasCodigo[i];
            linea = linea.replace(" ", "");
            if (linea.contains("label")) {
                codigoObjeto += "}\n";
            } else if (linea.contains("Label")) {
                System.out.println("OMITIENDO");
            } else if (linea.contains("if(")) {
                linea = linea.replace("$", "");
                codigoObjeto += " " + linea + "{\n";
            } else if (linea.contains("for(")) {
                linea = linea.replace("$", "");
                String codFor = "";
                String cont[] = linea.split("\\(");
                cont[1] = cont[1].replace(")", "");
                codFor = cont[0] + "(int i = 0; i<" + cont[1] + "; i++){";
                codigoObjeto += " " + codFor + "\n";
            } else if (linea.contains("T1=") || linea.contains("T2=")) {
                System.out.println("OMITIENDO");
            } else if (linea.contains("if_false")) {
                System.out.println("OMITIENDO");
            } else if (linea.contains("_")) {
                linea = linea.replace("$", "");
                codigoObjeto += codigoFuncion(linea) + "\n";
            } else if (linea.contains("$")) {//Variables en código 
                linea = linea.replace("$", "");
                codigoObjeto += " " + linea + ";\n";
            }

        }

        String pConfig
                = "        #include <Servo.h>\n"
                + "//#########################################################\n"
                + "//                CONFIGURACION\n"
                + "//\n"
                + "// Declaramos las variables para controlar los servos.\n"
                + "// Puerta recepción.\n"
                + "Servo serPrincipal;\n"
                + "// Puerta sala 1.\n"
                + "Servo serSala1;\n"
                + "// Puerta sala 2.\n"
                + "Servo serSala2;\n"
                + "// Aspersor.\n"
                + "Servo serAspersor;\n"
                + "// Panel.\n"
                + "Servo serPanel;\n"
                + "\n"
                + "// Variable para el movimiento del Aspersor.\n"
                + "int pos = 0;\n"
                + "// Asignamos los puertos a los LEDs y Servomotores.\n"
                + "const int ledRecepcion = 2;\n"
                + "const int ledPrincipal = 3;\n"
                + "const int ledSala1 = 4;\n"
                + "const int ledSala2 = 5;\n"
                + "const int motorVentilador = 7;\n"
                + "const int servoPrincipal = 8;\n"
                + "const int servoSala1 = 9;\n"
                + "const int servoSala2 = 10;\n"
                + "const int servoAspersor = 11;\n"
                + "const int servoPanel = 12;\n"
                + "\n"
                + "void iluminacion_desactivar(int led);\n"
                + "void iluminacion_activar(int led);\n"
                + "void puerta_abrir(int puerta);\n"
                + "void puerta_cerrar(int puerta);\n"
                + "void ventilador_activar();\n"
                + "void ventilador_desactivar();\n"
                + "void panel_encender();\n"
                + "void panel_apagar();\n"
                + "void panel_girar(int grados);\n"
                + "void aspersor_activar();\n"
                + "void aspersor_desactivar();\n"
                + "//#########################################################\n";

        String p2 = "\n"
                + "void setup() {\n"
                + "  // Iniciamos el monitor serie para mostrar el resultado.\n"
                + "  Serial.begin(9600);\n"
                + "\n"
                + "  // Asignamos los servomotores a los puertos.\n"
                + "  serPrincipal.attach(servoPrincipal);\n"
                + "  serSala1.attach(servoSala1);\n"
                + "  serSala2.attach(servoSala2);\n"
                + "  serAspersor.attach(servoAspersor);\n"
                + "  serPanel.attach(servoPanel);\n"
                + "\n"
                + "  // Definimos los pines como salida.\n"
                + "  pinMode(ledRecepcion, OUTPUT);\n"
                + "  pinMode(ledPrincipal, OUTPUT);\n"
                + "  pinMode(ledSala1, OUTPUT);\n"
                + "  pinMode(ledSala2, OUTPUT);\n"
                + "  pinMode(motorVentilador, OUTPUT);\n"
                + "\n"
                + "  digitalWrite(ledRecepcion, LOW);\n"
                + "  digitalWrite(ledPrincipal, LOW);\n"
                + "  digitalWrite(ledSala1, LOW);\n"
                + "  digitalWrite(ledSala2, LOW);\n"
                + "  digitalWrite(motorVentilador, LOW);\n"
                + "\n"
                + "  // Puerta principal (90 cerrado y 150 abierto).\n"
                + "  serPrincipal.write(90);\n"
                + "  // Puerta sala 1 (10 cerrado y 70 abierto).\n"
                + "  serSala1.write(10);\n"
                + "  // Puerta sala 2 (0 cerrado y 80 abierto).\n"
                + "  serSala2.write(0);\n"
                + "  // Panel (rango de 0 a 180).\n"
                + "  serPanel.write(0);\n"
                + "\n"
                + "  // Esperamos 1 segundo.\n"
                + "  delay(1000);\n"
                + "\n"
                + "  //INICIO CODIGO GENERADO EN COMPILADOR\n\n";

        String p3 = "void loop() {\n"
                + "// Código que se repite:\n";

        String pFunciones = "/*\n"
                + "###############################################\n"
                + "ILUMINACION\n"
                + "\n"
                + "1 --> Recepcion\n"
                + "2 --> Principal\n"
                + "3 --> Sala1\n"
                + "4 --> Sala2\n"
                + "\n"
                + "PUERTA\n"
                + "1 --> Recepcion\n"
                + "2 --> Sala1\n"
                + "3 --> Sala2\n"
                + "###############################################\n"
                + "\n"
                + "*/\n"
                + "\n"
                + "void iluminacion_desactivar(int led) {\n"
                + "  switch (led) {\n"
                + "    case 1:\n"
                + "      digitalWrite(ledRecepcion, LOW);\n"
                + "      break;\n"
                + "\n"
                + "    case 2:\n"
                + "      digitalWrite(ledPrincipal, LOW);\n"
                + "      break;\n"
                + "    case 3:\n"
                + "      digitalWrite(ledSala1, LOW);\n"
                + "      break;\n"
                + "    case 4:\n"
                + "      digitalWrite(ledSala2, LOW);\n"
                + "      break;\n"
                + "  }\n"
                + "  delay(250);\n"
                + "}\n"
                + "void iluminacion_activar(int led) {\n"
                + "  switch (led) {\n"
                + "    case 1:\n"
                + "      digitalWrite(ledRecepcion, HIGH);\n"
                + "      break;\n"
                + "\n"
                + "    case 2:\n"
                + "      digitalWrite(ledPrincipal, HIGH);\n"
                + "      break;\n"
                + "    case 3:\n"
                + "      digitalWrite(ledSala1, HIGH);\n"
                + "      break;\n"
                + "    case 4:\n"
                + "      digitalWrite(ledSala2, HIGH);\n"
                + "      break;\n"
                + "  }\n"
                + "  delay(250);\n"
                + "}\n"
                + "void puerta_abrir(int puerta) {\n"
                + "  switch (puerta) {\n"
                + "    case 1:\n"
                + "      // Puerta principal (90 cerrado y 150 abierto).\n"
                + "      serPrincipal.write(150);\n"
                + "      break;\n"
                + "    case 2:\n"
                + "      // Puerta sala 1 (10 cerrado y 70 abierto).\n"
                + "      serSala1.write(70);\n"
                + "      break;\n"
                + "    case 3:\n"
                + "      // Puerta sala 2 (0 cerrado y 80 abierto).\n"
                + "      serSala2.write(80);\n"
                + "      break;\n"
                + "  }\n"
                + "  delay(250);\n"
                + "}\n"
                + "void puerta_cerrar(int puerta) {\n"
                + "  switch (puerta) {\n"
                + "    case 1:\n"
                + "      // Puerta principal (90 cerrado y 150 abierto).\n"
                + "      serPrincipal.write(90);\n"
                + "      break;\n"
                + "    case 2:\n"
                + "      // Puerta sala 1 (10 cerrado y 70 abierto).\n"
                + "      serSala1.write(10);\n"
                + "      break;\n"
                + "    case 3:\n"
                + "      // Puerta sala 2 (0 cerrado y 80 abierto).\n"
                + "      serSala2.write(0);\n"
                + "      break;\n"
                + "  }\n"
                + "  delay(250);\n"
                + "}\n"
                + "void ventilador_activar() {\n"
                + "  digitalWrite(motorVentilador, HIGH);\n"
                + "  delay(1000);\n"
                + "}\n"
                + "void ventilador_desactivar() {\n"
                + "  digitalWrite(motorVentilador, LOW);\n"
                + "  delay(1000);\n"
                + "}\n"
                + "void panel_encender() {\n"
                + "  // Panel (rango de 0 a 180).\n"
                + "  serPanel.write(90);\n"
                + "  delay(250);\n"
                + "}\n"
                + "void panel_apagar() {\n"
                + "  // Panel (rango de 0 a 180).\n"
                + "  serPanel.write(0);\n"
                + "  delay(250);\n"
                + "}\n"
                + "void panel_girar(int grados) {\n"
                + "  // Panel (rango de 0 a 180).\n"
                + "  serPanel.write(grados);\n"
                + "  delay(250);\n"
                + "}\n"
                + "void aspersor_activar() {\n"
                + "  // Aspersor.\n"
                + "  for (pos = 0; pos <= 180; pos += 10) {\n"
                + "    serAspersor.write(pos);\n"
                + "    delay(250);\n"
                + "  }\n"
                + "\n"
                + "\n"
                + "  for (pos = 180; pos <= 0; pos += 10) {\n"
                + "    serAspersor.write(pos);\n"
                + "    delay(250);\n"
                + "  }\n"
                + "}\n"
                + "void aspersor_desactivar() {\n"
                + "  serAspersor.write(0);\n"
                + "  delay(250);\n"
                + "}";
        String codP2 = codigoObjeto + "\n";

        String codP3 = "" + "\n";
        String codigo
                = pConfig
                + variables
                + p2
                + codP2
                + "}\n"
                + p3
                + codP3
                + "}\n"
                + pFunciones;
        return codigo;
    }

    private String complemento(String lugar) {
        String codigo = "";
        switch (lugar) {
            case "iluRecepcion":
                codigo = "(" + "1" + ");\n";
                break;
            case "iluPrincipal":
                codigo = "(" + "2" + ");\n";
                break;
            case "iluSala1":
                codigo = "(" + "3" + ");\n";
                break;
            case "iluSala2":
                codigo = "(" + "4" + ");\n";
                break;
            case "pRecepcion":
                codigo = "(" + "1" + ");\n";
                break;
            case "pSala1":
                codigo = "(" + "2" + ");\n";
                break;
            case "pSala2":
                codigo = "(" + "3" + ");\n";
                break;
            case "vRecepcion":
                codigo = "(" + ");\n";
                break;
            case "aspPatio":
                codigo = "(" + ");\n";
                break;
            default:
        }
        if(lugar.contains("panelPatio")){
            String partes[] = lugar.split(",");
            codigo = "("+partes[1]+");\n";
        }
        return codigo;
    }
}
