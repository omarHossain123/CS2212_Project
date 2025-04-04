import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents the game logic for a pet simulation game.
 * The game includes actions like feeding, playing, walking, taking the pet to the vet, etc., with cooldowns.
 * The pet's stats are managed and updated during gameplay.
 * 
 * @author Ahmed Sinjab 
 * @author Omar Hossain
 */
public class Game {
    private Pet pet;  // The pet being managed in the game
    private String petType;  // Type of the pet (e.g., "dog", "cat")
    private double score;  // Player's score
    private Inventory inventory;  // Pet's inventory
    private mainGame gameUI;  // UI reference for updating the game interface
    
    // Cooldown timers for various actions (in milliseconds)
    private long lastFeedTime = 0;
    private long lastGiftTime = 0;
    private long lastSleepTime = 0;
    private long lastVetTime = 0;
    private long lastPlayTime = 0;
    private long lastWalkTime = 0;
     
    // Cooldown periods (in milliseconds)
    public static final long FEED_COOLDOWN = 5000; 
    public static final long GIFT_COOLDOWN = 5000; 
    public static final long SLEEP_COOLDOWN = 15000; 
    public static final long VET_COOLDOWN = 15000;   
    public static final long PLAY_COOLDOWN = 10000;  
    public static final long WALK_COOLDOWN = 10000;  
    
    // Temporary emotion duration (in milliseconds)
    private static final int TEMP_EMOTION_DURATION = 3000; // 3 seconds
    
    /**
     * Constructor for the Game class.
     * Initializes the game with a pet type and creates a new pet object.
     * 
     * @param petType The type of the pet (e.g., "dog", "cat")
     */
    public Game(String petType) {
        this.petType = petType;
        this.pet = new Pet(petType);
        this.score = 0;
        this.inventory = new Inventory("defaultType", 0.0);
    }
    
    /**
     * Decrements pet stats over time by calling the pet's increment method.
     * It also checks the pet's state after decrementing the stats.
     */
    public void gameDecrement() {
        pet.increment();
        checkState();
    }

    /**
     * Checks the pet's state based on its current stats and updates its state.
     * Changes the pet's state to "dead", "sleep", "hungry", or "angry" based on critical conditions.
     * 
     * @return The current state of the pet
     */
    public String checkState() {
        String oldState = pet.getState();
        
        if (pet.getHealth() <= 0) {
            pet.setState("dead");
            pet.updateRates(0);
            pet.setEmotion("sad", 0); // Permanent emotion 
            refreshUI();
            return "dead";
        } else if (pet.getSleep() <= 0 && !oldState.equals("sleep")) {
            pet.decrementHealth(15);
            if (pet.getHealth() != 0) {
                pet.setState("sleep");
                pet.updateRates(1);
                pet.setEmotion("blink", 0); // Permanent emotion 
                refreshUI();
                return "sleep";
            } else {
                pet.setState("dead");
                pet.updateRates(0);
                pet.setEmotion("sad", 0); // Permanent emotion
                refreshUI();
                return "dead";
            }
        } else if ((pet.getHunger() <= 0) && !oldState.equals("hungry") ) {
            pet.setState("hungry");
            pet.decrementHealth(15);
            pet.updateRates(2);
            pet.setEmotion("discontent", 0); // Permanent emotion
            refreshUI();
            return "hungry";
        } else if (pet.getHappiness() <= 0 && !oldState.equals("angry")) {
            pet.setState("angry");
            pet.setEmotion("angry", 0); // Permanent emotion
            refreshUI();
            return "angry";
        }
        
        return "default";
    }

    /**
     * Changes the pet's state back to default if conditions are met.
     * The pet's state is reset based on its hunger, sleep, or anger status.
     * 
     * @return true if the state was changed, false otherwise
     */
    public boolean changeState() {
        if (pet.getState().equals("hungry")) {
            if (pet.getHunger() != 0) {
                pet.setState("default");
                pet.updateRates(3);
                pet.setEmotion("neutral", 0); // Return to neutral permanently
                refreshUI();
                return true;
            }
        } else if (pet.getState().equals("angry")) {
            if (pet.getHunger() != 0) {
                pet.setState("default");
                pet.updateRates(3);
                pet.setEmotion("neutral", 0); // Return to neutral permanently
                refreshUI();
                return true;
            }
        } else if (pet.getState().equals("sleep")) {
            if (pet.getSleep() >= pet.getMaxSleep()) {
                pet.setState("default");
                pet.updateRates(3);
                pet.setEmotion("neutral", 0); // Return to neutral permanently
                refreshUI();
                return true;
            }
        }
        return false;
    }

