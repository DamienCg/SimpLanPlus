package ast.node;

import util.*;
import java.util.ArrayList;

public interface Node {

    String toPrint(String indent);

    //fa il type checking e ritorna:
    //  per una espressione, il suo tipo (oggetto BoolTypeNode o IntTypeNode)
    //  per una dichiarazione, "null"
    Node typeCheck();

    ArrayList<SemanticError> checkSemantics(Environment env);

}