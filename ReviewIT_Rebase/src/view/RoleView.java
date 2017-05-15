package view;
import model.Conference;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Abstract class to define callable methods inside of the RoleView Object.
 */
public abstract class RoleView {
    protected Conference myConference;
    protected String myId;
    protected HashMap<String, ViewAction> myViewActions;

    protected RoleView(Conference theConference, String theId) {
        myConference = theConference;
        myId = theId;
        myViewActions = new HashMap<>();
    }

    /**
     * Displays menu of "actions" that were instantiated in constructor.
     * Builds indexed HashMap of "actions", that can be selected via a number.
     * the call to .displayAction() call the method that is decided in a child class.
     */
    public void displayView() {
        System.out.println("Please select:");
        Scanner in = new Scanner(System.in);
        HashMap<Integer, ViewAction> indexedActions = new HashMap<>();
        int menuNumber = 1;
        for(String actionTitle : myViewActions.keySet()) {
            indexedActions.put(menuNumber, myViewActions.get(actionTitle));
            System.out.printf("%d) %s\n",menuNumber,actionTitle);
            menuNumber++;
        }
        int selection = MenuSelection.getMenuOptionSelection(indexedActions.size()); //index actions are 1 indexed
        indexedActions.get(selection).displayAction();

        System.out.println("Please select:");
        System.out.println("1) Exit " + myConference.getMyConferenceName());
        System.out.println("2) Perform another task in " + myConference.getMyConferenceName());
        if(MenuSelection.getMenuOptionSelection(2) == 2) {
            displayView();
        }
        //If here, they've selected to exit, go back to list of conferences.
    }


}
