/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm.nf;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author IUT2
 */
/**
 * 
 * @class GroupeContacts
 * Groupe de contacts
 */
public class GroupeContacts implements java.io.Serializable {
    
    /**
     * Attributs :
     * nom : nom du groupe
     * icone : liste de coordonnées d'une forme géométrique
     */
    private String nom;
    private ArrayList<Point> points;
    private ArrayList<Contact> contacts;    
    private ArrayList<Symbole> symboles;
    
    /**
     * Contructeur
     * @param id identifiant unique associé à un groupe
     */
    public GroupeContacts() {
        /*
         * Initialisation des champs
         */
        this.nom = "Nouveau groupe";
        this.points = new ArrayList<Point>();
        this.contacts = new ArrayList<Contact>();
        this.symboles = new ArrayList<Symbole>();        
    }
    
    /**
     * Retourne le nom du groupe
     * @return 
     */
    public String getNom() { return this.nom; }

    /**
     * Affecte le nom du groupe
     * @param nom
     * @return 
     */
    public boolean setNom(String nom) {
        boolean res = false;
        
        if (nom != null) { 
            this.nom = nom;
            res = true;
        }
        
        return res;
    }

    /**
     * Retourne les points définissant le dessin associé au groupe
     * @return tableau d'entiers
     */
    public Point [] getPoints() {
        return points.toArray(new Point[0]);
    }
     
    /**
     * Affecte le dessin associé au groupe
     */
    public void setPoints(Point [] p) {
        if (p != null) { 
            points.clear();
            for(int i = 0; i < p.length; i++) {
                if (p[i] != null) {
                    points.add(p[i]);
                }    
            }
        }
    }
    
    /**
     * Efface les points définissant l'icône
     */
    public void clearPoints() {
        points.clear();
    }
    
    /**
     * Indique si un contact est membre du groupe
     * @param c un contact
     * @return vrai si le contact est membre
     */
    public boolean isMembre(Contact c) {
        return ((c != null) && contacts.contains(c));
    }

    /**
     * Retourne la liste des contacts
     * @return tableau contenant les ID de chaque contact
     */
    public Contact [] getContacts() { 
        return contacts.toArray(new Contact[0]); 
    }    
        
    /**
     * Ajoute un contact dans le groupe
     * @param c classe identifiant un contact
     * @return vrai si le contact est objet non null et pas encore dans la liste
     */
    public boolean addContact(Contact c) {
        boolean res = false;
        
        if (c != null) {
           if (contacts.indexOf(c) == -1) {
               contacts.add(c);
               res = true;
           }
        }
        
        return res;        
    }
    
    /**
     * Retire un contact du groupe
     * @param c classe identifiant un contact
     * @return True si le contact est dans la liste
     */
    public boolean removeContact(Contact c) {
        boolean res = false;
        
        if (c != null) {
           int index = contacts.indexOf(c);
           
           if (index >= 0) {
               contacts.remove(index);
               res = true;
           }
        }
        
        return res;        
    }

    /**
     * Retourne la liste des symboles associés au groupe
     * @return tableau contenant la liste des symboles
     */
    public Symbole [] getSymboles() { 
        return symboles.toArray(new Symbole[0]); 
    }
    
    /**
     * Ajoute un symbole dans la liste
     * @param s un symbole
     * @return True si le symbole n'est pas encore dans la liste
     */
    public boolean addSymbole(Symbole s) {
        boolean res = false;
        
        if (s != null) {
           if (symboles.indexOf(s) == -1) {
               symboles.add(s);
               res = true;
           }
        }
        
        return res;        
    }
    
    /**
     * Retire un symbole de la liste
     * @param h un symbole
     * @return True si le symbole est dans la liste
     */
    public boolean removeSymbole(Symbole s) {
        boolean res = false;
        
        if (s != null) {
           int index = symboles.indexOf(s);
           
           if (index >= 0) {
               symboles.remove(index);
               res = true;
           }
        }
        
        return res;        
    }    
    
    /**
     * Retourne une forme textuelle d'un groupe de contacts
     * @return chaîne de caractères contenant le nom du groupe
     */
    public String toString() {
        return nom;
    }
}