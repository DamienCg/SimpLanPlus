package ast.node.statement;

import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.ExpNodes.ExpNode;
import ast.node.Node;
import ast.node.TypeNode;
import ast.node.declaration.DecFunNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ReturnNode implements Node {
    private Node exp;
    private DecFunNode parent_f;
    private int current_nl;


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
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> ret = new ArrayList<>();
        if(exp != null) {
            return exp.checkEffect(env);
        }
        return ret;
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
    public String codeGeneration() {
        StringBuilder codeGenerated = new StringBuilder();

        if( exp != null){
            codeGenerated.append(exp.codeGeneration()).append("\n");
        }

        codeGenerated.append("b ").append(parent_f.get_end_fun_label()).append("\n");

        return codeGenerated.toString();

    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> ret = new ArrayList<>();
        this.current_nl = env.getNestinglevel();
        this.parent_f = env.getLastParentFunction();
        if(exp != null) {
            return exp.checkSemantics(env);
        }
        this.parent_f = (DecFunNode) env.getLastParentFunction();

        return ret;
    }
}
