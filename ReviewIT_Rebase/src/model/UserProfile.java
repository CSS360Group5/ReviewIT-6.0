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
    private Map<Conference, Collection<Role>> myConferenceRoleMap;

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
        myConferenceRoleMap = new HashMap<>();

        if(myUserID.isEmpty() || myUserName.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public Collection<Role> getRolesForConference(final Conference theConference){
    	if(!myConferenceRoleMap.containsKey(theConference)){
    		return new HashSet<>();
    	}
    	return myConferenceRoleMap.get(theConference);
    }

    public void addRole(final Role theRole, final Conference theConference){
    	if(!myConferenceRoleMap.containsKey(theConference)){
    		myConferenceRoleMap.put(theConference, new HashSet<>());
    	}
    	myConferenceRoleMap.get(theConference).add(theRole);
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
