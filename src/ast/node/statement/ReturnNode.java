package ast.node.statement;

import ast.Label;
import ast.node.ExpNodes.ExpNode;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ReturnNode implements Node {
    private Node exp;

    public ReturnNode(Node exp) {

        this.exp =  exp;
    }

    public Node getExp() {

        return exp;
    }

    public void setExp(ExpNode exp) {

        this.exp = exp;
    }


    @Override
    public String toString() {
     String ret = "return";
        if(exp != null) {
         ret += " " + exp.toString();
        }
     return ret + ";";
    }

    @Override
    public TypeNode typeCheck() {
       if(exp != null) {
           return exp.typeCheck();
       }
       return new TypeNode("void");
    }

    @Override
    public String codeGeneration(Label labelManager) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> ret = new ArrayList<>();
        if(exp != null) {
            return exp.checkSemantics(env);
        }
        return ret;
    }
}
