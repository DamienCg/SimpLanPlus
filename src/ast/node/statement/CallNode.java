package ast.node.statements;

public class CallNode implements Node{
    @Override
    public String toString() {
        String ret = "";
        ret += "Call: (" + CallNode.toString() +")" ;
        return ret;
    }
}
