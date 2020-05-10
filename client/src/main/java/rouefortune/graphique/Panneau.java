package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class Panneau extends JPanel {

    public static final int CONNEXION = 0;
    public static final int ENIGME_RAPIDE = 11;
    public static final int ENIGME_NORMALE = 21;
    public static final int FAILURE = -1;
    public static final int CONNECTED = 1;

    public String enigme = "";
    public Joueur joueur;
    public Buzzer buzzer;
    public int state = Panneau.CONNEXION;
    public String theme = "";
    public PropositionTexte textfield;

    public void init(Joueur joueur)  {
        this.joueur = joueur;
        this.buzzer = new Buzzer("Buzz", joueur);
        this.buzzer.setVisible(false);
        this.add(buzzer);

        this.textfield = new PropositionTexte(joueur);
        this.textfield.setVisible(false);
        this.add(textfield);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Font font = new Font("Impact", Font.PLAIN, 64);
        Font pseudoFont = new Font("Impact", Font.PLAIN, 20);
        g.setFont(pseudoFont);
        g.setColor(Color.BLACK);
        g.drawString("Pseudo: "+this.joueur.getNomJoueur(), 5, 20);
        if(state == Panneau.CONNEXION) {
            g.setFont(font);
            g.setColor(Color.BLACK);
            String s = "Connexion en cours";
            this.drawMiddle(this.getWidth()/2, this.getHeight()/2, g, s);
        }else if(state == Panneau.CONNECTED){
            g.setFont(font);
            g.setColor(Color.BLACK);
            String s = "En attente de joueurs";
            this.drawMiddle(this.getWidth()/2, this.getHeight()/2, g, s);
        }else if(state == Panneau.FAILURE) {
            g.setFont(font);
            g.setColor(Color.RED);
            String s = "Connexion echouee";

            this.drawMiddle(this.getWidth()/2, this.getHeight()/2, g, s);
        }else if(state == Panneau.ENIGME_RAPIDE || state == Panneau.ENIGME_NORMALE){
            Map<TextAttribute, Object> attributes = new HashMap<>();
            attributes.put(TextAttribute.TRACKING, 0.5);
            Font font2 = font.deriveFont(attributes);
            g.setFont(font2);
            g.setColor(Color.BLACK);
            this.drawMiddle(this.getWidth()/2, this.getHeight()/2, g, this.enigme);

            g.setFont(font);
            this.drawMiddle(this.getWidth()/2, 30, g, "Enigme Rapide");
            g.setFont(new Font("Impact", Font.PLAIN, 48));
            this.drawMiddle(this.getWidth()/2, 90, g, "Theme: "+this.theme);



        }
    }

    public void drawMiddle(int x, int y, Graphics g, String s){
        FontMetrics fm = g.getFontMetrics();

        x = (x - fm.stringWidth(s) / 2);
        y = (fm.getAscent() + (y - (fm.getAscent() + fm.getDescent()) / 2));
        g.drawString(s, x, y);
    }

    public void setState(int state){
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
