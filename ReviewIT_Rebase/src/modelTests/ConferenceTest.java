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

/**
 * This class tests all methods and business rules for the conference class.
 * @author lorenzo pacis
 * @verison 5.2.2017
 */
public class ConferenceTest {

	// Fields for Conference class
	private static String conferenceName = "Computer Science";
	private static ZoneId zoneId = ZoneId.of("UTC+1");
	
	/** Year, Month, Day, Hour, Minute, Seconds, nano, zoneID */
	private static ZonedDateTime conferenceSubmissionDeadline = ZonedDateTime.of(2017, 11, 30, 23, 45, 59, 1234,
			zoneId);
	
	private static ArrayList<String> manuscriptAuthors;
	
	private static String submissionUserID = "John117";
	
	private static String reviewerUserID = "Kelly087";
	
	private static String coAuthorUserID = "Samuel034";
	
	private static String subProgramChairUserID = "Linda058";
	
	private static String secondCoAuthor = "Frederoc104";
	
	private static Manuscript manuscript;
	
	private static Manuscript manuscriptTwo;
	
	private static Manuscript subProgramChairManuscript;
	
	private static Manuscript subProgramChairIsCoAuthorManuscript;
	
	private static ArrayList<String> manuscriptAuthorsTwo;
	
	private static ArrayList<String> manuscriptAuthorsThree;
	
	private Conference conference;
	
