import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
/**
 * This Class is responsible for displaying a stylish warning dialog when a pet's stat is low.
 * 
 * @author Omar Hossain
 */
public class LowStatWarning {
    // Singleton pattern to prevent multiple warning instances
    private static LowStatWarning instance;
    
    // Track last warning time for each stat type
    private Map<String, Long> lastWarningTimes;
    
    // Cooldown period for warnings (1 minute)
    private static final long WARNING_COOLDOWN = 60000; 

    private LowStatWarning() {
        // Initialize the map to track warning times for different stats
        lastWarningTimes = new HashMap<>();
    }

    public static synchronized LowStatWarning getInstance() {
        if (instance == null) {
            instance = new LowStatWarning();
        }
        return instance;
    }

    /**
     * Shows a stylish warning dialog for low stats
     * @param parentComponent Parent component for dialog positioning
     * @param statName Name of the stat (e.g., "Sleep", "Hunger")
     * @param currentValue Current stat value
     * @param maxValue Maximum stat value
     * @return true if warning was shown, false if cooldown was active
     */
    public boolean showWarning(Component parentComponent, String statName, double currentValue, double maxValue) {
        long currentTime = System.currentTimeMillis();
        
        // Check if enough time has passed since the last warning for this specific stat
        Long lastWarningTime = lastWarningTimes.get(statName);
        if (lastWarningTime != null && currentTime - lastWarningTime < WARNING_COOLDOWN) {
            return false;
        }

        // Calculate percentage
        double percentage = (currentValue / maxValue) * 100;
        
        // Only show warning if stat is below 25%
        if (percentage >= 25) {
            return false;
        }

        // Update last warning time for this stat
        lastWarningTimes.put(statName, currentTime);

        // Create a custom dialog
        JDialog dialog = new JDialog((Frame)null, "Low " + statName + " Warning", true);
        dialog.setLayout(new BorderLayout(10, 10));
        
        // Warning icon
        ImageIcon warningIcon = new ImageIcon("assets\\images\\uiElements\\warning_icon.png");
        Image scaledImage = warningIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        
        // Warning message
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" +
            "Your pet's " + statName.toLowerCase() + " is very low!<br>" +
            "Current: " + String.format("%.1f", currentValue) + " (" + String.format("%.1f", percentage) + "%)<br>" +
            "Recommendation: Take action to restore " + statName.toLowerCase() + ".</div></html>");
        messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Action button
        JButton actionButton = new JButton("Okay, Got It");
        actionButton.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        actionButton.setBackground(new Color(217, 188, 133)); // Warm beige color
        actionButton.setForeground(new Color(90, 62, 44)); // Brown text color
        actionButton.addActionListener(e -> dialog.dispose());
        
        // Panel for styling
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPanel.setBackground(new Color(255, 245, 220)); // Light cream background
        
        // Add components
        contentPanel.add(iconLabel, BorderLayout.WEST);
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(actionButton, BorderLayout.SOUTH);
        
        dialog.add(contentPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
        
        return true;
    }
}