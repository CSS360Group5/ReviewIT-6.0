package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class ConferenceActionsPanel extends AutoSizeablePanel {
	private static final long serialVersionUID = 4528406208478237005L;
	
	public ConferenceActionsPanel(
			final double theXRatio,
			final double theYRatio,
			final Dimension theStartingSize
			){
		super(theXRatio, theYRatio, theStartingSize);
		initialize();
	}
	
	private void initialize() {
		this.setBackground(new Color(Integer.parseInt("C2E7DA", 16)));
		JLabel sampleLabel = new JLabel("Actions");
		sampleLabel.setForeground(new Color(Integer.parseInt("1A1B41", 16)));
		sampleLabel.setFont(new Font("Times New Roman", 1, 40));
		add(sampleLabel);
	}

}
