package condition;

import model.Player;

/**
 * Interface for all game conditions.
 * Conditions are requirements that must be met for a choice to be available.
 * Supports polymorphic checking of different condition types.
 */
public interface Condition {
    /**
     * Checks if this condition is met by the player.
     * @param player the player to check the condition against
     * @return true if the condition is satisfied, false otherwise
     */
    boolean isMet(Player player);

    /**
     * Gets a human-readable description of this condition requirement.
     * @return description like "Requires 5+ Knowledge" or "Must have met Scientist"
     */
    String getRequirementText();
}
