package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DeclarationNode implements Node {
    // declaration : decFun
    //            | decVar ;
    private Node decFun;
    private Node decVar;

    public DeclarationNode(Node decFun, Node decVar) {
        this.decFun = decFun;
        this.decVar = decVar;
    }

    public Node getDecFun() {
        return decFun;
    }

    public Node getDecVar() {
        return decVar;
    }

    @Override
    public String toString() {
        String ret = "DeclarationNode{\n";
        if (decFun != null) {
            ret += decFun.toString();
        }
        if (decVar != null) {
            ret += decVar.toString();
        }
        return ret + "}";
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
