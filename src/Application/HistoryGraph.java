package Application;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;


public class HistoryGraph extends Canvas {
	protected ArrayList<Category> categoriesToShow;
	protected int nbDays, endDate;	//On affiche les donnees entre les dates startDate et endDate.
	protected static Color[] drawingColors = {Color.black, Color.blue, Color.red, Color.green, Color.orange, Color.white, Color.yellow,
											Color.magenta, Color.pink, Color.cyan};
	protected int echelle=1000;
	/**
	 * @return the nbOfDays
	 */
	public int getNbDays() {
		return nbDays;
	}

	/**
	 * @param nbDays the number of days that should be shown on the graph. 
	 */
	public void setNbDays(int nbDays) {
		this.nbDays = nbDays;
	}

	/**
	 * @return the endDate
	 */
	public int getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate la date de fin.
	 */
	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}
        public int getEchelle(){
            return this.echelle;
        }
        
        public void setEchelle(int echelle2){
            this.echelle=echelle2;
        }
	
	/**
	 * Constructeur. 
	 */
	public HistoryGraph(){
		categoriesToShow = new ArrayList<Category>();
		nbDays=20;
		endDate = 0;
	}

	/**
	 * Ajoute une categorie dont la courbe sera affichee par le graphe.
	 * @param cat la categorie a ajouter
	 */
	public void addCategoryToShow(Category cat) {
		if(categoriesToShow.size() < drawingColors.length){
			categoriesToShow.add(cat);
		}
		else{
			String message = "Le nombre de produits et categories pouvant etre representes sur le graphe se limite a" + drawingColors.length;
			String title = "Ajout impossible!";
			JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * @return la dimension minimale
	 */
	public Dimension getMinimumSize(){
		return new Dimension(500, 400);
	}
	
	/**
	 * @return la dimension maximale
	 */
	public Dimension getMaximumSize(){
		return getMinimumSize();
	}
	
	/**
	 * @return la dimension preferee
	 */
	public Dimension getPreferredSize(){
		return getMinimumSize();
	}
	
	
	/**
	 * @param d La date pour laquelle on souhaite calculer la quantite
	 * @param c La categorie
	 * @return La somme des quantites des articles de la categorie a la date d.
	 */
	public double computeTotalQuantities(int d, Category c){
		if(c.getChildren().size()==0){
			Product p = (Product)c;
			return p.getQuantityLevelOnDate(d);
		}
		else{
			double somme = 0.0;
			for(Category cat:c.getChildren()){
				somme += computeTotalQuantities(d, cat);
			}
			return somme;
		}
	}

	
	/**
	 * @param c La categorie dont on souhaite calculer les quantites pour chacune des dates
	 * @return un HashMap qui a une date associe une quantite
	 */
	public TreeMap<Integer,Double> computeCategoryQuantities(Category c){
		TreeMap<Integer,Double> categoryQuantities = new TreeMap<Integer,Double>();
		for(int d=Math.max(0,endDate-nbDays);d<=endDate;d++){
			categoryQuantities.put(d, computeTotalQuantities(d,c));
		}
		return categoryQuantities;
	}
	
	/**
	 * Cette methode est utilisee pour implementer un double-buffering, qui evite le clignotement de l'affichage lors de sa mise a jour.
	 * @param g le graphique sur lequel dessine.
	 */
	public void update(Graphics g){	//Fonction utile pour le double-buffering, qui �vite des probl�mes de clignotement
		Image memoryImage = createImage(getSize().width, getSize().height);
		Graphics gMemoryImage = memoryImage.getGraphics();
		paint(gMemoryImage);
		g.drawImage(memoryImage, 0, 0, this);
	}
	
	/**
	 * Redefinition de la methode paint. Gere le dessin des courbes.
	 * @param g graphique sur lequel dessine
	 */
	public void paint(Graphics g){
		drawBackground(g);
		//On recupere la taille du canvas pour pouvoir dessiner dans les bonnes proportions
		Dimension size = this.getSize();
		for(Category cat:categoriesToShow){
			//Couleur du dessin
			g.setColor(drawingColors[categoriesToShow.indexOf(cat)]);
			//Donnees de la categorie
			TreeMap<Integer,Double> dataToDraw = computeCategoryQuantities(cat);
			//Dessin
			boolean b = false;	//Ce booleen est utilise pour ne pas dessiner les points de quantit� nulle pour les cas o� le produit n'existait pas.
			int xLast=-1;
			int yLast=0;
			int x,y;
			for(int d:dataToDraw.keySet()){
				x = (int)((double)(d-endDate+nbDays)/(double)nbDays*size.getWidth());
				y = (int)((double)(dataToDraw.get(d))/(double)(this.echelle)*size.getHeight());
				y = (int)size.getHeight() - y;
				if(xLast != -1 && (yLast != 0 || b)){
					b = true;
					g.drawLine(xLast, yLast, x, y);
					//g.drawLine(xLast, yLast-1, x, y-1);
				}
				xLast = x;
				yLast=y;
			}
		}
	}
	
	
	/**
	 * Fonction qui dessine la grille, les echelles et la legende
	 * @param g
	 */
	private void drawBackground(Graphics g){
		g.setColor(Color.gray);		//TODO � rendre parametrisable
		Dimension size = this.getSize();
		int w = (int)size.getWidth();
		int h = (int)size.getHeight();
		//Dessin de la grille
		int nbVertical = 10;	//TODO � rendre parametrisable
		int intervalHeight = (int)(h/nbVertical);
		for(int i=0;i<nbVertical;i++){
			//dessin de la ligne
			g.drawLine(0, i*intervalHeight, w, i*intervalHeight);
			//dessin de l'echelle
			g.drawString((int)((double)(nbVertical-i)/(double)nbVertical*(double)(this.echelle))+"", 0, i*intervalHeight);	//TODO 1500 � changer ici!
		}
		//Dessin de l'abscisse et de l'echelle
		int nbHorizontal = 10;
		int intervalWidth = (int)(w/nbHorizontal);
		int j;
		for(int i=0;i<nbHorizontal;i++){
			//dessin du trait
			g.drawLine(i*intervalWidth, h, i*intervalWidth, h-10);
			//ecriture de l'echelle
			j=(int)((double)i/(double)nbHorizontal*20.0)+endDate-nbDays+1;
			if(j>=0){
				g.drawString(j+"", i*intervalWidth, h-10);	//TODO 20 a changer
			}
		}
		
		//Dessin de la legende
		g.setColor(Color.white);
		g.fillRoundRect(50, 5, 150, 15*categoriesToShow.size(), 5, 5);
		for(Category cat:categoriesToShow){
			//Couleur du dessin
			g.setColor(drawingColors[categoriesToShow.indexOf(cat)]);
			g.drawLine(50, 10+categoriesToShow.indexOf(cat)*15, 65, 10+categoriesToShow.indexOf(cat)*15);
			g.setColor(Color.black);
			g.drawString(cat.getName(), 75, 15+categoriesToShow.indexOf(cat)*15);
		}
		
	}
	
	/**
	 * Retire la categorie de la liste de celles dont les courbes doivent etre affich�es.
	 * @param c la categorie a retirer de la liste.
	 */
	public void removeCategory(Category c) {
		categoriesToShow.remove(c);
	}
}
