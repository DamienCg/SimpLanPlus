package ast.node.statement;

import ast.node.ExpNodes.ExpNode;
import ast.node.IdNode;
import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class CallNode implements Node {
// call        : ID '(' (exp(',' exp)*)? ')';
    private IdNode id;
    private ArrayList<Node> expList;

    public CallNode(IdNode id, ArrayList<Node> expList) {
        this.id = id;
        this.expList = expList;
    }

    public IdNode getId() {
        return id;
    }


    @Override
    public String toString() {
        String ret = id.toString() + "( ";
        if (expList != null) {
            for (Node exp : expList) {
                ret += exp.toString() + ", ";
            }
        }
        return ret;
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
        // call        : ID '(' (exp(',' exp)*)? ')';
        ArrayList<SemanticError> ret = new ArrayList<>();
        // check if id is already declared
        if(env.lookUp(env.getNestinglevel(),id.getId()) == null){
            ret.add(new SemanticError("Undeclared Function " + id.getId()));
        }
        if(expList != null){
            for(Node exp : expList){
                ret.addAll(exp.checkSemantics(env));
            }
        }
        return ret;
    }
}

