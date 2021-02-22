package GUI;

import javax.swing.*;
import java.awt.*;

public class startOfGame {
    public startOfGame() {
        JFrame frame = new JFrame("textField");
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridBagLayout());

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("UNO Game");
        frame.pack();
        frame.setVisible(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel label = new JLabel("Enter the number of players: ");
        panel.add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JTextField text = new JTextField(10);
        panel.add(text, gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        JButton button = new JButton("START");
        panel.add(button, gbc);
    }

    public static void main(String[] args) {
        new startOfGame();
    }


}
