package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class Panneau extends JPanel {

    public static final int CONNEXION = 0;
    public static final int FAILURE = -1;
    public static final int CONNECTED = 1;
    public static final int ENIGME_RAPIDE = 11;
    public static final int FIN_ENIGME_RAPIDE = 12;
    public static final int ENIGME_NORMALE = 21;
    public static final int FIN_ENIGME_NORMALE = 22;
    public static final int END = -2;


    public String enigme = "";
    public Joueur joueur;
    public Buzzer buzzer, tournerroue;
    public int state = Panneau.CONNEXION;
    public String theme = "";
    public PropositionTexte textfield, lettrefield;
    public String gagnant = "";
    public String joueurActuel = "";
    public String resultatRoue = "";
    public Guesser guesser;
    public Buyer buyer;

    public void init(Joueur joueur)  {
        this.joueur = joueur;
        this.buzzer = new Buzzer("Buzz", joueur, Buzzer.ENIGME_RAPIDE);
        this.buzzer.setVisible(false);
        this.add(buzzer);

        this.tournerroue = new Buzzer("Roue", joueur, Buzzer.ENIGME_NORMALE);
        this.tournerroue.setVisible(false);
        this.add(tournerroue);

        this.guesser = new Guesser("Guess", joueur, this);
        this.guesser.setVisible(false);
        this.add(guesser);

        this.buyer = new Buyer("Achat", joueur, this);
        this.buyer.setVisible(false);
        this.add(buyer);

        this.textfield = new PropositionTexte(joueur, PropositionTexte.ENIGME_RAPIDE, this);
        this.textfield.setVisible(false);
        this.add(textfield);

        this.lettrefield = new PropositionTexte(joueur, PropositionTexte.ENIGME_NORMALE, this);
        this.lettrefield.setVisible(false);
        this.add(lettrefield);
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
        g.drawString("Cagnotte Totale: "+this.joueur.getCagnottePartie()+"€", 5, 40);
        g.drawString("Cagnotte Manche: "+this.joueur.getCagnotteManche()+"€", 5, 60);
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
        }else if(state == Panneau.ENIGME_RAPIDE){
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
        }else if(state == Panneau.FIN_ENIGME_RAPIDE){
            font = new Font("Impact", Font.PLAIN, 48);
            g.setFont(font);
            g.setColor(Color.BLACK);
            this.drawMiddle(this.getWidth()/2, this.getHeight()/2, g, "Fin de l'enigme rapide !");
            this.drawMiddle(this.getWidth()/2, this.getHeight()/2+50, g, this.gagnant+" gagne l'enigme rapide !");
        }else if(state == Panneau.ENIGME_NORMALE){
            g.setFont(pseudoFont);
            g.setColor(Color.BLACK);
            this.drawMiddle(this.getWidth()/2, 200, g, "Joueur actuel: "+this.joueurActuel);
            this.drawMiddle(this.getWidth()/2, 375, g, this.resultatRoue);
            Map<TextAttribute, Object> attributes = new HashMap<>();
            attributes.put(TextAttribute.TRACKING, 0.5);
            Font font2 = font.deriveFont(attributes);
            g.setFont(font2);
            this.drawMiddle(this.getWidth()/2, this.getHeight()/2, g, this.enigme);
            g.setFont(font);
            this.drawMiddle(this.getWidth()/2, 30, g, "Enigme Normale");
            g.setFont(new Font("Impact", Font.PLAIN, 48));
            this.drawMiddle(this.getWidth()/2, 90, g, "Theme: "+this.theme);
        }else if(state == Panneau.FIN_ENIGME_NORMALE){
            font = new Font("Impact", Font.PLAIN, 48);
            g.setFont(font);
            g.setColor(Color.BLACK);
            this.drawMiddle(this.getWidth()/2, this.getHeight()/2, g, "Fin de l'enigme normale !");
            this.drawMiddle(this.getWidth()/2, this.getHeight()/2+50, g, this.gagnant+" gagne l'enigme normale !");
        }else if(state == Panneau.END){
            g.setFont(font);
            g.setColor(Color.BLACK);
            String s = "Partie terminee";
            this.drawMiddle(this.getWidth()/2, this.getHeight()/2, g, s);
            this.drawMiddle(this.getWidth()/2, this.getHeight()/2+40, g, "Vainqueur: "+this.gagnant);
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

    public void disableButtons() {
        this.tournerroue.setVisible(false);
        this.buyer.setVisible(false);
        this.guesser.setVisible(false);
    }

    public void enableButtons(){
        this.tournerroue.setVisible(true);
        if(this.joueur.getCagnotteManche() >= 200) {
            this.buyer.setVisible(true);
        }
        this.guesser.setVisible(true);
    }
}
