/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.FlowLayout;
 import java.awt.Font;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.RenderingHints;
 import java.awt.event.ActionEvent;
 import java.awt.event.KeyEvent;
 import java.awt.event.MouseAdapter;
 import java.awt.event.MouseEvent;
 import javax.swing.AbstractAction;
 import javax.swing.Action;
 import javax.swing.ActionMap;
 import javax.swing.BorderFactory;
 import javax.swing.Box;
 import javax.swing.BoxLayout;
 import javax.swing.InputMap;
 import javax.swing.JButton;
 import javax.swing.JComponent;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JScrollPane;
 import javax.swing.KeyStroke;
 import javax.swing.ScrollPaneConstants;
 import javax.swing.border.CompoundBorder;
 import javax.swing.border.EmptyBorder;
 import javax.swing.border.LineBorder;
 import javax.swing.border.TitledBorder;
 
 /**
  *
  * @author Jacob
  */
 public class commands extends javax.swing.JFrame {
     // Define warm, cozy color scheme to match main game
     private final Color PANEL_BACKGROUND = new Color(230, 238, 213); // Soft green background
     private final Color BUTTON_COLOR = new Color(217, 188, 133);     // Warm beige
     private final Color BUTTON_HOVER = new Color(227, 205, 159);     // Lighter beige for hover
     private final Color TEXT_COLOR = new Color(90, 62, 44);          // Brown text
     private final Color BORDER_COLOR = new Color(164, 116, 73);      // Brown border
     private final Color SECTION_BACKGROUND = new Color(240, 245, 230); // Slightly lighter background for sections
     private final Color CIRCLE_BUTTON_COLOR = new Color(217, 188, 133);     // Warm beige
     private final Color CIRCLE_BUTTON_TEXT = new Color(90, 62, 44);  // Brown text
     private final Color COUNT_BACKGROUND = new Color(255, 255, 255, 200); // Semi-transparent white
     
     public int giftList = 0;
     public int foodList = 0;
     private final String[] button12Images = {
         "assets\\images\\itemSprites\\orang.png",
         "assets\\images\\itemSprites\\popsicle.png",
         "assets\\images\\itemSprites\\cake.png"
     };
     private final String[] foodNames = {
         "Orange", "Popsicle", "Cake"
     };
     private int currentImageIndexFood = 0;
     
     private final String[] button11Images = {
         "assets\\images\\itemSprites\\gift.png",
         "assets\\images\\itemSprites\\ball.png",
         "assets\\images\\itemSprites\\cards.png"
     };
     private final String[] giftNames = {
         "Gift", "Ball", "Cards"
     };
     private int currentImageIndexGift = 0;
     public Game game;
     
     // Keep track of the current food and gift button to update the labels
     private JLabel currentFoodLabel;
     private JLabel currentGiftLabel;
     private JLabel foodCountLabel;
     private JLabel giftCountLabel;
     
     /**
      * Creates new form commands
      */
     public commands(Game g) {
         game = g;
         
         // Set frame properties
         setUndecorated(true);
         setSize(400, 600);
         setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
         
         // Create custom UI
         createUI();
         
         // Setup key bindings
         setupKeyBindings();
         
         // Center on screen
         setLocationRelativeTo(null);
     }
     
     /**
      * Creates the custom UI for the commands window
      */
     private void createUI() {
         // Create main panel with border
         JPanel mainPanel = new JPanel();
         mainPanel.setLayout(new BorderLayout(10, 10));
         mainPanel.setBackground(PANEL_BACKGROUND);
         mainPanel.setBorder(BorderFactory.createCompoundBorder(
             BorderFactory.createLineBorder(BORDER_COLOR, 3),
             BorderFactory.createEmptyBorder(15, 15, 15, 15)
         ));
         
         // Add header with title and close button
         JPanel headerPanel = createHeaderPanel();
         mainPanel.add(headerPanel, BorderLayout.NORTH);
         
         // Create central content panel with actions
         JPanel contentPanel = new JPanel();
         contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
         contentPanel.setBackground(PANEL_BACKGROUND);
         
         // Add different sections
         contentPanel.add(createFeedingSection());
         contentPanel.add(Box.createVerticalStrut(20));
         contentPanel.add(createGiftsSection());
         contentPanel.add(Box.createVerticalStrut(20));
         contentPanel.add(createActivitiesSection());
         contentPanel.add(Box.createVerticalStrut(20));
         contentPanel.add(createHealthSection());
         contentPanel.add(Box.createVerticalStrut(20)); // Extra padding at bottom
         
         // Create scroll pane for content
         JScrollPane scrollPane = new JScrollPane(contentPanel);
         scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
         scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
         scrollPane.setBorder(BorderFactory.createEmptyBorder()); // No border on scroll pane
         scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Faster scrolling
         scrollPane.setBackground(PANEL_BACKGROUND);
         scrollPane.getViewport().setBackground(PANEL_BACKGROUND);
         
         // Add scrollable content to main panel
         mainPanel.add(scrollPane, BorderLayout.CENTER);
         
         // Set as content pane
         setContentPane(mainPanel);
     }
     
     /**
      * Creates the header panel with title and close button
      */
     private JPanel createHeaderPanel() {
         JPanel headerPanel = new JPanel(new BorderLayout());
         headerPanel.setBackground(PANEL_BACKGROUND);
         headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
         
         // Title label
         JLabel titleLabel = new JLabel("Pet Care Actions");
         titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
         titleLabel.setForeground(TEXT_COLOR);
         headerPanel.add(titleLabel, BorderLayout.WEST);
         
         // Close button
         JButton closeButton = createStyledButton("Close", null);
         closeButton.addActionListener(e -> escActionPerformed(e));
         headerPanel.add(closeButton, BorderLayout.EAST);
         
         return headerPanel;
     }
     
     /**
      * Creates the feeding section with food selection and feed button
      */
     private JPanel createFeedingSection() {
         JPanel panel = createSectionPanel("Feeding", "Feed your pet to increase hunger");
         
         // Current food display
         JPanel foodDisplayPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         foodDisplayPanel.setOpaque(false);
         
         // Navigation buttons and current food
         JButton prevButton = createCircleButton("<", 16);
         prevButton.addActionListener(e -> prevFoodActionPerformed(e));
         
         // Food info panel (name and count)
         JPanel foodInfoPanel = new JPanel();
         foodInfoPanel.setLayout(new BoxLayout(foodInfoPanel, BoxLayout.Y_AXIS));
         foodInfoPanel.setOpaque(false);
         
         currentFoodLabel = new JLabel(foodNames[currentImageIndexFood], JLabel.CENTER);
         currentFoodLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
         currentFoodLabel.setForeground(TEXT_COLOR);
         currentFoodLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
         
         // Get inventory count for current food
         int foodCount = getInventoryCountForFood(currentImageIndexFood);
         
         // Create count label with background panel for visibility
         JPanel countPanel = new JPanel();
         countPanel.setBackground(COUNT_BACKGROUND);
         countPanel.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
         
         foodCountLabel = new JLabel("You have: " + foodCount, JLabel.CENTER);
         foodCountLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
         foodCountLabel.setForeground(TEXT_COLOR);
         
         countPanel.add(foodCountLabel);
         
         foodInfoPanel.add(currentFoodLabel);
         foodInfoPanel.add(Box.createVerticalStrut(5));
         foodInfoPanel.add(countPanel);
         foodInfoPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
         
         JButton nextButton = createCircleButton(">", 16);
         nextButton.addActionListener(e -> nextFoodActionPerformed(e));
         
         foodDisplayPanel.add(prevButton);
         foodDisplayPanel.add(Box.createHorizontalStrut(15));
         foodDisplayPanel.add(foodInfoPanel);
         foodDisplayPanel.add(Box.createHorizontalStrut(15));
         foodDisplayPanel.add(nextButton);
         
         // Feed button
         JButton feedButton = createStyledButton("Feed", "Give food to your pet");
         feedButton.addActionListener(e -> feedActionPerformed(e));
         
         panel.add(foodDisplayPanel);
         panel.add(Box.createVerticalStrut(10));
         panel.add(feedButton);
         
         return panel;
     }
     
     /**
      * Creates the gifts section with gift selection and give gift button
      */
     private JPanel createGiftsSection() {
         JPanel panel = createSectionPanel("Gifts", "Give gifts to increase happiness");
         
         // Current gift display
         JPanel giftDisplayPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         giftDisplayPanel.setOpaque(false);
         
         // Navigation buttons and current gift
         JButton prevButton = createCircleButton("<", 16);
         prevButton.addActionListener(e -> prevGiftActionPerformed(e));
         
         // Gift info panel (name and count)
         JPanel giftInfoPanel = new JPanel();
         giftInfoPanel.setLayout(new BoxLayout(giftInfoPanel, BoxLayout.Y_AXIS));
         giftInfoPanel.setOpaque(false);
         
         currentGiftLabel = new JLabel(giftNames[currentImageIndexGift], JLabel.CENTER);
         currentGiftLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
         currentGiftLabel.setForeground(TEXT_COLOR);
         currentGiftLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
         
         // Get inventory count for current gift
         int giftCount = getInventoryCountForGift(currentImageIndexGift);
         
         // Create count label with background panel for visibility
         JPanel countPanel = new JPanel();
         countPanel.setBackground(COUNT_BACKGROUND);
         countPanel.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
         
         giftCountLabel = new JLabel("You have: " + giftCount, JLabel.CENTER);
         giftCountLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
         giftCountLabel.setForeground(TEXT_COLOR);
         
         countPanel.add(giftCountLabel);
         
         giftInfoPanel.add(currentGiftLabel);
         giftInfoPanel.add(Box.createVerticalStrut(5));
         giftInfoPanel.add(countPanel);
         giftInfoPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
         
         JButton nextButton = createCircleButton(">", 16);
         nextButton.addActionListener(e -> nextGiftActionPerformed(e));
         
         giftDisplayPanel.add(prevButton);
         giftDisplayPanel.add(Box.createHorizontalStrut(15));
         giftDisplayPanel.add(giftInfoPanel);
         giftDisplayPanel.add(Box.createHorizontalStrut(15));
         giftDisplayPanel.add(nextButton);
         
         // Give gift button
         JButton giftButton = createStyledButton("Give Gift", "Make your pet happy with a gift");
         giftButton.addActionListener(e -> giveGiftActionPerformed(e));
         
         panel.add(giftDisplayPanel);
         panel.add(Box.createVerticalStrut(10));
         panel.add(giftButton);
         
         return panel;
     }
     
     /**
      * Creates the activities section with play and walk buttons
      */
     private JPanel createActivitiesSection() {
         JPanel panel = createSectionPanel("Activities", "Keep your pet active and happy");
         
         // Play button
         JButton playButton = createStyledButton("Play", "Play with your pet to increase happiness");
         playButton.addActionListener(e -> playActionPerformed(e));
         
         // Walk button
         JButton walkButton = createStyledButton("Walk", "Take your pet for a walk");
         walkButton.addActionListener(e -> walkPetActionPerformed(e));
         
         panel.add(playButton);
         panel.add(Box.createVerticalStrut(10));
         panel.add(walkButton);
         
         return panel;
     }
     
     /**
      * Creates the health section with sleep and vet buttons
      */
     private JPanel createHealthSection() {
         JPanel panel = createSectionPanel("Health & Rest", "Take care of your pet's well-being");
         
         // Sleep button
         JButton sleepButton = createStyledButton("Sleep", "Put your pet to bed");
         sleepButton.addActionListener(e -> sleepActionPerformed(e));
         
         // Vet button
         JButton vetButton = createStyledButton("Visit Vet", "Take pet to vet to increase health");
         vetButton.addActionListener(e -> takeToVetActionPerformed(e));
         
         panel.add(sleepButton);
         panel.add(Box.createVerticalStrut(10));
         panel.add(vetButton);
         
         return panel;
     }
     
     /**
      * Creates a circular button with the specified text and font size
      */
     private JButton createCircleButton(String text, int fontSize) {
         JButton button = new JButton(text) {
             @Override
             protected void paintComponent(Graphics g) {
                 Graphics2D g2 = (Graphics2D) g.create();
                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                 
                 if (getModel().isPressed()) {
                     g2.setColor(CIRCLE_BUTTON_COLOR.darker());
                 } else if (getModel().isRollover()) {
                     g2.setColor(BUTTON_HOVER);
                 } else {
                     g2.setColor(CIRCLE_BUTTON_COLOR);
                 }
                 
                 g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                 g2.setColor(BORDER_COLOR);
                 g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
                 
                 // Calculate position for text
                 g2.setColor(CIRCLE_BUTTON_TEXT);
                 g2.setFont(new Font("Comic Sans MS", Font.BOLD, fontSize));
                 
                 int textWidth = g2.getFontMetrics().stringWidth(text);
                 int textHeight = g2.getFontMetrics().getHeight();
                 
                 g2.drawString(text, 
                     (getWidth() - textWidth) / 2, 
                     (getHeight() + textHeight / 2) / 2);
                 
                 g2.dispose();
             }
         };
         
         button.setPreferredSize(new Dimension(40, 40));
         button.setFocusPainted(false);
         button.setBorderPainted(false);
         button.setContentAreaFilled(false);
         button.setOpaque(false);
         
         return button;
     }
     
     /**
      * Creates a styled section panel with title and description
      */
     private JPanel createSectionPanel(String title, String description) {
         JPanel panel = new JPanel();
         panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
         panel.setBackground(SECTION_BACKGROUND);
         panel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
         
         // Create titled border with custom colors
         TitledBorder titledBorder = BorderFactory.createTitledBorder(
             BorderFactory.createLineBorder(BORDER_COLOR, 2),
             title
         );
         titledBorder.setTitleFont(new Font("Comic Sans MS", Font.BOLD, 16));
         titledBorder.setTitleColor(TEXT_COLOR);
         
         // Add the border and padding
         panel.setBorder(BorderFactory.createCompoundBorder(
             titledBorder,
             BorderFactory.createEmptyBorder(10, 15, 15, 15)
         ));
         
         // Add description
         if (description != null) {
             JLabel descLabel = new JLabel(description);
             descLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
             descLabel.setForeground(TEXT_COLOR);
             descLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
             panel.add(descLabel);
             panel.add(Box.createVerticalStrut(10));
         }
         
         return panel;
     }
     
     /**
      * Creates a styled button with text and optional tooltip
      */
     private JButton createStyledButton(String text, String tooltip) {
         JButton button = new JButton(text);
         button.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
         button.setForeground(TEXT_COLOR);
         button.setBackground(BUTTON_COLOR);
         button.setFocusPainted(false);
         button.setBorderPainted(true);
         button.setContentAreaFilled(true);
         button.setBorder(new CompoundBorder(
             new LineBorder(BORDER_COLOR, 2, true),
             new EmptyBorder(8, 15, 8, 15)
         ));
         
         // Set tooltip if provided
         if (tooltip != null) {
             button.setToolTipText(tooltip);
         }
         
         // Add hover effect
         button.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseEntered(MouseEvent e) {
                 button.setBackground(BUTTON_HOVER);
             }
             
             @Override
             public void mouseExited(MouseEvent e) {
                 button.setBackground(BUTTON_COLOR);
             }
         });
         
         // Set alignment for use in BoxLayout
         button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
         
         return button;
     }
     
     /**
      * Get the inventory count for a specific food
      */
      private int getInventoryCountForFood(int foodIndex) {
        // Get count from game inventory
        if (game != null && game.getInventory() != null) {
            Item[] items = game.getInventory().getInventory();
            if (items != null && foodIndex >= 0 && foodIndex < 3) {
                return items[foodIndex].getNumberItem();
            }
        }
        return 0; // Default if game or inventory is null
    }
     
     /**
      * Get the inventory count for a specific gift
      */
      private int getInventoryCountForGift(int giftIndex) {
        // In the inventory, gifts are at indices 3, 4, 5
        int inventoryIndex = giftIndex + 3;
        
        if (game != null && game.getInventory() != null) {
            Item[] items = game.getInventory().getInventory();
            if (items != null && inventoryIndex >= 3 && inventoryIndex < 6) {
                return items[inventoryIndex].getNumberItem();
            }
        }
        return 0; // Default if game or inventory is null
    }
     
     /**
      * Updates the current food/gift labels and count when navigation buttons are clicked
      */
     private void updateButtonLabels() {
         if (currentFoodLabel != null) {
             currentFoodLabel.setText(foodNames[currentImageIndexFood]);
             
             // Update food count
             int foodCount = getInventoryCountForFood(currentImageIndexFood);
             foodCountLabel.setText("You have: " + foodCount);
         }
         
         if (currentGiftLabel != null) {
             currentGiftLabel.setText(giftNames[currentImageIndexGift]);
             
             // Update gift count
             int giftCount = getInventoryCountForGift(currentImageIndexGift);
             giftCountLabel.setText("You have: " + giftCount);
         }
     }
     
     /**
      * Sets up key bindings for the commands window.
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
         
         // Bind the F key to the action
         inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "feedAction");
         actionMap.put("feedAction", feedAction);
         
         // Bind the G key to the action
         inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0), "giftAction");
         actionMap.put("giftAction", giftAction);
         
         // Add ESC key to close the window
         inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeWindow");
         actionMap.put("closeWindow", new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 dispose();
             }
         });
     }
 
     // Event handlers - these maintain the original functionality
     
     private void escActionPerformed(java.awt.event.ActionEvent evt) {
         // Close the commands window
         this.dispose();
     }
     
     private void takeToVetActionPerformed(java.awt.event.ActionEvent evt) {
         // Take pet to vet to increase health
         game.checkState();
         if(game.validState() && game.takeToVet()){
             // Successfully took pet to vet
             System.out.println("Pet has been treated by the vet!");
             // You could add animation or sound effects here
         }
         else{
             // On cooldown or other failure
             System.out.println("Cannot take pet to vet right now. Try again later.");
         }
     }
     
     private void walkPetActionPerformed(java.awt.event.ActionEvent evt) {
         // Take pet for a walk to improve health and happiness
         game.checkState();
         if( (game.validState() || game.getPet().getState() == "angry") && game.walk()){
             // Successfully walked pet
             System.out.println(game.validState());
             System.out.println(game.getPet().getState() == "angry");
             System.out.println("Took pet for a walk!");
             // You could add animation or sound effects here
         }
         else{
             // On cooldown or other failure
             System.out.println("Cannot walk pet right now. Try again later.");
         }
     }
     
     private void sleepActionPerformed(java.awt.event.ActionEvent evt) {
         // Put pet to sleep to restore sleep stat
         System.out.println("Sleep button pressed");
         game.checkState();
         if(game.validState() && game.goToBed()){
             System.out.println("Pet is now sleeping!");
             // You could add animation or sound effects here
         }
         else{
             System.out.println("Pet cannot sleep right now. Try again later.");
         }
     }
     
     private void playActionPerformed(java.awt.event.ActionEvent evt) {
         // Play with pet to increase happiness
         game.checkState();
         if( (game.validState() || game.getPet().getState() == "angry") && game.play()){
             // Successfully played with pet
             System.out.println("Playing with pet!");
             System.out.println(game.validState());
             System.out.println(game.getPet().getState() == "angry");
             // You could add animation or sound effects here
         }
         else{
             // On cooldown or other failure
             System.out.println("Cannot play with pet right now. Try again later.");
         }
     }
     
     private void giveGiftActionPerformed(java.awt.event.ActionEvent evt) {
         // Give gift to pet to increase happiness
         // Different gifts might have different effects based on the selected gift type
         String giftType = button11Images[currentImageIndexGift];
         System.out.println("Giving gift: " + giftType);
         System.out.println("Gift button pressed" + currentImageIndexGift);
         
         game.checkState();
         if((game.validState() || game.getPet().getState() == "angry") && game.giveGift(currentImageIndexGift+3) ){
             // Successfully gave gift to pet
             System.out.println("Gave a gift to the pet!");
             // Update the inventory count display after giving a gift
             updateButtonLabels();
             // You could add animation or sound effects here
         }
         else{
             // On cooldown or other failure
             System.out.println("Cannot give gift right now. Try again later.");
         }
     }
     
     private void feedActionPerformed(java.awt.event.ActionEvent evt) {
         // Feed pet to increase hunger stat
         // Different foods might have different effects based on the selected food type
         String foodType = button12Images[currentImageIndexFood];
         System.out.println("Feeding: " + foodType);
         
         game.checkState();
         if((game.validState() || game.getPet().getState() == "angry") && game.feed(currentImageIndexFood)) {
             // Successfully fed the pet
             System.out.println("Fed the pet!");
             // Update the inventory count display after feeding
             updateButtonLabels();
             // The Game class will handle refreshing the UI
         }
         else{
             // On cooldown or other failure
             System.out.println("Cannot feed pet right now. Try again later.");
         }
     }
     
     private void nextGiftActionPerformed(java.awt.event.ActionEvent evt) {
         // Cycle to next gift image
         currentImageIndexGift = (currentImageIndexGift + 1) % button11Images.length;
         updateButtonLabels();
     }
     
     private void prevGiftActionPerformed(java.awt.event.ActionEvent evt) {
         // Cycle to previous gift image
         currentImageIndexGift = (currentImageIndexGift - 1 + button11Images.length) % button11Images.length;
         updateButtonLabels();
     }
     
     private void prevFoodActionPerformed(java.awt.event.ActionEvent evt) {
         // Cycle to previous food image
         currentImageIndexFood = (currentImageIndexFood - 1 + button12Images.length) % button12Images.length;
         updateButtonLabels();
     }
     
     private void nextFoodActionPerformed(java.awt.event.ActionEvent evt) {
         // Cycle to next food image
         currentImageIndexFood = (currentImageIndexFood + 1) % button12Images.length;
         updateButtonLabels();
     }
 }