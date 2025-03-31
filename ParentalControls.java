import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;

/**
 * ParentalControls - Password screen followed by controls/settings screen.
 * Includes time limitations and gameplay statistics.
 */
public class ParentalControls extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static final String PASSWORD = "HelloWorld123$";
    
    // Parental control settings
    private boolean timeLimitEnabled = false;
    private LocalTime startTime = LocalTime.of(8, 0); // Default 8:00 AM
    private LocalTime endTime = LocalTime.of(20, 0);  // Default 8:00 PM
    private long totalPlayTime = 0; // in seconds
    private long averagePlayTime = 0; // in seconds
    private int sessionsCount = 0;
    
    private JCheckBox toggleLimit;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JLabel totalPlayTimeLabel;
    private JLabel avgPlayTimeLabel;
    
    private static final String CONTROLS_FILE = "saves/parental_controls.dat";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ParentalControls() {
        setTitle("Parental Controls");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load existing settings
        loadSettings();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createPasswordPanel(), "password");
        mainPanel.add(createSettingsPanel(), "settings");

        add(mainPanel);
        cardLayout.show(mainPanel, "password");
        
        // Add window listener to save settings on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveSettings();
            }
        });
    }

    private JPanel createPasswordPanel() {
        JPanel panel = new JPanel(null);

        JLabel label = new JLabel("Password:");
        label.setBounds(150, 100, 80, 30);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(230, 100, 200, 30);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(230, 150, 80, 30);
        exitButton.addActionListener(e -> dispose());

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(320, 150, 100, 30);
        confirmButton.addActionListener(e -> {
            String enteredPassword = new String(passwordField.getPassword());
            if (enteredPassword.equals(PASSWORD)) {
                cardLayout.show(mainPanel, "settings");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect Password", "Access Denied", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        });

        panel.add(label);
        panel.add(passwordField);
        panel.add(exitButton);
        panel.add(confirmButton);

        return panel;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(null);

        JLabel title = new JLabel("Parental Controls");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(30, 10, 200, 30);

        // Time limitations section
        JLabel timeLimitLabel = new JLabel("Time Limitations:");
        timeLimitLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timeLimitLabel.setBounds(30, 50, 150, 20);

        toggleLimit = new JCheckBox("Enable Time Limitations");
        toggleLimit.setBounds(30, 75, 200, 20);
        toggleLimit.setSelected(timeLimitEnabled);

        JLabel fromLabel = new JLabel("From:");
        fromLabel.setBounds(30, 100, 50, 25);
        startTimeField = new JTextField(startTime.format(TIME_FORMATTER));
        startTimeField.setBounds(80, 100, 60, 25);

        JLabel toLabel = new JLabel("To:");
        toLabel.setBounds(150, 100, 30, 25);
        endTimeField = new JTextField(endTime.format(TIME_FORMATTER));
        endTimeField.setBounds(180, 100, 60, 25);
        
        JLabel timeFormatLabel = new JLabel("(Format: HH:mm, 24-hour)");
        timeFormatLabel.setBounds(250, 100, 180, 25);

        // Statistics section
        JLabel statsLabel = new JLabel("Play Statistics:");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsLabel.setBounds(30, 140, 150, 20);

        avgPlayTimeLabel = new JLabel(formatTime(averagePlayTime));
        avgPlayTimeLabel.setBounds(30, 165, 120, 25);
        JLabel avgDesc = new JLabel("Average Time Per Session");
        avgDesc.setBounds(160, 165, 200, 25);

        totalPlayTimeLabel = new JLabel(formatTime(totalPlayTime));
        totalPlayTimeLabel.setBounds(30, 195, 120, 25);
        JLabel totalDesc = new JLabel("Total Time Played");
        totalDesc.setBounds(160, 195, 200, 25);
        
        JLabel sessionsLabel = new JLabel("Sessions: " + sessionsCount);
        sessionsLabel.setBounds(30, 225, 200, 25);

        JButton resetStatsButton = new JButton("Reset Statistics");
        resetStatsButton.setBounds(30, 255, 150, 30);
        resetStatsButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to reset all play statistics?", 
                "Confirm Reset", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                totalPlayTime = 0;
                averagePlayTime = 0;
                sessionsCount = 0;
                updateStatisticsLabels();
                saveSettings();
            }
        });

        JButton reviveButton = new JButton("Revive Pet");
        reviveButton.setBounds(30, 295, 130, 30);
        reviveButton.addActionListener(e -> {
            // Implement pet revival logic here
            JOptionPane.showMessageDialog(this, "Pet revival feature not yet implemented", 
                "Feature Unavailable", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(190, 295, 130, 30);
        saveButton.addActionListener(e -> {
            if (validateAndSaveTimeSettings()) {
                saveSettings();
                JOptionPane.showMessageDialog(this, "Settings saved successfully!", 
                    "Settings Saved", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(350, 295, 100, 30);
        exitButton.addActionListener(e -> {
            if (validateAndSaveTimeSettings()) {
                saveSettings();
                dispose();
            }
        });

        JLabel imagePlaceholder = new JLabel();
        imagePlaceholder.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePlaceholder.setBounds(400, 50, 170, 170);

        panel.add(title);
        panel.add(timeLimitLabel);
        panel.add(toggleLimit);
        panel.add(fromLabel);
        panel.add(startTimeField);
        panel.add(toLabel);
        panel.add(endTimeField);
        panel.add(timeFormatLabel);
        panel.add(statsLabel);
        panel.add(avgPlayTimeLabel);
        panel.add(avgDesc);
        panel.add(totalPlayTimeLabel);
        panel.add(totalDesc);
        panel.add(sessionsLabel);
        panel.add(resetStatsButton);
        panel.add(reviveButton);
        panel.add(saveButton);
        panel.add(exitButton);
        panel.add(imagePlaceholder);

        return panel;
    }
    
    /**
     * Validates time format and saves the time settings
     * @return true if validation successful, false otherwise
     */
    private boolean validateAndSaveTimeSettings() {
        try {
            // Parse and validate time formats
            LocalTime newStartTime = LocalTime.parse(startTimeField.getText(), TIME_FORMATTER);
            LocalTime newEndTime = LocalTime.parse(endTimeField.getText(), TIME_FORMATTER);
            
            // Update settings
            timeLimitEnabled = toggleLimit.isSelected();
            startTime = newStartTime;
            endTime = newEndTime;
            
            return true;
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Invalid time format. Please use HH:mm (24-hour format).\nExample: 08:00 or 20:30", 
                "Invalid Time Format", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Formats time in seconds to a readable string
     */
    private String formatTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
    
    /**
     * Update the statistics labels with current values
     */
    private void updateStatisticsLabels() {
        totalPlayTimeLabel.setText(formatTime(totalPlayTime));
        avgPlayTimeLabel.setText(formatTime(averagePlayTime));
    }
    
    /**
     * Save parental control settings to file
     */
    private void saveSettings() {
        File dir = new File("saves");
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONTROLS_FILE))) {
            // Create a settings object to save
            ParentalSettings settings = new ParentalSettings();
            settings.timeLimitEnabled = this.timeLimitEnabled;
            settings.startTimeHour = this.startTime.getHour();
            settings.startTimeMinute = this.startTime.getMinute();
            settings.endTimeHour = this.endTime.getHour();
            settings.endTimeMinute = this.endTime.getMinute();
            settings.totalPlayTime = this.totalPlayTime;
            settings.averagePlayTime = this.averagePlayTime;
            settings.sessionsCount = this.sessionsCount;
            
            oos.writeObject(settings);
            System.out.println("Parental settings saved successfully to " + CONTROLS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Failed to save settings: " + e.getMessage(), 
                "Save Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Load parental control settings from file
     */
    private void loadSettings() {
        File file = new File(CONTROLS_FILE);
        if (!file.exists()) {
            System.out.println("No existing parental controls settings found. Using defaults.");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONTROLS_FILE))) {
            ParentalSettings settings = (ParentalSettings) ois.readObject();
            
            // Apply loaded settings
            this.timeLimitEnabled = settings.timeLimitEnabled;
            this.startTime = LocalTime.of(settings.startTimeHour, settings.startTimeMinute);
            this.endTime = LocalTime.of(settings.endTimeHour, settings.endTimeMinute);
            this.totalPlayTime = settings.totalPlayTime;
            this.averagePlayTime = settings.averagePlayTime;
            this.sessionsCount = settings.sessionsCount;
            
            System.out.println("Parental settings loaded successfully from " + CONTROLS_FILE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load parental control settings: " + e.getMessage());
            // Continue with default settings
        }
    }
    
    /**
     * Check if playing is allowed at current time
     * @return true if playing is allowed, false otherwise
     */
    public static boolean isPlayingAllowed() {
        // Load the current settings
        ParentalSettings settings = loadSettingsStatic();
        
        // If time limit is not enabled, playing is always allowed
        if (settings == null || !settings.timeLimitEnabled) {
            return true;
        }
        
        // Check if current time is within allowed range
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(settings.startTimeHour, settings.startTimeMinute);
        LocalTime endTime = LocalTime.of(settings.endTimeHour, settings.endTimeMinute);
        
        // Handle the case where end time is before start time (overnight)
        if (endTime.isBefore(startTime)) {
            // Allowed if current time is after start time OR before end time
            return !currentTime.isBefore(startTime) || !currentTime.isAfter(endTime);
        } else {
            // Allowed if current time is between start and end time
            return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
        }
    }
    
    /**
     * Static method to load settings without creating a ParentalControls instance
     * @return ParentalSettings object or null if not found
     */
    private static ParentalSettings loadSettingsStatic() {
        File file = new File(CONTROLS_FILE);
        if (!file.exists()) {
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONTROLS_FILE))) {
            return (ParentalSettings) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Update playtime statistics with a new session duration
     * @param sessionDuration Duration of the session in seconds
     */
    public static void updatePlayStatistics(long sessionDuration) {
        ParentalSettings settings = loadSettingsStatic();
        
        if (settings == null) {
            settings = new ParentalSettings();
        }
        
        // Update total play time
        settings.totalPlayTime += sessionDuration;
        
        // Update session count
        settings.sessionsCount++;
        
        // Calculate new average
        settings.averagePlayTime = settings.totalPlayTime / settings.sessionsCount;
        
        // Save updated settings
        saveSettingsStatic(settings);
    }
    
    /**
     * Static method to save settings without creating a ParentalControls instance
     * @param settings The settings to save
     */
    private static void saveSettingsStatic(ParentalSettings settings) {
        File dir = new File("saves");
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONTROLS_FILE))) {
            oos.writeObject(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializable class to store parental control settings
     */
    static class ParentalSettings implements Serializable {
        private static final long serialVersionUID = 1L;
        
        boolean timeLimitEnabled = false;
        int startTimeHour = 8;
        int startTimeMinute = 0;
        int endTimeHour = 20;
        int endTimeMinute = 0;
        long totalPlayTime = 0;
        long averagePlayTime = 0;
        int sessionsCount = 0;
    }
}