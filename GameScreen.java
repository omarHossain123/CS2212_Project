import java.awt.*;
import java.io.*;
import javax.swing.*;

public class GameScreen {
    private Pet currentPet;
    private final String SAVE_FILE = "pet_save.dat";

    public GameScreen(Pet pet) {
        this.currentPet = pet;
        startGame();
    }

    private void startGame() {
        JFrame gameFrame = new JFrame("Pet Care");
        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel infoPanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel(currentPet.getName() + " the " + currentPet.getType(), JLabel.CENTER);
        nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        infoPanel.add(nameLabel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Pet Stats"));

        JProgressBar healthBar = createProgressBar("Health", currentPet.getHealth());
        JProgressBar happinessBar = createProgressBar("Happiness", currentPet.getHappiness());
        JProgressBar sleepBar = createProgressBar("Sleep", currentPet.getSleep());
        JProgressBar hungerBar = createProgressBar("Hunger", currentPet.getHunger());

        statsPanel.add(healthBar);
        statsPanel.add(happinessBar);
        statsPanel.add(sleepBar);
        statsPanel.add(hungerBar);
        infoPanel.add(statsPanel, BorderLayout.CENTER);

        JLabel petLabel = new JLabel(currentPet.getPetImage());
        petLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(petLabel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton feedButton = new JButton("Feed");
        feedButton.addActionListener(e -> {
            currentPet.feed();
            updateStats(healthBar, happinessBar, sleepBar, hungerBar);
            saveGame();
        });

        JButton playButton = new JButton("Play");
        playButton.addActionListener(e -> {
            currentPet.play();
            updateStats(healthBar, happinessBar, sleepBar, hungerBar);
            saveGame();
        });

        JButton sleepButton = new JButton("Sleep");
        sleepButton.addActionListener(e -> {
            currentPet.sleep();
            updateStats(healthBar, happinessBar, sleepBar, hungerBar);
            saveGame();
        });

        JButton saveButton = new JButton("Save & Exit");
        saveButton.addActionListener(e -> {
            saveGame();
            gameFrame.dispose();
            UserInterface.showMainMenu();
        });

        buttonPanel.add(feedButton);
        buttonPanel.add(playButton);
        buttonPanel.add(sleepButton);
        buttonPanel.add(saveButton);

        gameFrame.add(mainPanel, BorderLayout.CENTER);
        gameFrame.add(buttonPanel, BorderLayout.SOUTH);

        Timer timer = new Timer(30000, e -> {
            currentPet.decreaseHunger(5);
            currentPet.decreaseSleep(3);
            currentPet.decreaseHappiness(2);

            if (currentPet.getHunger() < 30 || currentPet.getSleep() < 30) {
                currentPet.decreaseHealth(1);
            }

            updateStats(healthBar, happinessBar, sleepBar, hungerBar);
            saveGame();

            if (currentPet.isDead()) {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(gameFrame,
                        "Oh no! " + currentPet.getName() + " has died due to neglect!",
                        "Game Over",
                        JOptionPane.ERROR_MESSAGE);

                File saveFile = new File(SAVE_FILE);
                if (saveFile.exists()) saveFile.delete();

                gameFrame.dispose();
                UserInterface.showMainMenu();
            }
        });
        timer.start();

        gameFrame.setVisible(true);
    }

    private JProgressBar createProgressBar(String name, int value) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(value);
        bar.setStringPainted(true);
        bar.setString(name + ": " + value);
        updateBarColor(bar);
        return bar;
    }

    private void updateStats(JProgressBar health, JProgressBar happiness,
                             JProgressBar sleep, JProgressBar hunger) {
        health.setValue(currentPet.getHealth());
        health.setString("Health: " + currentPet.getHealth());

        happiness.setValue(currentPet.getHappiness());
        happiness.setString("Happiness: " + currentPet.getHappiness());

        sleep.setValue(currentPet.getSleep());
        sleep.setString("Sleep: " + currentPet.getSleep());

        hunger.setValue(currentPet.getHunger());
        hunger.setString("Hunger: " + currentPet.getHunger());

        updateBarColor(health);
        updateBarColor(happiness);
        updateBarColor(sleep);
        updateBarColor(hunger);
    }

    private void updateBarColor(JProgressBar bar) {
        int value = bar.getValue();
        if (value > 70) {
            bar.setForeground(new Color(0, 150, 0));
        } else if (value > 30) {
            bar.setForeground(new Color(200, 150, 0));
        } else {
            bar.setForeground(new Color(200, 0, 0));
        }
    }

    private void saveGame() {
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
}
