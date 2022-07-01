package Application;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.Date;
import static javax.swing.JFrame.EXIT_ON_CLOSE;


public final class Application extends JFrame implements ActionListener {
        protected HomeScreen homeScreen;
        protected StructureManagerWindow structureManagerScreen;
        protected Stats statsScreen;
        protected Statistiques statistiquesScreen;
        protected String requiredScreen;
        protected String formerScreen;
        protected Simulation sim;
        public Category magasin;
        private final JLabel lblNewLabel = new JLabel("New label");
        
        public Application(){
                //Titre de la fenetre de l'application
                super("Gestion De stocks");
                
                this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                
                this.setBounds(50,50,1100,471);
                
                //Magasin
                magasin = new Category("Magasin");
                
                //Simulation
                sim = new Simulation();
               
                
                //Ecran d'accueil
                homeScreen = new HomeScreen(this);
                
                //Ecran du gestionnaire de structure
                structureManagerScreen = new StructureManagerWindow(this, magasin);
                
                //Ecran des simulations
                statsScreen = new Stats(this);
                
                //Ecran des simulations
                statistiquesScreen= new Statistiques(this,magasin);
                getContentPane().setLayout(null);
                lblNewLabel.setIcon(new ImageIcon("nRF2020-Zoom-magasins-alimentaires-americains-qui-renouvellent-experience-point-vente-1-F"));
                lblNewLabel.setBounds(0, 0, 791, 433);
                getContentPane().add(lblNewLabel);
                
               
                
                
                
                formerScreen = "";
                requiredScreen= "Home Screen";
                //Affichage de l'ecran d'accueil
                this.setVisible(true);
                while(true){
                        System.out.println("");
    if(!formerScreen.equals(requiredScreen)){
         this.getContentPane().removeAll();
    if(requiredScreen.equals("Home Screen")){
       homeScreen.activate();
          homeScreen.setVisible(true);
          this.getContentPane().add(homeScreen);
        formerScreen = "Home Screen";
   }
    else if(requiredScreen.equals("Structure Manager")){
  structureManagerScreen.activate();
   this.getContentPane().add(structureManagerScreen);
     structureManagerScreen.setVisible(true);
       this.setVisible(true);
    formerScreen = "Structure Manager";
 }
    else if(requiredScreen.equals("Stats Screen")){
       this.getContentPane().add(statsScreen);
       statsScreen.setVisible(true);
        this.setVisible(true);
       statsScreen.activate();
    formerScreen = "Stats Screen";
   }
 else if(requiredScreen.equals("Statistiques Screen")){
	statistiquesScreen.activate();
 this.getContentPane().add(statistiquesScreen);
     statsScreen.setVisible(true);
   this.setVisible(true);
  // statistiquesScreen.activate();
        formerScreen = "Statistiques Screen";
 }
                                
 System.out.println("");
     }
  }
 }
        
        public static void main(String[] args) {
                Application app = new Application();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                JButton b = (JButton)e.getSource();
                if(b==homeScreen.getGoToStructureManagerButton()){
                        this.requiredScreen = "Structure Manager";
                }
                else if(b==homeScreen.getGoToStatsButton()){
                        this.requiredScreen = "Statistiques Screen";
                }
                else if(b==statsScreen.getGetBackButton()){
                        this.requiredScreen = "Home Screen";
                }
                else if(b==homeScreen.getGoToSimButton()){
                        this.requiredScreen = "Stats Screen";
                }
                
                 else if(b==statistiquesScreen.getRetourButton()){
                	this.requiredScreen = "Home Screen";
                }
                
                
                else if(b==structureManagerScreen.getGetBackButton()){
                        if(!magasin.checkCategoryStructure()){
                        JOptionPane.showMessageDialog(this, "Erreur: la structure de l'arbre des catégories et produits n'est pas correcte."
                                        + "Les feuilles doivent exactement correspondre aux produits, et toute catégorie doit au moins contenir un produit.", "Erreur entrée utilisateur",
                    JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                                this.requiredScreen = "Home Screen";
                        }
                }
                
        }

        public int getSimulationDate() {
                return sim.getSimulationDate();
        }
        
        public Simulation getSim(){
                return sim;
        }

       
        public Category getMagasin() {
                return magasin;
        }

        
        public void setMagasin(Category magasin) {
                this.magasin = magasin;
        }
        
}