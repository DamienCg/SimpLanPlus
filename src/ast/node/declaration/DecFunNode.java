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
import java.util.Objects;

public class DecFunNode implements Node {

    private final TypeNode type;
    private final String id;
    private final ArrayList<Node> ArgList;
    private final BlockNode block;
    private final ArrayList<Node> returnNodes;
    private final String beginFuncLabel;
    private final String endFuncLabel;


    public DecFunNode(Node type, String id, Node block, ArrayList<Node> argList) {
        this.type = (TypeNode) type;
        this.id = id;
        this.ArgList = argList;
        this.block = (BlockNode) block;
        this.returnNodes = new ArrayList<>();
        this.beginFuncLabel = LabelManager.freshLabel();
        this.endFuncLabel = LabelManager.endFreshLabel();
    }

    public DecFunNode(DecFunNode f){
        this.type = f.type;
        this.id = f.id;
        this.ArgList = f.ArgList;
        this.block = f.block;
        this.returnNodes = f.returnNodes;
        this.beginFuncLabel = f.beginFuncLabel;
        this.endFuncLabel = f.endFuncLabel;
    }

    public String get_end_fun_label(){
        return endFuncLabel;
    }

    public TypeNode getType() {
        return type;
    }

    public BlockNode getBlock() {
        return block;
    }

    public String getId() {
        return id;
    }

    private void getReturnNodes(Node n){

            if (n instanceof ReturnNode) {
                returnNodes.add(n);
            }

            if (n instanceof IteNode) {
                getReturnNodes(((IteNode) n).thenstatement);
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
                    throw new RuntimeException("Error: Function " + this.id + " must contain void returns" );
                }
            }
        }
        if(type != null) {
            if (!Objects.equals(type.getType(), "void") && returnNodes.size() == 0) {
                throw new RuntimeException("Error: Function " + this.id + " must contain return statement");
            }

            for (Node returns : returnNodes) {
                if (!returns.typeCheck().isEqual(type)) {
                    throw new RuntimeException("Error: Function " + this.id + " must return type " + type.getType());
                }
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
        return ret + (block.toString() + "\n");
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

        codeGenerated.append("pop\n");
        codeGenerated.append("lw $fp 0($sp)\n");
        codeGenerated.append("pop\n");

        codeGenerated.append("jr $ra\n");

        codeGenerated.append("// END OF ").append(id).append("\n");

        return codeGenerated.toString();
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        return new ArrayList<>();
    }

    public ArrayList<EffectError> CheckEffectCall(EffectEnvironment env, ArrayList<Effect> varEffects) {

        ArrayList<EffectError> errors = new ArrayList<>();
        env.addNewTable();


        ArrayList<ArgNode> vars = new ArrayList<>();
        for (Node n:this.ArgList){
            if (n instanceof ArgNode a && a.isVar()){
                vars.add(a);
            }else{
                if (n instanceof ArgNode a){
                    env.addDecl(a.getId());
                    env.lookUp(a.getId()).setInitialized();
                }
            }
        }

        for (int i=0; i<vars.size(); i++){
            env.addDecl(vars.get(i).getId()).addRef(varEffects.get(i));
        }

        if(this.block!=null){
            errors.addAll(this.block.checkEffect(env));
        }

        return errors;
    }


    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();
        env.setLastFuncDecl(this);
        STentry newEntry = null;

        STentry entry = env.lookUp(id);

        if (entry != null) {
            errors.add(new SemanticError("The name of Function " + id + " is already taken"));
        } else {
            newEntry = new STentry(env.getNestinglevel(),type,1);
            SemanticError error = env.addDecl(id, newEntry);
            if (error != null) {
                errors.add(error);
            }
        }

        ArrayList<Node> parTypes = new ArrayList<>();

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

        if(newEntry != null)
        ((ArrowTypeNode) newEntry.getType()).setF(this);

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

    public ArrayList<Node> getArgList() {
        return ArgList;
    }



}