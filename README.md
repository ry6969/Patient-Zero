# PATIENT ZERO<h1 align = "center">PATIENT ZERO</h1>

<h3 align = "center">An text-based console RPG.</h3>

## Text-Based Strategic Adventure Game<p align = "center">

<b>CS 2103 </b> <br/>

A turn-based Java console RPG where players navigate a post-apocalyptic world, gather information, manage resources, and make critical choices that determine their fate.Bueno, Basty A. <br/>

Canalita, Harry D. <br/>

**CS 2103**  Manalo, Cris Julian V. <br/>

Bueno, Basty A.  </p>

Canalita, Harry D.  

Manalo, Cris Julian V.## ‧₊˚ ┊ Overview

NotesDump is a console-based Java application allowing users to manage note entries directly through the terminal.

## ‧₊˚ ┊ Overview<br/><br/>

It demonstrates the practical use of Object-oriented Programming (OOP) concepts such as encapsulation, inheritance, polymorphism, and abstraction, alongside proper file handling and modular design.

Patient Zero is a choice-driven narrative game demonstrating advanced Object-oriented Programming principles through a complex game engine. Players experience a richly branching story with multiple endings, strategic resource management (health, energy, knowledge, suspicion), zone progression, and dynamic relationships affecting outcomes.<br/>

### Users can:

The game showcases:✏️ Add a new entry<br/>

- **Polymorphism**: Effect and Condition interfaces with 6 and 4 implementations respectively📔 View all entries<br/>

- **Encapsulation**: Player model encapsulates game state with validated getters/setters✍🏻 Modify or delete specific entries<br/>

- **Abstraction**: GameEngine abstracts game loop complexity; StoryData abstracts narrative content📑 Insert an entry at a certain index

- **Exception Handling**: Custom exceptions for invalid input and game state violations

- **Array Usage**: Rumor system tracks story knowledge via boolean arrays### Note Entry Storage

- **Console I/O**: Text-based UI with formatted display and user input validation💾 All entries are stored in a plain text file.



## ‧₊˚ ┊ Game Premise## ‧₊˚ ┊ Project Structure

```

You are Patient Zero. In a world ravaged by plague, you arrive at Haven—a hidden sanctuary. Uncover the truth about your immunity, navigate political factions, and decide humanity's fate. Will you expose the secret, seize power, find comfort, or escape?📂 src/

└── 📂 diaryapp/

## ‧₊˚ ┊ Key Features    ├── ☕ Main.java          

    ├── ☕ Diary.java

✨ **11 Distinct Endings**: Different outcomes based on stats, relationships, and choices      └── ☕ FileHandler.java

🎲 **13 Branching Decision Points**: Random and deterministic outcomes affecting story progression  ```

📚 **Rumor System**: 7 collectable story elements with persistent player knowledge tracking  - `Main.java` - Entry point of the program, containing the menu and handles user interactions.

⚡ **Resource Management**: Health, Energy, Knowledge, Suspicion, Morale dynamically affect gameplay  - `Diary.java` - Handles the diary operations (CRUD)

🏘️ **Zone Progression**: Travel from Wasteland → Haven → Research Hub with zone-specific mechanics  - `FileHandler.java` - Handles file creation, reading, writing, and appending.

💔 **Relationship Dynamics**: Cris relationship determines escape ending variant  ### How to Run the Program

🚨 **Global Interrupts**: 6 interrupt conditions override normal flow (health, suspicion, morale, energy, haven days, purge countdown)  Open your terminal in the `src/` folder and run:

🎯 **Conditional Choices**: Choices become available/unavailable based on player stats  ```

⚠️ **Purge System**: 7-day countdown creates time pressure for critical decisionsjavac diaryapp/*.java

```

## ‧₊˚ ┊ How to Compile and RunRun the program using:

```

### Compile:java diaryapp.Main

```bash```

cd /path/to/Patient\ Zero## ‧₊˚ ┊ Features

javac -cp src src/*.java src/**/*.java1. **Add Entry.** Create a new diary entry with timestamp.

```2. **View Entries.** Display all saved entries with numbering.

