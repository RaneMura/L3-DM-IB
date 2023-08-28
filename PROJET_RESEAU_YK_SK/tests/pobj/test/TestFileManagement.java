package pobj.test;

import java.util.List;

import org.junit.Test;

import pobj.res.InputFileManager;

/**
 * Modele de classe de test
 * @author Sharane et Yannis
 *
 */
public class TestFileManagement {
	/**
	 * Test de creation de trames a partir d'un fichier simple
	 */
	@Test
	public void testEasy() {
		System.out.println("Test Easy:\n");
		InputFileManager ifm = new InputFileManager("data/testInitTrames/traceEasy");
		List<String> trames = ifm.getTrames();
		for(String s : trames)
			System.out.println(s+"\n");
		System.out.println("Fin Test Easy\n\n");
	}
	
	/**
	 * Test de creation de trames a partir d'un fichier contenant plusieurs trames
	 */
	@Test
	public void testMultipleTrame() {
		System.out.println("Test Multiple Trames:\n");
		InputFileManager ifm = new InputFileManager("data/testInitTrames/traceMultipleTrame");
		List<String> trames = ifm.getTrames();
		for(String s : trames)
			System.out.println(s+"\n");
		System.out.println("Fin Test Multiple Trames\n\n");
	}
	
	/**
	 * Test de creation de trames a partir d'un fichier contenant une unique ligne
	 */
	@Test
	public void testOneLine() {
		System.out.println("Test One Line:\n");
		InputFileManager ifm = new InputFileManager("data/testInitTrames/traceOneLine");
		List<String> trames = ifm.getTrames();
		for(String s : trames)
			System.out.println(s+"\n");
		System.out.println("Fin Test One Line\n\n");
	}
	
	/**
	 * Test de creation de trames a partir d'un fichier contenant une trame ayant une ligne trop courte
	 */
	@Test
	public void testErrorLineTooShort() {
		System.out.println("Test Error Line Too Short:\n");
		InputFileManager ifm = new InputFileManager("data/testInitTrames/traceErrorLineTooShort");
		List<String> trames = ifm.getTrames();
		for(String s : trames)
			System.out.println(s+"\n");
		System.out.println("Fin Test Error Line Too Short\n\n");
	}
	
	/**
	 * Test de creation de trames a partir d'un fichier contenant une trame ayant une ligne manquante
	 */
	@Test
	public void testErrorMissingLine() {
		System.out.println("Test Error Missing Line:\n");
		InputFileManager ifm = new InputFileManager("data/testInitTrames/traceErrorMissingLine");
		List<String> trames = ifm.getTrames();
		for(String s : trames)
			System.out.println(s+"\n");
		System.out.println("Fin Test Error Missing Line\n\n");
	}
	
	/**
	 * Test de creation de trames a partir d'un fichier contenant une trame ayant un non hexa en plein milieu
	 */
	@Test
	public void testErrorNotHexa() {
		System.out.println("Test Error Not Hexa:\n");
		InputFileManager ifm = new InputFileManager("data/testInitTrames/traceErrorNotHexa");
		List<String> trames = ifm.getTrames();
		for(String s : trames)
			System.out.println(s+"\n");
		System.out.println("Fin Test Error Not Hexa\n\n");
	}	
	
	/**
	 * Test de creation de trames a partir d'un fichier complique
	 */
	@Test
	public void testHard() {
		System.out.println("Test Hard:\n");
		InputFileManager ifm = new InputFileManager("data/testInitTrames/traceHard");
		List<String> trames = ifm.getTrames();
		for(String s : trames)
			System.out.println(s+"\n");
		System.out.println("Fin Test Hard\n\n");
	}

}
