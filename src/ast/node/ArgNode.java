package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ArgNode implements Node{
    // arg         : ('var')? type ID;
    private TypeNode type;
    private IdNode id;

    ArgNode(Node type, Node id){
        this.type = (TypeNode) type;
        this.id = (IdNode) id;
    }

    @Override
    public String toString() {
        String ret = "(";

        if (type != null) {
            ret += type.toString() + " ";
        }
        if (id != null) {
            ret += id.toString();
        }
        return ret + ")";
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
