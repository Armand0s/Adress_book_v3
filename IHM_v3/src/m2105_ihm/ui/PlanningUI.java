/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2105_ihm.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import m2105_ihm.Controleur;
import m2105_ihm.nf.Contact;
import m2105_ihm.nf.Evenement;
import m2105_ihm.nf.Mois;
import m2105_ihm.nf.NoyauFonctionnel;

/**
 *
 * @author Armand
 */
public class PlanningUI extends javax.swing.JPanel {

    /**
     * Creates new form PlanningUI
     */
    public PlanningUI(Controleur controleur) {
        this.controleur = controleur;
        
            
            currentEvt = this.getDefaultEvt();
       // } else {
          //  currentEvt = evt_list.get(0);
        //}
        initComponents();
            if (!evt_list.isEmpty()) {
                currentEvt = evt_list.get(0);
            }
        setValues(currentEvt);
        setValuesParticipants(currentEvt);
        setValuesList(currentEvt);

    }

    
    public Evenement getDefaultEvt() {
        return new Evenement("No Evenement Selected",
                    calendar.getDd(),
                    intToMonths(calendar.getMm()),
                    calendar.getYy()
            );
    }
    
    
    public void uncheckDelete() {
        this.checkDelete.setSelected(false);
        this.buttonDelete.setEnabled(false);
    }
    
    
    public Mois intToMonths(int mois) {
            Mois mTmp;
        switch (mois) {
            case 0 :
                mTmp = Mois.JANVIER;
                break;
            case 1 :
                mTmp = Mois.FEVRIER;
                break;
            case 2 :
                mTmp = Mois.MARS;
                break;
            case 3 :
                mTmp = Mois.AVRIL;
                break;
            case 4 :
                mTmp = Mois.MAI;
                break;
            case 5 :
                mTmp = Mois.JUIN;
                break;
            case 6 :
                mTmp = Mois.JUILLET;
                break;
            case 7 :
                mTmp = Mois.AOUT;
                break;
            case 8 :
                mTmp = Mois.SEPTEMBRE;
                break;
            case 9 :
                mTmp = Mois.OCTOBRE;
                break;
            case 10 :
                mTmp = Mois.NOVEMBRE;
                break;
            case 11 :
                mTmp = Mois.DECEMBRE;
                break;
            default :
                mTmp = Mois.JANVIER;
                break;
                
        }
        return mTmp;
    }

    public void ajouterEvt(Evenement e) {

       this.evt_list.add(e); 
    }
    
    public void ajouterEvt() {

        boiteEvtUI = new DialogCreerEvtUI(controleur, this);

    }
    
