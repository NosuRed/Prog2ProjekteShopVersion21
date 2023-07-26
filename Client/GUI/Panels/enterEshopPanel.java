/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package GUI.Panels;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Exception.KundeGibtEsSchonException;
import KernklassenInterface.eShopInterface;

/**
 * dient für das öffnen des eShops und stellt diese als Layout dar
 * und erbt von der JFrame Klasse 
 */
public class enterEshopPanel extends JFrame{
    
    /**
     * Die Eigenschaften der Klasse 
     * die deklariert werden
     */
    private JButton button; 
    private HauptMenuPanel hauptmenuPanel;

    /**
     * hier wird der Konstruktor für die Klasse erzeugt
     * es werden der eShop und der Titel des Frames per Parameter verwendet 
     * @param eS
     * @param titel
     */
    public enterEshopPanel(eShopInterface eS, String titel){

        /**
         * hier wird ein JFrame erzeugt mit einer definierten Fenster höhe und breite
         * Das Fenster wird hier mittig auf dem Bildschirm gestartet und wird beendet wenn man das JFrame schliesst
         */
        JFrame frame = new JFrame(titel);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * hier wird ein Panel Objekt erzeugt mit einem vorgefertigten Layout Manager
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite 
         * und wird dann hinzugefügt zum panel 
         * Fehlermeldungen werden als Fenster eingeblendet
         */
        JPanel panel = new JPanel(new GridBagLayout());
        button = new JButton(" Der eShop ");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
                try {
                    hauptmenuPanel = new HauptMenuPanel(eS, " Der eShop ");
                } catch (KundeGibtEsSchonException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button.setPreferredSize(new Dimension(100, 100));
        panel.add(button);

        /**
         * Fügt den Panel zum Frame hinzu und macht es dann sichtbar
         * das Fenster des Frames kann nicht verändert werden
         */
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
