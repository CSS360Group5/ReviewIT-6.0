package model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public static final int MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS = 5;
	public static final int MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED = 8;
	public static final int MIN_NUM_REVIEWS = 5;

	/**
	 * List of all Manuscripts submitted to this Conference.
	 */
	private Collection<Manuscript> myConferenceManuscripts;

	/**
	 * Map of an Author's name to a List of Manuscripts that the author has submitted to this Conference.
	 */
	private Map<String, Collection<Manuscript>> myAuthorManuscriptMap;

	/**
	 * Map of a Subprogram Chair's user ID to a List of Manuscripts for which they have been designated responsible
	 * for this Conference.
	 */
	private Map<UserProfile, Collection<Manuscript>> myActiveSubprogramChairAssignmentMap;

//	/**
//	 * Map of a Reviewer's user ID to a List of Manuscripts to which they have been assigned to create Reviews for.
//	 */
//	private Map<UserProfile, Collection<Manuscript>> myActiveReviewerAssignmentMap;

	/**
	 * This Conference's designated deadline for Manuscript submissions.
	 */
	private ZonedDateTime myManuscriptSubmissionDeadline;

	private String myConferenceName;
	
	/**
	 * Creates a new Conference with the list of Authors, Reviewers, 
	 * and a submission deadline for submitting papers for this Conference.
	 * 
	 * @param theSubmissionDeadline - the deadline for paper submissions for Authors to submit papers.
	 * @param theConferenceName - the name of this conference.
	 */
	public Conference(String theConferenceName, ZonedDateTime theSubmissionDeadline) {
		
		this.myConferenceName = theConferenceName;
		this.myConferenceManuscripts = new ArrayList<>();
		this.myAuthorManuscriptMap = new HashMap<>();
		this.myActiveSubprogramChairAssignmentMap = new HashMap<>();
//		this.myActiveReviewerAssignmentMap = new HashMap<>();
		
		this.myManuscriptSubmissionDeadline = theSubmissionDeadline;
		
	}

	
	/**
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
	 * @author Lorenzo Pacis
	 * @author Dimitar Kumanov
	 */
	public boolean submitManuscript(final Manuscript theManuscript) {
		if (theManuscript != null && isManuscriptSubmittable(theManuscript)) {
			myConferenceManuscripts.add(theManuscript);
			return true;
		}
		return false;
	}

//	/
	/**
	 * Method that determines whether a Manuscript may be submitted to this Conference.
	 * @param theManuscript The Manuscript to be submitted
	 * @param theUserID The submitting Author
	 * @return true if the Manuscript is submittable.
	 * @author Dimitar Kumanov
	 */
	private boolean isManuscriptSubmittable(final Manuscript theManuscript) {
		if(!submittedPriorToDeadline(theManuscript)){
			return false;
		}
		for(final String currentAuthorName: theManuscript.getAuthors()){
			if(!isAuthorUnderManuscriptSubmissionLimit(currentAuthorName)){
				return false;
			}
		}
		return true;
	}

	/**
	 * @return true iff getManuscriptsByName(theAuthorName).size() < this.MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS
	 */
	public boolean isAuthorUnderManuscriptSubmissionLimit(final String theAuthorName) {
		return getManuscriptsByName(theAuthorName).size() < MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS;
	}
	
	/**
	 * Method that checks whether a Manuscript has been submitted before this
	 * Conference's Manuscript Submission Deadline.
	 * @param theManuscript The Manuscript to be verified if submittable
	 * @return true if manuscript submission deadline is before the conference deadline, otherwise false.
	 */
	public boolean submittedPriorToDeadline(Manuscript theManuscript) {
		boolean returnValue = false;
		if (myManuscriptSubmissionDeadline.compareTo(theManuscript.getMySubmissionDate()) > 0) {
			returnValue = true;
		} 
		return returnValue;
	}

	/**
	 * @return the submission deadline for this conference.
	 */
	public ZonedDateTime getSubmissionDeadline() {
		return myManuscriptSubmissionDeadline;
	}
	
	
	/**
	 * Returns a non-null Collection of Manuscript associate with theAuthorName for this Conference.
	 */
	public Collection<Manuscript> getManuscriptsByName(final String theAuthorName) {
		if(myAuthorManuscriptMap.containsKey(theAuthorName))
			return myAuthorManuscriptMap.get(theAuthorName);
		return new ArrayList<>();
	}
	
	
	
