package fr.torguet.rmi;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello
{
    private final String name;

    public HelloImpl(String s) throws RemoteException {
        super();
        name = s;
    }

    // Implémentation de la méthode distante
    public String sayHello() throws RemoteException {
        return "Hello from " + name;
    }

    public String sayHello(Personne p) throws RemoteException {
        return "Hello "+p.getPrénom()+" "+p.getNom();
    }

    public static void main(String[] args) throws Exception {
        // Démarre le rmiregistry
        LocateRegistry.createRegistry(1099);
        // Crée et installe un gestionnaire de sécurité
        // inutile si on ne télécharge pas les classes des stubs et parametres
        // System.setSecurityManager(new RMISecurityManager());
        HelloImpl obj = new HelloImpl("HelloServer");
        Naming.rebind("HelloServer", obj);
        System.out.println("HelloServer déclaré auprès du serveur de noms");
    }
}
