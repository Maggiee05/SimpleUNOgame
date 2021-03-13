package GUI;

import Main.Game;
import Main.Player;
import Main.basicAI;
import Main.strategicAI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class startOfGame {
    public int player_num;
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<String> types = new ArrayList<>();

    public startOfGame(int player_num) {
        this.player_num = player_num;

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

        JLabel l = new JLabel("Select type of player: ");
        panel.add(l, gbc);

        String[] idx = {"Human", "Basic AI", "Strategic AI"};

        for (int i = 0; i < player_num; i++) {
            JLabel playerL = new JLabel("Player " + i + " ");
            JComboBox cb = new JComboBox(idx);
            cb.setEditable(true);
            gbc.gridx = 1;
            gbc.gridy = i+1;
            panel.add(playerL, gbc);
            gbc.gridx = 2;
            panel.add(cb, gbc);

            ActionListener cbActionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String val = cb.getSelectedItem().toString();
                    types.add(val);
                }
            };

            cb.addActionListener(cbActionListener);
            //cb.setSelectedIndex(0);
        }

        gbc.gridy = player_num + 2;
        gbc.gridx = 3;
        JCheckBox cb = new JCheckBox("Reverse On Black");
        panel.add(cb, gbc);
        cb.setSelected(false);

        gbc.gridy = player_num + 3;
        JButton startButton = new JButton("START");
        panel.add(startButton, gbc);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game();
                cb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (cb.isSelected()) {
                            game.setReverseOnB(true);
                        }
                    }
                });

                for (int i = 0; i < player_num; i++) { //declare the player type
                    String player_type = types.get(i);
                    Player newPlayer;
                    if (player_type.equals("Human")) {
                        newPlayer = new Player("Player " + i + " (Human)", game);
                    } else if (player_type.equals("Basic AI")) {
                        newPlayer = new basicAI("Player " + i + " (Basic AI)", game);
                    } else {
                        newPlayer = new strategicAI("Player " + i + " (Strategic AI)", game);
                    }
                    players.add(newPlayer);
//                    System.out.println(newPlayer.getName());
                }
                System.out.println(types);
                game.newGame(players);
                new gameOnGoing(game);
                frame.setVisible(false);
                frame.dispose();
            }
        });
        System.out.println(types);
    }

    public static void main(String[] args) {
        new startOfGame(4);
    }
}
