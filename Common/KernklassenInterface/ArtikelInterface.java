// Package not detected, please report project structure on CodeTogether's GitHub Issues
package KernklassenInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Exception.NegativBestandException;

public interface ArtikelInterface extends Remote {

    // gibt die Bezeichnung fuer den Artikel zurueck
    public String getBezeichnung() throws RemoteException;
    
    // gibt die Nummer fuer den Artikel zurueck
    public int getNummer() throws RemoteException;
   
    // gibt den Artikelbestand zurueck
    public int getBestand() throws RemoteException;

    // der Bestand fuer den Artikel wird geaendert
    public void setBestand(int bestand) throws NegativBestandException, RemoteException;

    // gibt den Artikelpreis zurueck
    public float getPreis() throws RemoteException;


     

    

}
