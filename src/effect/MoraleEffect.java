package effect;

import model.Player;

/**
 * Effect that changes player morale.
 */
public class MoraleEffect implements Effect {
    private int amount;

    public MoraleEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Player player) {
        player.changeMorale(amount);
    }

    @Override
    public String getDescription() {
        return (amount >= 0 ? "+" : "") + amount + " Morale";
    }
}
