package KernklassenInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface WarenkorbInterface extends Remote{

    /**
     * dient fuer das leeren des kompletten Warenkorbs und
     * entfernt alle Artikel aus dem Warenkorb
     */
    void leeren() throws RemoteException;

    // gibt die Map der kaufende Artikel zurück
    Map<ArtikelInterface, Integer> getZuKaufendeArt() throws RemoteException;

    /**
     * gibt die umgewandelte kaufende Artikelliste zurück
     * nach der initialisierung
     * @return kaufendeArtikel
     */
    List<ArtikelInterface> getKaufendeArt() throws RemoteException;

    /**
     * die Artikel werden zum Warenkorb hinzugefuegt, sowie die Stückzahl
     * if wird ausgeführt wenn der Artikel sich noch nicht im Warenkorb befindet, sie fügt den Artikel dann hinzu
     * else wird ausgeführt wenn der Artikel sich bereits im Warenkorb befindet 
     * und holt sich die Stückzahl vom Artikel und ersetzt die Stückzahl, in dem die alte Stückzahl mit der 
     * neuen Stückzahl addiert wird  
     * @param art
     * @param stueckzahl
     */
    void hinzufuegenArtikel(ArtikelInterface art, int stueckzahl) throws RemoteException;

    // die Anzahl der Artikel koennen erhoeht oder reduziert werden
    void stueckzahlVeraendern(ArtikelInterface art, int stueckzahl) throws RemoteException;

    /**
     * es wird ein Gesamtpreis aller Artikel ausgerechnet und
     * laesst sich den dann ausgeben
     * @return gesamtwert errechnet sich aus den addierten Preis und Stückzahl werten
     */
    float gesamtPreis() throws RemoteException;

    // dient fuer das entfernen eines kompletten Artikels aus dem Warenkorb
    void entfernen(ArtikelInterface art) throws RemoteException;

    /**
     * erstellt einen String aus allen Artikeln mit ihrer Stückzahl
     * dazu zählen alle Datensätze der Artikel die vorhanden sind
     */



}