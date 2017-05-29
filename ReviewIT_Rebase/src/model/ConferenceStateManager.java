package model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @author Group 6
 * @author Daniel
 * @author Dimitar Kumanov
 * @version 1.1
 * @date 5/04/17
 */

public class ConferenceStateManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -778163843425439777L;

	private Conference myCurrentConference;
	
	private Collection<Conference> myConferences;


	/**
	 * Constructor. Initializes the ArrayList.
	 */
	public ConferenceStateManager() {
		myConferences = new HashSet<>();
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
     * @throws IllegalArgumentException iff !this.getConferences().contains(theCurrentConference)
	 */
	public void setCurrentConference(final Conference theCurrentConference) throws IllegalArgumentException {
		if(!this.getConferences().contains(theCurrentConference)){
			throw new IllegalArgumentException("Can set theCurrentConference to a Conference not added.");
		}
        myCurrentConference = theCurrentConference;
	}

	public void addConference(Conference theConference) {
		myConferences.add(Objects.requireNonNull(theConference));
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
	public boolean isCurrentConferenceSet() {
		if (myCurrentConference == null)
			return false;
		return true;
	}


	public Collection<Conference> getConferences() {
		return myConferences;
	}

}