    /**
     * Takes the pet to the vet to restore health.
     * If the action is on cooldown, it returns false.
     * 
     * @return true if the pet was treated successfully, false if on cooldown
     */
    public boolean takeToVet() {
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - lastVetTime < VET_COOLDOWN) {
            System.out.println("Vet action is on cooldown!");
            return false;
        }
        
        String originalState = pet.getState();
        pet.setState("vet");
        pet.setEmotion("nervous", TEMP_EMOTION_DURATION);  // Nervous emotion
        if (gameUI != null) {
            gameUI.changeBackground("assets\\images\\Backgrounds\\Vet_Background.png");
        }
        
        refreshUI();
        pet.decrementHappiness(10);
        increaseScore(-20);
        lastVetTime = currentTime;
        
        final int totalHealthIncrease = 50;
        final int healingSteps = 1;
        final int healthPerStep = totalHealthIncrease / healingSteps;
        
        final Timer healingTimer = new Timer();
        healingTimer.scheduleAtFixedRate(new TimerTask() {
            private int stepsRemaining = healingSteps;
            
            @Override
            public void run() {
                if (stepsRemaining > 0 && !validState()) {
                    pet.increaseHealth(healthPerStep);
                    refreshUI();
                    stepsRemaining--;
                    System.out.println("Healing step completed. Health: " + pet.getHealth());
                } else {
                    healingTimer.cancel();
                    
                }
            }
        }, 0, TEMP_EMOTION_DURATION / healingSteps); 
        
