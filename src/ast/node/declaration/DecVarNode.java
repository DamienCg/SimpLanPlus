package ast.node.declaration;

import ast.STentry;
import ast.node.ExpNodes.ExpNode;
import ast.node.IdNode;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class DecVarNode implements Node {

    //decVar      : type ID ('=' exp)? ';' ;

    private TypeNode type;
    private IdNode id;
    private Node exp;

    public DecVarNode(Node type, Node id, Node exp) {
        this.type = (TypeNode) type;
        this.id = (IdNode) id;
        this.exp = exp;
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
    public String toString() {
       if (exp != null)
           return type.toString() + " " + id.toString() + " = " + exp.toString() + ";";
       else
           return type.toString() + " " + id.toString() + ";";

    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //decVar      : type ID ('=' exp)? ';' ;
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        //   public STentry (int n, Node t, int os)
        //  {nestingLevel=n;
        //   type=t;
        //   offset=os;}

        STentry entry = new STentry(env.getNestinglevel(),type,0);
        HashMap<String, STentry> map = new HashMap<String, STentry>();
        // lookUP se è gia definita la variabile
        map.put(id.getId(), entry);
        // se già definita la variabile
        // errors.add();

    }
}
