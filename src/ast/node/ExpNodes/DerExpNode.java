package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DerExpNode implements Node{

    private Node DerExpNode; //[TOSE] Meglio idNode?

    public DerExpNode(Node DerExpNode) {
        this.DerExpNode = DerExpNode;
    }

    @Override
    public String toString() {
        return "ID: " + DerExpNode.toString() ;
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