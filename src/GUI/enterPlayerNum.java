package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class enterPlayerNum {
    public int player_num;

    public enterPlayerNum() {

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

        // get the user input for the player numbers of the game
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        JButton button = new JButton("Next");
        panel.add(button, gbc);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = text.getText();
                player_num = Integer.parseInt(userInput);
                new startOfGame(player_num);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    public static void main(String[] args) {
        new enterPlayerNum();
    }


}
