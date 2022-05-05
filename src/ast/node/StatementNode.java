package ast.node;

import ast.node.statement.*;
import org.antlr.runtime.tree.TreeWizard;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class StatementNode implements Node {

    private Node statement;

    public StatementNode(Node node) {
        this.statement = node;
    }

    public Node getNode() {
        return statement;
    }

    public void setNode(Node node) {
        this.statement = node;
    }


    @Override
    public String toString() {
        if (statement != null) {
            return statement.toString();
        }
        return "";
    }

    @Override
    public TypeNode typeCheck() {
        TypeNode type = null;

        if (statement != null) {
            if (statement instanceof AssignmentNode) {
                type = statement.typeCheck();
            }
            else if (statement instanceof PrintNode) {
                type = statement.typeCheck();
            }
            else if (statement instanceof BlockNode) {
                type = statement.typeCheck();
            }
            else if (statement instanceof IteNode) {
                type =statement.typeCheck();
            }
            else if (statement instanceof CallNode) {
                type = statement.typeCheck();
            }
            else if (statement instanceof ReturnNode) {
                type = statement.typeCheck();
            }

        }
        return type;

    }

    public Node getChild() {

        if (statement != null) {
            if (statement instanceof AssignmentNode) {
                return (AssignmentNode) statement;
            }
            else if (statement instanceof PrintNode) {
                return (PrintNode) statement;
            }
            else if (statement instanceof BlockNode) {
                return (BlockNode) statement;
            }
            else if (statement instanceof IteNode) {
                return (IteNode) statement;
            }
            else if (statement instanceof CallNode) {
                return (CallNode) statement;
            }
            else if (statement instanceof ReturnNode) {
                return (ReturnNode) statement;
            }

        }
         return null;

    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // statement   : assignment ';'
        //	    | print ';'
        //	    | ret ';'
        //	    | ite
        //	    | call ';'
        //	    | block;

        ArrayList<SemanticError> errors = new ArrayList<>();
        // Passo il check semantics allo statement
        if (statement != null) {
            if (statement instanceof AssignmentNode) {
                errors.addAll(((AssignmentNode) statement).checkSemantics(env));
            }
            else if (statement instanceof PrintNode) {
                errors.addAll(((PrintNode) statement).checkSemantics(env));
            }
            else if (statement instanceof BlockNode) {
                errors.addAll(((BlockNode) statement).checkSemantics(env));
            }
            else if (statement instanceof IteNode) {
                errors.addAll(((IteNode) statement).checkSemantics(env));
            }
            else if (statement instanceof CallNode) {
                errors.addAll(((CallNode) statement).checkSemantics(env));
            }
            else if (statement instanceof ReturnNode) {
                errors.addAll(((ReturnNode) statement).checkSemantics(env));
            }

        }
        return errors;
    }
}
