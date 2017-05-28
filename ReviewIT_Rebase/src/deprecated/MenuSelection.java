package deprecated;

import java.util.Scanner;

/**
 * @author Myles Haynes
 *
 */
public class MenuSelection {

    private MenuSelection() {
        //Prevents instantiation.
    }

    /**
     * Convenience method for getting a menu option.
     * Input that is <1 or >theMaxMenuOption values are caught and new input is prompted from user.
     * @param theMaxMenuOption inclusive maximum option for menu
     * @return number representing menu item selected.
     */
    public static int getMenuOptionSelection(int theMaxMenuOption) {
        Scanner in = new Scanner(System.in);
        int selection = -1;
        while(selection < 1 || selection > theMaxMenuOption) {
            try{
                String input = in.nextLine();
                selection = Integer.parseInt(input);
                if(selection > theMaxMenuOption) {
                    System.out.printf("You entered %d, maximum option is: %d\n", selection, theMaxMenuOption);
                } else if(selection < 0) {
                    System.out.println("You entered a negative number, and that's not a valid option.");
                } else {
                    return selection;
                }
            } catch (IllegalArgumentException i) {
                System.out.println("You didn't enter a number, please try again.");
            }
        }
        in.close();
        return selection;
    }
}
