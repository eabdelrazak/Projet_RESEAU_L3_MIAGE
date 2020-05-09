package rouefortune.joueur;

import rouefortune.graphique.FenetrePrincipal;

import java.io.IOException;

public class AppClient {
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        Joueur joueur = new Joueur("Ramon"/*args[0]*/, client);
        client.setJoueur(joueur);
        FenetrePrincipal fenetrePrincipal = new FenetrePrincipal(joueur);
        client.setFenetrePrincipal(fenetrePrincipal);
        try {
            client.connectJoueur();
            client.boucleReceptionMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

