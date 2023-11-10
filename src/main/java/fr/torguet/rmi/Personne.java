package fr.torguet.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Personne extends Remote {
    String getNom() throws RemoteException;
    String getPrénom() throws RemoteException;
}
