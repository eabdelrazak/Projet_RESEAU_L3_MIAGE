package rouefortune.serveur;

import rouefortune.moteur.Roue;
import rouefortune.moteur.TableauAffichage;

public class Partie {

    private final Serveur serveur;
    private int numeroPartie;
    private Inventaire[] listeJoueur;
    private Manche laManche;
    private Roue laRoue;
    private TableauAffichage leTableau;

    public Partie(int numP, Inventaire[] joueurs, Serveur serveur){
        this.numeroPartie = numP;
        this.listeJoueur = joueurs;
        this.serveur = serveur;
        this.laManche = null;
        this.laRoue = new Roue();
        this.leTableau = new TableauAffichage(this);
    }

    public void commencer(){
        this.laManche = new Manche(1, this.listeJoueur, leTableau);
        this.laManche.commencerManche();
    }

}
