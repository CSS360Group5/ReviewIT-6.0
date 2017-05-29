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

	private static ConferenceStateManager myInstance;

	/**
	 * Constructor. Initializes the ArrayList.
	 */
	private ConferenceStateManager() {
		myConferences = new HashSet<>();
    }
	
	public ConferenceStateManager getInstance(){
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
	}

//	/**
//	 * Create a new conference and add to ArrayList of myConferences.
//	 * 
//	 * @param confName - conference Name
//	 * @param theSubmissionDeadline - deadline for authors
//	 * @throws IllegalArgumentException iff confName.isEmpty()
//	 */
//	public void createNewConference(String confName, ZonedDateTime theSubmissionDeadline) throws IllegalArgumentException{
//		if(confName.isEmpty()){
//			throw new IllegalArgumentException();
//		}
//		Conference newConference = new Conference(confName, theSubmissionDeadline);
//		myConferences.add(newConference);
//	}

	public void addConference(Conference theConference) {
		myConferences.add(Objects.requireNonNull(theConference));
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
            if (myConference.getName().equals(conferenceName)) {
                found = true;
            }
		return found;

	}

//	/**
//	 * Returns Integer representation of conference..
//	 * 
//	 * @param conferenceName name of conference
//	 * @return integer representation of conference.
//	 * @throws IllegalArgumentException if parameter is not the right type!
//	 */
//	public int searchForConference(String conferenceName) throws IllegalArgumentException {
//        int found = 0;
//	    if ((conferenceName != null) && (!conferenceName.equals(""))) {
//            for (int i = 0; i < myConferences.size(); i++) {
//                if (myConferences.get(i).getMyConferenceName().equals(conferenceName)) {
//                    found = i;
//                }
//            }
//        } else {
//	        throw new IllegalArgumentException("Parameter is empty String!");
//        }
//		return found;
//	}


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
	 * returns true if conference list is empty.
	 * 
	 * @return boolean for conference list.
	 */
	public boolean isConferenceListEmpty() {
		return myConferences.isEmpty();
	}

	public Collection<Conference> getConferences() {
		return myConferences;
	}

}
