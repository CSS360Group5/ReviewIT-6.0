package view;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;


/**
 * An custom JPanel class with automatic resizeable behavior.
 * Objects whose classes extend this class will always maintain a
 * proper X(theXRatio) and Y(theYRatio) ratio relative to theStartingSize.
 * In order for this to automatically resize it needs to be hooked up to a
 * component which fires property change listener with the new dimension.
 * @author Dimitar Kumanov
 *
 */
public abstract class AutoSizeablePanel extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = -3723466386912611355L;
	
	private double myXRatio;
	private double myYRatio;
	
	public AutoSizeablePanel(
			final double theXRatio,
			final double theYRatio,
			final Dimension theStartingSize
			){
		super();
		myXRatio = theXRatio;
		myYRatio = theYRatio;
		autosize(theStartingSize);			
	}

	@Override
	public void propertyChange(final PropertyChangeEvent theEvent) {
		if(PanelManager.RESIZE_EVENT.equals(theEvent.getPropertyName())){
			autosize((Dimension) theEvent.getNewValue());
		}
	}
	
	private void autosize(final Dimension theNewSize){
//		System.out.println(new Dimension(
//				(int) (theNewSize.getWidth() * myXRatio),
//				(int) (theNewSize.getHeight() * myYRatio)
//				));
		setMinimumSize(
				new Dimension(
				(int) (theNewSize.getWidth() * myXRatio),
				(int) (theNewSize.getHeight() * myYRatio)
				));
		setPreferredSize(
				new Dimension(
				(int) (theNewSize.getWidth() * myXRatio),
				(int) (theNewSize.getHeight() * myYRatio)
				));
	}

}