    public boolean retirerEvt(Evenement evt) {
        if (evt == null) { return false; }
        
        evt_list.remove(evt);
            
        return false;
    }
    
public boolean trieEvenement() {
        boolean sucess = false;
        
        if (evt_list.size() >=2) {
            for(int i = 0; i< evt_list.size()-1; i++) {
                if (!evtAvant(evt_list.get(i),evt_list.get(i+1))) {
                    evt_list.add(i, evt_list.get(i+1));
                    evt_list.remove(i+2);
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
                || ((evt1.getDateAnnee() == evt2.getDateAnnee())
                    && (evt1.getDateMois().compareTo(evt2.getDateMois()) == 0)
                        &&  evt1.getDateJour() < evt2.getDateJour()+1)
                ) {
            //System.out.println(evt1.getIntitule() + " est avant " + evt2.getIntitule());
            return true;
        } else {
            //System.out.println(evt2.getIntitule() + " est avant " + evt1.getIntitule());
        return false;
        }
    }
    
    
    
    /*
     * Retourne l'événement sélectionné
     */
    public boolean setValues(Evenement event) {
        if (event == null) { return false; }
        

        
        this.text_nomEvt.setText(event.getIntitule());
        this.combo_Jour.setSelectedIndex(event.getDateJour()-1);
        this.combo_Mois.setSelectedItem(event.getDateMois());
        this.combo_Annee.setSelectedIndex(event.getDateAnnee()-1965);
        
        
        
        
        
        
        return true;
    }
    
    public boolean setValuesParticipants(Evenement event) {
        if (event == null) { return false; }
        
        
        panel_listParticipants.removeAll();
        
        participants_JBu.clear();
        

        for(Contact c : event.getParticipants()) {
            participants_JBu.add(new JButton(c.getNom() + " " + c.getPrenom()));
        }
        
        for (JButton jb : participants_JBu) {
            jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PlanningUI.this.controleur.retirerParticipantEvenement(currentEvt.getParticipants()[(
                                participants_JBu.indexOf(e.getSource()
                                )
                            )])) {
                    System.out.println("blbl");
                    participants_JBu.remove(e.getSource());
                            /*panel_listParticipants.removeAll();
                            participants_JBu.clear();*/
                            setValuesParticipants(event);
                }
            }
        });
            jb.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                jb.setText("Supprimer ?");
                
            }
            public void mouseExited(MouseEvent evt) {
                //setValuesParticipants(event);
                System.out.println(event.getParticipants().length + " " + participants_JBu.size());
                if (event.getParticipants().length  == participants_JBu.size()) {
                jb.setText(event.getParticipants()[
                                    participants_JBu.indexOf(
                                        evt.getSource())].getNom() + " "
                            + event.getParticipants()[
                                    participants_JBu.indexOf(
                                        evt.getSource())].getPrenom());
                } else {
                    participants_JBu.remove(evt.getSource());
                }
                
            }
        });
            
            
    }
    
        for (JButton jb : participants_JBu) {
            panel_listParticipants.add(jb);
        }
        panel_listParticipants.validate();
        panel_listParticipants.repaint();
        return true;
    }
        /*
         * Ajoute les bouttons des Evenements à suivre
         */
    public boolean setValuesList(Evenement event) {
        if (event == null) { return false; }

            trieEvenement();
            list_Evt.removeAll();
            
            next_Evt_Evt.clear();
            next_Evt_JBu.clear();
        
        for (Evenement e : evt_list) {
            //System.out.println(e.toString());
            //System.out.println(evt_date_selected.toString());
            if (evtAvant(evt_date_selected, e)) {
                next_Evt_Evt.add(e);
                next_Evt_JBu.add(new JButton(e.toString()));
            }
        }
        
        /*
         * Ajoute les Listeners pour les bouttons
         */
        for (int i=0; i<next_Evt_JBu.size();i++) {
            this.tempo = i;
            next_Evt_JBu.get(i).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PlanningUI.this.setCurrentEvt(
                        next_Evt_Evt.get(
                                next_Evt_JBu.indexOf(e.getSource()
                                )
                        )
                        
                );
                /*
                 * Actualise les infos pour le nouvel Evenement selectionné
                 */
                PlanningUI.this.setValues(currentEvt);
                PlanningUI.this.setValuesParticipants(currentEvt);
                PlanningUI.this.setValuesList(currentEvt);
                
            }
        });
            
            list_Evt.add(next_Evt_JBu.get(i));
            list_Evt.validate();
            list_Evt.repaint();
        }
        return true;
    }
    
    
    /*
     * 
     */
    public boolean getValues(Evenement event) {
        if (event == null) { return false; }
        
        event.setIntitule(text_nomEvt.getText());
        event.setDate(combo_Jour.getSelectedIndex()+1,
                (Mois) combo_Mois.getSelectedItem(),
                combo_Annee.getSelectedIndex()+1965);

        return true;
    }

    public Evenement getEvt_date_selected() {
        return evt_date_selected;
    }

    public void setDate_Evt_date_selected(int j, int m, int y) {
        evt_date_selected.setDate(j, intToMonths(m), y);
    }

    public Evenement getCurrentEvt() {
        return currentEvt;
    }

    public void setCurrentEvt(Evenement currentEvt) {
        this.currentEvt = currentEvt;
        setValues(currentEvt);
        setValuesParticipants(currentEvt);
    }
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelPlanning = new javax.swing.JPanel();
        panel_listEvt = new javax.swing.JPanel();
        label_evtASuivre = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_Evt = new javax.swing.JPanel();
        panel_calendar = new javax.swing.JPanel();
        panelEvt = new javax.swing.JPanel();
        panel_currentDate = new javax.swing.JPanel();
        label_CurrentDay = new javax.swing.JLabel();
        panel_infoEvt = new javax.swing.JPanel();
        label_nomEvt = new javax.swing.JLabel();
        combo_Jour = new javax.swing.JComboBox();
        combo_Mois = new javax.swing.JComboBox();
        combo_Annee = new javax.swing.JComboBox();
        text_nomEvt = new javax.swing.JTextField();
        labal_dateEvt = new javax.swing.JLabel();
        label_jour = new javax.swing.JLabel();
        label_mois = new javax.swing.JLabel();
        label_annee = new javax.swing.JLabel();
        label_participants = new javax.swing.JLabel();
        button_addParticipant = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        panel_listParticipants = new javax.swing.JPanel();
        buttonDelete = new javax.swing.JButton();
        checkDelete = new javax.swing.JCheckBox();
        buttonValider = new javax.swing.JButton();
        buttonAnnuler = new javax.swing.JButton();

        panel_listEvt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        label_evtASuivre.setText("Evenements suivants la date selectionnée:");

        list_Evt.setLayout(new java.awt.GridLayout(100, 1));
        jScrollPane1.setViewportView(list_Evt);

        javax.swing.GroupLayout panel_listEvtLayout = new javax.swing.GroupLayout(panel_listEvt);
        panel_listEvt.setLayout(panel_listEvtLayout);
        panel_listEvtLayout.setHorizontalGroup(
            panel_listEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_listEvtLayout.createSequentialGroup()
                .addComponent(label_evtASuivre)
                .addGap(0, 13, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        panel_listEvtLayout.setVerticalGroup(
            panel_listEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_listEvtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_evtASuivre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        panel_calendar.setLayout(new java.awt.BorderLayout());

        panel_calendar.add(calendar);

        javax.swing.GroupLayout PanelPlanningLayout = new javax.swing.GroupLayout(PanelPlanning);
        PanelPlanning.setLayout(PanelPlanningLayout);
        PanelPlanningLayout.setHorizontalGroup(
            PanelPlanningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPlanningLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelPlanningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_calendar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_listEvt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelPlanningLayout.setVerticalGroup(
            PanelPlanningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPlanningLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_calendar, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_listEvt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelEvt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        panel_currentDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        label_CurrentDay.setText("Aujourd'hui: "
            + calendar_currentDate.get(Calendar.DAY_OF_MONTH) + " "
            + intToMonths(calendar_currentDate.get(Calendar.MONTH)).toString() + " "
            + calendar_currentDate.get(Calendar.YEAR));

        javax.swing.GroupLayout panel_currentDateLayout = new javax.swing.GroupLayout(panel_currentDate);
        panel_currentDate.setLayout(panel_currentDateLayout);
        panel_currentDateLayout.setHorizontalGroup(
            panel_currentDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_currentDateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_CurrentDay, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel_currentDateLayout.setVerticalGroup(
            panel_currentDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_currentDateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_CurrentDay, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_infoEvt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        label_nomEvt.setText("Nom Evenement :");

        Integer[] tabInt = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        combo_Jour.setModel(new javax.swing.DefaultComboBoxModel(tabInt));

        Mois[] mois = Mois.values();
        combo_Mois.setModel(new javax.swing.DefaultComboBoxModel(mois));

        String[] annee = new String[100];

        for (int i=1965;i<2065;i++) {
            annee[i-1965] = Integer.toString(i);
        }
        combo_Annee.setModel(new javax.swing.DefaultComboBoxModel(annee));

        text_nomEvt.setText("Nom Evt");

        labal_dateEvt.setText("Date Evenement :");

        label_jour.setText("Jour :");

        label_mois.setText("Mois :");

        label_annee.setText("Année :");

        label_participants.setText("Participants :");

        button_addParticipant.setText("Ajouter participant");
        button_addParticipant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_addParticipantActionPerformed(evt);
            }
        });

        panel_listParticipants.setLayout(new java.awt.GridLayout(20, 1));
        jScrollPane2.setViewportView(panel_listParticipants);

        buttonDelete.setText("Supprimer");
        buttonDelete.setEnabled(false);
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        checkDelete.setText("Supprimer Evenement ?");
        checkDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkDeleteActionPerformed(evt);
            }
        });

        buttonValider.setText("Valider");
        buttonValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonValiderAction(evt);
            }
        });

        buttonAnnuler.setText("Annuler");
        buttonAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAnnulerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_infoEvtLayout = new javax.swing.GroupLayout(panel_infoEvt);
        panel_infoEvt.setLayout(panel_infoEvtLayout);
        panel_infoEvtLayout.setHorizontalGroup(
            panel_infoEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_infoEvtLayout.createSequentialGroup()
                .addGroup(panel_infoEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_infoEvtLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonAnnuler)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonValider))
                    .addGroup(panel_infoEvtLayout.createSequentialGroup()
                        .addGroup(panel_infoEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_infoEvtLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(labal_dateEvt))
                            .addGroup(panel_infoEvtLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_infoEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(button_addParticipant, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_infoEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(panel_infoEvtLayout.createSequentialGroup()
                                            .addComponent(label_mois)
                                            .addGap(43, 43, 43)
                                            .addComponent(combo_Mois, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel_infoEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(panel_infoEvtLayout.createSequentialGroup()
                                                .addComponent(label_annee)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(combo_Annee, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panel_infoEvtLayout.createSequentialGroup()
                                                .addComponent(label_jour)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(combo_Jour, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(text_nomEvt, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panel_infoEvtLayout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(label_participants))
                            .addGroup(panel_infoEvtLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(label_nomEvt))
                            .addComponent(checkDelete)
                            .addGroup(panel_infoEvtLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(buttonDelete)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_infoEvtLayout.setVerticalGroup(
            panel_infoEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_infoEvtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_nomEvt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(text_nomEvt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labal_dateEvt)
                .addGap(10, 10, 10)
                .addGroup(panel_infoEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_jour)
                    .addComponent(combo_Jour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panel_infoEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_mois)
                    .addComponent(combo_Mois, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panel_infoEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_annee)
                    .addComponent(combo_Annee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(label_participants)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_addParticipant)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_infoEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonValider)
                    .addComponent(buttonAnnuler))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout panelEvtLayout = new javax.swing.GroupLayout(panelEvt);
        panelEvt.setLayout(panelEvtLayout);
        panelEvtLayout.setHorizontalGroup(
            panelEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEvtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_infoEvt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(panel_currentDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelEvtLayout.setVerticalGroup(
            panelEvtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEvtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_currentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_infoEvt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelPlanning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelEvt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPlanning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelEvt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonValiderAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonValiderAction
        getValues(this.currentEvt);
    }//GEN-LAST:event_buttonValiderAction

    private void buttonAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAnnulerActionPerformed
        setValues(this.currentEvt);
        setValuesParticipants(this.currentEvt);
        setValuesList(this.currentEvt);
    }//GEN-LAST:event_buttonAnnulerActionPerformed

    private void button_addParticipantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_addParticipantActionPerformed
        currentEvt.addParticipant(controleur.getBoiteUI().afficherChoixMembreContact(
                controleur.getFenetre(),
                null,
                controleur.getNf().getContacts()));
        
        //System.out.println(currentEvt.getParticipants().length);
        setValuesParticipants(currentEvt);
    }//GEN-LAST:event_button_addParticipantActionPerformed

    private void checkDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkDeleteActionPerformed
        buttonDelete.setEnabled(checkDelete.isSelected());
    }//GEN-LAST:event_checkDeleteActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
       controleur.supprimerEvenement();
    }//GEN-LAST:event_buttonDeleteActionPerformed
          
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelPlanning;
    private javax.swing.JButton buttonAnnuler;
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonValider;
    private javax.swing.JButton button_addParticipant;
    private javax.swing.JCheckBox checkDelete;
    private javax.swing.JComboBox combo_Annee;
    private javax.swing.JComboBox combo_Jour;
    private javax.swing.JComboBox combo_Mois;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labal_dateEvt;
    private javax.swing.JLabel label_CurrentDay;
    private javax.swing.JLabel label_annee;
    private javax.swing.JLabel label_evtASuivre;
    private javax.swing.JLabel label_jour;
    private javax.swing.JLabel label_mois;
    private javax.swing.JLabel label_nomEvt;
    private javax.swing.JLabel label_participants;
    private javax.swing.JPanel list_Evt;
    private javax.swing.JPanel panelEvt;
    private javax.swing.JPanel panel_calendar;
    private javax.swing.JPanel panel_currentDate;
    private javax.swing.JPanel panel_infoEvt;
    private javax.swing.JPanel panel_listEvt;
    private javax.swing.JPanel panel_listParticipants;
    private javax.swing.JTextField text_nomEvt;
    // End of variables declaration//GEN-END:variables
    private Cal calendar = new Cal(this);
    public GregorianCalendar calendar_currentDate = new GregorianCalendar();
    
    private Evenement currentEvt;
    private Evenement evt_date_selected = new Evenement("Selected",
                                                        calendar_currentDate.get(Calendar.DAY_OF_MONTH),
                                                        intToMonths(calendar_currentDate.get(Calendar.MONTH)),
                                                        calendar_currentDate.get(Calendar.YEAR));

    private Controleur controleur;
    
    private DialogCreerEvtUI boiteEvtUI;
    
    private ArrayList<Evenement> evt_list = new ArrayList<>();
    //private HashMap<Evenement,JButton> next_Evt= new HashMap<>();
    
    private ArrayList<Evenement> next_Evt_Evt = new ArrayList<>();
    private ArrayList<JButton> next_Evt_JBu = new ArrayList<>();
    public int tempo = 0;
    
    private ArrayList<JButton> participants_JBu = new ArrayList<>();

}
