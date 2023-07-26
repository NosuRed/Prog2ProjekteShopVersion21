package KernklassenInterface;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

import Exception.ArtikelIstSchonVorhanden;
import Exception.ArtikelNichtGefundenException;
import Exception.FalscheAnmeldeDatenException;
import Exception.KundeGibtEsSchonException;
import Exception.MitarbeiterGibtEsSchonException;
import Exception.NegativBestandException;

public interface eShopInterface extends Remote {

    /**
     * gibt die Artikelliste zurück
     * @return
     */
    Vector<ArtikelInterface> gibAlleArtikel() throws RemoteException;

    /**
     * gibt die Warenereignisliste zurück
     * @return
     */
    Vector<WarenereignisInterface> gibWarenEreignis() throws RemoteException;

    /**
     * gibt die Warenkorbliste zurück
     * @return
     * @throws RemoteException
     */
    List<ArtikelInterface> gibWarenkorb() throws RemoteException;

    /**
     * leert den Warenkorb komplett
     */
    void leereDenWarenkorb() throws RemoteException;

    /**
     * Diese Methode ist für das anmelden eines Kunden verantwortlich,
     * sie gibt ein true zurück wenn der Benutzername und Passwort in der
     * Kundenliste vorhanden ist und der Benutzername zum Passwort gehört
     * andernfalls wird ein Fehler zurück gegeben
     * dem Warenkorb wird der Warenkorb des Kunden zugewiesen
     * @param benutzer
     * @param password
     * @throws IOException
     * @throws FalscheAnmeldeDatenException
     */
    void anmeldenAlsKunde(String benutzer, String password) throws IOException, FalscheAnmeldeDatenException;

    /**
     * Diese Methode ist für das anmelden eines Mitarbeiter verantwortlich,
     * Der Prozess ist nur dann erfolgreich wenn der Benutzername und Passwort in der
     * Mitarbeiterliste vorhanden ist und das Passwort zum Benutzernamen auch gehört 
     * andernfalls wird ein Fehler zurück gegeben
     * @param benutzer
     * @param password
     * @throws IOException
     * @throws FalscheAnmeldeDatenException
     */
    void anmeldenAlsMitarbeiter(String benutzer, String password) throws IOException, FalscheAnmeldeDatenException;

    /**
     * Der Prozess ist dann erfolgreich wenn das Registrieren erfolgreich war
     * und der Kunde nicht schon vorhanden ist in der Liste andernfalls gibt es ein
     * Fehler zurück
     * @param name
     * @param benutzer
     * @param nummer
     * @param adresse
     * @param password
     * @throws IOException
     * @throws KundeGibtEsSchonException
     */
    void registrieren(String name, String benutzer, int nummer, String adresse, String password)
            throws IOException, KundeGibtEsSchonException;

    /**
     * Der Prozess ist dann erfolgreich wenn das registrieren eines neuen Mitarbeiters geklappt hat, 
     * aber nur wenn man als Mitarbeiter angemeldet ist
     * @param name
     * @param benutzer
     * @param nummer
     * @param passwort
     * @throws MitarbeiterGibtEsSchonException
     * @throws IOException
     */
    void registrierenMitar(String name, String benutzer, int nummer, String passwort)
            throws MitarbeiterGibtEsSchonException, IOException;

    /**
     * Der Prozess ist dann erfolgreich wenn das hinzufügen neuer Artikel geklappt hat, 
     * aber nur wenn man als Mitarbeiter angemeldet ist
     * Es wird auch ein Warenereignis erzeugt,
     * um es von dem Mitarbeiter festzuhalten
     * @param artNummer
     * @param bezeichnung
     * @param bestand
     * @param preis
     * @param jahrestag
     * @throws IOException
     * @throws ArtikelIstSchonVorhanden
     * @throws ArtikelNichtGefundenException
     */
    void neueArtikelHinzufuegen(int artNummer, String bezeichnung, int bestand, float preis, int jahrestag)
            throws IOException, ArtikelIstSchonVorhanden, ArtikelNichtGefundenException;

