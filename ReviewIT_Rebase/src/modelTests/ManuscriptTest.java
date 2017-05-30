package modelTests;

import static org.junit.Assert.*;

import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Conference;
import model.Manuscript;
import model.Review;
import model.UserProfile;


/**
 * This class tests the constructor for the manuscript class
 * and tests the set reviewers and get reviewers methods. Implicitly tests
 * all other methods.
 * @author Lorenzo Pacis
 * @version 4/30/2017
 */
public class ManuscriptTest {
	
	// Fields for Conference class
	private static String conferenceName = "Computer Science";
	private static ZoneId zoneId = ZoneId.of("UTC+1");
	/** Year, Month, Day, Hour, Minute, Seconds, nano, zoneID */
	private static ZonedDateTime conferenceSubmissionDeadline = ZonedDateTime.of(2017, 11, 30, 23, 45, 59, 1234,
				zoneId);
	private Conference conference;
	
	
	private static String manuscriptTitle = "Technology";
	
	private static String submissionUserID = "John117";

	private static UserProfile submissionUser = new UserProfile(submissionUserID, "submission John Doe");
	
	private static String reviewerUserID = "Kelly087";

	private static UserProfile reviewerUser = new UserProfile(reviewerUserID, "reviewer John Doe");
	
	private static String coAuthorUserID = "Samuel034";
	
	private static ArrayList<String> coAuthors = new ArrayList<String>();
	private static ZonedDateTime submissionDate = ZonedDateTime.now();
	private static File manuscript = new File("SomePath");
	private Manuscript testManuscript;
	
	private Manuscript testNullCoAuthorManuscript;
	
	@Before
	public void setUp() {
		coAuthors.add(coAuthorUserID);
		testManuscript = new Manuscript(manuscriptTitle, submissionUser, coAuthors, submissionDate, manuscript);
		testNullCoAuthorManuscript = new Manuscript(manuscriptTitle, submissionUser, new ArrayList<>(), submissionDate, manuscript);
		
		conference = new Conference(conferenceName, conferenceSubmissionDeadline);
		
	}
	/**
	 * @authorLorenzo Pacis
	 * This method tests that the constructor sets all of the fields properly.
	 */
    @Test	
	public void testConstructor() { 

		
		assertTrue("Testing Correct User Submitted the manuscript", testManuscript.getSubmissionUser().equals(submissionUserID));
		assertTrue("Testing submission date and time", testManuscript.getMySubmissionDate().getChronology().equals(submissionDate.getChronology()));
		assertTrue("Testing manuscript file is the one submitted", testManuscript.getManuscript().equals(manuscript));
		assertTrue("Testing Co-submissionUserID Addition", testManuscript.getAuthors().equals(coAuthors));
		assertTrue("Testing get title", testManuscript.getTitle().equals(manuscriptTitle));
	}
     

    /**
     * @author Lorenzo Pacis
     * @author Dimitar Kumanov
     * This method tests that the reviewer is set
     */
    @Test
    public void ReveiwersAreSetToCorrectValues() {
    	UserProfile aReviewer = new UserProfile("Bob", "Bob Reviewer");
    	UserProfile aNonReviewer = new UserProfile("John", "John Not Reviewer");
    	testManuscript.addReviewer(aReviewer,conference);
    	assertTrue(testManuscript.hasReviewer(aReviewer));
    	assertFalse(testManuscript.hasReviewer(aNonReviewer));
    	

    }
    
    /**
     * @author Lorenzo Pacis
     * @author Dimitar Kumanov
     * This method tests that the manuscript is not assigned more than Manuscript.MAX_REVIEWERS reviewers.
     */
    @Test(expected = IllegalArgumentException.class)
    public void manuscriptAddReviewerMAX_REVIEWERSThrowsException() {
    	for(int i = 0; i < Manuscript.MAX_REVIEWERS; ++i){
    		testManuscript.addReviewer(new UserProfile("Bob" + String.valueOf(i), "Bob Reviewer"+ String.valueOf(i)),conference);
    	}
    }
    
