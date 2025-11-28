import model.Player;
import model.StoryNode;
import model.Choice;
import effect.*;
import condition.*;
import engine.StoryData;

public class GameEngineTest {
    private static int testsRun = 0;
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    private Player player;

    private static void assertEqual(String testName, Object expected, Object actual) {
        testsRun++;
        if (expected == null && actual == null) {
            testsPassed++;
            System.out.println("✓ " + testName);
        } else if (expected != null && expected.equals(actual)) {
            testsPassed++;
            System.out.println("✓ " + testName);
        } else {
            testsFailed++;
            System.out.println("✗ " + testName);
            System.out.println("  Expected: " + expected + ", Got: " + actual);
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        testsRun++;
        if (condition) {
            testsPassed++;
            System.out.println("✓ " + testName);
        } else {
            testsFailed++;
            System.out.println("✗ " + testName);
        }
    }

    private static void assertFalse(String testName, boolean condition) {
        testsRun++;
        if (!condition) {
            testsPassed++;
            System.out.println("✓ " + testName);
        } else {
            testsFailed++;
            System.out.println("✗ " + testName);
        }
    }

    private static void assertNotNull(String testName, Object obj) {
        testsRun++;
        if (obj != null) {
            testsPassed++;
            System.out.println("✓ " + testName);
        } else {
            testsFailed++;
            System.out.println("✗ " + testName);
        }
    }

    private void testPlayerInitialization() {
        player = new Player(10, 5, 0, 0, 0);
        assertEqual("Player health initialization", 10, player.getHealth());
        assertEqual("Player energy initialization", 5, player.getEnergy());
        assertEqual("Player knowledge initialization", 0, player.getKnowledge());
        assertEqual("Player suspicion initialization", 0, player.getSuspicion());
    }

    private void testEnergyCapEnforcement() {
        player = new Player(10, 5, 0, 0, 0);
        player.changeEnergy(10);
        assertEqual("Energy cap at maximum (5)", 5, player.getEnergy());
        player.changeEnergy(-10);
        assertEqual("Energy floor at minimum (0)", 0, player.getEnergy());
    }

    private void testPlayerStatChanges() {
        player = new Player(10, 5, 0, 0, 0);
        player.changeHealth(-2);
        player.changeKnowledge(3);
        assertEqual("Health change", 8, player.getHealth());
        assertEqual("Knowledge change", 3, player.getKnowledge());
    }

    private void testZoneManagement() {
        player = new Player(10, 5, 0, 0, 0);
        assertEqual("Default zone is Wasteland", "Wasteland", player.getZone());
        player.setZone("Haven");
        assertEqual("Zone changed to Haven", "Haven", player.getZone());
    }

    private void testHealthEffect() {
        player = new Player(10, 5, 0, 0, 0);
        HealthEffect effect = new HealthEffect(-2);
        effect.apply(player);
        assertEqual("HealthEffect applies -2 damage", 8, player.getHealth());
    }

    private void testEnergyEffect() {
        player = new Player(10, 5, 0, 0, 0);
        player.setEnergy(3);
        EnergyEffect effect = new EnergyEffect(5);
        effect.apply(player);
        assertEqual("EnergyEffect respects cap", 5, player.getEnergy());
    }

    private void testKnowledgeEffect() {
        player = new Player(10, 5, 0, 0, 0);
        KnowledgeEffect effect = new KnowledgeEffect(3);
        effect.apply(player);
        assertEqual("KnowledgeEffect applies +3", 3, player.getKnowledge());
    }

    private void testEnergyCondition() {
        player = new Player(10, 5, 0, 0, 0);
        EnergyCondition condition = new EnergyCondition(3);
        player.setEnergy(2);
        assertFalse("EnergyCondition fails with energy < 3", condition.isMet(player));
        player.setEnergy(3);
        assertTrue("EnergyCondition passes with energy >= 3", condition.isMet(player));
    }

    private void testKnowledgeCondition() {
        player = new Player(10, 5, 0, 0, 0);
        KnowledgeCondition condition = new KnowledgeCondition(5);
        player.changeKnowledge(4);
        assertFalse("KnowledgeCondition fails with knowledge < 5", condition.isMet(player));
        player.changeKnowledge(1);
        assertTrue("KnowledgeCondition passes with knowledge >= 5", condition.isMet(player));
    }

    private void testChoiceFiltering() {
        player = new Player(10, 5, 0, 0, 0);
        Choice conditionalChoice = new Choice("Test Choice", "Test Subtext", "TestNode");
        conditionalChoice.addCondition(new KnowledgeCondition(5));
        assertFalse("Choice unavailable without meeting condition", conditionalChoice.isAvailable(player));
        player.changeKnowledge(5);
        assertTrue("Choice available when condition met", conditionalChoice.isAvailable(player));
    }

    private void testRumorSystem() {
        String[] rumors = StoryData.RUMORS;
        assertNotNull("RUMORS array exists", rumors);
        assertEqual("Has exactly 7 rumors", 7, rumors.length);
    }

    private void testRumorTracking() {
        player = new Player(10, 5, 0, 0, 0);
        assertFalse("Player hasn't heard rumor initially", player.hasHeardRumor(0));
        player.markRumorHeard(0);
        assertTrue("Player has heard rumor after marking", player.hasHeardRumor(0));
    }

    private void testStoryNodeRetrieval() {
        StoryNode startNode = StoryData.getNode("Start");
        assertNotNull("Start node exists", startNode);
        StoryNode nonExistentNode = StoryData.getNode("InvalidNodeId");
        assertEqual("Invalid node returns null", null, nonExistentNode);
    }

    private void testMultipleEffects() {
        player = new Player(10, 5, 0, 0, 0);
        StoryNode node = new StoryNode("TestNode", "Test story");
        node.addEffect(new HealthEffect(-2));
        node.addEffect(new KnowledgeEffect(3));
        for (Effect effect : node.getEffects()) {
            effect.apply(player);
        }
        assertEqual("Multiple effects: health -2", 8, player.getHealth());
        assertEqual("Multiple effects: knowledge +3", 3, player.getKnowledge());
    }

    private void testGameFlowSequence() {
        player = new Player(10, 5, 0, 0, 0);
        player.setZone("Wasteland");
        player.changeKnowledge(2);
        assertEqual("Game flow: knowledge +2", 2, player.getKnowledge());
        player.setZone("Haven");
        player.setHavenDays(1);
        assertEqual("Game flow: haven days +1", 1, player.getHavenDays());
    }

    private void testInterruptConditions() {
        player = new Player(10, 5, 0, 0, 0);
        player.setHealth(0);
        assertTrue("Health interrupt: health <= 0", player.getHealth() <= 0);
        
        player = new Player(10, 5, 0, 0, 0);
        player.changeSuspicion(10);
        assertTrue("Suspicion interrupt: suspicion >= 10", player.getSuspicion() >= 10);
        
        player = new Player(10, 5, 0, 0, 0);
        player.setEnergy(0);
        assertTrue("Energy interrupt: energy <= 0", player.getEnergy() <= 0);
        
        player = new Player(10, 5, 0, 0, 0);
        player.setHavenDays(20);
        assertTrue("Haven panic interrupt: havenDays >= 20", player.getHavenDays() >= 20);
    }

    private void testRelationshipTracking() {
        player = new Player(10, 5, 0, 0, 0);
        player.setCrisRelationship(0);
        assertEqual("Relationship initialized to 0", 0, player.getCrisRelationship());
        player.setCrisRelationship(3);
        assertEqual("Relationship increased to 3", 3, player.getCrisRelationship());
    }

    private void testStoryProgression() {
        assertNotNull("Start node exists", StoryData.getNode("Start"));
        assertNotNull("JourneyChoices node exists", StoryData.getNode("JourneyChoices"));
        assertNotNull("Arrival node exists", StoryData.getNode("Arrival"));
        assertNotNull("HavenIntro node exists", StoryData.getNode("HavenIntro"));
        assertNotNull("ResearchHub node exists", StoryData.getNode("ResearchHub"));
        assertNotNull("PurgeReveal node exists", StoryData.getNode("PurgeReveal"));
    }

    public void runAllTests() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PATIENT ZERO - COMPREHENSIVE UNIT TEST SUITE");
        System.out.println("=".repeat(70) + "\n");

        System.out.println("--- Player Model Tests ---");
        testPlayerInitialization();
        testEnergyCapEnforcement();
        testPlayerStatChanges();
        testZoneManagement();

        System.out.println("\n--- Effect System Tests ---");
        testHealthEffect();
        testEnergyEffect();
        testKnowledgeEffect();

        System.out.println("\n--- Condition System Tests ---");
        testEnergyCondition();
        testKnowledgeCondition();

        System.out.println("\n--- Choice Filtering Tests ---");
        testChoiceFiltering();

        System.out.println("\n--- StoryData Tests ---");
        testRumorSystem();
        testRumorTracking();
        testStoryNodeRetrieval();

        System.out.println("\n--- Integration Tests ---");
        testMultipleEffects();
        testGameFlowSequence();
        testInterruptConditions();
        testRelationshipTracking();
        testStoryProgression();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("TEST SUMMARY");
        System.out.println("=".repeat(70));
        System.out.println("Total Tests:  " + testsRun);
        System.out.println("Passed:       " + testsPassed + " ✓");
        System.out.println("Failed:       " + testsFailed + " ✗");
        System.out.println("Success Rate: " + (testsPassed * 100 / testsRun) + "%");
        System.out.println("=".repeat(70) + "\n");

        if (testsFailed == 0) {
            System.out.println("🎉 ALL TESTS PASSED! 🎉\n");
        } else {
            System.out.println("⚠️  " + testsFailed + " TEST(S) FAILED\n");
        }
    }

    public static void main(String[] args) {
        GameEngineTest testSuite = new GameEngineTest();
        testSuite.runAllTests();
    }
}
