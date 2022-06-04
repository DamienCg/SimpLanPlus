package ast.node.ExpNodes;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import ast.node.Node;
import ast.node.TypeNode;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class BinExpNode extends ExpNode {

    private String op;
    private Node left;
    private Node right;


    public BinExpNode(Node left, Node right, String op) {
        super();
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
    public ArrayList<EffectError> checkEffect(EffectEnvironment env) {
        ArrayList<EffectError> errors = new ArrayList<>();

        errors.addAll(left.checkEffect(env));
        errors.addAll(right.checkEffect(env));

        return errors;
    }

    @Override
    public String codeGeneration() {

        StringBuilder codeGenerated = new StringBuilder();

        String left_generated = left.codeGeneration();
        codeGenerated.append(left_generated);

        codeGenerated.append("push $a0 // push e1\n");
        String right_generated = right.codeGeneration();
        codeGenerated.append(right_generated);

        codeGenerated.append("lw $a2 0($sp) //take e2 and $a2 take e1\n");
        codeGenerated.append("pop // remove e1 from the stack to preserve stack\n");


        switch (op) {
            case "+":{
                codeGenerated.append("add $a0 $a2 $a0 // a0 = t1+a0\n");

                break;
            }
            case "-": {
                codeGenerated.append("sub $a0 $a2 $a0 // a0 = t1-a0\n");
                break;
            }
            case "*": {
                codeGenerated.append("mult $a0 $a2 $a0 // a0 = t1*a0\n");
                break;
            }
            case "/": {
                codeGenerated.append("div $a0 $a2 $a0 // a0 = t1/a0\n");
                break;
            }
            case "<=":{
                codeGenerated.append("le $a0 $a2 $a0 // $a0 = $a2 <= $a0\n");
                break;
            }
            case "<":{
                codeGenerated.append("lt $a0 $a2 $a0 // $a0 = $a2 < $a0\n");
                break;
            }
            case ">":{
                codeGenerated.append("gt $a0 $a2 $a0 // $a0 = $a2 > $a0\n");
                break;
            }
            case ">=":{
                codeGenerated.append("ge $a0 $a2 $a0 // $a0 = $a2 >= $a0\n");
                break;
            }
            case "==":{
                codeGenerated.append("eq $a0 $a2 $a0 // $a0 = $a2 == $a0\n");
                break;
            }
            case "!=":{
                codeGenerated.append("eq $a0 $a2 $a0 // $a0 = $a2 == $a0\n");
                codeGenerated.append("not $a0 $a0 // $a0 = !$a0\n");
                break;
            }
            case "&&":{
                codeGenerated.append("and $a0 $a2 $a0 // $a0 = $a2 && $a0\n");
                break;
            }

            case "||":{
                codeGenerated.append("or $a0 $a2 $a0 // $a0 = $a2 || $a0\n");
                break;
            }
            case "!":{
                codeGenerated.append("not $a0 $a0 // $a0 = !$a0\n");
                break;
            }

        }
        return codeGenerated.toString();
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
