package util;
import java.util.ArrayList;
import java.util.HashMap;
import ast.*;
import ast.node.Node;

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

	public ArrayList<HashMap<String, STentry>> getSymTable(){
		return this.symTable;
	}

	public Environment() {
		this(-1,0);
	}

	public void addNewTable(HashMap<String, STentry> hm){
		this.nestingLevel++;
		this.symTable.add(hm);
	}

	public SemanticError addDecl(int nestingLevel, final String id, STentry st){
		HashMap<String, STentry> scope = symTable.get(nestingLevel);
		if (scope.containsKey(id)) {
			return new SemanticError("Variable " + id + " already declared");
		}
		scope.put(id, st);
		return null;
	}

		//controlla se non ci sono sconflitti in tal caso inserisce in St
	// inserimento di una variabile/funzione

	// Search id in the symbol table and returns it if present
	public STentry lookUp(int nestingLevel, final String id) {
		for (int i = nestingLevel; i >= 0; i--) {
			HashMap<String, STentry> scope = symTable.get(i);
			STentry stEntry = scope.get(id);
			if (stEntry != null) {
				return stEntry;
			}
		}
		return null;

	}


	public void exitScope(){
		this.symTable.remove(this.nestingLevel);
		this.nestingLevel --;
	}
	//distruzione ultimo ambiente creato!

	//ricerca se una variabile è definita nella tabella dei simboli


}



