package model;

public class Player {
    // Core Stats
    private int health;
    private int energy;
    private int knowledge;
    private int suspicion;
    private int day;
    
    // Additional Stats
    private int morale;
    private String zone;
    private int havenDays;
    private int purgeCountdown;
    private boolean purgeActive;
    private int clinicVisits;
    private boolean metScientist;
    private int crisRelationship;
    
    // Rumor Tracking (7 rumors total, indexed 0-6)
    private boolean[] heardRumors;
    
    private boolean metScavenger;
    private int jaxRelationship;

    // Constructor
    public Player(int health, int energy, int knowledge, int suspicion, int day) {
        this.health = health;
        this.energy = energy;
        this.knowledge = knowledge;
        this.suspicion = suspicion;
        this.day = day;
        
        // Initialize with defaults
        this.morale = 5; 
        this.zone = "Wasteland"; // Starting zone
        this.havenDays = 0;
        this.purgeCountdown = 0;
        this.purgeActive = false;
        this.clinicVisits = 0;
        this.metScientist = false;
        this.crisRelationship = 0;
        
        this.heardRumors = new boolean[7]; // All false by default
        
        this.metScavenger = false;
        this.jaxRelationship = 0;
    }

    // CORE STAT GETTERS
    
    public int getHealth() {
        return health;
    }

    public int getEnergy() {
        return energy;
    }

    public int getKnowledge() {
        return knowledge;
    }

    public int getSuspicion() {
        return suspicion;
    }

    public int getDay() {
        return day;
    }

    // ADDITIONAL STAT GETTERS
    
    public int getMorale() {
        return morale;
    }

    public String getZone() {
        return zone;
    }

    public int getHavenDays() {
        return havenDays;
    }

    public int getPurgeCountdown() {
        return purgeCountdown;
    }

    public boolean isPurgeActive() {
        return purgeActive;
    }

    public int getClinicVisits() {
        return clinicVisits;
    }

    public boolean hasMetScientist() {
        return metScientist;
    }

    public int getCrisRelationship() {
        return crisRelationship;
    }

    public boolean hasMetScavenger() {
        return metScavenger;
    }

    public int getJaxRelationship() {
        return jaxRelationship;
    }

    // CORE STAT SETTERS
    
    public void setHealth(int health) {
        this.health = health;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setKnowledge(int knowledge) {
        this.knowledge = knowledge;
    }

    public void setSuspicion(int suspicion) {
        this.suspicion = suspicion;
    }

    public void setDay(int day) {
        this.day = day;
    }

    // ADDITIONAL STAT SETTERS
    
    public void setMorale(int morale) {
        this.morale = morale;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setHavenDays(int havenDays) {
        this.havenDays = havenDays;
    }

    public void setPurgeCountdown(int purgeCountdown) {
        this.purgeCountdown = purgeCountdown;
    }

    public void setPurgeActive(boolean purgeActive) {
        this.purgeActive = purgeActive;
    }

    public void setClinicVisits(int clinicVisits) {
        this.clinicVisits = clinicVisits;
    }

    public void setMetScientist(boolean metScientist) {
        this.metScientist = metScientist;
    }

    public void setCrisRelationship(int crisRelationship) {
        this.crisRelationship = crisRelationship;
    }

    public void setMetScavenger(boolean metScavenger) {
        this.metScavenger = metScavenger;
    }

    public void setJaxRelationship(int jaxRelationship) {
        this.jaxRelationship = jaxRelationship;
    }

    // CHANGE UTILITY METHODS
    
    public void changeHealth(int num) {
        this.health += num;
    }

    public void changeEnergy(int num) {
        this.energy += num;
        // Cap energy at 5
        if (this.energy > 5) {
            this.energy = 5;
        }
        // Don't let energy go below 0
        if (this.energy < 0) {
            this.energy = 0;
        }
    }

    public void changeKnowledge(int num) {
        this.knowledge += num;
    }

    public void changeSuspicion(int num) {
        this.suspicion += num;
    }

    public void changeMorale(int num) {
        this.morale += num;
    }

    public void advanceDay(int num) {
        this.day += num;
    }

    public void advanceHavenDays(int num) {
        this.havenDays += num;
    }

    public void decrementPurgeCountdown() {
        if (this.purgeActive && this.purgeCountdown > 0) {
            this.purgeCountdown--;
        }
    }

    public void incrementClinicVisits() {
        this.clinicVisits++;
    }

    public void changeCrisRelationship(int num) {
        this.crisRelationship += num;
    }

    public void changeJaxRelationship(int num) {
        this.jaxRelationship += num;
    }

    // RUMOR SYSTEM METHODS
    
    /**
     - Checks if the player has unheard rumors available.
     - returns true if any rumor is still unheard (false in heardRumors array)
    */
    public boolean hasUnheardRumors() {
        for (int i = 0; i < 7; i++) {
            if (!heardRumors[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     - Gets an array of unheard rumor indices.
     - Used by GameEngine to randomly select from available rumors.
     - returns int array of indices not yet heard (0-6), or empty array if all heard
     */
    public int[] getUnheardIndices() {
        int count = 0;
        for (int i = 0; i < 7; i++) {
            if (!heardRumors[i]) {
                count++;
            }
        }
        
        int[] unheard = new int[count];
        int idx = 0;
        for (int i = 0; i < 7; i++) {
            if (!heardRumors[i]) {
                unheard[idx++] = i;
            }
        }
        return unheard;
    }

    //Marks a rumor as heard by setting its index to true.

    public void markRumorHeard(int index) {
        if (index >= 0 && index < 7) {
            heardRumors[index] = true;
        }
    }


    //Gets the boolean array of rumors already heard.
    public boolean[] getHeardRumors() {
        return heardRumors;
    }

    
    //Checks if a specific rumor has been heard (Basically checks the value inside heardRumors[])
    public boolean hasHeardRumor(int index) {
        if (index >= 0 && index < 7) {
            return heardRumors[index];
        }
        return false;
    }
}
