package condition;

import model.Player;

/**
 * Condition that checks if the player has met the scientist (metScientist flag).
 * Used to gate choices like DormNight that require prior story progression.
 */
public class FlagCondition implements Condition {
    private String flagName;
    private boolean requiredValue;

    public FlagCondition(String flagName, boolean requiredValue) {
        this.flagName = flagName;
        this.requiredValue = requiredValue;
    }

    @Override
    public boolean isMet(Player player) {
        if (flagName.equals("metScientist")) {
            return player.hasMetScientist() == requiredValue;
        }
        if (flagName.equals("metScavenger")) {
            return player.hasMetScavenger() == requiredValue;
        }
        if (flagName.equals("purgeActive")) {
            return player.isPurgeActive() == requiredValue;
        }
        // Default: condition not recognized
        return false;
    }

    @Override
    public String getRequirementText() {
        String value = requiredValue ? "must have" : "must not have";
        return value + " '" + flagName + "'";
    }
}
