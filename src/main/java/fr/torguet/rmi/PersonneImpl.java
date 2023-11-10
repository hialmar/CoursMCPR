package fr.torguet.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.Unreferenced;

public class PersonneImpl extends UnicastRemoteObject
        implements Personne, Unreferenced {
    private final String nom;
    private final String prénom;

    public PersonneImpl(String p, String n) throws RemoteException {
        super();
        prénom = p;
        nom = n;
    }

    public String getNom() throws RemoteException {
        return nom;
    }

    public String getPrénom() throws RemoteException {
        return prénom;
    }

    public void unreferenced() {
        // utilisé pour libérer des ressources (threads, fichiers…)
        System.out.println("PersonneImpl: unreferenced()");
    }
}
