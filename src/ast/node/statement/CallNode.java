package ast.node.statement;

import CheckEffect.Effect;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.STentry;
import ast.node.ArrowTypeNode;
import ast.node.ExpNodes.DerExpNode;
import ast.node.Node;
import ast.node.TypeNode;
import ast.node.declaration.DecFunNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class CallNode implements Node {
    private String id;
    private ArrayList<Node> expList;
    private STentry entry;
    private int nestingLevel;
    private DecFunNode f;


    public CallNode(String id, ArrayList<Node> expList) {
        this.id = id;
        this.expList = expList;
        this.entry = null;
        this.nestingLevel = 0;
        this.f = null;
    }

    public String getId() {
        return id;
    }


    @Override
    public String toString() {
        String ret = id + "( ";
        if (expList != null) {
            for (Node exp : expList) {
                ret += exp.toString() + ", ";
            }
        }
        return ret;
    }

    @Override
    public TypeNode typeCheck() {
        ArrowTypeNode t=null;
        if (entry.getType() instanceof ArrowTypeNode)
            t=(ArrowTypeNode) entry.getType();
        if (t == null) {
            throw new RuntimeException("Invocation of a non-function "+id);
        }
        ArrayList<Node> p = t.getParList();
        if ( !(p.size() == expList.size()) ) {
            throw new RuntimeException("Wrong number of parameters in the invocation of "+id);
        }
        for (int i=0; i<expList.size(); i++) {
            if (((TypeNode) p.get(i)).getisVar() && !(expList.get(i) instanceof DerExpNode)) {
                throw new RuntimeException("The type for " + (i + 1) + "-th parameter in the invocation of " + id + " Must be a var");
            }
            if (!p.get(i).typeCheck().isEqual(expList.get(i).typeCheck())) {
                throw new RuntimeException("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + id);
            }
        }
        return (TypeNode) ((ArrowTypeNode)entry.getType()).getRet();
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> ret = new ArrayList<>();
        Effect effect = env.lookUpEffect(id);
        if(entry != null){
            effect.setUse(true);
        }
        if(expList != null){
            for(Node exp : expList){
                if(exp instanceof DerExpNode d){
                    Effect vareffect = env.lookUpEffect(d.getId());
                    if(entry != null)
                        vareffect.setUse(true);
                }
                else{
                    ret.addAll(exp.checkEffect(env));
                }
            }
        }
        return ret;
    }

    @Override
    public String codeGeneration() {
        StringBuilder codeGenerated = new StringBuilder();

        codeGenerated.append("push $fp\n");

        ArrayList<Node> blockDeclarations = f.getBlockDeclarations();

        for (int i = blockDeclarations.size()-1; i >= 0; i--){
            codeGenerated.append(blockDeclarations.get(i).codeGeneration());
            codeGenerated.append("push $a0\n");
        }
        for (int i = expList.size()-1; i>=0; i--){
            codeGenerated.append(expList.get(i).codeGeneration()).append("\n");
            codeGenerated.append("push $a0\n");
        }
        codeGenerated.append("push 0\n"); //FONDAMENTALE!

        codeGenerated.append("mv $fp $al //put in $al actual fp\n");


        codeGenerated.append("lw $al 0($al) //go up to chain\n".repeat(Math.max(0, nestingLevel - entry.getNestingLevel())));

        codeGenerated.append("push $al\n");
        codeGenerated.append("jal  ").append(getLabel()).append("// jump to start of function and put in $ra next instruction\n");

        return codeGenerated.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> ret = new ArrayList<>();
        STentry entry = env.lookUp(id);
        this.entry = entry;
        // check if id is already declared
        if(entry == null){
            ret.add(new SemanticError("Undeclared Function " + id));
        }
        else{
            nestingLevel = env.getNestinglevel();
        }

        if(expList != null){
            for(Node exp : expList){
                ret.addAll(exp.checkSemantics(env));
            }
        }
        else{
            expList = new ArrayList<>();
        }
        this.f = env.getLastFuncDecl();

        return ret;
    }
    String getLabel(){
        ArrowTypeNode d = (ArrowTypeNode) entry.getType();
        return d.getBeginFuncLabel();
    }
}

