package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Buzzer extends JButton implements MouseListener {
    private Joueur joueur;
    private String name;
    private Image img;
    public Buzzer(String str, Joueur joueur){
        super(str);
        this.joueur = joueur;
        this.name = str;
        try {
            img = ImageIO.read(new File("buzzer.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        this.addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        g2d.setColor(Color.BLACK);
        g2d.drawString(this.name, this.getWidth() / 2 - (this.getWidth() / 2 / 4), (this.getHeight() /2) +5);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("BUZZ");
        this.joueur.proposer();
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
