package ast.node.declaration;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DecVarNode implements Node {

    //decVar      : type ID ('=' exp)? ';' ;

    private Node type;
    private Node id;
    private Node exp;

    public DecVarNode(Node type, Node id, Node exp) {
        this.type = type;
        this.id = id;
        this.exp = exp;
    }

    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public String toString() {
       String ret = "";
       if (exp != null)
           ret += type.toString() + " " + id.toString() + " = " + exp.toString() + ";";
       else
           ret += type.toString() + " " + id.toString() + ";";

       return ret;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}
