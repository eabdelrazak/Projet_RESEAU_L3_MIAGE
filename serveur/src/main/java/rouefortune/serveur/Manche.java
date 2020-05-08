package rouefortune.serveur;

import rouefortune.moteur.EnigmeRapide;
import rouefortune.moteur.TableauAffichage;

import java.util.concurrent.TimeUnit;

public class Manche {

    private int numeroManche;
    private Inventaire[] lesJoueurs;
    public int joueurDebutant;
    private TableauAffichage leTableau;
    private EnigmeRapide enigmeRapide;


    public Manche(int i, Inventaire[] listeJoueur, TableauAffichage tableau) {
        this.numeroManche = i;
        this.lesJoueurs = listeJoueur;
        this.leTableau = tableau;
        this.joueurDebutant = -1;
    }

    public void commencerManche(){
        if(this.joueurDebutant == -1){
            jouerEnigmeRapide();
        }else{
            jouer();
        }
    }

    /**
     * Commence l'enigme rapide et la révélation des lettres
     */
    private void jouerEnigmeRapide() {
        this.enigmeRapide = new EnigmeRapide(this.leTableau);
        this.enigmeRapide.resume();
    }

    /**
     * Mets en pause la révélation des lettres  de l'enigme rapide.
     */
    private void pauseEnigmeRapide() {
        this.enigmeRapide.pause();
    }

    /**
     * Remets en route la révélation des lettres  de l'enigme rapide.
     */
    private void repriseEnigmeRapide() {
        this.enigmeRapide.resume();
    }

    /**
     * Termine l'enigme rapide.
     */
    private void terminerEnigmeRapide() {
        this.enigmeRapide.stop();
    }

    public void faireUneProposition(int idJoueur, String proposition){
        this.pauseEnigmeRapide();
        boolean resultatProposition = this.enigmeRapide.faireProposition(proposition);
        if(resultatProposition == false){
            repriseEnigmeRapide();
        }else{
            terminerEnigmeRapide();
            this.joueurDebutant = idJoueur;
            this.lesJoueurs[idJoueur].addCagnotePartie(500);
        }
    }

    public void jouer() {
        for(int i = this.joueurDebutant; i < this.lesJoueurs.length; i++){
            tournerRoue(i);
        }
        for (int i = 0; i < this.joueurDebutant; i++){
            tournerRoue(i);
        }
    }

    public void tournerRoue(int i){
        //this.lesJoueurs[i].tournerLaRoue();
    }
}
