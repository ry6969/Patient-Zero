# CHANGELOG# CHANGELOG



## [v2.0] - 2025-11-28## [v1.1] - 2025-11-26



### MAJOR RELEASE: COMPLETE GAME ENGINE### CHANGES

- **StoryNode**: 

#### ✨ Features Completed	- Ported the story from "save point 2"

	- Separated dynamic text into static nodes for better maintainability

**Phase 1 - Player Model**	  (allows game logic to remain in GameEngine while story data stays clean)

- Implemented Player class with 19 fields tracking game state

- Added validated getters/setters for all stats- **Folder Structure**:

- Energy capping enforced (0-5 max)```

- Rumor tracking with boolean array indexingPatient Zero/

- Comprehensive helper methods (changeEnergy, changeMorale, etc.)├── CHANGELOG.md

├── README.md

**Phase 2 - Effect System** ├── STORY_REFERENCE.md

- Created Effect interface with 6 implementations:└── src/

  - HealthEffect: Modify player health    ├── Main.java

  - EnergyEffect: Modify energy with cap enforcement    ├── condition/     (empty, ready for future use)

  - KnowledgeEffect: Modify knowledge stat    ├── effect/        (empty, ready for future use)

  - SuspicionEffect: Modify suspicion stat    ├── engine/

  - MoraleEffect: Modify morale stat    │   ├── GameEngine.java

  - DayEffect: Increment day counter    │   └── StoryData.java

- ~20+ story nodes wired with effects    ├── model/

- Polymorphic effect application in GameEngine    │   ├── Choice.java

    │   ├── Player.java

**Phase 3 - Condition System**    │   └── StoryNode.java

- Created Condition interface with 4 implementations:    └── ui/

  - EnergyCondition: Requires minimum energy        └── TextRenderer.java

  - KnowledgeCondition: Requires minimum knowledge```

  - SuspicionCondition: Suspicion < threshold	

  - FlagCondition: Checks player flags (metScientist, metScavenger, purgeActive)### Work in Progress

- Choice filtering based on conditions- Conditions interface and its implementations

- 3 key choices gated: Library (5+ knowledge), RiskInformation (6+ suspicion), DormNight (metScientist)- Effect interface and its implementations

- **GameEngine**: Game loop and logic still in progress

**Phase 4 - Game Engine**- **README**: Only posted the template, overview and other description in progress *[Features may still be revised depending on time constraints and project adjustments]*

- Complete game loop with 11 steps:

  1. Check global interrupts### Note to Devs

  2. Get available choices (filtered by conditions)- *To mimic the dynamic text from "save point 2" while separating different areas of concern, I have turned all dynamic text into static text nodes in StoryNode. The mechanism for triggering dynamic node output can be applied in the GameEngine or a separate class or subclass of the aforementioned class.*

  3. Display using TextRenderer
  4. Get player input
  5. Apply chosen choice effects
  6. Handle special node effects (zone changes, tracking)
  7. Determine next node ID (branching logic)
  8. Move to next node
  9. Decrement purge countdown (if active)
  10. Increment haven days (if in Haven)
  11. Loop or end
- 6 Global Interrupts:
  - Health ≤ 0 → BadEnd
  - Suspicion ≥ 10 → ArrestedEnd
  - Morale ≤ 0 → DespairEvent
  - Energy ≤ 0 → ForcedRest
  - Haven Days ≥ 20 → HavenPanicEnd
  - Purge Countdown ≤ 0 → PurgeEnd
- 13 Branching Decision Points with random/deterministic outcomes:
  - FastTravel (60/40), SearchTravel (50/50)
  - Mingle with rumor selection
  - ObserveClinic with visit probability scaling
  - SearchDorms (50/50), Library with knowledge gate
  - RiskInformation (50/50), Section1Gate knowledge check
  - StealSamples (50/50), FinalCheck knowledge ≥12
  - FinalHeist (50/50), EscapeEnd with Cris relationship branching
  - PurgeReveal activation
- Polymorphic effect application
- Input validation with try-catch exception handling

**Phase 5 - Special Systems**
- Zone Management:
  - Wasteland → Haven (at Arrival node)
  - Haven → Hub (at ResearchHub node)
  - Haven Days increment each turn in Haven
- Purge System:
  - Activation at PurgeReveal node
  - 7-day countdown decrements each turn
  - Countdown ≤ 0 triggers PurgeEnd
- Clinic Visit Tracking:
  - Increments on ObserveClinic nodes
  - Affects probability scaling for future clinic encounters

**Phase 6 - Exception Handling**
- InvalidChoiceException: Thrown for invalid player input
- GameStateException: Thrown for missing nodes/null states
- Try-catch blocks at all input/state validation points
- User-friendly error messages

**Phase 7 - Main Entry Point**
- Welcome screen with ASCII art
- Game initialization and startup
- Proper stdin handling (System.in.read())

**Phase 8 - Comprehensive Testing**
- GameEngineTest.java with 41 unit tests
- 100% pass rate (41/41 tests)
- Coverage includes:
  - Player model initialization and mutations
  - All 6 effect types
  - All 4 condition types
  - Choice filtering
  - Rumor system
  - Integration tests
  - Game flow sequences
  - Interrupt conditions

**Phase 9 - Documentation**
- Updated README.md with complete game information
- Comprehensive CHANGELOG.md (this file)
- README_DEVELOPERS.md with architecture documentation

#### 📝 Content

- **61 Story Nodes**: Complete narrative covering all game paths
- **7 Rumors**: Collectible story elements tracking player knowledge
- **12 Endings**: Multiple outcomes based on stats and choices

#### 🏗️ Architecture

```
Model Layer:
  ├── Player (19 fields, state management)
  ├── StoryNode (content + effects)
  └── Choice (decision + conditions)

Effect Layer:
  ├── Effect (interface)
  └── 6 implementations (polymorphic)

Condition Layer:
  ├── Condition (interface)
  └── 4 implementations (filtering)

Engine Layer:
  ├── GameEngine (main loop, interrupts, branching)
  ├── StoryData (61 nodes, 7 rumors)
  └── TextRenderer (UI display)

Exception Layer:
  ├── InvalidChoiceException
  └── GameStateException
```

#### 🔑 Key Improvements Over v1.1

- **Polymorphism**: Full effect and condition system using interfaces
- **Encapsulation**: Player model with validated state mutations
- **Abstraction**: GameEngine hides complex loop and branching logic
- **Exception Handling**: Custom exceptions with try-catch blocks
- **Testing**: 41-test unit test suite with 100% pass rate
- **Relationships**: Dynamic story outcomes based on Cris relationship
- **Zone System**: Three-zone progression (Wasteland → Haven → Hub)
- **Resource Management**: Energy capping, suspicion thresholds, morale tracking
- **Rumor Tracking**: Persistent player knowledge of story elements

#### 📊 Metrics

- **Classes**: 17 (Player, 3 model + GameEngine/StoryData/TextRenderer, 7 effect, 4 condition, 2 exception, Main)
- **Interfaces**: 2 (Effect, Condition)
- **Story Nodes**: 61
- **Branching Points**: 13
- **Global Interrupts**: 6
- **Test Cases**: 41 (100% pass rate)
- **Lines of Code**: ~3,000+ (engine, models, effects, conditions)

---

## [v1.1] - 2025-11-26

### Initial Narrative Foundation

- Story nodes ported from "save point 2"
- Folder structure established
- TextRenderer UI framework implemented
- Player, Choice, StoryNode models defined
- Placeholder for effects and conditions systems

---

## [v1.0] - Initial Concept

- Game concept and narrative outline
- Project structure planning
- Architecture design discussions
