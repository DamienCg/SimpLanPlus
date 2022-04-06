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

        for(SimpLanPlusParser.DeclarationContext dc: ctx.declaration()){
            declarations.add(visit(dc));
        }
        for(SimpLanPlusParser.StatementContext sc: ctx.statement()){
            statements.add(visit(sc));
        }
        ret = new BlockNode(declarations, statements);
        return ret;
    }

    @Override
    public Node visitDeclaration(SimpLanPlusParser.DeclarationContext ctx) {
       DeclarationNode ret = null;

        if (ctx.decFun() != null) {
            ret = new DeclarationNode((DecFunNode) visit(ctx.decFun()), null);
        }
        else if (ctx.decVar() != null) {
            ret = new DeclarationNode( null, (DecVarNode) visit(ctx.decVar()));
        }
        return ret;
    }

    @Override
    public Node visitAssignment(SimpLanPlusParser.AssignmentContext ctx) {
        AssignmentNode ret = null;
        // assignment  : ID '=' exp ;
        if (ctx.ID() != null && ctx.exp() != null) {
            IdNode id = new IdNode(ctx.ID().getText());
            ret = new AssignmentNode(id, (ExpNode) visit(ctx.exp()));
        }
        return ret;
    }




}


