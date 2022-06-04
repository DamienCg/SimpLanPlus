package ast;
import ast.node.*;
import ast.node.declaration.DecFunNode;

import java.util.HashMap;

public class STentry {
 
  private int nestingLevel;
  private Node type;
  private int offset;

  //Fun entry per la gestione dei ritorni
  private String beginFuncLabel = "";
  private String endFuncLabel = "";
  private DecFunNode funNode;


  public STentry (int n, Node t, int os) {
    this.nestingLevel=n;
    this.type=t;
    this.offset=os;
  }

  public String getBeginFuncLabel() {
    return beginFuncLabel;
  }

  public String getEndFuncLabel() {
    return endFuncLabel;
  }

  public void setFunctionNode(DecFunNode funNode) {
    this.funNode = funNode;
  }

  public void setBeginLabel(String beginFuncLabel) {
    this.beginFuncLabel = beginFuncLabel;
  }

  public void setEndLabel(String endFuncLabel) {
    this.endFuncLabel = endFuncLabel;
  }

  
  public Node getType ()
  {return type;}

  public void addType (Node t) {
    type=t;
  }

  public int getNestingLevel (){
    return nestingLevel;
  }

  public int getOffset (){
    return offset;
  }

  public String toString () {
    return "STentry: nestingLevel=" + nestingLevel + " type=" + type + " offset=" + offset;
  }

}  