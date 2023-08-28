package pobj.res.header;

import java.util.StringJoiner;

import pobj.exceptions.TrameTooShortException;
import pobj.exceptions.UnsupportedProtocolException;

/**
 * Classe initialisant une entete ethernet
 * @author Sharane et Yannis
 *
 */
public class Ethernet extends Header {
	//constantes representant l'indice des champs de l'entete IP dans la liste des champs
	public static final int ETH_DST_ADDR = 0;
	public static final int ETH_SRC_ADDR = 1;
	public static final int ETH_TYPE = 2;
	
	/**
	 * Construit une entete ethernet
	 * @param value Chaine de 14 octets sans espaces
	 * @throws UnsupportedProtocolException 
	 * @throws TrameTooShortException 
	 */
	public Ethernet(String value) throws UnsupportedProtocolException, TrameTooShortException
	{
		if(value.length()<this.getLength())throw new TrameTooShortException();
		String destAddr = value.substring(0, 12);
		String srcAddr = value.substring(12, 24);
		String type = value.substring(24, 28);
		
		this.addField(new Field(destAddr, "Destination address"));
		this.addField(new Field(srcAddr, "Source address"));
		this.addField(new Field(type, "Type"));	
		//gestion d'erreur
		try{
			this.getEthernetType(this.getFields().get(ETH_TYPE).getValue());
		}catch(UnsupportedProtocolException e) {
			throw e;
		}
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		Field dst = this.getFields().get(ETH_DST_ADDR);
		Field src = this.getFields().get(ETH_SRC_ADDR);
		Field type = this.getFields().get(ETH_TYPE);
		
		sb.append("Ethernet II:\n");
		sb.append("\t"+dst.getName()+":\n\t\t"+Ethernet.strToMacAddress(dst.getValue())+"\n");
		sb.append("\t"+src.getName()+":\n\t\t"+Ethernet.strToMacAddress(src.getValue())+"\n");
		
		try {
			sb.append("\t"+type.getName()+":\n\t\t"+this.getEthernetType(type.getValue())+" (0x"+type.getValue()+")\n");
		}catch(UnsupportedProtocolException e) {}
		
		return sb.toString();
	}
	
	/**
	 * Mets une chaine d'octets sous forme d'une addresse MAC
	 * @param str La chaine d'octets a mettre en forme
	 * @return La chaine sous forme d'addresse MAC
	 */
	static String strToMacAddress(String str)
	{
		//System.out.println(str);
		StringJoiner sj = new StringJoiner(":","(",")");
		for(int i=0; i<str.length(); i+=2)
		{
			sj.add(str.charAt(i)+""+str.charAt(i+1));
		}
		return sj.toString();
	}
	
	/**
	 * Renvoie la signification du code "Type" de la trame Ethernet
	 * @param type Le code "Type" de la trame
	 * @return La signification du code sous forme de chaine de characteres
	 * @throws UnsupportedProtocolException
	 */
	private String getEthernetType(String type) throws UnsupportedProtocolException
	{
		String res = "";
		boolean unsupported = false;
		
		switch(type)
		{
		case "0800":
			res = "IPv4";
			break;
		case "0806":
			res = "ARP";
			unsupported = true;
			break;
		case "86dd":
			res = "IPv6";
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

	/**
	 * Renvoie la taille de l'entete ethernet
	 * @return la taille de l'entete ethernet
	 */
	@Override
	public int getLength() {
		return 28;
	}

	/**
	 * Renvoie la valeur du champ 'type' de l'entete
	 * @return La valeur du champ
	 */
	@Override
	public String getNext() {
		return this.getFields().get(Ethernet.ETH_TYPE).getValue();
	}
}
