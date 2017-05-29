package model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

/**
 * Notifies all Observers w/ a Conference when the current conference changes and
 * notifies all Observers w/ a List<Conference> when a Conference is added or removed.
 * 
 * @author Group 6
 * @author Daniel
 * @author Dimitar Kumanov
 * @version 1.1
 * @date 5/04/17
 */

public class ConferenceStateManager extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -778163843425439777L;

	private Conference myCurrentConference;
	
	private Collection<Conference> myConferences;

	private static ConferenceStateManager myInstance;

	/**
	 * Constructor. Initializes the ArrayList.
	 */
	private ConferenceStateManager() {
		myConferences = new HashSet<>();
    }
	
	public static ConferenceStateManager getInstance(){
		if(myInstance == null){
			myInstance = new ConferenceStateManager();
		}
		return myInstance;
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
	public void setCurrentConference(Conference theCurrentConference) throws IllegalArgumentException {
		if(!this.getConferences().contains(theCurrentConference)){
			throw new IllegalArgumentException("Can set theCurrentConference to a Conference not added.");
		}
        myCurrentConference = theCurrentConference;
        this.hasChanged();
        this.notifyObservers(myCurrentConference);
	}

	public void addConference(Conference theConference) {
		myConferences.add(Objects.requireNonNull(theConference));
		this.hasChanged();
        this.notifyObservers(myConferences);
    }

	/**
	 * Removes all myConferences from the list.
	 */
	public void removeAllConferences() {
		myConferences = new ArrayList<>();
		this.hasChanged();
        this.notifyObservers(myConferences);
	}

	public boolean isCurrentConferenceSet() {
		return myCurrentConference != null;
	}

	public Collection<Conference> getConferences() {
		return myConferences;
	}

}
