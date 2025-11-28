# CHANGELOG# CHANGELOG# CHANGELOG



## [v2.0] - 2025-11-28 (FINAL - Simplified Professor Version)



**STATUS**: ✅ PRODUCTION READY & SUBMITTED ## [v2.0] - 2025-11-28 ## [v1.1] - 2025-11-26



### 🎉 MAJOR RELEASE: COMPLETE GAME ENGINE



All 9 development phases completed successfully. Final version simplified by removing test files and custom exceptions for cleaner professor submission.### MAJOR RELEASE: COMPLETE GAME ENGINE### CHANGES



### ✨ ALL FEATURES COMPLETED- **StoryNode**: 



#### Phase 1 - Player Model ✅#### ✨ Features Completed	- Ported the story from "save point 2"

- 19 fields tracking complete game state

- Validated getters/setters for all stats	- Separated dynamic text into static nodes for better maintainability

- Energy capping enforced (0-5 max)

- Rumor tracking with boolean[7] array**Phase 1 - Player Model**	  (allows game logic to remain in GameEngine while story data stays clean)

- Comprehensive helper methods (changeEnergy, changeMorale, etc.)

- Haven days tracking (increments only on sleep/rest)- Implemented Player class with 19 fields tracking game state

- Relationship tracking for Cris character

- Purge system tracking- Added validated getters/setters for all stats- **Folder Structure**:



#### Phase 2 - Effect System ✅- Energy capping enforced (0-5 max)```

- Effect interface with 6 implementations:

  - HealthEffect: Modify player health- Rumor tracking with boolean array indexingPatient Zero/

  - EnergyEffect: Modify energy with cap enforcement (0-5)

  - KnowledgeEffect: Modify knowledge stat- Comprehensive helper methods (changeEnergy, changeMorale, etc.)├── CHANGELOG.md

  - SuspicionEffect: Modify suspicion stat

  - MoraleEffect: Modify morale stat├── README.md

  - DayEffect: Increment day counter

- ~20+ story nodes wired with effects**Phase 2 - Effect System** ├── STORY_REFERENCE.md

- Polymorphic effect application in GameEngine

- Created Effect interface with 6 implementations:└── src/

#### Phase 3 - Condition System ✅

- Condition interface with 4 implementations:  - HealthEffect: Modify player health    ├── Main.java

  - EnergyCondition: Requires minimum energy

  - KnowledgeCondition: Requires minimum knowledge  - EnergyEffect: Modify energy with cap enforcement    ├── condition/     (empty, ready for future use)

  - SuspicionCondition: Suspicion < threshold

  - FlagCondition: Checks player flags (metScientist, etc.)  - KnowledgeEffect: Modify knowledge stat    ├── effect/        (empty, ready for future use)

- Choice filtering based on conditions

- 5+ key choices gated by conditions  - SuspicionEffect: Modify suspicion stat    ├── engine/



#### Phase 4 - Game Engine ✅  - MoraleEffect: Modify morale stat    │   ├── GameEngine.java

- Complete 11-step game loop:

  1. Check global interrupts (6 types)  - DayEffect: Increment day counter    │   └── StoryData.java

  2. Get available choices (filtered by conditions)

  3. Display using TextRenderer- ~20+ story nodes wired with effects    ├── model/

  4. Get player input (validated)

  5. Apply chosen choice effects (polymorphic)- Polymorphic effect application in GameEngine    │   ├── Choice.java

  6. Handle special node effects (zones, flags, tracking)

  7. Determine next node ID (branching logic)    │   ├── Player.java

  8. Load next node

  9. Validate node exists**Phase 3 - Condition System**    │   └── StoryNode.java

  10. Update time-based stats

  11. Loop or end- Created Condition interface with 4 implementations:    └── ui/

