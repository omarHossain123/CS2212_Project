import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Settings dialog that provides options to resume game, save game,
 * return to main menu, or exit the application.
 *
 * @author Jacob
 */
public class settings extends javax.swing.JFrame {
    // Define warm, cozy color scheme to match main game
    private final Color PANEL_BACKGROUND = new Color(230, 238, 213); // Soft green background
    private final Color PANEL_GRADIENT_END = new Color(242, 245, 233); // Lighter green for gradient
    private final Color BUTTON_COLOR = new Color(217, 188, 133);     // Warm beige
    private final Color BUTTON_HOVER = new Color(227, 205, 159);     // Lighter beige for hover
    private final Color TEXT_COLOR = new Color(90, 62, 44);          // Brown text
    private final Color BORDER_COLOR = new Color(164, 116, 73);      // Brown border
    private final Color TITLE_BACKGROUND = new Color(164, 116, 73, 180); // Semi-transparent brown
    
    private mainGame parentGame;

    /**
     * Creates new form settings
     * @param parent The parent frame to center this window on
     */
    public settings(javax.swing.JFrame parent) {
        this.parentGame = (mainGame) parent;
        
        // Set frame properties
        setUndecorated(true);
        setSize(320, 450); // Make it slightly larger for better spacing
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        // Create custom UI
        createUI();
        
        // Setup key bindings
        setupKeyBindings();
        
        // Center the window relative to parent
        if (parent != null) {
            setLocationRelativeTo(parent);
        }
    }
    
    /**
     * Creates the custom UI for the settings window
     */
    private void createUI() {
        // Create main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create a gradient from top to bottom
                GradientPaint gradient = new GradientPaint(
                    0, 0, PANEL_BACKGROUND,
                    0, getHeight(), PANEL_GRADIENT_END);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Create title panel
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(TITLE_BACKGROUND);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Title label
        JLabel titleLabel = new JLabel("Game Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);
        
        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        
        // Create buttons with consistent styling
        JButton resumeButton = createStyledButton("Resume Game");
        resumeButton.addActionListener(e -> resumeActionPerformed(e));
        
        JButton saveButton = createStyledButton("Save Game");
        saveButton.addActionListener(e -> saveGameActionPerformed(e));
        
        JButton menuButton = createStyledButton("Main Menu");
        menuButton.addActionListener(e -> mainMenuActionPerformed(e));
        
        JButton exitButton = createStyledButton("Exit Game");
        exitButton.addActionListener(e -> exitActionPerformed(e));
        
        // Add buttons to panel with spacing
        buttonsPanel.add(resumeButton);
        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(saveButton);
        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(menuButton);
        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(exitButton);
        
        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        // Set content pane
        setContentPane(mainPanel);
    }
    
    /**
     * Creates a styled button with text
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setMaximumSize(new Dimension(250, 60));
        button.setPreferredSize(new Dimension(250, 60));
        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        
        // Create a rounded border with a wooden fence style
        button.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 2, true),
            new EmptyBorder(10, 20, 10, 20)
        ));
        
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
        
        return button;
    }
    
    /**
     * Sets up a key listener for the Escape key to close this window.
     */
    private void setupKeyBindings() {
        // Get the input map and action map for the content pane
        JComponent contentPane = (JComponent) getContentPane();
        InputMap inputMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = contentPane.getActionMap();
        
        Action escAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeActionPerformed(null); // Close window on ESC
            }
        };
        
        // Bind the ESC key to the action
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escAction");
        actionMap.put("escAction", escAction);
    }

    private void resumeActionPerformed(java.awt.event.ActionEvent evt) {
        // Close the settings window
        dispose();
    }

    private void saveGameActionPerformed(java.awt.event.ActionEvent evt) {
        // Call the save game function from UserInterface
        UserInterface.saveCurrentGame();
    }

    private void mainMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // Save the current game before returning to main menu
        UserInterface.saveCurrentGame();
        
        // Close the current game window
        if (parentGame != null) {
            parentGame.dispose();
        }
        
        // Close the settings window
        dispose();
        
        // Use the UserInterface method to show the main menu with proper listeners
        UserInterface.showMainMenu();
    }

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {
        // Ask for confirmation before exiting
        int confirmed = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to exit the game?",
            "Exit Game",
            JOptionPane.YES_NO_OPTION);
            
        if (confirmed == JOptionPane.YES_OPTION) {
            // Save the game first
            UserInterface.saveCurrentGame();
            
            // Then exit properly using the safeExit method
            UserInterface.safeExit();
        }
    }
}