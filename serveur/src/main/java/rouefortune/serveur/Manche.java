package rouefortune.serveur;

import rouefortune.moteur.EnigmeRapide;
import rouefortune.moteur.TableauAffichage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Manche {

    private int numeroManche;
    /*private Inventaire[] lesJoueurs;*/
    private ArrayList<ClientHandler> clientHandlers;
    public Socket joueurDebutant;
    private Serveur serveur;
    private TableauAffichage leTableau;
    private EnigmeRapide enigmeRapide;
    private Random rand;


    public Manche(int i, Serveur serveur, TableauAffichage tableau) {
        this.numeroManche = i;
        this.serveur = serveur;
        this.leTableau = tableau;
        this.joueurDebutant = null;
        this.rand = new Random();
    }

    public void commencerManche(){
        /*if(this.joueurDebutant == -1){
            jouerEnigmeRapide();
        }else{
            jouerEnigmeLongue();
        }*/
        jouerEnigmeRapide();
    }

    /**
     * Commence l'enigme rapide et la révélation des lettres
     */
    private void jouerEnigmeRapide() {
        int random_un = rand.nextInt(Serveur.tabEnigmes.length);
        this.leTableau.setEnigmeADeviner(Serveur.tabEnigmes[random_un][0], Serveur.tabEnigmes[random_un][1]);
        this.enigmeRapide = new EnigmeRapide(this.leTableau, this.serveur);
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

    /**
     * Permet a un joueur de faire une proposition a l'enigme rapide
     */
    public void faireUnePropositionEnigmeRapide(ClientHandler client, String proposition){
        this.pauseEnigmeRapide();
        boolean resultatProposition = this.enigmeRapide.faireProposition(proposition);
        if(resultatProposition == false){
            repriseEnigmeRapide();
        }else{
            terminerEnigmeRapide();
            this.joueurDebutant = client.s;
            client.getInventaire().addCagnotePartie(500);
        }
    }

    public void jouerEnigmeLongue() {
        /*for(int i = this.joueurDebutant; i < this.lesJoueurs.length; i++){
            tournerRoue(i);
        }
        for (int i = 0; i < this.joueurDebutant; i++){
            tournerRoue(i);
        }*/
    }

    public void tournerRoue(int i){
        //this.lesJoueurs[i].tournerLaRoue();
    }
}
