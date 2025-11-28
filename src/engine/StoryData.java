package engine;

import model.StoryNode;
import model.Choice;
import effect.*;
import condition.*;

import java.util.HashMap;

public class StoryData {
    // ================= RUMOR SYSTEM =================
    public static final String[] RUMORS = {
        "A woman whispers that her husband went to the medical wing and never came back.",
        "Two janitors argue about the 'incinerator schedule' increasing.",
        "You hear of a 'Director's List'—names of people about to be purged.",
        "An old man claims the clinic's 'treatments' make people more compliant.",
        "You overhear guards joking about 'tonight's collection' from Sector B.",
        "A child's drawing shows people being led away by figures in hazmat suits.",
        "Graffiti in the bathroom: 'THEY LIE' with an arrow pointing toward the vents."
    };

    // Hashmap to store all story nodes
    private static HashMap<String, StoryNode> storyNodes = new HashMap<>();

    static{
        // ================= ACT 1: THE JOURNEY =================
        
        StoryNode start = new StoryNode("Start",
            "The air tastes of rust and decay. Thirty days since the world fell silent.\n" +
            "You've been surviving in the ruins, scavenging what little remains.\n\n" +
            "A shadow detaches from the crumbling wall. 'You look like you're searching for something,' " +
            "a gravelly voice says. The figure keeps to the darkness, but you can make out the glint of scavenged tech on their gear.");
        start.addChoice(new Choice("\"I'm looking for answers. The truth about the outbreak.\"", "Show your determination", "MeetScavenger_Convince"));
        start.addChoice(new Choice("\"Just trying to survive, like everyone else.\"", "Play it safe", "MeetScavenger_Neutral"));
        start.addChoice(new Choice("Keep your distance and observe", "Better to assess the situation first", "ObserveScavenger"));
        storyNodes.put(start.getId(), start);

        StoryNode scavengerConvince = new StoryNode("MeetScavenger_Convince",
            "The scavenger steps closer, his eyes narrowing. 'Answers? Most people just want food or shelter.'\n\n" +
            "You meet his gaze. 'I've seen what the Haven is doing. The disappearances. This isn't just an outbreak.'\n\n" +
            "He studies you for a long moment, then nods slowly. 'You're not like the others. Name's Jax. Used to be a Haven guard.'\n" +
            "He presses a data chip into your hand. 'Find Cris. He's the only one who can stop this madness.'");
        scavengerConvince.addEffect(new MoraleEffect(2));
        scavengerConvince.addChoice(new Choice("\"Thank you. I won't waste this.\"", null, "JourneyChoices"));
        storyNodes.put(scavengerConvince.getId(), scavengerConvince);

        StoryNode scavengerNeutral = new StoryNode("MeetScavenger_Neutral",
            "The scavenger shakes his head. 'Survival? That's what they want you to think.'\n\n" +
            "'Look around you. This isn't natural. The pattern of infection... it's too organized.'\n\n" +
            "He holds up a data chip. 'This could save lives. But I need to know you're committed. Why should I trust you?'");
        scavengerNeutral.addChoice(new Choice("\"Because I'm still here when most aren't. I'm not giving up.\"", null, "MeetScavenger_Trust"));
        scavengerNeutral.addChoice(new Choice("\"I don't know. Maybe you shouldn't trust me.\"", null, "MeetScavenger_Doubt"));
        storyNodes.put(scavengerNeutral.getId(), scavengerNeutral);

        StoryNode scavengerTrust = new StoryNode("MeetScavenger_Trust",
            "Jax smiles grimly. 'Stubbornness I can work with.'\n\n" +
            "'Find Cris in the Haven. Show him this chip. Tell him Jax sent you.'\n" +
            "He presses the data chip into your hand and vanishes into the shadows.");
        scavengerTrust.addEffect(new MoraleEffect(1));
        scavengerTrust.addChoice(new Choice("\"Time to find this Cris.\"", null, "JourneyChoices"));
        storyNodes.put(scavengerTrust.getId(), scavengerTrust);

        StoryNode scavengerDoubt = new StoryNode("MeetScavenger_Doubt",
            "Jax sighs. 'Honesty. Refreshing, but disappointing.'\n\n" +
            "'I'll give you this much—find Cris in the Haven. If you change your mind, tell him Jax sent you.'\n" +
            "He tosses the chip into the rubble near you and disappears.");
        scavengerDoubt.addEffect(new MoraleEffect(-1));
        scavengerDoubt.addChoice(new Choice("\"Better take the chip and move on.\"", null, "JourneyChoices"));
        storyNodes.put(scavengerDoubt.getId(), scavengerDoubt);

        StoryNode observeScavenger = new StoryNode("ObserveScavenger",
            "You hang back, watching from behind a collapsed concrete pillar. " +
            "The scavenger paces nervously, checking a battered wrist device. " +
            "He mutters to himself: '...if they don't make it to Cris in time...'\n\n" +
            "After a moment, he seems to make a decision and tosses something into the rubble near you " +
            "before melting back into the shadows.");
        observeScavenger.addEffect(new EnergyEffect(-1));
        observeScavenger.addEffect(new KnowledgeEffect(1));
        observeScavenger.addEffect(new MoraleEffect(-1));
        observeScavenger.addChoice(new Choice("\"Take the chip and continue.\"", null, "JourneyChoices"));
        storyNodes.put(observeScavenger.getId(), observeScavenger);

        StoryNode journeyChoices = new StoryNode("JourneyChoices",
            "You stand at the edge of the dead city, looking toward where Haven supposedly lies. " +
            "The journey won't be easy. Each path costs energy—choose wisely.");
        journeyChoices.addChoice(new Choice("\"I'll take the safe route through the sewers.\"", "Slow but steady (-1 Energy)", "SlowTravel"));
        journeyChoices.addChoice(new Choice("\"I'll risk the open route for speed.\"", "Fast but dangerous (-2 Energy)", "FastTravel"));
        journeyChoices.addChoice(new Choice("\"Let me search for supplies first.\"", "Risk vs reward (-2 Energy)", "SearchTravel"));
        journeyChoices.addChoice(new Choice("\"I need to rest before continuing.\"", "No energy left", "RestBeforeJourney"));
        storyNodes.put(journeyChoices.getId(), journeyChoices);

        StoryNode restBeforeJourney = new StoryNode("RestBeforeJourney",
            "You find a relatively secure alcove in the ruins. As you rest, you think about Jax's words. " +
            "What kind of place is the Haven really? And who is Cris?\n\n" +
            "The rest does you good. (Energy restored)");
        restBeforeJourney.addEffect(new DayEffect(1));
        restBeforeJourney.addEffect(new MoraleEffect(1));
        restBeforeJourney.addEffect(new EnergyEffect(5));
        restBeforeJourney.addChoice(new Choice("\"Continue toward Haven.\"", null, "JourneyChoices"));
        storyNodes.put(restBeforeJourney.getId(), restBeforeJourney);

        StoryNode slowTravel = new StoryNode("SlowTravel",
            "You move through sewers and back alleys to avoid the infected. " +
            "The air is thick with the smell of decay, but you remain unseen and unhurt. " +
            "Three long days pass in tense silence.");
        slowTravel.addEffect(new EnergyEffect(-1));
        slowTravel.addEffect(new DayEffect(3));
        slowTravel.addEffect(new MoraleEffect(-1));
        slowTravel.addChoice(new Choice("\"Finally... the Haven.\"", null, "Arrival"));
        storyNodes.put(slowTravel.getId(), slowTravel);

        StoryNode fastTravelSuccess = new StoryNode("FastTravel_Success",
            "You sprint across the open highway, exposed to the elements and whatever else might be watching. " +
            "Your heart pounds with every shadow that moves.\n\n" +
            "\"Made it!\" You move fast and stay low, feeling a surge of confidence.");
        fastTravelSuccess.addEffect(new EnergyEffect(-2));
        fastTravelSuccess.addEffect(new DayEffect(1));
        fastTravelSuccess.addEffect(new MoraleEffect(1));
        fastTravelSuccess.addChoice(new Choice("\"The Haven gates... almost there.\"", null, "Arrival"));
        storyNodes.put(fastTravelSuccess.getId(), fastTravelSuccess);

        StoryNode fastTravelFailure = new StoryNode("FastTravel_Failure",
            "You sprint across the open highway, exposed to the elements and whatever else might be watching.\n\n" +
            "\"Damn it!\" You scramble over a razor-wire fence to avoid a patrol. The wire slices your arm deep.\n" +
            "Blood soaks through your makeshift bandage.");
        fastTravelFailure.addEffect(new EnergyEffect(-2));
        fastTravelFailure.addEffect(new DayEffect(1));
        fastTravelFailure.addEffect(new HealthEffect(-3));
        fastTravelFailure.addEffect(new MoraleEffect(-2));
        fastTravelFailure.addChoice(new Choice("\"The Haven gates... almost there.\"", null, "Arrival"));
        storyNodes.put(fastTravelFailure.getId(), fastTravelFailure);

        StoryNode searchTravelSuccess = new StoryNode("SearchTravel_Success",
            "You scour a collapsed pharmacy, the shelves mostly empty but maybe something useful remains...\n\n" +
            "\"Jackpot!\" You find a pristine Medkit tucked behind the counter. This could save your life.");
        searchTravelSuccess.addEffect(new EnergyEffect(-2));
        searchTravelSuccess.addEffect(new HealthEffect(5));
        searchTravelSuccess.addEffect(new MoraleEffect(1));
        searchTravelSuccess.addChoice(new Choice("\"Back to the main path.\"", null, "JourneyChoices"));
        storyNodes.put(searchTravelSuccess.getId(), searchTravelSuccess);

        StoryNode searchTravelFailure = new StoryNode("SearchTravel_Failure",
            "You scour a collapsed pharmacy, the shelves mostly empty but maybe something useful remains...\n\n" +
            "\"Watch out!\" A desperate looter attacks you with a knife! You fend him off, but not before taking a hit.\n" +
            "Your ribs ache where the blade grazed you.");
        searchTravelFailure.addEffect(new EnergyEffect(-2));
        searchTravelFailure.addEffect(new HealthEffect(-2));
        searchTravelFailure.addEffect(new MoraleEffect(-1));
        searchTravelFailure.addChoice(new Choice("\"Back to the main path.\"", null, "JourneyChoices"));
        storyNodes.put(searchTravelFailure.getId(), searchTravelFailure);

        StoryNode arrival = new StoryNode("Arrival",
            "You stand before the **Safe Haven** a massive concrete fortress rising from the ruins.\n" +
            "Guards patrol the perimeter with cold efficiency. Civilians walk in tired lines.\n\n" +
            "Through the crowd, you spot a scientist with armed escorts, could that be Cris?\n" +
            "You try to reach him, but the crowd surges and he's gone. Still, you've made it inside.");
        arrival.addEffect(new MoraleEffect(2));
        arrival.addChoice(new Choice("\"Find a bunk and settle in.\"", "If you have energy", "HavenIntro"));
        arrival.addChoice(new Choice("\"I can't... keep going...\" (Collapse)", "If no energy left", "ArrivalCollapse"));
        storyNodes.put(arrival.getId(), arrival);

        StoryNode arrivalCollapse = new StoryNode("ArrivalCollapse",
            "You wake up in a sterile white room. A tag on your wrist reads: SUBJECT-88.\n" +
            "The air smells of antiseptic and something else... fear?");
        arrivalCollapse.addEffect(new SuspicionEffect(3));
        arrivalCollapse.addEffect(new MoraleEffect(-2));
        arrivalCollapse.addEffect(new EnergyEffect(3));
        arrivalCollapse.addEffect(new DayEffect(2));
        arrivalCollapse.addChoice(new Choice("\"I need to find the dorms...\"", null, "HavenIntro"));
        storyNodes.put(arrivalCollapse.getId(), arrivalCollapse);

        // ================= ACT 2: THE HAVEN =================

        StoryNode havenIntro = new StoryNode("HavenIntro",
            "You are in the refugees' quarters, a converted gymnasium filled with cots.\n" +
            "People keep to themselves, avoiding eye contact. Something's not right here.\n\n" +
            "Days in Haven: (Track this - panic at 20 days)\n" +
            "Energy remaining: (Track this - auto-rest at 0)");
        havenIntro.addChoice(new Choice("\"I should listen to what people are saying.\"", "Maybe I can learn something useful... (-1 Energy)", "Mingle"));
        havenIntro.addChoice(new Choice("\"The medical wing seems important...\"", "What are they really doing in there? (-1 Energy)", "ObserveClinic"));
        havenIntro.addChoice(new Choice("\"Maybe I can find something useful in the dorms.\"", "People must leave clues behind... (-1 Energy)", "SearchDorms"));
        Choice libraryChoice = new Choice("\"The library might have answers.\"", "I need to understand the science behind this (Needs 5+ Knowledge, -1 Energy)", "Library");
        libraryChoice.addCondition(new KnowledgeCondition(5));
        havenIntro.addChoice(libraryChoice);
        
        havenIntro.addChoice(new Choice("\"Help in the kitchen.\"", "Blend in with workers (-1 Energy)", "WorkKitchen"));
        havenIntro.addChoice(new Choice("\"Do cleaning duty.\"", "Appear harmless (-1 Energy)", "CleaningDuty"));
        havenIntro.addChoice(new Choice("\"I should share my rations with that family.\"", "They look like they need it more than me (-1 Energy)", "ShareFood"));
        havenIntro.addChoice(new Choice("\"I'll just rest and watch the courtyard.\"", "Maybe I'm overthinking this... (-1 Energy)", "LeisurelyRest"));
        
        Choice riskChoice = new Choice("\"I'm going for the main office. No more playing safe.\"", "Time for desperate measures (High suspicion only, -2 Energy)", "RiskInformation");
        riskChoice.addCondition(new SuspicionCondition(6));
        havenIntro.addChoice(riskChoice);
        
        Choice dormNightChoice = new Choice("\"Tonight's the night. Time to meet Cris.\"", "This could be my only chance (After meeting scientist, -1 Energy)", "DormNight");
        dormNightChoice.addCondition(new FlagCondition("metScientist", true));
        havenIntro.addChoice(dormNightChoice);
        havenIntro.addChoice(new Choice("\"I need to rest for tomorrow.\"", "Tomorrow will be another day", "RestInDorm"));
        storyNodes.put(havenIntro.getId(), havenIntro);

        StoryNode mingleRumor = new StoryNode("Mingle_Rumor",
            "You listen to the refugees' whispered conversations.\n\n" +
            "An old woman leans close: \"My husband went to the medical wing and never came back. They said he was 'processed.'\"\n\n" +
            "\"Interesting,\" you whisper to yourself. The pieces are coming together.");
        mingleRumor.addEffect(new EnergyEffect(-1));
        mingleRumor.addEffect(new KnowledgeEffect(1));
        mingleRumor.addEffect(new MoraleEffect(-1));
        mingleRumor.addChoice(new Choice("\"Back to the dorms.\"", null, "HavenIntro"));
        storyNodes.put(mingleRumor.getId(), mingleRumor);

        StoryNode mingleNothing = new StoryNode("Mingle_Nothing",
            "You listen to the refugees' whispered conversations.\n\n" +
            "\"Nothing useful today,\" you mutter. People are keeping to themselves, too afraid to talk.");
        mingleNothing.addEffect(new EnergyEffect(-1));
        mingleNothing.addChoice(new Choice("\"Back to the dorms.\"", null, "HavenIntro"));
        storyNodes.put(mingleNothing.getId(), mingleNothing);

        StoryNode observeClinicCaught = new StoryNode("ObserveClinic_Caught",
            "You watch the medical wing from a distance, trying to look casual.\n\n" +
            "\"Move along, subject,\" a guard snarls, catching you loitering. His hand rests on his weapon.\n" +
            "\"Just curious,\" you say quickly, backing away. He's watching you now.");
        observeClinicCaught.addEffect(new EnergyEffect(-1));
        observeClinicCaught.addEffect(new SuspicionEffect(1));
        observeClinicCaught.addEffect(new MoraleEffect(-1));
        observeClinicCaught.addChoice(new Choice("\"Better get back.\"", null, "HavenIntro"));
        storyNodes.put(observeClinicCaught.getId(), observeClinicCaught);

        StoryNode observeClinicLearn = new StoryNode("ObserveClinic_Learn",
            "You watch the medical wing from a distance, staying in the shadows.\n\n" +
            "\"What are they hiding?\" you wonder, watching a large van leave the bay. The side is marked \"Non-viable Samples.\"\n" +
            "Your blood runs cold. What does that mean?");
        observeClinicLearn.addEffect(new EnergyEffect(-1));
        observeClinicLearn.addEffect(new KnowledgeEffect(1));
        observeClinicLearn.addChoice(new Choice("\"Better get back.\"", null, "HavenIntro"));
        storyNodes.put(observeClinicLearn.getId(), observeClinicLearn);

        StoryNode searchDormsCaught = new StoryNode("SearchDorms_Caught",
            "You search through the sleeping areas looking for clues.\n\n" +
            "\"Looking for something?\" a guard asks suspiciously, appearing from nowhere.\n" +
            "\"Just... checking if this bed is taken,\" you stammer. He narrows his eyes but says nothing.\n" +
            "You feel his gaze follow you as you leave.");
        searchDormsCaught.addEffect(new EnergyEffect(-1));
        searchDormsCaught.addEffect(new SuspicionEffect(3));
        searchDormsCaught.addEffect(new MoraleEffect(-2));
        searchDormsCaught.addChoice(new Choice("\"Back to safety.\"", null, "HavenIntro"));
        storyNodes.put(searchDormsCaught.getId(), searchDormsCaught);

        StoryNode searchDormsFind = new StoryNode("SearchDorms_FindNote",
            "You search through the sleeping areas looking for clues.\n\n" +
            "\"This could be important,\" you whisper, finding a crumpled note under a mattress.\n" +
            "Written in shaky handwriting: \"4-1-B\". A code? An ID? You pocket it quickly.");
        searchDormsFind.addEffect(new EnergyEffect(-1));
        searchDormsFind.addEffect(new KnowledgeEffect(1));
        searchDormsFind.addChoice(new Choice("\"Back to safety.\"", null, "HavenIntro"));
        storyNodes.put(searchDormsFind.getId(), searchDormsFind);

        StoryNode workKitchen = new StoryNode("WorkKitchen",
            "\"Good worker,\" a guard grunts as you scrub pots. \"Stay out of trouble.\"\n\n" +
            "\"Just trying to help,\" you reply, accepting extra rations from the cook. (Suspicion -1, Health +1)");
        workKitchen.addEffect(new EnergyEffect(-1));
        workKitchen.addEffect(new SuspicionEffect(-1));
        workKitchen.addEffect(new HealthEffect(1));
        workKitchen.addEffect(new MoraleEffect(1));
        workKitchen.addChoice(new Choice("\"Back to the dorms.\"", null, "HavenIntro"));
        storyNodes.put(workKitchen.getId(), workKitchen);

        StoryNode cleaningDuty = new StoryNode("CleaningDuty",
            "You mop the endless corridors, making sure to look busy but harmless.\n\n" +
            "\"At least I'm blending in,\" you think, noticing the guards pay you no mind. (Suspicion -1)");
        cleaningDuty.addEffect(new EnergyEffect(-1));
        cleaningDuty.addEffect(new SuspicionEffect(-1));
        cleaningDuty.addEffect(new MoraleEffect(1));
        cleaningDuty.addChoice(new Choice("\"Time to head back.\"", null, "HavenIntro"));
        storyNodes.put(cleaningDuty.getId(), cleaningDuty);

        StoryNode leisurelyRest = new StoryNode("LeisurelyRest",
            "You find a quiet spot and watch people. For a moment, you can almost pretend this is normal life.\n\n" +
            "\"Maybe I'm being paranoid,\" you think, then remember Jax's warning.");
        leisurelyRest.addEffect(new EnergyEffect(-1));
        leisurelyRest.addEffect(new MoraleEffect(2));
        leisurelyRest.addChoice(new Choice("\"Back to reality.\"", null, "HavenIntro"));
        storyNodes.put(leisurelyRest.getId(), leisurelyRest);

        StoryNode shareFood = new StoryNode("ShareFood",
            "\"Thank you,\" the mother whispers, her children devouring the extra rations.\n\n" +
            "\"We're all in this together,\" you reply, feeling a glimmer of hope.");
        shareFood.addEffect(new EnergyEffect(-1));
        shareFood.addEffect(new MoraleEffect(2));
        shareFood.addChoice(new Choice("\"Back to the dorms.\"", null, "HavenIntro"));
        storyNodes.put(shareFood.getId(), shareFood);

        StoryNode riskInformationSuccess = new StoryNode("RiskInformation_Success",
            "You sneak toward the main office, heart pounding. Every shadow could be a guard.\n\n" +
            "\"Got it!\" you whisper, grabbing crucial data files before vanishing into the crowd.\n" +
            "\"This could save everyone,\" you think, clutching the stolen documents.");
        riskInformationSuccess.addChoice(new Choice("\"Back to the dorms.\"", null, "HavenIntro"));
        storyNodes.put(riskInformationSuccess.getId(), riskInformationSuccess);

        StoryNode riskInformationCaught = new StoryNode("RiskInformation_Caught",
            "You sneak toward the main office, heart pounding. Every shadow could be a guard.\n\n" +
            "\"Damn!\" A security drone spots you! Red lights flash as alarms blare.\n" +
            "You run, but you know they've marked you. There's no escape now.");
        riskInformationCaught.addChoice(new Choice("\"Back to the dorms.\"", null, "HavenIntro"));
        storyNodes.put(riskInformationCaught.getId(), riskInformationCaught);

        StoryNode libraryLowKnowledge = new StoryNode("Library_LowKnowledge",
            "You browse the library, looking for information about the outbreak.\n\n" +
            "\"This map could be useful,\" you think, finding detailed facility layouts hidden in a book.\n" +
            "You memorize as much as you can before leaving.");
        libraryLowKnowledge.addChoice(new Choice("\"Better leave before someone notices.\"", null, "HavenIntro"));
        storyNodes.put(libraryLowKnowledge.getId(), libraryLowKnowledge);

        StoryNode libraryHighKnowledge = new StoryNode("Library_HighKnowledge",
            "You browse advanced virology texts, piecing together the puzzle.\n\n" +
            "As you close a heavy book, a man in a pristine lab coat notices you.\n" +
            "\"Advanced reading for a refugee,\" he observes, his eyes sharp with intelligence.");
        libraryHighKnowledge.addChoice(new Choice("\"Talk to the scientist.\"", null, "ScientistApproaches"));
        storyNodes.put(libraryHighKnowledge.getId(), libraryHighKnowledge);

        StoryNode scientistApproaches = new StoryNode("ScientistApproaches",
            "\"Advanced virology texts,\" the man murmurs, fingers tracing the book cover. \"Most refugees are looking for food maps. Why this?\"\n\n" +
            "When you show him the data chip, his composure cracks. \"I'm Cris. That chip... it shouldn't exist outside secure storage.\"\n\n" +
            "He glances around nervously. \"The ventilation system, Section-1 access shaft. Meet me there tonight. Don't get caught.\"");
        scientistApproaches.addChoice(new Choice("\"I'll be there.\"", null, "HavenIntro"));
        storyNodes.put(scientistApproaches.getId(), scientistApproaches);

        StoryNode dormNight = new StoryNode("DormNight",
            "Night falls. The dorm is filled with uneasy sounds—whimpers, snores, occasional cries.\n\n" +
            "\"It's now or never,\" you whisper to yourself, checking that the data chip is secure.");
        dormNight.addChoice(new Choice("\"Time to find Section-1.\"", null, "Section1Gate"));
        storyNodes.put(dormNight.getId(), dormNight);

        StoryNode section1GateFail = new StoryNode("Section1Gate_Fail",
            "You stand before the access gate to Section-1. There's a keypad.\n\n" +
            "\"I don't know the code!\" you panic as the keypad blurs. You try random numbers.\n" +
            "BEEP BEEP BEEP—Alarm! Red lights flash as guards shout in the distance.");
        section1GateFail.addChoice(new Choice("\"Run!\"", null, "HavenIntro"));
        storyNodes.put(section1GateFail.getId(), section1GateFail);

        StoryNode section1GateSuccess = new StoryNode("Section1Gate_Success",
            "You stand before the access gate to Section-1. There's a keypad.\n\n" +
            "\"4-1-B,\" you whisper, fingers flying across the keypad. You remember the note from the dorms.\n" +
            "A green light flashes. The door hisses open. \"I'm in.\"");
        section1GateSuccess.addChoice(new Choice("\"Enter Section-1.\"", null, "Section1"));
        storyNodes.put(section1GateSuccess.getId(), section1GateSuccess);

        StoryNode section1 = new StoryNode("Section1",
            "You slip into darkness. The air smells of ozone and metal.\n\n" +
            "Cris waits in a small lab, illuminated by a single monitor. Dark circles shadow his eyes.\n" +
            "\"You made it,\" he whispers, relief evident.");
        section1.addChoice(new Choice("\"What's really happening here?\"", null, "PurgeReveal"));
        storyNodes.put(section1.getId(), section1);

        StoryNode restInDorm = new StoryNode("RestInDorm",
            "You collapse onto your cot, surrounded by the sounds of sleeping refugees.\n\n" +
            "\"Just a few hours of peace,\" you think before drifting off.\n\n" +
            "(A new day begins - Energy fully restored, +1 day, Haven day counter +1)");
        restInDorm.addEffect(new DayEffect(1));
        restInDorm.addEffect(new MoraleEffect(1));
        restInDorm.addEffect(new EnergyEffect(5));
        restInDorm.addChoice(new Choice("(Auto-continues to next day)", null, "HavenIntro"));
        storyNodes.put(restInDorm.getId(), restInDorm);

        // ================= ACT 3: PURGE PROTOCOL =================

        StoryNode purgeReveal = new StoryNode("PurgeReveal",
            "Cris runs a hand through his messy hair. \"They call it Protocol Omega,\" he says quietly. \"In seven days, they're gassing the entire refugee population.\"\n\n" +
            "\"They claim it's containment, but it's extermination. Your data chip proves they've been planning this from the start.\"\n\n" +
            "He gestures to the ventilation shafts. \"The research hub is through there. Get to 12 Knowledge points. With that evidence, we might stop this.\"");
        purgeReveal.addChoice(new Choice("\"I'll do it. What should I look for?\"", null, "ResearchHub"));
        purgeReveal.addChoice(new Choice("\"I can't... this is too much.\"", null, "ComfortBadEnd"));
        storyNodes.put(purgeReveal.getId(), purgeReveal);

        StoryNode researchHub = new StoryNode("ResearchHub",
            "RESEARCH HUB VENTS - A maze of metal and whispered secrets\n" +
            "Goal: 12 Knowledge to expose the truth.\n\n" +
            "PURGE COUNTDOWN: (Track days remaining)\n" +
            "Current Knowledge: (Show current/12)\n" +
            "Energy remaining: (Show current/max)");
        researchHub.addChoice(new Choice("\"I'll steal lab samples.\"", "High risk, but necessary (Random: +3 Knowledge OR -3 Health +3 Suspicion, -1 Energy)", "StealSamples"));
        researchHub.addChoice(new Choice("\"I'll spy on guard conversations.\"", "Learn what I can safely (+1 Knowledge, -1 Energy)", "SpyGuards"));
        researchHub.addChoice(new Choice("\"I'll question a friendly scientist.\"", "Find allies in the system (+2 Knowledge +2 Suspicion, -1 Energy)", "TalkScientist"));
        researchHub.addChoice(new Choice("\"I'm ready to confront the truth.\"", "No turning back now", "FinalCheck"));
        researchHub.addChoice(new Choice("\"I must rest... no energy left.\"", "Completely exhausted", "RestAction"));
        storyNodes.put(researchHub.getId(), researchHub);

        StoryNode stealSamplesSuccess = new StoryNode("StealSamples_Success",
            "You attempt to steal lab samples from the research area.\n\n" +
            "\"Success!\" You obtain vials of the purge agent, tucking them carefully into your jacket.\n" +
            "\"This could save everyone,\" you think, heart racing with the evidence in hand.");
        stealSamplesSuccess.addChoice(new Choice("\"Back to hiding.\"", null, "ResearchHub"));
        storyNodes.put(stealSamplesSuccess.getId(), stealSamplesSuccess);

        StoryNode stealSamplesCaught = new StoryNode("StealSamples_Caught",
            "You attempt to steal lab samples from the research area.\n\n" +
            "\"They spotted me!\" You barely escape as shots ring out. A bullet grazes your shoulder.\n" +
            "\"That was too close,\" you gasp, blood seeping through your clothes.");
        stealSamplesCaught.addChoice(new Choice("\"Back to hiding.\"", null, "ResearchHub"));
        storyNodes.put(stealSamplesCaught.getId(), stealSamplesCaught);

        StoryNode spyGuards = new StoryNode("SpyGuards",
            "\"Overtime for the big cleanse,\" you hear a guard complain. You piece together chemical formulas.\n\n" +
            "\"Now I understand how they're doing it,\" you realize. (Knowledge +1)");
        spyGuards.addChoice(new Choice("\"Continue gathering information.\"", null, "ResearchHub"));
        storyNodes.put(spyGuards.getId(), spyGuards);

        StoryNode talkScientist = new StoryNode("TalkScientist",
            "\"It's true,\" the nervous researcher confirms during his break. \"Everything Cris said.\"\n\n" +
            "\"At least someone here has a conscience,\" you think. (Knowledge +2, Suspicion +2)");
        talkScientist.addChoice(new Choice("\"Better get back to hiding.\"", null, "ResearchHub"));
        storyNodes.put(talkScientist.getId(), talkScientist);

        StoryNode restAction = new StoryNode("RestAction",
            "You force down dry rations in a quiet corner. Sleep comes fitfully, haunted by what you've learned.\n\n" +
            "\"Tomorrow, I finish this,\" you vow. (A new day begins - Energy restored, +1 day, Purge timer -1)");
        restAction.addChoice(new Choice("\"Wake up and continue.\"", null, "ResearchHub"));
        storyNodes.put(restAction.getId(), restAction);

        // ================= ACT 4: FINALE =================

        StoryNode finalCheckReady = new StoryNode("FinalCheck_Ready",
            "You review everything you've gathered. Formulas, deployment plans, witness statements.\n\n" +
            "\"I have everything,\" you whisper, clutching the documents. \"The full truth.\"\n" +
            "This is enough to expose them. Time to end this.");
        finalCheckReady.addChoice(new Choice("\"I'm going to Cris with the evidence.\"", null, "CureOptions"));
        storyNodes.put(finalCheckReady.getId(), finalCheckReady);

        StoryNode finalCheckNotReady = new StoryNode("FinalCheck_NotReady",
            "You review what you've gathered so far. It's not enough.\n\n" +
            "\"There are still gaps,\" you worry, checking your notes. \"Missing pieces that could mean everything.\"\n" +
            "Do you risk one final grab, or gather more carefully?");
        finalCheckNotReady.addChoice(new Choice("\"I have to risk one final information grab.\"", null, "LastChance"));
        finalCheckNotReady.addChoice(new Choice("\"I need more evidence first.\"", null, "ResearchHub"));
        storyNodes.put(finalCheckNotReady.getId(), finalCheckNotReady);

        StoryNode lastChance = new StoryNode("LastChance",
            "\"This is it,\" you think, heart pounding. \"No second chances. One desperate move for the truth.\"\n\n" +
            "Are you willing to bet everything?");
        lastChance.addChoice(new Choice("\"Yes - it's now or never.\"", null, "FinalHeist"));
        lastChance.addChoice(new Choice("\"No - I need to be more careful.\"", null, "ResearchHub"));
        storyNodes.put(lastChance.getId(), lastChance);

        StoryNode finalHeistSuccess = new StoryNode("FinalHeist_Success",
            "You attempt one final desperate information grab.\n\n" +
            "\"Against all odds!\" You snatch the final documents as alarms blare throughout the facility.\n" +
            "\"I have everything I need!\" You run through the chaos, the complete truth in your hands.");
        finalHeistSuccess.addChoice(new Choice("\"Escape to Cris!\"", null, "EscapeEnd"));
        storyNodes.put(finalHeistSuccess.getId(), finalHeistSuccess);

        StoryNode finalHeistFailure = new StoryNode("FinalHeist_Failure",
            "You attempt one final desperate information grab.\n\n" +
            "\"ACCESS DENIED!\" The terminal flashes red as guards approach from both sides.\n" +
            "\"It's over...\" you realize, as they surround you. You fought hard, but it wasn't enough.");
        finalHeistFailure.addChoice(new Choice("\"There's no escape...\"", null, "Collapse"));
        storyNodes.put(finalHeistFailure.getId(), finalHeistFailure);

        StoryNode cureOptions = new StoryNode("CureOptions",
            "\"You did it,\" Cris breathes, staring at the documents in your hands.\n\n" +
            "\"Now we face the hardest choice. What do we do with the truth?\"");
        cureOptions.addChoice(new Choice("\"We escape together and rebuild elsewhere.\"", null, "EscapeEnd"));
        cureOptions.addChoice(new Choice("\"We broadcast the truth to the outside world.\"", null, "ExposeEnd"));
        cureOptions.addChoice(new Choice("\"We take the evidence to the military.\"", null, "MilEnd"));
        storyNodes.put(cureOptions.getId(), cureOptions);

        StoryNode despairEvent = new StoryNode("DespairEvent",
            "Hopelessness washes over you. What's the point of fighting when everything is already lost?\n" +
            "The weight of the dying world feels unbearable. Maybe it would be easier to just... stop.\n\n" +
            "But then you remember Jax's words, Cris's desperation. People are counting on you.");
        despairEvent.addChoice(new Choice("\"I have to keep going. For everyone.\"", "If in Haven", "HavenIntro"));
        despairEvent.addChoice(new Choice("\"I can't give up now. The truth matters.\"", "If in Research Hub", "ResearchHub"));
        storyNodes.put(despairEvent.getId(), despairEvent);

        // ================= ENDINGS =================

        StoryNode badEnd = new StoryNode("BadEnd",
            "ENDING: ANOTHER CASUALTY\n\n" +
            "You join the countless others who fell to the outbreak. The world moves on, never knowing you were here.\n\n" +
            "In the grand scheme, one life rarely matters. But it mattered to you, and sometimes that has to be enough.\n\n" +
            "GAME OVER");
        badEnd.addChoice(new Choice("[Restart Game]", null, "Start"));
        storyNodes.put(badEnd.getId(), badEnd);

        StoryNode arrestedEnd = new StoryNode("ArrestedEnd",
            "ENDING: SUBJECT ZERO\n\n" +
            "They strap you to a cold metal table. \"Interesting resistance to the pathogen,\" a masked figure notes.\n\n" +
            "Your body becomes their laboratory. Your suffering becomes their data. The cure, when it comes, " +
            "will bear the price of your endless pain.\n\n" +
            "GAME OVER");
        arrestedEnd.addChoice(new Choice("[Restart Game]", null, "Start"));
        storyNodes.put(arrestedEnd.getId(), arrestedEnd);

        StoryNode havenPanicEnd = new StoryNode("HavenPanicEnd",
            "ENDING: TOO LATE\n\n" +
            "You fight through the screaming crowds, but the exits are sealed. Guards in hazmat suits " +
            "fire into the panicked masses. The air begins to taste metallic... then sweet.\n\n" +
            "You collapse as the gas takes effect. The last thing you see is Cris being dragged away, " +
            "shouting something you can no longer hear.\n\n" +
            "\"I waited too long...\" you think as everything fades to black.\n\n" +
            "GAME OVER");
        havenPanicEnd.addChoice(new Choice("[Restart Game]", null, "Start"));
        storyNodes.put(havenPanicEnd.getId(), havenPanicEnd);

        StoryNode purgeEnd = new StoryNode("PurgeEnd",
            "ENDING: CLEANSED\n\n" +
            "The gas floods the vents exactly on schedule. You take your final breath surrounded by the evidence that could have saved everyone.\n\n" +
            "The Haven reports another 'successful containment operation.' The world never learns the truth.\n\n" +
            "GAME OVER");
        purgeEnd.addChoice(new Choice("[Restart Game]", null, "Start"));
        storyNodes.put(purgeEnd.getId(), purgeEnd);

        StoryNode comfortBadEnd = new StoryNode("ComfortBadEnd",
            "ENDING: THE SILENT VICTIM\n\n" +
            "You fade into the crowd, choosing safety over truth. The purge happens as scheduled.\n\n" +
            "You disappear into the statistics—another number in the body count. The world never knows " +
            "that someone could have made a difference, if only they had tried.\n\n" +
            "GAME OVER");
        comfortBadEnd.addChoice(new Choice("[Restart Game]", null, "Start"));
        storyNodes.put(comfortBadEnd.getId(), comfortBadEnd);

        StoryNode collapse = new StoryNode("Collapse",
            "ENDING: THE FAILURE\n\n" +
            "The guards drag you away as the lab self-destructs behind you. The cure formula dies with your hope.\n\n" +
            "Sometimes, the world doesn't need a hero—it just needs someone who tried. You were that someone, " +
            "and for a moment, that was enough.\n\n" +
            "GAME OVER");
        collapse.addChoice(new Choice("[Restart Game]", null, "Start"));
        storyNodes.put(collapse.getId(), collapse);

        StoryNode escapeEndAlliance = new StoryNode("EscapeEnd_Alliance",
            "ENDING: THE ALLIANCE\n\n" +
            "\"I'm with you,\" Cris says without hesitation. Together, you slip into the night.\n\n" +
            "Weeks later, you establish a hidden research facility. Working side by side, you develop a counter-agent.\n" +
            "\"We can undo this,\" Cris says, looking at your work.\n\n" +
            "For the first time, the future feels worth fighting for.\n\n" +
            "GAME COMPLETE - BEST ENDING");
        escapeEndAlliance.addChoice(new Choice("[Restart Game]", null, "Start"));
        storyNodes.put(escapeEndAlliance.getId(), escapeEndAlliance);

        StoryNode escapeEndFugitive = new StoryNode("EscapeEnd_Fugitive",
            "ENDING: THE FUGITIVE\n\n" +
            "\"I have to stay and fight from within,\" Cris says. You escape alone into the dead city.\n\n" +
            "You're hunted and alone, but the cure formula burns in your memory.\n" +
            "\"Survival is victory,\" you whisper, watching every shadow.\n\n" +
            "It never gets easier, but you never stop.\n\n" +
            "GAME COMPLETE");
        escapeEndFugitive.addChoice(new Choice("[Restart Game]", null, "Start"));
        storyNodes.put(escapeEndFugitive.getId(), escapeEndFugitive);

        StoryNode escapeEndSavior = new StoryNode("EscapeEnd_Savior",
            "ENDING: THE SAVIOR\n\n" +
            "\"Go,\" Cris says, pressing a data chip into your hand. \"I'll create a distraction.\"\n\n" +
            "You slip into the night alone, hunted but carrying the future.\n" +
            "\"Someone has to try,\" you think, the formula secure in your mind.\n\n" +
            "You don't know if you'll survive, but you know you have to.\n\n" +
            "GAME COMPLETE");
        escapeEndSavior.addChoice(new Choice("[Restart Game]", null, "Start"));
        storyNodes.put(escapeEndSavior.getId(), escapeEndSavior);

        StoryNode exposeEnd = new StoryNode("ExposeEnd",
            "ENDING: THE MARTYR\n\n" +
            "You upload the data to every surviving network. The truth spreads like wildfire.\n\n" +
            "They kill you, of course. But as the screens go dark, revolutions ignite across the wasteland. " +
            "Your death becomes the spark that burns the old world down, making room for something new.\n\n" +
            "GAME COMPLETE");
        exposeEnd.addChoice(new Choice("[Restart Game]", null, "Start"));
        storyNodes.put(exposeEnd.getId(), exposeEnd);

        StoryNode milEnd = new StoryNode("MilEnd",
            "ENDING: THE STRATEGIST\n\n" +
            "The military distributes the cure under martial law. Order is restored, but freedom becomes a memory.\n\n" +
            "You're given a comfortable cell and three meals a day. The world is safe, clean, and utterly controlled. " +
            "Sometimes you wonder if the price was worth it, but the question no longer matters.\n\n" +
            "GAME COMPLETE");
        milEnd.addChoice(new Choice("[Restart Game]", null, "Start"));
        storyNodes.put(milEnd.getId(), milEnd);
    }

    public static StoryNode getNode(String id){
        return storyNodes.get(id);
    }
}   


