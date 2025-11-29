package condition;
import model.Player;


//Condition that requires the player's suspicion to be below a maximum threshold.
public class SuspicionCondition implements Condition {
    private int maxSuspicion;

    public SuspicionCondition(int maxSuspicion) {
        this.maxSuspicion = maxSuspicion;
    }

    @Override
    public boolean isMet(Player player) {
        return player.getSuspicion() < maxSuspicion;
    }
}