    /**
     * @author Dimitar Kumanov
     * This method tests that the manuscript can be assigned Manuscript.MAX_REVIEWERS - 1 reviewers.
     */
    @Test(expected = IllegalArgumentException.class)
    public void manuscriptAddReviewerLessThenMAX_REVIEWERSThrowsException() {
    	for(int i = 0; i < Manuscript.MAX_REVIEWERS - 1; ++i){
    		testManuscript.addReviewer(new UserProfile("Bob" + String.valueOf(i), "Bob Reviewer"+ String.valueOf(i)),conference);
    	}
    	assertEquals(testManuscript.getReviewers().size(), Manuscript.MAX_REVIEWERS - 1);
    }
    
    /**
     * @author Dimitar Kumanov
     * This method tests that the manuscript can be assigned Manuscript.MAX_REVIEWERS - 1 reviewers.
     */
    @Test(expected = IllegalArgumentException.class)
    public void manuscriptAddReviewerMoreThenMAX_REVIEWERSThrowsException() {
    	for(int i = 0; i < Manuscript.MAX_REVIEWERS + 2; ++i){
    		testManuscript.addReviewer(new UserProfile("Bob" + String.valueOf(i), "Bob Reviewer"+ String.valueOf(i)),conference);
    	}
    }
    
    /**
     * @author Lorenzo Pacis
     * This method tests that setReviewer the author as the reviewer returns false.
     */
    @Test (expected = IllegalArgumentException.class)
    public void manuscriptAddReviewerFailsWhenReviewerIsTheSubmitter() {
    	testManuscript.addReviewer(submissionUser,conference);
    }
    
    /**
     * @author Lorenzo Pacis
     * This method tests that get authors will return an array list containing the authors
     * and co authors.
     */
    @Test
    public void getAuthorsReturnsArrayListContainingAllAuthors() {
    	assertTrue(testManuscript.getAuthors().contains(coAuthorUserID));
    	assertTrue(testManuscript.getAuthors().contains(submissionUserID));
    
    }
    
    /**
     * @author Lorenzo Pacis
     * This method tests that get authors will return an empty array list if the the co
     * authors list passed into the constructor is null.
     */
    @Test
    public void getAuthorsReturnsAnArrayListWithOnlyTheAuthorIfCoAuthorsListIsNull() {
    	assertTrue(testNullCoAuthorManuscript.getAuthors().contains(submissionUserID));
    	
    }
    
    /**
     * @author Lorenzo Pacis
     * @author Dimitar Kumanov
     * This method tests that the getReviewers method returns the correct
     * reviewers set to the manuscript.
     */
    @Test
    public void manuscriptGetReviewersReturnsCorrectUserIDs() {
    	UserProfile aReviewer1 = new UserProfile("Bob", "Bob Reviewer");
    	UserProfile aReviewer2 = new UserProfile("John", "John Reviewer");
    	UserProfile aReviewer3 = new UserProfile("Billy", "Billy Reviewer");
    	
    	testManuscript.addReviewer(aReviewer1,conference);
    	testManuscript.addReviewer(aReviewer2,conference);
    	testManuscript.addReviewer(aReviewer3,conference);
    	assertTrue(testManuscript.getReviewers().contains(aReviewer1));
    	assertTrue(testManuscript.getReviewers().contains(aReviewer2));
    	assertTrue(testManuscript.getReviewers().contains(aReviewer3));
    }
    
    /**
     * @author Lorenzo Pacis
     * This method tests the getTitle will return the title of the manuscript.
     */
    @Test
    public void testGetTitleReturnsTheTitleOfTheManuscript() {
    	assertTrue(testManuscript.getTitle().equals(manuscriptTitle));
    }
    
    /**
     * @author Lorenzo Pacis
     * This method tests that getReviewers will return an array list containing
     * the reviewer set to this manuscript.
     */
    @Test
    public void testGetReviewerReturnsAnArrayListContainingTheReviewer() {
    	testManuscript.addReviewer(reviewerUser,conference);
    	assertTrue(testManuscript.getReviewers().contains(reviewerUserID));
    }
    
    /**
     * @author Dimitar Kumanov
     * Manuscript should throw exception if you attempt to add the same Reviewer twice.
     */
    @Test (expected = IllegalArgumentException.class)
    public void addReviewerReviewerAlreadyAddedThrowsException() {
    	testManuscript.addReviewer(reviewerUser,conference);
    	testManuscript.addReviewer(reviewerUser,conference);
    }
    

}
