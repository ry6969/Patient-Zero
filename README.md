# Patient Zero

A text-based console RPG demonstrating Object-Oriented Programming principles through a complex game engine with branching narratives, resource management, and multiple endings.

---

## 📋 Description/Overview

**Patient Zero** is a choice-driven narrative game where players navigate a post-apocalyptic world ravaged by plague. You arrive at Haven—a hidden sanctuary—and must uncover the truth about your immunity while managing resources, navigating political factions, and making critical decisions that determine humanity's fate.

The game demonstrates advanced Object-Oriented Programming principles through a sophisticated engine featuring:
- **11 distinct endings** based on player stats, relationships, and choices
- **13 branching decision points** with random and deterministic outcomes
- **Resource management** (health, energy, knowledge, suspicion, morale)
- **Dynamic relationships** affecting story outcomes
- **Zone progression** (Wasteland → Haven → Research Hub)
- **Interrupt system** with 6 global conditions that override normal gameplay flow
- **Rumor collection system** with persistent knowledge tracking

### Game Premise

You are Patient Zero. In a world ravaged by plague, you arrive at Haven—a hidden sanctuary. Uncover the truth about your immunity, navigate political factions, and decide humanity's fate. Will you expose the secret, seize power, find comfort, or escape?

---

## 🎯 OOP Concepts Applied

Patient Zero demonstrates all core Object-Oriented Programming principles:

### 1. Encapsulation

**Player Model**: 
- Private fields (health, energy, knowledge, suspicion, morale, day, zone, havenDays, etc.) protect game state
- Public getter/setter methods provide controlled access
- Validation enforced in methods (e.g., `changeEnergy()` enforces 0-5 cap)
- Example:
  ```java
  private int energy;
  
  public void changeEnergy(int delta) {
      this.energy += delta;
      if (this.energy > 5) this.energy = 5;  // Cap at max
      if (this.energy < 0) this.energy = 0;  // Cap at min
  }
  ```

**Data Separation**:
- `StoryData.RUMORS` (static String array) contains narrative content
- `Player.heardRumors` (boolean array) tracks which rumors player discovered
- Clear separation between content and state

### 2. Abstraction

**Effect System**:
- Abstract `Effect` class defines `apply(Player)` method
- GameEngine applies effects without knowing concrete types
- Hides complexity of stat modifications
- Example:
  ```java
  for (Effect effect : node.getEffects()) {
      effect.apply(player);  // Don't need to know if it's Health, Energy, etc.
  }
  ```

**GameEngine**:
- Hides complexity of 13 branching points, 6 interrupts, 11-step game loop
- Main.java only calls: `new GameEngine().startGame()`
- Internal logic completely abstracted away

**TextRenderer**:
- Abstracts all console formatting and display logic
- GameEngine only calls `printGameScreen()` and `clearScreen()`

### 3. Inheritance

**Effect Hierarchy**:
```
        Effect (abstract class)
           ↑
    ┌──────┼──────┬─────────┬──────────┬────────┐
    │      │      │         │          │        │
Health  Energy  Knowledge  Suspicion  Morale  Day
Effect  Effect   Effect     Effect    Effect  Effect
```
- All effects inherit `protected int amount` field
- Each implements `apply(Player)` method
- Enables code reuse and consistent interface

**Condition Hierarchy**:
```
      Condition (interface)
           ↑
    ┌──────┼──────┬──────────┐
    │      │      │          │
Energy  Knowledge  Suspicion  Flag
Condition Condition Condition Condition
```
- All implement `isMet(Player)` method
- Uniform interface for choice gating

### 4. Polymorphism

**Runtime Polymorphism with Effects**:
```java
// Node can have any mix of effect types
node.addEffect(new HealthEffect(-2));
node.addEffect(new EnergyEffect(-1));
node.addEffect(new KnowledgeEffect(1));

// Applied polymorphically - actual type determined at runtime
for (Effect effect : node.getEffects()) {
    effect.apply(player);  // Calls correct subclass method
}
```

**Polymorphism with Conditions**:
```java
// Choices can have multiple condition types
choice.addCondition(new EnergyCondition(3));
choice.addCondition(new KnowledgeCondition(5));

// Checked polymorphically
for (Condition condition : conditions) {
    if (!condition.isMet(player)) return false;
}
```

