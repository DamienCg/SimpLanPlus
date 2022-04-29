package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BinExpNode implements Node {

    private String op;
    private Node left; //[TOSE] ho messo expNode perché nella grammatica sono assegnate ad espressioni
    private Node right;

    public BinExpNode(Node left, Node right, String op) {
        this.left =  left;
        this.right =  right;
        this.op = op;
    }

    @Override
    public String toString() {
        String ret = "";
        ret +=left.toString() + " " + op + " " + right.toString();
        return ret;
    }

    @Override
    public void typeCheck() {

    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // Ho fatto questo, dovrebbe andare bene, perchè tanto richiami i figli
        ArrayList<SemanticError> ret = new ArrayList<SemanticError>();
        if(this.left!=null) {
            ret.addAll(left.checkSemantics(env));
        }
        if(this.right!=null) {
           ret.addAll(right.checkSemantics(env));
        }
        return ret;

    }

}
