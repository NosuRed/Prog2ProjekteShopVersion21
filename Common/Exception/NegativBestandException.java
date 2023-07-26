/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Exception;

/**
 * Diese Klasse dient für eine Fehlermeldung,
 * falls versucht wird ein Artikel mit einem Bestand von Null zu kaufen
 * Die Klasse erbt von der Exception Klasse
 */
public class NegativBestandException extends Exception{
    
    /**
     * Das ist der Konstruktor der Klasse
     * Bei einer vorkommenden Fehlermeldung,
     * wird eine String Nachricht ausgegeben
     */
    public NegativBestandException(){
        super("Artikelbestand darf nicht unter 0 sein!");
    }
}
