package rouefortune.serveur;

import rouefortune.Messages;
import rouefortune.moteur.EnigmeNormale;
import rouefortune.moteur.EnigmeRapide;
import rouefortune.moteur.Roue;
import rouefortune.moteur.TableauAffichage;

import java.io.IOException;
import java.util.Random;

public class Manche {

    private int numeroManche;
    public ClientHandler joueurActuel;
    private Serveur serveur;
    private TableauAffichage leTableau;
    private Roue laRoue;
    private EnigmeRapide enigmeRapide;
    private Random rand;
    private EnigmeNormale enigmeNormale;


    public Manche(int i, Serveur serveur, TableauAffichage tableau, Roue roue) {
        this.numeroManche = i;
        this.serveur = serveur;
        this.leTableau = tableau;
        this.laRoue = roue;
        this.joueurActuel = null;
        this.rand = new Random();
    }

    public void commencerManche(){
        if(this.joueurActuel == null){
            jouerEnigmeRapide();
        }else{
            jouerEnigmeLongue();
        }
    }

    /**
     * Commence l'enigme rapide et la révélation des lettres
     */
    private void jouerEnigmeRapide() {
        this.setRandomEnigme();
        this.enigmeRapide = new EnigmeRapide(this.leTableau, this.serveur);
        this.enigmeRapide.resume();
    }

    /**
     * Mets en pause la révélation des lettres  de l'enigme rapide.
     */
    public void pauseEnigmeRapide() {
        this.enigmeRapide.pause();
    }

    /**
     * Remets en route la révélation des lettres  de l'enigme rapide.
     */
    public void repriseEnigmeRapide() {
        for(ClientHandler client : this.serveur.getClientHandlers()){
            try {
                client.getDos().writeUTF(this.serveur.creerMessageJsonObject(Messages.REPRENDRE, "Le mot n'a pas été trouvée, ça reprend !!!"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.enigmeRapide.resume();
    }

    /**
     * Termine l'enigme rapide.
     */
    public void terminerEnigmeRapide() {
        this.enigmeRapide.stop();
    }

    public void jouerEnigmeLongue() {
        this.setRandomEnigme();
        this.enigmeNormale = new EnigmeNormale(this.leTableau, this.serveur);
    }

    private void setRandomEnigme() {
        int random_un;
        do {
            random_un = rand.nextInt(this.serveur.tabEnigmes.length);
        }while(this.serveur.tabEnigmes[random_un] == null);
        this.leTableau.setEnigmeADeviner(this.serveur.tabEnigmes[random_un][0], this.serveur.tabEnigmes[random_un][1]);
        this.serveur.enleverEnigme(random_un);
    }

    public void passerLaMain(){
        for(int i=0; i<this.serveur.getClientHandlers().size(); i++){
            if(this.serveur.getClientHandlers().get(i) == joueurActuel){
                if(i < this.serveur.getClientHandlers().size()-1) {
                    joueurActuel = this.serveur.getClientHandlers().get(i + 1);
                }else{
                    joueurActuel = this.serveur.getClientHandlers().get(0);
                }
            }
        }
    }

    public void passe(){

    }

    public String tournerRoue(){
        return this.laRoue.tourner();
    }

    public void setJoueurActuel(ClientHandler joueurActuel) {
        this.joueurActuel = joueurActuel;
    }

    public TableauAffichage getLeTableau() {
        return leTableau;
    }

}
