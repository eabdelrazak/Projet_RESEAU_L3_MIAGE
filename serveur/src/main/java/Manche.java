import java.util.TimerTask;

public class Manche {

    private int numeroManche;
    private Joueur[] lesJoueurs;
    public int joueurCourrant;
    private TableauAffichage leTableau;

    public Manche(int i, Joueur[] listeJoueur,TableauAffichage tableau) {
        this.numeroManche = i;
        this.lesJoueurs = listeJoueur;
        this.leTableau = tableau;
        this.joueurCourrant = -1;
    }

    public void commencerManche(){
        if(this.joueurCourrant == -1){
            determinerJoueurCommancant();
        }else{
            jouer();
        }
    }

    private void determinerJoueurCommancant() {

    }

    public void jouer() {
        for(int i = 0; i < this.lesJoueurs.length; i++){
            tournerRoue(i);
        }
    }

    public void tournerRoue(int i){
        this.lesJoueurs[i].tournerLaRoue();
    }
}
