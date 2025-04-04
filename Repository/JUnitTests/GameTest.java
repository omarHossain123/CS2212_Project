package JUnitTests;

import org.junit.*;

import Game;

import static org.junit.Assert.*;

/**
 * Unit tests for the Game class to verify pet state transitions and valid states.
 */
public class GameTest {

    /**
     * Tests if the pet is in a valid "hungry" state.
     */
    @Test
    public void validStateHungry() {
        Game game = new Game("Debatchi");
        game.getPet().setState("hungry"); // Set state to "hungry"
        assertTrue(game.validState()); // Verify the state is valid
    }

    /**
     * Tests if the pet is in a valid "angry" state.
     */
    @Test
    public void validStateAngry() {
        Game game = new Game("Debatchi");
        game.getPet().setState("angry"); // Set state to "angry"
        assertFalse(game.validState()); // Verify the state is valid
    }

    /**
     * Tests if the pet is in a valid "sleep" state.
     */
    @Test
    public void validStateSleep() {
        Game game = new Game("Debatchi");
        game.getPet().setState("sleep"); // Set state to "sleep"
       assertFalse(game.validState()); // Verify the state is valid
    }

    /**
     * Tests if the pet is in a valid "dead" state.
     */
    @Test
    public void validStateDead() {
        Game game = new Game("Debatchi");
        game.getPet().setState("dead"); // Set state to "dead"
        assertFalse(game.validState()); // Verify the state is valid
    }

    /**
     * Tests if the pet is in a valid "default" state.
     */
    @Test
    public void validStateDefault() {
        Game game = new Game("Debatchi");
        game.getPet().setState("default"); // Set state to "default"
        assertTrue(game.validState()); // Verify the state is valid
    }

    /**
     * Tests if the game transitions to the "hungry" state when the pet's hunger is 0.
     */
    @Test
    public void checkHungryStateTest() {
        Game game = new Game("Debatchi");
        game.getPet().setHunger(0); // Set hunger to 0
        assertEquals("hungry", game.checkState()); // Verify the state is "hungry"
    }

    /**
     * Tests if the game transitions to the "angry" state when the pet's happiness is 0.
     */
    @Test
    public void checkAngryStateTest() {
        Game game = new Game("Debatchi");
        game.getPet().setHappiness(0); // Set happiness to 0
        assertEquals("angry", game.checkState()); // Verify the state is "angry"
    }

    /**
     * Tests if the game transitions to the "sleep" state when the pet's sleep stat is 0.
     */
    @Test
    public void checkSleepStateTest() {
        Game game = new Game("Debatchi");
        game.getPet().setSleep(0); // Set sleep to 0
        assertEquals("sleep", game.checkState()); // Verify the state is "sleep"
    }

    /**
     * Tests if the game transitions to the "dead" state when the pet's health is 0.
     */
    @Test
    public void checkDeadStateTest() {
        Game game = new Game("Debatchi");
        game.getPet().setHealth(0); // Set health to 0
        assertEquals("dead", game.checkState()); // Verify the state is "dead"
    }

    /**
     * Tests if the game transitions from "hungry" to "default" when conditions are met.
     */
    @Test
    public void changeStateHungryTest() {
        Game game = new Game("Debatchi");
        game.getPet().setState("hungry");
        game.getPet().setHunger(100); // Set health to 0
        assertTrue(game.changeState());
    }

    /**
     * Tests if the game transitions from "angry" to "default" when conditions are met.
     */
    @Test
    public void changeStateAngryTest() {
        Game game = new Game("Debatchi");
        game.getPet().setState("angry"); // Set state to "angry"
        game.getPet().setHappiness(50); // Set happiness to a valid level
        assertTrue(game.changeState()); // Verify the state changes to "default"
        assertEquals("default", game.getPet().getState()); // Verify the new state is "default"
    }

    /**
     * Tests if the game transitions from "sleep" to "default" when conditions are met.
     */
    @Test
    public void changeStateSleepTest() {
        Game game = new Game("Debatchi");
        game.getPet().setState("sleep"); // Set state to "sleep"
        game.getPet().setSleep(game.getPet().getMaxSleep()); // Fully restore sleep
        assertTrue(game.changeState()); // Verify the state changes to "default"
        assertEquals("default", game.getPet().getState()); // Verify the new state is "default"
    }

    /**
     * Tests if the game does not transition from "hungry" to "default" when conditions are not met.
     */
    @Test
    public void changeStateHungryFailTest() {
        Game game = new Game("Debatchi");
        game.getPet().setState("hungry"); // Set state to "hungry"
        game.getPet().setHunger(0); // Hunger is not restored
        assertFalse(game.changeState()); // Verify the state does not change
        assertEquals("hungry", game.getPet().getState()); // Verify the state remains "hungry"
    }

   

    /**
     * Tests if the game does not transition from "sleep" to "default" when conditions are not met.
     */
    @Test
    public void changeStateSleepFailTest() {
        Game game = new Game("Debatchi");
        game.getPet().setState("sleep"); // Set state to "sleep"
        game.getPet().setSleep(0); // Sleep is not restored
        assertFalse(game.changeState()); // Verify the state does not change
        assertEquals("sleep", game.getPet().getState()); // Verify the state remains "sleep"
    }
}