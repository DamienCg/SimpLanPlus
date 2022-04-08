package ast.node;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class TypeNode implements Node {
//type        : 'int'
//            | 'bool';
    private String type;

    public TypeNode(String type) {
        this.type = type;
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
        return new ArrayList<SemanticError>();
    }

    @Override
    public String toString() {
        return "Type: " + this.type;
    }
}

