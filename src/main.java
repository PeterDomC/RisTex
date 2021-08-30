import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		
		// TODO: given filename over arguments
		// TODO: GUI where you can copy an entry into
		
		try {
			RIS entry = RISParser.parseFromFile("test.ris");
			Bibtex bibentry = new Bibtex(entry);
			bibentry.writeToFile("outcome_test");
			
		} catch (WrongFormatException e) {
			System.out.println(e);
		}
	}
}
