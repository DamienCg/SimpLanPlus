package ast.node;

import ast.STentry;
import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockNode implements Node {

    // I nostri 2 non terminali iniziali
    private ArrayList<Node> declarations;
    private ArrayList<Node> statements;

    //Constructors

    public BlockNode(ArrayList<Node> declarations,ArrayList<Node> statements){
        this.declarations = declarations;
        this.statements = statements;
    }

    public ArrayList<Node> getStatement(){
        return statements;
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

        try {
            if (this.declarations != null) {
                for (Node decl : declarations) {
                    decl.typeCheck();
                }
            }
            if (this.statements != null) {
                for (Node stmt : statements) {
                     st = stmt.typeCheck();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return st;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        HashMap<String, STentry> st = new HashMap<String, STentry>();
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        env.addNewTable(st);

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

        env.printUnuseVariable(env.getNestinglevel());
        env.exitScope();

        return errors;
    }

    public ArrayList<SemanticError> checkSemanticsFunction(Environment env) {
        //HashMap<String, STentry> st = new HashMap<String, STentry>();
        //env.addNewTable(st);

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        if(this.declarations != null  && this.declarations.size()>0) {
            for (Node decl : declarations) {
                errors.addAll(decl.checkSemantics(env));
            }
        }
        if(this.statements != null  && this.statements.size()>0){
            for(Node stmt: statements){
                errors.addAll(stmt.checkSemantics(env));
            }
        }
        //env.exitScope();

        return errors;
    }

}