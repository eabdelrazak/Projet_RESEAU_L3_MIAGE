package rouefortune.serveur;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import rouefortune.Echange;
import rouefortune.Messages;
import rouefortune.moteur.TableauAffichage;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.net.*;

public class Serveur {

    public String[][] tabEnigmes;
    public boolean infiniteLoop = true;
    private ArrayList<ClientHandler> clientHandlers, clientQuiABuzz;
    Thread [] clientThreads;
    private int nombreDeJoueur = 0;
    private Partie partie = null;
    private boolean setpartie = false;
    private int nbReady = 0;

    public Serveur(int nombreDeJoueur) throws IOException {

        /* Le serveur ecoute sur le port 5056 */
        ServerSocket serverSocket = new ServerSocket(5056);
        /* Creation d'une liste de thread pour la gestion de chaque client */
        this.clientHandlers = new ArrayList<>();
        /* Creation de la liste de ce qui ont buzz */
        this.clientQuiABuzz = new ArrayList<>();
        setNombreDeJoueur(nombreDeJoueur);

        System.out.println("Attente des joueurs");

        /* Boucle infini pour recupérer les requetes de connexion client */
        while (infiniteLoop) {
            Socket s = null;
            try {
                /* objet socket permettant d'écouter qu'un client fasse une demande de connexion */
                    s = serverSocket.accept();

                System.out.println("Un nouveau joueur s'est connecté : " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                ajoutClient(s, dis, dos, nombreDeJoueur, this);
                dos.writeUTF(creerMessageJsonObject(Messages.CONNEXION_REUSSI, null));

                if(clientHandlers.size() == nombreDeJoueur){
                    infiniteLoop = false;
                }

            } catch (Exception e){
                assert s != null;
                s.close();
                e.printStackTrace();
            }
        }
        System.out.println("DEBUT");
        this.debutPartie();
    }

    public synchronized void ajoutClient(Socket socket, DataInputStream dis, DataOutputStream dos, int nb, Serveur servP){
        if (clientHandlers.size() < nb) {
            ClientHandler clientHandler = new ClientHandler(socket, dis, dos, servP);
            clientHandlers.add(clientHandler);
        }
    }

    public synchronized void debutPartie(){
        clientThreads = new Thread[getNombreDeJoueur()];

        for(int i = 0; i < clientHandlers.size(); i++){
            clientThreads[i] = new Thread(clientHandlers.get(i));
        }

        for (Thread clientThread : clientThreads) {
            clientThread.start();
        }

        try {
            lireFichierEnigme();
        } catch (IOException e) {
            e.printStackTrace();
        }

        partie = new Partie(this);
        partie.commencer();
    }

    public void lireFichierEnigme() throws IOException {
        InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(Serveur.class.getClassLoader().getResourceAsStream("themeEnigme.csv")));
        BufferedReader nbCounter = new BufferedReader(isr);
        int nbMots = 0;
        String str;

        while(nbCounter.readLine() != null) {
            nbMots++;
        }
        nbCounter.close();

        String[][] tab = new String[nbMots][2];
        isr = new InputStreamReader(Objects.requireNonNull(Serveur.class.getClassLoader().getResourceAsStream("themeEnigme.csv")));
        BufferedReader br = new BufferedReader(isr);
        nbMots = 0;
        while((str = br.readLine()) != null){
            tab[nbMots] = str.split(";");
            nbMots++;
        }
        br.close();

        this.tabEnigmes = tab;
    }

    public ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public void setClientHandlers(ArrayList<ClientHandler> clientHandlers) {
        this.clientHandlers = clientHandlers;
    }

    public int getNombreDeJoueur() {
        return nombreDeJoueur;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setNombreDeJoueur(int nombreDeJoueur) {
        this.nombreDeJoueur = nombreDeJoueur;
    }

    public ArrayList<ClientHandler> getClientQuiABuzz() {
        return clientQuiABuzz;
    }

    public void setClientQuiABuzz(ArrayList<ClientHandler> clientQuiABuzz) {
        this.clientQuiABuzz = clientQuiABuzz;
    }

    public void envoyerEnigme(TableauAffichage tableau) {
        System.out.println(tableau.AfficherEnigmeDeviner());
        for (ClientHandler client : clientHandlers) {
            try {
                client.getInventaire().setMotADeviner(tableau.getPropositionATrouver());
                String message = creerMessageJsonObject(Messages.ENIGME_RAPIDE, tableau.AfficherEnigmeDeviner());
                client.getDos().writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void mettreEnPause(ClientHandler joueurQuiABuzz){
        for (ClientHandler client : clientHandlers) {
            String message;
            if(client != joueurQuiABuzz){
                message = creerMessageJsonObject(Messages.PAUSE, joueurQuiABuzz.getInventaire().getNomJoueur());
            }else{
                message = creerMessageJsonObject(Messages.FAIRE_PROPOSITION, null);
            }
            try {
                client.getDos().writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
