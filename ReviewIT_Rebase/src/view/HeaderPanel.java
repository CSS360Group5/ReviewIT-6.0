package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.time.ZonedDateTime;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Conference;
import model.ConferenceStateManager;
import model.Role;
import model.UserProfile;
import model.UserProfileStateManager;

public class HeaderPanel extends AutoSizeablePanel implements Observer{
	private static final long serialVersionUID = 8098693823655450146L;

	final JTextArea myReviewITTextArea;
	private final JTextArea myConferenceTextArea;
	private final JTextArea myUserIDTextArea;
	private final JTextArea myUserNameTextArea;
	private final JTextArea myUserRoleTextArea;

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
                final JPanel mainPanel = new HeaderPanel(0.6, 0.4, new Dimension(2100, 700));

                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
	}
	
	public HeaderPanel(
			final double theXRatio,
			final double theYRatio,
			final Dimension theStartingSize
			){
		super(theXRatio, theYRatio, theStartingSize);
		myReviewITTextArea = new JTextArea();
		myConferenceTextArea = new JTextArea();
		myUserIDTextArea = new JTextArea();
		myUserNameTextArea = new JTextArea();
		myUserRoleTextArea = new JTextArea();
		initialize();
		
		
		//Example of Observer pattern in action:
		
		final UserProfile aUserProfile = new UserProfile("jill32@uw.edu", "Jill Howard");
		UserProfileStateManager.getInstance().addUserProfile(aUserProfile);
		
		JButton tempButton = new JButton("Change current user!"); 
		tempButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		        UserProfileStateManager.getInstance().setCurrentUser(aUserProfile);
		    }
		});
		this.add(tempButton);
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		initObservers();
		addLabels();
		formatLabels();
		updateLabelsText();
	}
	
	private void initObservers(){
		ConferenceStateManager.getInstance().addObserver(this);
		UserProfileStateManager.getInstance().addObserver(this);
	}
	
	private void addLabels(){
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.9;
		constraints.weighty = 0.9;
		
		constraints.gridwidth = 100;
		constraints.gridheight = 40;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(myReviewITTextArea, constraints);
		
		constraints.gridwidth = 200;
		constraints.gridheight = 40;
		constraints.gridx = 100;
		constraints.gridy = 0;
		add(myConferenceTextArea, constraints);
		
		constraints.gridwidth = 100;
		constraints.gridheight = 30;
		constraints.gridx = 0;
		constraints.gridy = 40;
		add(myUserIDTextArea, constraints);
		
		constraints.gridwidth = 100;
		constraints.gridheight = 30;
		constraints.gridx = 0;
		constraints.gridy = 70;
		add(myUserNameTextArea, constraints);
		
		constraints.gridwidth = 200;
		constraints.gridheight = 45;
		constraints.gridx = 200;
		constraints.gridy = 50;
		add(myUserRoleTextArea, constraints);
	}

	private void formatLabels(){
		myReviewITTextArea.setFont(new Font("Times New Roman", Font.BOLD + Font.ITALIC, 35));
		myConferenceTextArea.setFont(new Font("Times New Roman", Font.BOLD, 40));
		myConferenceTextArea.setSize(800, 200);
		myConferenceTextArea.setEditable(false);
		myConferenceTextArea.setHighlighter(null);
		myConferenceTextArea.setWrapStyleWord(true);
		myConferenceTextArea.setLineWrap(true);
		
		myUserIDTextArea.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myUserNameTextArea.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myUserRoleTextArea.setFont(new Font("Times New Roman", Font.BOLD, 25));
	}
	
	@Override
	public void update(final Observable theObservable, final Object theObject) {
		
		if(theObservable instanceof UserProfileStateManager){
			if(theObject instanceof UserProfile){
				updateLabelsText();
			}
		}else if(theObservable instanceof ConferenceStateManager){
			if(theObject instanceof Conference){
				updateLabelsText();
			}
		}	
	}

	private void updateLabelsText() {
		myReviewITTextArea.setText("ReviewIT");
		if(ConferenceStateManager.getInstance().isCurrentConferenceSet()){
			myConferenceTextArea.setText("Conference: " + ConferenceStateManager.getInstance().getCurrentConference().getName());
		}else{
			myConferenceTextArea.setText("No Conference selected.");
		}
		if(UserProfileStateManager.getInstance().isCurrentUserProfileSet()){
			myUserIDTextArea.setText("ID: " + UserProfileStateManager.getInstance().getCurrentUserProfile().getUserID());
			myUserNameTextArea.setText("Name: " + UserProfileStateManager.getInstance().getCurrentUserProfile().getName());
		}else{
			myUserIDTextArea.setText("No UserID; No User logged in.");
			myUserNameTextArea.setText("No User Name; No user logged in.");
		}
		if(UserProfileStateManager.getInstance().isCurrentRoleSet()){
			myUserRoleTextArea.setText("as: " + UserProfileStateManager.getInstance().getCurrentRole().getRoleName());
		}else{
			myUserRoleTextArea.setText("No Role selected.");
		}
	}
}