### 5. Arrays

**Boolean Array - Rumor Tracking**:
```java
// Player class
private boolean[] heardRumors = new boolean[7];

public void markRumorHeard(int index) {
    if (index >= 0 && index < 7) {
        heardRumors[index] = true;
    }
}

public boolean hasHeardRumor(int index) {
    return index >= 0 && index < 7 && heardRumors[index];
}
```
- O(1) lookup for rumor availability
- Persistent knowledge tracking throughout game

**String Array - Story Content**:
```java
// StoryData class
public static final String[] RUMORS = {
    "A woman whispers that her husband went to the medical wing...",
    "Two janitors argue about the 'incinerator schedule'...",
    // ... 7 total rumors
};
```
- Immutable story content
- Accessed by index during Mingle branching

---

## 🏗️ Program Structure

The project follows a clean **three-layer architecture** separating concerns:

```
┌─────────────────────────────────────┐
│   PRESENTATION LAYER                │
│   (TextRenderer, Main)              │
├─────────────────────────────────────┤
│   BUSINESS LOGIC LAYER              │
│   (GameEngine, Branching, Interrupts)
├─────────────────────────────────────┤
│   DATA LAYER                        │
│   (Player, StoryNode, StoryData)    │
├─────────────────────────────────────┤
│   RULE LAYER                        │
│   (Effects, Conditions)             │
└─────────────────────────────────────┘
```

### File Structure

```
📂 Patient Zero/
├── 📂 src/
│   ├── Main.java                    # Game entry point
│   ├── 📂 model/
│   │   ├── Player.java              # Game state: 19 fields, 40+ methods
│   │   ├── StoryNode.java           # Story content with effect list & dynamic story updates
│   │   └── Choice.java              # Player choices with condition list & inverted conditions
│   ├── 📂 engine/
│   │   ├── GameEngine.java          # Core game loop: 11 steps, 6 interrupts, 13 branching IDs
│   │   └── StoryData.java           # 61 story nodes, 7 rumors, effect wiring
│   ├── 📂 effect/
│   │   ├── Effect.java              # Abstract class
│   │   ├── HealthEffect.java        # -/+ health
│   │   ├── EnergyEffect.java        # -/+ energy (capped 0-5)
│   │   ├── KnowledgeEffect.java     # -/+ knowledge
│   │   ├── SuspicionEffect.java     # -/+ suspicion
│   │   ├── MoraleEffect.java        # -/+ morale
│   │   └── DayEffect.java           # Increment day counter
│   ├── 📂 condition/
│   │   ├── Condition.java           # Interface
│   │   ├── EnergyCondition.java     # Requires minimum energy
│   │   ├── KnowledgeCondition.java  # Requires minimum knowledge
│   │   ├── SuspicionCondition.java  # Requires suspicion < limit
│   │   └── FlagCondition.java       # Checks player flags
│   └── 📂 ui/
│       └── TextRenderer.java        # Formatted console display
├── 📄 README.md                     # This file
├── 📄 CHANGELOG.md                  # Version history
└── 📄 PROJECT_COMPLETION_SUMMARY.md # Detailed completion report
```

### Main Classes and Their Roles

**1. Main.java**
- **Role**: Entry point for the application
- **Responsibilities**: Display welcome screen, initialize GameEngine, start game

**2. Player.java** (Model Layer)
- **Role**: Game state management
- **Responsibilities**: 
  - Store 19 player stats (health, energy, knowledge, suspicion, morale, day, zone, havenDays, purgeCountdown, etc.)
  - Provide validated getters/setters
  - Enforce energy capping (0-5)
  - Track rumor knowledge with boolean array
  - Manage relationship scores

**3. StoryNode.java** (Model Layer)
- **Role**: Story content container
- **Responsibilities**: 
  - Store narrative text
  - Hold list of effects to apply
  - Contain available choices
  - Support dynamic story updates (for rumor text)

**4. Choice.java** (Model Layer)
- **Role**: Player decision representation
- **Responsibilities**: 
  - Display choice text and subtext
  - Track conditions that gate availability
  - Point to next story node ID
  - Support inverted conditions (show when NOT met)

