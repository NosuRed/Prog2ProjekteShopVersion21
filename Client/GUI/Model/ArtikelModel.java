/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package GUI.Model;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import KernklassenInterface.ArtikelInterface;

/**
 * Die Klasse dient für das ausgeben der Artikelliste in einer Artikeltabelle
 * Die Klasse erbt von der AbstractTableModel Klasse
 */
public class ArtikelModel extends AbstractTableModel {

    /**
     * Die Eigenschaften der Klasse die deklariert und initialisiert werden
     * dazu zählen eine Artikelliste und ein String Array für die Splatennamen
     * dieses String Array beinhaltet Artikelnummer, Artikelbezeichnung,
     * Artikelbestand und Artikelpreis als Elemente
     * die als einzelne Spalten aufgeführt sind
     */
    private List<ArtikelInterface> artikel;
    private String[] spaltenName = { "Nummer", "Bezeichnung", "Bestand", "Preis" };

    /**
     * Der Konstruktor der Klasse dessen Parameter eine Artikelliste ist
     * 
     * @param aktuelleArtikel
     */
    public ArtikelModel(List<ArtikelInterface> aktuelleArtikel) {
        super();

        /**
         * Es wird eine Artikelliste erstellt
         * damit beim Aktualisieren(setArtikel)
         * keine Seiteneffekte auftreten
         */
        artikel = new Vector<ArtikelInterface>();
        artikel.addAll(aktuelleArtikel);
    }

    /**
     * Es wird den Parameter eine Artikelliste gegeben
     * Diese Mthode ist für das löschen und ändern der Artikeltabelle
     * 
     * @param aktuelleArtikel
     */
    public void setArtikel(List<ArtikelInterface> aktuelleArtikel) {

        // Alle Elemente in der Artikelliste werden gelöscht
        artikel.clear();
        // Zur Artikelliste werden dann alle Artikelelemente hinzugefügt
        artikel.addAll(aktuelleArtikel);
        // Benachrichtigt die Listener das sich die Werte aller Spalten geändert haben
        fireTableDataChanged();
    }

    /**
     * Überschriebene Methoden die ein JTable von jedem Tablemodel erwartet
     * .... Anzahl der Spalten
     * .... Anzahl der Zeilen
     * .... Benennung der Spalten
     * .... Wert einer Zelle in Zeilen und Spalten
     */
    @Override
    public int getColumnCount() {
        return spaltenName.length;
    }

    @Override
    public int getRowCount() {
        return artikel.size();
    }

    public String getColumnBezeichnung(int num) {
        return spaltenName[num];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        ArtikelInterface art = artikel.get(rowIndex);
        try {
            switch (columnIndex) {
                case 0:
                    return art.getNummer();

                case 1:
                    return art.getBezeichnung();

                case 2:
                    return art.getBestand();

                case 3:
                    return art.getPreis();

                default:
                    return null;
            }
        } catch (RemoteException e) {
            // TODO RemoteException
        }
        return -1;
    }
}
