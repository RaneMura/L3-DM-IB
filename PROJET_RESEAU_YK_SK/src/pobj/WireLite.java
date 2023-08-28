package pobj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import pobj.exceptions.ErrorValueException;
import pobj.exceptions.TrameTooShortException;
import pobj.exceptions.UnsupportedProtocolException;
import pobj.res.*;

/**
 * Classe representant le wireshark lite
 * @author Sharane et Yannis
 *
 */
public class WireLite {
	//file dans laquelle sera copie les resultats
	private static final String FILE_RES = "WireLite.log";
	
	public static void main(String[] args)
	{
		//erreur si pas d'arguments
		if(args.length==0)
		{
			System.out.println("This application need a file as an input !");
			return;
		}
		
		//test du parametre
		String filePath = args[0];
		File originFile = new File(filePath);
		//erreur si ce n'est pas un fichier
		if(!originFile.isFile())
		{
			System.out.println("The given argument is not a file !");
			return;
		}
		
		//mise en place des trames au bon format
		InputFileManager ifm = new InputFileManager(originFile.getPath());
		List<String> list = ifm.getTrames();
		
		//creation de la liste des trames
		List<ITrame> listTrames = new ArrayList<>();
		//tableau de messages d'erreur
		String[] errorMessage = new String[list.size()];
		//tableau de longueur le nombre de trames du fichier contenant true si la trame i est valide, false sinon
		boolean[] validTrames = new boolean[list.size()];
		for(int i=0; i<validTrames.length; i++)
			validTrames[i]=true;
		
		
		//parcours par indice pour reconnaitre les trames
		for(int i=0; i<list.size(); i++)
		{
			String s = list.get(i);
			//si la trame est nulle on continue et on met a jour le tableau des trames
			if(s==null)
			{
				validTrames[i]=false;
				continue;
			}
			try {
				TrameBuilder trBuild = new TrameBuilder(s);
				TrameDirector trDirector = new TrameDirector(trBuild);
				//creation de la trame
				trDirector.constructTrame();
				listTrames.add(trDirector.getTrame());
			}catch(Exception e) {
				errorMessage[i] = e.getMessage();
				listTrames.add(null);
			}
		}
		
		//affichage des trames
		StringJoiner sj = new StringJoiner("\n\n\n");
		int curTrame = 0;//indice courant de la trame (non nulle)
		int indice = 0;//indice du parcours des trames nulles ou non
		for(ITrame trame : listTrames)
		{
			//mise a jour des indices
			indice++;
			curTrame = findNextTrueInTab(curTrame,validTrames)+1;
			
			//si la trame d'indice i etait nulle on ecrit un message
			while(indice<(curTrame-1))
			{
				sj.add("Trame " + indice);
				sj.add("########################################");
				sj.add("Trame en erreur");
				sj.add("########################################");
				indice++;
			}
			
			//test trame en erreur
			if(trame==null)
			{
				sj.add("Trame " + indice);
				sj.add("########################################");
				sj.add("Trame en erreur:\n" + errorMessage[indice-1]);
				sj.add("########################################");
			}
			else
			{
				//ajout de la trame
				sj.add("Trame " + curTrame +"\n"+trame.toString()+"\n");
			}
			
		}
		//s'il reste des trames nulles on continue a afficher des messages
		for(int i=curTrame+1; i<=validTrames.length; i++)
		{
			sj.add("Trame " + i);
			sj.add("########################################");
			sj.add("Trame en erreur");
			sj.add("########################################");
		}
		//affichage du resultat
		System.out.println(sj.toString());
		
		//copy du resultat dans un fichier
		try {
			FileWriter myWriter = new FileWriter(FILE_RES);
			myWriter.write(sj.toString());
		    myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred while writting into the file "+FILE_RES);
		    e.printStackTrace();
		}
	}
	
	/**
	 * Cherche l'indice du premier element vrai dans un tableau a partir de l'indice fourni en parametre
	 * @param from L'indice a partir duquel on cherche le prochain element True
	 * @param tab Le tableau de boolean
	 * @return L'indice du prochain element True, -1 s'il n'y en a pas
	 */
	private static int findNextTrueInTab(int from, boolean[] tab)
	{
		for(int i=from; i<tab.length; i++)
			if(tab[i]) return i;
		return -1;
	}
}
