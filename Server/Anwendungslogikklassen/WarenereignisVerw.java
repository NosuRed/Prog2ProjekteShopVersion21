/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Anwendungslogikklassen;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import KernklassenInterface.ArtikelInterface;
import KernklassenInterface.WarenereignisInterface;
import KernKlassen.Warenereignis;
import Persistence.PersistenceDaten;
import Persistence.PersistenceSchnittstelle;

// Die Klasse dient für die Verwaltung der Warenereignisse in einer Liste 
public class WarenereignisVerw {

    /**
     * Die Eigenschaften der Warenereigniswaltungsklasse
     * Die Warenereignisse werden verwaltet durch eine Vektorenliste
     */
    private Vector<WarenereignisInterface> warenereignisVerw = new Vector<WarenereignisInterface>();

    /**
     * PersistenceDaten objekt wird erzeugt, 
     * dass für den Datenzugriff verantwortlich ist
     */
    private PersistenceSchnittstelle persistenceDaten = new PersistenceDaten();

    /** 
     * Das Warenereignis wird zur Warenereignisverwaltungsliste hinzugefügt 
     */
    public void warenereignisEinfuegen(WarenereignisInterface warerg) throws IOException {

        // Der Vector übernimmt es und wird zur Liste hinzugefügt
        warenereignisVerw.add(warerg);
    }

    /**
     * Nach Artikelnummer wird hier gesucht.
     * Es wird eine Liste von Warenereignissen zurückgegeben, bei denen die Artikelnummer übereinstimmt.
     * @param artikelNr
     * @return
     * @throws IOException
     */
    public Vector<WarenereignisInterface> warenereignisSuchen(int artikelNr) throws IOException {

        // Hier wird generics verwendet , die nur Artikel als Datentyp akzeptiert für das Suchergebnis
        Vector<WarenereignisInterface> warenereignisGefunden = new Vector<WarenereignisInterface>();

         /**
         * Ein Iterator wird erzeugt
         * Es wird der Iterator benutzt für das durchlaufen der Warenereignisverwaltungsliste,
         * hasNext : liefert true solange sich Elemente in der Liste befinden
         * next : holt sich das nächste Element in der Liste und weist es der Variable Kunde zu
         */
        Iterator<WarenereignisInterface> it = warenereignisVerw.iterator();
        while (it.hasNext()) {
            Warenereignis warerg = (Warenereignis) it.next();

            if (warerg.getBewegterArtikel() == artikelNr) {
                warenereignisGefunden.add(warerg);
            }

        }

        return warenereignisGefunden;
    }

    /**
     * Hier wird eine Kopie der Warenereignisliste zurückgegeben
     * @return
     */
    public Vector<WarenereignisInterface> getWarenereignisListe() {
        return new Vector<WarenereignisInterface>(warenereignisVerw);
    }

    /**
     * Hier werden Warenereignisdaten eingelesen aus einer Datei
     * @param daten
     * @throws IOException
     */
    public void datenEinlesen(String daten) throws IOException {

        // Hier wird die PersistenceSchnittstelle für Leseverläufe geöffnet
        persistenceDaten.openForReading(daten);

        Warenereignis warenereignis;

        do {
            // Hier wird ein Warenereignis Objekt eingelesen
            warenereignis = persistenceDaten.ladeWarenereignis();

            if (warenereignis != null) {

                // Warenereignis wird in die Liste eingefügt
                // try {
                warenereignisEinfuegen(warenereignis);
                // } catch (WarenereignisGibtEsSchon m) {
                // Wenn es das Warenereignis schon gibt, dann kommt ein Fehler
                // m.getMessage();
                // }
            }
        } while (warenereignis != null);

        // Schließen der PersistenceSchnittstelle
        persistenceDaten.close();
    }

