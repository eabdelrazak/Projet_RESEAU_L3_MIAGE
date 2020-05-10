package rouefortune.joueur;


public class Joueur {

    private Client client;
    private String nomJoueur;
    private int cagnottePartie;
    private int cagnotteManche;
    private int bonus;


    public Joueur(String nom, Client client){
        this.nomJoueur = nom;
        this.client = client;
        this.cagnottePartie = 0;
        this.cagnotteManche = 0;
        this.bonus = 0;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public void addCagnottePartie(int somme){
        this.cagnottePartie += somme;
    }

    public void addCagnotteManche(int bonus, int somme){
        this.cagnotteManche += (somme*bonus);
    }

    public void setCagnotteManche(int cagnotteManche) {
        this.cagnotteManche = cagnotteManche;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public Client getClient() {
        return client;
    }

    public int getCagnottePartie() {
        return cagnottePartie;
    }

    public void setCagnottePartie(int cagnottePartie) {
        this.cagnottePartie = cagnottePartie;
    }

    public int getCagnotteManche() {
        return cagnotteManche;
    }
}
