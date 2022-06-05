package ast.node.declaration;

import CheckEffect.Effect;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.STentry;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;

public class DecVarNode implements Node {

    private final TypeNode type;
    private final String id;
    private final Node exp;
    private STentry entry;
    private int currentNL;

    public DecVarNode(Node type, String id, Node exp) {
        this.type = (TypeNode) type;
        this.id =  id;
        this.exp = exp;
        this.entry = null;
        this.currentNL = 0;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public TypeNode typeCheck() {
        if (exp != null) {
            if (type.typeCheck().getType().equals(exp.typeCheck().getType())) {
                return type;
            }
            else {
                throw new RuntimeException("Type error in declaration of variable " + id);
            }
        }
            return type;
    }

    @Override
    public String codeGeneration() {

        StringBuilder codeGenerated = new StringBuilder();
        codeGenerated.append("li $a0 0\n");
        codeGenerated.append("push $a0\n");
        return codeGenerated.toString();

    }

    @Override
    public String toString() {
       if (exp != null)
           return type.toString() + " " + id + " = " + exp.toString() + ";";
       else
           return type.toString() + " " + id + ";";

    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> errors = new ArrayList<EffectError>();
        Effect newEffect = new Effect(false);
        env.addDecl(id, newEffect);

        if(this.exp!=null){
            env.updateEffect(id,new Effect(true));
            errors.addAll(this.exp.checkEffect(env));
        }
        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {


        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if(this.exp!=null)
            errors.addAll(this.exp.checkSemantics(env));


        int new_offset = env.decOffset(); // return offset decremented by 1
        STentry newEntry = new STentry(env.getNestinglevel(), type, new_offset);
        SemanticError error = env.addDecl(id, newEntry);

        if (error != null) {
            errors.add(error);
        }


        this.entry = newEntry;
        this.currentNL = env.getNestinglevel();

        return errors;
    }

    public String codeGenAsg(){
        if (this.exp == null) {
            return "";
        }
        StringBuilder codeGenerated = new StringBuilder();
        codeGenerated.append(exp.codeGeneration()).append("\n");
        codeGenerated.append("mv $fp $al\n");
        codeGenerated.append("lw $al 0($al) \n".repeat(Math.max(0,this.currentNL-this.entry.getNestingLevel())));
        codeGenerated.append("addi $al $al" + this.entry.getOffset() + "\n");
        codeGenerated.append("sw $a0 0($al) // 0($al) = $a0 ").append(id).append("=exp\n");
        return codeGenerated.toString();
    }

}