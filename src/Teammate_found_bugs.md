# FOUND BUGS
- Here are some bugs that my teammates have found. Do check first if their findings are indeed correct. Don't change anything in the code yet. Just take note of it

### BUG:
- Days and Days in Haven starting at 0
### FIX: || GameEngine.java ||
	public gameEngine() {
--> this.player = new Player(10, 5, 0, 0, 0); // Before
--> this.player = new Player(10, 5, 0, 0, 1); // After (Starting at Day 1)

### FIX: 
- In case "arrival"
'''
--> player.setHavenDays(1); // added so it starts at day 1 in days in haven
'''

### BUG: 
- Knowledge, Days, Days In Haven, Suspicion stuck at 0
### FIX: For all conditions, removed "private int amount;"

### UPDATE!: 
- Added Inverted initialization 
'''
--> private boolean inverted = false;
'''

- Added method setInverted() 
'''
-->     public void setInverted(boolean inverted) {
        this.inverted = inverted; 
    }
'''

- Added method setInverted() 
'''
-->     public boolean isInverted() { 
        return inverted;
    }
'''

- Updated isAvailable() method
'''
-->     public boolean isAvailable(Player player) {
        boolean conditionsMet = true; // added
        for (Condition condition : conditions) {
            if (!condition.isMet(player)) {
                conditionsMet = false; // added
                break; // added
            }
        }

        if (inverted) { //
            return !conditionsMet; // added
        }

        return conditionsMet; // added
    }
'''

'''
!! setInverted method in StoryData.java !!

        Choice restChoice = new Choice("\"I need to rest for tomorrow.\"", "Completely exhausted", "RestInDorm");
        restChoice.addCondition(new EnergyCondition(1));
        restChoice.setInverted(true); // This means it only shows when condition is NOT met
        havenIntro.addChoice(restChoice);
'''

--> This means that this choice will not be shown when Energy is more than 0

--> Also added more Choice objects instead of addChoices so that when Energy is 0, none of the options will show besides the "Rest Option"
'''
!! Example of choice objects !!

        Choice libraryChoice = new Choice("\"The library might have answers.\"", "I need to understand the science behind this (Needs 5+ Knowledge, -1 Energy)", "Library");
        libraryChoice.addCondition(new KnowledgeCondition(5));
        libraryChoice.addCondition(new EnergyCondition(1));    //
        havenIntro.addChoice(libraryChoice);
        
        Choice workKitchenChoice = new Choice("\"Help in the kitchen.\"", "Blend in with workers (-1 Energy)", "WorkKitchen");
        workKitchenChoice.addCondition(new EnergyCondition(1));
        havenIntro.addChoice(workKitchenChoice);

        Choice cleaningDutyChoice = new Choice("\"Do cleaning duty.\"", "Appear harmless (-1 Energy)", "CleaningDuty");
        cleaningDutyChoice.addCondition(new EnergyCondition(1));
        havenIntro.addChoice(cleaningDutyChoice);

        Choice shareFoodChoice = new Choice("\"I should share my rations with that family.\"", "They look like they need it more than me (-1 Energy)", "ShareFood");
        shareFoodChoice.addCondition(new EnergyCondition(1));
        havenIntro.addChoice(shareFoodChoice);
'''

### BUG: 
-Rumor Handling
### FIX:
- GameEngine.java
'''
-->     //MINGLE branching: Pick random unheard rumor or nothing
    private String branchMingle() {
            if (player.getKnowledge() >= 4 || !player.hasUnheardRumors()) { // knowledge cap at 4
            return "Mingle_Nothing";
        }

            // 70% chance to hear a rumor, 30% chance to hear nothing
        if (random.nextDouble() < 0.7) {
            // Get random unheard rumor index
            int[] unheardIndices = player.getUnheardIndices();
            int randomIndex = unheardIndices[random.nextInt(unheardIndices.length)];

            // Mark it as heard
            player.markRumorHeard(randomIndex);

            StoryNode rumorNode = StoryData.getNode("Mingle_Rumor");
            String rumorText = StoryData.RUMORS[randomIndex];

            String newStory = "You listen to the refugees' whispered conversations.\n\n" +
                "[RUMOR DISCOVERED]\n" +
                "\"" + rumorText + "\"\n" +
                "(Knowledge +1)\n";
            rumorNode.setStory(newStory);

            return "Mingle_Rumor";
        } else {
            return "Mingle_Nothing";
        }
    }
'''

- StoryNode.java (added setStory() method)
'''
-->     public void setStory(String newStory) { // for mingle_rumor
        this.story = newStory;
    }
'''    