package Application;
import java.util.LinkedList;
import java.util.List;


/**
* Classe representant une categorie et en meme temps l'arbre de ses categorie descendantes.
*
*/
public class Category {
        String name;
        List<Category> children;
        
        /**
         * Constructeur
         * @param n nom de la categorie
         */
        public Category(String n){
                this.name = n;
                children = new LinkedList<Category>();
        }
        
        /**
         * Methode permetant l'ajout d'une categorie fille
         * @param c categorie a ajouter
         */
        public void addChild(Category c){
                children.add(c);
        }
        
        /**
         * Methode retirant la categorie passe en parametre de la liste des enfants.
         * @param cat categorie à retirer de la liste des categories filles
         */
        public void removeChild(Category cat){
                if(children.contains(cat)){
                        children.remove(cat);
                }
        }
        
        
        public String[] getLineInformation(){
        	return null;
        }
                
        public List<Product> getProducts(){
            LinkedList<Product> res = new LinkedList<Product>();
            if(!(this instanceof Product)){
                for(Category c:this.getChildren()){
                    res.addAll(c.getProducts());
                }
            }
            else{
                res.add((Product)this);
                
            }
            return res;
        }
        
        /**
         * Retourne la categorie mere d'une categorie passee en parametre, en cherchant dans les descendants de la categorie appelante.
         * @param cat la categorie dont on cherche la categorie mere
         * @return null si cat n'a pas ete trouvee, renvoie la categorie mere si cat a ete trouvee.
         */
        public Category getParentCategory(Category cat){
                //cat est la categorie dont le parent est recherche
                Category result;
                for(Category c:getChildren()){
                        if(c == cat){
                                return this; //Si cat est enfant direct on retourne cat
                        }
                        else{ //Sinon on cherche dans cet enfant
                                result = c.getParentCategory(cat); //On cherche cat dans c
                                if (result != null){ //La methode renvoie null seulement si la categorie n'a pas ete trouvee.
                                        return result;
                                }
                        }
                }
                return null; //On renvoie null si la categorie cat n'a pas ete trouvee dans l'arbre defini par cat2
        }
        
        /**
         * Verifie que la structure de l'arbre est correcte.
         * @return true si toutes les feuilles de l'arbre sont de type Product
         */
        public boolean checkCategoryStructure(){
                if(children.size() == 0){
                        if(this instanceof Product){
                                return true;
                        }
                        else{
                                return false;
                        }
                }
                else{
                        if(this instanceof Product){
                                return false;
                        }
                        else{
                                for(Category cat:getChildren()){
                                        if(!cat.checkCategoryStructure()){
                                                return false;
                                        }
                                }
                        }
                }
                return true;
        }
        
        /**
         * Renvoie la liste des descendants directs de la cattegorie appelante
         * @return la liste des descendants directs de la categoorie appelante
         */
        public List<Category> getChildren(){
                return this.children;
        }
        
        /**
         * Definit le nom de la categorie
         * @param n le nouveau nom de la categorie
         */
        public void setName(String n){
                this.name = n;
        }
        
        /**
         * Renvoie le nom de la categorie
         * @return le nom de la categorie
         */
        public String getName(){
                return this.name;
        }
        
        /**
         * La methode toString renvoie le nom de la categorie (utile pour l'affichage de l'arbre)
         */
        public String toString(){
                return this.name;
        }
        
}