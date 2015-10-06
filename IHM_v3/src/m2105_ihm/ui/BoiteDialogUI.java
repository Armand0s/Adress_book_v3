/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package m2105_ihm.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import m2105_ihm.nf.Contact;
import m2105_ihm.nf.Evenement;
import m2105_ihm.nf.GroupeContacts;

/**
 *
 * @author laurillau
 */
public class BoiteDialogUI {
    /**
     * Boîte de dialogue pour confirmer la suppression d'un contact
     * @param c un contact
     * @return vrai si confirmé
     */
    public static boolean afficherConfirmation(JFrame fenetre, Contact c) {
        boolean res = false;

        if (c != null) {
            String [] choix = new String[] { "Supprimer", "Annuler" }; 
            
            Object selectedValue = JOptionPane.showOptionDialog(fenetre,
                  "Voulez-vous vraiment supprimer le contact : " 
                  + c.getPrenom() + " " + c.getNom() + " ?", 
                  "Suppression d'un contact",
                  JOptionPane.DEFAULT_OPTION,
                  JOptionPane.QUESTION_MESSAGE, 
                  null,
                  choix,
                  choix[1]);
            res = (((Integer) selectedValue) == 0);
        }
        
        return res;
    }

    /**
     * Boîte de dialogue pour confirmer la suppression d'un groupe de contacts
     * @param g un groupe de contacts
     * @return vrai si confirmé
     */    
    public static boolean afficherConfirmation(JFrame fenetre, GroupeContacts g) {
        boolean res = false;


        if (g != null) {
            String [] choix = new String[] { "Supprimer", "Annuler" }; 
            
            Object selectedValue = JOptionPane.showOptionDialog(fenetre,
                  "Voulez-vous vraiment supprimer le groupe : " 
                  + g.getNom() + " ?", 
                  "Suppression d'un groupe",
                  JOptionPane.DEFAULT_OPTION,
                  JOptionPane.QUESTION_MESSAGE, 
                  null,
                  choix,
                  choix[1]);
            res = (((Integer) selectedValue) == 0);
        }
        
        return res;
        
        
    }

    /**
     * Boîte de dialogue choisir un groupe auquel ajouter un contact
     * @param titre titre de la fenêtre
     * @param groupes liste des groupes existants
     * @return groupe choisi sinon valeur null
     */    
    public static GroupeContacts afficherChoixMembreContact(JFrame fenetre, String titre, GroupeContacts [] groupes) {
        GroupeContacts res = null;
        if (titre == null) { titre = ""; }
        
        
        JComboBox list_groupe = new javax.swing.JComboBox(new DefaultComboBoxModel<>(groupes));
        //String [] choix = new String[] { "Valider", "Annuler" };
        Object[] choix = new Object[] {(JComboBox) list_groupe, (String) "Valider", (String) "Annuler" };
            
            Object selectedValue = JOptionPane.showOptionDialog(fenetre,
                  "A quel groupe voulez vous ajouter ce contact ?", 
                  "Ajout d'un contact à un groupe",
                  JOptionPane.DEFAULT_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,
                  choix,
                  choix[2]);
            
            JComboBox jCTmp = (JComboBox) choix[0];
            
            res = (GroupeContacts) (jCTmp.getSelectedItem());
        
        return res;
    }
    
    
    public static Contact afficherChoixMembreContact(JFrame fenetre, String titre, Contact [] contacts) {
        Contact res = null;
        if (titre == null) { titre = ""; }
        
        
        JComboBox list_contacts = new javax.swing.JComboBox(new DefaultComboBoxModel<>(contacts));
        //String [] choix = new String[] { "Valider", "Annuler" };
        Object[] choix = new Object[] {(JComboBox) list_contacts, (String) "Valider", (String) "Annuler" };
            
            Object selectedValue = JOptionPane.showOptionDialog(fenetre,
                  "A quel Evenement voulez vous ajouter ce contact ?", 
                  "Ajout d'un contact à un groupe",
                  JOptionPane.DEFAULT_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,
                  choix,
                  choix[2]);
            
            JComboBox jCTmp = (JComboBox) choix[0];
            
            res = (Contact) (jCTmp.getSelectedItem());
        
        return res;
    }
} 


