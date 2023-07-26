/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package GUI.Panels;

import javax.swing.*;

import Exception.ArtikelNichtGefundenException;
import KernklassenInterface.eShopInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * dient für die Darstellung der Bestandshistorie und stellt diese als Layout dar
 * und erbt von der JFrame Klasse 
 */
public class BestandsHistoriePanel extends JFrame {

    /**
     * Die Eigenschaften der Klasse 
     * die deklariert werden
     */
    private eShopInterface eS;
    private JTextField artikelField;
    private JTextField jahrestagField;
    private JScrollPane scrollPane;
    private BestandGraphComponent bestandGraphComponent;

    /**
     * hier wird der Konstruktor für die Klasse erzeugt
     * es werden der eShop und der Titel des Frames per Parameter verwendet 
     * @param eS
     * @param titel
     */
    public BestandsHistoriePanel(eShopInterface eS, String title) {
        this.eS = eS;

        setTitle(title);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();

        JLabel artikelLabel = new JLabel("Artikel:");
        artikelField = new JTextField(10);

        JLabel jahrestagLabel = new JLabel("Jahrestag (1-365):");
        jahrestagField = new JTextField(10);

        JButton anzeigenButton = new JButton("Anzeigen");
        anzeigenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    String artikelBezeichnung = artikelField.getText();
                    int jahrestag = Integer.parseInt(jahrestagField.getText());

                    int[] zeitraum = eS.zeitraumAlsArray(jahrestag);
                    int[] bestaende = eS.getBestandshistorie(artikelBezeichnung, zeitraum);

                    bestandGraphComponent = new BestandGraphComponent(zeitraum);

                    /*
                     * Ehemalige Erzeugung einer Tabelle, die an das ScrollPane geheftet wurde
                     * 
                     * // Erzeugung des Tabellendatenmodells
                     * DefaultTableModel tableModel = new DefaultTableModel();
                     * tableModel.addColumn("Tag");
                     * tableModel.addColumn("Bestand am Tag");
                     * 
                     * // Hinzufügen der Daten zur Tabelle
                     * for (int i = 0; i < zeitraum.length; i++) {
                     * tableModel.addRow(new Object[] { zeitraum[i], bestaende[i] });
                     * }
                     * 
                     * // Erzeugung der JTable mit dem Tabellendatenmodell
                     * JTable table = new JTable(tableModel);
                     */

                    bestandGraphComponent.setData(bestaende);

                    // Erzeugung des neuen Scrollbereichs für die Tabelle
                    JScrollPane newScrollPane = new JScrollPane(bestandGraphComponent);

                    // Entfernen des alten JScrollPane-Komponenten, falls vorhanden
                    if (scrollPane != null) {
                        getContentPane().remove(scrollPane);
                    }

                    // Aktualisierung der Instanzvariable
                    scrollPane = newScrollPane;

                    // Hinzufügen des Scrollbereichs zum Content Pane
                    getContentPane().add(scrollPane, BorderLayout.CENTER);

                    pack();

                    setMinimumSize(getContentPane().getSize());

                    setLocationRelativeTo(null);

                    JOptionPane.showMessageDialog(getContentPane(),
                            "Jetziger Bestand: " + bestaende[bestaende.length - 1], "Info", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ioe) {

                } catch (ArtikelNichtGefundenException | NullPointerException ange) {
                    JOptionPane.showMessageDialog(getContentPane(), "Geben sie einen Artikel ein, der existiert.",
                            "Kein gültiger Artikel!", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(getContentPane(), "Geben sie eine gültige Ganzzahl ein.",
                            "Keine Zahl eingeben!", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        inputPanel.add(artikelLabel);
        inputPanel.add(artikelField);
        inputPanel.add(jahrestagLabel);
        inputPanel.add(jahrestagField);
        inputPanel.add(anzeigenButton);

        getContentPane().add(inputPanel, BorderLayout.NORTH);

        pack();

        setLocationRelativeTo(null);

        // Sorgt dafür, dass der anzeigenButton auch durch Enter gedrückt werden kann.
        getRootPane().setDefaultButton(anzeigenButton);

        setResizable(false);
        setVisible(true);
    }
}