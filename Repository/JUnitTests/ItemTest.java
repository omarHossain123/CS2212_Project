package JUnitTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Item;

import org.junit.jupiter.api.BeforeEach;

// Unit tests for the Item class.
public class ItemTest {
    // Test objects for the Item class.
    private Item orange;
    private Item cards;

    // This method runs before each test to set up initial conditions.
    @BeforeEach
    public void setUp(){
        // Initialize 'orange' with 0 items.
        orange = new Item(0, 0);
        // Initialize 'cards' with 3 items (and type 3, label "Cards", etc.).
        cards = new Item(3, 3);
    }

    // Test that decreaseItem returns false and does not decrease count when no items are available.
    @Test
    public void testDecreaseNoItems(){
        // Attempt to decrease items when count is 0 should fail.
        assertFalse(orange.decreaseItem());
        // The number of items should remain 0.
        assertEquals(0, orange.getNumberItem());
    }

    // Test that decreaseItem returns true and decreases count when items are available.
    @Test
    public void testDecreaseYesItems(){
        // Increase the number of items to 5.
        orange.increaseItem(5);
        // Now, decreasing an item should succeed.
        assertTrue(orange.decreaseItem());
        // After decreasing, the count should be reduced to 4.
        assertEquals(4, orange.getNumberItem());
    }

    // Test the constructor and default property values of the Item class.
    @Test
    public void testConstructor(){
        // Verify that the label is correctly assigned.
        assertEquals("Cards", cards.getLabel());
        // Verify that the number of items is set to 3.
        assertEquals(3, cards.getNumberItem());
        // Verify that the type is correctly set.
        assertEquals(3, cards.getType());
        // Verify the hunger bonus property is set to 0.
        assertEquals(0, cards.getHungerBonus());
        // Verify the happiness bonus property is set to 5.
        assertEquals(15, cards.getHappinessBonus());
    }
}
