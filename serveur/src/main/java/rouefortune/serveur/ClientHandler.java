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
                        this.serveur.getClientQuiABuzz().add(this);
                        System.out.println("MOT A DEVINER →"+this.getInventaire().getMotADeviner());
                        this.serveur.getPartie().getLaManche().pauseEnigmeRapide();
                        //dos.writeUTF(creerMessageJsonObject(Messages.PROPOSER_REPONSE,null));
                        break;
                    case Messages.PROPOSER_REPONSE:
                        if(this.messageReceived.getContenu().equals(this.getInventaire().getMotADeviner())){
                            System.out.println(this.getInventaire().getNomJoueur()+" a trouvée le mot !!");
                            dos.writeUTF(creerMessageJsonObject(Messages.MOT_TROUVEE, "Vous avez gagné !"));
                            this.getInventaire().addCagnoteManche(1,500);
                            this.serveur.getPartie().getLaManche().setJoueurDebutant(this);
                        }else{
                            this.serveur.getPartie().getLaManche().repriseEnigmeRapide();
                        }
                        break;
                    case Messages.PROPOSER_LETTRE:
                        if(this.messageReceived.getMessage().length() == 1){
                            String laLettre = this.messageReceived.getMessage();
                            if(!(laLettre.charAt(0) != 'a' && laLettre.charAt(0) != 'i' && laLettre.charAt(0) != 'u' && laLettre.charAt(0) != 'e' && laLettre.charAt(0) != 'o' && laLettre.charAt(0) != 'y')){
                                if(this.serveur.getPartie().getLaManche().getLeTableau().presenceLettre(laLettre.charAt(0))){
                                    int nombreTrouver = this.serveur.getPartie().getLaManche().getLeTableau().chercherLettre(laLettre.charAt(0));
                                    this.inventaire.addCagnoteManche(this.inventaire.getBonus(), nombreTrouver);
                                }else{
                                    dos.writeUTF(creerMessageJsonObject(Messages.INCORECT_LETTRE,"Lettre incorrecte ou deja deviner"));
                                }
                            }else{
                                if(this.inventaire.getCagnoteManche() >= 200){
                                    if(this.serveur.getPartie().getLaManche().getLeTableau().presenceLettre(laLettre.charAt(0))){
                                        this.inventaire.addCagnoteManche(1, -200);
                                        int nombreTrouver = this.serveur.getPartie().getLaManche().getLeTableau().chercherLettre(laLettre.charAt(0));
                                        this.inventaire.addCagnoteManche(this.inventaire.getBonus(), nombreTrouver);
                                    }else{
                                        dos.writeUTF(creerMessageJsonObject(Messages.INCORECT_LETTRE,"Lettre incorrecte ou deja deviner"));
                                    }
                                }
                            }
                        }else{
                            dos.writeUTF(creerMessageJsonObject(Messages.REFUSER_LETTRE,"Vous devez envoyer une lettre"));
                        }
                        break;
                    case Messages.TOURNER_ROUE:
                        String resultatRoue = this.serveur.getPartie().getLaManche().tournerRoue();
                        if(resultatRoue.equals("Banqueroute")){
                            this.inventaire.setBonus(0);
                            this.inventaire.setCagnoteManche(0);
                            dos.writeUTF(creerMessageJsonObject(Messages.RESULTAT_ROUE,resultatRoue));
                        }else if(resultatRoue.equals("Passe")){
                            this.serveur.getPartie().getLaManche().passe();
                            dos.writeUTF(creerMessageJsonObject(Messages.RESULTAT_ROUE,resultatRoue));
                        }else{
                            this.inventaire.setBonus(Integer.parseInt(resultatRoue));
                            dos.writeUTF(creerMessageJsonObject(Messages.RESULTAT_ROUE,resultatRoue));
                        }
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

