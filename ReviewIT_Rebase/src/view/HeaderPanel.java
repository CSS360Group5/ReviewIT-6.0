package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import model.Conference;
import model.ConferenceStateManager;
import model.Role;
import model.UserProfile;
import model.UserProfileStateManager;

public class HeaderPanel extends AutoSizeablePanel implements Observer{
	private static final long serialVersionUID = 8098693823655450146L;

	final JLabel myReviewitLabel;
	private final JLabel myConferenceLabel;
	private final JLabel myUserIDLabel;
	private final JLabel myUserNameLabel;
	private final JLabel myUserRoleLabel;

	
	public HeaderPanel(
			final double theXRatio,
			final double theYRatio,
			final Dimension theStartingSize
			){
		super(theXRatio, theYRatio, theStartingSize);
		myReviewitLabel = new JLabel();
		myConferenceLabel = new JLabel();
		myUserIDLabel = new JLabel();
		myUserNameLabel = new JLabel();
		myUserRoleLabel = new JLabel();
		initialize();
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		myReviewitLabel.setText("ReviewIT");
		myConferenceLabel.setText("myConferenceLabel");
		myUserIDLabel.setText("myUserIDLabel");
		myUserNameLabel.setText("myUserNameLabel");
		myUserRoleLabel.setText("myUserRoleLabel");
		addLabels();
		formatLabels();
	}
	
	private void addLabels(){
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.9;
		constraints.weighty = 0.9;
		
		constraints.gridwidth = 100;
		constraints.gridheight = 40;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(myReviewitLabel, constraints);
		
		constraints.gridwidth = 200;
		constraints.gridheight = 40;
		constraints.gridx = 100;
		constraints.gridy = 0;
		add(myConferenceLabel, constraints);
		
		constraints.gridwidth = 100;
		constraints.gridheight = 30;
		constraints.gridx = 0;
		constraints.gridy = 40;
		add(myUserIDLabel, constraints);
		
		constraints.gridwidth = 100;
		constraints.gridheight = 30;
		constraints.gridx = 0;
		constraints.gridy = 70;
		add(myUserNameLabel, constraints);
		
		constraints.gridwidth = 200;
		constraints.gridheight = 45;
		constraints.gridx = 200;
		constraints.gridy = 50;
		add(myUserRoleLabel, constraints);
	}

	private void formatLabels(){
		myReviewitLabel.setFont(new Font("Times New Roman", Font.BOLD + Font.ITALIC, 35));
		myConferenceLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		myUserIDLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myUserNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		myUserRoleLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
	}
	
	@Override
	public void update(Observable theObservable, Object theObject) {
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
		myConferenceLabel.setText("Conference: " + ConferenceStateManager.getInstance().getCurrentConference().getName());
		myUserIDLabel.setText("ID: " + UserProfileStateManager.getInstance().getCurrentUserProfile().getUserID());
		myUserNameLabel.setText("Name: " + UserProfileStateManager.getInstance().getCurrentUserProfile().getName());
		myUserRoleLabel.setText("as: " + ConferenceStateManager.getInstance().getCurrentConference().getName());
		
	}
	
}

