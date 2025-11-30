package model;

import java.util.List;
import java.util.ArrayList;
import effect.Effect;

public class StoryNode {
    private String id;
    private String story;
    private List<Choice> choices = new ArrayList<>();
    private List<Effect> effects = new ArrayList<>();

    public StoryNode(String id, String story){
        this.id = id;
        this.story = story;
    }

    public void setStory(String newStory) { // for mingle_rumor
        this.story = newStory;
    }

    public void addChoice(Choice c){
        choices.add(c);
    }

    public List<Choice> getChoices(){
        return choices;
    }

    public void addEffect(Effect e){
        effects.add(e);
    }

    public List<Effect> getEffects(){
        return effects;
    }

    public String getId() {return id;}
    public String getStory() {return story;}
}




