package KernklassenInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WarenereignisInterface extends Remote {

    // gibt die Käufernummer zurück
    int getVerantwortlicher() throws RemoteException;

    // gibt den Nutzertypen zurück
    char getNutzertyp() throws RemoteException;

    // gibt die Artikelnummer zurück
    int getBewegterArtikel() throws RemoteException;

    /**
     * Gibt die Anzahl zurück, um wieviel der Bestand verändert wurde.
     * Positiv bedeutet Artikel kamen hinzu.
     * Negativ bedeutet Artikel wurden entfernt.
     * @return Negative oder positive Stückzahl 
     */
    int getStueckzahl() throws RemoteException;

    // gibt das Datum zurück
    int getDatum() throws RemoteException;

    /**
     * gibt den Datensatz eines Warenereignisses aus
     * dazu zählen der Käufer(Kundennummer), Nutzertypen(Kunde oder Mitarbeiter), Artikel(Artikelnummer), 
     * die Anzahl(Stückzahl) und Datum 
     */

}