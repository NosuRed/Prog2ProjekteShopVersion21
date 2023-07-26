/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Anwendungslogikklassen;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

import Exception.ArtikelIstSchonVorhanden;
import Exception.ArtikelNichtGefundenException;
import Exception.NegativBestandException;
import KernKlassen.Artikel;
import KernklassenInterface.ArtikelInterface;
import Persistence.PersistenceDaten;
import Persistence.PersistenceSchnittstelle;

import java.rmi.RemoteException;

// Die Klasse dient für die Verwaltungen der Artikel in einer Liste
public class ArtikelVerw {

    /**
     * Die Eigenschaften der Artikelverwaltungsklasse
     * Die Artikel werden verwaltet durch eine Vektorenliste
     */
    private Vector<ArtikelInterface> artVerw = new Vector<ArtikelInterface>();

    /**
     * PersistenceDaten objekt wird erzeugt,
     * dass für den Datenzugriff verantwortlich ist
     */
    private PersistenceSchnittstelle persistenceDaten = new PersistenceDaten();

    /**
     * Der Artikel den man einfügen möchte,
     * wird in die Artikelverwaltungsliste eingefügt
     * Es wird überprüft ob der Artikel bereits hinzugefügt wurde,
     * wenn ja : gibt es eine Fehlermeldung
     * wenn nein : wird es hinzugefügt zur Liste
     * 
     * @param artikel
     * @throws IOException
     * @throws ArtikelIstSchonVorhanden
     */
    public void artEinfuegen(ArtikelInterface artikel) throws IOException, ArtikelIstSchonVorhanden {

        // contain benutzt die überschriebene equals Methode von Artikel
        if (artVerw.contains(artikel)) {
            throw new ArtikelIstSchonVorhanden();
        }

        // Der Vector übernimmt es und wird zur Liste hinzugefügt
        artVerw.add(artikel);
    }

    /**
     * es wird eine Artikelnummer generiert und vergeben,
     * für jeden Artikel der neu angelegt wird
     * wenn die Artikelverwaltungsliste leer ist, dann erhöhe die Artikelnummer des
     * nächsten Artikel der angelegt wird
     * wenn die Artikelliste nicht leer ist, dann sortiere die Artikel in der Liste
     * in dem die Nummern der Artikel verglichen werden
     * und hole dir die höhste Artikelnummer in der Liste und erhöhe die Nummer um 1
     * für das nächste Artikel
     * 
     * @return
     */
    public int generateID() throws RemoteException {
        int articleId = 0;
        if (artVerw.isEmpty()) {
            articleId++;
        } else {
            this.artVerw.sort(Comparator.comparing(t -> {
                try {
                    return t.getNummer();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return -1;
            }));

            articleId = (this.artVerw.get(artVerw.size() - 1).getNummer()) + 1;

        }
        return articleId;

    }

    /**
     * Artikelbezeichnung wird hier gesucht
     * Hier wird eine Liste von Artikel zurückgegeben, wenn die Bezeichnung
     * übereinstimmt
     * 
     * @param bezeichnung
     * @return
     * @throws IOException
     * @throws ArtikelNichtGefundenException
     */
    public Vector<ArtikelInterface> artikelSuchen(String bezeichnung)
            throws IOException, ArtikelNichtGefundenException {

        // Hier wird generics verwendet, die nur Artikel als Datentyp akzeptiert für das
        // Suchergebnis
        Vector<ArtikelInterface> artikelGefunden = new Vector<ArtikelInterface>();

        /**
         * Ein Iterator wird erzeugt
         * Es wird der Iterator benutzt für das durchlaufen der Artikelverwaltungsliste,
         * hasNext : liefert true solange sich Elemente in der Liste befinden
         * next : holt sich das nächste Element in der Liste und weist es der Variable
         * Artikel zu
         */
        Iterator<ArtikelInterface> iterator = artVerw.iterator();
        while (iterator.hasNext()) {
            Artikel artikel = (Artikel) iterator.next();

            if (artikel.getBezeichnung().equals(bezeichnung)) {
                artikelGefunden.add(artikel);
            }

        }

        return artikelGefunden;
    }

    /**
     * Verringert den Bestand eines in der Verwaltung vorhandenen Artikels
     * es wird der Index eines Artikels, falls der Artikel in der Liste sein sollte,
     * einer Variable zugewiesen
     * und holt sich den Index des Artikels und verändert nur den Bestand des
     * Artikels nichts anderes
     * 
     * @param artikel
     * @param neuerBestand
     * @throws NegativBestandException
     * @throws RemoteException
     */
    public void artikelBestandVeraendern(ArtikelInterface artikel, int neuerBestand)
            throws NegativBestandException, RemoteException {
        int listIndex = artVerw.indexOf(artikel);
        if (listIndex != -1) {
            artVerw.get(listIndex).setBestand(neuerBestand);
        }
    }

    /**
     * Hier wird eine Kopie der Artikelliste zurückgegeben
     * 
     * @return
     */
    public Vector<ArtikelInterface> getArtikelListe() {
        return new Vector<ArtikelInterface>(artVerw);
    }

    /**
     * Hier werden Artikeldaten eingelesen aus einer Datei
     * 
     * @param daten
     * @throws IOException
     */
    public void datenEinlesen(String daten) throws IOException {

        // Hier wird die PersistenceSchnittstelle für Leseverläufe geöffnet
        persistenceDaten.openForReading(daten);

        Artikel artikel;

        do {
            // Hier wird ein Artikel Objekt eingelesen
            artikel = persistenceDaten.ladeArtikel();

            if (artikel != null) {

                // Artikel wird in die Liste eingefügt
                try {
                    artEinfuegen(artikel);
                } catch (ArtikelIstSchonVorhanden a) {
                    // Wenn es den Artikel schon gibt , dann kommt ein Fehler
                    a.getMessage();
                }
            }
        } while (artikel != null);

        // Schließen der PersistenceSchnittstelle
        persistenceDaten.close();
    }

    /**
     * Hier werden Artikeldaten in einer Datei geschrieben
     * 
     * @param daten
     * @throws IOException
     */
    public void datenSchreiben(String daten) throws IOException {

        // PersistenceSchnittstelle wird für den Schreibverlauf geöffnet
        persistenceDaten.openForWriting(daten);

        // Wenn die Artikelverwaltungsliste nicht leer ist dann soll er den Artikel
        // einspeichern in die Liste
        if (!artVerw.isEmpty()) {
            Iterator<ArtikelInterface> it = artVerw.iterator();

            // solange Elemente sich in der Liste befinden , dann die Liste durchgehen und
            // Elemente sich raus holen
            while (it.hasNext()) {
                Artikel art = (Artikel) it.next();
                persistenceDaten.speichereArtikel(art);
            }
        }

        // Hier wird die PersistenceSchnittstelle geschlossen
        persistenceDaten.close();
    }
}
