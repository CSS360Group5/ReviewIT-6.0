package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class HeaderPanel extends AutoSizeablePanel {
	private static final long serialVersionUID = 8098693823655450146L;

	public HeaderPanel(
			final double theXRatio,
			final double theYRatio,
			final Dimension theStartingSize
			){
		super(theXRatio, theYRatio, theStartingSize);
		initialize();
	}

	private void initialize() {
		this.setBackground(new Color(Integer.parseInt("1A1B41", 16)));
		JLabel sampleLabel = new JLabel("HeaderPanel");
		sampleLabel.setForeground(new Color(Integer.parseInt("BAFF29", 16)));
		sampleLabel.setFont(new Font("Times New Roman", 1, 40));
		add(sampleLabel);
	}
}

