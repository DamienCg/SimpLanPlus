package ast;
import ast.node.*;
public class STentry {
 
  private int nestingLevel;
  private Node type;
  private int offset;
  
  public STentry (int n, int os)
  {nestingLevel=n;
  offset=os;} 
   
  public STentry (int n, Node t, int os)
  {nestingLevel=n;
   type=t;
   offset=os;}

  public STentry(STentry entry) {}

  public void addType (Node t)
  {type=t;}
  
  public Node getType ()
  {return type;}

  public int getOffset ()
  {return offset;}
  
  public int getNestinglevel ()
  {return nestingLevel;}
  
  public String toPrint(String s) { //
	   return s+"STentry: nestlev " + Integer.toString(nestingLevel) +"\n"+
			  s+"STentry: type\n" + 
			  type.toPrint(s+"  ") + 
		      s+"STentry: offset " + Integer.toString(offset) + "\n";
  }
}  