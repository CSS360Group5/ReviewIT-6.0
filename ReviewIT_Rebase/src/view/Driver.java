package view;

import model.Conference;
import model.ConferenceController;
import model.Manuscript;
import model.DataHandler;

import java.io.File;
import java.time.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Driver class for Program.
 * 
 * @author Myles Haynes
 * @author Akshay Goel
 * @author Lorenzo Pacis
 * @version 2.0
 */
public class Driver {

	private static String CONFERENCE_CONTROLLER_FILE = "src/data/ConferenceController.ser";

	private Driver() {
		// Static class, prevents instantiation.
	}

	public static void main(String[] args) {

		String username = displayGetLoginInfo();
		ConferenceController cc = null;
		cc = loadConferenceController(CONFERENCE_CONTROLLER_FILE);
		if(cc == null) cc = initTestData();
		Objects.requireNonNull(cc);
		boolean keepGoing = true;
		while(keepGoing) {
			Conference c = SelectConferenceView.displaySelectConference(cc.getConferences());
			RoleView view;
			switch (c.getRole(username)) {
				case SUBPROGRAM:
					view = new SubView(c, username);
					break;
				case PROGRAM_CHAIR:
				case REVIEWER:
				default:
					view = new AuthorView(c, username);
			}
			view.displayView();
			System.out.println("\n\n\nPlease select:");
			System.out.println("1) Select another conference");
			System.out.println("2) Exit program");
			if(MenuSelection.getMenuOptionSelection(2) == 2) {
				keepGoing = false;
			}
		}
		System.out.println("\n\nExiting....");
		System.out.println("***************************************************");
		saveConferenceController(cc);
	}

	/**
	 * This method gets the login information from the console that the
	 * user types in.
	 * @author Myles Haynes
	 * @return the login information from the console.
	 */
	private static String displayGetLoginInfo() {
		Scanner in = new Scanner(System.in);
		String inputFromConsole;
		System.out.print("Please enter your email address: ");
		inputFromConsole = in.nextLine();
		return inputFromConsole;
	}

	/**
	 * This method reads a serialized conference controller object which
	 * contains the previous state of the system, and returns it.
	 * @author Lorenzo Pacis
	 * @return this method returns the conference controller object.
	 */
	private static ConferenceController loadConferenceController(String theCCFilePath) {
		ConferenceController theObjectToBeRead = null;
		DataHandler handler = new DataHandler(CONFERENCE_CONTROLLER_FILE, theObjectToBeRead);
		theObjectToBeRead = handler.loadData();
		return theObjectToBeRead;
	}

	/**
	 * This method outputs a file with serialized information of the current
	 * state of the system.
	 * @author Lorenzo Pacis
	 * @param theObjectToBeSerialized
	 *            this conference controller object is read and serialized
	 */
	private static void saveConferenceController(ConferenceController theObjectToBeSerialized) {
		DataHandler handler = new DataHandler(CONFERENCE_CONTROLLER_FILE, theObjectToBeSerialized);
		handler.saveData();
	}


