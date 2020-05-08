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
        this.laManche = new Manche(1, this.listeJoueur, leTableau);
        this.laManche.commencerManche();
    }

    public void setRoueDesJoueurs(){
        for (Joueur joueur : this.listeJoueur) {
            joueur.setLaRoue(this.laRoue);
        }
    }

}
