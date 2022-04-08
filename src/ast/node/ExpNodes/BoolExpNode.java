package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BoolExpNode implements Node{

    private boolean BoolExpNode;

    public BoolExpNode(boolean boolExpNode) {
        BoolExpNode = boolExpNode;
    }

    @Override
    public String toString() {
        String ret = "";
        if (BoolExpNode == true)
            ret += "Bool: true";
         else
            ret += "Bool: false";
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