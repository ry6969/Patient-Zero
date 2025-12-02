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

        // Decrement purge countdown if active
        if (player.isPurgeActive()) {
            player.setPurgeCountdown(player.getPurgeCountdown() - 1);
        }
    }
}
