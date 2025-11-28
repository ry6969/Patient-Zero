package effect;

import model.Player;

/**
 * Effect that advances the player's day counter.
 */
public class DayEffect implements Effect {
    private int amount;

    public DayEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Player player) {
        player.advanceDay(amount);
    }

    @Override
    public String getDescription() {
        return (amount >= 0 ? "+" : "") + amount + " Day(s)";
    }
}
