package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ExpNode implements Node {

    private Node exp;

    public ExpNode(Node exp){
        this.exp = exp;
    }

    @Override
    public String toString() {
        if(exp != null) {
            return "\nExpNode{" + exp.toString() + "}";
        }
        else return "";
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
