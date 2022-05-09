package ast.node.ExpNodes;

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
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return callExpNode.checkSemantics(env);
    }
}