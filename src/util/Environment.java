package util;
import ast.STentry;
import java.util.ArrayList;
import java.util.HashMap;

public class Environment {

	private final ArrayList<HashMap<String, STentry>> symTable;
	private int nestingLevel;
	private int offset;

	public Environment(int nestingLevel, int offset) {
		this.symTable = new ArrayList<>();
		this.nestingLevel = nestingLevel;
		this.offset = offset;
	}

	//Copy Constructor
	public Environment(Environment e) {
		this(e.nestingLevel, e.offset);
		for (var scopeBlock : e.symTable) {
			final HashMap<String, STentry> copiedScope = new HashMap<>();
			for (var id : scopeBlock.keySet()) {
				STentry entry = scopeBlock.get(id);
				copiedScope.put(id, new STentry(entry));
			}
			this.symTable.add(copiedScope);
		}
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

	//TODO
	/*
	Environment newScope(Environment st){

	}
	//Prende una st e restituisce una st con un nuovo ambiente

	//TODO
	Environment addDecl(Environment st, String id, Node type){

	}//controlla se non ci sono sconflitti in tal caso inserisce in St
	// inserimento di una variabile/funzione

	//TODO
	Environment exitScope(Environment st){
	}
	//distruzione ultimo ambiente creato!

	//TODO
	public Node lookUp(Environment st, final String id) {

	}
	//ricerca se una variabile è definita nella tabella dei simboli

	 */

}



