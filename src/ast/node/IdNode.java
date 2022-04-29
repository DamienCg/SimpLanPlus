package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.Locale;

public class IdNode implements Node{

    private String id;

    public IdNode(String id){

        this.id = id.toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString(){
        return "ID: "+id;
    }

    public String getId(){
        return id;
    }

    @Override
    public void typeCheck() {

    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // questo va bene così.
        return new ArrayList<SemanticError>();
    }
}
