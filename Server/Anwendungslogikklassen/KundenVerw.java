/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Anwendungslogikklassen;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

import Exception.FalscheAnmeldeDatenException;
import Exception.KundeGibtEsSchonException;
import KernKlassen.Kunde;
import KernklassenInterface.KundeInterface;
import Persistence.PersistenceDaten;
import Persistence.PersistenceSchnittstelle;

// Die Klasse dient für die Verwaltungen der Kunden in einer Liste
public class KundenVerw {

    /**
     * Die Eigenschaften der Kundenverwaltungsklasse
     * Die Kunden werden verwaltet durch eine Vektorenliste 
     */
    private Vector<KundeInterface> kundVerw = new Vector<KundeInterface>();

    /**
     * PersistenceDaten objekt wird erzeugt, 
     * dass für den Datenzugriff verantwortlich ist
     */
    private PersistenceSchnittstelle persistenceDaten = new PersistenceDaten();

    /**
     * Der Kunde den man einfügen möchte, 
     * wird in die Kundenverwaltungsliste eingefügt
     * Es wird überprüft ob der Kunde bereits hinzugefügt wurde, 
     * wenn ja : gibt es eine Fehlermeldung
     * wenn nein : wird es hinzugefügt zur Liste
     * @param kund
     * @throws IOException
     * @throws KundeGibtEsSchonException
     */
    public void kundenEinfuegen(KundeInterface kund) throws IOException, KundeGibtEsSchonException {

        // contain benutzt die überschriebene equals Methode von Nutzer
        if (kundVerw.contains(kund)) {
            throw new KundeGibtEsSchonException();
        }

        // Der Vector übernimmt es und wird zur Liste hinzugefügt
        kundVerw.add(kund);

    }

    /**
     * es wird eine Kundennummer generiert und vergeben,
     * für jeden Kunden der neu angelegt wird
     * wenn die Kundenverwaltungsliste leer ist, dann erhöhe die Kundennummer des nächsten Kunden der angelegt wird
     * wenn die Kundenliste nicht leer ist, dann sortiere die Kunden in der Liste in dem die Nummern der Kunden verglichen werden
     * und hole dir die höhste Kundennummer in der Liste und erhöhe die Nummer um 1 für den nächsten Kunden
     * @return
     */
    public int generateID() throws RemoteException {
        int customerId = 0;
        if (kundVerw.isEmpty()) {
            customerId++;
        } else {
            this.kundVerw.sort(Comparator.comparing(t -> {
                try {
                    return t.getNummer();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return -1;
            }));
            customerId = (this.kundVerw.get(kundVerw.size() - 1).getNummer()) + 1;
        }
        return customerId;
    }

    /**
     * Benutzername des Kunden wird hier gesucht
     * Hier wird eine Liste von Kunden zurückgegeben, wenn der Benutzername übereinstimmt
     * @param benutzerName
     * @return
     * @throws FalscheAnmeldeDatenException
     */
    public Vector<KundeInterface> kundenSuchen(String benutzerName) throws FalscheAnmeldeDatenException, RemoteException {

        // Hier wird generics verwendet, die nur Kunde als Datentyp akzeptiert für das Suchergebnis
        Vector<KundeInterface> kundenGefunden = new Vector<KundeInterface>();

        /**
         * Ein Iterator wird erzeugt
         * Es wird der Iterator benutzt für das durchlaufen der Kundenverwaltungsliste,
         * hasNext : liefert true solange sich Elemente in der Liste befinden
         * next : holt sich das nächste Element in der Liste und weist es der Variable Kunde zu
         */
        Iterator<KundeInterface> it = kundVerw.iterator();
        while (it.hasNext()) {
            Kunde kunde = (Kunde) it.next();

            if (kunde.getBenutzerName().equals(benutzerName)) {
                kundenGefunden.add(kunde);
            }
        }

        return kundenGefunden;
    }

    /**
     * Hier wird eine Kopie der Kundenliste zurückgegeben
     * @return
     */
    public Vector<KundeInterface> getKundenListe() {
        return new Vector<KundeInterface>(kundVerw);
    }

    /**
     * Hier werden Kundendaten eingelesen aus einer Datei
     * @param daten
     * @throws IOException
     * @throws KundeGibtEsSchonException
     */
    public void datenEinlesen(String daten) throws IOException, KundeGibtEsSchonException {

        // Hier wird die PersistenceSchnittstelle für Leseverläufe geöffnet
        persistenceDaten.openForReading(daten);

        Kunde kunde;

        do {
            // Hier wird ein Kunden Objekt eingelesen
            kunde = persistenceDaten.ladeKunde();

            if (kunde != null) {

                // Kunde wird in die Liste eingefügt
                try {
                    kundenEinfuegen(kunde);
                } catch (KundeGibtEsSchonException k) {
                    // Wenn es den Kunden schon gibt , dann kommt ein Fehler
                    k.getMessage();
                }
            }
        } while (kunde != null);

        // Schließen der PersistenceSchnittstelle
        persistenceDaten.close();
    }

    /**
     * Hier werden Kundendaten in einer Datei geschrieben
     * @param daten
     * @throws IOException
     */
    public void datenSchreiben(String daten) throws IOException {

        // PersistenceSchnittstelle wird für den Schreibverlauf geöffnet
        persistenceDaten.openForWriting(daten);

        // Wenn die Kundenverwaltungsliste nicht leer ist dann soll er den Kunden einspeichern in die Liste
        if (!kundVerw.isEmpty()) {
            Iterator<KundeInterface> it = kundVerw.iterator();

            // solange Elemente sich in der Liste befinden , dann die Liste durchgehen und Elemente sich raus holen
            while (it.hasNext()) {
                Kunde kund = (Kunde) it.next();
                persistenceDaten.speichereKunden(kund);
            }
        }

        // Hier wird die PersistenceSchnittstelle geschlossen
        persistenceDaten.close();
    }
}
