package cotroller;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JList;
import javax.swing.JOptionPane;

import model.Conference;
import model.Manuscript;
import model.UserProfile;
import model.UserProfileStateManager;
import view.AuthorPanel;
import view.NavigationPanel;
import view.PanelManager;
import view.TemplatePanel;

public class SubprogramSelectManuscriptActionListener implements ActionListener {
	
	private static final int OPTION_RECOMMEND = 0;
	
	private static final int OPTION_ASSIGN_REVIEWER = 1;
	
	private final UserProfile mySubprogramUser;
	
	private final Conference myConference;
	
	private final Manuscript myManuscript;

	private PanelManager myPanelManager;
	
	public SubprogramSelectManuscriptActionListener(
			final PanelManager thePanelManager,
			final Conference theConference,
			final UserProfile theSubprogramUser,
			final Manuscript theManuscript
			){
		myPanelManager = thePanelManager; 
		mySubprogramUser = theSubprogramUser;
		myConference = theConference;
		myManuscript = theManuscript;
		
	}

	@Override
	public void actionPerformed(final ActionEvent theEvent) {
		List<String> options = new ArrayList<>();
//		myConference.getManuscriptAssignedToSubprogram(mySubprogramUser).contains(myManuscript);

		if(!myManuscript.isRecommendedBy(mySubprogramUser)){
			options.add("Make a Recommendation!");
		}
		
		if(myConference.getSubmissionDeadline().isBefore(ZonedDateTime.now())){
			options.add("Assign a Reviewer!");
		}
		if(options.size() > 0){
			String[] buttons = options.toArray(new String[0]);//{ "Yes", "Yes to all", "No", "Cancel"};    
			int returnValue = JOptionPane.showOptionDialog(
					(Component) theEvent.getSource(),
					"Please select what you want to do with Manuscript \"" + myManuscript.getTitle() + "\"",
					"Choose Action",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					buttons,
					null
					);
			switch(returnValue){
			case OPTION_RECOMMEND:
				myPanelManager.setNavigationPanel(new AuthorPanel(0.6, 0.4, new Dimension(2100, 700)));
				break;
			case OPTION_ASSIGN_REVIEWER:
				myPanelManager.setNavigationPanel(new AuthorPanel(0.6, 0.4, new Dimension(2100, 700)));
				break;
			}
		}else{
			JOptionPane.showMessageDialog(
					(Component) theEvent.getSource(),
					"There are not actions you can take with regards to Manuscript \"" + myManuscript.getTitle() + "\""
					);
			
		}
//		System.out.println(returnValue);
	}

}
