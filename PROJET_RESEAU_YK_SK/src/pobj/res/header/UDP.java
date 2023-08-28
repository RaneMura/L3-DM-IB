package pobj.res.header;

import pobj.exceptions.TrameTooShortException;
import pobj.exceptions.UnsupportedProtocolException;
import pobj.res.StringUtility;

/**
 * Classe gerant une entete UDP
 * @author Sharane et Yannis
 *
 */
public class UDP extends Header {
	//constantes representant l'indice des champs de l'entete UDP dans la liste des champs
	public static final int SRC_PORT_INDICE = 0;
	public static final int DEST_PORT_INDICE = 1;
	public static final int TOTAL_LENGTH_INDICE = 2;
	public static final int CHECKSUM_INDICE = 3;

	
	/**
	 * Construit une entete UDP
	 * @param value Chaine de longueur variable composee d'octets sans espaces
	 * @throws TrameTooShortException 
	 * @throws UnsupportedProtocolException 
	 */
	public UDP(String value) throws TrameTooShortException, UnsupportedProtocolException
	{
		if(value.length()<this.getLength())throw new TrameTooShortException();
		String srcPort = value.substring(0, 4);
		String destPort = value.substring(4, 8);
		String totLength = value.substring(8,12);
		String checksum = value.substring(12,16);
		
		//ajout des champs dans la liste des champs
		this.addField(new Field(srcPort, "Source Port"));
		this.addField(new Field(destPort, "Destination Port"));
		this.addField(new Field(totLength, "Total length"));
		this.addField(new Field(checksum, "Checksum"));
		
		//gestion d'erreur
		try{
			this.getPort(this.getFields().get(SRC_PORT_INDICE).getValue());
			this.getPort(this.getFields().get(DEST_PORT_INDICE).getValue());
		}catch(UnsupportedProtocolException e) {
			throw e;
		}
		
	}
		
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		Field srcp = this.getFields().get(SRC_PORT_INDICE);
		Field destp = this.getFields().get(DEST_PORT_INDICE);
		Field totl = this.getFields().get(TOTAL_LENGTH_INDICE);
		Field chks = this.getFields().get(CHECKSUM_INDICE);
		
		sb.append("User Datagram Protocol :\n");
		sb.append("\t"+srcp.getName()+":  "+StringUtility.hexaToInt(srcp.getValue())+" (0x"+srcp.getValue()+")\n");
		try {
			sb.append("\t\t"+this.getPort(srcp.getValue())+"\n");
		} catch (UnsupportedProtocolException e) {}
		sb.append("\t"+destp.getName()+":  "+StringUtility.hexaToInt(destp.getValue())+" (0x"+destp.getValue()+")\n");
		try {
			sb.append("\t\t"+this.getPort(destp.getValue())+"\n");
		} catch (UnsupportedProtocolException e) {}
		sb.append("\t"+totl.getName()+":  "+StringUtility.hexaToInt(totl.getValue())+" (0x"+totl.getValue()+")\n");
		sb.append("\t"+chks.getName()+":  0x"+chks.getValue()+"\n");
		
		return sb.toString();

	}
	
	/**
	 * Renvoie la longueur de l'entete udp
	 * @return La longueur de l'entete udp
	 */
	@Override
	public int getLength() {
		return 16;
	}

	/**
	 * Renvoie la valeur du champ contenant le prochain protocole suivant l'entete udp
	 * @return La valeur du champ
	 */
	@Override
	public String getNext() {
		if(StringUtility.hexaToInt(this.getFields().get(DEST_PORT_INDICE).getValue())<StringUtility.hexaToInt(this.getFields().get(SRC_PORT_INDICE).getValue()))
			return this.getFields().get(DEST_PORT_INDICE).getValue();
		return this.getFields().get(SRC_PORT_INDICE).getValue();
	}
	
	/**
	 * Renvoie la signification d'un port de la trame Ethernet
	 * @param port Le port source ou destination
	 * @return La signification du code sous forme de chaine de characteres
	 * @throws UnsupportedProtocolException
	 */
	private String getPort(String port) throws UnsupportedProtocolException
	{
		String res = "";
		boolean unsupported = false;
		int value = StringUtility.hexaToInt(port);
		//si la valeur du port n'est pas particuliere
		if(value>1024)
			return "Port Libre";
		//sinon
		switch(value)
		{
		case 67:
			res = "DHCP";
			break;
		case 68:
			res = "DHCP";
			break;
		case 53:
			res = "DNS";
			break;
		case 123:
			res = "NTP";
			unsupported = true;
			break;
		default:
			res = "Unknown Ethernet Type";
			unsupported = true;
			break;
		}
		
		if(unsupported)
			throw new UnsupportedProtocolException(res + " is not supported");
		
		return res;
	}
}
