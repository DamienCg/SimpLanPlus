package ast;
import ast.node.*;
public class STentry {
 
  private int nestingLevel;
  private Node type;
  private int offset;
  private Boolean isUse;
  private Boolean isInitialized;
  private Boolean isFun;
   
  public STentry (int n, Node t, int os) {
    this.nestingLevel=n;
    this.type=t;
    this.offset=os;
    this.isUse = false;
    this.isInitialized = false;
    this.isFun = false;}

  
  public Node getType ()
  {return type;}


  public void setIsUse (Boolean bool)
  {this.isUse = bool;}

  public Boolean getIsUse ()
  {return this.isUse;}

  public void setIsFun (Boolean bool)
  {this.isFun = bool;}

  public Boolean getIsFun ()
  {return this.isFun;}

  public void setIsInitialized (Boolean bool)
  {this.isInitialized = bool; }

  public Boolean getIsInitialized ()
  {return this.isInitialized;}

}  