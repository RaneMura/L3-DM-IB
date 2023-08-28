package pobj.res.header;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe gerant les Entetes
 * @author Sharane et Yannis
 *
 */
public abstract class Header {
	//liste de champs
	private List<Field> a_fields = new ArrayList<>();
	
	/**
	 * Ajoute un champ dans l'entete
	 * @param f Le champ a ajouter
	 */
	public void addField(Field f) {a_fields.add(f);}
	
	/**
	 * Accesseur sur la liste des champs de l'entete
	 * @return
	 */
	public List<Field> getFields(){return a_fields;}
	
	/**
	 * Renvoie la taille de l'entete
	 * @return La taille de l'entete
	 */
	public abstract int getLength();
	
	/**
	 * Renvoie la valeur du champ identifiant le prochain entete encapsule s'il existe
	 * @return La valeur de ce champ ou chaine vide s'il n'existe pas
	 */
	public abstract String getNext();
}
