package rouefortune.moteur;

import rouefortune.serveur.Partie;

import java.util.Random;

public class TableauAffichage {

    private final Partie partie;
    private String theme;
    private String propositionATrouver;
    private char[] enigmeADeviner;
    private char[] enigmeDeviner;
    private int longueurEnigme = 0;
    private Random rand;

    public TableauAffichage(Partie partie){
        this.partie = partie;
        this.rand = new Random();
    }

    //Methode pour definir l'énigme qui apparais sur le tableau
    public void setEnigmeADeviner(String themeP, String phrase){
        this.theme = themeP;
        this.propositionATrouver = phrase;
        this.longueurEnigme = phrase.length();
        this.enigmeADeviner = new char[longueurEnigme];
        this.enigmeDeviner = new char[longueurEnigme];

        for(int i = 0; i < this.longueurEnigme; i++){
            this.enigmeADeviner[i] = phrase.charAt(i);
        }

        for(int i = 0; i < this.longueurEnigme; i++){
            this.enigmeDeviner[i] = '_';
        }
    }

    //Methode pour savoir combiens de fois une lettre est presente dans l'énigme a deviné
    public int chercherLettre(char lettre){
        lettre = Character.toUpperCase(lettre);
        int nombreTrouver = 0;
        for(int i = 0; i < this.longueurEnigme; i++){
            if(this.enigmeADeviner[i] == lettre){
                this.enigmeDeviner[i] = lettre;
                nombreTrouver++;
                this.enigmeADeviner[i] = 0;
            }
        }
        return nombreTrouver;
    }

    public boolean presenceLettre(char lettre){
        lettre = Character.toUpperCase(lettre);
        for(int i = 0; i < this.longueurEnigme; i++){
            if(this.enigmeADeviner[i] == lettre){
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode pour avoir le nombre de consonnes restante dans l'enigme
     * @return Nombre de consonnes restantes
     */
    public int getNombreConsonneRestante() {
        int nbConsonne=0;
        char[] consonnes = {'B','C','D','F','G','H','J','K','L','M','N','P','Q','R','S','T','V','W','X','Z'};
        for(int i=0; i < consonnes.length; i++) {
            for(int j=0; j < this.enigmeADeviner.length; j++){
                if (consonnes[i] == this.enigmeADeviner[j]) {
                    nbConsonne++;
                    break;
                }
            }
        }
        System.out.println("Il reste "+nbConsonne+" consonnes dans le mot");
        return nbConsonne;
    }

    //Methode pour révéler une lettre aux hasard (a appeler toute les X secondes)
    public void revelerLettre(){
        boolean lettreReveler = false;

        while(!lettreReveler){
            int nombreRandom = rand.nextInt(this.enigmeADeviner.length);
            if(this.enigmeADeviner[nombreRandom] != 0){
                this.enigmeDeviner[nombreRandom] = this.enigmeADeviner[nombreRandom];
                this.enigmeADeviner[nombreRandom] = 0;
                lettreReveler = true;
            }
        }
    }

    //Methode pour savoir si tout les lettres d'une enigme on été révélé
    public boolean enigmeFini(){
        boolean fini = true;

        for(int i = 0; i < this.longueurEnigme; i++){
            if (this.enigmeADeviner[i] != 0) {
                fini = false;
                break;
            }
        }

        return fini;
    }

    public String AfficherEnigmeDeviner(){
        StringBuilder s = new StringBuilder();
        for (char c : enigmeDeviner) {
            s.append(c);
        }
        return s.toString();
    }

    public String AfficherEnigmeADeviner(){
        StringBuilder s = new StringBuilder();
        for (char c : enigmeDeviner) {
            s.append(c);
        }
        return s.toString();
    }

    public int getNombreCharacterRestant() {
        int nbRestant = 0;
        for (char c : this.enigmeADeviner) {
            if (c != 0) {
                nbRestant++;
            }
        }
        return nbRestant;
    }

    public String getPropositionATrouver() {
        return propositionATrouver;
    }

    public String getTheme() {
        return theme;
    }
}
