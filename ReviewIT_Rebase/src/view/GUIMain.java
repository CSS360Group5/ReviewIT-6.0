package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIMain {
    /**
     * A private constructor to prevent class instantiation.
     * @throws IllegalStateException Should not be instantiated.
     */
    private GUIMain() 
    {
        throw new IllegalStateException();
    }

    
    /**
     * The main method for starting up a JFrame containg the drawing application.
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) 
    {
        EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                final JFrame window = new JFrame();
                final JPanel mainPanel = new PanelManager(window);

                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                window.pack();
//                window.setMinimumSize(window.getSize());
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
    }
}
