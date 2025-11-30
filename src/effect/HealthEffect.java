package effect;

import model.Player;

//Effect that changes the player's health
public class HealthEffect extends Effect {

    public HealthEffect(int amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.changeHealth(amount);
    }
}
