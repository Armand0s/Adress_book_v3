/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm.nf;

import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 *
 * @author IUT2
 */
/**
 * 
 * @class NoyauFonctionnel
 * Gestion du carnet de contact et de la planification d'evenements
 */
public class NoyauFonctionnel {
    private static final String DB_FILE = "carnet.db";
    
    private ArrayList<Contact> contacts;
    private ArrayList<GroupeContacts> groupes;
    private LinkedList<Evenement> evenements;

    private String path = "";

    /**
     * Initialise la base du carnet
     */
    public NoyauFonctionnel() {
        if (! loadDB()) { newDB(); }
    }
    
    /**
     * Initialise la base du carnet
     */
    public NoyauFonctionnel(String path) {
        
        if (path != null) { this.path = path; }
        
        if (! loadDB()) { newDB(); }
    }
    
    /**
     * Retourne la liste des contacts
     * @return 
     */
    public Contact [] getContacts() {
        return contacts.toArray(new Contact[0]);
    }
    
    /**
     * Ajoute un contact
     * @param c un contact
     * @return 
     */            
    public boolean addContact(Contact c) {
        boolean success = false;
        
        if ((c != null) && (contacts.indexOf(c) == -1)) {
            contacts.add(c);
            success = true;
        }
        
        return success;
    }
    
    /**
     * Retire un contact
     * @param c un contact
     * @return 
     */    
    public boolean removeContact(Contact c) {
        boolean success = true;

        if ((c != null) && (contacts.indexOf(c) != -1)) {
            for(GroupeContacts g : groupes) {
                g.removeContact(c);
            }

            for(Evenement e : evenements) {
                e.removeParticipant(c);
            }

            contacts.remove(c);
            
            success = true;            
        }
        
        return success;        
    }

    /**
     * Retourne la liste des groupes de contacts
     * @return 
     */
    public GroupeContacts [] getGroupes() {
        return groupes.toArray(new GroupeContacts[0]);
    }
        
    /**
     * Ajoute un groupe
     * @param g un groupe de contacts
     * @return 
     */
    public boolean addGroupe(GroupeContacts g) {
        boolean success = true;

        if ((g != null) && (groupes.indexOf(g) == -1)) {
            groupes.add(g);
            success = true;
        }
                
        return success;               
    }
    
    /**
     * Retire un contact
     * @param g un groupe de contacts
     * @return 
     */
    public boolean removeGroupe(GroupeContacts g) {
        boolean success = true;

        if ((g != null) && (groupes.indexOf(g) != -1)) {
            groupes.remove(g);
            success = true; 
        }
        
        return success;               
    }

    /**
     * Retourne la liste des groupes associés à un contact
     * @param g un groupe
     * @return un tableau de groupes de contacts
     */
    public GroupeContacts [] getGroupesContact(Contact c) {
        GroupeContacts [] res;
        
        
        if (c != null) {
            ArrayList<GroupeContacts> liste;
            
            liste = new ArrayList<GroupeContacts>();
            for(GroupeContacts g : groupes) {
                if (g.isMembre(c)) { liste.add(g); }
            }
            
            res = liste.toArray(new GroupeContacts[0]);
        } else {
            res = new GroupeContacts[0];
        }
            
        return res;
    }

    /**
     * Retourne la liste des evenements
     * @return 
     */
    public Evenement [] getEvenements() {
        return evenements.toArray(new Evenement[0]);
    }
        
    /**
     * Ajoute un évènement à la liste
     * @param e un évènement
     * @return 
     */
    public boolean addEvenement(Evenement e) {
        boolean success = false;

        if ((e != null) && (evenements.indexOf(e) == -1)) {
            evenements.add(e);
            trieEvenement();
            for (int j = 0; j< evenements.size(); j++) {
                System.out.println("Trie : " + evenements.get(j).toString());
            }
            System.out.println("");
            /*for (Evenement evt : evenements) {
                System.out.println("Trie : " + evt.toString());
            }
            */
            success = true;
        }
        
        return success;               
    }
    
    public boolean trieEvenement() {
        boolean sucess = false;
        
        if (evenements.size() >=2) {
            
            


            for(int i = 0; i< evenements.size()-1; i++) {
                if (!evtAvant(evenements.get(i),evenements.get(i+1))) {
                    evenements.add(i, evenements.get(i+1));
                    evenements.remove(i+2);
                    i = -1;
                }
            }
        }
        
        
        return sucess;
    }
    
    public boolean evtAvant(Evenement evt1, Evenement evt2) { // verifie que evt1 est avant evt2
        //System.out.println(evt1.getDateMois().compareTo(evt2.getDateMois()));
        if ((evt1.getDateAnnee() < evt2.getDateAnnee())  
                || ((evt1.getDateAnnee() == evt2.getDateAnnee())
                    && (evt1.getDateMois().compareTo(evt2.getDateMois()) < 0))
                || (((evt1.getDateAnnee() == evt2.getDateAnnee())
                    && (evt1.getDateMois().compareTo(evt2.getDateMois()) == 0))
                        &&  evt1.getDateJour() < evt2.getDateJour())
                ) {
            System.out.println(evt1.getIntitule() + " est avant " + evt2.getIntitule());
            return true;
        } else {
            System.out.println(evt2.getIntitule() + " est avant " + evt1.getIntitule());
        return false;
        }
    }
    
    
    /**
     * Retire un évènement de la liste
     * @param e un évènement
     * @return 
     */
    public boolean removeEvenement(Evenement e) {
        boolean success = false;

        if ((e != null) && (evenements.indexOf(e) != -1)) {
            evenements.remove(e);
            success = true; 
        }
        
        return success;               
    }
    
    /**
     * Met à jour la base de données persistante
     * @return 
     */
    public boolean updateDB() {
        
        return saveDB();
    }

    /**
     * Créaction d'une nouvelle base de données
     */
    private void newDB() {
        contacts = new ArrayList<Contact>();
        groupes = new ArrayList<GroupeContacts>();
        evenements = new LinkedList<Evenement>();
    }
    
   /**
    * Sauvegarde de la base d'un fichier
    * @return 
    */
    private boolean saveDB() {
        File file;
        boolean success = true;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;            
        
        file = new File(path + DB_FILE);
        try {
            
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            
            oos.writeObject(contacts);
            oos.writeObject(groupes);
            oos.writeObject(evenements);
            
        }
        catch (Exception e) {
            System.out.println("SAVE" + e);
            success = false;
        }
        finally {
                if (oos != null) { 
                    try { oos.close(); }
                    catch(IOException e) {}
                }
                
                if (fos != null) { 
                    try { fos.close(); }
                    catch(IOException e) {}
                }
            
        }
        
        return success;
    }
    
    /**
     * Chargement d'une base de données à partir d'un fichier
     * @return 
     */
    private boolean loadDB() {
        boolean success = true;
        File file = new File(path + DB_FILE);
        
        if (file.exists()) {
            FileInputStream fis = null;
            ObjectInputStream ois = null;            

            try {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                
                contacts = (ArrayList<Contact>) ois.readObject();
                groupes = (ArrayList<GroupeContacts>) ois.readObject();
                evenements = (LinkedList<Evenement>) ois.readObject();
            }             
            catch(Exception e) {
                System.out.println("LOAD" + e);
                success = false;
            }
            finally {
                if (ois != null) { 
                    try { ois.close(); }
                    catch(IOException e) {}
                }
                
                if (fis != null) { 
                    try { fis.close(); }
                    catch(IOException e) {}
                }
            }
        } else { success = false; }
        
        return success;
    }
}
