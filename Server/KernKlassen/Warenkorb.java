/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package KernKlassen;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import KernklassenInterface.ArtikelInterface;
import KernklassenInterface.WarenkorbInterface;

// Die Klasse Warenkorb ist zustaendig für eine Liste mit Artikeln, die vom Kunden hinzugefügt werden
public class Warenkorb extends UnicastRemoteObject implements WarenkorbInterface {

    // Die Eigenschaften der Klasse Warenkorb, die deklariert und initialisiert werden

    protected Warenkorb() throws RemoteException {
        super();
    }

    /**
     * Es wird eine Hashmap deklariert und initialisiert
     * in dieser Hashmap werden nur Artikel Elemente und Integer Elemente hinzugefügt
     */
    private Map<ArtikelInterface, Integer> zuKaufendeArt = new HashMap<ArtikelInterface, Integer>();

    /**
     * Eine weitere Liste die einfach nur die zuKaufendeArt Liste 
     * von einer Map zu einer List umwandelt
     * hier wird sie deklariert
     */
    private List<ArtikelInterface> kaufendeArtikel;

    /**
     * dient fuer das leeren des kompletten Warenkorbs und
     * entfernt alle Artikel aus dem Warenkorb
     */
    @Override
    public void leeren() throws RemoteException {
        zuKaufendeArt.clear();
    }

    // gibt die Map der kaufende Artikel zurück
    @Override
    public Map<ArtikelInterface, Integer> getZuKaufendeArt() throws RemoteException {
        return zuKaufendeArt;
    }

    /**
     * gibt die umgewandelte kaufende Artikelliste zurück
     * nach der initialisierung
     * @return kaufendeArtikel
     */
    @Override
    public List<ArtikelInterface> getKaufendeArt() throws RemoteException {
        kaufendeArtikel = new ArrayList<ArtikelInterface>(zuKaufendeArt.keySet());
        return kaufendeArtikel;
    }

    /**
     * die Artikel werden zum Warenkorb hinzugefuegt, sowie die Stückzahl
     * if wird ausgeführt wenn der Artikel sich noch nicht im Warenkorb befindet, sie fügt den Artikel dann hinzu
     * else wird ausgeführt wenn der Artikel sich bereits im Warenkorb befindet 
     * und holt sich die Stückzahl vom Artikel und ersetzt die Stückzahl, in dem die alte Stückzahl mit der 
     * neuen Stückzahl addiert wird  
     * @param art
     * @param stueckzahl
     */
    @Override
    public void hinzufuegenArtikel(ArtikelInterface art, int stueckzahl) throws RemoteException{
        if (!zuKaufendeArt.containsKey(art)) {
            zuKaufendeArt.put(art, stueckzahl);
        } else {
            int alteStueckzahl = zuKaufendeArt.get(art);
            int neueStueckzahl = alteStueckzahl + stueckzahl;
            zuKaufendeArt.put(art, neueStueckzahl);
            //throw new ArtikeIstBereitsImWarenkorb(art);
        }
    }

    // die Anzahl der Artikel koennen erhoeht oder reduziert werden
    @Override
    public void stueckzahlVeraendern(ArtikelInterface art, int stueckzahl) throws RemoteException {
        zuKaufendeArt.replace(art, stueckzahl);
    }

    /**
     * es wird ein Gesamtpreis aller Artikel ausgerechnet und
     * laesst sich den dann ausgeben
     * @return gesamtwert errechnet sich aus den addierten Preis und Stückzahl werten
     */
    @Override
    public float gesamtPreis() throws RemoteException {
        float gesamtwert = 0.0f;
        for (ArtikelInterface artikel : zuKaufendeArt.keySet()) {
            gesamtwert += artikel.getPreis() * zuKaufendeArt.get(artikel); // Holt sich den Preis des jeweiligen
                                                                           // Artikels und multipliziert es mit der
                                                                           // Anzahl
        }
        return gesamtwert;
    }

    // dient fuer das entfernen eines kompletten Artikels aus dem Warenkorb
    @Override
    public void entfernen(ArtikelInterface art) throws RemoteException {
        zuKaufendeArt.remove(art);
    }

    /**
     * erstellt einen String aus allen Artikeln mit ihrer Stückzahl
     * dazu zählen alle Datensätze der Artikel die vorhanden sind
     */
    @Override
    public String toString() {
        String artikelAuflistung = "";
        for (ArtikelInterface artikel : zuKaufendeArt.keySet()) {
            artikelAuflistung += "\n" + artikel.toString() + "\nStückzahl: " + zuKaufendeArt.get(artikel);
        }
        return artikelAuflistung;
    }

}
