package pobj.res;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Classe permettant de faire des operations sur les fichiers passes en argument
 * @author Sharane et Yannis
 *
 */
public class InputFileManager {
	//list contenant les trames sous forme de chaines sans espaces
	private List<String> listTrames = new ArrayList<>();
	
	/**
	 * Construteur classique
	 * @param file Le nom du fichier en entree du programme
	 */
	public InputFileManager(String filePath)
	{
		//met le fichier sous forme de liste de String representant les lignes du fichier
		List<String> tmp1 = this.getLinesFromFile(filePath);
		/**
		for(String s : tmp1)
		{
			System.out.println(s);
		}
		**/
		List<String[]> tmp2 = this.removeSpaces(tmp1);
		//this.afficheListStringTab(tmp2);
		List<List<String[]>> tmp3 = this.splitTrames(tmp2);
		/**
		for(List<String[]> l : tmp3)
		{
			System.out.println("trame:");
			this.afficheListStringTab(l);
		}
		**/
		for(int i=0; i<tmp3.size(); i++)
		{
			//s'il manque une ligne dans la trame on l'enleve de la liste de trames
			if(!this.testTrameSansLigneManquante(tmp3.get(i)))
			{
				System.out.println("Trame "+(i+1)+" a une ou plusieurs lignes manquantes");//on compte les traes a partir de 1
				tmp3.set(i, new ArrayList<>());
			}
		}
		/**
		for(List<String[]> l : tmp3)
		{
			System.out.println("trame:");
			this.afficheListStringTab(l);
		}
		**/
		for(int i=0; i<tmp3.size(); i++)
		{
			System.out.println("Debut test Trame "+(i+1)+":");
			List<String[]> tmp = tmp3.get(i);
			if(!tmp.isEmpty())
				this.listTrames.add(this.removeUselessHex(tmp));
			else
				this.listTrames.add(null);
			System.out.println("Fin test Trame "+(i+1)+"\n");
		}
	}
		
	/**
	 * Affiche une liste de String[]
	 * @param liste La liste a afficher
	 */
	@SuppressWarnings("unused")
	private void afficheListStringTab(List<String[]> liste)
	{
		for(String[] tab : liste)
		{
			StringJoiner sj = new StringJoiner(",","[","]");
			for(int i=0; i<tab.length; i++)
				sj.add(tab[i]);
			System.out.println(sj.toString());
		}
	}
	
	/**
	 * Accesseur sur la liste des trames
	 * @return La liste des trames
	 */
	public List<String> getTrames(){return this.listTrames;}
	
	/**
	 * Copy les ignes d'un fichier dans une liste de String
	 * @param file Le fichier lus
	 * @return La liste des lignes du fichier
	 */
	private List<String> getLinesFromFile(String file)
	{
		List<String> res = new ArrayList<>();
		
		//ouverture du fichier
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while(line!=null)
			{
				res.add(line);
				line = br.readLine();	
			}				
		}catch(IOException ie) {
			System.out.println("Impossible d'ouvrir le fichier!");
			ie.printStackTrace();
		}
		
