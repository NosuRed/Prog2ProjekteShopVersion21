/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package CUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Exception.ArtikelIstSchonVorhanden;
import Exception.ArtikelNichtGefundenException;
import Exception.FalscheAnmeldeDatenException;
import Exception.KundeGibtEsSchonException;
import Exception.MitarbeiterGibtEsSchonException;
import Exception.NegativBestandException;
import KernklassenInterface.eShopInterface;
import Anwendungslogikklassen.eShop;

/**
 * Die Klasse eShop dient für die Registrierung als Kunde, Anmelden als Kunde oder Mitarbeiter
 * sowie das hinzufügen von Artikeln als Mitarbeiter und das kaufen als Kunde
 * die Bestandshistorie der Artikel wird auch ausgegeben 
 * sowie die Bestandsveränderungen der Artikel
 */ 
public class eShopCUI {

    /**
     * Die Eigenschaften der Klasse und die Deklarartion der Variablen
     * und die Initialisierung einiger Variablen
     */
    private eShopInterface eS;
    private BufferedReader br;
    private int kundenNummer = 0;
    private int artikelNr = 0;
    private int mitarNummer = 1;

    /**
     * Die main Methode zum ausführen des eShop mit verschiedenen Optionen, 
     * die ausgewählt werden können 
     * sollten Fehler an den entsprechenden Stellen vorkommen 
     * werden die ausgegeben
     * Es wird ein Cui Objekt angelegt und erzeugt,
     * die zum ausführen weiterer Methoden führt
     * @param args
     * @throws KundeGibtEsSchonException
     * @throws FalscheAnmeldeDatenException
     * @throws IOException
     * @throws MitarbeiterGibtEsSchonException
     * @throws ArtikelIstSchonVorhanden
     * @throws NegativBestandException
     * @throws ArtikelNichtGefundenException
     */
    public static void main(String[] args) throws KundeGibtEsSchonException, FalscheAnmeldeDatenException, IOException,
            MitarbeiterGibtEsSchonException, ArtikelIstSchonVorhanden, NegativBestandException, ArtikelNichtGefundenException {

        eShopCUI ui = new eShopCUI("eShop");
        try {
            ui.ausfuehrenKundeMitarbeiter();
        } catch (IOException e) {
            System.err.println("Der Fehler: " + e.getMessage());
        }
    }

