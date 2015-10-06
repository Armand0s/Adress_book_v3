/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm.nf;

/**
 *
 * @author IUT2
 */
public enum DispoSortie implements java.io.Serializable {
    SOIR("Le soir"),
    NUIT("La nuit"),
    WEEK_END("Le WE");
    
    private final String label;
    
    private DispoSortie(String label) { 
        this.label = label; 
    }
    
    public String toString() { return this.label; }
}