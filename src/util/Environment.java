package util;
import java.util.ArrayList;
import java.util.HashMap;
import ast.*;
import ast.node.TypeNode;
import ast.node.declaration.DecFunNode;


public class Environment {

	private final ArrayList<HashMap<String, STentry>> symTable;
	private int nestingLevel;
	private int offset;

	private DecFunNode lastFuncDecl = null;
	private Boolean isIf = false;

	public Environment(int nestingLevel, int offset) {
		this.symTable = new ArrayList<>();
		this.nestingLevel = nestingLevel;
		this.offset = offset;
	}



	public DecFunNode getLastFuncDecl(){
		return this.lastFuncDecl;
	}

	public void setLastFuncDecl(DecFunNode funcDecl){
		this.lastFuncDecl = funcDecl;
	}

	public int getNestinglevel(){
		return this.nestingLevel;
	}

	public void setIf(Boolean isIf){
		this.isIf = isIf;
	}

	public Boolean getIf(){
		return this.isIf;
	}

	public Environment() {
		this(-1,0);
	}


	public void addNewTable(){ // createVoidScope
		HashMap<String, STentry> hm = new HashMap<>();
		this.nestingLevel++;
		this.offset = 0;
		this.symTable.add(hm);
	}

	public void functionOffset(){
		this.offset = 2;
	}
	public void blockOffset(){
		this.offset = 1;
	}

	public int plusOffset(){
		int tmp = this.offset;
		this.offset += 1;
		return tmp;
	}

	public int getOffset(){
		return this.offset;
	}
	public void setOffset(int offset){
		this.offset = offset;
	}


	public SemanticError addDecl(final String id, STentry st){
		if(symTable.get(this.nestingLevel).containsKey(id)){
			return new SemanticError("Variable " + id + " already declared");
		}
		symTable.get(this.nestingLevel).put(id, st);
		//System.out.println("ID: " + id + " in " + st.toString());
		return null;
	}


	public STentry lookUp(final String id) {

			for (int i = this.nestingLevel; i >= 0; i--) {
				HashMap<String, STentry> scope = symTable.get(i);
				STentry stEntry = scope.get(id);
				if (stEntry != null) {
					return stEntry;
				}
			}
		return null;

	}

	//distruzione ultimo ambiente creato!
	public void exitScope(){
		printUnUsedVariable();
		this.symTable.remove(this.nestingLevel);
		this.nestingLevel--;
	}


	public void printUnUsedVariable(){
		HashMap<String, STentry> scope = symTable.get(this.nestingLevel);
		for(String key : scope.keySet()){
			if(scope.get(key).isUsed() == false){
				if((scope.get(key).getType() instanceof TypeNode)) {
					System.out.println("\033[0;33m" + "Warning! Variable " + key + " is not used" + "\033[0m");
				}
				else{
					System.out.println("\033[0;33m" + "Warning! Function " + key + " is not used" + "\033[0m");
				}
			}
		}
	}



}



