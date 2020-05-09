package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class Panneau extends JPanel {

    public static final int CONNEXION = 0;
    public static final int CONNECTED = 1;
    public static final int FAILURE = -1;

    public String enigme = "";
    public int state = Panneau.CONNEXION;

    public void init(Joueur joueur)  {
        Buzzer buzzer = new Buzzer("Buzz", joueur);
        this.add(buzzer);
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        Font font = new Font("Impact", Font.PLAIN, 64);

        if(state == Panneau.CONNEXION) {
            g.setFont(font);
            g.setColor(Color.BLACK);
            FontMetrics fm = g.getFontMetrics();
            int w = this.getWidth();
            int h = this.getHeight();
            String s = "Connexion en cours";
            int x = (w - fm.stringWidth(s)) / 2;
            int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
            g.drawString(s, x, y);
        }else if(state == Panneau.CONNECTED){
            Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
            attributes.put(TextAttribute.TRACKING, 0.5);
            Font font2 = font.deriveFont(attributes);
            g.setFont(font2);
            g.setColor(Color.BLACK);
            FontMetrics fm = g.getFontMetrics();
            int w = this.getWidth();
            int h = this.getHeight();
            String s = this.enigme;
            int x = (w - fm.stringWidth(s)) / 2;
            int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
            g.drawString(s, x, y);
        }else if(state == Panneau.FAILURE) {
            g.setFont(font);
            g.setColor(Color.RED);
            FontMetrics fm = g.getFontMetrics();
            int w = this.getWidth();
            int h = this.getHeight();
            String s = "Connexion echouee";
            int x = (w - fm.stringWidth(s)) / 2;
            int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
            g.drawString(s, x, y);
        }
    }

    public void setState(int state){
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
