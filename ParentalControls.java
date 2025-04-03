import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * ParentalControls - Password screen followed by controls/settings screen.
 * Includes allowed play time hours and gameplay statistics.
 * Styled to match the main menu and tutorial screens.
 */
public class ParentalControls extends JDialog {

    // Color scheme matching other screens
    private final Color TITLE_COLOR = new Color(75, 91, 22); // Dark green
    private final Color TEXT_COLOR = new Color(58, 71, 15);  // Darker green for content
    private final Color BUTTON_TEXT = new Color(70, 86, 20); // Dark green
    private final Color BUTTON_BORDER = new Color(139, 87, 42); // Brown color for button borders
    private final Color BUTTON_BACKGROUND = new Color(255, 255, 240, 220); // Slightly transparent cream
    private final Color CONTENT_BG = new Color(255, 255, 240, 200); // Semi-transparent background
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static final String PASSWORD = "123";
    
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
    private JLabel sessionsLabel;
    
    private static final String CONTROLS_FILE = "saves/parental_controls.dat";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ParentalControls() {
        this(null);
    }

    public ParentalControls(JFrame parent) {
        super(parent, "Parental Controls", true); // true makes it modal
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true); // Remove window decorations for popup feel
        
        // Create custom shaped window with rounded corners
        setShape(new RoundRectangle2D.Double(0, 0, 700, 500, 20, 20));

        // Load existing settings
        loadSettings();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setLayout(cardLayout);

        // Create and add panels
        mainPanel.add(createPasswordPanel(), "password");
        mainPanel.add(createSettingsPanel(), "settings");

        // Set content pane
        setContentPane(mainPanel);
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
        // Create custom background panel
        JPanel panel = new PasswordBackgroundPanel();
        panel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Parental Controls", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setBounds(0, 50, 700, 50);
        panel.add(titleLabel);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Please enter password to continue", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 16));
        subtitleLabel.setForeground(TEXT_COLOR);
        subtitleLabel.setBounds(0, 100, 700, 30);
        panel.add(subtitleLabel);

        // Create content panel with styled background
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
        contentPanel.setLayout(null);
        contentPanel.setBounds(150, 150, 400, 200);
        panel.add(contentPanel);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        passwordLabel.setForeground(TEXT_COLOR);
        passwordLabel.setBounds(50, 60, 100, 30);
        contentPanel.add(passwordLabel);
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        passwordField.setBounds(150, 60, 200, 30);
        contentPanel.add(passwordField);

        // Create styled buttons
        JButton exitButton = createStyledButton("Exit", 100);
        exitButton.setBounds(100, 130, 100, 40);
        exitButton.addActionListener(e -> dispose());
        contentPanel.add(exitButton);

        JButton confirmButton = createStyledButton("Confirm", 100);
        confirmButton.setBounds(220, 130, 100, 40);
        confirmButton.addActionListener(e -> {
            String enteredPassword = new String(passwordField.getPassword());
            if (enteredPassword.equals(PASSWORD)) {
                cardLayout.show(mainPanel, "settings");
            } else {
                StyledDialog.showErrorDialog(this, 
                    "Incorrect Password", 
                    "Access Denied");
                passwordField.setText("");
            }
        });
        contentPanel.add(confirmButton);

        return panel;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new SettingsBackgroundPanel();
        panel.setLayout(null);

