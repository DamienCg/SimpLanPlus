package Interpreter;

import Interpreter.Lexer.SVMLexer;
import Interpreter.parser.*;
import Interpreter.ast.Instruction;
import Interpreter.ast.SVMVisitorImpl;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Interpreter {
    public static void run(String bytecode) throws IOException {

        saveCode(bytecode);
        SVMLexer lexerASM = new SVMLexer(CharStreams.fromString(bytecode));

        CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);

        if (lexerASM.lexicalErrors>0 ){
            System.out.println("You had: "+lexerASM.lexicalErrors+" lexical errors in SVM.");
            System.exit(1);
        }

        SVMParser parserASM = new SVMParser(tokensASM);

        SVMVisitorImpl visitorSVM = new SVMVisitorImpl();
        visitorSVM.visit(parserASM.assembly());

        if (parserASM.getNumberOfSyntaxErrors()>0) {
            System.out.println("You had: "+parserASM.getNumberOfSyntaxErrors()+" syntax errors in SVM.");
            System.exit(2);
        }

        Instruction[] generatedCode = visitorSVM.getCode();

        SVM vm = new SVM(generatedCode);
        vm.cpu();
    }

    public static void saveCode(String bytecode) throws IOException {
        String fileAsm = "generatedCode.simplanplus";
        BufferedWriter out = new BufferedWriter(new FileWriter(fileAsm));
        out.write(bytecode);
        out.close();

        SVMLexer lexerASM = new SVMLexer(CharStreams.fromFileName(fileAsm));

    }
}
