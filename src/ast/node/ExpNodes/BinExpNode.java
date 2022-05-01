package ast.node.ExpNodes;

import ast.STentry;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BinExpNode implements Node {

    private String op;
    private Node left; //[TOSE] ho messo expNode perché nella grammatica sono assegnate ad espressioni
    private Node right;
    private STentry entryLeft;
    private STentry entryRight;
    private int nestingLevel;

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

        switch (op) {
            case "+":
            case "-":
            case "*":
            case "/":
                // Both must be integer type
                if (!leftType.isEqual(new TypeNode("int"))) {
                    throw new RuntimeException("Operands are not int in math operator ([ + | - | * | / ])");
                }
                return new TypeNode("int");

            case "<":
            case "<=":
            case ">":
            case ">=":
                if (!leftType.isEqual(new TypeNode("int"))) {
                    throw new RuntimeException("Operands are not int in ([ >= | > | < | <= ])");
                }
                return new TypeNode("bool");
            /**
             * Bool operator
             * These can be done with bool and Int
             *
             */
            case "==":
            case "!=":
                return new TypeNode("bool");

            case "&&":
            case "||":
                if(!leftType.isEqual(new TypeNode("bool"))){
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
        // Ho fatto questo, dovrebbe andare bene, perchè tanto richiami i figli
        ArrayList<SemanticError> ret = new ArrayList<SemanticError>();

        TypeNode a = null;
        TypeNode b = null;

        if(this.left!=null) {
            ret.addAll(left.checkSemantics(env));
        }
        if(this.right!=null) {
            ret.addAll(right.checkSemantics(env));
        }
        return ret;

    }

}
