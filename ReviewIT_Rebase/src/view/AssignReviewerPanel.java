/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Conference;
import model.ConferenceStateManager;
import model.Manuscript;
import model.Role;
import model.UserProfile;
import model.UserProfileStateManager;

/**
 * @author Danielle
 *
 */
public class AssignReviewerPanel extends AutoSizeablePanel implements Observer{

	/**
	 * Generated serialization
	 */
	private static final long serialVersionUID = -6463495129673836491L;
	
	private final JLabel titleHeaderArea;
	private final JLabel selectReviewerHeaderArea;
	private final Collection<JTextArea> rowTextAreas;
	private final JList<String> reviewerSelectionList;

	/**
	 * 
	 * @param theXRatio
	 * @param theYRatio
	 * @param theStartingSize
	 */
	public AssignReviewerPanel(double theXRatio, double theYRatio, Dimension theStartingSize) {
		super(theXRatio, theYRatio, theStartingSize);
		titleHeaderArea = new JLabel();
		selectReviewerHeaderArea = new JLabel();
		reviewerSelectionList = new JList<String>();
		rowTextAreas = new ArrayList<>();
		initialize();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Add some data:
				final Conference aConference = new Conference(
						"39th International Conference on Software Engineering",
						ZonedDateTime.parse("2017-06-12T10:15:30+01:00[Europe/Paris]")
					);
				ConferenceStateManager.getInstance().addConference(aConference);
				ConferenceStateManager.getInstance().setCurrentConference(aConference);
				
				final UserProfile aUser = new UserProfile("eric17@uw.edu", "Eric Vanroy");
				UserProfileStateManager.getInstance().addUserProfile(aUser);
				UserProfileStateManager.getInstance().setCurrentUser(aUser);
				
				UserProfileStateManager.getInstance().setCurrentRole(Role.AUTHOR);
				
				
				final Manuscript aManuscript = new Manuscript(
						"Intro to Crytography",
						new UserProfile("userid@uw.edu","Johny Mnemonic"),
						new ArrayList<>(),
						ZonedDateTime.now(),
						new File("Path")
						);
				
				ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(aManuscript);
				
				ConferenceStateManager.getInstance().getCurrentConference().
					assignManuscriptToSubprogramChair(aManuscript, aUser);
				
		EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                final JFrame window = new JFrame();
                final JPanel mainPanel = new AssignReviewerPanel(1, 1, new Dimension(800, 600));
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
}
	
	private void initialize() {
		setLayout(new GridBagLayout());
		createTable();
		formatTextAreas();
	}

	private void formatTextAreas() {
		final Collection<JLabel> headerAreas = new ArrayList<>(Arrays.asList(
				titleHeaderArea,
				selectReviewerHeaderArea
				));
		for(final JLabel currentArea: headerAreas){
			currentArea.setFont(new Font("Times New Roman", Font.BOLD, 25));
			currentArea.setBackground(this.getBackground());
		}
		for(final JTextArea currentArea: rowTextAreas){
			currentArea.setFont(new Font("Times New Roman", Font.BOLD, 25));
			currentArea.setBackground(this.getBackground());
			currentArea.setEditable(false);
			currentArea.setHighlighter(null);
			currentArea.setWrapStyleWord(true);
			currentArea.setLineWrap(true);
		}
	}

	private void createTable() {
		titleHeaderArea.setText("                               Manuscript Title                                ");
		titleHeaderArea.setHorizontalAlignment(JLabel.CENTER);
		selectReviewerHeaderArea.setText("Select Reviewer");
		
		final GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.01;
		constraints.weighty = 0.01;
		
		constraints.gridwidth = 1500;
		constraints.gridheight = 50;
		constraints.gridx = 0;
		constraints.gridy = 0;
		titleHeaderArea.setSize(2000, 50);
		titleHeaderArea.setBackground(Color.RED);
		add(titleHeaderArea, constraints);
		
		constraints.gridwidth = 200;
		constraints.gridheight = 50;
		constraints.gridx = 1500;
		constraints.gridy = 0;
		selectReviewerHeaderArea.setSize(200, 50);
		add(selectReviewerHeaderArea, constraints);
		
	}
	
	private static String formatTitleName(String theTitle){
		final int maxCharacters = 45;
		final StringBuilder formattedTitle = new StringBuilder();
		formattedTitle.append("<html><u>");
		while(theTitle.length() > maxCharacters){
			formattedTitle.append(theTitle.substring(0, maxCharacters) + "<br>");
			theTitle = theTitle.substring(maxCharacters, theTitle.length());
		}
		formattedTitle.append(theTitle);
		formattedTitle.append("</u></html>");
		return formattedTitle.toString();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
