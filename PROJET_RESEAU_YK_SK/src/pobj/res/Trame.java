package pobj.res;

import java.util.StringJoiner;

import pobj.res.header.Header;

/**
 * Classe representant une trame
 * @author Sharane et Yannis
 *
 */
public class Trame implements ITrame{
	private Header coucheLiaison;//entete de la couche liaison
	private Header coucheReseau;//entete de la couche reseau
	private Header coucheTransport;//entete de la couche transport
	private Header coucheApplication;//entete de la couche application
	
	/**
	 * Set l'entete de la couche liaison
	 * @param liaison L'entete liaison
	 */
	@Override
	public void setLiaison(Header liaison) {coucheLiaison = liaison;}
	
	/**
	 * Set l'entete de la couche reseau
	 * @param reseau L'entete reseau
	 */
	@Override
	public void setReseau(Header reseau) {coucheReseau = reseau;}

	/**
	 * Set l'entete de la couche transport
	 * @param transport L'entete transport
	 */
	@Override
	public void setTransport(Header transport){coucheTransport = transport;}
	
	/**
	 * Set l'entete de la couche application
	 * @param application L'entete application
	 */
	@Override
	public void setApplication(Header application) {coucheApplication = application;}
	
	/**
	 * Accesseur sur l'entete de la couche liaison
	 * @return L'entete voulue
	 */
	public Header getLiaison(){return coucheLiaison;}
	
	/**
	 * Accesseur sur l'entete de la couche reseau
	 * @return L'entete voulue
	 */
	public Header getReseau() {return coucheReseau;}
	
	/**
	 * Accesseur sur l'entete de la couche transport
	 * @return L'entete voulue
	 */
	public Header getTransport() {return coucheTransport;}
	
	/**
	 * Accesseur sur l'entete de la couche application
	 * @return L'entete voulue
	 */
	public Header getApplication() {return coucheApplication;}
	
	@Override
	public String toString()
	{
		StringJoiner sj = new StringJoiner("\n");
		sj.add("########################################");
		sj.add("Debut de trame:\n");
		sj.add(this.getLiaison().toString());
		sj.add(this.getReseau().toString());
		sj.add(this.getTransport().toString());
		sj.add(this.getApplication().toString());
		sj.add("\nFin de trame");
		sj.add("########################################");
		
		return sj.toString();
	}
	
}
