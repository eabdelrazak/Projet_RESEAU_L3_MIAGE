package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InformationTexte extends JTextField {

    public static final int PSEUDO = 0;
    public static final int IP = 1;
    public static final int PORT = 2;
    private Panneau panneau;

    public int state;
    InformationTexte(int x, int y, int state, Panneau panneau){
        super();
        this.state = state;
        this.panneau = panneau;
        this.setFont(new Font("Impact", Font.PLAIN, 64));
        this.setBounds(x, y, 400, 70);
    }
}
