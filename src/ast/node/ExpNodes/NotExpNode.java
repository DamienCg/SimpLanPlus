package ast.node.ExpNodes;

import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class NotExpNode extends BaseExpNode{


    public NotExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        return "!" + this.toString();
    }

    @Override
    public TypeNode typeCheck() {
        return new TypeNode("int");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return exp.checkSemantics(env);
    }
}
