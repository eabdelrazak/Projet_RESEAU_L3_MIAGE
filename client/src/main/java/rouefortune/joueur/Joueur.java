package rouefortune.joueur;

import java.io.*;
import java.util.Scanner;


public class Joueur {

    private Client client;
    private String nomJoueur;
    private int cagnotePartie;
    private int cagnoteManche;
    private String proposition;


    public Joueur(String nom, Client client){
        this.nomJoueur = nom;
        this.client = client;
        this.cagnotePartie = 0;
        this.cagnoteManche = 0;
        this.proposition = " ";
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

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public Client getClient() {
        return client;
    }
}
