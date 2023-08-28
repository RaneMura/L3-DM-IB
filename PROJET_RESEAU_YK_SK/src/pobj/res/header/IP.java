package pobj.res.header;

import java.util.StringJoiner;

import pobj.exceptions.*;
import pobj.res.StringUtility;

/**
 * Classe gerant une entete ip
 * @author Sharane et Yannis
 *
 */
public class IP extends Header {
	//constantes representant l'indice des champs de l'entete IP dans la liste des champs
	public static final int IP_VERSION_INDICE = 0;
	public static final int IP_IHL_INDICE = 1;
	public static final int IP_TOS_INDICE = 2;
	public static final int IP_TOTAL_LENGTH_INDICE = 3;
	public static final int IP_IDENTIFICATION_INDICE = 4;
	public static final int IP_R_INDICE = 5;
	public static final int IP_DF_INDICE = 6;
	public static final int IP_MF_INDICE = 7;
	public static final int IP_FRAGMENT_OFFSET_INDICE = 8;
	public static final int IP_TTL_INDICE = 9;
	public static final int IP_PROTOCOL_INDICE = 10;
	public static final int IP_HEADER_CHECKSUM_INDICE = 11;
	public static final int IP_SRC_ADDR_INDICE = 12;
	public static final int IP_DEST_ADDR_INDICE = 13;
	public static final int IP_OPTIONS = 14;
	
	/**
	 * Construit une entete ip
	 * @param value Chaine de longueur variable composee d'octets sans espaces
	 * @throws ErrorValueException 
	 * @throws TrameTooShortException 
	 * @throws UnsupportedProtocolException 
	 */
	public IP(String value) throws ErrorValueException, TrameTooShortException, UnsupportedProtocolException
	{
		if(value.length()<40)throw new TrameTooShortException();
		String version = value.substring(0, 1);
		String headerLength = value.substring(1, 2);
				
		String tos = value.substring(2,4);
		String dataLength = value.substring(4,8);
		
		
		String identifier = value.substring(8,12);
		
		String[] tmp = initFragmentOffset(value.substring(12,16)); //variable temporaire pour initialiser les elements du fragment offset
		String R = tmp[0];
		String DF = tmp[1];
		String MF = tmp[2];
		String fragmentOffset = tmp[3];
		
		
		String ttl = value.substring(16,18);
		String protocol = value.substring(18,20);
		String checksum = value.substring(20,24);
		String srcIp = value.substring(24,32);
		String dstIp = value.substring(32,40);	
		
		//test de validité des champs
		try
		{
			this.testVersion(version);
			this.getIPProtocole(protocol);
		}catch(ErrorValueException e) {
			throw e;
		} catch (UnsupportedProtocolException e) {
			throw e;
		}
		
		//ajout des champs dans la liste des champs
		this.addField(new Field(version, "Version"));
		this.addField(new Field(headerLength, "Header Length"));
		this.addField(new Field(tos, "TOS"));
		this.addField(new Field(dataLength, "Total Length"));
		this.addField(new Field(identifier, "Identification"));
		this.addField(new Field(R, "R"));
		this.addField(new Field(DF, "DF"));
		this.addField(new Field(MF, "MF"));
		this.addField(new Field(fragmentOffset, "Fragment Offset"));
		this.addField(new Field(ttl, "TTL"));
		this.addField(new Field(protocol, "Protocol"));
		this.addField(new Field(checksum, "Header Checksum"));
		this.addField(new Field(srcIp, "Source Address"));
		this.addField(new Field(dstIp, "Destination Address"));
		
		//ajout des options
		//test longueur
		if(this.getLength()>value.length())throw new TrameTooShortException();
		String options = value.substring(40, this.getLength());
		this.addField(new Field(options, "Options"));
		
	}
	
	/**
	 * Test si la valeur du champ 'Version' est correcte
	 * @param version La valeure du champ
	 * @throws ErrorValueException
	 */
	private void testVersion(String version) throws ErrorValueException
	{
		if(!version.equals("4"))
			throw new ErrorValueException("Mauvaise valeure pour le champ 'Version' de IP!");
	}	
	
	/**
	 * Initialise un tableau contenant les valeurs des elements du fragment offset
	 * @param value La valeur en hexa des 2 octets du champ
	 * @return Le tableau contenant les valeurs des sous champs sous forme de chaines de characteres
	 */
	private String[] initFragmentOffset(String value)
	{
		String[] res = new String[4];
		String binary = StringUtility.hexaToBinary(value);
		//System.out.println(value+"\n\n"+binary);
		//set de R, DF et MF
		for(int i=0; i<3; i++) {res[i]=binary.charAt(i)+"";}
		//set de fragment offset
		res[3]=binary.substring(3);
		return res;
	}
	
