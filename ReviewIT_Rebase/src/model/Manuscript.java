package model;
import java.io.File;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * This class handles the manuscripts for the system and is responsible for
 * creating myManuscript objects and contains their recommendation, which
 * myReviews are assigned to the myManuscript, the reviews, and if the paper is
 * accepted.
 * 
 * @author Harlan Stewart
 * @author Dimitar Kumanov
 * @version 1.0
 */
public class Manuscript implements Serializable {

	public static int MAX_REVIEWERS = 3;
	
	/*A serializable object  id.*/
	private static final long serialVersionUID = -154138662878164818L;

	/** Title of the paper*/
	private String myTitle;

	/* The date and time of the submission.*/
	private ZonedDateTime mySubmissionDate;

	/*File holding the myManuscript*/
	private File myManuscript;

	/*The list of myAuthors.*/
	private Collection<String> myAuthors;
	
	/*The user who submitted the myManuscript.*/
	private UserProfile mySubmitter;
	
	/* The list of myReviews assigned to the myManuscript.*/
	private Collection<UserProfile> myReviewers;
	
//	/* The list of myReviews assigned to the myManuscript.*/
//	private Collection<Review> myReviews;
	
	/* The list of of persons who are assigned as reviewers to this manuscript. */
	
	

	/**
	 * Creates a new myManuscript object holding the myAuthors, the date and time of
	 * submission, and the myManuscript as well as myReviews, reviews, its
	 * acceptance, and the recommendation.
	 * 
	 * @param theSubmitter
	 *            The submiter of the paper. This will be added to the list of
	 *            myAuthors and stored as the main author to be referenced as the
	 *            person who submitted the myManuscript.
	 * @param theAuthors
	 * 			  The myAuthors of the paper, including coAuthors.
	 * @param theSubmissionDate
	 *            The date and time the myManuscript was submitted.
	 * @param theManuscript
	 *            The file containing the myManuscript.
	 * @author Harlan Stewart
	 * @author Dimitar Kumanov
	 * 
	 */
	public Manuscript(String theTitle, UserProfile theSubmitter, List<String> theAuthors,
					  ZonedDateTime theSubmissionDate, File theManuscript ) {
		myReviewers = new HashSet<>();
//		myReviews = new ArrayList<>();
		mySubmitter = theSubmitter;
		myAuthors = new HashSet<>();
		
		myAuthors.add(theSubmitter.getName());
		for(String co : theAuthors) {
			myAuthors.add(co);
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
	public Collection<String> getAuthors() {
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
	 * Adds a Reviewer to this manuscript.
	 * @throws IllegalArgumentException if this.getReviewers().contains(theReviewerProfile) || this.getReviewers().size() > this.MAX_REVIEWERS || this.getAuthors().contains(theReviewerProfile.getName())
	 */
	public void addReviewer(final UserProfile theReviewerProfile) throws IllegalArgumentException {
		if(myReviewers.contains(theReviewerProfile)){
			throw new IllegalArgumentException("Reviewer already assigned to this manuscript.");
		}
		if(myReviewers.size() > MAX_REVIEWERS) {
			throw new IllegalArgumentException("Maximum of reviewers already assigned.");
		}
		if(myAuthors.contains(theReviewerProfile)) 
			throw new IllegalArgumentException("Reviewer cannot be author.");
		
		myReviewers.add(theReviewerProfile);
	}
	
	public Collection<UserProfile> getReviewers() {
		Collection<UserProfile> reviewers = new HashSet<>();
		for(UserProfile currentReviewer :myReviewers) {
			reviewers.add(currentReviewer);
		}
		return reviewers;
	}
	
	
	public boolean hasReviewer(UserProfile theReviewerProfile) {
		return myReviewers.contains(theReviewerProfile);
	}

	/**
	 * Gets the author name
	 * 
	 * @return the author
	 */
	public UserProfile getSubmissionUser() {
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
