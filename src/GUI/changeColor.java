package GUI;

import Cards.Card;
import Main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class changeColor {
    Game game;
    private JRadioButton button1;
    private JRadioButton button2;
    private JRadioButton button3;
    private JRadioButton button4;

    public changeColor(Game game) {
        this.game = game;
        humanSelectColor();
    }

    public void humanSelectColor() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));

        button1 = new JRadioButton("Red");
        button2 = new JRadioButton("Yellow");
        button3 = new JRadioButton("Green");
        button4 = new JRadioButton("Blue");

        button1.setActionCommand("Red");
        button2.setActionCommand("Yellow");
        button3.setActionCommand("Green");
        button4.setActionCommand("Blue");

        ButtonGroup bg = new ButtonGroup();
        bg.add(button1);
        bg.add(button2);
        bg.add(button3);
        bg.add(button4);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Select color you want to change");
        frame.pack();
        frame.setVisible(true);

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);


        JButton OKbutton = new JButton("OK");
        panel.add(OKbutton);
        OKbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String color = bg.getSelection().getActionCommand();
                colorChange(color);
                System.out.println("Color is changed to: " + color);
                new gameOnGoing(game);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

    public void colorChange(String color) {
        switch (color) {
            case "Red":
                game.setPrevColor(Card.Colors.RED);
                break;
            case "Yellow":
                game.setPrevColor(Card.Colors.YELLOW);
                break;
            case "Green":
                game.setPrevColor(Card.Colors.GREEN);
                break;
            case "Blue":
                game.setPrevColor(Card.Colors.BLUE);
                break;
        }
    }

//    public static void main(String[] args) {
//        new changeColor(game);
//    }
}
