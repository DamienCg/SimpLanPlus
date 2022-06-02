package CheckEffect;


public class Effect {

       private boolean isUse = false;
       private boolean isinizialized = false;

       public void setUse (boolean bool){
              this.isUse = bool;
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
