package ast.node;
import java.util.ArrayList;
import util.Environment;
import util.SemanticError;

public class ArrowTypeNode implements Node {

    private ArrayList<Node> parlist;
    private Node ret;

    public ArrowTypeNode (ArrayList<Node> p, Node r) {
        parlist=p;
        ret=r;
    }

    public String toString() { //
        String parlstr="";
        for (Node par:parlist)
            parlstr+=par.toString();
        return "ArrowType\n" + parlstr + ret.toString() ;
    }

    public Node getRet () { //
        return ret;
    }

    public ArrayList<Node> getParList () { //
        return parlist;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }

    //non utilizzato
    @Override
    public TypeNode typeCheck() {
        return null;
    }

    //non utilizzato
    @Override
    public String codeGeneration() {
        return "";
    }

}