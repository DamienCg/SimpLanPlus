package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DerExpNode extends ExpNode{

    private Node DerExpNode; //[TOSE] Meglio idNode?

    public DerExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "Id: " + DerExpNode.toString() ;
        return ret;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}