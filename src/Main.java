import Lexer.SimpLanPlusLexer;
import ast.SimpLanPlusVisitorImpl;
import ast.node.Node;
import parser.SimpLanPlusParser;
import SyntaxErrorHandler.SyntaxErrorListener;
import org.antlr.v4.runtime.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {

            String InputFile = Files.readString(Path.of("inputTest.txt"), StandardCharsets.US_ASCII);

            SimpLanPlusLexer lexer = new SimpLanPlusLexer(CharStreams.fromString(InputFile));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SimpLanPlusParser parser = new SimpLanPlusParser(tokens);

            SyntaxErrorListener MyErrorListener = new SyntaxErrorListener();
            parser.removeErrorListeners();
            //lexer.removeErrorListeners();
            lexer.addErrorListener(MyErrorListener);

            SimpLanPlusVisitorImpl visitor = new SimpLanPlusVisitorImpl();
            Node ast = visitor.visit(parser.block()); //generazione AST
            parser.block();



        }
        catch (Exception e)
        {e.printStackTrace();}
    }
}





