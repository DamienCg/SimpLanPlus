package CheckEffect;
import ast.STentry;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class EffectEnvironment {

    private int nestingLevel;
    private final ArrayList<HashMap<String, Effect>> EffectTable;


    public EffectEnvironment(int nestingLevel) {
        this.nestingLevel = nestingLevel;
        this.EffectTable = new ArrayList<>();;
    }

    public EffectEnvironment() {
        this(-1);
    }


    public void addNewTable(){ // createVoidScope
        HashMap<String, Effect> hm = new HashMap<>();
        this.nestingLevel++;
        this.EffectTable.add(hm);
    }

    public void addDecl(final String id, Effect st){
        EffectTable.get(this.nestingLevel).put(id, st);
    }


    public Effect lookUpEffect(final String id) {
        for (int i = this.nestingLevel; i >= 0; i--) {
            HashMap<String, Effect> scope = EffectTable.get(i);
            Effect stEntry = scope.get(id);
            if (stEntry != null) {
                return stEntry;
            }
        }
        return null;
    }

    public void addEffect(final String id, Effect ef){
        EffectTable.get(this.nestingLevel).put(id, ef);
    }

    public void updateEffect(final String id, Effect ef){
        EffectTable.get(this.nestingLevel).put(id, ef);
    }

    public void exitScope(){
        this.printUnuseVariable(this.nestingLevel);
        this.EffectTable.remove(this.nestingLevel);
        this.nestingLevel--;
    }

    public void printUnuseVariable(int nestingLevel){
        HashMap<String, Effect> scope = EffectTable.get(nestingLevel);
        scope.entrySet().forEach(entry -> {
            if(entry != null)
                if (entry.getValue().getUse() == false)
                    System.out.println("Warning: Variable/Function " + entry.getKey() + " not use");
        });
    }

}
