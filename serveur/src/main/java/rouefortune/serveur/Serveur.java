package rouefortune.serveur;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.net.*;
import java.text.*;

public class Serveur {

    public String[][] tabEnigmes;
    public boolean infiniteLoop = true;

    public Serveur(int nombreDeJoueur) throws IOException {

        /** Le serveur ecoute sur le port 4040 **/
        ServerSocket serverSocket = new ServerSocket(5056);
        /** Creation d'une liste de thread pour la gestion de chaque client **/
        List<Thread> clientThreads = new ArrayList<Thread>();

        /** Boucle infini pour recupérer les requetes de connexion client **/
        while (infiniteLoop)
        {
            Socket s = null;
            try
            {
                /** objet socket permettant d'écouter qu'un client fasse une demande de connexion **/
                s = serverSocket.accept();

                System.out.println("Un nouveau joueur s'est connecté : " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                ClientHandler clientHandler = new ClientHandler(s, dis, dos);
                clientThreads.add(new Thread(clientHandler));

                if(nombreDeJoueur == clientThreads.size()){
                    for(int i = 0; i < clientThreads.size(); i++){
                        clientThreads.get(i).start();
                    }
                    infiniteLoop = false;
                }
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
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
}
