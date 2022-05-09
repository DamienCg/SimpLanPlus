package ast.node.declaration;

import ast.STentry;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;

public class DecVarNode implements Node {

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
           return type.toString() + " " + id + " = " + exp.toString() + ";";
       else
           return type.toString() + " " + id + ";";

    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        STentry ret = null;
        // Check if the id is already declared
        if (env.getIsFun() > 0) {
             ret = env.lookUpSameNestingLevel(env.getNestinglevel(),id);
        }
        else{
             ret = env.lookUp(env.getNestinglevel(), id);
        }

        STentry newEntry = new STentry(env.getNestinglevel(), type, 0);
        if (ret != null) { // If the id is already declared
            errors.add(new SemanticError("The name of Variable " + id + " is already taken"));
        }
       else {  // If the id is not already declared
            env.addDecl(env.getNestinglevel(), id, newEntry);
        }

        if(this.exp!=null){
            errors.addAll(this.exp.checkSemantics(env));
            newEntry.setIsInitialized(true);
        }

        return errors;
    }

}
