import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CommandCooldownPopup {
    // Warm, cozy color scheme to match main game
    private static final Color PANEL_BACKGROUND = new Color(230, 238, 213); // Soft green background
    private static final Color BUTTON_COLOR = new Color(217, 188, 133);     // Warm beige
    private static final Color TEXT_COLOR = new Color(90, 62, 44);          // Brown text
    private static final Color BORDER_COLOR = new Color(164, 116, 73);      // Brown border

    /**
     * Shows a cooldown warning dialog with a countdown timer
     * @param parentComponent Parent component for dialog positioning
     * @param actionName Name of the action on cooldown
     * @param remainingTime Remaining cooldown time in milliseconds
     * @param totalCooldownTime Total cooldown time in milliseconds
     */
    public static void showCooldownWarning(Component parentComponent, String actionName, long remainingTime, long totalCooldownTime) {
        // Create a custom dialog
        JDialog dialog = new JDialog((Frame)null, actionName + " Cooldown", true);
        dialog.setLayout(new BorderLayout(10, 10));
        
        // Warning icon
        ImageIcon warningIcon = new ImageIcon("assets\\images\\uiElements\\warning_icon.png");
        Image scaledImage = warningIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        
        // Cooldown message
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" +
            "Cannot perform <b>" + actionName + "</b> right now.<br>" +
            "Please wait for the cooldown to finish.</div></html>");
        messageLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_COLOR);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Countdown label with initial text
        JLabel countdownLabel = new JLabel(String.format("Time remaining: %.1f seconds", remainingTime / 1000.0));
        countdownLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        countdownLabel.setForeground(TEXT_COLOR);
        countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
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
        
        // Panel for styling
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPanel.setBackground(PANEL_BACKGROUND);
        
        // Add components
        contentPanel.add(iconLabel, BorderLayout.WEST);
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(countdownLabel, BorderLayout.SOUTH);
        
        dialog.add(contentPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parentComponent);
        
        // Create a timer to update the countdown
        Timer countdownTimer = new Timer(100, new ActionListener() {
            long startTime = System.currentTimeMillis();
            
            @Override
            public void actionPerformed(ActionEvent e) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;
                long remainingTimeNow = Math.max(0, remainingTime - elapsedTime);
                
                // Update countdown text
                countdownLabel.setText(String.format("Time remaining: %.1f seconds", remainingTimeNow / 1000.0));
                
                // Close dialog when cooldown is over
                if (remainingTimeNow <= 0) {
                    ((Timer)e.getSource()).stop();
                    actionButton.setText("Cooldown Finished");
                    countdownLabel.setText("Cooldown Complete!");
                }
            }
        });
        
        // Add action listener to close button
        actionButton.addActionListener(e -> {
            dialog.dispose();
            countdownTimer.stop();
        });
        
        // Add button to panel
        contentPanel.add(actionButton, BorderLayout.EAST);
        
        // Show dialog and start timer
        dialog.pack(); // Repack to ensure new content is visible
        dialog.setVisible(true);
        countdownTimer.start();
    }
}