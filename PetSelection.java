import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PetSelection {
    public static Pet selectedPet;

    public static void selectPet() {
        JFrame frame = new JFrame("Select Your Pet");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3));

        String path = "assets/images/petSprites/";

        Pet kitty = new Pet("Kitty", "Cat", 120, 80, 100, 90, path + "cat.jpg");
        Pet puppy = new Pet("Puppy", "Dog", 100, 100, 80, 100, path + "dog.png");
        Pet bird = new Pet("Birdy", "Bird", 80, 120, 100, 70, path + "bird.jpeg");

        Pet[] pets = {kitty, puppy, bird};

        for (Pet pet : pets) {
            JPanel petPanel = new JPanel();
            petPanel.setLayout(new BorderLayout());

            JButton button = new JButton(pet.getType(), pet.getPetImage());
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);

            JLabel descriptionLabel = new JLabel(pet.toString(), SwingConstants.CENTER);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = JOptionPane.showInputDialog(frame, "Enter a name for your pet:");
                    if (name != null && !name.trim().isEmpty()) {
                        selectedPet = new Pet(name, pet.getType(), pet.getHealth(), pet.getHappiness(),
                                              pet.getSleep(), pet.getHunger(), pet.getPetImage().toString());
                        JOptionPane.showMessageDialog(frame, "You selected: " + selectedPet);
                        frame.dispose();
                        Game.startGame();
                    }
                }
            });

            petPanel.add(button, BorderLayout.CENTER);
            petPanel.add(descriptionLabel, BorderLayout.SOUTH);
            frame.add(petPanel);
        }

        frame.setVisible(true);
    }
}
