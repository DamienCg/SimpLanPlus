package ast.node.statements;

public class TypeNode implements Node{
    @Override
    public String toString() {
        String ret = "";
        ret += "Type: (" + TypeNode.toString() +")" ;
        return ret;
    }
}

