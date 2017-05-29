package view;

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
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import cotroller.SubprogramSelectManuscriptActionListener;
import model.Conference;
import model.ConferenceStateManager;
import model.Manuscript;
import model.Role;
import model.UserProfile;
import model.UserProfileStateManager;

public class SubprogramTablePanel extends AutoSizeablePanel implements Observer{
	private static final long serialVersionUID = 8098693823655450146L;

	private final JTextArea titleHeaderArea;
	private final JTextArea reviewersAssignedHeaderArea;
	private final JTextArea recommendedHeaderArea;

	private final Collection<JTextArea> rowTextAreas;
	public static void main(String[] args){
		
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
		
		//Set up a JFrame to test our panel in:
		EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                final JFrame window = new JFrame();
                final JPanel mainPanel = new SubprogramTablePanel(0.6, 0.4, new Dimension(2100, 1600));
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
	}
	
	public SubprogramTablePanel(
			final double theXRatio,
			final double theYRatio,
			final Dimension theStartingSize
			){
		super(theXRatio, theYRatio, theStartingSize);
		titleHeaderArea = new JTextArea();
		reviewersAssignedHeaderArea = new JTextArea();
		recommendedHeaderArea = new JTextArea();
		rowTextAreas = new ArrayList<>();
		initialize();
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		createTable();
		formatTextAreas();
		initObservers();
	}
	

	private void formatTextAreas() {
		final Collection<JTextArea> headerAreas = new ArrayList<>(Arrays.asList(
				titleHeaderArea,
				reviewersAssignedHeaderArea,
				recommendedHeaderArea
				));
		for(final JTextArea currentArea: headerAreas){
			currentArea.setFont(new Font("Times New Roman", Font.BOLD, 25));
			currentArea.setEditable(false);
			currentArea.setHighlighter(null);
			currentArea.setWrapStyleWord(true);
			currentArea.setLineWrap(true);
		}
	}

	private void createTable() {
		titleHeaderArea.setText("Manuscript Title");
		reviewersAssignedHeaderArea.setText("Reviewers Assigned");
		recommendedHeaderArea.setText("Recommended?");
		
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.9;
		constraints.weighty = 0.9;
		
		constraints.gridwidth = 1600;
		constraints.gridheight = 50;
		constraints.gridx = 0;
		constraints.gridy = 0;
		titleHeaderArea.setSize(750, 50);
		add(titleHeaderArea, constraints);
		
		constraints.gridwidth = 250;
		constraints.gridheight = 50;
		constraints.gridx = 1600;
		constraints.gridy = 0;
		reviewersAssignedHeaderArea.setSize(200, 50);
		add(reviewersAssignedHeaderArea, constraints);
		
		constraints.gridwidth = 250;
		constraints.gridheight = 50;
		constraints.gridx = 1850;
		constraints.gridy = 0;
		recommendedHeaderArea.setSize(200, 50);
		add(recommendedHeaderArea, constraints);
		
		//for row add row...
		Collection<Manuscript> assignedManuscripts = 
				ConferenceStateManager.getInstance().getCurrentConference().getManuscriptAssignedToSubprogram(
				UserProfileStateManager.getInstance().getCurrentUserProfile());
		for(final Manuscript currentManuscript: assignedManuscripts){
			final JButton titleButton = new JButton((Action) new SubprogramSelectManuscriptActionListener(currentManuscript));
			constraints.gridwidth = 1600;
			constraints.gridheight = 50;
			constraints.gridx = 0;
			constraints.gridy = 0;
			titleButton.setSize(750, 50);
			add(titleButton, constraints);
		}
	}
	
	private void initObservers(){
		//Not sure if i need these yet:
//		ConferenceStateManager.getInstance().addObserver(this);
//		UserProfileStateManager.getInstance().addObserver(this);
	}
	
	private void addLabels(){
		
//		add(myReviewITTextArea, constraints);
		
	}
	
	@Override
	public void update(final Observable theObservable, final Object theObject) {
		//New paper added? update!
	}
}

