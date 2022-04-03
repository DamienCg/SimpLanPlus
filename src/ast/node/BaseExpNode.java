package ast.node;

public class BaseExpNode extends ExpNode {
    public BaseExpNode(Node line) {
        super(line);
    }

    @Override
    public String toString() {
        return "BaseExpNode";
    }
}
