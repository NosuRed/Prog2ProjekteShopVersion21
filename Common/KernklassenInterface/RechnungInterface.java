package KernklassenInterface;

import java.rmi.Remote;

public interface RechnungInterface extends Remote {

    /**
     * gibt den Datensatz für eine Rechnung aus
     * dazu zählen der Kunde(käufer), datum, Warenkorb und der Gesamtpreis der Artikel
     */
}