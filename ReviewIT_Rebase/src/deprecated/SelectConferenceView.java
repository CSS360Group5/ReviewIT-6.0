package deprecated;

import model.Conference;

import java.util.List;
import java.util.Objects;

/**
 * Class to select a conference to view.
 * @author Myles Haynes
 * @version 2.0
 *
 */
public class SelectConferenceView {

    /**
     * Static class, not instantiable.
     */
    private SelectConferenceView() {
        //Prevents instantiation
    }
    /**
     * Precondition: Nonnull list of conferences.
     *
     * Displays menu
     * @param theConferences List of conferences to select from.
     */
    public static Conference displaySelectConference(List<Conference> theConferences) {
        String leftAlignFormat = "%-3d| %-30s  |%n";
        Objects.requireNonNull(theConferences, "theConferences is null");

        int i = 1;
        for(Conference c: theConferences) {
            System.out.printf(leftAlignFormat,i,c.getMyConferenceName());
            i++;
        }
        System.out.print("Please select a conference :");
        int selection = MenuSelection.getMenuOptionSelection(i) - 1; //Menu is 1 indexed, Array is 0 indexed
        Conference selectedConf = theConferences.get(selection);
        Objects.requireNonNull(selectedConf, "selected Conference is null");
        return selectedConf;

    }


}
