package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class NegExpNode extends ExpNode{

    private ExpNode negExpNode;

    public NegExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "-" + negExpNode.toString() ;
        return ret;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}