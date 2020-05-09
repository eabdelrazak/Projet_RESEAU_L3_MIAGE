package rouefortune.joueur;

import rouefortune.graphique.FenetrePrincipal;

import java.io.IOException;

public class AppClient {
    public static void main(String[] args) throws Exception {
        Joueur joueur = new Joueur("Ramon"/*args[0]*/);
        FenetrePrincipal fenetrePrincipal = new FenetrePrincipal(joueur);
        try {
            joueur.connectJoueur();
            joueur.jouer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

