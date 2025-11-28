import model.Player;import model.Player;

import model.StoryNode;import model.StoryNode;

import model.Choice;import model.Choice;

import effect.*;import effect.*;

import condition.*;import condition.*;

import engine.StoryData;import engine.StoryData;



/**/**

 * Comprehensive test suite for Patient Zero game engine * Comprehensive test suite for Patient Zero game engine

 * Unit tests for: Player model, Effects, Conditions, Game logic * Unit tests for: Player model, Effects, Conditions, Game logic

 * No external dependencies - uses simple assertion methods * No external dependencies - uses simple assertion methods

 */ */

public class GameEngineTest {public class GameEngineTest {



    private static int testsRun = 0;    private static int testsRun = 0;

    private static int testsPassed = 0;    private static int testsPassed = 0;

    private static int testsFailed = 0;    private static int testsFailed = 0;



    private Player player;    private Player player;



    // ============== Test Assertion Helpers ==============    // ============== Test Assertion Helpers ==============



    private static void assertEqual(String testName, Object expected, Object actual) {    private static void assertEqual(String testName, Object expected, Object actual) {

        testsRun++;        testsRun++;

        if (expected == null && actual == null) {        if (expected == null && actual == null) {

            testsPassed++;            testsPassed++;

            System.out.println("✓ " + testName);            System.out.println("✓ " + testName);

        } else if (expected != null && expected.equals(actual)) {        } else if (expected != null && expected.equals(actual)) {

            testsPassed++;            testsPassed++;

            System.out.println("✓ " + testName);            System.out.println("✓ " + testName);

        } else {        } else {

            testsFailed++;            testsFailed++;

            System.out.println("✗ " + testName);            System.out.println("✗ " + testName);

            System.out.println("  Expected: " + expected + ", Got: " + actual);            System.out.println("  Expected: " + expected + ", Got: " + actual);

        }        }

    }    }



    private static void assertTrue(String testName, boolean condition) {    private static void assertTrue(String testName, boolean condition) {

        testsRun++;        testsRun++;

        if (condition) {        if (condition) {

            testsPassed++;            testsPassed++;

            System.out.println("✓ " + testName);            System.out.println("✓ " + testName);

        } else {        } else {

            testsFailed++;            testsFailed++;

            System.out.println("✗ " + testName);            System.out.println("✗ " + testName);

        }        }

    }    }



    private static void assertFalse(String testName, boolean condition) {    private static void assertFalse(String testName, boolean condition) {

        testsRun++;        testsRun++;

        if (!condition) {        if (!condition) {

            testsPassed++;            testsPassed++;

            System.out.println("✓ " + testName);            System.out.println("✓ " + testName);

        } else {        } else {

            testsFailed++;            testsFailed++;

            System.out.println("✗ " + testName);            System.out.println("✗ " + testName);

        }        }

    }    }



    private static void assertNotNull(String testName, Object obj) {    private static void assertNotNull(String testName, Object obj) {

        testsRun++;        testsRun++;

        if (obj != null) {        if (obj != null) {

            testsPassed++;            testsPassed++;

            System.out.println("✓ " + testName);            System.out.println("✓ " + testName);

        } else {        } else {

            testsFailed++;            testsFailed++;

            System.out.println("✗ " + testName);            System.out.println("✗ " + testName);

            System.out.println("  Expected non-null value");            System.out.println("  Expected non-null value");

        }        }

    }    }



    // ============== Player Model Tests ==============    // ============== Player Model Tests ==============



    private void testPlayerInitialization() {    @Test

        player = new Player(10, 5, 0, 0, 0);    @DisplayName("Player initializes with correct stats")

        assertEqual("Player health initialization", 10, player.getHealth());    public void testPlayerInitialization() {

        assertEqual("Player energy initialization", 5, player.getEnergy());        assertEquals(10, player.getHealth(), "Health should be 10");

        assertEqual("Player knowledge initialization", 0, player.getKnowledge());        assertEquals(5, player.getEnergy(), "Energy should be 5");

        assertEqual("Player suspicion initialization", 0, player.getSuspicion());        assertEquals(0, player.getKnowledge(), "Knowledge should be 0");

        assertEqual("Player day initialization", 0, player.getDay());        assertEquals(0, player.getSuspicion(), "Suspicion should be 0");

        assertEqual("Player morale initialization", 0, player.getMorale());        assertEquals(0, player.getDay(), "Day should be 0");

    }        assertEquals(0, player.getMorale(), "Morale should be 0");

    }

