package rouefortune.joueur;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import rouefortune.Echange;
import rouefortune.Messages;
import rouefortune.graphique.FenetrePrincipal;
import rouefortune.graphique.Panneau;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket s;
    private Echange message;
    private Joueur joueur;
    private FenetrePrincipal fenetrePrincipal;
    private boolean fermerFenetre = false;

    /**
     * Fonction permettant la connection d'un joueur au serveur.
     *
     * @throws IOException Il peut y avoir un problème
     */
    public void connectJoueur() throws IOException {
        //getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");
        //establish the connection with server port 5056
        this.s = new Socket(ip, 5056);
    }

    public void boucleReceptionMessage() {
        try {
            Scanner scn = new Scanner(System.in);
            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            boolean disconnect = false;
            String toSend;

            this.fenetrePrincipal.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    System.out.println("TEST");
                    try {
                        dos.writeUTF(creerMessageJsonObject(Messages.QUITTER, null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            // the following loop performs the exchange of
            // information between client and client handler
            do {
                this.message = receptionMessage(dis.readUTF());
                System.out.println(message.getMessage() + "/" + message.getContenu());

                switch (this.message.getMessage()) {
                    case Messages.CONNEXION_REUSSI:
                        System.out.println("Connected");
                        this.fenetrePrincipal.setPanState(Panneau.CONNECTED);
                        this.fenetrePrincipal.repaint();
                        dos.writeUTF(creerMessageJsonObject(Messages.NOM, joueur.getNomJoueur()));
                        break;
                    case Messages.BEGIN:
                        break;
                    case Messages.DEBUT_ENIGME_RAPIDE:
                        this.fenetrePrincipal.setPanState(Panneau.ENIGME_RAPIDE);
                        this.fenetrePrincipal.pan.buzzer.setVisible(true);
                        this.fenetrePrincipal.pan.theme = this.message.getContenu();
                        this.fenetrePrincipal.repaint();
                        break;
                    case Messages.DEBUT_ENIGME_NORMALE:
                        this.fenetrePrincipal.setPanState(Panneau.ENIGME_NORMALE);
                        this.fenetrePrincipal.pan.theme = this.message.getContenu();
                        this.fenetrePrincipal.repaint();
                        break;
                    case Messages.ENIGME_RAPIDE: case Messages.ENIGME_NORMALE:
                        this.fenetrePrincipal.pan.enigme = this.message.getContenu();
                        this.fenetrePrincipal.repaint();
                        break;
                    case Messages.FAIRE_PROPOSITION:
                        this.fenetrePrincipal.pan.buzzer.setVisible(false);
                        this.fenetrePrincipal.pan.textfield.setVisible(true);
                        break;
                    case Messages.MOT_TROUVEE:
                        if (this.message.getContenu().split(";")[0].equals("rapide")) {
                            if (this.message.getContenu().split(";")[1].equals(this.joueur.getNomJoueur())) {
                                joueur.addCagnotteManche(1, 500);
                            }
                            this.fenetrePrincipal.pan.textfield.setVisible(false);
                            this.fenetrePrincipal.pan.gagnant = this.message.getContenu().split(";")[1];
                            this.fenetrePrincipal.setPanState(Panneau.FIN_ENIGME_RAPIDE);
                            this.fenetrePrincipal.pan.theme = "";
                            this.fenetrePrincipal.pan.enigme = "";
                            this.fenetrePrincipal.repaint();
                        }else if(this.message.getContenu().split(";")[0].equals("normale")){

                        }
                        break;
                    case Messages.CORRECT_LETTRE:
                        joueur.addCagnotteManche(joueur.getBonus(),Integer.parseInt(this.message.getContenu().split(";")[1]));
                        break;
                    case Messages.RESULTAT_ROUE:
                        System.out.println("En tournant la roue, vous avez eu : " + this.message.getContenu());
                        if (this.message.getContenu().equals("Banqueroute")) {
                            joueur.setBonus(0);
                            joueur.setCagnotteManche(0);
                        } else if (this.message.getContenu().equals("Passe")) {
                            //Afficher dans IHM que le joueur passe son tour.
                        } else {
                            joueur.setBonus(Integer.parseInt(this.message.getContenu()));
                        }
                        break;
                    case Messages.REPRENDRE:
                        this.fenetrePrincipal.pan.buzzer.setVisible(true);
                        this.fenetrePrincipal.pan.textfield.setVisible(false);
                        System.out.println(this.message.getContenu());
                        break;
                    case Messages.PAUSE:
                        this.fenetrePrincipal.pan.buzzer.setVisible(false);
                        break;
                    case Messages.DISCONNECT:
                        System.out.println("Connection closed");
                        this.s.close();
                        System.exit(0);
                        //Runtime.getRuntime().exit(0);
                        disconnect = true;
                        break;
                    case Messages.JOUEUR_ACTUEL:
                        this.fenetrePrincipal.pan.joueurActuel = this.message.getContenu();
                        if(this.message.getContenu().equals(this.joueur.getNomJoueur())){
                            this.fenetrePrincipal.pan.tournerroue.setVisible(true);
                        }else{
                            this.fenetrePrincipal.pan.tournerroue.setVisible(false);
                        }
                        break;
                    default:
                }

            } while (!disconnect);
            //closing resources
            dis.close();
            dos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Echange receptionMessage(String str) {
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

    public String creerMessageJsonObject(String message, String contenu) {
        Echange messageJoueur = new Echange(message, contenu);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String s = null;
        try {
            s = mapper.writeValueAsString(messageJoueur);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public void setFenetrePrincipal(FenetrePrincipal fenetrePrincipal) {
        this.fenetrePrincipal = fenetrePrincipal;
    }

    public Socket getSocket() {
        return s;
    }

    public void sendBuzz() {
        try {
            DataOutputStream dos = new DataOutputStream(this.s.getOutputStream());
            dos.writeUTF(this.creerMessageJsonObject(Messages.BUZZ, null));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendProposition(String s) {
        try {
            DataOutputStream dos = new DataOutputStream(this.s.getOutputStream());
            dos.writeUTF(this.creerMessageJsonObject(Messages.PROPOSER_REPONSE, s));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



}
