/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import KernklassenInterface.MassengutArtikelInterface;
import KernklassenInterface.WarenereignisInterface;
import KernKlassen.*;

/**
 * Die Klasse Persistence Daten implementiert die Persistencen Schnittstelle
 * Die Klasse dient für das speichern und laden der Artikel, Kunden, Mitarbeiter und Warenereignisse
 */
public class PersistenceDaten implements PersistenceSchnittstelle {

    // Das sind die Eigenschaften dieser Klasse die deklariert und initialisiert werden
    private BufferedReader bufferedReader = null;
    private PrintWriter printWriter = null;
    private static final int defaultAnzahl = 1;

    /**
     * Hier wird das öffnen des Lesevorgangs Methode aus der Persistencen Schnittstellen
     * Klasse implementiert
     */
    @Override
    public void openForReading(String daten) throws IOException {

        // der BufferedReader wird hier erzeugt zum einlesen der Eingaben
        bufferedReader = new BufferedReader(new FileReader(daten));
    }

    /**
     * Hier wird das öffnen des Schreibevorgangs Methode aus der Persistencen Schnittstellen 
     * Klasse implementiert
     */
    @Override
    public void openForWriting(String daten) throws IOException {

        // es wird ein PrintWriter erzeugt zum rein schreiben in einer Datei
        printWriter = new PrintWriter(new BufferedWriter(new FileWriter(daten)));
    }

