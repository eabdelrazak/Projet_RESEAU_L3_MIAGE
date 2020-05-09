package rouefortune.graphique;

import javax.swing.*;
import java.awt.*;

public class FenetrePrincipal extends JFrame {

    Panneau pan;

    public FenetrePrincipal(){

        this.setTitle("Roue de la Fortune - JBANT");
        this.setSize(800, 600);
        //this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pan = new Panneau();

        this.setContentPane(this.pan);
        this.setVisible(true);
    }


    public void afficherMessageErreurConnexion(){

    }
}
