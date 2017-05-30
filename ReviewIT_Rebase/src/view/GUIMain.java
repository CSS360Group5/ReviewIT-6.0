package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Conference;
import model.ConferenceStateManager;
import model.Manuscript;
import model.Role;
import model.UserProfile;
import model.UserProfileStateManager;

public class GUIMain {
    /**
     * A private constructor to prevent class instantiation.
     * @throws IllegalStateException Should not be instantiated.
     */
    private GUIMain() 
    {
        throw new IllegalStateException();
    }

    
    /**
     * The main method for starting up a JFrame containg the drawing application.
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs){
    	initData();
        EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                final JFrame window = new JFrame();
                final PanelManager mainPanel = new PanelManager(window);
                mainPanel.setNavigationPanel(new SubprogramTablePanel(mainPanel, 0.6, 0.4, new Dimension(2100, 700)));

                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                window.pack();
                window.setMinimumSize(new Dimension(750, 350));
                window.setLocationRelativeTo(null);
                window.setVisible(true);
//                window.pack();
                window.setSize(new Dimension(1350, 450));
            }
        });
    }


	private static void initData() {
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
		
		ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(aManuscript1);
		ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(aManuscript2);
		
		ConferenceStateManager.getInstance().getCurrentConference().
			assignManuscriptToSubprogramChair(aManuscript1, aUser);
		ConferenceStateManager.getInstance().getCurrentConference().
			assignManuscriptToSubprogramChair(aManuscript2, aUser);
		
	}
}
