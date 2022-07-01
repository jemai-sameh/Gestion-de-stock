package Application;
import Entity.Categories;
import Entity.IMetierCategoriesImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

public class HomeScreen extends Panel implements ActionListener{
	Application app;
	boolean active;
	JButton buttonStructureManager;
	JButton buttonStats;
	JButton loadB, saveB;
        JButton Stats;
	JLabel titre=new  JLabel("Logiciel de GestionDeStock",JLabel.CENTER);
	Font font = new Font("Gabriola",Font.BOLD,60);
	JPanel panel=new JPanel();
	private final JLabel lblNewLabel = new JLabel("New label");
	
	public HomeScreen(Application app){
		this.app = app;
		active = false;
		this.setLayout(null);
		this.setBackground(Color.white);
		
		titre.setBounds(150, 30, 800, 70);
		titre.setFont(font);
		this.add(titre);
		
		
		//Creation des trois bouttons de lecran d'accueil
		JLabel lab=new JLabel("Gerer les categories et les produits");
		buttonStructureManager = new JButton();
		buttonStructureManager.setBounds(143, 21, 199, 22);
		buttonStructureManager.setVisible(true);
		buttonStructureManager.addActionListener(app);
		buttonStructureManager.add(lab);
		//buttonStructureManager.add(icon);
		//this.add(buttonStructureManager);
		
		buttonStats = new JButton("Statistiques");
		buttonStats.setBounds(347, 21, 89, 23);
		buttonStats.setVisible(true);
		buttonStats.addActionListener(app);
		//this.add(buttonStats);
                
                Stats = new JButton("Simulation");
                Stats.setBounds(737, 21, 81, 23);
		Stats.setVisible(true);
		Stats.addActionListener(app);
		
		loadB = new JButton("Charger les donnees");
		loadB.setBounds(441, 21, 131, 23);
		loadB.setVisible(true);
		loadB.addActionListener(this);
		//this.add(loadB);
		
		saveB = new JButton("Sauvegarder les donnees");
		saveB.setBounds(577, 21, 155, 23);
		saveB.setVisible(true);
		saveB.addActionListener(this);
		//this.add(saveB);
		
		
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),"choisir l'action a effectuer"));
		panel.setLayout(null);
		panel.add(buttonStructureManager);
		panel.add(buttonStats);
		panel.add(loadB);
		panel.add(saveB);
                panel.add(Stats);
		panel.setBounds(150,500, 910, 94);
		lblNewLabel.setBounds(0, 90, 8, 4);
		panel.add(lblNewLabel);
		
	}
	
	public JButton getGoToStructureManagerButton(){
		return buttonStructureManager;
	}

	public JButton getGoToStatsButton() {
		return buttonStats;
	}
        
        public JButton getGoToSimButton() {
		return Stats;
	}
	
	public void activate(){
		active = true;
		if(app.getMagasin().checkCategoryStructure()){
			buttonStats.setEnabled(true);
		}
		else{
			buttonStats.setEnabled(false);
		}
	}
	
	public void inactivate(){
		active = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IMetierCategoriesImpl metier=new IMetierCategoriesImpl();
		if(e.getSource()==loadB){
			
                   Charge_Sauvegarde cs = new Charge_Sauvegarde();
          int[] IN=new int[1];
            List <Categories> produits=metier.getProduitParMotCle("asus") ;
          
            if(produits.size()!=0){
            	 System.out.println(produits.size());
                Category res = cs.conversion2(produits,1,IN);
                
               
                if(res.checkCategoryStructure()){
                    app.setMagasin(res);
                    buttonStats.setEnabled(true);
                }
            }
            else{
                System.out.println("la base est vide");
                
            }
		}
		else if(e.getSource()==saveB){
                     List <Categories> produits=metier.getProduitParMotCle("asus") ;
                     if(produits !=null){
                     for(int i=1;i<produits.size()+1;i++){
                        
                            metier.supprimerProduits();
                      
                         
                     }
                     }
                    Category categoryToSave = app.getMagasin();
                    Charge_Sauvegarde cs=new Charge_Sauvegarde();
                    int[] k=new int[1];
                    List<Categories> l=cs.conversion1(categoryToSave, 1, k);
                    for(Categories c:l){
                        
                         metier.insetionProduit(c);
                    }
                    Charge_Sauvegarde.id=0;
	                  
                       
		}
		
	}
}
