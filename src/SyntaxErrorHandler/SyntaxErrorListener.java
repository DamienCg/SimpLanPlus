package SyntaxErrorHandler;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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


    public void saveErrorFile() throws FileNotFoundException {

        try{
            PrintWriter out = new PrintWriter("ErrorFile.txt");
            out.println("Errore: "+ errorMessages);
            out.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

    }

}
