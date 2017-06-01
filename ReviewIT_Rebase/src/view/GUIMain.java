package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.ZonedDateTime;

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
				final JFrame window = new JFrame("ReviewIT");
				final JPanel mainPanel = new LoginPanel(1, 1, new Dimension(2100, 700));


				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setContentPane(mainPanel);
				window.pack();
				window.setLocationRelativeTo(null);
				window.setVisible(true);

//                window.pack();
                window.setSize(new Dimension(1350, 450));
            }
        });
    }


	private static void initData() {
		//Add some data:
		Conference con1 = new Conference("Collab' on VR Food as Viable " +
				"Placebic Nutritional Source",
				ZonedDateTime.now().plusHours(24));
		Conference con2 = new Conference("Right Scientists United Against Wrong Scientists 2017",
				ZonedDateTime.now().plusHours(24));
		Conference con3 = new Conference("Anti Everything TechPAC 2016 + 1 (F**K 7)",
				ZonedDateTime.now().plusHours(24));

		ConferenceStateManager.getInstance().addConference(con1);
		ConferenceStateManager.getInstance().addConference(con2);
		ConferenceStateManager.getInstance().addConference(con3);

		UserProfile userKevin = new UserProfile("kev@uw.edu", "Kevin");
		userKevin.addRole(Role.AUTHOR, con1);
		userKevin.addRole(Role.SUBPROGRAM, con1);
		userKevin.addRole(Role.AUTHOR, con2);
		userKevin.addRole(Role.REVIEWER, con2);
		userKevin.addRole(Role.AUTHOR, con3);
		userKevin.addRole(Role.SUBPROGRAM, con3);
		userKevin.addRole(Role.REVIEWER, con3);

		UserProfile userTyson = new UserProfile("mikeT@uw.edu", "Mike Tyson");
		userKevin.addRole(Role.AUTHOR, con1);
		userKevin.addRole(Role.SUBPROGRAM, con1);
		userKevin.addRole(Role.AUTHOR, con2);
		userKevin.addRole(Role.REVIEWER, con2);
		userKevin.addRole(Role.AUTHOR, con3);
		userKevin.addRole(Role.SUBPROGRAM, con3);
		userKevin.addRole(Role.REVIEWER, con3);

		UserProfile userFrederic = new UserProfile("freddy81chops@uw.edu", "Frederic Chopin");
		userKevin.addRole(Role.AUTHOR, con1);
		userKevin.addRole(Role.AUTHOR, con2);
		userKevin.addRole(Role.REVIEWER, con2);
		userKevin.addRole(Role.AUTHOR, con3);
		userKevin.addRole(Role.SUBPROGRAM, con3);
		userKevin.addRole(Role.REVIEWER, con3);

		UserProfile userStan = new UserProfile("stan@uw.edu", "Stanley :)");
		userKevin.addRole(Role.AUTHOR, con1);
		userKevin.addRole(Role.AUTHOR, con2);
		userKevin.addRole(Role.REVIEWER, con2);
		userKevin.addRole(Role.AUTHOR, con3);
		userKevin.addRole(Role.REVIEWER, con3);


		UserProfileStateManager.getInstance().addUserProfile(userKevin);
		UserProfileStateManager.getInstance().addUserProfile(userTyson);
		UserProfileStateManager.getInstance().addUserProfile(userFrederic);
		UserProfileStateManager.getInstance().addUserProfile(userStan);



//		final Conference aConference = new Conference(
//				"39th International Conference on Software Engineering",
//				ZonedDateTime.parse("2017-06-12T10:15:30+01:00[Europe/Paris]")
//			);
//		ConferenceStateManager.getInstance().addConference(aConference);
//		ConferenceStateManager.getInstance().setCurrentConference(aConference);
//
//		final UserProfile aUser = new UserProfile("eric17@uw.edu", "Eric Vanroy");
//		UserProfileStateManager.getInstance().addUserProfile(aUser);
//		UserProfileStateManager.getInstance().setCurrentUser(aUser);
//
//		UserProfileStateManager.getInstance().setCurrentRole(Role.SUBPROGRAM);
//
//
//		final Manuscript aManuscript1 = new Manuscript(
//				"Intro to Crytography",
//				new UserProfile("userid@uw.edu","Johny Mnemonic"),
//				new ArrayList<>(),
//				ZonedDateTime.now(),
//				new File("Path")
//				);
//		final Manuscript aManuscript2 = new Manuscript(
//				"Workload and Resource Aware Proactive Auto-scaler for PaaS Cloud",
//				new UserProfile("userid@uw.edu","Johny Mnemonic"),
//				new ArrayList<>(),
//				ZonedDateTime.now(),
//				new File("Path")
//				);
//
//		ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(aManuscript1);
//		ConferenceStateManager.getInstance().getCurrentConference().submitManuscript(aManuscript2);
//
//		ConferenceStateManager.getInstance().getCurrentConference().
//			assignManuscriptToSubprogramChair(aManuscript1, aUser);
//		ConferenceStateManager.getInstance().getCurrentConference().
//			assignManuscriptToSubprogramChair(aManuscript2, aUser);
//
	}
}
