package rouefortune.serveur;

import java.io.*;
import java.util.*;
import java.net.*;

public class ClientHandler implements Runnable {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    private Inventaire inventaire;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.inventaire = new Inventaire();
    }

    @Override
    public void run()
    {
        String received;
        String toReturn;

        while (true)
        {
            /*try {
                // Ask user what he wants
                dos.writeUTF("Enigme rapide - Trouvez le mot");

                // receive the answer from client
                received = dis.readUTF();

                if(received.equals("Quitter") || received.equals("Exit"))
                {
                    System.out.println("Joueur " + this.s + " souhaites quitter...");
                    System.out.println("Fermeture de la connexion.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // creating Date object
                Date date = new Date();

                // write on output stream based on the
                // answer from the client
                switch (received) {

                    case "Date" :
                        toReturn = "What's the date to day";
                        dos.writeUTF(toReturn);
                        break;

                    case "Time" :
                        toReturn = "ITS TIME TO SLEEP";
                        dos.writeUTF(toReturn);
                        break;

                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

       /* try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }*/
    }

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }
}

