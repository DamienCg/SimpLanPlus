package ast.node.declaration;

import ast.STentry;
import ast.node.IdNode;
import ast.node.Node;
import ast.node.BlockNode;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

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

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // decFun	    : (type | 'void') ID '(' (arg (',' arg)*)? ')' block ;

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        // My current symbol table entry
        HashMap<String, STentry> myCurrentSymTable = env.getSymTable().get(env.getNestinglevel());
        // Check if the id is already declared
        STentry ret = env.lookUp(env.getNestinglevel(), id.getId());
        if (ret != null) { // If it is already declared
            errors.add(new SemanticError("The name of Function " + id.getId() + " is already taken"));
        }
        else { // If it is not declared
            // Add the id to the symbol table
            STentry newEntry = new STentry(env.getNestinglevel(),type,0);
            env.addDecl(env.getNestinglevel(), id.getId(), newEntry);
        }

        //Increment nesting level and create new table
        HashMap<String, STentry> myCurrentSymTableArg = new HashMap<String, STentry>();
        env.addNewTable(myCurrentSymTableArg);
        env.setIsFun(env.getNestinglevel());
        //Check declarations arguments function
        if(this.ArgList.size() > 0) {
            for(Node arg:this.ArgList) {
                errors.addAll(arg.checkSemantics(env));
            }
        }

        //Check semantinc on block
        if(this.block!=null){
            errors.addAll(this.block.checkSemanticsFunction(env));
        }

        //Esco dalla funzione
        env.setIsFun(0);

        //Delete last ambient
        env.exitScope();

        return errors;
    }

}