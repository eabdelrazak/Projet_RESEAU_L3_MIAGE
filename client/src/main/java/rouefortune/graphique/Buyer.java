package rouefortune.graphique;

import rouefortune.joueur.Joueur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Objects;

public class Buyer extends JButton implements MouseListener {
    private Joueur joueur;
    private Panneau panneau;
    private String name;
    private Image currImg, img, imgEntered, imgPressed;

    public Buyer(String str, Joueur joueur, Panneau panneau){
        super(str);
        this.panneau = panneau;
        this.joueur = joueur;
        this.name = str;
        this.setBounds(225, 400, 100, 100);
        try {
            img = ImageIO.read(Objects.requireNonNull(Buyer.class.getClassLoader().getResourceAsStream("buzzerSelected.png")));
            imgEntered = ImageIO.read(Objects.requireNonNull(Buyer.class.getClassLoader().getResourceAsStream("buzzer.png")));
            imgPressed = ImageIO.read(Objects.requireNonNull(Buyer.class.getClassLoader().getResourceAsStream("buzzerPressed.png")));
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
        g2d.drawString(this.name, this.getWidth() / 2 - (this.getWidth() / 2)+15, (this.getHeight() /2)+12);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.panneau.disableButtons();
        this.panneau.textfield.state = PropositionTexte.ENIGME_NORMALE_BUYER;
        this.panneau.textfield.setVisible(true);
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
