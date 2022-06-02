package ast.node.ExpNodes;

import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;

public class CallExpNode implements Node{

    private Node callExpNode;

    public CallExpNode(Node call){
        callExpNode = call;
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "Call:" + callExpNode.toString() ;
        return ret;
    }

    @Override
    public TypeNode typeCheck() {
        return callExpNode.typeCheck();
    }

    @Override
    public String codeGeneration() {

        return callExpNode.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return callExpNode.checkSemantics(env);
    }


    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {

        return callExpNode.checkEffect(env);
    }

}