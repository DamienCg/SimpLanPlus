package ast.node.declaration;

import CheckEffect.Effect;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.STentry;
import ast.node.*;
import ast.node.statement.IteNode;
import ast.node.statement.ReturnNode;
import util.Environment;
import util.LabelManager;
import util.SemanticError;
import java.util.ArrayList;

public class DecFunNode implements Node {

    private TypeNode type;
    private String id;
    private ArrayList<Node> ArgList;
    private BlockNode block;
    private ArrayList<Node> returnNodes;
    private final String beginFuncLabel;
    private final String endFuncLabel;
    private final Node functionNode;

    public DecFunNode(Node type, String id, Node block, ArrayList<Node> argList,Node functionNode) {
        this.type = (TypeNode) type;
        this.id = id;
        this.ArgList = argList;
        this.block = (BlockNode) block;
        this.returnNodes = new ArrayList<>();
        this.beginFuncLabel = LabelManager.freshLabel();
        this.endFuncLabel = LabelManager.endFreshLabel();
        this.functionNode = functionNode;
    }

    public String get_end_fun_label(){
        return endFuncLabel;
    }

    public TypeNode getType() {
        return type;
    }

    public Node getfunctionNode() {
        return functionNode;
    }

    public BlockNode getBlock() {
        return block;
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
    public String codeGeneration() {
        int declaration_size = this.block.getDeclarations().size();
        int parameter_size = ArgList.size();

        StringBuilder codeGenerated = new StringBuilder();

        codeGenerated.append("//BEGIN FUNCTION ").append(beginFuncLabel).append("\n");
        codeGenerated.append(beginFuncLabel).append(":\n");

        codeGenerated.append("mv $sp $fp\n");
        codeGenerated.append("push $ra\n");

        codeGenerated.append(block.codeGeneration()).append("\n");

        codeGenerated.append(endFuncLabel).append(":\n");
        codeGenerated.append("subi $sp $fp 1\n");
        codeGenerated.append("lw $fp 0($fp)\n");
        codeGenerated.append("lw $ra 0($sp)\n");

        codeGenerated.append("addi $sp $sp ").append(declaration_size + parameter_size + 2).append("//pop declaration ").append(declaration_size).append("\n");
//      codeGenerated.append("addi $sp $sp ").append(parameter_size).append("// pop parameters").append(parameter_size).append("\n");

        codeGenerated.append("pop\n");
        codeGenerated.append("lw $fp 0($sp)\n");
        codeGenerated.append("pop\n");

        codeGenerated.append("jr $ra\n");

        codeGenerated.append("// END OF ").append(id).append("\n");

        return codeGenerated.toString();
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> errors = new ArrayList<>();
        Effect effect = new Effect(true,false);
        env.addDecl(id,effect);
        env.addNewTable();

        if(this.ArgList.size() > 0) {
            for(Node arg:this.ArgList) {
                errors.addAll(arg.checkEffect(env));
            }
        }

        if(this.block!=null){
            errors.addAll(this.block.checkEffect(env));
        }

        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        env.setLastParentFunction(this);
        env.setLastFuncDecl(this);
        STentry newEntry = null;

        STentry entry = env.lookUp(id);

        if (entry != null) {
            errors.add(new SemanticError("The name of Function " + id + " is already taken"));
        }

        else {
            newEntry = new STentry(env.getNestinglevel(),type,1);
            SemanticError error = env.addDecl(id, newEntry);
            if (error != null) {
                errors.add(error);
            }
        }

        ArrayList<Node> parTypes = new ArrayList<Node>();

        int oldOffset = env.getOffset();
        env.addNewTable();
        env.functionOffset();

        //Check declarations arguments function
        if(this.ArgList.size() > 0) {
            for(Node arg:this.ArgList) {
                parTypes.add(arg.typeCheck());
                errors.addAll(arg.checkSemantics(env));
            }
        }

        if(newEntry != null)
            newEntry.addType( new ArrowTypeNode(parTypes, type,beginFuncLabel,endFuncLabel) );

        //Check semantics on block
        if(this.block!=null){
            this.block.setIsFunction(true);
            errors.addAll(this.block.checkSemantics(env));
        }
        env.setOffset(oldOffset);
        return errors;
    }

    public ArrayList<Node> getBlockDeclarations(){
        return block.getDeclarations();
    }

}