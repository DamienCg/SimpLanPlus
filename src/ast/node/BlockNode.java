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
    public Node typeCheck() {
        return null;
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
        // Rimuovo la tabella corrente
        env.exitScope();
        return errors;
    }

    public ArrayList<SemanticError> checkSemanticsFunction(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        //non creo nuovo ambiente perché già creato nella parte di controllo degli argomenti
        if(this.declarations != null  && this.declarations.size()>0) {
            for (Node decl : declarations) {
                errors.addAll(decl.checkSemantics(env));
            }
        }
        //TODO: commentato perché da errore. Da sistemare
        if(this.statements != null  && this.statements.size()>0){
            for(Node stmt: statements){
                errors.addAll(stmt.checkSemantics(env));
            }
        }


        // BISOGNA AGGIUNGERE Questo: env.getSymTable().remove(env.getNestinglevel());

        // perchè una volta che esci dal blocco devi rimuvere ambiente
        // ti dà errore non perchè sia sbagliato ma perchè il resto degli altri nodi non è implementato
        // expNode viene usato da molte parti, finchè non si implementano non puoi sapere se funziona il tutto.

        return errors;
    }

}