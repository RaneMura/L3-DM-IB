package pobj.res.header;

/**
 * Classe gerant un champ d'en-tete
 * @author Sharane et Yannis
 *
 */
public class Field {
	//la valeur du champ sous forme de chaine de characteres
	private String a_value;
	//le nom du champ
	private String a_name;
	
	/**
	 * Constructeur classique
	 * @param value La valeur du champ
	 * @param name Le nom du champ
	 */
	public Field(String value, String name)
	{
		a_value = value;
		a_name = name;
	}
	
	/**
	 * Accesseur sur la valeur du champ
	 * @return La valeur du champ sous forme de chaine
	 */
	public String getValue()
	{
		return a_value;
	}
	
	/**
	 * Accesseur sur le nom du champ
	 * @return Le nom du champ
	 */
	public String getName()
	{
		return a_name;
	}
	
	@Override
	public String toString()
	{
		return this.getName()+":\n\t"+this.getValue();
	}
}
