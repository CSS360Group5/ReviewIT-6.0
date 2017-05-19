package deprecated;
import model.Conference;
import model.Manuscript;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Myles Haynes
 * @version 1.0
 * @date 4/28/2017
 */
public class AuthorView extends RoleView {

    public AuthorView(Conference theConference, String theId) {
        super(theConference, theId);
        myViewActions.put("Submit a manuscript to " + myConference.getMyConferenceName(), this::displayCreateManuscript);
        myViewActions.put("View already submitted manuscripts", this::displayViewSubmittedManuscripts);
    }

    private void displayViewSubmittedManuscripts() {
    	int subs;
    	try {
    		subs = myConference.getManuscripts(myId).size();
    	} catch(IllegalArgumentException e) {
    		System.out.println(e.getMessage());
    		return;
    	}
        String format= "| %-30s | %-30s | %-60s |%n";
        System.out.printf(format, "Title", "Date Submitted", "Co Authors");
        System.out.println(String.join("", Collections.nCopies(130, "-")));
        for(Manuscript m: myConference.getManuscripts(myId)) {
        	List<String> coAuthors = m.getAuthors();
        	coAuthors.remove(m.getSubmissionUser());
        	StringBuilder coAuthorsString = new StringBuilder();
        	if(!coAuthors.isEmpty()) {
        		for(String coAuthor : coAuthors) {
        			coAuthorsString.append(coAuthor + " | ");
        		}
        	} else {
        		coAuthorsString.append("No CoAuthors");
        	}
        	
            System.out.printf(format, m.getTitle(),
            		m.getMySubmissionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm")), coAuthorsString);
        }
        System.out.println(String.join("", Collections.nCopies(130, "-")));
        System.out.println();
    }


    private void displayCreateManuscript() {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter the title of the paper: ");
        String title = in.nextLine();

        System.out.print("Please enter any authors' username besides yourself, separated by a comma, if there are no co authors press Enter: ");
 
        List<String> coAuthors = new ArrayList<>();

        String[] c = in.nextLine().split(",");
        if(c.length != 1) {
            coAuthors.addAll(Arrays.asList(c));
        }
        System.out.println("Example File Path: C/Users/John/Documents/Manuscript.docx");
        System.out.println("Please enter the file path:");
        File f = new File(in.nextLine());
        try  {
        	myConference.submitManuscript(new Manuscript(title, myId, coAuthors, ZonedDateTime.now(), f));
        }catch(IllegalArgumentException e) {
        	System.out.println(e.getMessage());
        	
        }
        displayViewSubmittedManuscripts();
    }
}
