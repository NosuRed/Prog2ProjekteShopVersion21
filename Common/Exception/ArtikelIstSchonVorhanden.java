/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Exception;

/**
 * Diese Klasse dient für eine Fehlermeldung,
 * die wird dann ausgegeben wenn ein Artikel schon bereits zur Artikelliste hinzugefügt wurde
 * und man versucht sie erneut hinzuzufügen
 * Die Klasse erbt von der Exception Klasse
 */
public class ArtikelIstSchonVorhanden extends Exception{

    /**
     * Das ist der Konstruktor der Klasse 
     * Bei einer vorkommenden Fehlermeldung,
     * wird eine String Nachricht ausgegeben
     */
    public ArtikelIstSchonVorhanden(){
        super(" Dieser Artikel existiert bereits, geben sie einen anderen Artikel ein ");
    }
}
