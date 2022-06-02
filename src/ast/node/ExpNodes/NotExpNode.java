package ast.node.ExpNodes;

import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class NotExpNode extends BaseExpNode{


    public NotExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        return "!" + this.toString();
    }

    @Override
    public TypeNode typeCheck() {
        TypeNode expType = exp.typeCheck();

        if (! (expType.isEqual(new TypeNode("bool")))) {
            throw new RuntimeException("Trying to do negate (!) of a non bool");
        }

        return new TypeNode("bool");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return exp.checkSemantics(env);
    }

    @Override
    public String codeGeneration() {

        StringBuilder codeGenerated = new StringBuilder();
        String loaded_exp = exp.codeGeneration();
        codeGenerated.append(loaded_exp).append("\n");
        codeGenerated.append("not $a0 $a0\n");

        return codeGenerated.toString();
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        return exp.checkEffect(env);
    }

}
