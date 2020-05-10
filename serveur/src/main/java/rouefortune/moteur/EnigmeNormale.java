package rouefortune.moteur;


import rouefortune.Messages;
import rouefortune.serveur.Serveur;

import java.util.concurrent.TimeUnit;

public class EnigmeNormale extends Enigme {
    private volatile boolean running = true;
    private volatile boolean paused = true;
    private Serveur serveur;
    private final Object pauseLock = new Object();

    public EnigmeNormale(TableauAffichage tableau, Serveur serveur) {
        super(tableau);
        this.serveur = serveur;
        this.serveur.commencerEnigme(this.leTableau, Messages.DEBUT_ENIGME_NORMALE);
    }
}
