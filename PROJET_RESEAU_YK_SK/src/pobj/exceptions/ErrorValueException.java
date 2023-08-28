package pobj.exceptions;

/**
 * Exception levee si une valeure incoherente est entree dans un champ d'une entete de protocole
 * @author Sharane et Yannis
 *
 */
@SuppressWarnings("serial")
public class ErrorValueException extends Exception {
	public ErrorValueException(String m)
	{
		super(m);
	}
}
