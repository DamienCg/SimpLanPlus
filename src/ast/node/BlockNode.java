package ast.node;
import ast.node.declaration.DecFunNode;
import util.Environment;
import util.SemanticError;
import java.util.ArrayList;



public class BlockNode implements Node {

    // I nostri 2 non terminali iniziali
    private ArrayList<Node> declarations;
    private ArrayList<Node> statements;
    private int current_nl;

    //Constructors

    public BlockNode(ArrayList<Node> declarations,ArrayList<Node> statements) {
        this.declarations = declarations;
        this.statements = statements;
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

        return st;
    }

    public int getCurrent_nl(){
        return current_nl;
    }


    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
            env.addNewTable();

        current_nl = env.getNestinglevel();


        if(this.declarations != null) {
            for (Node decl : declarations) {
                errors.addAll(decl.checkSemantics(env));
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

    public ArrayList<SemanticError> checkSemanticsFunction(Environment env) {

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        current_nl = env.getNestinglevel();
        env.functionOffset();

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
        if(this.statements != null){
            for(Node stmt: statements){
                errors.addAll(stmt.checkSemantics(env));
            }
        }

        env.exitScope();

        return errors;
    }



}