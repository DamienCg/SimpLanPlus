package ast.node.statement;
import ast.Label;
import ast.node.BlockNode;
import ast.node.Node;
import ast.node.StatementNode;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

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

        if (!(exp.typeCheck().isEqual(new TypeNode("bool")))) {
            throw new RuntimeException("Non boolean condition inside if: " + exp.toString());
        }
        if(ifstatement != null){
            if(elsestatement != null){
                TypeNode elseType = elsestatement.typeCheck();
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
    public String codeGeneration(Label labelManager) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> ret = new ArrayList<SemanticError>();
        if(this.exp!=null){
            ret.addAll(this.exp.checkSemantics(env));
        }
        if(this.ifstatement != null){
            StatementNode ifblock = (StatementNode) ifstatement;
            BlockNode ifblock2 = (BlockNode) ifblock.getNode();
            if(ifblock2.isEmpty())
                ret.add(new SemanticError("if statement is empty"));
            ret.addAll(this.ifstatement.checkSemantics(env));
        }
        if(this.elsestatement != null){
            StatementNode elseblock = (StatementNode) elsestatement;
            BlockNode elseblock2 = (BlockNode) elseblock.getNode();
            if(elseblock2.isEmpty())
                ret.add(new SemanticError("else statement is empty"));
            ret.addAll(this.elsestatement.checkSemantics(env));
        }
        return ret;
    }
}
