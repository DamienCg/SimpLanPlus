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
        return this.entry.getType().typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        STentry ret = env.lookUp(env.getNestinglevel(), id.getId());
        this.entry = ret;
        this.nestingLevel = env.getNestinglevel();
        if(ret == null) { //variable not presence
            errors.add(new SemanticError("Variable " + this.id.getId() + " not declared"));
        }
        return errors;
    }
    
}