        Timer stateRestoreTimer = new Timer();
        stateRestoreTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!pet.getState().equals("dead")) {
                    pet.setState(originalState);
                    checkState();
                    if (gameUI != null) {
                        gameUI.restoreDefaultBackground();
                    }
                    refreshUI();
                    System.out.println("Pet state restored after vet visit");
                }
                stateRestoreTimer.cancel();
            }
        }, TEMP_EMOTION_DURATION);
        
        return true;
    }

    /**
     * Puts the pet to sleep to restore its sleep stat.
     * If the action is on cooldown, it returns false.
     * 
     * @return true if the pet went to sleep successfully, false if on cooldown
     */
    public boolean goToBed() {
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - lastSleepTime < SLEEP_COOLDOWN) {
            System.out.println("Sleep action is on cooldown!");
            return false;
        }

        if ((pet.getState() != "dead") && (pet.getState() != "angry")) {
            pet.setState("sleep");
            pet.setEmotion("blink", 0); 
            refreshUI();
            lastSleepTime = currentTime;
            pet.updateRates(1);
            System.out.println("Pet is sleeping! Sleep: " + pet.getSleep());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Takes the pet for a walk to improve multiple stats (health, happiness).
     * If the action is on cooldown, it returns false.
     * 
     * @return true if the pet was walked successfully, false if on cooldown
     */
    public boolean walk() {
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - lastWalkTime < WALK_COOLDOWN) {
            System.out.println("Walk action is on cooldown!");
            return false;
        }
        
        pet.increaseHealth(15);
        pet.increaseHappiness(20);
        pet.decrementHunger(15);
        pet.decrementSleep(15);
        
        increaseScore(20);
        lastWalkTime = currentTime;
        
        pet.setEmotion("happy", TEMP_EMOTION_DURATION);
        refreshUI();
        
        System.out.println("Walked the pet! Health: " + pet.getHealth() + ", Happiness: " + pet.getHappiness());
        return true;
    }

    /**
     * Plays with the pet to increase happiness.
     * If the action is on cooldown, it returns false.
     * 
     * @return true if the pet was played with successfully, false if on cooldown
     */
    public boolean play() {
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - lastPlayTime < PLAY_COOLDOWN) {
            System.out.println("Play action is on cooldown!");
            return false;
        }
        
        pet.increaseHappiness(25);
        pet.decrementHunger(5);
        pet.decrementSleep(5);
        
        increaseScore(25);
        lastPlayTime = currentTime;
        
        pet.setEmotion("happy", TEMP_EMOTION_DURATION);
        refreshUI();

        System.out.println("Played with pet! Happiness: " + pet.getHappiness());
        return true;
    }

    /**
     * Feeds the pet to increase hunger and potentially happiness.
     * If the action is on cooldown, it returns false.
     * 
     * @return true if the pet was fed successfully, false if on cooldown
     */
    public boolean feed(int index) {
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - lastFeedTime < FEED_COOLDOWN) {
            System.out.println("Feed action is on cooldown!");
            return false;
        }
        
        double[] improve = inventory.useItem(index);
        if((0 > improve[0])||(0 > improve[1])) {
            return false;
        } else {
            pet.increaseHunger(improve[0]);
            pet.increaseHappiness(improve[1]);
            
            String[] feedEmotions = {"happy", "blush"};
            int randomIndex = (int)(Math.random() * feedEmotions.length);
            
            pet.setEmotion(feedEmotions[randomIndex], TEMP_EMOTION_DURATION);
            refreshUI();
        }
    
        lastFeedTime = currentTime;
        System.out.println("Pet has been fed! Hunger: " + pet.getHunger());
        return true;
    }

    /**
     * Gives a gift to the pet to increase happiness.
     * If the action is on cooldown, it returns false.
     * 
     * @return true if the gift was given successfully, false if on cooldown
     */
    public boolean giveGift(int index) {
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - lastGiftTime < GIFT_COOLDOWN) {
            System.out.println("Gift action is on cooldown!");
            return false;
        }
        
        double[] improve = inventory.useItem(index);
        if((0 > improve[0])||(0 > improve[1])) {
            return false;
        } else {
            pet.increaseHunger(improve[0]);
            pet.increaseHappiness(improve[1]);
            
            pet.setEmotion("blush", TEMP_EMOTION_DURATION);
            refreshUI();
        }

        lastGiftTime = currentTime;
        System.out.println("Pet received a gift! Happiness: " + pet.getHappiness());
        return true;
    }

    public void TesterMethod() {
        System.out.println("Tester method called");
        System.out.println("Pet stats: " + pet.toString());
    }

    public void increaseScore(double amount) {
        score += amount;
    }

    // Getter and Setter Methods
    public Pet getPet() {
        return pet;
    }

    public double getScore() {
        return score;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String petType() {
        return petType;
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
    
    public boolean validState() {
        return (pet.getState().equals("default") || pet.getState().equals("hungry"));
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setGameUI(mainGame ui) {
        this.gameUI = ui;
    }

    public void refreshUI() {
        if (gameUI != null) {
            gameUI.refreshPetImage();
        }
    }

    /**
     * Updates the game state from a saved game.
     * Restores pet, score, inventory, and cooldown timers from the provided save data.
     * 
     * @param saveData The saved game data to restore from
     */
    public void restoreFromSave(GameSaveData saveData) {
        if (saveData == null) {
            System.out.println("WARNING: Attempting to restore from null save data");
            return;
        }
        
        System.out.println("DEBUG - Starting restore from save data...");
        
        // Restore pet
        if (saveData.getPet() != null) {
            this.pet = saveData.getPet();
            this.petType = this.pet.getType();
            System.out.println("DEBUG - Restored pet: " + this.pet.getType() + 
                           ", Health: " + this.pet.getHealth());
        } else {
            System.out.println("WARNING: Save data has null pet");
        }
        
        // Restore score
        this.score = saveData.getScore();
        System.out.println("DEBUG - Restored score: " + this.score);
        
        // Restore inventory
        if (saveData.getInventory() != null) {
            this.inventory = saveData.getInventory();
            System.out.println("DEBUG - Restored inventory");
        } else {
            System.out.println("WARNING: Save data has null inventory, creating default");
            this.inventory = new Inventory(this.petType, this.score);
        }
        
        // Restore cooldown timers
        this.lastFeedTime = saveData.getLastFeedTime();
        this.lastGiftTime = saveData.getLastGiftTime();
        this.lastSleepTime = saveData.getLastSleepTime();
        this.lastVetTime = saveData.getLastVetTime();
        this.lastPlayTime = saveData.getLastPlayTime();
        this.lastWalkTime = saveData.getLastWalkTime();
        System.out.println("DEBUG - Restored cooldown timers");
        
        System.out.println("DEBUG - Restore from save completed");
    }
}
