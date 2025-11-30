# PATIENT ZERO

<h1 align = "center">PATIENT ZERO</h1>

<h3 align = "center">A text-based console RPG demonstrating Object-Oriented Programming principles</h3>

## 🎮 Overview

Patient Zero is a choice-driven narrative game demonstrating advanced Object-Oriented Programming principles through a complex game engine. Players experience a richly branching story with multiple endings, strategic resource management (health, energy, knowledge, suspicion), zone progression, and dynamic relationships affecting outcomes.

## 🎯 Game Premise

You are Patient Zero. In a world ravaged by plague, you arrive at Haven—a hidden sanctuary. Uncover the truth about your immunity, navigate political factions, and decide humanity's fate. Will you expose the secret, seize power, find comfort, or escape?

## ✨ Key Features

- **11 Distinct Endings**: Different outcomes based on stats, relationships, and choices
- **13 Branching Decision Points**: Random and deterministic outcomes affecting story progression
- **7 Collectable Rumors**: Story elements with persistent player knowledge tracking via boolean arrays
- **Resource Management**: Health, Energy, Knowledge, Suspicion, Morale dynamically affect gameplay
- **Zone Progression**: Travel from Wasteland → Haven → Research Hub with zone-specific mechanics
- **Relationship Dynamics**: Cris relationship determines escape ending variant
- **Global Interrupts**: 6 interrupt conditions override normal flow (health, suspicion, morale, energy, haven days, purge countdown)
- **Conditional Choices**: Choices become available/unavailable based on player stats
- **Purge System**: 7-day countdown creates time pressure for critical decisions
- **Haven Days Tracking**: Day counter specifically for time spent in Haven (increments only on sleep/rest)

## 📂 Project Structure

```
📂 Patient Zero/
├── 📂 src/
│   ├── Main.java                    # Game entry point
│   ├── 📂 model/
│   │   ├── Player.java              # Game state: 19 fields, 40+ methods
│   │   ├── StoryNode.java           # Story content with effect list
│   │   └── Choice.java              # Player choices with condition list
│   ├── 📂 engine/
│   │   ├── GameEngine.java          # Core game loop: 11 steps, 6 interrupts, 13 branching IDs
│   │   └── StoryData.java           # 61 story nodes, 7 rumors, effect wiring
│   ├── 📂 effect/
│   │   ├── Effect.java              # Interface
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

## 🚀 How to Compile and Run

### Compile:
```bash
cd "/home/ry/Documents/School Projects/Patient Zero"
javac -cp src src/*.java src/**/*.java
```

### Run Game:
```bash
java -cp src Main
```

## 🎮 How to Play

1. **Start** the game by running `Main`
2. **Make choices** by entering the choice number (1-9 typically)
3. **Manage stats**:
   - **Health**: Drop to ≤0 = BadEnd
   - **Energy**: Required for activities (0-5 capped). When energy hits 0, triggers ForcedRest
   - **Knowledge**: Unlock choices at 5+ and 7+ thresholds
   - **Suspicion**: Hit 10 = ArrestedEnd
   - **Morale**: Drop to ≤0 = DespairEvent
   - **Days in Haven**: Track time spent resting in Haven (increments only on sleep/rest actions)
4. **Collect Rumors**: Encounter story elements that add to your knowledge
5. **Build Relationships**: Interacting with Cris affects escape ending outcome
6. **Reach Endings**: Decisions and stats determine which of 11 endings you get

## 🛌 Rest System

The game has three rest nodes:
- **LeisurelyRest**: Casual resting in Haven (restores morale, increments haven days)
- **RestInDorm**: Intentional sleep in Haven dorm (advances day, restores energy, increments haven days)
- **ForcedRest**: Automatic rest when energy hits 0 (emergency collapse, advances day, restores energy, increments haven days)

**Important**: Haven days counter only increments on actual sleep/rest actions, not on regular explorations. Regular actions (Mingle, ObserveClinic, SearchDorms) do NOT count as days passing.

## 🔐 Object-Oriented Principles Demonstrated

### Encapsulation
**Player Model**: Private fields (health, energy, knowledge, etc.) with validated getters/setters. Energy changes enforce cap (max 5, min 0) via `changeEnergy()` method. Player state is isolated and protected from invalid modifications.

**StoryData**: Static `RUMORS` array contains narrative text; Player tracks only which rumors are heard (boolean array). Content and state are separate.

### Abstraction
**Effect System**: Effect interface with `apply(Player)` method. Game engine calls effects polymorphically without knowing if it's HealthEffect, EnergyEffect, etc. Complexity of stat changes is hidden.

**Condition System**: Condition interface with `isMet(Player)` method. Choice filtering abstracted from game logic.

**GameEngine**: Hides complexity of 13 branching points, 6 interrupts, and game loop mechanics. Main only calls `startGame()`.

**TextRenderer**: Abstracts all formatting and display logic. GameEngine only calls `printGameScreen()` and `clearScreen()`.

### Inheritance
**Effect Interface → 6 Implementations**: Each effect (Health, Energy, Knowledge, Suspicion, Morale, Day) inherits from Effect and implements `apply(Player)`.

**Condition Interface → 4 Implementations**: Energy, Knowledge, Suspicion, Flag conditions inherit from Condition and implement `isMet(Player)`.

Enables extensibility—new effects/conditions can be added without modifying existing code.

### Polymorphism
**Effect.apply()**: Called polymorphically in `GameEngine.applyEffects()`. Actual behavior depends on concrete type at runtime.

```java
for (Effect effect : node.getEffects()) {
    effect.apply(player);  // Polymorphic call—exact type determined at runtime
}
```

**Condition.isMet()**: Choice filtering uses polymorphic condition checking.

```java
if (choice.isAvailable(player)) {
    // isMet() is polymorphically called on each condition
}
```

### Arrays
**Rumor Tracking**: `Player.heardRumors` is a boolean[7] tracking which rumors player has learned. Efficient O(1) lookup for rumor availability.

**Story Rumors**: `StoryData.RUMORS` is String[7] storing rumor text static to the story narrative.

## 🎯 Game Endings (11 Total)

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

## 💾 Code Metrics

- **Total Files**: 18 Java files
- **Total Lines of Code**: ~2,000+ LOC
- **Game Nodes**: 61 unique story nodes
- **Branching Points**: 13 decision branches
- **Global Interrupts**: 6 interrupt conditions
- **Effects**: 6 effect types (polymorphic)
- **Conditions**: 4 condition types (polymorphic)

## ⚙️ Game Loop Architecture

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

## 👥 Contributors

<table>
<tr>
<th> Name </th>
<th> Role </th>
</tr>
<tr>
<td><strong>Bueno, Basty A.</strong></td>
<td>Project Developer</td>
</tr>
<tr>
<td><strong>Canalita, Harry D.</strong></td>
<td>Project Developer</td>
</tr>
<tr>
<td><strong>Manalo, Cris Julian V.</strong></td>
<td>Project Developer</td>
</tr>
</table>

## 📝 Version

**Current Version**: 2.0  
**Last Updated**: November 28, 2025

---

**DISCLAIMER**  
This project is provided for educational purposes. Students should use it as a reference guide, not copy it verbatim.
