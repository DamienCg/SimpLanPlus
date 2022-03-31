package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class AssignmentNode implements Node{

    // assignment  : ID '=' exp ;
    private Node id;
    private Node exp;

    AssignmentNode(Node id, Node exp){
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return "\n"+indent + "Assignment: " + id.toPrint(indent) + " = " + exp.toPrint(indent);
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
