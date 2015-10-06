/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm;

/**
 *
 * @author IUT2
 */
import java.awt.Point;
import m2105_ihm.nf.*;

/*
 * Cette classe illustre l'utilisation du code du noyau fonctionnel par l'emploi
 * des classes : Contact, GroupeContacts, Evenement et CarnetPlanning
 */
public class ExempleNF {

    /**
     * Exemple 1 : Usage des énumérations
     */
    public static void exempleUtilisationEnumerations() {
        DispoSortie dispo = DispoSortie.WEEK_END;
                
        /*
         * Exemple avec l'énumération Hobby
         * Liste l'ensemble des hobby et compare avec un type de hobby
         */
        for(Hobby hobby : Hobby.values()) {
            if (hobby == Hobby.CINEMA) {
                System.out.println("Ce hobby est mon préféré : " + hobby); 
            } else {
                System.out.println("Je n'aime pas ce hobby : " + hobby);
            }
        }
        
        /* 
         * Exemple avec les Régions
         * Affiche le nom de chaque région
         */
        for(Region region : Region.values()) {
            System.out.println(region);
        }

        /* 
         * Exemple avec les symboles
         * Affiche l'indice de chaque élément de l'énumération
         */
        for(Symbole symbole : Symbole.values()) {
            System.out.println("Le symbole " + symbole + " a pour indice : " + symbole.ordinal());
        }
        
        /*
         * Exemple de comparaisons avec les disponibilités pour des sorties
         */
        if (dispo == DispoSortie.NUIT) {
            System.out.println("Je peux mais pas trop tard");
        } else if (dispo == DispoSortie.SOIR) {
            System.out.println("ok pour 20h30");
        } else if (dispo == DispoSortie.WEEK_END) {
            System.out.println("Plutot le samedi");
        }
    }

    /**
     * Exemple 2 : Créer un objet contact et renseigner les attributs
     */
    public static Contact exempleCreationContact() {
        Contact c = new Contact();
        
        c.setDateNaissance(2002, Mois.NOVEMBRE, 22);
        c.setNom("arthur");
        c.setPrenom("martin");
        c.setEmail("arthur@martin.com");
        c.setNumeroTelephone("01 02 03 04 05");
        c.setRegion(Region.PACA);
                
        c.setDisponibilite(DispoSortie.NUIT);
        
        c.addHobby(Hobby.SPORT);
        c.addHobby(Hobby.LECTURE);
        
        return c;
    }

    /**
     * Exemple 3 : Modifier les attributs d'un contact
     */
    public static Contact exempleModifierContact(Contact c) {
        
        c.setEmail("amartin@iut2.upmf-grenoble.fr");
        
        c.removeHobby(Hobby.SPORT);
        c.addHobby(Hobby.CINEMA);
        
        return c;
    }
    
    /**
     * Exemple 4 : Afficher la valeur des attributs pour objet contact
     */
    public static void exempleAfficherContact(Contact c) {
                
        System.out.println("nom : " + c.getNom());
        System.out.println("prenom : " + c.getPrenom());
        System.out.println("region : " + c.getRegion());
        System.out.println("email : " + c.getEmail());
        System.out.println("telephone : " + c.getNumeroTelephone());
        System.out.println("date de naissance : " + c.getDateNaissanceJour() 
                         + "/" + c.getDateNaissanceMois()
                         + "/" + c.getDateNaissanceAnnee());
        System.out.println("dispo : " + c.getDisponibilite());
            
        /* Liste des hobbys */
        for(Hobby h : c.getHobbies()) {
            System.out.println("hobbby : " + h);
        }

        System.out.println();
    }
    
    /**
     * Exemple 5 : Créer un groupe de contact sans contact
     */
    public static GroupeContacts exempleCreerGroupe() {
        GroupeContacts g;
        
        g = new GroupeContacts();
       
        /* nom du groupe */
        g.setNom("Copains IUT2");

        /* symboles du groupe */ 
        g.addSymbole(Symbole.FLEUR);
        g.addSymbole(Symbole.ETOILE);
        g.addSymbole(Symbole.TABLEAU);
        
        /* 
         * Forme géometrique pour le logo du groupe (liste de points)
         * Pour l'exemple : un carré
         */
        Point [] points = new Point[4];
        
        points[0] = new Point(20,20);
        points[1] = new Point(100,20);
        points[2] = new Point(100,100);
        points[3] = new Point(20,100);
        
        g.setPoints(points);
        
        /* Pour le moment aucun contact n'est associé au groupe */
        
        return g;
    }

