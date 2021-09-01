package citeTypes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import formatException.WrongFormatException;

/**
 * Class for parsing RIS files/Strings
 */
public class RISParser {
	
	/*
	 * Given a filename, this method parses the content and transforms it into a RIS
	 */
	public static RIS parseFromFile(String filename) throws WrongFormatException {
		
		RIS entry = null;
		
		try {
			FileReader fileReader = new FileReader(filename);
		    BufferedReader reader = new BufferedReader(fileReader);
		    String current_line = reader.readLine();
		    boolean first = true;
		    
		    // Parse the content of the file line by line
		    // RIS file lines always have the same form: XX  - content
		    // A RIS entry always starts with TY and ends with ER
		    while (current_line != null) {
		    	
		    	if (!first) {
		    		// Parse non-first line
		    		if (!detectLastLine(current_line)) {
	    				// Not the last line - add content to entry
	    				entry = parseContent(current_line,entry);
		    		} else {
		    			break;
		    		}
		    		
		    	} else {
		    		// Search for the first line - starts with TY
		    		// Everything before is ignored
		    		if (detectFirstLine(current_line)) {
			    		entry = parseFirstLine(current_line);
			    		first = false;
		    		}
		    	}
		    	
		    	current_line = reader.readLine();
		    }
		    
		    reader.close();
		    fileReader.close();
			
		} catch (IOException e) {
			System.out.println(e);
		}
		
		return entry;
	}
	
	/*
	 * Given a string encoding a ris entry, this method parses the content and transforms it into a RIS
	 */
	public static RIS parseFromString(String content) throws WrongFormatException {
		
		if (content == null) {
			throw new WrongFormatException("Wrong format!");
		}
		
		RIS entry = null;
		String[] contentSplit = content.split("\n");
		int n = contentSplit.length;

		// Flags for found first and last line
		// Everything before the first line starting with "TY  - " is ignored
		// Everything below the first line with "ER  - " is ignored
		boolean found_first = false;
		boolean found_end = false;
		
		// Parse entry line by line
		for (int i = 0; i < n; i++) {
			
			// A RIS entry always starts with "TY  - "
			// There can be comments above this line - these are ignored.
			if (found_first) {
				
				// First line already found - parse non-first line with actual content
				if (detectLastLine(contentSplit[i]) ) {
					// Current line is the last line - leave the loop and ignore the rest of the entry
					found_end = true;
					break;
					
				} else {
					// Current line is NOT the last line - parse its content and add to entry
					entry = parseContent(contentSplit[i],entry);
				}
				
			} else {
				
				// First line not found yet
				if (detectFirstLine(contentSplit[i])) {
					// First line detected
					entry = parseFirstLine(contentSplit[i]);
					found_first = true;
				}
			}
		}
		
		if (found_first && found_end) {
			// Parsing is only successful if first and last line have been found!
			return entry;
		} else {
			throw new WrongFormatException("Wrong format!");
		}
	}
	
	/*
	 * Detects whether the current line is the first line that has actual content - starts with TY
	 */
	private static boolean detectFirstLine(String line) {
		
		String[] content = line.split("  - ");
		if (content[0].equals("TY")) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Detects whether the current line is the last line
	 */
	private static boolean detectLastLine(String line) {
		
		String[] content = line.split("  -");
		if (content[0].equals("ER")) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Method takes line of content and adds it to the entry
	 * CAUTION: entry is modified!
	 */
	private static RIS parseContent(String line, RIS entry) throws WrongFormatException {
		
		String[] content = line.split("  - ");
		switch (content[0]) {
			case "AU": entry.addAuthor(content[1]); break;
			case "TI": entry.setTitle(content[1]); break;
			case "JO": entry.setJournal(content[1]); break;
			case "PB": entry.setPublisher(content[1]); break;
			case "BT": entry.setConference(content[1]); break;
			case "PY":
				// Sometimes, the year is followed by other symbols, which we ignore
				String year = content[1];
				if (content[1].length() > 4) year = content[1].substring(0,4);
				entry.setPubYear(Integer.parseInt(year)); 
				break;
			case "SP": entry.setSP(content[1]); break;
			case "EP": entry.setEP(content[1]); break;
			case "ID": entry.setID(content[1]); break;
			case "TY": throw new WrongFormatException("Wrong format!"); // If TY is found again - wrong format!
		}
		
		return entry;
	}

	/*
	 * Method for parsing the first line of a RIS entry.
	 * Determines the type of the entry, initializes the entry, and returns it.
	 */
	private static RIS parseFirstLine(String line) throws WrongFormatException {
		String[] content = line.split("  - ");
		RIS entry = null;
		
		if (content[0].equals("TY") && content.length == 2) {
			// First line starts with TY
			switch (content[1]) {
				case "BOOK": entry = new RIS(RISType.BOOK); break;
				case "CHAP": entry = new RIS(RISType.CHAP); break;
				case "CPAPER": entry = new RIS(RISType.CPAPER); break;
				case "JOUR": entry = new RIS(RISType.JOUR); break;
				case "RPRT": entry = new RIS(RISType.RPRT); break;
				case "THES": entry = new RIS(RISType.THES); break;
				default: throw new WrongFormatException("Wrong format!");
			}
			
		} else {
			// First line does not start with TY - wrong format, throw exception
			throw new WrongFormatException("Wrong format!");
		}
		
		return entry;
	}
}