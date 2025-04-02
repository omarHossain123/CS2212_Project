import java.io.Serializable;
    import javax.swing.ImageIcon;
    
    public class Pet implements Serializable {
        private static final long serialVersionUID = 1L;
    

        private String state;

        private String type;//pet1
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
        private String imagePath; // Store the path to recreate the image after deserialization Leads to the file + type"pet1" + "Dead.jpg"

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
                case "Tsuyopichi":
                    this.health = 105.0;
                    this.happiness = 95.0;
                    this.sleep = 80.0;
                    this.hunger = 100.0;
                    this.maxHealth = 105.0;
                    this.maxHappiness = 95.0;
                    this.maxHunger = 100.0;
                    this.maxSleep = 80.0;
                    this.type = "Tsuyopichi";
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
                    this.type = "Mameitchi";
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
                sleepRate = -10;
                healthRate = 0;
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
    }