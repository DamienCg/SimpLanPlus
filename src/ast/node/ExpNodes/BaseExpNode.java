package ast.node.ExpNodes;

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
        ArrayList<SemanticError> semanticErrors = new ArrayList<SemanticError>();
        if (this.exp != null) {
            semanticErrors.addAll(this.exp.checkSemantics(env));
        }
        return semanticErrors;
    }

    @Override
    public TypeNode typeCheck() {
        return this.exp.typeCheck();
    }

}