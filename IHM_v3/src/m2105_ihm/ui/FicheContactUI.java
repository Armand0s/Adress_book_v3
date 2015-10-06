/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm.ui;

import java.awt.*;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import m2105_ihm.nf.*;

/**
 *
 * @author IUT2
 */
public class FicheContactUI extends JPanel {

    private CarnetUI         carnet;
    private JPanel           panelinfo;
    
    private JPanel           panelnom;
    private JPanel           panelprenom;
    
    private Integer[]        jours;
    private JPanel           paneldate;
    private JPanel           paneldateLabel;
    private JPanel           paneldatechamp;
    
    
    private JPanel           panelperi;
    
    private JPanel           paneltel;
    private JPanel           panelmail;
    private JPanel           panelregion;
    private JPanel           panelhobby;
    private JPanel           paneldispo;
    
    
    private JComboBox        list_jour;
    private JComboBox        list_mois;
    private JComboBox        list_annee;
    private JComboBox        list_reg;

    
    private JLabel           labelNom;
    private JLabel           labelPre;
    private JLabel           labelTel;
    private JLabel           labelE_M;
    private JLabel           labelReg;
    
    private JTextField       champNom;
    private JTextField       champPre;
    private JTextField       champTel;
    private JTextField       champE_M;
    private JTextField       champReg;
    private Dimension        dim1,dim2,dim3;
    
    
    private JCheckBox        chCine;
    private JCheckBox        chLect;
    private JCheckBox        chMusi;
    private JCheckBox        chSpor;
    private HashMap<Hobby,JCheckBox> hashHobby;
    
    private JButton          buttonValider;
    private JButton          buttonAnnuler;
    
    
    public Integer[] limJour(){
        Integer[] j = new Integer[31];
        for(int i=0; i<31; i++){
            j[i]=i+1;
        }
        return j;
    }
    /**
     * Formulaire pour saisir les informations relatives à un contact
     */
    public FicheContactUI(CarnetUI carnet) {
        super();
        
        
        
        
        this.carnet      = carnet;
              
        initUIComponents();
        initListeners();
    }
    
    /**
     * Crée et positionne les composants graphiques constituant l'interface
     */
    private void initUIComponents() {      
        /** Pour l'exemple **/
        
        /*
         * Ajoute dans l'IHM un champ pour la saisie/Affichage du nom
         */  
        panelinfo = new JPanel();
        paneldate = new JPanel();
        paneldateLabel = new JPanel();
        paneldatechamp = new JPanel();
        panelperi = new JPanel();
        
        panelinfo.setLayout(new GridBagLayout());
        paneldate.setLayout(new BorderLayout());
        panelperi.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        //c.weightx=1;
        c.ipady=5;
        c.gridx=1;
        
        
        //////////////////////////////////////////
        ////////// PANEL INFO NOM PRENOM /////////
        //////////////////////////////////////////
        

        labelNom = new JLabel("Nom");
        champNom = new JTextField(30);
        
        
        labelPre = new JLabel("Prénom");
        champPre = new JTextField(30);
        
        panelinfo.add(labelNom,c);
        panelinfo.add(champNom,c);
        panelinfo.add(labelPre,c);
        panelinfo.add(champPre,c);
        
        /////////////////////
        //// PANEL DATE /////
        /////////////////////
        
        jours = limJour();
        list_jour = new JComboBox(jours);
        
       
        Mois[] mois = Mois.values();
        
        list_mois = new JComboBox(mois);
        
        
       String[] annee = new String[150];
       
       for (int i=1865;i<2015;i++) {
           annee[i-1865] = Integer.toString(i);
       }
       list_annee = new JComboBox(annee);
       
       paneldateLabel.add(new JLabel("Jour"),BorderLayout.EAST);
       paneldateLabel.add(new JLabel("Mois"),BorderLayout.CENTER);
       paneldateLabel.add(new JLabel("Annee"),BorderLayout.WEST);
       
       paneldatechamp.add(list_jour,BorderLayout.EAST);
       paneldatechamp.add(list_mois,BorderLayout.CENTER);
       paneldatechamp.add(list_annee,BorderLayout.WEST);
       
       
       
        
        ////////////////////////////////////
        //////// PANEL PERIPHERIQUE ////////
        ////////////////////////////////////
        
        
        champTel = new JTextField(30);
        
        
        champE_M = new JTextField(30);
        
        
        Region[] reg = Region.values();
        list_reg = new JComboBox(reg);
        
       
        
        
        panelperi.add(new JLabel("Telephone"),c);
        panelperi.add(champTel,c);
        
        panelperi.add(new JLabel("E_Mail"),c);
        panelperi.add(champE_M,c);
        
        panelperi.add(new JLabel("Region"),c);
        panelperi.add(list_reg,c);
        
        
        hashHobby = new HashMap<Hobby,JCheckBox>();
        
        for (Hobby h : Hobby.values()) {
            hashHobby.put(h, new JCheckBox(h.toString()));
            
            panelperi.add(hashHobby.get(h),c);
            
        }
        
        
        
        
        
        ///////////////////////////
        ////// POSITIONNEMENT /////
        ///////////////////////////
        
        /*panelinfo.setPreferredSize(dim1 = new Dimension(400,50));
        
        panelinfo.setLayout(new FlowLayout());
        paneldate.setPreferredSize(dim2 = new Dimension(400,150));
        panelperi.setPreferredSize(dim3 = new Dimension(400,150));*/
        float[] ret = {0,0,0};
        ret = Color.RGBtoHSB(200, 200, 200, ret);
        panelinfo.setBackground(Color.getHSBColor(ret[0], ret[1], ret[2]));
        ret = Color.RGBtoHSB(220, 220, 220, ret);
        paneldate.setBackground(Color.getHSBColor(ret[0], ret[1], ret[2]));
        ret = Color.RGBtoHSB(240, 240, 240, ret);
        panelperi.setBackground(Color.getHSBColor(ret[0], ret[1], ret[2]));
        this.setBackground(Color.GRAY);
        
        
        
        ///////////////////////
        //////// AJOUT ////////
        ///////////////////////
        this.setLayout(new GridBagLayout());
        GridBagConstraints contrainte = new GridBagConstraints();
        contrainte.fill = GridBagConstraints.HORIZONTAL;
        contrainte.weightx=1;
        contrainte.ipady=100;
        contrainte.gridx=1;
        this.add(panelinfo, contrainte);
        contrainte.ipady=10;
        this.add(paneldate, contrainte);
        contrainte.ipady=80;
        this.add(panelperi, contrainte);
        
        paneldate.add(paneldateLabel,BorderLayout.NORTH);
        paneldate.add(paneldatechamp,BorderLayout.CENTER);
        
        buttonValider = new JButton("Valider");
        panelperi.add(buttonValider,c);
        
        buttonAnnuler = new JButton("Annuler");
        panelperi.add(buttonAnnuler,c);
        
        
        
        
        
    }
    
