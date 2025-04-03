import java.io.Serializable;
import javax.swing.ImageIcon;
    
public class Pet implements Serializable {
    private static final long serialVersionUID = 1L;

    // Define a permanent emotion duration constant
    private static final long PERMANENT_EMOTION_DURATION = Long.MAX_VALUE;

    private String state;
    private String type; // pet1
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

        // Constructors
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
        
        public Pet(String state, double health, double happiness, double hunger,
                double sleep, double maxHealth,
                double maxHappiness, double maxHunger, double maxSleep) {
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
        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            in.defaultReadObject();
            if (imagePath != null) {
                petImage = new ImageIcon(imagePath);
            }
        }

        public String getType(){
            return this.type;
        }
        public String getName(){
            return name;
        }
        public void updateRates(int specifcation){
            switch(specifcation){
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
                System.out.println("healthRate: " + healthRate);
                System.out.println("Health" + health);
                happinessRate = 0.7;
                hungerRate = 0.7;
                break;
                case 3:
                sleepRate = -0.4;
                healthRate = -0.4;
                happinessRate = 0.4;
                hungerRate = 0.4;
                break;
            }
        }

    
        public void increment(){
        increaseSleep(sleepRate);
        increaseHealth(healthRate);
        decrementHappiness(happinessRate);
        decrementHunger(hungerRate);
        }
       
        public void setName(String name) {
            this.name = name;
        }
        

        // Getters
        public ImageIcon getPetImage() { return petImage; }
    
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
        


        public String getState() {
            return this.state;
        }

        public double getHealth() {
            return this.health;
        }

        public double getHappiness() {
            return this.happiness;
        }

        public double getHunger() {
            return this.hunger;
        }

        public double getSleep() {
            return this.sleep;
        }

    
        public double getMaxHealth() {
            return this.maxHealth;
        }

        public double getMaxHappiness() {
            return this.maxHappiness;
        }

        public double getMaxHunger() {
            return this.maxHunger;
        }

        public double getMaxSleep() {
            return this.maxSleep;
        }

        // Setters
        public void setState(String state) {
            this.state = state;
        }

        public void setHealth(double health) {
            // Optionally clamp to maxHealth if needed, e.g.:
            // this.health = Math.min(health, this.maxHealth);
            this.health = health;
        }

        public void setHappiness(double happiness) {
            // Optionally clamp to maxHappiness
            this.happiness = happiness;
        }

        public void setHunger(double hunger) {
            // Optionally clamp to maxHunger
            this.hunger = hunger;
        }

        public void setSleep(double sleep) {
            // Optionally clamp to maxSleep
            this.sleep = sleep;
        }

        /**
         * Sets the pet's emotion with a specified duration
         * @param emotion The emotion to display
         * @param durationMs Duration in milliseconds (0 for a permanent emotion)
         */
        public void setEmotion(String emotion, int durationMs) {
            this.emotion = emotion;
            
            // Check if this should be a permanent emotion (state-based)
            if (durationMs == 0) {
                this.emotionEndTime = PERMANENT_EMOTION_DURATION;
                this.isPermanentEmotion = true;
                System.out.println("Setting PERMANENT emotion to: " + emotion);
            } else {
                this.emotionEndTime = System.currentTimeMillis() + durationMs;
                this.isPermanentEmotion = false;
                System.out.println("Setting emotion to: " + emotion + " for " + durationMs + "ms");
            }
        }

         /**
         * Gets the current emotion, accounting for state and timing
         * @return The current emotion to display
         */
        public String getEmotion() {
            // Debug information
            System.out.println("EMOTION DEBUG - Current emotion: " + emotion + 
                            ", End time: " + emotionEndTime + 
                            ", Current time: " + System.currentTimeMillis() +
                            ", Diff: " + (emotionEndTime - System.currentTimeMillis()) +
                            ", Is permanent: " + isPermanentEmotion);
            
            // Check if a temporary emotion has expired
            if (!isPermanentEmotion && System.currentTimeMillis() > emotionEndTime && !emotion.equals("neutral")) {
                System.out.println("Emotion " + emotion + " expired, returning to neutral");
                emotion = "neutral";
            }
            
            return emotion;
        }


        /**
         * Gets the appropriate image path based on pet state and emotion
         * @return The file path to the appropriate emotion image
         */
        public String getEmotionImagePath() {
            // Base path for pet sprites
            String basePath = "assets\\images\\petSprites\\";
            
            // Get pet type name and determine pet ID
            String petTypeName = getType();
            String petId;
            
            switch (petTypeName) {
                case "Debatchi": petId = "pet1"; break;
                case "Tsuyopitchi": petId = "pet2"; break;
                case "Kikitchi": petId = "pet3"; break;
                case "Mametchi": petId = "pet4"; break;
                default: petId = "pet1"; break;
            }
            
            // Determine which emotion to display based on state first
            String displayEmotion;
            String state = getState();
            String currentEmotion = emotion; // Direct access to avoid debug logs every time
            
            // State-based emotions take priority over temporary ones
            switch (state) {
                case "dead":
                    displayEmotion = "sad"; // Use sad image for dead state
                    break;
                case "sleep":
                    displayEmotion = "blink"; // Use nervous for sleep state
                    break;
                case "hungry":
                    displayEmotion = "discontent"; // Use discontent for hungry state
                    break;
                case "angry":
                    displayEmotion = "angry"; // Use angry for angry state
                    break;
                case "nervous":
                    displayEmotion = "nervous"; // Use nervous for nervous state
                    break;
                case "vet":
                    displayEmotion = "nervous"; // Use nervous for vet state
                default:
                    // For default state, use the current emotion
                    displayEmotion = currentEmotion;
                    break;
            }
            
            // Construct the full image path
            String imagePath = basePath + petTypeName + "\\head1\\" + petId + "_head1_" + displayEmotion + ".png";
            
            // Verify the file exists
            java.io.File file = new java.io.File(imagePath);
            if (!file.exists()) {
                System.out.println("WARNING: Image file does not exist: " + imagePath);
                // Fall back to neutral if file doesn't exist
                imagePath = basePath + petTypeName + "\\head1\\" + petId + "_head1_neutral.png";
                
                // Check if the fallback exists
                file = new java.io.File(imagePath);
                if (!file.exists()) {
                    System.out.println("CRITICAL: Even fallback image doesn't exist: " + imagePath);
                    // Last resort fallback to Debatchi neutral
                    imagePath = "assets\\images\\petSprites\\Debatchi\\head1\\pet1_head1_neutral.png";
                }
            }
            
            return imagePath;
        }

        public long getEmotionEndTime() {
            return emotionEndTime;
        }
    }