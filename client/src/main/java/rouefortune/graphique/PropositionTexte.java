package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PropositionTexte extends JTextField implements KeyListener {
    private final Joueur joueur;

    public static final int ENIGME_RAPIDE = 0;
    public static final int ENIGME_NORMALE = 1;
    public static final int ENIGME_NORMALE_BUYER = 2;
    public static final int ENIGME_NORMALE_GUESSER = 3;
    private Panneau panneau;

    public int state;
    PropositionTexte(Joueur joueur, int state, Panneau panneau){
        super();
        this.state = state;
        this.panneau = panneau;
        this.joueur = joueur;
        this.setFont(new Font("Impact", Font.PLAIN, 64));
        this.setBounds(200, 425, 400, 70);
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //NOTHING
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //NOTHING
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            System.out.println(this.getText());
            if(state == PropositionTexte.ENIGME_RAPIDE) {
                this.joueur.getClient().sendProposition("rapide;"+this.getText());
            }else if(state == PropositionTexte.ENIGME_NORMALE){
                if(this.getText().length()==1) {
                    char c = this.getText().charAt(0);
                    if(!(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y')){
                        this.joueur.getClient().sendPropositionLettre(this.getText());
                    }
                }
            }else if(state == PropositionTexte.ENIGME_NORMALE_BUYER){
                if(this.getText().length()==1) {
                    char c = this.getText().charAt(0);
                    if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y') {
                        if (this.joueur.getCagnotteManche() >= 200) {
                            this.joueur.addCagnotteManche(1, -200);
                            this.joueur.getClient().sendPropositionLettre(this.getText());
                            this.setVisible(false);
                        }
                    }
                }
            }else if(state == PropositionTexte.ENIGME_NORMALE_GUESSER){
                this.joueur.getClient().sendProposition("normale;"+this.getText());
            }
            this.setText("");
        }
    }
}
