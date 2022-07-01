package Application;
import java.util.*;

/**  
 * Classe pour la simulation des ventes.
 */
public class Simulation{
	List<Product> listOfProducts;
	int simulationSpeed = 3;	//nombre d'appels a simulationStep par seconde
	int simulationDate;
	boolean active = false;		//Boolean a vrai si la simulation tourne
	
	/**
	 * Constructeur. Initialise la liste des produits simules et le jour courrent de la simulation a zero.
	 */
	public Simulation(){
		listOfProducts = new ArrayList<Product>();
		simulationDate = 0;
	}

	/**
	 * @return la liste des produits dont des ventes sont simulees.
	 */
	public List<Product> getListOfProducts() {
		return listOfProducts;
	}

	/**
	 * Definit la liste des produits dont on veut simuler les ventes.
	 * @param listOfProducts la liste des produits dont on souhaite simuler les ventes.
	 */
	public void setListOfProducts(List<Product> listOfProducts) {
		this.listOfProducts = listOfProducts;
                System.out.println(listOfProducts.size());
	}
	
	/**
	 * Ajoute un produit a la liste des produits dont les ventes sont simulees
	 * @param p le produit a ajouter
	 */
	public void addProduct(Product p){
		this.listOfProducts.add(p);
	}
	
	/**
	 * retire un produit de la liste des produits dont on souhaite simuler les ventes.
	 * @param p le produit a retirer
	 */
	public void removeProduct(Product p){
		this.listOfProducts.remove(p);
	}
	
	/**
	 * Ajoute tous les produits d'une categorie a la liste des produits qu'on souhaite simuler.
	 * @param category la categorie dont on veut ajouter les produits.
	 */
	public void addProductsOfCategory(Category category){
		boolean isProduct = true;
		for(Category c:category.getChildren()){
			isProduct = false;
			addProductsOfCategory(c);
		}
		if(isProduct){
			addProduct((Product)category);
		}
	}
	
	/**
	 * Methode principale de la classe. Simule les ventes d'une journee pour tous les produits de la liste listOfProducts.
	 */
	public void simulationStep(){
		if(active){
			for(Product p:this.listOfProducts){
				try{
					p.sell(Math.random()*10.0, simulationDate);
				}
				catch(QuantityHigherThanAvailabilityException e){
					p.getQuantityLevels().put(simulationDate, 0.0);
				}
			}
			simulationDate++;
		}
	}
	
	/**
	 * @return la date courante. 
	 */
	public int getSimulationDate() {
		return simulationDate;
	}

	/**
	 * Definit la date courante de la simulation.
	 * @param simulationDate la date courante de la simulation, en nombre de jours.
	 */
	public void setSimulationDate(int simulationDate) {
		this.simulationDate = simulationDate;
	}


	/**
	 * Retoure la vitesse de simulation.
	 * @return la vitesse de simulation.
	 */
	public int getSimulationSpeed() {
		return simulationSpeed;
	}
	
	/**
	 * Permet de mettre en pause/lancer la simulation selon l'etat courant.
	 * @return true si la simulation est active, false si la simulation est en pause.
	 */
	public boolean startPause() {
		active = !active;
		return active;
	}
	
	/**
	 * Met la simulation en pause, peu importe son etat actuel. Utilise par exemple lorsque l'utilisateur utilise le module statistique et simulation
	 * et qu'il clique sur le bouton retour. Peu importe l'etat actif ou inactif de la simulation, il faut alors le mettre � inactif.
	 */
	public void stopSimulation() {
		active = false;
	}
	
	/**
	 * Definit la vitesse de simulation.
	 * @param value nombre de jours simules par seconde.
	 */
	public void setSimulationSpeed(int value) {
		this.simulationSpeed = value;
	}
}
