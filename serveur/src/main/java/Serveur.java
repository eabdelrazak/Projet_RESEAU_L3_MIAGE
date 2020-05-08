import java.io.*;

public class Serveur {

    public String[][] tabEnigmes;

    public static void main(String[] args) {

        Serveur serveur = new Serveur();
        try {
            serveur.lireFichierEnigme();
            System.out.println(serveur.tabEnigmes[0][1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lireFichierEnigme() throws IOException {
        InputStreamReader isr = new InputStreamReader(Serveur.class.getClassLoader().getResourceAsStream("themeEnigme.csv"));
        BufferedReader nbCounter = new BufferedReader(isr);
        int nbMots = 0;
        String str;

        while((str = nbCounter.readLine()) != null) {
            nbMots++;
        }
        nbCounter.close();

        String[][] tab = new String[nbMots][2];
        isr = new InputStreamReader(Serveur.class.getClassLoader().getResourceAsStream("themeEnigme.csv"));
        BufferedReader br = new BufferedReader(isr);
        nbMots = 0;
        while((str = br.readLine()) != null){
            tab[nbMots] = str.split(";");
            nbMots++;
        }
        br.close();

        this.tabEnigmes = tab;
    }
}
