## **📋 PATIENT ZERO - REVISED TODO LIST**

Based on our OOP discussion and project requirements:

---

## **PHASE 1: COMPLETE THE PLAYER MODEL** ⚙️

### **Add Missing Stats & Fields to Player.java:**

**Currently Have:**
- ✅ health, energy, knowledge, suspicion, day

**Need to Add:**
- [ ] `morale` (int, 0-10+) - Triggers DespairEvent at 0
- [ ] `zone` (String) - "Wasteland", "Haven", or "Hub"
- [ ] `havenDays` (int) - Days spent in Haven (panic at 20)
- [ ] `purgeCountdown` (int) - Days remaining (7 when activated, fail at 0)
- [ ] `purgeActive` (boolean) - Whether purge has been activated
- [ ] `clinicVisits` (int) - Tracks visits to clinic (increases catch chance)
- [ ] `metScientist` (boolean) - Unlocks DormNight choice
- [ ] `crisRelationship` (int, 0 to 8) - **CRITICAL for endings**
- [ ] `inventory` (ArrayList<String>) - Items like "Data Chip"
- [ ] `rumors` (ArrayList<String>) - List of available rumors for Mingle

**Optional (for flavor):**
- [ ] `metScavenger` (boolean)
- [ ] `jaxRelationship` (int, -1 to 3)

**Also Add:**
- [ ] Getter/setter methods for all new fields
- [ ] Helper methods: `hasItem(String)`, `addToInventory(String)`, `hasRumorsAvailable()`, `removeRumor()`

---

## **PHASE 2: IMPLEMENT EFFECT SYSTEM (OOP Requirement)** 💊

### **✅ Satisfies: Inheritance + Polymorphism**

### **2.1: Create Effect Interface**
```
src/effect/Effect.java
```
- [ ] Define `void apply(Player player)` method
- [ ] Define `String getDescription()` method

### **2.2: Create Effect Implementations (At least 4 subclasses)**
- [ ] `HealthEffect.java` - Changes health
- [ ] `EnergyEffect.java` - Changes energy  
- [ ] `KnowledgeEffect.java` - Changes knowledge
- [ ] `SuspicionEffect.java` - Changes suspicion
- [ ] **BONUS:** `MoraleEffect.java`, `DayEffect.java`, etc.

Each implements:
- [ ] `apply(Player player)` - Applies the effect
- [ ] `getDescription()` - Returns description like "+5 Health"

### **2.3: Update StoryNode to Hold Effects**
- [ ] Add `List<Effect> effects` field
- [ ] Add `addEffect(Effect e)` method
- [ ] Add `getEffects()` getter

### **2.4: Update StoryData with Effects**
- [ ] Add effects to relevant nodes (e.g., `MeetScavenger_Convince` gets +2 morale)
- [ ] Example:
```java
start.addEffect(new MoraleEffect(2));
start.addEffect(new HealthEffect(-1));
```

---

## **PHASE 3: IMPLEMENT CONDITION SYSTEM (OOP Requirement)** 🔐

### **✅ Satisfies: Inheritance + Polymorphism**

### **3.1: Create Condition Interface**
```
src/condition/Condition.java
```
- [ ] Define `boolean isMet(Player player)` method
- [ ] Define `String getRequirementText()` method

### **3.2: Create Condition Implementations (At least 3 subclasses)**
- [ ] `EnergyCondition.java` - Requires X energy
- [ ] `KnowledgeCondition.java` - Requires X knowledge
- [ ] `SuspicionCondition.java` - Requires suspicion < X
- [ ] **BONUS:** `FlagCondition.java` (for metScientist), `ItemCondition.java`, etc.

### **3.3: Update Choice to Hold Conditions**
- [ ] Add `List<Condition> conditions` field
- [ ] Add `addCondition(Condition c)` method
- [ ] Add `isAvailable(Player player)` method that checks all conditions

