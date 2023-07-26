/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package KernKlassen;

import java.rmi.RemoteException;

import KernklassenInterface.KundeInterface;
import KernklassenInterface.WarenkorbInterface;

/**
 * Die Unterklasse "Kunde" wurde von der Oberklasse "Nutzer" abgeleitet
 * Die Klasse dient für das erzeugen neuer Kunden, 
 * mit bestimmten Eigenschaften
 */
public class Kunde extends Nutzer implements KundeInterface {

    // zusätzliche Eigenschaft fuer den Kunden und ein Warenkorb der einen Kunden dann zugewiesen wird als private
    private String adresse;
    private WarenkorbInterface warenKorb;

    /**
     * Der Konstruktor fuer die Klasse Kunde,
     * die Variablen werden initialisiert und von der Superklasse Nutzer verwendet
     * ein neuer Warenkorb wird erzeugt für den jeweiligen Kunden
     * @param kundName
     * @param benutzerName
     * @param kundNummer
     * @param kundAdresse
     * @param kundPasswort
     */
    public Kunde(String kundName, String benutzerName, int kundNummer, String kundAdresse, String kundPasswort) throws RemoteException {
        super.name = kundName;
        super.benutzername = benutzerName;
        super.nummer = kundNummer;
        this.adresse = kundAdresse;
        super.passwort = kundPasswort;
        warenKorb = new Warenkorb();
    }

    // gibt die Adresse des Kunden zurueck
    @Override
    public String getAdresse() throws RemoteException{
        return this.adresse;
    }

    // gibt den Warenkorb des Kunden zurück
    @Override
    public WarenkorbInterface getWk() throws RemoteException{
        return warenKorb;
    }

    /**
     * gibt den Datensatz vom Kunden aus
     * dafür wird die toString Methode von der Superklasse aufgerufen und zusätzlich die Adresse
     */
    @Override
    public String toString()  {
        return super.toString() + ", Adresse=" + adresse;
    }

}
