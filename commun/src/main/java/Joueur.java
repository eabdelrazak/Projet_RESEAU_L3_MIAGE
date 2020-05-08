public class Joueur {

    private int numeroJoueur;
    private Roue laRoue;
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
        String bonus = this.laRoue.tourner();
    }

    public void setLaRoue(Roue laRoue) {
        this.laRoue = laRoue;
    }

    public String getProposition() {
        return proposition;
    }

    public void addCagnotePartie(int somme){
        this.cagnotePartie += somme;
    }

    public void addCagnoteManche(int somme){
        this.cagnoteManche += somme;
    }
}
