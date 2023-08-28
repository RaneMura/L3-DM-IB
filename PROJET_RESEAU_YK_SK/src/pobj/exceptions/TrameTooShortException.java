package pobj.exceptions;

/**
 * Exception gerant une trame trop courte
 * @author Sharane et Yannis
 *
 */
@SuppressWarnings("serial")
public class TrameTooShortException extends Exception {
	public TrameTooShortException()
	{
		super("Il manque des octets dans la trame !");
	}
}
