package effect;
import model.Player;


public abstract class Effect {
    
    //Shared variable that's inherited by all the subclasses
    protected int amount; 

    public Effect(int amount) {
        this.amount = amount;
    }
    
    //Abstract method to be applied by all subclasses
    public abstract void apply(Player player);
}