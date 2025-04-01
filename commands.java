/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


 import java.awt.event.ActionEvent;
 import java.awt.event.KeyEvent;
 import javax.swing.AbstractAction;
 import javax.swing.Action;
 import javax.swing.ActionMap;
 import javax.swing.ImageIcon;
 import javax.swing.InputMap;
 import javax.swing.JButton;
 import javax.swing.JComponent;
 import javax.swing.KeyStroke;
 
 /**
  *
  * @author Jacob
  */
 public class commands extends javax.swing.JFrame {
     
     private final String[] button12Images = {
         "assets\\images\\itemSprites\\orang.png",
         "assets\\images\\itemSprites\\popsicle.png",
         "assets\\images\\itemSprites\\cake.png"
     };
     private int currentImageIndexFood = 0;
     
     private final String[] button11Images = {
         "assets\\images\\itemSprites\\gift.png",
         "assets\\images\\itemSprites\\ball.png",
         "assets\\images\\itemSprites\\cards.png"
     };
     private int currentImageIndexGift = 0;
     public Game game ;
     
     /**
      * Creates new form commands
      */
     public commands(Game g) {
        game = g;
         initComponents();
         updateButtonImage(button12Images, feed, currentImageIndexFood); // Initialize with first image
         updateButtonImage(button11Images, giveGift, currentImageIndexGift); // Initialize with first image
         
         // Set the frame size explicitly to match your panel
         this.setSize(330, 680); // Width, Height (add extra for window decorations)
         
         setupKeyBindings();
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
         
         // Define the action for C key
         Action escAction = new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 escActionPerformed(null); // Call your existing settings action
             }
         };
         
         // Define the action for P key
         Action pauseAction = new AbstractAction(){
             @Override
             public void actionPerformed(ActionEvent e){
                 //togglePause();
             }
         };
         
         // Define the action for F key
         Action feedAction = new AbstractAction(){
             @Override
             public void actionPerformed(ActionEvent e){
                 feedActionPerformed(e); // Calls same method as clicking the feed button
             }
         };
         
         // Define the action for G key
         Action giftAction = new AbstractAction(){
             @Override
             public void actionPerformed(ActionEvent e){
                 giveGiftActionPerformed(e); // Calls same method as clicking gift button 
             }
         };
         
         // Bind the C key to the action
         inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "escAction");
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
     }
     /**
     * Updates the icon of a JButton with the specified image from the given array.
     * 
     * @param buttonImages Array of image paths to be used as button icons
     * @param button The JButton whose icon will be updated
     * @param index The index of the image in the buttonImages array to use
     * 
     * @throws ArrayIndexOutOfBoundsException If the index is out of bounds for the buttonImages array
     * @throws NullPointerException If buttonImages array or button is null
     * 
     * @implNote This method handles image loading errors gracefully by catching exceptions
     *           and printing error messages to System.err. The button's icon remains unchanged
     *           if an error occurs during image loading.
     * 
     * @see javax.swing.JButton#setIcon(Icon)
     * @see javax.swing.ImageIcon
     */
     private void updateButtonImage(String[] buttonImages, JButton button, int index) {
         try {
             ImageIcon icon = new ImageIcon(buttonImages[index]);
             button.setIcon(icon);
         } catch (Exception e) {
             System.err.println("Error loading image: " + e.getMessage());
         }
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
         esc = new javax.swing.JButton();
         takeToVet = new javax.swing.JButton();
         walkPet = new javax.swing.JButton();
         sleep = new javax.swing.JButton();
         play = new javax.swing.JButton();
         giveGift = new javax.swing.JButton();
         feed = new javax.swing.JButton();
         nextFood = new javax.swing.JButton();
         nextGift = new javax.swing.JButton();
         prevGift = new javax.swing.JButton();
         prevFood = new javax.swing.JButton();
         jLabel1 = new javax.swing.JLabel();
 
         setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
         setUndecorated(true);
 
         jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
         jPanel1.setLayout(null);
 
         esc.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\commandsList\\exit_button.png")); // NOI18N
         esc.setBorderPainted(false);
         esc.setContentAreaFilled(false);
         esc.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 escActionPerformed(evt);
             }
         });
         jPanel1.add(esc);
         esc.setBounds(278, 8, 40, 40);
 
         takeToVet.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\commandsList\\syringe.png")); // NOI18N
         takeToVet.setBorderPainted(false);
         takeToVet.setContentAreaFilled(false);
         takeToVet.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 takeToVetActionPerformed(evt);
             }
         });
         jPanel1.add(takeToVet);
         takeToVet.setBounds(114, 551, 100, 80);
 
         walkPet.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\commandsList\\walk_the_pet.png")); // NOI18N
         walkPet.setBorder(null);
         walkPet.setBorderPainted(false);
         walkPet.setContentAreaFilled(false);
         walkPet.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 walkPetActionPerformed(evt);
             }
         });
         jPanel1.add(walkPet);
         walkPet.setBounds(114, 453, 98, 80);
 
         sleep.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\commandsList\\baby.png")); // NOI18N
         sleep.setBorderPainted(false);
         sleep.setContentAreaFilled(false);
         sleep.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 sleepActionPerformed(evt);
             }
         });
         jPanel1.add(sleep);
         sleep.setBounds(114, 355, 98, 80);
 
         play.setIcon(new javax.swing.ImageIcon("assets\\images\\itemSprites\\ball.png")); // NOI18N
         play.setBorderPainted(false);
         play.setContentAreaFilled(false);
         play.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 playActionPerformed(evt);
             }
         });
         jPanel1.add(play);
         play.setBounds(114, 257, 98, 80);
 
         giveGift.setIcon(new javax.swing.ImageIcon("assets\\images\\itemSprites\\gift.png")); // NOI18N
         giveGift.setBorderPainted(false);
         giveGift.setContentAreaFilled(false);
         giveGift.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 giveGiftActionPerformed(evt);
             }
         });
         jPanel1.add(giveGift);
         giveGift.setBounds(114, 159, 98, 80);
 
         feed.setIcon(new javax.swing.ImageIcon("assets\\images\\itemSprites\\orang.png")); // NOI18N
         feed.setBorderPainted(false);
         feed.setContentAreaFilled(false);
         feed.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 feedActionPerformed(evt);
             }
         });
         jPanel1.add(feed);
         feed.setBounds(114, 61, 98, 80);
 
         nextFood.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\commandsList\\next.png")); // NOI18N
         nextFood.setBorderPainted(false);
         nextFood.setContentAreaFilled(false);
         nextFood.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 nextFoodActionPerformed(evt);
             }
         });
         jPanel1.add(nextFood);
         nextFood.setBounds(224, 71, 50, 50);
 
         nextGift.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\commandsList\\next.png")); // NOI18N
         nextGift.setBorderPainted(false);
         nextGift.setContentAreaFilled(false);
         nextGift.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 nextGiftActionPerformed(evt);
             }
         });
         jPanel1.add(nextGift);
         nextGift.setBounds(224, 172, 50, 50);
 
         prevGift.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\commandsList\\previous.png")); // NOI18N
         prevGift.setBorderPainted(false);
         prevGift.setContentAreaFilled(false);
         prevGift.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 prevGiftActionPerformed(evt);
             }
         });
         jPanel1.add(prevGift);
         prevGift.setBounds(52, 172, 50, 50);
 
         prevFood.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\commandsList\\previous.png")); // NOI18N
         prevFood.setBorderPainted(false);
         prevFood.setContentAreaFilled(false);
         prevFood.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 prevFoodActionPerformed(evt);
             }
         });
         jPanel1.add(prevFood);
         prevFood.setBounds(52, 73, 50, 50);
 
         jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
         jLabel1.setIcon(new javax.swing.ImageIcon("assets\\images\\uiElements\\commandsList\\blue_background.png")); // NOI18N
         jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
         jPanel1.add(jLabel1);
         jLabel1.setBounds(0, 0, 330, 680);
 
         javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
         getContentPane().setLayout(layout);
         layout.setHorizontalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
         );
         layout.setVerticalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
         );
 
         pack();
     }// </editor-fold>//GEN-END:initComponents
 
     private void escActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_escActionPerformed
         // TODO add your handling code here:
         this.dispose();
     }//GEN-LAST:event_escActionPerformed
 
     private void takeToVetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_takeToVetActionPerformed
         // TODO add your handling code here:
         //Cool Down Check
         game.checkState();
         if(game.takeToVet()){

         }
         else{

         }
     }//GEN-LAST:event_takeToVetActionPerformed
 
     private void walkPetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_walkPetActionPerformed
        game.checkState();
        if(game.walk()){

        }
        else{

        }
     }//GEN-LAST:event_walkPetActionPerformed
 
     private void sleepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sleepActionPerformed
         // TODO add your handling code here:
         System.out.println("Button Pressed");
         game.TesterMethod();
         game.checkState();
         System.out.println(game.checkState());
         if(game.gotToBed()){
            System.out.println("ZZZZZZZZZZZZZZZZZZ");
         }
         else{
 
         }
     }//GEN-LAST:event_sleepActionPerformed
 
     private void playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playActionPerformed
         // TODO add your handling code here:
         ///Cool DOwn
         game.checkState();
         if(game.play()){
            
         }
         else{
 
         }
     }//GEN-LAST:event_playActionPerformed
 
     private void giveGiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giveGiftActionPerformed
         // TODO add your handling code here:
         //giveGift();
     }//GEN-LAST:event_giveGiftActionPerformed
 
     private void feedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedActionPerformed
         // TODO add your handling code here:
         //feed();
     }//GEN-LAST:event_feedActionPerformed
 
     private void nextGiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextGiftActionPerformed
         // TODO add your handling code here:
         // Next Image
         currentImageIndexGift = (currentImageIndexGift + 1) % button11Images.length;
         updateButtonImage(button11Images, giveGift, currentImageIndexGift);
     }//GEN-LAST:event_nextGiftActionPerformed
 
     private void prevGiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevGiftActionPerformed
         // TODO add your handling code here:
         // Previous image
         currentImageIndexGift = (currentImageIndexGift - 1 + button11Images.length) % button11Images.length;
         updateButtonImage(button11Images, giveGift, currentImageIndexGift);
     }//GEN-LAST:event_prevGiftActionPerformed
 
     private void prevFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevFoodActionPerformed
         // TODO add your handling code here:
         // Previous image
         currentImageIndexFood = (currentImageIndexFood - 1 + button12Images.length) % button12Images.length;
         updateButtonImage(button12Images, feed, currentImageIndexFood);
     }//GEN-LAST:event_prevFoodActionPerformed
 
     private void nextFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextFoodActionPerformed
         // TODO add your handling code here:
         // Next Image
         currentImageIndexFood = (currentImageIndexFood + 1) % button12Images.length;
         updateButtonImage(button12Images, feed, currentImageIndexFood);
     }//GEN-LAST:event_nextFoodActionPerformed
 
     /**
      * @param args the command line arguments
      */
  
     // Variables declaration - do not modify//GEN-BEGIN:variables
     private javax.swing.JButton esc;
     private javax.swing.JButton feed;
     private javax.swing.JButton giveGift;
     private javax.swing.JLabel jLabel1;
     private javax.swing.JPanel jPanel1;
     private javax.swing.JButton nextFood;
     private javax.swing.JButton nextGift;
     private javax.swing.JButton play;
     private javax.swing.JButton prevFood;
     private javax.swing.JButton prevGift;
     private javax.swing.JButton sleep;
     private javax.swing.JButton takeToVet;
     private javax.swing.JButton walkPet;
     // End of variables declaration//GEN-END:variables
 }
 