public class Partie {

    private int numeroPartie;
    private Joueur[] listeJoueur;
    private Manche laManche;
    private Roue laRoue;
    private TableauAffichage leTableau;

    public Partie(int numP, Joueur[] joueurs){
        this.numeroPartie = numP;
        this.listeJoueur = joueurs;
        this.laManche = null;
        this.laRoue = new Roue();
        this.leTableau = new TableauAffichage();
        setRoueDesJoueurs();
    }

    public void commencer(){
        Manche manche = new Manche(1, this.listeJoueur);
        this.laManche = manche;
        this.laManche.jouer();
    }

    public void setRoueDesJoueurs(){
        for(int i = 0; i < this.listeJoueur.length; i++){
            this.listeJoueur[i].setLaRoue(this.laRoue);
        }
    }

}
