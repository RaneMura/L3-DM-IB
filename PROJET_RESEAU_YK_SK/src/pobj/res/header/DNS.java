package pobj.res.header;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import pobj.exceptions.ErrorValueException;
import pobj.res.StringUtility;

/**
 * Classe gerant une entete DNS
 * @author Sharane et Yannis
 *
 */
public class DNS extends Header{
	//constantes representant l'indice des champs de l'entete DNS dans la liste des champs
	public static final int DNS_IDENTIFICATION = 0;
	public static final int DNS_FLAGS = 1;
	public static final int DNS_NB_QUESTIONS = 2;
	public static final int DNS_NB_ANSWERS = 3;
	public static final int DNS_NB_AUTHORITIES = 4;
	public static final int DNS_NB_ADDITIONALS = 5;
	public static final int DNS_QUESTIONS = 6;
	public static final int DNS_ANSWERS = 7;
	public static final int DNS_AUTHORITIES = 8;
	public static final int DNS_ADDITIONALS = 9;
	
	private int nbQuestions;
	private int nbAnswers;
	private int nbAuthorities;
	private int nbAdditionals;
	private String value;
	private List<String[]> contentRRs = new ArrayList<>();
	
	/**
	 * Construit une entete DNS
	 * @param value Une chaine de charactere representant le contenu de l'entete
	 * @throws ErrorValueException 
	 */
	public DNS(String value) throws ErrorValueException
	{
		//ajout dans des chaines
		String identification = value.substring(0, 4);
		String flags = value.substring(4, 8);
		String nbQuestions = value.substring(8, 12);
		String nbAnswers = value.substring(12, 16);
		String nbAuthorities = value.substring(16, 20);
		String nbAdditionnals = value.substring(20, 24);
		
		//enregistrement des valeurs de tailles
		this.nbQuestions = StringUtility.hexaToInt(nbQuestions);
		this.nbAnswers = StringUtility.hexaToInt(nbAnswers);
		this.nbAuthorities = StringUtility.hexaToInt(nbAuthorities);
		this.nbAdditionals = StringUtility.hexaToInt(nbAdditionnals);
		this.value = value;
		
		/**
		//affichage
		StringJoiner sj = new StringJoiner("\n");
		sj.add(identification);
		sj.add(flags);
		sj.add(nbQuestions+" : "+this.nbQuestions);
		sj.add(nbAnswers+" : "+this.nbAnswers);
		sj.add(nbAuthorities+" : "+this.nbAuthorities);
		sj.add(nbAdditionnals+" : "+this.nbAdditionals);
		System.out.println(sj.toString());
		**/
						
		//ajout dans des chaines
		int endQuestions = 24+this.getQuestionsSize(value.substring(24));
		String questions = value.substring(24, endQuestions);
		//System.out.println("endQuestions : "+endQuestions);
		//System.out.println("Questions : "+questions);
		int endAnswers = endQuestions + this.getRRsSize(value.substring(endQuestions), this.nbAnswers);
		String answers = value.substring(endQuestions, endAnswers);
		//System.out.println("endAnswers : "+endAnswers);
		//System.out.println("Answers : "+answers);
		
		//not necessaries options
		int endAuthorities, endAdditionals;
		String authorities = null;
		String additionals = null;
		if(endAnswers<value.length())
		{
			endAuthorities = endAnswers + this.getRRsSize(value.substring(endAnswers), this.nbAuthorities);
			authorities = value.substring(endAnswers, endAuthorities);
			if(endAuthorities<value.length())
			{
				endAdditionals = endAuthorities + this.getRRsSize(value.substring(endAuthorities), this.nbAdditionals);
				additionals = value.substring(endAuthorities, endAdditionals);
			}
		}
		
		//enregistrement des valeurs de tailles
		this.nbQuestions = StringUtility.hexaToInt(nbQuestions);
		this.nbAnswers = StringUtility.hexaToInt(nbAnswers);
		this.nbAuthorities = StringUtility.hexaToInt(nbAuthorities);
		this.nbAdditionals = StringUtility.hexaToInt(nbAdditionnals);
		this.value = value;
		
		//ajout des champs dans l'entete
		this.addField(new Field(identification, "Identification"));
		this.addField(new Field(flags, "Flags"));
		this.addField(new Field(nbQuestions, "Number of Questions"));
		this.addField(new Field(nbAnswers, "Number of Answers"));
		this.addField(new Field(nbAuthorities, "Number of Authorities"));
		this.addField(new Field(nbAdditionnals, "Number of Additionnals"));
		this.addField(new Field(questions, "Questions"));
		this.addField(new Field(answers, "Answers"));
		if(authorities!=null)this.addField(new Field(authorities, "Authorities"));
		if(additionals!=null)this.addField(new Field(additionals, "Additionnals"));
		
		this.addAllContents();
	}
	
