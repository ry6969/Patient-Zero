package effect;

import model.Player;

//Effect that changes the players knowlegde
public class KnowledgeEffect extends Effect {
    private int amount;

    public KnowledgeEffect(int amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.changeKnowledge(amount);
    }
}
