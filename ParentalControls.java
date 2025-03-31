import java.awt.*;
import javax.swing.*;

/**
 * ParentalControls - Password screen followed by controls/settings screen.
 */
public class ParentalControls extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static final String PASSWORD = "HelloWorld123$";

    public ParentalControls() {
        setTitle("Parental Controls");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createPasswordPanel(), "password");
        mainPanel.add(createSettingsPanel(), "settings");

        add(mainPanel);
        cardLayout.show(mainPanel, "password");
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

        JCheckBox toggleLimit = new JCheckBox();
        toggleLimit.setBounds(30, 50, 20, 20);

        JTextField screenTime = new JTextField("00:00");
        screenTime.setBounds(60, 50, 100, 25);
        JLabel screenLabel = new JLabel("Allotted Screen Time");
        screenLabel.setBounds(170, 50, 150, 25);

        JTextField accessTime = new JTextField("00:00");
        accessTime.setBounds(60, 90, 100, 25);
        JLabel accessLabel = new JLabel("Access Time");
        accessLabel.setBounds(170, 90, 100, 25);

        JButton reviveButton = new JButton("Revive Pet");
        reviveButton.setBounds(30, 130, 130, 30);

        JLabel avgLabel = new JLabel("00hrs");
        avgLabel.setBounds(30, 180, 100, 25);
        JLabel avgDesc = new JLabel("Average Time Played");
        avgDesc.setBounds(100, 180, 200, 25);

        JLabel totalLabel = new JLabel("00hrs");
        totalLabel.setBounds(30, 210, 100, 25);
        JLabel totalDesc = new JLabel("Total Time Played");
        totalDesc.setBounds(100, 210, 200, 25);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(30, 260, 100, 30);
        exitButton.addActionListener(e -> dispose());

        JLabel imagePlaceholder = new JLabel();
        imagePlaceholder.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePlaceholder.setBounds(300, 50, 250, 250);

        panel.add(title);
        panel.add(toggleLimit);
        panel.add(screenTime);
        panel.add(screenLabel);
        panel.add(accessTime);
        panel.add(accessLabel);
        panel.add(reviveButton);
        panel.add(avgLabel);
        panel.add(avgDesc);
        panel.add(totalLabel);
        panel.add(totalDesc);
        panel.add(exitButton);
        panel.add(imagePlaceholder);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ParentalControls().setVisible(true));
    }
}