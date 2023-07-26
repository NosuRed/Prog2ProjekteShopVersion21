/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package GUI.Panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import KernklassenInterface.eShopInterface;

/**
 * dient für das Menu des Mitarbeiter und stellt diese als Layout dar
 * und erbt von der JFrame Klasse
 */
public class mitarbeiterMenuPanel extends JFrame {

    /**
     * Die Eigenschaften der Klasse
     * die deklariert werden
     */
    private JButton button;
    private ArtikelHinzufuegenPanel artikelHinzufuegenPanel;
    private MitarbeiterRegistrierenPanel mitarbeiterRegistrierenPanel;
    private ArtikelBestandErhoehenPanel artikelBestandErhoehenPanel;
    private BestandsHistoriePanel bestandsHistoriePanel;

    /**
     * hier wird der Konstruktor für die Klasse erzeugt
     * es werden der eShop und der Titel des Frames per Parameter verwendet
     * 
     * @param eS
     * @param titel
     */
    public mitarbeiterMenuPanel(eShopInterface eS, String titel) {

        /**
         * hier wird ein JFrame erzeugt mit einer definierten Fenster höhe und breite
         * Das Fenster wird hier mittig auf dem Bildschirm gestartet und wird beendet
         * wenn man das JFrame schliesst
         */
        JFrame frame = new JFrame(titel);
        frame.setSize(300, 270);
        frame.setLocationRelativeTo(null);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * hier wird ein Panel Objekt erzeugt mit einem vorgefertigten Layout Manager
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel
         */
        JPanel panel = new JPanel(new FlowLayout());
        button = new JButton(" Neuen Mitarbeiter registrieren ");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                mitarbeiterRegistrierenPanel = new MitarbeiterRegistrierenPanel(eS, "Der eShop");
            }

        });
        button.setPreferredSize(new Dimension(220, 50));
        panel.add(button);

        /**
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel
         */
        button = new JButton(" Neuen Artikel hinzufügen ");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                try {
                    artikelHinzufuegenPanel = new ArtikelHinzufuegenPanel(eS, "Der eShop");
                } catch (RemoteException e1) {
                    // TODO RemoteException
                }

            }

        });
        button.setPreferredSize(new Dimension(220, 50));
        panel.add(button);

        /**
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel
         */
        button = new JButton(" Artikelbestand erhöhen ");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                artikelBestandErhoehenPanel = new ArtikelBestandErhoehenPanel(eS, "Der eShop");
            }

        });
        button.setPreferredSize(new Dimension(220, 50));
        panel.add(button);

        /**
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel
         */
        button = new JButton("Bestandshistorie anzeigen");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                bestandsHistoriePanel = new BestandsHistoriePanel(eS, "Bestandshistorie");
            }

        });
        button.setPreferredSize(new Dimension(220, 50));
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
