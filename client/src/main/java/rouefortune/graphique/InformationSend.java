package rouefortune.graphique;

import rouefortune.joueur.AppClient;
import rouefortune.joueur.Joueur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Objects;

public class InformationSend extends JButton implements MouseListener {

    private final String name;
    private final InformationTexte pseudo, ip, port;
    private final Panneau panneau;
    private final Joueur joueur;

    public InformationSend(String str, Joueur joueur, Panneau panneau, InformationTexte pseudo, InformationTexte ip, InformationTexte port){
        super(str);
        this.pseudo = pseudo;
        this.joueur = joueur;
        this.panneau = panneau;
        this.ip = ip;
        this.port = port;
        this.name = str;
        this.setBounds(350, 400, 100, 50);
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.ip.getText() != "" && this.pseudo.getText() != "" && this.port.getText() != ""){
            this.joueur.setNomJoueur(this.pseudo.getText());
            String ip = this.ip.getText();
            String port = this.port.getText();
            try {
                this.joueur.getClient().connectJoueur(ip, Integer.parseInt(port));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Thread t = new Thread(this.joueur.getClient());
            this.joueur.getClient().setThread(t);
            t.start();
            this.panneau.setState(Panneau.CONNEXION);
            this.panneau.repaint();
            this.pseudo.setVisible(false);
            this.ip.setVisible(false);
            this.port.setVisible(false);
            this.setVisible(false);
            this.panneau.repaint();
        }
        //System.out.println("BUZZ");
        //this.joueur.proposer();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
