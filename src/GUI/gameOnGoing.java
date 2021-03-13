package GUI;

import Cards.*;
import Main.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class gameOnGoing {
    protected Game game;
    protected ArrayList<JButton> buttons = new ArrayList<>();

    public gameOnGoing(Game game) {
        this.game = game;

        Player curPlayer = game.getCurPlayer();

        JFrame frame = new JFrame("textField");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));

        //showing the hand of the current player on the top if human player
        if ((curPlayer instanceof basicAI) || (curPlayer instanceof strategicAI)) {
            JLabel label = new JLabel("Current player is AI, cards are not revealed");
            panel.add(label);
        } else {
            showHand(curPlayer, panel);
        }

        //handling latest card from the discard pile
        showLatestCard(panel);

        //handling current state, including stack of cards and current player's turn
        JLabel labels = new JLabel("<html>Number of Cards to stack: " + game.getStack() +
                "<br> Current turn: " + curPlayer.getName());
        System.out.println("Now is " + curPlayer.getName() + " playing");
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        centerPanel.add(labels);
        panel.add(centerPanel, BorderLayout.CENTER);

        //handling skip, draw, change color button, and next for AI
        JButton skipButton = new JButton ("Skip");
        JButton drawButton = new JButton ("Draw Card");
        JButton colorButton = new JButton("Change Color"); //Only valid to click after a wild card is played
        JButton nextButton = new JButton("Next"); // Only valid when it's AI's turn

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

        if ((game.getPrevCard() instanceof WildCard) || (game.getPrevCard() instanceof WildDrawFourCard)) {
            if (curPlayer instanceof basicAI) {
                game.setPrevColor(((basicAI) curPlayer).pickColorRdm());
            } else if (curPlayer instanceof strategicAI) {
                game.setPrevColor(((strategicAI) curPlayer).pickColorSt());
            } else {
                JLabel label = new JLabel("Please wait for the previous player to change color");
                panel.add(label);
                selectColor(colorButton, panel, frame);
            }
        }

        //If the current player is skipped, human needs to click the skip button,
        // AI automatically skipped when the next button is clicked
        if (curPlayer.skipped()) {
            if ((curPlayer instanceof basicAI) || (curPlayer instanceof strategicAI)) {
                nextButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.resetFlag();
                        updateGame(frame);
                    }
                });
            } else {
                JLabel label = new JLabel("You are skipped. Please click skip button");
                panel.add(label);
                System.out.println("Current player is skipped. CLick the button!!!!!!!");
                skipButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.resetFlag();
                        updateGame(frame);
                    }
                });
            }
        }


        if (curPlayer.findValidCard() == null) { // no valid card in hand to play
            if ((curPlayer instanceof basicAI) || (curPlayer instanceof strategicAI)) { //AI
                userDraw(curPlayer, nextButton, frame);
            } else { //Human
                userDraw(curPlayer, drawButton, frame);
            }
        } else {
            if ((curPlayer instanceof basicAI) || (curPlayer instanceof strategicAI)) { //AI
                Card validC = curPlayer.findValidCard();
                panel.add(nextButton);
                nextButton.addActionListener(e -> {
                    curPlayer.playOneCard(validC);
                    if (game.checkGameOver(curPlayer)) {
                        new endOfGame(game);
                        frame.setVisible(false);
                    } else {
                        game.stateChange(validC);
                        updateGame(frame);
                    }
                });
            } else { //human
                for (Card c:curPlayer.getHand()) {
                    if (game.isValid(c)) {
                        clickAndPlay(c, curPlayer, frame);
                    }
                }
            }
        }

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
            button.setBackground(new Color(160,160,160));
        }
    }

    public void showHand(Player player, JPanel panel) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        ArrayList<Card> hand = player.getHand();
        int handSize = player.getHand().size();

        for (int i = 0; i < handSize; i++) {
            JButton cardButton = new JButton();
            cardButton.setOpaque(true);
            cardButton.setBorderPainted(false);
            cardButton.setText("Hided");
            cardButton.setBackground(Color.LIGHT_GRAY);
            cardButton.setPreferredSize(new Dimension (100, 120));
            buttons.add(cardButton);
            topPanel.add(cardButton);
        }

        JButton hideButton = new JButton("Show"); // Reveal/hide cards of the player of the current turn
        hideButton.addActionListener(ae -> {
            if (hideButton.getText().equals("Hide")) {
                for (JButton i:buttons) {
                    i.setText("Hided");
                    i.setBackground(Color.LIGHT_GRAY);
                }
                hideButton.setText("Show");
            } else {
                for (int i = 0; i < buttons.size(); i++) {
                    buttons.get(i).setText(hand.get(i).getVal());
                    setButtonColor(hand.get(i).getColor(), buttons.get(i));
                }
                hideButton.setText("Hide");
            }
        });
        topPanel.add(hideButton);
        panel.add(topPanel, BorderLayout.NORTH);
    }

    public void showLatestCard(JPanel panel) {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JLabel cardLabel = new JLabel("Latest Card:");
        leftPanel.add(cardLabel);

        Card latestCard = game.getPrevCard();
        JButton latestCardButton = new JButton();
        latestCardButton.setText(latestCard.getVal());
        setButtonColor(game.getPrevColor(), latestCardButton);
        latestCardButton.setPreferredSize(new Dimension (100, 120));
        leftPanel.add(latestCardButton);
        panel.add(leftPanel, BorderLayout.WEST);
    }

    /**
     * Handling the draw Button
     * The function is called only when there's no valid card in hand
     */
    public void userDraw(Player player, JButton button, JFrame frame) {
        if ((player instanceof basicAI) || (player instanceof strategicAI)) {
            frame.add(button);
            button.addActionListener(e -> {
                if (game.getStack() == 0) {
                    Card newCard = player.drawOneCard();
                    if (game.isValid(newCard)) {
                        player.playOneCard(newCard);
                    }
                } else {
                    game.drawAction(game.getStack());
                }
                updateGame(frame);
                frame.setVisible(false);
                frame.dispose();

            });
        } else if (player.drawFour() || player.drawTwo()) { //needs to draw more than 1 card, and miss a turn
            button.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame,"No valid card in hand. " + game.getStack() + " cards will be drawn.");
                game.drawAction(game.getStack());
                System.out.println("Current player draw " + game.getStack() + " cards");
                updateGame(frame);
                frame.setVisible(false);
                frame.dispose();
            });
        } else {
            button.addActionListener(e -> { //draw 1 card, if valid, play it
                JOptionPane.showMessageDialog(frame,"No valid card in hand. One card is drawn");
                Card newCard = player.drawOneCard();
                if (game.isValid(newCard)) {
                    player.playOneCard(newCard);
                    System.out.println("The card drawn is valid and is played");
                }
                updateGame(frame);
                frame.setVisible(false);
                frame.dispose();
            });
        }
        game.resetFlag();
    }

    public void updateGame(JFrame frame) { //For running output
        System.out.println("Direction is now: " + game.getDirection());
        System.out.println("Iterator is increased by 1");
        System.out.println("-----------------------");
        int idx = game.getPlayerIter(1);
        game.setCurPlayer(game.getPlayers().get(idx));
        game.incIter();
        new gameOnGoing(game);
        frame.setVisible(false);
        frame.dispose();
    }

    /**
     * Handling the Card button
     * When the valid card button is clicked, the card is played
     * If Invalid card is clicked, no effects are taken
     */
    public void clickAndPlay(Card c, Player p, JFrame frame) {
        int idx = p.getHand().indexOf(c);
        JButton bt = buttons.get(idx);
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.playCard(c);
                System.out.println("A " + c.getColor().toString()  + c.getVal() + " is played.");
                if (game.checkGameOver(p)) {
                    new endOfGame(game);
                    frame.setVisible(false);
                } else {
                    game.stateChange(c);
                    updateGame(frame);
                }
            }
        });
    }

    /**
     * Handling the changeColor Button
     */
    public void selectColor(JButton button, JPanel panel, JFrame frame) {
        if ((game.getPrevCard() instanceof WildDrawFourCard) || (game.getPrevCard() instanceof WildCard)) {
            button.addActionListener(ae -> {
                new changeColor(game);
                showLatestCard(panel);
                frame.setVisible(false);
            });
        }
    }

//    public static void main(String[] args) {
//        new gameOnGoing(game);
//    }
}
