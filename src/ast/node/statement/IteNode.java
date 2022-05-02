package ast.node.statement;

import ast.node.ExpNodes.BoolExpNode;
import ast.node.ExpNodes.ExpNode;
import ast.node.Node;
import ast.node.TypeNode;
import ast.node.utils.SimpLanPlusUtils;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class IteNode implements Node {
    // ite         : 'if' '(' exp ')' statement ('else' statement)?;
    public Node exp;
    public Node ifstatement;
    public Node elsestatement;

    public IteNode(Node exp, Node ifstatement, Node elsestatement) {
        this.exp = exp;
        this.ifstatement = ifstatement;
        this.elsestatement = elsestatement;
    }

    @Override
    public String toString() {
        String ret = "if (" + exp.toString() + ") \n";
        if (elsestatement != null) {
            return ret += ifstatement.toString() + "\n else \n" + elsestatement.toString() + "\n";
        }
        else {
            return ret += ifstatement.toString() + "\n";
        }
    }

    @Override
    public TypeNode typeCheck() {
        //TODO da testare
        if (!(SimpLanPlusUtils.isSubtype(exp.typeCheck(), new TypeNode("true")))) {
            System.err.println("Non boolean condition inside if: " + exp.toString());
        }
        return exp.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> ret = new ArrayList<SemanticError>();
        if(this.exp!=null){
            ret.addAll(this.exp.checkSemantics(env));
        }
        if(this.ifstatement != null){
            ret.addAll(this.ifstatement.checkSemantics(env));
        }
        if(this.elsestatement != null){
            ret.addAll(this.elsestatement.checkSemantics(env));
        }
        return ret;
    }
}
