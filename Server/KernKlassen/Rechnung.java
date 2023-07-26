/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package KernKlassen;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import KernklassenInterface.KundeInterface;
import KernklassenInterface.RechnungInterface;
import KernklassenInterface.WarenkorbInterface;

// Die Rechnungsklasse ist zuständig fuer den Abschluß des kaufes 
public class Rechnung extends UnicastRemoteObject implements RechnungInterface {
    
    // Die Eigenschaften der Klasse Rechnung, die hier deklariert werden 
    private WarenkorbInterface warenKorb;
    private float gesamtPreis;
    private int datum;
    private String kaeuferName;

    /**
     * Der Konstruktor, um eine Rechnung zu erzeugen 
     * Es werden hier die Variablen initialisiert durch die jeweiligen Parameter
     * @param kaeufer
     * @param date
     * @throws RemoteException
     */
    public Rechnung(KundeInterface kaeufer, int date) throws RemoteException {
        this.kaeuferName = kaeufer.getName();
        this.warenKorb = kaeufer.getWk();
        this.gesamtPreis = warenKorb.gesamtPreis();
        this.datum = date;
    }

    /**
     * gibt den Datensatz für eine Rechnung aus
     * dazu zählen der Kunde(käufer), datum, Warenkorb und der Gesamtpreis der Artikel
     */
    @Override
    public String toString()  {
        return "===IHRE RECHNUNG===" +
                "\nDer Käufer: " + this.kaeuferName +
                "\nDatum: " + datum +
                "\n==Artikel== " + warenKorb.toString() +
                "\nGesamtwert: " + gesamtPreis + " EUR";
    }
}
