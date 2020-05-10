package rouefortune.serveur;

public class Inventaire {
    private String nomJoueur, motADeviner;
    private int cagnotePartie;
    private int cagnoteManche;
    private int bonus;

    public Inventaire(){
        this.cagnotePartie = 0;
        this.cagnoteManche = 0;
        this.bonus = 0;
    }

    public void addCagnotePartie(int somme){
        this.cagnotePartie += somme;
    }
    public void addCagnoteManche(int bonus, int somme){
        this.cagnoteManche += (somme*this.bonus);
    }

    public String getNomJoueur(){
        return nomJoueur;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public String getMotADeviner() {
        return motADeviner;
    }

    public void setMotADeviner(String motADeviner) {
        this.motADeviner = motADeviner;
    }

    public int getCagnotePartie() {
        return cagnotePartie;
    }

    public int getCagnoteManche() {
        return cagnoteManche;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void setCagnoteManche(int cagnoteManche) {
        this.cagnoteManche = cagnoteManche;
    }
}
