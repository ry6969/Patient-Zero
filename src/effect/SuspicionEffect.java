package effect;

import model.Player;

/**
 * Effect that changes player suspicion.
 */
public class SuspicionEffect implements Effect {
    private int amount;

    public SuspicionEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Player player) {
        player.changeSuspicion(amount);
    }

    @Override
    public String getDescription() {
        return (amount >= 0 ? "+" : "") + amount + " Suspicion";
    }
}
