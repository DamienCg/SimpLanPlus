package ast.node;

import ast.STentry;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;

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
       return type.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        STentry entry = new STentry(env.getNestinglevel(),type,0);

        SemanticError error = env.addDecl(id, entry);
        if (error != null) {
            errors.add(error);
        }

        return errors;
    }
}
