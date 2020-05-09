package rouefortune.serveur;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        Serveur serveur = new Serveur(1/*Integer.parseInt(args[0])*/);

        /*try {
            serveur.lireFichierEnigme();
            System.out.println(serveur.tabEnigmes[0][1]);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        serveur.debutPartie();
    }
}
