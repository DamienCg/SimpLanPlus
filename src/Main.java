import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import Interpreter.Interpreter;
import Lexer.*;
import ast.node.Node;
import ast.node.SimpLanPlusVisitorImpl;
import parser.SimpLanPlusParser;
import SyntaxErrorHandler.*;
import org.antlr.v4.runtime.*;
import util.Environment;
import util.SemanticError;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("##### SimpLanPlus Compiler  ###");
        String fileName = "input.simplanplus";

        try {
            String InputFile = Files.readString(Path.of(fileName), StandardCharsets.US_ASCII);
            SimpLanPlusLexer lexer = new SimpLanPlusLexer(CharStreams.fromString(InputFile));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SimpLanPlusParser parser = new SimpLanPlusParser(tokens);

            OutFileErrorListener SyntaxtErrorListener = new OutFileErrorListener();
            OutFileErrorListener SemanticErrorListener = new OutFileErrorListener();

            lexer.addErrorListener(SyntaxtErrorListener);
            parser.addErrorListener(SemanticErrorListener);
            SimpLanPlusVisitorImpl visitor = new SimpLanPlusVisitorImpl();
            Node ast = visitor.visit(parser.init());

            if (SyntaxtErrorListener.getErrors().size() > 0 || SemanticErrorListener.getErrors().size() > 0) {
                SyntaxtErrorListener.saveErrorFile(SemanticErrorListener);
                System.exit(0);
            }
            // NO Lexical OR Syntax ERRORS
            else {
                Environment env = new Environment();
                ArrayList<SemanticError> myErrors = ast.checkSemantics(env);
                if (myErrors.size() >= 1) {
                    for (SemanticError error : myErrors) {
                        System.err.println("Error: "+error);
                    }
                    System.exit(0);
                } else {
                    ast.typeCheck();
                    EffectEnvironment envEffect = new EffectEnvironment();
                    ArrayList<EffectError> myEffectErrors = ast.checkEffect(envEffect);
                    if (myEffectErrors.size() >= 1) {
                        for (EffectError error : myEffectErrors) {
                            System.err.println("Error: "+error);
                        }
                        System.exit(0);
                    }else {
                        String code = ast.codeGeneration();
                        Interpreter.run(code);
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error: No file to compile");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}





