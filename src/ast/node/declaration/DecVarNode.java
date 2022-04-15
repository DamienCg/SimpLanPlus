package ast.node.declaration;

import ast.STentry;
import ast.node.ExpNodes.ExpNode;
import ast.node.IdNode;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class DecVarNode implements Node {

    //decVar      : type ID ('=' exp)? ';' ;

    private TypeNode type;
    private IdNode id;
    private Node exp;

    public DecVarNode(Node type, Node id, Node exp) {
        this.type = (TypeNode) type;
        this.id = (IdNode) id;
        this.exp = exp;
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
    public String toString() {
       if (exp != null)
           return type.toString() + " " + id.toString() + " = " + exp.toString() + ";";
       else
           return type.toString() + " " + id.toString() + ";";

    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //decVar      : type ID ('=' exp)? ';' ;
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        // My current symbol table entry
       HashMap<String, STentry> myCurrentSymTable = env.getSymTable().get(env.getNestinglevel());
       // Check if the id is already declared
        STentry ret = env.lookUp(env.getNestinglevel(), id.getId());
        if (ret != null) { // If it is already declared
            errors.add(new SemanticError("The name of Variable " + id.getId() + " is already taken"));
        }
        else { // If it is not declared
            // Add the id to the symbol table
            STentry newEntry = new STentry(env.getNestinglevel(),type,0);
            env.addDecl(env.getNestinglevel(), id.getId(), newEntry);
        }
        return errors;
    }

}
