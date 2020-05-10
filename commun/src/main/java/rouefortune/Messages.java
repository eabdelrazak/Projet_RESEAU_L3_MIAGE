package rouefortune;

public class Messages {
    /* MESSAGES SERVEUR → CLIENTS */
    // Le serveur dit au client qu'il s'est connecte
    public static final String CONNEXION_REUSSI="serveurconnexionreussi";
    //Le serveur signale le début de l'enigme rapide
    public static final String DEBUT_ENIGME_RAPIDE = "serveurdebutenigmerapide";
    // Le serveur envoie une lettre d'une enigme rapide
    public static final String ENIGME_RAPIDE="serveurafficherenigmerapide";
    //Le serveur signale le début de l'enigme rapide
    public static final String DEBUT_ENIGME_NORMALE = "serveurdebutenigmenormale";
    // Le serveur evnoie une lettre d'une enigme normale
    public static final String ENIGME_NORMALE="serveurafficherenigmenormale";
    // Le serveur dit au client de se mettre en pause
    public static final String PAUSE="serveurpause";
    //Le serveur dit au client qu'il peut faire une proposition
    public static final String FAIRE_PROPOSITION="serveurfaireproposition";
    //Le serveur dit au client qu'il peut se déconnecter
    public static final String DISCONNECT = "serveurdisconnect";
    // Le serveur demande une proposition
    public static final String PROPOSER_REPONSE="serveurproposerreponse";
    // Le serveur demande une proposition
    public static final String BEGIN = "serveurdebut";
    // Le serveur notifie les joueurs que le mot à été trouvé et donne le gagnant
    public static final String MOT_TROUVEE = "serveurvainqueur";
    // Le serveur reprend l'enigme
    public static final String REPRENDRE = "serveurreprendre";
    //Le serveur accepte la lettre proposer
    public static final String CORRECT_LETTRE="serveurcorrectlettre";
    //Le serveur refuse la lettre proposer
    public static final String REFUSER_LETTRE="serveurrefuserlettre";
    //Le serveur notifie le client que sa lettre n'est pas presente
    public static final String INCORECT_LETTRE="serveurincorectlettre";
    //Le serveur renvoie le resulat de la roue
    public static final String RESULTAT_ROUE="serveurresultatroue";
    //Le serveur indique quel joueur joue
    public static final String JOUEUR_ACTUEL="serveurjoueuractuel";

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

    public static final String NOM = "clientnomduclient";
}
