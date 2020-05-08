import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Serveur {

    public String[][] tabEnigmes;

    public static void main(String[] args) {

        Serveur serveur = new Serveur();
        try {
            serveur.tabEnigmes = Serveur.lireFichierEnigme();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[][] lireFichierEnigme() throws IOException {
        FileReader fr = new FileReader("themeEnigme.csv");
        BufferedReader nbCounter = new BufferedReader(fr);
        int nbMots = 0;
        String str;

        while((str = nbCounter.readLine()) != null) {
            nbMots++;
        }
        nbCounter.close();

        String[][] tab = new String[nbMots][2];

        BufferedReader br = new BufferedReader(fr);
        nbMots = 0;
        while((str = br.readLine()) != null){
            tab[nbMots] = str.split(";");
            nbMots++;
        }
        br.close();

        return tab;
    }
}
