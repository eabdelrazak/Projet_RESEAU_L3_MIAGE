package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PropositionTexte extends JTextField implements KeyListener {
    private final Joueur joueur;

    PropositionTexte(Joueur joueur){
        super();
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
            this.joueur.getClient().sendProposition(this.getText());
        }
    }
}
