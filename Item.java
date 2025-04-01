import java.io.Serializable;

/**
 * The Item class represents an item in the player's inventory.
 * Each item has a type, a label, a hunger bonus, and a happiness bonus.
 * The class allows for managing the quantity of an item and its effects on the player's pet.
 */
public class Item implements Serializable {

    /** The number of items in the inventory. */
    private int numberItem;
    
    /** The type of the item (e.g., 0 for Orange, 1 for Popsicle). */
    private int type;
    
    /** The label or name of the item. */
    private String label;
    
    /** The hunger bonus provided by the item when used. */
    private double hungerBonus;
    
    /** The happiness bonus provided by the item when used. */
    private double happinessBonus;

    /**
     * Constructs an Item object with a specified type and number.
     * The item's label, hunger bonus, and happiness bonus are set based on the type.
     * 
     * @param type The type of the item (0 for Orange, 1 for Popsicle, etc.).
     * @param numberItem The quantity of the item.
     */
    public Item(int type, int numberItem) {
        switch (type) {
            case 0: 
                this.numberItem = numberItem;
                this.label = "Orange";
                this.hungerBonus = 5;
                this.happinessBonus = 0;
                break;
            case 1: 
                this.numberItem = numberItem;
                this.label = "Popsicle";
                this.hungerBonus = 10;
                this.happinessBonus = 3;
                break;
            case 2: 
                this.numberItem = numberItem;
                this.label = "Cake";
                this.hungerBonus = 30;
                this.happinessBonus = 10;
                break;
            case 3: 
                this.numberItem = numberItem;
                this.label = "Cards";
                this.hungerBonus = 0;
                this.happinessBonus = 5;
                break;
            case 4: 
                this.numberItem = numberItem;
                this.label = "Ball";
                this.hungerBonus = 0;
                this.happinessBonus = 15;
                break;
            case 5: 
                this.numberItem = numberItem;
                this.label = "Gift";
                this.hungerBonus = 3;
                this.happinessBonus = 40;
                break;
            default:
                this.numberItem = 0;
                this.label = "";
                this.hungerBonus = 0;
                this.happinessBonus = 0;
        }
    }

    /**
     * Returns the hunger bonus associated with the item.
     * 
     * @return The hunger bonus of the item.
     */
    public double getHungerBonus() {
        return hungerBonus;
    }

    /**
     * Returns the happiness bonus associated with the item.
     * 
     * @return The happiness bonus of the item.
     */
    public double getHappinessBonus() {
        return happinessBonus;
    }

    /**
     * Returns the label or name of the item.
     * 
     * @return The label of the item.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the type of the item (e.g., 0 for Orange, 1 for Popsicle).
     * 
     * @return The type of the item.
     */
    public int getType() {
        return type;
    }

    /**
     * Decreases the quantity of the item by 1, as long as there is at least 1 item available.
     * @return If it successfully decreases or not.
     */
    public boolean decreaseItem() {
        if (numberItem > 0) {
            this.numberItem--;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns the current number of items in the inventory.
     * 
     * @return The number of items.
     */
    public int getNumberItem() {
        return numberItem;
    }

    /**
     * Increases the number of items by the specified amount.
     * 
     * @param bought The number of items to add to the inventory.
     */
    public void increaseItem(int bought) {
        this.numberItem += bought;
    }
}