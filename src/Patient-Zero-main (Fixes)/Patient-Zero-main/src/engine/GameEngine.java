package engine;

import model.Player;
import model.StoryNode;
import model.Choice;
import effect.*;
import ui.TextRenderer;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 -GameEngine - Core game loop and logic
 - Handles: interrupts, effect application, branching decisions, input validation
 */
public class GameEngine {
    private Player player;
    private StoryNode currentNode;
    private Scanner scanner;
    private Random random;
    private boolean gameRunning;

    public GameEngine() {
        this.player = new Player(10, 5, 0, 0, 1); // health, energy, knowledge, suspicion, day
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.gameRunning = false;
    }

    /**
     * Main game loop - starts the game from the beginning
     */
    public void startGame() {
        gameRunning = true;
        player.setZone("Wasteland");
        currentNode = StoryData.getNode("Start");

        TextRenderer.printWelcomeScreen(); 
        scanner.nextLine();

        applyEffects(currentNode, player); // 

        while (gameRunning) {

            // 1. Check global interrupts first
            if (checkInterrupts()) {
                continue; // Interrupt handled, loop continues or ends
            }

            // 2. Get and filter available choices by conditions
            List<Choice> availableChoices = getAvailableChoices(currentNode);

            // 3. Display game screen using TextRenderer
            TextRenderer.clearScreen();
            TextRenderer.printGameScreen(player, currentNode.getStory(), availableChoices);

            // 4. Get player input
            int choiceIndex = getPlayerInput(availableChoices.size()) - 1; // TextRenderer expects 1-indexed, convert to 0-indexed

            // 5. Get the chosen choice
            Choice chosenChoice = availableChoices.get(choiceIndex);

            // 6. Handle special node effects (zone changes, flags, tracking)
            handleSpecialNodeEffects(currentNode.getId(), player);

            // 7. Get the next node ID (may be a branching point)
            String nextNodeId = getNextNodeId(chosenChoice.getNextNodeId(), player);

            // 8. Move to next node
            currentNode = StoryData.getNode(nextNodeId);

            if (currentNode == null) {
                System.out.println("[ERROR: Node not found: " + nextNodeId + "]");
                gameRunning = false;
                break;
            }

            // 9. Apply effects to the new node here 
            applyEffects(currentNode, player);

            // 10. Decrement purge countdown if active
            if (player.isPurgeActive()) {
                player.setPurgeCountdown(player.getPurgeCountdown() - 1);
            }
        }

        scanner.close();
    }

    //Handle special node effects like zone changes, flag setting, and tracking
    private void handleSpecialNodeEffects(String nodeId, Player player) {
        switch (nodeId) {
            // Zone changes
            case "Arrival":
                player.setZone("Haven");
                player.setHavenDays(1); // setting days in haven to 1 after arriving
                break;

            case "ResearchHub":
                player.setZone("Hub");
                break;

            // Tracking updates
            case "ObserveClinic_Caught":
            case "ObserveClinic_Learn":
                player.setClinicVisits(player.getClinicVisits() + 1);
                break;

            // Sleep/Rest actions - increment haven days only when sleeping in Haven
            case "LeisurelyRest":
            case "RestInDorm":
            case "ForcedRest":
                if (player.getZone().equals("Haven")) {
                    player.setHavenDays(player.getHavenDays() + 1);
                }
                break;

            // No special effects for other nodes
            default:
                break;
        }
    }

    /**
     * Check for global interrupts that stop normal gameplay
     * Returns true if an interrupt was triggered
     */
    private boolean checkInterrupts() {
        // Health ≤ 0 → BadEnd
        if (player.getHealth() <= 0) {
            TextRenderer.clearScreen();
            TextRenderer.printGameScreen(player, StoryData.getNode("BadEnd").getStory(), new ArrayList<>());
            System.out.println("\n[END OF GAME]");
            gameRunning = false;
            return true;
        }

        // Suspicion ≥ 10 → ArrestedEnd
        if (player.getSuspicion() >= 10) {
            TextRenderer.clearScreen();
            TextRenderer.printGameScreen(player, StoryData.getNode("ArrestedEnd").getStory(), new ArrayList<>());
            System.out.println("\n[END OF GAME]");
            gameRunning = false;
            return true;
        }

        // Morale ≤ 0 → DespairEvent
        if (player.getMorale() <= 0) {
            currentNode = StoryData.getNode("DespairEvent");
            TextRenderer.clearScreen();
            List<Choice> despairChoices = getAvailableChoices(currentNode);
            TextRenderer.printGameScreen(player, currentNode.getStory(), despairChoices);
            int choiceIndex = getPlayerInput(despairChoices.size()) - 1;
            Choice chosen = despairChoices.get(choiceIndex);
            applyEffects(currentNode, player);
            String nextNodeId = getNextNodeId(chosen.getNextNodeId(), player);
            currentNode = StoryData.getNode(nextNodeId);
            return true;
        }

        // Energy ≤ 0 → ForcedRest
        if (player.getEnergy() <= 0) {
            currentNode = StoryData.getNode("ForcedRest");
            TextRenderer.clearScreen();
            List<Choice> forcedRestChoices = getAvailableChoices(currentNode);
            TextRenderer.printGameScreen(player, currentNode.getStory(), forcedRestChoices);
            int choiceIndex = getPlayerInput(forcedRestChoices.size()) - 1;
            Choice chosen = forcedRestChoices.get(choiceIndex);
            applyEffects(currentNode, player);
            handleSpecialNodeEffects(currentNode.getId(), player);
            String nextNodeId = getNextNodeId(chosen.getNextNodeId(), player);
            currentNode = StoryData.getNode(nextNodeId);
            return true;
        }

        // Haven days ≥ 20 (no purge) → HavenPanicEnd
        if (player.getHavenDays() >= 20 && !player.isPurgeActive()) {
            TextRenderer.clearScreen();
            TextRenderer.printGameScreen(player, StoryData.getNode("HavenPanicEnd").getStory(), new ArrayList<>());
            System.out.println("\n[END OF GAME]");
            gameRunning = false;
            return true;
        }

        // Purge countdown ≤ 0 → PurgeEnd
        if (player.isPurgeActive() && player.getPurgeCountdown() <= 0) {
            TextRenderer.clearScreen();
            TextRenderer.printGameScreen(player, StoryData.getNode("PurgeEnd").getStory(), new ArrayList<>());
            System.out.println("\n[END OF GAME]");
            gameRunning = false;
            return true;
        }

        return false; // No interrupts
    }