    /**
     * Der Konstruktor für den eShopCUI, der beim Aufruf ein eShop Objekt
     * initialisiert und ein BufferedReader erzeugt für eine Konsoleneingabe
     * Dem Parameter wird eine txt Datei übergeben, dann wird der eShop Konstruktor
     * geöffnet mit dem Namen der txt Datei
     * @param daten
     * @throws IOException
     * @throws KundeGibtEsSchonException
     * @throws MitarbeiterGibtEsSchonException
     * @throws ArtikelIstSchonVorhanden
     */
    public eShopCUI(String daten)
            throws IOException, KundeGibtEsSchonException, MitarbeiterGibtEsSchonException, ArtikelIstSchonVorhanden {

        eS = new eShop(daten);

        // dient für die Eingabe in einem Konsolenfenster
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * dient als Startmenue, um eins der folgenden Optionen auszuwählen
     * je nach Eingabe wird die zugehörige Methode ausgeführt
     */
    public void allgemeinMenueAusgabe() {

        System.out.println();
        System.out.println("==== DER ESHOP ====");
        System.out.print("Die Optionen: \nAnmelden als Kunde: 'k'");
        System.out.print("              \nAnmelden als Mitarbeiter: 'm'");
        System.out.print("              \nRegistrieren als Kunde: 'r'");
        System.out.print("              \nVorgang abbrechen?: 'q'");
        System.out.println();
        // wird verwendet für eine Zwischenspeicherung 
        System.out.flush();

    }

    /**
     * Wenn man die Option anmelden als Mitarbeiter ausgewählt hat im Hauptmenue,
     * wird dann dieses Menue angezeigt
     * Es ist ein weiteres Menue das ausgeführt wird
     * je nach Eingabe
     */
    public void menueAusgabeMitarbeiter() {

        System.out.print("\nNeuen Mitarbeiter registrieren ?: 'mr'");
        System.out.print("\nNeuen Artikel zur Liste hinzufügen ?: 'a'");
        System.out.print("\nArtikelbestand eines Artikels verändern ?: 'j'");
        System.out.print("\nBestandsveränderungen zu Artikel anzeigen ?: 'b'");
        System.out.print("\nVorgang abbrechen?: q");
        System.out.println();
        // wird verwendet für eine Zwischenspeicherung 
        System.out.flush();
    }

    /**
     * Wenn man die Option anmelden als Kunde ausgewählt hat im Hauptmenue, wird
     * dann dieses Menue angezeigt
     * Es ist ein weiteres Menue das ausgeführt wird
     * je nach Eingabe
     */
    public void menueAusgabeKunde() {

        System.out.print("\nArtikel zum Warenkorb hinzufügen: 'ar'");
        System.out.println("\nKaufen: 'kf'");
        System.out.print("\nVorgang abbrechen?: q");
        System.out.println();
        // wird verwendet für eine Zwischenspeicherung 
        System.out.flush();
    }

    /**
     * Je nachdem was eingegben wurde, wird durch eine Switch eines der folgenden
     * Optionen ausgeführt
     * als Kunde registrieren, als Kunde anmelden oder als Mitarbeiter anmelden
     * sollte es zur fehlschlägen kommen, dann werden Fehlermeldungen ausgegeben
     * @param eingabeLesen
     * @throws IOException
     * @throws KundeGibtEsSchonException
     * @throws FalscheAnmeldeDatenException
     * @throws MitarbeiterGibtEsSchonException
     * @throws ArtikelIstSchonVorhanden
     * @throws NegativBestandException
     * @throws ArtikelNichtGefundenException
     */
    public void eingabenVerarbeitungFuerKundeUndMitarbeiter(String eingabeLesen) throws IOException, KundeGibtEsSchonException, FalscheAnmeldeDatenException,
            MitarbeiterGibtEsSchonException, ArtikelIstSchonVorhanden, NegativBestandException, ArtikelNichtGefundenException {

        String benutzer;
        String name;
        String adresse;
        String passwort;

        switch (eingabeLesen) {

            // dient für das registrieren als Kunde
            case "r":
                System.out.println("Geben sie ihren Namen ein:");
                name = br.readLine();
                System.out.println("Geben sie jetzt ihren Benutzernamen ein:");
                benutzer = br.readLine();
                System.out.println("Vergeben sie ein Passwort für ihren Benutzernamen:");
                passwort = br.readLine();
                System.out.println("Geben sie ihre Adresse ein:");
                adresse = br.readLine();

                /**
                 * er versucht sich mit den eingegebenen werten ein Kundenobjekt zu erzeugen,
                 * dann wird es in einer Datei rein geschrieben,
                 * bei diversen fehlern wird eine Exception ausgegeben
                 */ 
                try {
                    kundenNummer = eS.generateID('k');
                    eS.registrieren(name, benutzer, kundenNummer, adresse, passwort);
                    System.out.println("Das Registrieren war erfolgreich!");
                    eS.schreibeKunden();
                    System.out.println("und wurde automatisch gesichert");
                } catch (KundeGibtEsSchonException k) {
                    System.err.println(" Fehler bei der Registrierung: " + k.getMessage());
                }
                break;

            // dient für das anmelden als Mitarbeiter
            case "m":
                System.out.println("Geben sie ihren Benutzernamen ein:");
                benutzer = br.readLine();

                System.out.println("Jetzt geben sie ihr Passwort ein:");
                passwort = br.readLine();

                /**
                 * er versucht sich mit vorhandenen Mitarbeiterdaten anzumelden,
                 * bei diversen fehlern wird eine Exception ausgegeben
                 */
                try {
                    eS.anmeldenAlsMitarbeiter(benutzer, passwort);
                    System.out.println("Anmeldung ist erfolgreich gewesen!");
                    ausfuehrenMitarbeiter();
                } catch (FalscheAnmeldeDatenException f) {
                    System.err.println("Anmeldung ist fehlgeschlagen: " + f.getMessage());
                }
                break;

            // dient für das anmelden als Kunde
            case "k":
                System.out.println("Geben sie ihren Benutzernamen ein:");
                benutzer = br.readLine();

                System.out.println("Jetzt geben sie ihr Passwort ein:");
                passwort = br.readLine();

                /**
                 * er versucht sich mit vorhandenen Kundendaten anzumelden,
                 * bei diversen fehlern wird eine Exception ausgegeben
                 */
                try {
                    eS.anmeldenAlsKunde(benutzer, passwort);
                    System.out.println("Anmeldung ist erfolgreich gewesen!");
                    ausfuehrenKunde();
                } catch (FalscheAnmeldeDatenException f) {
                    System.err.println("Anmeldung ist fehlgeschlagen: " + f.getMessage());
                }
                break;
        }
    }

    /**
     * dient für das registrieren neuer Mitarbeiter, hinzufügen neuer Artikel,
     * das ändern eines Artikelbestandes und das ausgeben der Bestandshistorie der Artikel
     * gibt bei falsch Eingaben Fehlermeldungen aus
     * @param eingabeLesen
     * @throws IOException
     * @throws MitarbeiterGibtEsSchonException
     * @throws ArtikelIstSchonVorhanden
     * @throws NegativBestandException
     * @throws ArtikelNichtGefundenException
     */
    public void eingabenVerarbeitungAlsMitarbeiter(String eingabeLesen) throws IOException, MitarbeiterGibtEsSchonException,
            ArtikelIstSchonVorhanden, NegativBestandException, ArtikelNichtGefundenException {

        String benutzer;
        String name;
        String passwort;

        String artikelBezeichnung;
        int bestand;
        int jahrestag;

        switch (eingabeLesen) {

            /**
             * dient für das registrieren neuer Mitarbeiter, wenn man als Mitarbeiter
             * angemeldet ist
             */
            case "mr":
                System.out.println("Geben sie den Namen ein:");
                name = br.readLine();
                System.out.println("Geben sie jetzt ihren Benutzernamen ein:");
                benutzer = br.readLine();
                System.out.println("Vergeben sie ein Passwort für ihren Benutzernamen:");
                passwort = br.readLine();

                /**
                 * er versucht sich mit den eingegebenen werten ein Mitarbeiterobjekt zu erzeugen, 
                 * dann wird es in einer Datei rein geschrieben,
                 * bei diversen fehlern wird eine Exception ausgegeben
                 */
                try {
                    mitarNummer = eS.generateID('m');
                    eS.registrierenMitar(name, benutzer, mitarNummer, passwort);
                    System.out.println("Registrierung war erfolgreich!");
                    eS.schreibeMitarbeiter();
                    System.out.println("und wurde automatisch gesichert");
                } catch (MitarbeiterGibtEsSchonException mg) {
                    System.err.println("Registrierung fehlgeschlagen " + mg.getMessage());
                }
                break;

                /**
                 * dient für das Hinzufügen neuer Artikel, 
                 * wenn man als Mitarbeiter angemeldet ist
                 */
            case "a":
                System.out.println("Geben sie die Artikelbezeichnung ein, die sie hinzufügen möchten:");
                artikelBezeichnung = br.readLine();
                System.out.println("Geben sie die Anzahl des Bestandes ein:");
                String be = br.readLine();
                bestand = Integer.parseInt(be);
                System.out.println("Vergeben sie ein Preis für den Artikel:");
                String pr = br.readLine();
                float preis = Float.parseFloat(pr);

                System.out.println("Geben Sie den heutigen Jahrestag ein:");
                jahrestag = liesGueltigenIntegerEin();
                jahrestag = jahrestagInsLimitSetzen(jahrestag);

                /**
                 * er versucht sich mit den eingegebenen werten ein Artikelobjekt zu erzeugen, 
                 * dann wird es in einer Datei rein geschrieben und als Warenereignis festgehalten,
                 * bei diversen fehlern wird eine Exception ausgegeben
                 */
                try {
                    artikelNr = eS.generateID('a');
                    eS.neueArtikelHinzufuegen(artikelNr, artikelBezeichnung, bestand, preis, jahrestag);
                    System.out.println("Artikel wurde erfolgreich hinzugefügt!");
                    eS.schreibeArtikel();
                    eS.schreibeWarenereignis();
                    System.out.println("und wurde automatisch gesichert");
                } catch (ArtikelIstSchonVorhanden ag) {
                    System.err.println(ag.getMessage());
                }
                break;

            // dient für das verändern eines Artikelbestandes
            case "j":

                System.out.println("Geben sie den Artikelnamen ein von dem der Bestand erhöht werden soll:");
                artikelBezeichnung = br.readLine();

                System.out.println("Um wie viel soll der Bestand erhöht werden?:");
                String best = br.readLine();
                bestand = Integer.parseInt(best);

                System.out.println("Geben Sie den heutigen Jahrestag ein (1-365):");
                jahrestag = liesGueltigenIntegerEin();
                jahrestag = jahrestagInsLimitSetzen(jahrestag);

                /**
                 * es wird versucht für ein vorhandenen Artikel den Bestand zu erhöhen,
                 * dann wird es in einer Datei geschrieben und als Warenereignis festgehalten,
                 * bei diversen fehlern wird eine Exception ausgegeben
                 */
                try {
                    int neuerBestand = eS.artikelBestandErhoehen(artikelBezeichnung, bestand, jahrestag);
                    System.out.println("Der Bestand des Artikels wurde verändert! Er beträgt nun " + neuerBestand);
                    eS.schreibeArtikelbestandErhoehen();
                    eS.schreibeWarenereignis();
                    System.out.println("und wurde automatisch gesichert");
                } catch (ArtikelNichtGefundenException ag) {
                    System.err.println(ag.getMessage());
                }
                break;

            // dient für das anzeigen der Bestandshistorie der Artikel
            case "b":

                System.out.println(
                        "Geben sie den Artikelnamen ein von dem sie die Bestandshistorie der letzten 30 Tage anzeigen lassen wollen:");
                artikelBezeichnung = br.readLine();

                System.out
                        .println("Geben Sie den (heutigen) Jahrestag ein (1-365) von dem sie aus zurückgehen wollen:");

                int date = liesGueltigenIntegerEin();

                /**
                 * es wird eine Variable Zeitraum als Array Integer deklariert und initialisiert, der das Datum des Zeitraumes beinhaltet 
                 * es wird eine Variable Bestände als Array Integer deklariert und initialisiert, der die Bestände der Artikel beinhaltet
                 */
                int[] zeitraum = eS.zeitraumAlsArray(date);
                int[] bestaende = eS.getBestandshistorie(artikelBezeichnung, zeitraum);

                ausgabeBestandshistorie(zeitraum, bestaende);

                break;

        }
    }

    // dient für kaufen der Artikel, wenn man als Kunde angemeldet ist
    public void eingabenVerarbeitungAlsKunde(String eingabeLesen) throws IOException, ArtikelNichtGefundenException {

        String artikelBezeichnung;
        String zahl;
        int anzahl;
        String eing;

        switch (eingabeLesen) {

            case "ar":

                /**
                 * es wird versucht ein Kaufprozess als Kunde durchzuführen,
                 * die Artikel werden vom Kunden ausgesucht und ihn ein Warekorb hinzugefügt,
                 * es wird hier mit einer Artikelliste und Warenkorbliste gearbeitet und
                 * bei diversen fehlern wird eine Exception ausgegeben
                 */
                try {
                    do {
                        System.out.println("Welchen Artikel wollen sie kaufen ?:");
                        artikelBezeichnung = br.readLine();
                        eS.sucheArt(artikelBezeichnung);
                        System.out.println("Wie viele wollen sie davon kaufen ?:");
                        zahl = br.readLine();
                        anzahl = Integer.parseInt(zahl);
                        System.out.println("Wollen sie weitere Artikel zum kaufen hinzufügen ?:");
                        eing = br.readLine();
                        eS.artikelZumWarenkorbHinzufuegen(artikelBezeichnung, anzahl);
                    } while (!eing.equals("n"));
                    kaufFrage();
                } catch (ArtikelNichtGefundenException a) {
                    System.err.println("Der Fehler:" + a.getMessage());
                }
                break;
            case "kf":
                kaufFrage();
                break;
        }
    }

    /**
     * gilt für das Hauptmenue
     * je nach Eingabe wird die Methode eingabenVerarbeitungFuerKundeUndMitarbeiter ausgeführt, bei falsch
     * eingaben wird ein Fehler ausgegeben
     * @throws IOException
     * @throws KundeGibtEsSchonException
     * @throws FalscheAnmeldeDatenException
     * @throws MitarbeiterGibtEsSchonException
     * @throws ArtikelIstSchonVorhanden
     * @throws NegativBestandException
     * @throws ArtikelNichtGefundenException
     */
    public void ausfuehrenKundeMitarbeiter() throws IOException, KundeGibtEsSchonException, FalscheAnmeldeDatenException, MitarbeiterGibtEsSchonException,
            ArtikelIstSchonVorhanden, NegativBestandException, ArtikelNichtGefundenException {

        String eingabe = "";

        // solange kein q eingegben wird, läuft der Prozess
        do {
            allgemeinMenueAusgabe();
            try {
                eingabe = br.readLine();
                eingabenVerarbeitungFuerKundeUndMitarbeiter(eingabe);
            } catch (IOException e) {
                System.err.println("Der Fehler: " + e.getMessage());
            }
        } while (!eingabe.equals("q"));
    }

    /**
     * gilt nur wenn man als Mitarbeiter angemeldet ist
     * je nach Eingabe wird die Methode eingabenVerarbeitungAlsMitarbeiter ausgeführt, bei falsch
     * eingaben wird ein Fehler ausgegeben
     * @throws MitarbeiterGibtEsSchonException
     * @throws ArtikelIstSchonVorhanden
     * @throws NegativBestandException
     * @throws ArtikelNichtGefundenException
     */
    public void ausfuehrenMitarbeiter() throws MitarbeiterGibtEsSchonException, ArtikelIstSchonVorhanden, NegativBestandException,
            ArtikelNichtGefundenException {

        String eingabe = "";

        // solange kein q eingegben wird, läuft der Prozess
        do {
            menueAusgabeMitarbeiter();
            try {
                eingabe = br.readLine();
                eingabenVerarbeitungAlsMitarbeiter(eingabe);
            } catch (IOException e) {
                System.err.println("Der Fehler: " + e.getMessage());
            }
        } while (!eingabe.equals("q"));
    }

    /**
     * gilt nur wenn man als Kunde angemeldet ist
     * je nach Eingabe wird die Methode eingabenVerarbeitungAlsKunde ausgeführt, bei falsch
     * eingaben wird ein Fehler ausgegeben
     * @throws ArtikelNichtGefundenException
     */
    public void ausfuehrenKunde() throws ArtikelNichtGefundenException {

        String eingabe = "";

        // solange kein q eingegben wird, läuft der Prozess
        do {
            menueAusgabeKunde();
            try {
                eingabe = br.readLine();
                eingabenVerarbeitungAlsKunde(eingabe);
            } catch (IOException e) {
                System.err.println(" Der Fehler: " + e.getMessage());
            }
        } while (!eingabe.equals("q"));
    }

    /**
     * Diese Methode beschäftigt sich damit das bei bestimmten eingaben die ein Integer Wert erfordern
     * auch ein Integer Wert eingelesen wird, ansonsten wird eine Fehlermeldung ausgegeben
     * @return
     */
    public int liesGueltigenIntegerEin() {
        int number = 0;
        boolean valid = false;

        while (!valid) {
            try {
                // System.out.print("Bitte geben Sie eine ganze Zahl ein: ");
                String input = br.readLine();
                number = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            } catch (IOException e) {
                System.out.println("Fehler beim Lesen der Eingabe. Bitte versuchen Sie es erneut.");
            }
        }

        return number;
    }

    /**
     * die Methode dient für die Ausgabe des Zeitraum Arrays und des änderungs Array,
     * solange sich Elemente im änderungs Array befinden,
     * wird die Bestandshistorie der vorhandenen Elemente ausgegeben
     * @param zeitraum
     * @param aenderungen
     */
    public void ausgabeBestandshistorie(int[] zeitraum, int[] aenderungen) {
        if (aenderungen != null) {
            for (int i = 0; i < zeitraum.length; i++) {
                System.out.println("Bestand am Tag " + zeitraum[i] + ": " + aenderungen[i]);
            }
        }
    }
    /**
     * Wenn ein Kunde angemeldet ist, kann er alle Artikel in seinem Warenkorb kaufen.
     * Für einen Kaufvorgang wird der heutige Jahrestag als Eingabe benötigt.
     * Es wird eine Rechnung erzeugt und ausgeben. Anschließend wird der Warenkorb geleert.
     * @throws IOException
     */
    public void kaufFrage() throws IOException {
        String eingabe = "";

        System.out.println("Wollen sie jetzt alle Artikel im Warenkorb kaufen? ('y' zum Bestätigen)");
        eingabe = br.readLine();
        if (eingabe.equals("y")) {
            try {
                System.out.println("Welcher Jahrestag ist heute?");
                int jahrestag = liesGueltigenIntegerEin();
                jahrestag = jahrestagInsLimitSetzen(jahrestag);
                String rechnungstext = eS.kaufen(jahrestag);
                eS.schreibeWarenereignis();
                eS.schreibeArtikel();
                System.out.println(rechnungstext);
            } catch (NegativBestandException e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.err.println("Der Kaufvorgang wurde abgebrochen");
            }
        }
    }

    /**
     * Diese Methode dient dafür das es ein Limit beim Jahrestag gibt
     * es soll verhindert werden das mit werten ausserhalb der Jahrestage gearbeitet wird
     * @param jahrestag
     * @return
     */
    public int jahrestagInsLimitSetzen(int jahrestag) {
        if (jahrestag > 365) {
            jahrestag = 365;
        } else if (jahrestag < 1) {
            jahrestag = 1;
        }
        return jahrestag;
    }

}
