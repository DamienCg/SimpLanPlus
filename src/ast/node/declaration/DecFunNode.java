package ast.node.declaration;

import ast.node.IdNode;
import ast.node.Node;
import ast.node.BlockNode;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class DecFunNode implements Node {

    private TypeNode type;
    private IdNode id;
    private ArrayList<Node> ArgList;
    private BlockNode block;
    // decFun	    : (type | 'void') ID '(' (arg (',' arg)*)? ')' block ;

    public DecFunNode(Node type, Node id, Node block, ArrayList<Node> argList) {
        this.type = (TypeNode) type;
        this.id = (IdNode) id;
        this.ArgList = argList;
        this.block = (BlockNode) block;
    }
    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String toString() {
        String ret = "\n";
        if (type != null)
            ret += type.toString()+"\n";
        else
            ret += "void ";

        ret += id.toString();
        ret += "(";
        int i = 0;
        if(ArgList.size() > 0) {
            for (Node arg : ArgList) {
                i++;
                ret += arg.toString() ;
                if(ArgList.size()-i > 0){
                    ret += ",";
                }
            }
        }
        ret += ")\n";
        return ret += block.toString()+"\n";
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    //TODO: check if the function is already defined
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}
