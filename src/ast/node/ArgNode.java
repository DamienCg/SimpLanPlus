package ast.node;

import ast.STentry;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class ArgNode implements Node{
    // arg         : ('var')? type ID;
    private TypeNode type;
    private String id;


    ArgNode(Node type, String id){
        this.type = (TypeNode) type;
        this.id =  id;
    }

    @Override
    public String toString() {
        String ret = "(";

        if (type != null) {
            ret += type + " ";
        }
        if (id != null) {
            ret += id;
        }
        return ret + ")";
    }

    @Override
    public TypeNode typeCheck() {
       return type;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        // Check if the id is already declared
        STentry ret = env.lookUpSameNestingLevel(env.getNestinglevel(), id);
        if (ret != null) { // If it is already declared
            errors.add(new SemanticError("Argument id " + id + " already declared"));
        }
        else { // If it is not declared
            // Add the id to the symbol table
            STentry newEntry = new STentry(env.getNestinglevel(),type,0);
            env.addDecl(env.getNestinglevel(), id, newEntry);
        }
        return errors;
    }
}
