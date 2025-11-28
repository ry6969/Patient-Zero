package model;

import condition.Condition;
import java.util.ArrayList;
import java.util.List;

public class Choice {
    private String text;
    private String subtext;
    private String nextNodeId;
    private List<Condition> conditions;

    public Choice(String text, String subtext, String nextNodeID){
        this.text = text;
        this.subtext = subtext;
        this.nextNodeId = nextNodeID;
        this.conditions = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public String getSubText(){
        return subtext;
    }

    public String getNextNodeId(){
        return nextNodeId;
    }

    /**
     * Adds a condition that must be met for this choice to be available.
     */
    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    /**
     * Gets all conditions for this choice.
     */
    public List<Condition> getConditions() {
        return conditions;
    }

    /**
     * Checks if this choice is available to the player.
     * A choice is available only if ALL conditions are met.
     */
    public boolean isAvailable(Player player) {
        for (Condition condition : conditions) {
            if (!condition.isMet(player)) {
                return false;
            }
        }
        return true;
    }
}
