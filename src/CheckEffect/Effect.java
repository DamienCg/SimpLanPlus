package CheckEffect;


public class Effect {

    private String id;
    private boolean isinizialized = false;
    private Effect idRef = null;
    int nestingLevel; // NON LO USO ????

    public Effect(String id, boolean b) {
        this.id = id;
        this.isinizialized = b;
    }


    public static Effect maxEffect(Effect ef1, Effect ef2) {
        Boolean e = ef1.isinizialized || ef2.isinizialized;
        return new Effect(ef1.id,e, ef1.nestingLevel);
    }

    public void setInitialized() {
        if (this.idRef != null) {
            this.idRef.setInitialized();
            return;
        }
        this.isinizialized = true;
    }

    public boolean getEffect() {
        if (this.idRef != null) {
            return this.idRef.getEffect();
        }
        return this.isinizialized;
    }

    public Effect(String id,boolean isDef, int nestingLevel) {
        this.isinizialized = isDef;
        this.id = id;
        this.nestingLevel = nestingLevel;
    }



    public void addRef(Effect ref) {
        this.idRef = ref;
        this.isinizialized = ref.isinizialized;
    }

    public void max(Effect e){
        this.isinizialized = this.isinizialized || e.isinizialized;
        if (this.isinizialized) {
            this.idRef.isinizialized = this.idRef.isinizialized || e.idRef.isinizialized ;
        }
    }

    public String toString() {
        if (this.idRef != null) {
            return "(" + this.id + " "+ this.isinizialized +"," + " "+ this.idRef.isinizialized +")";
        } else {
            return "(" + this.id + " "+ this.isinizialized +")";
        }

    }
}
