/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm.nf;

/**
 *
 * @author IUT2
 */

/*
 * Liste des regions
 */
public enum Region implements java.io.Serializable {
    ALSACE("Alsace"),
    AQUITAINE("Aquitaine"),
    AUVERGNE("Auvergne"),    
    BOURGOGNE("Bourgogne"),
    BRETAGNE("Bretagne"),
    CENTRE("Centre"), 
    CHAMPAGNE_ARDENNE("Champagne-Ardenne"),
    CORSE("Corse"),
    FRANCHE_COMTE("Franche-Comt\u00E9"),
    GUADELOUPE("Guadeloupe"),
    GUYANE("Guyane"),
    ILE_DE_FRANCE("\u00CEle-de-France"),
    LANGUEDOC_ROUSSILON("Languedoc-Roussilon"),    
    LIMOUSIN("Limousin"),
    LORRAINE("Lorraine"),    
    MARTINIQUE("Martinique"),
    MAYOTTE("Mayotte"),
    MIDI_PYRENEES("Midi-Pyr\u00E9n\u00E9es"),    
    NORD_PAS_DE_CALAIS("Nord-Pas-de-Calais"),    
    BASSE_NORMANDIE("Basse-Normandie"),
    HAUTE_NORMANDIE("Haute-Normandie"),
    PAYS_LOIRE("Pays de la Loire"),
    PICARDIE("Picardie"),
    POITOU_CHARENTES("Poitou-Charentes"),    
    PACA("Provence-Alpes-C\u00F4te d'Azur"),    
    REUNION("La R\u00E9union"),
    RHONE_ALPES("Rh\u00F4nes-Alpes");
    
    private final String label;
    
    private Region(String label) { 
        this.label = label; 
    }
    
    public String toString() { return this.label; }        
}