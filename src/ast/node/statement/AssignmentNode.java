package ast.node.statement;

import ast.node.ExpNodes.DerExpNode;
import ast.node.ExpNodes.ExpNode;
import ast.node.IdNode;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class AssignmentNode implements Node {

    // assignment  : ID '=' exp ;
    private IdNode id;
    private Node exp;

    public AssignmentNode(IdNode id, Node exp){
        this.id = id;
        this.exp = exp;
    }

    @Override
    public TypeNode typeCheck() {
        return new TypeNode("int");
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
        if(env.lookUp(env.getNestinglevel(),id.getId()) == null){
            res.add(new SemanticError("Undeclared variable " + id.getId()));
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
