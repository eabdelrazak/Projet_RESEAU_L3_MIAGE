package rouefortune.serveur;

public class Inventaire {
    private String nomJoueur, motADeviner;
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
}
