package ast.node.statements;

public class TypeNode implements Node{
    public ExpNode exp;
    public Node ifstatement;
    public Node elsestatement;
}
    @Override
    public String toString() {
        String ret = "CallNode{";
        if(id != null)
            ret += id.toString();
        if(exp != null)
            ret += " = " + exp.toString();
        return ret + "}";
    }