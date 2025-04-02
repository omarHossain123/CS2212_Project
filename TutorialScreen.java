import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * TutorialScreen - A multi-page tutorial walkthrough with styled navigation buttons
 * that matches the visual style of the main menu.
 */
public class TutorialScreen extends JDialog {
    
    // Color scheme matching main menu
    private final Color TITLE_COLOR = new Color(75, 91, 22); // Dark green
    private final Color TEXT_COLOR = new Color(58, 71, 15);  // Darker green for content
    private final Color BUTTON_TEXT = new Color(70, 86, 20); // Dark green
    private final Color BUTTON_BORDER = new Color(139, 87, 42); // Brown color for button borders
    private final Color CONTENT_BG = new Color(255, 255, 240, 200); // Semi-transparent background
    
    private final String[] titles = {
        "Welcome to Virtual Pet!", 
        "Game Basics", 
        "Pet Stats", 
        "Actions Menu", 
        "Inventory & Shop", 
        "Scoring & Survival"
    };
    
    private final String[] content = {
        "Welcome to the Virtual Pet Game! Your goal is to care for and nurture a virtual pet, keeping it healthy, happy, and alive as long as possible.\n\n" +
        "This game is all about balancing your pet's needs through various actions and managing its overall well-being.",
        
        "Navigate the game using different buttons:\n" +
        "• Commands Button: Open the action menu to interact with your pet\n" +
        "• Settings Button: Access game settings and options\n" +
        "• Shop Button: Buy items to help care for your pet\n\n" +
        "Your pet will react to your actions and have different states based on its current condition.",
        
        "Your pet has four key stats that you must manage:\n" +
        "1. Health: Represents physical well-being\n" +
        "   - Decreases when pet is sick or tired\n" +
        "   - Can be restored by resting or visiting the vet\n" +
        "   - If health reaches zero, the game ends\n\n" +
        "2. Hunger: Indicates how full your pet is\n" +
        "   - Decreases over time\n" +
        "   - Feed your pet to keep hunger levels up\n\n" +
        "3. Happiness: Reflects your pet's mood\n" +
        "   - Increases by playing and giving gifts\n" +
        "   - Decreases if needs are not met\n\n" +
        "4. Sleep: Shows how rested your pet is\n" +
        "   - Depletes with activities\n" +
        "   - Put your pet to bed to restore energy",
        
        "The Commands Menu allows you to interact with your pet:\n" +
        "• Feeding: Choose from different food items\n" +
        "   - Select food from your inventory\n" +
        "   - Each food affects hunger differently\n\n" +
        "• Gifts: Give items to increase happiness\n" +
        "   - Choose from various gift options\n\n" +
        "• Activities: Keep your pet active\n" +
        "   - Play with your pet\n" +
        "   - Take your pet for a walk\n\n" +
        "• Health & Rest:\n" +
        "   - Put your pet to sleep\n" +
        "   - Visit the vet to restore health",
        
        "Inventory & Shop Features:\n" +
        "• Shop allows you to purchase:\n" +
        "   - Food items\n" +
        "   - Gifts\n" +
        "   - Other special items\n\n" +
        "• Manage your inventory\n" +
        "   - Limited number of items\n" +
        "   - Choose wisely what to buy\n\n" +
        "• Spending depletes your score\n" +
        "   - Earn score by keeping your pet healthy\n" +
        "   - Budget carefully!",
        
        "Scoring and Survival:\n" +
        "• Score increases over time\n" +
        "   - Starts at zero\n" +
        "   - Increases as you keep your pet alive and happy\n\n" +
        "• Game Over Conditions:\n" +
        "   - Health reaches zero\n" +
        "   - Pet becomes too unhappy\n\n" +
        "• Tips for Long-Term Survival:\n" +
        "   - Balance all four key stats\n" +
        "   - Regular care and attention\n" +
        "   - Use shop items strategically\n" +
        "   - Respond quickly to your pet's needs"
    };
    
    private int page = 0;
    private JLabel titleLabel;
    private JTextArea contentArea;
    private JButton backButton, nextButton;
    
    public TutorialScreen() {
        this(null);
    }
    
    public TutorialScreen(JFrame parent) {
        super(parent, "Tutorial", true); // true makes it modal
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true); // Remove window decorations for popup feel
        
