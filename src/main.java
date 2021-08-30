import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		
		try {
			RIS entry = RISParser.parseFromFile("test.ris");
			Bibtex bibentry = new Bibtex(entry);
			bibentry.writeToFile("outcome");
			
		} catch (WrongFormatException e) {
			System.out.println(e);
		}
	}
}