3. **Modify Entry.** Edit any existing note by selecting its number

### Run Game:4. **Delete Entry.** Remove a specific entry permanently.

```bash5. **Insert Entry.** Add a note at any position in the list.

java -cp src Main

```## ‧₊˚ ┊ Object-oriented Principles

### 💊 Encapsulation

### Run Tests:Encapsulation was applied through class design and private fields. For instance, in `Diary`, the `filepath` variable is private and can only be accessed through the class's own methods such as, `addEntry()`, `viewEntries()`, etc.

```bash

java -cp src GameEngineTestThis ensures that data and operations on it are bundled together and protected from unauthorized modification.

```

### 💡 Abstraction

## ‧₊˚ ┊ Project StructureAbstraction was implemented when the `FileHandler` class abstracts file operations like reading, writing, and appending. The `Diary` class doesn't need to know how file handling works, for it just calls methods like `FileHandler.appendLine()` or `FileHandler.readAllLines()`.



```This hides low-level complexity and keeps the main logic clean.

📂 Patient Zero/

├── 📂 src/### 🧬 Inheritance

│   ├── Main.java                    # Game entry pointInheritance was not heavily used in the program, however, its structure is ready for extension.

│   ├── GameEngineTest.java          # 41-test unit test suite (100% pass rate)For instance, if a subclass like for diary is to be created, it could inherit from `Diary` and override some methods like `addEntry()` and `viewEntries()`.

│   ├── 📂 model/

│   │   ├── Player.java              # Game state: 19 fields, 40+ methodsThis shows potential for code reuse and expansion without rewriting existing logic.

│   │   ├── StoryNode.java           # Story content with effect list

│   │   └── Choice.java              # Player choices with condition list### 🎭 Polymorphism

│   ├── 📂 engine/The `switch` expression in `Main.java` demonstrates method-level polymorphism, the same action (`diary.[action]`) calls different behaviors depending on user choice.

│   │   ├── GameEngine.java          # Core game loop: 11 steps, 6 interrupts, 13 branching IDs

│   │   └── StoryData.java           # 61 story nodes, 7 rumors, effect wiringAlso, if a subclass of `Diary` overrides a method, for instance `addEntry()`, the program could dynamically call the correct version at runtime, enabling flexible behavior.

│   ├── 📂 effect/

│   │   ├── Effect.java              # Interface## ‧₊˚ ┊ Example Output

│   │   ├── HealthEffect.java        # -/+ health```

│   │   ├── EnergyEffect.java        # -/+ energy (capped 0-5)--- NOTES DIARY ---

│   │   ├── KnowledgeEffect.java     # -/+ knowledge1. Add an entry

│   │   ├── SuspicionEffect.java     # -/+ suspicion2. View all entries

│   │   ├── MoraleEffect.java        # -/+ morale3. Modify an entry

│   │   └── DayEffect.java           # Increment day counter4. Delete an entry

│   ├── 📂 condition/5. Insert an entry

│   │   ├── Condition.java           # Interface6. Exit

│   │   ├── EnergyCondition.java     # Requires minimum energyEnter your choice: 1

│   │   ├── KnowledgeCondition.java  # Requires minimum knowledgeEnter your note. (Press x to cancel): Had coffee while coding!

│   │   ├── SuspicionCondition.java  # Requires suspicion < limitEntry added.

│   │   └── FlagCondition.java       # Checks player flagsPress Enter to continue...

│   ├── 📂 exception/

│   │   ├── InvalidChoiceException.java  # Invalid input/out-of-range```

│   │   └── GameStateException.java      # Missing nodes, null states

│   └── 📂 ui/##  ‧₊˚ ┊ notes.txt Snippet

│       └── TextRenderer.java        # Formatted console display```

├── 📄 README.md                     # This file2025-11-09 11:10:16 | Woah-oh bakit ba ganito?

