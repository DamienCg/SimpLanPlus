package util;
import java.util.ArrayList;
import java.util.HashMap;
import ast.*;


public class Environment {

	private final ArrayList<HashMap<String, STentry>> symTable;
	private int nestingLevel;
	private int offset;

	public Environment(int nestingLevel, int offset) {
		this.symTable = new ArrayList<>();
		this.nestingLevel = nestingLevel;
		this.offset = offset;
	}

	public int getNestinglevel(){
		return this.nestingLevel;
	}

	public int getOffset(){
		return this.offset;
	}

	public Environment() {
		this(-1,0);
	}


	public void addNewTable(){
		HashMap<String, STentry> hm = new HashMap<>();
		this.nestingLevel++;
		this.symTable.add(hm);
	}

	public SemanticError addDecl(final String id, STentry st){
		if(symTable.get(this.nestingLevel).containsKey(id)){
			return new SemanticError("Variable " + id + " already declared");
		}
		symTable.get(this.nestingLevel).put(id, st);
		return null;
	}

	//controlla se non ci sono sconflitti in tal caso inserisce in St
	// inserimento di una variabile/funzione

	// Search id in the symbol table and returns it if present
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
		this.symTable.remove(this.nestingLevel);
		this.nestingLevel--;
	}

	public void printUnuseVariable(int nestingLevel){
		HashMap<String, STentry> scope = symTable.get(nestingLevel);
			scope.entrySet().forEach(entry -> {
				if(entry != null) {
					if (entry.getValue().getIsUse() == false) {
						if (entry.getValue().getIsFun() == true) {
							System.out.println("Warning: Function " + entry.getKey() + " not use");
						}
						if (entry.getValue().getIsFun() == false) {
							System.out.println("Warning: Variable " + entry.getKey() + " not use");
						}
					}
					if (entry.getValue().getIsInitialized() == false) {
						if (entry.getValue().getIsFun() == false) {
							System.out.println("Warning: Variable " + entry.getKey() + " not initialized");
						}
					}
				}});


	}
}