### **3.4: Update StoryData with Conditions**
- [ ] Add conditions to choices (e.g., Library requires 5+ knowledge)
- [ ] Example:
```java
libraryChoice.addCondition(new KnowledgeCondition(5));
libraryChoice.addCondition(new EnergyCondition(1));
```

---

## **PHASE 4: BUILD THE GAME ENGINE** 🎮

### **4.1: Core Game Loop**
- [ ] Create GameEngine.java class
- [ ] Add fields: `Player player`, `StoryNode currentNode`, `Scanner scanner`
- [ ] Create `startGame()` method
  - Initialize player with starting stats
  - Load "Start" node
  - Begin game loop

### **4.2: Main Game Loop Structure**
```java
while (gameRunning) {
    1. Check global interrupts
    2. Display current node
    3. Show available choices (filter by conditions)
    4. Get player input
    5. Apply effects from choice
    6. Process branching (if needed)
    7. Move to next node
}
```

- [ ] Implement global interrupt checks:
  - Health ≤ 0 → BadEnd
  - Suspicion ≥ 10 → ArrestedEnd
  - Morale ≤ 0 → DespairEvent
  - Haven days ≥ 20 (no purge) → HavenPanicEnd
  - Purge countdown ≤ 0 → PurgeEnd
  - Energy ≤ 0 → ForcedRest (branching ID)

### **4.3: Implement Effect Application**
- [ ] Create `applyEffects(StoryNode node, Player player)` method
- [ ] Loop through `node.getEffects()`
- [ ] Call `effect.apply(player)` for each (**polymorphism!** ✅)

### **4.4: Implement Branching System**
- [ ] Create `getNextNodeId(String choiceId, Player player)` method
- [ ] Add switch cases for all 13 branching IDs:
  1. `FastTravel` → 60% success / 40% failure
  2. `SearchTravel` → 50% success / 50% failure
  3. `Mingle` → check rumors available
  4. `ObserveClinic` → 30% + 10% per visit
  5. `SearchDorms` → 50% caught / 50% find
  6. `Library` → knowledge < 7 vs ≥ 7
  7. `RiskInformation` → 50% success / 50% caught
  8. `Section1Gate` → knowledge < 3 vs ≥ 3
  9. `StealSamples` → 50% success / 50% caught
  10. `FinalCheck` → knowledge ≥ 12 check
  11. `FinalHeist` → complex knowledge check + random
  12. `EscapeEnd` → Cris relationship branching
  13. **`ForcedRest`** → zone-based routing

### **4.5: Input & Validation**
- [ ] Display numbered choices (only available ones based on conditions)
- [ ] Get integer input
- [ ] Validate input range
- [ ] Handle invalid input with try-catch (**exception handling!** ✅)

---

## **PHASE 5: SPECIAL SYSTEMS** 🎲

### **5.1: Rumor System**
- [ ] Initialize 7 rumors in Player constructor
- [ ] Implement `hasRumorsAvailable()` → returns `!rumors.isEmpty()`
- [ ] Implement `removeRumor()` → removes one random rumor
- [ ] Use in Mingle branching

### **5.2: Zone Management**
- [ ] Set zone to "Wasteland" at game start
- [ ] Update zone when reaching nodes:
  - `Arrival` → "Haven"
  - `ResearchHub` → "Hub"
- [ ] Track havenDays only when zone = "Haven"
- [ ] Use zone in ForcedRest branching

### **5.3: Purge System**
- [ ] Activate at `PurgeReveal`: `purgeActive = true`, `purgeCountdown = 7`
- [ ] Decrement countdown only when `purgeActive == true`
- [ ] Check countdown ≤ 0 in global interrupts

### **5.4: Inventory System**
- [ ] Implement `addToInventory(String item)`
- [ ] Implement `hasItem(String item)`
- [ ] Optional: Check for "Data Chip" when meeting Cris

---

## **PHASE 6: EXCEPTION HANDLING (Requirement)** 🚨

