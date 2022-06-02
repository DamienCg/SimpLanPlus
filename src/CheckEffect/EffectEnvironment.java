package CheckEffect;

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

    public EffectEnvironment(EffectEnvironment env) {
        this.nestingLevel = env.nestingLevel;
        // deep copy EffectTable
        this.EffectTable = new ArrayList<>();
        for (HashMap<String, Effect> map : env.EffectTable) {
            HashMap<String, Effect> newMap = new HashMap<>();
            for (String key : map.keySet()) {
                newMap.put(key, new Effect(map.get(key)));
            }
            this.EffectTable.add(newMap);
        }
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

    public void updateEffect(final String id, Effect ef){

        EffectTable.get(this.nestingLevel).put(id, ef);
    }

    public void exitScope(){
        this.printUnusedVariable(this.nestingLevel);
        this.EffectTable.remove(this.nestingLevel);
        this.nestingLevel--;
    }

    public void printUnusedVariable(int nestingLevel){
        HashMap<String, Effect> scope = EffectTable.get(nestingLevel);
        scope.entrySet().forEach(entry -> {
            if(entry != null)
                if (!entry.getValue().getUse())
                    System.out.println("Warning: Variable/Function " + entry.getKey() + " not use");
        });
    }


    public void maxEffect(EffectEnvironment effect2) {
        for (HashMap<String, Effect> map : this.EffectTable) {
            for (String key : map.keySet()) {
                Effect effect = map.get(key);
                Effect effect2Value = effect2.lookUpEffect(key);
                if (effect2Value != null) {
                    if (effect.getIsInizialized() && !effect2Value.getIsInizialized()) {
                        this.updateEffect(key, effect);
                    }
                    if(effect.getUse() && !effect2Value.getUse()){
                        this.updateEffect(key, effect);
                    }
                }
            }
        }
    }

}
