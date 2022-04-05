package ast.node.ExpNodes;

import ast.node.Node;

public class BinExpNode extends ExpNode {

    private String op;
    private Node left;
    private Node right;

    public BinExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toString() {
        String ret = "";
        ret += "(" + left.toString() + " " + op + " " + right.toString() + ")";
        return ret;
    }


}
