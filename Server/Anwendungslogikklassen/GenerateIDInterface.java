/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Anwendungslogikklassen;

// Dieses Interface dient für das generieren der Artikel, Mitarbeiter oder Kundennummern
public interface GenerateIDInterface {

    // Diese Methode wird in den Klassen Artikel, Mitarbeiter und Kundenverwaltungen implementiert
    public int generateID();

}
