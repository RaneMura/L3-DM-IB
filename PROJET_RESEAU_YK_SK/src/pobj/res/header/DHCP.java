package pobj.res.header;

import java.util.ArrayList;

import java.util.StringJoiner;

import pobj.exceptions.TrameTooShortException;
import pobj.res.StringUtility;

/**
 * Classe gerant une entete DHCP
 * @author Sharane et Yannis
 *
 */
public class DHCP extends Header {
	//constantes representant l'indice des champs de l'entete DHCP dans la liste des champs
	public static final int OPCODE_INDICE = 0;
	public static final int HARDWARE_TYPE_INDICE = 1;
	public static final int HARDWARE_ADDRESS_LENGTH_INDICE = 2;
	public static final int HOPS_INDICE = 3;
	public static final int TRANSACTION_ID_INDICE = 4;
	public static final int SECONDS_INDICE = 5;
	public static final int FLAGS_INDICE = 6;
	public static final int CLIENT_IP_INDICE = 7;
	public static final int YOUR_IP_INDICE = 8;
	public static final int SERVER_IP_INDICE = 9;
	public static final int GATEWAY_IP_INDICE = 10;
	public static final int CLIENT_HARDWARE_ADDRESS_INDICE = 11;
	public static final int CLIENT_HARDWARE_ADDRESS_PADDING_INDICE = 12;
	public static final int SERVER_NAME_INDICE = 13;
	public static final int BOOT_FILE_NAME_INDICE = 14;
	public static final int MAGIC_COOKIE_INDICE = 15;
	public static final int OPTIONS_INDICE = 16;
	
	private String valeur;
	
	
	/**
	 * Construit une entete DHCP
	 * @param value Chaine de longueur variable composee d'octets sans espaces
	 * @throws TrameTooShortException 
	 */
	
	public DHCP(String value) throws TrameTooShortException
	{
		valeur = value;
		if(value.length()<480)throw new TrameTooShortException();
		String opcode = value.substring(0, 2);
		String hardwareType = value.substring(2, 4);
		String hardwareAddressLength = value.substring(4,6);
		String hops = value.substring(6,8);
		
		
		String transactionId = value.substring(8,16);
		
		String seconds = value.substring(16,20);
		String flags = value.substring(20,24);
		
		String clientIp = value.substring(24,32);
		String yourIp = value.substring(32,40);
		String serverIp = value.substring(40,48);
		String gatewayIp = value.substring(48,56);
		String clientMACAddress = value.substring(56,68);
		String clientHardwareAddressPad = value.substring(68,88);	
		String serverName = value.substring(88,216);	
		String bootFileName = value.substring(216,472);	
		String magicCookie = value.substring(472,480);
		String option = value.substring(480, this.getLength());		
		
		//ajout des champs dans la liste des champs
		this.addField(new Field(opcode, "Message Type"));
		this.addField(new Field(hardwareType, "Hardware Type"));
		this.addField(new Field(hardwareAddressLength, "Hardware Address Length"));
		this.addField(new Field(hops, "Hops"));
		this.addField(new Field(transactionId, "Transaction ID"));
		this.addField(new Field(seconds, "Secons elapsed"));
		this.addField(new Field(flags, "Bootp flags"));
		this.addField(new Field(clientIp, "Client IP address"));
		this.addField(new Field(yourIp, "Your (client) IP address"));
		this.addField(new Field(serverIp, "Next server IP address"));
		this.addField(new Field(gatewayIp, "Relay agent IP address"));
		this.addField(new Field(clientMACAddress, "Client MAC Address"));
		this.addField(new Field(clientHardwareAddressPad, "Client Hardware Address Padding"));
		this.addField(new Field(serverName, "Server Host Name"));
		this.addField(new Field(bootFileName, "Boot File Name"));
		this.addField(new Field(magicCookie, "Magic Cookie"));
		this.addField(new Field(option, "Options"));
		
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		Field op = this.getFields().get(0);
		Field hardtype = this.getFields().get(1);
		Field hardadlen = this.getFields().get(2);
		Field hop = this.getFields().get(3);
		Field transId = this.getFields().get(4);
		Field sec = this.getFields().get(5);
		Field fl = this.getFields().get(6);
		Field cliIp = this.getFields().get(7);
		Field yIp = this.getFields().get(8);
		Field srvIp = this.getFields().get(9);
		Field gateIp = this.getFields().get(10);
		Field clihardadd = this.getFields().get(11);
		Field clipad = this.getFields().get(12);
		Field srvname = this.getFields().get(13);
		Field bootfname = this.getFields().get(14);
		Field magic = this.getFields().get(15);
		Field option = this.getFields().get(16);
		
		sb.append("Dynamic Host Configuration Protocol : \n");
		if (Integer.parseInt(op.getValue(),16)==1)
			sb.append("\t"+op.getName()+" : Boot Request ("+StringUtility.hexaToInt(op.getValue())+") (0x"+op.getValue()+")\n");
		if (Integer.parseInt(op.getValue(),16)==2)
			sb.append("\t"+op.getName()+" : Boot Reply ("+StringUtility.hexaToInt(op.getValue())+") (0x"+op.getValue()+")\n");
		if (Integer.parseInt(hardtype.getValue(),16)==1)
			sb.append("\t"+hardtype.getName()+" :  Ethernet (0x"+hardtype.getValue()+")\n");
		else
			sb.append("\t"+hardtype.getName()+" :  "+ StringUtility.hexaToInt(hardtype.getValue()) +" (0x"+hardtype.getValue()+")\n");
		sb.append("\t"+hardadlen.getName()+" :  "+StringUtility.hexaToInt(hardadlen.getValue())+" (0x"+hardadlen.getValue()+")\n");
		sb.append("\t"+hop.getName()+" :  "+StringUtility.hexaToInt(hop.getValue())+" (0x"+hop.getValue()+")\n");
		sb.append("\t"+transId.getName()+" : 0x"+transId.getValue()+"\n");
		sb.append("\t"+sec.getName()+" :  "+StringUtility.hexaToInt(sec.getValue())+" (0x"+sec.getValue()+")\n");
		sb.append("\t"+fl.getName()+" :  "+StringUtility.hexaToInt(fl.getValue())+" (0x"+fl.getValue()+")\n");
		sb.append("\t"+cliIp.getName()+" :  "+IP.convertHexToIP(cliIp.getValue())+" (0x"+cliIp.getValue()+")\n");
		sb.append("\t"+yIp.getName()+" :  "+IP.convertHexToIP(yIp.getValue())+" (0x"+yIp.getValue()+")\n");
		sb.append("\t"+srvIp.getName()+" :  "+IP.convertHexToIP(srvIp.getValue())+" (0x"+srvIp.getValue()+")\n");
		sb.append("\t"+gateIp.getName()+" :  "+IP.convertHexToIP(gateIp.getValue())+" (0x"+gateIp.getValue()+")\n");
		sb.append("\t"+clihardadd.getName()+" :  "+Ethernet.strToMacAddress(clihardadd.getValue())+" (0x"+clihardadd.getValue()+")\n");
		sb.append("\t"+clipad.getName()+" : (0x"+clipad.getValue()+")\n");
		if (Integer.parseInt(srvname.getValue(),16)==0)
			sb.append("\t"+srvname.getName()+" not given \n");
		else
			sb.append("\t"+srvname.getName()+" :  "+Integer.parseInt(srvname.getValue(),16)+" (0x"+srvname.getValue()+")\n");
		if (Integer.parseInt(bootfname.getValue(),16)==0)
			sb.append("\t"+bootfname.getName()+" not given \n");
		else
			sb.append("\t"+bootfname.getName()+" :  "+Integer.parseInt(bootfname.getValue(),16)+" (0x"+bootfname.getValue()+")\n");
		
		if (Integer.parseInt(magic.getValue(),16)==1669485411)// vérifier avec un split en 4 de 0x63825363 
			sb.append("\t"+magic.getName()+" :  DHCP\n");
		sb.append("\t"+option.getName()+" :\n" + this.getOptions());

		return sb.toString();
	}
	
