/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package GUI.Table;

import java.rmi.RemoteException;
import java.util.Collections;

import javax.swing.JTable;

import KernklassenInterface.ArtikelInterface;
import GUI.Model.ArtikelModel;

/**
 * Die Klasse dient für die Artikeltabelle die erzeugt und ausgegeben wird
 * von einer existierenden Artikelliste mit einträgen
 * Die Klasse erbt von der JTable Klasse
 */
public class ArtikelTable extends JTable{

    //Der Konstruktor der Klasse dessen Parameter die Artikelliste hat
    public ArtikelTable(java.util.List<ArtikelInterface> artikel) throws RemoteException{
        
        super();

        // TableModel erzeugen an dieser stelle
        ArtikelModel model = new ArtikelModel(artikel);

        // beim JTable anmelden 
        setModel(model);

        // Die Daten an das Model übergeben
        updateArtikelList(artikel);
    }

    /**
     * Diese Methode sorgt dafür das der Listeninhalt aktualisiert wird
     * @param artikel
     */
    public void updateArtikelList(java.util.List<ArtikelInterface> artikel) throws RemoteException{

        /**
         * Sortierung mit Lambada Expression
         * es wird nach nummer sortiert
         */
        Collections.sort(artikel, (a1, a2) -> {
            try {
                return a1.getNummer() - a2.getNummer();
            } catch (RemoteException e) {
                //TODO RemoteException
            }
            return -1;
        });
        // TableModel vom JTable holen 
        ArtikelModel model = (ArtikelModel) getModel();

        // und hier wird der Inhalt aktualisiert 
        model.setArtikel(artikel);

    }
    
}
