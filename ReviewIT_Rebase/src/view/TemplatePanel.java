package view;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A template Panel for making of ReviewIT GUI.
 * To add components simply fill up init() method. Feel free to create other helper methods too.(don't bloat methods too much)
 * If you want to change the Conferences/Users/Manuscripts information that's initialized look into main. 
 * @author Dimitar Kumanov
 * @version 5/25/2017
 */
public class TemplatePanel extends AutoSizeablePanel {
	
	/**
	 * Sample main used to set up Conferences/Users/Manuscripts for testing this Panel
	 * and initialize it and put it into a JFrame.
	 */
	public static void main(String[] args){
		
		EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                final JFrame window = new JFrame();
                final JPanel mainPanel = new TemplatePanel(0.6, 0.4, new Dimension(750, 550));

                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setContentPane(mainPanel);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
	}
	
	
	public TemplatePanel(
			double theXRatio,
			double theYRatio,
			Dimension theStartingSize
			) {
		super(theXRatio, theYRatio, theStartingSize);
		init();
	}


	/**
	 * A private helper method for initializing all the components of this Panel. 
	 */
	private void init() {
		
	}

}
