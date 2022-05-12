package ast.node;
import ast.Label;
import util.*;
import java.util.ArrayList;

public interface Node {

    String toString();

    TypeNode typeCheck();

    String codeGeneration(Label labelManager);

    ArrayList<SemanticError> checkSemantics(Environment env);

}

