package ast.node;

import ast.STentry;
import ast.node.declaration.DecFunNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.Locale;

import static java.lang.System.err;

public class IdNode implements Node{

    private String id;
    private STentry entry;
    private int nestingLevel;


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
    public TypeNode typeCheck() {
        if(entry == null){
            throw new RuntimeException("TypeCheck: Entry of identifier "+ id +" is null");
        }
        if (entry.getType() instanceof DecFunNode) { //
            throw new RuntimeException("TWrong usage of function identifier: "+ id);
        }
        return entry.getType().typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //create result list
        ArrayList<SemanticError> res = new ArrayList<>();
        entry = env.lookUp(env.getNestinglevel(), id);
        if (entry == null)
            res.add(new SemanticError("Id "+id+" not declared."+id));
        else
            nestingLevel = env.getNestinglevel();
        return res;
    }
}
