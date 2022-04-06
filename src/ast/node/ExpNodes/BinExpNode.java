package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BinExpNode extends ExpNode {

    private String op;
    private ExpNode left; //[TOSE] ho messo expNode perché nella grammatica sono assegnate ad espressioni
    private ExpNode right;

    public BinExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        String ret = "";
        ret +=left.toString() + " " + op + " " + right.toString();
        return ret;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }


}