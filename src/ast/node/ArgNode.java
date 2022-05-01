package ast.node;

import ast.STentry;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class ArgNode implements Node{
    // arg         : ('var')? type ID;
    private TypeNode type;
    private IdNode id;

    ArgNode(Node type, Node id){
        this.type = (TypeNode) type;
        this.id = (IdNode) id;
    }

    @Override
    public String toString() {
        String ret = "(";

        if (type != null) {
            ret += type.toString() + " ";
        }
        if (id != null) {
            ret += id.toString();
        }
        return ret + ")";
    }

    @Override
    public TypeNode typeCheck() {
        if(type != null)
            if(type.typeCheck().isEqual(id.typeCheck())){
                return type.typeCheck();
            }
            else{
                throw new RuntimeException("Type mismatch"+type.typeCheck().toString()+" "+id.typeCheck().toString());
            }
        else{
            return id.typeCheck();
        }
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        // Check if the id is already declared
        STentry ret = env.lookUpSameNestingLevel(env.getNestinglevel(), id.getId());
        if (ret != null) { // If it is already declared
            errors.add(new SemanticError("Argument id " + id.getId() + " already declared"));
        }
        else { // If it is not declared
            // Add the id to the symbol table
            STentry newEntry = new STentry(env.getNestinglevel(),type,0);
            env.addDecl(env.getNestinglevel(), id.getId(), newEntry);
        }
        return errors;
    }
}
