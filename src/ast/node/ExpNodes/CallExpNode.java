package ast.node.ExpNodes;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class CallExpNode extends ExpNode{

    private Node callExpNode; //[TOSE] Non è di tipo ExpNode perché contiene il nodo "CALL"

    //[TOSE] Serve un altro costruttore?
    public CallExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "Call:" + callExpNode.toString() ; //[TOSE] devo scrivere pari alla grammatica?
        return ret;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}