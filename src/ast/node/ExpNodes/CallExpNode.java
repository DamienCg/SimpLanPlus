package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class CallExpNode implements Node{

    private Node callExpNode; //[TOSE] Non è di tipo ExpNode perché contiene il nodo "CALL"

    public CallExpNode(Node call){
        callExpNode = call;
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "Call:" + callExpNode.toString() ; //[TOSE] devo scrivere pari alla grammatica?
        return ret;
    }

    @Override
    public void typeCheck() {

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