    /**
     * Exemple 6 : Créer un évènement sans participant
     */
    public static Evenement exempleCreerEvenement() {
        Evenement e;
        
        e = new Evenement();
       
        /* intitulé de l'évènement */
        e.setIntitule("Journée Porte Ouvertes de l'IUT");
        e.setDate(2014, Mois.FEVRIER, 18);
        
        /* Pour le moment aucun participant n'est associé à l'évènement */
        
        return e;
    }
    
    /*
     * Exemple 7 : Créer et gérer le carnet de contacts et le planning 
     *             d'évènements
     */
    public static NoyauFonctionnel exempleCreerCarnetPlanning() {
        Contact contact;
        Evenement evenement;
        GroupeContacts groupe;        
        NoyauFonctionnel nf;
        
        /* Paramètre : chemin créer la base de données stockant le contenu
         * complet du carnet
         */
        nf = new NoyauFonctionnel("/Users/laurillau/");
        
        /*
         * Créer et ajouter un contact au carnet
         */
        contact = exempleCreationContact();
        nf.addContact(contact);
        
        /*
         * Créer, ajouter un groupe au carnet, et associer un contact au groupe
         */        
        groupe = exempleCreerGroupe();
        groupe.addContact(contact);        
        nf.addGroupe(groupe);
        
        /*
         * Créer, ajouter un évènement au planning, et associer un participant
         */
        evenement = exempleCreerEvenement();
        evenement.addParticipant(contact);
        nf.addEvenement(evenement);
        
        return nf;
    }
    
    /*
     * Exemple 8 : Afficher le contenu du carnet et planning
     */
    public static void exempleAfficherCarnetPlanning(NoyauFonctionnel nf) {
        
        /*
         * Afficher la liste des contacts, des groupes et des évènements
         */
        Contact [] listeContacts = nf.getContacts();
        
        for(Contact c : listeContacts) {
            exempleAfficherContact(c);
        }

        /*
         * Afficher la liste des groupes
         */       
        Contact [] membres;
        GroupeContacts [] listeGroupes = nf.getGroupes();
        
        for(GroupeContacts g : listeGroupes) {
            System.out.println("nom du groupe : " + g.getNom());

            /* Liste des symboles */  
            for(Symbole s : g.getSymboles()) {
                System.out.println("Symbole : " + s);
            }

            /*
             * Liste des membres du groupe
             */
            System.out.println("Membres :");
            
            membres = g.getContacts();
            for(Contact c : membres) {
                System.out.println("   -> " + c.getNom());
            }            
        }
        
        /*
         * Afficher la liste des évènements
         */       
        Contact [] participants;
        Evenement [] listeEvenements = nf.getEvenements();
        
        for(Evenement e : listeEvenements) {
            System.out.println("Intitulé : " + e.getIntitule());
            
            /*
             * Liste des participants
             */
            System.out.println("Participants :");
            
            participants = e.getParticipants();
            for(Contact c : participants) {
                System.out.println("   -> " + c.getNom());
            }            
        }
    }

    /*
     * Exemple 9 : Modifier le contenu du carnet et planning
     */
    public static void exempleModifierCarnetPlanning(NoyauFonctionnel nf,
            Contact contact, GroupeContacts groupe, Evenement evenement) {

        /* 
         * Retirer un contact d'un groupe 
         */
        groupe.removeContact(contact);
        
        /* 
         * Retirer un participant d'un évènement
         */
        evenement.removeParticipant(contact);
        
        /* 
         * Supprimer un contact du carnet
         */
        nf.removeContact(contact);

        /* 
         * Supprimer un contact du carnet
         */
        nf.removeGroupe(groupe);
        
        /* 
         * Supprimer un évènement du planning
         */
        nf.removeEvenement(evenement);
    }

    /*
     * Point d'entrée du programme d'exemple d'usage du NF
     */
    public static void main(String [] args) {
        Contact c;
        Evenement e;
        GroupeContacts g;
        NoyauFonctionnel nf;
        
        /*
         * Enumérations
         */
        exempleUtilisationEnumerations();
        
        /*
         * Contact
         */
        c = exempleCreationContact();
        exempleModifierContact(c);
        exempleAfficherContact(c);   
        
        /*
         * Groupe de contacts
         */
        g = exempleCreerGroupe();

        /*
         * Evènement
         */        
        e = exempleCreerEvenement();
        
        /*
         * Carnet et planning
         */
        nf = exempleCreerCarnetPlanning();
        exempleAfficherCarnetPlanning(nf);
        
        /*
         * Modification du carnet
         */
        exempleModifierCarnetPlanning(nf, c, g, e);
    }
}
