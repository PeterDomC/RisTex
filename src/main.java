import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		
		// TODO: GUI where you can copy an entry into
		
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
				filename = removeRIS(filename);
				bibentry.writeToFile(filename + "_out");
				
			} catch (WrongFormatException e) {
				System.out.println(e);
			}
		}
	}
	
	private static String removeRIS(String filename) throws WrongFormatException {
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