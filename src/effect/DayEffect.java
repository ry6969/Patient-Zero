package effect;
import model.Player;


//Effect that advances the player's day counter.
public class DayEffect extends Effect {
    private int amount;

    public DayEffect(int amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.advanceDay(amount);
    }
}
