/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Exception;

/**
 * Diese Klasse dient für eine Fehlermeldung,
 * falls nach einem Artikel gesucht wird, der nicht vorhanden ist in der Artikelliste
 * Die Klasse erbt von der Exception Klasse
 */
public class ArtikelNichtGefundenException extends Exception {

    /**
     * Das ist der Konstruktor der Klasse
     * Bei einer vorkommenden Fehlermeldung,
     * wird eine String Nachricht ausgegeben
     */
    public ArtikelNichtGefundenException() {
        super("Der gewünschte Artikel wurde nicht gefunden.");

    }
}
