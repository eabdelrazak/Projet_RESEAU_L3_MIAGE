package rouefortune.moteur;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import rouefortune.MessageJoueur;
import rouefortune.serveur.ClientHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class EnigmeRapide extends Enigme implements Runnable {
    private volatile boolean running = true;
    private volatile boolean paused = true;
    private ArrayList<ClientHandler> clientHandlers;
    private final Object pauseLock = new Object();

    public Thread threadLettre;

    public EnigmeRapide(TableauAffichage tableau, ArrayList<ClientHandler> clientHandlers) {
        super(tableau);
        this.clientHandlers = clientHandlers;
        this.threadLettre = new Thread(this);
        this.start();
    }

    /**
     * Revele une lettre du tableau de l'enigme rapide/
     */
    private void revelerLettre() {
        this.leTableau.revelerLettre();
    }

    /**
     * Permet de réveler une lettre une fois par seconde, sauf si le thread est en pause.
     */
    @Override
    public void run() {
        while (running) {
            synchronized (pauseLock) {
                if (!running) {
                    break;
                }
                if (paused) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait();
                        }
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) {
                        break;
                    }
                }
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.revelerLettre();
            String message = creerMessageJsonObject("Enigme rapide", this.leTableau.AfficherEnigmeDeviner());
            System.out.println("TEST "+message);
            /*for (ClientHandler client : clientHandlers) {
                try {
                    System.out.println(this.leTableau.AfficherEnigmeDeviner());
                    String message = creerMessageJsonObject("Enigme rapide", this.leTableau.AfficherEnigmeDeviner());
                    System.out.println(message);
                    //client.getDos().writeUTF(this.leTableau.AfficherEnigmeDeviner());
                    client.getDos().writeUTF(this.leTableau.AfficherEnigmeDeviner());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }

    public String creerMessageJsonObject(String message, String contenu){
        MessageJoueur messageJoueur = new MessageJoueur(message, contenu);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String s = null;
        try {
            s = mapper.writeValueAsString(messageJoueur);
        }
        catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }

    /**
     * Arrête le thread, doit être fait à la fin de l'enigme ou lorsque toutes les lettres ont été révélées
     */
    public void stop() {
        running = false;
        resume();
    }

    /**
     * Mets en pause le thread pour arrêter de réveler des lettres
     */
    public void pause() {
        paused = true;
    }

    /**
     * Permet de remettre le thread en route et donc de révéler une lettre en plus.
     */
    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll(); // Unblocks thread
        }
    }

    /**
     * Permet de lancer le thread au début de la création de l'enigme (le thread est immédiatement mis en pause)
     */
    private void start() {
        this.threadLettre.start();
    }

    public boolean faireProposition(String proposition) {
        return this.leTableau.comparerProposition(proposition);
    }
}
