package ast.node;

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
        return null;
    }
}
