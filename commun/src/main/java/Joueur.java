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
        String bunus = this.laRoue.tourner();
    }

    public void setLaRoue(Roue laRoue) {
        this.laRoue = laRoue;
    }

    public String getProposition() {
        return proposition;
    }

    public void interompreRevelationTableau(){
        /**A Faire: Demande au joueur d'ecrire sa proposition et la place dans l'attribut proposition**/
        String chaineSaisie = "";
        this.proposition = chaineSaisie.toUpperCase();
        Manche.interuptionIdJoueur = this.numeroJoueur;
    }

    public void addCagnotePartie(int somme){
        this.cagnotePartie += somme;
    }

    public void addCagnoteManche(int somme){
        this.cagnoteManche += somme;
    }
}