### **6.1: Create Custom Exceptions**
- [ ] Create `exception/InvalidChoiceException.java`
- [ ] Create `exception/GameStateException.java` (optional)

### **6.2: Use Exceptions in GameEngine**
- [ ] Throw `InvalidChoiceException` for invalid input
- [ ] Wrap input handling in try-catch blocks
- [ ] Display user-friendly error messages

---

## **PHASE 7: UPDATE MAIN.JAVA** 🚀

- [ ] Remove test code
- [ ] Create GameEngine instance
- [ ] Call `gameEngine.startGame()`
- [ ] Add welcome message/intro

---

## **PHASE 8: TESTING & POLISH** ✨

### **Testing:**
- [ ] Test all 11 endings are reachable
- [ ] Test all global interrupts trigger
- [ ] Test all 13 branching IDs work
- [ ] Test energy depletion → ForcedRest with zone routing
- [ ] Test condition filtering (unavailable choices hidden)
- [ ] Test effect application (polymorphism working)
- [ ] Test purge countdown
- [ ] Test Cris relationship → correct ending
- [ ] Playtest minimum victory path
- [ ] Playtest fastest victory path

### **Polish:**
- [ ] Screen clearing between turns (TextRenderer.clearScreen())
- [ ] "Press Enter to continue" pauses
- [ ] Balance initial stats if needed

---

## **PHASE 9: DOCUMENTATION** 📝

- [ ] Update README.md with actual Patient Zero info
- [ ] Add compilation instructions
- [ ] Add how to run
- [ ] Add example gameplay
- [ ] Update CHANGELOG.md
- [ ] Document OOP principles used (for submission):
  - Encapsulation (private fields, getters/setters)
  - Inheritance (Effect interface → 4+ implementations, Condition interface → 3+ implementations)
  - Polymorphism (apply() method, isMet() method)
  - Abstraction (interfaces, GameEngine hiding complexity)
  - Exception handling (custom exceptions)

---

## **🎯 OOP REQUIREMENTS CHECKLIST:**

- [ ] **Encapsulation** - Private fields with getters/setters ✅
- [ ] **Inheritance** - Effect interface + 4 subclasses, Condition interface + 3 subclasses ✅
- [ ] **Polymorphism** - `effect.apply(player)`, `condition.isMet(player)` ✅
- [ ] **Abstraction** - Interfaces (Effect, Condition) ✅
- [ ] **Exception Handling** - InvalidChoiceException in try-catch ✅
- [ ] **Arrays** - ArrayList for inventory, rumors, effects, conditions ✅
- [ ] **Console I/O** - Scanner for input, TextRenderer for output ✅

---

## **📊 RECOMMENDED IMPLEMENTATION ORDER:**

### **SPRINT 1: Foundation (Get it Running)**
1. Complete Player model (Phase 1)
2. Create Effect interface + 4 implementations (Phase 2.1-2.2)
3. Add effects to StoryNode (Phase 2.3)
4. Basic GameEngine loop (Phase 4.1-4.2)
5. Effect application (Phase 4.3)
6. Test one playthrough to Start → BadEnd

### **SPRINT 2: Core Mechanics**
7. Create Condition interface + 3 implementations (Phase 3)
8. Add conditions to choices (Phase 3.4)
9. Implement all 13 branching decisions (Phase 4.4)
10. Global interrupts (Phase 4.2)
11. Zone tracking + special systems (Phase 5)

### **SPRINT 3: Polish & Complete**
12. Exception handling (Phase 6)
13. Update Main.java (Phase 7)
14. Full effects in StoryData (Phase 2.4)
15. Full conditions in StoryData (Phase 3.4)
16. Testing all paths (Phase 8)

### **SPRINT 4: Documentation & Ship**
17. Update README (Phase 9)
18. Final testing
19. Submit! 🎉

---

**Does this look good?** Give me the signal and I'll create the `TODO.md` file! 🚀