    private void testEnergyCapEnforcement() {

        player = new Player(10, 5, 0, 0, 0);    @Test

        player.changeEnergy(10); // Try to add 10, should cap at 5    @DisplayName("Player energy changes respect max cap of 5")

        assertEqual("Energy cap at maximum (5)", 5, player.getEnergy());    public void testEnergyCapEnforcement() {

                player.changeEnergy(10); // Try to add 10, should cap at 5

        player.changeEnergy(-10); // Try to subtract 10, should floor at 0        assertEquals(5, player.getEnergy(), "Energy should cap at 5");

        assertEqual("Energy floor at minimum (0)", 0, player.getEnergy());        

    }        player.changeEnergy(-10); // Try to subtract 10, should floor at 0

        assertEquals(0, player.getEnergy(), "Energy should floor at 0");

    private void testPlayerStatChanges() {    }

        player = new Player(10, 5, 0, 0, 0);

        player.changeHealth(-2);    @Test

        player.changeKnowledge(3);    @DisplayName("Player can change all stats")

        player.changeSuspicion(1);    public void testPlayerStatChanges() {

        player.changeMorale(2);        player.changeHealth(-2);

                player.changeKnowledge(3);

        assertEqual("Health change", 8, player.getHealth());        player.changeSuspicion(1);

        assertEqual("Knowledge change", 3, player.getKnowledge());        player.changeMorale(2);

        assertEqual("Suspicion change", 1, player.getSuspicion());        

        assertEqual("Morale change", 2, player.getMorale());        assertEquals(8, player.getHealth());

    }        assertEquals(3, player.getKnowledge());

        assertEquals(1, player.getSuspicion());

    private void testZoneManagement() {        assertEquals(2, player.getMorale());

        player = new Player(10, 5, 0, 0, 0);    }

        assertEqual("Default zone is Wasteland", "Wasteland", player.getZone());

            @Test

        player.setZone("Haven");    @DisplayName("Player zone management works")

        assertEqual("Zone changed to Haven", "Haven", player.getZone());    public void testZoneManagement() {

                assertEquals("Wasteland", player.getZone(), "Should start in Wasteland");

        player.setZone("Hub");        

        assertEqual("Zone changed to Hub", "Hub", player.getZone());        player.setZone("Haven");

    }        assertEquals("Haven", player.getZone());

        

    private void testPurgeCountdown() {        player.setZone("Hub");

        player = new Player(10, 5, 0, 0, 0);        assertEquals("Hub", player.getZone());

        player.setPurgeActive(true);    }

        player.setPurgeCountdown(7);

            @Test

        assertEqual("Purge countdown set to 7", 7, player.getPurgeCountdown());    @DisplayName("Player purge countdown decrements correctly")

        assertTrue("Purge is active", player.isPurgeActive());    public void testPurgeCountdown() {

                player.setPurgeActive(true);

        player.setPurgeCountdown(player.getPurgeCountdown() - 1);        player.setPurgeCountdown(7);

        assertEqual("Purge countdown decremented", 6, player.getPurgeCountdown());        

    }        assertEquals(7, player.getPurgeCountdown());

        assertTrue(player.isPurgeActive());

    private void testHavenDays() {        

        player = new Player(10, 5, 0, 0, 0);        player.setPurgeCountdown(player.getPurgeCountdown() - 1);

        player.setHavenDays(0);        assertEquals(6, player.getPurgeCountdown());

        assertEqual("Haven days initialized to 0", 0, player.getHavenDays());    }

        

        player.setHavenDays(player.getHavenDays() + 1);    @Test

        player.setHavenDays(player.getHavenDays() + 1);    @DisplayName("Player haven days accumulate")

        assertEqual("Haven days accumulated to 2", 2, player.getHavenDays());    public void testHavenDays() {

    }        player.setHavenDays(0);

        assertEquals(0, player.getHavenDays());

    private void testClinicVisits() {        

        player = new Player(10, 5, 0, 0, 0);        player.setHavenDays(player.getHavenDays() + 1);

        player.setClinicVisits(0);        player.setHavenDays(player.getHavenDays() + 1);

        player.setClinicVisits(player.getClinicVisits() + 1);        assertEquals(2, player.getHavenDays());

        player.setClinicVisits(player.getClinicVisits() + 1);    }

        

        assertEqual("Clinic visits tracked to 2", 2, player.getClinicVisits());    @Test

    }    @DisplayName("Player clinic visits track correctly")

