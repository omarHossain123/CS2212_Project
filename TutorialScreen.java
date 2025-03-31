
import java.awt.*;
import javax.swing.*;

/**
 * TutorialScreen - A multi-page tutorial walkthrough with styled corner arrows and exit button.
 */
public class TutorialScreen extends JFrame {

    private final String[] titles = {
        "Overview", "Health", "Sleep", "Fullness", "Happiness"
    };

    private final String[] content = {
        "Overview content goes here.",
        "Health content goes here.",
        "Sleep content goes here.",
        "Fullness content goes here.",
        "Happiness content goes here."
    };

    private int page = 0;

    private JLabel titleLabel;
    private JTextArea contentArea;
    private JButton backButton, nextButton, exitButton;

    public TutorialScreen() {
        setTitle("Tutorial");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Title label
        titleLabel = new JLabel(titles[page], SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBounds(0, 10, 700, 30);
        add(titleLabel);

        // Content area
        contentArea = new JTextArea(content[page]);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Serif", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBounds(100, 60, 500, 300);
        add(scrollPane);

        // Styled arrow buttons
        backButton = createCircleButton("←");
        backButton.setBounds(20, 200, 50, 50);
        add(backButton);

        nextButton = createCircleButton("→");
        nextButton.setBounds(630, 200, 50, 50);
        add(nextButton);

        // Exit button (top right corner)
        exitButton = createCircleButton("✕");
        exitButton.setBounds(650, 10, 40, 40);
        add(exitButton);

        // Button actions
        backButton.addActionListener(e -> {
            if (page > 0) {
                page--;
                updatePage();
            }
        });

        nextButton.addActionListener(e -> {
            if (page < titles.length - 1) {
                page++;
                updatePage();
            }
        });

        exitButton.addActionListener(e -> dispose());
    }

    private void updatePage() {
        titleLabel.setText(titles[page]);
        contentArea.setText(content[page]);
    }

    private JButton createCircleButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setPreferredSize(new Dimension(40, 40));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TutorialScreen().setVisible(true));
    }
}