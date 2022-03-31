package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class PrintNode implements Node{

    private Node exp;
    // print	    : 'print' exp;

    PrintNode(Node exp){
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return "\n"+indent+"Print "+exp.toPrint(indent);
    }

    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}
