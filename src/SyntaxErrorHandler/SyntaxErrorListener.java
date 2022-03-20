package SyntaxErrorHandler;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import java.util.ArrayList;
import java.util.List;

public class SyntaxErrorListener extends BaseErrorListener {

    private final List<String> errorMessages = new ArrayList<>();

    public SyntaxErrorListener(){}


    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        errorMessages.add("line " + line + ":" + charPositionInLine + " " + msg);
    }

    public void printError(){
        System.out.println("errorMessages: " + errorMessages);
    }

}