    /**
     * Hier werden Warenereignisdaten in einer Datei geschrieben
     * @param daten
     * @throws IOException
     */
    public void datenSchreiben(String daten) throws IOException {

        // PersistenceSchnittstelle wird für den Schreibverlauf geöffnet
        persistenceDaten.openForWriting(daten);

        // Wenn die Warenereignisverwaltungsliste nicht leer ist, dann soll er das Warenereignis einspeichern in die Liste
        if (!warenereignisVerw.isEmpty()) {
            Iterator<WarenereignisInterface> it = warenereignisVerw.iterator();

            // solange Elemente sich in der Liste befinden , dann die Liste durchgehen und Elemente sich raus holen
            while (it.hasNext()) {
                WarenereignisInterface warenereignis = (WarenereignisInterface) it.next();
                persistenceDaten.speichereWarenereignis(warenereignis);
            }
        }

        // Hier wird die PersistenceSchnittstelle geschlossen
        persistenceDaten.close();
    }

    /**
     * Erstellt einen Array, der eine Länge DAUER + 1 hat. In ihm enthalten sind
     * alle Tage die in den gewünschten
     * Zeitraum fallen. heutigerJahrestag - DAUER ist das Intervall in welchen die
     * einzelnen Tage fallen. Ist heutigerJahrestag <= DAUER
     * so ist es der Spezialfall des Jahreswechsels.
     * 
     * @param heutigerJahrestag
     * @return zeitraum: Ein Array, der alle einzelnen Tagen in dem gewünschten
     *         Zeitraum enthält
     */
    public int[] zeitraumAlsArray(int heutigerJahrestag) {
        int DAUER = 30;

        int[] zeitraum = new int[DAUER + 1];
        int ende = heutigerJahrestag - DAUER;

        // Prüfung, ob die Bestandshistorie über den Jahresanfang hinausgeht.
        // Bedingung trifft zu wenn heutigerJahrestag <= DAUER
        // Anschließend Füllung von zeitraum mit den Zwischenwerten von
        // heutigerJahrestag bis ende. In absteigender Form
        int tag = 0;
        if (ende < 1) {
            ende = Math.abs(ende);
            //int zeitraumZumJahresanfang = DAUER - heutigerJahrestag;
            int zeitraumAbJahresende = 365 - ende;
            for (int i = 0; i <= ende; i++) {
                tag = zeitraumAbJahresende + i;
                zeitraum[i] = tag;
            }
            for (int i = ende + 1; i <= DAUER; i++) {
                tag = 1 + (i - ende - 1);
                zeitraum[i] = tag;
            }
        } else {
            for (int i = 0; i <= DAUER; i++) {
                tag = ende + i;
                zeitraum[i] = tag;
            }
        }

        return zeitraum;
    }

