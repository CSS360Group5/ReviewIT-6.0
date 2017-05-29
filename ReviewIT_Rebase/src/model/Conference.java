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
	private Map<UserProfile, Collection<Manuscript>> mySubprogramChairAssignmentMap;

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
		this.mySubprogramChairAssignmentMap = new HashMap<>();
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
	 * @author Dongsheng Han
	 */
	public boolean submitManuscript(final Manuscript theManuscript) {
		if (theManuscript.getAuthors() != null 
				&& theManuscript.getManuscript() != null 
				&& theManuscript.getMySubmissionDate() != null 
				&& theManuscript.getSubmissionUser() != null 
				&& theManuscript.getTitle() != null 
				&& isManuscriptSubmittable(theManuscript)) {
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
			if(mySubprogramChairAssignmentMap.containsKey(userId)) {
				if(!(mySubprogramChairAssignmentMap.get(userId).contains(theManuscript))) {
					mySubprogramChairAssignmentMap.get(userId).add(theManuscript);
					assignedManuscript = true;
				}
			} else {
				mySubprogramChairAssignmentMap.put(theUserProfile, new ArrayList<>());
				mySubprogramChairAssignmentMap.get(userId).add(theManuscript);
				assignedManuscript = true;
			}
		}
		return assignedManuscript;
	}

	public Collection<Manuscript> getManuscriptAssignedToSubprogram(final UserProfile theSubprogramUser){
		if(mySubprogramChairAssignmentMap.containsKey(theSubprogramUser)){
			return mySubprogramChairAssignmentMap.get(theSubprogramUser);
		}
		return new ArrayList<>();
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
	
	public String getName() {
		return myConferenceName;
	}
}