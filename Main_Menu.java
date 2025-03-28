import javax.swing.*;

/**
 * Main_Menu - Finalized Java Swing GUI main menu.
 */
public class Main_Menu extends JFrame {
    public Main_Menu() {
        initComponents();
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jButton4 = new JButton();
        jButton5 = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 36));
        jLabel1.setText("Virtual Pet Game");

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
        jLabel2.setText("Created in Winter 2025 for CS2212 at Western University");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 12));
        jLabel3.setText("By Group 15: Isa Alif, Omar Hossain, Humza Khan, Ahmed Sinjab, Jacob Tran");

        jButton1.setText("Start New Game");
        // Button actions are now handled in UserInterface class

        jButton2.setText("Continue Game");
        // Button actions are now handled in UserInterface class

        jButton3.setText("Tutorial");
        // Button actions are now handled in UserInterface class

        jButton4.setText("Parental Controls");
        jButton4.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Parental controls allow you to set limits on game time\n" +
                "and enable content filtering for younger players.\n\n" +
                "This feature is coming soon!",
                "Parental Controls",
                JOptionPane.INFORMATION_MESSAGE);
        });

        jButton5.setText("Exit");
        jButton5.addActionListener(e -> System.exit(0));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel2)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel3)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addComponent(jButton4, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null); // Center on screen
        setTitle("Virtual Pet Game");
    }

    // Getters for the buttons so UserInterface can add action listeners
    public JButton getStartNewGameButton() {
        return jButton1;
    }
    
    public JButton getContinueGameButton() {
        return jButton2;
    }
    
    public JButton getTutorialButton() {
        return jButton3;
    }

    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
}