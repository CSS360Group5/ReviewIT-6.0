package view;

import model.UserProfile;
import model.UserProfileStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LoginPanel allows users to either login with an existing
 * user ID, or create a new UserProfile.
 * @author Kevin Ravana
 * @version 5/29/2017
 */
public class LoginPanel extends AutoSizeablePanel {

    private static final String INCORRECT_USERID_FORMAT_MESSAGE = "UserID must resemble 'exampleID@uw.edu'.";
    private static final String EXAMPLE_USERID_TEXT = "exampleID@uw.edu";
    private static final Dimension DEFAULT_TEXT_FIELD_SIZE = new Dimension(250, 20);
    private static final String DEFAULT_EMAIL_REGEX_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final JTextField myLoginInputField;
    private final JTextField myNewUserIDInputField;
    private final JTextField myNewUserNameInputField;
    private final JTextArea myErrorMessageLabel;


//    /**
//     * Sample main used to set up this Panel
//     * and initialize it and put it into a JFrame.
//     */
//    public static void main(String[] args){
//
//        EventQueue.invokeLater(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                Conference con1 = new Conference("First Conference",
//                        ZonedDateTime.now());
//                Conference con2 = new Conference("Second Conference",
//                        ZonedDateTime.now());
//                ConferenceStateManager.getInstance().addConference(con1);
//                ConferenceStateManager.getInstance().addConference(con2);
//
//                UserProfile userKevin = new UserProfile("kev@uw.edu", "Kevin");
//                userKevin.addRole(Role.AUTHOR, con1);
//                userKevin.addRole(Role.SUBPROGRAM, con1);
//                userKevin.addRole(Role.AUTHOR, con2);
//                UserProfileStateManager.getInstance().addUserProfile(userKevin);
//
//                final JFrame window = new JFrame();
//                final JPanel mainPanel = new LoginPanel(1, 1, new Dimension(750, 550));
//
//                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                window.setContentPane(mainPanel);
//                window.pack();
//                window.setLocationRelativeTo(null);
//                window.setVisible(true);
//            }
//        });
//    }

    public LoginPanel(final double theXRatio,
                      final double theYRatio,
                      final Dimension theStartingSize) {


        super(theXRatio, theYRatio, theStartingSize);
        myLoginInputField = new JTextField(EXAMPLE_USERID_TEXT);
        myNewUserIDInputField = new JTextField(EXAMPLE_USERID_TEXT);
        myNewUserNameInputField = new JTextField();
        myErrorMessageLabel = new JTextArea();
        myErrorMessageLabel.setForeground(Color.red);
        initialize();
    }

    /**
     * Initializes all components for this panel.
     */
    private void initialize() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        final JLabel enterLoginLabel = new JLabel("Enter UserID: ");

        gbc.insets = new Insets(0, 5, 5, 0);

        // Add Error Label
        gbc.gridx = 1;
        gbc.gridy = 0;
        myErrorMessageLabel.setEditable(false);
        add(myErrorMessageLabel, gbc);

        // Add Login Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(enterLoginLabel, gbc);

        // Add Login Input Field
        gbc.gridx = 1;
        gbc.gridy = 1;
        myLoginInputField.setPreferredSize(DEFAULT_TEXT_FIELD_SIZE);
        add(myLoginInputField, gbc);

        // Add Login Button
        gbc.ipadx = 0;
        gbc.ipady = -5;
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(submitIdentificationBtn(), gbc);

        // Add Or Label
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.ipady = 30;
        add(new JLabel("Or register as a new user"), gbc);

        // Add New UserID Label
        gbc.ipady = 0;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Desired UserID: "), gbc);

        // Add New UserID Input Field
        gbc.gridx = 1;
        gbc.gridy = 3;
        myNewUserIDInputField.setPreferredSize(DEFAULT_TEXT_FIELD_SIZE);
        add(myNewUserIDInputField, gbc);

        // Add New User Name Label
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Your Name: "), gbc);

        // Add New User Name Input Field
        gbc.gridx = 1;
        gbc.gridy = 4;
        myNewUserNameInputField.setPreferredSize(DEFAULT_TEXT_FIELD_SIZE);
        add(myNewUserNameInputField, gbc);

