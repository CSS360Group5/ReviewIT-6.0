package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import model.Conference;
import model.ConferenceStateManager;
import model.Manuscript;
import model.UserProfile;
import model.UserProfileStateManager;

public class AuthorPanel extends AutoSizeablePanel implements Observer{
/**
	 * 
	 */
	private static final long serialVersionUID = -516457317769197779L;
	
	private Collection<Manuscript> authorManuscripts;
	private final JLabel myTitleLbl;
	private final JLabel myCoAuthorLbl;
	private final JLabel mySubDateLbl;
	private final JLabel myRemoveLbl;
	private JButton mySubmitManuscriptBtn;
	private JFormattedTextField myTitleTextField;
	private JFormattedTextField myCoAuthorTextField;
	private JFormattedTextField myUploadTextField;
	private JButton myFinalSubmitBtn;
	private JButton myCancelSubmitBtn;
	private JButton myFileUploadBtn;
	private JLabel mySubmitTitleLbl;
	private JLabel mySubmitCoAuthLbl;
	private JLabel mySubmitHeaderLbl;
	private JLabel numSubmits;
	private File fileToUpload;
	private String filePath;
	private String fileName;

/**
 * main method used for testing purposes while developing this panel.
 * @param args
 */
public static void main(String[] args){
		
	ZoneId zoneId = ZoneId.of("UTC+1");
	/** Year, Month, Day, Hour, Minute, Seconds, nano, zoneID */
	ZonedDateTime conferenceSubmissionDeadline = ZonedDateTime.of(4017, 11, 30, 23, 45, 59, 1234,
			zoneId);
	ZonedDateTime manuscriptSubTime = ZonedDateTime.of(2017, 11, 30, 23, 45, 59, 1234,
			zoneId);
	Conference testCon = new Conference("testCon", conferenceSubmissionDeadline);
	UserProfile testUser = new UserProfile("id123", "Author One");
	UserProfile testUser2 = new UserProfile("id1234", "Reviewer One");
	
	ConferenceStateManager.getInstance().addConference(testCon);
	ConferenceStateManager.getInstance().setCurrentConference(testCon);
	UserProfileStateManager.getInstance().addUserProfile(testUser);
	UserProfileStateManager.getInstance().setCurrentUser(testUser);
	
	
	List<String> testAuthors1 = new ArrayList<>(Arrays.asList("John Doe","Stacy Doe","Mark Suckerbergs"));
	List<String> testAuthors2 = new ArrayList<>(Arrays.asList("Hater Guy","Frank Einstein","Zohan Rowland"));
	List<String> testAuthors3 = new ArrayList<>(Arrays.asList("Jimminy Cricket","Winni Poe","Ralph Wreck"));
	List<String> testAuthors4 = new ArrayList<>(Arrays.asList("Sonic Hog","Knuckles Chin","Fox Tails"));
	List<String> testAuthors5 = new ArrayList<>(Arrays.asList("Candy Man","Jason Freddy","Rob Zombie"));
	
	Manuscript test1 = new Manuscript("Computer Science Big Oh Nose", testUser, testAuthors1, manuscriptSubTime, new File("BLANK"));
	Manuscript test2 = new Manuscript("Object Oriented Operations", testUser, testAuthors2, manuscriptSubTime, new File("BLANK"));
	Manuscript test3 = new Manuscript("Java Coffee and Life", testUser, testAuthors3, manuscriptSubTime, new File("BLANK"));
	Manuscript test4 = new Manuscript("Refactor Reform Relief", testUser, testAuthors4, manuscriptSubTime, new File("BLANK"));
	//Manuscript test5 = new Manuscript("Testing for testing Tests", testUser, testAuthors5, manuscriptSubTime, new File("BLANK"));
	test4.addReviewer(testUser2, testCon);
	
	ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(test1);
	ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(test2);
	ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(test3);
	ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(test4);
	//ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(test5);
		EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {           	
                final JFrame window = new JFrame();
                final JPanel authorPanel = new AuthorPanel(0.6, 0.4, new Dimension(1500, 800));
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(authorPanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
	}

	/**
	 * Constructor used to instantiate an AuthorPanel and all of its methods.
	 * @param theXRatio default xRatio for panel
	 * @param theYRatio default yRatio for panel
	 * @param theStartingSize default starting size for the panel
	 */
	public AuthorPanel(double theXRatio, double theYRatio, Dimension theStartingSize) {
		super(theXRatio, theYRatio, theStartingSize);
		myTitleLbl = new JLabel();
		mySubDateLbl = new JLabel();
		myCoAuthorLbl = new JLabel();
		myRemoveLbl = new JLabel();
		new JTextArea();
		mySubmitManuscriptBtn = new JButton();
		initialize();
}
	
	/**
	 * Method that is used to create the initial GUI panel that is displayed when
	 * an AuthorPanel is instantiated.
	 */
	public void initialize() {
		setLayout(new GridBagLayout());
		initObservers();
		setupLabels();
    	addColumns();
    	addManuscriptRow();
	}

	private void updateSubCountLabel() {
		if(ConferenceStateManager.getInstance().getCurrentConference().
				isAuthorUnderManuscriptSubmissionLimit(UserProfileStateManager.getInstance().getCurrentUserProfile().getName())) {
			numSubmits.setForeground(Color.GREEN);
			mySubmitManuscriptBtn.setEnabled(true);
		}else {
			numSubmits.setForeground(Color.RED);
			mySubmitManuscriptBtn.setEnabled(false);
		}
	}
	/**
	 * Private helper method to add the labels and establish columns for the author panel
	 * that will display all of an authors manuscripts, the manuscripts co authors, the submission time,
	 * and also an option for removing a manuscript.
	 */
	private void addColumns(){		
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.9;
		constraints.weighty = 0.9;
		
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 0;
		constraints.gridy = 0;
		mySubmitManuscriptBtn.setText("Submit Manuscript");
		mySubmitManuscriptBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				removeAll();
				initializeSubmit();
				revalidate();
				repaint();
			}			
		});
		add(mySubmitManuscriptBtn, constraints);
		constraints.gridx = 0;
		constraints.gridy = 25;
		numSubmits = new JLabel();
		numSubmits.setText("Allowed Submissions: "+ConferenceStateManager.getInstance().getCurrentConference().
				getManuscriptsSubmittedBy(UserProfileStateManager.getInstance().getCurrentUserProfile()).size() + "/" +
				ConferenceStateManager.getInstance().getCurrentConference().MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS);
		updateSubCountLabel();
/*		if(ConferenceStateManager.getInstance().getCurrentConference().
				isAuthorUnderManuscriptSubmissionLimit(UserProfileStateManager.getInstance().getCurrentUserProfile().getName())) {
			numSubmits.setForeground(Color.GREEN);
		}else {
			numSubmits.setForeground(Color.RED);
			mySubmitManuscriptBtn.setEnabled(false);
		}*/
		add(numSubmits, constraints);
		
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 0;
		constraints.gridy = 50;
		add(myTitleLbl, constraints);
		
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 300;
		constraints.gridy = 50;
		add(myCoAuthorLbl, constraints);
		
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 600;
		constraints.gridy = 50;
		add(mySubDateLbl, constraints);	
		
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 900;
		constraints.gridy = 50;
		add(myRemoveLbl, constraints);	
	}
	
	/**
	 * Private helper method to create a JPanel with a SpringLayout that uses a utility
	 * class written by Oracle and found at http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/SpringGridProject/src/layout/SpringUtilities.java
	 * the utility class is used to format text boxes and labels to make them interact better when displaying.
	 */
	private void createSpringPane() {
		JPanel springPane = new JPanel(new SpringLayout());
		mySubmitHeaderLbl = new JLabel("Please enter the information about the manuscript you want to submit.", JLabel.TRAILING);
		mySubmitHeaderLbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		springPane.add(mySubmitHeaderLbl);
		JFormattedTextField blankField = new JFormattedTextField();
		blankField.setVisible(false);
		mySubmitHeaderLbl.setLabelFor(blankField);
		springPane.add(blankField);
		
		mySubmitTitleLbl = new JLabel("Manuscript Title:", JLabel.TRAILING);
		mySubmitTitleLbl.setFont(new Font("Times New Roman", Font.BOLD, 15));
		springPane.add(mySubmitTitleLbl);
		myTitleTextField = new JFormattedTextField();
		mySubmitTitleLbl.setLabelFor(myTitleTextField);
		springPane.add(myTitleTextField);
		
		mySubmitCoAuthLbl = new JLabel("Enter CoAuthors:",JLabel.TRAILING);
		mySubmitCoAuthLbl.setFont(new Font("Times New Roman", Font.BOLD, 15));
		springPane.add(mySubmitCoAuthLbl);
		myCoAuthorTextField = new JFormattedTextField();
		mySubmitCoAuthLbl.setLabelFor(myCoAuthorTextField);
		springPane.add(myCoAuthorTextField);
		add(springPane);
		SpringUtilities.makeCompactGrid(springPane,3,2,6,6,6,6);
	}
	
	/**
	 * Private helper method that creates the Upload File button with a custom
	 * action listener that will let the user choose a manuscript file that they wish
	 * to submit to a conference.
	 */
	private void createUploadButton() {
		myFileUploadBtn = new JButton("Upload File");
		myFileUploadBtn.setPreferredSize(new Dimension(100,40));
		myFileUploadBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					fileToUpload = jfc.getSelectedFile();
					fileName = fileToUpload.getName();
					myUploadTextField.setText(fileName);
					filePath = fileToUpload.getAbsolutePath();
				}				
			}			
		});
	}
	
	/**
	 * Private helper method to help create the JPanel that will hold the buttons that appear
	 * on the author submission panel.
	 */
	private void createButtonPane() {
		JPanel buttonPane = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 0;
		createUploadButton();
		buttonPane.add(myFileUploadBtn,constraints);
		
		Component horizontalStrut = Box.createHorizontalStrut(10);
		constraints.gridx = 1;
		constraints.gridy = 0;
		buttonPane.add(horizontalStrut, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		myUploadTextField = new JFormattedTextField();
		myUploadTextField.setPreferredSize(new Dimension(200,30));
		myUploadTextField.setEditable(false);
		myUploadTextField.setBackground(Color.LIGHT_GRAY);
		buttonPane.add(myUploadTextField,constraints);
		
		Component vertStrut = Box.createVerticalStrut(125);
		constraints.gridx = 0;
		constraints.gridy = 1;
		buttonPane.add(vertStrut,constraints);
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 1;
		
		createSubmitButton();		
		buttonPane.add(myFinalSubmitBtn, constraints);
		
		Component horizontalStrut2 = Box.createHorizontalStrut(20);
		constraints.gridx = 1;
		constraints.gridy = 1;
		buttonPane.add(horizontalStrut2,constraints);
		
		myCancelSubmitBtn = new JButton("Cancel");
		myCancelSubmitBtn.setPreferredSize(new Dimension(100,40));
		myCancelSubmitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				initialize();
				revalidate();
				repaint();
				
			}
			
		});
		constraints.gridx = 2;
		constraints.gridy = 1;
		buttonPane.add(myCancelSubmitBtn,constraints);
		add(buttonPane);
	}
	
	/**
	 * Private helper method to create the submission button that uses a custom
	 * action listener to gather the information the user has entered into the panel's
	 * components and then uses that information to create and submit a manuscript to the
	 * current conference.
	 */
	private void createSubmitButton() {
		myFinalSubmitBtn = new JButton("Submit");
		myFinalSubmitBtn.setPreferredSize(new Dimension(100,40));
		myFinalSubmitBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {				
				String[] coAuthorInput = myCoAuthorTextField.getText().split(", ");
				List<String> coAuthorSplit = new ArrayList<String>();
				for(String s: coAuthorInput){
					coAuthorSplit.add(s);
				}
				Manuscript submission = new Manuscript(myTitleTextField.getText(),
						UserProfileStateManager.getInstance().getCurrentUserProfile(), coAuthorSplit, ZonedDateTime.now(), fileToUpload);
				ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(submission);
				removeAll();
				initialize();
				revalidate();
				repaint();
			}			
		});		
	}

	/**
	 * Private helper method that initializes the author submission panel after the user has chosen
	 * to submit a manuscript. Calls other other associated helper methods to display GUI components.
	 */
	private void initializeSubmit() {
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		createSpringPane();
		createButtonPane();
	}

	/**
	 * Private helper method that is used to populate the rows of the author view panel that
	 * displays a users submitted manuscripts and their associated information.
	 */
	private void addManuscriptRow(){
		authorManuscripts = ConferenceStateManager.getInstance().getCurrentConference()
				.getManuscriptsSubmittedBy(UserProfileStateManager.getInstance().getCurrentUserProfile());
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.9;
		constraints.weighty = 0.9;
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 0;
		constraints.gridy = 75;
		for(Manuscript m: authorManuscripts){
			JLabel theTitle = new JLabel(m.getTitle());
			theTitle.addMouseListener(new TitleListener());
			add(theTitle,constraints);
			constraints.gridy += 300;
		}
		
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 300;
		constraints.gridy = 75;
		
		for(Manuscript m: authorManuscripts){
			JComboBox<String> manCoAuthors = new JComboBox<String>();
			manCoAuthors.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX");
			for(String s: m.getAuthors()){
				manCoAuthors.addItem(s);
			}
			add(manCoAuthors, constraints);
			constraints.gridy += 300;
		}
		
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 600;
		constraints.gridy = 75;
		for(Manuscript m: authorManuscripts){
			add(new JLabel(m.getMySubmissionDate().toString()),constraints);
			constraints.gridy += 300;
		}
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 900;
		constraints.gridy = 75;
		for(Manuscript m: authorManuscripts) {
			if(m.getReviewers().isEmpty()) {
				JLabel noReviewers = new JLabel("NO");
				noReviewers.setForeground(Color.RED);
				add(noReviewers, constraints);
				constraints.gridy += 300;
			}else {
				JLabel hasReviewers = new JLabel("YES");
				hasReviewers.setForeground(Color.GREEN);
				add(hasReviewers, constraints);
				constraints.gridy += 300;
			}
			
		}
		
		
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 1200;
		constraints.gridy = 75;
		for(Manuscript m: authorManuscripts) {			
			if(m.getReviewers().isEmpty()) {
				JButton removeButton = new JButton("Remove");
				removeButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						int userReply = JOptionPane.showConfirmDialog(null, "Do you really want to remove this manuscript?",
								"Remove Manuscript", JOptionPane.YES_NO_OPTION);
						if(userReply == JOptionPane.YES_OPTION) {
							ConferenceStateManager.getInstance().getCurrentConference().deleteManuscript(m);
							removeAll();
							initialize();
							updateSubCountLabel();
							revalidate();
							repaint();						
						}					
					}				
				});
				add(removeButton, constraints);
			}
			
			
			constraints.gridy += 300;
		}		
	}
	
	/**
	 * Private helper method to format the font and text strings of the author view
	 * panel and its labels.
	 */
	private void setupLabels() {
		myTitleLbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myTitleLbl.setText("Title");
		myCoAuthorLbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myCoAuthorLbl.setText("Co Authors");
		mySubDateLbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		mySubDateLbl.setText("Submission Date");		
		myRemoveLbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myRemoveLbl.setText("Reviewers");
		
	}
	
	/**
	 * Method that initializes observers to ensure persistent data.
	 */
	private void initObservers(){
		ConferenceStateManager.getInstance().addObserver(this);
		UserProfileStateManager.getInstance().addObserver(this);
	}
	@Override
	public void update(Observable theObservable, Object theObject) {
		if(theObservable instanceof UserProfileStateManager){
			if(theObject instanceof UserProfile){
				authorManuscripts = ConferenceStateManager.getInstance().getCurrentConference()
						.getManuscriptsSubmittedBy(UserProfileStateManager.getInstance().getCurrentUserProfile());
			}
		}else if(theObservable instanceof ConferenceStateManager){
			if(theObject instanceof Conference){
				authorManuscripts = ConferenceStateManager.getInstance().getCurrentConference()
						.getManuscriptsSubmittedBy(UserProfileStateManager.getInstance().getCurrentUserProfile());
			}
		}
		
	}
	
	/**
	 * Public inner class that handles what action happens when a user clicks a manuscripts title
	 */
	public class TitleListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent theEvent) {
			Object[] options = {"Submit Manuscript", "Cancel"};
			int n = JOptionPane.showOptionDialog(theEvent.getComponent(), "What would you like to do with this manuscript?", 
					"Manuscript Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,null);			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}