package ast.node;

import CheckEffect.Effect;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.STentry;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;

public class ArgNode implements Node{
    // arg         : ('var')? type ID;
    private TypeNode type;
    private String id;


    ArgNode(Node type, String id, boolean isVar){
        ((TypeNode) type).setisVar(isVar);
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
        return " ";
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> errors = new ArrayList<EffectError>();
        // Non ci sono effetti sugli argomenti (Responsabilità del programmatore)
        Effect effect = new Effect(true,false);
        env.addDecl(id, effect);

        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        STentry entry = new STentry(env.getNestinglevel(),type,env.decOffset()-2);
        SemanticError error = env.addDecl(id, entry);
        if (error != null) {
            errors.add(error);
        }

        return errors;
    }
}
