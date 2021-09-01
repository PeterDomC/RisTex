package citeTypes;
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
	public RIS(RISType type) {
		this.type = type;
	}
	
	/*
	 * Constructor requires a Bibtex entry and transforms it into a RIS entry
	 */
	public RIS(Bibtex bibentry) {
		
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
		
		if (this.ID == null && authors != null) {
			Iterator<String> authit = authors.iterator();
			String name = "";
			if (authit.hasNext()) {
				name = authit.next().split(", ")[0];
			}
		
			this.ID = name + pub_year;
		}
	}
}