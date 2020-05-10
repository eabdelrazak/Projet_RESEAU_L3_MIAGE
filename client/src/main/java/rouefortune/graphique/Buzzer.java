package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Objects;

public class Buzzer extends JButton implements MouseListener {
    private Joueur joueur;
    private String name;
    private Image currImg, img, imgEntered, imgPressed;

    public static final int ENIGME_RAPIDE = 0;
    public static final int ENIGME_NORMALE = 1;

    public int state;

    public Buzzer(String str, Joueur joueur, int state){
        super(str);
        this.joueur = joueur;
        this.name = str;
        this.state = state;
        this.setBounds(350, 400, 100, 100);
        try {
            img = ImageIO.read(Objects.requireNonNull(Buzzer.class.getClassLoader().getResourceAsStream("buzzerSelected.png")));
            imgEntered = ImageIO.read(Objects.requireNonNull(Buzzer.class.getClassLoader().getResourceAsStream("buzzer.png")));
            imgPressed = ImageIO.read(Objects.requireNonNull(Buzzer.class.getClassLoader().getResourceAsStream("buzzerPressed.png")));
            currImg = img;
        } catch (IOException e){
            e.printStackTrace();
        }
        this.setBorder(BorderFactory.createEmptyBorder());

        this.addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0, this.getWidth(), this.getHeight());
        g2d.drawImage(currImg, 0, 0, this.getWidth(), this.getHeight(), this);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Impact", Font.PLAIN, 35));
        g2d.drawString(this.name, this.getWidth() / 2 - (this.getWidth() / 2)+20, (this.getHeight() /2)+12);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(state == Buzzer.ENIGME_RAPIDE) {
            this.joueur.getClient().sendBuzz();
        }else if(state == Buzzer.ENIGME_NORMALE){

        }
        //System.out.println("BUZZ");
        //this.joueur.proposer();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.currImg = imgPressed;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.currImg = imgEntered;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.currImg = imgEntered;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.currImg = img;
    }

}
