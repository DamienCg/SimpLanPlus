package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class NegExpNode extends BaseExpNode{

    public NegExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        return  "-" + this.toString() ;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}