package ast.node;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.STentry;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;

public class ArgNode implements Node{
    // arg         : ('var')? type ID;
    private final TypeNode type;
    private final String id;


    ArgNode(Node type, String id, boolean isVar){
        ((TypeNode) type).setisVar(isVar);
        this.type = (TypeNode) type;
        this.id =  id;
    }

    public Boolean isVar(){
        return type.getisVar();
    }

    public String getId(){
        return id;
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
        return " ";
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> errors = new ArrayList<EffectError>();
        env.addDecl(id);
        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        STentry entry = new STentry(env.getNestinglevel(),type,env.decOffset());
        SemanticError error = env.addDecl(id, entry);
        if (error != null) {
            errors.add(error);
        }

        return errors;
    }
}
