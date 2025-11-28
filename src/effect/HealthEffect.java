package effect;

import model.Player;

/**
 * Effect that changes player health.
 */
public class HealthEffect implements Effect {
    private int amount;

    public HealthEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Player player) {
        player.changeHealth(amount);
    }

    @Override
    public String getDescription() {
        return (amount >= 0 ? "+" : "") + amount + " Health";
    }
}