**5. GameEngine.java** (Logic Layer)
- **Role**: Core game loop orchestration
- **Responsibilities**: 
  - Execute 11-step main game loop
  - Check 6 global interrupt conditions
  - Filter choices by conditions
  - Apply effects polymorphically
  - Handle 13 branching decision points
  - Validate player input
  - Manage zone transitions and special effects

**6. StoryData.java** (Data Layer)
- **Role**: Story content repository
- **Responsibilities**: 
  - Initialize all 61 story nodes
  - Store 7 rumors as static String array
  - Wire effects to nodes
  - Provide node lookup via HashMap

**7. Effect.java & Implementations** (Rule Layer)
- **Role**: Game stat modification
- **Implementations**: HealthEffect, EnergyEffect, KnowledgeEffect, SuspicionEffect, MoraleEffect, DayEffect
- **Responsibilities**: 
  - Apply stat changes polymorphically
  - Enforce game rules (energy cap, etc.)

**8. Condition.java & Implementations** (Rule Layer)
- **Role**: Choice gating logic
- **Implementations**: EnergyCondition, KnowledgeCondition, SuspicionCondition, FlagCondition
- **Responsibilities**: 
  - Check if player meets requirements
  - Enable conditional choice availability

**9. TextRenderer.java** (Presentation Layer)
- **Role**: Console UI formatting
- **Responsibilities**: 
  - Display formatted game screen
  - Show player stats with borders
  - Print story sections with word wrap
  - List numbered choices
  - Clear console screen

---

## 🚀 How to Run the Program

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Command line terminal (bash, cmd, or PowerShell)

### Step 1: Navigate to Project Directory
```bash
cd "/home/ry/Documents/School Projects/Patient Zero"
```
*(Adjust path based on your actual project location)*

### Step 2: Compile All Java Files
```bash
javac -cp src src/*.java src/**/*.java
```

This compiles all Java files in the `src` directory and its subdirectories.

### Step 3: Run the Game
```bash
java -cp src Main
```

### Step 4: Play the Game
1. Read the story text presented on screen
2. View your current stats (Health, Energy, Knowledge, Suspicion, Morale, Day)
3. Review available choices (numbered 1, 2, 3, etc.)
4. Enter the number of your choice and press Enter
5. Continue making decisions to progress through the story

### Alternative: Using an IDE
If using an IDE like IntelliJ IDEA, Eclipse, or VS Code:
1. Open the project folder
2. Set `src` as the source root
3. Run `Main.java` directly from the IDE

---

## 📸 Sample Output

### Game Start Screen
```
===========================================================================


                                @@@@@@@@@@               
                              @@@@@@@@@@@@@@             
                            : + + @@@@@@ *:: .           
                           @       @@@@       @          
                                   @@@@                  
                           #      @*@@#@      *          
                            @  : @@@@@@@@.:  @           
                            @@@@@@@@@*@@@@@@@@           
                          .  @@@@%      %@@@@  .         
                     -@@@-@  @@@@ @:-.+- :@@@  @:@@@-    
                   @@@@@@@-@%@@@: @-@@*@ :@@@%@-@@@@@@@  
                    =@@@@@@*@@@@@  @@%@  @@@@@*@@@@@@=   
                    @@@@@@@@-@  @@+.   +@@  @=@@@@@@@@   
                    +@@@@@@@@@*  #@@@@@@#  *@@@@@@@@@+   
                      =@@@@@@                @@@@@@=     
                       -@-                      -@-      


                               PATIENT ZERO
                                Press Enter


===========================================================================
```

### Gameplay Example
```
===========================================================================
STATS:
===========================================================================
|  Health: 10  |  Energy: 3  | Knowledge: 0 | Suspicion: 0 |  Morale: 10  |
===========================================================================
DAY: 2
DAYS IN HAVEN: 1
===========================================================================
You are in the refugees' quarters, a converted gymnasium filled with
cots.
People keep to themselves, avoiding eye contact. Something's not
right here.
===========================================================================
ACTIONS: 
===========================================================================
[1] "I should listen to what people are saying."
    └─ Maybe I can learn something useful... (-1 Energy)
[2] "The medical wing seems important..."
    └─ What are they really doing in there? (-1 Energy)
[3] "Maybe I can find something useful in the dorms."
    └─ People must leave clues behind... (-1 Energy)
[4] "Help in the kitchen."
    └─ Blend in with workers (-1 Energy)
[5] "Do cleaning duty."
    └─ Appear harmless (-1 Energy)
[6] "I should share my rations with that family."
    └─ They look like they need it more than me (-1 Energy)
[7] "I'll just rest and watch the courtyard."
    └─ Maybe I'm overthinking this... (-1 Energy)
[8] "I'm going for the main office. No more playing safe."
    └─ Time for desperate measures (High suspicion only, -2 Energy)
[9] "I need to rest for tomorrow."
    └─ Tomorrow will be another day
===========================================================================
Enter Action Number: 9
```

