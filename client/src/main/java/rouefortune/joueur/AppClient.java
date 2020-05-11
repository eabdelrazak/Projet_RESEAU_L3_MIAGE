package rouefortune.joueur;

import rouefortune.graphique.FenetrePrincipal;

import java.io.IOException;

public class AppClient {
    public static void main(String[] args) {
        Client client = new Client();
        Joueur joueur;
        if(args.length > 0) {
            joueur = new Joueur(args[0], client);
        }else{
            joueur = new Joueur("NoName", client);
        }
        client.setJoueur(joueur);
        FenetrePrincipal fenetrePrincipal = new FenetrePrincipal(joueur);
        client.setFenetrePrincipal(fenetrePrincipal);
        fenetrePrincipal.repaint();
    }

}

