package rouefortune.moteur;

public class EnigmeRapide extends Enigme {
    public Thread threadLettre;

    EnigmeRapide(){
        super();
        this.threadLettre = new Thread(() -> {

        });
    }


}
