package ast.node.ExpNodes;

import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;

public class NegExpNode extends ExpNode{

    public NegExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        return  "-" + this.toString() ;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return exp.checkSemantics(env);
    }

    @Override
    public TypeNode typeCheck() {
        if (!exp.typeCheck().isEqual(new TypeNode("int"))) {
            throw new RuntimeException("Trying to do negative on a non int");
        }
        return new TypeNode("int");
    }

    @Override
    public String codeGeneration() {
        StringBuilder codeGenerated = new StringBuilder();
        String loaded_exp = exp.codeGeneration();
        codeGenerated.append(loaded_exp).append("\n");
        codeGenerated.append("multi $a0 $a0 -1 //do negate\n");
        return codeGenerated.toString();
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        return exp.checkEffect(env);
    }


}