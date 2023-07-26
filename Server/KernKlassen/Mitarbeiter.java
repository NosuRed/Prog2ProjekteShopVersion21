/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package KernKlassen;

import java.rmi.RemoteException;

import KernklassenInterface.MitarbeiterInterface;

/**
 * Die Unterklasse Mitarbeiter, die abgeleitet wurde von der Superklasse Nutzer
 * Die Klasse dient für das erzeugen neuer Mitarbeiter
 */
public class Mitarbeiter extends Nutzer implements MitarbeiterInterface{

    /**
     * Der Konstruktor für den Mitarbeiter, hier werden alle Eigenschaften von der Superklasse
     * übernommen und initialisiert mit den Parametern
     * @param mitarName
     * @param benutzerName
     * @param mitarNummer
     * @param mitarPasswort
     */
    public Mitarbeiter(String mitarName, String benutzerName, int mitarNummer, String mitarPasswort) throws RemoteException{
        super.name = mitarName;
        super.benutzername = benutzerName;
        super.nummer = mitarNummer;
        super.passwort = mitarPasswort;
    }
    
}
