package model;

public class Player {
    private int health;
    private int energy;
    private int knowledge;
    private int suspicion;
    private int day;

    //Constructor
    public Player(int health, int energy, int knowledge, int suspicion, int day){
        this.health = health;
        this.energy = energy;
        this.knowledge = knowledge;
        this.suspicion = suspicion;
        this.day = day;
    }

    public int getHealth(){
        return health;
    }

    public int getEnergy(){
        return energy;
    }

    public int getKnowledge(){
        return knowledge;
    }

    public int getSuspicion(){
        return suspicion;
    }

    public int getDay(){
        return day;
    }

    // Setter

    public void setHealth(int health){
        this.health = health;
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public void setKnowledge(int knowledge){
        this.knowledge = knowledge;
    }

    public void setSuspicion(int suspicion){
        this.suspicion = suspicion;
    }

    public void setDay(int day){
        this.day = day;
    }

    // Utility Methods
    public void changeHealth(int delta) {
        this.health += delta;
    }

    public void changeEnergy(int delta) {
        this.energy += delta;
    }

    public void changeKnowledge(int delta) {
        this.knowledge += delta;
    }

    public void changeSuspicion(int delta) {
        this.suspicion += delta;
    }

    public void advanceDay(int delta) {
        this.day += delta;
    }
}
