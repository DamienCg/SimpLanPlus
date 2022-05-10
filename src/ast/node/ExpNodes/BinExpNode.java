package ast.node.ExpNodes;

import ast.STentry;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BinExpNode implements Node {

    private String op;
    private Node left;
    private Node right;


    public BinExpNode(Node left, Node right, String op) {
        this.left =  left;
        this.right =  right;
        this.op = op;
    }

    @Override
    public String toString() {
        String ret = "";
        ret +=left.toString() + " " + op + " " + right.toString();
        return ret;
    }

    @Override
    public TypeNode typeCheck() {
        TypeNode leftType = left.typeCheck();
        TypeNode rightType = right.typeCheck();

        if(!leftType.isEqual(rightType)){
            throw new RuntimeException("Operands are of different type");
        }

        TypeNode intType = new TypeNode("int");
        TypeNode boolType = new TypeNode("bool");

        switch (op) {
            case "+":
            case "-":
            case "*":
            case "/":
                // Both must be integer type
                if (!leftType.isEqual(intType)) {
                    throw new RuntimeException("Operands are not int in math operator ([ + | - | * | / ])");
                }
                return new TypeNode("int");

            case "<":
            case "<=":
            case ">":
            case ">=":
                if (!leftType.isEqual(intType)) {
                    throw new RuntimeException("Operands are not int in ([ >= | > | < | <= ])");
                }
                return new TypeNode("bool");

            case "==":
            case "!=":
                return new TypeNode("bool");

            case "&&":
            case "||":
                if(!leftType.isEqual(boolType)){
                    throw new RuntimeException("Operands are not bool in [ or(||) | and[&&] ");
                }
                return new TypeNode("bool");
        }
        throw new RuntimeException("Operator not found");
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> ret = new ArrayList<SemanticError>();

        if(this.left!=null) {
            ret.addAll(left.checkSemantics(env));
        }
        if(this.right!=null) {
            ret.addAll(right.checkSemantics(env));
        }
        return ret;

    }

}
