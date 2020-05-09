package rouefortune.graphique;

import javax.swing.*;
import java.awt.*;

public class Panneau extends JPanel {

    @Override
    public void paintComponents(Graphics g) {
        System.out.println("coucou");
        g.setFont(new Font("Courier", Font.BOLD, 20));
        g.setColor(Color.RED);
        g.drawString("TEST", 10, 20);
    }
}
