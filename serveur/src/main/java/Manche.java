import java.util.TimerTask;

public class Manche {

    private int numeroManche;
    private Joueur[] lesJoueurs;
    public int joueurDebutant;
    private TableauAffichage leTableau;
    public static int interuptionIdJoueur;

    public Manche(int i, Joueur[] listeJoueur,TableauAffichage tableau) {
        this.numeroManche = i;
        this.lesJoueurs = listeJoueur;
        this.leTableau = tableau;
        this.joueurDebutant = -1;
        this.interuptionIdJoueur = -1;
    }

    public void commencerManche(){
        if(this.joueurDebutant == -1){
            determinerJoueurCommancant();
        }else{
            jouer();
        }
    }

    private void determinerJoueurCommancant() {
        if(this.leTableau.enigmeFini() == false){
            if(this.interuptionIdJoueur == -1){
                this.leTableau.revelerLettre();
                determinerJoueurCommancant(); //a lancer chaque x seconde
            }else{//interuption par un joueur qui fait une proposition
                //si proposition = vrai alors il gagne 500€ et devient le joueur courrant
                if(this.leTableau.comparerProposition(this.lesJoueurs[this.interuptionIdJoueur].getProposition()) == true){
                    this.lesJoueurs[this.interuptionIdJoueur].addCagnotePartie(500);
                    this.joueurDebutant = this.interuptionIdJoueur;
                }else{
                    this.interuptionIdJoueur = -1;
                    determinerJoueurCommancant();
                }
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
        this.lesJoueurs[i].tournerLaRoue();
    }
}
