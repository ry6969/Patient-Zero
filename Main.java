import engine.StoryData;
import model.StoryNode;
import model.Player;
import ui.TextRenderer;

public class Main {
    public static void main(String[] args) {
    
    Player player = new Player(10, 10, 0, 0, 1);
    StoryNode node = StoryData.getNode("Start");
    
    TextRenderer.printGameScreen(player, node.getStory(), node.getChoices());
    
    
    }
}
