package SyntaxErrorHandler;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class OutFileErrorListener extends BaseErrorListener {

    private final List<String> errors = new ArrayList<>();


    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {

        errors.add("In line " + line + ":" + charPositionInLine + " " + msg);
    }


    public void saveErrorFile(OutFileErrorListener SemanticErrorListener) throws FileNotFoundException {
        try{
            PrintWriter out = new PrintWriter("outErrors.txt");
            out.println("You had: "+errors.size()+" Lexical errors:");
            errors.stream().forEach((err) -> out.println(err));
            out.println();
            out.println();
            out.println("You had: "+SemanticErrorListener.getErrors().size()+" Syntax  errors:");
            SemanticErrorListener.getErrors().stream().forEach((err) -> out.println(err));
            out.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }/*POST= Return in a file Syntax and Semantic errors*/

    public List<String> getErrors() {
        return errors;
    }
}
