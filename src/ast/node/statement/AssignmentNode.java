package ast.node.statement;

import ast.STentry;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class AssignmentNode implements Node {

    // assignment  : ID '=' exp ;
    private String id;
    private Node exp;

    public AssignmentNode(String id, Node exp){
        this.id = id;
        this.exp = exp;
    }

    @Override
    public TypeNode typeCheck() {
          // assignment  : ID '=' exp ;
        // TODO
        return new TypeNode("void");
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // assignment  : ID '=' exp ;
        ArrayList<SemanticError> res = new ArrayList<>();
        // check if id is already declared
        STentry IdEntry = env.lookUp(env.getNestinglevel(),id);
        if(IdEntry == null){
            res.add(new SemanticError("Undeclared variable " + id));
        }
        // check if exp is already declared
        // exp può essere una variabile o una stringa o operazione d+b ecc
        if(exp != null)
            res.addAll(exp.checkSemantics(env));
         return res;
    }

    @Override
    public String toString() {
        String ret = "AssignmentNode{";
        if(id != null)
            ret += id.toString();
        if(exp != null)
            ret += " = " + exp.toString();
        return ret + "}";
    }
}
