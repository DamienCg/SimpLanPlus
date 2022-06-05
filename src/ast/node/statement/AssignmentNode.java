package ast.node.statement;

import CheckEffect.Effect;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.STentry;
import ast.node.ExpNodes.DerExpNode;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class AssignmentNode implements Node {

    private final String id;
    private final Node exp;
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

        System.err.println("Asg "+id + " " + this.currentNL + " " + entry.getNestingLevel() + " " + entry.getOffset());

        if (isVar()){
            codeGenerated.append("mv $fp $al\n");
            codeGenerated.append("//Var loading\n");
            codeGenerated.append("lw $al 0($al) //go up to chain\n".repeat(Math.max(0, this.currentNL - entry.getNestingLevel())));
            codeGenerated.append("lw $al "+entry.getOffset()+"($al) //put in $a0 value of Id ").append(id).append("\n");
            codeGenerated.append("sw $a0 0($al) //put in $a0 value of Id ").append(id).append("\n");
        }

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

        if(!Ideffect.getIdRef().equals("")){
            Effect Ideffect2 = new Effect(Ideffect);
            String realID = Ideffect2.getIdRef();
            res.addAll(exp.checkEffect(env));
            env.updateEffect(id, Ideffect2);
            env.updateEffect(realID, Ideffect2);
            if(exp != null) {
                Ideffect2.setIsInizialized(true);
                env.updateEffect(realID,Ideffect2);
            }
        }

        if (exp != null) {
            res.addAll(exp.checkEffect(env));
            Ideffect.setIsInizialized(true);
            //Ideffect.setUse(true); //TODO vedere se funziona altrimenti eliminare
            if(exp instanceof DerExpNode){
                String idExp = ((DerExpNode) exp).getId();
                Effect Ideffect2 = env.lookUpEffect(idExp);
                env.updateEffect(idExp,Ideffect2);

            }
            env.updateEffect(id, Ideffect);

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

    public boolean isVar(){
        return ((TypeNode)entry.getType()).getisVar();
    }
}

