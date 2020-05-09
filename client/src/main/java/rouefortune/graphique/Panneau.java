package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.swing.*;
import java.awt.*;

public class Panneau extends JPanel {


    public void init(Joueur joueur)  {
        Buzzer buzzer = new Buzzer("Buzz", joueur);
        this.add(buzzer);
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setFont(new Font("Impact", Font.PLAIN, 64));
        g.setColor(Color.RED);
        FontMetrics fm = g.getFontMetrics();
        int w = this.getWidth();
        int h = this.getHeight();
        String s = "Connexion en cours";
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }
}
