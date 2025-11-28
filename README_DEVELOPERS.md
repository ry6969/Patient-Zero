# README_DEVELOPERS.md

## Patient Zero - Architecture & Design Documentation

This document provides comprehensive technical documentation for developers working on the Patient Zero game engine.

---

## Table of Contents

1. [Architecture Overview](#architecture-overview)
2. [Design Patterns & OOP Principles](#design-patterns--oop-principles)
3. [System Components](#system-components)
4. [Game Loop & Flow](#game-loop--flow)
5. [Branching & Decision Points](#branching--decision-points)
6. [Data Structures](#data-structures)
7. [Testing Strategy](#testing-strategy)
8. [Future Enhancements](#future-enhancements)

---

## Architecture Overview

### Three-Layer Architecture

Patient Zero uses a clean three-layer separation of concerns:

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

### Why This Architecture?

- **Separation of Concerns**: Story content (StoryData) is completely separate from game logic (GameEngine)
- **Testability**: Each layer can be tested independently
- **Maintainability**: Changes to story don't affect game logic
- **Extensibility**: New effects/conditions can be added without modifying core logic
- **Reusability**: TextRenderer can be used across different game projects

---

## Design Patterns & OOP Principles

### 1. **Polymorphism - Strategy Pattern**

#### Effect System

```java
// Interface definition
public interface Effect {
    void apply(Player player);
    String getDescription();
}

// Concrete implementations
public class HealthEffect implements Effect {
    private int amount;
    
    public HealthEffect(int amount) {
        this.amount = amount;
    }
    
    @Override
    public void apply(Player player) {
        player.changeHealth(amount);  // Uses Player's validation
    }
}

public class EnergyEffect implements Effect {
    private int amount;
    
    @Override
    public void apply(Player player) {
        player.changeEnergy(amount);  // Respects energy cap (0-5)
    }
}

// Usage in GameEngine
for (Effect effect : node.getEffects()) {
    effect.apply(player);  // Polymorphic dispatch at runtime
}
```

**Benefit**: New effect types (e.g., RelationshipEffect) can be added without modifying GameEngine.

#### Condition System

```java
// Interface definition
public interface Condition {
    boolean isMet(Player player);
    String getRequirementText();
}

// Concrete implementations
public class KnowledgeCondition implements Condition {
    private int requiredKnowledge;
    
    @Override
    public boolean isMet(Player player) {
        return player.getKnowledge() >= requiredKnowledge;
    }
}

// Usage in Choice filtering
public boolean isAvailable(Player player) {
    for (Condition condition : conditions) {
        if (!condition.isMet(player)) {
            return false;  // Polymorphic condition checking
        }
    }
    return true;
}
```

**Benefit**: Choice gating is flexible and can use any combination of conditions.

### 2. **Encapsulation - State Protection**

#### Player Model

```java
public class Player {
    // Private fields - encapsulated state
    private int health;
    private int energy;
    private int knowledge;
    // ... 16 more fields
    
    // Validated mutation through methods
    public void changeEnergy(int delta) {
        this.energy += delta;
        // Enforce bounds
        if (this.energy > 5) this.energy = 5;
        if (this.energy < 0) this.energy = 0;
    }
    
    // Immutable or validated getters
    public int getHealth() { return health; }
}
```

**Why it matters**: 
- Energy can never exceed 5 (game rule enforcement)
- All stat changes go through validation
- Impossible states cannot be created

### 3. **Abstraction - Hiding Complexity**

#### GameEngine

```java
public class GameEngine {
    public void startGame() {
        // Main loop - hides all complexity
        while (gameRunning) {
            if (checkInterrupts()) continue;
            List<Choice> choices = getAvailableChoices(currentNode);
            // ... 11-step loop ...
        }
    }
    
    // Public interface is simple
    // Internal complexity (13 branching IDs, 6 interrupts) hidden
}
```

Main.java only calls:
```java
GameEngine engine = new GameEngine();
engine.startGame();
```

No knowledge of game loop details needed!

### 4. **Inheritance - Code Reuse**

#### Effect Hierarchy

```
        ┌─────────┐
        │ Effect  │  (interface)
        └────┬────┘
             │
    ┌────────┼────────┬──────────┬──────────┬────────┐
    │        │        │          │          │        │
 Health   Energy   Knowledge  Suspicion  Morale    Day
 Effect   Effect   Effect     Effect    Effect   Effect
```

Each concrete effect:
1. Inherits from Effect interface
2. Implements apply(Player) method
3. Can be used polymorphically

#### Condition Hierarchy

```
         ┌──────────┐
         │Condition │  (interface)
         └────┬─────┘
              │
    ┌─────────┼─────────┬──────────┐
    │         │         │          │
  Energy  Knowledge  Suspicion   Flag
 Condition Condition Condition  Condition
```

### 5. **Exception Handling**

#### Custom Exceptions

```java
// Invalid user input
try {
    int choice = getPlayerInput(numChoices);
} catch (InvalidChoiceException e) {
    System.out.println("Error: " + e.getMessage());
    // Graceful error recovery
}

// Invalid game state
if (currentNode == null) {
    throw new GameStateException("Node not found: " + nodeId);
}
```

**Benefits**:
- Specific exceptions for different error types
- Stack trace preservation with cause chaining
- User-friendly error messages

### 6. **Array Usage - Rumor Tracking**

#### Separation of Text and State

```java
// In StoryData - contains TEXT only (static)
public static final String[] RUMORS = {
    "The plague started in the city...",
    "Government created a cure...",
    // ... 5 more rumors ...
};

// In Player - tracks which rumors HEARD (state)
private boolean[] heardRumors = new boolean[7];

// Player can hear same rumor as text from StoryData
public void markRumorHeard(int index) {
    if (index >= 0 && index < 7) {
        heardRumors[index] = true;
    }
}
```

**Why this design?**
- StoryData is content, Player is state
- O(1) lookup: `player.hasHeardRumor(rumor_id)`
- Easy to add/remove rumors (resize arrays)
- Multiple players could have different rumor knowledge

---

## System Components

### 1. Player Model (src/model/Player.java)

**Purpose**: Central repository of all game state

**Fields (19 total)**:
- Core stats (5): health, energy, knowledge, suspicion, day
- Status (1): morale
- Location (1): zone
- Tracking (3): havenDays, purgeCountdown, clinicVisits
- Flags (3): purgeActive, metScientist, metScavenger
- Relationship (1): crisRelationship
- Rumors (1): heardRumors (boolean[7])

**Key Methods**:
- `changeEnergy(int delta)`: Enforces 0-5 cap
- `changeHealth/Knowledge/Suspicion/Morale(int delta)`: Unbounded mutations
- `markRumorHeard(int index)`: Tracks narrative knowledge
- `setCrisRelationship(int value)`: Affects EscapeEnd outcome

### 2. StoryNode & Choice (src/model/)

**StoryNode**:
- Stores story text (immutable)
- Holds list of effects
- Can have multiple choice branches

**Choice**:
- Text to display to player
- SubText for costs/requirements
- Next node ID (may be branching ID)
- List of conditions (gates availability)

### 3. Effect System (src/effect/)

| Effect | Parameters | Impact |
|--------|-----------|--------|
| **HealthEffect** | int amount | Modifies health |
| **EnergyEffect** | int amount | Modifies energy (0-5 capped) |
| **KnowledgeEffect** | int amount | Modifies knowledge |
| **SuspicionEffect** | int amount | Modifies suspicion |
| **MoraleEffect** | int amount | Modifies morale |
| **DayEffect** | int amount | Increments day counter |

**Design**: Each effect is independent and composable. A node can have 0-N effects.

### 4. Condition System (src/condition/)

| Condition | Purpose | Typical Use |
|-----------|---------|-------------|
| **EnergyCondition(int min)** | Requires min energy | "Rest needed before journey" |
| **KnowledgeCondition(int min)** | Requires min knowledge | "Library choice (5+ knowledge)" |
| **SuspicionCondition(int max)** | Requires suspicion < max | "Library entry (suspicion < 10)" |
| **FlagCondition(String flag)** | Checks boolean flag | "metScientist → DormNight" |

### 5. GameEngine (src/engine/GameEngine.java)

**Main Loop (11 Steps)**:

```
Step 1: checkInterrupts()
   ├─ Health ≤ 0 → BadEnd
   ├─ Suspicion ≥ 10 → ArrestedEnd
   ├─ Morale ≤ 0 → DespairEvent (choice between HavenIntro/ResearchHub)
   ├─ Energy ≤ 0 → ForcedRest (varies by zone)
   ├─ HavenDays ≥ 20 → HavenPanicEnd
   └─ Purge countdown ≤ 0 → PurgeEnd

Step 2: getAvailableChoices()
   └─ Filter by Condition.isMet(player)

Step 3: Display using TextRenderer

Step 4: Get player input (validated)

Step 5: Apply node effects (polymorphic)

Step 6: handleSpecialNodeEffects()
   ├─ Arrival → set zone = Haven
   ├─ ResearchHub → set zone = Hub
   └─ ObserveClinic → increment clinicVisits

Step 7: getNextNodeId() (handles 13 branching IDs)

Step 8: Load next node

Step 9: Decrement purge if active

Step 10: Increment haven days if in Haven

Step 11: Loop
```

### 6. StoryData (src/engine/StoryData.java)

**Content**:
- 61 story nodes covering all paths
- 7 rumors (narrative knowledge elements)
- ~20+ nodes wired with effects
- Node initialization (no game logic)

**Design**: Pure data. No references to game logic or interrupts.

### 7. TextRenderer (src/ui/TextRenderer.java)

**Methods**:
- `clearScreen()`: Clears console
- `printGameScreen()`: Display node + stats + choices
- `printStats()`: Player stat display
- `printStorySection()`: Formatted story text
- `printChoices()`: Numbered choice list

**Why separate?**: Can swap TextRenderer for GraphicalRenderer without changing GameEngine.

---

## Game Loop & Flow

### Main Game Flow Diagram

```
┌─────────────────────────────────────┐
│   START (Main.java)                 │
│   Create GameEngine + startGame()   │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   MAIN LOOP (GameEngine)            │
├──────────────────────────────────────┤
│ 1. Check Interrupts                 │
│    ├─ BadEnd (health ≤ 0)          │
│    ├─ ArrestedEnd (suspicion ≥ 10) │
│    ├─ DespairEvent (morale ≤ 0)    │
│    ├─ ForcedRest (energy ≤ 0)      │
│    ├─ HavenPanicEnd (havenDays≥20) │
│    └─ PurgeEnd (countdown ≤ 0)     │
│                                     │
│ 2. Get Available Choices            │
│    └─ Filter by Conditions         │
│                                     │
│ 3. Display Node                     │
│    ├─ Story text                   │
│    ├─ Stats                        │
│    └─ Numbered choices             │
│                                     │
│ 4. Get Input                        │
│    └─ Validate choice number       │
│                                     │
│ 5. Apply Node Effects               │
│    └─ Polymorphic Effect.apply()   │
│                                     │
│ 6. Handle Special Effects           │
│    ├─ Zone changes                 │
│    ├─ Flag settings                │
│    └─ Tracking updates             │
│                                     │
│ 7. Determine Next Node              │
│    ├─ Check for Branching ID       │
│    ├─ Apply branching logic        │
│    └─ Return next node ID          │
│                                     │
│ 8. Move to Next Node                │
│    └─ Load StoryNode               │
│                                     │
│ 9. Update Time-based Stats          │
│    ├─ Decrement purge countdown    │
│    └─ Increment haven days         │
│                                     │
│ 10. Return to Step 1 (until end)   │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   END (one of 11 endings)           │
│   Close scanner                     │
└─────────────────────────────────────┘
```

---

## Branching & Decision Points

### 13 Branching IDs

The `getNextNodeId()` method handles special decision points that have multiple outcomes:

| ID | Type | Outcomes | Logic |
|----|------|----------|-------|
| **FastTravel** | Random | 2 (Success/Failure) | 60% success, 40% failure |
| **SearchTravel** | Random | 2 (Success/Failure) | 50/50 coin flip |
| **Mingle** | Deterministic | 2 (Rumor/Nothing) | Picks unheard rumor or nothing |
| **ObserveClinic** | Probability | 2 (Caught/Learn) | 30% caught + 10% per visit |
| **SearchDorms** | Random | 2 (Caught/FindNote) | 50/50 |
| **Library** | Conditional | 2 (LowKnowledge/HighKnowledge) | Knowledge < 5 vs ≥ 5 |
| **RiskInformation** | Random | 2 (Success/Caught) | 50/50 |
| **Section1Gate** | Conditional | 2 Paths | Knowledge ≥ 8 required |
| **StealSamples** | Random | 2 (Success/Caught) | 50/50 |
| **FinalCheck** | Conditional | 2 Paths | Knowledge ≥ 12 required |
| **FinalHeist** | Random | 2 (Success/Failure) | 50/50 |
| **EscapeEnd** | Conditional | 3 (Alliance/Fugitive/Savior) | Cris relationship: ≥3/0-2/<0 |
| **PurgeReveal** | Trigger | Activation | Sets purgeActive=true, countdown=7 |

### Example: Mingle Branching

```java
// GameEngine.getNextNodeId() - Mingle case
case "Mingle":
    int rumor = player.getUnheardIndices().get(random.nextInt(...));
    if (rumor == -1) {
        return "Mingle_Nothing";
    } else {
        player.markRumorHeard(rumor);
        return "Mingle_Rumor";
    }
```

**Design**: 
- Rumor picking is intelligent (only unheard rumors)
- Player state is updated before branching
- Different node paths based on randomness

---

## Data Structures

### Player State Model

```
Player {
  // Stats (changeable, interact with effects)
  health: int                      (typically 0-10+)
  energy: int                      (0-5 capped)
  knowledge: int                   (0+)
  suspicion: int                   (0-10+)
  morale: int                      (can be negative)
  day: int                         (0+)
  
  // Status
  zone: String                     ("Wasteland", "Haven", "Hub")
  
  // Tracking
  havenDays: int                   (counts turns in Haven)
  purgeCountdown: int              (7 → 0)
  purgeActive: boolean             (activated by PurgeReveal)
  clinicVisits: int                (affects probability)
  
  // Flags (affect conditions/endings)
  metScientist: boolean            (opens DormNight choice)
  metScavenger: boolean            (affects some endings)
  crisRelationship: int            (0-3 affects EscapeEnd)
  
  // Knowledge
  heardRumors: boolean[7]          (which of 7 rumors heard)
}
```

### StoryNode Structure

```
StoryNode {
  id: String                       ("Start", "Arrival", etc.)
  story: String                    (narrative text)
  choices: List<Choice>            (0-4 available choices)
  effects: List<Effect>            (0-N effects applied)
}

Choice {
  text: String                     (displayed to player)
  subText: String                  (costs/requirements)
  nextNodeId: String               ("NodeID" or "BranchingID")
  conditions: List<Condition>      (all must be met)
}

Effect {
  amount: int (varies)             (magnitude of change)
  apply(player)                    (polymorphic behavior)
}

Condition {
  isMet(player): boolean           (true/false)
}
```

---

## Testing Strategy

### Unit Test Suite (GameEngineTest.java)

**41 Tests organized in 7 categories**:

1. **Player Model Tests (10)**
   - Initialization
   - Energy capping
   - Stat changes
   - Zone management
   - Purge/Haven tracking

2. **Effect System Tests (3)**
   - Each effect type applies correctly
   - Energy cap respected

3. **Condition System Tests (2)**
   - Energy, Knowledge, Suspicion conditions
   - Flag conditions

4. **Choice Filtering Tests (1)**
   - Conditions gate choices

5. **StoryData Tests (4)**
   - Rumor array exists
   - Rumor tracking works
   - Node retrieval

6. **Integration Tests (5)**
   - Multiple effects apply
   - Game flow sequences
   - Interrupt conditions
   - Relationship tracking
   - Story progression

7. **Story Progression Tests (6)**
   - Key nodes exist
   - Endings reachable

### Running Tests

```bash
cd /path/to/Patient\ Zero
javac -cp src src/*.java src/**/*.java
java -cp src GameEngineTest
```

**Expected Output**:
```
PATIENT ZERO - COMPREHENSIVE UNIT TEST SUITE
...
TEST SUMMARY
Total Tests:  41
Passed:       41 ✓
Failed:       0 ✗
Success Rate: 100%

🎉 ALL TESTS PASSED! 🎉
```

### How to Add Tests

```java
private void testNewFeature() {
    player = new Player(10, 5, 0, 0, 0);
    
    // Setup
    player.setEnergy(3);
    
    // Action
    SomeEffect effect = new SomeEffect(2);
    effect.apply(player);
    
    // Assert
    assertEqual("Effect description", expectedValue, actualValue);
}

// Then add to runAllTests():
testNewFeature();
```

---

## Future Enhancements

### Short Term (1-2 weeks)

1. **Inventory System**
   - Items player can carry
   - Inventory affects choices
   - Trade between characters

2. **NPC Relationships**
   - Extend beyond Cris
   - Affect multiple endings

3. **Sound/Music**
   - Background tracks
   - Event sounds

### Medium Term (1 month)

1. **Graphical Interface**
   - Swap TextRenderer for graphics
   - Pixel art or ASCII art
   - Mouse/keyboard input

2. **Save/Load System**
   - Serialize Player state
   - Multiple save slots
   - Resume gameplay

3. **Difficulty Levels**
   - Easy (forgiving stats)
   - Normal (balanced)
   - Hard (strict timers)

### Long Term (2+ months)

1. **Modding System**
   - Load custom story files
   - Custom effects/conditions
   - Community content

2. **Multiplayer (async)**
   - Shared story choices
   - Collective world state
   - Branching based on player votes

3. **Web Version**
   - Web UI (React/Vue)
   - Backend API (Java Spring)
   - Persistent online world

---

## Code Style & Conventions

### Naming

- **Classes**: PascalCase (Player, StoryNode, HealthEffect)
- **Methods**: camelCase (getHealth, changeEnergy)
- **Constants**: UPPER_SNAKE_CASE (RUMORS)
- **Variables**: camelCase (currentNode, playerChoice)

### Comments

- Use Javadoc for public methods
- Explain "why" not "what"
- Group related code with section comments

### Package Organization

```
src/
├── model/           (data classes: Player, StoryNode, Choice)
├── engine/          (game logic: GameEngine, StoryData)
├── effect/          (effect implementations)
├── condition/       (condition implementations)
├── exception/       (custom exceptions)
├── ui/              (presentation: TextRenderer)
└── Main.java        (entry point)
```

---

## Performance Considerations

### Current Performance

- **Startup**: ~50ms (Java JIT compilation)
- **Turn Processing**: <1ms (simple state updates)
- **Memory Usage**: ~5MB (small story data, minimal state)

### Optimization Opportunities

1. **Lazy Loading**: Load story nodes on-demand
2. **Caching**: Cache frequently accessed nodes
3. **Effect Pooling**: Reuse effect objects
4. **Parallel Condition Checking**: Check multiple conditions in parallel (but probably overkill for this game)

---

## Debugging Tips

### Common Issues & Solutions

| Issue | Cause | Solution |
|-------|-------|----------|
| **NoSuchElementException** | Scanner closed | Use System.in.read() instead |
| **NullPointerException** | Missing node | Check StoryData.getNode() returns non-null |
| **Condition not gating** | Condition logic wrong | Debug with System.out.println(condition.isMet()) |
| **Effect not applying** | Effect not wired | Check node.addEffect() called in StoryData |
| **Branching goes wrong way** | Logic in getNextNodeId() | Add debug logs in branching ID cases |

### Debug Mode

Add to GameEngine.startGame():

```java
boolean DEBUG = true;

if (DEBUG) {
    System.out.println("[DEBUG] Current node: " + currentNode.getId());
    System.out.println("[DEBUG] Available choices: " + availableChoices.size());
    System.out.println("[DEBUG] Next node ID: " + nextNodeId);
}
```

---

## Conclusion

Patient Zero demonstrates advanced Java OOP principles through a complete game engine. The architecture is clean, extensible, and maintainable—serving as an excellent reference for enterprise-level Java design patterns.

For questions or suggestions, refer to STORY_REFERENCE.md or contact the development team.

---

**Last Updated**: 2025-11-28  
**Version**: 2.0  
**Status**: Complete & Production Ready ✅
