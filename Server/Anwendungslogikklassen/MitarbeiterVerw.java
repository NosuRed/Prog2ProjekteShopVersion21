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
import Exception.MitarbeiterGibtEsSchonException;
import KernKlassen.Mitarbeiter;
import KernKlassen.Nutzer;
import KernklassenInterface.MitarbeiterInterface;
import Persistence.PersistenceDaten;
import Persistence.PersistenceSchnittstelle;

// Die Klasse dient für die Verwaltungen der Mitarbeiter in einer Liste
public class MitarbeiterVerw {

    /**
     * Die Eigenschaften der Mitarbeiterverwaltungsklasse
     * Die Mitarbeiter werden verwaltet durch eine Vektorenliste 
     */
    private Vector<MitarbeiterInterface> mitarVerw = new Vector<MitarbeiterInterface>();

    /**
     * PersistenceDaten objekt wird erzeugt, 
     * dass für den Datenzugriff verantwortlich ist
     */
    private PersistenceSchnittstelle persistenceDaten = new PersistenceDaten();

    /**
     * Der Mitarbeiter den man einfügen möchte, 
     * wird in die Mitarbeiterverwaltungsliste eingefügt
     * Es wird überprüft ob der Mitarbeiter bereits hinzugefügt wurde, 
     * wenn ja : gibt es eine Fehlermeldung
     * wenn nein : wird es hinzugefügt zur Liste
     * @param mitar
     * @throws IOException
     * @throws MitarbeiterGibtEsSchonException
     */
    public void mitarbeiterEinfuegen(MitarbeiterInterface mitar) throws IOException, MitarbeiterGibtEsSchonException {

        // contain benutzt die überschriebene equals Methode von Nutzer
        if (mitarVerw.contains(mitar)) {
            throw new MitarbeiterGibtEsSchonException();
        }

        // Der Vector übernimmt es und wird zur Liste hinzugefügt
        mitarVerw.add(mitar);
    }

    /**
     * es wird eine Mitarbeiternummer generiert und vergeben,
     * für jeden Mitarbeiter der neu angelegt wird
     * wenn die Mitarbeiterverwaltungsliste leer ist, dann erhöhe die Mitarbeiternummer des nächsten Mitarbeiter der angelegt wird
     * wenn die Mitarbeiterliste nicht leer ist, dann sortiere die Mitarbeiter in der Liste in dem die Nummern der Mitarbeiter verglichen werden
     * und hole dir die höhste Mitarbeiternummer in der Liste und erhöhe die Nummer um 1 für den nächsten Mitarbeiter
     * @return
     * @throws RemoteException
     */
    public int generateID() throws RemoteException {
        int articleId = 0;
        if (mitarVerw.isEmpty()) {
            articleId++;
        } else {
            this.mitarVerw.sort(Comparator.comparing(t -> {
                try {
                    return ((Nutzer) t).getNummer();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return -1;
            }));
            articleId = (this.mitarVerw.get(mitarVerw.size() - 1).getNummer()) + 1;
        }
        return articleId;
    }

 

    /**
     * Benutzername des Mitarbeiter wird hier gesucht
     * Hier wird eine Liste von Mitarbeitern zurückgegeben, wenn der Benutzername übereinstimmt
     * @param benutzerName
     * @return
     * @throws FalscheAnmeldeDatenException
     * @throws RemoteException
     */
    public Vector<Mitarbeiter> mitarbeiterSuchen(String benutzerName) throws FalscheAnmeldeDatenException, RemoteException {

        // Hier wird generics verwendet , die nur Mitarbeiter als Datentyp akzeptiert für das Suchergebnis
        Vector<Mitarbeiter> mitarbeiterGefunden = new Vector<Mitarbeiter>();

        /**
         * Ein Iterator wird erzeugt
         * Es wird der Iterator benutzt für das durchlaufen der Mitarbeiterverwaltungsliste,
         * hasNext : liefert true solange sich Elemente in der Liste befinden
         * next : holt sich das nächste Element in der Liste und weist es der Variable Mitarbeiter zu
         */
        Iterator<MitarbeiterInterface> it = mitarVerw.iterator();
        while (it.hasNext()) {
            Mitarbeiter mitarbeiter = (Mitarbeiter) it.next();

            if (mitarbeiter.getBenutzerName().equals(benutzerName)) {
                mitarbeiterGefunden.add(mitarbeiter);
            }
        }

        return mitarbeiterGefunden;
    }

    /**
     * Hier wird eine Kopie der Mitarbeiterliste zurückgegeben
     * @return
     */
    public Vector<MitarbeiterInterface> getMitarbeiterListe() {
        return new Vector<MitarbeiterInterface>(mitarVerw);
    }

    /**
     * Hier werden Mitarbeiterdaten eingelesen aus einer Datei
     * @param daten
     * @throws IOException
     */
    public void datenEinlesen(String daten) throws IOException {

        // Hier wird die PersistenceSchnittstelle für Leseverläufe geöffnet
        persistenceDaten.openForReading(daten);

        Mitarbeiter mitarbeiter;

        do {
            // Hier wird ein Mitarbeiter Objekt eingelesen
            mitarbeiter = persistenceDaten.ladeMitarbeiter();

            if (mitarbeiter != null) {

                // Mitarbeiter wird in die Liste eingefügt
                try {
                    mitarbeiterEinfuegen(mitarbeiter);
                } catch (MitarbeiterGibtEsSchonException m) {
                    // Wenn es den Mitarbeiter schon gibt , dann kommt ein Fehler
                    m.getMessage();
                }
            }
        } while (mitarbeiter != null);

        // Schließen der PersistenceSchnittstelle
        persistenceDaten.close();
    }

    /**
     * Hier werden Mitarbeiterdaten in einer Datei geschrieben
     * @param daten
     * @throws IOException
     */
    public void datenSchreiben(String daten) throws IOException {

        // PersistenceSchnittstelle wird für den Schreibverlauf geöffnet
        persistenceDaten.openForWriting(daten);

        // Wenn die Mitarbeiterverwaltungsliste nicht leer ist dann soll er den Mitarbeiter einspeichern in die Liste
        if (!mitarVerw.isEmpty()) {
            Iterator<MitarbeiterInterface> it = mitarVerw.iterator();

            // solange Elemente sich in der Liste befinden , dann die Liste durchgehen und Elemente sich raus holen
            while (it.hasNext()) {
                Mitarbeiter mitar = (Mitarbeiter) it.next();
                persistenceDaten.speichereMitarbeiter(mitar);
            }
        }

        // Hier wird die PersistenceSchnittstelle geschlossen
        persistenceDaten.close();
    }
}
