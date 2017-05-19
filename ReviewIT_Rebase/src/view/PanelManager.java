package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelManager extends JPanel {

	private static final long serialVersionUID = -5854112401171609799L;
	
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 750;

	public static final String RESIZE_EVENT = "A Resize occured.";
	
	private final JFrame myFrame;
	
	public PanelManager(final JFrame theFrame){
		super();
		myFrame = theFrame;
		initialize();
	}

	private void initialize() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(0, 0));
		this.setLayout(new GridBagLayout());
		
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		
		constraints.gridwidth = 100;
		constraints.gridheight = 20;
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		final JPanel headerPanel = (JPanel) new HeaderPanel(
				constraints.gridwidth / 100.0,
				constraints.gridheight / 100.0,
				new Dimension(WIDTH, HEIGHT)
				);
		add(headerPanel, constraints);
		this.addPropertyChangeListener((PropertyChangeListener) headerPanel);
		
		constraints.gridwidth = 20;
		constraints.gridheight = 80;
		constraints.gridx = 0;
		constraints.gridy = 20;
		
		final JPanel actionsPanel = (JPanel) new ConferenceActionsPanel(
				constraints.gridwidth/ 100.0,
				constraints.gridheight / 100.0,
				new Dimension(WIDTH, HEIGHT)
				);
		add(actionsPanel, constraints);
		this.addPropertyChangeListener((PropertyChangeListener) actionsPanel);
		
		constraints.gridwidth = 80;
		constraints.gridheight = 80;
		constraints.gridx = 20;
		constraints.gridy = 20;
		
		final JPanel navigationPanel = (JPanel) new NavigationPanel(
				constraints.gridwidth/ 100.0,
				constraints.gridheight / 100.0,
				new Dimension(WIDTH, HEIGHT)
				);
		add(navigationPanel, constraints);
		this.addPropertyChangeListener((PropertyChangeListener) navigationPanel);
		
		final ResizeListener listener = new ResizeListener();
		this.addComponentListener(listener);
	}
	
	/**
	 * Fires a property change listener from ReviewITPanel alerting of my new size.
	 * @author Dimitar Kumanov
	 *
	 */
    private class ResizeListener extends ComponentAdapter
    {
        @Override
        public void componentResized(final ComponentEvent theEvent)
        {
			final Dimension newDimension = ((JPanel) theEvent.getComponent()).getSize(); 
			PanelManager.this.firePropertyChange(
					PanelManager.RESIZE_EVENT,
					null,
					newDimension
					);
        }
    }
	
}
