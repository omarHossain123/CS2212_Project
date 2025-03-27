import java.awt.*;
import javax.swing.*;

public class UserInterface {
    public static void main(String[] args) {
        PetSelection.selectPet();
    }

    public static void startGame() {
        JFrame gameFrame = new JFrame("Pet Care");
        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new FlowLayout());

        JLabel petLabel = new JLabel(PetSelection.selectedPet.getPetImage());
        JLabel infoLabel = new JLabel(PetSelection.selectedPet.toString());

        gameFrame.add(petLabel);
        gameFrame.add(infoLabel);
        gameFrame.setVisible(true);
    }
}
