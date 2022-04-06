package ast.node;

import ast.node.declaration.DecFunNode;
import ast.node.declaration.DecVarNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DeclarationNode implements Node {
    // declaration : decFun
    //            | decVar ;
    private DecFunNode decFun;
    private DecVarNode decVar;

    public DeclarationNode(DecFunNode decFun, DecVarNode decVar) {
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
