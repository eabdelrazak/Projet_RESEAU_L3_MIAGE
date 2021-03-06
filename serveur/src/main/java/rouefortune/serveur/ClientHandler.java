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
    private volatile Serveur serveur;
    private boolean infiniteLoop = true;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, Serveur serveurP) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.inventaire = new Inventaire();
        this.serveur = serveurP;
        try {
            dos.writeUTF(creerMessageJsonObject(Messages.CONNEXION_REUSSI, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (isInfiniteLoop()) {
            try {
                // receive the answer from client
                if(getS().isClosed()){
                    System.out.println("TEST DECO "+this.getInventaire().getNomJoueur());
                    break;
                }
                Echange messageReceived = receptionMessage(dis.readUTF());
                //received = messageReceived.g
                System.out.println(messageReceived.getMessage()+"/"+messageReceived.getContenu());

                switch (messageReceived.getMessage()) {
                    case Messages.NOM:
                        this.getInventaire().setNomJoueur(messageReceived.getContenu());
                        dos.writeUTF(creerMessageJsonObject(Messages.NOM,"Identification du joueur : "+ this.getInventaire().getNomJoueur()));
                        dos.writeUTF(creerMessageJsonObject(Messages.BEGIN, "Soyez prêt la partie va commencer"));
                        break;
                    case Messages.BUZZ:
                        //System.out.println("MOT A DEVINER →"+this.getInventaire().getMotADeviner());
                        this.serveur.mettreEnPause(this);
                        //dos.writeUTF(creerMessageJsonObject(Messages.PROPOSER_REPONSE,null));
                        break;
                    case Messages.PROPOSER_REPONSE:
                        String type = messageReceived.getContenu().split(";")[0];
                        String contenu = messageReceived.getContenu().split(";")[1];
                        if(type.equals("rapide")) {
                            if (contenu.equalsIgnoreCase(this.getInventaire().getMotADeviner())) {
                                this.serveur.terminerEnigmeRapide(this);
                                this.getInventaire().addCagnoteManche(1, 500);
                            } else {
                                this.serveur.getPartie().getLaManche().repriseEnigmeRapide();
                            }
                        }else if(type.equals("normale")){
                            if (contenu.equalsIgnoreCase(this.getInventaire().getMotADeviner())) {
                                this.getInventaire().addCagnotePartie(this.getInventaire().getCagnotePartie());
                                this.getInventaire().setCagnoteManche(0);
                                this.serveur.terminerEnigmeNormale(this);
                            } else {
                                this.serveur.getPartie().getLaManche().passerLaMain();
                            }
                        }
                        break;
                    case Messages.PROPOSER_LETTRE:
                        if(messageReceived.getContenu().length() == 1){
                            String laLettre = messageReceived.getContenu();
                            System.out.println("Lettre proposé: "+laLettre);
                            if(!(laLettre.charAt(0) == 'a' || laLettre.charAt(0) == 'i' || laLettre.charAt(0) == 'u' || laLettre.charAt(0) == 'e' || laLettre.charAt(0) == 'o' || laLettre.charAt(0) == 'y')){
                                if(this.serveur.getPartie().getLaManche().getLeTableau().presenceLettre(laLettre.charAt(0))){
                                    int nombreTrouver = this.serveur.getPartie().getLaManche().getLeTableau().chercherLettre(laLettre.charAt(0));
                                    System.out.println("La consonne est dans l'enigme");
                                    dos.writeUTF(creerMessageJsonObject(Messages.CORRECT_LETTRE, "normale;"+nombreTrouver));
                                    this.inventaire.addCagnoteManche(this.inventaire.getBonus(), nombreTrouver);
                                }else{
                                    this.serveur.getPartie().getLaManche().passerLaMain();
                                    System.out.println("La consonne n'est pas dans l'enigme");
                                    dos.writeUTF(creerMessageJsonObject(Messages.INCORECT_LETTRE,"Lettre incorrecte ou deja deviner"));
                                }
                            }else{
                                if(this.inventaire.getCagnoteManche() >= 200){
                                    if(this.serveur.getPartie().getLaManche().getLeTableau().presenceLettre(laLettre.charAt(0))){
                                        int nombreTrouver = this.serveur.getPartie().getLaManche().getLeTableau().chercherLettre(laLettre.charAt(0));
                                        System.out.println("La voyelle est dans l'enigme");
                                        dos.writeUTF(creerMessageJsonObject(Messages.CORRECT_LETTRE, "normale;"+nombreTrouver));
                                    }else{
                                        this.serveur.getPartie().getLaManche().passerLaMain();
                                        System.out.println("La voyelle n'est pas dans l'enigme");
                                        dos.writeUTF(creerMessageJsonObject(Messages.INCORECT_LETTRE,"Lettre incorrecte ou deja deviner"));
                                    }
                                    this.inventaire.addCagnoteManche(1, -200);
                                }
                            }
                        }else{
                            dos.writeUTF(creerMessageJsonObject(Messages.REFUSER_LETTRE,"Vous devez envoyer une lettre"));
                        }
                        this.serveur.envoyerEnigme(this.serveur.getPartie().getLaManche().getLeTableau(), Messages.ENIGME_NORMALE);
                        break;
                    case Messages.TOURNER_ROUE:
                        String resultatRoue = this.serveur.getPartie().getLaManche().tournerRoue();
                        if(resultatRoue.equals("Banqueroute")){
                            this.inventaire.setBonus(0);
                            this.inventaire.setCagnoteManche(0);
                            dos.writeUTF(creerMessageJsonObject(Messages.RESULTAT_ROUE,resultatRoue));
                            this.serveur.getPartie().getLaManche().passerLaMain();
                        }else if(resultatRoue.equals("Passe")){
                            dos.writeUTF(creerMessageJsonObject(Messages.RESULTAT_ROUE,resultatRoue));
                            this.serveur.getPartie().getLaManche().passerLaMain();
                        }else{
                            this.inventaire.setBonus(Integer.parseInt(resultatRoue));
                            dos.writeUTF(creerMessageJsonObject(Messages.RESULTAT_ROUE,resultatRoue));
                        }
                        break;
                    case Messages.QUITTER:
                        System.out.println("Joueur " + this.s + " a decidé de quitter la partie...");
                        System.out.println("Fin de la partie. Fermeture de la connexion des joueurs.");
                        //this.s.close();
                        this.serveur.mettreFinJeu();
                        this.serveur.deconnecterAll();
                        break;
                    default:

                        break;
                }
            } catch (IOException e) {
                //e.printStackTrace();
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


    public Socket getS() {
        return s;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public boolean isInfiniteLoop() {
        return infiniteLoop;
    }

    public void setInfiniteLoop(boolean infiniteLoop) {
        this.infiniteLoop = infiniteLoop;
    }
}

