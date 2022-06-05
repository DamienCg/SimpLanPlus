package CheckEffect;


public class Effect {

       private boolean isUse = false;
       private boolean isinizialized = false;
       private String idRef = "";


    public static Effect maxEffect(Effect ef1, Effect ef2) {
        boolean isUsed = ef1.isUse || ef2.isUse;
        boolean isInitialized = ef1.isinizialized || ef2.isinizialized;
        return new Effect(isInitialized, isUsed);
    }

    public String getIdRef (){
        return this.idRef;
    }

    public void setUse (boolean bool){
              this.isUse = bool;
       }

       public void setIdRef (String idRef){
              this.idRef = idRef;
       }

       public void setIsInizialized (boolean bool){
              this.isinizialized = bool;
       }

       public boolean getUse (){
              return this.isUse;
       }

       public boolean getIsInizialized (){return this.isinizialized;}

         public Effect(boolean isDef,boolean isUse){
              this.isUse = isUse;
              this.isinizialized = isDef;
         }

         public Effect(Effect ef){
              this.isUse = ef.getUse();
              this.isinizialized = ef.getIsInizialized();
         }



}
