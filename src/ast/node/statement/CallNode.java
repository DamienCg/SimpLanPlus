package ast.node.statement;

import ast.STentry;
import ast.node.ExpNodes.ExpNode;
import ast.node.IdNode;
import ast.node.Node;
import ast.node.TypeNode;
import ast.node.declaration.DecFunNode;
import util.Environment;
import util.SemanticError;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CallNode implements Node {
    // call        : ID '(' (exp(',' exp)*)? ')';
    private IdNode id;
    private ArrayList<Node> expList;
    private STentry entry;
    private int nestinglevel;


    public CallNode(IdNode id, ArrayList<Node> expList) {
        this.id = id;
        this.expList = expList;
        this.entry = null;
    }

    public IdNode getId() {
        return id;
    }


    @Override
    public String toString() {
        String ret = id.toString() + "( ";
        if (expList != null) {
            for (Node exp : expList) {
                ret += exp.toString() + ", ";
            }
        }
        return ret;
    }

    @Override
    public TypeNode typeCheck() {
        DecFunNode decFunNode = (DecFunNode) entry.getType(); //TODO potenziale errore da rivedere
        if (entry != null) {
            if (decFunNode.getArgList().size() != expList.size()) {
                throw new RuntimeException("Error: wrong number of arguments in function: " + id.getId());
            } else {
                for (int i = 0; i < expList.size(); i++) {
                    if (!decFunNode.getArgList().get(i).typeCheck().isEqual(expList.get(i).typeCheck())) {
                        throw new RuntimeException("Error: wrong type of arguments in function: " + id.getId());
                    }
                }
            }
        }

            return decFunNode.getType();
    }


    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // call        : ID '(' (exp(',' exp)*)? ')';
        ArrayList<SemanticError> ret = new ArrayList<>();
        STentry entry = env.lookUp(env.getNestinglevel(),id.getId());
        this.nestinglevel = env.getNestinglevel();
        this.entry = entry;
        // check if id is already declared
        if(entry == null){
            ret.add(new SemanticError("Undeclared Function " + id.getId()));
        }
        if(expList != null){
            for(Node exp : expList){
                ret.addAll(exp.checkSemantics(env));
            }
        }
        return ret;
    }
}

