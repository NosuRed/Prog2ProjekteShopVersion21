/**
 * @author Tobias Kalusche, Raphael Körner, Philipp Behrens
 * Importierte Klassen und Packages, zu denen eine Beziehung benötigt wird
 */
package KernKlassen;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import KernklassenInterface.NutzerInterface;

/**
 * Dient als superklasse für die Unterklassen Kunde und Mitarbeiter
 * mit bestimmten Eigenschaften die initialisiert werden
 */
public abstract class Nutzer extends UnicastRemoteObject implements NutzerInterface {

    protected Nutzer() throws RemoteException {
        super();
    }

    /**
     * Die Eigenschaften fuer die Superklasse,
     * die an die Unterklassen weitergegeben werden
     */
    protected String name;
    protected int nummer;
    protected String benutzername;
    protected String passwort;

    // gibt den Nutzernamen zurueck
    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    // gibt den Benutzernamen des Nutzer zurueck
    @Override
    public String getBenutzerName() throws RemoteException {
        return this.benutzername;
    }

    // gibt die Nutzernummer zurueck
    @Override
    public int getNummer() throws RemoteException {
        return this.nummer;
    }

    // gibt das Passwort des Nutzers zurueck
    @Override
    public String getPasswd() throws RemoteException {
        return this.passwort;
    }

    /**
     * Es werden hier die Eigenschaften eines Nutzer(Kunde oder Mitarbeiter)
     * mit den Eigenschaften eines anderen Nutzer(Kunde oder Mitarbeiter)
     * verglichen,
     * wegen einer übereinstimmung(die Equals Methode wird hier überschrieben)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
		    return true;
		}
		if (obj == null) {
		    return false;
		}
		if (getClass() != obj.getClass()) {
		    return false;
		}
		Nutzer other = (Nutzer) obj;
		if (benutzername == null) {
		    if (other.benutzername != null) {
		        return false;
		    }
		} else if (!benutzername.equals(other.benutzername)) {
		    return false;
		}

		return true;

        /*
         * if (name == null) {
         * if (other.name != null)
         * return false;
         * } else if (!name.equals(other.name))
         * return false;
         * if (nummer != other.nummer){
         * return false;
         * }
         * 
         * if (passwd == null) {
         * if (other.passwd != null)
         * return false;
         * } else if (!passwd.equals(other.passwd)){
         * return false;
         * }
         * 
         * return true;
         */
    }

    /**
     * gibt den Datensatz fuer den Nutzer aus
     * dazu zählt Nutzername, Nutzernummer, Nutzerbenutzername und Nutzerpasswort
     */
    @Override
    public String toString() { 
        return "Name=" + name + ", Nummer=" + nummer + ", Benutzername=" + benutzername + ", Passwort=" + passwort;
    }

}
