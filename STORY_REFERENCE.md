# PATIENT ZERO: COMPLETE STORY REFERENCE

## Overview
- **Total Nodes**: 64 nodes
- **Acts**: 4 (Journey, Haven, Purge, Finale)
- **Endings**: 11 different endings
- **Branching Points**: 10 dynamic outcome nodes

---

## ACT 1: THE JOURNEY (Wasteland)

### Node: `Start`
**What Happens**: Meet Jax the scavenger in the ruins  
**Choices**:
1. "I'm looking for answers..." → `MeetScavenger_Convince`
2. "Just trying to survive..." → `MeetScavenger_Neutral`
3. "Keep your distance and observe" → `ObserveScavenger`

---

### Node: `MeetScavenger_Convince`
**What Happens**: Jax trusts you immediately, gives data chip willingly  
**Effects**: +2 Morale, Get "Data Chip", Set Jax relationship = 3  
**Choices**:
1. "Thank you. I won't waste this." → `JourneyChoices`

---

### Node: `MeetScavenger_Neutral`
**What Happens**: Jax questions your commitment  
**Choices**:
1. "Because I'm still here..." → `MeetScavenger_Trust`
2. "I don't know. Maybe you shouldn't..." → `MeetScavenger_Doubt`

---

### Node: `MeetScavenger_Trust`
**What Happens**: Earned trust through persistence  
**Effects**: Get "Data Chip", Set Jax relationship = 2  
**Choices**:
1. "Time to find this Cris." → `JourneyChoices`

---

### Node: `MeetScavenger_Doubt`
**What Happens**: Jax gives chip reluctantly  
**Effects**: -1 Morale, Get "Data Chip", Set Jax relationship = 0  
**Choices**:
1. "Better take the chip and move on." → `JourneyChoices`

---

### Node: `ObserveScavenger`
**What Happens**: Watched from hiding, found chip after Jax left  
**Effects**: -1 Energy, +1 Knowledge, -1 Morale, Get "Data Chip", Set Jax relationship = -1  
**Choices**:
1. "Take the chip and continue." → `JourneyChoices`

---

### Node: `JourneyChoices`
**What Happens**: Choose your route to Haven  
**Effects**: Set zone = "Wasteland"  
**Choices**:
1. "Safe route through sewers (1 Energy)" → `SlowTravel` *(Requires 1+ energy)*
2. "Risk the open route (2 Energy)" → `FastTravel` *(Requires 2+ energy - BRANCHES)*
3. "Search for supplies (2 Energy)" → `SearchTravel` *(Requires 2+ energy - BRANCHES)*
4. "I need to rest before continuing" → `RestBeforeJourney` *(Only if energy < 1)*

**BRANCH LOGIC**:
- `FastTravel` → GameEngine decides: 40% chance `FastTravel_Failure` OR 60% chance `FastTravel_Success`
- `SearchTravel` → GameEngine decides: 50% chance `SearchTravel_Failure` OR 50% chance `SearchTravel_Success`

---

### Node: `RestBeforeJourney`
**What Happens**: Rest in the ruins before traveling  
**Effects**: +1 Day, +1 Morale, Restore energy  
**Choices**:
1. "Continue toward Haven." → `JourneyChoices`

---

### Node: `SlowTravel`
**What Happens**: Safe 3-day journey through sewers  
**Effects**: -1 Energy, +3 Days, -1 Morale  
**Choices**:
1. "Finally... the Haven." → `Arrival`

---

### Node: `FastTravel_Success`
**What Happens**: Fast travel succeeded without injury  
**Effects**: -2 Energy, +1 Day, +1 Morale  
**Choices**:
1. "The Haven gates... almost there." → `Arrival`

---

### Node: `FastTravel_Failure`
**What Happens**: Injured by razor wire during fast travel  
**Effects**: -2 Energy, +1 Day, -3 Health, -2 Morale  
**Choices**:
1. "The Haven gates... almost there." → `Arrival`

---

### Node: `SearchTravel_Success`
**What Happens**: Found a Medkit in pharmacy  
**Effects**: -2 Energy, +5 Health, +1 Morale  
**Choices**:
1. "Back to the main path." → `JourneyChoices`

---

### Node: `SearchTravel_Failure`
**What Happens**: Attacked by looter with knife  
**Effects**: -2 Energy, -2 Health, -1 Morale  
**Choices**:
1. "Back to the main path." → `JourneyChoices`