        // Create custom shaped window with rounded corners
        setShape(new RoundRectangle2D.Double(0, 0, 700, 500, 20, 20));
        
        // Set up the content pane with custom background
        TutorialBackgroundPanel backgroundPanel = new TutorialBackgroundPanel();
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(null);
        
        // Title label
        titleLabel = new JLabel(titles[page], SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setBounds(0, 20, 700, 40);
        backgroundPanel.add(titleLabel);
        
        // Content panel with styled background
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fill with semi-transparent background
                g2.setColor(CONTENT_BG);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
                
                // Draw border
                g2.setColor(BUTTON_BORDER);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setBounds(100, 80, 500, 300);
        contentPanel.setLayout(new BorderLayout());
        backgroundPanel.add(contentPanel);
        
        // Content area
        contentArea = new JTextArea(content[page]);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        contentArea.setEditable(false);
        contentArea.setOpaque(false);
        contentArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        contentArea.setForeground(TEXT_COLOR);
        contentArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Navigation buttons
        backButton = createCircleButton("<", 24);   
        backButton.setBounds(30, 200, 50, 50);
        backgroundPanel.add(backButton);
        
        nextButton = createCircleButton(">", 24);
        nextButton.setBounds(620, 200, 50, 50);
        backgroundPanel.add(nextButton);
        
        // Page indicator
        JLabel pageIndicator = new JLabel("Page 1 of " + titles.length, SwingConstants.CENTER);
        pageIndicator.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        pageIndicator.setForeground(TEXT_COLOR);
        pageIndicator.setBounds(250, 390, 200, 30);
        backgroundPanel.add(pageIndicator);
        
        // Create styled "Done" button matching main menu style
        JButton doneButton = new JButton("Done") {
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
                super.paintComponent(g);
            }
        };
        
        doneButton.setContentAreaFilled(false);
        doneButton.setBorderPainted(false);
        doneButton.setFocusPainted(false);
        doneButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        doneButton.setForeground(BUTTON_TEXT);
        doneButton.setBounds(300, 430, 100, 40);
        backgroundPanel.add(doneButton);
        
        // Button actions
        backButton.addActionListener(e -> {
            if (page > 0) {
                page--;
                updatePage();
                pageIndicator.setText("Page " + (page + 1) + " of " + titles.length);
            }
        });
        
        nextButton.addActionListener(e -> {
            if (page < titles.length - 1) {
                page++;
                updatePage();
                pageIndicator.setText("Page " + (page + 1) + " of " + titles.length);
            }
        });
        
        doneButton.addActionListener(e -> dispose());
    }
    
    private void updatePage() {
        titleLabel.setText(titles[page]);
        contentArea.setText(content[page]);
        contentArea.setCaretPosition(0); // Scroll to top
        
        // Enable/disable navigation buttons based on current page
        backButton.setEnabled(page > 0);
        nextButton.setEnabled(page < titles.length - 1);
    }
    
    private JButton createCircleButton(String text, int fontSize) {
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
                
                // Draw circle
                g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
                
                // Draw border
                g2.setColor(BUTTON_BORDER);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new Ellipse2D.Double(0, 0, getWidth() - 1, getHeight() - 1));
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Comic Sans MS", Font.BOLD, fontSize));
        button.setForeground(BUTTON_TEXT);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        return button;
    }
    
    // Background panel with nature-themed background
    private class TutorialBackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D) g.create();
            
            // Create a gradient background that matches the nature theme
            GradientPaint gp = new GradientPaint(
                0, 0, new Color(180, 210, 140), // Light green
                0, getHeight(), new Color(120, 160, 90) // Darker green
            );
            
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // Draw some simple nature elements
            // Grass on bottom
            g2d.setColor(new Color(100, 140, 70));
            for (int i = 0; i < getWidth(); i += 20) {
                int height = 5 + (int)(Math.random() * 10);
                g2d.fillRect(i, getHeight() - height, 10, height);
            }
            
            // Sun in corner
            g2d.setColor(new Color(255, 220, 130, 120));
            g2d.fillOval(-50, -50, 150, 150);
            
            // Subtle cloud shapes
            g2d.setColor(new Color(255, 255, 255, 40));
            g2d.fillOval(500, 50, 150, 60);
            g2d.fillOval(450, 40, 100, 50);
            g2d.fillOval(520, 30, 120, 70);
            
            g2d.dispose();
        }
    }
}