    /**
     * Gibt einen Array zurück, der zu einem Artikel die Bestandstandsveränderung
     * beinhaltet und das auf eine Länge von 30 Tagen.
     * Der letzte Wert ist der heutigeJahrestag. Von da wird absteigend gelistet.
     * 
     * @param art      : Das Artikelobjekt zu dem die Bestandshistorie ermittelt
     *                 werden soll.
     * @param zeitraum : Ein Array, der alle einzelnen Tagen in dem gewünschten
     *                 Zeitraum enthält
     * @return Map(Integer, Integer)
     * @throws IOException
     */
    public int[] getBestandshistorie(ArtikelInterface art, int[] zeitraum) throws IOException {

        Vector<WarenereignisInterface> alleWarenereignisse = warenereignisSuchen(art.getNummer());

        Vector<WarenereignisInterface> gefilterteWarenereignisse = new Vector<>();

        // Geht alle Warenereignisse durch und prüft, ob sie im Zeitraum sind
        for (WarenereignisInterface warerg : alleWarenereignisse) {
            int warenereignisTag = warerg.getDatum();

            /**
             * Geht jedes Element (zeitraumTag) im Zeitraum Array durch und packt es beim
             * Zutreffen der Bedingung mit warenereignisTag in die gefilterte Liste
             * Ist eine elegantere und schlankere Schreibweise. Ansonsten würde man hierfür
             * eine zweite
             * for-Schleife benötigen.
             */
            if (Arrays.stream(zeitraum).anyMatch(zeitraumTag -> zeitraumTag == warenereignisTag)) {
                gefilterteWarenereignisse.add(warerg);
            }
        }
        /**
         * Sortieren der gefiltertenWarenereignisse nach dem Jahrestag in
         * absteigender Reihenfolge.
         */
        gefilterteWarenereignisse
                .sort((ereignis1, ereignis2) -> {
                    try {
                        return Integer.compare(ereignis2.getDatum(), ereignis1.getDatum());
                    } catch (RemoteException e) {
                        // TODO RemoteException
                    }
                    return -1;
                });

        // Erstellen eines int Arrays, um die Bestandsänderungen für jeden Tag zu verfolgen
        int[] bestandsaenderungen = new int[zeitraum.length];

        int jetzigerBestand = art.getBestand();

        /**
         * Iteration über den gesamten Zeitraum und Iteration über die gefiltertenWarenereignisse. 
         * Bei übereinstimmenden Daten wird der jetzigeBestand aktualisiert.
         */
        for (int i = (zeitraum.length - 1); i >= 0; i--) {
            int aenderung = jetzigerBestand;

            for (WarenereignisInterface warenerg : gefilterteWarenereignisse) {
                int jahrestag = warenerg.getDatum();

                if (zeitraum[i] == jahrestag) {
                    aenderung = warenerg.getStueckzahl();
                    // Bestandsänderung auf den jetzigen Bestand anwenden
                    jetzigerBestand -= aenderung; // Bei einer Artikelentnahme werden die entnommen Artikel wiederdrauf
                                                  // addiert.
                    // (- und - ergibt +)
                    // Und bei einer Zugabe werden die hineingepackten Artikel wieder entfernt.
                    // (- und + ergibt -)

                }
            }

            // Bestand für den aktuellen Tag in die Map bestandsaenderungen eintragen oder aktualisieren
            bestandsaenderungen[i] = jetzigerBestand;
        }

        return bestandsaenderungen;

        // gefilterteWarenereignisse sortiert werden nach heutigen Jahrestag in
        // absteigender Reihenfolge.
        // Warenereignisse am gleichen Tag sollen zu einer Summe addiert werden
        // (Veränderungen).
        // Jetziger Bestand soll geholt werden und dann von den Veränderungen erhöht
        // oder erniedrigt werden. Der veränderte Bestand wird an dann an das nächste
        // Warenereignis runtergegeben
        // Sodass man am Ende eine Liste hat, wo drin steht, was für einen absoluten
        // Bestand man am jeweiligen Tag zu dem Artikel hatte.
        // Artikel Apfel jetziger Bestand 30, heutiger Tag 360
        // Am 346 Tag wurden 7 Apfel herausgenommen
        // Am 330 Tag wurden 10 Apfel hinneingepackt
        // Am 330 Tag wurden 2 Apfel herausgenommen
        // Am 200 Tag wurden 20 Apfel herausgenommen
        // Finale Liste zu "Apfel"
        // Bestand am Tag 360: 30
        // Bestand am Tag 346: 37
        // Bestand am Tag 330: 29

        // Beispiel mit Jahreswechsel
        // Apfel jetziger Bestand 30, heutiger Tag 29
        // Am 16 Tag wurden 10 Apfel hinneingepackt
        // Am 365 Tag wurden 20 Apfel hinneingepackt
        // Am 364 Tag wurden 2 Apfel herausgenommen

        // Finale Liste zu Apfel
        // Bestand am Tag 29: 30
        // Bestand am Tag 16: 20
        // Bestand am Tag 365: 0
        // Bestand am Tag 364: 2

    }
}
