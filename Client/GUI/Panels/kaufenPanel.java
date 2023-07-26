/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package GUI.Panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Exception.ArtikelNichtGefundenException;
import Exception.NegativBestandException;
import GUI.Table.ArtikelTable;
import GUI.Table.WarenkorbTable;
import KernklassenInterface.ArtikelInterface;
import KernklassenInterface.eShopInterface;

/**
 * dient für das kaufen der Artikel aus einer Liste und stellt diese als Layout
 * dar
 * und erbt von der JFrame Klasse
 */
public class kaufenPanel extends JFrame {

    /**
     * Die Eigenschaften der Klasse
     * die deklariert werden
     */
    private JButton button;
    private JTextField artikelFeld;
    private JTextField anzahlFeld;
    private JTextField jahresTagFeld;

    private ArtikelTable artikelTable;

    private WarenkorbTable warenKorbTable;

    private eShopInterface eS;

    /**
     * hier wird der Konstruktor für die Klasse erzeugt
     * es werden der eShop und der Titel des Frames per Parameter verwendet
     * 
     * @param eS
     * @param titel
     * @throws RemoteException
     */
    public kaufenPanel(eShopInterface eS, String titel) throws RemoteException {

        this.eS = eS;

        // Es wird eine Liste der Artikel einer Variable gegeben
        java.util.List<ArtikelInterface> art = eS.gibAlleArtikel();
        // eine neue Artikeltable wird erzeugt
        artikelTable = new ArtikelTable(art);

        // Es wird eine Liste der Artikel in einem Warenkorb einer Variable gegeben
        java.util.List<ArtikelInterface> wk = eS.gibWarenkorb();
        // eine neue Artikeltable für den Warenkorb wird erzeugt
        warenKorbTable = new WarenkorbTable(wk);

        /**
         * hier wird ein JFrame erzeugt mit einer definierten Fenster höhe und breite
         * Das Fenster wird hier mittig auf dem Bildschirm gestartet und wird beendet
         * wenn man das JFrame schliesst
         */
        JFrame frame = new JFrame(titel);
        frame.setSize(550, 440);
        frame.setLocationRelativeTo(null);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * hier wird ein Panel Objekt erzeugt mit einem vorgefertigten Layout Manager
         * und neue TextFelder werden hier erzeugt
         */
        JPanel panel = new JPanel(new FlowLayout());
        artikelFeld = new JTextField();
        anzahlFeld = new JTextField();
        jahresTagFeld = new JTextField();

        /**
         * hier wird ein label hinzugefügt und ein TextFeld mit bevorzugte höhe und
         * breite
         * und ein Abstand zwischen den beiden Objekten wird erzeugt
         */
        panel.add(new JLabel(" Nach Artikel suchen: "));
        artikelFeld.setPreferredSize(new Dimension(150, 20));
        panel.add(Box.createHorizontalStrut(60));
        panel.add(artikelFeld);

        /**
         * hier wird ein label hinzugefügt und ein TextFeld mit bevorzugte höhe und
         * breite
         * und ein Abstand zwischen den beiden Objekten wird erzeugt
         */
        panel.add(new JLabel(" Geben sie den Jahrestag ein: "));
        jahresTagFeld.setPreferredSize(new Dimension(60, 20));
        panel.add(Box.createHorizontalStrut(100));
        panel.add(jahresTagFeld);

        /**
         * hier wird ein label hinzugefügt und ein TextFeld mit bevorzugte höhe und
         * breite
         * und ein Abstand zwischen den beiden Objekten wird erzeugt
         */
        panel.add(new JLabel(" Stückzahl eingeben: "));
        anzahlFeld.setPreferredSize(new Dimension(60, 20));
        panel.add(Box.createHorizontalStrut(150));
        panel.add(anzahlFeld);

        /**
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel und der Abstand von diesem Objekt zu einem anderen Objekt wird hier
         * auch vergeben
         * Fehlermeldungen werden als Fenster angezeigt
         */
        button = new JButton(" Hinzufügen ");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                try {
                    artikelSuchen();
                } catch (ArtikelNichtGefundenException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    artikelKaufen();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (ArtikelNichtGefundenException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button.setPreferredSize(new Dimension(110, 40));
        panel.add(Box.createHorizontalStrut(230));
        panel.add(button);

        /**
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel und der Abstand von diesem Objekt zu einem anderen Objekt wird hier
         * auch vergeben
         */
        button = new JButton(" Leeren ");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {

                try {
                    eS.leereDenWarenkorb();
                    warenKorbTable.updateWarenkorb(eS.gibWarenkorb());
                } catch (RemoteException e1) {
                    // TODO RemoteException
                }

            }
        });

        button.setPreferredSize(new Dimension(100, 40));
        panel.add(Box.createHorizontalStrut(0));
        panel.add(button);

        /**
         * hier wird ein neuer button erzeugt mit einer bevorzugten höhe und breite und
         * wird dann hinzugefügt
         * zum panel und der Abstand von diesem Objekt zu einem anderen Objekt wird hier
         * auch vergeben
         */
        button = new JButton(" Kaufen ");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                kaufAnfrage();
            }
        });
        button.setPreferredSize(new Dimension(100, 40));
        panel.add(button);
        panel.add(Box.createHorizontalStrut(280));

        /**
         * es wird ein grosses Feld mit einer gegeben höhe und breite erstellt in denen
         * artikel einträge
         * angezeigt werden und sie werden zum panel hinzugefügt
         */
        JScrollPane scrollPane = new JScrollPane(warenKorbTable);
        JScrollPane paneScroll = new JScrollPane(artikelTable);
        scrollPane.setPreferredSize(new Dimension(140, 210));
        paneScroll.setPreferredSize(new Dimension(220, 210));
        panel.add(scrollPane);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(paneScroll);

        /**
         * Fügt den Panel zum Frame hinzu und macht es dann sichtbar
         * das Fenster des Frames kann nicht verändert werden
         */
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Wenn ein Kunde angemeldet ist, kann er alle Artikel in seinem Warenkorb
     * kaufen.
     * Für einen Kaufvorgang wird der heutige Jahrestag als Eingabe benötigt.
     * Es wird eine Rechnung erzeugt und ausgeben.
     * Anschließend wird der Warenkorb geleert.
     * 
     * @throws IOException
     */
    public void kaufAnfrage() {

        try {

            int jahrestag = liesGueltigenIntegerEin();
            jahrestag = jahrestagInsLimitSetzen(jahrestag);
            String rechnungsText = eS.kaufen(jahrestag);
            eS.schreibeWarenereignis();
            eS.schreibeArtikel();
            JOptionPane.showMessageDialog(this, rechnungsText, "Ihr Kauf", JOptionPane.ERROR_MESSAGE);
        } catch (NegativBestandException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Der Kaufvorgang wurde abgebrochen", "Fehler", JOptionPane.ERROR_MESSAGE);
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

    /**
     * die Methode ist für das erhöhen des Artikelbestandes zuständig
     * Die Werte werden von den jeweiligen Textfeldern genommen und verarbeitet in
     * dem die
     * Werte als Parameter übergeben werden um die zugehörige Methode auszuführen
     * 
     * @throws ArtikelNichtGefundenException
     * @throws IOException
     */
    public void artikelSuchen() throws ArtikelNichtGefundenException, IOException {

        String bezeichnung = artikelFeld.getText();

        if (!bezeichnung.isEmpty()) {

            try {
                eS.sucheArt(bezeichnung);
                JOptionPane.showMessageDialog(this, "Artikel wurde gefunden!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (ArtikelNichtGefundenException e) {
                throw new ArtikelNichtGefundenException();
            }
        }
    }

    /**
     * die Methode ist für das kaufen von Artikeln zuständig
     * Die Werte werden von den jeweiligen Textfeldern genommen und verarbeitet in
     * dem die
     * Werte als Parameter übergeben werden um die zugehörige Methode auszuführen
     * 
     * @throws IOException
     * @throws ArtikelNichtGefundenException
     */
    public void artikelKaufen() throws IOException, ArtikelNichtGefundenException {
            String bezeichnung = artikelFeld.getText();
            String anzahl = anzahlFeld.getText();
            try {
                int menge = Integer.parseInt(anzahl);
                if (!(bezeichnung.isEmpty() && anzahl.isEmpty())) {
                    eS.artikelZumWarenkorbHinzufuegen(bezeichnung, menge);
                    warenKorbTable.updateWarenkorb(eS.gibWarenkorb());
                    JOptionPane.showMessageDialog(this, "Artikel wurde zum Warenkorb hinzugefügt", "Hinzugefügt", JOptionPane.INFORMATION_MESSAGE);
                }
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Fehler: Bitte geben sie den Artikel Namen an!", "Error", JOptionPane.ERROR_MESSAGE);
            }



    }

}
