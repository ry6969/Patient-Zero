package engine;

import model.StoryNode;
import model.Choice;

import java.util.HashMap;

public class StoryData {
    // Hashmap to store all story nodes
    private static HashMap<String, StoryNode> storyNodes = new HashMap<>();

    static{

        // Create the story nodes
 
        StoryNode startNode = new StoryNode(
        "Start",
            "The world has fallen silent.\n\n" +
            "Thirty days since the outbreak. The dust clings to your boots as you search for answers in the ruin of a city.\n\n" +
            "Ahead: a broken medical crate. Empty.\n" +
            "A figure emerges from the shadows, face hidden behind a battered gas mask.\n\n" +
            "\"Looking for something specific?\" he asks.");
        startNode.addChoice(new Choice("Talk to him", null, "MeetScavenger"));
        storyNodes.put(startNode.getId(),startNode);
        
        // TODO: add other nodes
    }

    public static StoryNode getNode(String id){
        return storyNodes.get(id);
    }
}   


