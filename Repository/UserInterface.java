import java.io.*;
import java.time.LocalTime;
import java.util.*;
import javax.swing.*;

/**
 * The {@code UserInterface} class serves as the main entry point for the Virtual Pet Application.
 * It manages the main menu, game state, save/load functionality, and parental controls.
 * 
 * <p>This class provides methods to:
 * <ul>
 *   <li>Initialize the application</li>
 *   <li>Create the saves directory</li>
 *   <li>Display the main menu</li>
 *   <li>Handle parental control restrictions</li>
 *   <li>Save and load game data</li>
 * </ul>
 * 
 * <p>Authors:
 * <ul>
 *   <li>Ahmed Sinjab</li>
 *   <li>Omar Hossain</li>
 *   <li>Jacob Tran</li>
 * </ul>
 */
public class UserInterface {
    private static Pet currentPet;
    private static final String SAVES_DIRECTORY = "saves";
    private static String currentSaveFile;
    private static long sessionStartTime;
    private static boolean isApplicationRunning = false;
    // Add this field to store the loaded save data
    private static GameSaveData currentSaveData;

    /**
     * The main method serves as the entry point for the application.
     * It initializes the application, sets up the saves directory, and displays the main menu.
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Record session start time
        sessionStartTime = System.currentTimeMillis();
        isApplicationRunning = true;
        
        // Create saves directory if it doesn't exist
        createSavesDirectory();
        
        // Add shutdown hook to record session statistics
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (isApplicationRunning) {
                long sessionDuration = (System.currentTimeMillis() - sessionStartTime) / 1000; // Convert to seconds
                ParentalControls.updatePlayStatistics(sessionDuration);
                isApplicationRunning = false;
            }
        }));
        
        // Display the main menu
        SwingUtilities.invokeLater(() -> {
            showMainMenu();
        });
    }

    /**
     * Creates the saves directory if it does not already exist.
     * This ensures that save files can be stored in a dedicated directory.
     */
    private static void createSavesDirectory() {
        File savesDir = new File(SAVES_DIRECTORY);
        if (!savesDir.exists()) {
            savesDir.mkdir();
            System.out.println("Created saves directory: " + savesDir.getAbsolutePath());
        }
    }

