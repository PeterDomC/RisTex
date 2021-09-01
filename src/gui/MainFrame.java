package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import citeTypes.Bibtex;
import citeTypes.RIS;
import citeTypes.RISParser;
import formatException.WrongFormatException;

/**
 * Simple absolute (evil!) GUI for Ristex
 */
public class MainFrame extends JFrame {
	
	private RIS entry;
	private Bibtex bibentry;
	private JTextArea risText;
	private JTextArea bibtexText;
	private JButton risToBibtex;
	private JButton bibtexToRis;
	private JButton closer;
	private static final ImageIcon error_icon = new ImageIcon(System.getProperty("user.dir") + "/src/images/picard.jpg");
	
	/*
	 * Constructor, generating the main frame with all required components
	 */
	public MainFrame() {
		this.setTitle("RisTex - A tool for translating RIS into Bibtex and vice versa");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(900,520);
		this.setLayout(null);
		
		// Add text ares
		JTextArea risText = new JTextArea();
		risText.setBounds(30,35,350,400);
		risText.setLineWrap(true);
		risText.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("RIS entry"),
				BorderFactory.createEmptyBorder(5,5,5,5)
			)
		);
		
		risText.setText("Copy your RIS entry here.");
		this.risText = risText;
		this.add(risText);
		
		JTextArea bibtexText = new JTextArea();
		bibtexText.setBounds(520,35,350,400);
		bibtexText.setLineWrap(true);
		bibtexText.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Bibtex entry"),
				BorderFactory.createEmptyBorder(5,5,5,5)
			)
		);
		
		bibtexText.setText("Copy your bibtex entry here.");
		this.bibtexText = bibtexText;
		this.add(bibtexText);
		
		// Add buttons
		JButton risToBibtex = new JButton("Ris to bibtex >>");
		risToBibtex.setBounds(390,200,120,20);
		this.risToBibtex = risToBibtex;
		this.add(risToBibtex);
		
		JButton bibtexToRis = new JButton("<< Bibtex to ris");
		bibtexToRis.setBounds(390,250,120,20);
		this.bibtexToRis = bibtexToRis;
		this.add(bibtexToRis);
		
		JButton closer = new JButton("Exit");
		closer.setBounds(390,450,120,20);
		this.closer = closer;
		this.add(closer);
		
		// Add coresponding actions to buttons
		risToBibtexAction();
		bibtexToRisAction();
		closeAction();
		
		// Fixed error icon
		
		// Show Frame
		this.setVisible(true);
		setLocationRelativeTo(null);
	}
	
	/*
	 * Action Listener for the button "risToBibtex"
	 */
	private void risToBibtexAction() {
		risToBibtex.addActionListener(
			new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent acteve) {
					
					// Parse risText and store in entry
					String content = risText.getText();
					try {
						entry = RISParser.parseFromString(content);
						// Translate it to bibtex and store in bibentry
						bibentry = new Bibtex(entry);
						
						// Write bibentry to bibtexText
						bibtexText.setText(bibentry.writeToString());
						
					} catch (WrongFormatException e) {
						// Replace by showing an error box
						displayError(e);
					}
				}
			}
		);
	}
	
	/*
	 * Action Listener for the button "bibtexToRis"
	 */
	private void bibtexToRisAction() {
		bibtexToRis.addActionListener(
			new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					displayWarning("Not implemented yet.");
				}
			}
		);
	}
	
	/*
	 * Action Listener for the button "exit"
	 */
	private void closeAction() {
		closer.addActionListener(
			new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			}
		);
	}
	
	/*
	 * Generate a error box for the wrong format exception
	 */
	private void displayError(WrongFormatException ex) {
		JOptionPane.showMessageDialog(this, "Given entry is not in RIS format!", "Error", JOptionPane.ERROR_MESSAGE, error_icon);
	}
	
	/*
	 * Generate a warning box for "not implemented yet"
	 */
	private void displayWarning(String message) {
		JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE, error_icon);
	}
}