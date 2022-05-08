package ast.node.declaration;

import ast.STentry;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;

public class DecVarNode implements Node {

    //decVar      : type ID ('=' exp)? ';' ;

    private TypeNode type;
    private String id;
    private Node exp;

    public DecVarNode(Node type, String id, Node exp) {
        this.type = (TypeNode) type;
        this.id =  id;
        this.exp = exp;
    }

    @Override
    public TypeNode typeCheck() {
        if (exp != null) {
            if (type.typeCheck().getType().equals(exp.typeCheck().getType())) {
                return type;

            }
            else {
                throw new RuntimeException("Type error in declaration of variable " + id);
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
        STentry ret = null;
        // Check if the id is already declared
        if (env.getIsFun() > 0) {
             ret = env.lookUpSameNestingLevel(env.getNestinglevel(),id);
        }
        else{
             ret = env.lookUp(env.getNestinglevel(), id);
        }
        // Se sono dentro una funzione e l'id è già dichiarato allora non restituisco errore
        STentry newEntry = new STentry(env.getNestinglevel(), type, 0);
        if (ret != null) { // la variabile e' dichiarata e non è dentro una funzione
            errors.add(new SemanticError("The name of Variable " + id + " is already taken"));
        }
       else { // variabile non dichiarata e dentro una funzione
            env.addDecl(env.getNestinglevel(), id, newEntry);
        }

        if(this.exp!=null){
            errors.addAll(this.exp.checkSemantics(env));
            newEntry.setIsInitialized(true);
        }

        return errors;
    }

}