        // Add Register Button
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.ipady = -5;
        add(registerNewUserBtn(), gbc);
    }

    private JButton submitIdentificationBtn() {
        JButton submitBtn = new JButton("Login");
        submitBtn.addActionListener(
                new LoginListener("UserID does not exist."));
        return submitBtn;
    }

    private JButton registerNewUserBtn() {
        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(
                new RegisterListener("UserID already exists."));
        return registerBtn;
    }

    /**
     * Sets the current user and opens a prompt for the user
     * to select a Conference and Role.
     * @param theUser A UserProfile Object that will be
     *                used as the current user.
     */
    private void successfulLogin(final UserProfile theUser) {
        UserProfileStateManager.getInstance().setCurrentUser(theUser);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new ConfRoleSelectPanel(1, 1, new Dimension(1350, 450)));
        frame.revalidate();
    }

    /**
     * Creates and adds a new UserProfile Object to UserProfileStateManager
     */
    private void successfulRegistration() {
        UserProfileStateManager.getInstance().addUserProfile(
                new UserProfile(myNewUserIDInputField.getText(),
                        myNewUserNameInputField.getText()));
        myErrorMessageLabel.setText("Successfully Registered.\nPlease login to continue.");
    }

    /**
     * Checks to ensure that the UserID entered by the user is
     * correctly formatted as an email address should be.
     * @param theUserID The UserID entered by the user.
     * @return true if theUserID matches DEFAULT_EMAIL_REGEX_PATTERN.
     */
    private boolean isProperUserIDFormat(final String theUserID) {
        Pattern ptrn = Pattern.compile(DEFAULT_EMAIL_REGEX_PATTERN);
        Matcher matcher = ptrn.matcher(theUserID);
        return matcher.matches();
    }

    /**
     * Sets myErrorMessageLabel's text in the event of a failed attempt at
     * either logging in or registering.
     * @param theFailureMessage A String used as the message to the user.
     */
    private void failedAttempt(final String theFailureMessage) {
        myErrorMessageLabel.setText(theFailureMessage);
        myLoginInputField.setText(EXAMPLE_USERID_TEXT);
        myNewUserIDInputField.setText(EXAMPLE_USERID_TEXT);
    }

    /**
     * Private Inner Class that implements ActionListener -
     * Performs login actions based on user input.
     */
    private class LoginListener implements ActionListener {

        private final String myFailureMessage;

        /**
         * Requests a LoginListener that will report failures with the
         * given message.
         * @param theFailMessage The reason for failure to perform the action.
         */
        LoginListener(final String theFailMessage) {
            myFailureMessage = theFailMessage;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean userIDExists = false;
            Collection<UserProfile> allUserProfiles =
                    UserProfileStateManager.getInstance().getAllUserProfiles();
            if (isProperUserIDFormat(myLoginInputField.getText())) {
                for (UserProfile up : allUserProfiles) {
                    if (up.getUserID().equals(myLoginInputField.getText())) {
                        userIDExists = true;
                        successfulLogin(up);
                    }
                }
                if (!userIDExists) failedAttempt(myFailureMessage);
            } else {
                failedAttempt(INCORRECT_USERID_FORMAT_MESSAGE);
            }
        }
    }

    /**
     * Private Inner Class that implements ActionListener -
     * Performs registration related actions based on user input.
     */
    private class RegisterListener implements ActionListener {

        private final String myFailureMessage;

        /**
         * Requests a RegisterListener that will report failures with the
         * given message.
         * @param theFailMessage The reason for failure to perform the action.
         */
        RegisterListener(final String theFailMessage) {
            myFailureMessage = theFailMessage;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean userIDExists = false;
            boolean userIDIsProperlyFormatted = isProperUserIDFormat(myNewUserIDInputField.getText());
            boolean userNameIsProperlyFormatted = !myNewUserNameInputField.getText().isEmpty();
            Collection<UserProfile> allUserProfiles =
                    UserProfileStateManager.getInstance().getAllUserProfiles();
            if (userIDIsProperlyFormatted && userNameIsProperlyFormatted) {
                for (UserProfile up : allUserProfiles) {
                    if (up.getUserID().equals(myNewUserIDInputField.getText())) {
                        userIDExists = true;
                        failedAttempt(myFailureMessage);
                    }
                }
                if (!userIDExists) successfulRegistration();
            } else if (userNameIsProperlyFormatted) {
                failedAttempt(INCORRECT_USERID_FORMAT_MESSAGE);
            } else {
                failedAttempt("Name must contain at least one character.");
            }
        }
    }
}
