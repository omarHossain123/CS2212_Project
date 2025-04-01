/**
 * Game class handles the game logic and keeps track of the pet and inventory
 * <p>
 * This class will handle the main game logic, and will interact with the pet and 
 * inventory class to manage and run the game logic. 
 * @Author Ahmed Sinjab
 * @version 1.1
 */


 public class Game {
    // Handles the game logic, keeps track of pet states, and manages inventory.
    // code goes here...
    private String Name;
    /**
     * The allowedTime varaible will store the time limit of the player
     * Total Allowed Time - Total Played time
     */
    private float allowedTime;
    /**
     * inventory will be the inventory of the game and its of class Inventory
     */
    private Inventory inventory;
    /**
     * pet will be the pet for the game, of which the character interact with, of
     * cLass Pet
     */
    private Pet pet;
    /**
     * timePlayed will be the variable that stores the amount of time the play spent
     * on the game
     */
    private float timePlayed;
    /**
     * score will be the variable to keep track of the players "score"
     */
    private double score;
    /**
     * lockedPets will be the array storing the pets the player has not unlocked yet
     */
    private boolean lockedPets[];

    /**
     * Constroctor for class Game made to intialize the game class
     */
    public Game(String Type) {
        timePlayed = 0;
        score = 0;
        pet = new Pet(Type);
        inventory = new Inventory(Type, score);
        score = 0;
    }

    /**
     * This one for new game
     * A Constroctor of class Game, this creates the Game object with the inputed
     * data and will manage all the details of the game
     * 
     * @param p          This will be the pet which the game will manage
     * @param locked     This will be the list of all the locked pets
     * @param unlocked   This will be the list of all unlocked pets
     * @param allowed    This will be the time allowed to play for the player
     * @param invent     This will be the inventory that will be used by the game
     *                   and that is assoicated with the pet.
     * @param scoreSoFar This will store the score of this pet so far.
     * @param time       This will store the time played so far.
     */
    public Game(Pet p, boolean locked[], float allowed, Inventory invent, double scoreSoFar, float time) {
        // Store each variable into the expected variable in the class
        pet = p;
        lockedPets = locked;
        allowedTime = allowed;
        inventory = invent;
        score = scoreSoFar;
        timePlayed = time;

    }
   public Pet getPet(){
       return pet;
   }
    // public void Game(){
    public void gameStats(int decrements){
        pet.decrementHealth(decrements);
    }
    public void gameDecrement(){
       pet.increment();
    }
    public void increaseScore(float increase) {
        score = score + increase;

    }
    public float getScore(){
       return (float) score;
    }
    public Inventory getInventory(){
       return this.inventory;
    }
   public String petType(){
       return pet.getType();
   }
   public void setScore(double s){
       score = (float)s;
     
   }
    public boolean goToBed(String state) {
        if ((state != "dead") || (state != "angry") || (state != "sleep")) {
            pet.setState("sleep");
            return true;
        } else {
            return false;
        }
    }

    public boolean validState() {
       System.out.println(pet.getState());
        if ((pet.getState() == "default") || (pet.getState() == "hungry")) {
            return true;
        } else {
            return false;
        }
    }

    // }
    /**
     * This method is ment to retrive the state that the pet is in
     * 
     * @return State of pet in String
     */

    public String checkState() {
        if (pet.getHealth() == 0) {
            pet.setState("dead");
            pet.updateRates(0);
            return "dead";
        } else if (pet.getSleep() == 0) {
            pet.decrementHealth(15);
            if (pet.getHealth() != 0) {
                pet.setState("sleep");
                pet.updateRates(1);
                return "sleep";
            } else {
                pet.setState("dead");
                pet.updateRates(0);
                return "dead";
            }
            
        } else if (pet.getHappiness() == 0) {
            pet.setState("angry");
            return "angry";
        } else if (pet.getHunger() == 0) {
            pet.setState("hungry");
            pet.decrementHealth(15);
            pet.updateRates(2);
            return "hungry";
        }
        return "default";

    }

    public boolean takeToVet() {
        if (validState()) {
            pet.increaseHealth(25);
            pet.decrementSleep(5);
            pet.decrementHappiness(15);
            return true;
        } else {
            return false;
        }
    }
    public void TesterMethod(){
       pet.setSleep(20);
    }
    public boolean play() {
        if (validState()) {
            pet.increaseHappiness(10);
            increaseScore(200);
            return true;
        } else {
            return false;
        }
    }

    public boolean walk() {
        if (validState()) {
            pet.increaseHappiness(15);
            pet.decrementSleep(15);
            pet.decrementHunger(10);
            increaseScore(150);
            return true;
        } else {
            return false;
        }
    }

    public boolean gotToBed() {
        if (validState()) {
            pet.setState("sleep");
            pet.updateRates(1);
            return true;
        } else {
            return false;
        }
    }

    public boolean giveGift(int index) {
        double[] improve = inventory.useItem(index);
        if((0 > improve[0])||(0 > improve[1])){
            return false;

        }
        else{
            pet.increaseHunger(improve[0]);
            pet.increaseHappiness(improve[1]);
            return true;
        }
    }
    public boolean giveFood(int index){
        double[] improve = inventory.useItem(index);
        if((0 > improve[0])||(0 > improve[1])){
            return false;

        }
        else{
            pet.increaseHunger(improve[0]);
            pet.increaseHappiness(improve[1]);
            return true;
        }
    }

}