	/**
	 * Met en forme un hexa sous forme d'adresse ip
	 * @param hex L'hexa a formater
	 * @return La chaine sous forme d'adresse ip
	 */
	public static String convertHexToIP(String hex)
	{
	    String ip= "";

	    for (int j = 0; j < hex.length(); j+=2) {
	        String sub = hex.substring(j, j+2);
	        int num = Integer.parseInt(sub, 16);
	        ip += num+".";
	    }

	    ip = ip.substring(0, ip.length()-1);
	    return ip;
	}
	
	@Override
	public String toString()
	{
		StringJoiner sj = new StringJoiner("\n");
		Field vers = this.getFields().get(IP.IP_VERSION_INDICE);
		Field headLen = this.getFields().get(IP.IP_IHL_INDICE);
		Field tos = this.getFields().get(IP.IP_TOS_INDICE);
		Field dLen = this.getFields().get(IP.IP_TOTAL_LENGTH_INDICE);
		Field ident = this.getFields().get(IP.IP_IDENTIFICATION_INDICE);
		Field r = this.getFields().get(IP.IP_R_INDICE);
		Field df = this.getFields().get(IP.IP_DF_INDICE);
		Field mf = this.getFields().get(IP.IP_MF_INDICE);
		Field fOff = this.getFields().get(IP.IP_FRAGMENT_OFFSET_INDICE);
		Field ttl = this.getFields().get(IP.IP_TTL_INDICE);
		Field proto = this.getFields().get(IP.IP_PROTOCOL_INDICE);
		Field chks = this.getFields().get(IP.IP_HEADER_CHECKSUM_INDICE);
		Field srcIP = this.getFields().get(IP.IP_SRC_ADDR_INDICE);
		Field destIP = this.getFields().get(IP.IP_DEST_ADDR_INDICE);
		Field options = this.getFields().get(IP.IP_OPTIONS);

		sj.add("Internet Protocol Version 4 :");
		sj.add("\t"+vers.getName()+" :  "+StringUtility.hexaToInt(vers.getValue())+" (0x"+vers.getValue()+")");
		sj.add("\t"+headLen.getName()+" :  "+StringUtility.hexaToInt(headLen.getValue())*4+" bytes (0x"+headLen.getValue()+")");
		sj.add("\t"+tos.getName()+" :  "+StringUtility.hexaToInt(tos.getValue())+" (0x"+tos.getValue()+")");
		sj.add("\t"+dLen.getName()+" :  "+StringUtility.hexaToInt(dLen.getValue())+" (0x"+dLen.getValue()+")");
		sj.add("\t"+ident.getName()+" :  0x"+ident.getValue()+" ("+StringUtility.hexaToInt(ident.getValue())+")");		
		sj.add("\t"+r.getName()+" :  "+StringUtility.hexaToInt(r.getValue())+" (0x"+r.getValue()+")");
		sj.add("\t"+df.getName()+" :  "+StringUtility.hexaToInt(df.getValue())+" (0x"+df.getValue()+")");
		sj.add("\t"+mf.getName()+" :  "+StringUtility.hexaToInt(mf.getValue())+" (0x"+mf.getValue()+")");
		sj.add("\t"+fOff.getName()+" :  "+StringUtility.hexaToInt(fOff.getValue())+" (0x"+fOff.getValue()+")");
		sj.add("\t"+ttl.getName()+" :  "+StringUtility.hexaToInt(ttl.getValue())+" (0x"+ttl.getValue()+")");
		sj.add("\t"+proto.getName()+" :  "+StringUtility.hexaToInt(proto.getValue())+" (0x"+proto.getValue()+")");
		try {
			sj.add("\t\t"+this.getIPProtocole(proto.getValue()));
		} catch (UnsupportedProtocolException e) {}
		sj.add("\t"+chks.getName()+" :  0x"+chks.getValue());
		sj.add("\t"+srcIP.getName()+" :  "+IP.convertHexToIP(srcIP.getValue())+" (0x"+srcIP.getValue()+")");
		sj.add("\t"+destIP.getName()+" :  "+IP.convertHexToIP(destIP.getValue())+" (0x"+destIP.getValue()+")");
		sj.add("\t"+options.getName()+" :\n" + this.getOptions());
		return sj.toString();
	}
	
	/**
	 * Renvoie la longueur de l'entete ip
	 * @return La longueur de l'entete ip
	 */
	@Override
	public int getLength() {
		return (2*4*StringUtility.hexaToInt(this.getFields().get(IP.IP_IHL_INDICE).getValue()));
	}

	/**
	 * Renvoie la valeur du champ 'Protocol' de l'entete ip
	 * @return La valeur du champ
	 */
	@Override
	public String getNext() {
		return this.getFields().get(IP.IP_PROTOCOL_INDICE).getValue();
	}
	
