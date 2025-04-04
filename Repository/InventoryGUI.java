
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

/**
 * InventoryGUI is a graphical user interface (GUI) that allows users to interact with the inventory system.
 * This class displays available items (food and toys), allows users to purchase items using a score system, 
 * and manage pet selection and item quantities. 
 * 
 * Most of the GUI was generated on Netbeans IDE and some parts for GUI, used the assistance of generative AI.
 * @author Mohammad Alif
 */
public class InventoryGUI extends javax.swing.JFrame {
    
    private String toPurchase;
    private int howMuch;

    public String[] shopItems = {"Orange", "Popsicle", "Cake", "Cards", "Ball", "Gift"};
    public double[] shopCosts = {10, 50, 100, 40, 75, 500};
    public String[] itemDescriptions = {
        "A simple fruit, full of vitamins sure to keep you healthy, makes for a great snack!",
        "One chilly delight, especially good for a hot summer day, but can be enjoyed any time of the year.",
        "This sugary delight is a filling confection, too much can be pretty bad for you in real life, but here, there's no consequences to eating as many slices as you can!",
        "Simple set of very bizarre playing cards, we aren't too sure what your pets can use them for, but they do...",
        "A ball for having lots of fun with! It's the vollleyball sorta ball, but who uses it for that anyways.",
        "Ooo, what's this? Try and give it to your pet, it might make them really happy :)"
    };

    public String currentPet;
    private static Inventory inventory;

     /**
     * Constructs the InventoryGUI with the specified current pet.
     * Initializes the inventory and sets up the UI components.
     * 
     * @param current The name of the current pet selected by the user.
     * @author Mohammad Alif
     */
    public InventoryGUI(String current, Inventory inventory) {
        // Initializations;
        toPurchase = "";
        howMuch = 0;
        currentPet = current;
        this.inventory = inventory;
        setFontForAllComponents();
        initComponents();
        applyMonochromaticPastelTheme();
    }

    /**
     * Applies a pastel monochromatic theme to the GUI components.
     * This method sets the background color and button colors for the entire interface.
     * @author Mohammad Alif
     */
    private void applyMonochromaticPastelTheme() {

        // Define pastel shades based on the warm background image color
        Color pastelBeige = new Color(252, 244, 225); // Lighter beige (background)
        Color deepPeach = new Color(252, 204, 154);  // Slightly deeper peach (for buttons)
        Color textColor = new Color(62, 62, 62);     // Darker gray for text (good contrast)

        UIManager.put("Panel.background", pastelBeige); // Light beige background for panels
        UIManager.put("Button.background", deepPeach);  // Pastel peach for buttons
        UIManager.put("Button.foreground", textColor); // Dark gray text for button labels
        UIManager.put("Label.background", pastelBeige); // Light beige background for labels
        UIManager.put("Label.foreground", textColor); // Dark gray text for labels
        UIManager.put("TextArea.foreground", textColor); // Dark text in text areas
    
        // Apply UIManager settings to all components
        SwingUtilities.updateComponentTreeUI(this);
    
    }

