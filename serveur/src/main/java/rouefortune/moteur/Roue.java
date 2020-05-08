package rouefortune.moteur;

import java.util.Random;

public class Roue {

    private String[] bonus;
    private Random rand;

    public Roue(){
        this.rand = new Random();
        initialisationRoue();
    }

    public String tourner(){
        int result = rand.nextInt(this.bonus.length);
        return this.bonus[result];
    }

    public void initialisationRoue(){
        this.bonus = new String[]{"0", "150", "250", "300", "150", "200", "Banqueroute", "100", "300", "250", "100", "1000", "150", "250", "500", "Banqueroute", "1500", "150", "250", "Passe", "400", "2000", "100"};
    }

}
