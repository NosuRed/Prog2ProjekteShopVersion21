/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package GUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import Exception.ArtikelIstSchonVorhanden;
import Exception.KundeGibtEsSchonException;
import Exception.MitarbeiterGibtEsSchonException;
import GUI.Panels.enterEshopPanel;
import KernklassenInterface.eShopInterface;

/**
 * dient für das ausführen unseres eShops und stellt diese als Layout dar
 * und erbt von der JFrame Klasse
 */
public class eShopGui extends JFrame {

    private static int DEFAULT_PORT = 1099;
    /**
     * Die Eigenschaften der Klasse
     * die deklariert werden
     */
    private eShopInterface eS;
    private enterEshopPanel enEshopPanel;

    /**
     * Der Konstruktor dieser Klasse
     * hier wird unser eShop initialisiert und das erste Fenster unseres eShops wird
     * geöffnet
     * Fehlermeldungen werden als Fenster eingeblendet
     * 
     * @param titel
     * @throws KundeGibtEsSchonException
     * @throws MitarbeiterGibtEsSchonException
     * @throws ArtikelIstSchonVorhanden
     */
    public eShopGui(eShopInterface eS)
            throws KundeGibtEsSchonException, MitarbeiterGibtEsSchonException, ArtikelIstSchonVorhanden {
        enEshopPanel = new enterEshopPanel(eS, "Der eShop");
    }

    /**
     * Die Main Methode dieser Klasse
     * es wird die eShop Gui ausführen
     * Fehlermeldungen werden als Fenster eingeblendet
     * 
     * @param args
     */
    public static void main(String[] args) {
        String serviceName = "eShop";
        String host = "localhost";
        int port = DEFAULT_PORT;

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Registry registry = LocateRegistry.getRegistry(host, port);
                    eShopInterface eS = (eShopInterface) registry.lookup(serviceName);
                    eShopGui esg = new eShopGui(eS);
                } catch (KundeGibtEsSchonException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (MitarbeiterGibtEsSchonException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (ArtikelIstSchonVorhanden e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (RemoteException e) {
                    JOptionPane.showMessageDialog(null, "Bitte versuchen sie es später!\n" + e.getMessage(),
                            "Server wurde nicht gefunden", JOptionPane.ERROR_MESSAGE);
                } catch (NotBoundException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });
    }

}
