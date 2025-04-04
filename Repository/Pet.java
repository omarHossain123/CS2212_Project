import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * The Pet class represents a virtual pet with various attributes such as health,
 * happiness, hunger, and sleep. It provides methods to manage the pet's state,
 * update its stats, and handle emotions.
 * 
 * Features:
 * - Tracks the pet's stats (health, happiness, hunger, sleep).
 * - Manages state transitions (e.g., default, hungry, angry, sleep).
 * - Handles emotions and their durations.
 * - Supports serialization for saving and loading pet data.
 * 
 * @author Ahmed, Omar
 */
public class Pet implements Serializable {
    private static final long serialVersionUID = 1L;

    // Define a permanent emotion duration constant
    private static final long PERMANENT_EMOTION_DURATION = Long.MAX_VALUE;

    private String state;
    private String type; // Pet type (e.g., Debatchi, Tsuyopitchi)
    private double health;
    private double happiness;
    private double hunger;
    private double sleep;
    private double sleepRate;
    private double healthRate;
    private double happinessRate;
    private double hungerRate;
    private double maxHealth;
    private double maxHappiness;
    private double maxHunger;
    private double maxSleep;
    private String name;
    private transient ImageIcon petImage;
    private String imagePath;

    private String emotion = "neutral"; // Default emotion
    private long emotionEndTime = 0; // When to end the current emotion
    private boolean isPermanentEmotion = false; // Whether the current emotion is tied to a state

    /**
     * Constructs a Pet object with the specified type.
     * Initializes the pet's stats based on its type.
     * 
     * @param type The type of the pet (e.g., "Debatchi", "Tsuyopitchi").
     *
     */
    public Pet(String type) {
        this.state = "default";
        updateRates(3);
        switch (type) {
            case "Debatchi":
                this.health = 120.0;
                this.happiness = 90.0;
                this.sleep = 85.0;
                this.hunger = 95.0;
                this.maxHealth = 120.0;
                this.maxHappiness = 90.0;
                this.maxHunger = 95.0;
                this.maxSleep = 85.0;
                this.type = "Debatchi";
                updateRates(3);
                break;
            case "Tsuyopitchi":
                this.health = 105.0;
                this.happiness = 95.0;
                this.sleep = 80.0;
                this.hunger = 100.0;
                this.maxHealth = 105.0;
                this.maxHappiness = 95.0;
                this.maxHunger = 100.0;
                this.maxSleep = 80.0;
                this.type = "Tsuyopitchi";
                break;
            case "Kikitchi":
                this.health = 100.0;
                this.happiness = 90.0;
                this.sleep = 85.0;
                this.hunger = 95.0;
                this.maxHealth = 100.0;
                this.maxHappiness = 90.0;
                this.maxHunger = 95.0;
                this.maxSleep = 85.0;
                this.type = "Kikitchi";
                break;
            case "Mametchi":
                this.health = 110.0;
                this.happiness = 85.0;
                this.sleep = 90.0;
                this.hunger = 85.0;
                this.maxHealth = 110.0;
                this.maxHappiness = 85.0;
                this.maxHunger = 85.0;
                this.maxSleep = 90.0;
                this.type = "Mametchi";
                break;
            default:
                System.out.println("Invalid pet type");
                break;
        }
    }

    /**
     * Constructs a Pet object with custom stats.
     * 
     * @param state The initial state of the pet.
     * @param health The initial health of the pet.
     * @param happiness The initial happiness of the pet.
     * @param hunger The initial hunger of the pet.
     * @param sleep The initial sleep of the pet.
     * @param maxHealth The maximum health of the pet.
     * @param maxHappiness The maximum happiness of the pet.
     * @param maxHunger The maximum hunger of the pet.
     * @param maxSleep The maximum sleep of the pet.
     * 
    public Pet(String state, double health, double happiness, double hunger,
               double sleep, double maxHealth, double maxHappiness, double maxHunger, double maxSleep) {
        this.state = state;
        this.health = health;
        this.happiness = happiness;
        this.hunger = hunger;
        this.sleep = sleep;
        this.maxHealth = maxHealth;
        this.maxHappiness = maxHappiness;
        this.maxHunger = maxHunger;
        this.maxSleep = maxSleep;
    }

    /**
     * Constructs a Pet object with a name, type, stats, and an image path.
     * 
     * @param name The name of the pet.
     * @param type The type of the pet.
     * @param health The initial health of the pet.
     * @param happiness The initial happiness of the pet.
     * @param sleep The initial sleep of the pet.
     * @param hunger The initial hunger of the pet.
     * @param imagePath The file path to the pet's image.
     * 
     */
    public Pet(String name, String type, int health, int happiness, int sleep, int hunger, String imagePath) {
        this.name = name;
        this.type = type;
        this.health = health;
        this.happiness = happiness;
        this.sleep = sleep;
        this.hunger = hunger;
        this.imagePath = imagePath;
        this.petImage = new ImageIcon(imagePath);
    }

