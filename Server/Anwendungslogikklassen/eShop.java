/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Anwendungslogikklassen;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import KernklassenInterface.ArtikelInterface;
import KernklassenInterface.KundeInterface;
import KernklassenInterface.MassengutArtikelInterface;
import KernklassenInterface.MitarbeiterInterface;
import KernklassenInterface.RechnungInterface;
import KernklassenInterface.WarenereignisInterface;
import KernklassenInterface.WarenkorbInterface;
import KernklassenInterface.eShopInterface;
import Exception.ArtikelIstSchonVorhanden;
import Exception.ArtikelNichtGefundenException;
import Exception.FalscheAnmeldeDatenException;
import Exception.KundeGibtEsSchonException;
import Exception.MitarbeiterGibtEsSchonException;
import Exception.NegativBestandException;

import KernKlassen.Kunde;
import KernKlassen.Mitarbeiter;
import KernKlassen.Warenereignis;
import KernKlassen.Artikel;
import KernKlassen.Rechnung;
import KernKlassen.MassengutArtikel;

/**
 * Die Klasse eShop arbeitet mit den Verwaltungsklassen zusammen, 
 * und leitet jegliche Methodenaufrufe weiter an die Verwaltungsklassen
 */
public class eShop extends UnicastRemoteObject implements eShopInterface {

    // Eigenschaften der Klasse eShop die deklariert werden als private
    private String datenSatz;

    private KundenVerw kundVerw;
    private KundeInterface kund;

    private MitarbeiterVerw mitarVerw;
    private MitarbeiterInterface mitar;

    private ArtikelVerw artVerw;

    private WarenereignisVerw warenereignisVerw;

    private WarenkorbInterface warenKorb;

    /**
     * Konstruktor der eShop Klasse in denen neue Kunden,Artikel und
     * Mitarbeiterverwaltungsobjekte erzeugt werden, 
     * sowie deren Datei zum einlesen benötigt werden 
     * @param datenSatz
     * @throws IOException
     * @throws KundeGibtEsSchonException
     * @throws MitarbeiterGibtEsSchonException
     * @throws ArtikelIstSchonVorhanden
     */
    public eShop(String datenSatz)
            throws IOException, KundeGibtEsSchonException, MitarbeiterGibtEsSchonException, ArtikelIstSchonVorhanden, RemoteException {
        super();
       

        this.datenSatz = "./Server/" + datenSatz;

        kundVerw = new KundenVerw();
        // Kundendetails aus einer Datei einlesen
        kundVerw.datenEinlesen(this.datenSatz + "-k.txt");

        mitarVerw = new MitarbeiterVerw();
        
        // Mitarbeiterobjekt erzeugen und in die Mitarbeiterverwaltungsliste einfügen
        MitarbeiterInterface mitar1 = new Mitarbeiter("Bill", "bill3", 1,"p");
        mitarVerw.mitarbeiterEinfuegen(mitar1);

        // Mitarbeiterdetails aus einer Datei einlesen
        mitarVerw.datenEinlesen(this.datenSatz + "-m.txt");

        artVerw = new ArtikelVerw();
        // Artikeldetails aus einer Datei einlesen
        artVerw.datenEinlesen(this.datenSatz + "-a.txt");

        warenereignisVerw = new WarenereignisVerw();
        // Warenereignisdetails aus einer Datei einlesen
        warenereignisVerw.datenEinlesen(this.datenSatz + "-w.txt");

    }

    /**
     * gibt die Artikelliste zurück
     * @return
     */
    @Override
    public Vector<ArtikelInterface> gibAlleArtikel() throws RemoteException{
        return artVerw.getArtikelListe();
    }

    /**
     * gibt die Warenereignisliste zurück
     * @return
     */
    @Override
    public Vector<WarenereignisInterface> gibWarenEreignis() throws RemoteException{
        return warenereignisVerw.getWarenereignisListe();
    }

    /**
     * gibt die Warenkorbliste zurück
     * @return
     * @throws RemoteException
     */
    @Override
    public List<ArtikelInterface> gibWarenkorb() throws RemoteException{
        return warenKorb.getKaufendeArt();
    }