### After Making a Choice
```
===========================================================================
STATS:
===========================================================================
|  Health: 10  |  Energy: 5  | Knowledge: 0 | Suspicion: 0 |  Morale: 11  |
===========================================================================
DAY: 3
DAYS IN HAVEN: 1
===========================================================================
You collapse onto your cot, surrounded by the sounds of sleeping
refugees.

"Just a few hours of peace," you think before drifting off.

(A
new day begins - Energy fully restored, +1 day, Haven day counter +1)
===========================================================================
ACTIONS: 
===========================================================================
[1] Continue to Next Day
===========================================================================
Enter Action Number: 1
```

### Stat Changes Example (Energy Depletion)
```
===========================================================================
STATS:
===========================================================================
|  Health: 10  |  Energy: 0  | Knowledge: 4 | Suspicion: 0 |  Morale: 7  |
===========================================================================
DAY: 3
DAYS IN HAVEN: 2
===========================================================================
Your body finally gives out. You collapse, unable to take another step.

As
consciousness fades, you find yourself in a bunk, recovering.

(Energy
fully restored, +1 day)
===========================================================================
ACTIONS: 
===========================================================================
[1] (Continue)
===========================================================================
Enter Action Number: 1
```

### Game Ending Example
```
===========================================================================
STATS:
===========================================================================
|  Health: 11  |  Energy: 3  | Knowledge: 7 | Suspicion: 3 |  Morale: 6  |
===========================================================================
DAY: 9
Purge Countdown: 0
===========================================================================
ENDING: CLEANSED

The gas floods the vents exactly on schedule. You take
your final breath surrounded by the evidence that could have saved
everyone.

The Haven reports another 'successful containment operation.'
The world never learns the truth.

GAME OVER
===========================================================================
ACTIONS: 
===========================================================================
===========================================================================
Enter Action Number: 
[END OF GAME]
```

---

## 👥 Authors and Acknowledgement

### Development Team

<table>
<tr>
<th>Name</th>
<th>Role</th>
<th>Contributions</th>
</tr>
<tr>
<td><strong>Bueno, Basty A.</strong></td>
<td>Lead Developer</td>
<td>Game engine architecture, effect system, branching logic</td>
</tr>
<tr>
<td><strong>Canalita, Harry D.</strong></td>
<td>Developer</td>
<td>Story nodes, condition system, testing & bug fixes</td>
</tr>
<tr>
<td><strong>Manalo, Cris Julian V.</strong></td>
<td>Developer</td>
<td>Player model, UI rendering, documentation</td>
</tr>
</table>

### Acknowledgements

- **Professor**: For guidance on Object-Oriented Programming principles
- **Course**: CS/IT Object-Oriented Programming Course
- **Inspiration**: Text-based adventure games and narrative-driven RPGs
- **Java Documentation**: Oracle Java SE Documentation for reference

### Special Thanks

Special appreciation to all teammates for their dedication, collaborative problem-solving, and commitment to demonstrating OOP principles through this project.

---

## 📝 Other Sections

### Game Features
- **11 Distinct Endings**: Different outcomes based on stats, relationships, and choices
- **13 Branching Decision Points**: Random and deterministic outcomes affecting story progression
- **7 Collectable Rumors**: Story elements with persistent player knowledge tracking via boolean arrays
- **Resource Management**: Health, Energy, Knowledge, Suspicion, Morale dynamically affect gameplay
- **Zone Progression**: Travel from Wasteland → Haven → Research Hub with zone-specific mechanics
- **Relationship Dynamics**: Cris relationship determines escape ending variant
- **Global Interrupts**: 6 interrupt conditions override normal flow (health, suspicion, morale, energy, haven days, purge countdown)
- **Conditional Choices**: Choices become available/unavailable based on player stats
- **Inverted Conditions**: Choices can be shown only when conditions are NOT met (e.g., rest option appears only when energy is low)
- **Purge System**: 7-day countdown creates time pressure for critical decisions
- **Haven Days Tracking**: Day counter specifically for time spent in Haven (increments only on sleep/rest)

