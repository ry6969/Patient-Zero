package effect;

import model.Player;

/**
 * Effect that changes player energy.
 */
public class EnergyEffect implements Effect {
    private int amount;

    public EnergyEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Player player) {
        player.changeEnergy(amount);
    }

    @Override
    public String getDescription() {
        return (amount >= 0 ? "+" : "") + amount + " Energy";
    }
}
