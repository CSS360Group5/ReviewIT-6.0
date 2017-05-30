package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kevin Ravana
 * @version 5/29/2017
 */
public class ConfRoleSelectPanel extends AutoSizeablePanel {

    private JComboBox<Conference> myConferenceComboBox;
    private JComboBox<Role> myRoleComboBox;

    private static final Dimension DEFAULT_COMBO_BOX_SIZE = new Dimension(250, 20);

    /**
     * Sample main used to set up this Panel
     * and initialize it and put it into a JFrame.
     */
    public static void main(String[] args){

        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Conference con1 = new Conference("First Conference",
                        ZonedDateTime.now());
                Conference con2 = new Conference("Second Conference",
                        ZonedDateTime.now());
                ConferenceStateManager.getInstance().addConference(con1);
                ConferenceStateManager.getInstance().addConference(con2);

                UserProfile userKevin = new UserProfile("kev@uw.edu", "Kevin");
                UserProfileStateManager.getInstance().addUserProfile(userKevin);
                UserProfileStateManager.getInstance().setCurrentUser(userKevin);
                userKevin.addRole(Role.AUTHOR, con1);
                userKevin.addRole(Role.SUBPROGRAM, con1);
                userKevin.addRole(Role.AUTHOR, con2);

                final JFrame window = new JFrame();
                final JPanel mainPanel = new ConfRoleSelectPanel(1, 1, new Dimension(750, 550));

                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
    }

    public ConfRoleSelectPanel(final double theXRatio,
                      final double theYRatio,
                      final Dimension theStartingSize) {
        super(theXRatio, theYRatio, theStartingSize);
        initialize();
    }

    /**
     * Initializes all components for this panel.
     */
    private void initialize() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        final JLabel pleaseSelectConferenceLabel = new JLabel("Successfully logged in! Please selected a Conference: ");
        final JLabel pleaseSelectRoleLabel = new JLabel("Select your preferred Role for the selected Conference: ");


        gbc.insets = new Insets(0, 5, 5, 0);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(pleaseSelectConferenceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        myConferenceComboBox = conferenceSelectorBox();
        myConferenceComboBox.addActionListener(new ConferenceSelectorListener());
        myConferenceComboBox.setPreferredSize(DEFAULT_COMBO_BOX_SIZE);
        add(myConferenceComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(pleaseSelectRoleLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        myRoleComboBox = roleSelectorBox();
        myRoleComboBox.addActionListener(new RoleSelectorListener());
        myRoleComboBox.setPreferredSize(DEFAULT_COMBO_BOX_SIZE);
        myRoleComboBox.setEnabled(false);
        add(myRoleComboBox, gbc);
    }

    private JComboBox<Conference> conferenceSelectorBox() {
        Collection<Conference> allConferences =
                ConferenceStateManager.getInstance().getConferences();
        List<Conference> conferences = new ArrayList<>();
        for (Conference con : allConferences) {
            conferences.add(con);
        }
        DefaultListCellRenderer lsr = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                if (value instanceof Conference) {
                    value = ((Conference) value).getName();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        };
        Conference[] confArray = conferences.toArray(new Conference[conferences.size()]);
        JComboBox<Conference> conferenceDisplay = new JComboBox<>(confArray);
        conferenceDisplay.setRenderer(lsr);
        return conferenceDisplay;
    }

    private JComboBox<Role> roleSelectorBox() {
        DefaultListCellRenderer lsr = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                if (value instanceof Role) {
                    value = ((Role) value).getRoleName();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        };
        JComboBox<Role> roleDisplay = new JComboBox<>();
        roleDisplay.setRenderer(lsr);
        return roleDisplay;
    }

    private void fillRoleSelectorBox(final Conference theConference) {
        myRoleComboBox.setEnabled(false);
        myRoleComboBox.removeAllItems();
        Collection<Role> userRoles =
                UserProfileStateManager.getInstance().getCurrentUserProfile().getRolesForConference(theConference);
        for (final Role role : userRoles) {
            myRoleComboBox.addItem(role);
        }
    }

    private class ConferenceSelectorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox jcb = (JComboBox) e.getSource();
            Conference selectedConference = (Conference) jcb.getSelectedItem();
            ConferenceStateManager.getInstance().setCurrentConference(selectedConference);
            fillRoleSelectorBox(selectedConference);
            myRoleComboBox.setEnabled(true);
//            System.out.print(ConferenceStateManager.getInstance().getCurrentConference().getName());
        }
    }

    private class RoleSelectorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (myRoleComboBox.isEnabled()) {
                JComboBox jcb = (JComboBox) e.getSource();
                Role selectedRole = (Role) jcb.getSelectedItem();
                UserProfileStateManager.getInstance().setCurrentRole(selectedRole);
//                System.out.print(UserProfileStateManager.getInstance().getCurrentRole().getRoleName());
            }
        }
    }
}
