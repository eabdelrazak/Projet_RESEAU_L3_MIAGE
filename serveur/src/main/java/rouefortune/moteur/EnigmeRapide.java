package rouefortune.moteur;


import rouefortune.Messages;
import rouefortune.serveur.Serveur;

import java.util.concurrent.TimeUnit;

public class EnigmeRapide extends Enigme implements Runnable {
    private volatile boolean running = true;
    private volatile boolean paused = true;
    private final Object pauseLock = new Object();

    public Thread threadLettre;

    public EnigmeRapide(TableauAffichage tableau, Serveur serveur) {
        super(tableau);
        this.serveur = serveur;
        this.threadLettre = new Thread(this);
        this.threadLettre.start();
        this.serveur.commencerEnigme(this.leTableau, Messages.DEBUT_ENIGME_RAPIDE);
    }

    /**
     * Revele une lettre du tableau de l'enigme rapide/
     */
    public void revelerLettre() {
        if(this.leTableau.getNombreCharacterRestant() > 1 && !paused){
            this.leTableau.revelerLettre();
            this.serveur.envoyerEnigme(this.leTableau, Messages.ENIGME_RAPIDE);
        }
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
        }
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
    public void start() {
        this.threadLettre.start();
    }
}
