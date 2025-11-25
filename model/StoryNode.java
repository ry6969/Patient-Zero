package model;

import java.util.List;
import java.util.ArrayList;

public class StoryNode {
    private String id;
    private String story;
    private List<Choice> choices = new ArrayList<>();

    public StoryNode(String id, String story){
        this.id = id;
        this.story = story;
    }

    public void addChoice(Choice c){
        choices.add(c);
    }

    public List<Choice> getChoices(){
        return choices;
    }

    public String getId() {return id;}
    public String getStory() {return story;}

    
}