    /**
     * Custom deserialization method to restore transient fields.
     * 
     * @param in The ObjectInputStream to read from.
     * @throws java.io.IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     * 
     */
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (imagePath != null) {
            petImage = new ImageIcon(imagePath);
        }
    }

    /**
     * Updates the rates for sleep, health, happiness, and hunger based on the specified mode.
     * 
     * @param specification The mode to update rates (0: stop, 1: sleep, 2: hungry, 3: default).
     * 
     */
    public void updateRates(int specification) {
        switch (specification) {
            case 0:
                sleepRate = 0;
                healthRate = 0;
                happinessRate = 0;
                hungerRate = 0;
                break;
            case 1:
                sleepRate = 1;
                healthRate = 0;
                happinessRate = 0.4;
                hungerRate = 0.4;
                break;
            case 2:
                healthRate = -1;
                happinessRate = 0.7;
                hungerRate = 0.7;
                break;
            case 3:
                sleepRate = -0.4;
                healthRate = 0;
                happinessRate = 0.4;
                hungerRate = 0.4;
                break;
        }
    }

    /**
     * Increments the pet's stats based on their respective rates.
     * 
     * 
     */
    public void increment() {
        increaseSleep(sleepRate);
        increaseHealth(healthRate);
        decrementHappiness(happinessRate);
        decrementHunger(hungerRate);
    }

    /**
     * Gets the type of the pet.
     * 
     * @return The type of the pet (e.g., "Debatchi", "Tsuyopitchi").
     * 
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets the name of the pet.
     * 
     * @return The name of the pet as a String.
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     * 
     * @param name The new name of the pet as a String.
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the pet's image as an ImageIcon.
     * 
     * @return The pet's image as an ImageIcon object, or null if not set or deserialized incorrectly.
     * 
     */
    public ImageIcon getPetImage() {
        return petImage;
    }

    @Override
    public String toString() {
        return "<html>" + name + " the " + type + "<br>Health: " + health + "<br>Happiness: " + happiness +
                "<br>Sleep: " + sleep + "<br>Hunger: " + hunger + "</html>";
    }

    public void increaseHealth(double increment) {
        this.health += increment;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void increaseHappiness(double increment) {
        this.happiness += increment;
        if (this.happiness > this.maxHappiness) {
            this.happiness = this.maxHappiness;
        }
    }

    public void increaseHunger(double increment) {
        this.hunger += increment;
        if (this.hunger > this.maxHunger) {
            this.hunger = this.maxHunger;
        }
    }

    public void increaseSleep(double increment) {
        this.sleep += increment;
        if (this.sleep > this.maxSleep) {
            this.sleep = this.maxSleep;
        }
        if (this.sleep < 0) {
            this.sleep = 0;
        }
    }

    public void decrementHealth(double decrement) {
        this.health -= decrement;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void decrementHappiness(double decrement) {
        this.happiness -= decrement;
        if (this.happiness < 0) {
            this.happiness = 0;
        }
    }

    public void decrementHunger(double decrement) {
        this.hunger -= decrement;
        if (this.hunger < 0) {
            this.hunger = 0;
        }
    }

    public void decrementSleep(double decrement) {
        this.sleep -= decrement;
        if (this.sleep < 0) {
            this.sleep = 0;
        }
    }

    /**
     * Gets the current state of the pet.
     * 
     * @return The current state of the pet (e.g., "default", "hungry", "angry"). 
     * 
     */
    public String getState() {
        return this.state;
    }

    /**
     * Sets the current state of the pet.
     * 
     * @param state The new state of the pet (e.g., "default", "hungry", "angry"). 
     * 
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the current health of the pet.
     * 
     * @return The current health as a double value between 0 and maxHealth. 
     * 
     */
    public double getHealth() {
        return this.health;
    }

    /**
     * Sets the health of the pet.
     * 
     * @param health The new health value as a double. Must be between 0 and maxHealth. 
     * @throws IllegalArgumentException if the value is out of range. 
     * 
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Gets the current happiness of the pet.
     * 
     * @return The current happiness as a double value between 0 and maxHappiness. 
     */
    public double getHappiness() {
        return this.happiness;
    }

    /**
     * Sets the happiness of the pet.
     * 
     * @param happiness The new happiness value as a double. Must be between 0 and maxHappiness. 
     */
    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    /**
     * Gets the current hunger level of the pet.
     * 
     * @return The current hunger as a double value between 0 and maxHunger. 
     */
    public double getHunger() {
        return this.hunger;
    }

    /**
     * Sets the hunger level of the pet.
     * 
     * @param hunger The new hunger value as a double. Must be between 0 and maxHunger. 
     */
    public void setHunger(double hunger) {
        this.hunger = hunger;
    }

    /**
     * Gets the current sleep level of the pet.
     * 
     * @return The current sleep as a double value between 0 and maxSleep. 
     */
    public double getSleep() {
        return this.sleep;
    }

    /**
     * Sets the sleep level of the pet.
     * 
     * @param sleep The new sleep value as a double. Must be between 0 and maxSleep. 
     */
    public void setSleep(double sleep) {
        this.sleep = sleep;
    }

    /**
     * Gets the maximum health of the pet.
     * 
     * @return The maximum health as a double value. 
     */
    public double getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Gets the maximum happiness of the pet.
     * 
     * @return The maximum happiness as a double value. 
     */
    public double getMaxHappiness() {
        return this.maxHappiness;
    }

    /**
     * Gets the maximum hunger level of the pet.
     * 
     * @return The maximum hunger as a double value. 
     */
    public double getMaxHunger() {
        return this.maxHunger;
    }

    /**
     * Gets the maximum sleep level of the pet.
     * 
     * @return The maximum sleep as a double value. 
     */
    public double getMaxSleep() {
        return this.maxSleep;
    }

    /**
     * Sets the pet's emotion with a specified duration.
     * 
     * @param emotion The emotion to display.
     * @param durationMs Duration in milliseconds (0 for a permanent emotion).
     */
    public void setEmotion(String emotion, int durationMs) {
        this.emotion = emotion;
        if (durationMs == 0) {
            this.emotionEndTime = PERMANENT_EMOTION_DURATION;
            this.isPermanentEmotion = true;
        } else {
            this.emotionEndTime = System.currentTimeMillis() + durationMs;
            this.isPermanentEmotion = false;
        }
    }

    /**
     * Gets the current emotion, accounting for state and timing.
     * 
     * @return The current emotion to display.
     */
    public String getEmotion() {
        if (!isPermanentEmotion && System.currentTimeMillis() > emotionEndTime && !emotion.equals("neutral")) {
            emotion = "neutral";
        }
        return emotion;
    }

    /**
     * Gets the appropriate image path based on pet state and emotion.
     * 
     * @return The file path to the appropriate emotion image.
     */
    public String getEmotionImagePath() {
        String basePath = "assets\\images\\petSprites\\";
        String petTypeName = getType();
        String petId;

        switch (petTypeName) {
            case "Debatchi":
                petId = "pet1";
                break;
            case "Tsuyopitchi":
                petId = "pet2";
                break;
            case "Kikitchi":
                petId = "pet3";
                break;
            case "Mametchi":
                petId = "pet4";
                break;
            default:
                petId = "pet1";
                break;
        }

        String displayEmotion;
        String state = getState();
        String currentEmotion = emotion;

        switch (state) {
            case "dead":
                displayEmotion = "sad";
                break;
            case "sleep":
                displayEmotion = "blink";
                break;
            case "hungry":
                displayEmotion = "discontent";
                break;
            case "angry":
                displayEmotion = "angry";
                break;
            case "nervous":
                displayEmotion = "nervous";
                break;
            case "vet":
                displayEmotion = "nervous";
            default:
                displayEmotion = currentEmotion;
                break;
        }

        String imagePath = basePath + petTypeName + "\\head1\\" + petId + "_head1_" + displayEmotion + ".png";
        java.io.File file = new java.io.File(imagePath);
        if (!file.exists()) {
            imagePath = basePath + petTypeName + "\\head1\\" + petId + "_head1_neutral.png";
            file = new java.io.File(imagePath);
            if (!file.exists()) {
                imagePath = "assets\\images\\petSprites\\Debatchi\\head1\\pet1_head1_neutral.png";
            }
        }

        return imagePath;
    }

    /**
     * Gets the end time of the current emotion.
     * 
     * @return The end time of the current emotion as a long value in milliseconds. 
     */
    public long getEmotionEndTime() {
        return emotionEndTime;
    }
}