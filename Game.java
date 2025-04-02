public class Game {
    private Pet pet;
    private String petType;
    private double score;
    private Inventory inventory;
    
    // Cooldown timers for various actions (in milliseconds)
    private long lastFeedTime = 0;
    private long lastGiftTime = 0;
    private long lastSleepTime = 0;
    private long lastVetTime = 0;
    private long lastPlayTime = 0;
    private long lastWalkTime = 0;
    
    // Cooldown periods (in milliseconds)
    private static final long FEED_COOLDOWN = 10000; // 10 seconds
    private static final long GIFT_COOLDOWN = 15000; // 15 seconds
    private static final long SLEEP_COOLDOWN = 20000; // 20 seconds
    private static final long VET_COOLDOWN = 30000;   // 30 seconds
    private static final long PLAY_COOLDOWN = 12000;  // 12 seconds
    private static final long WALK_COOLDOWN = 18000;  // 18 seconds
    
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
    public boolean changeState(){
        if (pet.getState() == "hungry") {
            if(pet.getHunger() != 0){
                System.out.println(pet.getHunger());
                pet.setState("default");
                pet.updateRates(3);
                return true;
            }
            
        } else if (pet.getState() == "angry") {
            
            if(pet.getHunger() != 0){
                pet.setState("default");
                pet.updateRates(3);
                return true;
            }
          

           
        } else if (pet.getState() == "sleep") {
            
            if(pet.getSleep() >= pet.getMaxSleep()){
                pet.setState("default");
                pet.updateRates(3);
                return true;
            }
            
        } 
            return false;
        
    }
    /**
     * Checks the pet's state and updates it accordingly
     * @return The current state of the pet
     */
    public String checkState() {

        if (pet.getHealth() <= 0) {
            pet.setState("dead");
            pet.updateRates(0);
            return "dead";
        } else if (pet.getSleep() <= 0 && pet.getState() != "sleep") {
            pet.decrementHealth(15);
            if (pet.getHealth() != 0) {
                pet.setState("sleep");
                pet.updateRates(1);
                return "sleep";
            } else {
                pet.setState("dead");
                pet.updateRates(0);
                return "dead";
            }
            
        } 
        else if ((pet.getHunger() <= 0) && (pet.getState() != "hungry")) {
            System.out.println("Retarded" + pet.getState());
            pet.setState("hungry");
            pet.decrementHealth(15);
            pet.updateRates(2);
            return "hungry";
        }else if (pet.getHappiness() <= 0 && pet.getState() != "angry" ) {
            pet.setState("angry");
            return "angry";
        }
        return "default";

    }

    // public String checkState() {
    //     // Check if the pet is in a critical state
    //     if (pet.getHealth() == 0) {
    //         pet.setState("sick");
    //     } else if (pet.getHunger() == 0) {
    //         pet.setState("hungry");
    //     } else if (pet.getSleep() == 0) {
    //         pet.setState("tired");
    //     } else if (pet.getHappiness() == 0) {
    //         pet.setState("sad");
    //     } 
        
    //     return pet.getState();
    // }
    
    /**
     * Feeds the pet to increase hunger and potentially happiness
     * @return true if the pet was fed successfully, false if on cooldown
     */
    public boolean feed() {
        long currentTime = System.currentTimeMillis();
        
        // Check if the action is on cooldown
        if (currentTime - lastFeedTime < FEED_COOLDOWN) {
            System.out.println("Feed action is on cooldown!");
            return false;
        }
        
        // Increase hunger
        pet.increaseHunger(15);
        
        // Small happiness boost from feeding
        pet.increaseHappiness(5);
        
        // Update score
        increaseScore(10);
        
        // Set cooldown timer
        lastFeedTime = currentTime;
        
        System.out.println("Pet has been fed! Hunger: " + pet.getHunger());
        return true;
    }
    
    /**
     * Gives a gift to the pet to increase happiness
     * @return true if the gift was given successfully, false if on cooldown
     */
    public boolean giveGift() {
        long currentTime = System.currentTimeMillis();
        
        // Check if the action is on cooldown
        if (currentTime - lastGiftTime < GIFT_COOLDOWN) {
            System.out.println("Gift action is on cooldown!");
            return false;
        }
        
        // Significant happiness boost
        pet.increaseHappiness(20);
        
        // Update score
        increaseScore(15);
        
        // Set cooldown timer
        lastGiftTime = currentTime;
        
        System.out.println("Pet received a gift! Happiness: " + pet.getHappiness());
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

        if ((pet.getState()!= "dead") || (pet.getState()!= "angry") || (pet.getState() != "sleep")) {
            pet.setState("sleep");
            // Restore sleep
            
        
        
        
        
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
        
        // Significant health boost
        pet.increaseHealth(30);
        
        // Small happiness penalty (pets don't like vets!)
        pet.decrementHappiness(5);
        
        // Update score
        increaseScore(20);
        
        // Set cooldown timer
        lastVetTime = currentTime;
        
        System.out.println("Pet visited the vet! Health: " + pet.getHealth());
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
        return true;
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
        
        System.out.println("Walked the pet! Health: " + pet.getHealth() + ", Happiness: " + pet.getHappiness());
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
        if ((pet.getState() == "default") || (pet.getState() == "hungry")) {
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

    /**
     * Updates the game state from a saved game
     * @param saveData The saved game data to restore from
     */
    public void restoreFromSave(GameSaveData saveData) {
        if (saveData == null) return;
        
        // Restore pet
        if (saveData.getPet() != null) {
            this.pet = saveData.getPet();
            this.petType = this.pet.getType();
        }
        
        // Restore score
        this.score = saveData.getScore();
        
        // Restore inventory
        if (saveData.getInventory() != null) {
            this.inventory = saveData.getInventory();
        }
        
        // Restore cooldown timers
        this.lastFeedTime = saveData.getLastFeedTime();
        this.lastGiftTime = saveData.getLastGiftTime();
        this.lastSleepTime = saveData.getLastSleepTime();
        this.lastVetTime = saveData.getLastVetTime();
        this.lastPlayTime = saveData.getLastPlayTime();
        this.lastWalkTime = saveData.getLastWalkTime();
    }
}