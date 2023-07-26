/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Exception;

/**
 * Diese Klasse dient für eine Fehlermeldung,
 * falls sich ein Mitarbeiter registrieren will, mit Daten die bereits vorhanden sind 
 * Die Klasse erbt von der Exception Klasse
 */
public class MitarbeiterGibtEsSchonException extends Exception{

    /**
     * Das ist der Konstruktor der Klasse
     * Bei einer vorkommenden Fehlermeldung,
     * wird eine String Nachricht ausgegeben
     */
    public MitarbeiterGibtEsSchonException(){
        super(" Dieser Mitarbeiter ist bereits vorhanden! ");
    }
}
