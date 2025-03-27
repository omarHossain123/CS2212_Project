import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PetSelection {
    public static Pet selectedPet;
    
    public static void selectPet() {
        // Create background image
        String backgroundPath = "assets/images/uiElements/Pet_Selection2.png";
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
        
        // Create a panel to center the pets both horizontally and vertically
        JPanel petCenterPanel = new JPanel(new GridBagLayout());
        petCenterPanel.setOpaque(false);
        
        // Create a sub-panel for the pets with FlowLayout
        JPanel petFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
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
            
            JPanel petPanel = new JPanel(new BorderLayout());
            petPanel.setOpaque(false);
            
            // Create neutral and blush image paths
            String neutralImagePath = pet.getPetImage().toString();
            String blushImagePath = neutralImagePath.replace("neutral.png", "blush.png");
            
            JLabel petLabel = new JLabel(pet.getPetImage());
            
            // Create buttons panel
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
            buttonsPanel.setOpaque(false);
            
            // View Description Button
            JButton descriptionButton = createStyledButton("View Description");
            descriptionButton.addActionListener(e -> showPetStats(pet, isLastPet));
            
            // Select Pet Button
            JButton selectButton = createStyledButton("Select Pet");
            selectButton.addActionListener(e -> confirmPetSelection(pet, frame));
            
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
            selectButton.addMouseListener(blushAdapter);
            
            buttonsPanel.add(descriptionButton);
            buttonsPanel.add(selectButton);
            
            petPanel.add(petLabel, BorderLayout.CENTER);
            petPanel.add(buttonsPanel, BorderLayout.SOUTH);
            
            petFlowPanel.add(petPanel);
        }
        
        // Add the flow panel to the center panel
        petCenterPanel.add(petFlowPanel);
        
        backgroundPanel.add(petCenterPanel, BorderLayout.CENTER);
        
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }
    
    // Helper method to create styled buttons
    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(210, 180, 140)); // Tan color
        button.setForeground(new Color(70, 40, 20)); // Dark brown text
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(new CompoundBorder(
            new LineBorder(new Color(139, 69, 19), 2, true),
            new EmptyBorder(5, 15, 5, 15)
        ));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(190, 160, 120)); // Slightly darker brown
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(210, 180, 140)); // Original color
            }
        });
        
        return button;
    }
    
    // Method to confirm pet selection
    private static void confirmPetSelection(Pet pet, JFrame parentFrame) {
        int result = JOptionPane.showConfirmDialog(
            parentFrame, 
            "Are you sure you want to select " + pet.getType() + "?", 
            "Confirm Pet Selection", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (result == JOptionPane.YES_OPTION) {
            selectedPet = pet;
            // TODO: Add code to move to the next screen
            // For now, just close the current frame
            parentFrame.dispose();
        }
    }
    
    private static void showPetStats(Pet pet, boolean isLockedPet) {
        // Create custom dialog
        JDialog statsDialog = new JDialog();
        statsDialog.setTitle("Pet Stats");
        statsDialog.setSize(400, 300);
        statsDialog.setModal(true);
        
        // Custom panel with brown background
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setBackground(new Color(222, 184, 135)); // Light brown background
        
        // Stats content
        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setBackground(new Color(255, 248, 220)); // Cornsilk background
        statsArea.setForeground(new Color(70, 40, 20)); // Dark brown text
        statsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        statsArea.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        if (isLockedPet) {
            statsArea.setText("LOCKED PET\n\n" +
                "Unlock Requirements:\n" +
                "- Reach 1000 total points\n" +
                "- Complete specific challenges\n\n" +
                "This special pet requires dedication\n" +
                "and skill to unlock!");
        } else {
            statsArea.setText(String.format(
                "Pet Type: %s\n\n" +
                "Special Characteristics:\n" +
                "- Health: %d/100\n" +
                "- Happiness: %d/100\n" +
                "- Sleep Need: %d/100\n" +
                "- Hunger Rate: %d/100\n\n" +
                "Unique Traits:\n" +
                "This pet requires careful attention\n" +
                "to maintain its optimal state.",
                pet.getType(), 
                pet.getHealth(), 
                pet.getHappiness(), 
                pet.getSleep(), 
                pet.getHunger()
            ));
        }
        
        // Layout
        dialogPanel.add(new JScrollPane(statsArea), BorderLayout.CENTER);
        
        statsDialog.setContentPane(dialogPanel);
        statsDialog.setLocationRelativeTo(null);
        statsDialog.setVisible(true);
    }
}