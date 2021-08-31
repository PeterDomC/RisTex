package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import citeTypes.Bibtex;
import citeTypes.RIS;
import citeTypes.RISParser;

/**
 * Simple absolute (evil!) layout for Ristex
 */
public class MainFrame extends JFrame {
	
	RIS entry;
	Bibtex bibentry;
	JTextArea risText;
	JTextArea bibtexText;
	JButton risToBibtex;
	JButton bibtexToRis;
	JButton closer;
	
	/*
	 * Constructor, generating the main frame with all required components
	 */
	public MainFrame() {
		this.setTitle("RisTex - A tool for translating RIS into Bibtex");
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
				public void actionPerformed(ActionEvent e) {
					
					// Parse risText and store in entry
					String content = risText.getText();
					//entry = RISParser.parseFromString(content);
					
					// Translate it to bibtex and store in bibentry
					
					// Write bibentry to bibtexText
					bibtexText.setText("Processing");
					
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
					risText.setText("Bibtex to RIS not implemented yet!");
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
}