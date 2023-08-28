package pobj.exceptions;

/**
 * Exception levee si une valeur representant un protocole non suporte est passee dans un champ d'une entete
 * @author Sharane et Yannis
 *
 */
@SuppressWarnings("serial")
public class UnsupportedProtocolException extends Exception {
	public UnsupportedProtocolException(String m)
	{
		super(m);
	}
}
