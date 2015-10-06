/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import m2105_ihm.nf.Contact;
import m2105_ihm.nf.GroupeContacts;
import m2105_ihm.nf.Symbole;

/**
 *
 * @author IUT2
 */
public class FicheGroupeUI extends javax.swing.JPanel {
    /*
     * Composants graphiques constituants l'interface
     */
    
    
    
    private CarnetUI     carnet;
    private ZoneDessinUI zoneDessin;
    
    
    private String[]     colonnes = {"Nom","Prenom","Telephone"};
    private JTextField   txNomG;
    private JLabel       labNomG;
    
    private JTable       tabContact;
    private DefaultTableModel model = new DefaultTableModel(); 
    
    private ButtonGroup  symboleGroup;
    private HashMap<Symbole, JRadioButton> checkboxes = new HashMap();
    
    public JButton       btEffacer;
    public JButton       btValider;
    
    
    private JPanel      panelLogo;
    private JPanel      panelInfo;
    private JPanel      panelContact;
    private JPanel      panelSymboleGlobal;
    private JPanel      panelSymbole1;
    private JPanel      panelSymbole2;
    private JPanel      panelBouton;

    
    /**
     * Creates new form CarnetUI
     */
    public FicheGroupeUI(CarnetUI carnet) { 
        super();
       
        this.carnet = carnet;
        
        initUIComponents();    
        initListeners();
    }

    /**
     * Crée et positionne les composants graphiques constituant l'interface
     */
    private void initUIComponents() {
        panelLogo = new JPanel();
        panelInfo = new JPanel();
        panelContact = new JPanel();
        panelSymboleGlobal = new JPanel();
        panelSymbole1 = new JPanel();
        panelSymbole2 = new JPanel();
        panelBouton = new JPanel();
        
        panelLogo.setLayout(new GridBagLayout());
        panelInfo.setLayout(new GridBagLayout());
        panelContact.setLayout(new GridBagLayout());
        panelSymboleGlobal.setLayout(new GridBagLayout());
        panelSymbole1.setLayout(new GridBagLayout());
        panelSymbole2.setLayout(new GridBagLayout());
        panelBouton.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.ipady=5;
        c.gridx=1;
        
        //////////////////////////////////////////
        /////////////// PANEL LOGO ///////////////
        //////////////////////////////////////////
        zoneDessin = new ZoneDessinUI();
        panelLogo.add(zoneDessin,c);
        

        //////////////////////////////////////////
        ////////// PANEL INFO NOM GROUPE /////////
        //////////////////////////////////////////
        labNomG = new JLabel("Nom du groupe");
        txNomG = new JTextField(20);
        panelInfo.add(labNomG,c);
        panelInfo.add(txNomG,c);
        
        
        //////////////////////////////////////////
        ///////////// PANEL TABLE ////////////////
        //////////////////////////////////////////
        model = new DefaultTableModel(); 
        model.setColumnIdentifiers(colonnes);
        tabContact = new JTable(model);
        panelContact.add(tabContact.getTableHeader(),c);
        panelContact.add(tabContact,c);
        
        
        //////////////////////////////////////////
        ///////////// PANEL SYMBOLE //////////////
        //////////////////////////////////////////
        symboleGroup = new ButtonGroup();
        int indice = 0;
        for (Symbole s : Symbole.values()){
            checkboxes.put(s, new JRadioButton(s.toString()));
            symboleGroup.add(checkboxes.get(s));
            if (indice < Symbole.values().length/2) {
                c.gridy=indice;
                panelSymbole1.add(checkboxes.get(s), c);
            } else {
                c.gridy=indice - Symbole.values().length/2;
                panelSymbole2.add(checkboxes.get(s), c);
            }
            indice++;
        }
        
        panelSymboleGlobal.add(panelSymbole1);
        panelSymboleGlobal.add(panelSymbole2);
        
        
        //////////////////////////////////////////
        ////////////// PANEL BOUTON //////////////
        //////////////////////////////////////////
        btValider = new JButton("Valider");
        btEffacer = new JButton("Effacer");
        panelBouton.add(btValider);
        panelBouton.add(btEffacer);
        

        c.gridy=1;
        c.gridx=1;
        this.add(panelLogo,c);
        c.gridy=2;
        this.add(panelInfo,c);
        c.gridy=3;
        this.add(panelContact,c);
        c.gridy=4;
        this.add(panelSymboleGlobal,c);
        c.gridy=5;
        c.ipady=2;
        this.add(panelBouton,c);
        
        
        
/*        this.add(labNomG);
        this.add(txNomG);
        this.add(tabContact.getTableHeader());
        this.add(tabContact);


        

        
        

        this.add(btValider);
        this.add(btEffacer);*/
        }

    /**
     * Affecte des valeurs au formulaire de fiche groupe de contacts
     * @param groupe groupe de contacts
     * @return
    */    
    public boolean setValues(GroupeContacts groupe) {
        if (groupe == null) {return false; }
        
        /** TP 2 : à compléter **/
        /* JTable */
        String [] obj = new String[3];
       
        model.setRowCount(0);
        for (Contact contact : groupe.getContacts()) {
            obj[0]=contact.getNom();
            obj[1]=contact.getPrenom();
            obj[2]=contact.getNumeroTelephone();
            
            model.addRow(obj);
        }
        
        txNomG.setText(groupe.getNom());
        
        /* Symbole */
        ButtonModel selection = symboleGroup.getSelection();
        for (Symbole s : Symbole.values()){
            if (Arrays.asList(groupe.getSymboles()).contains(s)){
                checkboxes.get(s).setSelected(true);
            } else {
                checkboxes.get(s).setSelected(false);
            }
            
        }
        
        return true;
    }
    
    /**
     * Retourne les valeurs associées au formulaire de fiche groupe de contacts
     * @return
     */    
    public boolean getValues(GroupeContacts groupe) {
        if (groupe == null) { return false; }
        
        /** TP 2 : à compléter **/
        
        /*
         * Ne pas s'occuper des membres du groupe car traité via des
         * commandes du menu qui appelera setValues
         */
        
        groupe.setNom(txNomG.getText());
        
        
        
        return true;
    }
    
    /**
     * Initialise la gestion des événements
     */
    public void initListeners() {        
        /*
         * Réagit aux évènements produits par le bouton effacer
         */
         btEffacer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carnet.setGroupeModified(false);
            }
        });
        btValider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carnet.setGroupeModified(true);
            }
            
            
        });
    }    
    
}
