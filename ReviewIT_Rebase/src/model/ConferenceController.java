package model;
import deprecated.RoleView;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @author Group 6
 * @author Daniel
 * @version 1.1
 * @date 5/04/17
 */

public class ConferenceController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -778163843425439777L;

	private Conference myCurrentConference;
	
	private List<Conference> myConferences;

	private List<UserProfile> myUserProfiles;
	
	private RoleView myView;

	/**
	 * Constructor. Initializes the ArrayList.
	 */
	public ConferenceController() {
        myUserProfiles = new ArrayList<>();
		myConferences = new ArrayList<>();
    }

	/**
	 * Returns the current Conference.
	 * 
	 * @return current Conference
	 */
	public Conference getCurrentConference() {
		return myCurrentConference;
	}



	/**
	 * Set conference by passing in a conference. (Will add conference to end of
	 * ArrayList if not already in it)
	 * 
	 * @param c - conference passed in to set as current.
     * @throws IllegalArgumentException when parameter is null.
	 */
	public void setCurrentConference(Conference c) throws IllegalArgumentException {
        if (c != null) {
            if (myConferences.contains(c)) {
                myCurrentConference = c;
            } else {
                addConference(c);
                setCurrentConference(myConferences.size() - 1);
            }
        } else {
            throw new IllegalArgumentException();
        }
	}

	/**
	 * Set conference by passing in a integer for myConferences array list.
	 * 
	 * @param i - integer of conference desired.
     * @throws IndexOutOfBoundsException if index is not in bounds of myConferences ArrayList
	 */
	public void setCurrentConference(int i) throws IndexOutOfBoundsException {
        if ((i > myConferences.size() - 1) || i < 0) {
            throw new IndexOutOfBoundsException("Index is out of Bounds!");
        }
		myCurrentConference = myConferences.get(i);
	}

	/**
	 * Returns the desired conference
	 * 
	 * @param i - conference in ArrayList
	 * @return conference [i] in arrayList
     * @throws IndexOutOfBoundsException if index is not in bounds of ArrayList
	 */
	public Conference getConference(int i) throws IndexOutOfBoundsException {
        if ((i > myConferences.size() - 1) || i < 0) {
            throw new IndexOutOfBoundsException("Index is out of Bounds!");
        }
	    return myConferences.get(i);
	}

	/**
	 * Create a new conference and add to ArrayList of myConferences.
	 * 
	 * @param confName - conference Name
	 * @param theSubmissionDeadline - deadline for authors
	 * @throws IllegalArgumentException
	 */
	public void createNewConference(String confName, ZonedDateTime theSubmissionDeadline) throws IllegalArgumentException{
		if(confName.isEmpty()){
			throw new IllegalArgumentException();
		}
		Conference newConference = new Conference(confName, theSubmissionDeadline);
		int i = myConferences.size();
		myConferences.add(i, newConference);
	}

	/**
	 * Add conference to ArrayList
	 * 
	 * @param c - conference to be added
	 */
	public void addConference(Conference c) {
		int i = myConferences.size();
		myConferences.add(i, Objects.requireNonNull(c));
    }

	/**
	 * Return number of myConferences in database.
	 * 
	 * @return number of myConferences in database.
	 */
	public int getNumberOfConferences() {
		return myConferences.size();
	}

	/**
	 * Returns true if the conference is in database.
	 * 
	 * @param conferenceName
	 *            - name of conference to search
	 * @return true if conference is in list.
	 */
	public boolean containsConference(String conferenceName) {
		boolean found = false;
        for (Conference myConference : myConferences)
            if (myConference.getMyConferenceName().equals(conferenceName)) {
                found = true;
            }
		return found;

	}

	/**
	 * Returns Integer representation of conference..
	 * 
	 * @param conferenceName name of conference
	 * @return integer representation of conference.
	 * @throws IllegalArgumentException if parameter is not the right type!
	 */
	public int searchForConference(String conferenceName) throws IllegalArgumentException {
        int found = 0;
	    if ((conferenceName != null) && (!conferenceName.equals(""))) {
            for (int i = 0; i < myConferences.size(); i++) {
                if (myConferences.get(i).getMyConferenceName().equals(conferenceName)) {
                    found = i;
                }
            }
        } else {
	        throw new IllegalArgumentException("Parameter is empty String!");
        }
		return found;
	}

	/**
	 * Setter for RoleView
	 * 
	 * @param view - the new RoleView being set.
     * @throws IllegalArgumentException if parameter is not the right type!
	 */
	public void setRoleView(RoleView view) throws IllegalArgumentException {
		if(view != null) {
            this.myView = view;
        } else {
		    throw new IllegalArgumentException("Parameter is Null!");
        }

	}

	/**
	 * Getter for RoleView
	 * 
	 * @return RoleView
	 */
	public RoleView getRoleView() {
		return myView;
	}

	/**
	 * Removes all myConferences from the list.
	 */
	public void removeAllConferences() {
		myConferences = new ArrayList<>();

	}

	/**
	 * Boolean to see if current conference is set.
	 * 
	 * @return true if a conference is set
	 */
	public boolean isConferenceSet() {
		boolean set = false;
		if (myCurrentConference != null)
			set = true;
		return set;
	}

	/**
	 * Boolean to see if Role myView is set.
	 * 
	 * @return true if role myView is set.
	 */
	public boolean isRoleViewSet() {
		boolean set = false;
		if (myView != null)
			set = true;
		return set;
	}

	/**
	 * returns true if conference list is empty.
	 * 
	 * @return boolean for conference list.
	 */
	public boolean isConferenceListEmpty() {
		return myConferences.isEmpty();
	}

	public List<Conference> getConferences() {
		return myConferences;
	}

}
