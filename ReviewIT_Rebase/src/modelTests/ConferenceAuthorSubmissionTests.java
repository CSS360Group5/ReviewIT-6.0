package modelTests;

import model.Conference;
import model.Manuscript;
import model.UserProfile;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * This class tests the business rule regarding the number of author submissions to a conference.
 * Business Rule 1 - The submission must be before the submission deadline.
 * Business Rule 2 - The author must not have submitted or co authored 5 manuscripts.
 * @author lorenzo pacis
 * @version 5.3.2017
 */
public class ConferenceAuthorSubmissionTests {
	
	private static final int MAX_NUM_AUTHOR_SUBMISSIONS = 5;
	private static final int NUM_AUTHOR_SUBMISSIONS_LESS_THAN_MAX = 3;
	// Fields for Conference class
	private static String conferenceName = "Computer Science";
	private static ZoneId zoneId = ZoneId.of("UTC-12");
	
	/** Year, Month, Day, Hour, Minute, Seconds, nano, zoneID */
	private static ZonedDateTime conferenceSubmissionDeadline = ZonedDateTime.of(2017, 11, 30, 23, 45, 59, 1234,
			zoneId);
	
	private static ArrayList<String> manuscriptAuthors;
	
	private static String submissionUserID = "John117";
	
	private static UserProfile submissionUser = new UserProfile(submissionUserID, "Author John Doe");
	
	private static String coAuthorUserID = "Samuel034";
	
	private static UserProfile coAuthornUser = new UserProfile(coAuthorUserID, "Co-Author John Doe");
	
	private static String secondCoAuthor = "Frederoc104";
	
	private static UserProfile secondCoAuthornUser = new UserProfile(secondCoAuthor, "Second Co-Author John Doe");
	
	private static ArrayList<String> secondCoAuthorList;
	
	private static Manuscript manuscript;
	
	private static Manuscript justBeforeDeadlineManuscript;
	
	private static Manuscript justAfterDeadlineManuscript;
	
	private static Manuscript afterDeadlineManuscript;
	
	
	private Conference conference;
	
	/**
	 * @author lorenzo pacis
	 * This method sets up any test furniture prior to tests executing.
	 */
	@Before
	public void setup() {
		manuscriptAuthors = new ArrayList<String>();
		secondCoAuthorList = new ArrayList<String>();
		secondCoAuthorList.add(secondCoAuthor);

		manuscriptAuthors.add(coAuthorUserID);
		conference = new Conference(conferenceName, conferenceSubmissionDeadline);
		manuscript = new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path"));
		justBeforeDeadlineManuscript = new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
				ZonedDateTime.of(2017, 11, 30, 23, 45, 58, 1234, zoneId), new File("Path"));

