package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.font.TextAttribute;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cotroller.SubprogramSelectManuscriptActionListener;
import model.Conference;
import model.ConferenceStateManager;
import model.Manuscript;
import model.Role;
import model.UserProfile;
import model.UserProfileStateManager;

public class SubprogramPanel extends AutoSizeablePanel implements Observer{
	private static final long serialVersionUID = 8098693823655450146L;

	private final JLabel titleHeaderArea;
	private final JLabel reviewersAssignedHeaderArea;
	private final JLabel reviewsSubmittedArea;
	private final JLabel recommendedHeaderArea;

	private final Collection<JTextArea> rowTextAreas;

	private final PanelManager myPanelManager;
	
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
		
		
		final Manuscript aManuscript1 = new Manuscript(
				"Intro to Crytography",
				new UserProfile("userid@uw.edu","Johny Mnemonic"),
				new ArrayList<>(),
				ZonedDateTime.now(),
				new File("Path")
				);
		final Manuscript aManuscript2 = new Manuscript(
				"Workload and Resource Aware Proactive Auto-scaler for PaaS Cloud",
				new UserProfile("userid@uw.edu","Johny Mnemonic"),
				new ArrayList<>(),
				ZonedDateTime.now(),
				new File("Path")
				);
		System.out.println(formatTitleName(aManuscript1.getTitle()));
		System.out.println(formatTitleName(aManuscript2.getTitle()));
		
		ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(aManuscript1);
		ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(aManuscript2);
		
		ConferenceStateManager.getInstance().getCurrentConference().
			assignManuscriptToSubprogramChair(aManuscript1, aUser);
		ConferenceStateManager.getInstance().getCurrentConference().
			assignManuscriptToSubprogramChair(aManuscript2, aUser);
		
		//Set up a JFrame to test our panel in:
		EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                final JFrame window = new JFrame();
                final JPanel mainPanel = new SubprogramPanel(null, 0.6, 0.4, new Dimension(2100, 700));
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
	}
	
	public SubprogramPanel(
			final PanelManager thePanelManager,
			final double theXRatio,
			final double theYRatio,
			final Dimension theStartingSize
			){
		super(theXRatio, theYRatio, theStartingSize);
		myPanelManager = thePanelManager;
		titleHeaderArea = new JLabel();
		reviewersAssignedHeaderArea = new JLabel();
		recommendedHeaderArea = new JLabel();
		reviewsSubmittedArea = new JLabel();
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
		final Collection<JLabel> headerAreas = new ArrayList<>(Arrays.asList(
				titleHeaderArea,
				reviewersAssignedHeaderArea,
				reviewsSubmittedArea,
				recommendedHeaderArea
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
		reviewersAssignedHeaderArea.setText("Reviewers\nAssigned");
		reviewsSubmittedArea.setText("Reviews\nSubmitted");
		recommendedHeaderArea.setText("Recommended?");
		
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
		reviewersAssignedHeaderArea.setSize(200, 50);
		add(reviewersAssignedHeaderArea, constraints);
		
		constraints.gridwidth = 200;
		constraints.gridheight = 50;
		constraints.gridx = 1700;
		constraints.gridy = 0;
		reviewsSubmittedArea.setSize(200, 50);
		add(reviewsSubmittedArea, constraints);
		
		constraints.gridwidth = 200;
		constraints.gridheight = 50;
		constraints.gridx = 1900;
		constraints.gridy = 0;
		recommendedHeaderArea.setSize(200, 50);
		add(recommendedHeaderArea, constraints);
		
		//for row add row...
		if(ConferenceStateManager.getInstance().isCurrentConferenceSet()){
			Collection<Manuscript> assignedManuscripts = 
					ConferenceStateManager.getInstance().getCurrentConference().getManuscriptAssignedToSubprogram(
					UserProfileStateManager.getInstance().getCurrentUserProfile());
	//		
			int i = 0;
			for(final Manuscript currentManuscript: assignedManuscripts){
				final JButton titleButton = new JButton(formatTitleName(currentManuscript.getTitle()));
				titleButton.addActionListener(
						new SubprogramSelectManuscriptActionListener(
								myPanelManager,
								ConferenceStateManager.getInstance().getCurrentConference(),
								UserProfileStateManager.getInstance().getCurrentUserProfile(),
								currentManuscript
								));
				constraints.gridwidth = 1500;
				constraints.gridheight = 50;
				constraints.gridx = 0;
				constraints.gridy = 50 + i * 50;
				titleButton.setSize(750, 50);
				Font buttonFont = new Font("Times New Roman", Font.PLAIN, 25);
				final Map attributes = buttonFont.getAttributes();
				attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
				titleButton.setFont(buttonFont.deriveFont(attributes));
				titleButton.setSize(750, 50);
				titleButton.setFocusPainted(false);
				titleButton.setMargin(new Insets(0, 0, 0, 0));
				titleButton.setContentAreaFilled(false);
				titleButton.setBorderPainted(false);
				titleButton.setOpaque(false);
				add(titleButton, constraints);
				
				
				final JTextArea reviewersAssignedArea = new JTextArea();
				rowTextAreas.add(reviewersAssignedArea);
				reviewersAssignedArea.setText(
						currentManuscript.getReviewers().size() +
						" / " + 
						ConferenceStateManager.getInstance().getCurrentConference().MIN_NUM_REVIEWS);
				constraints.gridwidth = 200;
				constraints.gridheight = 50;
				constraints.gridx = 1500;
				constraints.gridy = 50 + i * 50;
				reviewersAssignedArea.setSize(200, 50);
				this.add(reviewersAssignedArea, constraints);
				
				final JTextArea reviewsSubmittedArea = new JTextArea();
				rowTextAreas.add(reviewsSubmittedArea);
				reviewsSubmittedArea.setText(
						currentManuscript.getReviews().size() +
						" / " + 
						currentManuscript.getReviewers().size());
				constraints.gridwidth = 200;
				constraints.gridheight = 50;
				constraints.gridx = 1700;
				constraints.gridy = 50 + i * 50;
				reviewsSubmittedArea.setSize(200, 50);
				this.add(reviewsSubmittedArea, constraints);
				
				
				final JTextArea recommendationArea = new JTextArea();
				rowTextAreas.add(recommendationArea);
				recommendationArea.setText(
						currentManuscript.isRecommendedBy(
								UserProfileStateManager.getInstance().getCurrentUserProfile()
								) ? "Yes" : "No");
				constraints.gridwidth = 200;
				constraints.gridheight = 50;
				constraints.gridx = 1900;
				constraints.gridy = 50 + i * 50;
				recommendationArea.setSize(200, 50);
				this.add(recommendationArea, constraints);
				++i;
			}
		}
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
	
	private void initObservers(){
		//Not sure if i need these yet:
//		ConferenceStateManager.getInstance().addObserver(this);
//		UserProfileStateManager.getInstance().addObserver(this);
	}
	
	@Override
	public void update(final Observable theObservable, final Object theObject) {
		//New paper added? update!
	}
}

