package ast.node;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.declaration.DecFunNode;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;



public class BlockNode implements Node {

    // I nostri 2 non terminali iniziali
    private ArrayList<Node> declarations;
    private ArrayList<Node> statements;
    private int current_nl;
    private boolean isFunction;

    //Constructors

    public BlockNode(ArrayList<Node> declarations,ArrayList<Node> statements) {
        this.declarations = declarations;
        this.statements = statements;
        this.current_nl = 0;
        this.isFunction = false;
    }

    public ArrayList<Node> getStatement(){
        return statements;
    }

    public boolean isEmpty(){
        if(statements.size() == 0 && declarations.size() == 0)
            return true;
            else
                return false;
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
        TypeNode st = null;

            if (this.declarations != null) {
                for (Node decl : declarations) {
                    st = decl.typeCheck();
                }
            }
            if (this.statements != null) {
                for (Node stmt : statements) {
                     st = stmt.typeCheck();
                }

            }

        return new TypeNode("void");
    }

    public int getCurrent_nl(){
        return current_nl;
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> errors = new ArrayList<EffectError>();

        if(!isFunction)
            env.addNewTable();

        for (Node decl : declarations)
            errors.addAll(decl.checkEffect(env));


        for(Node stmt: statements)
            errors.addAll(stmt.checkEffect(env));


        env.exitScope();
        return errors;
    }


    @Override
    public String codeGeneration() {

       return null;

    }

    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if(!isFunction) {
            env.addNewTable();
            current_nl = env.getNestinglevel();

            env.blockOffset(); // setto l'offset del blocco a -1

            if(this.declarations != null) {
                for (Node decl : declarations) {
                    errors.addAll(decl.checkSemantics(env));
                }
            }
        }
        else{
            current_nl = env.getNestinglevel();
            env.functionOffset(); // setto offset funzione a -2

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


}