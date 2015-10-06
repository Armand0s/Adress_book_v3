/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm;

import m2105_ihm.nf.Contact;
import m2105_ihm.nf.GroupeContacts;
import m2105_ihm.nf.NoyauFonctionnel;
import m2105_ihm.nf.Evenement;
import m2105_ihm.nf.Mois;
import m2105_ihm.nf.Region;

import m2105_ihm.ui.CarnetUI;
import m2105_ihm.ui.FenetreUI;
import m2105_ihm.ui.PlanningUI;
import m2105_ihm.ui.BoiteDialogUI;
import m2105_ihm.ui.DialogCreerEvtUI;
import java.io.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IUT2
 */
public class Controleur {
    
    /*
     * Noyau Fonctionnel
     */    
    NoyauFonctionnel nf;
            
    /*
     * Composants
     */
    private CarnetUI carnetUI;
    private FenetreUI fenetre;
    private PlanningUI planningUI;
    private BoiteDialogUI boiteUI;
    private DialogCreerEvtUI boiteEvtUI;

    /**
     * Constructeur de la fenêtre principale
     */
    public Controleur() {
        initUI();
        initContent();
        planningUI.setValuesList(planningUI.getCurrentEvt());
    }
    
    /**
     * Action créer un nouveau contact
     */
    public void creerContact() {
        
        Contact c = new Contact();
        
        c.setDateNaissance(1, Mois.JANVIER, 1900);
        c.setNom("Nouveau");
        c.setPrenom("Contact");
        c.setNumeroTelephone("");
        c.setEmail("");
        c.setRegion(Region.ALSACE);
        carnetUI.ajouterContact(c);
        nf.addContact(c);
        
    }

    /**
     * Action supprimer contact
     */
    public void supprimerContact() {
        
        
        if (boiteUI.afficherConfirmation(fenetre,carnetUI.getSelectedContact())) {
        nf.removeContact(carnetUI.getSelectedContact());
        carnetUI.retirerContact(carnetUI.getSelectedContact());
        
        }
    }
    
    /**
     * Action créer un groupe de contacts
     */
    public void creerGroupe() {
        
        GroupeContacts g = new GroupeContacts();
        
        g.setNom("Nouveau Groupe");
        
        carnetUI.ajouterGroupe(g);
        nf.addGroupe(g);
    }

    /**
     * Action supprimer un groupe de contacts
     */
    public void supprimerGroupe() {
        
        
        
        if (boiteUI.afficherConfirmation(fenetre,carnetUI.getSelectedGroupe())) {
        nf.removeGroupe(carnetUI.getSelectedGroupe());
        carnetUI.retirerGroupe(carnetUI.getSelectedGroupe());
        
        }
    }
    
    /**
     * Crée un nouvel événement
     */
    public void creerEvenement(Evenement evt) {
        
        System.out.println("T " + evt.toString());
        if (evt != null) {
            nf.addEvenement(evt);
            planningUI.setCurrentEvt(evt);
            planningUI.setValuesList(planningUI.getCurrentEvt());
        }
        
    }

    /**
     * Supprime un événement existant
     */
    public void supprimerEvenement() {
       
        /*if ((planningUI.getCurrentEvt().getIntitule() != "No Evenement Selected")
                && (planningUI.getCurrentEvt().getDateJour() != planningUI.calendar_currentDate.get(Calendar.DAY_OF_MONTH))
                && (planningUI.getCurrentEvt().getDateMois() != planningUI.intToMonths(planningUI.calendar_currentDate.get(Calendar.DAY_OF_MONTH)))
                && (planningUI.getCurrentEvt().getDateAnnee() != planningUI.calendar_currentDate.get(Calendar.YEAR))) {*/
       nf.removeEvenement(planningUI.getCurrentEvt());
       planningUI.retirerEvt(planningUI.getCurrentEvt());
       planningUI.setCurrentEvt(planningUI.getDefaultEvt());
       planningUI.uncheckDelete();
       planningUI.setValuesList(planningUI.getCurrentEvt());
       //Bug graphique
       
        //}
    }
    
    /**
     * Ajouter un participant à un événement
     */
    public void ajouterParticipantEvenement() {
    
       // Fait directement dans le planning
           
    }

    /**
     * Retire un participant d'un événement
     */
    public boolean retirerParticipantEvenement(Contact c) {
    
       if (boiteUI.afficherConfirmation(fenetre,c)) {

        planningUI.getCurrentEvt().removeParticipant(c);
        return true;
        } else {
           return false;
       }
           
    }
    
    public void ajouterContactGroupe() {
        boiteUI.afficherChoixMembreContact(fenetre,null,nf.getGroupes()).addContact(carnetUI.getSelectedContact());
        
    }

    /**
     * Met à jour la base de données
     */
    public void enregistrer() {
        nf.updateDB();
        
    }    
        
    /**
     * Quitter l'application sans enregistrer les modifications
     */
    public void quitter() {
        System.exit(0);
    }

    /**
     * Création des composants constituant la fenêtre principale
     */
    private void initUI() {
        /* Onglets */
        carnetUI = new CarnetUI(this);
        planningUI = new PlanningUI(this);

        /* Fenêtre principale */
        fenetre = new FenetreUI(this);
        fenetre.addTab(carnetUI, "Carnet");     // onglet carnet
        fenetre.addTab(planningUI, "Planning"); // onglet planning
        
        fenetre.afficher();
    }
        
    /**
     * Alimente la liste avec la liste des contacts existants
     */
    private void initContent() {
        nf = new NoyauFonctionnel();
                
        for(Contact c : nf.getContacts()) {
            carnetUI.ajouterContact(c);
        }
        
        for(GroupeContacts g : nf.getGroupes()) {
            carnetUI.ajouterGroupe(g);
        }
        
        for(Evenement e : nf.getEvenements()) {
            planningUI.ajouterEvt(e);
        }
        
        carnetUI.show();
    }
    
    public void setContactSelected(boolean selected) {
        fenetre.setMenuContactSelected(selected);
        
    }
    
    public void setEvtSelected(boolean selected) {
        fenetre.setMenuEvenementSelected(selected);
    }

    public FenetreUI getFenetre() {
        return fenetre;
    }

    public PlanningUI getPlanningUI() {
        return planningUI;
    }

    public CarnetUI getCarnetUI() {
        return carnetUI;
    }

    public BoiteDialogUI getBoiteUI() {
        return boiteUI;
    }

    public NoyauFonctionnel getNf() {
        return nf;
    }
    
    
    
    
    
}