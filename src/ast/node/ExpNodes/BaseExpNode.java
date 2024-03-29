package ast.node.ExpNodes;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BaseExpNode extends ExpNode{

    public BaseExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "(" + this.exp.toString() +")" ;
        return ret;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> semanticErrors = new ArrayList<>();
        if (this.exp != null) {
            semanticErrors.addAll(this.exp.checkSemantics(env));
        }
        return semanticErrors;
    }

    @Override
    public TypeNode typeCheck() {
        return this.exp.typeCheck();
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        return exp.checkEffect(env);
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration();
    }
}