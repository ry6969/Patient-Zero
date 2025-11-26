package model;

public class Choice {
    private String text;
    private String subtext;
    private String nextNodeId;

    public Choice(String text, String subtext, String nextNodeID){
        this.text = text;
        this.subtext = subtext;
        this.nextNodeId = nextNodeID;
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
}