	/**
	 * Renvoie les options sous forme de chaine de characteres
	 * @return Les options formates
	 */
	private String getOptions()
	{
		StringJoiner sj = new StringJoiner("\n");
		//valeur des options
		String options = this.getFields().get(OPTIONS_INDICE).getValue();
		int pointer = 0;
		//tant que l'on n'est pas arrive a la fin des options
		while(pointer < options.length())
		{
			//creation de la chaine representant l'option
			StringBuilder sb = new StringBuilder();

			//String test = options.substring(pointer, pointer+2);
			//System.out.println(" test : "+test);
			//valeur entiere de l'option
			int valeurOption = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
			//mise a jour du pointeur
			pointer+=2;
			//switch sur la valeur des options//mise a jour du pointeur
			switch(valeurOption)
			{
			case 0://Padding
				sb.append("\t\tPadding");
				sj.add(sb);
				return sj.toString();
			
			case 255://End Option
				sb.append("\t\tOption : ("+valeurOption+") End");
				sj.add(sb);
				break;
			
			case 1://Subnet Mask
				//champ longueur de l'option
				int ol1 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				String opip1 = IP.convertHexToIP(options.substring(pointer+2, pointer+10));
				
				sb.append("\t\tOption : ("+valeurOption+") Subnet Mask\n\t\t\tLength : "+ol1+"\n\t\t\tSubnet Mask : "+opip1+"\n");
				sj.add(sb);
				pointer+=10;
				break;
			
			case 2://Time Offset
				//champ longueur de l'option
				int ol2 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int offs = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
				
				sb.append("\t\tOption : ("+valeurOption+") Time Offset\n\t\t\tLength : "+ol2+"\n\t\t\tDuration : "+offs+ "seconds\n");
				sj.add(sb);
				pointer+=4;
				break;
			
			case 3://Router Option
				//champ longueur de l'option
				int ol3 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter3 = ol3/4;
				
				StringBuilder sc3 = new StringBuilder();
				for(int i=0; i<iter3;i++) {
					sc3.append("\t"+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+") Router Option\n\t\t\tLength : "+ol3+"\n\t\t\tRouters : "+sc3.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter3);
				break;
			
			case 4://Time Server Option
				//champ longueur de l'option
				int ol4 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter4 = ol4/4;
				
				StringBuilder sc4 = new StringBuilder();
				for(int i=0; i<iter4;i++) {
					sc4.append("\t"+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+") Time Server Option\n\t\t\tLength : "+ol4+"\n\t\t\tServers : "+sc4.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter4);
				break;	
				
			case 5://Name Server Option
				//champ longueur de l'option
				int ol5 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter5 = ol5/4;
				
				StringBuilder sc5 = new StringBuilder();
				for(int i=0; i<iter5;i++) {
					sc5.append("\t"+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+") Name Server Option\n\t\t\tLength : "+ol5+"\n\t\t\tServers : "+sc5.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter5);
				break;	
			
			case 6://Domain Name Server Option
				//champ longueur de l'option
				int ol6 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter6 = ol6/4;
				
				StringBuilder sc6 = new StringBuilder();
				for(int i=0; i<iter6;i++) {
					sc6.append("\t"+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+") Domain Name Server Option\n\t\t\tLength : "+ol6+"\n\t\t\tServers : "+sc6.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter6);
				break;	
			
			case 7://Log Server Option
				//champ longueur de l'option
				int ol7 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter7 = ol7/4;
				
				StringBuilder sc7 = new StringBuilder();
				for(int i=0; i<iter7;i++) {
					sc7.append("\t"+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+") Log Server Option\n\t\\t\tLength : "+ol7+"\n\t\t\tServers : "+sc7.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter7);
				break;	
			
			case 8://Cookie Server Option
				//champ longueur de l'option
				int ol8 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter8 = ol8/4;
				
				StringBuilder sc8 = new StringBuilder();
				for(int i=0; i<iter8;i++) {
					sc8.append("\t"+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+") Cookie Server Option\n\t\t\tLength : "+ol8+"\n\t\t\tServers : "+sc8.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter8);
				break;
				
			case 9://LPR Server Option
				//champ longueur de l'option
				int ol9 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter9 = ol9/4;
				
				StringBuilder sc9 = new StringBuilder();
				for(int i=0; i<iter9;i++) {
					sc9.append("\t"+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+") LPR Server Option\n\t\t\tLength : "+ol9+"\n\t\t\tServers : "+sc9.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter9);
				break;
			
			case 10://Impress Server Option
				//champ longueur de l'option
				int ol10 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter10 = ol10/4;
				
				StringBuilder sc10 = new StringBuilder();
				for(int i=0; i<iter10;i++) {
					sc10.append("\t"+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+") Impress Server Option\n\t\t\tLength : "+ol10+"\n\t\t\tServers : "+sc10.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter10);
				break;
				
			case 11:// Resource Location Server Option
				//champ longueur de l'option
				int ol11 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter11 = ol11/4;
				
				StringBuilder sc11 = new StringBuilder();
				for(int i=0; i<iter11;i++) {
					sc11.append("\t"+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+") Resource Location Server Option\n\t\t\tLength : "+ol11+"\n\t\t\tServers : "+sc11.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter11);
				break;
			
			case 12:// Host Name Option
				//champ longueur de l'option
				int ol12 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String name12 = options.substring(pointer+2, pointer+2+(2*ol12));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Host Name Option\n\t\t\tLength : "+ol12+"\n\t\t\tHost Name : "+name12+"\n");
				sj.add(sb);
				pointer+=(2+2*ol12);
				break;
			
			case 13:// Boot File Option
				//champ longueur de l'option
				int ol13 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int file13 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
				int size13 =  StringUtility.hexaToInt(options.substring(pointer+4, pointer+6));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Boot File Option\n\t\t\tLength : "+ol13+"\n\t\t\tFile Length : "+file13+"\n\t\t\tSize : "+size13+"\n");
				sj.add(sb);
				pointer+=6;
				break;
			
			case 14:// Merit Dump File
				//champ longueur de l'option
				int ol14 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String path14 = options.substring(pointer+2, pointer+2+(2*ol14));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Merit Dump File \n\t\t\tLength : "+ol14+"\n\t\t\tDump File Pathname : "+path14+"\n");
				sj.add(sb);
				pointer+=(2+2*ol14);
				break;
			
			case 15:// Domain Name
				//champ longueur de l'option
				int ol15 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String name15 = options.substring(pointer+2, pointer+2+(2*ol15));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Domain Name \n\t\t\tLength : "+ol15+"\n\t\t\tDomain Name : "+name15+"\n");
				sj.add(sb);
				pointer+=(2+2*ol15);
				break;
		
			case 16://Swap Server
				//champ longueur de l'option
				int ol16 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				String opip16 = IP.convertHexToIP(options.substring(pointer+2, pointer+10));
				
				sb.append("\t\tOption : ("+valeurOption+") Swap Server\n\t\t\tLength : "+ol16+"\n\t\t\tSwap Server Address : "+opip16+"\n");
				sj.add(sb);
				pointer+=10;
				break;
				
				
			case 17:// Root Path
				//champ longueur de l'option
				int ol17 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String path17 = options.substring(pointer+2, pointer+2+(2*ol17));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Root Path \n\t\t\tLength : "+ol17+"\n\t\t\tRoot Disk Pathname : "+path17+"\n");
				sj.add(sb);
				pointer+=(2+2*ol17);
				break;	
			
			case 18:// Extensions Path
				//champ longueur de l'option
				int ol18 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String path18 = options.substring(pointer+2, pointer+2+(2*ol18));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Extensions Path \n\t\t\tLength : "+ol18+"\n\t\t\t Extensions Pathname : "+path18+"\n");
				sj.add(sb);
				pointer+=(2+2*ol18);
				break;	
			
			case 19:// IP Forwarding Enable/Disable
				//champ longueur de l'option
				int ol19 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val19 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				if (val19==0) {
					sb.append("\t\tOption : ("+valeurOption+") IP Forwarding Enable/Disable \n\t\t\tLength : "+ol19+"\n\t\t\tAction : Disable ("+val19+")\n");
				}
				if (val19==1) {
					sb.append("\t\tOption : ("+valeurOption+") IP Forwarding Enable/Disable \n\t\t\tLength : "+ol19+"\n\t\t\tAction : Enable ("+val19+")\n");
				}
				sj.add(sb);
				pointer+=4;
				break;	
			
			case 20:// Non-Local Source Routing Enable/Disable
				//champ longueur de l'option
				int ol20 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val20 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				if (val20==0) {
					sb.append("\t\tOption : ("+valeurOption+") Non-Local Source Routing Enable/Disable \n\t\t\tLength : "+ol20+"\n\t\t\tAction : Disable ("+val20+")\n");
				}
				if (val20==1) {
					sb.append("\t\tOption : ("+valeurOption+") Non-Local Source Routing Enable/Disable \n\t\t\tLength : "+ol20+"\n\t\t\tAction : Enable ("+val20+")\n");
				}
				sj.add(sb);
				pointer+=4;
				break;
			
			case 21:// Policy Filter Option
				//champ longueur de l'option
				int ol21 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter21 = ol21/4;
				
				StringBuilder sc21 = new StringBuilder();
				for(int i=0; i<iter21;i+=2) {
					sc21.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
					sc21.append("\t Mask : "+IP.convertHexToIP(options.substring(pointer+2+(8*(i+1)), pointer+10+(8*(i+1)))+"\n"));
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Policy Filter Option\n\t\t\tLength : "+ol21+"\n\t\t\tAddresses : "+sc21.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter21);
				break;
				
				
			case 22:// Maximum Datagram Reassembly Size
				//champ longueur de l'option
				int ol22 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int size22 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+6));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Maximum Datagram Reassembly Size \n\t\t\tLength : "+ol22+"\n\t\t\t Size : "+size22+"\n");
				sj.add(sb);
				pointer+=6;
				break;
			
			case 23:// Default IP Time-to-live
				//champ longueur de l'option
				int ol23 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int ttl23 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Default IP Time-to-live \n\t\t\tLength : "+ol23+" \n\t\t\tTime to leave : "+ttl23+"\n");
				sj.add(sb);
				pointer+=4;
				break;
			
			case 24:// Path MTU Aging Timeout Option
				//champ longueur de l'option
				int ol24 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int time24 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+10));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Path MTU Aging Timeout Option \n\t\t\tLength : "+ol24+" \n\t\t\tTimeout : "+time24+"\n");
				sj.add(sb);
				pointer+=10;
				break;
			
			case 25:// Path MTU Plateau Table Option
				//champ longueur de l'option
				int ol25 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter25 = ol25/2;
				
				StringBuilder sc25 = new StringBuilder();
				for(int i=0; i<iter25;i++) {
					sc25.append("\t Size : "+IP.convertHexToIP(options.substring(pointer+2+(4*i), pointer+6+(4*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Path MTU Plateau Table Option\n\t\t\tLength : "+ol25+"\n\t\t\tAddresses : "+sc25.toString()+"\n");
				sj.add(sb);
				pointer+=(10+6*iter25);
				break;
				
				
			case 26:// Interface MTU Option
				//champ longueur de l'option
				int ol26 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val26 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+6));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Interface MTU Option \n\t\t\tLength : "+ol26+" \n\t\t\tValue : "+val26+"\n");
				sj.add(sb);
				pointer+=6;
				break;			
				
			case 27:// All Subnets are Local Option
				//champ longueur de l'option
				int ol27 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val27 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				if (val27==0) {
					sb.append("\t\tOption : ("+valeurOption+") All Subnets are Local Option \n\t\t\tLength : "+ol27+"\n\t\t\tAction : Disable ("+val27+")\n");
				}
				if (val27==1) {
					sb.append("\t\tOption : ("+valeurOption+") All Subnets are Local Option \n\t\t\tLength : "+ol27+"\n\t\t\tAction : Enable ("+val27+")\n");
				}
				sj.add(sb);
				pointer+=4;
				break;
			
			case 28://Broadcast Address Option
				//champ longueur de l'option
				int ol28 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				String opip28 = IP.convertHexToIP(options.substring(pointer+2, pointer+10));
				
				sb.append("\t\tOption : ("+valeurOption+") Broadcast Address Option\n\t\t\tLength : "+ol28+"\n\t\t\tBroadcast Address : "+opip28+"\n");
				sj.add(sb);
				pointer+=10;
				break;
			
			case 29:// Perform Mask Discovery Option
				//champ longueur de l'option
				int ol29 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val29 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				if (val29==0) {
					sb.append("\t\tOption : ("+valeurOption+") Perform Mask Discovery Option \n\t\t\tLength : "+ol29+"\n\t\t\tAction : Disable ("+val29+")\n");
				}
				if (val29==1) {
					sb.append("\t\tOption : ("+valeurOption+") Perform Mask Discovery Option \n\t\t\tLength : "+ol29+"\n\t\t\tAction : Enable ("+val29+")\n");
				}
				sj.add(sb);
				pointer+=4;
				break;
			
			case 30:// Mask Supplier Option
				//champ longueur de l'option
				int ol30 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val30 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				if (val30==0) {
					sb.append("\t\tOption : ("+valeurOption+") Mask Supplier Option \n\t\t\tLength : "+ol30+"\n\t\t\tAction : Disable ("+val30+")\n");
				}
				if (val30==1) {
					sb.append("\t\tOption : ("+valeurOption+") Mask Supplier Option \n\t\t\tLength : "+ol30+"\n\t\t\tAction : Enable ("+val30+")\n");
				}
				sj.add(sb);
				pointer+=4;
				break;
				
			case 31:// Perform Router Discovery Option
				//champ longueur de l'option
				int ol31 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val31 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				if (val31==0) {
					sb.append("\t\tOption : ("+valeurOption+") Perform Router Discovery Option \n\t\t\tLength : "+ol31+"\n\t\t\tAction : Disable ("+val31+")\n");
				}
				if (val31==1) {
					sb.append("\t\tOption : ("+valeurOption+") Perform Router Discovery Option \n\t\t\tLength : "+ol31+"\n\t\t\tAction : Enable ("+val31+")\n");
				}
				sj.add(sb);
				pointer+=4;
				break;
			
			case 32://Router Solicitation Address Option
				//champ longueur de l'option
				int ol32 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				String opip32 = IP.convertHexToIP(options.substring(pointer+2, pointer+10));
				
				sb.append("\t\tOption : ("+valeurOption+") Router Solicitation Address Option \n\t\t\tLength : "+ol32+"\n\t\t\tAddress : "+opip32+"\n");
				sj.add(sb);
				pointer+=10;
				break;

			case 33:// Static Route Option
				//champ longueur de l'option
				int ol33 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter33 = ol33/4;
				
				StringBuilder sc33 = new StringBuilder();
				for(int i=0; i<iter33;i+=2) {
					sc33.append("\t Destination Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
					sc33.append("\t Router : "+IP.convertHexToIP(options.substring(pointer+2+(8*(i+1)), pointer+10+(8*(i+1)))+"\n"));
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Static Route Option\n\t\t\tLength : "+ol33+"\n\t\t\tAddresses : "+sc33.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter33);
				break;
			
			case 34:// Trailer Encapsulation Option
				//champ longueur de l'option
				int ol34 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val34 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				if (val34==0) {
					sb.append("\t\tOption : ("+valeurOption+") Trailer Encapsulation Option \n\t\t\tLength : "+ol34+"\n\t\t\tAction : Disable ("+val34+")\n");
				}
				if (val34==1) {
					sb.append("\t\tOption : ("+valeurOption+") Trailer Encapsulation Option \n\t\t\tLength : "+ol34+"\n\t\t\tAction : Enable ("+val34+")\n");
				}
				sj.add(sb);
				pointer+=4;
				break;
			
			case 35:// ARP Cache Timeout Option
				//champ longueur de l'option
				int ol35 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int time35 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+10));
					
				
				sb.append("\t\tOption : ("+valeurOption+") ARP Cache Timeout Option \n\t\t\tLength : "+ol35+" \n\t\t\tTime : "+time35+"\n");
				sj.add(sb);
				pointer+=10;
				break;
			
			case 36:// Ethernet Encapsulation Option
				//champ longueur de l'option
				int ol36 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val36 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				if (val36==0) {
					sb.append("\t\tOption : ("+valeurOption+") Ethernet Encapsulation Option \n\t\t\tLength : "+ol36+"\n\t\t\tAction : Ethernet II ("+val36+")\n");
				}
				if (val36==1) {
					sb.append("\t\tOption : ("+valeurOption+") Ethernet Encapsulation Option \n\t\t\tLength : "+ol36+"\n\t\t\tAction : IEEE 802.3 ("+val36+")\n");
				}
				sj.add(sb);
				pointer+=4;
				break;
				
				
			case 37:// TCP Default TTL Option
				//champ longueur de l'option
				int ol37 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int time37 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				
				sb.append("\t\tOption : ("+valeurOption+") ATCP Default TTL Option \n\t\t\tLength : "+ol37+" \n\t\t\tTTL : "+time37+"\n");
				sj.add(sb);
				pointer+=4;
				break;
				
	
			case 38:// TCP Keepalive Interval Option
				//champ longueur de l'option
				int ol38 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int time38 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+10));
					
				
				sb.append("\t\tOption : ("+valeurOption+") TCP Keepalive Interval Option \n\t\t\tLength : "+ol38+" \n\t\t\tTime : "+time38+"\n");
				sj.add(sb);
				pointer+=10;
				break;
			
			case 39:// TCP Keepalive Garbage Option
				//champ longueur de l'option
				int ol39 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val39 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				if (val39==0) {
					sb.append("\t\tOption : ("+valeurOption+") TCP Keepalive Garbage Option \n\t\t\tLength : "+ol39+"\n\t\t\tAction : Garbage octet shoud not be sent ("+val39+")\n");
				}
				if (val39==1) {
					sb.append("\t\tOption : ("+valeurOption+") TCP Keepalive Garbage Option \n\t\t\tLength : "+ol39+"\n\t\t\tAction : Garbage octet shoud be sent ("+val39+")\n");
				}
				sj.add(sb);
				pointer+=4;
				break;
			
			case 40:// Network Information Service Domain Option
				//champ longueur de l'option
				int ol40 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String name40 = options.substring(pointer+2, pointer+2+(2*ol40));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Network Information Service Domain Option \n\t\t\tLength : "+ol40+"\n\t\t\tNIS Domain Name : "+name40+"\n");
				sj.add(sb);
				pointer+=(2+2*ol40);
				break;	
				
				
			case 41:// Network Information Servers Option
				//champ longueur de l'option
				int ol41 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter41 = ol41/4;
				
				StringBuilder sc41 = new StringBuilder();
				for(int i=0; i<iter41;i++) {
					sc41.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Network Information Servers Option\n\t\t\tLength : "+ol41+"\n\t\t\tAddresses : "+sc41.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter41);
				break;
			
			case 42:// Network Time Protocol Servers Option
				//champ longueur de l'option
				int ol42 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter42 = ol42/4;
				
				StringBuilder sc42 = new StringBuilder();
				for(int i=0; i<iter42;i++) {
					sc42.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Network Time Protocol Servers Option\n\t\t\tLength : "+ol42+"\n\t\t\tIP Addresses : "+sc42.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter42);
				break;
				
				
				
				
				
			case 43: //Vendor Specific Information
				//champ longueur de l'option
				int ol43 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				ArrayList<Integer>  dico43 = new ArrayList<Integer>();
				for(int i =0; i<ol43;i++) {
					int val43 = StringUtility.hexaToInt(options.substring(pointer+2+(2*i), pointer+2+(2*i+2)));
					dico43.add(val43);
				}
		
				sb.append("\t\tOption : ("+valeurOption+")  Vendor Specific Information \n\t\t\tLength : "+ol43+"\n\t\t\tVendor Specific Information: "+ dico43.toString()+"\n");
				sj.add(sb);
				pointer+=(2+2*ol43);
				break;
					
				
			case 44:// NetBIOS over TCP/IP Name Server Option
				//champ longueur de l'option
				int ol44 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter44 = ol44/4;
				
				StringBuilder sc44 = new StringBuilder();
				for(int i=0; i<iter44;i++) {
					sc44.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  NetBIOS over TCP/IP Name Server Option\n\t\t\tLength : "+ol44+"\n\t\t\tServer Addresses : "+sc44.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter44);
				break;
			
			case 45:// NetBIOS over TCP/IP Datagram Distribution Server Option
				//champ longueur de l'option
				int ol45 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter45 = ol45/4;
				
				StringBuilder sc45 = new StringBuilder();
				for(int i=0; i<iter45;i++) {
					sc45.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  NetBIOS over TCP/IP Datagram Distribution Server Option \n\t\t\tLength : "+ol45+"\n\t\t\tServer Addresses : "+sc45.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter45);
				break;
			
			case 46:// NetBIOS over TCP/IP Node Type Option
				//champ longueur de l'option
				int ol46 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val46 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				if (val46==1) {
					sb.append("\t\tOption : ("+valeurOption+") NetBIOS over TCP/IP Node Type Option \n\t\t\tLength : "+ol46+"\n\t\t\tType : B-node ("+val46+")\n");
				}
				if (val46==2) {
					sb.append("\t\tOption : ("+valeurOption+") NetBIOS over TCP/IP Node Type Option \n\t\t\tLength : "+ol46+"\n\t\t\tType : P-node ("+val46+")\n");
				}
				if (val46==3) {
					sb.append("\t\tOption : ("+valeurOption+") NetBIOS over TCP/IP Node Type Option \n\t\t\tLength : "+ol46+"\n\t\t\tType : M-node ("+val46+")\n");
				}
				if (val46==4) {
					sb.append("\t\tOption : ("+valeurOption+") NetBIOS over TCP/IP Node Type Option \n\t\t\tLength : "+ol46+"\n\t\t\tType : H-node ("+val46+")\n");
				}
				sj.add(sb);
				pointer+=4;
				break;
				
			case 47:// NetBIOS over TCP/IP Scope Option
				//champ longueur de l'option
				int ol47 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String name47 = options.substring(pointer+2, pointer+2+(2*ol47));
					
				
				sb.append("\t\tOption : ("+valeurOption+") NetBIOS over TCP/IP Scope Option \n\t\t\tLength : "+ol47+"\n\t\t\tNetBios Scope : "+name47+"\n");
				sj.add(sb);
				pointer+=(2+2*ol47);
				break;	
			
			case 48:// X Window System Font Server Option
				//champ longueur de l'option
				int ol48 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter48 = ol48/4;
				
				StringBuilder sc48 = new StringBuilder();
				for(int i=0; i<iter48;i++) {
					sc48.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  X Window System Font Server Option  \n\t\t\tLength : "+ol48+"\n\t\t\tAddresses : "+sc48.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter48);
				break;
			
			case 49://X Window System Display Manager Option
				//champ longueur de l'option
				int ol49 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter49 = ol49/4;
				
				StringBuilder sc49 = new StringBuilder();
				for(int i=0; i<iter49;i++) {
					sc49.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  X Window System Display Manager Option  \n\t\t\tLength : "+ol49+"\n\t\t\tIP Addresses : "+sc49.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter49);
				break;
			
				
			case 50://Requested IP Address
				//champ longueur de l'option
				int ol50 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				String opip50 = IP.convertHexToIP(options.substring(pointer+2, pointer+10));
				
				sb.append("\t\tOption : ("+valeurOption+") Requested IP Address \n\t\t\tLength : "+ol50+"\n\t\t\tRequested IP Address : "+opip50+"\n");
				sj.add(sb);
				pointer+=10;
				break;
				
			case 51:// IP Address Lease Time
				//champ longueur de l'option
				int ol51 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int time51 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+10));
					
				
				sb.append("\t\tOption : ("+valeurOption+") IP Address Lease Time \n\t\t\tLength : "+ol51+" \n\t\t\tIP Address Lease Time : ("+time51+"s)\n");
				sj.add(sb);
				pointer+=10;
				break;
			
			case 52:// Option Overload
				//champ longueur de l'option
				int ol52 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val52 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
					
				if (val52==1) {
					sb.append("\t\tOption : ("+valeurOption+") Option Overload \n\t\t\tLength : "+ol52+"\n\t\t\tType : file field is used to hold options ("+val52+")\n");
				}
				if (val52==2) {
					sb.append("\t\tOption : ("+valeurOption+") Option Overload \n\t\t\tLength : "+ol52+"\n\t\t\tType : sname field is used to hold options ("+val52+")\n");
				}
				if (val52==3) {
					sb.append("\t\tOption : ("+valeurOption+") Option Overload \n\t\t\tLength : "+ol52+"\n\t\t\tType : both fields are used to hold options ("+val52+")\n");
				}

				sj.add(sb);
				pointer+=4;
				break;
				
				
			case 53: //DHCP MESSAGE TYPE
				int ol53 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int otype53 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
				ArrayList<String>  dico = new ArrayList<>();
				dico.add("Discover");
				dico.add("Offer");
				dico.add("Request");
				dico.add("Decline");
				dico.add("ACK");
				dico.add("NAK");
				dico.add("RELEASE");
				dico.add("INFORM");
		
				sb.append("\t\tOption : ("+valeurOption+") DHCP Message Type\n\t\t\tLength : "+ol53+"\n\t\t\tDHCP : "+ dico.get(otype53-1)+"\n");
				sj.add(sb);
				pointer+=4;
				break;
			
			case 54://Server Identifier
				//champ longueur de l'option
				int ol54 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				String opip54 = IP.convertHexToIP(options.substring(pointer+2, pointer+10));
				
				sb.append("\t\tOption : ("+valeurOption+") DHCP Server Identifier \n\t\t\tLength : "+ol54+"\n\t\t\tDHCP Server Identifier : "+opip54+"\n");
				sj.add(sb);
				pointer+=10;
				break;
			
				
			case 55: //Parameter Request List
				int ol55 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
			
				ArrayList<Integer>  dico55 = new ArrayList<Integer>();
				for(int i =0; i<ol55;i++) {
					int val55 = StringUtility.hexaToInt(options.substring(pointer+2+(2*i), pointer+2+(2*i+2)));
					dico55.add(val55);
				}
				ArrayList<String>  dico55name = new ArrayList<String>();
				dico55name.add("Pad"); //0
				dico55name.add("Subnet Mask");
				dico55name.add("Time Offset");
				dico55name.add("Router");
				dico55name.add("Time Server Addresses");
				dico55name.add("Name Server Addresses");
				dico55name.add("Domain Server Addresses");
				dico55name.add("Log Server Addresses");
				dico55name.add("Quotes Server Addresses");
				dico55name.add("LPR Server Addresses");
				dico55name.add("Impress Server Addresses");
				dico55name.add("RLP Server Addresses");
				dico55name.add("Hostname");
				dico55name.add("Boot File Size");
				dico55name.add("Merit Dump File");
				dico55name.add("Domain Name");
				dico55name.add("Swap Server Addresses");
				dico55name.add("Root Path");
				dico55name.add("Extension File");
				dico55name.add("IP Forward On/Off");
				dico55name.add("Source Route On/Off");
				dico55name.add("Policy Filter");
				dico55name.add("Max Datagram Reassembly Size");
				dico55name.add("Default IP TTL");
				dico55name.add("MTU Timeout");
				dico55name.add("MTU Plateau");
				dico55name.add("MTU Interface");
				dico55name.add("MTU Subnet");
				dico55name.add("Broadcast Address");
				dico55name.add("Mask Discovery");
				dico55name.add("Mask Supplier");
				dico55name.add("Router Discovery");
				dico55name.add("Router Request");
				dico55name.add("Static Route");
				dico55name.add("Trailer Encapsulation");
				dico55name.add("ARP Timeout");
				dico55name.add("Ethernet Encapsulation");
				dico55name.add("Default TCP TTL");
				dico55name.add("TCP Keepalive Interval");
				dico55name.add("TCP Keepalive Garbage");
				dico55name.add("NIS Domain Name");
				dico55name.add("NIS Servers Adresses");
				dico55name.add("NTP Server Addresses");
				dico55name.add("Vendor Specific Information");
				dico55name.add("NETBIOS Name Servers");
				dico55name.add("NETBIOS Datagram Distribution");
				dico55name.add("NETBIOS Node Type");
				dico55name.add("NETBIOS Scope");
				dico55name.add("X Window Font Server");
				dico55name.add("X Window Display Manager");
				dico55name.add("Address Request");
				dico55name.add("Address Time");
				dico55name.add("Overload");		
				dico55name.add("DHCP Message Type");
				dico55name.add("DHCP Server Identification");
				dico55name.add("Parameter Request List");
				dico55name.add("DHCP Error Message");
				dico55name.add("DHCP Maximum Message Size");
				dico55name.add("DHCP Renewal (T1) Time");
				dico55name.add("DHCP Rebinding (T2) Time");
				dico55name.add("Class Identifier");
				dico55name.add("Client Identifier");
				dico55name.add("NetWare/IP Domain Name");
				dico55name.add("NetWare/IP sub Options");
				dico55name.add("NIS+ v3 Client Domain Name");
				dico55name.add("NIS+ v3 Server Addresses");
				dico55name.add("TFTP Server Name");
				dico55name.add("Boot File Name");
				dico55name.add("Home Agent Addresses");
				dico55name.add("Simple Mail Server Addresses");
				dico55name.add("Post Office Server Addresses");
				dico55name.add("Network News Server Addresses");
				dico55name.add("WWW Server Addresses");
				dico55name.add("Finger Server Addresses");
				dico55name.add("Chat Server Addresses");
				dico55name.add("StreetTalk Server Addresses");
				dico55name.add("ST Directory Assist. Addresses");
				dico55name.add("User Class Information");
				dico55name.add("Directory Agent Information");
				dico55name.add("Service Location Agent Scope");
				dico55name.add("Rapid Commit");
				dico55name.add("Fully Qualified Domain Name");
				dico55name.add("Relay Agent Information");
				dico55name.add("Internet Storage Name Service");
				dico55name.add("unassigned");
				dico55name.add("NDS Servers");
				dico55name.add("NDS Tree Name");
				dico55name.add("NDS Context");
				dico55name.add("BCMCS Controller Domain Name List");
				dico55name.add("BCMCS Controller IPv4 Address");
				dico55name.add("Authentication");
				dico55name.add("Client Last Transaction Time Option");
				dico55name.add("Associated-IP Option");
				dico55name.add("Client System Architecture");
				dico55name.add("Client Network Device Interface");
				dico55name.add("Lightweight Directory Access Protocol");
				dico55name.add("unassigned");
				dico55name.add("UUID/GUID-based Client Identifier");
				dico55name.add("Open Group's User Authentication");
				dico55name.add("GEOCONF_CIVIC");
				dico55name.add("PCode");	//100
				dico55name.add("TCode");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("IPv6-Only Preferred");
				dico55name.add("OPTION_DHCP4O6_S46_SADDR");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("NetInfo Parent Server Address");
				dico55name.add("NetInfo Parent Server Tag");
				dico55name.add("DHCP Captive-Portal");
				dico55name.add("unassigned");
				dico55name.add("DHCP Auto-Configuration");
				dico55name.add("Name Service Search");
				dico55name.add("Subnet Selection Option");
				dico55name.add("DNS domain search list");
				dico55name.add("SIP Servers DHCP Option");
				dico55name.add("Classless Static Route Option");
				dico55name.add("CableLabs Client Configuration");
				dico55name.add("GeoConf Option");
				dico55name.add("Vendor-Identifying Vendor Class");
				dico55name.add("Vendor-Identifying Vendor-Specific Information");	
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("PXE - undefined (vendor specific),Etherboot signature,DOCSIS full security Server IP address,TFTP Server IP address");
				dico55name.add("PXE - undefined (vendor specific), Kernel options, Call Server IP address");
				dico55name.add("PXE - undefined (vendor specific),Ethernet interface ,Discrimination string (to identify vendor)");
				dico55name.add("PXE - undefined (vendor specific), Remote statistics server IP address");
				dico55name.add("PXE - undefined (vendor specific), IEEE 802.1Q VLAN ID");
				dico55name.add("PXE - undefined (vendor specific), IEEE 802.1D/p Layer 2 Priority");
				dico55name.add("PXE - undefined (vendor specific), Diffserv Code Point (DSCP) for VoIP signalling and media streams");
				dico55name.add("PXE - undefined (vendor specific), HTTP Proxy for phone-specific applications");
				dico55name.add("OPTION_PANA_AGENT");
				dico55name.add("OPTION_V4_LOST");
				dico55name.add("CAPWAP Access Controller addresses");
				dico55name.add("OPTION-IPv4_Address-MoS");
				dico55name.add("OPTION-IPv4_FQDN-MoS");
				dico55name.add("SIP UA Configuration Service Domains");
				dico55name.add("OPTION-IPv4_Address-ANDSF");
				dico55name.add("OPTION_V4_SZTP_REDIRECT");
				dico55name.add("Geospatial Location with Uncertainty");
				dico55name.add("Forcerenew Nonce Capable");
				dico55name.add("RDNSS Selection");
				dico55name.add("OPTION_V4_DOTS_RI");
				dico55name.add("OPTION_V4_DOTS_ADDRESS");
				dico55name.add("unassigned");
				dico55name.add("TFTP server address, Etherboot, GRUB configuration path name");
				dico55name.add("status-code");
				dico55name.add("base-time");
				dico55name.add("start-time-of-state");
				dico55name.add("query-start-time");
				dico55name.add("query-end-time");
				dico55name.add("dhcp-state");
				dico55name.add("data-source");
				dico55name.add("OPTION_V4_PCP_SERVER");
				dico55name.add("OPTION_V4_PORTPARAMS");
				dico55name.add("unassigned");
				dico55name.add("OPTION_MUD_URL_V4");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("Etherboot (Tentatively Assigned - 2005-06-23)");
				dico55name.add("IP Telephone (Tentatively Assigned - 2005-06-23)");
				dico55name.add("Etherboot (Tentatively Assigned - 2005-06-23), PacketCable and CableHome (replaced by 122)");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("PXELINUX Magic");
				dico55name.add("Configuration File");
				dico55name.add("Path Prefix");
				dico55name.add("Reboot Time");
				dico55name.add("OPTION_6RD");
				dico55name.add("OPTION_V4_ACCESS_DOMAIN");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("Subnet Allocation Option");
				dico55name.add("Virtual Subnet Selection (VSS) Option");
				dico55name.add("unassigned");
				dico55name.add("unassigned");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("Reserved (Private Use)");
				dico55name.add("End");
		
				sb.append("\t\tOption : ("+valeurOption+") Parameter Request List \n\t\t\tLength : "+ol55+"\n");
				for (int i=0; i<dico55.size();i++) {
					sb.append("\t\t\tParameter Request List Item : ("+dico55.get(i)+") "+dico55name.get(dico55.get(i))+"\n");
				}
				sj.add(sb);
				pointer+=(2+2*ol55);
				break;
			
			case 56:// Message
				//champ longueur de l'option
				int ol56 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String name56 = options.substring(pointer+2, pointer+2+(2*ol56));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Message \n\t\t\tLength : "+ol56+"\n\t\t\tText : "+name56+"\n");
				sj.add(sb);
				pointer+=(2+2*ol56);
				break;	
			
			
			case 57:// Maximum DHCP Message Size
				//champ longueur de l'option
				int ol57 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int val57 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+6));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Maximum DHCP Message Size \n\t\t\tLength : "+ol57+" \n\t\t\tMessage Length : "+val57+"\n");
				sj.add(sb);
				pointer+=6;
				break;
			
			case 58:// Renewal (T1) Time Value
				//champ longueur de l'option
				int ol58 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int time58 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+10));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Renewal Time Value \n\t\t\tLength : "+ol58+" \n\t\t\tRenewal Time Value : ("+time58+"s)\n");
				sj.add(sb);
				pointer+=10;
				break;
			
			case 59:// Rebinding (T2) Time Value
				//champ longueur de l'option
				int ol59 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				int time59 =  StringUtility.hexaToInt(options.substring(pointer+2, pointer+10));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Rebinding Time Value \n\t\t\tLength : "+ol59+" \n\t\t\tRebinding Time Value : ("+time59+"s)\n");
				sj.add(sb);
				pointer+=10;
				break;
			
			case 60:// Vendor class identifier
				//champ longueur de l'option
				int ol60 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String name60 =  options.substring(pointer+2, pointer+2+(2*ol60));
					
				
				sb.append("Option : ("+valeurOption+") Vendor class identifier \n\tLength : "+ol60+" \n\tVendor Class Identifier : "+name60+"\n");
				sj.add(sb);
				pointer+=10;
				break;
			
			
			case 61: //Client Identifier
				int ol61 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int otype61 = StringUtility.hexaToInt(options.substring(pointer+2, pointer+4));
				//System.out.println(options.substring(pointer, pointer+4));
				String op61address = Ethernet.strToMacAddress(options.substring(pointer+4, pointer+16));
				if (otype61==1)
					sb.append("\t\tOption : ("+valeurOption+") Client Identifier\n\t\t\tLength : "+ol61+"\n\t\t\tHardware Type : Ethernet (0x01) \n\t\t\tClient MAC Address"+op61address+"\n" );
					sj.add(sb);
				//System.out.println("61\n");
				pointer+=16;
				break;
			
				
				
			case 64:// Network Information Service Domain Option
				//champ longueur de l'option
				int ol64 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String name64 = options.substring(pointer+2, pointer+2+(2*ol64));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Network Information Service Domain Option \n\t\t\tLength : "+ol64+"\n\t\t\tNIS Domain Name : "+name64+"\n");
				sj.add(sb);
				pointer+=(2+2*ol64);
				break;	
				
			case 65://Network Information Service+ Servers Option
				//champ longueur de l'option
				int ol65 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter65 = ol65/4;
				
				StringBuilder sc65 = new StringBuilder();
				for(int i=0; i<iter65;i++) {
					sc65.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Network Information Service+ Servers Option  \n\t\t\tLength : "+ol65+"\n\t\t\tIP Addresses : "+sc65.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter65);
				break;
			
			case 66:// TFTP server name
				//champ longueur de l'option
				int ol66 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String name66 = options.substring(pointer+2, pointer+2+(2*ol66));
					
				
				sb.append("\t\tOption : ("+valeurOption+") TFTP server name \n\t\t\tLength : "+ol66+"\n\t\t\tServer Name : "+name66+"\n");
				sj.add(sb);
				pointer+=(2+2*ol66);
				break;	
								
			case 67:// Bootfile name
				//champ longueur de l'option
				int ol67 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				
				String name67 = options.substring(pointer+2, pointer+2+(2*ol67));
					
				
				sb.append("\t\tOption : ("+valeurOption+") Bootfile Name \n\t\t\tLength : "+ol67+"\n\t\t\tBootfile Name : "+name67+"\n");
				sj.add(sb);
				pointer+=(2+2*ol67);
				break;		
				
				
				
			case 68://Mobile IP Home Agent option
				//champ longueur de l'option
				
				int ol68 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				if (ol68!=0) {
					int iter68 = ol68/4;
				
					StringBuilder sc68 = new StringBuilder();
					for(int i=0; i<iter68;i++) {
						sc68.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
					}	
				
					sb.append("\t\tOption : ("+valeurOption+")  Mobile IP Home Agent Option  \n\t\t\tLength : "+ol68+"\n\t\t\tHome Agent Addresses : "+sc68.toString()+"\n");
					sj.add(sb);
					pointer+=(10+8*iter68);
				}
				if(ol68==0) {
					sb.append("\t\tOption : ("+valeurOption+")  Mobile IP Home Agent Option  \n\t\t\tLength : "+ol68+"\n\t\t\tNo Home Agent Addresses\n");
					sj.add(sb);
					pointer+=2;
				}
				break;
			
			case 69://Simple Mail Transport Protocol (SMTP) Server Option
				//champ longueur de l'option
				int ol69 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter69 = ol69/4;
				
				StringBuilder sc69 = new StringBuilder();
				for(int i=0; i<iter69;i++) {
					sc69.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Simple Mail Transport Protocol (SMTP) Server Option  \n\t\t\tLength : "+ol69+"\n\t\t\tAddresses : "+sc69.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter69);
				break;
			
			
			case 70://Post Office Protocol (POP3) Server Option
				//champ longueur de l'option
				int ol70 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter70 = ol70/4;
				
				StringBuilder sc70 = new StringBuilder();
				for(int i=0; i<iter70;i++) {
					sc70.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Post Office Protocol (POP3) Server Option  \n\t\t\tLength : "+ol70+"\n\t\t\tAddresses : "+sc70.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter70);
				break;
			
			case 71://Network News Transport Protocol (NNTP) Server Option
				//champ longueur de l'option
				int ol71 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter71 = ol71/4;
				
				StringBuilder sc71 = new StringBuilder();
				for(int i=0; i<iter71;i++) {
					sc71.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Network News Transport Protocol (NNTP) Server Option  \n\t\t\tLength : "+ol71+"\n\t\t\tAddresses : "+sc71.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter71);
				break;
			
			case 72://Default World Wide Web (WWW) Server Option
				//champ longueur de l'option
				int ol72 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter72 = ol72/4;
				
				StringBuilder sc72 = new StringBuilder();
				for(int i=0; i<iter72;i++) {
					sc72.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Default World Wide Web (WWW) Server Option  \n\t\t\tLength : "+ol72+"\n\t\t\tAddresses : "+sc72.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter72);
				break;
			
			case 73://Default Finger Server Option
				//champ longueur de l'option
				int ol73 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter73 = ol73/4;
				
				StringBuilder sc73 = new StringBuilder();
				for(int i=0; i<iter73;i++) {
					sc73.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Default Finger Server Option  \n\t\t\tLength : "+ol73+"\n\t\t\tAddresses : "+sc73.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter73);
				break;
			
			case 74://Default Internet Relay Chat (IRC) Server Option
				//champ longueur de l'option
				int ol74 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter74 = ol74/4;
				
				StringBuilder sc74 = new StringBuilder();
				for(int i=0; i<iter74;i++) {
					sc74.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  Default Internet Relay Chat (IRC) Server Option  \n\t\t\tLength : "+ol74+"\n\t\t\tAddresses : "+sc74.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter74);
				break;
				
			case 75://StreetTalk Server Option
				//champ longueur de l'option
				int ol75 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter75 = ol75/4;
				
				StringBuilder sc75 = new StringBuilder();
				for(int i=0; i<iter75;i++) {
					sc75.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  StreetTalk Server Option  \n\t\t\tLength : "+ol75+"\n\t\t\tAddresses : "+sc75.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter75);
				break;
			
			case 76://StreetTalk Directory Assistance (STDA) Server Option
				//champ longueur de l'option
				int ol76 = StringUtility.hexaToInt(options.substring(pointer, pointer+2));
				int iter76 = ol76/4;
				
				StringBuilder sc76 = new StringBuilder();
				for(int i=0; i<iter76;i++) {
					sc76.append("\t Address : "+IP.convertHexToIP(options.substring(pointer+2+(8*i), pointer+10+(8*i)))+"\n");
				}	
				
				sb.append("\t\tOption : ("+valeurOption+")  StreetTalk Directory Assistance (STDA) Server Option  \n\t\t\tLength : "+ol76+"\n\t\t\tAddresses : "+sc76.toString()+"\n");
				sj.add(sb);
				pointer+=(10+8*iter76);
				break;
			

			
			default://autres options si possible
				break;
			}
		}
		return sj.toString();
	}
	
	/**
	 * Renvoie la longueur de l'entete dhcp
	 * @return La longueur de l'entete dhcp
	 */
	@Override
	public int getLength() {
		return valeur.length();
	}

	/**
	 * Renvoie la valeur du champ 'Protocol' de l'entete dhcp
	 * @return La valeur du champ
	 */
	@Override
	public String getNext() {
		return "";
	}

}

