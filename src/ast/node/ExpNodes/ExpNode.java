package ast.node.ExpNodes;

import ast.node.BlockNode;
import ast.node.Node;
import ast.node.statement.*;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ExpNode implements Node {

    protected Node exp;
    // Protected because it must be accessed by subclasses

    public ExpNode(Node exp){
        this.exp = exp;
    }

    @Override
    public String toString() {
        if(exp != null) {
            return "\nExpNode{" + exp.toString() + "}";
        }
        else return "";
    }

    public String getId(){
        return exp.toString();
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
        return exp.checkSemantics(env);

    }

}
