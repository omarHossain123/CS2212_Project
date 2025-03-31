import java.io.*;
import java.util.*;
import javax.swing.*;

public class UserInterface {
    private static Pet currentPet;
    private static final String SAVES_DIRECTORY = "saves";
    private static String currentSaveFile;

    public static void main(String[] args) {
        // Create saves directory if it doesn't exist
        createSavesDirectory();
        
        SwingUtilities.invokeLater(() -> {
            showMainMenu();
        });
    }

    private static void createSavesDirectory() {
        File savesDir = new File(SAVES_DIRECTORY);
        if (!savesDir.exists()) {
            savesDir.mkdir();
            System.out.println("Created saves directory: " + savesDir.getAbsolutePath());
        }
    }

    public static void showMainMenu() {
        Main_Menu mainMenu = new Main_Menu();

        mainMenu.getStartNewGameButton().addActionListener(e -> {
            mainMenu.dispose();
            PetSelection.selectPet(newPet -> {
                currentPet = newPet;
                
                // Prompt for save name when starting a new game
                String saveName = promptForSaveName(null);
                if (saveName != null) {
                    currentSaveFile = saveName;
                    mainGame game = new mainGame(currentPet);
                    
                    // Add window listener to save game on close
                    game.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            saveGame();
                        }
                    });
                    
                    game.setVisible(true);
                } else {
                    // User canceled save name prompt, go back to main menu
                    showMainMenu();
                }
            });            
        });

        mainMenu.getContinueGameButton().addActionListener(e -> {
            List<String> saveFiles = getSaveFiles();
            if (saveFiles.isEmpty()) {
                JOptionPane.showMessageDialog(mainMenu,
                        "No saved games found. Please start a new game.",
                        "No Saves Found",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Show save file selection dialog
                String selectedSave = (String) JOptionPane.showInputDialog(
                        mainMenu,
                        "Select a saved game to load:",
                        "Load Game",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        saveFiles.toArray(),
                        saveFiles.get(0));
                
                if (selectedSave != null) {
                    currentSaveFile = selectedSave;
                    if (loadGame()) {
                        mainMenu.dispose();
                        mainGame game = new mainGame(currentPet);
                        
                        // Add window listener to save game on close
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
     * Prompts the user for a save file name
     * @param defaultName Default name suggestion (or null)
     * @return The save file name entered by user, or null if canceled
     */
    private static String promptForSaveName(String defaultName) {
        String saveName = JOptionPane.showInputDialog(
                null,
                "Enter a name for this save file:",
                "Save Game",
                JOptionPane.QUESTION_MESSAGE);
        
        if (saveName == null || saveName.trim().isEmpty()) {
            return null;
        }
        
        // Sanitize the save name to be a valid filename
        saveName = saveName.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");
        
        // Check if save already exists
        File saveFile = new File(SAVES_DIRECTORY + File.separator + saveName + ".dat");
        if (saveFile.exists()) {
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "A save with this name already exists. Overwrite?",
                    "Save Exists",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            
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
                    saveFiles.add(name.substring(0, name.length() - 4)); // Remove .dat extension
                }
            }
        }
        
        return saveFiles;
    }

    public static void saveGame() {
        if (currentPet == null || currentSaveFile == null) {
            return;
        }
        
        String savePath = SAVES_DIRECTORY + File.separator + currentSaveFile + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savePath))) {
            oos.writeObject(currentPet);
            System.out.println("Game saved successfully to " + savePath);
            
            // Show confirmation dialog
            JOptionPane.showMessageDialog(null,
                    "Game saved successfully!",
                    "Save Complete",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Failed to save game: " + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
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
            currentPet = (Pet) ois.readObject();
            System.out.println("Game loaded successfully from " + savePath);
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Failed to load game: " + e.getMessage(),
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
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
}