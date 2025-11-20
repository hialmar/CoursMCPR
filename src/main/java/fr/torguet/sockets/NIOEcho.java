package fr.torguet.sockets;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOEcho {
    static public void main(String[] args) throws Exception {
        ByteBuffer echoBuffer = ByteBuffer.allocate( 1024 );
        // Création du selector
        Selector selector = Selector.open();
        // Création du canal server socket
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // devient non bloquant
        ssc.configureBlocking( false );
        // liaison sur le port 1234
        ServerSocket ss = ssc.socket();
        InetSocketAddress address = new InetSocketAddress( 1234 );
        ss.bind( address );
        // enregistrement dans le selecteur
        SelectionKey keyServ = ssc.register( selector, SelectionKey.OP_ACCEPT );
        while (true) {
            int num = selector.select(); // attente
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                // enlève la clé de l’itérateur
                it.remove();
                if (key.isAcceptable()) {
                    // accepte la connexion
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking( false );
                    // enregistrement du canal dans le selecteur
                    SelectionKey newKey = sc.register( selector,
                            SelectionKey.OP_READ );
                    System.out.println( "Got connection from "+sc );
                } else if (key.isReadable()) {
                    // Récupération du canal
                    SocketChannel sc = (SocketChannel)key.channel();
                    echoBuffer.clear();
                    int r = sc.read( echoBuffer );
                    if (r<=0) {
                        if (r < 0) {
                            // le client a fermé la connexion
                            // => enlève le canal du sélecteur
                            sc.close();
                        }
                        break;
                    }
                    echoBuffer.flip();
                    sc.write( echoBuffer );
                }
            }
        }
    }
}
