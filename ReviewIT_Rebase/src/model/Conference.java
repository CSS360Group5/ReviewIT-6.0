package model;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * Conference class that handles submissions of manuscripts,
 * assigning reviewers, subProgramChairs, and program chairs. 
 * @author Tommy Warren
 * @author Lorenzo Pacis
 * 
 * @date 5/1/2017
 * 
 * @version 1.5
 */

public class Conference implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8924081621903486980L;
	private final int DEFAULT_MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS = 5;
	private final int DEFAULT_MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED = 8;

	private int MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS;
	private int MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED;
	private ArrayList<String> myAuthors; // list of Authors in this conference.
	private ArrayList<String> myReviewers; // list of Reviewers in this conference.
	private ArrayList<String> mySubProgramChairs;
	private ArrayList<Manuscript> myConferenceManuscripts; // the list of manuscripts in this conference.
	private HashMap<String, ArrayList<Manuscript>> mySubmittedManuscripts; // the list of submitted manuscripts in this conference.
	private HashMap<String, ArrayList<Manuscript>> myAssignedReviewers; // All manuscripts assigned to specific Review.
	private HashMap<String, ArrayList<Manuscript>> mySubManuscripts; // All manuscripts assigned to specific Sub Program Chair.
	private ZonedDateTime mySubmissionDeadline; // a Local time for submission deadlines for Authors.
	private String myConferenceName;
	
	/**
	 * Creates a new Conference with the list of Authors, Reviewers, 
	 * and a submission deadline for submitting papers for this Conference.
	 * 
	 * @param theSubmissionDeadline - the deadline for paper submissions for Authors to submit papers.
	 * @param theConferenceName - the name of this conference.
	 */
	public Conference(String theConferenceName, ZonedDateTime theSubmissionDeadline) {
		
		setMaxNumberOfManuscriptSubmissions(DEFAULT_MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS);
		setMaxNumberOfManuscriptsAssigned(DEFAULT_MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED);
		this.myConferenceName = theConferenceName;
		this.mySubProgramChairs = new ArrayList<>();
		this.myAuthors = new ArrayList<>();
		this.myReviewers = new ArrayList<>();
		this.myConferenceManuscripts = new ArrayList<>();
		this.mySubmittedManuscripts = new HashMap<>();
		this.myAssignedReviewers = new HashMap<>();
		this.mySubManuscripts = new HashMap<>();
		
		this.mySubmissionDeadline = theSubmissionDeadline;
		
	}
	
	public void addSubProgramChair(String theUserId) {
		mySubProgramChairs.add(theUserId);
		mySubManuscripts.put(theUserId, new ArrayList<Manuscript>());
	}
	
	/**
	 * @author Lorenzo Pacis
	 * Submits a manuscript to a conference. (Business rules apply):
	 * 		* Authors cannot submit more than MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS per conference.
	 * 		* Manuscripts must be submitted before the submission deadline.
	 * This method will throw an IllegalArgumentException for the following cases if they are
	 * not fufilled by the caller.
	 * 		*The author cannot have already submitted five manuscripts.
	 * 		*Any of the co authors cannot have already submitted five manuscripts.
	 * 		*The manuscript cannot have already been submitted to the conference.
	 * 		*The manuscript object cannot be null.
	 * 
	 * @param theManuscript - the manuscript to be added to this conference.
	 * 		* Cannot be submitted more than once.
	 * 
	 */
	
	public boolean submitManuscript(Manuscript theManuscript) { 
		boolean returnValue = false;
		
		if(theManuscript != null) {
			
			if(isBeforeSubmission(theManuscript, getSubmissionDeadline())) {
				
				if(!myConferenceManuscripts.contains(theManuscript)){
					
					if(mySubmittedManuscripts.containsKey(theManuscript.getSubmissionUser())) { 
						
						if(isUnderManuscriptSubmissionLimit(theManuscript)) {
							mySubmittedManuscripts.get(theManuscript.getSubmissionUser()).add(theManuscript);
							myConferenceManuscripts.add(theManuscript);
							addCoAuthorSubmission(theManuscript);
							returnValue = true;
						}

						
					} else {

						if(isUnderManuscriptSubmissionLimit(theManuscript)) {
							mySubmittedManuscripts.put(theManuscript.getSubmissionUser(), new ArrayList<Manuscript>());
							mySubmittedManuscripts.get(theManuscript.getSubmissionUser()).add(theManuscript);
							myConferenceManuscripts.add(theManuscript);
							myAuthors.add(theManuscript.getSubmissionUser());
							addCoAuthorSubmission(theManuscript);
							returnValue = true;
						}

					}
				}
							
			} else {
				returnValue = false;
				throw new IllegalArgumentException("The deadline for submitting a manuscript was " + mySubmissionDeadline +"\n" +
				"It is now " + ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.now()), ZoneId.of("UTC-12")) + "\n"
						+ "Your current time is " + ZonedDateTime.now());
			}

		} else {
			returnValue = false;
			throw new IllegalArgumentException("Error manuscript object is null \n");
			

		}
		
		return returnValue;
	}
	
	private boolean isUnderManuscriptSubmissionLimit(Manuscript theManuscript) {
		boolean isUnder = true;
		for(String theUserId : theManuscript.getAuthors()) {
			if(mySubmittedManuscripts.containsKey(theUserId)) { 
				if(mySubmittedManuscripts.get(theUserId).size() >= getMaxNumberOfManuscriptSubmissions()) {
					isUnder = false;
					throw new IllegalArgumentException("Manuscript not submitted over five submitted manuscripts for " + theUserId + "\n");
				}
			}

		}
		return isUnder;
		
	}
	
	/**
	 * @author Lorenzo Pacis
	 * This method will add co authors of a manuscript to the submitted manuscripts hashmap.
	 * @param theManuscript to be added to the submissions hashmap.
	 */
	private void addCoAuthorSubmission(Manuscript theManuscript) {
		
		for(String theUserId : theManuscript.getAuthors()) {
			if(theUserId != theManuscript.getSubmissionUser()) {
				if(!mySubmittedManuscripts.containsKey(theUserId)) {
					mySubmittedManuscripts.put(theUserId, new ArrayList<>());
					mySubmittedManuscripts.get(theUserId).add(theManuscript);
					myAuthors.add(theUserId);
				} else {
					mySubmittedManuscripts.get(theUserId).add(theManuscript);
				}

			}
		}
	}

	
	/**
	 * This method returns either true or false depending upon the condition check. 
	 *
	 * @param theManuscript - the Manuscript to be verified if submittable.
	 * @param theSubmissionDeadline - the Conference's submission deadline for all paper submissions.
	 * @return true if manuscript submission deadline is before the conference deadline, otherwise false.
	 */
	public boolean isBeforeSubmission(Manuscript theManuscript, ZonedDateTime theSubmissionDeadline) {

		boolean returnValue = false;

		// if Submitted after the deadline, returns true.
		if (theSubmissionDeadline.compareTo(theManuscript.getMySubmissionDate()) > 0) {
			returnValue = true;


		} 
		return returnValue;
	}

	
	/**
	 * addReviewer - adds a Review to the list of Reviewers to this Conference.
	 */
	
	public void addReviewer(String theId) {
		if(!myReviewers.contains(theId))
			myReviewers.add(theId);
	}
	
	/**
	 * @return a list of authors' IDs in this conference.
	 */
	
	public ArrayList<String> getAuthors() {
		return myAuthors;
	}
	
	/**
	 * @return a list of reviewers' IDs in this conference.
	 */
	
	public ArrayList<String> getReviewers(){
		return myReviewers;
	}
	
	/**
	 * @return the submission deadline for this conference.
	 */
	
	public ZonedDateTime getSubmissionDeadline() {
		return mySubmissionDeadline;
	}
	

	
	/** User ID refers to the author which is stored in the authors list of this class
	 * @author Lorenzo Pacis
	 * @return the number of submissions from a specified Author.
	 */
	public int getNumSubmissions(String userId) {
		
		int numberOfSubmissions = 0;
		
		if(mySubmittedManuscripts.get(userId) != null) {
			numberOfSubmissions = mySubmittedManuscripts.get(userId).size();
		} 
		
		return numberOfSubmissions;
	}
	
	/**
	 * Will get the manuscripts that have been submitted by the user, if it is found that the user has
	 * not submitted a manuscript the method will throw an IllegalArgumentException which must be
	 * handled by the caller.
	 * @param theUserId is the specified Author to obtain the list of manuscripts.
	 * @return the manuscripts submitted by a specified Author.
	 */
	
	public ArrayList<Manuscript> getManuscripts(String theUserId) {

		if(mySubmittedManuscripts.containsKey(theUserId)) {
			return mySubmittedManuscripts.get(theUserId);
		} else {
			throw new IllegalArgumentException("No submitted manuscripts");
		}

	}
	
	
	/**
	 * @author Lorenzo Pacis
	 * @author Tommy Warren
	 * assignReviewer assigns a manuscript to a reviewer, so that a reviewer can review a manuscript.
	 * 		Business rules applied: 
	 * 			* A reviewer must have no more than 8 manuscripts assigned.
	 * 			* A reviewer cannot be an Author or Co-Author to the manuscript they are being assigned to.
	 * 
	 * @param theManuscript - the manuscript to be assigned to a reviewer.
	 * @param theId - the reviewer's ID to assign a manuscript to.
	 */
	public boolean assignReviewer(Manuscript theManuscript, String theId) {
		
		boolean reviewerSet = false;
		
		
		if(myAssignedReviewers.containsKey(theId)) {
			if(reviewerIsEligibleToReview(theManuscript, theId)) {
				myAssignedReviewers.get(theId).add(theManuscript);
				theManuscript.setReviewer(theId);
				reviewerSet = true;
			}
			
		} else {
			myAssignedReviewers.put(theId, new ArrayList<>());
			if(reviewerIsEligibleToReview(theManuscript, theId)) {
				myAssignedReviewers.get(theId).add(theManuscript);
				theManuscript.setReviewer(theId);
				reviewerSet = true;
			}
		}
		return reviewerSet;
	}
	
	
	/**
	 * Tests if the reviewer is eligible to review this manuscript, if they are not, the method
	 * will throw an IllegalArgumentException that must be fufilled by the caller for the following cases.
	 * 		*The reviewer cannot have more than eight manuscripts assigned to them.
	 * 		*The reviewer cannot be a co author of the manuscript.
	 * 		*The reviewer cannot be the author of the manuscript.
	 * @param theManuscript The manuscript to be reviewed.
	 * @param theReviewerId the user Id of the reviewer.
	 * @return true if the reviewer is eligible to review a manuscript, otherwise returns false.
	 */
	public boolean reviewerIsEligibleToReview(Manuscript theManuscript, String theReviewerId) {
		boolean eligible = true;
		// Business rule 1: A reviewer must have no more than MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED.
		if(myAssignedReviewers.containsKey(theReviewerId)) {
			
			if(myAssignedReviewers.get(theReviewerId).size() >=  getMaxNumberOfManuscriptsAssigned()){
				eligible = false;
			throw new IllegalArgumentException("Manuscript not assigned, Reviewer " + theReviewerId + " cannot be assigned more than eight manuscripts!");

			}
		}

		
		if(theManuscript.getAuthors().contains(theReviewerId)) {
				eligible = false;
				if(theReviewerId.equals(theManuscript.getSubmissionUser())) {
					throw new IllegalArgumentException("Manuscript not assigned, Reviewer: " + theReviewerId+ " is an author to this manuscript.");
				} else {
					throw new IllegalArgumentException("Manuscript not assigned, Reviewer: " + theReviewerId+ " is a co author to this manuscript.");
				}

		}
		if(theManuscript.hasReviewer(theReviewerId)) {
					eligible = false;
					throw new IllegalArgumentException("Manuscript not assigned, Reviewer: " + theReviewerId + " is already assigned to this manuscript.");
		}
		return eligible;
	}

	/**
	 * This method will return the manuscripts of for the userID for the subprogram chair.
	 * If the userID is not found as a key in the hashmap, the method will throw an IllegalArgumentException
	 * that must be handled by the caller.
	 * @author Myles Haynes
	 * @author Lorenzo Pacis
	 * @param theId - id to get the
	 * @return a list of Manuscripts assigned to a specified Subprogram Chair, otherwise returns a new list of manuscripts.
	 */
	public ArrayList<Manuscript> getSubManuscripts(String theId) {
		
		if(mySubManuscripts.containsKey(theId)) {
			return mySubManuscripts.get(theId);
			
		} else {
			throw new IllegalArgumentException("You have no manuscripts assigned to you");
		}

	}

	/**
	 * @author Lorenzo Pacis
	 * Assigns a Manuscript to a Subprogram Chair.
	 * Follows Business rule that a Subprogram Chair cannot be assigned their own paper.
	 * @param theManuscript - to assign to a subprogram chair
	 * @param userId - who to assign manuscript to.
	 * @return true if assignment succeeded, otherwise false.
	 */
	public boolean assignSubManuscripts(Manuscript theManuscript, String userId) {
		
		boolean assignedManuscript = false;
		if(isValidSubChair(theManuscript, userId)) {
			if(mySubManuscripts.containsKey(userId)) {
				
				if(!(mySubManuscripts.get(userId).contains(theManuscript))) {
					mySubManuscripts.get(userId).add(theManuscript);
					assignedManuscript = true;
				}

			} else {
				
				mySubManuscripts.put(userId, new ArrayList<Manuscript>());
				mySubManuscripts.get(userId).add(theManuscript);
				assignedManuscript = true;

			}
		}
			
		return assignedManuscript;
	}

	
	/**
	 * @author Lorenzo Pacis
	 * @param theManuscript - the manuscript to be assigned to the Subprogram Chair.
	 * @param theId - The Subprogram Chair's user Id.
	 * @return true If the subprogram chair was assigned the manuscript, otherwise returns false.
	 */
	private boolean isValidSubChair(Manuscript theManuscript, String theId) {
		boolean validSubChair = true;
		
		if(theManuscript.getSubmissionUser().equals(theId)) {

			validSubChair = false;
		}
		
		if(mySubmittedManuscripts.containsKey(theId)) {
			
			if(mySubmittedManuscripts.get(theId).contains(theManuscript)) {

				validSubChair = false;
			}
		}
		return validSubChair;
	}
	
	/**
	 * @return This Conference's title.
	 */

	public String getMyConferenceName() {
		return myConferenceName;
	}
	
	/**
	 * @author Lorenzo Pacis
	 * @param theReviewerId The reviewer ID to be checked how many manuscripts they are assigned.
	 * @return The number of manuscripts assigned to this reviewer.
	 */
	public int getNumAssignedManuscripts(String theReviewerId) {
		int numAssigned = 0;
		
		if(myAssignedReviewers.containsKey(theReviewerId)) {
			
			numAssigned = myAssignedReviewers.get(theReviewerId).size();
		}
		
		return numAssigned;

	}

	/**
	 * @param theUserId the user to obtain their Role.
	 * @return a Role assigned to a user.
	 */
	public Role getRole(String theUserId) {
		if(mySubManuscripts.containsKey(theUserId)) return Role.SUBPROGRAM;
		if(mySubProgramChairs.size() > 0) {
			if(mySubProgramChairs.contains(theUserId)) return Role.SUBPROGRAM;
		}
		else if(myAssignedReviewers.containsKey(theUserId)) return Role.REVIEWER;
		return Role.AUTHOR;
	}
	

	/**
	 * getter of MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS
	 * @return MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS
	 */
	public int getMaxNumberOfManuscriptSubmissions() {
		return MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS;
	}
	
	
	/**
	 * setter of MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS
	 * @param MaxNumberOfManuscriptSubmissions The Max Number Of Manuscript Submissions
	 */
	public void setMaxNumberOfManuscriptSubmissions(int MaxNumberOfManuscriptSubmissions) {
		this.MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS = MaxNumberOfManuscriptSubmissions;
	}
	
	/**
	 * getter of MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED
	 * @return MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED
	 */
	public int getMaxNumberOfManuscriptsAssigned() {
		return MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED;
	}
	
	/**
	 * setter of MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED
	 * @param MaxNumberOfManuscriptsAssigned The Max Number Of Manuscripts Assigned
	 */
	public void setMaxNumberOfManuscriptsAssigned(int MaxNumberOfManuscriptsAssigned) {
		this.MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED = MaxNumberOfManuscriptsAssigned;
	}
	

}