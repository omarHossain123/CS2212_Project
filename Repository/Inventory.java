import java.io.Serializable;

/**
 * The Inventory class represents the player's inventory, containing items, a current pet,
 * locked pets, and the player's score.
 * It provides methods to manage the inventory, current pet, and score, as well as to use items
 * in the inventory to affect the pet's hunger and happiness.
 * @author Mohammad Isa Alif
 */
public class Inventory implements Serializable{
    
    /** The current pet of the player. */
    private String currentPet;
    
    /** Array of items in the player's inventory. */
    private Item[] inventory;
    
    /** Array indicating whether the pets are locked. */
    private boolean[] isLockedPets;
    
    /** The current score of the player. */
    private double score;

    /**
     * Constructs an Inventory object with a given current pet and initial game score.
     * The inventory is initialized with 6 items, and the locked pets array is set up.
     * 
     * @param current The name of the current pet.
     * @param gameScore The starting score of the player.
     */
    public Inventory(String current, double gameScore) {
        this.currentPet = current;
        inventory = new Item[6];

        // Initialize inventory with 6 items.
        for (int i = 0; i < 6; i++) {
            this.inventory[i] = new Item(i, 0);
        }

        // Initialize locked pets array (only the first pet is unlocked initially).
        this.isLockedPets = new boolean[]{false, true, false, false};
        this.score = gameScore;
    }

    /**
     * Sets the current pet of the player.
     * 
     * @param current The name of the new current pet.
     */
    public void setCurrentPet(String current) {
        currentPet = current;
    }

    /**
     * Returns the name of the current pet.
     * 
     * @return The name of the current pet.
     */
    public String getCurrentPet() {
        return this.currentPet;
    }

    /**
     * Returns the current score of the player.
     * 
     * @return The player's score.
     */
    public double getScore() {
        return this.score;
    }
    
    /**
     * Sets the player's score to a new value.
     * 
     * @param scoreNew The new score to set.
     */
    public void setScore(double scoreNew) {
        score = scoreNew;
    }

    /**
     * Returns an array indicating the locked status of pets.
     * 
     * @return An array of boolean values, where each element represents whether the corresponding pet is locked.
     */
    public boolean[] getLockedPets() {
        return this.isLockedPets;
    }

    /**
     * Sets the locked status of pets to a new array of values.
     * 
     * @param lockedPets An array of boolean values representing the new locked status of the pets.
     */
    public void setLockedPets(boolean[] lockedPets) {
        isLockedPets = lockedPets;
    }

    /**
     * Returns the array of items in the player's inventory.
     * 
     * @return An array of Item objects representing the inventory.
     */
    public Item[] getInventory() {
        return this.inventory;
    }

    /**
     * Sets the inventory to a new array of items.
     * 
     * @param inventoryNew An array of Item objects representing the new inventory.
     */
    public void setInventory(Item[] inventoryNew) {
        inventory = inventoryNew;
    }

    /**
     * Uses an item from the inventory based on its index. The item is used by decreasing its quantity.
     * The method also returns the hunger and happiness bonuses associated with using the item.
     * Indexes: 0 - Orange, 1 - Popsicle, 2 - Cake, 3 - Cards, 4 - Ball, 5 - Gift 
     * 
     * @param itemIndex The index of the item to use.
     * @return An array containing the hunger bonus at index 0 and the happiness bonus at index 1.
     */
    public double[] useItem(int itemIndex) {

        if (inventory[itemIndex].decreaseItem()){
            return new double[]{inventory[itemIndex].getHungerBonus(), inventory[itemIndex].getHappinessBonus()};
        }
        else{
            double[] zero = {-1,-1};
            return zero;

        }
    }
}