	/**
	 * Renvoie le nom en hexa a partir d'une chaine de charactere representant un RR
	 * @param value Le RR
	 * @return Le nom de domaine sous forme hexa
	 */
	private String getNameHexa(String value)
	{
		String hexaPoint = "2e";//la valeur en hexa d'un point
		StringJoiner sj = new StringJoiner(hexaPoint);
		int pointer = 0;
		String tmp = "";
		while(!tmp.equals("00"))
		{
			StringBuilder sb = new StringBuilder();
			int length = StringUtility.hexaToInt(value.substring(pointer,pointer+2));//longueur du label en octets
			//System.out.println("Length :" + length);
			pointer+=2;
			//on cree chaque label
			for(int j=0; j<length; j+=1)
			{
				tmp = value.substring(pointer, pointer+2);
				sb.append(tmp);
				//System.out.println(tmp);
				pointer+=2;
			}
			//on ajoute chaque label en les separants par un point
			sj.add(sb.toString());			
			tmp = value.substring(pointer, pointer+2);
		}
		return sj.toString();
	}
	
	/**
	 * Renvoie le nom de domaine a partir d'une chaine d'hexa representant le nom
	 * @param value Le nom en hexa
	 * @return Le nom de domaine 
	 */
	private String getName(String value)
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<value.length(); i+=2)
		{
			int tmp = StringUtility.hexaToInt(value.substring(i, i+2));
			char c = (char)tmp;
			sb.append(c);
		}
		//System.out.println("get name: "+sb.toString());
		return sb.toString();
	}
	
	/**
	 * Renvoie la taille du champ questions
	 * @param value La valeur du champ
	 * @return La taille du champ questions
	 * @throws ErrorValueException 
	 */
	private int getQuestionsSize(String value) throws ErrorValueException
	{
		int res=0;
		for(int i=0; i<this.nbQuestions; i++)
		{
			String first2Bytes = StringUtility.hexaToBinary(value.substring(res, res+4));//on recupere les 2 premiers octets sous forme de binaire
			//cas compressed
			if(first2Bytes.charAt(0)=='1' && first2Bytes.charAt(1)=='1')
			{
				res+=3*4;//3*2octets pour les champs name type et class
				while(first2Bytes.charAt(0)=='1' && first2Bytes.charAt(1)=='1')//tant qu'on tombe sur un pointeur
				{
					int dist = this.getDist(first2Bytes);
					System.out.println("Dist :"+dist);
				}
			}
			else
			{
				res+=this.getNameSize(value.substring(res))+2*4;//2*2octets pour les camps type et class
			}
		}
		return res;
	}
	
	/**
	 * Renvoie les 14 derniers bits de 2 octets sous forme d'entier
	 * @param firstBytes Une chaine representant 2 octets sous forme binaire
	 * @return Les 14 derniers bits sous forme d'entier
	 */
	private int getDist(String firstBytes)
	{
		return StringUtility.binToInt(firstBytes.substring(2));
	}
	
	/**
	 * Renvoie la taille d'un champ nom en nombre d'hexa
	 * @param value La chaine representant le nom
	 * @return Le nombre d'hexa dans le nom
	 */
	private int getNameSize(String value)
	{
		int pointer = 0;
		String tmp = "";
		String first2Bytes = StringUtility.hexaToBinary(value.substring(pointer, pointer+4));
		
		while(!tmp.equals("00") && !(first2Bytes.charAt(0)=='1' && first2Bytes.charAt(1)=='1'))
		{
			//System.out.println("sub: "+value.substring(pointer,pointer+2));
			//System.out.println(value);
			int length = StringUtility.hexaToInt(value.substring(pointer,pointer+2));//longueur du label en octets
			pointer+=2;	
			pointer+=(2*length);
			first2Bytes = StringUtility.hexaToBinary(value.substring(pointer, pointer+4));
			//System.out.println("first 2 bytes : "+value.substring(pointer, pointer+4));
			tmp = value.substring(pointer, pointer+2);
		}
		if(tmp.equals("00")) 
		{
			pointer+=2;
		}
		//si on tombe sur un pointeur
		else 
		{
			//System.out.println("pointeur");
			pointer+=4;
		}
		
		return pointer;
	}
	
	/**
	 * Renvoie la taille d'un RR a partir de la chaine representant le RR
	 * @param value La chaine representant un RR
	 * @param nbRRs Le nombre de RRs dans le champ
	 * @return La taille du RR
	 */
	private int getRRsSize(String value, int nbRRs)
	{
		int res=0;
		int dataLength = 0;
		for(int i=0; i<nbRRs; i++)
		{
			String first2Bytes = StringUtility.hexaToBinary(value.substring(res, res+4));//on recupere les 2 premiers octets sous forme de binaire
			//System.out.println(value.substring(res, res+4));
			//System.out.println("First 2 bytes : " + first2Bytes);
			//cas compressed
			if(first2Bytes.charAt(0)=='1' && first2Bytes.charAt(1)=='1')
			{
				res += 3*4+2*4;//4*2octets pour les champs name,type,class + 4octets TTL
				//System.out.println(value.substring(res, res+4));
				dataLength = StringUtility.hexaToInt(value.substring(res, res+4));
				res+=4+2*dataLength;//2octets dataLength + 2*dataLength
				//System.out.println("dataLength : "+dataLength+"\nres : " + res);
			}
			else
			{
				//System.out.println(value.substring(res));
				int tmp = this.getNameSize(value.substring(res))+2*4+2*4;
				dataLength = StringUtility.hexaToInt(value.substring(tmp, tmp+4));
				res+=this.getNameSize(value.substring(res))+3*4+dataLength*2+2*4;//3*2octets pour les camps type,class,dataLength + 4octets TTL + 2*dataLength
				//System.out.println("dataLength : "+dataLength+"\nres : " + res);
			}
			//System.out.println(value.substring(0,res));
		}
		//System.out.println("end : " + value.substring(0,res));
		return res;
	}
	
	/**
	 * Rends les 6 premiers champs d'entete DNS sous forme de chaine
	 * @return Une chaine de characteres representant les 6 premiers champs d'entete
	 */
	private String getMainHeader()
	{
		StringJoiner sj = new StringJoiner("\n");
		for(int i=0; i<6; i++)
		{
			if(i>=2)//champs int
				sj.add(this.getFields().get(i).getName() + " : 0x" + this.getFields().get(i).getValue() + " ("+StringUtility.hexaToInt(this.getFields().get(i).getValue())+")");		
		}
		return sj.toString();
	}
	
	/**
	 * Rend le champ question sous forme de chaine
	 * @param qName Le nom du domaine
	 * @param qType Le type
	 * @param qClass La classe de la question
	 * @return
	 */
	private String getQuestions(String qName, String qType, String qClass)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\t{NAME="+qName+", TYPE="+qType);
		
		String commonMiddle = ", CLASS=1 (IN)";
		switch(qType)
		{
		case "0001":
			sb.append(StringUtility.hexaToInt(qType)+" (A)"+commonMiddle+"}");
			break;
		case "0005":
			sb.append(StringUtility.hexaToInt(qType)+" (CNAME)"+commonMiddle+"}");
			break;
		case "0002":
			sb.append(StringUtility.hexaToInt(qType)+" (NS)"+commonMiddle+"}");
			break;
		case "000F":
			sb.append(StringUtility.hexaToInt(qType)+" (MX)"+commonMiddle+"}");
			break;
		case "001C":
			sb.append(StringUtility.hexaToInt(qType)+" (AAAA)"+commonMiddle+"}");
			break;
		}
		
		return sb.toString();
	}
	
	/**
	 * Rend un resource record complet
	 * @param rrName Le nom du domaine
	 * @param rrType Le type du resource record
	 * @param rrClass La classe
	 * @param rrTTL Le ttl
	 * @param rrDataLength La longueur du champ data
	 * @param rrData Le resource record
	 * @return
	 */
	private String getCompleteRRs(String rrName, String rrType, String rrClass, String rrTTL, String rrDataLength, String rrData)
	{
		//System.out.println(this.afficheTab());
		StringBuilder sb = new StringBuilder();
		sb.append("\t{NAME="+rrName+", TYPE="+rrType);
		
		String commonMiddle = ", CLASS=1 (IN)"
				+ ", TTL="+StringUtility.hexaToInt(rrTTL)+" (seconds)"
				+ ", RDLENGTH="+StringUtility.hexaToInt(rrDataLength)+" (bytes)";
		
		switch(rrType)
		{
		case "0001":
			sb.append(StringUtility.hexaToInt(rrType)+" (A)"
					+ commonMiddle
					+ ", RDATA="+IP.convertHexToIP(rrData)+"}");
			break;
		case "0005":
			sb.append(StringUtility.hexaToInt(rrType)+" (CNAME)"
					+ commonMiddle
					+ ", RDATA="+this.getName(rrData)+"}");
			break;
		case "0002":
			sb.append(StringUtility.hexaToInt(rrType)+" (NS)"
					+ commonMiddle
					+ ", RDATA="+this.getName(rrData)+"}");
			break;
		case "000F":
			sb.append(StringUtility.hexaToInt(rrType)+" (MX)"
					+ commonMiddle
					+ ", RDATA="+this.getName(rrData)+"}");
			break;
		case "001C":
			sb.append(StringUtility.hexaToInt(rrType)+" (AAAA)"
					+ commonMiddle
					+ ", RDATA="+this.getName(rrData)+"}");
			break;
		}
		
		return sb.toString();		
	}
	
	/**
	 * Renoie la longueuer de l'entete
	 * @return La longueur de la chaine passee en argument du constructeur
	 */
	@Override
	public int getLength() {
		return this.value.length();
	}

	/**
	 * Renvoie null
	 * @return null
	 */
	@Override
	public String getNext() {
		return null;
	}
	
	@Override
	public String toString()
	{
		StringJoiner sj = new StringJoiner("\n");
		sj.add(this.getMainHeader());		
		for(int i=6; i<this.getFields().size(); i++)
		{
			Field f = this.getFields().get(i);
			sj.add(f.getName()+" : "+f.getValue());
			//ajout des questions
			int tmp = 0;
			if(i==6) sj.add(this.getQuestions());
			tmp += this.nbQuestions;
			if(i==7) sj.add(this.getRRs(tmp,this.nbAnswers));	
			tmp += this.nbAnswers;
			if(i==8) sj.add(this.getRRs(tmp,this.nbAuthorities));	
			tmp += this.nbAuthorities;
			if(i==9) sj.add(this.getRRs(tmp,this.nbAdditionals));	
		}
		return sj.toString();		
	}
	
	/**
	 * Renvoie les questions sonus forme de chaine lisible
	 * @return Les questions
	 */
	private String getQuestions()
	{
		StringJoiner sj = new StringJoiner("\n\t");
		for(int i=0; i<this.nbQuestions; i++)
		{
			String[] tmp = this.contentRRs.get(i);
			sj.add(this.getQuestions(tmp[0], tmp[1], tmp[2]));
		}
		return sj.toString();
	}
	
	/**
	 * Renvoie les RRs sous forme de chaine lisible
	 * @param deb Le debut du parcours
	 * @param nbRRs Le nombre de RRs dans le champ
	 * @return Les RR
	 */
	private String getRRs(int deb, int nbRRs)
	{
		StringJoiner sj = new StringJoiner("\n");
		for(int i=deb; i<deb+nbRRs; i++)
		{
			String[] tmp = this.contentRRs.get(i);
			//System.out.println("tmp size : "+tmp.length);
			sj.add(this.getCompleteRRs(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], tmp[5]));
		}
		return sj.toString();
	}
	
	/**
	 * Ajoute un RR dans la liste globale des RR
	 * @param value La valeur du RR
	 * @param nbRR Le nombre de RRs
	 */
	private void addRRContent(String value, int nbRR)
	{
		int pointeur = 0;
		for(int i=0; i<nbRR; i++)
		{
			String newValue = value.substring(pointeur);
			//System.out.println("tmp :"+newValue);
			int rrLength = getRRLength(newValue);
			//System.out.println("RRLength :"+rrLength);
			String[] newRR = { this.getNameRR(newValue), 
					this.getTypeRR(newValue), 
					this.getClassRR(newValue),
					this.getTTLRR(newValue), 
					this.getDataLengthRR(newValue),
					this.getData(newValue)};
			this.contentRRs.add(newRR);
			pointeur += rrLength;
		}
	}
	
	/**
	 * Ajoute tous les contenus des RRs dans le tableau global
	 */
	private void addAllContents()
	{
		String value = this.getFields().get(DNS_QUESTIONS).getValue();
		this.addQuestionContent(value);
		
		value = this.getFields().get(DNS_ANSWERS).getValue();
		this.addRRContent(value, this.nbAnswers);
		
		if(this.getFields().size()>DNS_AUTHORITIES)
		{
			value = this.getFields().get(DNS_AUTHORITIES).getValue();
			this.addRRContent(value, this.nbAuthorities);
		}
		
		if(this.getFields().size()>DNS_ADDITIONALS)
		{
			value = this.getFields().get(DNS_ADDITIONALS).getValue();
			this.addRRContent(value, this.nbAdditionals);
		}
	}
	
	/**
	 * Ajoute une question dans la liste globale des RR
	 * @param value La valeur de la question
	 */
	private void addQuestionContent(String value)
	{
		//System.out.println("tmp :"+value);
		int pointeur = 0;
		for(int i=0; i<this.nbQuestions; i++)
		{
			String newValue = value.substring(pointeur);
			int rrLength = getQuestionLength(value);
			String[] newRR = { this.getNameRR(newValue), 
					this.getTypeRR(newValue), 
					this.getClassRR(newValue)};
			this.contentRRs.add(newRR);
			pointeur += rrLength;
		}
	}

	/**
	 * Renvoie les donnees d'un RR
	 * @param value La valeur du RR
	 * @return Les donnees du RR
	 */
	private String getData(String value) {
		int tmp = this.getNameSize(value)+4+4+8+4;
		String dataLength = this.getDataLengthRR(value);
		return value.substring(tmp, tmp+StringUtility.hexaToInt(dataLength)*2);
	}

	/**
	 * Renvoie le dataLength d'un RR
	 * @param value La valeur du RR
	 * @return La longueur des donnees du RR
	 */
	private String getDataLengthRR(String value) {
		int tmp = this.getNameSize(value)+4+4+8;
		//System.out.println("DataLength : "+value.substring(tmp, tmp+4));
		return value.substring(tmp, tmp+4);
	}

	/**
	 * Renvoie le ttl d'un RR
	 * @param value La valeur du RR
	 * @return Le TTL du RR
	 */
	private String getTTLRR(String value) {
		int tmp = this.getNameSize(value)+4+4;
		return value.substring(tmp, tmp+8);
	}

	/**
	 * Renvoie la classe d'un RR
	 * @param value La valeur du RR
	 * @return La classe du RR
	 */
	private String getClassRR(String value) {
		int tmp = this.getNameSize(value)+4;
		return value.substring(tmp, tmp+4);
	}

	/**
	 * Renvoie le type d'un RR
	 * @param value La valeur du RR
	 * @return Le type du RR
	 */
	private String getTypeRR(String value) {
		int tmp = this.getNameSize(value);
		return value.substring(tmp, tmp+4);
	}

	/**
	 * Renvoie le nom de domaine d'un RR
	 * @param value La valeur du RR
	 * @return Le nom de domaine
	 */
	private String getNameRR(String value)
	{
		//TODO
		
		return "Name";
	}
	
	/**
	 * Renvoie la longueur d'un RR
	 * @param value La valeur du RR
	 * @return La longueur du RR
	 */
	private int getRRLength(String value) {
		return this.getNameSize(value)+2*4+8+4+StringUtility.hexaToInt(this.getDataLengthRR(value))*2;
	}
	
	/**
	 * Renvoie la longueur d'une question
	 * @param value La valeur de la question
	 * @return La longueur de la question
	 */
	private int getQuestionLength(String value)
	{
		return this.getNameSize(value)+2*4;
	}
	
	private String afficheTab() {
		StringJoiner sj = new StringJoiner("\n\n");
		for(String[] tab : this.contentRRs)
		{
			StringBuilder sb = new StringBuilder();
			for(String s : tab)
				sb.append(s+"\n");
			sj.add(sb);
		}
		return sj.toString();
	}

}
