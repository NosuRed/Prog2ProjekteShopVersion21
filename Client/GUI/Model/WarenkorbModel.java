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
 * Die Klasse dient für das ausgeben der Artikelliste in einer Warenkorbtabelle
 * Die Klasse erbt von der AbstractTableModel Klasse
 */
public class WarenkorbModel extends AbstractTableModel {

    /**
     * Die Eigenschaften der Klasse die deklariert und initialisiert werden
     * dazu zählen eine Artikelliste(Warenkorbliste) und ein String Array für die
     * Splatennamen
     * dieses String Array beinhaltet Artikelnummer, Artikelbezeichnung und
     * Artikelpreis als Elemente
     * die als einzelne Spalten aufgeführt sind
     */
    private List<ArtikelInterface> warenKorb;
    private String[] spaltenName = { "Nummer", "Bezeichnung", "Preis" };

    /**
     * Der Konstruktor der Klasse dessen Parameter eine Artikelliste(Warenkorbliste)
     * ist
     * 
     * @param aktuellerWarenkorb
     */
    public WarenkorbModel(List<ArtikelInterface> aktuellerWarenkorb) {

        super();

        /**
         * Es wird eine Artikelliste(Warenkorbliste) erstellt
         * damit beim Aktualisieren(setWarenkorb)
         * keine Seiteneffekte auftreten
         */
        warenKorb = new Vector<ArtikelInterface>();
        warenKorb.addAll(aktuellerWarenkorb);

    }

    /**
     * Es wird den Parameter eine Artikelliste(Warenkorbliste) gegeben
     * Diese Mthode ist für das löschen und ändern der
     * Artikeltabelle(Warenkorbtabelle)
     * 
     * @param aktuellerWarenkorb
     */
    public void setWarenkorb(List<ArtikelInterface> aktuellerWarenkorb) {

        // Alle Elemente in der Artikelliste(Warenkorbliste) werden gelöscht
        warenKorb.clear();
        // Zur Artikelliste(Warenkorbliste) werden dann alle Artikelelemente hinzugefügt
        warenKorb.addAll(aktuellerWarenkorb);
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
        return warenKorb.size();
    }

    public String getColumnBezeichnung(int num) {
        return spaltenName[num];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        ArtikelInterface art = warenKorb.get(rowIndex);
        try {
            switch (columnIndex) {
                case 0:
                    return art.getNummer();

                case 1:
                    return art.getBezeichnung();

                case 2:
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
