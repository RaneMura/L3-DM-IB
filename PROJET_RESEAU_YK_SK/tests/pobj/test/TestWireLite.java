package pobj.test;

import org.junit.Test;
import pobj.WireLite;

/**
 * Modele de classe de test
 * @author Sharane et Yannis
 *
 */
public class TestWireLite {
	/**
	 * Test de creation de trames a partir d'un fichier simple
	 */
	@Test
	public void testEasy() {
		String[] args = {"data/testWireLite/dhcp"};
		WireLite.main(args);
	}
}
