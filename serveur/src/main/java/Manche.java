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
        if(this.leTableau.enigmeFini() == false){
            this.leTableau.revelerLettre(); //a lancer chaque seconde
            //interuption par un joueur qui fait une proposition
            //si proposition = vrai alors il gagne 500€ et devient le joueur courrant
        }else{//personne n'a trouver l'enigme le joueur 0 devient le 1er a jouer par defaut
            this.joueurCourrant = 0;
            commencerManche();
        }
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
