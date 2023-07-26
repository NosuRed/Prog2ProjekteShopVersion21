package KernklassenInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NutzerInterface extends Remote {

    // gibt den Nutzernamen zurueck
    String getName() throws RemoteException;

    // gibt den Benutzernamen des Nutzer zurueck
    String getBenutzerName() throws RemoteException;

    // gibt die Nutzernummer zurueck
    int getNummer() throws RemoteException;

    // gibt das Passwort des Nutzers zurueck
    String getPasswd() throws RemoteException;
}