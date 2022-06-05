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
    Boolean is_if = false;


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
        if (is_if){
            //codeGenerated.append("lw $fp 0($fp) //Load old $fp pushed \n".repeat(Math.max(0, current_nl - parent_f.getBlock().getCurrent_nl())));
            codeGenerated.append("subi $sp $fp 1 //Restore stack pointer as before block creation in return \n");
            codeGenerated.append("lw $fp 0($fp) //Load old $fp pushed \n");
        }
        codeGenerated.append("b ").append(parent_f.get_end_fun_label()).append("\n");


        return codeGenerated.toString();

    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> ret = new ArrayList<>();
        this.parent_f = env.getLastFuncDecl();
        this.is_if = env.getIf();
        if(exp != null) {
            return exp.checkSemantics(env);
        }
        return ret;
    }
}
