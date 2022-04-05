package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BoolExpNode extends ExpNode{

    private boolean BoolExpNode;

    public BoolExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        String ret = "";
        if (BoolExpNode == true) {
            ret += "Bool: true";
        } else {
            ret += "Bool: false";
        }
        return ret;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}