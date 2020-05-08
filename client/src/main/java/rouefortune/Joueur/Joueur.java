package rouefortune.Joueur;

public class Joueur {

    private int numeroJoueur;
    private int cagnotePartie;
    private int cagnoteManche;
    private String proposition;

    public Joueur(int numP){
        this.numeroJoueur = numP;
        this.cagnotePartie = 0;
        this.cagnoteManche = 0;
        this.proposition = " ";
    }

    public void tournerLaRoue(){
        //String bonus = this.laRoue.tourner();
    }

    public String getProposition() {
        return proposition;
    }
}
