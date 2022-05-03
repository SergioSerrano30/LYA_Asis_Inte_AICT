


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import jflex.exceptions.SilentExit;

/**
 *
 * @author sergi
 */
public class ExecuteJFlex {
    public static void main(String omega[]) {
        //String nombreArchivo1 = "Lexer.flex";
        /*Path lexerFile = Paths.get("Lexer.flex").toAbsolutePath(),
                lexerFileColor = Paths.get("LexerColor.flex").toAbsolutePath();*/
        String lexerFile = System.getProperty("user.dir") + "\\src\\Lexer.flex",
                lexerFileColor = System.getProperty("user.dir") + "\\src\\LexerColor.flex";
        try {
            jflex.Main.generate(new String[]{lexerFile, lexerFileColor});
            
        } catch (SilentExit ex) {
            System.out.println("Error al compilar/generar el archivo flex: " + ex);
        }
        System.out.println("Ruta lexer File: "+lexerFile);
        System.out.println("Ruta lexer FileColor: "+lexerFileColor);
        //System.out.println("Ruta absoluta File: "+rutaAbsoluta);
        
        
    }
}
