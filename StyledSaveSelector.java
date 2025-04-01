import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 * StyledSaveSelector - A stylish dialog for selecting saved games
 * Uses a dropdown menu to clearly display numbered save files
 */
public class StyledSaveSelector extends JDialog {
    
    // Warm color palette (matches pet selection and other dialogs)
    private final Color DIALOG_BACKGROUND = new Color(237, 227, 202);
    private final Color DIALOG_BORDER = new Color(166, 124, 82); 
    private final Color TITLE_COLOR = new Color(121, 85, 61);
    private final Color TEXT_COLOR = new Color(121, 85, 61);
    private final Color BUTTON_BACKGROUND = new Color(203, 152, 101);
    private final Color BUTTON_HOVER = new Color(232, 180, 128);
    private final Color BUTTON_TEXT = new Color(255, 255, 255);
    private final Color LIST_BACKGROUND = new Color(247, 237, 212);
    private final Color LIST_BORDER = new Color(186, 144, 102);
    private final Color LIST_SELECTION = new Color(213, 172, 129);
    private final Color LIST_HIGHLIGHT = new Color(232, 212, 185);
    
    private String selectedSave = null;
    private List<String> originalSaveNames;
    private JComboBox<String> comboBox;
    
    /**
     * Creates a custom styled save selector dialog with dropdown
     * 
     * @param parent Parent component (can be null)
     * @param title Dialog title
     * @param message Message to display
     * @param saveFiles List of save file names to display
     */
    public StyledSaveSelector(Component parent, String title, String message, List<String> saveFiles) {
        // Handle the case where parent is null - use a default owner frame
        super(parent == null ? new JFrame() : SwingUtilities.getWindowAncestor(parent), 
              title, Dialog.ModalityType.APPLICATION_MODAL);
        
        this.originalSaveNames = saveFiles;
        
        // Configure dialog
        setSize(420, 260); // Reduced height since we're using a dropdown
        setLocationRelativeTo(parent);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 420, 260, 15, 15));
        
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
                selectedSave = null;
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
        
        // Prepare the display data for the dropdown (add numbers)
        List<String> displayData = new ArrayList<>();
        for (int i = 0; i < saveFiles.size(); i++) {
            displayData.add((i + 1) + ". " + saveFiles.get(i));
        }
        
        // Create styled ComboBox dropdown
        comboBox = new JComboBox<>(displayData.toArray(new String[0]));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                                                        int index, boolean isSelected, 
                                                        boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                
                if (isSelected) {
                    label.setBackground(LIST_SELECTION);
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(index % 2 == 0 ? LIST_BACKGROUND : LIST_HIGHLIGHT);
                    label.setForeground(TEXT_COLOR);
                }
                
                return label;
            }
        });

        // Apply the selection color directly to the UI as well
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(LIST_BACKGROUND);
        comboBox.setForeground(TEXT_COLOR);

        // Add these lines to override the selection colors used in the dropdown
        UIManager.put("ComboBox.selectionBackground", LIST_SELECTION);
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);

        // Custom UI for the combo box
        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton() {
                    @Override
                    public void paint(Graphics g) {
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        
                        // Fill button background
                        g2d.setColor(BUTTON_BACKGROUND);
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
                        
                        // Draw the down arrow
                        int size = 8;
                        int x = (getWidth() - size) / 2;
                        int y = (getHeight() - size / 2) / 2;
                        
                        g2d.setColor(Color.WHITE);
                        g2d.fillPolygon(
                            new int[] {x, x + size, x + size / 2},
                            new int[] {y, y, y + size / 2},
                            3
                        );
                        
                        g2d.dispose();
                    }
                };
                
                button.setBackground(BUTTON_BACKGROUND);
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                
                return button;
            }
        });
        
        // Style the combobox to match the game aesthetic
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIST_BORDER, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 5)
        ));
        
        // Create a panel to hold the combo box with proper spacing
        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.setOpaque(false);
        comboPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        comboPanel.add(comboBox, BorderLayout.CENTER);
        
        // Center panel with message and dropdown
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BorderLayout(0, 10));
        centerPanel.add(messageLabel, BorderLayout.NORTH);
        centerPanel.add(comboPanel, BorderLayout.CENTER);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        
        // Load button
        JButton loadButton = createStyledButton("Load Game", 120);
        loadButton.addActionListener(e -> {
            if (comboBox.getSelectedIndex() >= 0) {
                selectedSave = originalSaveNames.get(comboBox.getSelectedIndex());
                dispose();
            }
        });
        
        // Cancel button
        JButton cancelButton = createStyledButton("Cancel", 120);
        cancelButton.addActionListener(e -> {
            selectedSave = null;
            dispose();
        });
        
        buttonPanel.add(loadButton);
        buttonPanel.add(Box.createHorizontalStrut(20)); // Add space between buttons
        buttonPanel.add(cancelButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(panel);
        
        // Handle Enter key as OK
        getRootPane().setDefaultButton(loadButton);
        
        // Add a fade-in effect when displayed
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
     * Creates a styled button with hover effects
     */
    private JButton createStyledButton(String text, int width) {
        JButton button = new JButton(text) {
            private boolean hover = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Button color based on hover state
                Color bgColor = hover ? BUTTON_HOVER : BUTTON_BACKGROUND;
                
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
     * Shows a save selector dialog and returns the selected save name, or null if canceled
     * 
     * @param parent Parent component (can be null)
     * @param message Message to display
     * @param title Dialog title
     * @param saveFiles List of save file names to display
     * @return The selected save name, or null if canceled
     */
    public static String showDialog(Component parent, String message, String title, List<String> saveFiles) {
        if (saveFiles.isEmpty()) {
            return null;
        }
        
        StyledSaveSelector dialog = new StyledSaveSelector(parent, title, message, saveFiles);
        dialog.setVisible(true);
        return dialog.selectedSave;
    }
}