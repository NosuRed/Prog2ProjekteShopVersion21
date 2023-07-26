/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package KernKlassen;

import java.rmi.RemoteException;

import KernklassenInterface.MassengutArtikelInterface;

/**
 * Die Klasse ist von der Klasse Artikel abgeleitet
 * Die Klasse dient für das erzeugen neuer Massengutartikel, 
 * mit bestimmten Eigenschaften
 */
public class MassengutArtikel extends Artikel implements MassengutArtikelInterface{

    // Die zusätzliche Eigenschaft für diese Klasse
    private int anzahl;

    /**
     * Der Konstruktor eines Massengutartikel dessen Eigenschaften hier initialisiert werden durch die Parameter
     * Die Eigenschaften werden von der Klasse Artikel übernommen
     * @param nummer
     * @param bezeichnung
     * @param bestand
     * @param preis
     * @param anzahl
     */
    public MassengutArtikel(String nummer, String bezeichnung, int bestand, float preis, int anzahl) throws RemoteException{
        super(nummer, bezeichnung, bestand, preis);
        this.anzahl = anzahl;
    }

    /**
     * weiterer Konstruktor die genutzt wird
     * @param nummer
     * @param bezeichnung
     * @param bestand
     * @param preis
     * @param anzahl
     */
    public MassengutArtikel(int nummer, String bezeichnung, int bestand, float preis, int anzahl) throws RemoteException{
        super(nummer, bezeichnung, bestand, preis);
        this.anzahl = anzahl;
    }

    /**
     * Es wird der Artikelpreis überschrieben und ein neuer Artikelpreis zurückgegeben
     * in dem der alte Artikelpreis multipliziert wird mit der Anzahl der Artikel
     */ 
    @Override
    public float getPreis() throws RemoteException {
        return super.getPreis() * anzahl;
    }

    // Es wird die Anzahl der Artikel zurückgegeben
    @Override
    public int getAnzahl() throws RemoteException{
        return anzahl;
    }

    // Es wird die Artikelbezeichnung zurückgegeben
    @Override
    public String getBezeichnung() throws RemoteException{
        return super.getBezeichnung();
    }

}