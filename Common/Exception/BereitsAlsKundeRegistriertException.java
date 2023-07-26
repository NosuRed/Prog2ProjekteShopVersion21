/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Exception;

/**
 * Wenn ein Nutzer sich als Kunde registrieren will der schon vorhanden ist
 * dann wird ein Fehler ausgegeben
 * Die Klasse erbt von der Exception Klasse
 */
public class BereitsAlsKundeRegistriertException extends Exception{
    
    /**
     * Das ist der Konstruktor der Klasse
     * Bei einer vorkommenden Fehlermeldung,
     * wird eine String Nachricht ausgegeben
     */
    public BereitsAlsKundeRegistriertException(){
        super(" Der Kunde existiert bereits! ");
    }
}