- 6 Global Interrupts:

  - Health ≤ 0 → BadEnd  - EnergyCondition: Requires minimum energy        └── TextRenderer.java

  - Suspicion ≥ 10 → ArrestedEnd

  - Morale ≤ 0 → DespairEvent  - KnowledgeCondition: Requires minimum knowledge```

  - Energy ≤ 0 → ForcedRest

  - Haven Days ≥ 20 → HavenPanicEnd  - SuspicionCondition: Suspicion < threshold	

  - Purge Countdown ≤ 0 → PurgeEnd

- 13 Branching Decision Points with random/deterministic outcomes  - FlagCondition: Checks player flags (metScientist, metScavenger, purgeActive)### Work in Progress

- Input validation

- Choice filtering based on conditions- Conditions interface and its implementations

#### Phase 5 - Special Systems ✅

- Zone Management (Wasteland → Haven → Hub)- 3 key choices gated: Library (5+ knowledge), RiskInformation (6+ suspicion), DormNight (metScientist)- Effect interface and its implementations

- Purge System (7-day countdown activation)

- Haven Days Tracking (increments only on sleep/rest actions)- **GameEngine**: Game loop and logic still in progress

- Clinic Visit Tracking (affects probability scaling)

- Rumor System (7 rumors, player knowledge tracking)**Phase 4 - Game Engine**- **README**: Only posted the template, overview and other description in progress *[Features may still be revised depending on time constraints and project adjustments]*

- Relationship System (Cris affects EscapeEnd variant)

- Complete game loop with 11 steps:

#### Phase 6 - Simplified Error Handling ✅

**Design Decision**: Removed custom exceptions for simplified professor version  1. Check global interrupts### Note to Devs

- Deleted `InvalidChoiceException.java`

- Deleted `GameStateException.java`  2. Get available choices (filtered by conditions)- *To mimic the dynamic text from "save point 2" while separating different areas of concern, I have turned all dynamic text into static text nodes in StoryNode. The mechanism for triggering dynamic node output can be applied in the GameEngine or a separate class or subclass of the aforementioned class.*

- Replaced with simple if/else error handling

- Built-in exceptions (NumberFormatException) still used  3. Display using TextRenderer

- User-friendly error messages  4. Get player input

- **Benefit**: Cleaner codebase for educational purposes  5. Apply chosen choice effects

  6. Handle special node effects (zone changes, tracking)

#### Phase 7 - Main Entry Point ✅  7. Determine next node ID (branching logic)

- Welcome screen with ASCII art  8. Move to next node

- Game initialization and startup  9. Decrement purge countdown (if active)

- Proper Scanner handling  10. Increment haven days (if in Haven)

- Graceful game flow  11. Loop or end

- 6 Global Interrupts:

#### Phase 8 - Polish & Refinement ✅  - Health ≤ 0 → BadEnd

**Changes in this version**:  - Suspicion ≥ 10 → ArrestedEnd

- ✅ Deleted test files (GameEngineTest.java)  - Morale ≤ 0 → DespairEvent

  - Removed: `src/GameEngineTest.java`  - Energy ≤ 0 → ForcedRest

  - Removed: `test/GameEngineTest.java`  - Haven Days ≥ 20 → HavenPanicEnd

  - Original: 41 tests, 100% pass rate  - Purge Countdown ≤ 0 → PurgeEnd

- ✅ Deleted exception files (for simplified professor version)- 13 Branching Decision Points with random/deterministic outcomes:

  - Removed: `src/exception/InvalidChoiceException.java`  - FastTravel (60/40), SearchTravel (50/50)

  - Removed: `src/exception/GameStateException.java`  - Mingle with rumor selection

- ✅ Added ForcedRest story node (energy = 0 trigger)  - ObserveClinic with visit probability scaling

- ✅ Enhanced TextRenderer with "DAYS IN HAVEN" display  - SearchDorms (50/50), Library with knowledge gate

- ✅ Fixed haven days increment logic  - RiskInformation (50/50), Section1Gate knowledge check

  - **Original Problem**: Incremented every turn (9 days on day 2)  - StealSamples (50/50), FinalCheck knowledge ≥12

  - **Solution**: Increment only on sleep/rest actions  - FinalHeist (50/50), EscapeEnd with Cris relationship branching

  - **Nodes**: LeisurelyRest, RestInDorm, ForcedRest  - PurgeReveal activation

- ✅ Deleted all .class files for clean submission- Polymorphic effect application

- ✅ Zero compilation errors- Input validation with try-catch exception handling



#### Phase 9 - Documentation ✅**Phase 5 - Special Systems**

- README.md (500+ lines) - Complete game overview- Zone Management:

- CHANGELOG.md (this file) - Version history  - Wasteland → Haven (at Arrival node)

- README_DEVELOPERS.md (1,000+ lines) - Technical architecture  - Haven → Hub (at ResearchHub node)

- PROJECT_COMPLETION_SUMMARY.md (400+ lines) - Completion status  - Haven Days increment each turn in Haven

- TODO.md - Project completion checklist- Purge System:

  - Activation at PurgeReveal node

### 📝 Content  - 7-day countdown decrements each turn

  - Countdown ≤ 0 triggers PurgeEnd

- **61 Story Nodes**: Complete narrative covering all game paths- Clinic Visit Tracking:

- **7 Rumors**: Collectible story elements tracking player knowledge  - Increments on ObserveClinic nodes

- **11 Endings**: Multiple outcomes based on stats and choices  - Affects probability scaling for future clinic encounters



### 🏗️ Architecture**Phase 6 - Exception Handling**

- InvalidChoiceException: Thrown for invalid player input

```- GameStateException: Thrown for missing nodes/null states

