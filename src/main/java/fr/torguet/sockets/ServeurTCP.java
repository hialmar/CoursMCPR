package fr.torguet.sockets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP extends Thread {

    private final Socket sss;

    ServeurTCP(Socket sss) {
        this.sss = sss;
    }

    public void run() {
        try {
            // Construction d'un BufferedReader pour lire du texte envoyé à travers la connexion socket
            BufferedReader entreeSocket = new BufferedReader(new InputStreamReader(sss.getInputStream()));
            // Construction d'un PrintStream pour envoyer du texte à travers la connexion socket
            PrintStream sortieSocket = new PrintStream(sss.getOutputStream());

            String chaine = "";

            while (chaine != null) {
                // lecture d'une chaine envoyée à travers la connexion socket
                chaine = entreeSocket.readLine();

                // si elle est nulle c'est que le client a fermé la connexion
                if (chaine != null) {
                    System.out.println("Requete " + chaine);
                    sortieSocket.println(chaine); // on envoie la chaine au client
                }
            }

            // on ferme nous aussi la connexion
            sss.close();
        } catch(RuntimeException exc) {
            exc.printStackTrace();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {

        // Création d'un socket serveur générique sur le port 40000
        try (ServerSocket ssg = new ServerSocket(40000)) {

            while (true) {
                // On attend une connexion puis on l'accepte
                Socket sss = ssg.accept();

                ServeurTCP thread = new ServeurTCP(sss);
                thread.start();
            }
        }
    }
}