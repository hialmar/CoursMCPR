package fr.torguet.sockets;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class McastSender {
    public static void main(String [] args) throws Exception {
        // Création d'un socket UDP sur un port choisi par le système
        try (DatagramSocket socket = new DatagramSocket()) {

            // tampon pour recevoir les données des datagrammes UDP
            final byte[] tampon = new byte[1024];

            // objet Java permettant de recevoir un datagramme UDP
            DatagramPacket reception = new DatagramPacket(tampon, tampon.length);

            // Scanner sur System.in
            Scanner scanner = new Scanner(System.in);

            // On veut envoyer les messages à la même machine
            InetAddress destination = InetAddress.getByName("225.0.0.1");

            String chaine = "";
            System.out.println("Tapez vos phrases ou FIN pour arrêter :");

            while (!chaine.equalsIgnoreCase("FIN")) {
                // lecture clavier
                chaine = scanner.nextLine();
                // on récupère un tableau des octets de la chaîne
                byte[] octetsChaine = chaine.getBytes();
                // objet Java permettant d'envoyer un datagramme UDP vers la machine destination et le port 40000
                DatagramPacket emission = new DatagramPacket(octetsChaine, octetsChaine.length, destination, 40000);

                // on envoie le datagramme UDP
                socket.send(emission);
            }
        }

    }
}
