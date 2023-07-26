/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package KernKlassen;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import KernklassenInterface.ArtikelInterface;
import Exception.NegativBestandException;

/**
 * Die Klasse dient für das erzeugen neuer Artikel, 
 * mit bestimmten Eigenschaften
 */
public class Artikel extends UnicastRemoteObject implements ArtikelInterface {

    /**
     * Die Eigenschaften das ein Artikel Objekt hat
     * Deklaration der folgenden Artikeleigenschaften als private
     */
    private int artNummer;
    private String artBezeichnung;
    private int artBestand;
    private float artPreis;

    /**
     * Artikel Konstruktor für die Variablen nummer, bezeichnung, bestand,
     * preis, die initialisiert werden
     * @param nummer
     * @param bezeichnung
     * @param bestand
     * @param preis
     */

    public Artikel(int nummer, String bezeichnung, int bestand, float preis) throws RemoteException{
        this.artNummer = nummer;
        this.artBezeichnung = bezeichnung;
        this.artBestand = bestand;
        this.artPreis = preis;
    }
    
    /**
     * Weiterer Artikel Konstruktor fuer die Variablen nummer, bezeichnung, bestand,
     * preis, die initialisiert werden
     * @param nummer
     * @param bezeichnung
     * @param bestand
     * @param preis
     */
    public Artikel(String nummer, String bezeichnung, int bestand, float preis) throws RemoteException{
        this.artBezeichnung = bezeichnung;
        this.artBestand = bestand;
        this.artPreis = preis;
        this.artNummer = Integer.parseInt(nummer);
    }

    // gibt die Bezeichnung fuer den Artikel zurueck
    public String getBezeichnung() throws RemoteException{
        return artBezeichnung;
    }

    // gibt die Nummer fuer den Artikel zurueck
    public int getNummer() throws RemoteException {
        return this.artNummer;
    }

    // gibt den Artikelbestand zurueck
    public int getBestand(){
        return artBestand;
    }

    // der Bestand fuer den Artikel wird geaendert
    public void setBestand(int bestand) throws NegativBestandException, RemoteException{

        this.artBestand = bestand;
        if(this.artBestand < 0){
            throw new NegativBestandException();
        }
    }

    // gibt den Artikelpreis zurueck
    public float getPreis() throws RemoteException {
        return artPreis;
    }

    /**
     * Es werden hier die Eigenschaften eines Artikels mit den Eigenschaften eines anderen Artikels verglichen,
     * wegen einer übereinstimmung(die Equals Methode wird hier überschrieben)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }

        Artikel other = (Artikel) obj;
        if (this.artBezeichnung == null) {
            if (other.artBezeichnung != null){
                return false;
            }
        } else if (!this.artBezeichnung.equals(other.artBezeichnung)){
            return false;
        }
        return true;


    }

    /**
     * gibt den Datensatz fuer den Artikel aus
     * dazu zählt Artikelnummer, Artikelbezeichnung, Artikelbestand und Artikelpreis
     */
    @Override
    public String toString() {
        return "Artikelnummer: " + artNummer + ", Bezeichnung: " + artBezeichnung + ", Bestand: " + artBestand + ", Preis: "
                + artPreis;
    }

}

