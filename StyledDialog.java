import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * StyledDialog - A modernized custom dialog with enhanced visual styling
 * Creates professional notification pop-ups with a clean, cohesive design
 * that matches the game's visual identity
 */
public class StyledDialog extends JDialog {

    // Modern color palette based on green theme for standard dialogs
    private final Color PRIMARY_GREEN = new Color(76, 143, 62);      // Main green
    private final Color DARK_GREEN = new Color(52, 99, 43);          // For text and accents
    private final Color LIGHT_GREEN = new Color(200, 230, 190);      // For highlights
    private final Color BACKGROUND_START = new Color(235, 244, 230); // Gradient start
    private final Color BACKGROUND_END = new Color(213, 232, 205);   // Gradient end
    private final Color BUTTON_HOVER = new Color(133, 187, 101);     // Button hover state
    private final Color TRANSPARENT = new Color(0, 0, 0, 0);         // Transparent color
    private final Color WARNING_COLOR = new Color(230, 162, 60);     // Amber warning color
    
    // Warm color palette for input dialog (matches pet selection)
    private final Color DIALOG_BACKGROUND = new Color(237, 227, 202);
    private final Color DIALOG_BORDER = new Color(166, 124, 82);
    private final Color TITLE_COLOR = new Color(121, 85, 61);
    private final Color TEXT_COLOR = new Color(121, 85, 61);
    private final Color BUTTON_BACKGROUND = new Color(203, 152, 101);
    private final Color BUTTON_HOVER_WARM = new Color(232, 180, 128);
    private final Color BUTTON_TEXT = new Color(255, 255, 255);
    private final Color FIELD_BACKGROUND = new Color(247, 237, 212);
    private final Color FIELD_BORDER = new Color(186, 144, 102);
    
    // Dialog types
    public static final int INFORMATION = 0;
    public static final int ERROR = 1;
    public static final int CONFIRM = 2;
    public static final int WARNING = 3;
    public static final int INPUT = 4;
    
    private int result = JOptionPane.CLOSED_OPTION;
    private JTextField inputField;
    private String inputResult = null;

    /**
     * Creates a custom styled dialog
     * 
     * @param parent Parent component (can be null)
     * @param title Dialog title
     * @param message Message to display
     * @param type Dialog type (INFORMATION, ERROR, WARNING, or CONFIRM)
     */
    public StyledDialog(Component parent, String title, String message, int type) {
        // Handle the case where parent is null - use a default owner frame
        super(parent == null ? new JFrame() : SwingUtilities.getWindowAncestor(parent), title, Dialog.ModalityType.APPLICATION_MODAL);
        
        if (type == INPUT) {
            createInputDialog(parent, title, message, null);
        } else {
            createStandardDialog(parent, title, message, type);
        }
    }
    
