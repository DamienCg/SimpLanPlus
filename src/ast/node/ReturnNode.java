package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ReturnNode implements Node {
    // ret : 'return' (exp)?;
    private Node exp;

    public ReturnNode(Node exp) {

        this.exp = exp;
    }

    public Node getExp() {

        return exp;
    }

    public void setExp(Node exp) {

        this.exp = exp;
    }


    @Override
    public String toString() {
     String ret = "returnNode{ ";
        if(exp != null) {
         ret += " " + exp.toString();
        }
     return ret + ";";
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
