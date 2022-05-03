package ast.node.ExpNodes;

import ast.STentry;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DerExpNode implements Node{
    private String id;
    private STentry entry;

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
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> res = new ArrayList<>();

        this.entry = env.lookUp(env.getNestinglevel(),id);
        if (entry == null)
            res.add(new SemanticError("Id "+id+" not declared."));
        if (entry != null) {
            entry.setIsUse(true);
        }

        return res;
    }
    
}