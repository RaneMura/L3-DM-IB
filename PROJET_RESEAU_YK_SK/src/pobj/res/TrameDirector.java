package pobj.res;

import pobj.exceptions.ErrorValueException;
import pobj.exceptions.TrameTooShortException;
import pobj.exceptions.UnsupportedProtocolException;

/**
 * Director pour le Design Pattern Builder
 * @author Sharane et Yannis
 *
 */
public class TrameDirector {
	//le constructeur de trame choisit
	private ITrameBuilder trameBuilder;
	
	/**
	 * Constructeur prenant un argument un builder de trame
	 * @param trameBuilder Le builder de trame
	 */
	public TrameDirector(ITrameBuilder trameBuilder)
	{
		this.trameBuilder = trameBuilder;
	}
	
	/**
	 * Accesseur sur la trame construite par le builder
	 * @return La trame construite par le builder
	 */
	public ITrame getTrame(){return this.trameBuilder.getTrame();}
	
	/**
	 * Construit une trame selon le builder en attribut
	 * @throws UnsupportedProtocolException 
	 * @throws ErrorValueException 
	 * @throws TrameTooShortException 
	 */
	public void constructTrame() throws UnsupportedProtocolException, ErrorValueException, TrameTooShortException
	{
		try {
			this.trameBuilder.buildLiaison();
			this.trameBuilder.buildReseau();
			this.trameBuilder.buildTransport();
			this.trameBuilder.buildApplication();
		}catch(UnsupportedProtocolException e) {
			throw e;
		} catch (ErrorValueException e) {
			throw e;
		}
	}
}
