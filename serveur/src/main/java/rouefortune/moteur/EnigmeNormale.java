package rouefortune.moteur;


import rouefortune.Messages;
import rouefortune.serveur.Serveur;

import java.util.concurrent.TimeUnit;

public class EnigmeNormale extends Enigme {
    private Serveur serveur;

    public EnigmeNormale(TableauAffichage tableau, Serveur serveur) {
        super(tableau);
        this.serveur = serveur;
        this.serveur.commencerEnigme(this.leTableau, Messages.DEBUT_ENIGME_NORMALE);
    }

    /**
     * Revele une lettre du tableau de l'enigme normale
     */
    public void revelerLettre() {
        if (this.leTableau.getNombreConsonneRestante() > 1) {
            this.leTableau.revelerLettre();
            this.serveur.envoyerEnigme(this.leTableau, Messages.ENIGME_NORMALE);
        }
    }

}