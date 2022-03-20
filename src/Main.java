import ParserAndLexer.SimpLanPlusLexer;
import ParserAndLexer.SimpLanPlusParser;
import SyntaxErrorHandler.SyntaxErrorListener;
import org.antlr.v4.runtime.*;

public class Main {
    public static void main(String[] args) {
        String source = "{ int a; int b; int c = 1 ;\n" +
                "     if (c > 1 { b = c ; } else { a = b  }\n" +
                "   }";

        SimpLanPlusLexer lexer = new SimpLanPlusLexer(CharStreams.fromString(source));
        SimpLanPlusParser parser = new SimpLanPlusParser(new CommonTokenStream(lexer));

        SyntaxErrorListener MyErrorListner = new SyntaxErrorListener();
        parser.addErrorListener(MyErrorListner);
        parser.block();
        MyErrorListner.printError();

    }
}





