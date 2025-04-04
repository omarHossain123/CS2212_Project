package JUnitTests;
import org.junit.*;

import Pet;

import static org.junit.Assert.*;

public class PetTest {

    /**
     * Tests if the pet's health is correctly decremented when hunger is 0.
     */
    @Test
    public void testPetStateHungry() {
        Pet pet = new Pet("Debatchi"); // Create a pet
        pet.setHunger(0); // Set hunger to 0
        pet.decrementHunger(1); // Decrease health
        assertEquals(0, pet.getHunger(), 0); // Verify health is 0
    }

    /**
     * Tests if the pet's happiness is correctly decremented when happiness is 0.
     */
    @Test
    public void testPetStateHappiness() {
        Pet pet = new Pet("Debatchi"); // Create a pet
        pet.setHappiness(0); // Set happiness to 0
        pet.decrementHappiness(1); // Decrease happiness
        assertEquals(0, pet.getHappiness(), 0); // Verify happiness is 0
    }

    /**
     * Tests if the pet's health is correctly decremented when health is 0.
     */
    @Test
    public void testPetStateHealth() {
        Pet pet = new Pet("Debatchi"); // Create a pet
        pet.setHealth(0); // Set health to 0
        pet.decrementHealth(1); // Decrease health
        assertEquals(0, pet.getHealth(), 0); // Verify health is 0
    }

    /**
     * Tests if the pet's sleep is correctly decremented when sleep is 0.
     */
    @Test
    public void testPetStateSleep() {
        Pet pet = new Pet("Debatchi"); // Create a pet
        pet.setSleep(0); // Set sleep to 0
        pet.decrementSleep(1); // Decrease sleep
        assertEquals(0, pet.getSleep(), 0); // Verify sleep is 0
    }

    /**
     * Tests if the pet's hunger is correctly increased.
     */
    @Test
    public void testPetHungerIncrease() {
        Pet pet = new Pet("Debatchi"); // Create a pet
        pet.setHunger(50); // Set initial hunger to 50
        pet.increaseHunger(10); // Increase hunger by 10
        assertEquals(60, pet.getHunger(), 0); // Verify hunger is now 60
    }

    /**
     * Tests if the pet's happiness is correctly increased.
     */
    @Test
    public void testPetHappinessIncrease() {
        Pet pet = new Pet("Debatchi"); // Create a pet
        pet.setHappiness(30); // Set initial happiness to 30
        pet.increaseHappiness(20); // Increase happiness by 20
        assertEquals(50, pet.getHappiness(), 0); // Verify happiness is now 50
    }

    /**
     * Tests if the pet's health is correctly increased.
     */
    @Test
    public void testPetHealthIncrease() {
        Pet pet = new Pet("Debatchi"); // Create a pet
        pet.setHealth(70); // Set initial health to 70
        pet.increaseHealth(15); // Increase health by 15
        assertEquals(85, pet.getHealth(), 0); // Verify health is now 85
    }

    /**
     * Tests if the pet's sleep is correctly increased.
     */
    @Test
    public void testPetSleepIncrease() {
        Pet pet = new Pet("Debatchi"); // Create a pet
        pet.setSleep(40); // Set initial sleep to 40
        pet.increaseSleep(25); // Increase sleep by 25
        assertEquals(65, pet.getSleep(), 0); // Verify sleep is now 65
    }
}