		return res;		
	}
	
	/**
	 * Cree une liste de tableau de String representant chacun une ligne du fichier sans espaces
	 * @param fileContent Le contenu du fichier sous forme de liste de String
	 * @return La liste des tableau de String
	 */
	private List<String[]> removeSpaces(List<String> fileContent)
	{
		List<String[]> res = new ArrayList<>();
		for(String s : fileContent)
		{
			String[] tmp = s.split(" ");
			tmp = Arrays.stream(tmp).filter(e -> e.trim().length() > 0).toArray(String[]::new);
			//si la ligne n'est pas vide et commence par un Hexa
			if(tmp.length!=0 && this.isHex(tmp[0]))
				res.add(tmp);
		}
		return res;
	}
	
	/**
	 * Repartis les trames en sous liste de liste
	 * @param fileContent Les trames sous forme de list de tableau de String
	 * @return Une liste de liste de tableau de String representant les differentes trames contenue dans le fichier
	 */
	private List<List<String[]>> splitTrames(List<String[]> fileContent)
	{
		List<List<String[]>> res = new ArrayList<>();
		int indiceLigne=0;
		List<String[]> tmp = new ArrayList<>();
		//pour chaque ligne du fichier
		while(indiceLigne<fileContent.size())
		{
			//si on a un offset a 0
			int t = StringUtility.hexaToInt(fileContent.get(indiceLigne)[0]);
			if(t==0)
			{
				if(!tmp.isEmpty())
					//on ajoute la liste representant la trame au resultat (si elle n'est pas vide)
					res.add(tmp);
				//on reset la liste temporaire
				tmp = new ArrayList<>();
			}
			//on ajoute la ligne a la liste temporaire
			tmp.add(fileContent.get(indiceLigne));
			indiceLigne++;
		}
		res.add(tmp);
		return res;
	}
	
	/**
	 * Test s'il manque une ligne dans la trame
	 * @param trameContent Le contenu de la trame
	 * @return True si la trame est complete
	 */
	private boolean testTrameSansLigneManquante(List<String[]> trameContent)
	{
		//si la trame fait 1 ou 2 lignes
		if(trameContent.size()<2)
			return true;
		
		//offset de la deuxieme ligne
		int incrementation = StringUtility.hexaToInt(trameContent.get(1)[0]);
		int precedOffset = incrementation;
		//pour chaque ligne on recupere l'offset et on regarde s'il est bien incremente de incrementation
		for(int i=2; i<trameContent.size(); i++)
		{
			//offset de la ligne i
			int offsetLineI = StringUtility.hexaToInt(trameContent.get(i)[0]);
			//si l'offset actuel n'est pas egal a l'offset precedent + la valeur d'incrementation d'un offset on renvoie faux
			if(offsetLineI != precedOffset+incrementation)
				return false;
			//mise a jour de l'offset precedent
			precedOffset = offsetLineI;
		}
		return true;
		
	}
	
	/**
	 * Transforme un tableau de String en String sans prendre en compte le premier element du tableau
	 * @param tab Le tableau de String
	 * @return La String formee apres concatenation
	 */
	private String tabStringToString(String[] tab)
	{
		StringBuilder sb = new StringBuilder();
		for(int i=1; i<tab.length; i++)
			sb.append(tab[i]);
		return sb.toString();
	}
	
	/**
	 * Met en forme une ligne d'une trame
	 * @param tab La ligne de la trame sous forme d'un tableau de String
	 * @param length La longueur supposee de la ligne
	 * @return La ligne formee
	 */
	private String removeUselessHexInStringTab(String[] tab, int length)
	{
		StringBuilder sb = new StringBuilder();
		//on met le tableau sous forme de chaine en enlevant l'offset
		String tmp = this.tabStringToString(tab);
		//si la ligne est inferieur a la longueur suppose alors elle est en ereur
		if(tmp.length()<length)
			return null;
		//pour chaque characteres 
		for(int i=0; i<length; i++)
		{
			if(!this.isHex(tmp.charAt(i)))//si un charactere n'est pas un hexa la trame est en erreur
				return null;
			sb.append(tmp.charAt(i));
		}
		return sb.toString();
			
	}
	
	/**
	 * Transforme le contenu du fichier en enlevenat les elements de fin en trop
	 * @param trameContent Le contenue d'une trame
	 * @return La trame sous forme de liste
	 */
	private String removeUselessHex(List<String[]> trameContent)
	{
		StringBuilder trame = new StringBuilder();
		//longueur theorique de la trame
		switch(trameContent.size())
		{
		case 0://liste vide
			System.out.println("Erreur trame vide!");
			return null;
		case 1://trame sur une seule ligne
			String[] tmp = trameContent.get(0);
			//on ajoute tous les elements jusqu'a atteindre la longueur de la chaine
			for(int j=1; j<tmp.length; j++)
			{
				//parcours char by char pour verifier qu'il s'agit d'hexa
				for(int k=0; k<tmp[j].length(); k++)
				{
					char c = tmp[j].charAt(k);
					//si c'est un hexa on l'ajoute
					if(this.isHex(c)) {trame.append(c);}
					//sinon on arrete d'ajouter des characteres et on renvoie la trame
					else {return trame.toString();}
				}
			}
			return trame.toString();
		default:
			//offset de la deuxieme ligne
			int length = StringUtility.hexaToInt(trameContent.get(1)[0])*2; //*2 car sinon length en octet et non en chars
			for(int i=0; i<trameContent.size()-1; i++)//pour chaque ligne de la trame
			{
				String tmpLine = this.removeUselessHexInStringTab(trameContent.get(i), length);
				if(tmpLine==null)
				{
					System.out.println("Ligne " + i + " trop courte");
					return null;
				}
				trame.append(tmpLine);
			}
			//ajout derniere ligne
			String[] lastLine = trameContent.get(trameContent.size()-1);
			//on ajoute tous les elements jusqu'a atteindre la longueur de la chaine
			for(int j=1; j<lastLine.length; j++)
			{
				//parcours char by char pour verifier qu'il s'agit d'hexa
				for(int k=0; k<lastLine[j].length(); k++)
				{
					char c = lastLine[j].charAt(k);
					//si c'est un hexa on l'ajoute
					if(this.isHex(c)) {trame.append(c);}
					//sinon on arrete d'ajouter des characteres et on renvoie la trame
					else {return trame.toString();}
				}
			}
			return trame.toString();
		}
	}
		
	/**
	 * Test si un char est un hexa
	 * @param c Le charactere a tester
	 * @return True si le char est un hex
	 */
	private boolean isHex(char c)
	{
		switch(c)
		{
		case 'A': return true;
		case 'a': return true;
		case 'B': return true;
		case 'b': return true;
		case 'C': return true;
		case 'c': return true;
		case 'D': return true;
		case 'd': return true;
		case 'E': return true;
		case 'e': return true;
		case 'F': return true;
		case 'f': return true;
		case '0': return true;
		case '1': return true;
		case '2': return true;
		case '3': return true;
		case '4': return true;
		case '5': return true;
		case '6': return true;
		case '7': return true;
		case '8': return true;
		case '9': return true;
		}
		return false;
	}

	/**
	 * Test si une chaine represente un nombre en hexa
	 * @param s La chaine a tester
	 * @return True si la chaine represente un hexa
	 */
	private boolean isHex(String s)
	{
		for(int i=0; i<s.length(); i++)
		{
			if(!this.isHex(s.charAt(i)))
				return false;
		}
		return true;
	}
}
