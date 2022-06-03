package ast.node.statement;

import CheckEffect.Effect;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.STentry;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class AssignmentNode implements Node {

    private String id;
    private Node exp;
    private STentry entry;
    private int currentNL;

    public AssignmentNode(String id, Node exp){
        this.id = id;
        this.exp = exp;
        this.currentNL = 0;
    }

    @Override
    public TypeNode typeCheck() {
                if(!entry.getType().typeCheck().isEqual(exp.typeCheck())){
            throw new RuntimeException("Type error: " + id + " is not of type " + exp.typeCheck().toString());
        }
        return new TypeNode("void");
    }



    @Override
    public String codeGeneration() {
        StringBuilder codeGenerated = new StringBuilder();
        codeGenerated.append(exp.codeGeneration()).append("\n");
        codeGenerated.append("mv $fp $al\n");

        codeGenerated.append("lw $al 0($al) \n".repeat(Math.max(0,this.currentNL-this.entry.getNestingLevel())));
        codeGenerated.append("addi $al $al" + this.entry.getOffset() + "\n");
        codeGenerated.append("sw $a0 0($al) // 0($al) = $a0 ").append(id).append("=exp\n");

        return codeGenerated.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        // check if id is already declared
        STentry IdEntry = env.lookUp(id);
        this.currentNL = env.getNestinglevel();
        this.entry = IdEntry;
        if(IdEntry == null){
            res.add(new SemanticError("Undeclared variable " + id));
        }

        if(exp != null) {
            res.addAll(exp.checkSemantics(env));
        }

         return res;
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> res = new ArrayList<>();
        Effect Ideffect = env.lookUpEffect(id);

        if(exp != null) {
            res.addAll(exp.checkEffect(env));
            env.updateEffect(id,new Effect(true,false));
        }

        return res;
    }


    @Override
    public String toString() {
        String ret = "AssignmentNode{";
        if(id != null)
            ret += id;
        if(exp != null)
            ret += " = " + exp;
        return ret + "}";
    }
}

