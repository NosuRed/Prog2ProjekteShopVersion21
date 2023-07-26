import java.io.IOException;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Anwendungslogikklassen.eShop;
import Exception.ArtikelIstSchonVorhanden;
import Exception.KundeGibtEsSchonException;
import Exception.MitarbeiterGibtEsSchonException;
import KernklassenInterface.eShopInterface;

/**
 * Serverseitige Anwendung, die den RMI-eShop bei der rmiregistry
 * registriert
 * und somit für Clients verfügbar macht.
 * 
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 */
public class eShopServer {
	/**
	 * Konstruktor zur Erzeugung des eShop-Servers.
	 * Der Konstruktor stellt eine Instanz des eShops über RMI bereit.
	 */
	public eShopServer() throws ArtikelIstSchonVorhanden, MitarbeiterGibtEsSchonException, IOException, KundeGibtEsSchonException {
		eShopInterface eS = new eShop("eShop"); // Variante mit Remote-Adressobjekten
		String serviceName = "eShop";
		Registry registry = null;

		try {
			// Dienst-Objekt erzeugen
			eS = new eShop(serviceName); // Variante mit Remote-Adressobjekten
			// Läuft schon eine Registry?
			// Registry-Objekt holen...
			registry = LocateRegistry.getRegistry();
			// unseren eShop-Service registrieren:
			registry.rebind(serviceName, eS);
			System.out.println("Lokales Registry-Objekt gefunden.");
			System.out.println("eShop-Server läuft...");
		} catch (ConnectException | RemoteException ce) {
			
			System.out.println("Registry läuft nicht.");

			try {
				// Dann versuchen, selber eine Registry zu starten:
				registry = LocateRegistry.createRegistry(1099);
				System.out.println("Registry erzeugt.");
				registry.rebind(serviceName, eS);
				System.out.println("eShop-Server läuft...");

			} catch (RemoteException e) {
				System.out.println(e.getMessage());
				// tritt z.B. auf, wenn Stub-Klasse nicht vorhanden ist
				e.printStackTrace();
			}
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			// tritt z.B. auf, wenn Stub-Klasse nicht vorhanden ist
			e1.printStackTrace();
		} catch (MitarbeiterGibtEsSchonException | KundeGibtEsSchonException
				| ArtikelIstSchonVorhanden e) {
			// Wird vom Client gehandelt
		}
	}

	/**
	 * main()-Methode zum Starten des Servers
	 */
	public static void main(String[] args) throws ArtikelIstSchonVorhanden, MitarbeiterGibtEsSchonException, IOException, KundeGibtEsSchonException {
		new eShopServer();
	}
}
