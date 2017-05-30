package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelManager extends JPanel {

	private static final long serialVersionUID = -5854112401171609799L;
	
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 750;

	public static final String RESIZE_EVENT = "A Resize occured.";
	
	private final JFrame myFrame;
	
	JPanel navigationPanel;
	
	public PanelManager(final JFrame theFrame){
		super();
		myFrame = theFrame;
//		navigationPanel = (JPanel) new NavigationPanel(
//				0.6,
//				0.4,
//				new Dimension(WIDTH, HEIGHT)
//				);
//		initialize();
	}
	
	/**
	 * Changes the main navigation panel to thePanel
	 */
	public void setNavigationPanel(final JPanel thePanel){
		this.removeAll();
		navigationPanel = thePanel;
		initialize();
	}

	private void initialize() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(0, 0));
//		this.setLayout(new GridBagLayout());
//		
//		final GridBagConstraints constraints = new GridBagConstraints();
//		constraints.weightx = 0.05;
//		constraints.weighty = 0.05;
//		
//		constraints.gridwidth = 100;
//		constraints.gridheight = 20;
//		constraints.gridx = 0;
//		constraints.gridy = 0;
		
		final JPanel mainPanel = new HeaderPanel(0.6, 0.4, new Dimension(2100, 700));
//		final JPanel headerPanel = (JPanel) new HeaderPanel(
//				constraints.gridwidth / 100.0,
//				constraints.gridheight / 100.0,
//				new Dimension(WIDTH, HEIGHT)
//				);
//		add(headerPanel, constraints);
//		this.addPropertyChangeListener((PropertyChangeListener) headerPanel);
		this.add(mainPanel);
		this.add(navigationPanel);
//		constraints.gridwidth = 20;
//		constraints.gridheight = 80;
//		constraints.gridx = 0;
//		constraints.gridy = 20;
//		
//		final JPanel actionsPanel = (JPanel) new ConferenceActionsPanel(
//				constraints.gridwidth/ 100.0,
//				constraints.gridheight / 100.0,
//				new Dimension(WIDTH, HEIGHT)
//				);
//		add(actionsPanel, constraints);
//		this.addPropertyChangeListener((PropertyChangeListener) actionsPanel);
		
//		constraints.gridwidth = 80;
//		constraints.gridheight = 80;
//		constraints.gridx = 0;
//		constraints.gridy = 0;
//		
//		add(navigationPanel, constraints);
//		this.addPropertyChangeListener((PropertyChangeListener) navigationPanel);
//		
//		final ResizeListener listener = new ResizeListener();
//		this.addComponentListener(listener);
//		
//		myFrame.addWindowStateListener(new WindowStateListener() {
//	        public void windowStateChanged(final WindowEvent event) {
//	        	//its hacky but it works...except the first time.
//	        	myFrame.setVisible(false);
//	    		myFrame.setVisible(true);
//	    		alertResize();
//	    		myFrame.setVisible(false);
//	    		myFrame.setVisible(true);
//	        }
//		});
	}
	
	private void alertResize(){
		final Dimension newDimension = this.getSize(); 
		PanelManager.this.firePropertyChange(
				PanelManager.RESIZE_EVENT,
				null,
				newDimension
				);
		PanelManager.this.invalidate();		
//		repaint();	
	}
	/**
	 * Fires a property change listener from PanelManager alerting of my new size when resized.
	 * @author Dimitar Kumanov
	 *
	 */
    private class ResizeListener extends ComponentAdapter
    {
        @Override
        public void componentResized(final ComponentEvent theEvent)
        {
        	PanelManager.this.alertResize();
        }
    }
	
}
