import engine.GameEngine;
import ui.TextRenderer;

public class Main {
    public static void main(String[] args) {
        TextRenderer.clearScreen();
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║                                            ║");
        System.out.println("║           PATIENT ZERO: A Story            ║");
        System.out.println("║                                            ║");
        System.out.println("║   The world fell silent thirty days ago.   ║");
        System.out.println("║   You are humanity's last hope.            ║");
        System.out.println("║                                            ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("\nPress Enter to begin...");
        
        // Use System.in directly - don't create a Scanner here
        try {
            System.in.read(); // Wait for player to press Enter
        } catch (Exception e) {
            // Ignore
        }
        
        GameEngine game = new GameEngine();
        game.startGame();
    }
}
