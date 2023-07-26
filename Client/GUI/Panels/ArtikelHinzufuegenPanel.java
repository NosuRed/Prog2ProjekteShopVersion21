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
import java.rmi.RemoteException;

import javax.swing.*;

import Exception.ArtikelIstSchonVorhanden;
import Exception.ArtikelNichtGefundenException;
import GUI.Table.ArtikelTable;
import KernklassenInterface.ArtikelInterface;
import KernklassenInterface.eShopInterface;

/**
 * dient für das Artikelhinzufügen und stellt diese als Layout dar
 * und erbt von der JFrame Klasse
 */
public class ArtikelHinzufuegenPanel extends JFrame {

    /**
     * Die Eigenschaften der Klasse
     * die deklariert werden
     */
    private JButton button;
    private JTextField artikelBezeichnungFeld;
    private JTextField artikelBestandFeld;
    private JTextField artikelPreisFeld;
    private JTextField jahresTagFeld;

    private JLabel packetSizeLabel;
    private JCheckBox isPackArtCB;
    private JTextField packetSizeTX;

    private eShopInterface eS;

    private ArtikelTable artikelTable;

    /**
     * hier wird der Konstruktor für die Klasse erzeugt
     * es werden der eShop und der Titel des Frames per Parameter verwendet
     * 
     * @param eS
     * @param titel
     * @throws RemoteException
     */
    public ArtikelHinzufuegenPanel(eShopInterface eS, String titel) throws RemoteException {

        this.eS = eS;

        // Es wird eine Liste der Artikel einer Variable gegeben
        java.util.List<ArtikelInterface> art = eS.gibAlleArtikel();
        // eine neue Artikeltable wird erzeugt
        artikelTable = new ArtikelTable(art);

        /**
         * hier wird ein JFrame erzeugt mit einer definierten Fenster höhe und breite
         * Das Fenster wird hier mittig auf dem Bildschirm gestartet und wird beendet
         * wenn man das JFrame schliesst
         */
        JFrame frame = new JFrame(titel);
        frame.setSize(400, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * hier wird ein Panel Objekt erzeugt mit einem vorgefertigten Layout Manager
         * und neue TextFelder werden hier erzeugt
         * sowie eine CheckBox und ein Label
         */
        JPanel panel = new JPanel(new FlowLayout());
        artikelBezeichnungFeld = new JTextField();
        artikelBestandFeld = new JTextField();
        artikelPreisFeld = new JTextField();
        jahresTagFeld = new JTextField();
        isPackArtCB = new JCheckBox("Massengut Artikel? Ja/nein");
        packetSizeLabel = new JLabel("Massengut Anzahl");
        packetSizeTX = new JTextField();

        /**
         * hier wird ein label hinzugefügt und ein TextFeld mit bevorzugte höhe und
         * breite
         * und ein Abstand zwischen den beiden Objekten wird erzeugt
         */
        panel.add(new JLabel(" Artikelbezeichnung eingeben: "));
        artikelBezeichnungFeld.setPreferredSize(new Dimension(150, 20));
        panel.add(Box.createHorizontalStrut(30));
        panel.add(artikelBezeichnungFeld);

        /**
         * hier wird ein label hinzugefügt und ein TextFeld mit bevorzugte höhe und
         * breite
         * und ein Abstand zwischen den beiden Objekten wird erzeugt
         */
        panel.add(new JLabel(" Bestand eingeben: "));
        panel.add(Box.createHorizontalStrut(191));
        artikelBestandFeld.setPreferredSize(new Dimension(50, 20));
        panel.add(artikelBestandFeld);

        /**
         * hier wird ein label hinzugefügt und ein TextFeld mit bevorzugte höhe und
         * breite
         * und ein Abstand zwischen den beiden Objekten wird erzeugt
         */
        panel.add(new JLabel(" Preis eingeben: "));
        artikelPreisFeld.setPreferredSize(new Dimension(50, 20));
        panel.add(Box.createHorizontalStrut(208));
        panel.add(artikelPreisFeld);

        panel.add(new JLabel(" Geben sie den Jahrestag ein: "));
        jahresTagFeld.setPreferredSize(new Dimension(50, 20));
        panel.add(Box.createHorizontalStrut(133));
        panel.add(jahresTagFeld);

        /**
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel und der Abstand von diesem Objekt zu einem anderen Objekt wird hier
         * auch vergeben
         * Fehlermeldungen werden als Fenster eingeblendet
         */
        button = new JButton(" Hinzufügen ");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    artikelHinzufuegen();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (ArtikelNichtGefundenException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (ArtikelIstSchonVorhanden e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(Box.createHorizontalStrut(258));
        button.setPreferredSize(new Dimension(105, 40));
        panel.add(button);
        panel.add(Box.createVerticalStrut(40));

        /**
         * es wird ein PacketSize mit einer bevorzugten höhe und breite erzeugt
         * und wird zum panel hinzugefügt sowie das Label und die CheckBox
         */
        packetSizeTX.setPreferredSize(new Dimension(50, 20));
        panel.add(packetSizeLabel);
        panel.add(packetSizeTX);
        panel.add(isPackArtCB);

        /**
         * es wird ein grosses Feld mit einer gegeben höhe und breite erstellt in denen
         * artikel einträge
         * angezeigt werden und sie werden zum panel hinzugefügt
         */
        JScrollPane scrollPane = new JScrollPane(artikelTable);
        scrollPane.setPreferredSize(new Dimension(360, 220));
        panel.add(scrollPane);

        /**
         * Fügt den Panel zum Frame hinzu und macht es dann sichtbar
         * das Fenster des Frames kann nicht verändert werden
         */
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    /**
     * die Methode ist für das hinzufügen der Artikel zuständig
     * Die Werte werden von den jeweiligen Textfeldern genommen und verarbeitet in
     * dem die
     * Werte als Parameter übergeben werden um die zugehörige Methode auszuführen
     * 
     * @throws IOException
     * @throws ArtikelNichtGefundenException
     * @throws ArtikelIstSchonVorhanden
     */
    public void artikelHinzufuegen() throws IOException, ArtikelNichtGefundenException, ArtikelIstSchonVorhanden {
        try {
            String artikelBezeichnung = artikelBezeichnungFeld.getText();
            String artikelBestand = artikelBestandFeld.getText();
            int bestand = Integer.parseInt(artikelBestand);
            String artikelPreis = artikelPreisFeld.getText();
            float preis = Float.parseFloat(artikelPreis);
            int artikelID = eS.generateID('a');
            int jahresTag = liesGueltigenIntegerEin();
            int packCount;

            jahresTag = jahrestagInsLimitSetzen(jahresTag);


            if (!(artikelBezeichnung.isEmpty() && artikelBestand.isEmpty() && artikelPreis.isEmpty())) {

                try {
                    if (!isPackArtCB.isSelected()) {
                        eS.neueArtikelHinzufuegen(artikelID, artikelBezeichnung, bestand, preis, jahresTag);
                        eS.schreibeArtikel();
                        artikelTable.updateArtikelList(eS.gibAlleArtikel());
                        JOptionPane.showMessageDialog(this, "Artikel wurde erfolgreich hinzugefügt");
                    } else {
                        packCount = Integer.parseInt(packetSizeTX.getText());
                        eS.neueMassenArtikelHinzufuegen(artikelID, artikelBezeichnung, bestand, preis, jahresTag,
                                packCount);
                        eS.schreibeArtikel();
                        artikelTable.updateArtikelList(eS.gibAlleArtikel());
                    }
                } catch (ArtikelIstSchonVorhanden e) {
                    throw new ArtikelIstSchonVorhanden();
                }

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Bitte geben sie gültige Werte an!");
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