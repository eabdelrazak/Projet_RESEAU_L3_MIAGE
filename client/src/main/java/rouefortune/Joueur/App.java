package rouefortune.Joueur;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        Joueur joueur = new Joueur(args[0]);
        try {
            joueur.connectJoueur();
            joueur.jouer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

