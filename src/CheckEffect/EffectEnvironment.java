package CheckEffect;
import ast.node.declaration.DecFunNode;

import java.util.ArrayList;
import java.util.HashMap;

public class EffectEnvironment {

    private int nestingLevel;
    private final ArrayList<HashMap<String, Effect>> EffectTable;
    private DecFunNode lastCall;


    public EffectEnvironment(int nestingLevel) {
        this.nestingLevel = nestingLevel;
        this.EffectTable = new ArrayList<>();;
        this.lastCall = null;
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
                newMap.put(key, (map.get(key)));
            }
            this.EffectTable.add(newMap);
        }
        this.lastCall = env.lastCall;
    }


    public void addNewTable(){ // createVoidScope
        HashMap<String, Effect> hm = new HashMap<>();
        this.nestingLevel++;
        this.EffectTable.add(hm);
    }

    public Effect addDecl(final String id){
        EffectTable.get(this.nestingLevel).put(id, new Effect(id,false));
        return lookUp(id);
    }


    public Effect lookUp(final String id) {
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
        this.EffectTable.remove(this.nestingLevel);
        this.nestingLevel--;
    }



    public static EffectEnvironment maxEffect(EffectEnvironment effect1, EffectEnvironment effect2) {
        for (HashMap<String, Effect> map : effect1.EffectTable) {
            for (String key : map.keySet()) {
                Effect ef1 = map.get(key);
                Effect ef2 = effect2.lookUp(key);
                if (ef2 != null) {
                    Effect ef = Effect.maxEffect(ef1, ef2);
                    effect1.updateEffect(key, ef);
                }
            }
        }
        return effect1;
    }

    public void setLastCall(DecFunNode lastCall){
        this.lastCall = lastCall;
    }

    public boolean isRecursive(DecFunNode s){
        if (this.lastCall == null) return false;
        if (this.lastCall.getId().equals(s.getId())) return true;
        return false;
    }


}
