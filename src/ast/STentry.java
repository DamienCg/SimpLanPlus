package ast;
import ast.node.*;
public class STentry {
 
  private final int nestingLevel;
  private Node type;
  private final int offset;
  private boolean isUsed = false;


  public STentry (int n, Node t, int os) {
    this.nestingLevel=n;
    this.type=t;
    this.offset=os;
  }

  public void setUsed() {
    this.isUsed = true;
  }

    public boolean isUsed() {
        return isUsed;
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