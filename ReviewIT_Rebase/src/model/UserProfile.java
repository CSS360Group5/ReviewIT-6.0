package model;

import java.io.Serializable;
import java.util.*;

/**
 * An Object that allows persistence of User records.
 * Records include the user's name and unique ID, as
 * well as a relationship defining the user's roles
 * according to particular conferences.
 *
 * @author Kevin Ravana
 * @author Dimitar Kumanov
 * @version 5/18/2017
 */
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 3960715525297567136L;

    private String myUserID;
    private String myUserName;

    /**
     * Maps conferences to this user's roles for each conference.
     */
    private Map<String, ArrayList<Role>> myUserRoles;

    /**
     * List of user's roles for selected conference.
     */
    private transient List<Role> myCurrentConferenceRoles;

    /**
     * Creates a UserProfile Object with the given
     * name and user ID.
     *
     * PRECONDITION: theUserID and theUserName must be non-null
     * and non-empty.
     *
     * @param theUserID Unique identifier for this UserProfile
     * @param theUserName User's legal name
     * @throws IllegalArgumentException
     */
    public UserProfile(final String theUserID,
                       final String theUserName)
            throws IllegalArgumentException {

        myUserID = Objects.requireNonNull(theUserID);
        myUserName = Objects.requireNonNull(theUserName);
        myUserRoles = new HashMap<>();

        if(myUserID.isEmpty() || myUserName.isEmpty()) {
            throw new IllegalArgumentException();
        }

    }

    /**
     * Private method used to acquire this user's roles for a particular
     * conference.
     * @param theConferenceID Unique identifier for particular conference
     */
    private void getRoleListForConference(final String theConferenceID) {
        if (!myUserRoles.containsKey(theConferenceID)) {
            myUserRoles.put(theConferenceID, new ArrayList<>());
        }
        myCurrentConferenceRoles = myUserRoles.get(theConferenceID);
    }

    /**
     * Public method used to assign a role to this user for a particular
     * conference.
     * @param theConferenceID Unique identifier for particular conference
     * @param theRole Role enum to be assigned to this user
     */
    public void assignRole(final String theConferenceID,
                        final Role theRole) {
        getRoleListForConference(theConferenceID);
        if (!myCurrentConferenceRoles.contains(theRole)) {
            myCurrentConferenceRoles.add(theRole);
        }
    }

    /**
     * Returns a list of this  user's roles for a particular conference.
     * @param theConferenceID Unique identifier for particular conference
     * @return List of user's designated roles for a particular conference
     */
    public List<Role> getRoles(final String theConferenceID) {
        getRoleListForConference(theConferenceID);
        return myCurrentConferenceRoles;
    }

    /**
     *
     * @return Non-null, non-empty, and unique UserID for this profile
     */
    public String getUserID() {
        return myUserID;
    }

    /**
     *
     * @return Non-null, non-empty user name for this profile
     */
    public String getName() {
        return myUserName;
    }

    @Override
    public int hashCode(){
        return Objects.hash(myUserID);
    }

    @Override
    public boolean equals(final Object theOtherProfile){
        if(!(theOtherProfile instanceof UserProfile)){
            return false;
        }
        return this.myUserID.equals(((UserProfile) theOtherProfile).myUserID);
    }
}
