import Lexer.*;
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
        String fileName = "input.simplanplus";
        try {
            String InputFile = Files.readString(Path.of(fileName), StandardCharsets.US_ASCII);
            SimpLanPlusLexer lexer = new SimpLanPlusLexer(CharStreams.fromString(InputFile));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SimpLanPlusParser parser = new SimpLanPlusParser(tokens);

            SyntaxAndSemanticErrorListener SyntaxtErrorListener = new SyntaxAndSemanticErrorListener();
            SyntaxAndSemanticErrorListener SemanticErrorListener = new SyntaxAndSemanticErrorListener();

            parser.removeErrorListeners();
            lexer.removeErrorListeners();
            lexer.addErrorListener(SyntaxtErrorListener);
            parser.addErrorListener(SemanticErrorListener);
            parser.block();

            if (SyntaxtErrorListener.getErrors().size() > 0 || SemanticErrorListener.getErrors().size() > 0) {
                SyntaxtErrorListener.saveErrorFile(SemanticErrorListener);
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