├── 📄 CHANGELOG.md                  # Version history2025-11-09 11:10:27 | Palaging bumabalik sa'yo?

└── 📄 README_DEVELOPERS.md          # Architecture & design decisions2025-11-09 11:10:43 | Woah-oh, litong-lito

```2025-11-09 11:10:55 | na ang puso ko~~~

2025-11-09 11:11:18 | Paikot-ikot, nahihilo

## ‧₊˚ ┊ How to Play2025-11-09 11:11:26 | Gulong-gulo ang isipan ko

2025-11-09 11:11:51 | Handa ng magpasalo

1. **Start** the game by running `Main`2025-11-09 11:11:58 | Basta ikaw ang kasalo

2. **Make choices** by entering the choice number (1-4 typically)2025-11-09 11:12:08 | Hanggang sa dulo ng mundo

3. **Manage stats**:2025-11-09 11:12:22 | Paikot-ikot na lang sa'yo

   - **Health**: Drop to ≤0 = BadEnd2025-11-09 11:12:33 | Sana'y pakinggan mo 'ko

   - **Energy**: Required for activities (0-5 capped)2025-11-09 11:12:54 | Dahil handa na akong

   - **Knowledge**: Unlock choices at 5+ and 12+ thresholds2025-11-09 11:13:00 | magpasalo

   - **Suspicion**: Hit 10 = ArrestedEnd2025-11-09 11:13:11 | Basta ikaw ang kasalo <3

   - **Morale**: Drop to ≤0 = DespairEvent```



4. **Collect Rumors**: Encounter story elements that add to your knowledge##  ‧₊˚ ┊ Contributors

5. **Build Relationships**: Interacting with Cris affects escape ending outcome

6. **Reach Endings**: Decisions and stats determine which of 11 endings you get<table>

<tr>

## ‧₊˚ ┊ Object-Oriented Principles Demonstrated    <th> &nbsp; </th>

    <th> Name </th>

### 🔐 Encapsulation    <th> Role </th>

**Player Model**: Private fields (health, energy, knowledge, etc.) with validated getters/setters. Energy changes enforce cap (max 5, min 0) via `changeEnergy()` method. Player state is isolated and protected from invalid modifications.</tr>

<tr>

**StoryData**: Static `RUMORS` array contains narrative text; Player tracks only which rumors are heard (boolean array). Content and state are separate.    <td><img src="static/marieemoiselle.JPG" width="100" height="100"> </td>

    <td><strong>Fatima Marie P. Agdon, MSCS</strong> <br/>

### 🎯 Abstraction    <a href="https://github.com/marieemoiselle" target="_blank">

**Effect System**: Effects interface with `apply(Player)` method. Game engine calls effects polymorphically without knowing if it's HealthEffect, EnergyEffect, etc. Complexity of stat changes is hidden.    <img src="https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=pink" alt="marieemoiselle's GitHub">

        </a>

**Condition System**: Conditions interface with `isMet(Player)` method. Choice filtering abstracted from game logic.    </td>

    <td>Project Leader/System Architect</td>

**GameEngine**: Hides the complexity of 13 branching points, 6 interrupts, and game loop mechanics. Main only calls `startGame()`.</tr>

<tr>

### 🧬 Inheritance    <td><img src="static/jeisquared.jpg" width="100" height="100"> </td>

**Effect Interface → 6 Implementations**: Each effect (Health, Energy, Knowledge, Suspicion, Morale, Day) inherits from Effect and implements `apply(Player)`.    <td><strong>Jei Q. Pastrana, MSIT</strong> <br/>

    <a href="https://github.com/jeisquaredd" target="_blank">

**Condition Interface → 4 Implementations**: Energy, Knowledge, Suspicion, Flag conditions inherit from Condition and implement `isMet(Player)`.    <img src="https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=blue" alt="jeisquaredd's GitHub">

        </a>

