package ast.node;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class IfNode implements Node {
    // ite         : 'if' '(' exp ')' statement ('else' statement)?;
    public Node exp;
    public Node ifstatement;
    public Node elsestatement;

    public IfNode(Node exp, Node ifstatement, Node elsestatement) {
        this.exp = exp;
        this.ifstatement = ifstatement;
        this.elsestatement = elsestatement;
    }

    @Override
    public String toString() {
        String ret = "if (" + exp.toString() + ") {\n";
        if (elsestatement != null) {
            ret += ifstatement.toString() + "\n} else {\n" + elsestatement.toString() + "\n}";
        }
        else {
            ret += ifstatement.toString() + "\n}";
        }
        return ret;
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
