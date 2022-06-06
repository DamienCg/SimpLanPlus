package ast.node;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.declaration.DecFunNode;
import ast.node.declaration.DecVarNode;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;
import java.util.Collection;


public class BlockNode implements Node {

    // I nostri 2 non terminali iniziali
    private final ArrayList<Node> declarations;
    private final ArrayList<Node> statements;
    private boolean isFunction;
    private final boolean isMain;

    //Constructors

    public BlockNode(ArrayList<Node> declarations, ArrayList<Node> statements, boolean isMain) {
        this.declarations = declarations;
        this.statements = statements;
        this.isFunction = false;
        this.isMain = isMain;
    }

    public ArrayList<Node> getStatement(){
        return statements;
    }


    public void setIsFunction(boolean isFunction){
            this.isFunction = isFunction;
        }


    @Override
    public String toString() {
        String ret = "{\n";
        if(this.declarations != null) {
            for (Node decl : declarations) {
                ret += decl.toString() + "\n";
            }
        }
        if(this.statements != null){
            for(Node stmt: statements){
                ret += stmt.toString() + "\n";
            }
        }
        return ret += "}";
    }
    //Stampo i rispettivi sottoalberi dx e sx

    @Override
    public TypeNode typeCheck() {
            if (this.declarations != null) {
                for (Node decl : declarations) {
                    decl.typeCheck();
                }
            }
            if (this.statements != null) {
                for (Node stmt : statements) {
                     stmt.typeCheck();
                }

            }

        return new TypeNode("void");
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> errors = new ArrayList<EffectError>();

        if(!isFunction)
            env.addNewTable();

        for (Node decl : declarations)
            errors.addAll(decl.checkEffect(env));

        for(Node stmt: statements) {
            errors.addAll(stmt.checkEffect(env));
        }

        env.exitScope();

        return errors;
    }


    @Override
    public String codeGeneration() {
        StringBuilder codeGenerated = new StringBuilder();

        ArrayList<Node> varDec = new ArrayList<>();
        Collection<Node> funDec = new ArrayList<>();

        for(Node decl: declarations){
            if(((DeclarationNode)decl).getDec() instanceof DecVarNode){
                varDec.add(decl);
            }
        }

        for(Node decl: declarations){
            if(((DeclarationNode)decl).getDec() instanceof DecFunNode){
                funDec.add(decl);
            }
        }

        if (!isFunction){
            // BLOCK
            for (int i = varDec.size()-1; i >= 0; i--) {
                codeGenerated.append(varDec.get(i).codeGeneration()).append("\n");
            }
            codeGenerated.append("push 0\n"); // block
            if (!isMain) {
                // if
                codeGenerated.append("push $fp //loading new block\n");
            }
            codeGenerated.append("mv $sp $fp //Load new $fp\n");
        }
        // If FUNCTION vars are reserved in call

        for(Node decl: declarations){
            if(((DeclarationNode)decl).getDec() instanceof DecVarNode d){
                codeGenerated.append(d.codeGenAsg()).append("\n");
            }
        }

        for (Node stat:statements)
            codeGenerated.append(stat.codeGeneration()).append("\n");

        if(!isFunction){
            if(isMain){
                codeGenerated.append("halt\n");
            }
            else{
                codeGenerated.append("subi $sp $fp 1 //Restore stack pointer as before block creation in blockNode\n");
                codeGenerated.append("lw $fp 0($fp) //Load old $fp pushed \n");
            }
        }

        if (funDec.size() > 0)
            codeGenerated.append("//Creating function:\n");
        for (Node fun:funDec){
            codeGenerated.append(fun.codeGeneration()).append("\n");
        }
        if (funDec.size() > 0)
            codeGenerated.append("//Ending function.\n");
        return  codeGenerated.toString();

    }

    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if(!isFunction) {
            env.addNewTable();
            env.blockOffset(); // setto l'offset del blocco a 1

            if (!isMain) {
                env.decOffset(); // setto a 2
            }

            if(this.declarations != null) {
                for (Node decl : declarations) {
                    errors.addAll(decl.checkSemantics(env));
                }
            }
        }
        else{
            if(this.declarations != null) {
                for (Node decl : declarations) {
                    DeclarationNode dec = (DeclarationNode) decl;
                    if(dec.getDec() instanceof DecFunNode){
                        errors.add(new SemanticError("Function declaration in block function"));
                    }
                    else{
                        errors.addAll(decl.checkSemantics(env));
                    }
                }
            }
        }

        if(this.statements != null){
            for(Node stmt: statements){
                errors.addAll(stmt.checkSemantics(env));
            }
        }
        env.exitScope();
        return errors;
    }

    public ArrayList<Node> getDeclarations() {
        return this.declarations;
    }

}