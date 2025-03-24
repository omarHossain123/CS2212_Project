import javax.swing.*;

public class Pet {
    private String name;
    private String type;
    private int health;
    private int happiness;
    private int sleep;
    private int hunger;
    private ImageIcon petImage;

    public Pet(String name, String type, String imagePath) {
        this.name = name;
        this.type = type;
        this.health = 100;
        this.happiness = 100;
        this.sleep = 100;
        this.hunger = 100;
        this.petImage = new ImageIcon(imagePath);
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getHealth() { return health; }
    public int getHappiness() { return happiness; }
    public int getSleep() { return sleep; }
    public int getHunger() { return hunger; }
    public ImageIcon getPetImage() { return petImage; }

    public void decreaseHealth(int amount) {
        health = Math.max(0, health - amount);
    }

    public void decreaseHappiness(int amount) {
        happiness = Math.max(0, happiness - amount);
    }

    public void decreaseSleep(int amount) {
        sleep = Math.max(0, sleep - amount);
    }

    public void decreaseHunger(int amount) {
        hunger = Math.max(0, hunger - amount);
    }

    public void increaseHappiness(int amount) {
        happiness = Math.min(100, happiness + amount);
    }

    public void feed() {
        hunger = Math.min(100, hunger + 20);
        health = Math.min(100, health + 5);
    }

    public void sleep() {
        sleep = 100;
        happiness -= 5; // Slightly unhappy from sleeping
    }

    public void play() {
        happiness = Math.min(100, happiness + 10);
        hunger -= 10;
        sleep -= 5;
    }

    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public String toString() {
        return name + " the " + type + " (Health: " + health + ", Happiness: " + happiness + 
               ", Sleep: " + sleep + ", Hunger: " + hunger + ")";
    }
}
