package KernklassenInterface;

import java.rmi.RemoteException;

public interface KundeInterface extends NutzerInterface {

    // gibt die Adresse des Kunden zurueck
    String getAdresse() throws RemoteException;

    // gibt den Warenkorb des Kunden zurück
    WarenkorbInterface getWk() throws RemoteException;

}