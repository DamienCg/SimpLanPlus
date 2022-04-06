package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class TypeNode implements Node{

    private String type;

        @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String toString() {
        return this.type;
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
