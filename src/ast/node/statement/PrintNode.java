package ast.node.statement;

import ast.Label;
import ast.node.ExpNodes.ExpNode;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class PrintNode implements Node {

    private ExpNode exp;

    public PrintNode(Node exp){
        this.exp = (ExpNode) exp;
    }

    @Override
    public String toString() {
        String ret = "print(";
        if (exp != null) {
            ret += exp.toString();
        }
        return ret + ")";
    }

    @Override
    public TypeNode typeCheck() {
         exp.typeCheck();
        return new TypeNode("void");
    }

    @Override
    public String codeGeneration(Label labelManager) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }
}
