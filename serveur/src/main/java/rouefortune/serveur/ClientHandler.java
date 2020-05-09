package rouefortune.serveur;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import rouefortune.Message;

import java.io.*;
import java.util.*;
import java.net.*;

public class ClientHandler implements Runnable {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    private Inventaire inventaire;
    private Serveur serveur;
    private Message message;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, Serveur serveurP) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.inventaire = new Inventaire();
        this.serveur = serveurP;
    }

    @Override
    public void run()
    {
        String received;
        String toReturn;

        while (true)
        {
            try {
                // receive the answer from client
                if(s.isClosed()){
                    break;
                }
                received = dis.readUTF();

                if(received.equals("Quitter") || received.equals("Exit"))
                {
                    System.out.println("Joueur " + this.s + " souhaites quitter...");
                    System.out.println("Fermeture de la connexion.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                switch (received) {

                    case "Buzz" :
                        this.serveur.getPartie().getLaManche().pauseEnigmeRapide();
                        toReturn = "What's the date to day";
                        dos.writeUTF(toReturn);
                        break;

                    default:
                        
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Message receptionMessage(String str){
        Message message = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            message = mapper.readValue(str, Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }
}

