package ast.node.statements;

public class VoidNode implements Node{
    @Override
    public String toString() {
        String ret = "";
        ret += "Void: (" + VoidNode.toString() +")" ;
        return ret;
    }

}
