/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm.ui;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import m2105_ihm.Controleur;
import m2105_ihm.nf.Contact;
import m2105_ihm.nf.GroupeContacts;

/**
 *
 * @author IUT2
 */
public class CarnetUI extends JPanel {
    
    /*
     * Composants de l'interface
     */
    private CardLayout             fiches;
    private JPanel                 cardPanel;    
    private ListeContacts          listeContacts;
    
    private FicheGroupeUI          ficheGroupe;    
    private FicheContactUI         ficheContact;
        
    protected Controleur           controleur;

    /**
     * Panel pour le carnet de contacts
     * @param controleur
     */
    public CarnetUI(Controleur controleur) {
        super();
        
        this.controleur = controleur;
        
        initUIComponents(); 
        setBackground(Color.red);
    }
    
    /**
     * Crée et positionne les composants graphiques constituant l'interface
     */
    private void initUIComponents() {

        /*
         * Liste de contacts
         */
        listeContacts = new ListeContacts(this);
        listeContacts.setBorder(BorderFactory.createTitledBorder("Contacts et groupes"));
          
        /* 
         * Ajout des fiches avec commutation grace au layout
         */
        
        JPanel vide = new JPanel();        
        ficheGroupe = new FicheGroupeUI(this);
        ficheContact = new FicheContactUI(this);

        fiches = new CardLayout();
        fiches.addLayoutComponent(ficheContact, "contact");
        fiches.addLayoutComponent(vide, "vide");
        fiches.addLayoutComponent(ficheGroupe, "groupe");
        
        cardPanel = new JPanel();
        cardPanel.setLayout(fiches);
        cardPanel.add(ficheContact, "contact");
        cardPanel.add(vide, "vide");
        cardPanel.add(ficheGroupe, "groupe");
        
        setLayout(new BorderLayout());
        add(listeContacts, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER); 
    }

    /**
     * Retourne le contact sélectionné
     * @return 
     */
    public Contact getSelectedContact() {
        return listeContacts.getSelectedContact();
    }

    /**
     * Retourne le groupe de contacts sélectionné
     */
    public GroupeContacts getSelectedGroupe() {
        return listeContacts.getSelectedGroupe();
    }
    
    /**
     * Ajoute une entrée dans le carnet pour les contacts
     * @param Contact objet contact associé
     */
    public boolean ajouterContact(Contact contact) {
        return listeContacts.ajouterContact(contact);
    }
    
    /**
     * Retire une entrée dans le carnet pour les contacts
     * @param Contact contact à retirer
     */    
    public boolean retirerContact(Contact contact) {
        return listeContacts.retirerContact(contact);
    }

    /**
     * Ajoute une entrée dans le carnet pour les groupes
     * @param title texte affiché dans l'arbre pour un groupe
     * @param GroupeContacts groupe de contacts associé
     */
    public boolean ajouterGroupe(GroupeContacts groupe) {
        return listeContacts.ajouterGroupe(groupe);
    }
    
    /**
     * Retire une entrée dans le carnet pour les groupe
     * @param GroupeContacts groupe que l'on veut retirer
     */    
    public boolean retirerGroupe(GroupeContacts groupe) {
        return listeContacts.retirerGroupe(groupe);
    }
    
    /**
     * 
     * @param modified 
     */
    public void setContactModified(boolean modified) {
        Contact c = listeContacts.getSelectedContact();

        if (modified) {
            ficheContact.getValues(c);
            listeContacts.updateEntry(c);
        } else {
            ficheContact.setValues(c);
        } 
    }
    
    /**
     * 
     * @param modified 
     */
    public void setGroupeModified(boolean modified) {
        GroupeContacts g = listeContacts.getSelectedGroupe();

        if (modified) {
            ficheGroupe.getValues(g);
            listeContacts.updateEntry(g);
        } else {
            ficheGroupe.setValues(g);
        } 
    }
    
    /**
     * Indique au carnet quel est l'item sélectionné : un contact ou un groupe
     */
    public void setSelectedItem(Object item) {
        if (item == null) {
            fiches.show(cardPanel,"vide");
        } else {
            if (item instanceof Contact) {
                controleur.setContactSelected(true);
                ficheContact.setValues((Contact) item); // affiche les données du contact                 
                fiches.show(cardPanel,"contact");                
            } else if (item instanceof GroupeContacts) {
                controleur.setContactSelected(false);
                fiches.show(cardPanel,"groupe");                                
                ficheGroupe.setValues((GroupeContacts) item); // affiche les données du groupe        
            }
        }
    }
    
    @Override
    public void show() {
        super.show();
        listeContacts.showAll();
        listeContacts.selectFirstContact();    
    }
}