---

### Node: `Arrival`
**What Happens**: First sight of Haven fortress  
**Effects**: Set zone = "Haven", +2 Morale  
**Choices**:
1. "Find a bunk and settle in." → `HavenIntro` *(Requires energy > 0)*
2. "I can't... keep going... (Collapse)" → `ArrivalCollapse` *(Only if energy ≤ 0)*

---

### Node: `ArrivalCollapse`
**What Happens**: Collapsed from exhaustion, wake up in medical wing  
**Effects**: +3 Suspicion, -2 Morale, Restore energy, +2 Days  
**Choices**:
1. "I need to find the dorms..." → `HavenIntro`

---

## ACT 2: THE HAVEN (Safe Haven Refugee Camp)

### Node: `HavenIntro` (MAIN HUB)
**What Happens**: Daily life hub in refugee quarters  
**Effects**: Replenish energy if new day  
**Choices** (11 total):
1. "Listen to what people are saying (1 Energy)" → `Mingle` *(Requires energy > 0, suspicion < 6 - BRANCHES)*
2. "The medical wing... (1 Energy)" → `ObserveClinic` *(Requires energy > 0, suspicion < 6 - BRANCHES)*
3. "Find something in dorms (1 Energy)" → `SearchDorms` *(Requires energy > 0, suspicion < 6 - BRANCHES)*
4. "The library (1 Energy)" → `Library` *(Requires knowledge ≥ 5, energy > 0, suspicion < 6 - BRANCHES)*
5. "Help in kitchen (1 Energy)" → `WorkKitchen` *(Requires energy > 0, suspicion ≥ 3)*
6. "Do cleaning duty (1 Energy)" → `CleaningDuty` *(Requires energy > 0, suspicion ≥ 3)*
7. "Share my rations (1 Energy)" → `ShareFood` *(Requires energy > 0)*
8. "Rest and watch courtyard (1 Energy)" → `LeisurelyRest` *(Requires energy > 0)*
9. "Go for main office (2 Energy)" → `RiskInformation` *(Requires energy > 0, suspicion ≥ 6 - BRANCHES)*
10. "Meet Cris tonight (1 Energy)" → `DormNight` *(Requires met scientist, energy > 0)*
11. "I need to rest for tomorrow" → `RestInDorm` *(Always available)*

**BRANCH LOGIC**:
- `Mingle` → GameEngine: If rumors available → `Mingle_Rumor`, else → `Mingle_Nothing`
- `ObserveClinic` → GameEngine: Random 30%+ (increases with visits) → `ObserveClinic_Caught` OR `ObserveClinic_Learn`
- `SearchDorms` → GameEngine: Random 50% → `SearchDorms_Caught` OR `SearchDorms_FindNote`
- `Library` → GameEngine: If knowledge < 7 → `Library_LowKnowledge`, else → `Library_HighKnowledge`
- `RiskInformation` → GameEngine: Random 50% → `RiskInformation_Success` OR `RiskInformation_Caught`

---

### Node: `Mingle_Rumor`
**What Happens**: Heard a useful rumor from refugees  
**Effects**: -1 Energy, +1 Knowledge, -1 Morale, Remove one rumor from list  
**Choices**:
1. "Back to the dorms." → `HavenIntro`

---

### Node: `Mingle_Nothing`
**What Happens**: No useful information today  
**Effects**: -1 Energy  
**Choices**:
1. "Back to the dorms." → `HavenIntro`

---

### Node: `ObserveClinic_Caught`
**What Happens**: Spotted by guard while observing clinic  
**Effects**: -1 Energy, +Suspicion (varies by visit count), -1 Morale, Increment clinic visits  
**Choices**:
1. "Better get back." → `HavenIntro`

---

### Node: `ObserveClinic_Learn`
**What Happens**: Saw suspicious "Non-viable Samples" van  
**Effects**: -1 Energy, +1 Knowledge, Increment clinic visits  
**Choices**:
1. "Better get back." → `HavenIntro`

---

### Node: `SearchDorms_Caught`
**What Happens**: Caught by guard searching dorms  
**Effects**: -1 Energy, +3 Suspicion, -2 Morale  
**Choices**:
1. "Back to safety." → `HavenIntro`

