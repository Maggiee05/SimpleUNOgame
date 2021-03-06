package GUI;

import Main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class endOfGame {
    Game game;
    public endOfGame(Game game) {
        this.game = game;
        JFrame frame = new JFrame("End of Game");
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridBagLayout());

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game Over");
        frame.pack();
        frame.setVisible(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel label = new JLabel("The winner is: " + game.getWinner().getName());
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        JButton button = new JButton("Start a new game");
        panel.add(button, gbc);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new enterPlayerNum();
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

//    public static void main(String[] args) {
//        new endOfGame();
//    }

}