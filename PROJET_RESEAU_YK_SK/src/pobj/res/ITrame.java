package pobj.res;

import pobj.res.header.Header;

/**
 * Interface d'une trame
 * @author Sharane et Yannis
 *
 */
public interface ITrame {
	/**
	 * Set l'entete de la couche liaison
	 * @param L'entete liaison
	 */
	public void setLiaison(Header liaison);
	
	/**
	 * Set l'entete de la couche reseau
	 * @param L'entete reseau
	 */
	public void setReseau(Header reseau);

	/**
	 * Set l'entete de la couche transport
	 * @param L'entete transport
	 */
	public void setTransport(Header transport);
	
	/**
	 * Set l'entete de la couche application
	 * @param L'entete application
	 */
	public void setApplication(Header application);
	
	/**
	 * Accesseur sur l'entete de la couche liaison
	 * @return L'entete voulue
	 */
	public Header getLiaison();
	
	/**
	 * Accesseur sur l'entete de la couche reseau
	 * @return L'entete voulue
	 */
	public Header getReseau();
	
	/**
	 * Accesseur sur l'entete de la couche transport
	 * @return L'entete voulue
	 */
	public Header getTransport();
	
	/**
	 * Accesseur sur l'entete de la couche application
	 * @return L'entete voulue
	 */
	public Header getApplication();
}
