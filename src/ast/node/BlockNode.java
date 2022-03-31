package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BlockNode implements Node{

    // I nostri 2 non terminali iniziali
    private ArrayList<Node> declarations;
    private ArrayList<Node> statements;

    //Constructors

    BlockNode(ArrayList<Node> declarations,ArrayList<Node> statements){
        this.declarations = declarations;
        this.statements = statements;
    }

    @Override
    public String toPrint(String indent) {
        String ret = "";
        if(this.declarations != null){
            for(Node decl: declarations){
                ret += decl.toPrint(indent);
            }
        }
        if(statements != null){
            for(Node stat: statements){
                ret += stat.toPrint(indent);
            }
        }
        return "\n"+indent+"block"+ret;
    }
    //Stampo i rispettivi sottoalberi dx e sx

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
