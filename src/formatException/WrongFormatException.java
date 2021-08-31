package formatException;
/*
 * A custom exception if a RIS entry or a bibtex entry do not have the right format
 */
public class WrongFormatException extends Exception {
	
	public WrongFormatException(String message) {
	    super(message);
	}
}