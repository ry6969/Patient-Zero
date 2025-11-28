package effect;

import model.Player;

/**
 * Effect that changes player knowledge.
 */
public class KnowledgeEffect implements Effect {
    private int amount;

    public KnowledgeEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Player player) {
        player.changeKnowledge(amount);
    }

    @Override
    public String getDescription() {
        return (amount >= 0 ? "+" : "") + amount + " Knowledge";
    }
}
