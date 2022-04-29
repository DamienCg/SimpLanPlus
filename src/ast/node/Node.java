package ast.node;

import util.*;
import java.util.ArrayList;

public interface Node {

    String toString();

    void typeCheck();

    String codeGeneration();

    ArrayList<SemanticError> checkSemantics(Environment env);
}

