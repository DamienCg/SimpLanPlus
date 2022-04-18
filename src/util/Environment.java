package util;
import java.util.ArrayList;
import java.util.HashMap;
import ast.*;
import ast.node.Node;

public class Environment {

	private final ArrayList<HashMap<String, STentry>> symTable;
	private int nestingLevel;
	private int offset;
	private int isFun = 0;

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

	public int getIsFun(){
		return this.isFun;
	}

	public void setIsFun(int val){
		this.isFun = val;
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

	public void addDecl(int nestingLevel, final String id, STentry st){
		HashMap<String, STentry> scope = symTable.get(nestingLevel);
		scope.put(id, st);
	}

	//controlla se non ci sono sconflitti in tal caso inserisce in St
	// inserimento di una variabile/funzione

	// Search id in the symbol table and returns it if present
	public STentry lookUp(int nestingLevel, final String id) {
		/*
		for (int i = nestingLevel; i >= 0; i--) {
			HashMap<String, STentry> scope = symTable.get(i);
			STentry stEntry = scope.get(id);
			if (stEntry != null) {
				return stEntry;
			}
		}
		return null;
		*/

		if(isFun == 0) {
			for (int i = nestingLevel; i >= 0; i--) {
				HashMap<String, STentry> scope = symTable.get(i);
				STentry stEntry = scope.get(id);
				if (stEntry != null) {
					return stEntry;
				}
			}
		} else {
			for (int i = nestingLevel; i >= isFun; i--) {
				HashMap<String, STentry> scope = symTable.get(i);
				STentry stEntry = scope.get(id);
				if (stEntry != null) {
					return stEntry;
				}
			}
		}
		return null;

	}

	// Search id in the symbol table and returns it if present
	public STentry lookUpSameNestingLevel(int nestingLevel, final String id) {
			HashMap<String, STentry> scope = symTable.get(nestingLevel);
			STentry stEntry = scope.get(id);
			if (stEntry != null) {
				return stEntry;
			}
		return null;

	}

	//distruzione ultimo ambiente creato!
	public void exitScope(){
		this.symTable.remove(this.nestingLevel);
		this.nestingLevel--;
	}

}



