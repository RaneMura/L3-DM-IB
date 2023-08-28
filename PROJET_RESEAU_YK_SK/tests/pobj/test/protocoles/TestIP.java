package pobj.test.protocoles;

//import static org.junit.Assert.*;
import org.junit.Test;

import pobj.exceptions.ErrorValueException;
import pobj.exceptions.TrameTooShortException;
import pobj.exceptions.UnsupportedProtocolException;
import pobj.res.ITrame;
import pobj.res.InputFileManager;
import pobj.res.TrameBuilder;
import pobj.res.header.*;


/**
 * Classe testant l'initialisation d'un header ethernet
 * @author Sharane et Yannis
 *
 */
public class TestIP {

	/**
	 * Test un header IP
	 */
	/**
	@Test
	public void testSample() {
		IP ip = new IP("4500012ca8360000fa11178b00000000ffffffff");
		System.out.println(ip);
	}
	**/
	
	/**
	 * Test un header IP avec option Record Route
	 * @throws UnsupportedProtocolException 
	 * @throws ErrorValueException 
	 * @throws TrameTooShortException 
	 */
	@Test
	public void testOptionRR() throws UnsupportedProtocolException, ErrorValueException, TrameTooShortException {
		InputFileManager ifm = new InputFileManager("data/testProtocoles/IPOptionRR");
		String content = ifm.getTrames().get(0);
		TrameBuilder trBuild = new TrameBuilder(content);
		trBuild.buildLiaison();
		trBuild.buildReseau();
		ITrame trame = trBuild.getTrame();
		Header ip = trame.getReseau();
		System.out.println(ip);
	}
}
