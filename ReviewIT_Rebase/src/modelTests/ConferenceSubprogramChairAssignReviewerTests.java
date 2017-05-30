package modelTests;

import static org.junit.Assert.*;

import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Manuscript;
import model.UserProfile;

/**
 * This class tests the business rule regarding the number of manuscripts that a reviewer
 * can be assigned, and if they are eligible to be assigned a specific manuscript.
 * Business Rule 1 - The reviewer cannot be the author.
 * Business Rule 2 - The reviewer cannot be assigned more than MAX_NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS.
 * @author Lorenzo pacis
 *
 */
public class ConferenceSubprogramChairAssignReviewerTests {
	
	private static final int MAX_NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS = 8;
	private static final int NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS_LESS_THAN_MAX = 6;
	
	// Fields for Conference class
	private static String conferenceName = "Computer Science";
	private static ZoneId zoneId = ZoneId.of("UTC-12");
	
	/** Year, Month, Day, Hour, Minute, Seconds, nano, zoneID */
	private static ZonedDateTime conferenceSubmissionDeadline = ZonedDateTime.of(2017, 11, 30, 23, 45, 59, 1234,
			zoneId);
	
	private static Manuscript manuscript;
	
	private static String submissionUserID = "John117";
	
	private static UserProfile submissionUser = new UserProfile(submissionUserID, "Submitter John Doe");
	
	private static String reviewerUserID = "Kelly087";
	
	private static UserProfile reviewerUser = new UserProfile(reviewerUserID, "Reviewer John Doe");
	
	private static String coAuthorUserID = "Samuel034";
	private static String coAuthorUserName = "CoAuthor John Doe";
	
	private static UserProfile coAuthorUser = new UserProfile(coAuthorUserID, coAuthorUserName);
	
	private Conference conference;
	
	private static ArrayList<String> manuscriptAuthors;

	/**
	 * @author lorenzo pacis
	 * @author Dimitar Kumanov
	 * This method sets up any test furniture prior to tests executing.
	 */
	@Before
	public void setup() {
		manuscriptAuthors = new ArrayList<String>();

		manuscriptAuthors.add(coAuthorUserName);

		conference = new Conference(conferenceName, conferenceSubmissionDeadline);
		manuscript = new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path"));

	}
	
	/**
	 * @author Lorenzo Pacis
	 * @author Dimitar Kumanov
	 * This method will test that {@link model.Conference#assignReviewer(String) assignManuscriptToReviewer} returns true
	 * if the reviewer is not the author of the manuscript.
	 */
	@Test
	public void assignReviewerReturnsTrueIfTheReviewerIsNotTheAuthorOfTheManuscript() {
		conference.submitManuscript(manuscript);
		manuscript.addReviewer(reviewerUser,conference);
		assertTrue(manuscript.getReviewers().contains(reviewerUser));
	}
	
	/**
	 * @author Lorenzo Pacis
	 * @author Dimitar Kumanov
	 * This method will test that {@link model.Conference#assignReviewer(String) assignManuscriptToReviewer} returns false
	 * if the reviewer is the author of the manuscript.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void assignReviewerThrowsIllegalArgumentExceptionIfTheReviewerIsTheAuthorOfTheManuscript() {
		conference.submitManuscript(manuscript);
//		conference.assignManuscriptToReviewer(manuscript, submissionUser);
		manuscript.addReviewer(submissionUser,conference);
	}
	
	/**
	 * @author Lorenzo Pacis
	 * @author Dimitar Kumanov
	 * This method will test that {@link model.Conference#assignReviewer(String) assignManuscriptToReviewer}
	 * throws an IllegalArgumentException if the reviewer is a co author of the manuscript.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void assignReviewerThrowsIllegalArgumentExceptionIfTheReviewerIsCoAuthorOfTheManuscript() {
		conference.submitManuscript(manuscript);
//		conference.assignManuscriptToReviewer(manuscript, coAuthorUser);
		manuscript.addReviewer(coAuthorUser,conference);
	}
	
	/**
	 * @author Lorenzo Pacis
	 * @author Dimitar Kumanov
	 * This method will test that {@link model.Conference#assignReviewer(String) assignManuscriptToReviewer} returns true
	 * if the reviewer has been assigned less than the MAX_NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS.
	 */
	@Test
	public void assignReviewerReturnsTrueIfTheReviewerHasBeenAssignedMaxNumManuscriptsMinusNumManuscriptsLessThanMax() {
		for(int i = 0; i < MAX_NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS - 6; i++) {
			new Manuscript(
					"Intro to Crytography",
					submissionUser,
					manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId),
					new File("Path")
					).addReviewer(reviewerUser,conference);
			
		}
		manuscript.addReviewer(reviewerUser,conference);
		assertTrue(
				"Testing that assign reviewer returns true if the reviewer has been assigned " + (MAX_NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS - NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS_LESS_THAN_MAX) + "manuscripts.",
				manuscript.getReviewers().contains(reviewerUser));
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#assignReviewer(String) assignManuscriptToReviewer} will return true
	 * if the reviewer has been assigned one less than the MAX_NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS.
	 */
	@Test
	public void assignReviewerReturnsTrueIfTheReviewerHasBeenAssignedMaxNumOfManuscriptsMinusOne() {
		for(int i = 0; i < MAX_NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS - 1; i++) {
			new Manuscript(
					"Intro to Crytography",
					submissionUser,
					manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId),
					new File("Path")
					).addReviewer(reviewerUser,conference);
			
		}
		manuscript.addReviewer(reviewerUser,conference);
		assertTrue(
				"Testing that assign reviewer returns true if the reviewer has been assigned " + (MAX_NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS - NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS_LESS_THAN_MAX) + "manuscripts.",
				manuscript.getReviewers().contains(reviewerUser));
		
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#assignReviewer(String) assignManuscriptToReviewer}
	 * throws an IllegalArgumentException if the reviewer has been assigned the MAX_NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void assignReviewerThrowsIllegalArgumentExceptionIfTheReviewerHasBeenAssignedMaxNumOfManuscripts() {
		for(int i = 0; i < MAX_NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS + 1; i++) {
			new Manuscript(
					"Intro to Crytography" + Integer.toString(i),
					submissionUser,
					manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId),
					new File("Path")
					).addReviewer(reviewerUser,conference);
			
		}
		manuscript.addReviewer(reviewerUser,conference);
		assertTrue(
				"Testing that assign reviewer returns true if the reviewer has been assigned " + (MAX_NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS - NUM_REVIEWER_MANUSCRIPT_ASSIGNMENTS_LESS_THAN_MAX) + "manuscripts.",
				manuscript.getReviewers().contains(reviewerUser));
		
	}
}
