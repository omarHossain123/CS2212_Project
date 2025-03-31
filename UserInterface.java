import java.io.*;
import javax.swing.*;

public class UserInterface {
    private static Pet currentPet;
    private static final String SAVE_FILE = "pet_save.dat";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            showMainMenu();
        });
    }

    public static void showMainMenu() {
        Main_Menu mainMenu = new Main_Menu();

        mainMenu.getStartNewGameButton().addActionListener(e -> {
            mainMenu.dispose();
            PetSelection.selectPet(newPet -> {
                currentPet = newPet;
                mainGame game = new mainGame(currentPet);  // New line
                game.setVisible(true);
            });            
        });

        mainMenu.getContinueGameButton().addActionListener(e -> {
            if (loadGame()) {
                mainMenu.dispose();
                new GameScreen(currentPet);  // Launch game screen
            } else {
                JOptionPane.showMessageDialog(mainMenu,
                        "No saved game found. Please start a new game.",
                        "No Save Found",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        mainMenu.setVisible(true);
    }

    public static void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(currentPet);
            System.out.println("Game saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Failed to save game: " + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean loadGame() {
        File saveFile = new File(SAVE_FILE);
        if (!saveFile.exists()) {
            return false;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            currentPet = (Pet) ois.readObject();
            System.out.println("Game loaded successfully");
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
}
