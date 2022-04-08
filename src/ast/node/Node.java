package ast.node;

import util.*;
import java.util.ArrayList;

public interface Node {

    String toString();

    Node typeCheck();

    String codeGeneration();

    ArrayList<SemanticError> checkSemantics(Environment env);
}





//TODO: Vittoria
// Implementare il metodo CheckSemantics() in tutti i nodi di ast.node
//OK--

//TODO: Tutti
// Implementare il metodo CheckSemantics() in tutti i nodi di ast.node.statement
// AssignimentNode -> Damien
// blockNode -> Damien
// iteNode -> Vittoria
//OK--
// printNode -> Vittoria
//OK--
// returnNode -> Tommy

//TODO: Damien
// Implementare il metodo CheckSemantics() in tutti i nodi di ast.node.declaration