    /**
     * Get available choices filtered by conditions
     */
    private List<Choice> getAvailableChoices(StoryNode node) {
        List<Choice> available = new ArrayList<>();
        for (Choice choice : node.getChoices()) {
            if (choice.isAvailable(player)) {
                available.add(choice);
            }
        }
        return available;
    }


    //Get and validate player input
    private int getPlayerInput(int numChoices) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);

                if (choice < 1 || choice > numChoices) {
                    System.out.print("Choice out of range. Expected 1-" + numChoices + ", got " + choice + ". Please try again: ");
                    continue;
                }

                return choice; // Return as 1-indexed (TextRenderer handles this)
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number between 1 and " + numChoices + ": ");
            }
        }
    }

    
    //Apply all effects from a node to the player (polymorphism!)
    private void applyEffects(StoryNode node, Player player) {
        for (Effect effect : node.getEffects()) {
            effect.apply(player);
        }
    }

    //Get the actual next node ID (handles branching)
    //Some choices lead to branching decisions with multiple outcomes
    private String getNextNodeId(String choiceNodeId, Player player) {
        switch (choiceNodeId) {
            // ===== JOURNEY PHASE BRANCHING =====
            case "FastTravel":
                return (random.nextDouble() < 0.6) ? "FastTravel_Success" : "FastTravel_Failure";

            case "SearchTravel":
                return (random.nextDouble() < 0.5) ? "SearchTravel_Success" : "SearchTravel_Failure";

            // ===== HAVEN PHASE BRANCHING =====
            case "Mingle":
                return branchMingle();

            case "ObserveClinic":
                // 30% base + 10% per clinic visit
                double catchChance = 0.3 + (0.1 * player.getClinicVisits());
                return (random.nextDouble() < catchChance) ? "ObserveClinic_Caught" : "ObserveClinic_Learn";

            case "SearchDorms":
                return (random.nextDouble() < 0.5) ? "SearchDorms_Caught" : "SearchDorms_FindNote";

            case "Library":
                if (player.getKnowledge() < 7) {
                    return "Library_LowKnowledge";
                } else {
                    player.setMetScientist(true); // Flag: met the scientist
                    return "Library_HighKnowledge";
                }

            case "RiskInformation":
                return (random.nextDouble() < 0.5) ? "RiskInformation_Success" : "RiskInformation_Caught";

            // ===== RESEARCH HUB PHASE BRANCHING =====
            case "Section1Gate":
                return (player.getKnowledge() >= 3) ? "Section1Gate_Success" : "Section1Gate_Fail";

            case "StealSamples":
                return (random.nextDouble() < 0.5) ? "StealSamples_Success" : "StealSamples_Caught";

            case "FinalCheck":
                return (player.getKnowledge() >= 12) ? "FinalCheck_Ready" : "FinalCheck_NotReady";

            case "FinalHeist":
                return (random.nextDouble() < 0.5) ? "FinalHeist_Success" : "FinalHeist_Failure";

            case "EscapeEnd":
                return branchEscapeEnd();

            // ===== SPECIAL BRANCHING =====
            case "PurgeReveal":
                player.setPurgeActive(true);
                player.setPurgeCountdown(7);
                return choiceNodeId; // Continue to next choice node

            default:
                return choiceNodeId; // No branching, return as-is
        }
    }

    //MINGLE branching: Pick random unheard rumor or nothing
    private String branchMingle() {
        if (player.hasUnheardRumors()) {
            // Get random unheard rumor index
            int[] unheardIndices = player.getUnheardIndices();
            int randomIndex = unheardIndices[random.nextInt(unheardIndices.length)];

            // Mark it as heard
            player.markRumorHeard(randomIndex);

            // Fetch and display rumor text
            String rumorText = StoryData.RUMORS[randomIndex];
            System.out.println("\n[RUMOR DISCOVERED]");
            System.out.println("\"" + rumorText + "\"");

            return "Mingle_Rumor";
        } else {
            return "Mingle_Nothing";
        }
    }

    /**
     * ESCAPE END branching: Depends on Cris relationship
     */
    private String branchEscapeEnd() {
        int crisRelationship = player.getCrisRelationship();

        if (crisRelationship >= 6) {
            return "EscapeEnd_Alliance"; // Best ending with Cris
        } else if (crisRelationship >= 3) {
            return "EscapeEnd_Savior"; // Medium relationship
        } else {
            return "EscapeEnd_Fugitive"; // Low relationship - escape alone
        }
    }
}
