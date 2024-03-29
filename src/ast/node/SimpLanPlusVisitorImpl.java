package ast.node;
import ast.SimpLanPlusBaseVisitor;
import ast.node.ExpNodes.*;
import ast.node.declaration.DecFunNode;
import ast.node.declaration.DecVarNode;
import ast.node.statement.*;
import parser.SimpLanPlusParser;

import java.util.ArrayList;

public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node> {


    @Override
    public Node visitInit(SimpLanPlusParser.InitContext ctx) {
        SimpLanPlusParser.BlockContext ctxBlock = ctx.block();
        return  visitMainBlock(ctxBlock ,true);
    }

    @Override
    public Node visitBlock(SimpLanPlusParser.BlockContext ctx) {
        BlockNode ret;
        ArrayList<Node> declarations = new ArrayList<Node>();
        ArrayList<Node> statements = new ArrayList<>();


        if (ctx.declaration() != null) {
            for (SimpLanPlusParser.DeclarationContext dc : ctx.declaration()) {
                declarations.add(visit(dc));
            }
        }
        if (ctx.statement() != null) {
            for (SimpLanPlusParser.StatementContext sc : ctx.statement()) {
                statements.add(visit(sc));
            }
        }

        ret = new BlockNode(declarations, statements,false);
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
        DecFunNode decFunNode; // is TypeNode OR VOID:

        ArrayList<Node> arguments = new ArrayList<Node>();
        if (ctx.arg() != null) {
            for (SimpLanPlusParser.ArgContext ac : ctx.arg()) {
                arguments.add(visit(ac));
            }
        }
        if (ctx.type() != null) { // is TypeNode
            //  DecFunNode(Node type, Node id, Node block, ArrayList<Node> argList)
            decFunNode = new DecFunNode(visit(ctx.type()), ctx.ID().getText(), visit(ctx.block()), arguments);
        } else { // is VOID
            decFunNode = new DecFunNode(new TypeNode("void"), ctx.ID().getText(), visit(ctx.block()), arguments);
        }

        return decFunNode;
    }

    @Override
    public Node visitDecVar(SimpLanPlusParser.DecVarContext ctx) {
        // decVar      : type ID ('=' exp)? ';' ;
            DecVarNode ret;
            if(ctx.exp()!=null){
                ret = new DecVarNode((TypeNode) visit(ctx.type()), ctx.ID().getText(), visit(ctx.exp()));
            }
            else{
                ret = new DecVarNode((TypeNode) visit(ctx.type()), ctx.ID().getText(), null);
            }

            return ret;
        }

    @Override
    public Node visitType(SimpLanPlusParser.TypeContext ctx){
        //type        : 'int'| 'bool';
        return new TypeNode(ctx.getText());
    }

    @Override
    public Node visitPrint(SimpLanPlusParser.PrintContext ctx) {
        //print       : 'print' exp ';' ;
        return new PrintNode(visit(ctx.exp()));
    }

    @Override public Node visitArg(SimpLanPlusParser.ArgContext ctx){
        if(ctx.children.size() == 3) // is a var
            return new ArgNode((TypeNode) visit(ctx.type()), ctx.ID().getText(),true);
        else
        return new ArgNode((TypeNode)visit(ctx.type()), ctx.ID().getText(),false);
    }

    @Override public Node visitAssignment(SimpLanPlusParser.AssignmentContext ctx){
        return new AssignmentNode(ctx.ID().getText(), visit(ctx.exp()));
    }

    @Override public Node visitRet(SimpLanPlusParser.RetContext ctx){
        if(ctx.exp()!=null){
            return new ReturnNode(visit(ctx.exp()));
        }
        return new ReturnNode(null);
    }

    @Override public Node visitBaseExp(SimpLanPlusParser.BaseExpContext ctx){
        return new BaseExpNode(visit(ctx.exp()));
    }

    @Override
    public Node visitIte(SimpLanPlusParser.IteContext ctx) {
        //ite         : 'if' exp 'then' statement 'else' statement;
        IteNode ret;
        // How Much statement is it?
        //  If statement is only one, then it is only one statement
        //  If statement is more than one, then it is block
        if (ctx.statement().size() == 1) { // If statement is only one, then it is only one statement
            // public IteNode(Node exp, Node ifstatement, Node elsestatement = NULL) {
            ret = new IteNode(visit(ctx.exp()), visit(ctx.statement(0)),null);
        } else { // If statement is more than one, then it is block
            // public IteNode(Node exp, Node ifstatement, Node elsestatement = NOTNULL) {
            ret = new IteNode(visit(ctx.exp()), visit(ctx.statement(0)), visit(ctx.statement(1)));
        }
        return ret;
    }

    @Override public Node visitCall(SimpLanPlusParser.CallContext ctx){
        //     public CallNode(IdNode id, ArrayList<ExpNode> expList) {
        if(ctx.exp().isEmpty()){
            return new CallNode(ctx.ID().getText(), null);
        }
        ArrayList<Node> params = new ArrayList<Node>();
        for(SimpLanPlusParser.ExpContext ex: ctx.exp()){
            params.add(visit(ex));
        }
        return new CallNode(ctx.ID().getText(), params);
    }

    @Override public Node visitNegExp(SimpLanPlusParser.NegExpContext ctx){
        return new NegExpNode(visit(ctx.exp()));
    }

    @Override public Node visitNotExp(SimpLanPlusParser.NotExpContext ctx){
        return new NotExpNode(visit(ctx.exp()));
    }

    @Override public Node visitDerExp(SimpLanPlusParser.DerExpContext ctx){
        return new DerExpNode(ctx.ID().getText());
    }

    @Override public Node visitBinExp(SimpLanPlusParser.BinExpContext ctx){
        return new BinExpNode(visit(ctx.left), visit(ctx.right),ctx.op.getText());
    }

    @Override public Node visitCallExp(SimpLanPlusParser.CallExpContext ctx){
        return new CallExpNode(visit(ctx.call()));
    }

    @Override public Node visitBoolExp(SimpLanPlusParser.BoolExpContext ctx){
        return new BoolExpNode(Boolean.parseBoolean(ctx.getText()));
    }

    @Override public Node visitValExp(SimpLanPlusParser.ValExpContext ctx){
        return new ValExpNode(Integer.parseInt(ctx.getText()));
    }

    public Node visitMainBlock(SimpLanPlusParser.BlockContext ctx, Boolean isMainBlock) {
        BlockNode res;

        ArrayList<Node> declarations = new ArrayList<>();
        ArrayList<Node> statements = new ArrayList<>();

        if (ctx.declaration()!=null) {
            for (SimpLanPlusParser.DeclarationContext dc : ctx.declaration()){
                declarations.add( visit(dc) );
            }
        }

        if (ctx.statement()!= null) {
            for (SimpLanPlusParser.StatementContext st : ctx.statement()){
                statements.add( visit(st) );
            }
        }

        res = new BlockNode(declarations,  statements, isMainBlock);


        return res;
    }


}

