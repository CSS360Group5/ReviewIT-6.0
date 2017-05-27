package model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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

	private static final int MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS = 5;
	private static final int MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED = 8;
	private static final int MIN_NUM_REVIEWS = 5;

	/**
	 * List of all Manuscripts submitted to this Conference.
	 */
	private List<Manuscript> myConferenceManuscripts;

	/**
	 * Map of an Author's user ID to a List of Manuscripts that the author has submitted to this Conference.
	 */
	private Map<String, List<Manuscript>> myActiveAuthorSubmitterMap;

	/**
	 * Map of a Subprogram Chair's user ID to a List of Manuscripts for which they have been designated responsible
	 * for this Conference.
	 */
	private Map<String, List<Manuscript>> myActiveSubprogramChairAssignmentMap;

	/**
	 * Map of a Reviewer's user ID to a List of Manuscripts to which they have been assigned to create Reviews for.
	 */
	private Map<String, List<Manuscript>> myActiveReviewerAssignmentMap;

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
		this.myActiveAuthorSubmitterMap = new HashMap<>();
		this.myActiveSubprogramChairAssignmentMap = new HashMap<>();
		this.myActiveReviewerAssignmentMap = new HashMap<>();
		
		this.myManuscriptSubmissionDeadline = theSubmissionDeadline;
		
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
	public boolean submitManuscript(final Manuscript theManuscript,
									final String theUserID) {
		boolean returnValue = false;

		if (theManuscript != null && isSubmittable(theManuscript, theUserID)) {
			if (!handleDuplicateManuscript(theManuscript)) {
				myConferenceManuscripts.add(theManuscript);
			}
			returnValue = true;
		}

//
//		if(theManuscript != null) {
//
//			if(isBeforeSubmission(theManuscript, getSubmissionDeadline())) {
//
//				if(!myConferenceManuscripts.contains(theManuscript)){
//
//					if(mySubmittedManuscripts.containsKey(theManuscript.getSubmissionUser())) {
//
//						if(isUnderManuscriptSubmissionLimit(theManuscript)) {
//							mySubmittedManuscripts.get(theManuscript.getSubmissionUser()).add(theManuscript);
//							myConferenceManuscripts.add(theManuscript);
//							addCoAuthorSubmission(theManuscript);
//							returnValue = true;
//						}
//
//
//					} else {
//
//						if(isUnderManuscriptSubmissionLimit(theManuscript)) {
//							mySubmittedManuscripts.put(theManuscript.getSubmissionUser(), new ArrayList<Manuscript>());
//							mySubmittedManuscripts.get(theManuscript.getSubmissionUser()).add(theManuscript);
//							myConferenceManuscripts.add(theManuscript);
//							myAuthors.add(theManuscript.getSubmissionUser());
//							addCoAuthorSubmission(theManuscript);
//							returnValue = true;
//						}
//
//					}
//				}
//
//			} else {
//				returnValue = false;
//				throw new IllegalArgumentException("The deadline for submitting a manuscript was " + myManuscriptSubmissionDeadline +"\n" +
//				"It is now " + ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.now()), ZoneId.of("UTC-12")) + "\n"
//						+ "Your current time is " + ZonedDateTime.now());
//			}
//
//		} else {
//			returnValue = false;
//			throw new IllegalArgumentException("Error manuscript object is null \n");
//
//
//		}
//


		return returnValue;
	}

	/**
	 * Method that handles cases of duplicate Manuscripts being submitted.
	 *
	 * Currently, if a prior existing Manuscript shares a title with the
	 * Manuscript to be submitted, the older Manuscript is replaced by the
	 * newer.
	 * @param theManuscript The Manuscript to be submitted
	 * @return true if a duplicate title was found.
	 */
	private boolean handleDuplicateManuscript(final Manuscript theManuscript) {
		boolean duplicateFound = false;
		for (Manuscript m : myConferenceManuscripts) {
			if (m.getTitle().equals(theManuscript.getTitle())) {
				duplicateFound = true;
				myConferenceManuscripts.remove(m);
				myConferenceManuscripts.add(theManuscript);
			}
		}
		return duplicateFound;
	}

	/**
	 * Method that determines whether a Manuscript may be submitted to this Conference.
	 * @param theManuscript The Manuscript to be submitted
	 * @param theUserID The submitting Author
	 * @return true if the Manuscript is submittable.
	 */
	private boolean isSubmittable(final Manuscript theManuscript,
								  final String theUserID) {
		boolean isSubmittable = isUnderManuscriptSubmissionLimit(theUserID)
				&& submittedPriorToDeadline(theManuscript);
		return isSubmittable;
	}

	/**
	 * Method that checks an authors total distinct manuscript submissions
	 * to determine whether they may submit another manuscript.
	 * @param theUserID The submitting Author
	 * @return true if the submitting Author has not met the submission limit.
	 */
	private boolean isUnderManuscriptSubmissionLimit(final String theUserID) {
		boolean isUnder = true;
		if (getNumSubmissions(theUserID) >= MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS ) {
			isUnder = false;
		}
		return isUnder;
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
	 * User ID refers to the author which is stored in the authors list of this class
	 *
	 * @return the number of submissions from a specified Author.
	 * @author Lorenzo Pacis
	 */
	public int getNumSubmissions(final String theUserID) {
		int numberOfSubmissions = 0;
		if(myActiveAuthorSubmitterMap.containsKey(theUserID)) {
			numberOfSubmissions = myActiveAuthorSubmitterMap.get(theUserID).size();
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
	public List<Manuscript> getManuscripts(final String theUserId) {
		if(myActiveAuthorSubmitterMap.containsKey(theUserId)) {
			return myActiveAuthorSubmitterMap.get(theUserId);
		} else {
			throw new IllegalArgumentException("No submitted manuscripts");
		}
	}
	
	
	/**
	 * PRECONDITION: theUserId of the intended Reviewer must exist in the List
	 * generated by UserProfileStateManager.getEligibleReviewers(String c, Manuscript m),
	 * where c is this Conference's name and m is the Manuscript to be reviewed.
	 *
	 * Adds theManuscript to the List of Manuscripts associated with the Reviewer's
	 * user ID. If the Reviewer has not yet been assigned a Manuscript, their ID is
	 * put() in myActiveReviewerAssignmentMap and theManuscript is added to the List value.
	 * 
	 * @param theManuscript the manuscript to be assigned to a reviewer
	 * @param theUserProfile the intended Reviewer's UserProfile Object
	 */
	public void assignManuscriptToReviewer(final Manuscript theManuscript,
										   final UserProfile theUserProfile) {
		/*
		Ensure that the intended Reviewer holds a Reviewer Role for this Conference.
		 */
		if (theUserProfile.getRoles(myConferenceName).contains(Role.REVIEWER)) {
			final String theUserID = theUserProfile.getUserID();

			/*
			If the intended Reviewer is not already assigned to the Manuscript, then set
			them as a Reviewer of that Manuscript.
			 */
			if (!theManuscript.getReviewers().contains(theUserProfile.getUserID())) {
				theManuscript.setReviewer(theUserProfile.getUserID());
			}

			/*
			If this Reviewer has not already been assigned a Manuscript to review for this
			Conference, add them and the Manuscript to myActiveReviewerAssignmentMap.

			Else, if this Reviewer HAS been assigned a Manuscript for this Conference, and
			the Manuscript is not already associated with them under myActiveReviewerAssignmentMap,
			add theManuscript to their assignment List.
			 */
			if (!myActiveReviewerAssignmentMap.containsKey(theUserID)) {
				myActiveReviewerAssignmentMap.put(theUserID, new ArrayList<>());
				myActiveReviewerAssignmentMap.get(theUserID).add(theManuscript);
			} else if (!myActiveReviewerAssignmentMap.get(theUserID).contains(theManuscript)) {
				myActiveReviewerAssignmentMap.get(theUserID).add(theManuscript);
			}
		}
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
	public List<Manuscript> getAllSubmittedManuscripts(final String theId) {
		if(myActiveAuthorSubmitterMap.containsKey(theId)) {
			return myActiveAuthorSubmitterMap.get(theId);
			
		} else {
			throw new IllegalArgumentException("This user has not submitted any Manuscripts to this Conference");
		}
	}

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
				myActiveSubprogramChairAssignmentMap.put(userId, new ArrayList<>());
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
		if(myActiveAuthorSubmitterMap.containsKey(theId)) {
			if(myActiveAuthorSubmitterMap.get(theId).contains(theManuscript)) {
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
	public int getNumAssignedManuscripts(String theReviewerId) {
		int numAssigned = 0;
		if(myActiveReviewerAssignmentMap.containsKey(theReviewerId)) {
			numAssigned = myActiveReviewerAssignmentMap.get(theReviewerId).size();
		}
		return numAssigned;
	}
}