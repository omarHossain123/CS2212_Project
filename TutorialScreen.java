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
        "Overview", "Health", "Sleep", "Fullness", "Happiness"
    };
    
    private final String[] content = {
        "Welcome to the Virtual Pet Game! This tutorial will guide you through the basics of the game. Use the arrow buttons to navigate through the pages.\n\nIn this game, you'll need to manage your pet's health, happiness, hunger, and sleep levels while taking care of its needs.",
        "Health represents your pet's physical well-being. It decreases when your pet gets sick or injured, and increases when it rests or visits the vet.\n\nIf your pet's health reaches zero, it will die, and the game will be over.",
        "Sleep is essential for your pet. As it performs activities, it gets tired. Make sure to put your pet to bed regularly to keep it healthy and happy.\n\nYou can let your pet sleep anywhere, but some areas may help it recover faster.",
        "Fullness represents your pet's hunger. If it's too hungry, its health will begin to decline.\n\nYou can feed your pet from your inventory of food items. Different food items affect fullness differently, so choose wisely!",
        "Happiness affects your pet's mood and ability to interact. Keep your pet happy by playing with it, giving gifts, and completing its needs.\n\nA happy pet will perform better, and a sad pet will become less responsive to your actions."
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
