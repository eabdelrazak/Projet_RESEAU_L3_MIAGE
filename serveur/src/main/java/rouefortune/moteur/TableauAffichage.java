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
            this.enigmeDeviner[i] = ' ';
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
                this.enigmeADeviner[i] = ' ';
            }
        }
        return nombreTrouver;
    }

    //Methode pour révéler une lettre aux hasard (a appeler toute les X secondes)
    public void revelerLettre(){
        boolean lettreReveler = false;

        while(!lettreReveler){
            int nombreRandom = rand.nextInt(this.enigmeADeviner.length);
            if(this.enigmeADeviner[nombreRandom] != ' '){
                this.enigmeDeviner[nombreRandom] = this.enigmeADeviner[nombreRandom];
                this.enigmeADeviner[nombreRandom] = ' ';
                lettreReveler = true;
            }
        }
    }

    //Methode pour savoir si tout les lettres d'une enigme on été révélé
    public boolean enigmeFini(){
        boolean fini = true;

        for(int i = 0; i < this.longueurEnigme; i++){
            if (this.enigmeADeviner[i] != ' ') {
                fini = false;
                break;
            }
        }

        return fini;
    }

    public boolean comparerProposition(String propositionP){
        if(this.propositionATrouver.equals(propositionP)){
            return true;
        }else{
            return false;
        }
    }

}