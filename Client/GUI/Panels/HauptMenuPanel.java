/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package GUI.Panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Exception.KundeGibtEsSchonException;
import KernklassenInterface.eShopInterface;

/**
 * dient für das auswählen von paar Optionen und stellt diese als Layout dar
 * und erbt von der JFrame Klasse
 */
public class HauptMenuPanel extends JFrame {

    /**
     * Die Eigenschaften der Klasse
     * die deklariert werden
     */
    private JButton button;
    private KundeRegistrierenPanel kundeRegistrierenPanel;
    private MitarbeiterAnmeldungPanel mitarbeiterAnmeldungPanel;
    private KundenAnmeldungPanel kundeAnmeldungPanel;

    /**
     * hier wird der Konstruktor für die Klasse erzeugt
     * es werden der eShop und der Titel des Frames per Parameter verwendet
     * 
     * @param eS
     * @param titel
     */
    public HauptMenuPanel(eShopInterface eS, String titel) throws KundeGibtEsSchonException {

        /**
         * hier wird ein JFrame erzeugt mit einer definierten Fenster höhe und breite
         * Das Fenster wird hier mittig auf dem Bildschirm gestartet und wird beendet
         * wenn man das JFrame schliesst
         */
        JFrame frame = new JFrame(titel);
        frame.setSize(300, 220);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * hier wird ein Panel Objekt erzeugt mit einem vorgefertigten Layout Manager
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite
         * und wird dann hinzugefügt zum panel
         */
        JPanel panel = new JPanel(new FlowLayout());
        button = new JButton(" Als Kunde registrieren ");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                kundeRegistrierenPanel = new KundeRegistrierenPanel(eS, " Der eShop ");
            }
        });
        button.setPreferredSize(new Dimension(200, 50));
        panel.add(button);

        /**
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel
         */
        button = new JButton(" Als Kunde anmelden ");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                kundeAnmeldungPanel = new KundenAnmeldungPanel(eS, " Der eShop ");

            }
        });
        button.setPreferredSize(new Dimension(200, 50));
        panel.add(button);

        /**
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel
         */
        button = new JButton(" Als Mitarbeiter anmelden ");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mitarbeiterAnmeldungPanel = new MitarbeiterAnmeldungPanel(eS, " Der eShop ");
            }
        });
        button.setPreferredSize(new Dimension(200, 50));
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