---

### Node: `SearchDorms_FindNote`
**What Happens**: Found note with code "4-1-B"  
**Effects**: -1 Energy, +1 Knowledge  
**Choices**:
1. "Back to safety." → `HavenIntro`

---

### Node: `WorkKitchen`
**What Happens**: Helped in kitchen to reduce suspicion  
**Effects**: -1 Energy, -1 Suspicion, +1 Health, +1 Morale  
**Choices**:
1. "Back to the dorms." → `HavenIntro`

---

### Node: `CleaningDuty`
**What Happens**: Did cleaning to appear harmless  
**Effects**: -1 Energy, -1 Suspicion, +1 Morale  
**Choices**:
1. "Time to head back." → `HavenIntro`

---

### Node: `LeisurelyRest`
**What Happens**: Relaxed and watched courtyard  
**Effects**: -1 Energy, +2 Morale  
**Choices**:
1. "Back to reality." → `HavenIntro`

---

### Node: `ShareFood`
**What Happens**: Shared rations with refugee family  
**Effects**: -1 Energy, +2 Morale  
**Choices**:
1. "Back to the dorms." → `HavenIntro`

---

### Node: `RiskInformation_Success`
**What Happens**: Successfully stole data from main office  
**Effects**: -2 Energy, +2 Knowledge, +1 Morale  
**Choices**:
1. "Back to the dorms." → `HavenIntro`

---

### Node: `RiskInformation_Caught`
**What Happens**: Caught by security drone  
**Effects**: -2 Energy, +10 Suspicion (MAXED), -3 Morale  
**Choices**:
1. "Back to the dorms." → `HavenIntro`

---

### Node: `Library_LowKnowledge`
**What Happens**: Found facility maps in library  
**Effects**: -1 Energy, +1 Knowledge, +1 Suspicion  
**Choices**:
1. "Better leave before someone notices." → `HavenIntro`

---

### Node: `Library_HighKnowledge`
**What Happens**: Scientist Cris notices you reading advanced texts  
**Effects**: -1 Energy, +1 Suspicion  
**Choices**:
1. "Talk to the scientist." → `ScientistApproaches`

---

### Node: `ScientistApproaches`
**What Happens**: First meeting with Cris, he arranges secret meeting  
**Effects**: Set metScientist = true, +1 Knowledge, Set Cris relationship = 3  
**Choices**:
1. "I'll be there." → `HavenIntro`

---

### Node: `DormNight`
**What Happens**: Night before meeting Cris in Section-1  
**Effects**: +1 Day, -1 Morale  
**Choices**:
1. "Time to find Section-1." → `Section1Gate`

**BRANCH LOGIC**:
- `Section1Gate` → GameEngine: If knowledge < 3 → `Section1Gate_Fail`, else → `Section1Gate_Success`

---

### Node: `Section1Gate_Fail`
**What Happens**: Don't know the access code, alarm triggered  
**Effects**: +4 Suspicion, -2 Morale  
**Choices**:
1. "Run!" → `HavenIntro`

---

### Node: `Section1Gate_Success`
**What Happens**: Used code "4-1-B", entered Section-1  
**Effects**: +1 Day, +2 Morale  
**Choices**:
1. "Enter Section-1." → `Section1`

---

### Node: `Section1`
**What Happens**: Secret meeting with Cris in lab  
**Choices**:
1. "What's really happening here?" → `PurgeReveal`

---

### Node: `RestInDorm`
**What Happens**: Sleep and recover (auto-progresses next day)  
**Effects**: +1 Day, +1 Morale, Restore energy, +1 Haven day counter  
**Choices**:
- *(Auto-continues to `HavenIntro`)*

---

## ACT 3: PURGE PROTOCOL (Research Hub)

### Node: `PurgeReveal`
**What Happens**: Cris reveals Protocol Omega - 7 day countdown begins  
**Effects**: Activate purge (7 days), -3 Morale, Set Cris relationship = 5  
**Choices**:
1. "I'll do it. What should I look for?" → `ResearchHub`
2. "I can't... this is too much." → `ComfortBadEnd`

---

