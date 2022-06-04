package ast.node;

import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.statement.*;
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
        if (statement != null) {
            return statement.typeCheck();
        }
        return null;
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

        if (statement != null)
            return statement.codeGeneration();;


        return " ";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> errors = new ArrayList<>();
        if (statement != null) {
            errors.addAll(statement.checkSemantics(env));
        }
        return errors;
    }

    @Override
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> errors = new ArrayList<>();
        if (statement != null) {
            errors.addAll(statement.checkEffect(env));
        }
        return errors;
    }

    public boolean isBlock() {
        if (statement instanceof BlockNode) {
            return true;
        }
        return false;
    }


}
