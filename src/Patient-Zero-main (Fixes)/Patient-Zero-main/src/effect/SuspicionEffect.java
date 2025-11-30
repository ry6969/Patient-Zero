package effect;

import model.Player;

/**
 * Effect that changes player suspicion.
 */
public class SuspicionEffect extends Effect {

    public SuspicionEffect(int amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.changeSuspicion(amount);
    }

}