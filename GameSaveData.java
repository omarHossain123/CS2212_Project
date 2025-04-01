import java.io.Serializable;

/**
 * GameSaveData encapsulates all data that needs to be saved when the game is saved.
 * This includes the pet, score, inventory, and potentially other game state information.
 */
public class GameSaveData implements Serializable {
    // Add a serialVersionUID to ensure compatibility between saved games
    private static final long serialVersionUID = 1L;
    
    private Pet pet;
    private double score;
    private Inventory inventory;
    
    // Additional fields could be added here if more state needs to be saved
    private long lastFeedTime;
    private long lastGiftTime;
    private long lastSleepTime;
    private long lastVetTime;
    private long lastPlayTime;
    private long lastWalkTime;
    
    // Default constructor
    public GameSaveData() {
        // Initialize with default values
        this.score = 0;
    }
    
    // Getters and setters
    public Pet getPet() {
        return pet;
    }
    
    public void setPet(Pet pet) {
        this.pet = pet;
    }
    
    public double getScore() {
        return score;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    
    public long getLastFeedTime() {
        return lastFeedTime;
    }
    
    public void setLastFeedTime(long lastFeedTime) {
        this.lastFeedTime = lastFeedTime;
    }
    
    public long getLastGiftTime() {
        return lastGiftTime;
    }
    
    public void setLastGiftTime(long lastGiftTime) {
        this.lastGiftTime = lastGiftTime;
    }
    
    public long getLastSleepTime() {
        return lastSleepTime;
    }
    
    public void setLastSleepTime(long lastSleepTime) {
        this.lastSleepTime = lastSleepTime;
    }
    
    public long getLastVetTime() {
        return lastVetTime;
    }
    
    public void setLastVetTime(long lastVetTime) {
        this.lastVetTime = lastVetTime;
    }
    
    public long getLastPlayTime() {
        return lastPlayTime;
    }
    
    public void setLastPlayTime(long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }
    
    public long getLastWalkTime() {
        return lastWalkTime;
    }
    
    public void setLastWalkTime(long lastWalkTime) {
        this.lastWalkTime = lastWalkTime;
    }
}