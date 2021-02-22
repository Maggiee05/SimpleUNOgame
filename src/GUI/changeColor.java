package GUI;

import javax.swing.*;
import java.awt.*;
public class changeColor {
    public changeColor() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));

        JRadioButton button1 = new JRadioButton("Red");
        JRadioButton button2 = new JRadioButton("Yellow");
        JRadioButton button3 = new JRadioButton("Green");
        JRadioButton button4 = new JRadioButton("Blue");

        JButton button = new JButton("Select");

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Select color you want to change");
        frame.pack();
        frame.setVisible(true);

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);

        panel.add(button);
    }

    public static void main(String[] args) {
        new changeColor();
    }
}