    /**
     * Displays the main menu and sets up event listeners for menu actions.
     * The main menu allows the user to start a new game, continue a saved game, or exit the application.
     */
    public static void showMainMenu() {
        Main_Menu mainMenu = new Main_Menu();

        mainMenu.getStartNewGameButton().addActionListener(e -> {
            // Check parental controls before starting the game
            if (!ParentalControls.isPlayingAllowed()) {
                showTimeRestrictionMessage(mainMenu);
                return;
            }
            
            mainMenu.dispose();
            PetSelection.selectPet(newPet -> {
                currentPet = newPet;
                
                // Prompt for save name when starting a new game
                String saveName = promptForSaveName(null);
                if (saveName != null) {
                    currentSaveFile = saveName;
                    
                    // Create a new game 
                    mainGame game = new mainGame(currentPet, currentSaveData);
                    
                    // Add window listener to save the game on close
                    game.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            saveGame();
                        }
                    });
                    
                    game.setVisible(true);
                } else {
                    // User canceled save name prompt, go back to the main menu
                    showMainMenu();
                }
            });            
        });

        mainMenu.getContinueGameButton().addActionListener(e -> {
            // Check parental controls before loading the game
            if (!ParentalControls.isPlayingAllowed()) {
                showTimeRestrictionMessage(mainMenu);
                return;
            }
            
            List<String> saveFiles = getSaveFiles();
            if (saveFiles.isEmpty()) {
                StyledDialog.showInformationDialog(mainMenu,
                        "No saved games found. Please start a new game.",
                        "No Saves Found");
            } else {
                // Show save file selection dialog
                String selectedSave = StyledSaveSelector.showDialog(
                        mainMenu,
                        "Select a saved game to load:",
                        "Load Game",
                        saveFiles);
                
                if (selectedSave != null) {
                    currentSaveFile = selectedSave;
                    if (loadGame()) {
                        mainMenu.dispose();
                        
                        // Create the game with the loaded pet and save data
                        Pet loadedPet = currentSaveData.getPet();
                        mainGame game = new mainGame(loadedPet, currentSaveData);
                        
                        // Add window listener to save the game on close
                        game.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                saveGame();
                            }
                        });
                        
                        game.setVisible(true);
                    }
                }
            }
        });

        mainMenu.setVisible(true);
    }
    
    /**
     * Shows a message when play is restricted by parental controls
     */
    private static void showTimeRestrictionMessage(JFrame parent) {
        // Load settings to show the allowed time range
        ParentalControls.ParentalSettings settings = ParentalControls.loadSettingsStatic();
        String timeRange = "";
        
        if (settings != null) {
            LocalTime startTime = LocalTime.of(settings.startTimeHour, settings.startTimeMinute);
            LocalTime endTime = LocalTime.of(settings.endTimeHour, settings.endTimeMinute);
            timeRange = startTime.format(ParentalControls.TIME_FORMATTER) + 
                    " to " + 
                    endTime.format(ParentalControls.TIME_FORMATTER);
        }
        
        StyledDialog.showWarningDialog(parent,
            "Outside of allowed play time hours.\n" +
            "You can only play during the hours: " + timeRange + "\n" +
            "Please try again during your allowed play time.",
            "Play Time Restriction");
    }

    /**
     * Prompts the user for a save file name
     * @param defaultName Default name suggestion (or null)
     * @return The save file name entered by user, or null if canceled
     */
    private static String promptForSaveName(String defaultName) {
        String saveName = StyledDialog.showInputDialog(
                null,
                "Enter a name for this save file:",
                "Save Game",
                defaultName);
        
        if (saveName == null || saveName.trim().isEmpty()) {
            return null;
        }
        
        // Sanitize the save name to be a valid filename
        saveName = saveName.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");
        
        // Check if save already exists
        File saveFile = new File(SAVES_DIRECTORY + File.separator + saveName + ".dat");
        if (saveFile.exists()) {
            int option = StyledDialog.showConfirmDialog(
                    null,
                    "A save with this name already exists. Overwrite?",
                    "Save Exists");
            
            if (option == JOptionPane.YES_OPTION) {
                return saveName;
            } else if (option == JOptionPane.NO_OPTION) {
                return promptForSaveName(saveName); // Try again
            } else {
                return null; // Canceled
            }
        }
        
        return saveName;
    }

    /**
     * Gets a list of available save files
     * @return List of save file names without extensions
     */
    private static List<String> getSaveFiles() {
        List<String> saveFiles = new ArrayList<>();
        File savesDir = new File(SAVES_DIRECTORY);
        
        if (savesDir.exists() && savesDir.isDirectory()) {
            File[] files = savesDir.listFiles((dir, name) -> name.endsWith(".dat"));
            
            if (files != null) {
                for (File file : files) {
                    String name = file.getName();
                    // Exclude parental controls file from save files list
                    if (!name.equals("parental_controls.dat")) {
                        saveFiles.add(name.substring(0, name.length() - 4)); // Remove .dat extension
                    }
                }
            }
        }
        
        return saveFiles;
    }

    public static void saveGame() {
        if (currentPet == null || currentSaveFile == null) {
            return;
        }
        
        // Get reference to the game instance from mainGame
        mainGame gameInstance = null;
        
        // Find the mainGame instance if it exists
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            if (window instanceof mainGame) {
                gameInstance = (mainGame) window;
                break;
            }
        }
        
        // Create a GameSaveData object to store all relevant data
        GameSaveData saveData = new GameSaveData();
        
        // If we found the game instance, save all game state
        if (gameInstance != null) {
            Game game = gameInstance.game;
            
            // Save pet data - make sure to get the CURRENT pet from the game
            saveData.setPet(game.getPet());
            
            // Save score
            saveData.setScore(game.getScore());
            
            // Save inventory
            saveData.setInventory(game.getInventory());
            
            // Save cooldown timers
            saveData.setLastFeedTime(game.getLastFeedTime());
            saveData.setLastGiftTime(game.getLastGiftTime());
            saveData.setLastSleepTime(game.getLastSleepTime());
            saveData.setLastVetTime(game.getLastVetTime());
            saveData.setLastPlayTime(game.getLastPlayTime());
            saveData.setLastWalkTime(game.getLastWalkTime());
            
            System.out.println("DEBUG - Saving game with pet: " + saveData.getPet().getType() + 
                               ", Health: " + saveData.getPet().getHealth() + 
                               ", Score: " + saveData.getScore());
        } else {
            // If we couldn't find the game instance, just save the current pet
            saveData.setPet(currentPet);
            System.out.println("DEBUG - Could not find game instance, only saving pet data");
        }
        
        String savePath = SAVES_DIRECTORY + File.separator + currentSaveFile + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savePath))) {
            oos.writeObject(saveData);
            System.out.println("Game saved successfully to " + savePath);
            
            // Show confirmation dialog
            StyledDialog.showInformationDialog(null,
                    "Game saved successfully!",
                    "Save Complete");
        } catch (IOException e) {
            e.printStackTrace();
            StyledDialog.showErrorDialog(null,
                    "Failed to save game: " + e.getMessage(),
                    "Save Error");
        }
    }

    public static boolean loadGame() {
        if (currentSaveFile == null) {
            return false;
        }
        
        String savePath = SAVES_DIRECTORY + File.separator + currentSaveFile + ".dat";
        File saveFile = new File(savePath);
        
        if (!saveFile.exists()) {
            return false;
        }
    
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savePath))) {
            Object loadedObject = ois.readObject();
            
            // Handle both old save format (just Pet) and new format (GameSaveData)
            if (loadedObject instanceof GameSaveData) {
                GameSaveData saveData = (GameSaveData) loadedObject;
                currentPet = saveData.getPet();
                
                // Store the loaded save data
                currentSaveData = saveData;
                
                System.out.println("DEBUG - Loaded game data: Pet: " + saveData.getPet().getType() + 
                               ", Health: " + saveData.getPet().getHealth() + 
                               ", Score: " + saveData.getScore());
                
                System.out.println("Game loaded successfully from " + savePath);
                return true;
            } 
            else if (loadedObject instanceof Pet) {
                // Legacy support for old save files that only saved the Pet
                currentPet = (Pet) loadedObject;
                
                // Create a new GameSaveData with just the pet
                currentSaveData = new GameSaveData();
                currentSaveData.setPet(currentPet);
                
                System.out.println("DEBUG - Loaded legacy pet data: " + currentPet.getType());
                
                System.out.println("Legacy game save loaded successfully from " + savePath);
                return true;
            }
            else {
                throw new ClassNotFoundException("Unknown save file format");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            StyledDialog.showErrorDialog(null,
                    "Failed to load game: " + e.getMessage(),
                    "Load Error");
            return false;
        }
    }
    
    // Method to be called by the mainGame class to save the game at any time
    public static void saveCurrentGame() {
        if (currentSaveFile == null) {
            String saveName = promptForSaveName(null);
            if (saveName != null) {
                currentSaveFile = saveName;
                saveGame();
            }
        } else {
            saveGame();
        }
    }
    
    /**
     * Called when the application is exiting properly
     * Records the session statistics before shutdown
     */
    public static void safeExit() {
        if (isApplicationRunning) {
            System.out.println("DEBUG - Saving session statistics and exiting...");
            long sessionDuration = (System.currentTimeMillis() - sessionStartTime) / 1000; // Convert to seconds
            ParentalControls.updatePlayStatistics(sessionDuration);
            isApplicationRunning = false;
        }
        // Force exit with status code 0 (normal exit)
        System.exit(0);
    }
}