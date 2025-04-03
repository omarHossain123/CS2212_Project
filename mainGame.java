/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


 import java.awt.Image;
 import java.awt.Font;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.KeyEvent;
 import java.awt.event.MouseAdapter;
 import java.awt.event.MouseEvent;
 import java.awt.event.ComponentAdapter;
 import java.awt.event.ComponentEvent;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import javax.swing.AbstractAction;
 import javax.swing.Action;
 import javax.swing.ActionMap;
 import javax.swing.ImageIcon;
 import javax.swing.InputMap;
 import javax.swing.JButton;
 import javax.swing.JComponent;
 import javax.swing.JProgressBar;
 import javax.swing.KeyStroke;
 import javax.swing.SwingUtilities;
 import javax.swing.Timer;
 import java.awt.Dimension;
 import java.awt.GraphicsDevice;
 import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
  
 
 /**
  *
  * @author Jacob
  */
  public class mainGame extends javax.swing.JFrame {
    public Game game;
    private float openedInventory;
    String basePath = "assets\\images\\petSprites";

    private int gameScore = 0; // Add this as a class variable
    private Pet currentPet;
    
    // Add a Timer variable to access it globally
    private Timer timer;
    private boolean isPaused = false;

    /**
     * Creates new form mainMenu
     * @param selectedPet The pet to use for this game
     * @param saveData Optional save data to restore (can be null for new game)
     */
    public mainGame(Pet selectedPet, GameSaveData saveData) {
        System.out.println("DEBUG - Creating mainGame with pet: " + selectedPet.getType());
        this.currentPet = selectedPet;
    
        // If we have save data, log what we're about to restore
        if (saveData != null) {
            System.out.println("DEBUG - Save data provided: Pet: " + saveData.getPet().getType() + 
                              ", Health: " + saveData.getPet().getHealth() + 
                              ", Score: " + saveData.getScore());
        } else {
            System.out.println("DEBUG - No save data provided, creating new game");
        }
    
        // IMPORTANT: Create game with the correct pet type
        game = new Game(selectedPet.getType());
        System.out.println("DEBUG - Game created with pet type: " + selectedPet.getType());
        
        // Set the UI reference
        game.setGameUI(this);
    
        // IMPORTANT: Restore from save data BEFORE initializing UI and other components
        if (saveData != null) {
            System.out.println("DEBUG - Attempting to restore from save...");
            game.restoreFromSave(saveData);
            
            // Update the current pet reference to use the one from the saved game
            this.currentPet = game.getPet();
            
            System.out.println("DEBUG - After restore: Pet health: " + game.getPet().getHealth() + 
                              ", Score: " + game.getScore());
        }
    
        // After restoring save data, then set up the UI and other components
        setupRandomBlinking();
        setupRandomIdleAnimations();
        
        // Initialize the UI components
        initComponents();
        addSaveButton();
    
        // Set window to full screen on startup
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // indow listener to save game on close and exit properly
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Save the game first
                UserInterface.saveGame();
                
                // Then exit properly
                UserInterface.safeExit();
            }
        });
        
        // Add component listener to handle resizing
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Ensure background and components adjust to the new size
                adaptUIToWindowSize();
            }
        });
        
        // Load the initial pet image
        loadPetImage();
        
        // Initialize the score label with current value from game
        gameScore = (int) game.getScore();
        scoreLabel.setText(String.format("%017d", gameScore));
        
    /**
     * Timer action listener that handles pet state and image updates
     */
    // Create and start the score timer
    timer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Store initial state and emotion for comparison
            String oldState = game.getPet().getState();
            String oldEmotion = game.getPet().getEmotion();

            // Update pet state
            game.changeState();
            String newState = game.checkState();
            
            // Check if state or emotion changed
            String newEmotion = game.getPet().getEmotion();
            boolean stateChanged = !oldState.equals(newState);
            boolean emotionChanged = !oldEmotion.equals(newEmotion);
            
            if (emotionChanged) {
                System.out.println("REFRESH: Emotion changed from " + oldEmotion + " to " + newEmotion);
                refreshPetImage(); // Load new image because emotion changed
            }

            // Check for death state
            if (game.getPet().getState().equals("dead")) {
                // Stop the timer and show game over message
                timer.stop();
                refreshPetImage(); // Update image for death state
                // You could show a game over dialog here
                return;
            } else if (game.getPet().getState().equals("default")) {
                // Only increase score if in default state
                game.increaseScore(1);
                gameScore = (int) game.getScore();
            }
            
            // Update pet stats
            game.gameDecrement();
            
            // Update progress bars to reflect current pet state
            updateProgressBars();
            
            // If state changed, ensure the image is updated
            if (stateChanged) {
                refreshPetImage();
            }
            
            // Update the score label with leading zeros (17 digits)
            scoreLabel.setText(String.format("%017d", (int)game.getScore()));
        }
    });

        timer.setRepeats(true);
        timer.start();
        
        /**
         * Initialize progress bars to reflect the pet's current state
         */
        updateProgressBars();
        
        // Setup depletion rates based on pet type
        setupDepleteProgressBars();
        
        setupKeyBindings();
        
        // Adjust UI to fit current window size on startup
        adaptUIToWindowSize();
    }
    
    /**
     * Adjusts the UI elements to fit the current window size
     */
    private void adaptUIToWindowSize() {
        // Get the current window dimensions
        int width = getWidth();
        int height = getHeight();
        
        // Resize the background image
        resizeBackground();
        
        // Reposition elements
        repositionElements(width, height);
    }

    /**
     * Resizes the background image to fill the window
     */
    private void resizeBackground() {
        if (jLabel3 != null) {
            // Get the original background image
            ImageIcon originalIcon = new ImageIcon("assets\\images\\Backgrounds\\Main_Living.png");
            Image originalImage = originalIcon.getImage();
            
            // Get the current size of the panel
            int width = jPanel1.getWidth();
            int height = jPanel1.getHeight();
            
            if (width > 0 && height > 0) {
                // Scale the image to fill the panel
                Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                
                // Update the label with the scaled image
                jLabel3.setIcon(new ImageIcon(scaledImage));
                jLabel3.setBounds(0, 0, width, height);
            }
        }
    }

    /**
     * Repositions UI elements based on the window dimensions
     */
    private void repositionElements(int width, int height) {
        // Center the pet image
        if (petImage != null) {
            int petX = (width - petImage.getWidth()) / 2;
            int petY = (height - petImage.getHeight()) / 2 - 50; // Slightly above center
            petImage.setLocation(petX, petY);
        }
        
        // Position progress bars at the bottom but higher up
        int progressBarHeight = 40;
        int progressBarPadding = 6;
        int extraBottomSpace = 50; // Extra space from the bottom

        // Position the health bar higher
        if (healthBar != null) {
            healthBar.setBounds(
                progressBarPadding, 
                height - progressBarHeight - progressBarPadding - extraBottomSpace, 
                width - (2 * progressBarPadding), 
                progressBarHeight
            );
        }
        
        // Calculate widths for the three upper bars (equal width)
        int smallBarWidth = (width - (4 * progressBarPadding)) / 3;
        
        // Position the sleep bar on the left (higher up)
        if (sleepBar != null) {
            sleepBar.setBounds(
                progressBarPadding, 
                height - (2 * progressBarHeight) - (2 * progressBarPadding) - extraBottomSpace, 
                smallBarWidth, 
                progressBarHeight
            );
        }
        
        // Position the happiness bar in the middle (higher up)
        if (happinessBar != null) {
            happinessBar.setBounds(
                progressBarPadding * 2 + smallBarWidth, 
                height - (2 * progressBarHeight) - (2 * progressBarPadding) - extraBottomSpace, 
                smallBarWidth, 
                progressBarHeight
            );
        }
        
        // Position the hunger bar on the right (higher up)
        if (hungerBar != null) {
            hungerBar.setBounds(
                progressBarPadding * 3 + (2 * smallBarWidth), 
                height - (2 * progressBarHeight) - (2 * progressBarPadding) - extraBottomSpace, 
                smallBarWidth, 
                progressBarHeight
            );
        }
        
        // Position the score label and text more to the left
        if (jLabel2 != null && scoreLabel != null) {
            int scoreLabelWidth = 210;
            int scoreLabelHeight = 32;
            int scoreLabelX = width - scoreLabelWidth - 120; 
            
            jLabel2.setBounds(scoreLabelX - 60, 10, 60, 50);
            scoreLabel.setBounds(scoreLabelX, 20, scoreLabelWidth, scoreLabelHeight);
        }
        
        // Position the settings button more to the left
        if (settings != null) {
            settings.setBounds(width - 100, 6, 60, 60); // More to the left
        }
        
        // Position the buttons
        if (commands != null) commands.setBounds(10, 10, 100, 40);
        if (inventory != null) inventory.setBounds(10, 60, 100, 40);
    }
    
    /**
     * Updates all progress bars to reflect the current state of the pet.
     * This method should be called whenever the pet's stats change.
     */
    private void updateProgressBars() {
        Pet pet = game.getPet();
        
        // Calculate percentages for each stat
        int healthPercent = (int)((pet.getHealth() / pet.getMaxHealth()) * 100);
        int happinessPercent = (int)((pet.getHappiness() / pet.getMaxHappiness()) * 100);
        int hungerPercent = (int)((pet.getHunger() / pet.getMaxHunger()) * 100);
        int sleepPercent = (int)((pet.getSleep() / pet.getMaxSleep()) * 100);
        
        // Update progress bars
        healthBar.setValue(healthPercent);
        happinessBar.setValue(happinessPercent);
        hungerBar.setValue(hungerPercent);
        sleepBar.setValue(sleepPercent);
        
        // Refresh pet image based on state
        refreshPetImage();
    }
    
    /**
     * Sets up the depletion rates for progress bars based on pet type
     */
    private void setupDepleteProgressBars() {
        // Get depletion rates based on pet type
        double sleepRate = 1.0;
        double happinessRate = 1.0;
        double hungerRate = 1.0;
        
        // Adjust rates based on pet type if needed
        String petType = currentPet.getType();
        
        // Setup the progress bar depletion threads
        depleteProgressBar(sleepBar, 100, 1, 6000);
        depleteProgressBar(happinessBar, 100, 1, 6000);
        depleteProgressBar(hungerBar, 100, 1, 6000);
    }

    /**
     * Loads the pet image based on current state and emotion
     */
    private void loadPetImage() {
        try {
            // Get the current pet from the game
            currentPet = game.getPet();
            
            // Get pet's current state and emotion for debugging
            String currentState = currentPet.getState();
            String currentEmotion = currentPet.getEmotion();
            
            // Get the emotion image path
            String imagePath = currentPet.getEmotionImagePath();
            
            System.out.println("Loading image for state: " + currentState + 
                             ", emotion: " + currentEmotion + 
                             ", path: " + imagePath);
            
            ImageIcon originalIcon = new ImageIcon(imagePath);
            
            // Scale the image
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                346, 
                320, 
                Image.SCALE_SMOOTH);
                
            petImage.setIcon(new ImageIcon(scaledImage));
            petImage.repaint();
        } catch (Exception e) {
            System.err.println("Error loading pet image: " + e.getMessage());
            e.printStackTrace();
        }
    }
     
    /**
     * Sets up key bindings for the main game window.
     */
    private void setupKeyBindings() {
        // Get the input map and action map for the content pane
        JComponent contentPane = (JComponent) getContentPane();
        InputMap inputMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = contentPane.getActionMap();
        
        // Define the action for ESC key
        Action escAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsActionPerformed(null); 
            }
        };
        
        // Define the action for P key
        Action pauseAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                togglePause();
            }
        };
        
        Action feedAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                feed();
            }
        };
        
        Action giftAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                giveGift();
            }
        };
        
        Action commandAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                commandsActionPerformed(null);
            }
        };
        
        // save game 
        Action saveAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInterface.saveCurrentGame();
            }
        };

        // Ctrl+S to save
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK), "saveAction");
        actionMap.put("saveAction", saveAction);

        // Bind the ESC key to the action
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escAction");
        actionMap.put("escAction", escAction);
        
        // Bind the P key to the action
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pauseAction");
        actionMap.put("pauseAction", pauseAction);
        
        // Bind the F key to the action
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "feedAction");
        actionMap.put("feedAction", feedAction);
        
        // Bind the G key to the action
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0), "giftAction");
        actionMap.put("giftAction", giftAction);
        
        // Bind the C key to the action
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "commandAction");
        actionMap.put("commandAction", commandAction);
    }
    
    /**
     * Toggles the game pause state
     */
    private void togglePause() {
        if (isPaused) {
            timer.start();
            isPaused = false;
        } else {
            timer.stop();
            isPaused = true;
        }
    }
    
    /**
     * Feeds the pet to increase hunger and happiness
     */
    private void feed() {
        // Feed the pet
        boolean fed = game.feed(0);
        
        if (fed) {
            // Update progress bars to reflect changes
            updateProgressBars();
            // Optional: Add animation or sound effect
        }
    }
    
    /**
     * Gives a gift to the pet to increase happiness
     */
    private void giveGift() {
        // Give gift to pet
        boolean gifted = game.giveGift(0);
        
        if (gifted) {
            // Update progress bars to reflect changes
            updateProgressBars();
            // Optional: Add animation or sound effect
        }
    }
    
    /**
     * Puts the pet to sleep to restore sleep stat
     */
    private void sleep() {
        // Put pet to sleep
        boolean sleeping = game.goToBed();
        
        if (sleeping) {
            // Update progress bars to reflect changes
            updateProgressBars();
            // Optional: Add animation or sound effect
        }
    }
    
    /**
     * Takes the pet to vet to restore health
     */
    private void takeToVet() {
        // Take pet to vet
        boolean treated = game.takeToVet();
        
        if (treated) {
            // Update progress bars to reflect changes
            updateProgressBars();
            // Optional: Add animation or sound effect
        }
    }
    
    /**
     * Forces an immediate refresh of the pet image
     */
    public void refreshPetImage() {
        loadPetImage();
        petImage.repaint(); // Force immediate repaint
    }
    
    
    /**
     * Depletes specified JProgressBar from a given start value down to 0.
     * Depletion occurs in decrements at a certain delay interval.
     * 
     * @param progressBar   The JProgressBar to update.
     * @param startValue    The initial starting value of the progress bar.
     * @param decrement     Value to decrement the progress bar on each step.
     * @param delay         The delay in milliseconds between each decrement.
     */
    private void depleteProgressBar(JProgressBar progressBar, int startValue, int decrement, int delay) {
        Thread t = new Thread(() -> {
            for (int i = startValue; i >= 0; i -= decrement) {
                try {
                    // Update pet image when progress changes
                    if (i % 10 == 0) { // Update every 10% change for performance
                        SwingUtilities.invokeLater(() -> loadPetImage());
                    }
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    Logger.getLogger(mainGame.class.getName()).log(Level.SEVERE, null, ex);
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
            SwingUtilities.invokeLater(() -> loadPetImage());
        });
        t.start();
    }

  
     /**
      * This method is called from within the constructor to initialize the form.
      * WARNING: Do NOT modify this code. The content of this method is always
      * regenerated by the Form Editor.
      */
     @SuppressWarnings("unchecked")
     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
     private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        
        // Create the Commands button with styling
        commands = new javax.swing.JButton("Commands");
        commands.setFont(new java.awt.Font("Comic Sans MS", Font.BOLD, 12));
        commands.setBackground(new java.awt.Color(217, 188, 133)); // Warm beige color
        commands.setForeground(new java.awt.Color(90, 62, 44)); // Brown text color
        commands.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(new java.awt.Color(164, 116, 73), 2, true), // Brown border
            javax.swing.BorderFactory.createEmptyBorder(6, 10, 6, 10))); // Padding
        commands.setFocusPainted(false);
        commands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandsActionPerformed(evt);
            }
        });
        // Add hover effect
        commands.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                commands.setBackground(new java.awt.Color(227, 205, 159)); // Lighter color on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                commands.setBackground(new java.awt.Color(217, 188, 133)); // Back to original color
            }
        });
        
        // Create the Shop button with styling
        inventory = new javax.swing.JButton("Shop");
        inventory.setFont(new java.awt.Font("Comic Sans MS", Font.BOLD, 12));
        inventory.setBackground(new java.awt.Color(217, 188, 133)); // Warm beige color
        inventory.setForeground(new java.awt.Color(90, 62, 44)); // Brown text color
        inventory.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(new java.awt.Color(164, 116, 73), 2, true), // Brown border
            javax.swing.BorderFactory.createEmptyBorder(6, 10, 6, 10))); // Padding
        inventory.setFocusPainted(false);
        inventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryActionPerformed(evt);
            }
        });
        // Add hover effect
        inventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                inventory.setBackground(new java.awt.Color(227, 205, 159)); // Lighter color on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                inventory.setBackground(new java.awt.Color(217, 188, 133)); // Back to original color
            }
        });
        
        settings = new javax.swing.JButton();
        healthBar = new javax.swing.JProgressBar();
        hungerBar = new javax.swing.JProgressBar();
        happinessBar = new javax.swing.JProgressBar();
        sleepBar = new javax.swing.JProgressBar();
        petImage = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
 
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
 
        jPanel1.setLayout(null);
 
        jPanel1.add(commands);
        commands.setBounds(10, 10, 100, 40);
 
        jPanel1.add(inventory);
        inventory.setBounds(10, 60, 100, 40);
 
        settings.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\mainGameButtons\\settings.png")); // NOI18N
        settings.setBorderPainted(false);
        settings.setContentAreaFilled(false);
        settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsActionPerformed(evt);
            }
        });
        jPanel1.add(settings);
        settings.setBounds(994, 6, 60, 60);
 
        healthBar.setBackground(new java.awt.Color(153, 153, 153));
        healthBar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        healthBar.setForeground(new java.awt.Color(0, 204, 0));
        healthBar.setValue(100);
        healthBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        healthBar.setString("Health");
        healthBar.setStringPainted(true);
        jPanel1.add(healthBar);
        healthBar.setBounds(6, 591, 1048, 40);
 
        hungerBar.setBackground(new java.awt.Color(153, 153, 153));
        hungerBar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        hungerBar.setForeground(new java.awt.Color(102, 51, 0));
        hungerBar.setValue(100);
        hungerBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hungerBar.setString("Hunger");
        hungerBar.setStringPainted(true);
        jPanel1.add(hungerBar);
        hungerBar.setBounds(706, 545, 348, 40);
 
        happinessBar.setBackground(new java.awt.Color(153, 153, 153));
        happinessBar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        happinessBar.setForeground(new java.awt.Color(255, 0, 0));
        happinessBar.setValue(100);
        happinessBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        happinessBar.setString("Happiness");
        happinessBar.setStringPainted(true);
        jPanel1.add(happinessBar);
        happinessBar.setBounds(354, 545, 346, 40);
 
        sleepBar.setBackground(new java.awt.Color(153, 153, 153));
        sleepBar.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        sleepBar.setForeground(new java.awt.Color(102, 153, 255));
        sleepBar.setValue(100);
        sleepBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sleepBar.setString("Sleep");
        sleepBar.setStringPainted(true);
        jPanel1.add(sleepBar);
        sleepBar.setBounds(6, 545, 342, 40);
 
        petImage.setText("jLabel1");
        jPanel1.add(petImage);
        petImage.setBounds(350, 210, 346, 320);
 
        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel2.setText("Score:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(710, 10, 60, 50);
 
        scoreLabel.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        scoreLabel.setText("00000000000000000");
        scoreLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(scoreLabel);
        scoreLabel.setBounds(700, 20, 210, 32);
 
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon("assets\\images\\Backgrounds\\Main_Living.png")); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 0, 1060, 640);
 
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
        );
 
        pack();
    }// </editor-fold>//GEN-END:initComponents

    // save game 
    private void saveGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
        UserInterface.saveCurrentGame();
    }

    // to change background temporarily
    public void changeBackground(String imagePath) {
        if (jLabel3 != null) {
            // Load the new background image
            ImageIcon newBackground = new ImageIcon(imagePath);
            Image originalImage = newBackground.getImage();
            
            // Get the current size of the panel
            int width = jPanel1.getWidth();
            int height = jPanel1.getHeight();
            
            if (width > 0 && height > 0) {
                // Scale the image to fill the panel
                Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                
                // Update the label with the scaled image
                jLabel3.setIcon(new ImageIcon(scaledImage));
                jLabel3.setBounds(0, 0, width, height);
            }
        }
    }

    // Also add a method to restore the original background
    public void restoreDefaultBackground() {
        changeBackground("assets\\images\\Backgrounds\\Main_Living.png");
    }

 
     private void settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsActionPerformed
         // TODO add your handling code here:
         settings exit = new settings(this); // Pass 'this' as parent frame
         exit.setVisible(true);
     }//GEN-LAST:event_settingsActionPerformed
 
     private void commandsActionPerformed(java.awt.event.ActionEvent evt) {
        // Create commands window and pass the game instance
        commands actions = new commands(game);
    
        // Set location relative to main game window
        actions.setLocation(
        this.getX(),  // X position of main game window
        this.getY()   // Y position of main game window
        );

        // Make it visible
        actions.setVisible(true);
    }
 
    private void inventoryActionPerformed(java.awt.event.ActionEvent evt) {
        game.getInventory().setScore(game.getScore());

        InventoryGUI inventoryWindow = new InventoryGUI(game.petType(), game.getInventory());
        openedInventory = (float) game.getInventory().getScore();

        // Set the default close operation to DISPOSE_ON_CLOSE instead of EXIT_ON_CLOSE
        inventoryWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // Add a WindowListener to detect when the InventoryGUI is closed
        inventoryWindow.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                System.out.println("InventoryGUI has been closed!");

                // Perform any post-close actions here
                float tempScore = (float) game.getInventory().getScore();
                tempScore = (float) ( openedInventory- tempScore);
                System.out.println(tempScore + "------------------------------------------------------------------------");
                game.increaseScore(-tempScore);
            }
        });

        // Center inventory window relative to the main game window
        inventoryWindow.setLocationRelativeTo(this);
        inventoryWindow.setVisible(true);
    }

    private void addSaveButton() {
        JButton saveButton = new JButton();
        saveButton.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\mainGameButtons\\save.png")); // Use a save icon
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setToolTipText("Save Game (Ctrl+S)");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGameButtonActionPerformed(evt);
            }
        });

        jPanel1.add(saveButton);
        saveButton.setBounds(994, 70, 60, 60); // Position it below the settings button
    }

    private void setupRandomBlinking() {
        // Create a timer to occasionally make the pet blink
        javax.swing.Timer blinkTimer = new javax.swing.Timer(3000, new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                // Only blink if in default state and not already showing an emotion
                if (game.getPet().getState().equals("default") && 
                    game.getPet().getEmotion().equals("neutral")) {
                    
                    
                    if (Math.random() < 0.9) {
                        System.out.println("Random blink triggered");
                        game.getPet().setEmotion("blink", 1);
                        refreshPetImage(); // Update the image right away
                    }
                }
            }
        });
        
        blinkTimer.setRepeats(true);
        blinkTimer.start();
    }

    private void setupRandomIdleAnimations() {
        // Create a timer for occasional random animations
        javax.swing.Timer idleTimer = new javax.swing.Timer(15000, new ActionListener() { // Check every 15 seconds
            @Override
            public void actionPerformed(ActionEvent e) {
                // Only show idle animations if in default state and not already showing an emotion
                if (game.getPet().getState().equals("default") && 
                    game.getPet().getEmotion().equals("neutral")) {
                    
                    // 20% chance to show a random idle animation
                    if (Math.random() < 0.2) {
                        // Choose a random emotion from happy, or blush
                        String[] idleEmotions = {"happy", "blush"};
                        int randomIndex = (int)(Math.random() * idleEmotions.length);
                        String randomEmotion = idleEmotions[randomIndex];
                        
                        System.out.println("Random idle animation: " + randomEmotion);
                        game.getPet().setEmotion(randomEmotion, 2000); // Show for 2 seconds
                        refreshPetImage(); // Update the image right away
                    }
                }
            }
        });
        
        idleTimer.setRepeats(true);
        idleTimer.start();
    }
 
   
     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.JButton commands;
     private javax.swing.JProgressBar happinessBar;
     private javax.swing.JProgressBar healthBar;
     private javax.swing.JProgressBar hungerBar;
     private javax.swing.JButton inventory;
     private javax.swing.JLabel jLabel2;
     private javax.swing.JLabel jLabel3;
     private javax.swing.JPanel jPanel1;
     private javax.swing.JLabel petImage;
     private javax.swing.JLabel scoreLabel;
     private javax.swing.JButton settings;
     private javax.swing.JProgressBar sleepBar;
     // End of variables declaration//GEN-END:variables
 }