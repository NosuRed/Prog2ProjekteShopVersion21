package KernklassenInterface;

import java.rmi.RemoteException;

public interface MassengutArtikelInterface extends ArtikelInterface{
    /**
     * Es wird der Artikelpreis überschrieben und ein neuer Artikelpreis zurückgegeben
     * in dem der alte Artikelpreis multipliziert wird mit der Anzahl der Artikel
     */
    float getPreis() throws RemoteException;

    // Es wird die Anzahl der Artikel zurückgegeben
    int getAnzahl() throws RemoteException;

    // Es wird die Artikelbezeichnung zurückgegeben
    String getBezeichnung() throws RemoteException;

}