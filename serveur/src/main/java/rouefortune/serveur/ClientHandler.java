package rouefortune.serveur;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import rouefortune.Echange;
import rouefortune.Messages;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    private Inventaire inventaire;
    private Serveur serveur;
    private Echange messageReceived;
    private boolean buzz = false;

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
                this.messageReceived = receptionMessage(dis.readUTF());
                //received = messageReceived.g

                switch (this.messageReceived.getMessage()) {
                    case Messages.NOM:
                        this.getInventaire().setNomJoueur(this.messageReceived.getContenu());
                        dos.writeUTF(creerMessageJsonObject(Messages.NOM,"Identification du joueur : "+ this.getInventaire().getNomJoueur()));
                        dos.writeUTF(creerMessageJsonObject(Messages.BEGIN, "Soyez prêt la partie va commencer"));
                        break;
                    case Messages.BUZZ:
                        this.serveur.getPartie().getLaManche().pauseEnigmeRapide();
                        dos.writeUTF(creerMessageJsonObject(Messages.PROPOSER_REPONSE,null));
                        break;
                    case Messages.QUITTER:
                        System.out.println("Joueur " + this.s + " souhaites quitter...");
                        System.out.println("Fermeture de la connexion.");
                        this.s.close();
                        System.out.println("Connection closed");
                        break;
                    default:

                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

       /* try
        {
            // closing resources
            //this.dis.close();
            //this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }*/
    }

    public Echange receptionMessage(String str){
        Echange message = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            message = mapper.readValue(str, Echange.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String creerMessageJsonObject(String message, String contenu){
        Echange messageJoueur = new Echange(message, contenu);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String s = null;
        try {
            s = mapper.writeValueAsString(messageJoueur);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
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

    public Socket getS() {
        return s;
    }
}

