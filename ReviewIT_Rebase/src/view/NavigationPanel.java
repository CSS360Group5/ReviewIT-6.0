package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class NavigationPanel extends AutoSizeablePanel {
	private static final long serialVersionUID = 6617565163207060009L;
	
	public NavigationPanel(
			final double theXRatio,
			final double theYRatio,
			final Dimension theStartingSize
			){
		super(theXRatio, theYRatio, theStartingSize);
		initialize();
	}

	private void initialize() {
		this.setBackground(new Color(Integer.parseInt("6290C3", 16)));
		JLabel sampleLabel = new JLabel("NavigationPanel");
		sampleLabel.setForeground(new Color(Integer.parseInt("F1FFE7", 16)));
		sampleLabel.setFont(new Font("Times New Roman", 1, 40));
		add(sampleLabel);
	}

}
