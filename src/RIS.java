import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class for the format RIS.
 */
public class RIS {
	
	// Characteristics of a RIS entry with corresponding abbreviations
	private final RISType type; // TY
	private List<String> authors; // AU
	private String title; // TI
	private String journal; // JO (only used for journal papers)
	private String publisher; //PB
	private String conference; // BT (only used for conference papers)
	private int pub_year; // PY
	private String SP; // SP (start page)
	private String EP; // EP (end page)
	private String ID; // ID
	
	/*
	 * Constructor requires the type of the RIS entry.
	 * The type cannot be changed once set.
	 */
	RIS(RISType type) {
		this.type = type;
	}
	
	/*
	 * Constructor requires a Bibtex entry and transforms it into a RIS entry
	 */
	RIS(Bibtex bibentry) {
		
		// Type of RIS entry - depends on given Bibtex type
		switch (bibentry.getType()) {
			case book: this.type = RISType.BOOK; break;
			case inbook: this.type = RISType.CHAP; break;
			case inproceedings: this.type = RISType.CPAPER; break;
			case article: this.type = RISType.JOUR; break;
			case techreport: this.type = RISType.RPRT; break;
			case phdthesis: this.type  = RISType.THES; break;
			default: this.type = RISType.CPAPER;
		}
		
		// Remaining entries
		this.authors = bibentry.getAuthors();
		this.title = bibentry.getTitle();
		this.journal = bibentry.getJournal();
		this.publisher = bibentry.getPublisher();
		this.conference = bibentry.getBooktitle();
		this.pub_year = bibentry.getYear();
		this.ID = bibentry.getID();
		
		// Pages if present
		String pages = bibentry.getPages();
		if (pages != null) {
			// pages is of the form NR--NR
			String[] pagesplit = pages.split("--");
			this.SP = pagesplit[0];
			this.EP = pagesplit[1];
		}
	}

	// Getters and setters
	public RISType getType() {
		return type;
	}
	
	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	
	/*
	 * Method for adding a single author to the list of authors
	 */
	public void addAuthor(String name) {
		if (authors == null) {
			// Generate list if not present
			List<String> new_auth = new ArrayList<String>();
			new_auth.add(name);
			setAuthors(new_auth);
			
		} else {
			// Add given name to already existing author list
			this.authors.add(name);
		}
	}
	
	public int getPubYear() {
		return pub_year;
	}

	public void setPubYear(int pub_year) {
		this.pub_year = pub_year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getConference() {
		return conference;
	}

	public void setConference(String conference) {
		this.conference = conference;
	}

	public String getSP() {
		return SP;
	}

	public void setSP(String SP) {
		this.SP = SP;
	}

	public String getEP() {
		return EP;
	}

	public void setEP(String EP) {
		this.EP = EP;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	/*
	 * ID is automatically generated when calling the method.
	 * It is the last name of the first author, followed by the year
	 */
	public void generateID() {
		Iterator<String> authit = authors.iterator();
		String name = "";
		if (authit.hasNext()) {
			name = authit.next().split(", ")[0];
		}
		
		this.ID = name + pub_year;
	}
	
	/*
	 * Write the RIS entry to a file 
	 */
	public void writeToFile(String filename) {
		
		try {
		    FileWriter fileWriter = new FileWriter(filename + ".ris");
		    PrintWriter writer = new PrintWriter(fileWriter);
		    
		    // Print type into file
		    String typeString = "";
		    switch(type) {
		    	case BOOK: typeString = "BOOK"; break;
		    	case CHAP: typeString = "CHAP"; break;
		    	case JOUR: typeString = "JOUR"; break;
		    	case CPAPER: typeString = "CPAPER"; break;
		    	case THES: typeString = "THES"; break;
		    	case RPRT: typeString = "RPRT"; break;
		    }
		    
		    writer.println("TY  - " + typeString);
		    
		    // Print authors
		    Iterator<String> authit = authors.iterator();
		    while (authit.hasNext()) {
		    	writer.println("AU  - " + authit.next());
		    }
		    
		    // Print title
		    writer.println("TI  - " + title);
		    
		    // Print journal or conference
		    switch(type) {
		    	case JOUR: writer.println("JO  - " + journal); break;
		    	case CPAPER: writer.println("BT  - " + conference); break;
		    }
		    
		    // Print publisher if set
		    if (publisher != null) writer.println("PB  - " + publisher);
		    
		    // Print pages if set
		    if (SP != null && EP != null) {
		    	writer.println("SP  - " + SP);
		    	writer.println("EP  - " + EP);
		    }
		    
		    // Print year
		    writer.println("PY  - " + pub_year);
		    
		    // Print ID
		    if (ID == null) generateID();
		    writer.println("ID  - " + ID);
		    
		    // End of entry
		    writer.print("ER  - ");
		    
		    // Close writers
		    writer.close();
		    fileWriter.close();
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}