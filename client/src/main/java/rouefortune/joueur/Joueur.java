package rouefortune.joueur;

import java.io.*;
import java.util.Scanner;


public class Joueur {

    private Client client;
    private String nomJoueur;
    private int cagnottePartie;
    private int cagnotteManche;
    private String proposition;
    private int bonus;


    public Joueur(String nom, Client client){
        this.nomJoueur = nom;
        this.client = client;
        this.cagnottePartie = 0;
        this.cagnotteManche = 0;
        this.proposition = " ";
        this.bonus = 0;
    }

    /**
     * Fonction permettant au joueur de jouer son tour.
     */
    public void jouer() {

    }

    public String getProposition() {
        return proposition;
    }

    public void proposer() {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(this.client.getSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scn = new Scanner(System.in);
        String proposition = scn.nextLine();
        try {
            assert dos != null;
            dos.writeUTF(proposition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public void addCagnottePartie(int somme){
        this.cagnottePartie += somme;
    }

    public void addCagnotteManche(int bonus, int somme){
        this.cagnotteManche += (somme*this.bonus);
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
}
