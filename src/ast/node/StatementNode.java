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
    public Node typeCheck() {
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
