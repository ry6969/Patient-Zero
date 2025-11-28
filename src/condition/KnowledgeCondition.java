package condition;

import model.Player;

/**
 * Condition that requires the player to have a minimum amount of knowledge.
 */
public class KnowledgeCondition implements Condition {
    private int requiredKnowledge;

    public KnowledgeCondition(int requiredKnowledge) {
        this.requiredKnowledge = requiredKnowledge;
    }

    @Override
    public boolean isMet(Player player) {
        return player.getKnowledge() >= requiredKnowledge;
    }

    @Override
    public String getRequirementText() {
        return "Requires " + requiredKnowledge + "+ Knowledge";
    }
}
