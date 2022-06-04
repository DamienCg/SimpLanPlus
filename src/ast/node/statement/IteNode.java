package ast.node.statement;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.BlockNode;
import ast.node.Node;
import ast.node.StatementNode;
import ast.node.TypeNode;
import util.Environment;
import util.LabelManager;
import util.SemanticError;

import java.util.ArrayList;

public class IteNode implements Node {
    public Node exp;
    public Node ifstatement;
    public Node elsestatement;

    public IteNode(Node exp, Node ifstatement, Node elsestatement) {
        this.exp = exp;
        this.ifstatement = ifstatement;
        this.elsestatement = elsestatement;
    }

    @Override
    public String toString() {
        String ret = "if (" + exp.toString() + ") \n";
        if (elsestatement != null) {
            return ret += ifstatement.toString() + "\n else \n" + elsestatement.toString() + "\n";
        }
        else {
            return ret += ifstatement.toString() + "\n";
        }
    }

    @Override
    public TypeNode typeCheck() {
        TypeNode thenType = ifstatement.typeCheck();

        if(thenType == null)
            throw new RuntimeException("if statement is empty");


        if (!(exp.typeCheck().isEqual(new TypeNode("bool")))) {
            throw new RuntimeException("Non boolean condition inside if: " + exp.toString());
        }
        if(ifstatement != null){
            if(elsestatement != null){
                TypeNode elseType = elsestatement.typeCheck();
                if(elseType == null )
                    throw new RuntimeException("else statement is empty");

                if(!thenType.isEqual(elseType)){
                    throw new RuntimeException("Different types inside if: " + thenType.getType() + " and " + elseType.getType());
                }
            }
        }
        return thenType;
    }

    /*
    * gamma -| exp: T    gamma -| ifstatement: T1    gamma -| elsestatement: T2
    * -------------     ------------------  ------------------
    *  T = bool        T1 = T'    T2 = T'
    *---------------------------------------------------------
    * gamma -| if (exp) { ifstatement} else {elsestatement} : T'
    *
    * */

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> ret = new ArrayList<EffectError>();

        if(this.exp!=null){
            ret.addAll(this.exp.checkEffect(env));
        }
        EffectEnvironment copy_env = new EffectEnvironment(env);

        if(this.ifstatement != null){
            ret.addAll(this.ifstatement.checkEffect(copy_env));
        }

        if(this.elsestatement != null){
            ret.addAll(this.elsestatement.checkEffect(env));
        }

        env.maxEffect(copy_env); //TODO NON FUNZIONA
        return ret;
    }

    @Override
    public String codeGeneration() {

        StringBuilder codeGenerated = new StringBuilder();
        String then_branch = LabelManager.freshLabelglobal("then");
        String end_label = LabelManager.freshLabelglobal("endIf");

        codeGenerated.append(exp.codeGeneration()).append("\n");
        codeGenerated.append("bc $a0 ").append(then_branch).append("\n");

        /**
         * Code generation else
         */
        if(elsestatement != null){
            String loaded_el = elsestatement.codeGeneration();
            codeGenerated.append(loaded_el);
        }
        codeGenerated.append("b ").append(end_label).append("\n");
        codeGenerated.append(then_branch).append(":\n");
        String loaded_th = ifstatement.codeGeneration();
        codeGenerated.append(loaded_th).append("\n");
        codeGenerated.append(end_label).append(":\n");


        return codeGenerated.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        env.setIf(false);

        ArrayList<SemanticError> ret = new ArrayList<SemanticError>();
        if(this.exp!=null){
            ret.addAll(this.exp.checkSemantics(env));
        }
        if(this.ifstatement != null && this.ifstatement instanceof StatementNode s){
            if (s.isBlock()) {
                env.setIf(true);
            }
            ret.addAll(this.ifstatement.checkSemantics(env));
        }
        env.setIf(false);
        if(this.elsestatement != null && this.ifstatement instanceof StatementNode s){
            if (s.isBlock()) {
                env.setIf(true);
            }
            ret.addAll(this.elsestatement.checkSemantics(env));
        }

        env.setIf(false);

        return ret;
    }
}
