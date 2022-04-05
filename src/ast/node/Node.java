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
// BaseExpNode  --> ok print, ko checkSemantics
// NegExpNode   --> ok print, ko checkSemantics
// NotExpNode   --> ok print, ko checkSemantics
// DerExpNode   --> ok print, ko checkSemantics
// BinExpNode   --> ok print, ko checkSemantics
// CallExpNode  --> ok print, ko checkSemantics
// BoolExpNode  --> ok print, ko checkSemantics
// ValExpNode   --> ok print, ko checkSemantics
// implementare il metodo toString() --> ok
// implementare il metodo CheckSemantics() --> ko


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



