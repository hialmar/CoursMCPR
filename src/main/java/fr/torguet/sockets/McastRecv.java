package fr.torguet.sockets;

import java.net.*;

public class McastRecv {
    public static void main(String[] args) throws Exception {
        // Création d'un socket UDP sur le port 40000
        try (MulticastSocket socket = new MulticastSocket(40000)) {
            InetAddress groupe = InetAddress.getByName("225.0.0.1");
            SocketAddress socketAddress = new InetSocketAddress(groupe, 40000);
            //socket.joinGroup(groupe);
            socket.joinGroup(socketAddress, socket.getNetworkInterface());

            // tampon pour recevoir les données des datagrammes UDP
            final byte[] tampon = new byte[1024];

            // objet Java permettant de recevoir un datagramme UDP
            DatagramPacket dgram = new DatagramPacket(tampon, tampon.length);

            while (true) {
                // attente et réception d'un datagramme UDP
                socket.receive(dgram);

                // extraction des données
                String chaine = new String(dgram.getData(), 0, dgram.getLength());

                System.out.println("Chaine reçue : " + chaine);

                // on renvoie le message au client
                socket.send(dgram);

                // on replace la taille du tampon au max
                // elle a été modifiée lors de la réception
                //dgram.setLength(tampon.length);
                dgram.setData(tampon);
            }
        }

    }

}
