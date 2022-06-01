package ast.node;
import CheckEffect.EffectEnvironment;
import CheckEffect.EffectError;
import util.*;
import java.util.ArrayList;

public interface Node {

    String toString();

    TypeNode typeCheck();

    String codeGeneration();

    ArrayList<SemanticError> checkSemantics(Environment env);

    ArrayList<EffectError> checkEffect(EffectEnvironment env);

}

