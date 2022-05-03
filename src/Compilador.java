

import com.formdev.flatlaf.FlatIntelliJLaf;
import compilerTools.Directory;
import compilerTools.ErrorLSSL;
import compilerTools.Functions;
import compilerTools.Grammar;
import compilerTools.Production;
import compilerTools.TextColor;
import compilerTools.Token;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import say.swing.JFontChooser;


/**
 *
 * @author sergi
 */
public class Compilador extends javax.swing.JFrame {

    private String title;
    private Directory directorio;
    private ArrayList<Token> tokens;
    private ArrayList<ErrorLSSL> errors;
    private ArrayList<TextColor> textsColor;
    private Timer timerKeyReleased;
    private ArrayList<Production> identProd;
    private HashMap<String, String> identificadores;
    private boolean codeHasBeenCompiled = false;
    
    public Compilador() {
        initComponents();
        init();
    }
    
    public void init(){
        title = "AICT - Compilador";
        setLocationRelativeTo(null);
        setTitle(title);
        directorio = new Directory(this, txtCodigo, title, ".aict");
        addWindowListener(new WindowAdapter() {// Cuando presiona la "X" de la esquina superior derecha
            @Override
            public void windowClosing(WindowEvent e) {
                directorio.Exit();
                System.exit(0);
            }
        });
        
        Functions.setLineNumberOnJTextComponent(txtCodigo); //Pone los numeros de linea
        timerKeyReleased = new Timer((int) (1000 * 0.3), (ActionEvent e) -> {
            timerKeyReleased.stop();
            
            int posicion = txtCodigo.getCaretPosition();
            txtCodigo.setText(txtCodigo.getText().replaceAll("[\r]+", ""));
            txtCodigo.setCaretPosition(posicion);
            
            colorAnalysis();
            
        });
        Functions.insertAsteriskInName(this, txtCodigo,() ->{
            timerKeyReleased.restart();
        });
        tokens = new ArrayList<>();
        errors = new ArrayList<>();
        textsColor = new ArrayList<>();
        identProd = new ArrayList<>(); //Identificadores de producción
        identificadores = new HashMap<>();
        
        Functions.setAutocompleterJTextComponent(new String[]{"color","numero","este","oeste","norte","sur","pintar"}, txtCodigo,() ->{
            timerKeyReleased.restart();
        });
        
    }
    private void colorAnalysis() {
        /* Limpiar el arreglo de colores */
        textsColor.clear();
        /* Extraer rangos de colores */
        LexerColor lexerColor;
        try {
            File codigo = new File("color.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = txtCodigo.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexerColor = new LexerColor(entrada);
            while (true) {
                TextColor textColor = lexerColor.yylex();
                if (textColor == null) {
                    break;
                }
                textsColor.add(textColor);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
        Functions.colorTextPane(textsColor, txtCodigo, new Color(40, 40, 40));
    }
    private void clearField(){
        //Functions.clearDataInTable(tblTokens);
        txtConsola.setText("");
        tokens.clear();
        errors.clear();
        identProd.clear();
        identificadores.clear();
        codeHasBeenCompiled = false;
    }
    private void compile(){
        clearField();
        lexicalAnalysis();
        fillTablaTokens();
        syntacticAnalysis();
        semanticAnalysis();
        printConsole();
        codeHasBeenCompiled = true;
    }
    private void lexicalAnalysis(){
        Lexer lexer;
        try {
            File codigo = new File("code.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = txtCodigo.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexer = new Lexer(entrada);
            while (true) {
                Token token = lexer.yylex();
                if (token == null) {
                    break;
                }
                tokens.add(token);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
        
    }
    private void fillTablaTokens(){
        /*tokens.forEach(token -> {
            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(), "[" + token.getLine() + ", " + token.getColumn() + "]"};
            Functions.addRowDataInTable(tblTokens, data);
        });*/
    }
    private void syntacticAnalysis(){
        Grammar gramatica = new Grammar(tokens,errors);
        gramatica.show();
    }
    private void semanticAnalysis(){
        
    }
    private void printConsole(){
        int sizeErrors = errors.size();
        if (sizeErrors > 0) {
            Functions.sortErrorsByLineAndColumn(errors);
            String strErrors = "\n";
            for (ErrorLSSL error : errors) {
                String strError = String.valueOf(error);
                strErrors += strError + "\n";
            }
            txtConsola.setText("Compilación terminada...\n" + strErrors + "\nLa compilación terminó con errores...");
        } else {
            txtConsola.setText("Compilación terminada...");
        }
        txtConsola.setCaretPosition(0);
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        pnlFondo = new javax.swing.JPanel();
        pnlBarraHerramientas = new javax.swing.JPanel();
        imgNuevo = new javax.swing.JLabel();
        imgAbrir = new javax.swing.JLabel();
        imgGuardar = new javax.swing.JLabel();
        imgGuardarComo = new javax.swing.JLabel();
        imgCompilar = new javax.swing.JLabel();
        imgEjecutar = new javax.swing.JLabel();
        pnlCodigo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCodigo = new javax.swing.JTextPane();
        jFontChooser1 = new say.swing.JFontChooser();
        pnlError = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtConsola = new javax.swing.JTextArea();
        menu = new javax.swing.JMenuBar();
        archivo = new javax.swing.JMenu();
        opNuevo = new javax.swing.JMenuItem();
        opAbrir = new javax.swing.JMenuItem();
        opGuardar = new javax.swing.JMenuItem();
        opGuardarComo = new javax.swing.JMenuItem();
        editar = new javax.swing.JMenu();
        opCortar = new javax.swing.JMenuItem();
        opCopiar = new javax.swing.JMenuItem();
        opPegar = new javax.swing.JMenuItem();
        correr = new javax.swing.JMenu();
        opCompilar = new javax.swing.JMenuItem();
        opEjecutar = new javax.swing.JMenuItem();
        opciones = new javax.swing.JMenu();
        opFuente = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AICT Compilador");
        setMinimumSize(new java.awt.Dimension(830, 550));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlFondo.setBackground(new java.awt.Color(255, 255, 255));
        pnlFondo.setMaximumSize(new java.awt.Dimension(830, 530));
        pnlFondo.setMinimumSize(new java.awt.Dimension(830, 530));
        pnlFondo.setPreferredSize(new java.awt.Dimension(830, 530));
        pnlFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlBarraHerramientas.setBackground(new java.awt.Color(255, 255, 255));
        pnlBarraHerramientas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        pnlBarraHerramientas.setMaximumSize(new java.awt.Dimension(830, 30));
        pnlBarraHerramientas.setMinimumSize(new java.awt.Dimension(830, 30));
        pnlBarraHerramientas.setPreferredSize(new java.awt.Dimension(830, 30));
        pnlBarraHerramientas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imgNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevoArchivo30x30.png"))); // NOI18N
        imgNuevo.setToolTipText("Nuevo archivo");
        imgNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgNuevoMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        imgAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abrirArchivo30x30.png"))); // NOI18N
        imgAbrir.setToolTipText("Abrir archivo");
        imgAbrir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgAbrir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgAbrirMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgAbrir, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, -1));

        imgGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardarArchivo30x30.png"))); // NOI18N
        imgGuardar.setToolTipText("Guardar archivo");
        imgGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgGuardarMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, -1, -1));

        imgGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardarComo30x30.png"))); // NOI18N
        imgGuardarComo.setToolTipText("Guardar como");
        imgGuardarComo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgGuardarComo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgGuardarComoMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgGuardarComo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, -1, -1));

        imgCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/compilar30x30.png"))); // NOI18N
        imgCompilar.setToolTipText("Compilar");
        imgCompilar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgCompilar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgCompilarMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgCompilar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, -1, -1));

        imgEjecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ejecutar30x30.png"))); // NOI18N
        imgEjecutar.setToolTipText("Ejecutar");
        imgEjecutar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imgEjecutar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgEjecutarMousePressed(evt);
            }
        });
        pnlBarraHerramientas.add(imgEjecutar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, -1, -1));

        pnlFondo.add(pnlBarraHerramientas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlCodigo.setBackground(new java.awt.Color(204, 204, 204));
        pnlCodigo.setMinimumSize(new java.awt.Dimension(830, 320));
        pnlCodigo.setPreferredSize(new java.awt.Dimension(830, 320));
        pnlCodigo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        txtCodigo.setMaximumSize(new java.awt.Dimension(830, 320));
        txtCodigo.setMinimumSize(new java.awt.Dimension(830, 320));
        txtCodigo.setPreferredSize(new java.awt.Dimension(830, 320));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtCodigo);

        pnlCodigo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 810, 320));

        jFontChooser1.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jFontChooser1.setSelectedFontFamily("Roboto");
        jFontChooser1.setSelectedFontSize(16);
        pnlCodigo.add(jFontChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        pnlFondo.add(pnlCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, -1));

        pnlError.setBackground(new java.awt.Color(184, 184, 184));
        pnlError.setMaximumSize(new java.awt.Dimension(830, 130));
        pnlError.setMinimumSize(new java.awt.Dimension(830, 130));
        pnlError.setPreferredSize(new java.awt.Dimension(830, 130));
        pnlError.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtConsola.setColumns(20);
        txtConsola.setRows(5);
        txtConsola.setMaximumSize(new java.awt.Dimension(830, 130));
        txtConsola.setMinimumSize(new java.awt.Dimension(830, 130));
        txtConsola.setPreferredSize(new java.awt.Dimension(830, 130));
        jScrollPane3.setViewportView(txtConsola);

        pnlError.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 810, 130));

        pnlFondo.add(pnlError, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, -1, 170));

        getContentPane().add(pnlFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        menu.setBackground(new java.awt.Color(255, 255, 255));
        menu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menu.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        archivo.setBackground(new java.awt.Color(255, 255, 255));
        archivo.setForeground(new java.awt.Color(0, 0, 0));
        archivo.setText("Archivo");

        opNuevo.setForeground(new java.awt.Color(0, 0, 0));
        opNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevoArchivo20x20.png"))); // NOI18N
        opNuevo.setText("Nuevo...");
        opNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opNuevoActionPerformed(evt);
            }
        });
        archivo.add(opNuevo);

        opAbrir.setForeground(new java.awt.Color(0, 0, 0));
        opAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/abrirArchivo20x20.png"))); // NOI18N
        opAbrir.setText("Abrir...");
        opAbrir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opAbrirActionPerformed(evt);
            }
        });
        archivo.add(opAbrir);

        opGuardar.setForeground(new java.awt.Color(0, 0, 0));
        opGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardarArchivo20x20.png"))); // NOI18N
        opGuardar.setText("Guardar...");
        opGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opGuardarActionPerformed(evt);
            }
        });
        archivo.add(opGuardar);

        opGuardarComo.setForeground(new java.awt.Color(0, 0, 0));
        opGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardarComo20x20.png"))); // NOI18N
        opGuardarComo.setText("Guardar como...");
        opGuardarComo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opGuardarComoActionPerformed(evt);
            }
        });
        archivo.add(opGuardarComo);

        menu.add(archivo);

        editar.setForeground(new java.awt.Color(0, 0, 0));
        editar.setText("Editar");

        opCortar.setForeground(new java.awt.Color(0, 0, 0));
        opCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cortar20x20.png"))); // NOI18N
        opCortar.setText("Cortar");
        opCortar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editar.add(opCortar);

        opCopiar.setForeground(new java.awt.Color(0, 0, 0));
        opCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/copiar20x20.png"))); // NOI18N
        opCopiar.setText("Copiar");
        opCopiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editar.add(opCopiar);

        opPegar.setForeground(new java.awt.Color(0, 0, 0));
        opPegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pegar20x20.png"))); // NOI18N
        opPegar.setText("Pegar");
        opPegar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editar.add(opPegar);

        menu.add(editar);

        correr.setForeground(new java.awt.Color(0, 0, 0));
        correr.setText("Correr");

        opCompilar.setForeground(new java.awt.Color(0, 0, 0));
        opCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/compilar20x20.png"))); // NOI18N
        opCompilar.setText("Compilar");
        opCompilar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opCompilarActionPerformed(evt);
            }
        });
        correr.add(opCompilar);

        opEjecutar.setForeground(new java.awt.Color(0, 0, 0));
        opEjecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ejecutar20x20.png"))); // NOI18N
        opEjecutar.setText("Ejecutar");
        opEjecutar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opEjecutarActionPerformed(evt);
            }
        });
        correr.add(opEjecutar);

        menu.add(correr);

        opciones.setBackground(new java.awt.Color(255, 255, 255));
        opciones.setForeground(new java.awt.Color(0, 0, 0));
        opciones.setText("Opciones");

        opFuente.setBackground(new java.awt.Color(255, 255, 255));
        opFuente.setForeground(new java.awt.Color(0, 0, 0));
        opFuente.setText("Fuente");
        opFuente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opFuenteMouseClicked(evt);
            }
        });
        opFuente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opFuenteActionPerformed(evt);
            }
        });
        opciones.add(opFuente);

        jMenuItem1.setText("Tabla de tokens");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        opciones.add(jMenuItem1);

        menu.add(opciones);

        setJMenuBar(menu);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void opFuenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opFuenteMouseClicked
        //JFontChooser fc = new JFontChooser();
        //JOptionPane.showMessageDialog(null, fc,"Elegir fuente",JOptionPane.PLAIN_MESSAGE);
        //txtCodigo.setFont(fc.getSelectedFont());
    }//GEN-LAST:event_opFuenteMouseClicked

    private void imgEjecutarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgEjecutarMousePressed
        opCompilar.doClick();
        if(codeHasBeenCompiled){
            if(!errors.isEmpty()){
                JOptionPane.showMessageDialog(null, "Se encontró un error");
            }else{
                
            }
        }
    }//GEN-LAST:event_imgEjecutarMousePressed

    private void opFuenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opFuenteActionPerformed
        JFontChooser fc = new JFontChooser();
        JOptionPane.showMessageDialog(null, fc,"Elegir fuente",JOptionPane.PLAIN_MESSAGE);
        txtCodigo.setFont(fc.getSelectedFont());
    }//GEN-LAST:event_opFuenteActionPerformed

    private void imgNuevoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgNuevoMousePressed
        directorio.New();
        clearField();
    }//GEN-LAST:event_imgNuevoMousePressed

    private void imgAbrirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgAbrirMousePressed
        if(directorio.Open()){
            colorAnalysis();
            clearField();
        }
    }//GEN-LAST:event_imgAbrirMousePressed

    private void imgGuardarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgGuardarMousePressed
        if(directorio.Save()){
            clearField();
        }
    }//GEN-LAST:event_imgGuardarMousePressed

    private void imgGuardarComoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgGuardarComoMousePressed
        if(directorio.SaveAs()){
            clearField();
        }
    }//GEN-LAST:event_imgGuardarComoMousePressed

    private void imgCompilarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgCompilarMousePressed
        if(getTitle().contains("*") || getTitle().equals(title)){
            if(directorio.Save()){
                compile();
            }
        }else{
            compile();
        }
        //txtConsola.setText("Compilando...");
    }//GEN-LAST:event_imgCompilarMousePressed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        if(evt.getKeyCode() == 9){
            int pos = txtCodigo.getCaretPosition();
            txtCodigo.setText(txtCodigo.getText().replaceAll("\t", "    "));
            txtCodigo.setCaretPosition(pos+4);

        }
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        vtn_TablaTokens vtnTabla = new vtn_TablaTokens();
        vtnTabla.setVisible(true);
        Functions.clearDataInTable(vtnTabla.tblTokens);
        tokens.forEach(token -> {
            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(), "[" + token.getLine() + ", " + token.getColumn() + "]"};
            Functions.addRowDataInTable(vtnTabla.tblTokens, data);
        });
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void opNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opNuevoActionPerformed
        directorio.New();
        clearField();
    }//GEN-LAST:event_opNuevoActionPerformed

    private void opAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opAbrirActionPerformed
        if(directorio.Open()){
            colorAnalysis();
            clearField();
        }
    }//GEN-LAST:event_opAbrirActionPerformed

    private void opGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opGuardarActionPerformed
        if(directorio.Save()){
            clearField();
        }
    }//GEN-LAST:event_opGuardarActionPerformed

    private void opGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opGuardarComoActionPerformed
        if(directorio.SaveAs()){
            clearField();
        }
    }//GEN-LAST:event_opGuardarComoActionPerformed

    private void opCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opCompilarActionPerformed
        if(getTitle().contains("*") || getTitle().equals(title)){
            if(directorio.Save()){
                compile();
            }
        }else{
            compile();
        }
    }//GEN-LAST:event_opCompilarActionPerformed

    private void opEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opEjecutarActionPerformed
        //imgCompilar.doLayout();
        if(codeHasBeenCompiled){
            if(!errors.isEmpty()){
                JOptionPane.showMessageDialog(null, "Se encontró un error");
            }else{
                
            }
        }
    }//GEN-LAST:event_opEjecutarActionPerformed

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
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    new Compilador().setVisible(true);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu archivo;
    private javax.swing.JMenu correr;
    private javax.swing.JMenu editar;
    private javax.swing.JLabel imgAbrir;
    private javax.swing.JLabel imgCompilar;
    private javax.swing.JLabel imgEjecutar;
    private javax.swing.JLabel imgGuardar;
    private javax.swing.JLabel imgGuardarComo;
    private javax.swing.JLabel imgNuevo;
    private say.swing.JFontChooser jFontChooser1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenuItem opAbrir;
    private javax.swing.JMenuItem opCompilar;
    private javax.swing.JMenuItem opCopiar;
    private javax.swing.JMenuItem opCortar;
    private javax.swing.JMenuItem opEjecutar;
    private javax.swing.JMenuItem opFuente;
    private javax.swing.JMenuItem opGuardar;
    private javax.swing.JMenuItem opGuardarComo;
    private javax.swing.JMenuItem opNuevo;
    private javax.swing.JMenuItem opPegar;
    private javax.swing.JMenu opciones;
    private javax.swing.JPanel pnlBarraHerramientas;
    private javax.swing.JPanel pnlCodigo;
    private javax.swing.JPanel pnlError;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JTextPane txtCodigo;
    private javax.swing.JTextArea txtConsola;
    // End of variables declaration//GEN-END:variables
}
