package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.Conference;
import model.ConferenceStateManager;
import model.Manuscript;
import model.Role;
import model.UserProfile;
import model.UserProfileStateManager;

public class AuthorPanel extends AutoSizeablePanel implements Observer{
/**
	 * 
	 */
	private static final long serialVersionUID = -516457317769197779L;
	
	private Collection<Manuscript> authorManuscripts;
	private final JTextArea myManuscriptTextArea;
	private final JLabel myTitleLbl;
	private final JLabel myCoAuthorLbl;
	private final JLabel mySubDateLbl;
	private final JLabel myRemoveLbl;


public static void main(String[] args){
		
	ZoneId zoneId = ZoneId.of("UTC+1");
	/** Year, Month, Day, Hour, Minute, Seconds, nano, zoneID */
	ZonedDateTime conferenceSubmissionDeadline = ZonedDateTime.of(4017, 11, 30, 23, 45, 59, 1234,
			zoneId);
	ZonedDateTime manuscriptSubTime = ZonedDateTime.of(2017, 11, 30, 23, 45, 59, 1234,
			zoneId);
	Conference testCon = new Conference("testCon", conferenceSubmissionDeadline);
	UserProfile testUser = new UserProfile("id123", "Author One");
	
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
	Manuscript test5 = new Manuscript("Testing for testing Tests", testUser, testAuthors5, manuscriptSubTime, new File("BLANK"));
	
	ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(test1);
	ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(test2);
	ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(test3);
	ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(test4);
	ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(test5);
		EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {           	
            	
            	//authorPanel.authorTable.setPreferredSize(new Dimension(750, 550));
            	
                final JFrame window = new JFrame();
                final JPanel authorPanel = new AuthorPanel(0.6, 0.4, new Dimension(2100, 700));
                //final JPanel mainPanel = new TemplatePanel(0.6, 0.4, new Dimension(1500, 800));
                
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(authorPanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
	}

public AuthorPanel(double theXRatio, double theYRatio, Dimension theStartingSize) {
	super(theXRatio, theYRatio, theStartingSize);
	myTitleLbl = new JLabel();
	mySubDateLbl = new JLabel();
	myCoAuthorLbl = new JLabel();
	myRemoveLbl = new JLabel();
	myManuscriptTextArea = new JTextArea();
	initialize();
}
	
	
	public void initialize() {
		setLayout(new GridBagLayout());
		initObservers();
		setupLabels();
    	addColumns();
    	addManuscriptRow();
	}

	private void addColumns(){
		
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.9;
		constraints.weighty = 0.9;
		
		//Add title column
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(myTitleLbl, constraints);
		
		//Add CoAuthor column
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 300;
		constraints.gridy = 0;
		add(myCoAuthorLbl, constraints);
		
		//Add SubmissionDate column
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 600;
		constraints.gridy = 0;
		add(mySubDateLbl, constraints);
		
		//Add Remove column
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 900;
		constraints.gridy = 0;
		add(myRemoveLbl, constraints);
		
		
		
	}
	
	private void addManuscriptRow(){
		authorManuscripts = ConferenceStateManager.getInstance().getCurrentConference()
				.getManuscriptsSubmittedBy(UserProfileStateManager.getInstance().getCurrentUserProfile());
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.9;
		constraints.weighty = 0.9;
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 0;
		constraints.gridy = 50;
		for(Manuscript m: authorManuscripts){
			JLabel theTitle = new JLabel(m.getTitle());
			theTitle.addMouseListener(new TitleListener());
			add(theTitle,constraints);
			constraints.gridy += 300;
		}
		
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 300;
		constraints.gridy = 50;
		
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
		constraints.gridy = 50;
		for(Manuscript m: authorManuscripts){
			add(new JLabel(m.getMySubmissionDate().toString()),constraints);
			constraints.gridy += 300;
		}
		
		constraints.gridwidth = 100;
		constraints.gridheight = 25;
		constraints.gridx = 900;
		constraints.gridy = 50;
		for(Manuscript m: authorManuscripts){
			JButton removeButton = new JButton("Remove");
			removeButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int userReply = JOptionPane.showConfirmDialog(null, "Do you really want to remove this manuscript?",
							"Remove Manuscript", JOptionPane.YES_NO_OPTION);
					if(userReply == JOptionPane.YES_OPTION){
						ConferenceStateManager.getInstance().getCurrentConference().deleteManuscript(m);
						removeAll();
						initialize();
						revalidate();
						repaint();
						
					}
					
				}
				
			});
			add(removeButton, constraints);
			constraints.gridy += 300;
		}
		
		
		
	}
	private void setupLabels() {
		myTitleLbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myTitleLbl.setText("Title");
		myCoAuthorLbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myCoAuthorLbl.setText("Co Authors");
		mySubDateLbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		mySubDateLbl.setText("Submission Date");		
		myRemoveLbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myRemoveLbl.setText("Remove Option");
		
	}
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
	

	public class RemoveListener implements ActionListener {

		private final Manuscript manuscriptToRemove;
		
		RemoveListener(Manuscript theManuscript){
			manuscriptToRemove = theManuscript;
		}
		
		@Override
		public void actionPerformed(ActionEvent theEvent) {
			int userReply = JOptionPane.showConfirmDialog(null, "Do you really want to remove this manuscript?",
					"Remove Manuscript", JOptionPane.YES_NO_OPTION);
			if(userReply == JOptionPane.YES_OPTION){
				ConferenceStateManager.getInstance().getCurrentConference().deleteManuscript(manuscriptToRemove);
				
			}
		}
		
	}
	
	
	/**
	 * Public inner class that handles what action happens when a user clicks a manuscripts title
	 * @author Harlan Stewart
	 *
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