		justAfterDeadlineManuscript = new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
				ZonedDateTime.of(2017, 12, 1, 0, 0, 0, 0, zoneId), new File("Path"));
		afterDeadlineManuscript = new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
				ZonedDateTime.of(2017, 12, 2, 0, 0, 0, 0, zoneId), new File("Path"));

	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#submitManuscript(Manuscript) submitManuscript} will return true if the submission
	 * is before the submission deadline.
	 */
	@Test
	public void testAuthorSubmitsManuscriptBeforeSubmissionDeadlineReturnsTrue() {
		assertTrue("Testing that submit manuscript returns true if the submission is before the deadline." ,
				conference.submitManuscript(manuscript));
		
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#submitManuscript(Manuscript) submitManuscript} will
	 * return true if the manuscript is submitted just before the deadline.
	 */
	@Test
	public void testAuthorSubmitsManuscriptJustBeforeSubmissionDeadlineReturnsTrue() {
		assertTrue("Testing that submit manuscript returns true if the submission is JUST before the deadline.",
				conference.submitManuscript(justBeforeDeadlineManuscript));

		
	}
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#submitManuscript(Manuscript) submitManuscript} 
	 * throws an IllegalArgumentException if the manuscript is submitted just after the deadline.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAuthorSubmitsManuscriptJustAfterDeadlineThrowsIllegalArgumentException() {
			conference.submitManuscript(justAfterDeadlineManuscript);
		
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#submitManuscript(Manuscript) submitManuscript}
	 * throws an IllegalArgumentException if the manuscript is submitted after the deadline.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAuthorSubmitsManuscriptAfterDeadlineThrowsIllegalArgumentException() {
			conference.submitManuscript(afterDeadlineManuscript);
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#submitManuscript(Manuscript) submitManuscript} will return
	 * true if the author has co authored MAX_NUM_AUTHOR_SUBMISSIONS - 2 manuscripts.
	 */
	@Test
	public void testAuthorSubmitsManuscriptAfterCoAuthoringAndAuthoringMaxNumManuscriptsMinusNumManuscriptsLessThanMaxReturnsTrue() {
		for(int i = 0; i < 2; i++) {
			conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
		}
		
	
		for(int i = 0; i < 2; i++) {
			conference.submitManuscript(new Manuscript("Intro to Crytography", coAuthornUser, secondCoAuthorList,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
		}
		
		assertTrue("Testing that submit manuscript returns true after the author has submitted " + (MAX_NUM_AUTHOR_SUBMISSIONS - NUM_AUTHOR_SUBMISSIONS_LESS_THAN_MAX) + "manuscripts.", 
				conference.submitManuscript((new Manuscript("Intro to Crytography", coAuthornUser, secondCoAuthorList,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")))));
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#submitManuscript(Manuscript) submitManuscript} will return
	 * true if the author has co authored MAX_NUM_AUTHOR_SUBMISSIONS - 1 manuscripts.
	 */
	@Test
	public void testAuthorSubmitsManuiscriptsAfterCoAuthoringMaxNumManuscriptsMinusOneReturnsTrue() {
		for(int i = 0; i < 4; i++) {
			conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
		}
		
		assertTrue("Testings that submit manuscript returns true after the author has co authored " + (MAX_NUM_AUTHOR_SUBMISSIONS - NUM_AUTHOR_SUBMISSIONS_LESS_THAN_MAX) + "manuscripts.",
				conference.submitManuscript((new Manuscript("Intro to Crytography", coAuthornUser, secondCoAuthorList,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")))));
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#submitManuscript(Manuscript) submitManuscript} returns true
	 * if the author has submitted the MAX_NUM_AUTHOR_SUBMISSIONS -1 manuscripts.
	 */
	@Test
	public void testAuthorSubmitsManuscriptAfterSubmittingMaxNumManuscriptsMinusOneReturnsTrue() {
		for(int i = 0; i  < 4; i++) { 
			conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
		}
		
		assertTrue("Testing that submit manuscript returns true after the author submits " + (MAX_NUM_AUTHOR_SUBMISSIONS -1) + "manuscripts.", 
				conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path"))));
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#submitManuscript(Manuscript) submitManuscript} throws
	 * IllegalArgumentExceptionif the author has co authored the MAX_NUM_AUTHOR_SUBMISSIONS manuscripts.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAuthorSubmitsManuscriptAfterCoAuthoringMaxNumManuscriptsThrowsIllegalArgumentException() {
		for(int i = 0; i  < MAX_NUM_AUTHOR_SUBMISSIONS ; i++) { 
			conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
		}
		conference.submitManuscript(new Manuscript("Intro to Crytography", coAuthornUser, secondCoAuthorList,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#submitManuscript(Manuscript) submitManuscript} will throw
	 * an IllegalArgumentException if the author has submitted the MAX_NUM_AUTHOR_SUBMISSIONS manuscripts.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAuthorSubmitsFiveManuscriptsAfterAuthoringMaxNumManuscriptsThrowsIllegalArgumentException() {
		for(int i = 0; i  < MAX_NUM_AUTHOR_SUBMISSIONS ; i++) { 
			conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
		}
				conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will test that {@link model.Conference#submitManuscript(Manuscript) submitManuscript}
	 * throws an IllegalArgumentException if the author has submitted the MAX_NUM_AUTHOR_SUBMISSIONS -1 
	 * manuscripts and co authored the MAX_NUM_AUTHOR_SUBMISSIONS - 1 manuscripts. The resulting 
	 * number they have submitted will reach the MAX_NUM_AUTHOR_SUBMISSIONS.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAuthorSubmitsMaxNumManuscriptsMinusThreeTheyAuthoredAndHasCoAuthoredMaxNumManuscriptsMinusTwoReturnsFalse() {
		for(int i = 0; i < MAX_NUM_AUTHOR_SUBMISSIONS  - 2; i++) {
			conference.submitManuscript(new Manuscript("Intro to Crytography", coAuthornUser, secondCoAuthorList,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
		}
		
		for(int i = 0; i < MAX_NUM_AUTHOR_SUBMISSIONS - 3; i++) {
			conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUser, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
		}

			conference.submitManuscript(new Manuscript("Intro to Crytography", coAuthornUser, secondCoAuthorList,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
	}

}
