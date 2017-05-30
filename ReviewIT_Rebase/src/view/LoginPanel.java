package view;

import javax.swing.*;
import java.awt.*;

/**
 * @author Kevin Ravana
 * @version 5/28/2017
 */
public class LoginPanel extends AutoSizeablePanel {

    public LoginPanel(final double theXRatio,
               final double theYRatio,
               final Dimension theStartingSize) {
        super(theXRatio, theYRatio, theStartingSize);
    }

    private void initialize() {
        add(new JLabel("Login"), BorderLayout.CENTER);
    }

}
