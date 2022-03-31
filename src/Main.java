import Lexer.*;
import ast.*;
import parser.SimpLanPlusParser;
import SyntaxErrorHandler.*;
import org.antlr.v4.runtime.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        System.out.println("##### SimpLanPlus Compiler  ###");
        String name = "inputTest";
        String ext = ".txt";
        try {
            String InputFile = Files.readString(Path.of(name+ext), StandardCharsets.US_ASCII);
            SimpLanPlusLexer lexer = new SimpLanPlusLexer(CharStreams.fromString(InputFile));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SimpLanPlusParser parser = new SimpLanPlusParser(tokens);

            SyntaxErrorListener MyErrorListener = new SyntaxErrorListener();
            parser.removeErrorListeners();
            //lexer.removeErrorListeners();
            lexer.addErrorListener(MyErrorListener);
            parser.block();

            if (MyErrorListener.getErrors().size() > 0) {
                MyErrorListener.saveErrorFile();
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error: No file to compile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





