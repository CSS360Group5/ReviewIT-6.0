package view;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

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
		setMinimumSize(new Dimension(
				(int) (theNewSize.getWidth() * myXRatio),
				(int) (theNewSize.getHeight() * myYRatio)
				));
		setPreferredSize(new Dimension(
				(int) (theNewSize.getWidth() * myXRatio),
				(int) (theNewSize.getHeight() * myYRatio)
				));
	}

}
