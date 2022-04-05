package ast.node.statement;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class AssignmentNode implements Node {

    // assignment  : ID '=' exp ;
    private Node id;
    private Node exp;

    AssignmentNode(Node id, Node exp){
        this.id = id;
        this.exp = exp;
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

    @Override
    public String toString() {
        String ret = "AssignmentNode{";
        if(id != null)
            ret += id.toString();
        if(exp != null)
            ret += " = " + exp.toString();
        return ret + "}";
    }
}
