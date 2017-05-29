package cotroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Manuscript;
import model.UserProfile;
import model.UserProfileStateManager;

public class SubprogramSelectManuscriptActionListener implements ActionListener {
	
	final Manuscript myManuscript;
	
	public SubprogramSelectManuscriptActionListener(final Manuscript theManuscript){
		myManuscript = theManuscript;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("THIS WORKS");
	}

}
