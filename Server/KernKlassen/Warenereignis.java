/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package KernKlassen;

import java.rmi.server.UnicastRemoteObject;

import KernklassenInterface.WarenereignisInterface;
import java.rmi.RemoteException;

/**
 * Diese Klasse dient dazu ein Warenereignis festzuhalten
 * dazu zählen sämtliche Wareneingänge und Warenausgänge, die vom Kunden oder Mitarbeiter getätigt werden 
 */
public class Warenereignis extends UnicastRemoteObject implements WarenereignisInterface {
    
    // Die Eigenschaften werden die hier deklariert werden
    private int verantwortlicher;

    // Sollte entweder 'k'(Kunde) oder 'm'(Mitarbeiter) sein für den entsprechenden Nutzertypen 
    private char nutzertyp;

    private int bewegterArtikel;
    private int stueckzahl;
    private int datum;

    /**
     * Wenn ein Artikel seine Stückzahl verringert oder erhöht bekommt, 
     * dann wird ein Warenereignis mit folgenden Informationen darüber geworfen:
     * @param verantwNr : Nummer des Nutzers, der die Waren bewegt hat
     * @param nutzertyp : Sollte entweder 'k' oder 'm' sein für den entsprechenden Nutzertypen
     * @param artNr : Nummer des bewegten Artikels
     * @param stueck : Stückzahl des bewegten Artikel. Negativ bei Artikelentnahme. Positiv bei Artikelzugabe
     * @param jahrestag : Zu welchem Tag des Jahres ist es geschehen
     */
    public Warenereignis(int verantwNr, char nutzertyp, int artNr, int stueck, int jahrestag) throws RemoteException {
        verantwortlicher = verantwNr;
        this.nutzertyp = nutzertyp;
        bewegterArtikel = artNr;
        stueckzahl = stueck;
        datum = jahrestag;
    }

    // gibt die Käufernummer zurück
    @Override
    public int getVerantwortlicher() throws RemoteException {
        return verantwortlicher;
    }
    
    // gibt den Nutzertypen zurück
    @Override
    public char getNutzertyp() throws RemoteException{
        return nutzertyp;
    }

    // gibt die Artikelnummer zurück
    @Override
    public int getBewegterArtikel() throws RemoteException{
        return bewegterArtikel;
    }

    /**
     * Gibt die Anzahl zurück, um wieviel der Bestand verändert wurde.
     * Positiv bedeutet Artikel kamen hinzu.
     * Negativ bedeutet Artikel wurden entfernt.
     * @return Negative oder positive Stückzahl 
     */
    @Override
    public int getStueckzahl() throws RemoteException{
        return stueckzahl;
    }

    // gibt das Datum zurück
    @Override
    public int getDatum() throws RemoteException{
        return datum;
    }

    /**
     * gibt den Datensatz eines Warenereignisses aus
     * dazu zählen der Käufer(Kundennummer), Nutzertypen(Kunde oder Mitarbeiter), Artikel(Artikelnummer), 
     * die Anzahl(Stückzahl) und Datum 
     */
    @Override
    public String toString() {
        return "===WARENEREIGNIS===" +
                "\nVerantwortlicher:" + verantwortlicher +
                "\nNutzertyp: " + nutzertyp +
                "\nBewegter Artikel: " + bewegterArtikel +
                "\nStueckzahl: " + stueckzahl +
                "\nDatum=" + datum;
    }


}
