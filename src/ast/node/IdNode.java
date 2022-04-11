package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class IdNode implements Node{

    private String id;

    public IdNode(String id){

        this.id = id;
    }

    @Override
    public String toString(){
        return "ID: "+id;
    }

    public String getId(){
        return id;
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
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}
