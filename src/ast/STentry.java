package ast;
import ast.node.*;
public class STentry {
 
  private int nestingLevel;
  private Node type;
  private int offset;
  private Boolean isUse = false;
  private Boolean isInitialized = false;
  private Boolean isFun = false;
  
  public STentry (int n, int os)
  {nestingLevel=n;
  offset=os;} 
   
  public STentry (int n, Node t, int os)
  {nestingLevel=n;
   type=t;
   offset=os;}

  public STentry(STentry entry) {
    this.nestingLevel = entry.nestingLevel;
    this.offset = entry.offset;
    this.type = entry.type;
  }

  public void addType (Node t)
  {type=t;}
  
  public Node getType ()
  {return type;}

  public int getOffset ()
  {return offset;}
  
  public int getNestinglevel ()
  {return nestingLevel;}

  public void setIsUse (Boolean bool)
  {this.isUse = bool;}

  public Boolean getIsUse ()
  {return this.isUse;}

  public void setIsFun (Boolean bool)
  {this.isFun = bool;}

  public Boolean getIsFun ()
  {return this.isFun;}

  public void setIsInitialized (Boolean bool)
  {this.isInitialized = bool;}

  public Boolean getIsInitialized ()
  {return this.isInitialized;}

}  