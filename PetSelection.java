import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.StyledDocument;

public class PetSelection {
    public static Pet selectedPet;
    
    // Custom colors to match the pastoral image
    private static final Color PASTEL_GREEN = new Color(154, 177, 125);
    private static final Color PASTEL_BROWN = new Color(164, 116, 73);
    private static final Color PASTEL_SKY = new Color(173, 198, 178);
    private static final Color PASTEL_TEXT = new Color(90, 62, 44);
    private static final Color PASTEL_BUTTON = new Color(217, 188, 133);
    private static final Color PASTEL_BUTTON_HOVER = new Color(227, 205, 159);
    private static final Color PASTEL_DIALOG_BG = new Color(230, 238, 213);
    
    public static void selectPet(Consumer<Pet> onPetSelected) {
        // Create background image
        String backgroundPath = "assets\\images\\Backgrounds\\PetSelection.png";
        ImageIcon backgroundImage = new ImageIcon(backgroundPath);
        
        JFrame frame = new JFrame("Select Your Pet");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a custom panel with background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        
        // Create a title panel at the top with a rustic look
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Choose Your New Friend");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        titleLabel.setForeground(PASTEL_BROWN);
        titlePanel.add(titleLabel);
        
        // Create a panel to center the pets
        JPanel petCenterPanel = new JPanel(new GridBagLayout());
        petCenterPanel.setOpaque(false);
        
        // Create a sub-panel for the pets with FlowLayout
        JPanel petFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        petFlowPanel.setOpaque(false);
        
        String basePath = "assets/images/petSprites/";
        
        // Create pets with their specific names and image paths
        Pet debatchi = new Pet("Debatchi", "Debatchi", 120, 80, 100, 90, 
            basePath + "Debatchi/head1/pet1_head1_neutral.png");
        
        Pet kikitchi = new Pet("Kikitchi", "Kikitchi", 100, 90, 85, 95, 
            basePath + "Kikitchi/head1/pet3_head1_neutral.png");
        
        Pet mametchi = new Pet("Mametchi", "Mametchi", 110, 85, 90, 85, 
            basePath + "Mametchi/head1/pet4_head1_neutral.png");
        
        Pet tsuyopitchi = new Pet("Tsuyopitchi", "Tsuyopitchi", 105, 95, 80, 100, 
            basePath + "Tsuyopitchi/head1/pet2_head1_neutral.png");
        
        Pet[] pets = {debatchi, kikitchi, mametchi, tsuyopitchi};
        
        for (int index = 0; index < pets.length; index++) {
            final Pet pet = pets[index];
            final boolean isLastPet = (index == pets.length - 1);
            
            JPanel petPanel = new JPanel(new BorderLayout(0, 10));
            petPanel.setOpaque(false);
            
            // Add pet name on top with a cute font
            JLabel nameLabel = new JLabel(pet.getType(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            nameLabel.setForeground(PASTEL_TEXT);
            
            // Create neutral and blush image paths
            String neutralImagePath = pet.getPetImage().toString();
            String blushImagePath = neutralImagePath.replace("neutral.png", "blush.png");
            
            JLabel petLabel = new JLabel(pet.getPetImage());
            
            // Create a panel with a slight transparency for the pet image
            JPanel petImagePanel = new JPanel();
            petImagePanel.setOpaque(false);
            petImagePanel.add(petLabel);
            
            // Create buttons panel
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
            buttonsPanel.setOpaque(false);
            
            // View Description Button
            JButton descriptionButton = createPastoralButton("About");
            descriptionButton.addActionListener(e -> showPetStats(pet, isLastPet, frame));
            
            // Select Pet Button - only for unlocked pets
            JButton selectButton = createPastoralButton(isLastPet ? "Locked" : "Choose");
            
            if (isLastPet) {
                // Visual indicator that pet is locked
                selectButton.setBackground(new Color(180, 180, 180)); // Grayed out
                selectButton.setForeground(new Color(80, 80, 80));
                
                // Show locked message when clicked
                selectButton.addActionListener(e -> {
                    JDialog lockedDialog = new JDialog(frame, "Pet Locked", true);
                    lockedDialog.setSize(350, 200);
                    lockedDialog.setLayout(new BorderLayout());
                    lockedDialog.getContentPane().setBackground(PASTEL_DIALOG_BG);
                    
                    JLabel lockedLabel = new JLabel("<html><center>🔒 This pet is currently locked! 🔒<br><br>" +
                            "Continue playing to unlock this special friend.</center></html>", JLabel.CENTER);
                    lockedLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
                    lockedLabel.setForeground(PASTEL_TEXT);
                    lockedLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    
                    JButton lockedOkButton = createPastoralButton("OK");
                    JPanel lockedButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    lockedButtonPanel.setBackground(PASTEL_DIALOG_BG);
                    lockedButtonPanel.add(lockedOkButton);
                    
                    lockedOkButton.addActionListener(evt -> lockedDialog.dispose());
                    
                    lockedDialog.add(lockedLabel, BorderLayout.CENTER);
                    lockedDialog.add(lockedButtonPanel, BorderLayout.SOUTH);
                    lockedDialog.setLocationRelativeTo(frame);
                    lockedDialog.setVisible(true);
                });
            } else {
                // Normal selection for unlocked pets
                selectButton.addActionListener(e -> {
                    Pet updatedPet = promptForPetName(pet, frame);
                    if (updatedPet != null) {
                        confirmPetSelection(updatedPet, frame, onPetSelected);
                    }
                });
            }
            
            // Add hover effect to blush the pet image
            MouseAdapter blushAdapter = new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    petLabel.setIcon(new ImageIcon(blushImagePath));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    petLabel.setIcon(pet.getPetImage());
                }
            };
            descriptionButton.addMouseListener(blushAdapter);
            
            // Only add blush effect to unlocked pets' select buttons
            if (!isLastPet) {
                selectButton.addMouseListener(blushAdapter);
            }
            
            buttonsPanel.add(descriptionButton);
            buttonsPanel.add(selectButton);
            
            petPanel.add(nameLabel, BorderLayout.NORTH);
            petPanel.add(petImagePanel, BorderLayout.CENTER);
            petPanel.add(buttonsPanel, BorderLayout.SOUTH);
            
            petFlowPanel.add(petPanel);
        }
        
        // Add the flow panel to the center panel
        petCenterPanel.add(petFlowPanel);
        
        backgroundPanel.add(titlePanel, BorderLayout.NORTH);
        backgroundPanel.add(petCenterPanel, BorderLayout.CENTER);
        
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }
    
