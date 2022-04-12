package ast.node;

import ast.node.declaration.DecFunNode;
import ast.node.declaration.DecVarNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DeclarationNode implements Node {
    // declaration : decFun
    //            | decVar ;
    private Node dec;

    public DeclarationNode(Node dec) {
        this.dec = dec;
    }

    @Override
    public String toString() {
        return dec.toString();
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

        ArrayList<SemanticError> errors = new ArrayList<>();
        // declaration : decFun
        //            | decVar ;
        if (this.dec != null) {
            if (this.dec instanceof DecFunNode) { // declaration : decFun
                errors.addAll(((DecFunNode) this.dec).checkSemantics(env)); // decfun.checkSemantics(env)
            } else if (this.dec instanceof DecVarNode) { // declaration : decVar
                errors.addAll(((DecVarNode) this.dec).checkSemantics(env));
            }
        }
        return errors;
    }
}
