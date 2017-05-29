package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * Notifies all Observers w/ a UserProfile when the current UserProfile changes and
 * notifies all Observers w/ a List<UserProfile> when a UserProfile is added or removed.
 * 
 * @author Kevin Ravana
 * @author Dimitar Kumanov
 * @version 5/21/2017
 */
public class UserProfileStateManager extends Observable implements Serializable {

    /*
    Current User's UserProfile
     */
    private UserProfile myCurrentUserProfile;
    
    private Role myCurrentRole;

    /*
    List of all registered UserProfiles
     */
    private Collection<UserProfile> myUserProfiles;

    private static UserProfileStateManager myInstance;

    private UserProfileStateManager() {
        myUserProfiles = new HashSet<>();
    }
    
    public static UserProfileStateManager getInstance(){
    	if(myInstance == null)
    		myInstance = new UserProfileStateManager();
    	return myInstance;
    }

    /**
     * This method deletes the current instances.
     * Only for testing purposes.
     */
    public static void destroy(){
    	myInstance = null;
    }
    
    public void addUserProfile(final UserProfile theUserProfile){
    	myUserProfiles.add(theUserProfile);
    	this.hasChanged();
        this.notifyObservers(myUserProfiles);
    }
    
    public UserProfile getCurrentUserProfile() {
        return myCurrentUserProfile;
    }
    
    public void setCurrentRole(final Role theCurrentRole){
    	myCurrentRole = theCurrentRole;
    }
    
    public Role getCurrentRole(){
    	return myCurrentRole;
    }

    /**
     * This UserProfileStateManager Object is made aware of the current user.
     * @throws IllegalArgumentException iff !this.getAllUserProfiles.contains(theCurrentUserProfile)
     */
    public void setCurrentUser(final UserProfile theCurrentUserProfile) {
        if(!myUserProfiles.contains(theCurrentUserProfile)){
        	throw new IllegalArgumentException("UserProfile must be added first!");
        }
        myCurrentUserProfile = theCurrentUserProfile;
        this.hasChanged();
        this.notifyObservers(myCurrentUserProfile);
    }
    
    public Collection<UserProfile> getAllUserProfiles(){
    	return myUserProfiles;
    }

    public boolean isCurrentUserProfileSet(){
    	return myCurrentUserProfile != null;
    }
    
    public boolean isCurrentRoleSet(){
    	return myCurrentRole != null;
    }
//    /**
//		THIS SHOULD BE IN CONFERENCE!
//     * Creates a List of users who are eligible to review a
//     * manuscript based on the relations assessed in the method.
//     *
//     * (Currently, the method fills a List of users who perform
//     * the Reviewer Role for the specified conference, and whose
//     * names are not identical to any of the manuscripts coauthors'
//     * names.)
//     *
//     * @param theConferenceName
//     * @param theManuscript
//     * @return
//     */
//    public List<UserProfile> getEligibleReviewers(final String theConferenceName,
//                                                  final Manuscript theManuscript) {
//        List<UserProfile> eligibleReviewers = getUsersByRole(Role.REVIEWER,
//                theConferenceName);
//        // TODO: Improve logic to check more than just author names
//        for (UserProfile up : myUserProfiles) {
//            for (String author : theManuscript.getAuthors()) {
//                if (!up.getName().equals(author)) {
//                    eligibleReviewers.add(up);
//                }
//            }
//        }
//        return eligibleReviewers;
//    }

}