### Node: `ResearchHub` (MAIN HUB)
**What Happens**: Investigation hub in research vents (Goal: 12 Knowledge)  
**Effects**: Set zone = "Hub"  
**Choices**:
1. "Steal lab samples (1 Energy)" → `StealSamples` *(Requires energy > 0, suspicion < 10, days remaining > 0 - BRANCHES)*
2. "Spy on guard conversations (1 Energy)" → `SpyGuards` *(Same requirements)*
3. "Question friendly scientist (1 Energy)" → `TalkScientist` *(Same requirements)*
4. "I'm ready to confront the truth" → `FinalCheck` *(Same requirements - BRANCHES)*
5. "I must rest... no energy left" → `RestAction` *(Only if energy ≤ 0)*

**BRANCH LOGIC**:
- `StealSamples` → GameEngine: Random 50% → `StealSamples_Success` OR `StealSamples_Caught`
- `FinalCheck` → GameEngine: If knowledge ≥ 12 → `FinalCheck_Ready`, else → `FinalCheck_NotReady`

---

### Node: `StealSamples_Success`
**What Happens**: Successfully stole purge agent samples  
**Effects**: -1 Energy, +3 Knowledge, +2 Morale  
**Choices**:
1. "Back to hiding." → `ResearchHub`

---

### Node: `StealSamples_Caught`
**What Happens**: Caught while stealing, shot at and injured  
**Effects**: -1 Energy, +3 Suspicion, -3 Health, -1 Morale  
**Choices**:
1. "Back to hiding." → `ResearchHub`

---

### Node: `SpyGuards`
**What Happens**: Overheard guards discussing purge chemicals  
**Effects**: -1 Energy, +1 Knowledge  
**Choices**:
1. "Continue gathering information." → `ResearchHub`

---

### Node: `TalkScientist`
**What Happens**: Questioned friendly researcher  
**Effects**: -1 Energy, +2 Knowledge, +2 Suspicion  
**Choices**:
1. "Better get back to hiding." → `ResearchHub`

---

### Node: `RestAction`
**What Happens**: Rest in research hub  
**Effects**: +1 Day, +1 Morale, Restore energy, -1 Purge countdown  
**Choices**:
1. "Wake up and continue." → `ResearchHub`

---

## ACT 4: THE FINALE

### Node: `FinalCheck_Ready`
**What Happens**: Have enough evidence (12+ Knowledge)  
**Choices**:
1. "I'm going to Cris with the evidence." → `CureOptions`

---

### Node: `FinalCheck_NotReady`
**What Happens**: Not enough evidence yet (< 12 Knowledge)  
**Choices**:
1. "I have to risk one final information grab." → `LastChance`
2. "I need more evidence first." → `ResearchHub`

---

### Node: `LastChance`
**What Happens**: Considering desperate final heist  
**Choices**:
1. "Yes - it's now or never." → `FinalHeist` *(BRANCHES)*
2. "No - I need to be more careful." → `ResearchHub`

**BRANCH LOGIC**:
- `FinalHeist` → GameEngine: If knowledge ≥ 12 after attempt → `FinalHeist_Success`, else random 50% → `FinalHeist_Success` OR `FinalHeist_Failure`

---

### Node: `FinalHeist_Success`
**What Happens**: Final heist succeeded, got remaining evidence  
**Effects**: -2 Health, +10 Suspicion, +2 Knowledge  
**Choices**:
1. "Escape to Cris!" → `EscapeEnd` *(BRANCHES)*

**BRANCH LOGIC**:
- `EscapeEnd` → GameEngine: If Cris relationship ≥ 8 → `EscapeEnd_Alliance`, else if ≤ 2 → `EscapeEnd_Fugitive`, else → `EscapeEnd_Savior`

---

### Node: `FinalHeist_Failure`
**What Happens**: Final heist failed, caught by guards  
**Effects**: -2 Health, +10 Suspicion, +2 Knowledge, -3 Morale  
**Choices**:
1. "There's no escape..." → `Collapse`

---

### Node: `CureOptions`
**What Happens**: Have full evidence, choose what to do with truth  
**Choices**:
1. "We escape together and rebuild elsewhere." → `EscapeEnd` *(BRANCHES to Alliance/Fugitive/Savior)*
2. "We broadcast the truth to the outside world." → `ExposeEnd`
3. "We take the evidence to the military." → `MilEnd`

---

