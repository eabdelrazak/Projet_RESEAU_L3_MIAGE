package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Objects;

public class Buzzer extends JButton implements MouseListener {
    private Joueur joueur;
    private String name;
    private Image img;
    public Buzzer(String str, Joueur joueur){
        super(str);
        this.joueur = joueur;
        this.name = str;
        this.setBounds(350, 400, 100, 100);
        try {
            img = ImageIO.read(Objects.requireNonNull(Buzzer.class.getClassLoader().getResourceAsStream("buzzer.png")));
        } catch (IOException e){
            e.printStackTrace();
        }

        this.addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0, this.getWidth(), this.getHeight());
        g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Impact", Font.PLAIN, 35));
        g2d.drawString(this.name, this.getWidth() / 2 - (this.getWidth() / 2)+20, (this.getHeight() /2)+12);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.joueur.getClient().sendBuzz();
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
