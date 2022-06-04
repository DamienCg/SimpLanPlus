package ast.node.ExpNodes;

import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;

public class BoolExpNode extends ExpNode{

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
    public TypeNode typeCheck() {
        return new TypeNode("bool");
    }

    @Override
    public String codeGeneration() {
        return "li $a0 "+(BoolExpNode?1:0)+"\n";
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        return new ArrayList<EffectError>();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return new ArrayList<SemanticError>();
    }
}