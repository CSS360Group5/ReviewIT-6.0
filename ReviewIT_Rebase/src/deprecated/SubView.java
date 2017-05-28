package deprecated;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Conference;
import model.Manuscript;

//import com.sun.org.apache.regexp.internal.recompile;

/**
 * @author Myles Haynes
 * @date 4-28-2017
 */
public class SubView extends AuthorView {

    protected ArrayList<Manuscript> myAssignedManuscripts;

    /**
     * @author Myles Haynes
     * @param theConference conference to display
     * @param theId id of user
     */
    public SubView(Conference theConference, String theId) {
        super(theConference,theId);
        myAssignedManuscripts = myConference.getSubManuscripts(myId);
        myViewActions.put("Assign Reviewer to Manuscript",this::displaySelectManuscript);
    }

    /**
     * @author Myles Haynes
     * @author Lorenzo Pacis
     * Displays all assigned manuscripts to myId
     * Selects a manuscript, passes it to displayAssignReviewer
     */
    private void displaySelectManuscript() {
        int index = 1;
        Manuscript selectedManuscript;
        String format= "| %-2s | %-35s | %-35s | %-45s | %-70s |%n";
        System.out.printf(format,"ID", "Title", "Author","Co-Authos","Reviewers");
        System.out.println(String.join("", Collections.nCopies(203, "-")));
        for(Manuscript m: myConference.getSubManuscripts(myId)) {
        	ArrayList<String> reviewers = m.getReviewers();
        	List<String> coAuthors = m.getAuthors();
        	coAuthors.remove(m.getSubmissionUser());
        	StringBuilder reviewersString = new StringBuilder();
        	if(!reviewers.isEmpty()) {
        		for(String reviewer : reviewers) {
            		reviewersString.append(reviewer + " | ");
        		}
        	} else {
        		reviewersString.append("No reviewers assigned");
        	}
        	
        	StringBuilder coAuthorsString = new StringBuilder();
        	if(!coAuthors.isEmpty()) {
        		for(String coAuthor : coAuthors) {
        			coAuthorsString.append(coAuthor + " | ");
        		}
        	} else {
        		coAuthorsString.append("No CoAuthors");
        	}
            System.out.printf(format,index, m.getTitle(), m.getSubmissionUser(), coAuthorsString, reviewersString );
            index++;
        }
        System.out.println(String.join("", Collections.nCopies(203, "-")));
        if(myConference.getSubManuscripts(myId).size() == 0) {
        	System.out.println("You have no manuscripts assigned to you");
        } else {
        	System.out.print("Select a manuscript ID to assign: ");
            int selection = MenuSelection.getMenuOptionSelection(index) - 1; //0 index array, 1 indexed menu.
            selectedManuscript = myConference.getSubManuscripts(myId).get(selection);
            displayAssignReviewer(selectedManuscript);
        }

    }

    /**
     * Displays options for assigning Review to manuscript.
     * No business rules are applied here.
     * @param theManuscript Manuscript to assign.
     */
    private void displayAssignReviewer(Manuscript theManuscript) {
        System.out.println("\nYou've selected: " + theManuscript.getTitle());
        displayCurrentReviewers(theManuscript);
        System.out.println("Your reviewers are ");
      	int i = 1; 
      	boolean avail = false;
      	for(String reviewer : myConference.getReviewers()) {
      		if(!theManuscript.getReviewers().contains(reviewer)) {
      			System.out.println(i + ") " + reviewer);
      			avail = true;
      		}
      		i++;
      	}
      	if(!avail) {
      		System.out.println("No Reviewers are available to be assigned to this manuscript.");
      	}
      	System.out.print("Please select a reviewer: ");
        int selection = MenuSelection.getMenuOptionSelection(myConference.getReviewers().size())-1;
        
        try {
        	myConference.assignReviewer(theManuscript, myConference.getReviewers().get(selection));
            displayCurrentReviewers(theManuscript);
        } catch (IllegalArgumentException e) {
        	System.out.println(e.getMessage());
        }
        	
    }

    private void displayCurrentReviewers(Manuscript theManuscript) {

        if(theManuscript.getReviewers().size() == 0) {
        	System.out.println("No reviewers are assigned to this manuscript.");
        } else {
            System.out.print("Reviewers currently assigned to this manuscript are: \n");
            for(String r: theManuscript.getReviewers()) {
            	System.out.printf("%s\n",r);
            }
        }

    }


}