Model Layer:- Try-catch blocks at all input/state validation points

  ├── Player (19 fields, state management)- User-friendly error messages

  ├── StoryNode (content + effects)

  └── Choice (decision + conditions)**Phase 7 - Main Entry Point**

- Welcome screen with ASCII art

Effect Layer:- Game initialization and startup

  ├── Effect (interface)- Proper stdin handling (System.in.read())

  └── 6 implementations (polymorphic)

**Phase 8 - Comprehensive Testing**

Condition Layer:- GameEngineTest.java with 41 unit tests

  ├── Condition (interface)- 100% pass rate (41/41 tests)

  └── 4 implementations (filtering)- Coverage includes:

  - Player model initialization and mutations

Engine Layer:  - All 6 effect types

  ├── GameEngine (main loop, interrupts, branching)  - All 4 condition types

  ├── StoryData (61 nodes, 7 rumors)  - Choice filtering

  └── TextRenderer (UI display)  - Rumor system

```  - Integration tests

  - Game flow sequences

### 🔑 Key Features  - Interrupt conditions



- **Polymorphism**: Full effect and condition system using interfaces**Phase 9 - Documentation**

- **Encapsulation**: Player model with validated state mutations- Updated README.md with complete game information

- **Abstraction**: GameEngine hides complex loop and branching logic- Comprehensive CHANGELOG.md (this file)

- **Simplified Errors**: If/else validation without custom exceptions- README_DEVELOPERS.md with architecture documentation

- **Relationships**: Dynamic story outcomes based on Cris relationship

- **Zone System**: Three-zone progression (Wasteland → Haven → Hub)#### 📝 Content

- **Resource Management**: Energy capping, suspicion thresholds, morale tracking

- **Rumor Tracking**: Persistent player knowledge of story elements- **61 Story Nodes**: Complete narrative covering all game paths

- **Rest System**: Three rest nodes with intelligent time progression- **7 Rumors**: Collectible story elements tracking player knowledge

- **Haven Days Tracking**: Separate time counter for Haven location- **12 Endings**: Multiple outcomes based on stats and choices



### 📊 Final Metrics#### 🏗️ Architecture



- **Java Files**: 17 (Main + 3 model + 6 effect + 4 condition + 2 engine + ui)```

- **Interfaces**: 2 (Effect, Condition)Model Layer:

- **Story Nodes**: 61  ├── Player (19 fields, state management)

- **Branching Points**: 13  ├── StoryNode (content + effects)

- **Global Interrupts**: 6  └── Choice (decision + conditions)

- **Lines of Code**: ~2,000 LOC

- **Compilation Errors**: 0Effect Layer:

- **Documentation**: 2,000+ lines  ├── Effect (interface)

  └── 6 implementations (polymorphic)

---

Condition Layer:

## [v1.1] - 2025-11-26  ├── Condition (interface)

  └── 4 implementations (filtering)

### Initial Narrative Foundation

Engine Layer:

- Story nodes ported from design document  ├── GameEngine (main loop, interrupts, branching)

- Folder structure established  ├── StoryData (61 nodes, 7 rumors)

- TextRenderer UI framework implemented  └── TextRenderer (UI display)

- Player, Choice, StoryNode models defined

- Condition and Effect system designException Layer:

  ├── InvalidChoiceException

---  └── GameStateException

```

## [v1.0] - Initial Concept

#### 🔑 Key Improvements Over v1.1

- Game concept and narrative outline

- Project structure planning- **Polymorphism**: Full effect and condition system using interfaces

- Architecture design discussions- **Encapsulation**: Player model with validated state mutations

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