    /**
     * Der Prozess ist dann erfolgreich wenn das hinzufügen neuer Massengutartikel geklappt hat, 
     * aber nur wenn man als Mitarbeiter angemeldet ist
     * Es wird auch ein Warenereignis erzeugt,
     * um es von dem Mitarbeiter festzuhalten
     * @param artNummer
     * @param bezeichnung
     * @param bestand
     * @param preis
     * @param jahrestag
     * @param packSize
     * @throws IOException
     * @throws ArtikelIstSchonVorhanden
     * @throws ArtikelNichtGefundenException
     */
    void neueMassenArtikelHinzufuegen(int artNummer, String bezeichnung, int bestand, float preis, int jahrestag,
            int packSize)
            throws IOException, ArtikelIstSchonVorhanden, ArtikelNichtGefundenException;

    /**
     * dient für das erhöhen eines Artikelbestandes, aber nur wenn man als
     * Mitarbeiter angemeldet ist 
     * wenn der gesuchte Artikel gefunden wurde, wird versucht den Bestand des Artikels zu erhöhen,
     * bei fehlern werden Fehlermeldungen ausgeworfen  
     * @param artikelBezeichnung
     * @param bestand
     * @param jahrestag
     * @return
     * @throws ArtikelNichtGefundenException
     * @throws NegativBestandException
     * @throws IOException
     */
    int artikelBestandErhoehen(String artikelBezeichnung, int bestand, int jahrestag)
            throws ArtikelNichtGefundenException, NegativBestandException, IOException;

    /**
     * Generiert für den jeweiligen Verwaltungstypen die nächste freie ID.
     * @param variant
     * @return Die nächste freie ID oder bei nicht vorhandener Variante -1
     */
    int generateID(char variant) throws RemoteException;

    /**
     * dient für das suchen eines Artikels und delegiert es weiter an die Artikelverwaltung
     * @param bezeichnung
     * @throws ArtikelNichtGefundenException
     * @throws IOException
     */
    void sucheArt(String bezeichnung) throws ArtikelNichtGefundenException, IOException;

    /**
     * Sucht über die Artikelverwaltung den passenden Artikel zur gegebenen
     * Bezeichnung raus
     * und besorgt dann die Bestandshistorie zu diesem
     * 
     * @param artName
     * @return Map(Integer, Integer) / null
     * @throws IOException
     * @throws ArtikelNichtGefundenException
     */
    int[] getBestandshistorie(String artName, int[] zeitraum) throws IOException, ArtikelNichtGefundenException;

    /**
     * dient für den Zeitraum der Warenereignisse, 
     * und gibt den Zeitraum des Jahrestages zurück
     * @param jahrestag
     * @return
     */
    int[] zeitraumAlsArray(int jahrestag) throws RemoteException;

    /**
     * dient für das Artikel hinzufügen zum Warenkorb, sofern der Artikel vorhanden sein sollte 
     * und die Anzahl wird ebenfalls hinzugefügt
     * @param artBez
     * @param anzahl
     * @throws IOException
     * @throws ArtikelNichtGefundenException
     */
    void artikelZumWarenkorbHinzufuegen(String artBez, int anzahl) throws IOException, ArtikelNichtGefundenException;

    /**
     * Nur Kunden können einen Kauf tätigen. Jeder Artikel in ihrem Warenkorb wird
     * in seinem Bestand verringert.
     * Der erfolgreiche Kauf eines jeden Artikel erzeugt ein Wareneregnis, in
     * welchem der Abgang des Artikels geloggt ist.
     * Benötigt den heutigen Jahrestag als Eingabe.
     * 
     * @param jahrestag
     * @return erzeugte Rechnung.toString()
     * 
     * @throws NegativBestandException
     * @throws IOException
     */
    String kaufen(int jahrestag) throws NegativBestandException, IOException;

    // dient für das sichern eines Kundenobjektes in einer .txt Datei
    void schreibeKunden() throws IOException;

    // dient für das sichern eines Mitarbeiterobjektes in einer .txt Datei
    void schreibeMitarbeiter() throws IOException;

    // dient für das sichern eines Artikelobjektes in einer .txt Datei
    void schreibeArtikel() throws IOException;

    // dient für das sichern eines Artikelbestandes in einer .txt Datei
    void schreibeArtikelbestandErhoehen() throws IOException;

    // dient für das sichern eines Warenergebnisobjekts in einer .txt Datei
    void schreibeWarenereignis() throws IOException;

}