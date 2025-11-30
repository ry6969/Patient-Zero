package model;

import condition.Condition;
import java.util.ArrayList;
import java.util.List;

public class Choice {
    private String text;
    private String subtext;
    private String nextNodeId;
    private List<Condition> conditions;
    private boolean inverted = false;

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

    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    public List<Condition> getConditions() {
        return conditions;
    }
    
    public boolean isAvailable(Player player) {
        boolean conditionsMet = true; //
        for (Condition condition : conditions) {
            if (!condition.isMet(player)) {
                conditionsMet = false; //
                break; //
            }
        }

        if (inverted) { //
            return !conditionsMet; //
        }

        return conditionsMet; //
    }

    public void setInverted(boolean inverted) { //
        this.inverted = inverted; //
    }

    public boolean isInverted() { //
        return inverted; //
    }

}
