package ast.node;

import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.declaration.DecFunNode;
import ast.node.declaration.DecVarNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DeclarationNode implements Node {
    // declaration : decFun
    //            | decVar ;
    private final Node dec;

    public DeclarationNode(Node dec) {
        this.dec = dec;
    }

    public Node getDec() {
        return dec;
    }

    @Override
    public String toString() {
        return dec.toString();
    }

    @Override
    public TypeNode typeCheck() {
        TypeNode type = null;
        if (this.dec != null) {
            if (this.dec instanceof DecFunNode) { // declaration : decFun
                type = this.dec.typeCheck(); // decfun.typeCheck()
            } else if (this.dec instanceof DecVarNode) { // declaration : decVar
                type = this.dec.typeCheck(); // decVar.typeCheck()
            }
        }
        return type;
    }

    @Override
    public String codeGeneration() {

        return dec.codeGeneration();
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

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        return dec.checkEffect(env);
    }

}
