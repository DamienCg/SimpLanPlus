package ast.node;
import java.util.ArrayList;

import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.declaration.DecFunNode;
import util.Environment;
import util.SemanticError;

public class ArrowTypeNode implements Node {

    private ArrayList<Node> parlist;
    private Node ret;
    private String beginFuncLabel;
    private String endFuncLabel;
    private DecFunNode f;

    public ArrowTypeNode (ArrayList<Node> p, Node r, String beginFuncLabel, String endFuncLabel) {
        parlist=p;
        ret=r;
        this.beginFuncLabel = beginFuncLabel;
        this.endFuncLabel = endFuncLabel;
    }

    public String getBeginFuncLabel(){
        return beginFuncLabel;
    }
    public String getEndFuncLabel(){
        return endFuncLabel;
    }

    public String toString() { //
        String parlstr="";
        for (Node par:parlist)
            parlstr+=par.toString();
        return "ArrowType\n" + parlstr + ret.toString() ;
    }

    public Node getRet () { //
        return ret;
    }

    public ArrayList<Node> getParList () { //
        return parlist;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return new ArrayList<SemanticError>();
    }


    @Override
    public TypeNode typeCheck() {
        return null;
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        return new ArrayList<EffectError>();
    }


    @Override
    public String codeGeneration() {
        return "";
    }

    public void setF(DecFunNode f) {
        this.f = new DecFunNode(f);
    }
    public DecFunNode getF() {
        return f;
    }
}