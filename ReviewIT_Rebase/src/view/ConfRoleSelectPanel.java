package view;

import model.Conference;
import model.ConferenceStateManager;
import model.Role;
import model.UserProfileStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton myContinueButton;

    private static final Dimension DEFAULT_COMBO_BOX_SIZE = new Dimension(250, 20);

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
        myConferenceComboBox.setSelectedIndex(-1);

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


        gbc.gridx = 1;
        gbc.gridy = 3;
        myContinueButton = continueButton();
        myContinueButton.setEnabled(false);
        add(myContinueButton, gbc);
    }

    private JButton continueButton() {
        JButton continueBtn = new JButton("Continue...");
        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continueToRolePanel();
            }
        });
        return continueBtn;
    }

    private void continueToRolePanel() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        final JFrame window = new JFrame();
        final PanelManager mainPanel = new PanelManager(window);


        if(UserProfileStateManager.getInstance().getCurrentRole().equals(Role.AUTHOR)){
            mainPanel.setNavigationPanel(new AuthorPanel(0.6, 0.4, new Dimension(2100, 700)));
        }else if(UserProfileStateManager.getInstance().getCurrentRole().equals(Role.SUBPROGRAM)){
            mainPanel.setNavigationPanel(new SubprogramPanel(mainPanel, 0.6, 0.4, new Dimension(2100, 700)));
        }

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(mainPanel);
        window.pack();
        window.setMinimumSize(new Dimension(750, 350));
        window.setLocationRelativeTo(frame);
        window.setVisible(true);
        window.setSize(new Dimension(1350, 450));
        frame.dispose();
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
        if (userRoles.isEmpty()) {
            UserProfileStateManager.getInstance().getCurrentUserProfile().addRole(Role.AUTHOR, theConference);
            myRoleComboBox.addItem(Role.AUTHOR);
        } else {
            for (final Role role : userRoles) {
                myRoleComboBox.addItem(role);
            }
        }
        myRoleComboBox.setSelectedIndex(-1);
    }

    private class ConferenceSelectorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox jcb = (JComboBox) e.getSource();
            if (jcb.getSelectedItem() instanceof Conference) {
                Conference selectedConference = (Conference) jcb.getSelectedItem();
                ConferenceStateManager.getInstance().setCurrentConference(selectedConference);
                fillRoleSelectorBox(selectedConference);
                myRoleComboBox.setEnabled(true);
            }
//            System.out.print(ConferenceStateManager.getInstance().getCurrentConference().getName());
        }
    }

    private class RoleSelectorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (myRoleComboBox.isEnabled()) {
                JComboBox jcb = (JComboBox) e.getSource();
                if (jcb.getSelectedItem() instanceof Role) {
                    Role selectedRole = (Role) jcb.getSelectedItem();
                    UserProfileStateManager.getInstance().setCurrentRole(selectedRole);
//                System.out.print(UserProfileStateManager.getInstance().getCurrentRole().getRoleName());
                    myContinueButton.setEnabled(true);
                }
            }
        }
    }
}
