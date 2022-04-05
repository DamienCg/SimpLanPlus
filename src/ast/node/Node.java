package ast.node;

import util.*;
import java.util.ArrayList;

public interface Node {

    String toString();

    Node typeCheck();

    String codeGeneration();

    ArrayList<SemanticError> checkSemantics(Environment env);
}


//TODO: VITTORIA
// Dentro package ast.node fare:
// TypeNode
// VoidNode
// ed implementare il metodo toString()

//TODO: VITTORIA
// Dentro package ast.node.statement fare:
// CallNode
// ed implementare il metodo toString()

//TODO: Tommy
// Dentro il package ast.node.ExpNodes
// Fare i seguenti nodi classe:
// NegExpNode
// NotExpNode
// DerExpNode
// BinExpNode
// CallExpNode
// BoolExpNode
// ValExpNode
// ed implementare il metodo toString()


//TODO: Vittoria
// Implementare il metodo CheckSemantics() in tutti i nodi di ast.node

//TODO: Tutti
// Implementare il metodo CheckSemantics() in tutti i nodi di ast.node.statement
// AssignimentNode -> Damien
// blockNode -> Damien
// iteNode -> Vittoria
// printNode -> Vittoria
// returnNode -> Tommy

//TODO: Damien
// Implementare il metodo CheckSemantics() in tutti i nodi di ast.node.declaration

//TODO: Tommy
// Implementare il metodo CheckSemantics() in tutti i nodi di ast.node.ExpNodes


