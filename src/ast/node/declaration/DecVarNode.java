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

        StringBuilder codeGenerated = new StringBuilder();
        if(exp != null){
            codeGenerated.append(exp.codeGeneration()).append("\n");
            codeGenerated.append("push $a0\n");
        }
        else{
            codeGenerated.append("subi $sp $sp 1 // No value assigned\n");
        }
        return codeGenerated.toString();
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

        STentry newEntry = new STentry(env.getNestinglevel(), type, 0);
        SemanticError error = env.addDecl(id, newEntry);

        if (error != null) {
            errors.add(error);
        }

        if(this.exp!=null){
            errors.addAll(this.exp.checkSemantics(env));
            newEntry.setIsInitialized(true);
        }

        return errors;
    }

}
