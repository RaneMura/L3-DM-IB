package pobj.res;

import pobj.exceptions.ErrorValueException;
import pobj.exceptions.TrameTooShortException;
import pobj.exceptions.UnsupportedProtocolException;
import pobj.res.header.*;

/**
 * Constructeur de trames
 * @author Sharane et Yannis
 *
 */
public class TrameBuilder implements ITrameBuilder {
	private Trame trame;//la trame a construire
	private String content;//le contenu de la trame
	private int contentPointer = 0;//pointeur de characteres du champ content
	private String nextType; //valeur representant le type de la prochaine couche
	
	/**
	 * Constructeur prenant en argument le contenu d'une trame sous forme d'une liste d'hexa sans espaces
	 * @param content Le contenu d'une trame valide
	 */
	public TrameBuilder(String content)
	{
		this.trame = new Trame();
		this.content = content;
	}
	
	/**
	 * Initialise l'entete liaison de la trame
	 * @throws UnsupportedProtocolException 
	 * @throws TrameTooShortException 
	 */
	@Override
	public void buildLiaison() throws UnsupportedProtocolException, TrameTooShortException {
		//creation de l'entete ethernet
		Ethernet eth;
		try {
			eth = new Ethernet(content.substring(contentPointer));
		}catch(UnsupportedProtocolException e) {
			throw e;
		}
		//ajout de l'entete ethernet dans la trame
		this.getTrame().setLiaison(eth);
		//mise a jour du pointeur de la chaine representant la trame
		contentPointer += eth.getLength();
		//mise a jour du prochain type encapsule
		nextType = eth.getNext();
	}

	/**
	 * Initialise l'entete reaseau de la trame
	 * @throws ErrorValueException 
	 * @throws TrameTooShortException 
	 * @throws UnsupportedProtocolException 
	 */
	@Override
	public void buildReseau() throws ErrorValueException, TrameTooShortException, UnsupportedProtocolException {
		Header reseau = null;
		//on test le type du paquet de la couche Reseau
		switch(nextType)
		{
		//cas couche Reseau = IP
		case "0800":
			//creation de l'entete ip
			reseau = new IP(content.substring(this.contentPointer));
			break;
		default:
			return;
		}
		//ajout de l'entete ip
		this.getTrame().setReseau(reseau);
		//mise a jour du pointeur
		contentPointer += reseau.getLength(); 
		//mise a jour du prochain type encapsule
		nextType = reseau.getNext();
	}

	/**
	 * Initialise l'entete transport de la trame
	 * @throws TrameTooShortException 
	 * @throws UnsupportedProtocolException 
	 */
	@Override
	public void buildTransport() throws TrameTooShortException, UnsupportedProtocolException {
		Header transport = null;
		//on test le type du paquet de la couche Reseau
		switch(StringUtility.hexaToInt(nextType))
		{
		//cas couche Transport = UDP
		case 17:
			//creation de l'entete udp
			transport = new UDP(content.substring(this.contentPointer));
			break;
		default:
			return;
		}
		//ajout de l'entete udp
		this.getTrame().setTransport(transport);
		//mise a jour du pointeur
		contentPointer += transport.getLength(); 
		//mise a jour du prochain type encapsule
		nextType = transport.getNext();
		//System.out.println(nextType);
	}

	/**
	 * Initialise l'entete application de la trame
	 * @throws ErrorValueException 
	 * @throws TrameTooShortException 
	 */
	@Override
	public void buildApplication() throws ErrorValueException, TrameTooShortException {
		Header application = null;
		//on test le type du paquet de la couche Reseau
		switch(StringUtility.hexaToInt(nextType))
		{
		//cas couche application = DNS
		case 53:
			//creation de l'entete dns
			application = new DNS(content.substring(this.contentPointer));
			break;
		//cas couche application = DHCP
		case 67:
			//creation de l'entete DHCP
			application = new DHCP(content.substring(this.contentPointer));
			break;
		case 68:
			//creation de l'entete DHCP
			application = new DHCP(content.substring(this.contentPointer));
			break;
		default:
			return;
		}
		//ajout de l'entete udp
		this.getTrame().setApplication(application);
		//mise a jour du pointeur
		contentPointer += application.getLength(); 
		//mise a jour du prochain type encapsule
		nextType = null;
	}

	/**
	 * Renvoie la trame cree
	 */
	@Override
	public ITrame getTrame() {
		return this.trame;
	}

	/**
	 * Reinitialise le pointeur de donnees du constructeur
	 */
	@Override
	public void reset() {
		this.contentPointer = 0;
		this.nextType = "";
		this.trame = new Trame();
	}

}
