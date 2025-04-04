import java.io.Serializable;

/**
 * GameSaveData encapsulates all data that needs to be saved when the game is saved.
 * This includes the pet, score, inventory, and other game state information such as
 * timestamps for various actions.
 * 
 * <p>This class implements {@link Serializable} to allow the game state to be serialized
 * and saved to a file. It ensures that the game can be resumed from the same state later.
 * 
 * <p>Author: Omar Hossain
 */
public class GameSaveData implements Serializable {
    // Add a serialVersionUID to ensure compatibility between saved games
    private static final long serialVersionUID = 1L;
    
    // The pet associated with the game
    private Pet pet;
    
    // The player's current score
    private double score;
    
    // The player's inventory containing items
    private Inventory inventory;
    
    // Timestamps for various game actions
    private long lastFeedTime;
    private long lastGiftTime;
    private long lastSleepTime;
    private long lastVetTime;
    private long lastPlayTime;
    private long lastWalkTime;
    
    /**
     * Default constructor for GameSaveData.
     * Initializes the game state with default values.
     */
    public GameSaveData() {
        // Initialize score to 0
        this.score = 0;
    }
    
    /**
     * Gets the pet associated with the game.
     * 
     * @return the pet object
     */
    public Pet getPet() {
        return pet;
    }
    
    /**
     * Sets the pet associated with the game.
     * 
     * @param pet the pet object to set
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }
    
    /**
     * Gets the player's current score.
     * 
     * @return the score as a double
     */
    public double getScore() {
        return score;
    }
    
    /**
     * Sets the player's current score.
     * 
     * @param score the score to set
     */
    public void setScore(double score) {
        this.score = score;
    }
    
    /**
     * Gets the player's inventory.
     * 
     * @return the inventory object
     */
    public Inventory getInventory() {
        return inventory;
    }
    
    /**
     * Sets the player's inventory.
     * 
     * @param inventory the inventory object to set
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    
    /**
     * Gets the timestamp of the last feeding action.
     * 
     * @return the last feed time in milliseconds
     */
    public long getLastFeedTime() {
        return lastFeedTime;
    }
    
    /**
     * Sets the timestamp of the last feeding action.
     * 
     * @param lastFeedTime the last feed time in milliseconds
     */
    public void setLastFeedTime(long lastFeedTime) {
        this.lastFeedTime = lastFeedTime;
    }
    
    /**
     * Gets the timestamp of the last gift action.
     * 
     * @return the last gift time in milliseconds
     */
    public long getLastGiftTime() {
        return lastGiftTime;
    }
    
    /**
     * Sets the timestamp of the last gift action.
     * 
     * @param lastGiftTime the last gift time in milliseconds
     */
    public void setLastGiftTime(long lastGiftTime) {
        this.lastGiftTime = lastGiftTime;
    }
    
    /**
     * Gets the timestamp of the last sleep action.
     * 
     * @return the last sleep time in milliseconds
     */
    public long getLastSleepTime() {
        return lastSleepTime;
    }
    
    /**
     * Sets the timestamp of the last sleep action.
     * 
     * @param lastSleepTime the last sleep time in milliseconds
     */
    public void setLastSleepTime(long lastSleepTime) {
        this.lastSleepTime = lastSleepTime;
    }
    
    /**
     * Gets the timestamp of the last vet visit action.
     * 
     * @return the last vet time in milliseconds
     */
    public long getLastVetTime() {
        return lastVetTime;
    }
    
    /**
     * Sets the timestamp of the last vet visit action.
     * 
     * @param lastVetTime the last vet time in milliseconds
     */
    public void setLastVetTime(long lastVetTime) {
        this.lastVetTime = lastVetTime;
    }
    
    /**
     * Gets the timestamp of the last play action.
     * 
     * @return the last play time in milliseconds
     */
    public long getLastPlayTime() {
        return lastPlayTime;
    }
    
    /**
     * Sets the timestamp of the last play action.
     * 
     * @param lastPlayTime the last play time in milliseconds
     */
    public void setLastPlayTime(long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }
    
    /**
     * Gets the timestamp of the last walk action.
     * 
     * @return the last walk time in milliseconds
     */
    public long getLastWalkTime() {
        return lastWalkTime;
    }
    
    /**
     * Sets the timestamp of the last walk action.
     * 
     * @param lastWalkTime the last walk time in milliseconds
     */
    public void setLastWalkTime(long lastWalkTime) {
        this.lastWalkTime = lastWalkTime;
    }
}