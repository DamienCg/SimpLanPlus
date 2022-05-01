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
        TypeNode expType = exp.typeCheck();

        if (! (expType.isEqual(new TypeNode("bool")))) {
            throw new RuntimeException("Trying to do negate (!) of a non bool");
        }
        //throw new SimplanPlusException("Exp not bool, throw exception");

        return new TypeNode("bool");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return exp.checkSemantics(env);
    }
}
