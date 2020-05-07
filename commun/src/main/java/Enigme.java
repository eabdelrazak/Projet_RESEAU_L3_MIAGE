package lecture;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Enigme {

    String str;
    ArrayList<String> themeGeo = new ArrayList();
    ArrayList<String> themeAnimal = new ArrayList();
    ArrayList<String> themeAliment = new ArrayList();

    public void stockerEnigme() throws IOException {
        FileReader fr = new FileReader("themeEnigme.txt");
        BufferedReader br = new BufferedReader(fr);

        while((str = br.readLine()) != null){
            String tab[] = str.split(";");
            for(int i=0;i<tab.length;i++){
                switch(tab[i]) {
                    case "geo":
                        themeGeo.add(tab[i + 1]);
                        break;
                    case "animal":
                        themeAnimal.add(tab[i + 1]);
                        break;
                    case "aliment":
                        themeAliment.add(tab[i + 1]);
                        break;
                    default:
                }
            }
        }
        br.close();
    }

    public ArrayList<String> getThemeGeo() {
        return themeGeo;
    }

    public ArrayList<String> getThemeAnimal() {
        return themeAnimal;
    }

    public ArrayList<String> getThemeAliment() {
        return themeAliment;
    }
}
