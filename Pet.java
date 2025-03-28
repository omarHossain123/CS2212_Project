import java.io.Serializable;
import javax.swing.*;

public class Pet implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String type;
    private int health;
    private int happiness;
    private int sleep;
    private int hunger;
    private transient ImageIcon petImage;
    private String imagePath; // Store the path to recreate the image after deserialization
    
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
    
    // Make sure the ImageIcon is recreated after deserialization
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (imagePath != null) {
            petImage = new ImageIcon(imagePath);
        }
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() { return name; }
    public String getType() { return type; }
    public int getHealth() { return health; }
    public int getHappiness() { return happiness; }
    public int getSleep() { return sleep; }
    public int getHunger() { return hunger; }
    public ImageIcon getPetImage() { return petImage; }
    
    public void decreaseHealth(int amount) { health = Math.max(0, health - amount); }
    public void decreaseHappiness(int amount) { happiness = Math.max(0, happiness - amount); }
    public void decreaseSleep(int amount) { sleep = Math.max(0, sleep - amount); }
    public void decreaseHunger(int amount) { hunger = Math.max(0, hunger - amount); }
    public void increaseHappiness(int amount) { happiness = Math.min(100, happiness + amount); }
    
    public void feed() {
        hunger = Math.min(100, hunger + 20);
        health = Math.min(100, health + 5);
    }
    
    public void sleep() {
        sleep = 100;
        happiness = Math.max(0, happiness - 5);
    }
    
    public void play() {
        happiness = Math.min(100, happiness + 10);
        hunger = Math.max(0, hunger - 10);
        sleep = Math.max(0, sleep - 5);
    }
    
    public boolean isDead() { return health <= 0; }
    
    @Override
    public String toString() {
        return "<html>" + name + " the " + type + "<br>Health: " + health + "<br>Happiness: " + happiness + 
               "<br>Sleep: " + sleep + "<br>Hunger: " + hunger + "</html>";
    }
}