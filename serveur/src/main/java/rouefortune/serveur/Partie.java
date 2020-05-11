package rouefortune.serveur;

import rouefortune.moteur.Roue;
import rouefortune.moteur.TableauAffichage;

public class Partie {

    private final Serveur serveur;
    private int numeroManche;
    private int numeroPartie;
    /*private Inventaire[] listeJoueur;*/
    private Manche laManche;
    private Roue laRoue;
    private TableauAffichage leTableau;

    public Partie(/*int numP, Inventaire[] joueurs,*/ Serveur serveur){
        /*this.numeroPartie = numP;
        this.listeJoueur = joueurs;*/
        this.numeroManche = 0;
        this.serveur = serveur;
        this.laManche = null;
        this.laRoue = new Roue();
        this.leTableau = new TableauAffichage(this);
    }

    public void commencer(){
        this.laManche = new Manche(1, this.serveur, this.leTableau, this.laRoue);
        this.laManche.commencerManche();
    }

    public void prochaineManche(){
        if(this.numeroManche<4) {
            this.numeroManche++;
            this.laManche = new Manche(this.numeroManche, this.serveur, this.leTableau, this.laRoue);
            this.laManche.commencerManche();
        }else{
            this.fin();
        }
    }

    private void fin() {
        this.serveur.annoncerGagnant();
    }

    public Manche getLaManche() {
        return laManche;
    }
}
