package CheckEffect;


public class Effect {

       private boolean isUse = false;
       private boolean isDef = false;

       public void setUse (boolean bool){
              this.isUse = bool;
       }

       public void setDef (boolean bool){
              this.isDef = bool;
       }

       public boolean getUse (){
              return this.isUse;
       }

         public boolean getDef (){
                  return this.isDef;
         }
}
