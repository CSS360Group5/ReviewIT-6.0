package model;
import java.io.File;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the manuscripts for the system and is responsible for
 * creating myManuscript objects and contains their recommendation, which
 * myReviews are assigned to the myManuscript, the reviews, and if the paper is
 * accepted.
 * 
 * @author Lorenzo Pacis
 * @version 4.29.2017
 */
public class Manuscript implements Serializable {

	/**
	 * Max reviewers allowed to be assigned.
	 */
	private static final int MAX_REVIEWERS = 3;

	/**
	 *  A serializable object  id
	 */
	private static final long serialVersionUID = -154138662878164818L;

	/**
	 * Title of the paper
	 */
	private String myTitle;

	/**
	 * The date and time of the submission.
	 */
	private ZonedDateTime mySubmissionDate;

	/**
	 * File holding the myManuscript;
	 */
	private File myManuscript;

	/**
	 * The list of myAuthors.
	 */
	private List<String> myAuthors;

	
	/**
	 * The user who submitted the myManuscript.
	 */
	private String mySubmitter;
	
	/**
	 * The list of myReviews assigned to the myManuscript.
	 */
	private ArrayList<Review> myReviews;
	

	/**
	 * Creates a new myManuscript object holding the myAuthors, the date and time of
	 * submission, and the myManuscript as well as myReviews, reviews, its
	 * acceptance, and the recommendation.
	 * 
	 * @param theSubmitter
	 *            The author of the paper. This will be added to the list of
	 *            myAuthors and stored as the main author to be referenced as the
	 *            person who submitted the myManuscript.
	 * @param theAuthors
	 * 			  The myAuthors of the paper, including coAuthors.
	 * @param theSubmissionDate
	 *            The date and time the myManuscript was submitted.
	 * @param theManuscript
	 *            The file containing the myManuscript.
	 */
	public Manuscript(String theTitle, String theSubmitter, List<String> theAuthors,
					  ZonedDateTime theSubmissionDate, File theManuscript ) {
		myReviews = new ArrayList<>();
		mySubmitter = theSubmitter;
		myAuthors = new ArrayList<>();
		if(theAuthors == null) {
			myAuthors.add(theSubmitter);
		} else {
			myAuthors.add(theSubmitter);
			for(String co : theAuthors) {
				myAuthors.add(co);
			}
		}

		mySubmissionDate = theSubmissionDate;
		myManuscript = theManuscript;
		myTitle = theTitle;
		
	}

	/**
	 * Gets the submission date and time.
	 * 
	 * @return the submission date and time.
	 */
	public ZonedDateTime getMySubmissionDate() {
		return mySubmissionDate;
	}

	/**
	 * Gets the myAuthors of the paper.
	 * 
	 * @return All of the myAuthors of the paper.
	 */
	public List<String> getAuthors() {
		//deep copy of the authors list
		List<String> authors = new ArrayList<>();
		for(String auth :myAuthors) {
			authors.add(auth);
		}
		return authors;
	}

	/**
	 * Gets the myManuscript file.
	 * @return The file containing the myManuscript.
	 */
	public File getManuscript() {
		return myManuscript;
	}
	
	/**
	 * Adds a reviewer to the myManuscript, there cannot
	 * be more than 3 myReviews assigned to one myManuscript.
	 */
	public void setReviewer(String theUserID) throws IllegalArgumentException {
		if(myReviews.size() < MAX_REVIEWERS) {
			
			if(myAuthors.contains(theUserID)) {
				throw new IllegalArgumentException("Reviewer cannot be author");
			} else {
				myReviews.add(new Review(theUserID));
			}
		} else {
			throw new IllegalArgumentException(String.format("Maximum of %d reviewers already assigned", MAX_REVIEWERS));
		}

	}
	
	/**
	 * Gets the list of reviewers assigned to the myManuscript.
	 * @return
	 */
	public ArrayList<String> getReviewers() {
		ArrayList<String> reviewers = new ArrayList<>();
		for(Review r :myReviews) {
			reviewers.add(r.getUserID());
		}
		return reviewers;
	}
	
	/**
	 * Tests to see if the list of myReviews on the myManuscript contains the
	 * user passed in.
	 * @param theUserID The userID of the person to be checked if they are in the
	 * list of myReviews.
	 * @return True if the user is in the list, or false if they are not.
	 */
	public boolean hasReviewer(String theUserID) {
		for(Review rev : myReviews){
			if (rev.getUserID().equals(theUserID)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the author name
	 * 
	 * @return the author
	 */
	public String getSubmissionUser() {
		return mySubmitter;

	}
	/**
	 *
	 * @return Returns the title of the myManuscript.
	 */
	public String getTitle() {
		return myTitle;
	}

}
