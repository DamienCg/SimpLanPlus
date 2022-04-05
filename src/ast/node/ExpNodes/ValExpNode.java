package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ValExpNode extends ExpNode{

    private Integer ValExpNode;

    public ValExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "Int: " + ValExpNode;
        return ret;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}