     /**
     * Sets the font for all components in the GUI to "Comic Sans MS" with size 14.
     * This method ensures that the font for buttons, labels, text fields, and other components is consistent.
     * @author Mohammad Alif
     */
    private void setFontForAllComponents() {
        try {
            // Set the font for all components
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            Font font = new Font("Comic Sans MS", Font.PLAIN, 14);  // Font style and size
            UIManager.put("Label.font", font);
            UIManager.put("Button.font", font);
            UIManager.put("TextField.font", font);
            UIManager.put("TextArea.font", font);
            UIManager.put("TextPane.font", font);
            UIManager.put("ComboBox.font", font);
            UIManager.put("TabbedPane.font", font);
            UIManager.put("ScrollPane.font", font);


        } catch (UnsupportedLookAndFeelException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (InstantiationException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Custom JPanel that displays a background image.
     * 
     * This class extends JPanel and is used to render a background image on the panel. 
     * The image is scaled to fit the width of the panel, but its height is preserved 
     * according to the original dimensions of the image. The image is drawn starting 
     * from the top-left corner of the panel.
     * @author Mohammad Alif
     */
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;
    
         /**
         * Constructs a BackgroundPanel with the specified image path.
         * 
         * This constructor loads the image from the given path and prepares it for 
         * rendering on the panel. The image is scaled to fit the width of the panel.
         * 
         * @param imagePath The path to the image file to be used as the background.
         * @author Mohammad Alif
         */
        public BackgroundPanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
             // To prevent the image from resizing, draw it at a fixed location and size
            int imageHeight = backgroundImage.getHeight(this);
        
            g.drawImage(backgroundImage, 0, 0, getWidth(), imageHeight, this);
        }
    }

    /**
     * Sets up the layout and functionality for the GUI components, including the inventory items and purchase options.
     * This method is automatically called when the form is initialized.
     * @author Mohammad Alif
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        BackgroundPanel backgroundPanel = new BackgroundPanel("assets\\images\\petSprites\\wood_bg_1.png");
        setContentPane(backgroundPanel);

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextPane4 = new javax.swing.JTextPane();
        jLabel5 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextPane5 = new javax.swing.JTextPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextPane7 = new javax.swing.JTextPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextPane8 = new javax.swing.JTextPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextPane6 = new javax.swing.JTextPane();
        jLabel7 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextPane9 = new javax.swing.JTextPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextPane10 = new javax.swing.JTextPane();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTextPane11 = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jButton8 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        jTabbedPane7.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setAutoscrolls(false);
        jTextArea2.setLineWrap(true);  
        jTextArea2.setWrapStyleWord(true);
        jScrollPane5.setViewportView(jTextArea2);

        jTextPane4.setEditable(false);
        jTextPane4.setAutoscrolls(false);
        jScrollPane6.setViewportView(jTextPane4);

        jLabel5.setText("Item:");

        jButton9.setIcon(new ImageIcon("assets\\images\\itemSprites\\cake.png"));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new ImageIcon("assets\\images\\itemSprites\\popsicle.png"));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new ImageIcon("assets\\images\\itemSprites\\orang.png"));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jTextPane5.setEditable(false);
        jTextPane5.setText(String.valueOf(inventory.getInventory()[0].getNumberItem()));
        jTextPane5.setAutoscrolls(false);
        jScrollPane7.setViewportView(jTextPane5);

        jTextPane7.setEditable(false);
        jTextPane7.setText(String.valueOf(inventory.getInventory()[2].getNumberItem()));
        jTextPane7.setAutoscrolls(false);
        jScrollPane10.setViewportView(jTextPane7);

        jTextPane8.setEditable(false);
        jTextPane8.setText(String.valueOf(inventory.getInventory()[1].getNumberItem()));
        jTextPane8.setAutoscrolls(false);
        jScrollPane11.setViewportView(jTextPane8);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 26, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane6)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(259, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Food", jPanel1);

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setAutoscrolls(false);
        jTextArea3.setLineWrap(true);  
        jTextArea3.setWrapStyleWord(true);
        jScrollPane8.setViewportView(jTextArea3);

        jTextPane6.setEditable(false);
        jTextPane6.setAutoscrolls(false);
        jScrollPane9.setViewportView(jTextPane6);

        jLabel7.setText("Item:");

        jButton12.setIcon(new ImageIcon("assets\\images\\itemSprites\\gift.png"));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setIcon(new ImageIcon("assets\\images\\itemSprites\\ball.png"));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setIcon(new ImageIcon("assets\\images\\itemSprites\\cards.png"));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jTextPane9.setEditable(false);
        jTextPane9.setText(String.valueOf(inventory.getInventory()[3].getNumberItem()));
        jTextPane9.setAutoscrolls(false);
        jScrollPane12.setViewportView(jTextPane9);

        jTextPane10.setEditable(false);
        jTextPane10.setText(String.valueOf(inventory.getInventory()[5].getNumberItem()));
        jTextPane10.setAutoscrolls(false);
        jScrollPane13.setViewportView(jTextPane10);

        jTextPane11.setEditable(false);
        jTextPane11.setText(String.valueOf(inventory.getInventory()[4].getNumberItem()));
        jTextPane11.setAutoscrolls(false);
        jScrollPane14.setViewportView(jTextPane11);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane12)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane13)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 26, Short.MAX_VALUE))
                    .addComponent(jScrollPane8))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(259, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Toys", jPanel4);

        jTabbedPane1.addTab("Inventory", jTabbedPane7);
        jTabbedPane7.getAccessibleContext().setAccessibleName("Inventory Tab");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 391, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Pets", jPanel2);
        jPanel2 = createPetsSelect(jPanel2, jTextPane1, currentPet);
        jTabbedPane1.addTab("Pets", jPanel2);
        jPanel2.getAccessibleContext().setAccessibleName("Pets Tab");
        
        jButton2.setIcon(new ImageIcon("assets\\images\\itemSprites\\orang.png"));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new ImageIcon("assets\\images\\itemSprites\\popsicle.png"));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new ImageIcon("assets\\images\\itemSprites\\cards.png"));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new ImageIcon("assets\\images\\itemSprites\\ball.png"));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new ImageIcon("assets\\images\\itemSprites\\gift.png"));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new ImageIcon("assets\\images\\itemSprites\\cake.png"));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jSpinner1.setName("HowMuch"); 

        jButton8.setText("Purchase");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchaseItem(evt, jTextPane1);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setAutoscrolls(false);
        jTextArea1.setLineWrap(true); 
        jTextArea1.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel2.setText("Item:");

        jLabel3.setText("Cost:");

        jTextPane2.setEditable(false);
        jTextPane2.setAutoscrolls(false);
        jScrollPane3.setViewportView(jTextPane2);

        jTextPane3.setEditable(false);
        jTextPane3.setAutoscrolls(false);
        jScrollPane4.setViewportView(jTextPane3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 207, Short.MAX_VALUE)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane4))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton8)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(189, Short.MAX_VALUE))
        );

        jButton2.getAccessibleContext().setAccessibleName("Food1");
        jButton2.getAccessibleContext().setAccessibleDescription("");
        jButton3.getAccessibleContext().setAccessibleName("Food2");
        jButton5.getAccessibleContext().setAccessibleName("Toy1");
        jButton6.getAccessibleContext().setAccessibleName("Toy2");
        jButton7.getAccessibleContext().setAccessibleName("Toy3");
        jButton4.getAccessibleContext().setAccessibleName("Food3");
        jSpinner1.getAccessibleContext().setAccessibleName("HowMuch");

        jTabbedPane1.addTab("Shop", jPanel3);
        jPanel3.getAccessibleContext().setAccessibleName("Shop Tab");

        jButton1.setText("Settings");

        jTextPane1.setEditable(false);
        jTextPane1.setText(String.valueOf(inventory.getScore()));
        jScrollPane1.setViewportView(jTextPane1);

        jLabel1.setText("Score:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Main Tab");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        int x = (int) (screenSize.getWidth() - this.getWidth()) / 5;
        int y = (int) (screenSize.getHeight() - this.getHeight()) / 8;

        setLocation(x, y);

        pack();
    }// </editor-fold>                                     
    
     /**
     * Displays details about the selected item in the GUI, including its name, cost, and description.
     * 
     * @param itemName The name of the item.
     * @param cost The cost of the item.
     * @param message The description of the item.
     * @author Mohammad Alif
     */
     private void showDetail(String itemName, String cost, String message){
        jTextPane2.setText(itemName);
        jTextPane3.setText(cost);
        jTextArea1.setText(message);
        
        jTextPane4.setText(itemName);
        jTextArea2.setText(message);
        
        jTextPane6.setText(itemName);
        jTextArea3.setText(message);
        
    }
  
    // Actions for the shop buttons
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        showDetail(shopItems[0], String.valueOf(shopCosts[0]),  itemDescriptions[0]);
        toPurchase = jTextPane2.getText();
       
    }                                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        showDetail(shopItems[1], String.valueOf(shopCosts[1]),  itemDescriptions[1]);
        toPurchase = jTextPane2.getText();
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        showDetail(shopItems[2], String.valueOf(shopCosts[2]),  itemDescriptions[2]);
        toPurchase = jTextPane2.getText();
    }                                        

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        showDetail(shopItems[3], String.valueOf(shopCosts[3]),  itemDescriptions[3]);
        toPurchase = jTextPane2.getText();
    }                                        

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        showDetail(shopItems[4], String.valueOf(shopCosts[4]),  itemDescriptions[4]);
        toPurchase = jTextPane2.getText();
    }                                        

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        showDetail(shopItems[5], String.valueOf(shopCosts[5]),  itemDescriptions[5]);
        toPurchase = jTextPane2.getText();
    }                                        

    /**
     * Handles the purchase of an item from the shop based on the selected item and quantity.
     * The method checks if the user has enough score to make the purchase. If the user has sufficient score, 
     * the inventory is updated, the score is adjusted, and a success message is shown. If the user doesn't have 
     * enough score, an error message is displayed. The method also updates the corresponding inventory display 
     * (e.g., number of items) and the scoreboard with the updated score.
     * 
     * @param evt The ActionEvent triggered by the purchase button click.
     * @param scoreBoard The JTextPane that displays the current score.
     * @author Mohammad Alif
     */
    private void purchaseItem(java.awt.event.ActionEvent evt, JTextPane scoreBoard) {                              
        howMuch = (Integer) jSpinner1.getValue();
       
         if (howMuch < 1){
            JOptionPane.showMessageDialog(this, "Please select at least one item.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
            return;  
        }

        switch(toPurchase){
            case "Orange":
                if (howMuch*shopCosts[0] > inventory.getScore()) {
                    JOptionPane.showMessageDialog(null, "You do not have enough score to purchase this orange!", "Insufficient Score", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    inventory.setScore(inventory.getScore() - howMuch*shopCosts[0]); // Update score after purchase
                    scoreBoard.setText(String.valueOf(inventory.getScore()));
                    inventory.getInventory()[0].increaseItem(howMuch);
                    jTextPane5.setText(String.valueOf(inventory.getInventory()[0].getNumberItem()));
                    JOptionPane.showMessageDialog(null, "You have bought "+ howMuch +" "+ shopItems[0], "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);

                }
            break;
            case "Popsicle":
                if (howMuch*shopCosts[1] > inventory.getScore()) {
                    JOptionPane.showMessageDialog(null, "You do not have enough score to purchase this popsicle!", "Insufficient Score", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    inventory.setScore(inventory.getScore() - howMuch*shopCosts[1]); // Update score after purchase
                    scoreBoard.setText(String.valueOf(inventory.getScore()));
                    inventory.getInventory()[1].increaseItem(howMuch);
                    jTextPane8.setText(String.valueOf(inventory.getInventory()[1].getNumberItem()));
                    JOptionPane.showMessageDialog(null, "You have bought "+ howMuch +" "+ shopItems[1], "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);

                }
            break;
            case "Cake":
                if (howMuch*shopCosts[2] > inventory.getScore()) {
                    JOptionPane.showMessageDialog(null, "You do not have enough score to purchase this cake!", "Insufficient Score", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    inventory.setScore(inventory.getScore() - howMuch*shopCosts[2]); // Update score after purchase
                    scoreBoard.setText(String.valueOf(inventory.getScore()));
                    inventory.getInventory()[2].increaseItem(howMuch);
                    jTextPane7.setText(String.valueOf(inventory.getInventory()[2].getNumberItem()));
                    JOptionPane.showMessageDialog(null, "You have bought "+ howMuch +" "+ shopItems[2], "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);

                }
            break;
            case "Cards":
                if (howMuch*shopCosts[3] > inventory.getScore()) {
                    JOptionPane.showMessageDialog(null, "You do not have enough score to purchase these cards!", "Insufficient Score", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    inventory.setScore(inventory.getScore() - howMuch*shopCosts[3]); // Update score after purchase
                    scoreBoard.setText(String.valueOf(inventory.getScore()));
                    inventory.getInventory()[3].increaseItem(howMuch);
                    jTextPane9.setText(String.valueOf(inventory.getInventory()[3].getNumberItem()));
                    JOptionPane.showMessageDialog(null, "You have bought "+ howMuch +" "+ shopItems[3], "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);

                }
            break;
            case "Ball":
                if (howMuch*shopCosts[4] > inventory.getScore()) {
                    JOptionPane.showMessageDialog(null, "You do not have enough score to purchase this ball!", "Insufficient Score", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    inventory.setScore(inventory.getScore() - howMuch*shopCosts[4]); // Update score after purchase
                    scoreBoard.setText(String.valueOf(inventory.getScore()));
                    inventory.getInventory()[4].increaseItem(howMuch);
                    jTextPane11.setText(String.valueOf(inventory.getInventory()[4].getNumberItem()));
                    JOptionPane.showMessageDialog(null, "You have bought "+ howMuch +" "+ shopItems[4], "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);

                }
            break;
            case "Gift":
                if (howMuch*shopCosts[5] > inventory.getScore()) {
                    JOptionPane.showMessageDialog(null, "You do not have enough score to purchase this gift!", "Insufficient Score", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    inventory.setScore(inventory.getScore() - howMuch*shopCosts[5]); // Update score after purchase
                    scoreBoard.setText(String.valueOf(inventory.getScore()));
                    inventory.getInventory()[5].increaseItem(howMuch);
                    jTextPane10.setText(String.valueOf(inventory.getInventory()[5].getNumberItem()));
                    JOptionPane.showMessageDialog(null, "You have bought "+ howMuch +" "+ shopItems[5], "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);

                }
            break;

            default:
                JOptionPane.showMessageDialog(this, "You need to choose what to buy!", "Choose Something", JOptionPane.WARNING_MESSAGE);
            break;
        }
    }                             

    // Actions for the Toys Inventory
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        showDetail(shopItems[5], "-",  itemDescriptions[5]);
    }                                         

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        showDetail(shopItems[4], "-",  itemDescriptions[4]);
    }                                         

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        showDetail(shopItems[3], "-",  itemDescriptions[3]);
    }                                         

    // Actions for the Food Inventory
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        showDetail(shopItems[0], "-",  itemDescriptions[0]);
    }                                         

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        showDetail(shopItems[1], "-",  itemDescriptions[1]);
    }                                         

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        showDetail(shopItems[2], "-", itemDescriptions[2]);

    }                                        

    /**
     * Creates a JPanel that allows the user to select pets, displaying their 
     * images, names, descriptions, and prices. The layout is handled using 
     * GridBagLayout to center the components and make them responsive to panel resizing.
     *
     * @param panel The JPanel to add the pet selection components to.
     * @param scoreBoard The JTextPane used to display the user's score.
     * @return The modified JPanel with pet selection components.
     * @author Mohammad Alif
     */
    private static JPanel createPetsSelect(JPanel panel, JTextPane scoreBoard, String current){
        panel.setLayout(new GridBagLayout());  // Change the layout to GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();  // Create GridBagConstraints for centering
        gbc.gridx = 0;  // Horizontal position
        gbc.gridy = 0;  // Vertical position
        gbc.anchor = GridBagConstraints.CENTER;  // Center the component within the grid cell
        gbc.insets = new Insets(10, 10, 10, 10);  // Add some padding around the components
        gbc.fill = GridBagConstraints.NONE;  // Prevent stretching
        gbc.weightx = 1.0;  // Allow horizontal expansion
        gbc.weighty = 1.0;  // Allow vertical expansion

        String assets = "assets\\images\\petSprites\\Tsuyopitchi\\head2\\pet2_head2_neutral.png";
        String description = "Our Sassy Star";
        String petNames = "Tsuyopitchi";

        String petCosts = "10000";
        boolean [] locked = inventory.getLockedPets();

        ActionListener purchaseListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton sourceButton = (JButton) e.getSource();
                int petIndex = Integer.parseInt(sourceButton.getActionCommand()); // Get the pet index from the button's action command
                
                // If the pet is already unlocked, don't allow purchasing again
                if (!locked[1]) {
                    return; // Exit if pet is already unlocked
                }

                // Get the cost of the pet
                int cost = Integer.parseInt(petCosts);
                
                // Check if the player has enough score to purchase the pet
                if (inventory.getScore() >= cost) {
                    // Unlock the pet (update the locked array)
                    locked[petIndex] = false; // Unlock the pet
                    
                    // Run UI updates on the Event Dispatch Thread (EDT)
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            // Change button text to "Select"
                            sourceButton.setText("Purchased");
                            sourceButton.setEnabled(false);
                            sourceButton.setActionCommand(String.valueOf(petIndex)); // Set the pet index as the action command

                            // Update the cost label to say "Unlocked"
                            JPanel petPanel = (JPanel) sourceButton.getParent();
                            JLabel costLabel = (JLabel) petPanel.getComponent(1); // Get the cost label (assuming it's the 3rd component)
                            costLabel.setText(" Unlocked "); // Change cost to "Unlocked"
                        }
                    });
                    
                    // Deduct the cost from the player's score
                    inventory.setScore(inventory.getScore() - cost); // Update score after purchase
                    scoreBoard.setText(String.valueOf(inventory.getScore()));

                    // Show a success message
                    JOptionPane.showMessageDialog(null, "Purchase Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Update locked pets in the inventory after the purchase
                    inventory.setLockedPets(locked);
                    
                } else {
                    // If the player doesn't have enough score, show a message
                    JOptionPane.showMessageDialog(sourceButton, "You do not have enough score to purchase this pet!", "Insufficient Score", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        // Iterate through possible pets
        
            JPanel petPanel = new JPanel();
            petPanel.setLayout(new BoxLayout(petPanel, BoxLayout.Y_AXIS));
            petPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

            // Initialize Image
            ImageIcon petImageIcon = new ImageIcon(assets);
            Image img = petImageIcon.getImage();  
            Image newImg = img.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
            petImageIcon = new ImageIcon(newImg);
            
            // Set name
            JLabel petName = new JLabel(petNames);
            petName.setAlignmentX(Component.CENTER_ALIGNMENT);
            petName.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            petPanel.add(petName);

            JLabel petImage = new JLabel(petImageIcon); // Set image
            petImage.setAlignmentX(Component.CENTER_ALIGNMENT);

            Color lightBeige = new Color(250, 228, 182);  

            petImage.setBackground(lightBeige);  // Set background color (cyan in this case)
            petImage.setOpaque(true);
            Border border = BorderFactory.createLineBorder(Color.white, 5);  // Black border with 5px width
            petImage.setBorder(border);

            petPanel.add(petImage);
            
            JLabel petDescription = new JLabel(description); // Add description
            petDescription.setAlignmentX(Component.CENTER_ALIGNMENT);
            petPanel.add(petDescription);

            JLabel costLabel = new JLabel("Cost: " + petCosts);
            costLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the cost label
            costLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 14));

            petPanel.add(costLabel);
            
            JButton button;

            button = new JButton("Purchase for " + petCosts);
            button.setActionCommand(String.valueOf(1)); // Set the pet index as the action command
            button.addActionListener(purchaseListener);
                
            button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

            inventory.setLockedPets(locked);
            petPanel.add(button);
            panel.add(petPanel);
         
        return panel;
    }
    
    /**
     * Retrieves the current instance of the Inventory class from the InventoryGUI.
     * 
     * This method provides access to the inventory object, allowing other classes or methods 
     * to retrieve the inventory managed by the `InventoryGUI`. It returns the Inventory object 
     * associated with the current game session, which includes information about the current 
     * pet, score, and inventory items.
     * 
     * @return The Inventory object containing the current pet, inventory items, and score.
     * @author Mohammad Alif
     */
    public Inventory getInventoryClass(){
        return InventoryGUI.inventory;
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane10;
    private javax.swing.JTextPane jTextPane11;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JTextPane jTextPane4;
    private javax.swing.JTextPane jTextPane5;
    private javax.swing.JTextPane jTextPane6;
    private javax.swing.JTextPane jTextPane7;
    private javax.swing.JTextPane jTextPane8;
    private javax.swing.JTextPane jTextPane9;
    // End of variables declaration                   
}
