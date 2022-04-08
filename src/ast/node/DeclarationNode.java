package ast.node;

import ast.node.declaration.DecFunNode;
import ast.node.declaration.DecVarNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DeclarationNode implements Node {
    // declaration : decFun
    //            | decVar ;
    private Node dec;

    public DeclarationNode(Node dec){
        this.dec = dec;
    }

    @Override
    public String toString() {
        String ret = "DeclarationNode{\n";
        return ret += dec.toString() + "}";
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