### Node: `DespairEvent` (Triggered by Global Check)
**What Happens**: Morale hit 0, moment of despair but can recover  
**Effects**: +2 Morale (rebound from bottom)  
**Choices**:
1. "I have to keep going. For everyone." → `HavenIntro` *(If in Haven zone)*
2. "I can't give up now. The truth matters." → `ResearchHub` *(If in Hub zone)*

---

## ENDINGS (11 Total)

### Node: `BadEnd` (TRIGGERED: Health ≤ 0)
**What Happens**: Died from injuries  
**Type**: Failure ending  
**Choices**:
1. "[Restart Game]" → `Start`

---

### Node: `ArrestedEnd` (TRIGGERED: Suspicion ≥ 10)
**What Happens**: Captured and used as test subject  
**Type**: Failure ending  
**Choices**:
1. "[Restart Game]" → `Start`

---

### Node: `HavenPanicEnd` (TRIGGERED: Days in Haven ≥ 20)
**What Happens**: Waited too long, Haven descends into chaos  
**Type**: Failure ending  
**Choices**:
1. "[Restart Game]" → `Start`

---

### Node: `PurgeEnd` (TRIGGERED: Purge countdown ≤ 0)
**What Happens**: Ran out of time, purge executed  
**Type**: Failure ending  
**Choices**:
1. "[Restart Game]" → `Start`

---

### Node: `ComfortBadEnd`
**What Happens**: Refused to help Cris, chose safety over truth  
**Type**: Failure ending  
**Choices**:
1. "[Restart Game]" → `Start`

---

### Node: `Collapse`
**What Happens**: Failed final heist, caught and formula lost  
**Type**: Failure ending  
**Choices**:
1. "[Restart Game]" → `Start`

---

### Node: `EscapeEnd_Alliance` (Best Ending)
**What Happens**: Escaped with Cris, built research facility together  
**Requirements**: Cris relationship ≥ 8  
**Type**: Victory ending  
**Choices**:
1. "[Restart Game]" → `Start`

---

### Node: `EscapeEnd_Fugitive`
**What Happens**: Escaped alone, hunted but carrying the cure  
**Requirements**: Cris relationship ≤ 2  
**Type**: Victory ending  
**Choices**:
1. "[Restart Game]" → `Start`

---

### Node: `EscapeEnd_Savior`
**What Happens**: Escaped alone while Cris distracts guards  
**Requirements**: Cris relationship 3-7  
**Type**: Victory ending  
**Choices**:
1. "[Restart Game]" → `Start`

---

### Node: `ExposeEnd`
**What Happens**: Broadcast truth to world, martyred but started revolution  
**Type**: Victory ending  
**Choices**:
1. "[Restart Game]" → `Start`

---

### Node: `MilEnd`
**What Happens**: Military distributes cure under martial law  
**Type**: Bittersweet ending  
**Choices**:
1. "[Restart Game]" → `Start`

---

## GLOBAL INTERRUPT SYSTEM (GameEngine)

These conditions automatically redirect to specific nodes:

| **Trigger Condition** | **Target Node** | **When Checked** |
|-----------------------|-----------------|------------------|
| Health ≤ 0 | `BadEnd` | Every turn before choices |
| Suspicion ≥ 10 | `ArrestedEnd` | Every turn before choices |
| Morale ≤ 0 | `DespairEvent` | Every turn before choices |
| Days in Haven ≥ 20 AND purge not active | `HavenPanicEnd` | When entering `HavenIntro` |
| Purge active AND days remaining ≤ 0 | `PurgeEnd` | Every turn before choices |
| Energy ≤ 0 | Auto-rest on current node | When no choices available |

---

## BRANCHING DECISION POINTS (For GameEngine)

### When player chooses these node IDs, GameEngine must decide outcome:

1. **`FastTravel`** → Random: 40% `FastTravel_Failure` OR 60% `FastTravel_Success`

2. **`SearchTravel`** → Random: 50% `SearchTravel_Failure` OR 50% `SearchTravel_Success`

3. **`Mingle`** → Check: If rumors available → `Mingle_Rumor`, else → `Mingle_Nothing`

4. **`ObserveClinic`** → Random with increasing chance per visit: `ObserveClinic_Caught` OR `ObserveClinic_Learn`

5. **`SearchDorms`** → Random: 50% `SearchDorms_Caught` OR 50% `SearchDorms_FindNote`

6. **`Library`** → Check: If knowledge < 7 → `Library_LowKnowledge`, else → `Library_HighKnowledge`

