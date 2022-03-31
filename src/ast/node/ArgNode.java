package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ArgNode implements Node{
    // arg         : ('var')? type ID;
    private Node type;
    private Node id;

    ArgNode(Node type, Node id){
        this.type = type;
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        return "\n"+indent+"Arg"+this.type.toPrint(indent)+this.id.toPrint(indent);

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
}
