package ast.node.ExpNodes;

import ast.node.Node;
import ast.node.TypeNode;
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
    public TypeNode typeCheck() {
        return new TypeNode("int");
    }

    @Override
    public String codeGeneration() {
        return "li $a0 "+ValExpNode+"\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return new ArrayList<SemanticError>();
    }
}