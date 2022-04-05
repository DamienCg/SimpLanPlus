package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BaseExpNode extends ExpNode{

    private ExpNode BaseExpNode;

    public BaseExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "Base: (" + BaseExpNode.toString() +")" ;
        return ret;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}