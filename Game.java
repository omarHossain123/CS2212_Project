import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Game {
    private static Pet selectedPet;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Select Your Pet");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(1, 3));

            String path = "assets/images/petSprites/";

            Pet kitty = new Pet("Kitty", "Cat", path + "cat.jpg");
            Pet puppy = new Pet("Puppy", "Dog", path + "dog.png");
            Pet bird = new Pet("Bird", "Birdy", path + "bird.jpeg");

            Pet[] pets = {kitty, puppy, bird};

            for (Pet pet : pets) {
                JButton button = new JButton(pet.getType(), pet.getPetImage());
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = JOptionPane.showInputDialog(frame, "Enter a name for your pet:");
                        if (name != null && !name.trim().isEmpty()) {
                            selectedPet = new Pet(name, pet.getType(), pet.getPetImage().toString());
                            JOptionPane.showMessageDialog(frame, "You selected: " + selectedPet);
                            frame.dispose();
                            startGame();
                        }
                    }
                });

                frame.add(button);
            }

            frame.setVisible(true);
        });
    }

    private static void startGame() {
        JFrame gameFrame = new JFrame("Pet Care");
        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new FlowLayout());

        JLabel petLabel = new JLabel(selectedPet.getPetImage());
        JLabel infoLabel = new JLabel(selectedPet.toString());

        gameFrame.add(petLabel);
        gameFrame.add(infoLabel);
        gameFrame.setVisible(true);
    }
}
