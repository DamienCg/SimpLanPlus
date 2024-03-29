package ast.node.ExpNodes;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.STentry;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;

public class DerExpNode extends ExpNode{
    private final String id;
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
        if (isVar()){
            codeGenerated.append("//Var loading\n");
            codeGenerated.append("lw $al 0($al) //go up to chain\n".repeat(Math.max(0, nestingLevel - entry.getNestingLevel())));
            codeGenerated.append("lw $al ").append(entry.getOffset()).append("($al) //get value\n"); //mette in al l'indirizzo della var parametro attuale
            codeGenerated.append("lw $a0 0($al) //put in $a0 value of Id ").append(id).append("\n");
        }else{
            codeGenerated.append("//Value loading\n");
            codeGenerated.append("lw $al 0($al) //go up to chain\n".repeat(Math.max(0, nestingLevel - entry.getNestingLevel())));
            codeGenerated.append("lw $a0 ").append(entry.getOffset()).append("($al) //put in $a0 value of Id ").append(id).append("\n");
        }

        return codeGenerated.toString();

    }

    public String codeGenVar(){
        StringBuilder codeGenerated = new StringBuilder();

        codeGenerated.append("mv $fp $al //put in $al actual fp\n");
        codeGenerated.append("lw $al 0($al) //go up to chain\n".repeat(Math.max(0, nestingLevel - entry.getNestingLevel())));
        codeGenerated.append("addi $a0 $al ").append(entry.getOffset()).append(" //put in $a0 value of Id ").append(id).append("\n");

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
            entry.setUsed();
        }

        return res;
    }
//int g = f(s);
    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> errors = new ArrayList<>();
        if (!env.lookUp(id).getEffect()){
            errors.add(new EffectError("variable "+id+" is not initialized"));
        }
        return errors;
    }



    public boolean isVar(){
        return ((TypeNode)entry.getType()).getisVar();
    }
    
}