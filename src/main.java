import citeTypes.Bibtex;
import citeTypes.RIS;
import citeTypes.RISParser;
import formatException.WrongFormatException;
import gui.MainFrame;

/*
 * Main class of RisTex
 */
public class main {
	
	/*
	 * Main method of RisTex
	 */
	public static void main(String[] args) {
		
		
		MainFrame mainfr = new MainFrame();
		
		/*
		if (args.length == 0) {
			System.out.println("No filename given.");
			return;
		} else {
			
			try {
				String filename = args[0];
				
				// Parse given file
				RIS entry = RISParser.parseFromFile(filename);
				
				// Translate to bibtex
				Bibtex bibentry = new Bibtex(entry);
				
				// Output file
				filename = fileNameChecker(filename);
				bibentry.writeToFile(filename + "_out");
				
			} catch (WrongFormatException e) {
				System.out.println(e);
			}
		} */
	}
	
	private static String fileNameChecker(String filename) throws WrongFormatException {
		// Check if filename ends with .ris
		// If not: throw WrongFormatException
		int n = filename.length();
		if (n >= 4) {
			if (filename.substring(n-4).equals(".ris")) {
				return filename.substring(0,n-4);
			}
		}

		throw new WrongFormatException("Wrong Filename!");
	}
}