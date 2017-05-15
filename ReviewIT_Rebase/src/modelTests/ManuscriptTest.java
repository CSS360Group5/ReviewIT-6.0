package modelTests;

import static org.junit.Assert.*;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Manuscript;
import model.Review;


/**
 * This class tests the constructor for the manuscript class
 * and tests the set reviewers and get reviewers methods. Implicitly tests
 * all other methods.
 * @author Lorenzo Pacis
 * @version 4/30/2017
 */
public class ManuscriptTest {
	
	private static String manuscriptTitle = "Technology";
	
	private static String submissionUserID = "John117";
	
	private static String reviewerUserID = "Kelly087";
	
	
	private static String coAuthorUserID = "Samuel034";
	
	private static ArrayList<String> coAuthors = new ArrayList<String>();
	private static ZonedDateTime submissionDate = ZonedDateTime.now();
	private static File manuscript = new File("SomePath");
	private Manuscript testManuscript;
	
	private Manuscript testNullCoAuthorManuscript;
	
	@Before
	public void setUp() {
		coAuthors.add(coAuthorUserID);
		testManuscript = new Manuscript(manuscriptTitle, submissionUserID, coAuthors, submissionDate, manuscript);
		testNullCoAuthorManuscript = new Manuscript(manuscriptTitle, submissionUserID, null, submissionDate, manuscript);
		
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
     * This method tests that the reviewer is set
     */
    @Test
    public void ReveiwersAreSetToCorrectValues() {

    	testManuscript.setReviewer("Bob");
    	assertTrue(testManuscript.hasReviewer("Bob"));
    	assertFalse(testManuscript.hasReviewer("John"));
    	

    }
    
    /**
     * @author Lorenzo Pacis
     * This method tests that the manuscript is not assigned more than three reviewers.
     */
    @Test
    public void manuscriptSetReviewerFailsWhenMoreThanThreeReviewersAssigned() {
    	testManuscript.setReviewer("John");
    	testManuscript.setReviewer("Billy");
    	testManuscript.setReviewer("Jimmy");
    	assertFalse(testManuscript.getReviewers().size() > 3);
    	
    }
    
    
    /**
     * @author Lorenzo Pacis
     * This method tests that setReviewer the author as the reviewer returns false.
     */
    @Test (expected = IllegalArgumentException.class)
    public void manuscriptSetReviewerFailsWhenReviewerIsTheSubmitter() {
    	testManuscript.setReviewer(submissionUserID);
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
     * This method tests that the getReviewers method returns the correct
     * reviewers set to the manuscript.
     */
    @Test
    public void manuscriptGetReviewersReturnsCorrectUserIDs() {
    	
    	testManuscript.setReviewer("Bob");
    	testManuscript.setReviewer("John");
    	testManuscript.setReviewer("Billy");
    	ArrayList<String> reviewers = testManuscript.getReviewers();
    	assertTrue(reviewers.get(0).equals("Bob"));
    	assertTrue(reviewers.get(1).equals("John"));
    	assertTrue(reviewers.get(2).equals("Billy"));
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
    	testManuscript.setReviewer(reviewerUserID);
    	assertTrue(testManuscript.getReviewers().contains(reviewerUserID));
    }
    
    /**
     * @author Lorenzo Pacis
     * This method tests that setReviewer will return false if there are three reviewers already assigned
     * to the manuscript.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSetReviewersReturnsFalseIfThereAreThreeReviewersAlreadyAssignedToThisManuscript() {
    	testManuscript.setReviewer(reviewerUserID);
    	testManuscript.setReviewer(reviewerUserID);
    	testManuscript.setReviewer(reviewerUserID);
    	//Error thrown here
		testManuscript.setReviewer(reviewerUserID);
    }
    

}
