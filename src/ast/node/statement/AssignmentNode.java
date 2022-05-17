package ast.node.statement;

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

    public AssignmentNode(String id, Node exp){
        this.id = id;
        this.exp = exp;
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
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        // check if id is already declared
        STentry IdEntry = env.lookUp(id);
        this.entry = IdEntry;
        if(IdEntry == null){
            res.add(new SemanticError("Undeclared variable " + id));
        }

        if(exp != null) {
            res.addAll(exp.checkSemantics(env));
            if(entry != null) {
                this.entry.setIsInitialized(true);
            }
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

