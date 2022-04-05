package ast.node;

import util.*;
import java.util.ArrayList;

public interface Node {

    String toString();

    Node typeCheck();

    String codeGeneration();

    ArrayList<SemanticError> checkSemantics(Environment env);
}

//TODO:
// Dentro package ast.node fare:
// TypeNode

//TODO:
// Dentro package ast.node.statement fare:
// CallNode


//TODO:
// Dentro package ast.node.declaration fare:
// DecFunNode
// DecVarNode

//TODO:
// Dentro il package ast.node.ExpNodes
// Fare i seguenti nodi classe:
// NegExpNode
// NotExpNode
// DerExpNode
// BinExpNode
// CallExpNode
// BoolExpNode
// ValExpNode

//TODO:
// Implementare metodo ToString() in tutti i nodi di ast.node

//TODO:
// Implementare metodo ToString() in tutti i nodi di ast.node.statement

//TODO:
// Implementare metodo ToString() in tutti i nodi di ast.node.declaration

//TODO:
// Implementare metodo ToString() in tutti i nodi di ast.node.ExpNodes

//TODO:
// Implementare il metodo CheckSemantics() in tutti i nodi di ast.node

//TODO:
// Implementare il metodo CheckSemantics() in tutti i nodi di ast.node.statement

//TODO:
// Implementare il metodo CheckSemantics() in tutti i nodi di ast.node.declaration

//TODO:
// Implementare il metodo CheckSemantics() in tutti i nodi di ast.node.ExpNodes