    /**
     * Creates an input dialog with a text field
     */
    private void createInputDialog(Component parent, String title, String message, String defaultValue) {
        // Configure dialog
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 400, 200, 15, 15));
        
        // Main panel with warm background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fill with background color
                g2d.setColor(DIALOG_BACKGROUND);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Draw border
                g2d.setColor(DIALOG_BORDER);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                g2d.dispose();
            }
        };
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Title at top
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Close button in corner
        JLabel closeButton = new JLabel("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setForeground(TITLE_COLOR);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inputResult = null;
                dispose();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setForeground(new Color(180, 40, 40));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setForeground(TITLE_COLOR);
            }
        });
        
        // Title panel with close button
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(closeButton, BorderLayout.EAST);
        panel.add(titlePanel, BorderLayout.NORTH);
        
        // Message label
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_COLOR);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        // Input field - styled to match game aesthetics
        inputField = new JTextField(defaultValue) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fill background
                g2d.setColor(FIELD_BACKGROUND);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Draw border
                g2d.setColor(FIELD_BORDER);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                
                g2d.dispose();
                
                super.paintComponent(g);
            }
        };
        inputField.setOpaque(false);
        inputField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setForeground(TEXT_COLOR);
        inputField.setPreferredSize(new Dimension(300, 30));
        
        // Center panel with message and input field
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(messageLabel);
        
        JPanel fieldPanel = new JPanel();
        fieldPanel.setOpaque(false);
        fieldPanel.add(inputField);
        centerPanel.add(fieldPanel);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        
        // OK button
        JButton okButton = createWarmStyledButton("Save Game", 120);
        okButton.addActionListener(e -> {
            inputResult = inputField.getText();
            dispose();
        });
        
        // Cancel button
        JButton cancelButton = createWarmStyledButton("Cancel", 120);
        cancelButton.addActionListener(e -> {
            inputResult = null;
            dispose();
        });
        
        buttonPanel.add(okButton);
        buttonPanel.add(Box.createHorizontalStrut(20)); // Add space between buttons
        buttonPanel.add(cancelButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(panel);
        
        // Handle Enter key as OK and Escape as Cancel
        getRootPane().setDefaultButton(okButton);
        
        // Select all text in the field by default
        inputField.addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override
            public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                inputField.selectAll();
                inputField.requestFocusInWindow();
            }
            
            @Override
            public void ancestorRemoved(javax.swing.event.AncestorEvent e) {}
            
            @Override
            public void ancestorMoved(javax.swing.event.AncestorEvent e) {}
        });
    }
    
    /**
     * Creates a standard info/error/warning/confirm dialog
     */
    private void createStandardDialog(Component parent, String title, String message, int type) {
        // Configure dialog
        setSize(420, 230);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 420, 230, 15, 15));
        
        // Main panel with enhanced background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Create a smooth gradient background
                GradientPaint gp = new GradientPaint(
                    0, 0, BACKGROUND_START,
                    0, getHeight(), BACKGROUND_END
                );
                
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Draw a subtle border
                g2d.setColor(PRIMARY_GREEN);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                // Add subtle decoration
                g2d.setColor(new Color(255, 255, 255, 60));
                g2d.fillRoundRect(15, 15, getWidth() - 30, 45, 10, 10);
                
                // Add a subtle accent line
                g2d.setColor(LIGHT_GREEN);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawLine(40, 75, getWidth() - 40, 75);
                
                g2d.dispose();
            }
        };
        panel.setLayout(null);
        
        // Add close button in corner
        JLabel closeButton = new JLabel("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setForeground(DARK_GREEN);
        closeButton.setBounds(390, 8, 20, 20);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setForeground(new Color(180, 40, 40));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setForeground(DARK_GREEN);
            }
        });
        panel.add(closeButton);
        
        // Add title with enhanced styling (centered)
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(DARK_GREEN);
        titleLabel.setBounds(20, 20, 380, 30);
        panel.add(titleLabel);
        
        // Create the appropriate icon based on dialog type
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int size = 40;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                
                switch (type) {
                    case ERROR:
                        // Error icon (red circle with white X)
                        g2d.setColor(new Color(220, 80, 80));
                        g2d.fillOval(x, y, size, size);
                        g2d.setColor(Color.WHITE);
                        g2d.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2d.drawLine(x + 12, y + 12, x + size - 12, y + size - 12);
                        g2d.drawLine(x + size - 12, y + 12, x + 12, y + size - 12);
                        break;
                        
                    case INFORMATION:
                        // Info icon (blue circle with white i)
                        g2d.setColor(new Color(70, 130, 180));
                        g2d.fillOval(x, y, size, size);
                        g2d.setColor(Color.WHITE);
                        g2d.setFont(new Font("Segoe UI", Font.BOLD, 25));
                        g2d.drawString("i", x + 17, y + 28);
                        break;
                        
                    case WARNING:
                        // Warning icon (amber triangle with exclamation mark)
                        g2d.setColor(WARNING_COLOR);
                        int[] xPoints = {x + size/2, x + size, x};
                        int[] yPoints = {y, y + size, y + size};
                        g2d.fillPolygon(xPoints, yPoints, 3);
                        g2d.setColor(Color.WHITE);
                        g2d.setFont(new Font("Segoe UI", Font.BOLD, 25));
                        g2d.drawString("!", x + 17, y + 32);
                        break;
                        
                    case CONFIRM:
                        // Question icon (green circle with white ?)
                        g2d.setColor(PRIMARY_GREEN);
                        g2d.fillOval(x, y, size, size);
                        g2d.setColor(Color.WHITE);
                        g2d.setFont(new Font("Segoe UI", Font.BOLD, 24));
                        g2d.drawString("?", x + 14, y + 29);
                        break;
                }
                
                g2d.dispose();
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setBounds(20, 80, 60, 60);
        panel.add(iconPanel);
        
        // Create content panel with improved styling
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fill with semi-transparent background
                g2d.setColor(new Color(255, 255, 255, 180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Draw subtle border
                g2d.setColor(new Color(PRIMARY_GREEN.getRed(), PRIMARY_GREEN.getGreen(), PRIMARY_GREEN.getBlue(), 100));
                g2d.setStroke(new BasicStroke(1f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                
                g2d.dispose();
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBounds(90, 85, 300, 70);
        
        // Add message with improved font
        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageArea.setForeground(new Color(50, 50, 50));
        messageArea.setBackground(TRANSPARENT);
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        contentPanel.add(messageArea, BorderLayout.CENTER);
        
        panel.add(contentPanel);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(0, 170, 420, 50);
        
        if (type == CONFIRM) {
            // Create Yes/No buttons for confirmation
            JButton yesButton = createStyledButton("Yes", 120);
            yesButton.addActionListener(e -> {
                result = JOptionPane.YES_OPTION;
                dispose();
            });
            
            JButton noButton = createStyledButton("No", 120);
            noButton.addActionListener(e -> {
                result = JOptionPane.NO_OPTION;
                dispose();
            });
            
            buttonPanel.add(yesButton);
            buttonPanel.add(noButton);
        } else {
            // Create OK button for info/error/warning dialogs
            JButton okButton = createStyledButton("OK", 120);
            okButton.addActionListener(e -> dispose());
            buttonPanel.add(okButton);
        }
        
        panel.add(buttonPanel);
        setContentPane(panel);
        
        // Add a shadow effect when displayed
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                // Reset the opacity to create a fade-in effect
                setOpacity(0.0f);
                
                // Create a timer to gradually increase opacity
                Timer timer = new Timer(20, evt -> {
                    float currentOpacity = getOpacity();
                    if (currentOpacity < 1.0f) {
                        setOpacity(Math.min(currentOpacity + 0.1f, 1.0f));
                    } else {
                        ((Timer)evt.getSource()).stop();
                    }
                });
                timer.start();
            }
        });
    }
    
    /**
     * Creates a modern styled button with hover effects (green style)
     */
    private JButton createStyledButton(String text, int width) {
        JButton button = new JButton(text) {
            private boolean hover = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Button color based on hover state
                Color bgColor = hover ? BUTTON_HOVER : PRIMARY_GREEN;
                
                // Fill button background
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Add subtle highlight at top for 3D effect
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight()/2, 8, 8);
                
                // Text will be white
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();
                
                // Center the text
                g2d.drawString(text, 
                    (getWidth() - textWidth) / 2, 
                    (getHeight() - textHeight) / 2 + fm.getAscent());
                
                g2d.dispose();
            }
            
            // Don't use UI delegate's painting
            @Override
            public void setText(String text) {
                super.setText(text);
                repaint();
            }
        };
        
        // Make button transparent for our custom painting
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setForeground(Color.WHITE); // Not used but set for accessibility
        
        // Style the text - using Segoe UI for a more modern look
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Set preferred size
        button.setPreferredSize(new Dimension(width, 36));
        
        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.putClientProperty("hover", true);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btn.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.putClientProperty("hover", false);
                btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btn.repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setLocation(btn.getX(), btn.getY() + 1); // Small "press" effect
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setLocation(btn.getX(), btn.getY() - 1); // Restore position
            }
        });
        
        return button;
    }
    
    /**
     * Creates a warm-styled button (brown/tan style) for input dialogs
     */
    private JButton createWarmStyledButton(String text, int width) {
        JButton button = new JButton(text) {
            private boolean hover = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Button color based on hover state
                Color bgColor = hover ? BUTTON_HOVER_WARM : BUTTON_BACKGROUND;
                
                // Fill button background
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Draw subtle border
                g2d.setColor(DIALOG_BORDER);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                
                // Text will be white
                g2d.setColor(BUTTON_TEXT);
                g2d.setFont(getFont());
                
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();
                
                // Center the text
                g2d.drawString(text, 
                    (getWidth() - textWidth) / 2, 
                    (getHeight() - textHeight) / 2 + fm.getAscent());
                
                g2d.dispose();
            }
        };
        
        // Make button transparent for our custom painting
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setForeground(BUTTON_TEXT);
        
        // Style the text
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Set preferred size
        button.setPreferredSize(new Dimension(width, 36));
        
        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.putClientProperty("hover", true);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btn.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.putClientProperty("hover", false);
                btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btn.repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setLocation(btn.getX(), btn.getY() + 1); // Small "press" effect
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setLocation(btn.getX(), btn.getY() - 1); // Restore position
            }
        });
        
        return button;
    }
    
    /**
     * Shows an information dialog with custom styling
     * 
     * @param parent Parent component
     * @param message Message to display
     * @param title Dialog title
     */
    public static void showInformationDialog(Component parent, String message, String title) {
        StyledDialog dialog = new StyledDialog(parent, title, message, INFORMATION);
        dialog.setVisible(true);
    }
    
    /**
     * Shows an error dialog with custom styling
     * 
     * @param parent Parent component
     * @param message Message to display
     * @param title Dialog title
     */
    public static void showErrorDialog(Component parent, String message, String title) {
        StyledDialog dialog = new StyledDialog(parent, title, message, ERROR);
        dialog.setVisible(true);
    }
    
    /**
     * Shows a warning dialog with custom styling
     * 
     * @param parent Parent component
     * @param message Message to display
     * @param title Dialog title
     */
    public static void showWarningDialog(Component parent, String message, String title) {
        StyledDialog dialog = new StyledDialog(parent, title, message, WARNING);
        dialog.setVisible(true);
    }
    
    /**
     * Shows a confirmation dialog with custom styling
     * 
     * @param parent Parent component
     * @param message Message to display
     * @param title Dialog title
     * @return JOptionPane.YES_OPTION or JOptionPane.NO_OPTION
     */
    public static int showConfirmDialog(Component parent, String message, String title) {
        StyledDialog dialog = new StyledDialog(parent, title, message, CONFIRM);
        dialog.setVisible(true);
        return dialog.result;
    }
    
    /**
     * Shows an input dialog and returns the entered text, or null if canceled
     * 
     * @param parent Parent component
     * @param message Message to display
     * @param title Dialog title
     * @param defaultValue Default value for the input field (can be null)
     * @return The entered text, or null if canceled
     */
    public static String showInputDialog(Component parent, String message, String title, String defaultValue) {
        StyledDialog dialog = new StyledDialog(parent, title, message, INPUT);
        if (defaultValue != null) {
            dialog.inputField.setText(defaultValue);
        }
        dialog.setVisible(true);
        return dialog.inputResult;
    }
}