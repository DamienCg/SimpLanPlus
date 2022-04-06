package ast.node.declaration;

import ast.node.IdNode;
import ast.node.Node;
import ast.node.BlockNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DecFunNode implements Node {

    private Node type;
    private IdNode id;
    private ArrayList<Node> ArgList;
    private BlockNode block;
    // decFun	    : (type | 'void') ID '(' (arg (',' arg)*)? ')' block ;

    public DecFunNode(Node type, IdNode id, BlockNode block, ArrayList<Node> argList) {
        this.type = type;
        this.id = id;
        this.ArgList = argList;
        this.block = block;
    }
    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String toString() {
        String ret = "DecFunNode{"+"\n";
        if (type != null)
            ret += type.toString()+"\n";
        else
            ret += "void\n";

        ret += id.toString()+"\n";
        ret += "(";
        if(ArgList.size() > 0) {
            for (Node arg : ArgList) {
                ret += arg.toString() + ",";
            }
        }
        ret += ")\n";
        return ret += block.toString()+"\n"+"}";
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
