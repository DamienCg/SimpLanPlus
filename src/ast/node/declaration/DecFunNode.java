package ast.node.declaration;

import ast.Label;
import ast.STentry;
import ast.node.*;
import ast.node.statement.IteNode;
import ast.node.statement.ReturnNode;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;

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

            block.typeCheck();


        return type;
    }

    @Override
    public String toString() {
        String ret = "\n";
        if (type != null)
            ret += type.getType()+"\n";
        else
            ret += "void ";

        ret += id;
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
    public String codeGeneration(Label labelManager) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        STentry newEntry = null;

        // Check if the id of function is already declared
        STentry ret = env.lookUp(id);
        if (ret != null) { // If it is already declared
            errors.add(new SemanticError("The name of Function " + id + " is already taken"));
        }
        else {
            newEntry = new STentry(env.getNestinglevel(),type,0);
            SemanticError error = env.addDecl(id, newEntry);
            if (error != null) {
                errors.add(error);
            }
        }

        ArrayList<Node> parTypes = new ArrayList<Node>();

        env.addNewTable();
        //Check declarations arguments function
        if(this.ArgList.size() > 0) {
            for(Node arg:this.ArgList) {
                parTypes.add(arg.typeCheck());
                errors.addAll(arg.checkSemantics(env));
            }
        }

        newEntry.addType( new ArrowTypeNode(parTypes, type) );

        //Check semantinc on block
        if(this.block!=null){
            errors.addAll(this.block.checkSemanticsFunction(env));
        }


        return errors;
    }

}