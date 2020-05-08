package rouefortune.moteur;

public abstract class Enigme {

    protected TableauAffichage leTableau;

    Enigme(TableauAffichage tableau){
        this.leTableau = tableau;
    }
}
