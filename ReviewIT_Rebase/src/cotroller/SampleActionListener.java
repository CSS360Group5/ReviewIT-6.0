package cotroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.UserProfile;
import model.UserProfileStateManager;

public class SampleActionListener implements ActionListener {
	
	final UserProfile myUserProfileToChange;
	
	public SampleActionListener(final UserProfile theUserProfile){
		myUserProfileToChange = theUserProfile;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UserProfileStateManager.getInstance().setCurrentUser(myUserProfileToChange);
	}

}
