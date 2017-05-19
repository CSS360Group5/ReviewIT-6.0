package deprecated;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import model.Conference;
import model.ConferenceController;


/**
 * @author G6
 * 
 * this class used only 4 testing purposes.
 *
 */
public class TestMain {

	/**
	 * @param args - arguments into main
	 */
	public static void main(String[] args) {
		System.out.println("-------------- Trial 1 for Conference Controller ----------------- \n");
		
		ConferenceController control = new ConferenceController();
		
	    ZonedDateTime deadline = ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]");
	    System.out.println(deadline);
	    
	    ZonedDateTime a = ZonedDateTime.now();                          //2013-03-02T21:52:25.371+01:00[Europe/London]
	    System.out.println(a);
	    ZonedDateTime b = ZonedDateTime.now(ZoneId.of("Europe/Paris")); //2013-03-02T22:52:25.374+02:00[Europe/Paris]
	    System.out.println(b );
	    
		
		System.out.println("Control isEmpty: " + control.isConferenceListEmpty());
		System.out.println("Size of conferences ArrayList: " + control.getNumberOfConferences());
		
//		control.createNewConference("The first Conference", deadline);
//		
//		System.out.println("Control isEmpty: " + control.conferences.isEmpty());
//		System.out.println("Size of conferences ArrayList: " + control.conferences.size());
		
		for(int i = 0; i < 10; i++) {
			deadline.toInstant();
			control.createNewConference("Le " + i + " conference", deadline);
		}
		System.out.println("\n\n\n\n");
		for (int i = 0; i < 10; i++) {
			System.out.println("The " + i + " conference is called: " + control.getConference(i).getMyConferenceName());
			System.out.println("The " + i + " conference deadline is: " + control.getConference(i).getSubmissionDeadline());
		}
		
		System.out.println("Current Conference: " + control.getCurrentConference());
		control.setCurrentConference(5);
		System.out.println("Current Conference: " + control.getCurrentConference().getMyConferenceName());
		
		System.out.println("Get Conference Info on Conference 4: " + control.getConference(4));
		
		System.out.println("Current Role View: " + control.getRoleView());
		
		System.out.println("\n\n ------------- Trial 2 for Conference Controller ------------------- \n");
		
		
		ZonedDateTime timeNow = ZonedDateTime.now();                          
		ZonedDateTime timeOther = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
		ZonedDateTime time3 = ZonedDateTime.now(ZoneId.of("Europe/London"));
		Conference conference1 = new Conference("The first Conference", timeNow);
		Conference conference2 = new Conference("The second Conference", timeOther);
		//Conference conference3 = new Conference("The third Conference", time3);

		control.removeAllConferences();
		
		System.out.println("Control isEmpty: " + control.isConferenceListEmpty());
		System.out.println("Size of conferences ArrayList: " + control.getNumberOfConferences());
		
		control.addConference(conference1);
		
		System.out.println("Control isEmpty: " + control.isConferenceListEmpty());
		System.out.println("Size of conferences ArrayList: " + control.getNumberOfConferences());
		System.out.println("Element 1 is: " + control.getConference(0).getMyConferenceName());
		System.out.println("Is conference Set: " + control.isConferenceSet());
		
		control.setCurrentConference(conference1);
		System.out.println("**Set to conference1??** --->> :" + control.getCurrentConference().getMyConferenceName());
		
		System.out.println("\n*** set Second Conference ***");
		
		control.setCurrentConference(conference2);
		System.out.println("Set conference when not even added: " + control.getCurrentConference().getMyConferenceName());
		
		System.out.println("Conference 1 location: " + control.searchForConference("The first Conference"));
		System.out.println("Conference 2 location: " + control.searchForConference("The second Conference"));
		
		System.out.println("Found second Conference: " + control.containsConference("The second Conference"));
		System.out.println("Found third Conference: " + control.containsConference("The third Conference"));
		
		System.out.println("\n*** created third conference ***");
		control.createNewConference("The third Conference", time3);
		System.out.println("Number of conferences in List: " + control.getNumberOfConferences());
		System.out.println("Found third Conference: " + control.searchForConference("The third Conference"));
		//String s = null;
		//System.out.println(s);
		
	}

}
