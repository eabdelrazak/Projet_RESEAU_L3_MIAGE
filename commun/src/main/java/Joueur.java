public class Joueur {

    private int numeroJoueur;
    private Roue laRoue;

    public Joueur(int numP){
        this.numeroJoueur = numP;
    }

    public void tournerLaRoue(){
        String bunus = this.laRoue.tourner();
    }

    public void setLaRoue(Roue laRoue) {
        this.laRoue = laRoue;
    }
}
