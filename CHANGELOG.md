# CHANGELOG

## [v2.2] - 2025-12-01 (Critical Bug Fixes & Energy Condition System)

### 🐛 BUG FIXES

#### Effect System - Variable Shadowing Bug
**Problem**: Several Effect subclasses were declaring duplicate `private int amount;` fields, shadowing the parent's `protected int amount` field. This caused the local `amount` to remain at 0 (default value) while the parent's `amount` was correctly initialized, resulting in effects applying 0 change instead of the intended values.

**Files Fixed**:
- **DayEffect.java**: Removed duplicate `private int amount;` declaration
  - Impact: Day counter now properly increments
- **KnowledgeEffect.java**: Removed duplicate `private int amount;` declaration
  - Impact: Knowledge stat now properly increases/decreases
- **HealthEffect.java**: Removed duplicate `private int amount;` declaration
  - Impact: Health stat now properly increases/decreases

**Result**: All stats (Days, Knowledge, Health) now update correctly when effects are applied.

#### TextRenderer.java - Purge Counter Display
- **Purge Counter**: Added a purge counter display on the stats bars under the day counter

#### GameEngine.java - Initial State Improvements
- **Day Counter**: Changed initial player day from 0 to 1 (game now starts on "Day 1")
- **Haven Days**: Added `player.setHavenDays(1);` in "Arrival" case to start Haven days at 1 instead of 0

#### Rumor System - Enhanced Mechanics
- **Knowledge Cap**: Added maximum knowledge limit of 4 from rumors to prevent infinite knowledge farming
- **Probability**: Changed from 100% rumor chance to 70% chance (30% nothing), adding variability
- **Display**: Rumors now dynamically update the StoryNode text instead of using separate System.out.println
  - Requires new `setStory()` method in StoryNode.java

#### Choice System - Inverted Conditions (Enhancement)
**New Feature**: Added ability to show choices only when conditions are NOT met.

**Changes to Choice.java**:
- Added `private boolean inverted = false;` field
- Added `setInverted(boolean inverted)` method
- Added `isInverted()` getter method
- Updated `isAvailable()` logic to support condition inversion

**Use Case**: Can now show "Rest" choice only when energy is low/zero by using:
```java
restChoice.addCondition(new EnergyCondition(1));
restChoice.setInverted(true); // Shows only when energy < 1
```

#### StoryData.java - Energy-Based Choice Gating
**Major Enhancement**: Implemented comprehensive energy requirement system to prevent actions when energy is depleted.

**Changes**:
1. **Arrival Node** - Added conditional paths based on energy:
   - `regularArrival` choice requires `EnergyCondition(1)` (shows when energy ≥ 1)
   - `collapseChoice` uses inverted `EnergyCondition(1)` (shows only when energy = 0)
   
2. **HavenIntro Node** - All activity choices now require energy:
   - All exploration/activity choices (`Mingle`, `ObserveClinic`, `SearchDorms`, `Library`, `WorkKitchen`, `CleaningDuty`, `ShareFood`, `DormNight`) now require `EnergyCondition(1)`
   - `riskChoice` ("main office") requires `EnergyCondition(2)` and uses inverted `SuspicionCondition(7)`
   - `restChoice` ("rest for tomorrow") uses inverted `EnergyCondition(1)` - **only shows when energy = 0**
   
3. **ResearchHub Node** - Added energy-based rest gating:
   - `restActionChoice` ("must rest") uses inverted `EnergyCondition(1)` - **only shows when energy = 0**