	/**
	 * This class will run test data for business rules to be ran and displayed that
	 * the UI is properly implementing the conference class.
	 * @author Myles Haynes
	 * @author Tommy Warren
	 * @author Lorenzo Pacis
	 * @return A conference controller that has a list of conferences with information to be tested.
	 */
	private static ConferenceController initTestData() {
		
		String reviewer = "Samuel034@gmail.com";
		String subProg = "Kelly087@gmail.com";
		String auth = "John117@gmail.com";
		String coAuth = "Frederoc104@gmail.com";
		final int MAX_AUTHOR_SUBMISSIONS = 5;
		ArrayList<String> coAuthors = new ArrayList<String>();
		
		coAuthors.add(coAuth);
		
		ZonedDateTime deadlineSubmission = ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT), ZoneId.of("UTC-12"));
		
		ZonedDateTime deadlineSubmissionPlusThreeDays = deadlineSubmission.plusDays(3);
		ZonedDateTime deadlineSubmissionPlusOneYear = deadlineSubmission.plusYears(1);
		ZonedDateTime deadlineSubmissionPlusOneDay = deadlineSubmission.plusDays(1);
		ZonedDateTime deadlienSubmissionMinusOneDay = deadlineSubmission.minusDays(1);
		Manuscript manuscript = new Manuscript("Intro to Crytography", auth, coAuthors, ZonedDateTime.now(), new File("Path"));
		
		ConferenceController cc = new ConferenceController();
		Conference deadLinePassed = new Conference("After Deadline", deadlineSubmission);
		Conference deadlinePassedOneDay = new Conference("One Day After Deadline", deadlienSubmissionMinusOneDay);
		Conference futureDeadline = new Conference("Before Deadline", deadlineSubmissionPlusThreeDays);
		Conference deadlineOneDayBefore = new Conference("One Day Before Deadline", deadlineSubmissionPlusOneDay);
		Conference twoAssignedToReviewer = new Conference("2 Papers assigned to Reviewer", deadlineSubmissionPlusOneYear);
		Conference sevenAssignedToReviewer = new Conference("7 Papers assigned to Reviewer", deadlineSubmissionPlusOneYear);
		Conference authorFour = new Conference("Author Submitted 4 Manuscripts", deadlineSubmissionPlusOneYear);
		Conference eightAssignedToReviewer = new Conference("8 Papers Assigned To Reviewer", deadlineSubmissionPlusOneYear);
		Conference reviewerIsAnAuthor = new Conference("Reviewer is an Author", deadlineSubmissionPlusOneYear);
		Conference reviewerIsACoAuthor = new Conference("Reviewer is a Co-Author", deadlineSubmissionPlusOneYear);
		Conference userSubmitsFive = new Conference("Author Submits Five Papers", deadlineSubmissionPlusThreeDays);
		Conference coAuthorSubmitsFive = new Conference("CoAuthor submitted 5", deadlineSubmissionPlusThreeDays);
		Conference authorTwoCoAuthorTwo = new Conference("2 Author subs 2 Co Author subs", deadlineSubmissionPlusThreeDays);
		Conference coAuthorHasSubmittedFour = new Conference("4 CoAuth Submissions", deadlineSubmissionPlusThreeDays);
		Conference coAuthorHasThreeAuthorHasTwo = new Conference("2 Author Subs, 3 CoAuthor subs", deadlineSubmissionPlusThreeDays);

		eightAssignedToReviewer.addSubProgramChair("Kelly087@gmail.com");
		twoAssignedToReviewer.addSubProgramChair("Kelly087@gmail.com");
		sevenAssignedToReviewer.addSubProgramChair("Kelly087@gmail.com");
		
		//For Author Submits 4 manuscripts Test Case
		for(int i = 0 ; i< MAX_AUTHOR_SUBMISSIONS - 1; i++){
			manuscript = new Manuscript("Programming is fun! Volume " + i, auth, new ArrayList<String>(), ZonedDateTime.now(), new File("Path"));
			
			authorFour.submitManuscript(manuscript); // testing for Author submitting 4 manuscripts. When testing for 
													 //	five submissions, submit a new manuscript with same author username.		
		}
		
		//For assign reviewer who has two manuscripts assigned
		Manuscript manuscript2 = new Manuscript("Programming AI's For UNSC", auth, coAuthors, ZonedDateTime.now(), new File("Path"));
		Manuscript manuscript3 = new Manuscript("Programming Fleet Comms For UNSC", auth, coAuthors, ZonedDateTime.now(), new File("Path"));
		Manuscript manuscript4 = new Manuscript("Programming Tanks For UNSC", auth, coAuthors, ZonedDateTime.now(), new File("Path"));
		twoAssignedToReviewer.assignReviewer(manuscript2, reviewer);
		twoAssignedToReviewer.assignReviewer(manuscript3, reviewer);
		twoAssignedToReviewer.assignSubManuscripts(manuscript2, subProg);
		twoAssignedToReviewer.assignSubManuscripts(manuscript3, subProg);
		twoAssignedToReviewer.assignSubManuscripts(manuscript4, subProg);
		twoAssignedToReviewer.addReviewer("Jeff@gmail.com");
		twoAssignedToReviewer.addReviewer(reviewer);
		
		//For assign reviewer who has seven manuscripts assigned
		
		for(int i = 0; i < MAX_AUTHOR_SUBMISSIONS+2; i++) {
			manuscript = new Manuscript("Programming is fun! Volume " + i, "Clone #" + i + auth, new ArrayList<String>(), ZonedDateTime.now(), new File("Path"));
			sevenAssignedToReviewer.submitManuscript(manuscript);
			if(i < 7) {
				sevenAssignedToReviewer.assignReviewer(manuscript, reviewer);
			}
			sevenAssignedToReviewer.assignSubManuscripts(manuscript, subProg);
		}
		Manuscript manuscript5 = new Manuscript("Eight Manuscript Assignment", "John117@gmail.com7",coAuthors, ZonedDateTime.now(), new File("Path"));
		sevenAssignedToReviewer.assignSubManuscripts(manuscript5, subProg);
		sevenAssignedToReviewer.addReviewer("Jeff@gmail.com");
		sevenAssignedToReviewer.addReviewer(reviewer);
		
		//For eight papers assigned to a reviewer already
		eightAssignedToReviewer.addReviewer(reviewer);
		for(int i = 0; i < MAX_AUTHOR_SUBMISSIONS + 3; i++) {
			manuscript = new Manuscript("Programming is fun! Volume " + i, "Clone #"+ i + auth, new ArrayList<String>(), ZonedDateTime.now(), new File("Path"));
			eightAssignedToReviewer.submitManuscript(manuscript);
			eightAssignedToReviewer.assignReviewer(manuscript, reviewer);
			eightAssignedToReviewer.assignSubManuscripts(manuscript, subProg);
		}
		manuscript = new Manuscript("UNSC Systems", auth, coAuthors, ZonedDateTime.now(), new File("Path"));
		eightAssignedToReviewer.submitManuscript(manuscript);
		eightAssignedToReviewer.assignSubManuscripts(manuscript, subProg);
		
		//For Reviewer is the author
		Manuscript reviewerIsAuthor = new Manuscript("UNSC AI Programming", auth, coAuthors, ZonedDateTime.now(), new File("Path"));
		reviewerIsAnAuthor.submitManuscript(reviewerIsAuthor);
		reviewerIsAnAuthor.addReviewer(auth);
		reviewerIsAnAuthor.addSubProgramChair(subProg);
		reviewerIsAnAuthor.assignSubManuscripts(reviewerIsAuthor, subProg);
		
		//For reviewer is the co author
		Manuscript reviewerIsCoAuthor = new Manuscript("UNSC Fleet Systems", auth, coAuthors, ZonedDateTime.now(), new File("Path"));
		reviewerIsACoAuthor.submitManuscript(reviewerIsCoAuthor);
		reviewerIsACoAuthor.addSubProgramChair(subProg);
		reviewerIsACoAuthor.assignSubManuscripts(reviewerIsCoAuthor, subProg);
		reviewerIsACoAuthor.addReviewer(coAuth);
		//for co author submitting five manuscripts
		ArrayList<String> moreCoAuthors = new ArrayList<>();
		moreCoAuthors.add(coAuth);
		for(int i = 0; i < 5; i++) {
			manuscript = new Manuscript("Programming is fun! Volume " + i, "Clone #"+ i + auth, moreCoAuthors, ZonedDateTime.now(), new File("Path"));
			coAuthorSubmitsFive.submitManuscript(manuscript);
		}

		
		//for author submits two and co author submits two
		for(int i = 0; i < 2; i++) {
			manuscript = new Manuscript("Programming is fun! Volume " + i, auth, coAuthors, ZonedDateTime.now(), new File("Path"));
			Manuscript manuscript1 = new Manuscript("Programming is too much fun! Volume " + i, coAuth, new ArrayList<String>(), ZonedDateTime.now(), new File("Path"));
			authorTwoCoAuthorTwo.submitManuscript(manuscript1);
			authorTwoCoAuthorTwo.submitManuscript(manuscript);
		}
		
		//For co author has submitted four
		for(int i = 0; i < 4; i++) {
			manuscript = new Manuscript("Programming is fun! Volume " + i, coAuth, new ArrayList<String>(), ZonedDateTime.now(), new File("Path"));
			coAuthorHasSubmittedFour.submitManuscript(manuscript);
		}
		
		//for co author submits 3 author submits 2
		for(int i = 0; i < 2; i++) {
			manuscript = new Manuscript("Programming is fun! Volume " + i, auth, coAuthors, ZonedDateTime.now(), new File("Path"));
			Manuscript manuscript1 = new Manuscript("Programming is too much fun! Volume " + i, coAuth, new ArrayList<String>(), ZonedDateTime.now(), new File("Path"));
			coAuthorHasThreeAuthorHasTwo.submitManuscript(manuscript1);
			coAuthorHasThreeAuthorHasTwo.submitManuscript(manuscript);
		}
		Manuscript manuscript1 = new Manuscript("Programming Cortana's AI", coAuth, new ArrayList<String>(), ZonedDateTime.now(), new File("Path"));
		coAuthorHasThreeAuthorHasTwo.submitManuscript(manuscript1);
		
		cc.addConference(futureDeadline);
		cc.addConference(deadlineOneDayBefore); 
		cc.addConference(deadlinePassedOneDay);
		cc.addConference(deadLinePassed);
		
		cc.addConference(authorTwoCoAuthorTwo);
		cc.addConference(coAuthorHasSubmittedFour);

		cc.addConference(authorFour);
		cc.addConference(coAuthorSubmitsFive); 
		cc.addConference(userSubmitsFive);
		cc.addConference(coAuthorHasThreeAuthorHasTwo);
		
		cc.addConference(twoAssignedToReviewer);
		cc.addConference(sevenAssignedToReviewer);
		cc.addConference(eightAssignedToReviewer);
												  
		
		cc.addConference(reviewerIsAnAuthor); 
		cc.addConference(reviewerIsACoAuthor); 

		return cc;
	}
}