### How to Play
1. **Start** the game by running `Main`
2. **Make choices** by entering the choice number (1-9 typically)
3. **Manage stats**:
   - **Health**: Drop to ≤0 = BadEnd
   - **Energy**: Required for activities (0-5 capped). When energy hits 0, triggers ForcedRest
   - **Knowledge**: Unlock choices at 5+ and 7+ thresholds
   - **Suspicion**: Hit 10 = ArrestedEnd
   - **Morale**: Drop to ≤0 = DespairEvent
   - **Days in Haven**: Track time spent resting in Haven (increments only on sleep/rest actions)
4. **Collect Rumors**: Encounter story elements that add to your knowledge (max 4 knowledge from rumors)
5. **Build Relationships**: Interacting with Cris affects escape ending outcome
6. **Reach Endings**: Decisions and stats determine which of 11 endings you get

### Rest System
The game has three rest nodes:
- **LeisurelyRest**: Casual resting in Haven (restores morale, increments haven days)
- **RestInDorm**: Intentional sleep in Haven dorm (advances day, restores energy, increments haven days)
- **ForcedRest**: Automatic rest when energy hits 0 (emergency collapse, advances day, restores energy, increments haven days)

**Important**: Haven days counter only increments on actual sleep/rest actions, not on regular explorations. Regular actions (Mingle, ObserveClinic, SearchDorms) do NOT count as days passing.

### Game Endings (11 Total)
1. **BadEnd**: Health ≤ 0 (starvation/exhaustion)
2. **ArrestedEnd**: Suspicion ≥ 10 (discovered by authorities)
3. **HavenPanicEnd**: Haven Days ≥ 20 without purge (confinement madness)
4. **PurgeEnd**: Purge countdown ≤ 0 (military execution)
5. **DespairEvent**: Morale ≤ 0 (temporary event, leads to HavenIntro or ResearchHub)
6. **ComfortBadEnd**: Choosing comfort and stability
7. **Collapse**: Authority system fails
8. **EscapeEnd_Alliance**: Cris relationship ≥ 6 (united escape with scientist)
9. **EscapeEnd_Savior**: Cris relationship 3-5 (escape with medium relationship)
10. **EscapeEnd_Fugitive**: Cris relationship ≤ 2 (solo escape)
11. **ExposeEnd**: Reveal truth to humanity

### Code Metrics
- **Total Files**: 18 Java files
- **Total Lines of Code**: ~2,000+ LOC
- **Game Nodes**: 61 unique story nodes
- **Branching Points**: 13 decision branches
- **Global Interrupts**: 6 interrupt conditions
- **Effects**: 6 effect types (polymorphic)
- **Conditions**: 4 condition types (polymorphic)

### Game Loop Architecture
The GameEngine follows an 11-step main loop:
1. **Check global interrupts** (health, suspicion, morale, energy, haven days, purge countdown)
2. **Get available choices** (filtered by conditions)
3. **Display game screen** (stats, story, choices)
4. **Get player input** (validated choice number)
5. **Get chosen choice** (from available list)
6. **Apply effects** from current node (polymorphic)
7. **Handle special node effects** (zone changes, flag updates, haven day increments on rest)
8. **Get next node ID** (handles 13 branching points)
9. **Move to next node** (null check)
10. **Decrement purge countdown** if active
11. **Loop continues** or game ends

---

## 📄 License & Disclaimer

**Educational Use Only**

This project was created for educational purposes as part of an Object-Oriented Programming course. Students may use this as a reference for understanding OOP concepts, but should not copy it verbatim for their own assignments.

**Version Information**
- **Current Version**: 2.2
- **Last Updated**: December 1, 2025
- **Java Version**: Java 11+

---

**For detailed technical documentation, see:**
- `developer guides/README_DEVELOPERS.md` - Architecture and design patterns
- `CHANGELOG.md` - Version history
- `PROJECT_COMPLETION_SUMMARY.md` - Completion metrics

---

**© 2025 Patient Zero Development Team. All Rights Reserved.**
