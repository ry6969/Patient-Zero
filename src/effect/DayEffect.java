package effect;
import model.Player;


//Effect that advances the player's day counter.
public class DayEffect extends Effect {

    public DayEffect(int amount) {
        super(amount);
    }

    @Override
    public void apply(Player player) {
        player.advanceDay(amount);
    }
}