Enables extensibility—new effects/conditions can be added without modifying existing code.    </td>

    <td>File Handling Specialist</td>

### 🎭 Polymorphism</tr>

**Effect.apply()**: Called polymorphically in `GameEngine.applyEffects()`. Actual behavior depends on concrete type at runtime.<tr>

    <td><img src="static/renzmarrion.jpg" width="100" height="100"> </td>

```java    <td><strong>Renz Marrion O. Perez</strong> <br/>

for (Effect effect : node.getEffects()) {    <a href="https://github.com/digZy030509" target="_blank">

    effect.apply(player);  // Polymorphic call    <img src="https://img.shields.io/badge/GitHub-%23121011.svg?logo=github&logoColor=green" alt="digZy030509's GitHub">

}        </a>

```    </td>

    <td>Feature Developer</td>

**Condition.isMet()**: Choice filtering uses polymorphic condition checking.</tr>

</table>

### ⚠️ Exception Handling

**InvalidChoiceException**: Thrown for out-of-range input or invalid choice format. Caught and displayed to user with friendly message.##  ‧₊˚ ┊ Acknowledgment

We sincerely express our gratitude to our instructor for the guidance and support provided throughout the completion of this project. We also extend our appreciation to our classmates and peers for their cooperation and encouragement during the development process.

**GameStateException**: Thrown when story node is not found. Prevents null pointer exceptions.

---

```java<small>

if (currentNode == null) {<b>DISCLAIMER</b><br/>

    throw new GameStateException("Node not found: " + nextNodeId);This project and its contents are provided for example and learning purposes only. Students are encouraged to use it as a reference and not copy it in its entirety.</small>
}
```

### 📊 Arrays
**Rumor Tracking**: `Player.heardRumors` is a boolean[7] tracking which rumors player has learned. Efficient O(1) lookup for rumor availability.

**Story Rumors**: `StoryData.RUMORS` is String[7] storing rumor text static to story.

## ‧₊˚ ┊ Testing

Run the comprehensive test suite:

```bash
java -cp src GameEngineTest
```

**Test Coverage**: 41 unit tests covering:
- ✅ Player model initialization and stat changes
- ✅ Energy capping enforcement (0-5)
- ✅ All 6 effect types (Health, Energy, Knowledge, Suspicion, Morale, Day)
- ✅ All 4 condition types (Energy, Knowledge, Suspicion, Flag)
- ✅ Choice filtering by conditions
- ✅ Rumor system and tracking
- ✅ Multiple effect application
- ✅ Game flow sequences
- ✅ Interrupt conditions (6 types)
- ✅ Relationship tracking
- ✅ Story node retrieval

**Result**: 100% pass rate (41/41 tests passing)

## ‧₊˚ ┊ Game Endings

The game has 11 distinct endings determined by your choices and stats:

1. **BadEnd**: Health ≤ 0 (starvation/exhaustion)
2. **ArrestedEnd**: Suspicion ≥ 10 (discovered by authorities)
3. **DespairEvent**: Morale ≤ 0 (leads to HavenIntro or ResearchHub)
4. **HavenPanicEnd**: Haven Days ≥ 20 (confinement madness)
5. **PurgeEnd**: Purge Countdown ≤ 0 (military execution)
6. **ComfortBadEnd**: Choosing comfort and stability
7. **Collapse**: Authority system fails
8. **EscapeEnd_Alliance**: Cris relationship ≥ 3 (united escape)
9. **EscapeEnd_Fugitive**: Cris relationship 0-2 (solo escape)
10. **EscapeEnd_Savior**: Cris relationship < 0 (sacrifice play)
11. **ExposeEnd**: Reveal truth to humanity
12. **MilEnd**: Military takes control

## ‧₊˚ ┊ Acknowledgments

We express gratitude to our instructor for guidance and support throughout project completion, and to classmates for cooperation and encouragement.

---

**DISCLAIMER**  
This project is provided for educational purposes. Students should use it as a reference guide, not copy it verbatim.
