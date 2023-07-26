/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package Exception;

import java.rmi.RemoteException;

import KernKlassen.Artikel;

/**
 * Diese Klasse dient für eine Fehlermeldung,
 * die dann ausgegeben wird wenn ein Artikel schon bereits zum Warenkorb hinzugefügt wurde
 * und man versucht diesen Artikel erneut hinzuzufügen
 * Die Klasse erbt von der Exception Klasse 
 */
public class ArtikelIstBereitsImWarenkorbException extends Exception{

    /**
     * Das ist der Konstruktor der Klasse 
     * hier werden die Eigenschaften des Parameters "art" durch "super" als String ausgegeben
     * bei einer vorkommenden Fehlermeldung   
     * @param art
     * @throws RemoteException
     */
    public ArtikelIstBereitsImWarenkorbException(Artikel art) throws RemoteException{
        super(" Der Artikel: " + art.getBezeichnung() + " befindet sich bereits im Warenkorb ");
    }
    
}
