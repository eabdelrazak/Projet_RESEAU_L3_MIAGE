package rouefortune.serveur;

public class Inventaire {
    private int numeroJoueur;
    private int cagnotePartie;
    private int cagnoteManche;

    public Inventaire(){
        this.cagnotePartie = 0;
        this.cagnoteManche = 0;
    }

    public void addCagnotePartie(int somme){
        this.cagnotePartie += somme;
    }
    public void addCagnoteManche(int somme){
        this.cagnoteManche += somme;
    }
}
