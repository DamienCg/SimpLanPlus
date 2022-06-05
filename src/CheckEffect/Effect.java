package CheckEffect;


public class Effect {

       private boolean isinizialized = false;
       private String idRef = "";


    public static Effect maxEffect(Effect ef1, Effect ef2) {
        boolean isInitialized = ef1.isinizialized || ef2.isinizialized;
        return new Effect(isInitialized);
    }

    public String getIdRef (){
        return this.idRef;
    }


       public void setIdRef (String idRef){
              this.idRef = idRef;
       }

       public void setIsInizialized (boolean bool){
              this.isinizialized = bool;
       }

       public boolean getIsInizialized (){return this.isinizialized;}

         public Effect(boolean isDef){
              this.isinizialized = isDef;
         }

         public Effect(Effect ef){
              this.isinizialized = ef.getIsInizialized();
         }



}