//	/**
//	 * PRECONDITION: theUserId of the intended Reviewer must exist in the List
//	 * generated by UserProfileStateManager.getEligibleReviewers(String c, Manuscript m),
//	 * where c is this Conference's name and m is the Manuscript to be reviewed.
//	 *
//	 * Adds theManuscript to the List of Manuscripts associated with the Reviewer's
//	 * user ID. If the Reviewer has not yet been assigned a Manuscript, their ID is
//	 * put() in myActiveReviewerAssignmentMap and theManuscript is added to the List value.
//	 * 
//	 * @param theManuscript the manuscript to be assigned to a reviewer
//	 * @param theUserProfile the intended Reviewer's UserProfile Object
//	 */
//	public void assignManuscriptToReviewer(final Manuscript theManuscript,
//										   final UserProfile theUserProfile) {
//		/*
//		Ensure that the intended Reviewer holds a Reviewer Role for this Conference.
//		 */
//		if (theUserProfile.getRoles(myConferenceName).contains(Role.REVIEWER)) {
//			final String theUserID = theUserProfile.getUserID();
//
//			/*
//			If the intended Reviewer is not already assigned to the Manuscript, then set
//			them as a Reviewer of that Manuscript.
//			 */
//			if (!theManuscript.getReviewers().contains(theUserProfile.getUserID())) {
//				theManuscript.addReviewer(theUserProfile);
//			}
//
//			/*
//			If this Reviewer has not already been assigned a Manuscript to review for this
//			Conference, add them and the Manuscript to myActiveReviewerAssignmentMap.
//
//			Else, if this Reviewer HAS been assigned a Manuscript for this Conference, and
//			the Manuscript is not already associated with them under myActiveReviewerAssignmentMap,
//			add theManuscript to their assignment List.
//			 */
//			if (!myActiveReviewerAssignmentMap.containsKey(theUserID)) {
//				myActiveReviewerAssignmentMap.put(theUserID, new ArrayList<>());
//				myActiveReviewerAssignmentMap.get(theUserID).add(theManuscript);
//			} else if (!myActiveReviewerAssignmentMap.get(theUserID).contains(theManuscript)) {
//				myActiveReviewerAssignmentMap.get(theUserID).add(theManuscript);
//			}
//		}
//	}

	/**
	 * PRECONDITION: The UserProfile of the intended Subprogram Chair must provide evidence
	 * that the Subprogram Chair Role has been assigned for this Conference.
	 *
	 * @author Lorenzo Pacis
	 * @param theManuscript - to assign to a subprogram chair
	 * @param theUserProfile - who to assign manuscript to.
	 * @return true if assignment succeeded, otherwise false.
	 */
	public boolean assignManuscriptToSubprogramChair(final Manuscript theManuscript,
													 final UserProfile theUserProfile) {
		final String userId = theUserProfile.getUserID();
		
		boolean assignedManuscript = false;
		if(isValidSubChair(theManuscript, userId)) {
			if(myActiveSubprogramChairAssignmentMap.containsKey(userId)) {
				if(!(myActiveSubprogramChairAssignmentMap.get(userId).contains(theManuscript))) {
					myActiveSubprogramChairAssignmentMap.get(userId).add(theManuscript);
					assignedManuscript = true;
				}
			} else {
				myActiveSubprogramChairAssignmentMap.put(theUserProfile, new ArrayList<>());
				myActiveSubprogramChairAssignmentMap.get(userId).add(theManuscript);
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
		if(myAuthorManuscriptMap.containsKey(theId)) {
			if(myAuthorManuscriptMap.get(theId).contains(theManuscript)) {
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
	 *
	 * @param theReviewerId The reviewer ID to be checked how many manuscripts they are assigned.
	 * @return the number of Manuscripts assigned to this reviewer.
	 * @author Lorenzo Pacis
	 */
	public int getNumAssignedManuscripts(final UserProfile theReviewerProfile) {
		int numAssigned = 0;
		for(final Manuscript currentManuscript: myConferenceManuscripts){
			if(currentManuscript.getReviewers().contains(theReviewerProfile)){
				++numAssigned;
			}
		}
		return numAssigned;
	}
}