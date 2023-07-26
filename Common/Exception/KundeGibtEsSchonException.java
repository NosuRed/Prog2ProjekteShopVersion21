/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Exception;

/**
 * Diese Klasse dient für eine Fehlermeldung,
 * falls sich ein Kunde registrieren will, mit Daten die bereits vorhanden sind 
 * Die Klasse erbt von der Exception Klasse
 */
public class KundeGibtEsSchonException extends Exception{

    /**
     * Das ist der Konstruktor der Klasse
     * Bei einer vorkommenden Fehlermeldung,
     * wird eine String Nachricht ausgegeben
     */
    public KundeGibtEsSchonException(){
        super(" Dieser Kunde ist bereits vorhanden! ");
    }
}
