package JUnitTests;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Inventory;
import Item;

// Unit tests for the Inventory class.
public class InventoryTest {
    // Instance of Inventory to be tested.
    private Inventory inventory;

    // This method runs before each test to set up the initial inventory.
    @BeforeEach
    public void setUp() {
        // Create an inventory with the current pet being "Debatchi" and an initial score of 10000.
        inventory = new Inventory("Debatchi", 10000);
    }

    // Test the useItem method when the item count is positive.
    @Test
    public void testUseItem(){
        // Increase the count of the first item (index 0) by 1.
        inventory.getInventory()[0].increaseItem(1);
        // Use the item at index 0 and capture the bonuses returned.
        double[] bonuses = inventory.useItem(0);
        // Expected bonuses for oranges are 5 for happiness and 0 for hunger.
        double[] expected = {15, 0};
        // Assert that the returned bonuses match the expected values.
        assertArrayEquals(expected, bonuses, "Test succesful");
    }

    // Test the useItem method when the item count is zero.
    @Test
    public void testUseItemWithNoCount(){
        // Use the item at index 1 which has a count of 0.
        double[] bonuses = inventory.useItem(1);
        // Expected bonuses when no items are available are -1 for both values.
        double[] expected = {-1, -1};
        // Assert that the returned bonuses indicate failure (i.e., no items were used).
        assertArrayEquals(expected, bonuses, "Test succesful");
    }
    
    // Test the Inventory constructor and the initial state of the Inventory object.
    @Test
    public void testConstructor() { 
        // Create a new Inventory object.
        Inventory inv = new Inventory("Debatchi", 10000);
        // Assert that the current pet is correctly set.
        assertEquals("Debatchi", inv.getCurrentPet());
        // Assert that the score is correctly set.
        assertEquals(10000, inv.getScore());
        // Assert that the inventory contains 6 items.
        assertEquals(6, inv.getInventory().length);
        // Assert that each item in the inventory starts with a count of 0.
        for (Item item : inv.getInventory()) {
            assertEquals(0, item.getNumberItem());
        }
        // Define the expected locked status for pets.
        boolean[] expectedLocked = {false, true, false, false};
        // Assert that the locked pets array matches the expected values.
        assertArrayEquals(expectedLocked, inv.getLockedPets());
    }
}
