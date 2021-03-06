package citeTypes;
import java.util.Iterator;
import java.util.List;

/**
 * Class for the format bibtex.
 */
public class Bibtex {
	
	// Characteristics of a bibtex entry
	private final BibType type; // type of the entry - @type
	private List<String> authors; // author
	private String title; // title
	private String journal; // journal of publication
	private int year; // year
	private String publisher; // publisher of paper/book
	private String booktitle; // name of the conference if inproceedings
	private String school; // name of institute for PhD thesis or name of institution for technical report
	private String series; // series in which paper appeared
	private String volume; // volume of conference/journal/book
	private String pages; // pages in proceedings/journal/book
	private int number; // number in journal
	private String ID; // id of the entry
	
	/*
	 * Constructor requires the type of the bibtex entry.
	 * The type cannot be changed once set.
	 */
	public Bibtex(BibType type) {
		this.type = type;
	}
	
	/*
	 * Constructor requires a RIS entry and transforms it into a bibtex entry
	 */
	public Bibtex(RIS risentry) {
		
		// Type of the Bibtex entry - depends on given RIS type
		switch (risentry.getType()) {
			case JOUR: this.type = BibType.article; break;
			case BOOK: this.type = BibType.book; break;
			case CHAP: this.type = BibType.inbook; break;
			case CPAPER: this.type = BibType.inproceedings; break;
			case THES: this.type = BibType.phdthesis; break;
			case RPRT: this.type = BibType.techreport; break;
			default: this.type = BibType.techreport;
		}
		
		// Remaining entries
		this.authors = risentry.getAuthors();
		this.title = risentry.getTitle();
		this.journal = risentry.getJournal();
		this.year = risentry.getPubYear();
		this.publisher = risentry.getPublisher();
		this.booktitle = risentry.getConference();
		// school, series, volume, number currently not supported by RIS
		this.pages = risentry.getSP() + "--" + risentry.getEP();
		this.ID = risentry.getID();
	}
	
	// Getters and setters
	public BibType getType() {
		return type;
	}
	
	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getBooktitle() {
		return booktitle;
	}

	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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
			
			this.ID = name + year;
		}
	}
	
	/*
	 * Write the Bibtex into a single string
	 */
	public String writeToString() {
		
		String result = "";
	
	    // Add type and ID to result String
	    String typeString = "";
	    switch(type) {
	    	case article: typeString = "@article"; break;
	    	case book: typeString = "@book"; break;
	    	case inbook: typeString = "@inbook"; break;
	    	case inproceedings: typeString = "@inproceedings"; break;
	    	case phdthesis: typeString = "@phdthesis"; break;
	    	case techreport: typeString = "@techreport"; break;
	    }
	    
	    // Add the ID to the String
	    generateID();
	    typeString += "{" + ID + ",";
	    result = typeString + "\n";
	    
	    // Add authors
	    String indent = "    ";
	    result += indent + "author = {";
	    if (authors != null) {
		    Iterator<String> authit = authors.iterator();
		    boolean more_than_one_auth = false;
		    while (authit.hasNext()) {
		    	if (more_than_one_auth) {
		    		result += " and " + authit.next();
		    	} else {
		    		result += authit.next();
		    		more_than_one_auth = true;
		    	}
		    }   
	    }
	    result += "},\n";
	    
	    // Print title
	    result += indent + "title = {" + title + "},\n";
	    
	    // Print year
	    result += indent + "year = {" + year + "}";
	    
	    // Now the optional entries are left
	    // Print booktitle/journal/institution
	    switch (type) {
	    	case inproceedings: result += ",\n" + indent + "booktitle = {" + booktitle + "}"; break;
	    	case inbook: result += ",\n" + indent + "booktitle = {" + booktitle + "}"; break;
	    	case article: result += ",\n" + indent + "journal = {" + journal + "}"; break;
	    	case phdthesis: result += ",\n" + indent + "school = {" + school + "}"; break;
	    	case techreport: result += ",\n" + indent + "school = {" + school + "}"; break;
	    	default:
	    }
	    
	    // Remaining entries - all optional
	    if (volume != null) result += ",\n" + indent + "volume = {" + volume + "}";
	    if (number != 0) result += ",\n" + indent + "number = {" + number + "}";
	    if (pages != null) result += ",\n" + indent + "pages = {" + pages + "}";
	    if (series != null) result += ",\n" + indent + "series = {" + series + "}";
	    if (publisher != null) result += ",\n" + indent + "publisher = {" + publisher + "}";
	    
	    // End of entry
	    result += "\n}";
	    return result;
	}
}