    /**
     * Hier wird die schließen Methode aus der Persistencen Schnittstelle
     * Klasse implementiert
     * dient für das schließen des reading und writing teil
     */
    @Override
    public boolean close() {

        if (printWriter != null) {
            printWriter.close();
        }

        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.getMessage();

                return false;
            }
        }

        return true;
    }

    /**
     * Mit dieser Methode kann die Eingabe eingelesen werden
     * wenn der Reader nicht null ist gebe die eingelesene Eingabe zurück
     * ansonsten gebe "" zurück
     * @return
     * @throws IOException
     */
    @Override
    public String einlesen() throws IOException {
        if (bufferedReader != null) {
            return bufferedReader.readLine();
        } else {
            return "";
        }
    }

    /**
     * Mit dieser Methode werden die Eigenschaften des Artikels festgehalten
     * wenn der Writer nicht null ist dann schreibe in die Datei rein
     * ansonsten mache nichts
     * @param daten
     */
    @Override
    public void schreiben(String daten) {
        if (printWriter != null) {
            printWriter.println(daten);
        }
    }

    /**
     * Hier wird der Artikel mit seinen Eigenschaften geladen 
     * und geprüft ob dieser vorhanden ist
     */
    @Override
    public Artikel ladeArtikel() throws IOException {

        // Die String werte werden in Integer umgewandelt
        String artNummer = einlesen();
        //int nummer = Integer.parseInt(artNummer);

        String artBezeichnung = einlesen();
        if (artBezeichnung == null) {
            return null;
        }

        String artBestand = einlesen();
        int bestand = Integer.parseInt(artBestand);

        String artPreis = einlesen();
        float preis = Float.parseFloat(artPreis);

        String artAnzahl = einlesen();
        int anzahl = Integer.parseInt(artAnzahl);

        /**
         * Wenn die eingelesene Artikelanzahl größer als 1 ist 
         * dann wird ein Massengutartikel erzeugt mit den entsprechenden werten
         * die dem Parameter zugewiesen werden und wird dann zurück gegeben
         */
        if(anzahl > 1){
            return new MassengutArtikel(artNummer, artBezeichnung, bestand, preis, anzahl);
        }
        /**
         * Artikel mit folgenden werten(Artikelnummer, Bezeichnung, Bestand, Preis)
         * erzeugen und wird zurück gegeben
         */
        return new Artikel(artNummer, artBezeichnung, bestand, preis);

    }

    /**
     * Diese Methode dient für das speichern der Artikel in einer Datenbank 
     * hier Textdatei in der Reihenfolge
     * Artikelnummer, Artikelbezeichnung, Artikelbestand, Artikelpreis 
     * und Massengutanzahl
     */
    @Override
    public boolean speichereArtikel(Artikel art) throws IOException {

        schreiben(art.getNummer() + "");

        schreiben(art.getBezeichnung());

        schreiben(art.getBestand() + "");

        schreiben(art.getPreis() + "");

        /**
         * hier wird geprüft ob art eine Beziehung zum Massengutartikel hat
         * wenn ja : wird die Variable Anzahl einen Massengutwert kriegen 
         * und wird in die Datei rein geschrieben 
         * wenn nein : dann wird der vorhandene Standardwert genommen und in die Datei rein geschrieben
         */
        if (art instanceof MassengutArtikel){
            int anzahl = ((MassengutArtikelInterface) art).getAnzahl();
            schreiben(anzahl +"");
        }else{
            schreiben(defaultAnzahl +"");
        }


        return true;
    }

    /**
     * Diese Methode dient für das speichern der Mitarbeiter in einer Datenbank
     * hier Textdatei in der Reihenfolge
     * Mitarbeitername, Mitarbeiterbenutzername, Mitarbeiternummer, Mitarbeiterpasswort
     */
    @Override
    public boolean speichereMitarbeiter(Mitarbeiter mitar) throws IOException {

        schreiben(mitar.getName());

        schreiben(mitar.getBenutzerName());

        schreiben(mitar.getNummer() + "");

        schreiben(mitar.getPasswd());

        return true;
    }

    /**
     * Hier wird der Mitarbeiter mit seinen Eigenschaften geladen und geprüft ob
     * dieser vorhanden ist
     */
    @Override
    public Mitarbeiter ladeMitarbeiter() throws IOException {

        String name = einlesen();

        String benutzer = einlesen();
        if (benutzer == null) {
            return null;
        }

        // Die String werte werden in Integer umgewandelt
        String nummer = einlesen();
        int zahlenNummer = Integer.parseInt(nummer);

        String passwort = einlesen();

        /**
         * Mitarbeiter mit folgenden werten(Mitarbeitername, Benutzernamen, Nummer, Passwort) erzeugen
         * und wird zurück gegeben
         */
        return new Mitarbeiter(name, benutzer, zahlenNummer, passwort);

    }

    /**
     * Diese Methode dient für das speichern der Kunden in einer Datenbank
     * hier Textdatei in der Reihenfolge
     * Kundenname, Kundenbenutzername, Kundennummer, Kundenadresse und Kundenpasswort
     */
    @Override
    public boolean speichereKunden(Kunde kund) throws IOException {

        schreiben(kund.getName());

        schreiben(kund.getBenutzerName());

        schreiben(kund.getNummer() + "");

        schreiben(kund.getAdresse());

        schreiben(kund.getPasswd());

        return true;
    }

    /**
     * Hier wird der Kunde mit seinen Eigenschaften geladen und geprüft ob dieser
     * vorhanden ist
     */
    @Override
    public Kunde ladeKunde() throws IOException {

        String name = einlesen();

        String benutzerN = einlesen();
        if (benutzerN == null) {
            return null;
        }

        String nummer = einlesen();
        int kundenNummer = Integer.parseInt(nummer);

        String kundenAdr = einlesen();

        String kundenPasswort = einlesen();

        /**
         * Kunden mit folgenden werten(Kundenname, Benutzernamen, Nummer, Adresse, Passwort) erzeugen
         * und wird zurück gegeben
         */
        return new Kunde(name, benutzerN, kundenNummer, kundenAdr, kundenPasswort);
    }

    /**
     * Diese Methode dient für das speichern der Warenereignisse in einer Datenbank
     * hier Textdatei in der Reihenfolge
     * Nutzertyp, Artikelnummer, Stückzahl und Datum
     */
    @Override
    public boolean speichereWarenereignis(WarenereignisInterface warerg) throws IOException {
        schreiben(warerg.getVerantwortlicher() + "");

        schreiben(warerg.getNutzertyp() + "");

        schreiben(warerg.getBewegterArtikel() + "");

        schreiben(warerg.getStueckzahl() + "");

        schreiben(warerg.getDatum() + "");

        return true;

    }

    /**
     * Hier wird das Warenereignis mit seinen Eigenschaften geladen und geprüft ob dieser
     * vorhanden ist
     */
    @Override
    public Warenereignis ladeWarenereignis() throws IOException {

        String verantwortlicher = einlesen();

        if (verantwortlicher == null) {
            return null;
        }
        int verantwNr = Integer.parseInt(verantwortlicher);

        String nutzertyp = einlesen();
        char nutztyp = nutzertyp.charAt(0);

        // Die String werte werden in Integer umgewandelt
        String artNummer = einlesen();
        int nummer = Integer.parseInt(artNummer);

        String stueckzahl = einlesen();
        int stckzhl = Integer.parseInt(stueckzahl);

        String datum = einlesen();
        int dtm = Integer.parseInt(datum);

        // Warenereignis wurde erfolgreich gelesen und als Objekt weitergegeben.
        return new Warenereignis(verantwNr, nutztyp, nummer, stckzhl, dtm);
    }

}