import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

/**
 * mainGameNew - New implementation of the main game screen that displays pet stats
 */
public class mainGameNew extends JFrame {
    // Pet and game data
    private Pet currentPet;
    private GameSaveData saveData;
    
    // Color scheme based on the Main_Menu
    private final Color TITLE_COLOR = new Color(75, 91, 22); // Dark green
    private final Color TEXT_COLOR = new Color(58, 71, 15);  // Darker green for text
    private final Color STAT_PANEL_BG = new Color(255, 255, 240, 220); // Slightly transparent cream
    private final Color BUTTON_BACKGROUND = new Color(255, 255, 240, 220); // Slightly transparent cream
    private final Color BUTTON_TEXT = new Color(70, 86, 20); // Dark green
    private final Color BUTTON_BORDER = new Color(139, 87, 42); // Brown color for button borders
    
    // UI Components
    private JLabel petImageLabel;
    
    public mainGameNew(Pet pet, GameSaveData saveData) {
        this.currentPet = pet;
        this.saveData = saveData;
        
        initComponents();
        setTitle("Virtual Pet: " + pet.getName());
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start in full screen
    }

    private void initComponents() {
        // Main content pane with BorderLayout
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        
        // Create the main background panel
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        contentPane.add(backgroundPanel, BorderLayout.CENTER);
        
        // Use absolute positioning for components on top of background
        backgroundPanel.setLayout(null);
        
        // Top panel for buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        // Back button
        JButton backButton = createStyledButton("Back to Main Menu", 200);
        backButton.addActionListener(e -> {
            UserInterface.saveCurrentGame();
            dispose();
            SwingUtilities.invokeLater(() -> UserInterface.showMainMenu());
        });
        
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setOpaque(false);
        backButtonPanel.add(backButton);
        
        // Save button
        JButton saveButton = createStyledButton("Save Game", 150);
        saveButton.addActionListener(e -> UserInterface.saveCurrentGame());
        
        JPanel saveButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButtonPanel.setOpaque(false);
        saveButtonPanel.add(saveButton);
        
        topPanel.add(backButtonPanel, BorderLayout.WEST);
        topPanel.add(saveButtonPanel, BorderLayout.EAST);
        
        // Add top panel with fixed position and size
        topPanel.setBounds(0, 0, 1200, 70);
        backgroundPanel.add(topPanel);
        
        // Center panel for pet name and image
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        
        // Pet name label
        JLabel nameLabel = new JLabel(currentPet.getName() + " the " + currentPet.getType());
        nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        nameLabel.setForeground(TITLE_COLOR);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Pet image
        petImageLabel = new JLabel(currentPet.getPetImage());
        petImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(nameLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(petImageLabel);
        
        // Make center panel position fixed relative to window
        centerPanel.setBounds(400, 100, 400, 300);
        backgroundPanel.add(centerPanel);
        
        // Create stats panel
        JPanel statsContainer = createStatsPanel();
        // Position the stats panel at the bottom
        statsContainer.setBounds(300, 500, 600, 250);
        backgroundPanel.add(statsContainer);
        
        // Set minimum size
        setMinimumSize(new Dimension(800, 600));
        
        // Set default close operation
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UserInterface.saveCurrentGame();
                dispose();
                UserInterface.safeExit();
            }
        });
        
        // Make window auto-resize stats panel
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                
                // Reposition and resize components based on window size
                topPanel.setBounds(0, 0, width, 70);
                
                // Center the pet display
                centerPanel.setBounds((width - 400) / 2, 100, 400, 300);
                
                // Adjust stats panel position and size
                statsContainer.setBounds((width - 600) / 2, height - 250, 600, 200);
                
                // Update font sizes
                nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, Math.max(24, Math.min(36, width / 25))));
            }
        });
    }
    
    // Create the stats panel
    private JPanel createStatsPanel() {
        // Main container with border
        JPanel container = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw rounded rectangle with semi-transparent background
                g2d.setColor(STAT_PANEL_BG);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Draw border
                g2d.setColor(BUTTON_BORDER);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                super.paintComponent(g);
            }
        };
        container.setOpaque(false);
        container.setLayout(new BorderLayout(10, 10));
        container.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Title
        JLabel titleLabel = new JLabel("Pet Stats", JLabel.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        titleLabel.setForeground(TITLE_COLOR);
        
        // Stats grid (2x2)
        JPanel statsGrid = new JPanel(new GridLayout(2, 2, 20, 20));
        statsGrid.setOpaque(false);
        
        // Add the individual stat components
        statsGrid.add(createStatDisplay("Health", currentPet.getHealth(), 100));
        statsGrid.add(createStatDisplay("Happiness", currentPet.getHappiness(), 100));
        statsGrid.add(createStatDisplay("Sleep", currentPet.getSleep(), 100));
        statsGrid.add(createStatDisplay("Hunger", currentPet.getHunger(), 100));
        
        // Add components to the container
        container.add(titleLabel, BorderLayout.NORTH);
        container.add(statsGrid, BorderLayout.CENTER);
        
        return container;
    }
    
    // Create a display for an individual stat
    private JPanel createStatDisplay(String statName, double value, int maxValue) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        // Stat name
        JLabel nameLabel = new JLabel(statName, JLabel.CENTER);
        nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        nameLabel.setForeground(TEXT_COLOR);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Stat value
        JLabel valueLabel = new JLabel(String.format("%.1f", value), JLabel.CENTER);
        valueLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        valueLabel.setForeground(TEXT_COLOR);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Progress bar panel
        JPanel progressPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(new Color(220, 220, 220));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Progress fill
                int progressWidth = (int)(getWidth() * (value / maxValue));
                g2d.setColor(getColorForStat(statName, value));
                g2d.fillRoundRect(0, 0, progressWidth, getHeight(), 10, 10);
                
                // Border
                g2d.setColor(new Color(120, 120, 120));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(150, 20);
            }
            
            @Override
            public Dimension getMinimumSize() {
                return new Dimension(100, 20);
            }
            
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(300, 20);
            }
        };
        progressPanel.setOpaque(false);
        progressPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add components to panel
        panel.add(nameLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(valueLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(progressPanel);
        
        return panel;
    }
    
    // Get color for stat based on value
    private Color getColorForStat(String statName, double value) {
        if (value >= 80) {
            return new Color(75, 181, 67); // Green for good
        } else if (value >= 50) {
            return new Color(255, 204, 0); // Yellow for ok
        } else {
            return new Color(200, 0, 0); // Red for bad
        }
    }
    
    // Create a styled button
    private JButton createStyledButton(String text, int width) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create gradient background
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(255, 255, 240, 220), 
                    0, getHeight(), new Color(235, 235, 210, 220)
                );
                g2.setPaint(gp);
                
                // Draw rounded rectangle for button
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
                
                // Draw border
                g2.setColor(BUTTON_BORDER);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
                
                g2.dispose();
                
                // Let UI delegate handle the text
                super.paintComponent(g);
            }
        };
        
        // Make button transparent so our custom painting shows
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        
        // Style the text
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        button.setForeground(BUTTON_TEXT);
        
        // Set the button size
        button.setPreferredSize(new Dimension(width, 40));
        button.setMaximumSize(new Dimension(width, 40));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                Font currentFont = button.getFont();
                button.setFont(new Font(currentFont.getName(), Font.BOLD, currentFont.getSize() + 1));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            public void mouseExited(MouseEvent evt) {
                Font currentFont = button.getFont();
                button.setFont(new Font(currentFont.getName(), Font.BOLD, currentFont.getSize() - 1));
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        return button;
    }
    
    // Background panel class similar to Main_Menu
    private class BackgroundPanel extends JPanel {
        private final ImageIcon backgroundImage = new ImageIcon("assets\\images\\Backgrounds\\Main_Menu.png");

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the image to fill the entire panel, maintaining aspect ratio
            Image img = backgroundImage.getImage();
            
            // Calculate dimensions to maintain aspect ratio while filling the panel
            double panelRatio = (double) getWidth() / getHeight();
            double imageRatio = (double) img.getWidth(this) / img.getHeight(this);
            
            int drawWidth, drawHeight;
            int x = 0, y = 0;
            
            if (panelRatio > imageRatio) {
                // Panel is wider than image ratio, so fill width
                drawWidth = getWidth();
                drawHeight = (int)(drawWidth / imageRatio);
                y = (getHeight() - drawHeight) / 2; // Center vertically
            } else {
                // Panel is taller than image ratio, so fill height
                drawHeight = getHeight();
                drawWidth = (int)(drawHeight * imageRatio);
                x = (getWidth() - drawWidth) / 2; // Center horizontally
            }
            
            g.drawImage(img, x, y, drawWidth, drawHeight, this);
        }
    }
}