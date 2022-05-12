package ast.node.ExpNodes;
import ast.Label;
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
    public TypeNode typeCheck() {
        return exp.typeCheck();
    }

    @Override
    public String codeGeneration(Label labelManager) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);

    }

}
