package effect;

import model.Player;

//Effect that changes the player's morale
public class MoraleEffect extends Effect {

    public MoraleEffect(int amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.changeMorale(amount);
    }

}
