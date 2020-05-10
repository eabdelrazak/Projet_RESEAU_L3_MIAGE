package rouefortune.moteur;

import rouefortune.serveur.Serveur;

public abstract class Enigme {

    public TableauAffichage leTableau;
    public Serveur serveur;

    Enigme(TableauAffichage tableau){
        this.leTableau = tableau;
    }
}
