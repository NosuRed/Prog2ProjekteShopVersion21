/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Persistence;

import java.io.IOException;

import KernklassenInterface.WarenereignisInterface;
import KernKlassen.Artikel;
import KernKlassen.Kunde;
import KernKlassen.Mitarbeiter;
import KernKlassen.Warenereignis;

/**
 * Die Methoden dieser Klasse müssen in den Klassen implementiert werden,
 * die diese Schnittstelle realisieren wollen
 */
public interface PersistenceSchnittstelle{

    /**
     * Öffnet den Lesevorgang
     * @param daten
     * @throws IOException
     */
    public void openForReading(String daten) throws IOException;

    /**
     * Öffnet den Schreibvorgang
     * @param daten
     * @throws IOException
     */
    public void openForWriting(String daten) throws IOException;

    /**
     * Die Schnittstelle wird geschlossen
     * @return
     */
    public boolean close();

    String einlesen() throws IOException;

    void schreiben(String daten);

    /**
     * Die Artikelliste mit vorhandenen einträgen werden geladen
     * Es werden Artikel in die Artikelliste eingespeichert
     * @return
     * @throws IOException
     */
    public Artikel ladeArtikel() throws IOException;
    public boolean speichereArtikel(Artikel art) throws IOException;

    /**
     * Die Kundenliste mit vorhandenen einträgen werden geladen
     * Es werden Kunden in die Kundenliste eingespeichert
     * @return
     * @throws IOException
     */
    public Kunde ladeKunde() throws IOException;
    public boolean speichereKunden(Kunde kund) throws IOException;

    /**
     * Die Mitarbeiterliste mit vorhandenen einträgen werden geladen
     * Es werden Mitarbeiter in die Mitarbeiterliste eingespeichert
     * @return
     * @throws IOException
     */
    public Mitarbeiter ladeMitarbeiter() throws IOException;
    public boolean speichereMitarbeiter(Mitarbeiter mitar) throws IOException;

    /**
     * Die Warenereignisliste mit vorhandenen einträgen werden geladen
     * Es werden Warenereignisse in die Warenereignisliste eingespeichert
     * @return
     * @throws IOException
     */
    public Warenereignis ladeWarenereignis() throws IOException;
    public boolean speichereWarenereignis(WarenereignisInterface warerg) throws IOException;
}