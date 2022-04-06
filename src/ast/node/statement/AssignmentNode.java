package ast.node.statement;

import ast.node.ExpNodes.ExpNode;
import ast.node.IdNode;
import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class AssignmentNode implements Node {

    // assignment  : ID '=' exp ;
    private IdNode id;
    private ExpNode exp;

    public AssignmentNode(IdNode id, ExpNode exp){
        this.id = id;
        this.exp = exp;
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
