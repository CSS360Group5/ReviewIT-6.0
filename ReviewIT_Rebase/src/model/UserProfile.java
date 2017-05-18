package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Kevin on 5/18/2017.
 */
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 3960715525297567136L;

    private String myUID;
    private String myName;
    private List<Role> myUserRoles;

    public UserProfile(final String theUserID,
                       final String theUserName)
            throws IllegalArgumentException {

        myUID = Objects.requireNonNull(theUserID);
        myName = Objects.requireNonNull(theUserName);
        myUserRoles = new ArrayList<>();

        if(myUID.isEmpty() || myName.isEmpty()) {
            throw new IllegalArgumentException();
        }

    }

    public void addRole(final Role theRole) {
        if (!myUserRoles.contains(theRole)) {
            myUserRoles.add(theRole);
        }
    }

    public List<Role> getRoles() {
        return new ArrayList<>(myUserRoles);
    }

    @Override
    public int hashCode(){
        return Objects.hash(myUID);
    }

    @Override
    public boolean equals(final Object theOtherProfile){
        if(!(theOtherProfile instanceof UserProfile)){
            return false;
        }
        return this.myUID.equals(((UserProfile) theOtherProfile).myUID);
    }
}
