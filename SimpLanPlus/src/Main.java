import Parser.SimpLanPlusLexer;
import Parser.SimpLanPlusParser;
import org.antlr.v4.runtime.*;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        CharStream charStreamES1 = CharStreams.fromString("{ int a; int b; int c = 1 ; if (c > 1) { b = c ; } else { a = b ; }}");
        SimpLanPlusLexer simpleLexer = new SimpLanPlusLexer(charStreamES1);
        SimpLanPlusParser simpleparser = new SimpLanPlusParser(new CommonTokenStream(simpleLexer));

        simpleparser.removeErrorListeners();


        final List<String> errorMessages = new ArrayList<>();

        simpleparser.addErrorListener(new BaseErrorListener(){
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                errorMessages.add(msg);
            }
        });

        System.out.println("errorMessages: " + errorMessages);
        


    }
}