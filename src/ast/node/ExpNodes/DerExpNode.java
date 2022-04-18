package ast.node.ExpNodes;

import ast.STentry;
import ast.node.IdNode;
import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DerExpNode implements Node{

    private IdNode id;

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
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        STentry ret = env.lookUp(env.getNestinglevel(), id.getId());
        if(ret == null) { //variable not presence
            errors.add(new SemanticError("Variable " + this.id.getId() + " not declared"));
        }
        return errors;
    }
    
}