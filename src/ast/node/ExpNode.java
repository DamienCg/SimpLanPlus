package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ExpNode implements Node {

    private Node exp;

    public ExpNode(Node exp){
        this.exp = exp;
    }

    @Override
    public String toString() {
        String ret = "ExpNode{\n";
        if (exp != null) {
            ret += exp.toString();
        }
         return ret + "}\n";
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