    /**
     * Affecte des valeurs au formulaire de fiche contact
     * @param contact un contact pour mettre à jour à l'IHM
     * @return vrai si ok
     */
    public boolean setValues(Contact contact) {
        if (contact == null) { return false; }
        
        /** TP 1 : à compléter **/
        
        /*
         * Nom du contact
         */
        champNom.setText(contact.getNom()); 
        champPre.setText(contact.getPrenom());
        champTel.setText(contact.getNumeroTelephone());
        champE_M.setText(contact.getEmail());
        list_jour.setSelectedItem(contact.getDateNaissanceJour());
        list_mois.setSelectedItem(contact.getDateNaissanceMois());
        list_annee.setSelectedItem("" +contact.getDateNaissanceAnnee());
        list_reg.setSelectedItem(contact.getRegion());
        //System.out.println(contact.getNom());
        
        for(JCheckBox J : hashHobby.values()) {
            J.setSelected(false);
        }
        for (Hobby h : contact.getHobbies()) {
            //System.out.println(h.toString());
            hashHobby.get(h).setSelected(true);
        }
        
        
        return true;
    }
    
    /**
     * Retourne les valeurs associées au formulaire de fiche contact
     * @param contact un contact à mettre à jour à partir de l'IHM
     * @return vrai si ok
     */
    public boolean getValues(Contact contact) {
        if (contact == null) { return false; }
        
        /** TP 1 : à compléter **/
          
        /*
         * Affecte une valeur à l'attribut Nom avec le nom saisi dans le champ
         * correspondant de l'IHM
         */
        contact.setNom(champNom.getText());     
        contact.setPrenom(champPre.getText());
        
        int jour = list_jour.getSelectedIndex()+1;
        Mois mois = (Mois) list_mois.getSelectedItem();
        
        int annee = list_annee.getSelectedIndex() +1865;
        
        contact.setDateNaissance(jour,mois, annee);
        
        
        contact.setNumeroTelephone(champTel.getText());
        contact.setEmail(champE_M.getText());
        contact.setRegion((Region) list_reg.getSelectedItem());
        
        for (Hobby h : contact.getHobbies()) {
            contact.removeHobby(h);
        }
        for (Hobby h : hashHobby.keySet()) {
            if (hashHobby.get(h).isSelected()){
            contact.addHobby(h);
            }
        }
        
        //buttonValider = new JButton("Is JCheckBox selected?");
        
        

        return true;
    }
    
    /**
     * Initialise la gestion des événements
     */
    private void initListeners() {
        /** TP 5 : à compléter **/ 
        
        buttonAnnuler.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carnet.setContactModified(false);
            }
        });
        
        buttonValider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carnet.setContactModified(true);
            }
            
            
        });
    }    
}