import Lexer.*;
import ast.node.Node;
import ast.node.SimpLanPlusVisitorImpl;
import parser.SimpLanPlusParser;
import SyntaxErrorHandler.*;
import org.antlr.v4.runtime.*;
import util.Environment;
import util.SemanticError;

import java.io.FileNotFoundException;
import java.io.IOException;
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

            //parser.removeErrorListeners();
            //lexer.removeErrorListeners();
            lexer.addErrorListener(SyntaxtErrorListener);
            parser.addErrorListener(SemanticErrorListener);
            SimpLanPlusVisitorImpl visitor = new SimpLanPlusVisitorImpl();
            Node ast = visitor.visit(parser.block());
            //System.out.println(ast.toString());

            if (SyntaxtErrorListener.getErrors().size() > 0 || SemanticErrorListener.getErrors().size() > 0) {
                SyntaxtErrorListener.saveErrorFile(SemanticErrorListener);
                System.exit(0);
            }
            // NO Lexical OR Syntax ERRORS
            else {
                System.out.println("NO Lexical OR Syntax ERRORS");
                System.out.println("Start semantic analysis");
                Environment env = new Environment();
                ArrayList<SemanticError> myErrors = ast.checkSemantics(env);
                if (myErrors.size() >= 1) {
                    System.out.println("Semantic errors found");
                    for (SemanticError error : myErrors) {
                        System.out.println(error);
                    }
                    System.exit(0);
                } else {
                    System.out.println("** Now Type Check **");
                    ast.typeCheck();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error: No file to compile");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}





