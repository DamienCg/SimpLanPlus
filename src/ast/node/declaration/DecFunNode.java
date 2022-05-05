package ast.node.declaration;

import ast.STentry;
import ast.node.Node;
import ast.node.BlockNode;
import ast.node.StatementNode;
import ast.node.TypeNode;
import ast.node.statement.IteNode;
import ast.node.statement.ReturnNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class DecFunNode implements Node {

    private TypeNode type;
    private String id;
    private ArrayList<Node> ArgList;
    private BlockNode block;
    private ArrayList<Node> returnNodes;

    public DecFunNode(Node type, String id, Node block, ArrayList<Node> argList) {
        this.type = (TypeNode) type;
        this.id = id;
        this.ArgList = argList;
        this.block = (BlockNode) block;
        this.returnNodes = new ArrayList<>();
    }

    public TypeNode getType() {
        return type;
    }

    public ArrayList<Node> getArgList() {
        return ArgList;
    }

    private void getReturnNodes(Node n){

            if (n instanceof ReturnNode) {
                returnNodes.add(n);
            }

            if (n instanceof IteNode) {
                getReturnNodes(((IteNode) n).ifstatement);
                getReturnNodes(((IteNode) n).elsestatement);
            }
            if(n instanceof StatementNode){
                getReturnNodes(((StatementNode) n).getChild());
            }

            if(n instanceof BlockNode){
                for (Node node:((BlockNode) n).getStatement()) {
                    getReturnNodes(node);
                }
            }

        }


    @Override
    public TypeNode typeCheck() {
        getReturnNodes(block);

        //funzione void
        if(type == null){
            for (Node returns: returnNodes){
                if (!returns.typeCheck().isEqual(new TypeNode("void"))){
                    throw new RuntimeException("Function " + this.id + " must contain void returns" );
                }
            }
        }

            if (type.getType() != "void" && returnNodes.size() == 0) {
                throw new RuntimeException("Function " + this.id + " must contain return statement");
            }

            for (Node returns : returnNodes) {
                if (!returns.typeCheck().isEqual(type)) {
                    throw new RuntimeException("Function " + this.id + " must return type " + type.toString());
                }
            }


        return type;
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
        STentry ret = env.lookUp(env.getNestinglevel(), id);
        if (ret != null) { // If it is already declared
            errors.add(new SemanticError("The name of Function " + id + " is already taken"));
        }
        else { // If it is not declared
            // Add the id to the symbol table
            STentry newEntry = new STentry(env.getNestinglevel(),this,0);
            newEntry.setIsFun(true);
            env.addDecl(env.getNestinglevel(), id, newEntry);

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