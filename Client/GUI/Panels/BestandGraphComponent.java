/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package GUI.Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * Diese Klasse ist für das darstellen des Bestandes dar
 * und zeichnet den Graphen für den Bestand
 * es wird von der Klasse JComponent geerbt
 * und stellt es als Layout dar
 */
public class BestandGraphComponent extends JComponent {

    // Die Eigenschaften der Klasse werden hier deklariert
    private int[] xAxis;
    private int[] yAxis;

    /**
     * Der Konstruktor der Klasse dem ein Integer Array mit Elementen 
     * an den Parameter gegeben wird 
     * @param xValues
     */
    public BestandGraphComponent(int[] xValues) {
        xAxis = xValues; // Initialisierung mit Platz für 30 Werte
    }

    public void setData(int[] yValues) {
        yAxis = yValues;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Hintergrund zeichnen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Koordinatensystem zeichnen
        g.setColor(Color.BLACK);
        g.drawLine(30, getHeight() - 30, 30, 30); // y-Achse
        g.drawLine(30, getHeight() - 30, getWidth() - 30, getHeight() - 30); // x-Achse

        // Achsenbeschriftungen
        g.drawString("Tage", getWidth() - 50, getHeight() - 10);
        g.drawString("Bestand am Tag", 15, 20);

        // Linien und Trennstriche zeichnen
        g.setColor(Color.GRAY);
        int xStep = (getWidth() - 60) / 29; // Berechnung der Schrittweite für die x-Koordinate | Abzug von 60 um den Abstand der Achse von den Componenträndern auszuschließen.

        // Trennstriche auf der x-Achse
        for (int i = 0; i <= 29; i++) {
            int x = 30 + i * xStep;
            g.drawLine(x, getHeight() - 30, x, getHeight() - 25);
            g.drawString("" + xAxis[i], x - 3, getHeight() - 10);
        }

        // Trennstriche auf der y-Achse
        int yStep = (getHeight() - 60) / 5; // Schrittweite für die y-Koordinate
        for (int i = 0; i <= 5; i++) {
            int y = getHeight() - 30 - i * yStep; // * yScale;
            g.drawLine(25, y, 30, y);
            g.drawString("" + (i * yStep), 5, y + 5);
        }

        // Linie zeichnen
        g.setColor(Color.BLUE);
        for (int i = 0; i < yAxis.length - 1; i++) {
            int x1 = 30 + i * xStep;
            int y1 = getHeight() - 30 - (yAxis[i]);// * yStep);
            int x2 = 30 + (i + 1) * xStep;
            int y2 = getHeight() - 30 - (yAxis[i + 1]);// * yStep);
            g.drawLine(x1, y1, x2, y2);

            //System.out.println("Jetziger Koordinatenpunkt: " + (30 + i * xStep) + "|" + (yAxis[i] * 1 + yStep));
            //System.out.println("Breite: " + getWidth() + "| Höhe: " + getHeight());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 400); // Bevorzugte Größe des Koordinatensystem-Komponenten
    }
}
