package ast.node.ExpNodes;

import ast.STentry;
import ast.node.IdNode;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DerExpNode implements Node{
    private IdNode id;
    private STentry entry;
    private int nestingLevel;

    public DerExpNode(IdNode id) {
        this.id = id;
    }

    public IdNode getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id.toString() ;
    }

    @Override
    public TypeNode typeCheck() {
        System.out.println("DerExpNode");
        if(entry != null) {
        return this.entry.getType().typeCheck();
        }
        return id.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> res = new ArrayList<>();

        entry = env.lookUp(env.getNestinglevel(),id.getId());
        if (entry == null)
            res.add(new SemanticError("Id "+id.getId()+" not declared."));
        else
            nestingLevel = env.getNestinglevel();

        return res;
    }
    
}