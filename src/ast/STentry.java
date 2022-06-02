package ast;
import ast.node.*;
import java.util.HashMap;

public class STentry {
 
  private int nestingLevel;
  private Node type;
  private int offset;


  public STentry (int n, Node t, int os) {
    this.nestingLevel=n;
    this.type=t;
    this.offset=os;
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

}  