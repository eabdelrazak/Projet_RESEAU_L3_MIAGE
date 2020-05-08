package rouefortune.serveur;

import java.io.*;
import java.util.*;
import java.net.*;

class ClientHandler implements Runnable {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run()
    {
        String received;
        String toReturn;

        while (true)
        {
            try {
                // Ask user what he wants
                dos.writeUTF("Enigme rapide - Trouvez le mot");

                // receive the answer from client
                received = dis.readUTF();

                if(received.equals("Quitter"))
                {
                    System.out.println("Joueur " + this.s + " souhaites quitter...");
                    System.out.println("Fermeture de la connexion.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