        // Title
        JLabel title = new JLabel("Parental Controls", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        title.setForeground(TITLE_COLOR);
        title.setBounds(0, 20, 700, 40);
        panel.add(title);

        // Create content panel with custom styling
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
        contentPanel.setLayout(null);
        contentPanel.setBounds(50, 80, 620, 380);
        panel.add(contentPanel);

        // Time limitations section
        JLabel timeLimitLabel = new JLabel("Allowed Play Time Hours:");
        timeLimitLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        timeLimitLabel.setForeground(TEXT_COLOR);
        timeLimitLabel.setBounds(30, 20, 250, 25);
        contentPanel.add(timeLimitLabel);

        toggleLimit = new JCheckBox("Enable Play Time Restrictions");
        toggleLimit.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        toggleLimit.setForeground(TEXT_COLOR);
        toggleLimit.setOpaque(false);
        toggleLimit.setBounds(30, 50, 250, 25);
        toggleLimit.setSelected(timeLimitEnabled);
        contentPanel.add(toggleLimit);

        JLabel fromLabel = new JLabel("Allow play from:");
        fromLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        fromLabel.setForeground(TEXT_COLOR);
        fromLabel.setBounds(30, 80, 110, 25);
        contentPanel.add(fromLabel);
        
        startTimeField = new JTextField(startTime.format(TIME_FORMATTER));
        startTimeField.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        startTimeField.setBounds(140, 80, 60, 25);
        contentPanel.add(startTimeField);

        JLabel toLabel = new JLabel("to:");
        toLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        toLabel.setForeground(TEXT_COLOR);
        toLabel.setBounds(210, 80, 30, 25);
        contentPanel.add(toLabel);
        
        endTimeField = new JTextField(endTime.format(TIME_FORMATTER));
        endTimeField.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        endTimeField.setBounds(240, 80, 60, 25);
        contentPanel.add(endTimeField);
        
        JLabel timeFormatLabel = new JLabel("(Format: HH:mm, 24-hour)");
        timeFormatLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 12));
        timeFormatLabel.setForeground(TEXT_COLOR);
        timeFormatLabel.setBounds(310, 80, 180, 25);
        contentPanel.add(timeFormatLabel);
        
        // Add a clarification label
        JLabel clarificationLabel = new JLabel("Child can only play during these hours when enabled");
        clarificationLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 12));
        clarificationLabel.setForeground(TEXT_COLOR);
        clarificationLabel.setBounds(30, 110, 350, 20);
        contentPanel.add(clarificationLabel);

        // Statistics section
        JLabel statsLabel = new JLabel("Play Statistics:");
        statsLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        statsLabel.setForeground(TEXT_COLOR);
        statsLabel.setBounds(30, 150, 150, 25);
        contentPanel.add(statsLabel);

        JLabel avgLabel = new JLabel("Average Time Per Session:");
        avgLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        avgLabel.setForeground(TEXT_COLOR);
        avgLabel.setBounds(30, 180, 200, 25);
        contentPanel.add(avgLabel);
        
        avgPlayTimeLabel = new JLabel(formatTime(averagePlayTime));
        avgPlayTimeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        avgPlayTimeLabel.setForeground(TEXT_COLOR);
        avgPlayTimeLabel.setBounds(230, 180, 150, 25);
        contentPanel.add(avgPlayTimeLabel);

        JLabel totalLabel = new JLabel("Total Time Played:");
        totalLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        totalLabel.setForeground(TEXT_COLOR);
        totalLabel.setBounds(30, 210, 200, 25);
        contentPanel.add(totalLabel);
        
        totalPlayTimeLabel = new JLabel(formatTime(totalPlayTime));
        totalPlayTimeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        totalPlayTimeLabel.setForeground(TEXT_COLOR);
        totalPlayTimeLabel.setBounds(230, 210, 150, 25);
        contentPanel.add(totalPlayTimeLabel);
        
        JLabel sessionsLabelText = new JLabel("Number of Play Sessions:");
        sessionsLabelText.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        sessionsLabelText.setForeground(TEXT_COLOR);
        sessionsLabelText.setBounds(30, 240, 200, 25);
        contentPanel.add(sessionsLabelText);
        
        sessionsLabel = new JLabel(String.valueOf(sessionsCount));
        sessionsLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        sessionsLabel.setForeground(TEXT_COLOR);
        sessionsLabel.setBounds(230, 240, 150, 25);
        contentPanel.add(sessionsLabel);

        // Button section - creating styled buttons
        JButton resetStatsButton = createStyledButton("Reset Statistics", 150);
        resetStatsButton.setBounds(30, 290, 150, 40);
        resetStatsButton.addActionListener(e -> {
            int confirm = StyledDialog.showConfirmDialog(this, 
                "Are you sure you want to reset all play statistics?", 
                "Confirm Reset");
                
            if (confirm == JOptionPane.YES_OPTION) {
                totalPlayTime = 0;
                averagePlayTime = 0;
                sessionsCount = 0;
                updateStatisticsLabels();
                saveSettings();
            }
        });
        contentPanel.add(resetStatsButton);

        JButton reviveButton = createStyledButton("Revive Pet", 130);
        reviveButton.setBounds(200, 290, 130, 40);
        reviveButton.addActionListener(e -> {
            // Call the newly implemented revive pet method
            revivePet();
        });
        contentPanel.add(reviveButton);

        JButton saveButton = createStyledButton("Save Settings", 150);
        saveButton.setBounds(350, 290, 150, 40);
        saveButton.addActionListener(e -> {
            if (validateAndSaveTimeSettings()) {
                saveSettings();
                StyledDialog.showInformationDialog(this, 
                    "Play time settings saved successfully!", 
                    "Settings Saved");
            }
        });
        contentPanel.add(saveButton);

        JButton exitButton = createStyledButton("Exit", 80);
        exitButton.setBounds(520, 290, 80, 40);
        exitButton.addActionListener(e -> {
            if (validateAndSaveTimeSettings()) {
                saveSettings();
                dispose();
            }
        });
        contentPanel.add(exitButton);

        return panel;
    }
    
    /**
     * Creates a styled button that matches the rest of the application
     */
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
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        button.setForeground(BUTTON_TEXT);
        
        // Set the button size
        button.setPreferredSize(new Dimension(width, 40));
        button.setMaximumSize(new Dimension(width, 40));
        
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
            StyledDialog.showErrorDialog(this, 
                "Invalid time format. Please use HH:mm (24-hour format).\nExample: 08:00 or 20:30", 
                "Invalid Time Format");
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
        sessionsLabel.setText(String.valueOf(sessionsCount));
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
            StyledDialog.showErrorDialog(this, 
                "Failed to save settings: " + e.getMessage(), 
                "Save Error");
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
        
        // Get current time
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(settings.startTimeHour, settings.startTimeMinute);
        LocalTime endTime = LocalTime.of(settings.endTimeHour, settings.endTimeMinute);
        
        boolean isAllowed;
        
        // Handle the case where end time is before start time (overnight)
        if (endTime.isBefore(startTime)) {
            // Allowed if current time is after or equal to start time OR before or equal to end time
            isAllowed = !currentTime.isBefore(startTime) || !currentTime.isAfter(endTime);
        } else {
            // Allowed if current time is between start and end time (inclusive)
            isAllowed = !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
        }
        
        return isAllowed;
    }
    
    /**
     * Static method to load settings without creating a ParentalControls instance
     * @return ParentalSettings object or null if not found
     */
    public static ParentalSettings loadSettingsStatic() {
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
     * Utility method to check if parental control settings are correctly set
     * This can be called from UserInterface to verify settings
     */
    public static void checkSettings() {
        ParentalSettings settings = loadSettingsStatic();
        
        if (settings == null) {
            System.out.println("No parental control settings found.");
            return;
        }
        
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(settings.startTimeHour, settings.startTimeMinute);
        LocalTime endTime = LocalTime.of(settings.endTimeHour, settings.endTimeMinute);
        
        System.out.println("===== PARENTAL CONTROL SETTINGS =====");
        System.out.println("Current Time: " + currentTime.format(TIME_FORMATTER));
        System.out.println("Time Limit Enabled: " + settings.timeLimitEnabled);
        System.out.println("Allowed Time Range: " + startTime.format(TIME_FORMATTER) + 
                           " to " + endTime.format(TIME_FORMATTER));
        
        // Check if playing would be allowed with current settings
        boolean wouldBeAllowed;
        
        if (endTime.isBefore(startTime)) {
            wouldBeAllowed = !currentTime.isBefore(startTime) || !currentTime.isAfter(endTime);
            System.out.println("Overnight case detected");
        } else {
            wouldBeAllowed = !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
            System.out.println("Normal case detected");
        }
        
        System.out.println("Play should be " + (wouldBeAllowed ? "ALLOWED" : "BLOCKED") + 
                         " based on current time");
        System.out.println("Actual result from isPlayingAllowed(): " + isPlayingAllowed());
        System.out.println("======================================");
    }

    /**
     * Background panel for the password screen
     */
    private class PasswordBackgroundPanel extends JPanel {
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
            
            // Clock in corner
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
    
    /**
     * Background panel for the settings screen
     */
    private class SettingsBackgroundPanel extends JPanel {
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
            g2d.fillOval(getWidth() - 100, 50, 150, 150);
            
            // Clock icon to represent time restrictions
            g2d.setColor(new Color(139, 87, 42, 160));
            int clockX = 625;
            int clockY = 40;
            int clockSize = 70;
            g2d.fillOval(clockX, clockY, clockSize, clockSize);
            g2d.setColor(new Color(255, 255, 240, 220));
            g2d.fillOval(clockX + 5, clockY + 5, clockSize - 10, clockSize - 10);
            g2d.setColor(new Color(70, 86, 20));
            g2d.setStroke(new BasicStroke(3));
            // Clock hands
            g2d.drawLine(clockX + clockSize/2, clockY + clockSize/2, clockX + clockSize/2, clockY + clockSize/3);
            g2d.drawLine(clockX + clockSize/2, clockY + clockSize/2, clockX + 2*clockSize/3, clockY + clockSize/2);
            
            // Subtle cloud shapes
            g2d.setColor(new Color(255, 255, 255, 40));
            g2d.fillOval(200, 30, 150, 60);
            g2d.fillOval(150, 20, 100, 50);
            g2d.fillOval(250, 15, 120, 70);
            
            g2d.dispose();
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

    private void revivePet() {
        // Get list of save files
        List<String> saveFiles = getSaveFilesWithDeadPets();
        
        if (saveFiles.isEmpty()) {
            StyledDialog.showInformationDialog(this, 
                "No dead pets found in any save files.", 
                "No Dead Pets");
            return;
        }
        
        // Show dialog to select a dead pet save file
        String selectedSave = StyledSaveSelector.showDialog(
            this,
            "Select a dead pet to revive:",
            "Revive Pet",
            saveFiles);
        
        if (selectedSave != null) {
            try {
                // Load the selected save file
                File saveFile = new File("saves" + File.separator + selectedSave + ".dat");
                
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
                    Object loadedObject = ois.readObject();
                    
                    GameSaveData saveData;
                    if (loadedObject instanceof GameSaveData) {
                        saveData = (GameSaveData) loadedObject;
                    } else if (loadedObject instanceof Pet) {
                        // Legacy support for old save files
                        saveData = new GameSaveData();
                        saveData.setPet((Pet) loadedObject);
                    } else {
                        throw new ClassNotFoundException("Unknown save file format");
                    }
                    
                    // Check if the pet is actually dead
                    Pet pet = saveData.getPet();
                    if (pet == null || !pet.getState().equals("dead")) {
                        StyledDialog.showInformationDialog(this, 
                            "The selected save file does not contain a dead pet.", 
                            "No Dead Pet");
                        return;
                    }
                    
                    // Revive the pet
                    revivePetToNormalState(pet);
                    
                    // Reset save data
                    saveData.setScore(0); // Reset score
                    saveData.setLastFeedTime(0);
                    saveData.setLastGiftTime(0);
                    saveData.setLastSleepTime(0);
                    saveData.setLastVetTime(0);
                    saveData.setLastPlayTime(0);
                    saveData.setLastWalkTime(0);
                    
                    // Save the revived pet
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                        oos.writeObject(saveData);
                    }
                    
                    StyledDialog.showInformationDialog(this, 
                        "Pet successfully revived!", 
                        "Pet Revived");
                    
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    StyledDialog.showErrorDialog(this, 
                        "Failed to revive pet: " + e.getMessage(), 
                        "Revive Error");
                }
            } catch (Exception e) {
                e.printStackTrace();
                StyledDialog.showErrorDialog(this, 
                    "An error occurred while reviving the pet.", 
                    "Revive Error");
            }
        }
    }
    
    /**
     * Revives a pet by resetting its state and stats to maximum values
     * @param pet The pet to revive
     */
    private void revivePetToNormalState(Pet pet) {
        // Reset pet to its initial state with max values
        pet.setState("default");
        
        // Reset all stats to maximum
        pet.setHealth(pet.getMaxHealth());
        pet.setHappiness(pet.getMaxHappiness());
        pet.setHunger(pet.getMaxHunger());
        pet.setSleep(pet.getMaxSleep());
        
        // Set emotion to neutral
        pet.setEmotion("neutral", 0);
        
        // Update rates to default
        pet.updateRates(3);
    }
    
    /**
     * Get a list of save files containing dead pets
     * @return List of save file names with dead pets
     */
    private List<String> getSaveFilesWithDeadPets() {
        List<String> deadPetSaves = new ArrayList<>();
        File savesDir = new File("saves");
        
        if (savesDir.exists() && savesDir.isDirectory()) {
            File[] files = savesDir.listFiles((dir, name) -> name.endsWith(".dat"));
            
            if (files != null) {
                for (File file : files) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                        Object loadedObject = ois.readObject();
                        
                        Pet pet = null;
                        if (loadedObject instanceof GameSaveData) {
                            pet = ((GameSaveData) loadedObject).getPet();
                        } else if (loadedObject instanceof Pet) {
                            pet = (Pet) loadedObject;
                        }
                        
                        // Check if the pet is dead
                        if (pet != null && pet.getState().equals("dead")) {
                            String name = file.getName();
                            deadPetSaves.add(name.substring(0, name.length() - 4)); // Remove .dat
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        // Skip files that can't be read
                        System.err.println("Could not read save file: " + file.getName());
                    }
                }
            }
        }
        
        return deadPetSaves;
    }
}