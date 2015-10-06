/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Enumeration;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import m2105_ihm.nf.Contact;
import m2105_ihm.nf.GroupeContacts;

import java.awt.Component;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
/**
 *
 * @author IUT2
 */
public class ListeContacts extends javax.swing.JPanel  {
    /*
     * Principaux élements de l'arbre
     */
    private JTree                  listeContacts;
    private JScrollPane            listeDefilante;           
    private TreePath               selected;    
    private DefaultMutableTreeNode listeRacine;
    private DefaultMutableTreeNode listeNoeudGroupes;
    private DefaultMutableTreeNode listeNoeudContacts;
        
    private CarnetUI               carnet;
    
    // this is what you want
    private static class MyTreeCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            // decide what icons you want by examining the node
            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                
                if (node.getUserObject() instanceof NodeData) {
                    // your root node, since you just put a String as a user obj                    
                    setIcon(null);
                } else {
                    setIcon(UIManager.getIcon("FileChooser.homeFolderIcon"));
                }    
            }

            return this;
        }

    }
    /*
     * Classe de données spécifiques pour les entrées dans l'arbre
     */
    private class NodeData {
        public String title;
        public Object id;
        
        public NodeData(String title, Object id) {
            this.title = title;
            this.id = id;
        }
        
        public String toString() {
            return this.title;
        }
    }    
    
    /**
     * Constructeur
     */
    public ListeContacts(CarnetUI carnet) {
        this.carnet = carnet;
        
        initListe();        
        initUIComponents();
    }
    
    /**
     * Crée et positionne les composants graphiques constituant l'interface
     */
    private void initUIComponents() {  
        /* 
         * Ajout de la liste de contact dans une zone défilante 
         */
        listeContacts = new JTree(listeRacine);   
        listeContacts.setCellRenderer(new MyTreeCellRenderer());
        
        listeContacts.addTreeSelectionListener(new TreeSelectionListener() {
            /**
             * Traite les évènements liés à une sélection dans la liste de contacts
             */
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                Object node, data;
                TreePath newSelected = listeContacts.getSelectionPath();

                /* Vérifie qu'il y a une sélection */
                if (newSelected == null) { return; }
        
                /* Récupère la sélection */
                node = newSelected.getLastPathComponent();
                data = ((DefaultMutableTreeNode) node).getUserObject();
        
                /* Vérifie s'il s'agit d'un contact ou un groupe de contact */
                if (data instanceof NodeData) {
                    selected = newSelected;
                    if (((NodeData) data).id instanceof Contact) {                
                        /* Affiche le contact sélectionné */
                        carnet.setSelectedItem(((NodeData) data).id);
                    } else if (((NodeData) data).id instanceof GroupeContacts) {
                        /* Affiche le groupe de contacts sélectionné */
                        carnet.setSelectedItem(((NodeData) data).id);
                    }
                } else {
                    listeContacts.setSelectionPath(selected);
                }
            }
        });

        /* Astuce pour ne pas pouvoir fermer un "dossier" de l'arbre */
        BasicTreeUI treeUI = (BasicTreeUI) listeContacts.getUI();
        treeUI.setCollapsedIcon(null);
        treeUI.setExpandedIcon(null);

        listeDefilante = new JScrollPane();
	listeDefilante.getViewport().add(listeContacts);
        
        setLayout(new BorderLayout());
        add(listeDefilante, BorderLayout.NORTH);        
    }

    /**
     * Crée et initialise la structure arborescente pour le contenu du carnet
     */
    private void initListe() {        
        /* Noeuds élémentaires de l'arbre de contacts */
        listeRacine = new DefaultMutableTreeNode("Contacts et groupes de contacts");
        listeNoeudGroupes = new DefaultMutableTreeNode("Groupes");
        listeNoeudContacts = new DefaultMutableTreeNode("Contacts");
        listeRacine.add(listeNoeudContacts);
        listeRacine.add(listeNoeudGroupes);        
    }

    /**
     * Ajoute une entrée dans l'arbre pour les contacts
     * @param title texte affiché dans la liste pour un contact
     * @param Contact objet contact associé
     */
    public boolean ajouterContact(Contact contact) {
        boolean res;
        String titre = contact.getNom() + " " + contact.getPrenom();
                
        res = ajouter(listeNoeudContacts, titre, contact);
        if (res) { selectFirstContact(); }
        
        return res;
    }
    
    /**
     * Retire une entrée dans l'arbre pour les contacts
     * @param Contact contact à retirer
     */    
    public boolean retirerContact(Contact contact) {
        boolean res;
        
        res = retirer(listeNoeudContacts, contact);
        if (res) { selectFirstContact(); }
        
        return res;
    }

    /**
     * Ajoute une entrée dans l'arbre pour les groupes
     * @param title texte affiché dans l'arbre pour un groupe
     * @param GroupeContacts groupe de contacts associé
     */
    public boolean ajouterGroupe(GroupeContacts groupe) {
        boolean res;
        
        if (groupe == null) { return false; }
        
        res = ajouter(listeNoeudGroupes, groupe.getNom(), groupe);
        if (res) { selectFirstGroupe(); }
        
        return res;
    }
    
    /**
     * Retire une entrée dans l'arbre pour les groupe
     * @param GroupeContacts groupe que l'on veut retirer
     */    
    public boolean retirerGroupe(GroupeContacts groupe) {
        boolean res;
        
        res = retirer(listeNoeudGroupes, groupe);
        if (res) { selectFirstGroupe(); }
        
        return res;
    }
    
    /**
     * Retourne le contact sélectionné
     */
    public Contact getSelectedContact() {
        NodeData data;
        Contact c = null; 
        DefaultMutableTreeNode node;
        
        node = (DefaultMutableTreeNode) selected.getLastPathComponent();
        data = (NodeData) node.getUserObject();
        if (data.id instanceof Contact) {
            c = (Contact) data.id;
        }

        return c;
    }

    /**
     * Retourne le groupe de contacts sélectionné
     */
    public GroupeContacts getSelectedGroupe() {
        NodeData data;
        GroupeContacts g = null;
        DefaultMutableTreeNode node;
        
        node = (DefaultMutableTreeNode) selected.getLastPathComponent();
        data = (NodeData) node.getUserObject();

        if (data.id instanceof GroupeContacts) {
            g = (GroupeContacts) data.id;
        }
        
        return (GroupeContacts) g;
    }

    /**
     * Affiche toutes les feuilles de l'arbre du composant listeContacts
     */
    public void showAll() {        
        for(int i = listeContacts.getRowCount(); i >= 0; i--) {
          listeContacts.expandRow(i);
        }
    }
    
    public void updateEntry(Object c) {
        NodeData data;
        Enumeration e;
        DefaultMutableTreeNode node;
        
        if (c == null) { return; }
        
        if (c instanceof Contact) {
           e = listeNoeudContacts.children();
        } else if (c instanceof GroupeContacts)  {
           e = listeNoeudGroupes.children(); 
        } else {
           return;
        }
        
        while(e.hasMoreElements()) {
            node = (DefaultMutableTreeNode) e.nextElement();
            data = (NodeData) node.getUserObject();
            if (c == data.id ) {
                DefaultTreeModel model;
                if (c instanceof Contact) {
                    data.title = ((Contact) c).getNom() + " " + ((Contact) c).getPrenom();
                } else {
                    data.title = ((GroupeContacts) c).getNom();                    
                }
                
                model = (DefaultTreeModel)listeContacts.getModel();
                model.reload(listeRacine);
                showAll();
                selected = new TreePath(model.getPathToRoot(node));
                listeContacts.setSelectionPath(selected);
                break;
            }
        }
        
    }
    
    /**
     * Sélectionne le premier contact de la liste des contacts
     */
    public void selectFirstContact() {
        NodeData data;        
        Object selectedItem = null;        
        DefaultMutableTreeNode node;
        
        if (! listeNoeudContacts.isLeaf()) {
           node = (DefaultMutableTreeNode) listeNoeudContacts.getChildAt(0);
           data = (NodeData) node.getUserObject();        
           selectedItem = data.id;           
           highlightSelected(node);
        }
        
        carnet.setSelectedItem(selectedItem);
    }

    /**
     * Sélectionne le premier groupe de contacts de la liste des groupes
     */    
    private void selectFirstGroupe() {
        NodeData data;        
        Object selectedItem = null;                
        DefaultMutableTreeNode node;
        
        if (! listeNoeudGroupes.isLeaf()) {
           node = (DefaultMutableTreeNode) listeNoeudGroupes.getChildAt(0);
           data = (NodeData) node.getUserObject();        
           selectedItem = data.id;
           highlightSelected(node);           
        }
        
        carnet.setSelectedItem(selectedItem);
    }
    
    /**
     * Rend visible la sélection
     */
    private void highlightSelected(DefaultMutableTreeNode node) {
        DefaultTreeModel model;
        
        model = (DefaultTreeModel)listeContacts.getModel();
        selected = new TreePath(model.getPathToRoot(node));
        listeContacts.setSelectionPath(selected);
    }

    /**
     * Ajoute une entrée dans l'arbre pour un noeud
     * @param title texte affiché dans l'arbre pour un contact
     * @param itemData données associées à l'entrée
     */
    private boolean ajouter(DefaultMutableTreeNode root, String title, Object itemData) {
        boolean success = false;
        
        if ((title != null) && (itemData != null)) {
            DefaultMutableTreeNode node;
        
            node = new DefaultMutableTreeNode(new NodeData(title, itemData));
            // XXX S'occuper du tri par ordre alphabétique
            //root.add(node);
            root.insert(node, 0);
            
            /* redraw content */
            DefaultTreeModel model = (DefaultTreeModel)listeContacts.getModel();
            model.reload(listeRacine);
            showAll();            
            
            success = true;
        }
        
        return success;
    }
    
    /**
     * Retire une entrée dans l'arbre pour un noeud
     * @param itemData données associées à l'entrée
     */    
    private boolean retirer(DefaultMutableTreeNode root, Object itemData) {
        boolean success = false;
        
        if (itemData != null) {
            NodeData data;
            DefaultMutableTreeNode node;
            int count = root.getChildCount();
            
            for(int i = count - 1; i >= 0; i--) {
                node = (DefaultMutableTreeNode) root.getChildAt(i);
                data = (NodeData) node.getUserObject();
                
                if (itemData.equals(data.id)) {
                    root.remove(i);
                }
            }
            
            /* redraw content */
            DefaultTreeModel model = (DefaultTreeModel)listeContacts.getModel();
            model.reload(listeRacine);
            showAll();

            success = true;
        }
        
        return success;
    }
}
