package ast.node.statement;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class IteNode implements Node {
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
        TypeNode thenType = ifstatement.typeCheck();

        if(thenType == null)
            throw new RuntimeException("if statement is empty");


        if (!(exp.typeCheck().isEqual(new TypeNode("bool")))) {
            throw new RuntimeException("Non boolean condition inside if: " + exp.toString());
        }
        if(ifstatement != null){
            if(elsestatement != null){
                TypeNode elseType = elsestatement.typeCheck();
                if(elseType == null )
                    throw new RuntimeException("else statement is empty");

                if(!thenType.isEqual(elseType)){
                    throw new RuntimeException("Different types inside if: " + thenType.getType() + " and " + elseType.getType());
                }
            }
        }
        return thenType;
    }

    /*
    * gamma -| exp: T    gamma -| ifstatement: T1    gamma -| elsestatement: T2
    * -------------     ------------------  ------------------
    *  T = bool        T1 = T'    T2 = T'
    *---------------------------------------------------------
    * gamma -| if (exp) { ifstatement} else {elsestatement} : T'
    *
    * */

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
