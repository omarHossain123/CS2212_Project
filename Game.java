import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private Pet pet;
    private String petType;
    private double score;
    private Inventory inventory;
    private mainGame gameUI;
    
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
    
    // Temporary emotion duration
    private static final int TEMP_EMOTION_DURATION = 3000; // 3 seconds
    
    public Game(String petType) {
        this.petType = petType;
        this.pet = new Pet(petType);
        this.score = 0;
        this.inventory = new Inventory("defaultType", 0.0);
    }
    
    
    /**
     * Decrements pet stats over time
     */
    public void gameDecrement() {
        // Rate at which stats decrease
        // double healthRate = 0.1;
        // double happinessRate = 0.2;
        // double hungerRate = 0.25;
        // double sleepRate = 0.15;
        pet.increment();
        
        // Check for critical conditions
        checkState();
    }

    /**
     * Checks the pet's state and updates it accordingly
     * @return The current state of the pet
     */
    public String checkState() {
        String oldState = pet.getState();
        
        if (pet.getHealth() <= 0) {
            // Pet is dead - set state and permanent sad emotion
            pet.setState("dead");
            pet.updateRates(0);
            pet.setEmotion("sad", 0); // 0 duration means permanent
            refreshUI();
            return "dead";
        } else if (pet.getSleep() <= 0 && !oldState.equals("sleep")) {
            // Pet is tired - set sleep state and permanent nervous emotion
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
        } else if ((pet.getHunger() <= 0) && !oldState.equals("hungry") && !oldState.equals("angry")) {
            // Pet is hungry - set hungry state and permanent discontent emotion
            pet.setState("hungry");
            pet.decrementHealth(15);
            pet.updateRates(2);
            pet.setEmotion("discontent", 0); // Permanent emotion
            refreshUI();
            return "hungry";
        } else if (pet.getHappiness() <= 0 && !oldState.equals("angry")) {
            // Pet is angry - set angry state and permanent angry emotion
            pet.setState("angry");
            pet.setEmotion("angry", 0); // Permanent emotion
            refreshUI();
            return "angry";
        }
        
        return "default";
    }

    /**
     * Changes the pet's state back to default if conditions are met
     * @return true if state was changed, false otherwise
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
     * Takes the pet to the vet to restore health
     * @return true if the pet was treated successfully, false if on cooldown
     */
    public boolean takeToVet() {
        long currentTime = System.currentTimeMillis();
        
        // Check if the action is on cooldown
        if (currentTime - lastVetTime < VET_COOLDOWN) {
            System.out.println("Vet action is on cooldown!");
            return false;
        }
        
        // Store original state to restore later
        String originalState = pet.getState();
        
        // Set to a temporary "vet" state
        pet.setState("vet");
        
        // Set temporary nervous state and emotion
        pet.setEmotion("nervous", TEMP_EMOTION_DURATION);
        
        // Change the background to the vet background
        if (gameUI != null) {
            gameUI.changeBackground("assets\\images\\Backgrounds\\Vet_Background.png");
        }
        
        // Force UI refresh immediately
        refreshUI(); 
        
        // Small happiness penalty (pets don't like vets!)
        pet.decrementHappiness(10);
        
        // Update score
        increaseScore(-20);
        
        // Set cooldown timer
        lastVetTime = currentTime;
        
        // Get the amount of health to restore (30 total)
        final int totalHealthIncrease = 50;
        final int healingSteps = 1; // Number of steps to spread the healing over
        final int healthPerStep = totalHealthIncrease / healingSteps;
        
        // Create a timer for gradual health increase
        final Timer healingTimer = new Timer();
        healingTimer.scheduleAtFixedRate(new TimerTask() {
            private int stepsRemaining = healingSteps;
            
            @Override
            public void run() {
                if (stepsRemaining > 0 && !pet.getState().equals("dead")) {
                    // Add health for this step
                    pet.increaseHealth(healthPerStep);
                    
                    // Update UI to show progress
                    refreshUI();
                    
                    stepsRemaining--;
                    System.out.println("Healing step completed. Health: " + pet.getHealth());
                } else {
                    // Healing complete or pet died, cancel timer
                    healingTimer.cancel();
                }
            }
        }, 0, TEMP_EMOTION_DURATION / healingSteps); // Spread healing evenly over the duration
        
        System.out.println("Pet visited the vet! Starting treatment...");
        
        // Schedule restoration of original state after emotion duration
        Timer stateRestoreTimer = new Timer();
        stateRestoreTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Don't restore to original state if pet died during treatment
                if (!pet.getState().equals("dead")) {
                    pet.setState(originalState);
                    // Also check state conditions - maybe state needs to be updated
                    checkState();
                    
                    // Restore the original background
                    if (gameUI != null) {
                        gameUI.restoreDefaultBackground();
                    }
                    
                    refreshUI();
                    System.out.println("Pet state restored after vet visit");
                }
                stateRestoreTimer.cancel(); // Clean up timer
            }
        }, TEMP_EMOTION_DURATION);
        
        return true;
    }
    
    /**
     * Puts the pet to sleep to restore sleep stat
     * @return true if the pet went to sleep successfully, false if on cooldown
     */
    public boolean goToBed() {
        long currentTime = System.currentTimeMillis();
        
        // Check if the action is on cooldown
        if (currentTime - lastSleepTime < SLEEP_COOLDOWN) {
            System.out.println("Sleep action is on cooldown!");
            return false;
        }

        if ((pet.getState() != "dead") && (pet.getState() != "angry")) {
            System.out.println("Setting pet state to sleep");
            pet.setState("sleep");
            pet.setEmotion("blink", 0); 
            
            // Force UI refresh immediately
            refreshUI();
            
            // Set cooldown timer
            lastSleepTime = currentTime;
            pet.updateRates(1);
            System.out.println("Pet is sleeping! Sleep: " + pet.getSleep());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Takes the pet for a walk to improve multiple stats
     * @return true if walked successfully, false if on cooldown
     */
    public boolean walk() {
        long currentTime = System.currentTimeMillis();
        
        // Check if the action is on cooldown
        if (currentTime - lastWalkTime < WALK_COOLDOWN) {
            System.out.println("Walk action is on cooldown!");
            return false;
        }
        
        // Improve multiple stats
        pet.increaseHealth(10);
        pet.increaseHappiness(15);
        
        // Walking makes the pet more hungry and tired
        pet.decrementHunger(10);
        pet.decrementSleep(10);
        
        // Update score
        increaseScore(20);
        
        // Set cooldown timer
        lastWalkTime = currentTime;

        // Show happy emotion when walking
        pet.setEmotion("happy", TEMP_EMOTION_DURATION);
        
        // Force immediate UI update
        refreshUI();
        
        System.out.println("Walked the pet! Health: " + pet.getHealth() + ", Happiness: " + pet.getHappiness());
        return true;
    }

    /**
     * Plays with the pet to increase happiness
     * @return true if played successfully, false if on cooldown
     */
    public boolean play() {
        long currentTime = System.currentTimeMillis();
        
        // Check if the action is on cooldown
        if (currentTime - lastPlayTime < PLAY_COOLDOWN) {
            System.out.println("Play action is on cooldown!");
            return false;
        }
        
        // Increase happiness
        pet.increaseHappiness(20);
        
        // Playing makes the pet a bit more hungry and tired
        pet.decrementHunger(5);
        pet.decrementSleep(5);
        
        // Update score
        increaseScore(15);
        
        // Set cooldown timer
        lastPlayTime = currentTime;
        
        System.out.println("Played with pet! Happiness: " + pet.getHappiness());

        // Show happy emotion when playing
        pet.setEmotion("happy", TEMP_EMOTION_DURATION);
    
        // Refresh UI immediately
        refreshUI();

        return true;
    }

    /**
     * Feeds the pet to increase hunger and potentially happiness
     * @return true if the pet was fed successfully, false if on cooldown
     */
    public boolean feed(int index) {
        long currentTime = System.currentTimeMillis();
        
        // Check if the action is on cooldown
        if (currentTime - lastFeedTime < FEED_COOLDOWN) {
            System.out.println("Feed action is on cooldown!");
            return false;
        }
        
        double[] improve = inventory.useItem(index);
        if((0 > improve[0])||(0 > improve[1])){
            return false;
        }
        else{
            pet.increaseHunger(improve[0]);
            pet.increaseHappiness(improve[1]);
            
            // Choose randomly between happy and blush emotions when fed
            String[] feedEmotions = {"happy", "blush"};
            int randomIndex = (int)(Math.random() * feedEmotions.length);
            
            // Set a temporary emotion
            pet.setEmotion(feedEmotions[randomIndex], TEMP_EMOTION_DURATION);
            
            // Refresh UI immediately
            refreshUI();
        }
    
        // Set cooldown timer
        lastFeedTime = currentTime;
        
        System.out.println("Pet has been fed! Hunger: " + pet.getHunger());
        return true;
    }

    /**
     * Gives a gift to the pet to increase happiness
     * @return true if the gift was given successfully, false if on cooldown
     */
    public boolean giveGift(int index) {
        long currentTime = System.currentTimeMillis();
        
        // Check if the action is on cooldown
        if (currentTime - lastGiftTime < GIFT_COOLDOWN) {
            System.out.println("Gift action is on cooldown!");
            return false;
        }
        
        double[] improve = inventory.useItem(index);
        if((0 > improve[0])||(0 > improve[1])){
            return false;
        }
        else{
            pet.increaseHunger(improve[0]);
            pet.increaseHappiness(improve[1]);
            
            // Set emotion to blush when given a gift
            pet.setEmotion("blush", TEMP_EMOTION_DURATION);
            
            // Refresh UI immediately
            refreshUI();
        }

        // Set cooldown timer
        lastGiftTime = currentTime;
        
        System.out.println("Pet received a gift! Happiness: " + pet.getHappiness());
        return true;
    }
    
    /**
     * A test method for debugging
     */
    public void TesterMethod() {
        System.out.println("Tester method called");
        System.out.println("Pet stats: " + pet.toString());
    }
    
    /**
     * Increases the game score
     * @param amount Amount to increase the score by
     */
    public void increaseScore(double amount) {
        score += amount;
    }
    
    // Getters
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

    /**
     * Gets the time when the pet was last fed
     * @return last feed time in milliseconds
     */
    public long getLastFeedTime() {
        return lastFeedTime;
    }

    /**
     * Sets the time when the pet was last fed
     * @param lastFeedTime time in milliseconds
     */
    public void setLastFeedTime(long lastFeedTime) {
        this.lastFeedTime = lastFeedTime;
    }

    /**
     * Gets the time when the pet was last given a gift
     * @return last gift time in milliseconds
     */
    public long getLastGiftTime() {
        return lastGiftTime;
    }

    /**
     * Sets the time when the pet was last given a gift
     * @param lastGiftTime time in milliseconds
     */
    public void setLastGiftTime(long lastGiftTime) {
        this.lastGiftTime = lastGiftTime;
    }

    /**
     * Gets the time when the pet last went to sleep
     * @return last sleep time in milliseconds
     */
    public long getLastSleepTime() {
        return lastSleepTime;
    }

    /**
     * Sets the time when the pet last went to sleep
     * @param lastSleepTime time in milliseconds
     */
    public void setLastSleepTime(long lastSleepTime) {
        this.lastSleepTime = lastSleepTime;
    }

    /**
     * Gets the time when the pet was last taken to the vet
     * @return last vet time in milliseconds
     */
    public long getLastVetTime() {
        return lastVetTime;
    }

    /**
     * Sets the time when the pet was last taken to the vet
     * @param lastVetTime time in milliseconds
     */
    public void setLastVetTime(long lastVetTime) {
        this.lastVetTime = lastVetTime;
    }

    /**
     * Gets the time when the pet last played
     * @return last play time in milliseconds
     */
    public long getLastPlayTime() {
        return lastPlayTime;
    }

    /**
     * Sets the time when the pet last played
     * @param lastPlayTime time in milliseconds
     */
    public void setLastPlayTime(long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    /**
     * Gets the time when the pet was last walked
     * @return last walk time in milliseconds
     */
    public long getLastWalkTime() {
        return lastWalkTime;
    }

    /**
     * Sets the time when the pet was last walked
     * @param lastWalkTime time in milliseconds
     */
    public void setLastWalkTime(long lastWalkTime) {
        this.lastWalkTime = lastWalkTime;
    }
    public boolean validState() {
        if ((pet.getState().equals("default")) || (pet.getState().equals("hungry"))) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Sets the score to a specific value
     * @param score the score value to set
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Sets the pet object
     * @param pet The pet object to set
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Sets the inventory object
     * @param inventory The inventory to set
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    // method to set the UI reference
    public void setGameUI(mainGame ui) {
        this.gameUI = ui;
    }

    // method to refresh the UI
    public void refreshUI() {
        if (gameUI != null) {
            gameUI.refreshPetImage();
        }
    }

    /**
     * Updates the game state from a saved game
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