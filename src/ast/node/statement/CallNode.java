package ast.node.statement;

import ast.STentry;
import ast.node.ArrowTypeNode;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class CallNode implements Node {
    private String id;
    private ArrayList<Node> expList;
    private STentry entry;


    public CallNode(String id, ArrayList<Node> expList) {
        this.id = id;
        this.expList = expList;
        this.entry = null;
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
        for (int i=0; i<expList.size(); i++)
            if (!p.get(i).typeCheck().isEqual(expList.get(i).typeCheck())) {
                throw new RuntimeException("Wrong type for "+(i+1)+"-th parameter in the invocation of "+id);
            }
        return new TypeNode("void");
    }


    @Override
    public String codeGeneration() {
        return null;
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
        if(entry != null){
            entry.setIsUse(true);
        }
        if(expList != null){
            for(Node exp : expList){
                ret.addAll(exp.checkSemantics(env));
            }
        }
        return ret;
    }
}

