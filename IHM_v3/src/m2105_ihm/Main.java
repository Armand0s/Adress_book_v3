/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm;

/**
 *
 * @author IUT2
 */
public class Main implements Runnable {

    private Controleur controleur;
    
    public void run() {
        controleur = new Controleur();
    }
    
    public static void main(String[] args) {        
        java.awt.EventQueue.invokeLater(new Main());
    }
}
