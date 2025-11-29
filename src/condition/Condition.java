package condition;

import model.Player;

//Interface to check if a certain condition is met by a player

public interface Condition {
    boolean isMet(Player player);
}
