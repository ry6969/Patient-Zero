import engine.GameEngine;
import ui.TextRenderer;

public class Main {
    public static void main(String[] args) {
        TextRenderer.clearScreen();
           
        GameEngine game = new GameEngine();
        game.startGame();
    }
}
