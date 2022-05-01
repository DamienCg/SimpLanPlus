package ast.node.ExpNodes;

import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class NegExpNode extends BaseExpNode{

    public NegExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        return  "-" + this.toString() ;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        return exp.checkSemantics(env);
    }

    @Override
    public TypeNode typeCheck() {
        if (exp.typeCheck().isEqual(new TypeNode("int"))) {
            throw new RuntimeException("Trying to do negative on a non int");
        }
        return new TypeNode("int");
    }

}