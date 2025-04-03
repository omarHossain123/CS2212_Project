import java.awt.*;
import javax.swing.*;

public class CommandRestrictionPopup {
    // Warm, cozy color scheme to match main game
    private static final Color PANEL_BACKGROUND = new Color(230, 238, 213); // Soft green background
    private static final Color BUTTON_COLOR = new Color(217, 188, 133);     // Warm beige
    private static final Color TEXT_COLOR = new Color(90, 62, 44);          // Brown text
    private static final Color BORDER_COLOR = new Color(164, 116, 73);      // Brown border

    /**
     * Shows a stylish warning dialog for restricted commands
     * @param parentComponent Parent component for dialog positioning
     * @param stateName Name of the current pet state
     * @param actionName Name of the action attempted
     */
    public static void showRestrictionWarning(Component parentComponent, String stateName, String actionName) {
        // Create a custom dialog
        JDialog dialog = new JDialog((Frame)null, "Action Restricted", true);
        dialog.setLayout(new BorderLayout(10, 10));
        
        // Warning icon
        ImageIcon warningIcon = new ImageIcon("assets\\images\\uiElements\\warning_icon.png");
        Image scaledImage = warningIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        
        // Warning message
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" +
            "Cannot " + actionName + " right now.<br>" +
            "Your pet is currently in <b>" + stateName + "</b> state.</div></html>");
        messageLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_COLOR);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Action button
        JButton actionButton = new JButton("Okay, Got It");
        actionButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        actionButton.setForeground(TEXT_COLOR);
        actionButton.setBackground(BUTTON_COLOR);
        actionButton.setFocusPainted(false);
        actionButton.setBorderPainted(true);
        actionButton.setContentAreaFilled(true);
        actionButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 2, true),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        actionButton.addActionListener(e -> dialog.dispose());
        
        // Panel for styling
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPanel.setBackground(PANEL_BACKGROUND);
        
        // Add components
        contentPanel.add(iconLabel, BorderLayout.WEST);
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(actionButton, BorderLayout.SOUTH);
        
        dialog.add(contentPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
    }
}