    /**
     * leert den Warenkorb komplett
     */
    @Override
    public void leereDenWarenkorb() throws RemoteException{
        warenKorb.leeren();
    }

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
    @Override
    public void anmeldenAlsKunde(String benutzer, String password) throws IOException, FalscheAnmeldeDatenException {

        Vector<KundeInterface> gefundeneKunden = kundVerw.kundenSuchen(benutzer);
        if (!gefundeneKunden.isEmpty()) {
            kund = gefundeneKunden.firstElement();
            warenKorb = kund.getWk();
            if (kund.getPasswd().equals(password)) {

            } else {
                throw new FalscheAnmeldeDatenException();
            }
        } else {
            throw new FalscheAnmeldeDatenException();
        }

    }

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
    @Override
    public void anmeldenAlsMitarbeiter(String benutzer, String password) throws IOException, FalscheAnmeldeDatenException {

        Vector<Mitarbeiter> gefundeneMitarbeiter = mitarVerw.mitarbeiterSuchen(benutzer);
        if (!gefundeneMitarbeiter.isEmpty()) {
            mitar = gefundeneMitarbeiter.firstElement();
            if (mitar.getPasswd().equals(password)) {

            } else {
                throw new FalscheAnmeldeDatenException();
            }
        } else {
            throw new FalscheAnmeldeDatenException();
        }

    }

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
    @Override
    public void registrieren(String name, String benutzer, int nummer, String adresse, String password)
            throws IOException, KundeGibtEsSchonException {

        KundeInterface zuRegistrierenderKunde = new Kunde(name, benutzer, nummer, adresse, password);

        kundVerw.kundenEinfuegen(zuRegistrierenderKunde);

    }

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
    @Override
    public void registrierenMitar(String name, String benutzer, int nummer, String passwort)
            throws MitarbeiterGibtEsSchonException, IOException {

        Mitarbeiter zuRegistrierenderMitarbeiter = new Mitarbeiter(name, benutzer, nummer, passwort);

        mitarVerw.mitarbeiterEinfuegen(zuRegistrierenderMitarbeiter);
    }

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
    @Override
    public void neueArtikelHinzufuegen(int artNummer, String bezeichnung, int bestand, float preis, int jahrestag)
            throws IOException, ArtikelIstSchonVorhanden, ArtikelNichtGefundenException {

        ArtikelInterface a = new Artikel(artNummer, bezeichnung, bestand, preis);

        WarenereignisInterface warenereignis = new Warenereignis(this.mitar.getNummer(), 'm', artNummer, bestand, jahrestag);
        warenereignisVerw.warenereignisEinfuegen(warenereignis);

        artVerw.artEinfuegen(a);

    }

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
    @Override
    public void neueMassenArtikelHinzufuegen(int artNummer, String bezeichnung, int bestand, float preis, int jahrestag, int packSize)
            throws IOException, ArtikelIstSchonVorhanden, ArtikelNichtGefundenException {

        MassengutArtikelInterface a = new MassengutArtikel(artNummer, bezeichnung, bestand, preis, packSize);

        WarenereignisInterface warenereignis = new Warenereignis(this.mitar.getNummer(), 'm', artNummer, bestand, jahrestag);
        warenereignisVerw.warenereignisEinfuegen(warenereignis);

        artVerw.artEinfuegen(a);

    }

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
    @Override
    public int artikelBestandErhoehen(String artikelBezeichnung, int bestand, int jahrestag)
            throws ArtikelNichtGefundenException, NegativBestandException, IOException {

        Vector<ArtikelInterface> gefundeneArtikel = artVerw.artikelSuchen(artikelBezeichnung);
        if (!gefundeneArtikel.isEmpty()) {
            ArtikelInterface a = gefundeneArtikel.firstElement();
            int neuerBestand = a.getBestand() + Math.abs(bestand);
            a.setBestand(neuerBestand);
            WarenereignisInterface warenereignis = new Warenereignis(mitar.getNummer(), 'm', a.getNummer(), Math.abs(bestand),
                    jahrestag);
            warenereignisVerw.warenereignisEinfuegen(warenereignis);
            return a.getBestand();
        } else {
            throw new ArtikelNichtGefundenException();
        }

    }

    /**
     * Generiert für den jeweiligen Verwaltungstypen die nächste freie ID.
     * @param variant
     * @return Die nächste freie ID oder bei nicht vorhandener Variante -1
     */
    @Override
    public int generateID(char variant) throws RemoteException {
        switch (variant) {
            case 'k':
                return kundVerw.generateID();
            case 'm':
                return mitarVerw.generateID();
            case 'a':
                return artVerw.generateID();
        }
        return -1;
    }

