package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.ZonedDateTime;
import java.util.*;

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
		
		
//		//Example of Observer pattern in action:
//		final UserProfile aUserProfile = new UserProfile("jill32@uw.edu", "Jill Howard");
//		UserProfileStateManager.getInstance().addUserProfile(aUserProfile);
//		
//		JButton tempButton = new JButton("Change current user!"); 
//		tempButton.addActionListener(new SampleActionListener(aUserProfile));
//		this.add(tempButton);
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		initObservers();
		addTextAreas();
		formatTextAreas();
		updateText();

	}
	
	private void initObservers(){
		ConferenceStateManager.getInstance().addObserver(this);
		UserProfileStateManager.getInstance().addObserver(this);
	}
	
	private void addTextAreas(){
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.9;
		constraints.weighty = 0.9;
		
		constraints.gridwidth = 25;
		constraints.gridheight = 40;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(myReviewITTextArea, constraints);
		
		constraints.gridwidth = 200;
		constraints.gridheight = 40;
		constraints.gridx = 25;
		constraints.gridy = 0;
		add(myConferenceTextArea, constraints);
		
		constraints.gridwidth = 150;
		constraints.gridheight = 30;
		constraints.gridx = 0;
		constraints.gridy = 40;
		add(myUserIDTextArea, constraints);
		
		constraints.gridwidth = 150;
		constraints.gridheight = 30;
		constraints.gridx = 0;
		constraints.gridy = 70;
		add(myUserNameTextArea, constraints);
		
		constraints.gridwidth = 200;
		constraints.gridheight = 45;
		constraints.gridx = 200;
		constraints.gridy = 50;
		add(myUserRoleTextArea, constraints);

		constraints.gridwidth = 250;
		constraints.gridheight = 0;
		constraints.gridx = 150;
		constraints.gridy = 70;
		JButton reselectBtn = (new JButton("Reselect Conference and Role..."));
		reselectBtn.addActionListener(e -> reselectConferenceAndRole());
		add(reselectBtn, constraints);

		constraints.gridwidth = 300;
		constraints.gridheight = 0;
		constraints.gridx = 300;
		constraints.gridy = 70;
		JButton logoutBtn = (new JButton("Logout"));
		logoutBtn.addActionListener(e -> logout());
		add(logoutBtn, constraints);
	}

	private void reselectConferenceAndRole() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		final JFrame window = new JFrame();
		final JPanel mainPanel = new ConfRoleSelectPanel(1, 1, new Dimension(1350, 450));


		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(mainPanel);
		window.pack();
		window.setLocationRelativeTo(frame);
		window.setVisible(true);
		window.setSize(new Dimension(1350, 450));
		frame.dispose();
	}

	private void logout() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		final JFrame window = new JFrame();
		final JPanel mainPanel = new LoginPanel(1, 1, new Dimension(1350, 450));


		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(mainPanel);
		window.pack();
		window.setLocationRelativeTo(frame);
		window.setVisible(true);
		window.setSize(new Dimension(1350, 450));
		frame.dispose();
	}

	private void formatTextAreas(){
		Collection<JTextArea> textAreas = new ArrayList<>(Arrays.asList(
				myReviewITTextArea,
				myConferenceTextArea,
				myUserIDTextArea,
				myUserNameTextArea,
				myUserRoleTextArea
				));
		myReviewITTextArea.setFont(new Font("Times New Roman", Font.BOLD + Font.ITALIC, 35));
		myReviewITTextArea.setSize(200, 200);
		myConferenceTextArea.setFont(new Font("Times New Roman", Font.BOLD, 40));
		myConferenceTextArea.setSize(1000, 200);
		myUserIDTextArea.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myUserIDTextArea.setSize(650, 200);
		myUserNameTextArea.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myUserNameTextArea.setSize(650, 200);
		myUserRoleTextArea.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myUserRoleTextArea.setSize(200, 200);
		
		for(final JTextArea currentArea: textAreas){
			currentArea.setBackground(this.getBackground());
			currentArea.setEditable(false);
			currentArea.setHighlighter(null);
			currentArea.setWrapStyleWord(true);
			currentArea.setLineWrap(true);
		}
	}
	
	@Override
	public void update(final Observable theObservable, final Object theObject) {
		if(theObservable instanceof UserProfileStateManager){
			if(theObject instanceof UserProfile){
				updateText();
			}
		}else if(theObservable instanceof ConferenceStateManager){
			if(theObject instanceof Conference){
				updateText();
			}
		}	
	}

	private void updateText() {
		myReviewITTextArea.setText("ReviewIT");
		if(ConferenceStateManager.getInstance().isCurrentConferenceSet()){
			myConferenceTextArea.setText("Conference: " + ConferenceStateManager.getInstance().getCurrentConference().getName());
		}else{
			myConferenceTextArea.setText("No Conference selected.");
		}
		if(UserProfileStateManager.getInstance().isCurrentUserProfileSet()){
			myUserIDTextArea.setText("User ID: " + UserProfileStateManager.getInstance().getCurrentUserProfile().getUserID());
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

