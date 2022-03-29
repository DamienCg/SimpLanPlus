package ast;

import java.util.ArrayList;

import ast.node.BoolTypeNode;
import ast.node.IntTypeNode;
import ast.node.Node;
import ast.node.ProgLetInNode;
import parser.SimpLanPlusParser.*;

//TODO
public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node> {

    @Override
    public Node visitBlock(BlockContext ctx) {

        return null;
    }
}
