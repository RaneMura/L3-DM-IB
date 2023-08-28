package pobj.test.protocoles;

//import static org.junit.Assert.*;
import org.junit.Test;

import pobj.exceptions.TrameTooShortException;
import pobj.exceptions.UnsupportedProtocolException;
import pobj.res.header.Ethernet;


/**
 * Classe testant l'initialisation d'un header ethernet
 * @author Sharane et Yannis
 *
 */
public class TestEthernet {

	/**
	 * Test un header Ethernet de type IPv4
	 * @throws UnsupportedProtocolException 
	 * @throws TrameTooShortException 
	 */
	@Test
	public void testIPv4() throws UnsupportedProtocolException, TrameTooShortException {
		Ethernet eth = new Ethernet("5c514fe6da5cdc00b066346e0800");
		System.out.println(eth);
	}
	
	/**
	 * Test un header Ethernet de type ARP
	 * @throws UnsupportedProtocolException 
	 * @throws TrameTooShortException 
	 */
	@Test(expected = RuntimeException.class)
	public void testARP() throws UnsupportedProtocolException, TrameTooShortException {
		Ethernet eth = new Ethernet("dc00b066346e5c514fe6da5c0806");
		System.out.println(eth);
	}
	
	/**
	 * Test un header Ethernet de type IPv6
	 * @throws UnsupportedProtocolException 
	 * @throws TrameTooShortException 
	 */
	@Test(expected = RuntimeException.class)
	public void testIPv6() throws UnsupportedProtocolException, TrameTooShortException {
		Ethernet eth = new Ethernet("5c514fe6da5cdc00b066346e86dd");
		System.out.println(eth);
	}
	
	/**
	 * Test un header Ethernet de type inconnu
	 * @throws UnsupportedProtocolException 
	 * @throws TrameTooShortException 
	 */
	@Test(expected = RuntimeException.class)
	public void testUnknown() throws UnsupportedProtocolException, TrameTooShortException {
		Ethernet eth = new Ethernet("5c514fe6da5cdc00b08914cc888e");
		System.out.println(eth);
	}

}
