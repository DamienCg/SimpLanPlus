package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BinExpNode implements Node {

    private String op;
    private ExpNode left; //[TOSE] ho messo expNode perché nella grammatica sono assegnate ad espressioni
    private ExpNode right;

    public BinExpNode(Node left, Node right, String op) {
        this.left = (ExpNode) left;
        this.right = (ExpNode) right;
        this.op = op;
    }

    @Override
    public String toString() {
        String ret = "";
        ret +=left.toString() + " " + op + " " + right.toString();
        return ret;
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
