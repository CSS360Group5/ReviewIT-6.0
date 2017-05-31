<<<<<<< HEAD
package model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

/**
 * @author Dongsheng Han
 *
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
//	/**
//	 * Map of an Author's name to a List of Manuscripts that the author has submitted to this Conference.
//	 */
//	private Map<String, Collection<Manuscript>> myAuthorManuscriptMap;

	/**
	 * Map of a Subprogram Chair's user ID to a List of Manuscripts for which they have been designated responsible
	 * for this Conference.
	 */
	private Map<UserProfile, Collection<Manuscript>> mySubprogramChairAssignmentMap;
	
	private Map<UserProfile, Integer> myReviewerMap;

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
		this.myReviewerMap = new HashMap<>();
//		this.myAuthorManuscriptMap = new HashMap<>();
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
	public void submitManuscript(final Manuscript theManuscript) {
		//This needs to check bus. rules not just null fields of theManuscript
		//This should be a void method, to check if a manuscript is submitted use the getters methods!
		if (theManuscript.getAuthors() != null 
				&& theManuscript.getManuscript() != null 
				&& theManuscript.getMySubmissionDate() != null 
				&& theManuscript.getSubmissionUser() != null 
				&& theManuscript.getTitle() != null 
				/*&& isManuscriptSubmittable(theManuscript)*/) {
			myConferenceManuscripts.add(theManuscript);
		}else{
			throw new IllegalArgumentException("Failed submission of manuscript");
		}
	}
	
	/**
	 * This method will delete the chosen manuscript from the conference manuscript collection.
	 * @param theManuscript
	 * @author Harlan Stewart
	 */
	public void deleteManuscript(final Manuscript theManuscript) {
		if(myConferenceManuscripts.contains(theManuscript)){
			myConferenceManuscripts.remove(theManuscript);
		}
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
		
		for(Manuscript manuscript:myConferenceManuscripts){
			if(manuscript.getTitle().equals(theManuscript.getTitle()) 
					&& manuscript.getAuthors().equals(theManuscript.getAuthors())){
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
	
=======
package model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

/**
 * @author Dongsheng Han
 *
 */
public class Conference implements Serializable {
	
	private static final long serialVersionUID = 8924081621903486980L;

	public static final int MAX_NUMBER_OF_MANUSCRIPT_SUBMISSIONS = 5;
	public static final int MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED = 8;
	public static final int MIN_NUM_REVIEWS = 5;

>>>>>>> refs/remotes/origin/master
	/**
	 * List of all Manuscripts submitted to this Conference.
	 */
	private Collection<Manuscript> myConferenceManuscripts;
//	/**
//	 * Map of an Author's name to a List of Manuscripts that the author has submitted to this Conference.
//	 */
//	private Map<String, Collection<Manuscript>> myAuthorManuscriptMap;

	/**
	 * Map of a Subprogram Chair's user ID to a List of Manuscripts for which they have been designated responsible
	 * for this Conference.
	 */
	private Map<UserProfile, Collection<Manuscript>> mySubprogramChairAssignmentMap;
	
	private Map<UserProfile, Integer> myReviewerMap;

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
		this.myReviewerMap = new HashMap<>();
//		this.myAuthorManuscriptMap = new HashMap<>();
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
	public void submitManuscript(final Manuscript theManuscript) {
		//This needs to check bus. rules not just null fields of theManuscript
		//This should be a void method, to check if a manuscript is submitted use the getters methods!
		if (theManuscript.getAuthors() != null 
				&& theManuscript.getManuscript() != null 
				&& theManuscript.getMySubmissionDate() != null 
				&& theManuscript.getSubmissionUser() != null 
				&& theManuscript.getTitle() != null 
				&& isManuscriptSubmittable(theManuscript)) {
			myConferenceManuscripts.add(theManuscript);
		}else{
			throw new IllegalArgumentException("Failed submission of manuscript");
		}
	}
	
	/**
	 * This method will delete the chosen manuscript from the conference manuscript collection.
	 * @param theManuscript
	 * @author Harlan Stewart
	 */
	public void deleteManuscript(final Manuscript theManuscript) {
		if(myConferenceManuscripts.contains(theManuscript)){
			myConferenceManuscripts.remove(theManuscript);
		}
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
		for(Manuscript manuscript:myConferenceManuscripts){
			if(manuscript.getTitle().equals(theManuscript.getTitle()) 
					&& manuscript.getAuthors().equals(theManuscript.getAuthors())){
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
	 * @author Dongsheng Han
	 * could be improved by holding info in a map while submission
	 */
	public Collection<Manuscript> getManuscriptsSubmittedBy(final UserProfile theSubmitterUserProfile){
		Collection<Manuscript> manuscriptsSubmitted = new HashSet<>();
		for(final Manuscript currentManuscript: myConferenceManuscripts){
			if(currentManuscript.getSubmissionUser().equals(theSubmitterUserProfile)){
				manuscriptsSubmitted.add(currentManuscript);}
			
		}
		return manuscriptsSubmitted;
	}
	
	/**
	 * Returns a non-null Collection of Manuscript associate with theAuthorName for this Conference.
	 * @author Dongsheng Han
	 * could be improved by holding info in a map while submission
	 */
	public Collection<Manuscript> getManuscriptsByName(final String theAuthorName) {
		Collection<Manuscript> AuthorManuscripts = new ArrayList<>();
		for(Manuscript manuscript:myConferenceManuscripts){
			if(manuscript.getAuthors().contains(theAuthorName)){
				AuthorManuscripts.add(manuscript);
			}
		}
//		if(myAuthorManuscriptMap.containsKey(theAuthorName))
//			return myAuthorManuscriptMap.get(theAuthorName);
//		return new ArrayList<>();
		return AuthorManuscripts;
	}

	public void assignManuscriptToSubprogramChair(
			final Manuscript theManuscript,
			final UserProfile theSubprogramUserProfile
			) {
		if(!mySubprogramChairAssignmentMap.containsKey(theSubprogramUserProfile)){
			mySubprogramChairAssignmentMap.put(theSubprogramUserProfile, new HashSet<>());
		}
		mySubprogramChairAssignmentMap.get(theSubprogramUserProfile).add(theManuscript);
	}

	public Collection<Manuscript> getManuscriptAssignedToSubprogram(final UserProfile theSubprogramUser){
		if(mySubprogramChairAssignmentMap.containsKey(theSubprogramUser)){
			return mySubprogramChairAssignmentMap.get(theSubprogramUser);
		}
		return new ArrayList<>();
	}

	
	public String getName() {
		return myConferenceName;
	}
	
	/**
	 * getter for all the manuscripts
	 * @author Dongsheng Han
	 * @return myConferenceManuscripts
	 */
	public Collection<Manuscript> getManuscripts() {
		return myConferenceManuscripts;
	}
	
	/**
	 * getter for all the manuscripts assigned to specific subprogramchiar
	 * @author Dongsheng Han
	 * @return myConferenceManuscripts
	 */
	public Collection<Manuscript> getSubprogramChairAssignment(UserProfile subProgramChairUser) {
		return mySubprogramChairAssignmentMap.get(subProgramChairUser);
	}


	/**
	 * @author Dongsheng Han
	 * @param theReviewerProfile
	 * @return true if reviewer has less that 8 manuscript assigned.
	 */
	public boolean isLegalReviewer(UserProfile theReviewerProfile) {
		if(myReviewerMap.containsKey(theReviewerProfile) 
				&& myReviewerMap.get(theReviewerProfile) < MAX_NUMBER_OF_MANUSCRIPTS_ASSIGNED){
			myReviewerMap.put(theReviewerProfile, myReviewerMap.get(theReviewerProfile) + 1);
			return true;
		}
		if(!myReviewerMap.containsKey(theReviewerProfile) ){
			myReviewerMap.put(theReviewerProfile, 1);
			return true;
		}
		return false;
	}
	
	/**
	 * @author Danielle Lambion
	 * Retrieves an arrayList of eligible reviewers for this manuscript
	 * @param theManuscript
	 * @return
	 */
	public List<UserProfile> getEligibleReviewers(Manuscript theManuscript) {
			ArrayList<UserProfile> eligibleReviewers = new ArrayList<UserProfile>();	
			
			for (UserProfile key : myReviewerMap.keySet()) {
			    if(isLegalReviewer(key) && isReviewerNotAuthor(theManuscript.getAuthors(), key))
			    	eligibleReviewers.add(key);
			}
			return eligibleReviewers;
	}
	
	/**
	 * @author Danielle Lambion
	 * Returns true if reviewer is not an author and is available to review on this manuscript
	 * @param ManuscriptAuthors
	 * @param theReviewer
	 * @return
	 */
	public boolean isReviewerNotAuthor(Collection<String> ManuscriptAuthors, UserProfile theReviewer) {
		
		for (int i = 0; i < ManuscriptAuthors.size(); i++) {
			if(((List<String>) ManuscriptAuthors).get(i).equals(theReviewer.getName()))
				return false;
		}
		return true;
	}
}