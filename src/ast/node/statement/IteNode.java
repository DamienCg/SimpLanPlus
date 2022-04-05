package ast.node.statement;

import ast.node.Node;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class IteNode implements Node {
    // ite         : 'if' '(' exp ')' statement ('else' statement)?;
    public Node exp;
    public Node ifstatement;
    public Node elsestatement;

    public IteNode(Node exp, Node ifstatement, Node elsestatement) {
        this.exp = exp;
        this.ifstatement = ifstatement;
        this.elsestatement = elsestatement;
    }

    @Override
    public String toString() {
        String ret = "if (" + exp.toString() + ") {\n";
        if (elsestatement != null) {
            return ret += ifstatement.toString() + "\n} else {\n" + elsestatement.toString() + "\n}";
        }
        else {
            return ret += ifstatement.toString() + "\n}";
        }
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
