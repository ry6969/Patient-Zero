package effect;

import model.Player;

public interface Effect {
    void apply(Player player);
    String getDescription();
}
