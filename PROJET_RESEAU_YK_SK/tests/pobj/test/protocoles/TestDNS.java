package pobj.test.protocoles;

//import static org.junit.Assert.*;
import org.junit.Test;

import pobj.exceptions.ErrorValueException;
import pobj.res.header.DNS;


/**
* Classe testant l'initialisation d'un header UDP
* @author Sharane et Yannis
*
*/

public class TestDNS {

	/**
	 * Test un header DNS
	 * @throws ErrorValueException 
	 */
	@Test
	public void test() throws ErrorValueException {
		DNS dns = new DNS("37e581800001000200000000037777770475706d630246720000010001c00c000500010000001e000603776562c010c029000100010000001e0004869dfa3b");
		System.out.println(dns);
	}
	
}
