package pobj.test.trameBuilder;

import static org.junit.Assert.*;
import org.junit.Test;

import pobj.exceptions.ErrorValueException;
import pobj.exceptions.TrameTooShortException;
import pobj.exceptions.UnsupportedProtocolException;
import pobj.res.*;

/**
 * Test sur les trames builder
 * @author Sharane et Yannis
 *
 */
public class TestTrameBuilder {

	/**
	 * Test la creation des entete ethernet et ip d'une trame en utilisant un builder
	 * @throws UnsupportedProtocolException 
	 * @throws ErrorValueException 
	 * @throws TrameTooShortException 
	 */
	@Test
	public void testEthernetIp() throws UnsupportedProtocolException, ErrorValueException, TrameTooShortException {
		//trame en entree ne contenant qu'un champ ethernet et ip et un message test en fin de chaine
		TrameBuilder trBuild = new TrameBuilder("5c514fe6da5cdc00b066346e080045000034238440004006d00bc0a801c225305f9atest");
		TrameDirector trDirector = new TrameDirector(trBuild);
		//creation de la trame
		trDirector.constructTrame();
		ITrame trame = trDirector.getTrame();
		//System.out.println(trame.toString()); //affiche la trame (renverra un nullPointerException si la methode toString de Trame n'est pas modifie en consequence
	}

}
