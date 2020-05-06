public class Manche {

    private int numeroManche;
    private Joueur[] lesJoueurs;
    public int joueurCourrant;

    public Manche(int i, Joueur[] listeJoueur) {
        this.numeroManche = i;
        this.lesJoueurs = listeJoueur;
        this.joueurCourrant = 0;
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
