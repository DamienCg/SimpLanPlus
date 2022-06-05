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
        this.EffectTable.remove(this.nestingLevel);
        this.nestingLevel--;
    }



    public static EffectEnvironment maxEffect(EffectEnvironment effect1, EffectEnvironment effect2) {
        for (HashMap<String, Effect> map : effect1.EffectTable) {
            for (String key : map.keySet()) {
                Effect ef1 = map.get(key);
                Effect ef2 = effect2.lookUpEffect(key);
                if (ef2 != null) {
                    Effect ef = Effect.maxEffect(ef1, ef2);
                    effect1.updateEffect(key, ef);
                }
            }
        }
        return effect1;
    }


}
