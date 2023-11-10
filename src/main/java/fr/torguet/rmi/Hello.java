package fr.torguet.rmi;

import java.rmi.RemoteException;

public interface Hello extends java.rmi.Remote {
    String sayHello() throws java.rmi.RemoteException;

    String sayHello(Personne p) throws RemoteException;

}
