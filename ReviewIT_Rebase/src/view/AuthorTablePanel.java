package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Conference;
import model.ConferenceStateManager;
import model.Role;
import model.UserProfile;
import model.UserProfileStateManager;

public class AuthorTablePanel extends AutoSizeablePanel implements Observer{
	private static final long serialVersionUID = 8098693823655450146L;

	private final JTextArea titleHeaderArea;
	private final JTextArea authorsHeaderArea;
	private final JTextArea submitDateHeaderArea;

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
		
		//Set up a JFrame to test our panel in:
		EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                final JFrame window = new JFrame();
                final JPanel mainPanel = new AuthorTablePanel(0.6, 0.4, new Dimension(2100, 1600));
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
	}
	
	public AuthorTablePanel(
			final double theXRatio,
			final double theYRatio,
			final Dimension theStartingSize
			){
		super(theXRatio, theYRatio, theStartingSize);
		titleHeaderArea = new JTextArea();
		authorsHeaderArea = new JTextArea();
		submitDateHeaderArea = new JTextArea();
		rowTextAreas = new ArrayList<>();
		initialize();
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		initTable();
		initObservers();
		addLabels();
	}
	
	private void initTable() {
		titleHeaderArea.setText("Title");
		authorsHeaderArea.setText("Authors");
		submitDateHeaderArea.setText("Date Submitted");
	}

	private void formatTableTextAreas(){
		for (JTextArea currentTextArea: rowTextAreas){
//			currentTextArea.setFont();
		}
	}
	
	private void initObservers(){
		//Not sure if i need these yet:
//		ConferenceStateManager.getInstance().addObserver(this);
//		UserProfileStateManager.getInstance().addObserver(this);
	}
	
	private void addLabels(){
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.9;
		constraints.weighty = 0.9;
		
		constraints.gridwidth = 100;
		constraints.gridheight = 40;
		constraints.gridx = 0;
		constraints.gridy = 0;
//		add(myReviewITTextArea, constraints);
		
	}
	
	@Override
	public void update(final Observable theObservable, final Object theObject) {
		//New paper added? update!
	}
}

