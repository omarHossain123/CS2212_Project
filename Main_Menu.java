import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

/**
 * Main_Menu - Responsive Java Swing GUI main menu with centered, styled components.
 */
public class Main_Menu extends JFrame {

    // Color scheme based on the background image
    private final Color TITLE_COLOR = new Color(75, 91, 22); // Dark green
    private final Color TEXT_COLOR = new Color(58, 71, 15);  // Darker green for subtitle text
    private final Color BUTTON_BACKGROUND = new Color(255, 255, 240, 220); // Slightly transparent cream
    private final Color BUTTON_TEXT = new Color(70, 86, 20); // Dark green
    private final Color BUTTON_BORDER = new Color(139, 87, 42); // Brown color for button borders
    
    public Main_Menu() {
        initComponents();
        setTitle("Virtual Pet Application");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start in full screen
    }

    private void initComponents() {
        BackgroundPanel mainPanel = new BackgroundPanel(); // Use BackgroundPanel for custom background
        
        // Create responsive layout that centers components
        mainPanel.setLayout(new BorderLayout());
        
        // Create a center panel that will hold all our components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        
        // Title panel with custom styling - centered
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        
        jLabel1 = new JLabel("Virtual Pet Application");
        jLabel1.setFont(new Font("Comic Sans MS", Font.BOLD, 42));
        jLabel1.setForeground(TITLE_COLOR);
        jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        jLabel2 = new JLabel("Created in Winter 2025 for CS2212 at Western University");
        jLabel2.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        jLabel2.setForeground(TEXT_COLOR);
        jLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        jLabel3 = new JLabel("By Group 15: Isa Alif, Omar Hossain, Hamza Khan, Ahmed Sinjab, Jacob Tran");
        jLabel3.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        jLabel3.setForeground(TEXT_COLOR);
        jLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(jLabel1);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        titlePanel.add(jLabel2);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 3)));
        titlePanel.add(jLabel3);
        
        // Add some space between title and buttons
        titlePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Create button panel - centered
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        // Create styled buttons
        jButton1 = createStyledButton("Start New Game", 250);
        jButton2 = createStyledButton("Continue Game", 250);
        jButton3 = createStyledButton("Tutorial", 250);
        jButton4 = createStyledButton("Parental Controls", 250);
        jButton5 = createStyledButton("Exit", 250);
        
        // Set action listeners
        jButton3.addActionListener(e -> new TutorialScreen().setVisible(true));
        jButton4.addActionListener(e -> new ParentalControls().setVisible(true));
        jButton5.addActionListener(e -> {
            // Use the safe exit method to ensure play statistics are recorded
            UserInterface.safeExit();
        });
        
        // Center each button
        jButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton3.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton4.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton5.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add buttons to panel with spacing
        buttonPanel.add(jButton1);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(jButton2);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(jButton3);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(jButton4);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(jButton5);
        
        // Add panels to center panel
        centerPanel.add(Box.createVerticalGlue()); // Push everything down from top
        centerPanel.add(titlePanel);
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalGlue()); // Push everything up from bottom
        
        // Add center panel to main panel with some padding
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Add the main panel to the frame
        setContentPane(mainPanel);
        
        // Set minimum size
        setMinimumSize(new Dimension(800, 600));
        
        // Set default close operation to use our safe exit method
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                UserInterface.safeExit();
            }
        });
        
        // Add a component listener to handle resize events
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                // Scale fonts based on window size
                int width = getWidth();
                int height = getHeight();
                
                // Scale title font size (between 36 and 54)
                int titleSize = Math.max(36, Math.min(54, width / 20));
                jLabel1.setFont(new Font("Comic Sans MS", Font.BOLD, titleSize));
                
                // Scale subtitle font size
                int subtitleSize = Math.max(11, Math.min(14, width / 80));
                jLabel2.setFont(new Font("Comic Sans MS", Font.PLAIN, subtitleSize));
                jLabel3.setFont(new Font("Comic Sans MS", Font.PLAIN, subtitleSize));
                
                // Scale button size
                int buttonWidth = Math.max(220, Math.min(350, width / 4));
                int buttonHeight = Math.max(40, Math.min(60, height / 14));
                Dimension buttonDimension = new Dimension(buttonWidth, buttonHeight);
                
                jButton1.setPreferredSize(buttonDimension);
                jButton1.setMaximumSize(buttonDimension);
                jButton2.setPreferredSize(buttonDimension);
                jButton2.setMaximumSize(buttonDimension);
                jButton3.setPreferredSize(buttonDimension);
                jButton3.setMaximumSize(buttonDimension);
                jButton4.setPreferredSize(buttonDimension);
                jButton4.setMaximumSize(buttonDimension);
                jButton5.setPreferredSize(buttonDimension);
                jButton5.setMaximumSize(buttonDimension);
                
                // Update button font size
                int buttonFontSize = Math.max(14, Math.min(20, width / 60));
                updateButtonFonts(buttonFontSize);
                
                revalidate();
                repaint();
            }
        });
        
        pack();
    }
    
    // Update all button fonts
    private void updateButtonFonts(int size) {
        jButton1.setFont(new Font("Comic Sans MS", Font.BOLD, size));
        jButton2.setFont(new Font("Comic Sans MS", Font.BOLD, size));
        jButton3.setFont(new Font("Comic Sans MS", Font.BOLD, size));
        jButton4.setFont(new Font("Comic Sans MS", Font.BOLD, size));
        jButton5.setFont(new Font("Comic Sans MS", Font.BOLD, size));
    }
    
    // Create a styled button that matches the theme
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
        button.setPreferredSize(new Dimension(width, 50));
        button.setMaximumSize(new Dimension(width, 50));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Font currentFont = button.getFont();
                button.setFont(new Font(currentFont.getName(), Font.BOLD, currentFont.getSize() + 1));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Font currentFont = button.getFont();
                button.setFont(new Font(currentFont.getName(), Font.BOLD, currentFont.getSize() - 1));
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        return button;
    }

    // Getter methods
    public JButton getStartNewGameButton() {
        return jButton1;
    }
    
    public JButton getContinueGameButton() {
        return jButton2;
    }
    
    public JButton getTutorialButton() {
        return jButton3;
    }
    
    public JButton getParentalControlsButton() {
        return jButton4;
    }
    
    public JButton getExitButton() {
        return jButton5;
    }

    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;

    // Background panel class to set custom background image
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