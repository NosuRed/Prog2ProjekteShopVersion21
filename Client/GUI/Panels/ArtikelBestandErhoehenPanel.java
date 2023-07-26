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

import Exception.ArtikelNichtGefundenException;
import Exception.NegativBestandException;
import KernklassenInterface.eShopInterface;

/**
 * dient für die Artikelbestand erhöhung und stellt diese als Layout dar
 * und erbt von der JFrame Klasse
 */
public class ArtikelBestandErhoehenPanel extends JFrame {

    /**
     * Die Eigenschaften der Klasse
     * die deklariert werden
     */
    private JButton button;
    private JTextField artikelBezeichnungsFeld;
    private JTextField bestandErhoehenFeld;
    private JTextField jahresTagFeld;

    private eShopInterface eS;

    /**
     * hier wird der Konstruktor für die Klasse erzeugt
     * es werden der eShop und der Titel des Frames per Parameter verwendet
     * 
     * @param eS
     * @param titel
     */
    public ArtikelBestandErhoehenPanel(eShopInterface eS, String titel) {

        this.eS = eS;

        /**
         * hier wird ein JFrame erzeugt mit einer definierten Fenster höhe und breite
         * Das Fenster wird hier mittig auf dem Bildschirm gestartet und wird beendet
         * wenn man das JFrame schliesst
         */
        JFrame frame = new JFrame(titel);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(400, 220);

        /**
         * hier wird ein Panel Objekt erzeugt mit einem vorgefertigten Layout Manager
         * und neue TextFelder werden hier erzeugt
         */
        JPanel panel = new JPanel(new FlowLayout());
        artikelBezeichnungsFeld = new JTextField();
        bestandErhoehenFeld = new JTextField();
        jahresTagFeld = new JTextField();

        /**
         * hier wird ein label hinzugefügt und ein TextFeld mit bevorzugte höhe und
         * breite
         * und ein Abstand zwischen den beiden Objekten wird erzeugt
         */
        panel.add(new JLabel(" Artikelbezeichnung eingeben: "));
        artikelBezeichnungsFeld.setPreferredSize(new Dimension(150, 20));
        panel.add(Box.createHorizontalStrut(40));
        panel.add(artikelBezeichnungsFeld);

        /**
         * hier wird ein label hinzugefügt und ein TextFeld mit bevorzugte höhe und
         * breite
         * und ein Abstand zwischen den beiden Objekten wird erzeugt
         */
        panel.add(new JLabel(" Um wie viel soll der Bestand erhöht werden ? "));
        bestandErhoehenFeld.setPreferredSize(new Dimension(50, 20));
        panel.add(Box.createHorizontalStrut(52));
        panel.add(bestandErhoehenFeld);

        panel.add(new JLabel(" Geben sie den Jahrestag ein: "));
        jahresTagFeld.setPreferredSize(new Dimension(50, 20));
        panel.add(Box.createHorizontalStrut(142));
        panel.add(jahresTagFeld);

        /**
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel und der Abstand von diesem Objekt zu einem anderen Objekt wird hier
         * auch vergeben
         * Fehlermeldungen werden als Fenster eingeblendet
         */
        button = new JButton(" Bestätigen ");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    artikelErhoehen();
                } catch (NegativBestandException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (ArtikelNichtGefundenException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button.setPreferredSize(new Dimension(200, 50));
        panel.add(button);
        panel.add(Box.createVerticalStrut(100));

        /**
         * Fügt den Panel zum Frame hinzu und macht es dann sichtbar
         * das Fenster des Frames kann nicht verändert werden
         */
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    /**
     * die Methode ist für das erhöhen des Artikelbestandes zuständig
     * Die Werte werden von den jeweiligen Textfeldern genommen und verarbeitet in
     * dem die
     * Werte als Parameter übergeben werden um die zugehörige Methode auszuführen
     * 
     * @throws NegativBestandException
     * @throws IOException
     * @throws ArtikelNichtGefundenException
     */
    public void artikelErhoehen() throws NegativBestandException, IOException, ArtikelNichtGefundenException {
        try {
            String bezeichnung = artikelBezeichnungsFeld.getText();
            String bestand = bestandErhoehenFeld.getText();
            int zahl = Integer.parseInt(bestand);
            int jahresTag = liesGueltigenIntegerEin();
            jahresTag = jahrestagInsLimitSetzen(jahresTag);

            if (!(bezeichnung.isEmpty() && bestand.isEmpty())) {

                try {
                    int neuerBestand = eS.artikelBestandErhoehen(bezeichnung, zahl, jahresTag);
                    JOptionPane.showMessageDialog(this, " Artikelbestand wurde auf " + neuerBestand + " erhöht ", "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                    eS.schreibeArtikelbestandErhoehen();
                    eS.schreibeWarenereignis();
                    JOptionPane.showMessageDialog(this, "Bestand wurde erfolgreich erhöht");
                } catch (ArtikelNichtGefundenException e) {
                    throw new ArtikelNichtGefundenException();
                }
            }
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Bitte füllen Sie die Felder vollständig/richtig aus", "Fehler",JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Diese Methode beschäftigt sich damit das bei bestimmten eingaben die ein
     * Integer Wert erfordern
     * auch ein Integer Wert eingelesen wird, ansonsten wird eine Fehlermeldung
     * ausgegeben
     * 
     * @return
     */
    public int liesGueltigenIntegerEin() {
        int number = 0;
        boolean valid = false;

        while (!valid) {
            try {
                String input = jahresTagFeld.getText();
                number = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ungültige Eingabe. Bitte versuchen Sie es erneut.", "Fehler", JOptionPane.ERROR_MESSAGE);
                valid = true;
            }
        }

        return number;
    }

    /**
     * Diese Methode dient dafür das es ein Limit beim Jahrestag gibt
     * es soll verhindert werden das mit werten ausserhalb der Jahrestage gearbeitet
     * wird
     * 
     * @param jahrestag
     * @return
     */
    public int jahrestagInsLimitSetzen(int jahrestag) {
        if (jahrestag > 365) {
            jahrestag = 365;
        } else if (jahrestag < 1) {
            jahrestag = 1;
        }
        return jahrestag;
    }

}
