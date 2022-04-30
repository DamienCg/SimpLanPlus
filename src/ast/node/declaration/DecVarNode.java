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
    public TypeNode typeCheck() {
        if (exp != null) {
            if (type.typeCheck().getType().equals(exp.typeCheck().getType())) {
                return type;
            }
            else {
                throw new RuntimeException("Type error in declaration of variable " + id.getId());
            }
        }
            return type;
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
        STentry ret = null;
        // Check if the id is already declared
        if (env.getIsFun() > 0) {
             ret = env.lookUpSameNestingLevel(env.getNestinglevel(),id.getId());
        }
        else{
             ret = env.lookUp(env.getNestinglevel(), id.getId());
        }
        // Se sono dentro una funzione e l'id è già dichiarato allora non restituisco errore
        if (ret != null) { // la variabile e' dichiarata e non è dentro una funzione
            errors.add(new SemanticError("The name of Variable " + id.getId() + " is already taken"));
        }
       else { // variabile non dichiarata e dentro una funzione
            STentry newEntry = new STentry(env.getNestinglevel(), type, 0);
            env.addDecl(env.getNestinglevel(), id.getId(), newEntry);
        }

        if(this.exp!=null){
            errors.addAll(this.exp.checkSemantics(env));
        }

        return errors;
    }

}
