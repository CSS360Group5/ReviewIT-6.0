package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An Object used to serialize UserObjects and to
 * mediate any interaction with them.
 * @author Kevin Ravana
 * @version 5/21/2017
 */
public class UserController implements Serializable {

    /*
    Current User's UserProfile
     */
    private UserProfile myCurrentUser;

    /*
    List of all registered UserProfiles
     */
    private List<UserProfile> myUserProfiles;


    public UserController() {
        myUserProfiles = new ArrayList<>();
    }

    /**
     *
     * @return UserProfile of the currently logged in user
     */
    public UserProfile getCurrentUser() {
        return myCurrentUser;
    }

    /**
     * This UserController Object is made aware of the current user.
     * @param theUserID The ID of the user that is currently logged in
     */
    public void setCurrentUser(final String theUserID) {
        for (UserProfile up : myUserProfiles) {
            if (up.getUserID().equals(theUserID)) {
                myCurrentUser = up;
            }
        }
    }

    /**
     * Creates a new UserProfile Object with theUserID
     * and theUserName.
     *
     * PRECONDITION: Client must call containsUserProfile() prior to
     * this method in order to ensure that the UserProfile that
     * contains theUserID does not already exist.
     *
     * @param theUserID Unique user ID of the UserProfile
     * @param theUserName Name of the UserProfile's associated user
     */
    public void createNewUserProfile(final String theUserID,
                                        final String theUserName) {
        myUserProfiles.add(new UserProfile(theUserID, theUserName));
    }

    /**
     * Retrieves UserProfile object whose user ID corresponds
     * to theUserID.
     *
     * PRECONDITION: Client must call containsUserProfile() prior to
     * this method in order to ensure that the method will return
     * an existing UserProfile.
     *
     * @param theUserID Unique ID of sought after UserProfile
     * @return Sought after registered user's UserProfile Object
     */
    public UserProfile getUser(final String theUserID) throws NoSuchElementException {
        UserProfile result = null;
        for (UserProfile up : myUserProfiles) {
            if (up.getUserID().equals(theUserID)) {
                result = up;
            }
        }
        return result;
    }

    /**
     * Return a boolean that indicates wether a particular user
     * exists.
     * @param theUserID Unique ID of sought after UserProfile
     * @return true if a UserProfile's User ID matches theUserID
     */
    public boolean containsUserProfile(final String theUserID) {
        for (UserProfile up : myUserProfiles) {
            if (up.getUserID().equals(theUserID)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return a List of all unique User IDs on record
     */
    public List<String> getAllUserIDs() {
        List<String> allUserIDs = new ArrayList<>();
        for (UserProfile up : myUserProfiles) {
            allUserIDs.add(up.getUserID());
        }
        return allUserIDs;
    }

    /**
     *
     * @return a List of each UserProfile's user name
     */
    public List<String> getAllUserNames() {
        List<String> allUserNames = new ArrayList<>();
        for (UserProfile up : myUserProfiles) {
            allUserNames.add(up.getName());
        }
        return allUserNames;
    }

    /**
     * Consults each UserProfile's Map of Conference keys and
     * Role values to create a list of users who share a Role
     * in a specified Conference.
     * @param theRole Specified Role
     * @param theConferenceName Specified Conference
     * @return a List of users who have the specified role in
     * the specified Conference
     */
    public List<UserProfile> getUsersByRole(final Role theRole,
                                            final String theConferenceName) {
        List<UserProfile> usersWithTheRole = new ArrayList<>();
        for (UserProfile up : myUserProfiles) {
            if (up.getRoles(theConferenceName).contains(theRole)) {
                usersWithTheRole.add(up);
            }
        }
        return usersWithTheRole;
    }

    /**
     * Creates a List of users who are eligible to review a
     * manuscript based on the relations assessed in the method.
     *
     * (Currently, the method fills a List of users who perform
     * the Reviewer Role for the specified conference, and whose
     * names are not identical to any of the manuscripts coauthors'
     * names.)
     *
     * @param theConferenceName
     * @param theManuscript
     * @return
     */
    public List<UserProfile> getEligibleReviewers(final String theConferenceName,
                                                  final Manuscript theManuscript) {
        List<UserProfile> Reviewers = getUsersByRole(Role.REVIEWER, theConferenceName);
        List<UserProfile> eligibleReviewers = new ArrayList<>();
        // TODO: Improve logic to check more than just author names
        for (UserProfile up : Reviewers) {
            if (!theManuscript.getAuthors().contains(up.getName())){
                eligibleReviewers.add(up);
            }  
        }
        return eligibleReviewers;
    }

    /**
     * Assigns a Role to a user for the designated Conference.
     * @param theConferenceName The designated Conference
     * @param theRole The appointed Role
     * @param theUserID The user's ID
     */
    public void assignConferenceRoleToUser(final String theConferenceName,
                                           final Role theRole,
                                           final String theUserID) {
        getUser(theUserID).assignRole(theConferenceName, theRole);
    }

    /**
     *
     * @return whether there are any records of any users
     */
    public boolean userListIsEmpty() {
        return myUserProfiles.isEmpty();
    }


}
