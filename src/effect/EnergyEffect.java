package effect;
import model.Player;

//Effect that changes player's energy

public class EnergyEffect extends Effect {
    public EnergyEffect(int amount) {
        super(amount); 
    }
    
    //Override the method from parent class
    @Override
    public void apply(Player player) {
        player.changeEnergy(amount); 
    }
}