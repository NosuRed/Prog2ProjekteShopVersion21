/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Exception;

/**
 * Diese Klasse dient für eine Fehlermeldung,
 * wenn der Nutzer sich mit einem falschen Benutzernamen oder Passwort versucht anzumelden
 * Die Klasse erbt von der Exception Klasse
 */
public class FalscheAnmeldeDatenException extends Exception{
    
    /**
     * Das ist der Konstruktor der Klasse
     * Bei einer vorkommenden Fehlermeldung,
     * wird eine String Nachricht ausgegeben
     */
    public FalscheAnmeldeDatenException(){
        super("Der Benutzername oder das Passwort ist falsch!");
    }
}
