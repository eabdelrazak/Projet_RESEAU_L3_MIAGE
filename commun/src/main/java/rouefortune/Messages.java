package rouefortune;

public class Messages {
    /* MESSAGES SERVEUR → CLIENTS */
    // Le serveur commence une enigme rapide
    public static final String ENIGME_RAPIDE="serveurafficherenigmerapide";
    // Le serveur commence une enigme normale
    public static final String ENIGME_NORMALE="serveurafficherenigmenormale";

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
