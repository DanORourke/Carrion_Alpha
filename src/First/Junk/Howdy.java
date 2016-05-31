package First.Junk;

import javax.swing.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Howdy {

    public Howdy(){

        JFrame frame = new JFrame("Carrion Howdy");
        JLabel picLabel = new JLabel(new StretchIcon("C:/Users/Dan/IdeaProjects/Learning/art/background/map2.jpg"));

        frame.add(picLabel);
        frame.setResizable(true);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

    }
}
