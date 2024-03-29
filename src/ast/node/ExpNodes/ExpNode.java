package ast.node.ExpNodes;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ExpNode implements Node {

    protected Node exp;
    // Protected because it must be accessed by subclasses

    public ExpNode(Node exp){
        this.exp = exp;
    }
    public ExpNode(){}


    @Override
    public String toString() {
        if(exp != null) {
            return "\nExpNode{ }";
        }
        else return "";
    }

    public String getId(){
        return exp.toString();
    }

    @Override
    public TypeNode typeCheck() {
        return exp.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);

    }
    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {

        return exp.checkEffect(env);
    }


}
