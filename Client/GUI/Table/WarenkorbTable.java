/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package GUI.Table;

import javax.swing.JTable;

import GUI.Model.WarenkorbModel;
import KernklassenInterface.ArtikelInterface;

/**
 * Die Klasse dient für die Warenkorbtabelle die erzeugt und ausgegeben wird
 * von einer existierenden Artikelliste mit einträgen
 * Die Klasse erbt von der JTable Klasse
 */
public class WarenkorbTable extends JTable{

    // Der Konstruktor der Klasse dessen Parameter die Artikelliste hat
    public WarenkorbTable(java.util.List<ArtikelInterface> warenKorb){

        super();

        // TableModel erzeugen an dieser stelle
        WarenkorbModel model = new WarenkorbModel(warenKorb);

        // beim JTable anmelden 
        setModel(model);

        // Die Daten an das Model übergeben
        updateWarenkorb(warenKorb);

    }

    /**
     * Diese Methode sorgt dafür das der Listeninhalt aktualisiert wird
     * @param warenKorb
     */
    public void updateWarenkorb(java.util.List<ArtikelInterface> warenKorb){

        // TableModel vom JTable holen 
        WarenkorbModel model = (WarenkorbModel) getModel();

        // und hier wird der Inhalt aktualisiert 
        model.setWarenkorb(warenKorb);

    }
    
}
