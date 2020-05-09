package rouefortune.joueur;

import rouefortune.graphique.FenetrePrincipal;

import java.io.IOException;

public class AppClient {
    public static void main(String[] args) throws Exception {
        FenetrePrincipal fenetrePrincipal = new FenetrePrincipal();
        Joueur joueur = new Joueur("Ramon"/*args[0]*/);
        try {
            joueur.connectJoueur();
            joueur.jouer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

