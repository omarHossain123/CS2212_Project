/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


 import java.awt.Image;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.KeyEvent;
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
  
 
 /**
  *
  * @author Jacob
  */
 public class mainGame extends javax.swing.JFrame {
    public Game game;
     
     String basePath = "assets\\images\\petSprites";
 
     private int gameScore = 0; // Add this as a class variable

     private Pet currentPet;

 
 
     /**
      * Creates new form mainMenu
      */
      public mainGame(Pet selectedPet) {

        this.currentPet = selectedPet;
        game = new Game(selectedPet.getType());
        initComponents();

        addSaveButton();

         
         // Set the frame size explicitly to match your panel
         this.setSize(1075, 680); // Width, Height (add extra for window decorations)
         
         // Sets the game application location in the middle of the screen
         setLocationRelativeTo(null);
 
         // Load the initial pet image
         loadPetImage();
         
 
          // Initialize the score label with starting value
         scoreLabel.setText(String.format("%017d", gameScore));
          
         /**
         * Creates and starts a Swing Timer that periodically updates the game score.
         * The timer triggers every 0.5 seconds (500 milliseconds) and performs the following actions:
         * <ul>
         *   <li>Increments the game score by 1 points</li>
         *   <li>Updates the score display label with the new value</li>
         *   <li>Formats the score to display as 17 digits with leading zeros</li>
         * </ul>
         * 
         * <p>The timer is set to repeat indefinitely until explicitly stopped. The score update
         * occurs on the Event Dispatch Thread, making it safe for Swing UI operations.</p>
         * 
         * <p>Example usage:
         * <pre>
         * // Timer updates score every 0.5 seconds
         * Timer scoreTimer = createScoreTimer();
         * scoreTimer.start();
         * </pre>
         * </p>
         * 
         * @return A configured Timer instance that handles score updates. The timer must be
         *         started explicitly by calling {@link javax.swing.Timer#start()}.
         * 
         * @see javax.swing.Timer
         * @see java.awt.event.ActionListener
         * @see #gameScore  The game's score variable being modified
         * @see #scoreLabel  The JLabel displaying the score
         */
         Timer timer = new Timer(500, new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 // Update the test label
                 // label.setText("Updated at " + System.currentTimeMillis());
 
                 // Update the game score (example: increment by 1)
                 game.checkState();
                 game.increaseScore(1);
                 gameScore = (int) game.getScore();
                 
                    game.gameDecrement();
                 
                 System.out.println("SysoutL " + game.getPet().getHappiness());
                 // Update the score label with leading zeros (17 digits)
                 scoreLabel.setText(String.format("%017d", gameScore));
             }
         });
     
         timer.setRepeats(true);
         timer.start();
         
         /**
          * Add if statement for depletion of progress bar
          * If certain pet image is on the petImage label 
          * Then changes the progression speeds to match the pets perks and debuffs
          */
         depleteProgressBar(sleepBar, 100, 1, 6000);
         depleteProgressBar(happinessBar, 100, 1, 6000);
         depleteProgressBar(hungerBar, 100, 1, 6000);
         
         setupKeyBindings();
         
         
 
     }
 
     private void loadPetImage() {
        // Get the image path from the current pet
        String imagePath = currentPet.getPetImage().getDescription();
        
        try {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            
            // Scale the image to fit the JLabel dimensions (346x320 as per your code)
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                346, 
                320, 
                Image.SCALE_SMOOTH);
                
            petImage.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("Error loading pet image: " + imagePath);
            petImage.setIcon(null); // Clear image if loading fails
            petImage.setText("Pet Image Missing");
        }
    }
     
     /**
     * Sets up key bindings for the main game window. Specifically binds the ESC key
     * to open the settings window when pressed. This uses Swing's key binding system
     * which is more reliable than KeyListeners as it works even when components have focus.
     * 
     * <p>The binding is set for WHEN_IN_FOCUSED_WINDOW condition, meaning it will trigger
     * when the ESC key is pressed anywhere within this window, regardless of which component
     * has focus.</p>
     * 
     * <p>The action performed by the ESC key is to call the existing {@link #settingsActionPerformed}
     * method, which displays the settings dialog.</p>
     * 
     * @see javax.swing.KeyStroke
     * @see javax.swing.InputMap
     * @see javax.swing.ActionMap
     * @see #settingsActionPerformed(java.awt.event.ActionEvent)
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
                 settingsActionPerformed(null); // Call your existing settings action
             }
         };
         
         // Define the action for p key
         Action pauseAction = new AbstractAction(){
             @Override
             public void actionPerformed(ActionEvent e){
                 //togglePause();
             }
         };
         
         Action feedAction = new AbstractAction(){
             @Override
             public void actionPerformed(ActionEvent e){
                 //feed();
             }
         };
         
         Action giftAction = new AbstractAction(){
             @Override
             public void actionPerformed(ActionEvent e){
                 //giveGift();
             }
         };
         
         Action commandTab = new AbstractAction(){
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

        // Ctrl+S tp save
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
         inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "commandTab");
         actionMap.put("commandTab", commandTab);
 
     }
     
     public void refreshPetImage() {
         loadPetImage();
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
         commands = new javax.swing.JButton();
         store = new javax.swing.JButton();
         petChange = new javax.swing.JButton();
         inventory = new javax.swing.JButton();
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
 
         commands.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\mainGameButtons\\stroller.png")); // NOI18N
         commands.setBorderPainted(false);
         commands.setContentAreaFilled(false);
         commands.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 commandsActionPerformed(evt);
             }
         });
         jPanel1.add(commands);
         commands.setBounds(6, 6, 60, 60);
 
         store.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\mainGameButtons\\shopping_basket.png")); // NOI18N
         store.setBorderPainted(false);
         store.setContentAreaFilled(false);
         store.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 storeActionPerformed(evt);
             }
         });
         jPanel1.add(store);
         store.setBounds(10, 70, 60, 60);
 
         petChange.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\mainGameButtons\\pawprint.png")); // NOI18N
         petChange.setBorderPainted(false);
         petChange.setContentAreaFilled(false);
         petChange.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 petChangeActionPerformed(evt);
             }
         });
         jPanel1.add(petChange);
         petChange.setBounds(10, 130, 60, 60);
 
         inventory.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\mainGameButtons\\backpack.png")); // NOI18N
         inventory.setBorderPainted(false);
         inventory.setContentAreaFilled(false);
         inventory.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 inventoryActionPerformed(evt);
             }
         });
         jPanel1.add(inventory);
         inventory.setBounds(10, 190, 60, 60);
 
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
         jLabel3.setIcon(new javax.swing.ImageIcon("assets\\images\\Backgrounds\\background.png")); // NOI18N
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

 
     private void settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsActionPerformed
         // TODO add your handling code here:
         settings exit = new settings(this); // Pass 'this' as parent frame
         exit.setVisible(true);
     }//GEN-LAST:event_settingsActionPerformed
 
     private void commandsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandsActionPerformed
         // TODO add your handling code here:
         commands actions = new commands(game);
     
         // Set location relative to main game window
         actions.setLocation(
         this.getX(),  // X position of main game window
         this.getY()   // Y position of main game window
         );
 
         // Make it visible
         actions.setVisible(true);
     }//GEN-LAST:event_commandsActionPerformed
 
     private void storeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storeActionPerformed
         // TODO add your handling code here:
     }//GEN-LAST:event_storeActionPerformed
 
     private void inventoryActionPerformed(java.awt.event.ActionEvent evt) {
        game.getInventory().setScore(game.getScore());
        float tempScore = (float) game.getInventory().getScore();
        InventoryGUI inventoryWindow = new InventoryGUI(game.petType(), game.getInventory());
        tempScore = game.getScore() - tempScore;
        game.increaseScore(-tempScore);
        // Set the default close operation to DISPOSE_ON_CLOSE instead of EXIT_ON_CLOSE
        inventoryWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        // Center inventory window relative to the main game window
        inventoryWindow.setLocationRelativeTo(this);
        inventoryWindow.setVisible(true);
    }
    
     private void petChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_petChangeActionPerformed
         // TODO add your handling code here:
     }//GEN-LAST:event_petChangeActionPerformed

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
 
   
     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.JButton commands;
     private javax.swing.JProgressBar happinessBar;
     private javax.swing.JProgressBar healthBar;
     private javax.swing.JProgressBar hungerBar;
     private javax.swing.JButton inventory;
     private javax.swing.JLabel jLabel2;
     private javax.swing.JLabel jLabel3;
     private javax.swing.JPanel jPanel1;
     private javax.swing.JButton petChange;
     private javax.swing.JLabel petImage;
     private javax.swing.JLabel scoreLabel;
     private javax.swing.JButton settings;
     private javax.swing.JProgressBar sleepBar;
     private javax.swing.JButton store;
     
     // End of variables declaration//GEN-END:variables
 }
 