	/**
	 * @author lorenzo pacis
	 * This method sets up any test furniture prior to tests executing.
	 */
	@Before
	public void setup() {
		manuscriptAuthors = new ArrayList<String>();
		manuscriptAuthorsTwo = new ArrayList<String>();
		manuscriptAuthorsThree = new ArrayList<String>();
		manuscriptAuthors.add(coAuthorUserID);
		manuscriptAuthorsTwo.add(secondCoAuthor);
		manuscriptAuthorsThree.add(subProgramChairUserID);
		conference = new Conference(conferenceName, conferenceSubmissionDeadline);
		manuscript = new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path"));
		manuscriptTwo = new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path"));
		subProgramChairManuscript = new Manuscript("Halo Wars", subProgramChairUserID, manuscriptAuthorsTwo,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path"));
		subProgramChairIsCoAuthorManuscript = new Manuscript("Halo Wars", submissionUserID, manuscriptAuthorsThree,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path"));
	 
		
	}
	
	
	/**
	 * @author Lorenzo Pacis
	 * Tests the the constructor sets the fields to the input values.
	 */
	@Test
	public void conferenceConstructorSetsFieldsToParameterValue() {
		assertTrue(conference.getSubmissionDeadline().equals(conferenceSubmissionDeadline));
		assertTrue(conference.getMyConferenceName().equals(conferenceName));
		
	}
	
	/**
	 * @author Lorenzo Pacis
	 * Tests that submit mansucript will throw an IllegalArgumentException if the manuscript
	 * is submitted past the deadline.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void submitManuscriptThrowsIllegalArgumentExceptionIfPastSubmissionDeadline() {
		conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
				ZonedDateTime.of(2017, 11, 30, 23, 46, 59, 1234, zoneId), new File("Path")));
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests the submitManuscript will return true if the manuscript author
	 * has less than 5 submissions.
	 */
	@Test
	public void submitManuscriptReturnsTrueUnderSubmissionLimit() {
		for(int i = 0; i < 4; i++) {
			conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
		}
		assertTrue(conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path"))));
	
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that submitManuscript will throw IllegalArgumentException
	 * if a null manuscript is submitted.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void submitManuscriptThrowsIllegalArgumentExceptionOnNullManuscriptObject() {
		Manuscript nullManuscript = null;
		conference.submitManuscript(nullManuscript);
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This manuscript will return false if the manuscript being submitted
	 * has already been submitted.
	 */
	@Test
	public void submitManuscriptReturnsFalseFromManuscriptAlreadyBeingSubmitted() {
		conference.submitManuscript(manuscript);

		assertFalse(conference.submitManuscript(manuscript));

	}
	
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that submit manuscript will thorw an IllegalArgumentException
	 * if the author has submitted 5 manuscripts.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void submitManuscriptThrowsIllegalArgumentExceptionIfOverSubmissionLimit() {
		for(int i = 0; i < 6; i++) {
			conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
			
		}
		conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));

		
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that assign reviewer returns true if the reviewer has less
	 * than eight manuscripts assigned
	 */
	@Test
	public void testAssignReviewerReturnsTrueWithUnderEightManuscriptsAssigned() {
		
		
		for(int i = 0; i  < 7; i++) {
			conference.assignManuscriptToReviewer(new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")), reviewerUserID);
		}
		
		assertTrue(conference.assignManuscriptToReviewer(new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")), reviewerUserID));

	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that assign reviewer throws an IllegalArgumentException if the reviewer
	 * has 8 manuscripts assigned.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAssignReviewerThrowsIllegalArgumentExceptionWithEightManuscriptsAssigned() {
		for(int i = 0; i < 9; i++) {
			conference.assignManuscriptToReviewer(new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")), reviewerUserID);
		}
		conference.assignManuscriptToReviewer(new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
				ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")), reviewerUserID);
		
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that addReviewer adds the reviewer if the reviewer is not a reviewer already.
	 */
	@Test
	public void testAddReviewerAddsTheReviewerForReviewerNotInReviewerList() {
		conference.addReviewer(reviewerUserID);
		assertTrue(conference.getReviewers().contains(reviewerUserID));
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that addReviewer does not add the reviewer if the reviewer added to the conference
	 * is already a reviewer for this conference.
	 */
	@Test
	public void testAddReviewerDoesntAddReviewerForReviewerInReviewerList() {
		conference.addReviewer(reviewerUserID);
		conference.addReviewer(reviewerUserID);
		assertTrue(conference.getReviewers().size() != 2);
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that assignReviewerManuscript returns false if the reviewer
	 * is the author of the manuscript.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testAssignReviewerManuscriptThrowsIllegalArgumentExceptionOnThatTheyAuthored() {
		conference.submitManuscript(manuscript);
		conference.assignManuscriptToReviewer(manuscript, submissionUserID);
		
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that assignManuscriptToReviewer throws an IllegalArgumentException if the reviewer is a co author of the manuscript.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testAssignReivewerThrowsIllegalArgumentExceptionOnManuscriptThatTheyCoAuthored() {
		conference.submitManuscript(manuscript);
		conference.assignManuscriptToReviewer(manuscript, coAuthorUserID);
		
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that the reviewer has already been assigned to this manuscript.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAssignedReviewerHasBeenPreviouslyAssignedToThisManuscriptThrowsIllegalArgumentException() {
		conference.assignManuscriptToReviewer(manuscript, reviewerUserID);
		conference.assignManuscriptToReviewer(manuscript, reviewerUserID);

	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that getNumSubmissions will return the correct number of manuscript
	 * submissions for the author.
	 */
	@Test
	public void testGetNumSubmissionsReturnsFourForAuthor() {
		for(int i = 0; i < 4; i++) { 
			conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
		}
		assertTrue(conference.getNumSubmissions(submissionUserID) == 4);
	}
	
	@Test
	public void testGetNumSubmissionsReturnsFourForCoAuthor() {
		for(int i = 0; i < 4; i++) {
			conference.submitManuscript(new Manuscript("Intro to Crytography", submissionUserID, manuscriptAuthors,
					ZonedDateTime.of(2017, 10, 30, 23, 45, 59, 1234, zoneId), new File("Path")));
		}
		assertTrue(conference.getNumSubmissions(coAuthorUserID) == 4);
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that getManuscripts for an author will throw an IllegalArgumentException
	 * if they have not submitted any manuscripts or that they have not co authored any manuscripts.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetManuscriptsForNoManuscriptsSubmittedThrowsIllegalArgumentExcecption() {
		conference.getManuscripts(coAuthorUserID);
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that getManuscripts will return the correct manuscripts for the user.
	 */
	@Test
	public void testGetManuscriptsForArrayListContainingTheManuscriptsTheUserSubmitted() {
		conference.submitManuscript(manuscript);
		assertTrue(conference.getManuscripts(submissionUserID).contains(manuscript));
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests thatGetSubManuscripts for a program chair who has not been assigned
	 * any manuscripts will throw an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetSubProgramChairAssignedManuscriptsThrowsIllegalArgumentExceptiontIfNonAssigned() {
		conference.getAllSubmittedManuscripts(subProgramChairUserID);
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that getAllSubmittedManuscripts will return an array list of manuscripts containing
	 * the manuscript that the sub program chair has been assigned to.
	 */
	@Test
	public void testSubProgramChairAssignedManuscriptsForPopulatedArrayListContainingOneAssignedManuscript() {
		conference.submitManuscript(manuscript);
		conference.assignManuscriptToSubprogramChair(manuscript, subProgramChairUserID);
		assertTrue(conference.getAllSubmittedManuscripts(subProgramChairUserID).size() == 1);
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that assignManuscriptToSubprogramChair will return false if the sub program chair being assigned the
	 * manuscript is the author of the manuscript.
	 */
	@Test
	public void testSubProgramChairIsTheAuthorOfTheManuscript() {
		conference.submitManuscript(manuscript);
		assertFalse(conference.assignManuscriptToSubprogramChair(manuscript, submissionUserID));
		
	}

	/**
	 * @author Lorenzo Pacis
	 * This method tests that assignManuscriptToSubprogramChair will return false if the sub program chair has
	 * already been assigned this manuscript.
	 */
	@Test
	public void testSubProgramChairHasAlreadyBeenAssignedThisManuscript() {
		conference.submitManuscript(manuscript);
		conference.assignManuscriptToSubprogramChair(manuscript, subProgramChairUserID);
		assertFalse(conference.assignManuscriptToSubprogramChair(manuscript, subProgramChairUserID));
	}
	
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that the subProgramChair is assigned a manuscript that they are eligible to be
	 * assigned to.
	 */
	@Test
	public void testSubProgramChairIsAssignedManuscriptAndTheyAreEiligableIsTrue() {
		conference.submitManuscript(manuscript);
		conference.assignManuscriptToSubprogramChair(manuscript, subProgramChairUserID);
		conference.submitManuscript(manuscriptTwo);
		assertTrue(conference.assignManuscriptToSubprogramChair(manuscriptTwo, subProgramChairUserID));

	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that assignManuscriptToSubprogramChair will assign a sub program chair a manuscript
	 * even if they are an author, but not of this manuscript.
	 */
	@Test
	public void testSubProgramChairIsAnAuthorButNotOfThisManuscriptReturnsTrue() {
		conference.submitManuscript(subProgramChairManuscript);
		conference.submitManuscript(manuscript);
		assertTrue(conference.assignManuscriptToSubprogramChair(subProgramChairManuscript, submissionUserID));
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that assignManuscriptToSubprogramChair will return false if the sub program chair
	 * is a co author of this manuscript.
	 */
	@Test
	public void testSubProgramChairIsTheCoAuthorOfTheManuscript() {
		conference.submitManuscript(subProgramChairIsCoAuthorManuscript);
		assertFalse(conference.assignManuscriptToSubprogramChair(subProgramChairIsCoAuthorManuscript, subProgramChairUserID));
		
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that getNumAssignedManuscripts will return the correct
	 * number of manuscripts assigned to that reviewer.
	 */
	@Test
	public void testGetNumAssignedManuscriptsReturnsOne() {
		conference.submitManuscript(manuscript);
		conference.assignManuscriptToReviewer(manuscript, reviewerUserID);
		assertTrue(conference.getNumAssignedManuscripts(reviewerUserID) == 1);
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method tests that getNumAssignedManuscripts will return zero if the reviewer
	 * has not been assigned any manuscripts.
	 */
	@Test
	public void testGetNumAssignedManuscriptsToReviewerReturnsZeroIfNoneHaveBeenAssigned() {
		conference.submitManuscript(manuscript);
		conference.addReviewer(reviewerUserID);
		assertTrue(conference.getNumAssignedManuscripts(reviewerUserID) == 0);
	}


}
