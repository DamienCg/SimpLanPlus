package ast.node;
import ast.SimpLanPlusBaseVisitor;
import ast.node.ExpNodes.ExpNode;
import ast.node.declaration.DecFunNode;
import ast.node.declaration.DecVarNode;
import ast.node.statement.AssignmentNode;
import parser.SimpLanPlusParser;

import java.util.ArrayList;

public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node> {
    @Override
    public Node visitBlock(SimpLanPlusParser.BlockContext ctx) {
        BlockNode ret;
        ArrayList<Node> declarations = new ArrayList<Node>();
        ArrayList<Node> statements = new ArrayList<Node>();

        for (SimpLanPlusParser.DeclarationContext dc : ctx.declaration()) {
            declarations.add(visit(dc));
        }
        for (SimpLanPlusParser.StatementContext sc : ctx.statement()) {
            statements.add(visit(sc));
        }
        ret = new BlockNode(declarations, statements);
        return ret;
    }

    @Override
    public Node visitDeclaration(SimpLanPlusParser.DeclarationContext ctx) {
        DeclarationNode declarationNode;
        if (ctx.decFun() != null) {
            declarationNode = new DeclarationNode(visit(ctx.decFun()));
        }
        else if (ctx.decVar() != null) {
            declarationNode = new DeclarationNode(visit(ctx.decVar()));
        }
        else
            return null; //Not exit declaration context

        return declarationNode;
    }

    @Override
    public Node visitStatement(SimpLanPlusParser.StatementContext ctx) {
        //statement   : assignment ';'
        //      | print ';'
        //       | ret ';'
        //      | ite
        //        | call ';'
        //        | block;
        //      ALL CASES IS LOGIC OR
        StatementNode statementNode;

        if (ctx.assignment() != null) {
            statementNode = new StatementNode(visit(ctx.assignment()));
        }
        else if (ctx.print() != null) {
            statementNode = new StatementNode(visit(ctx.print()));
        }
        else if (ctx.ret() != null) {
            statementNode = new StatementNode(visit(ctx.ret()));
        }
        else if (ctx.ite() != null) {
            statementNode = new StatementNode(visit(ctx.ite()));
        }
        else if (ctx.call() != null) {
            statementNode = new StatementNode(visit(ctx.call()));
        }
        else if (ctx.block() != null) {
            statementNode = new StatementNode(visit(ctx.block()));
        }
        else
            return null; //Not exit statement context

        return statementNode;
    }

    @Override
    public Node visitDecFun(SimpLanPlusParser.DecFunContext ctx) {
        // decFun	    : (type | 'void') ID '(' (arg (',' arg)*)? ')' block ;
        DecFunNode decFunNode; // is TypeNode OR VOID:
        // FIRST OF ALL SAVE ALL ARGUMENTS
        ArrayList<Node> arguments = new ArrayList<Node>();
        if (ctx.arg() != null) {
            for (SimpLanPlusParser.ArgContext ac : ctx.arg()) {
                arguments.add(visit(ac));
            }
        }
        if (ctx.type() != null) { // is TypeNode
            //  DecFunNode(Node type, Node id, Node block, ArrayList<Node> argList)
            decFunNode = new DecFunNode(visit(ctx.type()), visit(ctx.ID()), visit(ctx.block()), arguments);
        } else { // is VOID
            decFunNode = new DecFunNode(null, visit(ctx.ID()), visit(ctx.block()), arguments);
        }

        return decFunNode;
    }

}

