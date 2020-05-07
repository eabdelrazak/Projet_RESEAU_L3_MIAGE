public class Joueur {

    private int numeroJoueur;
    private Roue laRoue;
    private int cagnotePartie;
    private int cagnoteManche;

    public Joueur(int numP){
        this.numeroJoueur = numP;
        this.cagnotePartie = 0;
        this.cagnoteManche = 0;
    }

    public void tournerLaRoue(){
        String bunus = this.laRoue.tourner();
    }

    public void setLaRoue(Roue laRoue) {
        this.laRoue = laRoue;
    }
}