**Impact**: 
- Prevents players from taking actions when exhausted (energy = 0)
- Forces players to rest when energy depleted
- Only "Rest" options appear when energy reaches 0
- Creates more strategic energy management gameplay
- Better reflects narrative realism (can't explore while exhausted)

#### StoryNode.java
- Added `setStory(String newStory)` method to support dynamic story text updates (used by enhanced rumor system)

### 📝 Technical Details

**Root Cause Analysis**:
The variable shadowing bug occurred because:
1. Parent `Effect` class declares `protected int amount`
2. Child classes incorrectly redeclared `private int amount`
3. Constructor called `super(amount)`, setting parent's field correctly
4. But `apply()` method referenced the local shadowed field (always 0)
5. Result: All effects using the shadowed variable applied 0 change

**Solution**: Removed duplicate field declarations, allowing child classes to properly inherit and use the parent's `amount` field.

**Energy Condition System Architecture**:
1. **Individual Choice Objects**: Changed from inline `addChoice()` calls to named Choice objects
2. **Conditional Gating**: Applied `EnergyCondition` to all energy-consuming actions
3. **Inverted Logic**: Used `setInverted(true)` on rest choices to show only when needed
4. **Result**: Dynamic choice availability based on player energy state

### 🎯 Impact

- **Critical**: Game stats now work as intended - players can progress through the game
- **Quality of Life**: Starting at Day 1 instead of Day 0 feels more natural
- **Balance**: Rumor knowledge cap prevents exploitation; energy requirements add strategic depth
- **Flexibility**: Inverted conditions enable more sophisticated choice gating
- **Realism**: Energy depletion now properly forces rest, preventing unrealistic exhausted exploration

---

## [v2.1] - 2025-11-29 (Minor Fixes and Welcome Display Function Optimization)

### CHANGES

#### Effects.java
*Due to project criteria, the developers have decided to change Effect and its implementations using "interfaces" into abstract classes instead*
- Effect.java is now an abstract class.
- The remaining Effect implementations are now subclasses that has the Inherited "int amount" and the Overriden "apply()".

#### TextRenderer.java
- Now contains the printIcon() and printWelcomeScreen() as helper functions for printing a basic Welcome Screen.
- The printWelcomeScreen() is being called at the start of the game in the GameEngine before the start of the game loop.

---

## [v2.0] - 2025-11-28 (FINAL Version)

**STATUS**: ✅ PRODUCTION READY & SUBMITTED

### 🎉 MAJOR RELEASE: COMPLETE GAME ENGINE

All 9 development phases completed successfully.

### ✨ ALL FEATURES COMPLETED

#### Phase 1 - Player Model ✅
- 19 fields tracking complete game state
- Validated getters/setters for all stats
- Energy capping enforced (0-5 max)
- Rumor tracking with boolean[7] array
- Comprehensive helper methods (changeEnergy, changeMorale, etc.)
- Haven days tracking (increments only on sleep/rest)
- Relationship tracking for Cris character
- Purge system tracking

#### Phase 2 - Effect System ✅
- Effect interface with 6 implementations:
  - HealthEffect: Modify player health
  - EnergyEffect: Modify energy with cap enforcement (0-5)
  - KnowledgeEffect: Modify knowledge stat
  - SuspicionEffect: Modify suspicion stat
  - MoraleEffect: Modify morale stat
  - DayEffect: Increment day counter
- ~20+ story nodes wired with effects
- Polymorphic effect application in GameEngine

#### Phase 3 - Condition System ✅
- Condition interface with 4 implementations:
  - EnergyCondition: Requires minimum energy
  - KnowledgeCondition: Requires minimum knowledge
  - SuspicionCondition: Suspicion < threshold
  - FlagCondition: Checks player flags (metScientist, etc.)
- Choice filtering based on conditions
- 5+ key choices gated by conditions

#### Phase 4 - Game Engine ✅
- Complete 11-step game loop:
  1. Check global interrupts (6 types)
  2. Get available choices (filtered by conditions)
  3. Display using TextRenderer
  4. Get player input (validated)
  5. Apply chosen choice effects (polymorphic)
  6. Handle special node effects (zones, flags, tracking)
  7. Determine next node ID (branching logic)
  8. Load next node
  9. Validate node exists
  10. Update time-based stats
  11. Loop or end
- 6 Global Interrupts:
  - Health ≤ 0 → BadEnd
  - Suspicion ≥ 10 → ArrestedEnd
  - Morale ≤ 0 → DespairEvent
  - Energy ≤ 0 → ForcedRest
  - Haven Days ≥ 20 → HavenPanicEnd
  - Purge Countdown ≤ 0 → PurgeEnd
- 13 Branching Decision Points with random/deterministic outcomes
- Input validation

#### Phase 5 - Special Systems ✅
- Zone Management (Wasteland → Haven → Hub)
- Purge System (7-day countdown activation)
- Haven Days Tracking (increments only on sleep/rest actions)
- Clinic Visit Tracking (affects probability scaling)
- Rumor System (7 rumors, player knowledge tracking)
- Relationship System (Cris affects EscapeEnd variant)

#### Phase 6 - Simplified Error Handling ✅
**Design Decision**: Removed custom exceptions for simplified professor version
- Deleted `InvalidChoiceException.java`
- Deleted `GameStateException.java`
- Replaced with simple if/else error handling
- Built-in exceptions (NumberFormatException) still used
- User-friendly error messages
- **Benefit**: Cleaner codebase for educational purposes

#### Phase 7 - Main Entry Point ✅
- Welcome screen with ASCII art
- Game initialization and startup
- Proper Scanner handling
- Graceful game flow

#### Phase 8 - Polish & Refinement ✅
**Changes in this version**:
- ✅ Deleted test files (GameEngineTest.java)
- ✅ Deleted exception files (for simplified professor version)
- ✅ Added ForcedRest story node (energy = 0 trigger)
- ✅ Enhanced TextRenderer with "DAYS IN HAVEN" display
- ✅ Fixed haven days increment logic
- ✅ Deleted all .class files for clean submission
- ✅ Zero compilation errors

#### Phase 9 - Documentation ✅
- README.md (500+ lines) - Complete game overview
- CHANGELOG.md (this file) - Version history
- README_DEVELOPERS.md (1,000+ lines) - Technical architecture
- PROJECT_COMPLETION_SUMMARY.md (400+ lines) - Completion status
- TODO.md - Project completion checklist

### 📝 Content
- **61 Story Nodes**: Complete narrative covering all game paths
- **7 Rumors**: Collectible story elements tracking player knowledge
- **11 Endings**: Multiple outcomes based on stats and choices

### 🏗️ Architecture
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
```

### 🔑 Key Features
- **Polymorphism**: Full effect and condition system using interfaces
- **Encapsulation**: Player model with validated state mutations
- **Abstraction**: GameEngine hides complex loop and branching logic
- **Simplified Errors**: If/else validation without custom exceptions
- **Relationships**: Dynamic story outcomes based on Cris relationship
- **Zone System**: Three-zone progression (Wasteland → Haven → Hub)
- **Resource Management**: Energy capping, suspicion thresholds, morale tracking
- **Rumor Tracking**: Persistent player knowledge of story elements
- **Rest System**: Three rest nodes with intelligent time progression
- **Haven Days Tracking**: Separate time counter for Haven location

### 📊 Final Metrics
- **Java Files**: 17 (Main + 3 model + 6 effect + 4 condition + 2 engine + ui)
- **Interfaces**: 2 (Effect, Condition)
- **Story Nodes**: 61
- **Branching Points**: 13
- **Global Interrupts**: 6
- **Lines of Code**: ~2,000 LOC
- **Compilation Errors**: 0
- **Documentation**: 2,000+ lines

---

## [v1.1] - 2025-11-26

### Initial Narrative Foundation
- Story nodes ported from design document
- Folder structure established
- TextRenderer UI framework implemented
- Player, Choice, StoryNode models defined
- Condition and Effect system design

---

## [v1.0] - Initial Concept
- Game concept and narrative outline
- Project structure planning
- Architecture design discussions
