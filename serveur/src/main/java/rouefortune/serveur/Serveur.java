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
import java.util.concurrent.TimeUnit;

public class Serveur {

    public String[][] tabEnigmes;
    public boolean infiniteLoop = true;
    private ArrayList<ClientHandler> clientHandlers;
    Thread [] clientThreads;
    private int nombreDeJoueur = 0;
    private Partie partie = null;
    private ServerSocket serverSocket;

    public Serveur(int nombreDeJoueur) throws IOException {

        /* Le serveur ecoute sur le port 5056 */
        setServerSocket(new ServerSocket(5056));
        /* Creation d'une liste de thread pour la gestion de chaque client */
        this.clientHandlers = new ArrayList<>();
        /* Creation de la liste de ce qui ont buzz */
        setNombreDeJoueur(nombreDeJoueur);

        connexionClient(serverSocket);

        /*System.out.println("Attente des joueurs");

        *//* Boucle infini pour recupérer les requetes de connexion client *//*
        while (infiniteLoop) {
            Socket s = null;
            try {
                *//* objet socket permettant d'écouter qu'un client fasse une demande de connexion *//*
                    s = serverSocket.accept();

                System.out.println("Un nouveau joueur s'est connecté : " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                ajoutClient(s, dis, dos, nombreDeJoueur, this);

                if(clientHandlers.size() == nombreDeJoueur){
                    infiniteLoop = false;
                }

            } catch (Exception e){
                assert s != null;
                s.close();
                e.printStackTrace();
            }
        }*/
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

    public int getNombreDeJoueur() {
        return nombreDeJoueur;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setNombreDeJoueur(int nombreDeJoueur) {
        this.nombreDeJoueur = nombreDeJoueur;
    }

    public void commencerEnigme(TableauAffichage tableau, String typeEnigme) {
        for (ClientHandler client : clientHandlers) {
            try {
                client.getInventaire().setMotADeviner(tableau.getPropositionATrouver());
                String message = creerMessageJsonObject(typeEnigme, tableau.getTheme());
                client.getDos().writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void terminerEnigmeRapide(ClientHandler gagnant){
        System.out.println(gagnant.getInventaire().getNomJoueur()+" a trouvée le mot !!");
        for(ClientHandler client : clientHandlers){
            try {
                client.getDos().writeUTF(creerMessageJsonObject(Messages.MOT_TROUVEE, "rapide;"+gagnant.getInventaire().getNomJoueur()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.partie.getLaManche().terminerEnigmeRapide();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.partie.getLaManche().setJoueurActuel(gagnant);
        this.partie.getLaManche().commencerManche();

    }

    public void envoyerEnigme(TableauAffichage tableau, String typeEnigme) {

        for (ClientHandler client : clientHandlers) {
            try {
                System.out.println(client.getInventaire().getNomJoueur());
                System.out.println(tableau.AfficherEnigmeDeviner());
                String message = creerMessageJsonObject(typeEnigme, tableau.AfficherEnigmeDeviner());
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
        this.getPartie().getLaManche().pauseEnigmeRapide();
        for (ClientHandler client : clientHandlers) {
            String message;
            if(!client.equals(joueurQuiABuzz)){
                System.out.println(client.getInventaire().getNomJoueur()+" est en pause!");
                message = creerMessageJsonObject(Messages.PAUSE, joueurQuiABuzz.getInventaire().getNomJoueur());
            }else{
                System.out.println(client.getInventaire().getNomJoueur()+" fait une proposition!");
                message = creerMessageJsonObject(Messages.FAIRE_PROPOSITION, "Vous êtes le premier à buzz, proposez un mot !");
            }
            try {
                client.getDos().writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void mettreFinJeu(){
        if(this.getPartie().getLaManche().getEnigmeRapide().threadLettre.isAlive())
            this.getPartie().getLaManche().terminerEnigmeRapide();
    }

    public void enleverEnigme(int random_un) {
        String[][] newtab = new String[this.tabEnigmes.length-1][2];
        int j=0;
        for(int i=0; i<this.tabEnigmes.length; i++){
            if(i != random_un) {
                newtab[j] = this.tabEnigmes[i];
                j++;
            }
        }
        this.tabEnigmes = newtab;
    }

    public void envoyerJoueurActuel() {
        for (ClientHandler client : clientHandlers) {
            try {
                String message = creerMessageJsonObject(Messages.JOUEUR_ACTUEL, this.getPartie().getLaManche().joueurActuel.getInventaire().getNomJoueur());
                client.getDos().writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deconnecterAll(){
        for(ClientHandler client : this.getClientHandlers()){
            try {
                client.getDos().writeUTF(creerMessageJsonObject(Messages.DISCONNECT, "La partie prend fin car un joueur a quitté la partie !!!"));
                client.setInfiniteLoop(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getClientHandlers().clear();
        connexionClient(getServerSocket());
    }

    public void connexionClient(ServerSocket serverSocket){
        setInfiniteLoop(true);
        System.out.println("Attente des joueurs");

        /* Boucle infini pour recupérer les requetes de connexion client */
        while (isInfiniteLoop()) {
            Socket s = null;
            try {
                /* objet socket permettant d'écouter qu'un client fasse une demande de connexion */
                s = serverSocket.accept();

                System.out.println("Un nouveau joueur s'est connecté : " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                ajoutClient(s, dis, dos, nombreDeJoueur, this);

                if(clientHandlers.size() == nombreDeJoueur){
                    infiniteLoop = false;
                }

            } catch (Exception e){
                assert s != null;
                try {
                    s.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        System.out.println("DEBUT");
        this.debutPartie();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public boolean isInfiniteLoop() {
        return infiniteLoop;
    }

    public void setInfiniteLoop(boolean infiniteLoop) {
        this.infiniteLoop = infiniteLoop;
    }
}