7. **`RiskInformation`** → Random: 50% `RiskInformation_Success` OR 50% `RiskInformation_Caught`

8. **`Section1Gate`** → Check: If knowledge < 3 → `Section1Gate_Fail`, else → `Section1Gate_Success`

9. **`StealSamples`** → Random: 50% `StealSamples_Success` OR 50% `StealSamples_Caught`

10. **`FinalCheck`** → Check: If knowledge ≥ 12 → `FinalCheck_Ready`, else → `FinalCheck_NotReady`

11. **`FinalHeist`** → Complex: If knowledge ≥ 12 after +2 → `FinalHeist_Success`, else Random 50% → Success/Failure

12. **`EscapeEnd`** → Check Cris relationship: ≥8 → `Alliance`, ≤2 → `Fugitive`, else → `Savior`

---

## STAT TRACKING REQUIREMENTS

### Core Stats (Player class):
- **Health** (0-10): Die at 0
- **Energy** (0-3): Actions cost energy, replenish daily
- **Knowledge** (0-12+): Need 12 to win
- **Suspicion** (0-10): Arrested at 10
- **Day**: Track total days passed

### Special Counters:
- **Days in Haven**: Panic at 20 days (while in Haven zone)
- **Purge Countdown**: 7 days when activated, fail at 0
- **Clinic Visits**: Increases catch chance at clinic
- **Zone**: "Wasteland", "Haven", or "Hub"

### Story Flags (booleans):
- **metScientist**: Unlocks meeting Cris at night
- **metScavenger**: Story tracking
- **convincedJax**: Story tracking

### Relationships (integers):
- **Jax**: -1 to 3 (affects story flavor)
- **Cris**: 0 to 8 (determines which escape ending)

### Inventory (strings):
- "Data Chip" (required to meet Cris)
- Other items as needed

### Rumor System:
List of ~7 rumors that get consumed when shown to player

---

## CRITICAL PATHS

### Minimum Path to Victory:
1. Start → Get Data Chip → Reach Haven
2. Investigate to reach 5+ Knowledge → Meet Cris in Library
3. Learn code "4-1-B" (≥3 Knowledge) → Enter Section-1
4. Gather 12 Knowledge in 7 days
5. Choose ending

### Fastest Victory Path:
- Convince Jax (morale boost)
- Fast Travel Success (1 day)
- Search Dorms (find code immediately)
- Risk Information Success (+2 knowledge)
- Meet Cris quickly
- Steal Samples 3x in Research Hub (+9 knowledge)
- Spy/Talk for remaining knowledge
- Choose escape with high Cris relationship

### Common Failure Points:
- Running out of energy without resting
- Getting caught too many times (suspicion ≥ 10)
- Taking 20+ days in Haven before meeting Cris
- Running out of purge time (7 days)
- Not reaching 12 knowledge before finale

---

## IMPLEMENTATION NOTES

### For GameEngine Development:
1. Check global interrupts EVERY turn before displaying choices
2. Handle branching decisions when processing choice selection
3. Apply effects AFTER choosing but BEFORE moving to next node
4. Track energy carefully - force rest when depleted
5. Update zone when entering Haven/Hub areas
6. Increment Haven day counter only while in Haven zone
7. Decrement purge countdown only when purge is active

### For Condition System:
- Most conditions check: `energy > 0` and `suspicion < threshold`
- Knowledge gates: Library (5+), Section1 (3+), Finale (12+)
- Flag gates: DormNight requires `metScientist == true`
- Zone gates: DespairEvent returns to current zone's hub

### For Effect System:
- Energy costs: Most actions cost 1, some cost 2
- Knowledge gains: +1 (safe), +2 (medium), +3 (risky)
- Suspicion changes: +1-3 (risky actions), -1 (helpful actions)
- Health changes: -2 to -3 (injuries), +1 to +5 (healing)
- Morale changes: -3 to +2 (story events)

---

## TOTAL NODE COUNT: 64 Nodes

**By Category:**
- Journey: 13 nodes
- Haven: 27 nodes
- Purge: 10 nodes
- Finale: 8 nodes
- Endings: 11 nodes
- Special: 1 node (DespairEvent)

**Total Choices**: ~100+ choice connections

**Dynamic Branches**: 12 branching decision points