	/**
	 * Renvoie la signification du code "Protocole" de la trame IP
	 * @param protocole Le code "Protocole" de la trame
	 * @return La signification du code sous forme de chaine de characteres
	 * @throws UnsupportedProtocolException
	 */
	private String getIPProtocole(String protocole) throws UnsupportedProtocolException
	{
		String res = "";
		boolean unsupported = false;
		int p = StringUtility.hexaToInt(protocole);
		
		switch(p)
		{
		case 1:
			res = "ICMP";
			unsupported = true;
			break;
		case 2:
			res = "IGMP";
			unsupported = true;
			break;
		case 6:
			res = "TCP";
			unsupported = true;
			break;
		case 8:
			res = "EGP";
			unsupported = true;
			break;
		case 9:
			res = "IGP";
			unsupported = true;
			break;
		case 17:
			res = "UDP";
			break;
		case 36:
			res = "XTP";
			unsupported = true;
			break;
		case 46:
			res = "RSVP";
			unsupported = true;
			break;
		default:
			res = "Unknown IP Protocole";
			unsupported = true;
			break;
		}
		
		if(unsupported)
			throw new UnsupportedProtocolException(res + " is not supported");
		
		return res;
	}
	
	/**
	 * Renvoie les options sous forme de chaine de characteres
	 * @return Les options formates
	 */
	private String getOptions()
	{
		StringJoiner sj = new StringJoiner("\n");
		//valeur des options
		String options = this.getFields().get(IP.IP_OPTIONS).getValue();
		System.out.println(options);
		int pointer = 0;
		//tant que l'on n'est pas arrive a la fin des options
		while(pointer < options.length())
		{
			//creation de la chaine representant l'option
			StringBuilder sb = new StringBuilder();
			//valeur entiere de l'option
			int valeurOption = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
			int optionLength;
			//mise a jour du pointeur
			pointer+=2;
			//switch sur la valeur des options
			switch(valeurOption)
			{
			case 0://fin de la liste des options
				sb.append("\t\tEnd of Options List (EOOL)");
				sj.add(sb);
				return sj.toString();
			case 1://cas NOP
				sb.append("\t\tNo Operation (NOP)");
				//tant que on a des NOP et que l'on n'a pas atteint la fin de l'entete on avance dans les options
				while(StringUtility.hexaToInt(options.substring(pointer, pointer+2)) == 1 && pointer<options.length())
					pointer+=2;
				sj.add(sb);
				break;
			case 7://RR
				//champ lenogueur de l'option
				optionLength = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				//debut de la chaine representant l'option
				sb.append("\t\tRecord Route (RR): length ");
				sb.append(optionLength+"\n\t\t");
				//pour chaque next hop
				for(int i=pointer+2; i<pointer+optionLength-2; i+=8)//-2 car on enleve le type de l'option et sa longueur
					sb.append("\tNext Hop: " + IP.convertHexToIP(options.substring(i, i+8)));
				//maj taille du pointeur
				pointer += optionLength-2;//-2 car le champ type est deja lue
				//ajout de l'option dans la chaine finale
				sj.add(sb);
				break;
			case 68://TS
				//champ lenogueur de l'option
				optionLength = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				//debut de la chaine representant l'option
				sb.append("\t\tTime Stamp (TS): length ");
				sb.append(optionLength+"\n\t\t");
				pointer+=2;
				//offset
				String offset = options.substring(pointer, pointer+2);
				sb.append("\t\t\tOffset: 0x" + offset+"\n");
				pointer+=2;
				//overflow
				String overflw = options.substring(pointer, pointer+1);
				sb.append("\t\t\tOverflw : " + StringUtility.hexaToInt(overflw) + "(0x"+overflw+")\n");
				pointer++;
				//flags
				String flagsTS = options.substring(pointer, pointer+1);
				sb.append("\t\t\tFlags : " + StringUtility.hexaToInt(flagsTS) + "(0x"+flagsTS +")\n\t\t\t\t");
				switch(StringUtility.hexaToInt(flagsTS))
				{
				case 0:
					sb.append("time stamps only\n");
					break;
				case 1:
					sb.append("each timestamp is preceded with internet ID of the registering entity\n");
					break;
				case 3:
					sb.append("the internet ID fields are prespecified\n");
					break;
				default:
					break;
				}
				pointer++;
				//internet ID
				String internetID = options.substring(pointer, pointer+8);
				sb.append("\t\t\tInternet ID : (0x"+internetID+")\n");
				pointer+=8;
				//time stamp
				String timestamp = options.substring(pointer, pointer+8);
				sb.append("\t\t\tTime Stamp : (0x" + timestamp+")");
				//ajout de l'option dans la chaine finale
				sj.add(sb);
				break;
			default://autres options si possible
				//champ lenogueur de l'option\t\tTime Stamp (TS): length "
				optionLength = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				//debut de la chaine representant l'option
				sb.append("\t\tUnknown option " + valeurOption+" : \n\t\t\tlength ");
				pointer+=2;
				sb.append(optionLength+"\n\t\t\tvalue : "+options.substring(pointer, optionLength-4));	
				sj.add(sb);
				break;
			}
		}
		return sj.toString();
	}
}