    public void testClinicVisits() {

    // ============== Effect System Tests ==============        player.setClinicVisits(0);

        player.setClinicVisits(player.getClinicVisits() + 1);

    private void testHealthEffect() {        player.setClinicVisits(player.getClinicVisits() + 1);

        player = new Player(10, 5, 0, 0, 0);        

        HealthEffect effect = new HealthEffect(-2);        assertEquals(2, player.getClinicVisits());

        effect.apply(player);    }

        assertEqual("HealthEffect applies -2 damage", 8, player.getHealth());

    }    // ============== Effect System Tests ==============



    private void testEnergyEffect() {    @Test

        player = new Player(10, 5, 0, 0, 0);    @DisplayName("HealthEffect applies correctly")

        player.setEnergy(3);    public void testHealthEffect() {

        EnergyEffect effect = new EnergyEffect(5); // Try to add 5, should cap at 5        HealthEffect effect = new HealthEffect(-2);

        effect.apply(player);        effect.apply(player);

        assertEqual("EnergyEffect respects cap", 5, player.getEnergy());        assertEquals(8, player.getHealth());

    }    }



    private void testKnowledgeEffect() {    @Test

        player = new Player(10, 5, 0, 0, 0);    @DisplayName("EnergyEffect respects energy cap")

        KnowledgeEffect effect = new KnowledgeEffect(3);    public void testEnergyEffect() {

        effect.apply(player);        player.setEnergy(3);

        assertEqual("KnowledgeEffect applies +3", 3, player.getKnowledge());        EnergyEffect effect = new EnergyEffect(5); // Try to add 5, should cap at 5

    }        effect.apply(player);

        assertEquals(5, player.getEnergy(), "Energy should cap at max 5");

    private void testSuspicionEffect() {    }

        player = new Player(10, 5, 0, 0, 0);

        SuspicionEffect effect = new SuspicionEffect(2);    @Test

        effect.apply(player);    @DisplayName("KnowledgeEffect applies correctly")

        assertEqual("SuspicionEffect applies +2", 2, player.getSuspicion());    public void testKnowledgeEffect() {

    }        KnowledgeEffect effect = new KnowledgeEffect(3);

        effect.apply(player);

    private void testMoraleEffect() {        assertEquals(3, player.getKnowledge());

        player = new Player(10, 5, 0, 0, 0);    }

        MoraleEffect effect = new MoraleEffect(3);

        effect.apply(player);    @Test

        assertEqual("MoraleEffect applies +3", 3, player.getMorale());    @DisplayName("SuspicionEffect applies correctly")

    }    public void testSuspicionEffect() {

        SuspicionEffect effect = new SuspicionEffect(2);

    private void testDayEffect() {        effect.apply(player);

        player = new Player(10, 5, 0, 0, 0);        assertEquals(2, player.getSuspicion());

        DayEffect effect = new DayEffect(1);    }

        effect.apply(player);

        assertEqual("DayEffect increments day", 1, player.getDay());    @Test

            @DisplayName("MoraleEffect applies correctly")

        effect.apply(player);    public void testMoraleEffect() {

        assertEqual("DayEffect increments day again", 2, player.getDay());        MoraleEffect effect = new MoraleEffect(3);

    }        effect.apply(player);

        assertEquals(3, player.getMorale());

    // ============== Condition System Tests ==============    }



    private void testEnergyCondition() {    @Test

        player = new Player(10, 5, 0, 0, 0);    @DisplayName("DayEffect increments day counter")

        EnergyCondition condition = new EnergyCondition(3);    public void testDayEffect() {

                DayEffect effect = new DayEffect(1);

        player.setEnergy(2);        effect.apply(player);

        assertFalse("EnergyCondition fails with energy < 3", condition.isMet(player));        assertEquals(1, player.getDay());

                

        player.setEnergy(3);        effect.apply(player);

        assertTrue("EnergyCondition passes with energy >= 3", condition.isMet(player));        assertEquals(2, player.getDay());

    }    }



    private void testKnowledgeCondition() {    // ============== Condition System Tests ==============

        player = new Player(10, 5, 0, 0, 0);

        KnowledgeCondition condition = new KnowledgeCondition(5);    @Test

            @DisplayName("EnergyCondition requires minimum energy")

        player.changeKnowledge(4);    public void testEnergyCondition() {

        assertFalse("KnowledgeCondition fails with knowledge < 5", condition.isMet(player));        EnergyCondition condition = new EnergyCondition(3);

                

        player.changeKnowledge(1);        player.setEnergy(2);

        assertTrue("KnowledgeCondition passes with knowledge >= 5", condition.isMet(player));        assertFalse(condition.isMet(player), "Should fail with energy < 3");

    }        

        player.setEnergy(3);

    private void testSuspicionCondition() {        assertTrue(condition.isMet(player), "Should pass with energy >= 3");

        player = new Player(10, 5, 0, 0, 0);    }

        SuspicionCondition condition = new SuspicionCondition(10);

            @Test

        player.changeSuspicion(9);    @DisplayName("KnowledgeCondition requires minimum knowledge")

        assertTrue("SuspicionCondition passes with suspicion < 10", condition.isMet(player));    public void testKnowledgeCondition() {

                KnowledgeCondition condition = new KnowledgeCondition(5);

        player.changeSuspicion(1);        

        assertFalse("SuspicionCondition fails with suspicion >= 10", condition.isMet(player));        player.changeKnowledge(4);

    }        assertFalse(condition.isMet(player), "Should fail with knowledge < 5");

        

    private void testFlagCondition() {        player.changeKnowledge(1);

        player = new Player(10, 5, 0, 0, 0);        assertTrue(condition.isMet(player), "Should pass with knowledge >= 5");

        FlagCondition metScientistCondition = new FlagCondition("metScientist");    }

        

        assertFalse("FlagCondition fails when flag not set", metScientistCondition.isMet(player));    @Test

            @DisplayName("SuspicionCondition has upper bound check")

        player.setMetScientist(true);    public void testSuspicionCondition() {

        assertTrue("FlagCondition passes when flag set", metScientistCondition.isMet(player));        SuspicionCondition condition = new SuspicionCondition(10);

    }        

        player.changeSuspicion(9);

    // ============== Choice Filtering Tests ==============        assertTrue(condition.isMet(player), "Should pass with suspicion < 10");

        

    private void testChoiceFiltering() {        player.changeSuspicion(1);

        player = new Player(10, 5, 0, 0, 0);        assertFalse(condition.isMet(player), "Should fail with suspicion >= 10");

        Choice conditionalChoice = new Choice("Test Choice", "Test Subtext", "TestNode");    }

        conditionalChoice.addCondition(new KnowledgeCondition(5));

            @Test

        assertFalse("Choice unavailable without meeting condition", conditionalChoice.isAvailable(player));    @DisplayName("FlagCondition checks player flags")

            public void testFlagCondition() {

        player.changeKnowledge(5);        FlagCondition metScientistCondition = new FlagCondition("metScientist");

        assertTrue("Choice available when condition met", conditionalChoice.isAvailable(player));        

    }        assertFalse(metScientistCondition.isMet(player), "Should fail when flag not set");

        

    // ============== StoryData Tests ==============        player.setMetScientist(true);

        assertTrue(metScientistCondition.isMet(player), "Should pass when flag set");

    private void testRumorSystem() {    }

        String[] rumors = StoryData.RUMORS;

        assertNotNull("RUMORS array exists", rumors);    // ============== Choice Filtering Tests ==============

        assertEqual("Has exactly 7 rumors", 7, rumors.length);

            @Test

        boolean allValid = true;    @DisplayName("Choices filter based on conditions")

        for (String rumor : rumors) {    public void testChoiceFiltering() {

            if (rumor == null || rumor.isEmpty()) {        // Create a choice with a condition

                allValid = false;        Choice conditionalChoice = new Choice("Test Choice", "Test Subtext", "TestNode");

                break;        conditionalChoice.addCondition(new KnowledgeCondition(5));

            }        

        }        // Player doesn't meet condition

        assertTrue("All rumors are defined and non-empty", allValid);        assertFalse(conditionalChoice.isAvailable(player), "Choice should be unavailable");

    }        

        // Give player knowledge to meet condition

    private void testRumorTracking() {        player.changeKnowledge(5);

        player = new Player(10, 5, 0, 0, 0);        assertTrue(conditionalChoice.isAvailable(player), "Choice should be available");

        assertFalse("Player hasn't heard rumor initially", player.hasHeardRumor(0));    }

        

        player.markRumorHeard(0);    // ============== StoryData Tests ==============

        assertTrue("Player has heard rumor after marking", player.hasHeardRumor(0));

    }    @Test

    @DisplayName("StoryData contains rumor array")

    private void testStoryNodeRetrieval() {    public void testRumorSystem() {

        StoryNode startNode = StoryData.getNode("Start");        String[] rumors = StoryData.RUMORS;

        assertNotNull("Start node exists", startNode);        assertNotNull(rumors, "RUMORS array should exist");

                assertEquals(7, rumors.length, "Should have 7 rumors");

        StoryNode nonExistentNode = StoryData.getNode("InvalidNodeId");        

        assertEqual("Invalid node returns null", null, nonExistentNode);        for (String rumor : rumors) {

    }            assertNotNull(rumor, "Each rumor should be defined");

            assertFalse(rumor.isEmpty(), "Each rumor should have content");

    private void testNodeEffects() {        }

        StoryNode node = new StoryNode("TestNode", "Test story text");    }

        

        HealthEffect healthEffect = new HealthEffect(-1);    @Test

        node.addEffect(healthEffect);    @DisplayName("Player can track heard rumors")

            public void testRumorTracking() {

        assertEqual("Node has 1 effect", 1, node.getEffects().size());        assertFalse(player.hasHeardRumor(0), "Should not have heard rumor initially");

        assertEqual("Effect matches added effect", healthEffect, node.getEffects().get(0));        

    }        player.markRumorHeard(0);

        assertTrue(player.hasHeardRumor(0), "Should have heard rumor after marking");

    // ============== Integration Tests ==============    }



    private void testMultipleEffects() {    @Test

        player = new Player(10, 5, 0, 0, 0);    @DisplayName("StoryData provides story nodes")

        StoryNode node = new StoryNode("TestNode", "Test story");    public void testStoryNodeRetrieval() {

        node.addEffect(new HealthEffect(-2));        StoryNode startNode = StoryData.getNode("Start");

        node.addEffect(new KnowledgeEffect(3));        assertNotNull(startNode, "Start node should exist");

        node.addEffect(new SuspicionEffect(1));        

                StoryNode nonExistentNode = StoryData.getNode("InvalidNodeId");

        for (Effect effect : node.getEffects()) {        assertNull(nonExistentNode, "Invalid node should return null");

            effect.apply(player);    }

        }

            @Test

        assertEqual("Multiple effects: health -2", 8, player.getHealth());    @DisplayName("StoryNodes hold effects")

        assertEqual("Multiple effects: knowledge +3", 3, player.getKnowledge());    public void testNodeEffects() {

        assertEqual("Multiple effects: suspicion +1", 1, player.getSuspicion());        StoryNode node = new StoryNode("TestNode", "Test story text");

    }        

        HealthEffect healthEffect = new HealthEffect(-1);

    private void testGameFlowSequence() {        node.addEffect(healthEffect);

        player = new Player(10, 5, 0, 0, 0);        

        player.setZone("Wasteland");        assertEquals(1, node.getEffects().size(), "Node should have 1 effect");

        player.changeKnowledge(2);        assertEquals(healthEffect, node.getEffects().get(0), "Effect should match");

        player.changeSuspicion(1);    }

        

        assertEqual("Game flow: zone Wasteland", "Wasteland", player.getZone());    // ============== Integration Tests ==============

        assertEqual("Game flow: knowledge +2", 2, player.getKnowledge());

        assertEqual("Game flow: suspicion +1", 1, player.getSuspicion());    @Test

            @DisplayName("Multiple effects apply correctly")

        player.setZone("Haven");    public void testMultipleEffects() {

        player.setHavenDays(1);        StoryNode node = new StoryNode("TestNode", "Test story");

                node.addEffect(new HealthEffect(-2));

        assertEqual("Game flow: zone Haven", "Haven", player.getZone());        node.addEffect(new KnowledgeEffect(3));

        assertEqual("Game flow: haven days +1", 1, player.getHavenDays());        node.addEffect(new SuspicionEffect(1));

                

        player.setPurgeActive(true);        // Apply all effects

        player.setPurgeCountdown(7);        for (Effect effect : node.getEffects()) {

                    effect.apply(player);

        assertTrue("Game flow: purge activated", player.isPurgeActive());        }

        assertEqual("Game flow: purge countdown 7", 7, player.getPurgeCountdown());        

    }        assertEquals(8, player.getHealth());

        assertEquals(3, player.getKnowledge());

    private void testInterruptConditions() {        assertEquals(1, player.getSuspicion());

        // Health interrupt    }

        player = new Player(10, 5, 0, 0, 0);

        player.setHealth(0);    @Test

        assertTrue("Health interrupt: health <= 0", player.getHealth() <= 0);    @DisplayName("Game flow sequence works correctly")

            public void testGameFlowSequence() {

        // Suspicion interrupt        // Simulate a short game sequence

        player = new Player(10, 5, 0, 0, 0);        player.setZone("Wasteland");

        player.changeSuspicion(10);        player.changeKnowledge(2);

        assertTrue("Suspicion interrupt: suspicion >= 10", player.getSuspicion() >= 10);        player.changeSuspicion(1);

                

        // Energy interrupt        assertEquals("Wasteland", player.getZone());

        player = new Player(10, 5, 0, 0, 0);        assertEquals(2, player.getKnowledge());

        player.setEnergy(0);        assertEquals(1, player.getSuspicion());

        assertTrue("Energy interrupt: energy <= 0", player.getEnergy() <= 0);        

                // Move to Haven

        // Morale interrupt        player.setZone("Haven");

        player = new Player(10, 5, 0, 0, 0);        player.setHavenDays(1);

        player.setMorale(0);        

        assertTrue("Morale interrupt: morale <= 0", player.getMorale() <= 0);        assertEquals("Haven", player.getZone());

                assertEquals(1, player.getHavenDays());

        // Haven panic interrupt        

        player = new Player(10, 5, 0, 0, 0);        // Activate purge

        player.setHavenDays(20);        player.setPurgeActive(true);

        assertTrue("Haven panic interrupt: havenDays >= 20", player.getHavenDays() >= 20);        player.setPurgeCountdown(7);

                

        // Purge interrupt        assertTrue(player.isPurgeActive());

        player = new Player(10, 5, 0, 0, 0);        assertEquals(7, player.getPurgeCountdown());

        player.setPurgeActive(true);    }

        player.setPurgeCountdown(0);

        assertTrue("Purge interrupt: active and countdown <= 0",     @Test

            player.isPurgeActive() && player.getPurgeCountdown() <= 0);    @DisplayName("Interrupt conditions trigger correctly")

    }    public void testInterruptConditions() {

        // Health interrupt: health <= 0

    private void testRelationshipTracking() {        player.setHealth(0);

        player = new Player(10, 5, 0, 0, 0);        assertTrue(player.getHealth() <= 0, "Should trigger health interrupt");

        player.setCrisRelationship(0);        

        assertEqual("Relationship initialized to 0", 0, player.getCrisRelationship());        // Suspicion interrupt: suspicion >= 10

                player = new Player(10, 5, 0, 0, 0);

        player.setCrisRelationship(3);        player.changeSuspicion(10);

        assertEqual("Relationship increased to 3", 3, player.getCrisRelationship());        assertTrue(player.getSuspicion() >= 10, "Should trigger suspicion interrupt");

                

        assertTrue("Relationship >= 3 triggers Alliance", player.getCrisRelationship() >= 3);        // Energy interrupt: energy <= 0

    }        player = new Player(10, 5, 0, 0, 0);

        player.setEnergy(0);

    private void testStoryProgression() {        assertTrue(player.getEnergy() <= 0, "Should trigger energy interrupt");

        // Start        

        assertNotNull("Start node exists", StoryData.getNode("Start"));        // Morale interrupt: morale <= 0

                player = new Player(10, 5, 0, 0, 0);

        // Early game nodes        player.setMorale(0);

        assertNotNull("WelcomeFriend node exists", StoryData.getNode("WelcomeFriend"));        assertTrue(player.getMorale() <= 0, "Should trigger morale interrupt");

        assertNotNull("Arrival node exists", StoryData.getNode("Arrival"));        

                // Haven panic interrupt: havenDays >= 20

        // Mid-game nodes        player = new Player(10, 5, 0, 0, 0);

        assertNotNull("ResearchHub node exists", StoryData.getNode("ResearchHub"));        player.setHavenDays(20);

        assertNotNull("PurgeReveal node exists", StoryData.getNode("PurgeReveal"));        assertTrue(player.getHavenDays() >= 20, "Should trigger haven panic interrupt");

                

        // End game nodes        // Purge interrupt: purgeCountdown <= 0

        assertNotNull("ExposeEnd node exists", StoryData.getNode("ExposeEnd"));        player = new Player(10, 5, 0, 0, 0);

        assertNotNull("MilEnd node exists", StoryData.getNode("MilEnd"));        player.setPurgeActive(true);

    }        player.setPurgeCountdown(0);

        assertTrue(player.isPurgeActive() && player.getPurgeCountdown() <= 0, 

    // ============== Main Test Runner ==============            "Should trigger purge interrupt");

    }

    public void runAllTests() {

        System.out.println("\n" + "=".repeat(70));    @Test

        System.out.println("PATIENT ZERO - COMPREHENSIVE UNIT TEST SUITE");    @DisplayName("Relationship tracking works for EscapeEnd outcomes")

        System.out.println("=".repeat(70) + "\n");    public void testRelationshipTracking() {

        player.setCrisRelationship(0);

        System.out.println("--- Player Model Tests ---");        assertEquals(0, player.getCrisRelationship(), "Should start at 0");

        testPlayerInitialization();        

        testEnergyCapEnforcement();        // Increase relationship

        testPlayerStatChanges();        player.setCrisRelationship(3);

        testZoneManagement();        assertEquals(3, player.getCrisRelationship(), "Relationship should increase");

        testPurgeCountdown();        

        testHavenDays();        // Check alliance outcome (relationship >= 3)

        testClinicVisits();        assertTrue(player.getCrisRelationship() >= 3, "Should trigger Alliance outcome");

    }

        System.out.println("\n--- Effect System Tests ---");

        testHealthEffect();    @Test

        testEnergyEffect();    @DisplayName("Story progression from start to mid-game works")

        testKnowledgeEffect();    public void testGameProgression() {

        testSuspicionEffect();        // Start

        testMoraleEffect();        assertNotNull(StoryData.getNode("Start"));

        testDayEffect();        

        // Early game nodes should exist

        System.out.println("\n--- Condition System Tests ---");        assertNotNull(StoryData.getNode("WelcomeFriend"));

        testEnergyCondition();        assertNotNull(StoryData.getNode("Arrival"));

        testKnowledgeCondition();        

        testSuspicionCondition();        // Mid-game nodes should exist

        testFlagCondition();        assertNotNull(StoryData.getNode("ResearchHub"));

        assertNotNull(StoryData.getNode("PurgeReveal"));

        System.out.println("\n--- Choice Filtering Tests ---");        

        testChoiceFiltering();        // End game nodes should exist

        assertNotNull(StoryData.getNode("ExposeEnd"));

        System.out.println("\n--- StoryData Tests ---");        assertNotNull(StoryData.getNode("MilEnd"));

        testRumorSystem();    }

        testRumorTracking();}

        testStoryNodeRetrieval();
        testNodeEffects();

        System.out.println("\n--- Integration Tests ---");
        testMultipleEffects();
        testGameFlowSequence();
        testInterruptConditions();
        testRelationshipTracking();
        testStoryProgression();

        // Print summary
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
