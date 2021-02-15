package GUI;

import Cards.*;
import Main.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class gameOnGoing extends JComponent{
    public gameOnGoing() {
        JFrame frame = new JFrame("textField");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));

        //handling cards of the player of current turn, and hide/show
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));

        Game game = new Game();
        ArrayList<Player> players = new ArrayList<>();
        Player temp_player = new Player("temp", game);
        players.add(temp_player);
        game.addPlayer(players);
        int handSize = 7; // 7 for now, demo
        temp_player.assignCards();

        ArrayList<Card> hand = temp_player.getHand();
        for (int i = 0; i < handSize; i++) {
            JButton cardButton = new JButton();
            cardButton.setText(hand.get(i).getVal());
            setButtonColor(hand.get(i).getColor(), cardButton);
            cardButton.setPreferredSize(new Dimension (100, 120));
            topPanel.add(cardButton);
        }

        JButton hideButton = new JButton("Hide"); // Reveal/hide cards of the player of the current turn
        hideButton.addActionListener(ae -> {
            if (hideButton.getText().equals("Hide")) {

                hideButton.setText("Show");
            } else {
                hideButton.setText("Hide");
            }
        });
        topPanel.add(hideButton);
        panel.add(topPanel, BorderLayout.NORTH);

        //handling latest card from the discard pile
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JLabel cardLabel = new JLabel("Latest Card:");
        leftPanel.add(cardLabel);

        JButton latestCardButton = new JButton(); //pick a temporary latest card for now
        latestCardButton.setText(hand.get(0).getVal());
        setButtonColor(hand.get(0).getColor(), latestCardButton);
        latestCardButton.setPreferredSize(new Dimension (100, 120));
        leftPanel.add(latestCardButton);
        panel.add(leftPanel, BorderLayout.WEST);

        //handling current state, including stack of cards and current player's turn
        JLabel labels = new JLabel("<html>Number of Cards to stack: <br> Current turn: ");
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        centerPanel.add(labels);
        panel.add(centerPanel, BorderLayout.CENTER);

        //handling skip, draw, change color button
        JButton skipButton = new JButton ("Skip");
        JButton drawButton = new JButton ("Draw Card");
        JButton colorButton = new JButton("Change Color"); //Only valid to click after a wild card is played
        colorButton.addActionListener(ae -> {
            new changeColor();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        bottomPanel.add(skipButton);
        bottomPanel.add(drawButton);
        bottomPanel.add(colorButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("UNO Game");
        frame.pack();
        frame.setVisible(true);
    }

    public void setButtonColor(Card.Colors color, JButton button) {
        button.setOpaque(true);
        button.setBorderPainted(false);
        if (color == Card.Colors.RED) {
            button.setBackground(new Color(239, 63, 63, 223));
        } else if (color == Card.Colors.YELLOW) {
            button.setBackground(new Color(243,238,82));
        } else if (color == Card.Colors.GREEN) {
            button.setBackground(new Color(97, 250, 97, 188));
        } else if (color == Card.Colors.BLUE) {
            button.setBackground(new Color(25,138,240));
        } else {
            button.setBackground(new Color(204,204,204));
        }
    }


    public static void main(String[] args) {
        new gameOnGoing();
    }


}
