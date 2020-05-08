package rouefortune.serveur;

import rouefortune.moteur.EnigmeRapide;
import rouefortune.moteur.TableauAffichage;

import java.util.concurrent.TimeUnit;

public class Manche {

    private int numeroManche;
    private Inventaire[] lesJoueurs;
    public int joueurDebutant;
    private TableauAffichage leTableau;
    public int interuptionIdJoueur;
    private EnigmeRapide enigmeRapide;


    public Manche(int i, Inventaire[] listeJoueur, TableauAffichage tableau) {
        this.numeroManche = i;
        this.lesJoueurs = listeJoueur;
        this.leTableau = tableau;
        this.joueurDebutant = -1;
        this.interuptionIdJoueur = -1;
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
     * Termine l'enigme rapide.
     */
    private void terminerEnigmeRapide() {
        this.enigmeRapide.stop();
    }

    private void determinerJoueurCommancant() {
        if(!this.leTableau.enigmeFini()){
            if(this.interuptionIdJoueur == -1){
            }else{//interuption par un joueur qui fait une proposition
                //si proposition = vrai alors il gagne 500€ et devient le joueur courrant
                /*if(this.leTableau.comparerProposition(this.lesJoueurs[this.interuptionIdJoueur].getProposition())){
                    this.lesJoueurs[this.interuptionIdJoueur].addCagnotePartie(500);
                    this.joueurDebutant = this.interuptionIdJoueur;
                }else{
                    this.interuptionIdJoueur = -1;
                    determinerJoueurCommancant();
                }*/
            }
        }else{//personne n'a trouver l'enigme le joueur 0 devient le 1er a jouer par defaut
            this.joueurDebutant = 0;
            commencerManche();
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
