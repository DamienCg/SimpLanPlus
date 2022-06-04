package ast.node.ExpNodes;
import CheckEffect.Effect;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.STentry;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DerExpNode extends ExpNode{
    private String id;
    private STentry entry;
    private int nestingLevel;

    public DerExpNode(String id) {
        this.id = id; this.entry = null;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public TypeNode typeCheck() {
        return this.entry.getType().typeCheck();
    }

    @Override
    public String codeGeneration() {

        StringBuilder codeGenerated = new StringBuilder();

        codeGenerated.append("mv $fp $al //put in $al actual fp\n");

        codeGenerated.append("lw $al 0($al) //go up to chain\n".repeat(Math.max(0, nestingLevel - entry.getNestingLevel())));

        codeGenerated.append("lw $a0 ").append(entry.getOffset()).append("($al) //put in $a0 value of Id ").append(id).append("\n");
        System.err.println(entry.getOffset());
        return codeGenerated.toString();

    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> res = new ArrayList<>();

        this.entry = env.lookUp(id);

        if (entry == null)
            res.add(new SemanticError("variable "+id+" is not defined"));
        else {
            nestingLevel = env.getNestinglevel();
        }

        return res;
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> errors = new ArrayList<EffectError>();
        Effect effect = env.lookUpEffect(id);
        if(!effect.getIsInizialized()){
            errors.add(new EffectError("variable "+id+" is not initialized"));
        }
        else{
            effect.setUse(true);
        }
        return errors;
    }
    
}