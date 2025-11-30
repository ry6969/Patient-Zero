package ui;

import model.Player;
import model.Choice;

import java.util.List;
import java.util.ArrayList;

public class TextRenderer {

    private static final int MAX_WIDTH = 75;
    private static final int COLUMN_WIDTH = 14;

    //Printing the Welcome Screen
    public static void printWelcomeScreen() {
        printDoubleBorder();
        System.out.println();
        System.out.println();
        printIcon();
        
        // Add 2 newlines after the icon
        System.out.println();
        System.out.println();
        
        // Print "PATIENT ZERO" centered
        String patientZero = "PATIENT ZERO";
        int padding = (MAX_WIDTH - patientZero.length()) / 2;
        System.out.println(" ".repeat(padding) + patientZero);
        
        // Print "Press Enter" centered
        String pressEnter = "Press Enter";
        padding = (MAX_WIDTH - pressEnter.length()) / 2;
        System.out.println(" ".repeat(padding) + pressEnter);

        System.out.println();
        System.out.println();
        printDoubleBorder();
    }

    //Printing the Game Screen

    public static void printGameScreen(Player player, String story, List<Choice> choices){
        printStats(player);
        printStorySection(story);
        printChoices(choices);
    }

    //Private Methods

    private static void printStats(Player player) {
        printDoubleBorder();
        System.out.println("STATS:");
        printSingleBorder();

        String[] stats = {
            "Health: " + player.getHealth(),
            "Energy: " + player.getEnergy(),
            "Knowledge: " + player.getKnowledge(),
            "Suspicion: " + player.getSuspicion(),
            "Morale: " + player.getMorale()
        };

        System.out.print("|");
        for (String stat : stats) {
            int padding = (COLUMN_WIDTH - stat.length()) / 2;

            System.out.print(" ".repeat(padding) + stat + " ".repeat(padding) + "|");
        }
        System.out.println();

        printDoubleBorder();
        System.out.println("DAY: " + player.getDay());
        if (player.getZone().equals("Haven")) {
            System.out.println("DAYS IN HAVEN: " + player.getHavenDays());
        } else if (player.getZone().equals("Hub")) {
            System.out.println("Purge Countdown: " + player.getPurgeCountdown());
        }
        printSingleBorder();
    }


    private static void printStorySection(String text){
        List<String> lines = wrap(text, MAX_WIDTH);
        for (String line : lines) {
            System.out.println(line);
        }
    }

    private static void printChoices(List<Choice> choices){
        printDoubleBorder();
        System.out.println("ACTIONS: ");
        printSingleBorder();
        for(int i = 0; i < choices.size(); i++){
            System.out.printf("[%d] %s\n", i+1, choices.get(i).getText());

            if(choices.get(i).getSubText() != null) {
                System.out.println("    └─ " + choices.get(i).getSubText());
            }
        }
        printDoubleBorder();
        System.out.print("Enter Action Number: ");
    }

private static void printIcon() {
    String[] iconLines = {
        "               @@@@@@@@@@               ",
        "             @@@@@@@@@@@@@@             ",
        "           : + + @@@@@@ *:: .           ",
        "          @       @@@@       @          ",
        "                  @@@@                  ",
        "          #      @*@@#@      *          ",
        "           @  : @@@@@@@@.:  @           ",
        "           @@@@@@@@@*@@@@@@@@           ",
        "         .  @@@@%      %@@@@  .         ",
        "    -@@@-@  @@@@ @:-.+- :@@@  @:@@@-    ",
        "  @@@@@@@-@%@@@: @-@@*@ :@@@%@-@@@@@@@  ",
        "   =@@@@@@*@@@@@  @@%@  @@@@@*@@@@@@=   ",
        "   @@@@@@@@-@  @@+.   +@@  @=@@@@@@@@   ",
        "   +@@@@@@@@@*  #@@@@@@#  *@@@@@@@@@+   ",
        "     =@@@@@@                @@@@@@=     ",
        "      -@-                      -@-      "
    };
    
    for (String line : iconLines) {
        // Center each line of the icon
        int padding = (MAX_WIDTH - line.length()) / 2;
        System.out.println(" ".repeat(padding) + line);
    }
}

    //Public Methods

    public static void printDoubleBorder(){
        System.out.println("=".repeat(MAX_WIDTH));
    }

    public static void printSingleBorder(){
        System.out.println("=".repeat(MAX_WIDTH));
    }

    // Word Wrapping
    private static List<String> wrap (String text, int width){
        List<String> result = new ArrayList<>();
        String[] words = text.split(" ");

        StringBuilder line = new StringBuilder();

        for (String w : words) {
            if (line.length() + w.length() + 1 > width){
                result.add(line.toString());
                line = new StringBuilder(w);
            } else {
                if (line.length() > 0) line.append(" ");
                line.append(w);
            }
        }

        if (line.length() > 0) {
            result.add(line.toString());
        }

        return result;
    }

    // Screen Clearing
    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("windows")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Linux / macOS
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fallback: just print some newlines
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

}

