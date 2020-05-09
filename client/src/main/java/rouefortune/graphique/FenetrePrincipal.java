package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.swing.*;

public class FenetrePrincipal extends JFrame {

    private Joueur joueur;
    Panneau pan;

    public FenetrePrincipal(Joueur joueur){
        this.joueur = joueur;
        this.setTitle("Roue de la Fortune - JBANT");
        this.setSize(800, 600);
        //this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pan = new Panneau();
        pan.init(joueur);

        this.setContentPane(this.pan);
        this.setVisible(true);
    }


    public void afficherMessageErreurConnexion(){

    }
}
