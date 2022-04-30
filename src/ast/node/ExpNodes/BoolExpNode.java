package ast.node.ExpNodes;

import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BoolExpNode implements Node{

    private boolean BoolExpNode;

    public BoolExpNode(boolean boolExpNode) {
        BoolExpNode = boolExpNode;
    }

    @Override
    public String toString() {
        String ret = "";
        if (BoolExpNode == true)
            ret += "Bool: true";
         else
            ret += "Bool: false";
        return ret;
    }

    @Override
    public TypeNode typeCheck() {
        return new TypeNode("bool");
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // Anche questo va bene così perchè tanto non ci sono variabili da controllare
        // e non ci sono errori da ritornare
        // copilot mi scrive pure i commenti, vabhè, bon comunque il resto lo lascio a te, testa perfavore tutto appena finisci
        // BUona pasqua :P
        return new ArrayList<SemanticError>();
    }
}