    // Helper method to create pastoral-themed buttons
    private static JButton createPastoralButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(PASTEL_BUTTON);
        button.setForeground(PASTEL_TEXT);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        
        // Rounded border with wooden fence style
        button.setBorder(new CompoundBorder(
            new LineBorder(PASTEL_BROWN, 2, true),
            new EmptyBorder(6, 18, 6, 18)
        ));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PASTEL_BUTTON_HOVER);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PASTEL_BUTTON);
            }
        });
        
        return button;
    }
    
    // Method to prompt for pet name with pastoral styling
    private static Pet promptForPetName(Pet pet, JFrame parentFrame) {
        // Create custom dialog instead of using JOptionPane
        JDialog nameDialog = new JDialog(parentFrame, "Name Your Pet", true);
        nameDialog.setSize(350, 200);
        nameDialog.setLayout(new BorderLayout());
        nameDialog.getContentPane().setBackground(PASTEL_DIALOG_BG);
        
        JPanel namePanel = new JPanel(new GridBagLayout());
        namePanel.setBackground(PASTEL_DIALOG_BG);
        
        JLabel promptLabel = new JLabel("What would you like to name your " + pet.getType() + "?");
        promptLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        promptLabel.setForeground(PASTEL_TEXT);
        
        JTextField nameField = new JTextField(15);
        nameField.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PASTEL_BROWN, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(PASTEL_DIALOG_BG);
        
        JButton okButton = createPastoralButton("Confirm");
        JButton cancelButton = createPastoralButton("Cancel");
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        namePanel.add(promptLabel, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 15, 10);
        namePanel.add(nameField, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        namePanel.add(buttonPanel, gbc);
        
        nameDialog.add(namePanel, BorderLayout.CENTER);
        
        final Pet[] result = new Pet[1]; // Using array to get around the final restriction
        
        okButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                Pet newPet = new Pet(name, pet.getType(), 
                (int)pet.getHealth(), 
                (int)pet.getHappiness(), 
                (int)pet.getSleep(), 
                (int)pet.getHunger(), 
                pet.getPetImage().toString());
                result[0] = newPet;
                nameDialog.dispose();
            } else {
                // Show a separate error dialog instead of modifying the panel
                JDialog errorDialog = new JDialog(nameDialog, "Invalid Name", true);
                errorDialog.setSize(250, 150);
                errorDialog.setLayout(new BorderLayout());
                errorDialog.getContentPane().setBackground(PASTEL_DIALOG_BG);
                
                JLabel errorLabel = new JLabel("Please enter a valid name", JLabel.CENTER);
                errorLabel.setForeground(Color.RED);
                errorLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
                errorLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
                
                JButton errorOkButton = createPastoralButton("OK");
                JPanel errorButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                errorButtonPanel.setBackground(PASTEL_DIALOG_BG);
                errorButtonPanel.add(errorOkButton);
                
                errorDialog.add(errorLabel, BorderLayout.CENTER);
                errorDialog.add(errorButtonPanel, BorderLayout.SOUTH);
                
                errorOkButton.addActionListener(evt -> errorDialog.dispose());
                
                errorDialog.setLocationRelativeTo(nameDialog);
                errorDialog.setVisible(true);
            }
        });
        
        cancelButton.addActionListener(e -> {
            nameDialog.dispose();
        });
        
        // Set dialog position relative to parent frame
        nameDialog.setLocationRelativeTo(parentFrame);
        nameDialog.setVisible(true);
        
        return result[0];
    }
    
    // Method to confirm pet selection with custom dialog
    private static void confirmPetSelection(Pet pet, JFrame parentFrame, Consumer<Pet> onPetSelected) {
        JDialog confirmDialog = new JDialog(parentFrame, "Confirm Your Choice", true);
        confirmDialog.setSize(400, 200);
        confirmDialog.setLayout(new BorderLayout());
        confirmDialog.getContentPane().setBackground(PASTEL_DIALOG_BG);
        
        JLabel messageLabel = new JLabel("<html><center>Are you sure you want to choose<br>" +
            pet.getName() + " the " + pet.getType() + "?</center></html>", JLabel.CENTER);
        messageLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        messageLabel.setForeground(PASTEL_TEXT);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(PASTEL_DIALOG_BG);
        
        JButton yesButton = createPastoralButton("Yes, I'm sure!");
        JButton noButton = createPastoralButton("Let me think...");
        
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(PASTEL_DIALOG_BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        confirmDialog.add(contentPanel, BorderLayout.CENTER);
        
        yesButton.addActionListener(e -> {
            selectedPet = pet;
            confirmDialog.dispose();
            onPetSelected.accept(pet);
            parentFrame.dispose(); // Close the pet selection window
        });
        
        noButton.addActionListener(e -> {
            confirmDialog.dispose();
        });
        
        // Set dialog position relative to parent frame
        confirmDialog.setLocationRelativeTo(parentFrame);
        confirmDialog.setVisible(true);
    }
    
    // Get stat-based description for a pet
    private static String getStatBasedDescription(Pet pet) {
        StringBuilder description = new StringBuilder();
        
        // Health-based description
        if (pet.getHealth() > 110) {
            description.append("• Highly resilient and easy to care for - great for beginners!\n");
        } else if (pet.getHealth() > 100) {
            description.append("• Good health level - can withstand occasional neglect\n");
        } else {
            description.append("• Requires more attention to health - for attentive players\n");
        }
        
        // Happiness-based description
        if (pet.getHappiness() > 90) {
            description.append("• Naturally cheerful - stays happy with minimal play\n");
        } else if (pet.getHappiness() > 80) {
            description.append("• Moderate happiness needs - enjoys regular interaction\n");
        } else {
            description.append("• Needs frequent attention to stay happy - for dedicated players\n");
        }
        
        // Sleep-based description
        if (pet.getSleep() > 95) {
            description.append("• Needs lots of rest - perfect for busy or casual players\n");
        } else if (pet.getSleep() > 85) {
            description.append("• Balanced sleep needs - fits most play styles\n");
        } else {
            description.append("• Requires less sleep - ideal for active players\n");
        }
        
        // Hunger-based description
        if (pet.getHunger() > 95) {
            description.append("• Big appetite - for players who enjoy feeding minigames\n");
        } else if (pet.getHunger() > 85) {
            description.append("• Moderate appetite - balanced feeding schedule\n");
        } else {
            description.append("• Smaller appetite - forgiving if meals are occasionally missed\n");
        }
        
        return description.toString();
    }
    
    // Method to show pet stats with updated styling
    private static void showPetStats(Pet pet, boolean isLockedPet, JFrame parentFrame) {
        // Create custom dialog - now larger
        JDialog statsDialog = new JDialog(parentFrame, "Pet Information", true);
        statsDialog.setSize(450, 400);
        
        // Custom panel with pastoral background
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setBackground(PASTEL_DIALOG_BG);
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Title
        JLabel titleLabel = new JLabel(isLockedPet ? "Special Pet" : pet.getType(), JLabel.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        titleLabel.setForeground(PASTEL_BROWN);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Stats content using JPanel with painted custom background
        JPanel statsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Create a gradient for a sky-like effect
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(255, 255, 255, 200),
                    0, getHeight(), new Color(PASTEL_SKY.getRed(), 
                                              PASTEL_SKY.getGreen(), 
                                              PASTEL_SKY.getBlue(), 200));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Add a light border
                g2d.setColor(PASTEL_BROWN);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 15, 15);
            }
        };
        statsPanel.setOpaque(false);
        statsPanel.setLayout(new BorderLayout());
        statsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JTextPane statsText = new JTextPane();
        statsText.setEditable(false);
        statsText.setOpaque(false);
        statsText.setForeground(PASTEL_TEXT);
        statsText.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        
        // Using StyledDocument to format text a bit nicer
        StyledDocument doc = statsText.getStyledDocument();
        
        if (isLockedPet) {
            try {
                doc.insertString(doc.getLength(), "🔒 SPECIAL SECRET PET 🔒\n\n", null);
                doc.insertString(doc.getLength(), "Unlock Requirements:\n", null);
                doc.insertString(doc.getLength(), "• Reach 1000 total points\n", null);
                doc.insertString(doc.getLength(), "• Complete all challenges\n", null);
                doc.insertString(doc.getLength(), "• Find all hidden treasures\n", null);
                doc.insertString(doc.getLength(), "• Raise 3 pets to max level\n\n", null);
                doc.insertString(doc.getLength(), "This special pet requires dedication and skill to unlock! ", null);
                doc.insertString(doc.getLength(), "Keep playing to discover this rare friend.\n\n", null);
                doc.insertString(doc.getLength(), "This pet has legendary stats and unique abilities!", null);
            } catch (Exception e) {
                statsText.setText("🔒 SPECIAL SECRET PET 🔒\n\nUnlock Requirements:\n• Reach 1000 total points\n• Complete all challenges\n• Find all hidden treasures\n• Raise 3 pets to max level\n\nThis special pet requires dedication and skill to unlock!");
            }
        } else {
            try {
                doc.insertString(doc.getLength(), "Type: " + pet.getType() + "\n\n", null);
                doc.insertString(doc.getLength(), "Stats:\n", null);
                doc.insertString(doc.getLength(), "• Health: " + pet.getHealth() + "\n", null);
                doc.insertString(doc.getLength(), "• Happiness: " + pet.getHappiness() + "\n", null);
                doc.insertString(doc.getLength(), "• Sleep Need: " + pet.getSleep() + "\n", null);
                doc.insertString(doc.getLength(), "• Hunger Rate: " + pet.getHunger() + "\n\n", null);
                
                doc.insertString(doc.getLength(), "Play Style Compatibility:\n", null);
                doc.insertString(doc.getLength(), getStatBasedDescription(pet), null);
                doc.insertString(doc.getLength(), "\n", null);
                
                // Add unique descriptions for each pet
                doc.insertString(doc.getLength(), "Unique Traits:\n", null);
                if (pet.getType().equals("Debatchi")) {
                    doc.insertString(doc.getLength(), "Debatchi loves intellectual challenges and games. This pet will thrive with puzzles and learning activities!\n\n", null);
                    doc.insertString(doc.getLength(), "Perfect for: Players who enjoy strategy and problem-solving.", null);
                } else if (pet.getType().equals("Kikitchi")) {
                    doc.insertString(doc.getLength(), "Kikitchi is playful and energetic! This pet loves physical activities and outdoor adventures.\n\n", null);
                    doc.insertString(doc.getLength(), "Perfect for: Active players who enjoy mini-games.", null);
                } else if (pet.getType().equals("Mametchi")) {
                    doc.insertString(doc.getLength(), "Mametchi is creative and artistic. This pet will appreciate music, colors, and creative activities.\n\n", null);
                    doc.insertString(doc.getLength(), "Perfect for: Players who enjoy customization and decoration.", null);
                } else if (pet.getType().equals("Tsuyopitchi")) {
                    doc.insertString(doc.getLength(), "Tsuyopitchi is strong and resilient! This pet recovers quickly from low stats and enjoys challenges.\n\n", null);
                    doc.insertString(doc.getLength(), "Perfect for: Players who want a slightly more challenging pet.", null);
                } else {
                    doc.insertString(doc.getLength(), "This pet loves attention and care.\nPerfect for new pet parents!", null);
                }
            } catch (Exception e) {
                // Fallback plain text if styling fails
                StringBuilder stats = new StringBuilder();
                stats.append("Type: ").append(pet.getType()).append("\n\n");
                stats.append("Stats:\n");
                stats.append("• Health: ").append(pet.getHealth()).append("\n");
                stats.append("• Happiness: ").append(pet.getHappiness()).append("\n");
                stats.append("• Sleep Need: ").append(pet.getSleep()).append("\n");
                stats.append("• Hunger Rate: ").append(pet.getHunger()).append("\n\n");
                
                stats.append("Play Style Compatibility:\n");
                stats.append(getStatBasedDescription(pet)).append("\n");
                
                stats.append("Unique Traits:\n");
                stats.append("This pet loves attention and care.\n");
                stats.append("Perfect for new pet parents!");
                
                statsText.setText(stats.toString());
            }
        }
        
        // Add text to a scroll pane for longer descriptions
        JScrollPane scrollPane = new JScrollPane(statsText);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        // Ensure scroll starts at the top
        statsText.setCaretPosition(0);
        
        statsPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Close button at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        JButton closeButton = createPastoralButton("Back to Selection");
        closeButton.addActionListener(e -> statsDialog.dispose());
        
        buttonPanel.add(closeButton);
        
        // Assemble the dialog
        dialogPanel.add(titleLabel, BorderLayout.NORTH);
        dialogPanel.add(statsPanel, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        statsDialog.setContentPane(dialogPanel);
        statsDialog.setLocationRelativeTo(parentFrame);
        statsDialog.setVisible(true);
    }
}