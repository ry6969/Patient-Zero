package condition;

import model.Player;


//Condition that requires the player to have a minimum amount of energy.
public class EnergyCondition implements Condition {
    private int requiredEnergy;

    public EnergyCondition(int requiredEnergy) {
        this.requiredEnergy = requiredEnergy;
    }

    @Override
    public boolean isMet(Player player) {
        return player.getEnergy() >= requiredEnergy;
    }

}
