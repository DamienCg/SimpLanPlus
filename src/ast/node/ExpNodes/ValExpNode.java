package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ValExpNode implements Node {

    private Integer ValExpNode;

    public ValExpNode(int val) {
        this.ValExpNode = val;
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "Int: " + ValExpNode;
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

        return new ArrayList<SemanticError>();
    }
}