    /**
     * dient für das suchen eines Artikels und delegiert es weiter an die Artikelverwaltung
     * @param bezeichnung
     * @throws ArtikelNichtGefundenException
     * @throws IOException
     */
    @Override
    public void sucheArt(String bezeichnung) throws ArtikelNichtGefundenException, IOException {

        if (bezeichnung != null) {
            
                if(!artVerw.artikelSuchen(bezeichnung).isEmpty()){

                }else{
                    throw new ArtikelNichtGefundenException();
                }
        } else {
            throw new ArtikelNichtGefundenException();
        }
    }

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
    @Override
    public int[] getBestandshistorie(String artName, int[] zeitraum) throws IOException, ArtikelNichtGefundenException {
        Vector<ArtikelInterface> artikel = artVerw.artikelSuchen(artName);
        if (artikel.isEmpty()) {
            throw new ArtikelNichtGefundenException();
        } else {
            int[] bestandshistorie = warenereignisVerw.getBestandshistorie(artikel.firstElement(), zeitraum);
            return bestandshistorie;
        }
    }

    /**
     * dient für den Zeitraum der Warenereignisse, 
     * und gibt den Zeitraum des Jahrestages zurück
     * @param jahrestag
     * @return
     */
    @Override
    public int[] zeitraumAlsArray(int jahrestag) throws RemoteException {
        return warenereignisVerw.zeitraumAlsArray(jahrestag);
    }

    /**
     * dient für das Artikel hinzufügen zum Warenkorb, sofern der Artikel vorhanden sein sollte 
     * und die Anzahl wird ebenfalls hinzugefügt
     * @param artBez
     * @param anzahl
     * @throws IOException
     * @throws ArtikelNichtGefundenException
     */
    @Override
    public void artikelZumWarenkorbHinzufuegen(String artBez, int anzahl) throws IOException, ArtikelNichtGefundenException{
        ArtikelInterface art = artVerw.artikelSuchen(artBez).firstElement();
        if (art != null) {
            kund.getWk().hinzufuegenArtikel(art, anzahl);
        }
    }

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
    @Override
    public String kaufen(int jahrestag) throws NegativBestandException, IOException {
        WarenkorbInterface wk = kund.getWk();
        Map<ArtikelInterface, Integer> waren = wk.getZuKaufendeArt();
        for (ArtikelInterface artikel : waren.keySet()) {
            int anzahlVomZuKaufenden = waren.get(artikel);
            int jetzigerBestand = artikel.getBestand();
            int neueStueckzahl = jetzigerBestand - anzahlVomZuKaufenden;
            artVerw.artikelBestandVeraendern(artikel, neueStueckzahl);
            WarenereignisInterface warenereignis = new Warenereignis(kund.getNummer(), 'k', artikel.getNummer(),
                    -(anzahlVomZuKaufenden), jahrestag);
            warenereignisVerw.warenereignisEinfuegen(warenereignis);
        }
        RechnungInterface rechnung = new Rechnung(kund, jahrestag);
        String rechnungsText = rechnung.toString();
        wk.leeren();
        return rechnungsText;
    }

    // dient für das sichern eines Kundenobjektes in einer .txt Datei
    @Override
    public void schreibeKunden() throws IOException {
        kundVerw.datenSchreiben(this.datenSatz + "-k.txt");
    }

    // dient für das sichern eines Mitarbeiterobjektes in einer .txt Datei
    @Override
    public void schreibeMitarbeiter() throws IOException {
        mitarVerw.datenSchreiben(this.datenSatz + "-m.txt");
    }

    // dient für das sichern eines Artikelobjektes in einer .txt Datei
    @Override
    public void schreibeArtikel() throws IOException {
        artVerw.datenSchreiben(this.datenSatz + "-a.txt");
    }

    // dient für das sichern eines Artikelbestandes in einer .txt Datei
    @Override
    public void schreibeArtikelbestandErhoehen() throws IOException {
        artVerw.datenSchreiben(this.datenSatz + "-a.txt");
    }

    // dient für das sichern eines Warenergebnisobjekts in einer .txt Datei
    @Override
    public void schreibeWarenereignis() throws IOException {
        warenereignisVerw.datenSchreiben(this.datenSatz + "-w.txt");
    }

}
