package SymbolTable;
import ast.STentry;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbTable {

    private final ArrayList<HashMap<String, STentry>> symTable;
    private int nestingLevel;
    private int offset;

    public SymbTable(int nestingLevel, int offset) {
        this.symTable = new ArrayList<>();
        this.nestingLevel = nestingLevel;
        this.offset = offset;
    }

    //Copy Constructor
    public SymbTable(SymbTable e) {
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

    public SymbTable() {
        this(-1,0);
    }

}


