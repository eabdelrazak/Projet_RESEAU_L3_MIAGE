package rouefortune.joueur;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import rouefortune.MessageJoueur;

import java.io.*;
import java.net.*;
import java.util.Scanner;

//Java implementation pour le client


public class Joueur {

    private String nomJoueur;
    private int cagnotePartie;
    private int cagnoteManche;
    private String proposition;
    private Socket s;
    private MessageJoueur message;

    public Joueur(String nom){
        this.nomJoueur = nom;
        this.cagnotePartie = 0;
        this.cagnoteManche = 0;
        this.proposition = " ";
    }

    /**
     * Fonction permettant la connection d'un joueur au serveur.
     * @throws IOException
     */
    public void connectJoueur() throws IOException {
        //getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");
        //establish the connection with server port 5056
        s = new Socket(ip, 5056);
    }

    /**
     * Fonction permettant au joueur de jouer son tour.
     * @throws IOException
     */
    public void jouer() throws IOException {
        try{
            Scanner scn = new Scanner(System.in);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            while (true){
                this.message = receptionMessage(dis.readUTF());
                //System.out.println(this.message.getMessage());
                if(this.message.getMessage().equals("Enigme rapide")){
                    System.out.println(this.message.getContenu());
                }
                String tosend = "";//scn.nextLine();
                dos.writeUTF(tosend);

            // If client sends buzz, he will player his
            // and then break from the while loop
            if(tosend.equals("Buzz")){
                String proposition = scn.nextLine();
                dos.writeUTF(proposition);
            }
            // If client sends exit,close this connection
            // and then break from the while loop
            else if(tosend.equals("Quitter")){
                System.out.println("Closing this connection : " + s);
                s.close();
                System.out.println("Connection closed");
                break;
            }

            }

            //closing resources
            scn.close();
            dis.close();
            dos.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getProposition() {
        return proposition;
    }

    public MessageJoueur receptionMessage(String str){
        MessageJoueur messageJoueur = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            messageJoueur = mapper.readValue(str, MessageJoueur.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageJoueur;
    }

    public void proposer() {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scn = new Scanner(System.in);
        String proposition = scn.nextLine();
        try {
            dos.writeUTF(proposition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}