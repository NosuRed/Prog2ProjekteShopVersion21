/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package GUI.Panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Exception.FalscheAnmeldeDatenException;
import KernklassenInterface.eShopInterface;

/**
 * dient für die Kundenanmeldung und stellt diese als Layout dar
 * und erbt von der JFrame Klasse
 */
public class KundenAnmeldungPanel extends JFrame {

    /**
     * Die Eigenschaften der Klasse
     * die deklariert werden
     */
    private JButton button;
    private JTextField benutzerFeld;
    private JTextField passwortFeld;

    private eShopInterface eS;
    private kaufenPanel kaufPanel;

    /**
     * hier wird der Konstruktor für die Klasse erzeugt
     * es werden der eShop und der Titel des Frames per Parameter verwendet
     * 
     * @param eS
     * @param titel
     */
    public KundenAnmeldungPanel(eShopInterface eS, String titel) {
        this.eS = eS;

        /**
         * hier wird ein JFrame erzeugt mit einer definierten Fenster höhe und breite
         * Das Fenster wird hier mittig auf dem Bildschirm gestartet und wird beendet
         * wenn man das JFrame schliesst
         */
        JFrame frame = new JFrame(titel);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * hier wird ein Panel Objekt erzeugt mit einem vorgefertigten Layout Manager
         * und neue TextFelder werden hier erzeugt
         */
        JPanel panel = new JPanel(new FlowLayout());
        benutzerFeld = new JTextField();
        passwortFeld = new JTextField();

        /**
         * hier wird ein label hinzugefügt und ein TextFeld mit bevorzugte höhe und
         * breite
         * und ein Abstand zwischen den beiden Objekten wird erzeugt
         */
        panel.add(new JLabel(" Benutzernamen eingeben: "));
        panel.add(Box.createHorizontalStrut(14));
        benutzerFeld.setPreferredSize(new Dimension(200, 20));
        panel.add(benutzerFeld);

        /**
         * hier wird ein label hinzugefügt und ein TextFeld mit bevorzugte höhe und
         * breite
         * und ein Abstand zwischen den beiden Objekten wird erzeugt
         */
        panel.add(new JLabel(" Passwort eingeben: "));
        panel.add(Box.createHorizontalStrut(50));
        passwortFeld.setPreferredSize(new Dimension(200, 20));
        panel.add(passwortFeld);

        /**
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel und der Abstand von diesem Objekt zu einem anderen Objekt wird hier
         * auch vergeben
         * Fehlermeldungen werden als Fenster eingeblendet
         */
        button = new JButton(" Anmelden ");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    kundenAnmeldung();
                    kaufPanel = new kaufenPanel(eS, "Der eShop");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (FalscheAnmeldeDatenException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button.setPreferredSize(new Dimension(200, 50));
        panel.add(button);
        panel.add(Box.createVerticalStrut(80));

        /**
         * Fügt den Panel zum Frame hinzu und macht es dann sichtbar
         * das Fenster des Frames kann nicht verändert werden
         */
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * die Methode ist für die Kundenanmeldung zuständig
     * Die Werte werden von den jeweiligen Textfeldern genommen und verarbeitet in
     * dem die
     * Werte als Parameter übergeben werden um die zugehörige Methode auszuführen
     * Bei einem Fehler wird eine Exception ausgeworfen
     * 
     * @throws IOException
     * @throws FalscheAnmeldeDatenException
     */
    public void kundenAnmeldung() throws IOException, FalscheAnmeldeDatenException {

        String benutzer = benutzerFeld.getText();
        String passwort = passwortFeld.getText();

        if (!(benutzer.isEmpty() && passwort.isEmpty())) {

            try {
                eS.anmeldenAlsKunde(benutzer, passwort);
                benutzerFeld.setText("");
                passwortFeld.setText("");
                JOptionPane.showMessageDialog(this, "Die Anmeldung war erfolgreich", "Ameldung", JOptionPane.INFORMATION_MESSAGE);
            } catch (FalscheAnmeldeDatenException e) {
                throw new FalscheAnmeldeDatenException();
            }
        }
    }

}
