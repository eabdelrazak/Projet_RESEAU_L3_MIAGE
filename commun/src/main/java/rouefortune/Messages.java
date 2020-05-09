package rouefortune;

public class Messages {
    /* MESSAGES SERVEUR → CLIENTS */
    // Le serveur dit au client qu'il s'est connecte
    public static final String CONNEXION_REUSSI="serveurconnexionreussi";
    // Le serveur commence une enigme rapide
    public static final String ENIGME_RAPIDE="serveurafficherenigmerapide";
    // Le serveur commence une enigme normale
    public static final String ENIGME_NORMALE="serveurafficherenigmenormale";
    // Le serveur dit au client de se mettre en pause
    public static final String PAUSE="serveurpause";
    //Le serveur dit au client qu'il peut faire une proposition
    public static final String FAIRE_PROPOSITION="serveurfaireproposition";
    //Le serveur dit au client qu'il peut se déconnecter
    public static final String DISCONNECT = "serveurdisconnect";
    // Le serveur demande une proposition
    public static final String PROPOSER_REPONSE="clientproposerreponse";

    /* MESSAGE CLIENT → SERVEUR */
    // Le client buzz
    public static final String BUZZ="clientbuzz";
    // Le client veut tourner la roue
    public static final String TOURNER_ROUE="clienttournerroue";
    // Le client veut acheter une voyelle
    public static final String ACHETER_VOYELLE="clientachetervoyelle";
    // Le client propose une lettre
    public static final String PROPOSER_LETTRE="clientproposerconsonne";
    // Le client souhaite quitter le jeu
    public static final String QUITTER="clientquitte";
    public static final String EXIT="clientquitte";

}
